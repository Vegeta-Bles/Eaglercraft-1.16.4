package net.minecraft.server.command;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.MobEffectArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.text.TranslatableText;

public class EffectCommand {
   private static final SimpleCommandExceptionType GIVE_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.effect.give.failed"));
   private static final SimpleCommandExceptionType CLEAR_EVERYTHING_FAILED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.effect.clear.everything.failed")
   );
   private static final SimpleCommandExceptionType CLEAR_SPECIFIC_FAILED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.effect.clear.specific.failed")
   );

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("effect").requires(_snowman -> _snowman.hasPermissionLevel(2)))
               .then(
                  ((LiteralArgumentBuilder)CommandManager.literal("clear")
                        .executes(
                           _snowman -> executeClear((ServerCommandSource)_snowman.getSource(), ImmutableList.of(((ServerCommandSource)_snowman.getSource()).getEntityOrThrow()))
                        ))
                     .then(
                        ((RequiredArgumentBuilder)CommandManager.argument("targets", EntityArgumentType.entities())
                              .executes(_snowman -> executeClear((ServerCommandSource)_snowman.getSource(), EntityArgumentType.getEntities(_snowman, "targets"))))
                           .then(
                              CommandManager.argument("effect", MobEffectArgumentType.mobEffect())
                                 .executes(
                                    _snowman -> executeClear(
                                          (ServerCommandSource)_snowman.getSource(),
                                          EntityArgumentType.getEntities(_snowman, "targets"),
                                          MobEffectArgumentType.getMobEffect(_snowman, "effect")
                                       )
                                 )
                           )
                     )
               ))
            .then(
               CommandManager.literal("give")
                  .then(
                     CommandManager.argument("targets", EntityArgumentType.entities())
                        .then(
                           ((RequiredArgumentBuilder)CommandManager.argument("effect", MobEffectArgumentType.mobEffect())
                                 .executes(
                                    _snowman -> executeGive(
                                          (ServerCommandSource)_snowman.getSource(),
                                          EntityArgumentType.getEntities(_snowman, "targets"),
                                          MobEffectArgumentType.getMobEffect(_snowman, "effect"),
                                          null,
                                          0,
                                          true
                                       )
                                 ))
                              .then(
                                 ((RequiredArgumentBuilder)CommandManager.argument("seconds", IntegerArgumentType.integer(1, 1000000))
                                       .executes(
                                          _snowman -> executeGive(
                                                (ServerCommandSource)_snowman.getSource(),
                                                EntityArgumentType.getEntities(_snowman, "targets"),
                                                MobEffectArgumentType.getMobEffect(_snowman, "effect"),
                                                IntegerArgumentType.getInteger(_snowman, "seconds"),
                                                0,
                                                true
                                             )
                                       ))
                                    .then(
                                       ((RequiredArgumentBuilder)CommandManager.argument("amplifier", IntegerArgumentType.integer(0, 255))
                                             .executes(
                                                _snowman -> executeGive(
                                                      (ServerCommandSource)_snowman.getSource(),
                                                      EntityArgumentType.getEntities(_snowman, "targets"),
                                                      MobEffectArgumentType.getMobEffect(_snowman, "effect"),
                                                      IntegerArgumentType.getInteger(_snowman, "seconds"),
                                                      IntegerArgumentType.getInteger(_snowman, "amplifier"),
                                                      true
                                                   )
                                             ))
                                          .then(
                                             CommandManager.argument("hideParticles", BoolArgumentType.bool())
                                                .executes(
                                                   _snowman -> executeGive(
                                                         (ServerCommandSource)_snowman.getSource(),
                                                         EntityArgumentType.getEntities(_snowman, "targets"),
                                                         MobEffectArgumentType.getMobEffect(_snowman, "effect"),
                                                         IntegerArgumentType.getInteger(_snowman, "seconds"),
                                                         IntegerArgumentType.getInteger(_snowman, "amplifier"),
                                                         !BoolArgumentType.getBool(_snowman, "hideParticles")
                                                      )
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int executeGive(
      ServerCommandSource source, Collection<? extends Entity> targets, StatusEffect effect, @Nullable Integer seconds, int amplifier, boolean showParticles
   ) throws CommandSyntaxException {
      int _snowman = 0;
      int _snowmanx;
      if (seconds != null) {
         if (effect.isInstant()) {
            _snowmanx = seconds;
         } else {
            _snowmanx = seconds * 20;
         }
      } else if (effect.isInstant()) {
         _snowmanx = 1;
      } else {
         _snowmanx = 600;
      }

      for (Entity _snowmanxx : targets) {
         if (_snowmanxx instanceof LivingEntity) {
            StatusEffectInstance _snowmanxxx = new StatusEffectInstance(effect, _snowmanx, amplifier, false, showParticles);
            if (((LivingEntity)_snowmanxx).addStatusEffect(_snowmanxxx)) {
               _snowman++;
            }
         }
      }

      if (_snowman == 0) {
         throw GIVE_FAILED_EXCEPTION.create();
      } else {
         if (targets.size() == 1) {
            source.sendFeedback(
               new TranslatableText("commands.effect.give.success.single", effect.getName(), targets.iterator().next().getDisplayName(), _snowmanx / 20), true
            );
         } else {
            source.sendFeedback(new TranslatableText("commands.effect.give.success.multiple", effect.getName(), targets.size(), _snowmanx / 20), true);
         }

         return _snowman;
      }
   }

   private static int executeClear(ServerCommandSource source, Collection<? extends Entity> targets) throws CommandSyntaxException {
      int _snowman = 0;

      for (Entity _snowmanx : targets) {
         if (_snowmanx instanceof LivingEntity && ((LivingEntity)_snowmanx).clearStatusEffects()) {
            _snowman++;
         }
      }

      if (_snowman == 0) {
         throw CLEAR_EVERYTHING_FAILED_EXCEPTION.create();
      } else {
         if (targets.size() == 1) {
            source.sendFeedback(new TranslatableText("commands.effect.clear.everything.success.single", targets.iterator().next().getDisplayName()), true);
         } else {
            source.sendFeedback(new TranslatableText("commands.effect.clear.everything.success.multiple", targets.size()), true);
         }

         return _snowman;
      }
   }

   private static int executeClear(ServerCommandSource source, Collection<? extends Entity> targets, StatusEffect effect) throws CommandSyntaxException {
      int _snowman = 0;

      for (Entity _snowmanx : targets) {
         if (_snowmanx instanceof LivingEntity && ((LivingEntity)_snowmanx).removeStatusEffect(effect)) {
            _snowman++;
         }
      }

      if (_snowman == 0) {
         throw CLEAR_SPECIFIC_FAILED_EXCEPTION.create();
      } else {
         if (targets.size() == 1) {
            source.sendFeedback(
               new TranslatableText("commands.effect.clear.specific.success.single", effect.getName(), targets.iterator().next().getDisplayName()), true
            );
         } else {
            source.sendFeedback(new TranslatableText("commands.effect.clear.specific.success.multiple", effect.getName(), targets.size()), true);
         }

         return _snowman;
      }
   }
}
