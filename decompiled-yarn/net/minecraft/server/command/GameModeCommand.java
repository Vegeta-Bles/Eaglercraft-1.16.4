package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;

public class GameModeCommand {
   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      LiteralArgumentBuilder<ServerCommandSource> _snowman = (LiteralArgumentBuilder<ServerCommandSource>)CommandManager.literal("gamemode")
         .requires(_snowmanx -> _snowmanx.hasPermissionLevel(2));

      for (GameMode _snowmanx : GameMode.values()) {
         if (_snowmanx != GameMode.NOT_SET) {
            _snowman.then(
               ((LiteralArgumentBuilder)CommandManager.literal(_snowmanx.getName())
                     .executes(_snowmanxx -> execute(_snowmanxx, Collections.singleton(((ServerCommandSource)_snowmanxx.getSource()).getPlayer()), _snowman)))
                  .then(
                     CommandManager.argument("target", EntityArgumentType.players())
                        .executes(_snowmanxx -> execute(_snowmanxx, EntityArgumentType.getPlayers(_snowmanxx, "target"), _snowman))
                  )
            );
         }
      }

      dispatcher.register(_snowman);
   }

   private static void setGameMode(ServerCommandSource source, ServerPlayerEntity player, GameMode gameMode) {
      Text _snowman = new TranslatableText("gameMode." + gameMode.getName());
      if (source.getEntity() == player) {
         source.sendFeedback(new TranslatableText("commands.gamemode.success.self", _snowman), true);
      } else {
         if (source.getWorld().getGameRules().getBoolean(GameRules.SEND_COMMAND_FEEDBACK)) {
            player.sendSystemMessage(new TranslatableText("gameMode.changed", _snowman), Util.NIL_UUID);
         }

         source.sendFeedback(new TranslatableText("commands.gamemode.success.other", player.getDisplayName(), _snowman), true);
      }
   }

   private static int execute(CommandContext<ServerCommandSource> context, Collection<ServerPlayerEntity> targets, GameMode gameMode) {
      int _snowman = 0;

      for (ServerPlayerEntity _snowmanx : targets) {
         if (_snowmanx.interactionManager.getGameMode() != gameMode) {
            _snowmanx.setGameMode(gameMode);
            setGameMode((ServerCommandSource)context.getSource(), _snowmanx, gameMode);
            _snowman++;
         }
      }

      return _snowman;
   }
}
