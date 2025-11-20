/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.tree.CommandNode
 *  com.mojang.brigadier.tree.LiteralCommandNode
 */
package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.List;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class TeamMsgCommand {
    private static final Style STYLE = Style.EMPTY.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableText("chat.type.team.hover"))).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/teammsg "));
    private static final SimpleCommandExceptionType NO_TEAM_EXCEPTION = new SimpleCommandExceptionType((Message)new TranslatableText("commands.teammsg.failed.noteam"));

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralCommandNode literalCommandNode = dispatcher.register((LiteralArgumentBuilder)CommandManager.literal("teammsg").then(CommandManager.argument("message", MessageArgumentType.message()).executes(commandContext -> TeamMsgCommand.execute((ServerCommandSource)commandContext.getSource(), MessageArgumentType.getMessage((CommandContext<ServerCommandSource>)commandContext, "message")))));
        dispatcher.register((LiteralArgumentBuilder)CommandManager.literal("tm").redirect((CommandNode)literalCommandNode));
    }

    private static int execute(ServerCommandSource source, Text message) throws CommandSyntaxException {
        Entity entity = source.getEntityOrThrow();
        Team _snowman2 = (Team)entity.getScoreboardTeam();
        if (_snowman2 == null) {
            throw NO_TEAM_EXCEPTION.create();
        }
        MutableText _snowman3 = _snowman2.getFormattedName().fillStyle(STYLE);
        List<ServerPlayerEntity> _snowman4 = source.getMinecraftServer().getPlayerManager().getPlayerList();
        for (ServerPlayerEntity serverPlayerEntity : _snowman4) {
            if (serverPlayerEntity == entity) {
                serverPlayerEntity.sendSystemMessage(new TranslatableText("chat.type.team.sent", _snowman3, source.getDisplayName(), message), entity.getUuid());
                continue;
            }
            if (serverPlayerEntity.getScoreboardTeam() != _snowman2) continue;
            serverPlayerEntity.sendSystemMessage(new TranslatableText("chat.type.team.text", _snowman3, source.getDisplayName(), message), entity.getUuid());
        }
        return _snowman4.size();
    }
}

