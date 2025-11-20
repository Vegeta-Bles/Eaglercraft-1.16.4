package net.minecraft.server.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ResultConsumer;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.IntFunction;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.command.CommandSource;
import net.minecraft.command.DataCommandObject;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.BlockPredicateArgumentType;
import net.minecraft.command.argument.DimensionArgumentType;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.command.argument.NbtPathArgumentType;
import net.minecraft.command.argument.NumberRangeArgumentType;
import net.minecraft.command.argument.ObjectiveArgumentType;
import net.minecraft.command.argument.RotationArgumentType;
import net.minecraft.command.argument.ScoreHolderArgumentType;
import net.minecraft.command.argument.SwizzleArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.CommandBossBar;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionManager;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.Tag;
import net.minecraft.predicate.NumberRange;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;

public class ExecuteCommand {
   private static final Dynamic2CommandExceptionType BLOCKS_TOOBIG_EXCEPTION = new Dynamic2CommandExceptionType(
      (_snowman, _snowmanx) -> new TranslatableText("commands.execute.blocks.toobig", _snowman, _snowmanx)
   );
   private static final SimpleCommandExceptionType CONDITIONAL_FAIL_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.execute.conditional.fail")
   );
   private static final DynamicCommandExceptionType CONDITIONAL_FAIL_COUNT_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("commands.execute.conditional.fail_count", _snowman)
   );
   private static final BinaryOperator<ResultConsumer<ServerCommandSource>> BINARY_RESULT_CONSUMER = (_snowman, _snowmanx) -> (_snowmanxxxxx, _snowmanxxxx, _snowmanxxx) -> {
         _snowman.onCommandComplete(_snowmanxxxxx, _snowmanxxxx, _snowmanxxx);
         _snowmanx.onCommandComplete(_snowmanxxxxx, _snowmanxxxx, _snowmanxxx);
      };
   private static final SuggestionProvider<ServerCommandSource> LOOT_CONDITIONS = (_snowman, _snowmanx) -> {
      LootConditionManager _snowmanxx = ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getPredicateManager();
      return CommandSource.suggestIdentifiers(_snowmanxx.getIds(), _snowmanx);
   };

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      LiteralCommandNode<ServerCommandSource> _snowman = dispatcher.register(
         (LiteralArgumentBuilder)CommandManager.literal("execute").requires(_snowmanx -> _snowmanx.hasPermissionLevel(2))
      );
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal(
                                                   "execute"
                                                )
                                                .requires(_snowmanx -> _snowmanx.hasPermissionLevel(2)))
                                             .then(CommandManager.literal("run").redirect(dispatcher.getRoot())))
                                          .then(addConditionArguments(_snowman, CommandManager.literal("if"), true)))
                                       .then(addConditionArguments(_snowman, CommandManager.literal("unless"), false)))
                                    .then(CommandManager.literal("as").then(CommandManager.argument("targets", EntityArgumentType.entities()).fork(_snowman, _snowmanx -> {
                                       List<ServerCommandSource> _snowmanx = Lists.newArrayList();

                                       for (Entity _snowmanxx : EntityArgumentType.getOptionalEntities(_snowmanx, "targets")) {
                                          _snowmanx.add(((ServerCommandSource)_snowmanx.getSource()).withEntity(_snowmanxx));
                                       }

                                       return _snowmanx;
                                    }))))
                                 .then(
                                    CommandManager.literal("at")
                                       .then(
                                          CommandManager.argument("targets", EntityArgumentType.entities())
                                             .fork(
                                                _snowman,
                                                _snowmanx -> {
                                                   List<ServerCommandSource> _snowmanx = Lists.newArrayList();

                                                   for (Entity _snowmanxx : EntityArgumentType.getOptionalEntities(_snowmanx, "targets")) {
                                                      _snowmanx.add(
                                                         ((ServerCommandSource)_snowmanx.getSource())
                                                            .withWorld((ServerWorld)_snowmanxx.world)
                                                            .withPosition(_snowmanxx.getPos())
                                                            .withRotation(_snowmanxx.getRotationClient())
                                                      );
                                                   }

                                                   return _snowmanx;
                                                }
                                             )
                                       )
                                 ))
                              .then(
                                 ((LiteralArgumentBuilder)CommandManager.literal("store").then(addStoreArguments(_snowman, CommandManager.literal("result"), true)))
                                    .then(addStoreArguments(_snowman, CommandManager.literal("success"), false))
                              ))
                           .then(
                              ((LiteralArgumentBuilder)CommandManager.literal("positioned")
                                    .then(
                                       CommandManager.argument("pos", Vec3ArgumentType.vec3())
                                          .redirect(
                                             _snowman,
                                             _snowmanx -> ((ServerCommandSource)_snowmanx.getSource())
                                                   .withPosition(Vec3ArgumentType.getVec3(_snowmanx, "pos"))
                                                   .withEntityAnchor(EntityAnchorArgumentType.EntityAnchor.FEET)
                                          )
                                    ))
                                 .then(CommandManager.literal("as").then(CommandManager.argument("targets", EntityArgumentType.entities()).fork(_snowman, _snowmanx -> {
                                    List<ServerCommandSource> _snowmanx = Lists.newArrayList();

                                    for (Entity _snowmanxx : EntityArgumentType.getOptionalEntities(_snowmanx, "targets")) {
                                       _snowmanx.add(((ServerCommandSource)_snowmanx.getSource()).withPosition(_snowmanxx.getPos()));
                                    }

                                    return _snowmanx;
                                 })))
                           ))
                        .then(
                           ((LiteralArgumentBuilder)CommandManager.literal("rotated")
                                 .then(
                                    CommandManager.argument("rot", RotationArgumentType.rotation())
                                       .redirect(
                                          _snowman,
                                          _snowmanx -> ((ServerCommandSource)_snowmanx.getSource())
                                                .withRotation(
                                                   RotationArgumentType.getRotation(_snowmanx, "rot").toAbsoluteRotation((ServerCommandSource)_snowmanx.getSource())
                                                )
                                       )
                                 ))
                              .then(CommandManager.literal("as").then(CommandManager.argument("targets", EntityArgumentType.entities()).fork(_snowman, _snowmanx -> {
                                 List<ServerCommandSource> _snowmanx = Lists.newArrayList();

                                 for (Entity _snowmanxx : EntityArgumentType.getOptionalEntities(_snowmanx, "targets")) {
                                    _snowmanx.add(((ServerCommandSource)_snowmanx.getSource()).withRotation(_snowmanxx.getRotationClient()));
                                 }

                                 return _snowmanx;
                              })))
                        ))
                     .then(
                        ((LiteralArgumentBuilder)CommandManager.literal("facing")
                              .then(
                                 CommandManager.literal("entity")
                                    .then(
                                       CommandManager.argument("targets", EntityArgumentType.entities())
                                          .then(CommandManager.argument("anchor", EntityAnchorArgumentType.entityAnchor()).fork(_snowman, _snowmanx -> {
                                             List<ServerCommandSource> _snowmanx = Lists.newArrayList();
                                             EntityAnchorArgumentType.EntityAnchor _snowmanxx = EntityAnchorArgumentType.getEntityAnchor(_snowmanx, "anchor");

                                             for (Entity _snowmanxxx : EntityArgumentType.getOptionalEntities(_snowmanx, "targets")) {
                                                _snowmanx.add(((ServerCommandSource)_snowmanx.getSource()).withLookingAt(_snowmanxxx, _snowmanxx));
                                             }

                                             return _snowmanx;
                                          }))
                                    )
                              ))
                           .then(
                              CommandManager.argument("pos", Vec3ArgumentType.vec3())
                                 .redirect(_snowman, _snowmanx -> ((ServerCommandSource)_snowmanx.getSource()).withLookingAt(Vec3ArgumentType.getVec3(_snowmanx, "pos")))
                           )
                     ))
                  .then(
                     CommandManager.literal("align")
                        .then(
                           CommandManager.argument("axes", SwizzleArgumentType.swizzle())
                              .redirect(
                                 _snowman,
                                 _snowmanx -> ((ServerCommandSource)_snowmanx.getSource())
                                       .withPosition(
                                          ((ServerCommandSource)_snowmanx.getSource()).getPosition().floorAlongAxes(SwizzleArgumentType.getSwizzle(_snowmanx, "axes"))
                                       )
                              )
                        )
                  ))
               .then(
                  CommandManager.literal("anchored")
                     .then(
                        CommandManager.argument("anchor", EntityAnchorArgumentType.entityAnchor())
                           .redirect(_snowman, _snowmanx -> ((ServerCommandSource)_snowmanx.getSource()).withEntityAnchor(EntityAnchorArgumentType.getEntityAnchor(_snowmanx, "anchor")))
                     )
               ))
            .then(
               CommandManager.literal("in")
                  .then(
                     CommandManager.argument("dimension", DimensionArgumentType.dimension())
                        .redirect(_snowman, _snowmanx -> ((ServerCommandSource)_snowmanx.getSource()).withWorld(DimensionArgumentType.getDimensionArgument(_snowmanx, "dimension")))
                  )
            )
      );
   }

   private static ArgumentBuilder<ServerCommandSource, ?> addStoreArguments(
      LiteralCommandNode<ServerCommandSource> node, LiteralArgumentBuilder<ServerCommandSource> builder, boolean requestResult
   ) {
      builder.then(
         CommandManager.literal("score")
            .then(
               CommandManager.argument("targets", ScoreHolderArgumentType.scoreHolders())
                  .suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER)
                  .then(
                     CommandManager.argument("objective", ObjectiveArgumentType.objective())
                        .redirect(
                           node,
                           _snowmanx -> executeStoreScore(
                                 (ServerCommandSource)_snowmanx.getSource(),
                                 ScoreHolderArgumentType.getScoreboardScoreHolders(_snowmanx, "targets"),
                                 ObjectiveArgumentType.getObjective(_snowmanx, "objective"),
                                 requestResult
                              )
                        )
                  )
            )
      );
      builder.then(
         CommandManager.literal("bossbar")
            .then(
               ((RequiredArgumentBuilder)CommandManager.argument("id", IdentifierArgumentType.identifier())
                     .suggests(BossBarCommand.SUGGESTION_PROVIDER)
                     .then(
                        CommandManager.literal("value")
                           .redirect(node, _snowmanx -> executeStoreBossbar((ServerCommandSource)_snowmanx.getSource(), BossBarCommand.getBossBar(_snowmanx), true, requestResult))
                     ))
                  .then(
                     CommandManager.literal("max")
                        .redirect(node, _snowmanx -> executeStoreBossbar((ServerCommandSource)_snowmanx.getSource(), BossBarCommand.getBossBar(_snowmanx), false, requestResult))
                  )
            )
      );

      for (DataCommand.ObjectType _snowman : DataCommand.TARGET_OBJECT_TYPES) {
         _snowman.addArgumentsToBuilder(
            builder,
            _snowmanxxx -> _snowmanxxx.then(
                  ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument(
                                       "path", NbtPathArgumentType.nbtPath()
                                    )
                                    .then(
                                       CommandManager.literal("int")
                                          .then(
                                             CommandManager.argument("scale", DoubleArgumentType.doubleArg())
                                                .redirect(
                                                   node,
                                                   _snowmanxxxxx -> executeStoreData(
                                                         (ServerCommandSource)_snowmanxxxxx.getSource(),
                                                         _snowman.getObject(_snowmanxxxxx),
                                                         NbtPathArgumentType.getNbtPath(_snowmanxxxxx, "path"),
                                                         _snowmanxxxxxxxx -> IntTag.of((int)((double)_snowmanxxxxxxxx * DoubleArgumentType.getDouble(_snowmanxxxxx, "scale"))),
                                                         requestResult
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    CommandManager.literal("float")
                                       .then(
                                          CommandManager.argument("scale", DoubleArgumentType.doubleArg())
                                             .redirect(
                                                node,
                                                _snowmanxxxxx -> executeStoreData(
                                                      (ServerCommandSource)_snowmanxxxxx.getSource(),
                                                      _snowman.getObject(_snowmanxxxxx),
                                                      NbtPathArgumentType.getNbtPath(_snowmanxxxxx, "path"),
                                                      _snowmanxxxxxxxx -> FloatTag.of((float)((double)_snowmanxxxxxxxx * DoubleArgumentType.getDouble(_snowmanxxxxx, "scale"))),
                                                      requestResult
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 CommandManager.literal("short")
                                    .then(
                                       CommandManager.argument("scale", DoubleArgumentType.doubleArg())
                                          .redirect(
                                             node,
                                             _snowmanxxxxx -> executeStoreData(
                                                   (ServerCommandSource)_snowmanxxxxx.getSource(),
                                                   _snowman.getObject(_snowmanxxxxx),
                                                   NbtPathArgumentType.getNbtPath(_snowmanxxxxx, "path"),
                                                   _snowmanxxxxxxxx -> ShortTag.of((short)((int)((double)_snowmanxxxxxxxx * DoubleArgumentType.getDouble(_snowmanxxxxx, "scale")))),
                                                   requestResult
                                                )
                                          )
                                    )
                              ))
                           .then(
                              CommandManager.literal("long")
                                 .then(
                                    CommandManager.argument("scale", DoubleArgumentType.doubleArg())
                                       .redirect(
                                          node,
                                          _snowmanxxxxx -> executeStoreData(
                                                (ServerCommandSource)_snowmanxxxxx.getSource(),
                                                _snowman.getObject(_snowmanxxxxx),
                                                NbtPathArgumentType.getNbtPath(_snowmanxxxxx, "path"),
                                                _snowmanxxxxxxxx -> LongTag.of((long)((double)_snowmanxxxxxxxx * DoubleArgumentType.getDouble(_snowmanxxxxx, "scale"))),
                                                requestResult
                                             )
                                       )
                                 )
                           ))
                        .then(
                           CommandManager.literal("double")
                              .then(
                                 CommandManager.argument("scale", DoubleArgumentType.doubleArg())
                                    .redirect(
                                       node,
                                       _snowmanxxxxx -> executeStoreData(
                                             (ServerCommandSource)_snowmanxxxxx.getSource(),
                                             _snowman.getObject(_snowmanxxxxx),
                                             NbtPathArgumentType.getNbtPath(_snowmanxxxxx, "path"),
                                             _snowmanxxxxxxxx -> DoubleTag.of((double)_snowmanxxxxxxxx * DoubleArgumentType.getDouble(_snowmanxxxxx, "scale")),
                                             requestResult
                                          )
                                    )
                              )
                        ))
                     .then(
                        CommandManager.literal("byte")
                           .then(
                              CommandManager.argument("scale", DoubleArgumentType.doubleArg())
                                 .redirect(
                                    node,
                                    _snowmanxxxxx -> executeStoreData(
                                          (ServerCommandSource)_snowmanxxxxx.getSource(),
                                          _snowman.getObject(_snowmanxxxxx),
                                          NbtPathArgumentType.getNbtPath(_snowmanxxxxx, "path"),
                                          _snowmanxxxxxxxx -> ByteTag.of((byte)((int)((double)_snowmanxxxxxxxx * DoubleArgumentType.getDouble(_snowmanxxxxx, "scale")))),
                                          requestResult
                                       )
                                 )
                           )
                     )
               )
         );
      }

      return builder;
   }

   private static ServerCommandSource executeStoreScore(
      ServerCommandSource source, Collection<String> targets, ScoreboardObjective objective, boolean requestResult
   ) {
      Scoreboard _snowman = source.getMinecraftServer().getScoreboard();
      return source.mergeConsumers((_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx) -> {
         for (String _snowmanx : targets) {
            ScoreboardPlayerScore _snowmanx = _snowman.getPlayerScore(_snowmanx, objective);
            int _snowmanxx = requestResult ? _snowmanxxxxxx : (_snowmanxxxxx ? 1 : 0);
            _snowmanx.setScore(_snowmanxx);
         }
      }, BINARY_RESULT_CONSUMER);
   }

   private static ServerCommandSource executeStoreBossbar(ServerCommandSource source, CommandBossBar bossBar, boolean storeInValue, boolean requestResult) {
      return source.mergeConsumers((_snowmanxxx, _snowmanxxxx, _snowmanxxxxx) -> {
         int _snowmanxxxxxx = requestResult ? _snowmanxxxxx : (_snowmanxxxx ? 1 : 0);
         if (storeInValue) {
            bossBar.setValue(_snowmanxxxxxx);
         } else {
            bossBar.setMaxValue(_snowmanxxxxxx);
         }
      }, BINARY_RESULT_CONSUMER);
   }

   private static ServerCommandSource executeStoreData(
      ServerCommandSource source, DataCommandObject object, NbtPathArgumentType.NbtPath path, IntFunction<Tag> tagSetter, boolean requestResult
   ) {
      return source.mergeConsumers((_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx) -> {
         try {
            CompoundTag _snowmanxxxxxxx = object.getTag();
            int _snowmanx = requestResult ? _snowmanxxxxxx : (_snowmanxxxxx ? 1 : 0);
            path.put(_snowmanxxxxxxx, () -> tagSetter.apply(_snowman));
            object.setTag(_snowmanxxxxxxx);
         } catch (CommandSyntaxException var9) {
         }
      }, BINARY_RESULT_CONSUMER);
   }

   private static ArgumentBuilder<ServerCommandSource, ?> addConditionArguments(
      CommandNode<ServerCommandSource> root, LiteralArgumentBuilder<ServerCommandSource> argumentBuilder, boolean positive
   ) {
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)argumentBuilder.then(
                     CommandManager.literal("block")
                        .then(
                           CommandManager.argument("pos", BlockPosArgumentType.blockPos())
                              .then(
                                 addConditionLogic(
                                    root,
                                    CommandManager.argument("block", BlockPredicateArgumentType.blockPredicate()),
                                    positive,
                                    _snowman -> BlockPredicateArgumentType.getBlockPredicate(_snowman, "block")
                                          .test(
                                             new CachedBlockPosition(
                                                ((ServerCommandSource)_snowman.getSource()).getWorld(), BlockPosArgumentType.getLoadedBlockPos(_snowman, "pos"), true
                                             )
                                          )
                                 )
                              )
                        )
                  ))
                  .then(
                     CommandManager.literal("score")
                        .then(
                           CommandManager.argument("target", ScoreHolderArgumentType.scoreHolder())
                              .suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER)
                              .then(
                                 ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument(
                                                      "targetObjective", ObjectiveArgumentType.objective()
                                                   )
                                                   .then(
                                                      CommandManager.literal("=")
                                                         .then(
                                                            CommandManager.argument("source", ScoreHolderArgumentType.scoreHolder())
                                                               .suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER)
                                                               .then(
                                                                  addConditionLogic(
                                                                     root,
                                                                     CommandManager.argument("sourceObjective", ObjectiveArgumentType.objective()),
                                                                     positive,
                                                                     _snowman -> testScoreCondition(_snowman, Integer::equals)
                                                                  )
                                                               )
                                                         )
                                                   ))
                                                .then(
                                                   CommandManager.literal("<")
                                                      .then(
                                                         CommandManager.argument("source", ScoreHolderArgumentType.scoreHolder())
                                                            .suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER)
                                                            .then(
                                                               addConditionLogic(
                                                                  root,
                                                                  CommandManager.argument("sourceObjective", ObjectiveArgumentType.objective()),
                                                                  positive,
                                                                  _snowman -> testScoreCondition(_snowman, (_snowmanx, _snowmanxx) -> _snowmanx < _snowmanxx)
                                                               )
                                                            )
                                                      )
                                                ))
                                             .then(
                                                CommandManager.literal("<=")
                                                   .then(
                                                      CommandManager.argument("source", ScoreHolderArgumentType.scoreHolder())
                                                         .suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER)
                                                         .then(
                                                            addConditionLogic(
                                                               root,
                                                               CommandManager.argument("sourceObjective", ObjectiveArgumentType.objective()),
                                                               positive,
                                                               _snowman -> testScoreCondition(_snowman, (_snowmanx, _snowmanxx) -> _snowmanx <= _snowmanxx)
                                                            )
                                                         )
                                                   )
                                             ))
                                          .then(
                                             CommandManager.literal(">")
                                                .then(
                                                   CommandManager.argument("source", ScoreHolderArgumentType.scoreHolder())
                                                      .suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER)
                                                      .then(
                                                         addConditionLogic(
                                                            root,
                                                            CommandManager.argument("sourceObjective", ObjectiveArgumentType.objective()),
                                                            positive,
                                                            _snowman -> testScoreCondition(_snowman, (_snowmanx, _snowmanxx) -> _snowmanx > _snowmanxx)
                                                         )
                                                      )
                                                )
                                          ))
                                       .then(
                                          CommandManager.literal(">=")
                                             .then(
                                                CommandManager.argument("source", ScoreHolderArgumentType.scoreHolder())
                                                   .suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER)
                                                   .then(
                                                      addConditionLogic(
                                                         root,
                                                         CommandManager.argument("sourceObjective", ObjectiveArgumentType.objective()),
                                                         positive,
                                                         _snowman -> testScoreCondition(_snowman, (_snowmanx, _snowmanxx) -> _snowmanx >= _snowmanxx)
                                                      )
                                                   )
                                             )
                                       ))
                                    .then(
                                       CommandManager.literal("matches")
                                          .then(
                                             addConditionLogic(
                                                root,
                                                CommandManager.argument("range", NumberRangeArgumentType.numberRange()),
                                                positive,
                                                _snowman -> testScoreMatch(_snowman, NumberRangeArgumentType.IntRangeArgumentType.getRangeArgument(_snowman, "range"))
                                             )
                                          )
                                    )
                              )
                        )
                  ))
               .then(
                  CommandManager.literal("blocks")
                     .then(
                        CommandManager.argument("start", BlockPosArgumentType.blockPos())
                           .then(
                              CommandManager.argument("end", BlockPosArgumentType.blockPos())
                                 .then(
                                    ((RequiredArgumentBuilder)CommandManager.argument("destination", BlockPosArgumentType.blockPos())
                                          .then(addBlocksConditionLogic(root, CommandManager.literal("all"), positive, false)))
                                       .then(addBlocksConditionLogic(root, CommandManager.literal("masked"), positive, true))
                                 )
                           )
                     )
               ))
            .then(
               CommandManager.literal("entity")
                  .then(
                     ((RequiredArgumentBuilder)CommandManager.argument("entities", EntityArgumentType.entities())
                           .fork(root, _snowmanx -> getSourceOrEmptyForConditionFork(_snowmanx, positive, !EntityArgumentType.getOptionalEntities(_snowmanx, "entities").isEmpty())))
                        .executes(getExistsConditionExecute(positive, _snowman -> EntityArgumentType.getOptionalEntities(_snowman, "entities").size()))
                  )
            ))
         .then(
            CommandManager.literal("predicate")
               .then(
                  addConditionLogic(
                     root,
                     CommandManager.argument("predicate", IdentifierArgumentType.identifier()).suggests(LOOT_CONDITIONS),
                     positive,
                     _snowman -> testLootCondition((ServerCommandSource)_snowman.getSource(), IdentifierArgumentType.method_23727(_snowman, "predicate"))
                  )
               )
         );

      for (DataCommand.ObjectType _snowman : DataCommand.SOURCE_OBJECT_TYPES) {
         argumentBuilder.then(
            _snowman.addArgumentsToBuilder(
               CommandManager.literal("data"),
               _snowmanxxx -> _snowmanxxx.then(
                     ((RequiredArgumentBuilder)CommandManager.argument("path", NbtPathArgumentType.nbtPath())
                           .fork(
                              root,
                              _snowmanxxxxx -> getSourceOrEmptyForConditionFork(
                                    _snowmanxxxxx, positive, countPathMatches(_snowman.getObject(_snowmanxxxxx), NbtPathArgumentType.getNbtPath(_snowmanxxxxx, "path")) > 0
                                 )
                           ))
                        .executes(
                           getExistsConditionExecute(positive, _snowmanxxxxx -> countPathMatches(_snowman.getObject(_snowmanxxxxx), NbtPathArgumentType.getNbtPath(_snowmanxxxxx, "path")))
                        )
                  )
            )
         );
      }

      return argumentBuilder;
   }

   private static Command<ServerCommandSource> getExistsConditionExecute(boolean positive, ExecuteCommand.ExistsCondition condition) {
      return positive ? _snowmanx -> {
         int _snowmanxx = condition.test(_snowmanx);
         if (_snowmanxx > 0) {
            ((ServerCommandSource)_snowmanx.getSource()).sendFeedback(new TranslatableText("commands.execute.conditional.pass_count", _snowmanxx), false);
            return _snowmanxx;
         } else {
            throw CONDITIONAL_FAIL_EXCEPTION.create();
         }
      } : _snowmanx -> {
         int _snowmanxx = condition.test(_snowmanx);
         if (_snowmanxx == 0) {
            ((ServerCommandSource)_snowmanx.getSource()).sendFeedback(new TranslatableText("commands.execute.conditional.pass"), false);
            return 1;
         } else {
            throw CONDITIONAL_FAIL_COUNT_EXCEPTION.create(_snowmanxx);
         }
      };
   }

   private static int countPathMatches(DataCommandObject object, NbtPathArgumentType.NbtPath path) throws CommandSyntaxException {
      return path.count(object.getTag());
   }

   private static boolean testScoreCondition(CommandContext<ServerCommandSource> context, BiPredicate<Integer, Integer> condition) throws CommandSyntaxException {
      String _snowman = ScoreHolderArgumentType.getScoreHolder(context, "target");
      ScoreboardObjective _snowmanx = ObjectiveArgumentType.getObjective(context, "targetObjective");
      String _snowmanxx = ScoreHolderArgumentType.getScoreHolder(context, "source");
      ScoreboardObjective _snowmanxxx = ObjectiveArgumentType.getObjective(context, "sourceObjective");
      Scoreboard _snowmanxxxx = ((ServerCommandSource)context.getSource()).getMinecraftServer().getScoreboard();
      if (_snowmanxxxx.playerHasObjective(_snowman, _snowmanx) && _snowmanxxxx.playerHasObjective(_snowmanxx, _snowmanxxx)) {
         ScoreboardPlayerScore _snowmanxxxxx = _snowmanxxxx.getPlayerScore(_snowman, _snowmanx);
         ScoreboardPlayerScore _snowmanxxxxxx = _snowmanxxxx.getPlayerScore(_snowmanxx, _snowmanxxx);
         return condition.test(_snowmanxxxxx.getScore(), _snowmanxxxxxx.getScore());
      } else {
         return false;
      }
   }

   private static boolean testScoreMatch(CommandContext<ServerCommandSource> context, NumberRange.IntRange range) throws CommandSyntaxException {
      String _snowman = ScoreHolderArgumentType.getScoreHolder(context, "target");
      ScoreboardObjective _snowmanx = ObjectiveArgumentType.getObjective(context, "targetObjective");
      Scoreboard _snowmanxx = ((ServerCommandSource)context.getSource()).getMinecraftServer().getScoreboard();
      return !_snowmanxx.playerHasObjective(_snowman, _snowmanx) ? false : range.test(_snowmanxx.getPlayerScore(_snowman, _snowmanx).getScore());
   }

   private static boolean testLootCondition(ServerCommandSource _snowman, LootCondition _snowman) {
      ServerWorld _snowmanxx = _snowman.getWorld();
      LootContext.Builder _snowmanxxx = new LootContext.Builder(_snowmanxx)
         .parameter(LootContextParameters.ORIGIN, _snowman.getPosition())
         .optionalParameter(LootContextParameters.THIS_ENTITY, _snowman.getEntity());
      return _snowman.test(_snowmanxxx.build(LootContextTypes.COMMAND));
   }

   private static Collection<ServerCommandSource> getSourceOrEmptyForConditionFork(CommandContext<ServerCommandSource> context, boolean positive, boolean value) {
      return (Collection<ServerCommandSource>)(value == positive ? Collections.singleton((ServerCommandSource)context.getSource()) : Collections.emptyList());
   }

   private static ArgumentBuilder<ServerCommandSource, ?> addConditionLogic(
      CommandNode<ServerCommandSource> root, ArgumentBuilder<ServerCommandSource, ?> builder, boolean positive, ExecuteCommand.Condition condition
   ) {
      return builder.fork(root, _snowmanxx -> getSourceOrEmptyForConditionFork(_snowmanxx, positive, condition.test(_snowmanxx))).executes(_snowmanxx -> {
         if (positive == condition.test(_snowmanxx)) {
            ((ServerCommandSource)_snowmanxx.getSource()).sendFeedback(new TranslatableText("commands.execute.conditional.pass"), false);
            return 1;
         } else {
            throw CONDITIONAL_FAIL_EXCEPTION.create();
         }
      });
   }

   private static ArgumentBuilder<ServerCommandSource, ?> addBlocksConditionLogic(
      CommandNode<ServerCommandSource> root, ArgumentBuilder<ServerCommandSource, ?> builder, boolean positive, boolean masked
   ) {
      return builder.fork(root, _snowmanxx -> getSourceOrEmptyForConditionFork(_snowmanxx, positive, testBlocksCondition(_snowmanxx, masked).isPresent()))
         .executes(positive ? _snowmanx -> executePositiveBlockCondition(_snowmanx, masked) : _snowmanx -> executeNegativeBlockCondition(_snowmanx, masked));
   }

   private static int executePositiveBlockCondition(CommandContext<ServerCommandSource> context, boolean masked) throws CommandSyntaxException {
      OptionalInt _snowman = testBlocksCondition(context, masked);
      if (_snowman.isPresent()) {
         ((ServerCommandSource)context.getSource()).sendFeedback(new TranslatableText("commands.execute.conditional.pass_count", _snowman.getAsInt()), false);
         return _snowman.getAsInt();
      } else {
         throw CONDITIONAL_FAIL_EXCEPTION.create();
      }
   }

   private static int executeNegativeBlockCondition(CommandContext<ServerCommandSource> context, boolean masked) throws CommandSyntaxException {
      OptionalInt _snowman = testBlocksCondition(context, masked);
      if (_snowman.isPresent()) {
         throw CONDITIONAL_FAIL_COUNT_EXCEPTION.create(_snowman.getAsInt());
      } else {
         ((ServerCommandSource)context.getSource()).sendFeedback(new TranslatableText("commands.execute.conditional.pass"), false);
         return 1;
      }
   }

   private static OptionalInt testBlocksCondition(CommandContext<ServerCommandSource> context, boolean masked) throws CommandSyntaxException {
      return testBlocksCondition(
         ((ServerCommandSource)context.getSource()).getWorld(),
         BlockPosArgumentType.getLoadedBlockPos(context, "start"),
         BlockPosArgumentType.getLoadedBlockPos(context, "end"),
         BlockPosArgumentType.getLoadedBlockPos(context, "destination"),
         masked
      );
   }

   private static OptionalInt testBlocksCondition(ServerWorld world, BlockPos start, BlockPos end, BlockPos destination, boolean masked) throws CommandSyntaxException {
      BlockBox _snowman = new BlockBox(start, end);
      BlockBox _snowmanx = new BlockBox(destination, destination.add(_snowman.getDimensions()));
      BlockPos _snowmanxx = new BlockPos(_snowmanx.minX - _snowman.minX, _snowmanx.minY - _snowman.minY, _snowmanx.minZ - _snowman.minZ);
      int _snowmanxxx = _snowman.getBlockCountX() * _snowman.getBlockCountY() * _snowman.getBlockCountZ();
      if (_snowmanxxx > 32768) {
         throw BLOCKS_TOOBIG_EXCEPTION.create(32768, _snowmanxxx);
      } else {
         int _snowmanxxxx = 0;

         for (int _snowmanxxxxx = _snowman.minZ; _snowmanxxxxx <= _snowman.maxZ; _snowmanxxxxx++) {
            for (int _snowmanxxxxxx = _snowman.minY; _snowmanxxxxxx <= _snowman.maxY; _snowmanxxxxxx++) {
               for (int _snowmanxxxxxxx = _snowman.minX; _snowmanxxxxxxx <= _snowman.maxX; _snowmanxxxxxxx++) {
                  BlockPos _snowmanxxxxxxxx = new BlockPos(_snowmanxxxxxxx, _snowmanxxxxxx, _snowmanxxxxx);
                  BlockPos _snowmanxxxxxxxxx = _snowmanxxxxxxxx.add(_snowmanxx);
                  BlockState _snowmanxxxxxxxxxx = world.getBlockState(_snowmanxxxxxxxx);
                  if (!masked || !_snowmanxxxxxxxxxx.isOf(Blocks.AIR)) {
                     if (_snowmanxxxxxxxxxx != world.getBlockState(_snowmanxxxxxxxxx)) {
                        return OptionalInt.empty();
                     }

                     BlockEntity _snowmanxxxxxxxxxxx = world.getBlockEntity(_snowmanxxxxxxxx);
                     BlockEntity _snowmanxxxxxxxxxxxx = world.getBlockEntity(_snowmanxxxxxxxxx);
                     if (_snowmanxxxxxxxxxxx != null) {
                        if (_snowmanxxxxxxxxxxxx == null) {
                           return OptionalInt.empty();
                        }

                        CompoundTag _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.toTag(new CompoundTag());
                        _snowmanxxxxxxxxxxxxx.remove("x");
                        _snowmanxxxxxxxxxxxxx.remove("y");
                        _snowmanxxxxxxxxxxxxx.remove("z");
                        CompoundTag _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.toTag(new CompoundTag());
                        _snowmanxxxxxxxxxxxxxx.remove("x");
                        _snowmanxxxxxxxxxxxxxx.remove("y");
                        _snowmanxxxxxxxxxxxxxx.remove("z");
                        if (!_snowmanxxxxxxxxxxxxx.equals(_snowmanxxxxxxxxxxxxxx)) {
                           return OptionalInt.empty();
                        }
                     }

                     _snowmanxxxx++;
                  }
               }
            }
         }

         return OptionalInt.of(_snowmanxxxx);
      }
   }

   @FunctionalInterface
   interface Condition {
      boolean test(CommandContext<ServerCommandSource> context) throws CommandSyntaxException;
   }

   @FunctionalInterface
   interface ExistsCondition {
      int test(CommandContext<ServerCommandSource> context) throws CommandSyntaxException;
   }
}
