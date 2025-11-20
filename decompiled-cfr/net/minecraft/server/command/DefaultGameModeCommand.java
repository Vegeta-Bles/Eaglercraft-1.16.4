/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 */
package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.GameMode;

public class DefaultGameModeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder literalArgumentBuilder = (LiteralArgumentBuilder)CommandManager.literal("defaultgamemode").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2));
        for (GameMode gameMode : GameMode.values()) {
            if (gameMode == GameMode.NOT_SET) continue;
            literalArgumentBuilder.then(CommandManager.literal(gameMode.getName()).executes(commandContext -> DefaultGameModeCommand.execute((ServerCommandSource)commandContext.getSource(), gameMode)));
        }
        dispatcher.register(literalArgumentBuilder);
    }

    private static int execute(ServerCommandSource source, GameMode defaultGameMode) {
        int n = 0;
        MinecraftServer _snowman2 = source.getMinecraftServer();
        _snowman2.setDefaultGameMode(defaultGameMode);
        if (_snowman2.shouldForceGameMode()) {
            for (ServerPlayerEntity serverPlayerEntity : _snowman2.getPlayerManager().getPlayerList()) {
                if (serverPlayerEntity.interactionManager.getGameMode() == defaultGameMode) continue;
                serverPlayerEntity.setGameMode(defaultGameMode);
                ++n;
            }
        }
        source.sendFeedback(new TranslatableText("commands.defaultgamemode.success", defaultGameMode.getTranslatableName()), true);
        return n;
    }
}

