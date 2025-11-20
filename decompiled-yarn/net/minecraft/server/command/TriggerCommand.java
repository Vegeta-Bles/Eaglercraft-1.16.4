package net.minecraft.server.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.ObjectiveArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

public class TriggerCommand {
   private static final SimpleCommandExceptionType FAILED_UNPRIMED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.trigger.failed.unprimed")
   );
   private static final SimpleCommandExceptionType FAILED_INVALID_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.trigger.failed.invalid")
   );

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)CommandManager.literal("trigger")
            .then(
               ((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument("objective", ObjectiveArgumentType.objective())
                        .suggests((_snowman, _snowmanx) -> suggestObjectives((ServerCommandSource)_snowman.getSource(), _snowmanx))
                        .executes(
                           _snowman -> executeSimple(
                                 (ServerCommandSource)_snowman.getSource(),
                                 getScore(((ServerCommandSource)_snowman.getSource()).getPlayer(), ObjectiveArgumentType.getObjective(_snowman, "objective"))
                              )
                        ))
                     .then(
                        CommandManager.literal("add")
                           .then(
                              CommandManager.argument("value", IntegerArgumentType.integer())
                                 .executes(
                                    _snowman -> executeAdd(
                                          (ServerCommandSource)_snowman.getSource(),
                                          getScore(((ServerCommandSource)_snowman.getSource()).getPlayer(), ObjectiveArgumentType.getObjective(_snowman, "objective")),
                                          IntegerArgumentType.getInteger(_snowman, "value")
                                       )
                                 )
                           )
                     ))
                  .then(
                     CommandManager.literal("set")
                        .then(
                           CommandManager.argument("value", IntegerArgumentType.integer())
                              .executes(
                                 _snowman -> executeSet(
                                       (ServerCommandSource)_snowman.getSource(),
                                       getScore(((ServerCommandSource)_snowman.getSource()).getPlayer(), ObjectiveArgumentType.getObjective(_snowman, "objective")),
                                       IntegerArgumentType.getInteger(_snowman, "value")
                                    )
                              )
                        )
                  )
            )
      );
   }

   public static CompletableFuture<Suggestions> suggestObjectives(ServerCommandSource source, SuggestionsBuilder builder) {
      Entity _snowman = source.getEntity();
      List<String> _snowmanx = Lists.newArrayList();
      if (_snowman != null) {
         Scoreboard _snowmanxx = source.getMinecraftServer().getScoreboard();
         String _snowmanxxx = _snowman.getEntityName();

         for (ScoreboardObjective _snowmanxxxx : _snowmanxx.getObjectives()) {
            if (_snowmanxxxx.getCriterion() == ScoreboardCriterion.TRIGGER && _snowmanxx.playerHasObjective(_snowmanxxx, _snowmanxxxx)) {
               ScoreboardPlayerScore _snowmanxxxxx = _snowmanxx.getPlayerScore(_snowmanxxx, _snowmanxxxx);
               if (!_snowmanxxxxx.isLocked()) {
                  _snowmanx.add(_snowmanxxxx.getName());
               }
            }
         }
      }

      return CommandSource.suggestMatching(_snowmanx, builder);
   }

   private static int executeAdd(ServerCommandSource source, ScoreboardPlayerScore score, int value) {
      score.incrementScore(value);
      source.sendFeedback(new TranslatableText("commands.trigger.add.success", score.getObjective().toHoverableText(), value), true);
      return score.getScore();
   }

   private static int executeSet(ServerCommandSource source, ScoreboardPlayerScore score, int value) {
      score.setScore(value);
      source.sendFeedback(new TranslatableText("commands.trigger.set.success", score.getObjective().toHoverableText(), value), true);
      return value;
   }

   private static int executeSimple(ServerCommandSource source, ScoreboardPlayerScore score) {
      score.incrementScore(1);
      source.sendFeedback(new TranslatableText("commands.trigger.simple.success", score.getObjective().toHoverableText()), true);
      return score.getScore();
   }

   private static ScoreboardPlayerScore getScore(ServerPlayerEntity player, ScoreboardObjective objective) throws CommandSyntaxException {
      if (objective.getCriterion() != ScoreboardCriterion.TRIGGER) {
         throw FAILED_INVALID_EXCEPTION.create();
      } else {
         Scoreboard _snowman = player.getScoreboard();
         String _snowmanx = player.getEntityName();
         if (!_snowman.playerHasObjective(_snowmanx, objective)) {
            throw FAILED_UNPRIMED_EXCEPTION.create();
         } else {
            ScoreboardPlayerScore _snowmanxx = _snowman.getPlayerScore(_snowmanx, objective);
            if (_snowmanxx.isLocked()) {
               throw FAILED_UNPRIMED_EXCEPTION.create();
            } else {
               _snowmanxx.setLocked(true);
               return _snowmanxx;
            }
         }
      }
   }
}
