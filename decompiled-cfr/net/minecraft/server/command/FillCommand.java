/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  javax.annotation.Nullable
 */
package net.minecraft.server.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.BlockPredicateArgumentType;
import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.command.argument.BlockStateArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.SetBlockCommand;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;

public class FillCommand {
    private static final Dynamic2CommandExceptionType TOO_BIG_EXCEPTION = new Dynamic2CommandExceptionType((object, object2) -> new TranslatableText("commands.fill.toobig", object, object2));
    private static final BlockStateArgument AIR_BLOCK_ARGUMENT = new BlockStateArgument(Blocks.AIR.getDefaultState(), Collections.emptySet(), null);
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType((Message)new TranslatableText("commands.fill.failed"));

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("fill").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))).then(CommandManager.argument("from", BlockPosArgumentType.blockPos()).then(CommandManager.argument("to", BlockPosArgumentType.blockPos()).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument("block", BlockStateArgumentType.blockState()).executes(commandContext -> FillCommand.execute((ServerCommandSource)commandContext.getSource(), new BlockBox(BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "from"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "to")), BlockStateArgumentType.getBlockState((CommandContext<ServerCommandSource>)commandContext, "block"), Mode.REPLACE, null))).then(((LiteralArgumentBuilder)CommandManager.literal("replace").executes(commandContext -> FillCommand.execute((ServerCommandSource)commandContext.getSource(), new BlockBox(BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "from"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "to")), BlockStateArgumentType.getBlockState((CommandContext<ServerCommandSource>)commandContext, "block"), Mode.REPLACE, null))).then(CommandManager.argument("filter", BlockPredicateArgumentType.blockPredicate()).executes(commandContext -> FillCommand.execute((ServerCommandSource)commandContext.getSource(), new BlockBox(BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "from"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "to")), BlockStateArgumentType.getBlockState((CommandContext<ServerCommandSource>)commandContext, "block"), Mode.REPLACE, BlockPredicateArgumentType.getBlockPredicate((CommandContext<ServerCommandSource>)commandContext, "filter")))))).then(CommandManager.literal("keep").executes(commandContext -> FillCommand.execute((ServerCommandSource)commandContext.getSource(), new BlockBox(BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "from"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "to")), BlockStateArgumentType.getBlockState((CommandContext<ServerCommandSource>)commandContext, "block"), Mode.REPLACE, cachedBlockPosition -> cachedBlockPosition.getWorld().isAir(cachedBlockPosition.getBlockPos()))))).then(CommandManager.literal("outline").executes(commandContext -> FillCommand.execute((ServerCommandSource)commandContext.getSource(), new BlockBox(BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "from"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "to")), BlockStateArgumentType.getBlockState((CommandContext<ServerCommandSource>)commandContext, "block"), Mode.OUTLINE, null)))).then(CommandManager.literal("hollow").executes(commandContext -> FillCommand.execute((ServerCommandSource)commandContext.getSource(), new BlockBox(BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "from"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "to")), BlockStateArgumentType.getBlockState((CommandContext<ServerCommandSource>)commandContext, "block"), Mode.HOLLOW, null)))).then(CommandManager.literal("destroy").executes(commandContext -> FillCommand.execute((ServerCommandSource)commandContext.getSource(), new BlockBox(BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "from"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "to")), BlockStateArgumentType.getBlockState((CommandContext<ServerCommandSource>)commandContext, "block"), Mode.DESTROY, null)))))));
    }

    private static int execute(ServerCommandSource source, BlockBox range, BlockStateArgument block, Mode mode, @Nullable Predicate<CachedBlockPosition> filter) throws CommandSyntaxException {
        int n;
        int n2 = range.getBlockCountX() * range.getBlockCountY() * range.getBlockCountZ();
        if (n2 > 32768) {
            throw TOO_BIG_EXCEPTION.create((Object)32768, (Object)n2);
        }
        ArrayList _snowman2 = Lists.newArrayList();
        ServerWorld _snowman3 = source.getWorld();
        n = 0;
        for (BlockPos blockPos : BlockPos.iterate(range.minX, range.minY, range.minZ, range.maxX, range.maxY, range.maxZ)) {
            if (filter != null && !filter.test(new CachedBlockPosition(_snowman3, blockPos, true)) || (object = mode.filter.filter(range, blockPos, block, _snowman3)) == null) continue;
            BlockEntity blockEntity = _snowman3.getBlockEntity(blockPos);
            Clearable.clear(blockEntity);
            if (!((BlockStateArgument)object).setBlockState(_snowman3, blockPos, 2)) continue;
            _snowman2.add(blockPos.toImmutable());
            ++n;
        }
        for (BlockPos blockPos : _snowman2) {
            Object object = _snowman3.getBlockState(blockPos).getBlock();
            _snowman3.updateNeighbors(blockPos, (Block)object);
        }
        if (n == 0) {
            throw FAILED_EXCEPTION.create();
        }
        source.sendFeedback(new TranslatableText("commands.fill.success", n), true);
        return n;
    }

    static enum Mode {
        REPLACE((blockBox, blockPos, blockStateArgument, serverWorld) -> blockStateArgument),
        OUTLINE((blockBox, blockPos, blockStateArgument, serverWorld) -> {
            if (blockPos.getX() == blockBox.minX || blockPos.getX() == blockBox.maxX || blockPos.getY() == blockBox.minY || blockPos.getY() == blockBox.maxY || blockPos.getZ() == blockBox.minZ || blockPos.getZ() == blockBox.maxZ) {
                return blockStateArgument;
            }
            return null;
        }),
        HOLLOW((blockBox, blockPos, blockStateArgument, serverWorld) -> {
            if (blockPos.getX() == blockBox.minX || blockPos.getX() == blockBox.maxX || blockPos.getY() == blockBox.minY || blockPos.getY() == blockBox.maxY || blockPos.getZ() == blockBox.minZ || blockPos.getZ() == blockBox.maxZ) {
                return blockStateArgument;
            }
            return AIR_BLOCK_ARGUMENT;
        }),
        DESTROY((blockBox, blockPos, blockStateArgument, serverWorld) -> {
            serverWorld.breakBlock(blockPos, true);
            return blockStateArgument;
        });

        public final SetBlockCommand.Filter filter;

        private Mode(SetBlockCommand.Filter filter) {
            this.filter = filter;
        }
    }
}

