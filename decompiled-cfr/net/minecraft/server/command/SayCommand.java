/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 */
package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;

public class SayCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("say").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))).then(CommandManager.argument("message", MessageArgumentType.message()).executes(commandContext -> {
            Text text = MessageArgumentType.getMessage((CommandContext<ServerCommandSource>)commandContext, "message");
            TranslatableText _snowman2 = new TranslatableText("chat.type.announcement", ((ServerCommandSource)commandContext.getSource()).getDisplayName(), text);
            Entity _snowman3 = ((ServerCommandSource)commandContext.getSource()).getEntity();
            if (_snowman3 != null) {
                ((ServerCommandSource)commandContext.getSource()).getMinecraftServer().getPlayerManager().broadcastChatMessage(_snowman2, MessageType.CHAT, _snowman3.getUuid());
            } else {
                ((ServerCommandSource)commandContext.getSource()).getMinecraftServer().getPlayerManager().broadcastChatMessage(_snowman2, MessageType.SYSTEM, Util.NIL_UUID);
            }
            return 1;
        })));
    }
}

