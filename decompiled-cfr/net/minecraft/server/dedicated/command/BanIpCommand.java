/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  javax.annotation.Nullable
 */
package net.minecraft.server.dedicated.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.server.BannedIpEntry;
import net.minecraft.server.BannedIpList;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class BanIpCommand {
    public static final Pattern PATTERN = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    private static final SimpleCommandExceptionType INVALID_IP_EXCEPTION = new SimpleCommandExceptionType((Message)new TranslatableText("commands.banip.invalid"));
    private static final SimpleCommandExceptionType ALREADY_BANNED_EXCEPTION = new SimpleCommandExceptionType((Message)new TranslatableText("commands.banip.failed"));

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("ban-ip").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(3))).then(((RequiredArgumentBuilder)CommandManager.argument("target", StringArgumentType.word()).executes(commandContext -> BanIpCommand.checkIp((ServerCommandSource)commandContext.getSource(), StringArgumentType.getString((CommandContext)commandContext, (String)"target"), null))).then(CommandManager.argument("reason", MessageArgumentType.message()).executes(commandContext -> BanIpCommand.checkIp((ServerCommandSource)commandContext.getSource(), StringArgumentType.getString((CommandContext)commandContext, (String)"target"), MessageArgumentType.getMessage((CommandContext<ServerCommandSource>)commandContext, "reason"))))));
    }

    private static int checkIp(ServerCommandSource source, String target, @Nullable Text reason) throws CommandSyntaxException {
        Matcher matcher = PATTERN.matcher(target);
        if (matcher.matches()) {
            return BanIpCommand.banIp(source, target, reason);
        }
        ServerPlayerEntity _snowman2 = source.getMinecraftServer().getPlayerManager().getPlayer(target);
        if (_snowman2 != null) {
            return BanIpCommand.banIp(source, _snowman2.getIp(), reason);
        }
        throw INVALID_IP_EXCEPTION.create();
    }

    private static int banIp(ServerCommandSource source, String targetIp, @Nullable Text reason) throws CommandSyntaxException {
        BannedIpList bannedIpList = source.getMinecraftServer().getPlayerManager().getIpBanList();
        if (bannedIpList.isBanned(targetIp)) {
            throw ALREADY_BANNED_EXCEPTION.create();
        }
        List<ServerPlayerEntity> _snowman2 = source.getMinecraftServer().getPlayerManager().getPlayersByIp(targetIp);
        BannedIpEntry _snowman3 = new BannedIpEntry(targetIp, null, source.getName(), null, reason == null ? null : reason.getString());
        bannedIpList.add(_snowman3);
        source.sendFeedback(new TranslatableText("commands.banip.success", targetIp, _snowman3.getReason()), true);
        if (!_snowman2.isEmpty()) {
            source.sendFeedback(new TranslatableText("commands.banip.info", _snowman2.size(), EntitySelector.getNames(_snowman2)), true);
        }
        for (ServerPlayerEntity serverPlayerEntity : _snowman2) {
            serverPlayerEntity.networkHandler.disconnect(new TranslatableText("multiplayer.disconnect.ip_banned"));
        }
        return _snowman2.size();
    }
}

