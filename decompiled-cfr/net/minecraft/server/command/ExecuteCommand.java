/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.Command
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.ResultConsumer
 *  com.mojang.brigadier.arguments.DoubleArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.suggestion.SuggestionProvider
 *  com.mojang.brigadier.tree.CommandNode
 *  com.mojang.brigadier.tree.LiteralCommandNode
 */
package net.minecraft.server.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.command.BossBarCommand;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.DataCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;

public class ExecuteCommand {
    private static final Dynamic2CommandExceptionType BLOCKS_TOOBIG_EXCEPTION = new Dynamic2CommandExceptionType((object, object2) -> new TranslatableText("commands.execute.blocks.toobig", object, object2));
    private static final SimpleCommandExceptionType CONDITIONAL_FAIL_EXCEPTION = new SimpleCommandExceptionType((Message)new TranslatableText("commands.execute.conditional.fail"));
    private static final DynamicCommandExceptionType CONDITIONAL_FAIL_COUNT_EXCEPTION = new DynamicCommandExceptionType(object -> new TranslatableText("commands.execute.conditional.fail_count", object));
    private static final BinaryOperator<ResultConsumer<ServerCommandSource>> BINARY_RESULT_CONSUMER = (resultConsumer, resultConsumer2) -> (commandContext, bl, n) -> {
        resultConsumer.onCommandComplete(commandContext, bl, n);
        resultConsumer2.onCommandComplete(commandContext, bl, n);
    };
    private static final SuggestionProvider<ServerCommandSource> LOOT_CONDITIONS = (commandContext, suggestionsBuilder) -> {
        LootConditionManager lootConditionManager = ((ServerCommandSource)commandContext.getSource()).getMinecraftServer().getPredicateManager();
        return CommandSource.suggestIdentifiers(lootConditionManager.getIds(), suggestionsBuilder);
    };

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralCommandNode literalCommandNode = dispatcher.register((LiteralArgumentBuilder)CommandManager.literal("execute").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2)));
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("execute").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))).then(CommandManager.literal("run").redirect((CommandNode)dispatcher.getRoot()))).then(ExecuteCommand.addConditionArguments((CommandNode<ServerCommandSource>)literalCommandNode, CommandManager.literal("if"), true))).then(ExecuteCommand.addConditionArguments((CommandNode<ServerCommandSource>)literalCommandNode, CommandManager.literal("unless"), false))).then(CommandManager.literal("as").then(CommandManager.argument("targets", EntityArgumentType.entities()).fork((CommandNode)literalCommandNode, commandContext -> {
            ArrayList arrayList = Lists.newArrayList();
            for (Entity entity : EntityArgumentType.getOptionalEntities((CommandContext<ServerCommandSource>)commandContext, "targets")) {
                arrayList.add(((ServerCommandSource)commandContext.getSource()).withEntity(entity));
            }
            return arrayList;
        })))).then(CommandManager.literal("at").then(CommandManager.argument("targets", EntityArgumentType.entities()).fork((CommandNode)literalCommandNode, commandContext -> {
            ArrayList arrayList = Lists.newArrayList();
            for (Entity entity : EntityArgumentType.getOptionalEntities((CommandContext<ServerCommandSource>)commandContext, "targets")) {
                arrayList.add(((ServerCommandSource)commandContext.getSource()).withWorld((ServerWorld)entity.world).withPosition(entity.getPos()).withRotation(entity.getRotationClient()));
            }
            return arrayList;
        })))).then(((LiteralArgumentBuilder)CommandManager.literal("store").then(ExecuteCommand.addStoreArguments((LiteralCommandNode<ServerCommandSource>)literalCommandNode, CommandManager.literal("result"), true))).then(ExecuteCommand.addStoreArguments((LiteralCommandNode<ServerCommandSource>)literalCommandNode, CommandManager.literal("success"), false)))).then(((LiteralArgumentBuilder)CommandManager.literal("positioned").then(CommandManager.argument("pos", Vec3ArgumentType.vec3()).redirect((CommandNode)literalCommandNode, commandContext -> ((ServerCommandSource)commandContext.getSource()).withPosition(Vec3ArgumentType.getVec3((CommandContext<ServerCommandSource>)commandContext, "pos")).withEntityAnchor(EntityAnchorArgumentType.EntityAnchor.FEET)))).then(CommandManager.literal("as").then(CommandManager.argument("targets", EntityArgumentType.entities()).fork((CommandNode)literalCommandNode, commandContext -> {
            ArrayList arrayList = Lists.newArrayList();
            for (Entity entity : EntityArgumentType.getOptionalEntities((CommandContext<ServerCommandSource>)commandContext, "targets")) {
                arrayList.add(((ServerCommandSource)commandContext.getSource()).withPosition(entity.getPos()));
            }
            return arrayList;
        }))))).then(((LiteralArgumentBuilder)CommandManager.literal("rotated").then(CommandManager.argument("rot", RotationArgumentType.rotation()).redirect((CommandNode)literalCommandNode, commandContext -> ((ServerCommandSource)commandContext.getSource()).withRotation(RotationArgumentType.getRotation((CommandContext<ServerCommandSource>)commandContext, "rot").toAbsoluteRotation((ServerCommandSource)commandContext.getSource()))))).then(CommandManager.literal("as").then(CommandManager.argument("targets", EntityArgumentType.entities()).fork((CommandNode)literalCommandNode, commandContext -> {
            ArrayList arrayList = Lists.newArrayList();
            for (Entity entity : EntityArgumentType.getOptionalEntities((CommandContext<ServerCommandSource>)commandContext, "targets")) {
                arrayList.add(((ServerCommandSource)commandContext.getSource()).withRotation(entity.getRotationClient()));
            }
            return arrayList;
        }))))).then(((LiteralArgumentBuilder)CommandManager.literal("facing").then(CommandManager.literal("entity").then(CommandManager.argument("targets", EntityArgumentType.entities()).then(CommandManager.argument("anchor", EntityAnchorArgumentType.entityAnchor()).fork((CommandNode)literalCommandNode, commandContext -> {
            ArrayList arrayList = Lists.newArrayList();
            EntityAnchorArgumentType.EntityAnchor _snowman2 = EntityAnchorArgumentType.getEntityAnchor((CommandContext<ServerCommandSource>)commandContext, "anchor");
            for (Entity entity : EntityArgumentType.getOptionalEntities((CommandContext<ServerCommandSource>)commandContext, "targets")) {
                arrayList.add(((ServerCommandSource)commandContext.getSource()).withLookingAt(entity, _snowman2));
            }
            return arrayList;
        }))))).then(CommandManager.argument("pos", Vec3ArgumentType.vec3()).redirect((CommandNode)literalCommandNode, commandContext -> ((ServerCommandSource)commandContext.getSource()).withLookingAt(Vec3ArgumentType.getVec3((CommandContext<ServerCommandSource>)commandContext, "pos")))))).then(CommandManager.literal("align").then(CommandManager.argument("axes", SwizzleArgumentType.swizzle()).redirect((CommandNode)literalCommandNode, commandContext -> ((ServerCommandSource)commandContext.getSource()).withPosition(((ServerCommandSource)commandContext.getSource()).getPosition().floorAlongAxes(SwizzleArgumentType.getSwizzle((CommandContext<ServerCommandSource>)commandContext, "axes"))))))).then(CommandManager.literal("anchored").then(CommandManager.argument("anchor", EntityAnchorArgumentType.entityAnchor()).redirect((CommandNode)literalCommandNode, commandContext -> ((ServerCommandSource)commandContext.getSource()).withEntityAnchor(EntityAnchorArgumentType.getEntityAnchor((CommandContext<ServerCommandSource>)commandContext, "anchor")))))).then(CommandManager.literal("in").then(CommandManager.argument("dimension", DimensionArgumentType.dimension()).redirect((CommandNode)literalCommandNode, commandContext -> ((ServerCommandSource)commandContext.getSource()).withWorld(DimensionArgumentType.getDimensionArgument((CommandContext<ServerCommandSource>)commandContext, "dimension"))))));
    }

    private static ArgumentBuilder<ServerCommandSource, ?> addStoreArguments(LiteralCommandNode<ServerCommandSource> node, LiteralArgumentBuilder<ServerCommandSource> builder, boolean requestResult) {
        builder.then(CommandManager.literal("score").then(CommandManager.argument("targets", ScoreHolderArgumentType.scoreHolders()).suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER).then(CommandManager.argument("objective", ObjectiveArgumentType.objective()).redirect(node, commandContext -> ExecuteCommand.executeStoreScore((ServerCommandSource)commandContext.getSource(), ScoreHolderArgumentType.getScoreboardScoreHolders((CommandContext<ServerCommandSource>)commandContext, "targets"), ObjectiveArgumentType.getObjective((CommandContext<ServerCommandSource>)commandContext, "objective"), requestResult)))));
        builder.then(CommandManager.literal("bossbar").then(((RequiredArgumentBuilder)CommandManager.argument("id", IdentifierArgumentType.identifier()).suggests(BossBarCommand.SUGGESTION_PROVIDER).then(CommandManager.literal("value").redirect(node, commandContext -> ExecuteCommand.executeStoreBossbar((ServerCommandSource)commandContext.getSource(), BossBarCommand.getBossBar((CommandContext<ServerCommandSource>)commandContext), true, requestResult)))).then(CommandManager.literal("max").redirect(node, commandContext -> ExecuteCommand.executeStoreBossbar((ServerCommandSource)commandContext.getSource(), BossBarCommand.getBossBar((CommandContext<ServerCommandSource>)commandContext), false, requestResult)))));
        for (DataCommand.ObjectType objectType : DataCommand.TARGET_OBJECT_TYPES) {
            objectType.addArgumentsToBuilder((ArgumentBuilder<ServerCommandSource, ?>)builder, argumentBuilder -> argumentBuilder.then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument("path", NbtPathArgumentType.nbtPath()).then(CommandManager.literal("int").then(CommandManager.argument("scale", DoubleArgumentType.doubleArg()).redirect((CommandNode)node, commandContext -> ExecuteCommand.executeStoreData((ServerCommandSource)commandContext.getSource(), objectType.getObject((CommandContext<ServerCommandSource>)commandContext), NbtPathArgumentType.getNbtPath((CommandContext<ServerCommandSource>)commandContext, "path"), n -> IntTag.of((int)((double)n * DoubleArgumentType.getDouble((CommandContext)commandContext, (String)"scale"))), requestResult))))).then(CommandManager.literal("float").then(CommandManager.argument("scale", DoubleArgumentType.doubleArg()).redirect((CommandNode)node, commandContext -> ExecuteCommand.executeStoreData((ServerCommandSource)commandContext.getSource(), objectType.getObject((CommandContext<ServerCommandSource>)commandContext), NbtPathArgumentType.getNbtPath((CommandContext<ServerCommandSource>)commandContext, "path"), n -> FloatTag.of((float)((double)n * DoubleArgumentType.getDouble((CommandContext)commandContext, (String)"scale"))), requestResult))))).then(CommandManager.literal("short").then(CommandManager.argument("scale", DoubleArgumentType.doubleArg()).redirect((CommandNode)node, commandContext -> ExecuteCommand.executeStoreData((ServerCommandSource)commandContext.getSource(), objectType.getObject((CommandContext<ServerCommandSource>)commandContext), NbtPathArgumentType.getNbtPath((CommandContext<ServerCommandSource>)commandContext, "path"), n -> ShortTag.of((short)((double)n * DoubleArgumentType.getDouble((CommandContext)commandContext, (String)"scale"))), requestResult))))).then(CommandManager.literal("long").then(CommandManager.argument("scale", DoubleArgumentType.doubleArg()).redirect((CommandNode)node, commandContext -> ExecuteCommand.executeStoreData((ServerCommandSource)commandContext.getSource(), objectType.getObject((CommandContext<ServerCommandSource>)commandContext), NbtPathArgumentType.getNbtPath((CommandContext<ServerCommandSource>)commandContext, "path"), n -> LongTag.of((long)((double)n * DoubleArgumentType.getDouble((CommandContext)commandContext, (String)"scale"))), requestResult))))).then(CommandManager.literal("double").then(CommandManager.argument("scale", DoubleArgumentType.doubleArg()).redirect((CommandNode)node, commandContext -> ExecuteCommand.executeStoreData((ServerCommandSource)commandContext.getSource(), objectType.getObject((CommandContext<ServerCommandSource>)commandContext), NbtPathArgumentType.getNbtPath((CommandContext<ServerCommandSource>)commandContext, "path"), n -> DoubleTag.of((double)n * DoubleArgumentType.getDouble((CommandContext)commandContext, (String)"scale")), requestResult))))).then(CommandManager.literal("byte").then(CommandManager.argument("scale", DoubleArgumentType.doubleArg()).redirect((CommandNode)node, commandContext -> ExecuteCommand.executeStoreData((ServerCommandSource)commandContext.getSource(), objectType.getObject((CommandContext<ServerCommandSource>)commandContext), NbtPathArgumentType.getNbtPath((CommandContext<ServerCommandSource>)commandContext, "path"), n -> ByteTag.of((byte)((double)n * DoubleArgumentType.getDouble((CommandContext)commandContext, (String)"scale"))), requestResult))))));
        }
        return builder;
    }

    private static ServerCommandSource executeStoreScore(ServerCommandSource source, Collection<String> targets, ScoreboardObjective objective, boolean requestResult) {
        ServerScoreboard serverScoreboard = source.getMinecraftServer().getScoreboard();
        return source.mergeConsumers((ResultConsumer<ServerCommandSource>)((ResultConsumer)(commandContext, bl2, n) -> {
            for (String string : targets) {
                ScoreboardPlayerScore scoreboardPlayerScore = serverScoreboard.getPlayerScore(string, objective);
                int _snowman2 = requestResult ? n : (bl2 ? 1 : 0);
                scoreboardPlayerScore.setScore(_snowman2);
            }
        }), BINARY_RESULT_CONSUMER);
    }

    private static ServerCommandSource executeStoreBossbar(ServerCommandSource source, CommandBossBar bossBar, boolean storeInValue, boolean requestResult) {
        return source.mergeConsumers((ResultConsumer<ServerCommandSource>)((ResultConsumer)(commandContext, bl3, n) -> {
            int n2 = requestResult ? n : (_snowman = bl3 ? 1 : 0);
            if (storeInValue) {
                bossBar.setValue(_snowman);
            } else {
                bossBar.setMaxValue(_snowman);
            }
        }), BINARY_RESULT_CONSUMER);
    }

    private static ServerCommandSource executeStoreData(ServerCommandSource source, DataCommandObject object, NbtPathArgumentType.NbtPath path, IntFunction<Tag> tagSetter, boolean requestResult) {
        return source.mergeConsumers((ResultConsumer<ServerCommandSource>)((ResultConsumer)(commandContext, bl2, n) -> {
            try {
                CompoundTag compoundTag = object.getTag();
                int _snowman2 = requestResult ? n : (bl2 ? 1 : 0);
                path.put(compoundTag, () -> (Tag)tagSetter.apply(_snowman2));
                object.setTag(compoundTag);
            }
            catch (CommandSyntaxException commandSyntaxException) {
                // empty catch block
            }
        }), BINARY_RESULT_CONSUMER);
    }

    private static ArgumentBuilder<ServerCommandSource, ?> addConditionArguments(CommandNode<ServerCommandSource> root, LiteralArgumentBuilder<ServerCommandSource> argumentBuilder2, boolean positive) {
        ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)argumentBuilder2.then(CommandManager.literal("block").then(CommandManager.argument("pos", BlockPosArgumentType.blockPos()).then(ExecuteCommand.addConditionLogic(root, CommandManager.argument("block", BlockPredicateArgumentType.blockPredicate()), positive, commandContext -> BlockPredicateArgumentType.getBlockPredicate((CommandContext<ServerCommandSource>)commandContext, "block").test(new CachedBlockPosition(((ServerCommandSource)commandContext.getSource()).getWorld(), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "pos"), true))))))).then(CommandManager.literal("score").then(CommandManager.argument("target", ScoreHolderArgumentType.scoreHolder()).suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument("targetObjective", ObjectiveArgumentType.objective()).then(CommandManager.literal("=").then(CommandManager.argument("source", ScoreHolderArgumentType.scoreHolder()).suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER).then(ExecuteCommand.addConditionLogic(root, CommandManager.argument("sourceObjective", ObjectiveArgumentType.objective()), positive, commandContext -> ExecuteCommand.testScoreCondition((CommandContext<ServerCommandSource>)commandContext, Integer::equals)))))).then(CommandManager.literal("<").then(CommandManager.argument("source", ScoreHolderArgumentType.scoreHolder()).suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER).then(ExecuteCommand.addConditionLogic(root, CommandManager.argument("sourceObjective", ObjectiveArgumentType.objective()), positive, commandContext -> ExecuteCommand.testScoreCondition((CommandContext<ServerCommandSource>)commandContext, (n, n2) -> n < n2)))))).then(CommandManager.literal("<=").then(CommandManager.argument("source", ScoreHolderArgumentType.scoreHolder()).suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER).then(ExecuteCommand.addConditionLogic(root, CommandManager.argument("sourceObjective", ObjectiveArgumentType.objective()), positive, commandContext -> ExecuteCommand.testScoreCondition((CommandContext<ServerCommandSource>)commandContext, (n, n2) -> n <= n2)))))).then(CommandManager.literal(">").then(CommandManager.argument("source", ScoreHolderArgumentType.scoreHolder()).suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER).then(ExecuteCommand.addConditionLogic(root, CommandManager.argument("sourceObjective", ObjectiveArgumentType.objective()), positive, commandContext -> ExecuteCommand.testScoreCondition((CommandContext<ServerCommandSource>)commandContext, (n, n2) -> n > n2)))))).then(CommandManager.literal(">=").then(CommandManager.argument("source", ScoreHolderArgumentType.scoreHolder()).suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER).then(ExecuteCommand.addConditionLogic(root, CommandManager.argument("sourceObjective", ObjectiveArgumentType.objective()), positive, commandContext -> ExecuteCommand.testScoreCondition((CommandContext<ServerCommandSource>)commandContext, (n, n2) -> n >= n2)))))).then(CommandManager.literal("matches").then(ExecuteCommand.addConditionLogic(root, CommandManager.argument("range", NumberRangeArgumentType.numberRange()), positive, commandContext -> ExecuteCommand.testScoreMatch((CommandContext<ServerCommandSource>)commandContext, NumberRangeArgumentType.IntRangeArgumentType.getRangeArgument((CommandContext<ServerCommandSource>)commandContext, "range"))))))))).then(CommandManager.literal("blocks").then(CommandManager.argument("start", BlockPosArgumentType.blockPos()).then(CommandManager.argument("end", BlockPosArgumentType.blockPos()).then(((RequiredArgumentBuilder)CommandManager.argument("destination", BlockPosArgumentType.blockPos()).then(ExecuteCommand.addBlocksConditionLogic(root, CommandManager.literal("all"), positive, false))).then(ExecuteCommand.addBlocksConditionLogic(root, CommandManager.literal("masked"), positive, true))))))).then(CommandManager.literal("entity").then(((RequiredArgumentBuilder)CommandManager.argument("entities", EntityArgumentType.entities()).fork(root, commandContext -> ExecuteCommand.getSourceOrEmptyForConditionFork((CommandContext<ServerCommandSource>)commandContext, positive, !EntityArgumentType.getOptionalEntities((CommandContext<ServerCommandSource>)commandContext, "entities").isEmpty()))).executes(ExecuteCommand.getExistsConditionExecute(positive, commandContext -> EntityArgumentType.getOptionalEntities((CommandContext<ServerCommandSource>)commandContext, "entities").size()))))).then(CommandManager.literal("predicate").then(ExecuteCommand.addConditionLogic(root, CommandManager.argument("predicate", IdentifierArgumentType.identifier()).suggests(LOOT_CONDITIONS), positive, commandContext -> ExecuteCommand.testLootCondition((ServerCommandSource)commandContext.getSource(), IdentifierArgumentType.method_23727((CommandContext<ServerCommandSource>)commandContext, "predicate")))));
        for (DataCommand.ObjectType objectType : DataCommand.SOURCE_OBJECT_TYPES) {
            argumentBuilder2.then(objectType.addArgumentsToBuilder((ArgumentBuilder<ServerCommandSource, ?>)CommandManager.literal("data"), argumentBuilder -> argumentBuilder.then(((RequiredArgumentBuilder)CommandManager.argument("path", NbtPathArgumentType.nbtPath()).fork(root, commandContext -> ExecuteCommand.getSourceOrEmptyForConditionFork((CommandContext<ServerCommandSource>)commandContext, positive, ExecuteCommand.countPathMatches(objectType.getObject((CommandContext<ServerCommandSource>)commandContext), NbtPathArgumentType.getNbtPath((CommandContext<ServerCommandSource>)commandContext, "path")) > 0))).executes(ExecuteCommand.getExistsConditionExecute(positive, commandContext -> ExecuteCommand.countPathMatches(objectType.getObject((CommandContext<ServerCommandSource>)commandContext), NbtPathArgumentType.getNbtPath((CommandContext<ServerCommandSource>)commandContext, "path")))))));
        }
        return argumentBuilder2;
    }

    private static Command<ServerCommandSource> getExistsConditionExecute(boolean positive, ExistsCondition condition) {
        if (positive) {
            return commandContext -> {
                int n = condition.test((CommandContext<ServerCommandSource>)commandContext);
                if (n > 0) {
                    ((ServerCommandSource)commandContext.getSource()).sendFeedback(new TranslatableText("commands.execute.conditional.pass_count", n), false);
                    return n;
                }
                throw CONDITIONAL_FAIL_EXCEPTION.create();
            };
        }
        return commandContext -> {
            int n = condition.test((CommandContext<ServerCommandSource>)commandContext);
            if (n == 0) {
                ((ServerCommandSource)commandContext.getSource()).sendFeedback(new TranslatableText("commands.execute.conditional.pass"), false);
                return 1;
            }
            throw CONDITIONAL_FAIL_COUNT_EXCEPTION.create((Object)n);
        };
    }

    private static int countPathMatches(DataCommandObject object, NbtPathArgumentType.NbtPath path) throws CommandSyntaxException {
        return path.count(object.getTag());
    }

    private static boolean testScoreCondition(CommandContext<ServerCommandSource> context, BiPredicate<Integer, Integer> condition) throws CommandSyntaxException {
        String string = ScoreHolderArgumentType.getScoreHolder(context, "target");
        ScoreboardObjective _snowman2 = ObjectiveArgumentType.getObjective(context, "targetObjective");
        _snowman = ScoreHolderArgumentType.getScoreHolder(context, "source");
        ScoreboardObjective _snowman3 = ObjectiveArgumentType.getObjective(context, "sourceObjective");
        ServerScoreboard _snowman4 = ((ServerCommandSource)context.getSource()).getMinecraftServer().getScoreboard();
        if (!_snowman4.playerHasObjective(string, _snowman2) || !_snowman4.playerHasObjective(_snowman, _snowman3)) {
            return false;
        }
        ScoreboardPlayerScore _snowman5 = _snowman4.getPlayerScore(string, _snowman2);
        ScoreboardPlayerScore _snowman6 = _snowman4.getPlayerScore(_snowman, _snowman3);
        return condition.test(_snowman5.getScore(), _snowman6.getScore());
    }

    private static boolean testScoreMatch(CommandContext<ServerCommandSource> context, NumberRange.IntRange range) throws CommandSyntaxException {
        String string = ScoreHolderArgumentType.getScoreHolder(context, "target");
        ScoreboardObjective _snowman2 = ObjectiveArgumentType.getObjective(context, "targetObjective");
        ServerScoreboard _snowman3 = ((ServerCommandSource)context.getSource()).getMinecraftServer().getScoreboard();
        if (!_snowman3.playerHasObjective(string, _snowman2)) {
            return false;
        }
        return range.test(_snowman3.getPlayerScore(string, _snowman2).getScore());
    }

    private static boolean testLootCondition(ServerCommandSource serverCommandSource, LootCondition lootCondition) {
        ServerWorld serverWorld = serverCommandSource.getWorld();
        LootContext.Builder _snowman2 = new LootContext.Builder(serverWorld).parameter(LootContextParameters.ORIGIN, serverCommandSource.getPosition()).optionalParameter(LootContextParameters.THIS_ENTITY, serverCommandSource.getEntity());
        return lootCondition.test(_snowman2.build(LootContextTypes.COMMAND));
    }

    private static Collection<ServerCommandSource> getSourceOrEmptyForConditionFork(CommandContext<ServerCommandSource> context, boolean positive, boolean value) {
        if (value == positive) {
            return Collections.singleton(context.getSource());
        }
        return Collections.emptyList();
    }

    private static ArgumentBuilder<ServerCommandSource, ?> addConditionLogic(CommandNode<ServerCommandSource> root, ArgumentBuilder<ServerCommandSource, ?> builder, boolean positive, Condition condition) {
        return builder.fork(root, commandContext -> ExecuteCommand.getSourceOrEmptyForConditionFork((CommandContext<ServerCommandSource>)commandContext, positive, condition.test((CommandContext<ServerCommandSource>)commandContext))).executes(commandContext -> {
            if (positive == condition.test((CommandContext<ServerCommandSource>)commandContext)) {
                ((ServerCommandSource)commandContext.getSource()).sendFeedback(new TranslatableText("commands.execute.conditional.pass"), false);
                return 1;
            }
            throw CONDITIONAL_FAIL_EXCEPTION.create();
        });
    }

    private static ArgumentBuilder<ServerCommandSource, ?> addBlocksConditionLogic(CommandNode<ServerCommandSource> root, ArgumentBuilder<ServerCommandSource, ?> builder, boolean positive, boolean masked) {
        return builder.fork(root, commandContext -> ExecuteCommand.getSourceOrEmptyForConditionFork((CommandContext<ServerCommandSource>)commandContext, positive, ExecuteCommand.testBlocksCondition((CommandContext<ServerCommandSource>)commandContext, masked).isPresent())).executes(positive ? commandContext -> ExecuteCommand.executePositiveBlockCondition((CommandContext<ServerCommandSource>)commandContext, masked) : commandContext -> ExecuteCommand.executeNegativeBlockCondition((CommandContext<ServerCommandSource>)commandContext, masked));
    }

    private static int executePositiveBlockCondition(CommandContext<ServerCommandSource> context, boolean masked) throws CommandSyntaxException {
        OptionalInt optionalInt = ExecuteCommand.testBlocksCondition(context, masked);
        if (optionalInt.isPresent()) {
            ((ServerCommandSource)context.getSource()).sendFeedback(new TranslatableText("commands.execute.conditional.pass_count", optionalInt.getAsInt()), false);
            return optionalInt.getAsInt();
        }
        throw CONDITIONAL_FAIL_EXCEPTION.create();
    }

    private static int executeNegativeBlockCondition(CommandContext<ServerCommandSource> context, boolean masked) throws CommandSyntaxException {
        OptionalInt optionalInt = ExecuteCommand.testBlocksCondition(context, masked);
        if (optionalInt.isPresent()) {
            throw CONDITIONAL_FAIL_COUNT_EXCEPTION.create((Object)optionalInt.getAsInt());
        }
        ((ServerCommandSource)context.getSource()).sendFeedback(new TranslatableText("commands.execute.conditional.pass"), false);
        return 1;
    }

    private static OptionalInt testBlocksCondition(CommandContext<ServerCommandSource> context, boolean masked) throws CommandSyntaxException {
        return ExecuteCommand.testBlocksCondition(((ServerCommandSource)context.getSource()).getWorld(), BlockPosArgumentType.getLoadedBlockPos(context, "start"), BlockPosArgumentType.getLoadedBlockPos(context, "end"), BlockPosArgumentType.getLoadedBlockPos(context, "destination"), masked);
    }

    private static OptionalInt testBlocksCondition(ServerWorld world, BlockPos start, BlockPos end, BlockPos destination, boolean masked) throws CommandSyntaxException {
        BlockBox blockBox = new BlockBox(start, end);
        _snowman = new BlockBox(destination, destination.add(blockBox.getDimensions()));
        BlockPos _snowman2 = new BlockPos(_snowman.minX - blockBox.minX, _snowman.minY - blockBox.minY, _snowman.minZ - blockBox.minZ);
        int _snowman3 = blockBox.getBlockCountX() * blockBox.getBlockCountY() * blockBox.getBlockCountZ();
        if (_snowman3 > 32768) {
            throw BLOCKS_TOOBIG_EXCEPTION.create((Object)32768, (Object)_snowman3);
        }
        int _snowman4 = 0;
        for (int i = blockBox.minZ; i <= blockBox.maxZ; ++i) {
            for (_snowman = blockBox.minY; _snowman <= blockBox.maxY; ++_snowman) {
                for (_snowman = blockBox.minX; _snowman <= blockBox.maxX; ++_snowman) {
                    BlockPos blockPos = new BlockPos(_snowman, _snowman, i);
                    _snowman = blockPos.add(_snowman2);
                    BlockState _snowman5 = world.getBlockState(blockPos);
                    if (masked && _snowman5.isOf(Blocks.AIR)) continue;
                    if (_snowman5 != world.getBlockState(_snowman)) {
                        return OptionalInt.empty();
                    }
                    BlockEntity _snowman6 = world.getBlockEntity(blockPos);
                    BlockEntity _snowman7 = world.getBlockEntity(_snowman);
                    if (_snowman6 != null) {
                        if (_snowman7 == null) {
                            return OptionalInt.empty();
                        }
                        CompoundTag compoundTag = _snowman6.toTag(new CompoundTag());
                        compoundTag.remove("x");
                        compoundTag.remove("y");
                        compoundTag.remove("z");
                        _snowman = _snowman7.toTag(new CompoundTag());
                        _snowman.remove("x");
                        _snowman.remove("y");
                        _snowman.remove("z");
                        if (!compoundTag.equals(_snowman)) {
                            return OptionalInt.empty();
                        }
                    }
                    ++_snowman4;
                }
            }
        }
        return OptionalInt.of(_snowman4);
    }

    @FunctionalInterface
    static interface ExistsCondition {
        public int test(CommandContext<ServerCommandSource> var1) throws CommandSyntaxException;
    }

    @FunctionalInterface
    static interface Condition {
        public boolean test(CommandContext<ServerCommandSource> var1) throws CommandSyntaxException;
    }
}

