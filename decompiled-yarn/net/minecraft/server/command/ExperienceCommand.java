package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntFunction;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.MathHelper;

public class ExperienceCommand {
   private static final SimpleCommandExceptionType SET_POINT_INVALID_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.experience.set.points.invalid")
   );

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      LiteralCommandNode<ServerCommandSource> _snowman = dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("experience")
                     .requires(_snowmanx -> _snowmanx.hasPermissionLevel(2)))
                  .then(
                     CommandManager.literal("add")
                        .then(
                           CommandManager.argument("targets", EntityArgumentType.players())
                              .then(
                                 ((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument("amount", IntegerArgumentType.integer())
                                          .executes(
                                             _snowmanx -> executeAdd(
                                                   (ServerCommandSource)_snowmanx.getSource(),
                                                   EntityArgumentType.getPlayers(_snowmanx, "targets"),
                                                   IntegerArgumentType.getInteger(_snowmanx, "amount"),
                                                   ExperienceCommand.Component.POINTS
                                                )
                                          ))
                                       .then(
                                          CommandManager.literal("points")
                                             .executes(
                                                _snowmanx -> executeAdd(
                                                      (ServerCommandSource)_snowmanx.getSource(),
                                                      EntityArgumentType.getPlayers(_snowmanx, "targets"),
                                                      IntegerArgumentType.getInteger(_snowmanx, "amount"),
                                                      ExperienceCommand.Component.POINTS
                                                   )
                                             )
                                       ))
                                    .then(
                                       CommandManager.literal("levels")
                                          .executes(
                                             _snowmanx -> executeAdd(
                                                   (ServerCommandSource)_snowmanx.getSource(),
                                                   EntityArgumentType.getPlayers(_snowmanx, "targets"),
                                                   IntegerArgumentType.getInteger(_snowmanx, "amount"),
                                                   ExperienceCommand.Component.LEVELS
                                                )
                                          )
                                    )
                              )
                        )
                  ))
               .then(
                  CommandManager.literal("set")
                     .then(
                        CommandManager.argument("targets", EntityArgumentType.players())
                           .then(
                              ((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument("amount", IntegerArgumentType.integer(0))
                                       .executes(
                                          _snowmanx -> executeSet(
                                                (ServerCommandSource)_snowmanx.getSource(),
                                                EntityArgumentType.getPlayers(_snowmanx, "targets"),
                                                IntegerArgumentType.getInteger(_snowmanx, "amount"),
                                                ExperienceCommand.Component.POINTS
                                             )
                                       ))
                                    .then(
                                       CommandManager.literal("points")
                                          .executes(
                                             _snowmanx -> executeSet(
                                                   (ServerCommandSource)_snowmanx.getSource(),
                                                   EntityArgumentType.getPlayers(_snowmanx, "targets"),
                                                   IntegerArgumentType.getInteger(_snowmanx, "amount"),
                                                   ExperienceCommand.Component.POINTS
                                                )
                                          )
                                    ))
                                 .then(
                                    CommandManager.literal("levels")
                                       .executes(
                                          _snowmanx -> executeSet(
                                                (ServerCommandSource)_snowmanx.getSource(),
                                                EntityArgumentType.getPlayers(_snowmanx, "targets"),
                                                IntegerArgumentType.getInteger(_snowmanx, "amount"),
                                                ExperienceCommand.Component.LEVELS
                                             )
                                       )
                                 )
                           )
                     )
               ))
            .then(
               CommandManager.literal("query")
                  .then(
                     ((RequiredArgumentBuilder)CommandManager.argument("targets", EntityArgumentType.player())
                           .then(
                              CommandManager.literal("points")
                                 .executes(
                                    _snowmanx -> executeQuery(
                                          (ServerCommandSource)_snowmanx.getSource(), EntityArgumentType.getPlayer(_snowmanx, "targets"), ExperienceCommand.Component.POINTS
                                       )
                                 )
                           ))
                        .then(
                           CommandManager.literal("levels")
                              .executes(
                                 _snowmanx -> executeQuery(
                                       (ServerCommandSource)_snowmanx.getSource(), EntityArgumentType.getPlayer(_snowmanx, "targets"), ExperienceCommand.Component.LEVELS
                                    )
                              )
                        )
                  )
            )
      );
      dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("xp").requires(_snowmanx -> _snowmanx.hasPermissionLevel(2))).redirect(_snowman));
   }

   private static int executeQuery(ServerCommandSource source, ServerPlayerEntity player, ExperienceCommand.Component component) {
      int _snowman = component.getter.applyAsInt(player);
      source.sendFeedback(new TranslatableText("commands.experience.query." + component.name, player.getDisplayName(), _snowman), false);
      return _snowman;
   }

   private static int executeAdd(
      ServerCommandSource source, Collection<? extends ServerPlayerEntity> targets, int amount, ExperienceCommand.Component component
   ) {
      for (ServerPlayerEntity _snowman : targets) {
         component.adder.accept(_snowman, amount);
      }

      if (targets.size() == 1) {
         source.sendFeedback(
            new TranslatableText("commands.experience.add." + component.name + ".success.single", amount, targets.iterator().next().getDisplayName()), true
         );
      } else {
         source.sendFeedback(new TranslatableText("commands.experience.add." + component.name + ".success.multiple", amount, targets.size()), true);
      }

      return targets.size();
   }

   private static int executeSet(
      ServerCommandSource source, Collection<? extends ServerPlayerEntity> targets, int amount, ExperienceCommand.Component component
   ) throws CommandSyntaxException {
      int _snowman = 0;

      for (ServerPlayerEntity _snowmanx : targets) {
         if (component.setter.test(_snowmanx, amount)) {
            _snowman++;
         }
      }

      if (_snowman == 0) {
         throw SET_POINT_INVALID_EXCEPTION.create();
      } else {
         if (targets.size() == 1) {
            source.sendFeedback(
               new TranslatableText("commands.experience.set." + component.name + ".success.single", amount, targets.iterator().next().getDisplayName()), true
            );
         } else {
            source.sendFeedback(new TranslatableText("commands.experience.set." + component.name + ".success.multiple", amount, targets.size()), true);
         }

         return targets.size();
      }
   }

   static enum Component {
      POINTS("points", PlayerEntity::addExperience, (_snowman, _snowmanx) -> {
         if (_snowmanx >= _snowman.getNextLevelExperience()) {
            return false;
         } else {
            _snowman.setExperiencePoints(_snowmanx);
            return true;
         }
      }, _snowman -> MathHelper.floor(_snowman.experienceProgress * (float)_snowman.getNextLevelExperience())),
      LEVELS("levels", ServerPlayerEntity::addExperienceLevels, (_snowman, _snowmanx) -> {
         _snowman.setExperienceLevel(_snowmanx);
         return true;
      }, _snowman -> _snowman.experienceLevel);

      public final BiConsumer<ServerPlayerEntity, Integer> adder;
      public final BiPredicate<ServerPlayerEntity, Integer> setter;
      public final String name;
      private final ToIntFunction<ServerPlayerEntity> getter;

      private Component(
         String name, BiConsumer<ServerPlayerEntity, Integer> adder, BiPredicate<ServerPlayerEntity, Integer> setter, ToIntFunction<ServerPlayerEntity> getter
      ) {
         this.adder = adder;
         this.name = name;
         this.setter = setter;
         this.getter = getter;
      }
   }
}
