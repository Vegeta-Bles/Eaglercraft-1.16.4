package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.network.packet.s2c.play.PlaySoundIdS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class PlaySoundCommand {
   private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.playsound.failed"));

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      RequiredArgumentBuilder<ServerCommandSource, Identifier> _snowman = CommandManager.argument("sound", IdentifierArgumentType.identifier())
         .suggests(SuggestionProviders.AVAILABLE_SOUNDS);

      for (SoundCategory _snowmanx : SoundCategory.values()) {
         _snowman.then(makeArgumentsForCategory(_snowmanx));
      }

      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("playsound").requires(_snowmanx -> _snowmanx.hasPermissionLevel(2))).then(_snowman)
      );
   }

   private static LiteralArgumentBuilder<ServerCommandSource> makeArgumentsForCategory(SoundCategory category) {
      return (LiteralArgumentBuilder<ServerCommandSource>)CommandManager.literal(category.getName())
         .then(
            ((RequiredArgumentBuilder)CommandManager.argument("targets", EntityArgumentType.players())
                  .executes(
                     _snowmanx -> execute(
                           (ServerCommandSource)_snowmanx.getSource(),
                           EntityArgumentType.getPlayers(_snowmanx, "targets"),
                           IdentifierArgumentType.getIdentifier(_snowmanx, "sound"),
                           category,
                           ((ServerCommandSource)_snowmanx.getSource()).getPosition(),
                           1.0F,
                           1.0F,
                           0.0F
                        )
                  ))
               .then(
                  ((RequiredArgumentBuilder)CommandManager.argument("pos", Vec3ArgumentType.vec3())
                        .executes(
                           _snowmanx -> execute(
                                 (ServerCommandSource)_snowmanx.getSource(),
                                 EntityArgumentType.getPlayers(_snowmanx, "targets"),
                                 IdentifierArgumentType.getIdentifier(_snowmanx, "sound"),
                                 category,
                                 Vec3ArgumentType.getVec3(_snowmanx, "pos"),
                                 1.0F,
                                 1.0F,
                                 0.0F
                              )
                        ))
                     .then(
                        ((RequiredArgumentBuilder)CommandManager.argument("volume", FloatArgumentType.floatArg(0.0F))
                              .executes(
                                 _snowmanx -> execute(
                                       (ServerCommandSource)_snowmanx.getSource(),
                                       EntityArgumentType.getPlayers(_snowmanx, "targets"),
                                       IdentifierArgumentType.getIdentifier(_snowmanx, "sound"),
                                       category,
                                       Vec3ArgumentType.getVec3(_snowmanx, "pos"),
                                       (Float)_snowmanx.getArgument("volume", Float.class),
                                       1.0F,
                                       0.0F
                                    )
                              ))
                           .then(
                              ((RequiredArgumentBuilder)CommandManager.argument("pitch", FloatArgumentType.floatArg(0.0F, 2.0F))
                                    .executes(
                                       _snowmanx -> execute(
                                             (ServerCommandSource)_snowmanx.getSource(),
                                             EntityArgumentType.getPlayers(_snowmanx, "targets"),
                                             IdentifierArgumentType.getIdentifier(_snowmanx, "sound"),
                                             category,
                                             Vec3ArgumentType.getVec3(_snowmanx, "pos"),
                                             (Float)_snowmanx.getArgument("volume", Float.class),
                                             (Float)_snowmanx.getArgument("pitch", Float.class),
                                             0.0F
                                          )
                                    ))
                                 .then(
                                    CommandManager.argument("minVolume", FloatArgumentType.floatArg(0.0F, 1.0F))
                                       .executes(
                                          _snowmanx -> execute(
                                                (ServerCommandSource)_snowmanx.getSource(),
                                                EntityArgumentType.getPlayers(_snowmanx, "targets"),
                                                IdentifierArgumentType.getIdentifier(_snowmanx, "sound"),
                                                category,
                                                Vec3ArgumentType.getVec3(_snowmanx, "pos"),
                                                (Float)_snowmanx.getArgument("volume", Float.class),
                                                (Float)_snowmanx.getArgument("pitch", Float.class),
                                                (Float)_snowmanx.getArgument("minVolume", Float.class)
                                             )
                                       )
                                 )
                           )
                     )
               )
         );
   }

   private static int execute(
      ServerCommandSource source,
      Collection<ServerPlayerEntity> targets,
      Identifier sound,
      SoundCategory category,
      Vec3d pos,
      float volume,
      float pitch,
      float minVolume
   ) throws CommandSyntaxException {
      double _snowman = Math.pow(volume > 1.0F ? (double)(volume * 16.0F) : 16.0, 2.0);
      int _snowmanx = 0;

      for (ServerPlayerEntity _snowmanxx : targets) {
         double _snowmanxxx = pos.x - _snowmanxx.getX();
         double _snowmanxxxx = pos.y - _snowmanxx.getY();
         double _snowmanxxxxx = pos.z - _snowmanxx.getZ();
         double _snowmanxxxxxx = _snowmanxxx * _snowmanxxx + _snowmanxxxx * _snowmanxxxx + _snowmanxxxxx * _snowmanxxxxx;
         Vec3d _snowmanxxxxxxx = pos;
         float _snowmanxxxxxxxx = volume;
         if (_snowmanxxxxxx > _snowman) {
            if (minVolume <= 0.0F) {
               continue;
            }

            double _snowmanxxxxxxxxx = (double)MathHelper.sqrt(_snowmanxxxxxx);
            _snowmanxxxxxxx = new Vec3d(_snowmanxx.getX() + _snowmanxxx / _snowmanxxxxxxxxx * 2.0, _snowmanxx.getY() + _snowmanxxxx / _snowmanxxxxxxxxx * 2.0, _snowmanxx.getZ() + _snowmanxxxxx / _snowmanxxxxxxxxx * 2.0);
            _snowmanxxxxxxxx = minVolume;
         }

         _snowmanxx.networkHandler.sendPacket(new PlaySoundIdS2CPacket(sound, category, _snowmanxxxxxxx, _snowmanxxxxxxxx, pitch));
         _snowmanx++;
      }

      if (_snowmanx == 0) {
         throw FAILED_EXCEPTION.create();
      } else {
         if (targets.size() == 1) {
            source.sendFeedback(new TranslatableText("commands.playsound.success.single", sound, targets.iterator().next().getDisplayName()), true);
         } else {
            source.sendFeedback(new TranslatableText("commands.playsound.success.multiple", sound, targets.size()), true);
         }

         return _snowmanx;
      }
   }
}
