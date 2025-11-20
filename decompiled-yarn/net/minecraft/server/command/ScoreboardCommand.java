package net.minecraft.server.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.ObjectiveArgumentType;
import net.minecraft.command.argument.ObjectiveCriteriaArgumentType;
import net.minecraft.command.argument.OperationArgumentType;
import net.minecraft.command.argument.ScoreHolderArgumentType;
import net.minecraft.command.argument.ScoreboardSlotArgumentType;
import net.minecraft.command.argument.TextArgumentType;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.text.TranslatableText;

public class ScoreboardCommand {
   private static final SimpleCommandExceptionType OBJECTIVES_ADD_DUPLICATE_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.scoreboard.objectives.add.duplicate")
   );
   private static final SimpleCommandExceptionType OBJECTIVES_DISPLAY_ALREADY_EMPTY_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.scoreboard.objectives.display.alreadyEmpty")
   );
   private static final SimpleCommandExceptionType OBJECTIVES_DISPLAY_ALREADY_SET_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.scoreboard.objectives.display.alreadySet")
   );
   private static final SimpleCommandExceptionType PLAYERS_ENABLE_FAILED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.scoreboard.players.enable.failed")
   );
   private static final SimpleCommandExceptionType PLAYERS_ENABLE_INVALID_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.scoreboard.players.enable.invalid")
   );
   private static final Dynamic2CommandExceptionType PLAYERS_GET_NULL_EXCEPTION = new Dynamic2CommandExceptionType(
      (_snowman, _snowmanx) -> new TranslatableText("commands.scoreboard.players.get.null", _snowman, _snowmanx)
   );

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("scoreboard").requires(_snowman -> _snowman.hasPermissionLevel(2)))
               .then(
                  ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("objectives")
                                 .then(CommandManager.literal("list").executes(_snowman -> executeListObjectives((ServerCommandSource)_snowman.getSource()))))
                              .then(
                                 CommandManager.literal("add")
                                    .then(
                                       CommandManager.argument("objective", StringArgumentType.word())
                                          .then(
                                             ((RequiredArgumentBuilder)CommandManager.argument("criteria", ObjectiveCriteriaArgumentType.objectiveCriteria())
                                                   .executes(
                                                      _snowman -> executeAddObjective(
                                                            (ServerCommandSource)_snowman.getSource(),
                                                            StringArgumentType.getString(_snowman, "objective"),
                                                            ObjectiveCriteriaArgumentType.getCriteria(_snowman, "criteria"),
                                                            new LiteralText(StringArgumentType.getString(_snowman, "objective"))
                                                         )
                                                   ))
                                                .then(
                                                   CommandManager.argument("displayName", TextArgumentType.text())
                                                      .executes(
                                                         _snowman -> executeAddObjective(
                                                               (ServerCommandSource)_snowman.getSource(),
                                                               StringArgumentType.getString(_snowman, "objective"),
                                                               ObjectiveCriteriaArgumentType.getCriteria(_snowman, "criteria"),
                                                               TextArgumentType.getTextArgument(_snowman, "displayName")
                                                            )
                                                      )
                                                )
                                          )
                                    )
                              ))
                           .then(
                              CommandManager.literal("modify")
                                 .then(
                                    ((RequiredArgumentBuilder)CommandManager.argument("objective", ObjectiveArgumentType.objective())
                                          .then(
                                             CommandManager.literal("displayname")
                                                .then(
                                                   CommandManager.argument("displayName", TextArgumentType.text())
                                                      .executes(
                                                         _snowman -> executeModifyObjective(
                                                               (ServerCommandSource)_snowman.getSource(),
                                                               ObjectiveArgumentType.getObjective(_snowman, "objective"),
                                                               TextArgumentType.getTextArgument(_snowman, "displayName")
                                                            )
                                                      )
                                                )
                                          ))
                                       .then(makeRenderTypeArguments())
                                 )
                           ))
                        .then(
                           CommandManager.literal("remove")
                              .then(
                                 CommandManager.argument("objective", ObjectiveArgumentType.objective())
                                    .executes(
                                       _snowman -> executeRemoveObjective((ServerCommandSource)_snowman.getSource(), ObjectiveArgumentType.getObjective(_snowman, "objective"))
                                    )
                              )
                        ))
                     .then(
                        CommandManager.literal("setdisplay")
                           .then(
                              ((RequiredArgumentBuilder)CommandManager.argument("slot", ScoreboardSlotArgumentType.scoreboardSlot())
                                    .executes(
                                       _snowman -> executeClearDisplay((ServerCommandSource)_snowman.getSource(), ScoreboardSlotArgumentType.getScoreboardSlot(_snowman, "slot"))
                                    ))
                                 .then(
                                    CommandManager.argument("objective", ObjectiveArgumentType.objective())
                                       .executes(
                                          _snowman -> executeSetDisplay(
                                                (ServerCommandSource)_snowman.getSource(),
                                                ScoreboardSlotArgumentType.getScoreboardSlot(_snowman, "slot"),
                                                ObjectiveArgumentType.getObjective(_snowman, "objective")
                                             )
                                       )
                                 )
                           )
                     )
               ))
            .then(
               ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal(
                                          "players"
                                       )
                                       .then(
                                          ((LiteralArgumentBuilder)CommandManager.literal("list")
                                                .executes(_snowman -> executeListPlayers((ServerCommandSource)_snowman.getSource())))
                                             .then(
                                                CommandManager.argument("target", ScoreHolderArgumentType.scoreHolder())
                                                   .suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER)
                                                   .executes(
                                                      _snowman -> executeListScores(
                                                            (ServerCommandSource)_snowman.getSource(), ScoreHolderArgumentType.getScoreHolder(_snowman, "target")
                                                         )
                                                   )
                                             )
                                       ))
                                    .then(
                                       CommandManager.literal("set")
                                          .then(
                                             CommandManager.argument("targets", ScoreHolderArgumentType.scoreHolders())
                                                .suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER)
                                                .then(
                                                   CommandManager.argument("objective", ObjectiveArgumentType.objective())
                                                      .then(
                                                         CommandManager.argument("score", IntegerArgumentType.integer())
                                                            .executes(
                                                               _snowman -> executeSet(
                                                                     (ServerCommandSource)_snowman.getSource(),
                                                                     ScoreHolderArgumentType.getScoreboardScoreHolders(_snowman, "targets"),
                                                                     ObjectiveArgumentType.getWritableObjective(_snowman, "objective"),
                                                                     IntegerArgumentType.getInteger(_snowman, "score")
                                                                  )
                                                            )
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    CommandManager.literal("get")
                                       .then(
                                          CommandManager.argument("target", ScoreHolderArgumentType.scoreHolder())
                                             .suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER)
                                             .then(
                                                CommandManager.argument("objective", ObjectiveArgumentType.objective())
                                                   .executes(
                                                      _snowman -> executeGet(
                                                            (ServerCommandSource)_snowman.getSource(),
                                                            ScoreHolderArgumentType.getScoreHolder(_snowman, "target"),
                                                            ObjectiveArgumentType.getObjective(_snowman, "objective")
                                                         )
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 CommandManager.literal("add")
                                    .then(
                                       CommandManager.argument("targets", ScoreHolderArgumentType.scoreHolders())
                                          .suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER)
                                          .then(
                                             CommandManager.argument("objective", ObjectiveArgumentType.objective())
                                                .then(
                                                   CommandManager.argument("score", IntegerArgumentType.integer(0))
                                                      .executes(
                                                         _snowman -> executeAdd(
                                                               (ServerCommandSource)_snowman.getSource(),
                                                               ScoreHolderArgumentType.getScoreboardScoreHolders(_snowman, "targets"),
                                                               ObjectiveArgumentType.getWritableObjective(_snowman, "objective"),
                                                               IntegerArgumentType.getInteger(_snowman, "score")
                                                            )
                                                      )
                                                )
                                          )
                                    )
                              ))
                           .then(
                              CommandManager.literal("remove")
                                 .then(
                                    CommandManager.argument("targets", ScoreHolderArgumentType.scoreHolders())
                                       .suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER)
                                       .then(
                                          CommandManager.argument("objective", ObjectiveArgumentType.objective())
                                             .then(
                                                CommandManager.argument("score", IntegerArgumentType.integer(0))
                                                   .executes(
                                                      _snowman -> executeRemove(
                                                            (ServerCommandSource)_snowman.getSource(),
                                                            ScoreHolderArgumentType.getScoreboardScoreHolders(_snowman, "targets"),
                                                            ObjectiveArgumentType.getWritableObjective(_snowman, "objective"),
                                                            IntegerArgumentType.getInteger(_snowman, "score")
                                                         )
                                                   )
                                             )
                                       )
                                 )
                           ))
                        .then(
                           CommandManager.literal("reset")
                              .then(
                                 ((RequiredArgumentBuilder)CommandManager.argument("targets", ScoreHolderArgumentType.scoreHolders())
                                       .suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER)
                                       .executes(
                                          _snowman -> executeReset((ServerCommandSource)_snowman.getSource(), ScoreHolderArgumentType.getScoreboardScoreHolders(_snowman, "targets"))
                                       ))
                                    .then(
                                       CommandManager.argument("objective", ObjectiveArgumentType.objective())
                                          .executes(
                                             _snowman -> executeReset(
                                                   (ServerCommandSource)_snowman.getSource(),
                                                   ScoreHolderArgumentType.getScoreboardScoreHolders(_snowman, "targets"),
                                                   ObjectiveArgumentType.getObjective(_snowman, "objective")
                                                )
                                          )
                                    )
                              )
                        ))
                     .then(
                        CommandManager.literal("enable")
                           .then(
                              CommandManager.argument("targets", ScoreHolderArgumentType.scoreHolders())
                                 .suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER)
                                 .then(
                                    CommandManager.argument("objective", ObjectiveArgumentType.objective())
                                       .suggests(
                                          (_snowman, _snowmanx) -> suggestDisabled(
                                                (ServerCommandSource)_snowman.getSource(), ScoreHolderArgumentType.getScoreboardScoreHolders(_snowman, "targets"), _snowmanx
                                             )
                                       )
                                       .executes(
                                          _snowman -> executeEnable(
                                                (ServerCommandSource)_snowman.getSource(),
                                                ScoreHolderArgumentType.getScoreboardScoreHolders(_snowman, "targets"),
                                                ObjectiveArgumentType.getObjective(_snowman, "objective")
                                             )
                                       )
                                 )
                           )
                     ))
                  .then(
                     CommandManager.literal("operation")
                        .then(
                           CommandManager.argument("targets", ScoreHolderArgumentType.scoreHolders())
                              .suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER)
                              .then(
                                 CommandManager.argument("targetObjective", ObjectiveArgumentType.objective())
                                    .then(
                                       CommandManager.argument("operation", OperationArgumentType.operation())
                                          .then(
                                             CommandManager.argument("source", ScoreHolderArgumentType.scoreHolders())
                                                .suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER)
                                                .then(
                                                   CommandManager.argument("sourceObjective", ObjectiveArgumentType.objective())
                                                      .executes(
                                                         _snowman -> executeOperation(
                                                               (ServerCommandSource)_snowman.getSource(),
                                                               ScoreHolderArgumentType.getScoreboardScoreHolders(_snowman, "targets"),
                                                               ObjectiveArgumentType.getWritableObjective(_snowman, "targetObjective"),
                                                               OperationArgumentType.getOperation(_snowman, "operation"),
                                                               ScoreHolderArgumentType.getScoreboardScoreHolders(_snowman, "source"),
                                                               ObjectiveArgumentType.getObjective(_snowman, "sourceObjective")
                                                            )
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

   private static LiteralArgumentBuilder<ServerCommandSource> makeRenderTypeArguments() {
      LiteralArgumentBuilder<ServerCommandSource> _snowman = CommandManager.literal("rendertype");

      for (ScoreboardCriterion.RenderType _snowmanx : ScoreboardCriterion.RenderType.values()) {
         _snowman.then(
            CommandManager.literal(_snowmanx.getName())
               .executes(_snowmanxx -> executeModifyRenderType((ServerCommandSource)_snowmanxx.getSource(), ObjectiveArgumentType.getObjective(_snowmanxx, "objective"), _snowman))
         );
      }

      return _snowman;
   }

   private static CompletableFuture<Suggestions> suggestDisabled(ServerCommandSource source, Collection<String> targets, SuggestionsBuilder builder) {
      List<String> _snowman = Lists.newArrayList();
      Scoreboard _snowmanx = source.getMinecraftServer().getScoreboard();

      for (ScoreboardObjective _snowmanxx : _snowmanx.getObjectives()) {
         if (_snowmanxx.getCriterion() == ScoreboardCriterion.TRIGGER) {
            boolean _snowmanxxx = false;

            for (String _snowmanxxxx : targets) {
               if (!_snowmanx.playerHasObjective(_snowmanxxxx, _snowmanxx) || _snowmanx.getPlayerScore(_snowmanxxxx, _snowmanxx).isLocked()) {
                  _snowmanxxx = true;
                  break;
               }
            }

            if (_snowmanxxx) {
               _snowman.add(_snowmanxx.getName());
            }
         }
      }

      return CommandSource.suggestMatching(_snowman, builder);
   }

   private static int executeGet(ServerCommandSource source, String target, ScoreboardObjective objective) throws CommandSyntaxException {
      Scoreboard _snowman = source.getMinecraftServer().getScoreboard();
      if (!_snowman.playerHasObjective(target, objective)) {
         throw PLAYERS_GET_NULL_EXCEPTION.create(objective.getName(), target);
      } else {
         ScoreboardPlayerScore _snowmanx = _snowman.getPlayerScore(target, objective);
         source.sendFeedback(new TranslatableText("commands.scoreboard.players.get.success", target, _snowmanx.getScore(), objective.toHoverableText()), false);
         return _snowmanx.getScore();
      }
   }

   private static int executeOperation(
      ServerCommandSource source,
      Collection<String> targets,
      ScoreboardObjective targetObjective,
      OperationArgumentType.Operation operation,
      Collection<String> sources,
      ScoreboardObjective sourceObjectives
   ) throws CommandSyntaxException {
      Scoreboard _snowman = source.getMinecraftServer().getScoreboard();
      int _snowmanx = 0;

      for (String _snowmanxx : targets) {
         ScoreboardPlayerScore _snowmanxxx = _snowman.getPlayerScore(_snowmanxx, targetObjective);

         for (String _snowmanxxxx : sources) {
            ScoreboardPlayerScore _snowmanxxxxx = _snowman.getPlayerScore(_snowmanxxxx, sourceObjectives);
            operation.apply(_snowmanxxx, _snowmanxxxxx);
         }

         _snowmanx += _snowmanxxx.getScore();
      }

      if (targets.size() == 1) {
         source.sendFeedback(
            new TranslatableText("commands.scoreboard.players.operation.success.single", targetObjective.toHoverableText(), targets.iterator().next(), _snowmanx),
            true
         );
      } else {
         source.sendFeedback(
            new TranslatableText("commands.scoreboard.players.operation.success.multiple", targetObjective.toHoverableText(), targets.size()), true
         );
      }

      return _snowmanx;
   }

   private static int executeEnable(ServerCommandSource source, Collection<String> targets, ScoreboardObjective objective) throws CommandSyntaxException {
      if (objective.getCriterion() != ScoreboardCriterion.TRIGGER) {
         throw PLAYERS_ENABLE_INVALID_EXCEPTION.create();
      } else {
         Scoreboard _snowman = source.getMinecraftServer().getScoreboard();
         int _snowmanx = 0;

         for (String _snowmanxx : targets) {
            ScoreboardPlayerScore _snowmanxxx = _snowman.getPlayerScore(_snowmanxx, objective);
            if (_snowmanxxx.isLocked()) {
               _snowmanxxx.setLocked(false);
               _snowmanx++;
            }
         }

         if (_snowmanx == 0) {
            throw PLAYERS_ENABLE_FAILED_EXCEPTION.create();
         } else {
            if (targets.size() == 1) {
               source.sendFeedback(
                  new TranslatableText("commands.scoreboard.players.enable.success.single", objective.toHoverableText(), targets.iterator().next()), true
               );
            } else {
               source.sendFeedback(
                  new TranslatableText("commands.scoreboard.players.enable.success.multiple", objective.toHoverableText(), targets.size()), true
               );
            }

            return _snowmanx;
         }
      }
   }

   private static int executeReset(ServerCommandSource source, Collection<String> targets) {
      Scoreboard _snowman = source.getMinecraftServer().getScoreboard();

      for (String _snowmanx : targets) {
         _snowman.resetPlayerScore(_snowmanx, null);
      }

      if (targets.size() == 1) {
         source.sendFeedback(new TranslatableText("commands.scoreboard.players.reset.all.single", targets.iterator().next()), true);
      } else {
         source.sendFeedback(new TranslatableText("commands.scoreboard.players.reset.all.multiple", targets.size()), true);
      }

      return targets.size();
   }

   private static int executeReset(ServerCommandSource source, Collection<String> targets, ScoreboardObjective objective) {
      Scoreboard _snowman = source.getMinecraftServer().getScoreboard();

      for (String _snowmanx : targets) {
         _snowman.resetPlayerScore(_snowmanx, objective);
      }

      if (targets.size() == 1) {
         source.sendFeedback(
            new TranslatableText("commands.scoreboard.players.reset.specific.single", objective.toHoverableText(), targets.iterator().next()), true
         );
      } else {
         source.sendFeedback(new TranslatableText("commands.scoreboard.players.reset.specific.multiple", objective.toHoverableText(), targets.size()), true);
      }

      return targets.size();
   }

   private static int executeSet(ServerCommandSource source, Collection<String> targets, ScoreboardObjective objective, int score) {
      Scoreboard _snowman = source.getMinecraftServer().getScoreboard();

      for (String _snowmanx : targets) {
         ScoreboardPlayerScore _snowmanxx = _snowman.getPlayerScore(_snowmanx, objective);
         _snowmanxx.setScore(score);
      }

      if (targets.size() == 1) {
         source.sendFeedback(
            new TranslatableText("commands.scoreboard.players.set.success.single", objective.toHoverableText(), targets.iterator().next(), score), true
         );
      } else {
         source.sendFeedback(new TranslatableText("commands.scoreboard.players.set.success.multiple", objective.toHoverableText(), targets.size(), score), true);
      }

      return score * targets.size();
   }

   private static int executeAdd(ServerCommandSource source, Collection<String> targets, ScoreboardObjective objective, int score) {
      Scoreboard _snowman = source.getMinecraftServer().getScoreboard();
      int _snowmanx = 0;

      for (String _snowmanxx : targets) {
         ScoreboardPlayerScore _snowmanxxx = _snowman.getPlayerScore(_snowmanxx, objective);
         _snowmanxxx.setScore(_snowmanxxx.getScore() + score);
         _snowmanx += _snowmanxxx.getScore();
      }

      if (targets.size() == 1) {
         source.sendFeedback(
            new TranslatableText("commands.scoreboard.players.add.success.single", score, objective.toHoverableText(), targets.iterator().next(), _snowmanx), true
         );
      } else {
         source.sendFeedback(new TranslatableText("commands.scoreboard.players.add.success.multiple", score, objective.toHoverableText(), targets.size()), true);
      }

      return _snowmanx;
   }

   private static int executeRemove(ServerCommandSource source, Collection<String> targets, ScoreboardObjective objective, int score) {
      Scoreboard _snowman = source.getMinecraftServer().getScoreboard();
      int _snowmanx = 0;

      for (String _snowmanxx : targets) {
         ScoreboardPlayerScore _snowmanxxx = _snowman.getPlayerScore(_snowmanxx, objective);
         _snowmanxxx.setScore(_snowmanxxx.getScore() - score);
         _snowmanx += _snowmanxxx.getScore();
      }

      if (targets.size() == 1) {
         source.sendFeedback(
            new TranslatableText("commands.scoreboard.players.remove.success.single", score, objective.toHoverableText(), targets.iterator().next(), _snowmanx), true
         );
      } else {
         source.sendFeedback(
            new TranslatableText("commands.scoreboard.players.remove.success.multiple", score, objective.toHoverableText(), targets.size()), true
         );
      }

      return _snowmanx;
   }

   private static int executeListPlayers(ServerCommandSource source) {
      Collection<String> _snowman = source.getMinecraftServer().getScoreboard().getKnownPlayers();
      if (_snowman.isEmpty()) {
         source.sendFeedback(new TranslatableText("commands.scoreboard.players.list.empty"), false);
      } else {
         source.sendFeedback(new TranslatableText("commands.scoreboard.players.list.success", _snowman.size(), Texts.joinOrdered(_snowman)), false);
      }

      return _snowman.size();
   }

   private static int executeListScores(ServerCommandSource source, String target) {
      Map<ScoreboardObjective, ScoreboardPlayerScore> _snowman = source.getMinecraftServer().getScoreboard().getPlayerObjectives(target);
      if (_snowman.isEmpty()) {
         source.sendFeedback(new TranslatableText("commands.scoreboard.players.list.entity.empty", target), false);
      } else {
         source.sendFeedback(new TranslatableText("commands.scoreboard.players.list.entity.success", target, _snowman.size()), false);

         for (Entry<ScoreboardObjective, ScoreboardPlayerScore> _snowmanx : _snowman.entrySet()) {
            source.sendFeedback(
               new TranslatableText("commands.scoreboard.players.list.entity.entry", _snowmanx.getKey().toHoverableText(), _snowmanx.getValue().getScore()), false
            );
         }
      }

      return _snowman.size();
   }

   private static int executeClearDisplay(ServerCommandSource source, int slot) throws CommandSyntaxException {
      Scoreboard _snowman = source.getMinecraftServer().getScoreboard();
      if (_snowman.getObjectiveForSlot(slot) == null) {
         throw OBJECTIVES_DISPLAY_ALREADY_EMPTY_EXCEPTION.create();
      } else {
         _snowman.setObjectiveSlot(slot, null);
         source.sendFeedback(new TranslatableText("commands.scoreboard.objectives.display.cleared", Scoreboard.getDisplaySlotNames()[slot]), true);
         return 0;
      }
   }

   private static int executeSetDisplay(ServerCommandSource source, int slot, ScoreboardObjective objective) throws CommandSyntaxException {
      Scoreboard _snowman = source.getMinecraftServer().getScoreboard();
      if (_snowman.getObjectiveForSlot(slot) == objective) {
         throw OBJECTIVES_DISPLAY_ALREADY_SET_EXCEPTION.create();
      } else {
         _snowman.setObjectiveSlot(slot, objective);
         source.sendFeedback(
            new TranslatableText("commands.scoreboard.objectives.display.set", Scoreboard.getDisplaySlotNames()[slot], objective.getDisplayName()), true
         );
         return 0;
      }
   }

   private static int executeModifyObjective(ServerCommandSource source, ScoreboardObjective objective, Text displayName) {
      if (!objective.getDisplayName().equals(displayName)) {
         objective.setDisplayName(displayName);
         source.sendFeedback(new TranslatableText("commands.scoreboard.objectives.modify.displayname", objective.getName(), objective.toHoverableText()), true);
      }

      return 0;
   }

   private static int executeModifyRenderType(ServerCommandSource source, ScoreboardObjective objective, ScoreboardCriterion.RenderType type) {
      if (objective.getRenderType() != type) {
         objective.setRenderType(type);
         source.sendFeedback(new TranslatableText("commands.scoreboard.objectives.modify.rendertype", objective.toHoverableText()), true);
      }

      return 0;
   }

   private static int executeRemoveObjective(ServerCommandSource source, ScoreboardObjective objective) {
      Scoreboard _snowman = source.getMinecraftServer().getScoreboard();
      _snowman.removeObjective(objective);
      source.sendFeedback(new TranslatableText("commands.scoreboard.objectives.remove.success", objective.toHoverableText()), true);
      return _snowman.getObjectives().size();
   }

   private static int executeAddObjective(ServerCommandSource source, String objective, ScoreboardCriterion criteria, Text displayName) throws CommandSyntaxException {
      Scoreboard _snowman = source.getMinecraftServer().getScoreboard();
      if (_snowman.getNullableObjective(objective) != null) {
         throw OBJECTIVES_ADD_DUPLICATE_EXCEPTION.create();
      } else if (objective.length() > 16) {
         throw ObjectiveArgumentType.LONG_NAME_EXCEPTION.create(16);
      } else {
         _snowman.addObjective(objective, criteria, displayName, criteria.getCriterionType());
         ScoreboardObjective _snowmanx = _snowman.getNullableObjective(objective);
         source.sendFeedback(new TranslatableText("commands.scoreboard.objectives.add.success", _snowmanx.toHoverableText()), true);
         return _snowman.getObjectives().size();
      }
   }

   private static int executeListObjectives(ServerCommandSource source) {
      Collection<ScoreboardObjective> _snowman = source.getMinecraftServer().getScoreboard().getObjectives();
      if (_snowman.isEmpty()) {
         source.sendFeedback(new TranslatableText("commands.scoreboard.objectives.list.empty"), false);
      } else {
         source.sendFeedback(
            new TranslatableText("commands.scoreboard.objectives.list.success", _snowman.size(), Texts.join(_snowman, ScoreboardObjective::toHoverableText)), false
         );
      }

      return _snowman.size();
   }
}
