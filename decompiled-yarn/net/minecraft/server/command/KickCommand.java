package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.Collection;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class KickCommand {
   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("kick").requires(_snowman -> _snowman.hasPermissionLevel(3)))
            .then(
               ((RequiredArgumentBuilder)CommandManager.argument("targets", EntityArgumentType.players())
                     .executes(
                        _snowman -> execute(
                              (ServerCommandSource)_snowman.getSource(),
                              EntityArgumentType.getPlayers(_snowman, "targets"),
                              new TranslatableText("multiplayer.disconnect.kicked")
                           )
                     ))
                  .then(
                     CommandManager.argument("reason", MessageArgumentType.message())
                        .executes(
                           _snowman -> execute(
                                 (ServerCommandSource)_snowman.getSource(), EntityArgumentType.getPlayers(_snowman, "targets"), MessageArgumentType.getMessage(_snowman, "reason")
                              )
                        )
                  )
            )
      );
   }

   private static int execute(ServerCommandSource source, Collection<ServerPlayerEntity> targets, Text reason) {
      for (ServerPlayerEntity _snowman : targets) {
         _snowman.networkHandler.disconnect(reason);
         source.sendFeedback(new TranslatableText("commands.kick.success", _snowman.getDisplayName(), reason), true);
      }

      return targets.size();
   }
}
