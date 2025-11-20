package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.GameMode;

public class DefaultGameModeCommand {
   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      LiteralArgumentBuilder<ServerCommandSource> _snowman = (LiteralArgumentBuilder<ServerCommandSource>)CommandManager.literal("defaultgamemode")
         .requires(_snowmanx -> _snowmanx.hasPermissionLevel(2));

      for (GameMode _snowmanx : GameMode.values()) {
         if (_snowmanx != GameMode.NOT_SET) {
            _snowman.then(CommandManager.literal(_snowmanx.getName()).executes(_snowmanxx -> execute((ServerCommandSource)_snowmanxx.getSource(), _snowman)));
         }
      }

      dispatcher.register(_snowman);
   }

   private static int execute(ServerCommandSource source, GameMode defaultGameMode) {
      int _snowman = 0;
      MinecraftServer _snowmanx = source.getMinecraftServer();
      _snowmanx.setDefaultGameMode(defaultGameMode);
      if (_snowmanx.shouldForceGameMode()) {
         for (ServerPlayerEntity _snowmanxx : _snowmanx.getPlayerManager().getPlayerList()) {
            if (_snowmanxx.interactionManager.getGameMode() != defaultGameMode) {
               _snowmanxx.setGameMode(defaultGameMode);
               _snowman++;
            }
         }
      }

      source.sendFeedback(new TranslatableText("commands.defaultgamemode.success", defaultGameMode.getTranslatableName()), true);
      return _snowman;
   }
}
