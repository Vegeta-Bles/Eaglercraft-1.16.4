package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.GameMode;

public class DefaultGameModeCommand {
   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = (LiteralArgumentBuilder<ServerCommandSource>)CommandManager.literal(
            "defaultgamemode"
         )
         .requires(arg -> arg.hasPermissionLevel(2));

      for (GameMode lv : GameMode.values()) {
         if (lv != GameMode.NOT_SET) {
            literalArgumentBuilder.then(
               CommandManager.literal(lv.getName()).executes(commandContext -> execute((ServerCommandSource)commandContext.getSource(), lv))
            );
         }
      }

      dispatcher.register(literalArgumentBuilder);
   }

   private static int execute(ServerCommandSource source, GameMode defaultGameMode) {
      int i = 0;
      MinecraftServer minecraftServer = source.getMinecraftServer();
      minecraftServer.setDefaultGameMode(defaultGameMode);
      if (minecraftServer.shouldForceGameMode()) {
         for (ServerPlayerEntity lv : minecraftServer.getPlayerManager().getPlayerList()) {
            if (lv.interactionManager.getGameMode() != defaultGameMode) {
               lv.setGameMode(defaultGameMode);
               i++;
            }
         }
      }

      source.sendFeedback(new TranslatableText("commands.defaultgamemode.success", defaultGameMode.getTranslatableName()), true);
      return i;
   }
}
