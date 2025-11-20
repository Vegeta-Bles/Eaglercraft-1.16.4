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
      (object, object2) -> new TranslatableText("commands.execute.blocks.toobig", object, object2)
   );
   private static final SimpleCommandExceptionType CONDITIONAL_FAIL_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.execute.conditional.fail")
   );
   private static final DynamicCommandExceptionType CONDITIONAL_FAIL_COUNT_EXCEPTION = new DynamicCommandExceptionType(
      object -> new TranslatableText("commands.execute.conditional.fail_count", object)
   );
   private static final BinaryOperator<ResultConsumer<ServerCommandSource>> BINARY_RESULT_CONSUMER = (resultConsumer, resultConsumer2) -> (commandContext, bl, i) -> {
         resultConsumer.onCommandComplete(commandContext, bl, i);
         resultConsumer2.onCommandComplete(commandContext, bl, i);
      };
   private static final SuggestionProvider<ServerCommandSource> LOOT_CONDITIONS = (commandContext, suggestionsBuilder) -> {
      LootConditionManager lv = ((ServerCommandSource)commandContext.getSource()).getMinecraftServer().getPredicateManager();
      return CommandSource.suggestIdentifiers(lv.getIds(), suggestionsBuilder);
   };

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      LiteralCommandNode<ServerCommandSource> literalCommandNode = dispatcher.register(
         (LiteralArgumentBuilder)CommandManager.literal("execute").requires(arg -> arg.hasPermissionLevel(2))
      );
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal(
                                                   "execute"
                                                )
                                                .requires(arg -> arg.hasPermissionLevel(2)))
                                             .then(CommandManager.literal("run").redirect(dispatcher.getRoot())))
                                          .then(addConditionArguments(literalCommandNode, CommandManager.literal("if"), true)))
                                       .then(addConditionArguments(literalCommandNode, CommandManager.literal("unless"), false)))
                                    .then(
                                       CommandManager.literal("as")
                                          .then(CommandManager.argument("targets", EntityArgumentType.entities()).fork(literalCommandNode, commandContext -> {
                                             List<ServerCommandSource> list = Lists.newArrayList();

                                             for (Entity lv : EntityArgumentType.getOptionalEntities(commandContext, "targets")) {
                                                list.add(((ServerCommandSource)commandContext.getSource()).withEntity(lv));
                                             }

                                             return list;
                                          }))
                                    ))
                                 .then(
                                    CommandManager.literal("at")
                                       .then(
                                          CommandManager.argument("targets", EntityArgumentType.entities())
                                             .fork(
                                                literalCommandNode,
                                                commandContext -> {
                                                   List<ServerCommandSource> list = Lists.newArrayList();

                                                   for (Entity lv : EntityArgumentType.getOptionalEntities(commandContext, "targets")) {
                                                      list.add(
                                                         ((ServerCommandSource)commandContext.getSource())
                                                            .withWorld((ServerWorld)lv.world)
                                                            .withPosition(lv.getPos())
                                                            .withRotation(lv.getRotationClient())
                                                      );
                                                   }

                                                   return list;
                                                }
                                             )
                                       )
                                 ))
                              .then(
                                 ((LiteralArgumentBuilder)CommandManager.literal("store")
                                       .then(addStoreArguments(literalCommandNode, CommandManager.literal("result"), true)))
                                    .then(addStoreArguments(literalCommandNode, CommandManager.literal("success"), false))
                              ))
                           .then(
                              ((LiteralArgumentBuilder)CommandManager.literal("positioned")
                                    .then(
                                       CommandManager.argument("pos", Vec3ArgumentType.vec3())
                                          .redirect(
                                             literalCommandNode,
                                             commandContext -> ((ServerCommandSource)commandContext.getSource())
                                                   .withPosition(Vec3ArgumentType.getVec3(commandContext, "pos"))
                                                   .withEntityAnchor(EntityAnchorArgumentType.EntityAnchor.FEET)
                                          )
                                    ))
                                 .then(
                                    CommandManager.literal("as")
                                       .then(CommandManager.argument("targets", EntityArgumentType.entities()).fork(literalCommandNode, commandContext -> {
                                          List<ServerCommandSource> list = Lists.newArrayList();

                                          for (Entity lv : EntityArgumentType.getOptionalEntities(commandContext, "targets")) {
                                             list.add(((ServerCommandSource)commandContext.getSource()).withPosition(lv.getPos()));
                                          }

                                          return list;
                                       }))
                                 )
                           ))
                        .then(
                           ((LiteralArgumentBuilder)CommandManager.literal("rotated")
                                 .then(
                                    CommandManager.argument("rot", RotationArgumentType.rotation())
                                       .redirect(
                                          literalCommandNode,
                                          commandContext -> ((ServerCommandSource)commandContext.getSource())
                                                .withRotation(
                                                   RotationArgumentType.getRotation(commandContext, "rot")
                                                      .toAbsoluteRotation((ServerCommandSource)commandContext.getSource())
                                                )
                                       )
                                 ))
                              .then(
                                 CommandManager.literal("as")
                                    .then(CommandManager.argument("targets", EntityArgumentType.entities()).fork(literalCommandNode, commandContext -> {
                                       List<ServerCommandSource> list = Lists.newArrayList();

                                       for (Entity lv : EntityArgumentType.getOptionalEntities(commandContext, "targets")) {
                                          list.add(((ServerCommandSource)commandContext.getSource()).withRotation(lv.getRotationClient()));
                                       }

                                       return list;
                                    }))
                              )
                        ))
                     .then(
                        ((LiteralArgumentBuilder)CommandManager.literal("facing")
                              .then(
                                 CommandManager.literal("entity")
                                    .then(
                                       CommandManager.argument("targets", EntityArgumentType.entities())
                                          .then(
                                             CommandManager.argument("anchor", EntityAnchorArgumentType.entityAnchor())
                                                .fork(literalCommandNode, commandContext -> {
                                                   List<ServerCommandSource> list = Lists.newArrayList();
                                                   EntityAnchorArgumentType.EntityAnchor lv = EntityAnchorArgumentType.getEntityAnchor(commandContext, "anchor");

                                                   for (Entity lv2 : EntityArgumentType.getOptionalEntities(commandContext, "targets")) {
                                                      list.add(((ServerCommandSource)commandContext.getSource()).withLookingAt(lv2, lv));
                                                   }

                                                   return list;
                                                })
                                          )
                                    )
                              ))
                           .then(
                              CommandManager.argument("pos", Vec3ArgumentType.vec3())
                                 .redirect(
                                    literalCommandNode,
                                    commandContext -> ((ServerCommandSource)commandContext.getSource())
                                          .withLookingAt(Vec3ArgumentType.getVec3(commandContext, "pos"))
                                 )
                           )
                     ))
                  .then(
                     CommandManager.literal("align")
                        .then(
                           CommandManager.argument("axes", SwizzleArgumentType.swizzle())
                              .redirect(
                                 literalCommandNode,
                                 commandContext -> ((ServerCommandSource)commandContext.getSource())
                                       .withPosition(
                                          ((ServerCommandSource)commandContext.getSource())
                                             .getPosition()
                                             .floorAlongAxes(SwizzleArgumentType.getSwizzle(commandContext, "axes"))
                                       )
                              )
                        )
                  ))
               .then(
                  CommandManager.literal("anchored")
                     .then(
                        CommandManager.argument("anchor", EntityAnchorArgumentType.entityAnchor())
                           .redirect(
                              literalCommandNode,
                              commandContext -> ((ServerCommandSource)commandContext.getSource())
                                    .withEntityAnchor(EntityAnchorArgumentType.getEntityAnchor(commandContext, "anchor"))
                           )
                     )
               ))
            .then(
               CommandManager.literal("in")
                  .then(
                     CommandManager.argument("dimension", DimensionArgumentType.dimension())
                        .redirect(
                           literalCommandNode,
                           commandContext -> ((ServerCommandSource)commandContext.getSource())
                                 .withWorld(DimensionArgumentType.getDimensionArgument(commandContext, "dimension"))
                        )
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
                           commandContext -> executeStoreScore(
                                 (ServerCommandSource)commandContext.getSource(),
                                 ScoreHolderArgumentType.getScoreboardScoreHolders(commandContext, "targets"),
                                 ObjectiveArgumentType.getObjective(commandContext, "objective"),
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
                           .redirect(
                              node,
                              commandContext -> executeStoreBossbar(
                                    (ServerCommandSource)commandContext.getSource(), BossBarCommand.getBossBar(commandContext), true, requestResult
                                 )
                           )
                     ))
                  .then(
                     CommandManager.literal("max")
                        .redirect(
                           node,
                           commandContext -> executeStoreBossbar(
                                 (ServerCommandSource)commandContext.getSource(), BossBarCommand.getBossBar(commandContext), false, requestResult
                              )
                        )
                  )
            )
      );

      for (DataCommand.ObjectType lv : DataCommand.TARGET_OBJECT_TYPES) {
         lv.addArgumentsToBuilder(
            builder,
            argumentBuilder -> argumentBuilder.then(
                  ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument(
                                       "path", NbtPathArgumentType.nbtPath()
                                    )
                                    .then(
                                       CommandManager.literal("int")
                                          .then(
                                             CommandManager.argument("scale", DoubleArgumentType.doubleArg())
                                                .redirect(
                                                   node,
                                                   commandContext -> executeStoreData(
                                                         (ServerCommandSource)commandContext.getSource(),
                                                         lv.getObject(commandContext),
                                                         NbtPathArgumentType.getNbtPath(commandContext, "path"),
                                                         i -> IntTag.of((int)((double)i * DoubleArgumentType.getDouble(commandContext, "scale"))),
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
                                                commandContext -> executeStoreData(
                                                      (ServerCommandSource)commandContext.getSource(),
                                                      lv.getObject(commandContext),
                                                      NbtPathArgumentType.getNbtPath(commandContext, "path"),
                                                      i -> FloatTag.of((float)((double)i * DoubleArgumentType.getDouble(commandContext, "scale"))),
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
                                             commandContext -> executeStoreData(
                                                   (ServerCommandSource)commandContext.getSource(),
                                                   lv.getObject(commandContext),
                                                   NbtPathArgumentType.getNbtPath(commandContext, "path"),
                                                   i -> ShortTag.of((short)((int)((double)i * DoubleArgumentType.getDouble(commandContext, "scale")))),
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
                                          commandContext -> executeStoreData(
                                                (ServerCommandSource)commandContext.getSource(),
                                                lv.getObject(commandContext),
                                                NbtPathArgumentType.getNbtPath(commandContext, "path"),
                                                i -> LongTag.of((long)((double)i * DoubleArgumentType.getDouble(commandContext, "scale"))),
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
                                       commandContext -> executeStoreData(
                                             (ServerCommandSource)commandContext.getSource(),
                                             lv.getObject(commandContext),
                                             NbtPathArgumentType.getNbtPath(commandContext, "path"),
                                             i -> DoubleTag.of((double)i * DoubleArgumentType.getDouble(commandContext, "scale")),
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
                                    commandContext -> executeStoreData(
                                          (ServerCommandSource)commandContext.getSource(),
                                          lv.getObject(commandContext),
                                          NbtPathArgumentType.getNbtPath(commandContext, "path"),
                                          i -> ByteTag.of((byte)((int)((double)i * DoubleArgumentType.getDouble(commandContext, "scale")))),
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
      Scoreboard lv = source.getMinecraftServer().getScoreboard();
      return source.mergeConsumers((commandContext, bl2, i) -> {
         for (String string : targets) {
            ScoreboardPlayerScore lvx = lv.getPlayerScore(string, objective);
            int j = requestResult ? i : (bl2 ? 1 : 0);
            lvx.setScore(j);
         }
      }, BINARY_RESULT_CONSUMER);
   }

   private static ServerCommandSource executeStoreBossbar(ServerCommandSource source, CommandBossBar bossBar, boolean storeInValue, boolean requestResult) {
      return source.mergeConsumers((commandContext, bl3, i) -> {
         int j = requestResult ? i : (bl3 ? 1 : 0);
         if (storeInValue) {
            bossBar.setValue(j);
         } else {
            bossBar.setMaxValue(j);
         }
      }, BINARY_RESULT_CONSUMER);
   }

   private static ServerCommandSource executeStoreData(
      ServerCommandSource source, DataCommandObject object, NbtPathArgumentType.NbtPath path, IntFunction<Tag> tagSetter, boolean requestResult
   ) {
      return source.mergeConsumers((commandContext, bl2, i) -> {
         try {
            CompoundTag lv = object.getTag();
            int j = requestResult ? i : (bl2 ? 1 : 0);
            path.put(lv, () -> tagSetter.apply(j));
            object.setTag(lv);
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
                                    commandContext -> BlockPredicateArgumentType.getBlockPredicate(commandContext, "block")
                                          .test(
                                             new CachedBlockPosition(
                                                ((ServerCommandSource)commandContext.getSource()).getWorld(),
                                                BlockPosArgumentType.getLoadedBlockPos(commandContext, "pos"),
                                                true
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
                                                                     commandContext -> testScoreCondition(commandContext, Integer::equals)
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
                                                                  commandContext -> testScoreCondition(
                                                                        commandContext, (integer, integer2) -> integer < integer2
                                                                     )
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
                                                               commandContext -> testScoreCondition(commandContext, (integer, integer2) -> integer <= integer2)
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
                                                            commandContext -> testScoreCondition(commandContext, (integer, integer2) -> integer > integer2)
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
                                                         commandContext -> testScoreCondition(commandContext, (integer, integer2) -> integer >= integer2)
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
                                                commandContext -> testScoreMatch(
                                                      commandContext, NumberRangeArgumentType.IntRangeArgumentType.getRangeArgument(commandContext, "range")
                                                   )
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
                           .fork(
                              root,
                              commandContext -> getSourceOrEmptyForConditionFork(
                                    commandContext, positive, !EntityArgumentType.getOptionalEntities(commandContext, "entities").isEmpty()
                                 )
                           ))
                        .executes(
                           getExistsConditionExecute(positive, commandContext -> EntityArgumentType.getOptionalEntities(commandContext, "entities").size())
                        )
                  )
            ))
         .then(
            CommandManager.literal("predicate")
               .then(
                  addConditionLogic(
                     root,
                     CommandManager.argument("predicate", IdentifierArgumentType.identifier()).suggests(LOOT_CONDITIONS),
                     positive,
                     commandContext -> testLootCondition(
                           (ServerCommandSource)commandContext.getSource(), IdentifierArgumentType.method_23727(commandContext, "predicate")
                        )
                  )
               )
         );

      for (DataCommand.ObjectType lv : DataCommand.SOURCE_OBJECT_TYPES) {
         argumentBuilder.then(
            lv.addArgumentsToBuilder(
               CommandManager.literal("data"),
               argumentBuilderx -> argumentBuilderx.then(
                     ((RequiredArgumentBuilder)CommandManager.argument("path", NbtPathArgumentType.nbtPath())
                           .fork(
                              root,
                              commandContext -> getSourceOrEmptyForConditionFork(
                                    commandContext,
                                    positive,
                                    countPathMatches(lv.getObject(commandContext), NbtPathArgumentType.getNbtPath(commandContext, "path")) > 0
                                 )
                           ))
                        .executes(
                           getExistsConditionExecute(
                              positive,
                              commandContext -> countPathMatches(lv.getObject(commandContext), NbtPathArgumentType.getNbtPath(commandContext, "path"))
                           )
                        )
                  )
            )
         );
      }

      return argumentBuilder;
   }

   private static Command<ServerCommandSource> getExistsConditionExecute(boolean positive, ExecuteCommand.ExistsCondition condition) {
      return positive ? commandContext -> {
         int i = condition.test(commandContext);
         if (i > 0) {
            ((ServerCommandSource)commandContext.getSource()).sendFeedback(new TranslatableText("commands.execute.conditional.pass_count", i), false);
            return i;
         } else {
            throw CONDITIONAL_FAIL_EXCEPTION.create();
         }
      } : commandContext -> {
         int i = condition.test(commandContext);
         if (i == 0) {
            ((ServerCommandSource)commandContext.getSource()).sendFeedback(new TranslatableText("commands.execute.conditional.pass"), false);
            return 1;
         } else {
            throw CONDITIONAL_FAIL_COUNT_EXCEPTION.create(i);
         }
      };
   }

   private static int countPathMatches(DataCommandObject object, NbtPathArgumentType.NbtPath path) throws CommandSyntaxException {
      return path.count(object.getTag());
   }

   private static boolean testScoreCondition(CommandContext<ServerCommandSource> context, BiPredicate<Integer, Integer> condition) throws CommandSyntaxException {
      String string = ScoreHolderArgumentType.getScoreHolder(context, "target");
      ScoreboardObjective lv = ObjectiveArgumentType.getObjective(context, "targetObjective");
      String string2 = ScoreHolderArgumentType.getScoreHolder(context, "source");
      ScoreboardObjective lv2 = ObjectiveArgumentType.getObjective(context, "sourceObjective");
      Scoreboard lv3 = ((ServerCommandSource)context.getSource()).getMinecraftServer().getScoreboard();
      if (lv3.playerHasObjective(string, lv) && lv3.playerHasObjective(string2, lv2)) {
         ScoreboardPlayerScore lv4 = lv3.getPlayerScore(string, lv);
         ScoreboardPlayerScore lv5 = lv3.getPlayerScore(string2, lv2);
         return condition.test(lv4.getScore(), lv5.getScore());
      } else {
         return false;
      }
   }

   private static boolean testScoreMatch(CommandContext<ServerCommandSource> context, NumberRange.IntRange range) throws CommandSyntaxException {
      String string = ScoreHolderArgumentType.getScoreHolder(context, "target");
      ScoreboardObjective lv = ObjectiveArgumentType.getObjective(context, "targetObjective");
      Scoreboard lv2 = ((ServerCommandSource)context.getSource()).getMinecraftServer().getScoreboard();
      return !lv2.playerHasObjective(string, lv) ? false : range.test(lv2.getPlayerScore(string, lv).getScore());
   }

   private static boolean testLootCondition(ServerCommandSource arg, LootCondition arg2) {
      ServerWorld lv = arg.getWorld();
      LootContext.Builder lv2 = new LootContext.Builder(lv)
         .parameter(LootContextParameters.ORIGIN, arg.getPosition())
         .optionalParameter(LootContextParameters.THIS_ENTITY, arg.getEntity());
      return arg2.test(lv2.build(LootContextTypes.COMMAND));
   }

   private static Collection<ServerCommandSource> getSourceOrEmptyForConditionFork(CommandContext<ServerCommandSource> context, boolean positive, boolean value) {
      return (Collection<ServerCommandSource>)(value == positive ? Collections.singleton((ServerCommandSource)context.getSource()) : Collections.emptyList());
   }

   private static ArgumentBuilder<ServerCommandSource, ?> addConditionLogic(
      CommandNode<ServerCommandSource> root, ArgumentBuilder<ServerCommandSource, ?> builder, boolean positive, ExecuteCommand.Condition condition
   ) {
      return builder.fork(root, commandContext -> getSourceOrEmptyForConditionFork(commandContext, positive, condition.test(commandContext)))
         .executes(commandContext -> {
            if (positive == condition.test(commandContext)) {
               ((ServerCommandSource)commandContext.getSource()).sendFeedback(new TranslatableText("commands.execute.conditional.pass"), false);
               return 1;
            } else {
               throw CONDITIONAL_FAIL_EXCEPTION.create();
            }
         });
   }

   private static ArgumentBuilder<ServerCommandSource, ?> addBlocksConditionLogic(
      CommandNode<ServerCommandSource> root, ArgumentBuilder<ServerCommandSource, ?> builder, boolean positive, boolean masked
   ) {
      return builder.fork(
            root, commandContext -> getSourceOrEmptyForConditionFork(commandContext, positive, testBlocksCondition(commandContext, masked).isPresent())
         )
         .executes(
            positive
               ? commandContext -> executePositiveBlockCondition(commandContext, masked)
               : commandContext -> executeNegativeBlockCondition(commandContext, masked)
         );
   }

   private static int executePositiveBlockCondition(CommandContext<ServerCommandSource> context, boolean masked) throws CommandSyntaxException {
      OptionalInt optionalInt = testBlocksCondition(context, masked);
      if (optionalInt.isPresent()) {
         ((ServerCommandSource)context.getSource())
            .sendFeedback(new TranslatableText("commands.execute.conditional.pass_count", optionalInt.getAsInt()), false);
         return optionalInt.getAsInt();
      } else {
         throw CONDITIONAL_FAIL_EXCEPTION.create();
      }
   }

   private static int executeNegativeBlockCondition(CommandContext<ServerCommandSource> context, boolean masked) throws CommandSyntaxException {
      OptionalInt optionalInt = testBlocksCondition(context, masked);
      if (optionalInt.isPresent()) {
         throw CONDITIONAL_FAIL_COUNT_EXCEPTION.create(optionalInt.getAsInt());
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
      BlockBox lv = new BlockBox(start, end);
      BlockBox lv2 = new BlockBox(destination, destination.add(lv.getDimensions()));
      BlockPos lv3 = new BlockPos(lv2.minX - lv.minX, lv2.minY - lv.minY, lv2.minZ - lv.minZ);
      int i = lv.getBlockCountX() * lv.getBlockCountY() * lv.getBlockCountZ();
      if (i > 32768) {
         throw BLOCKS_TOOBIG_EXCEPTION.create(32768, i);
      } else {
         int j = 0;

         for (int k = lv.minZ; k <= lv.maxZ; k++) {
            for (int l = lv.minY; l <= lv.maxY; l++) {
               for (int m = lv.minX; m <= lv.maxX; m++) {
                  BlockPos lv4 = new BlockPos(m, l, k);
                  BlockPos lv5 = lv4.add(lv3);
                  BlockState lv6 = world.getBlockState(lv4);
                  if (!masked || !lv6.isOf(Blocks.AIR)) {
                     if (lv6 != world.getBlockState(lv5)) {
                        return OptionalInt.empty();
                     }

                     BlockEntity lv7 = world.getBlockEntity(lv4);
                     BlockEntity lv8 = world.getBlockEntity(lv5);
                     if (lv7 != null) {
                        if (lv8 == null) {
                           return OptionalInt.empty();
                        }

                        CompoundTag lv9 = lv7.toTag(new CompoundTag());
                        lv9.remove("x");
                        lv9.remove("y");
                        lv9.remove("z");
                        CompoundTag lv10 = lv8.toTag(new CompoundTag());
                        lv10.remove("x");
                        lv10.remove("y");
                        lv10.remove("z");
                        if (!lv9.equals(lv10)) {
                           return OptionalInt.empty();
                        }
                     }

                     j++;
                  }
               }
            }
         }

         return OptionalInt.of(j);
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
