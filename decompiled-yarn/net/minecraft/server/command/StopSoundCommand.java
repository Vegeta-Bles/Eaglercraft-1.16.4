package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.network.packet.s2c.play.StopSoundS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class StopSoundCommand {
   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      RequiredArgumentBuilder<ServerCommandSource, EntitySelector> _snowman = (RequiredArgumentBuilder<ServerCommandSource, EntitySelector>)((RequiredArgumentBuilder)CommandManager.argument(
               "targets", EntityArgumentType.players()
            )
            .executes(_snowmanx -> execute((ServerCommandSource)_snowmanx.getSource(), EntityArgumentType.getPlayers(_snowmanx, "targets"), null, null)))
         .then(
            CommandManager.literal("*")
               .then(
                  CommandManager.argument("sound", IdentifierArgumentType.identifier())
                     .suggests(SuggestionProviders.AVAILABLE_SOUNDS)
                     .executes(
                        _snowmanx -> execute(
                              (ServerCommandSource)_snowmanx.getSource(),
                              EntityArgumentType.getPlayers(_snowmanx, "targets"),
                              null,
                              IdentifierArgumentType.getIdentifier(_snowmanx, "sound")
                           )
                     )
               )
         );

      for (SoundCategory _snowmanx : SoundCategory.values()) {
         _snowman.then(
            ((LiteralArgumentBuilder)CommandManager.literal(_snowmanx.getName())
                  .executes(_snowmanxx -> execute((ServerCommandSource)_snowmanxx.getSource(), EntityArgumentType.getPlayers(_snowmanxx, "targets"), _snowman, null)))
               .then(
                  CommandManager.argument("sound", IdentifierArgumentType.identifier())
                     .suggests(SuggestionProviders.AVAILABLE_SOUNDS)
                     .executes(
                        _snowmanxx -> execute(
                              (ServerCommandSource)_snowmanxx.getSource(),
                              EntityArgumentType.getPlayers(_snowmanxx, "targets"),
                              _snowman,
                              IdentifierArgumentType.getIdentifier(_snowmanxx, "sound")
                           )
                     )
               )
         );
      }

      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("stopsound").requires(_snowmanx -> _snowmanx.hasPermissionLevel(2))).then(_snowman)
      );
   }

   private static int execute(ServerCommandSource source, Collection<ServerPlayerEntity> targets, @Nullable SoundCategory category, @Nullable Identifier sound) {
      StopSoundS2CPacket _snowman = new StopSoundS2CPacket(sound, category);

      for (ServerPlayerEntity _snowmanx : targets) {
         _snowmanx.networkHandler.sendPacket(_snowman);
      }

      if (category != null) {
         if (sound != null) {
            source.sendFeedback(new TranslatableText("commands.stopsound.success.source.sound", sound, category.getName()), true);
         } else {
            source.sendFeedback(new TranslatableText("commands.stopsound.success.source.any", category.getName()), true);
         }
      } else if (sound != null) {
         source.sendFeedback(new TranslatableText("commands.stopsound.success.sourceless.sound", sound), true);
      } else {
         source.sendFeedback(new TranslatableText("commands.stopsound.success.sourceless.any"), true);
      }

      return targets.size();
   }
}
