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
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.BlockPredicateArgumentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerTickScheduler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;

public class CloneCommand {
    private static final SimpleCommandExceptionType OVERLAP_EXCEPTION = new SimpleCommandExceptionType((Message)new TranslatableText("commands.clone.overlap"));
    private static final Dynamic2CommandExceptionType TOO_BIG_EXCEPTION = new Dynamic2CommandExceptionType((object, object2) -> new TranslatableText("commands.clone.toobig", object, object2));
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType((Message)new TranslatableText("commands.clone.failed"));
    public static final Predicate<CachedBlockPosition> IS_AIR_PREDICATE = cachedBlockPosition -> !cachedBlockPosition.getBlockState().isAir();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("clone").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))).then(CommandManager.argument("begin", BlockPosArgumentType.blockPos()).then(CommandManager.argument("end", BlockPosArgumentType.blockPos()).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument("destination", BlockPosArgumentType.blockPos()).executes(commandContext -> CloneCommand.execute((ServerCommandSource)commandContext.getSource(), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "begin"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "end"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "destination"), cachedBlockPosition -> true, Mode.NORMAL))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("replace").executes(commandContext -> CloneCommand.execute((ServerCommandSource)commandContext.getSource(), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "begin"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "end"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "destination"), cachedBlockPosition -> true, Mode.NORMAL))).then(CommandManager.literal("force").executes(commandContext -> CloneCommand.execute((ServerCommandSource)commandContext.getSource(), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "begin"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "end"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "destination"), cachedBlockPosition -> true, Mode.FORCE)))).then(CommandManager.literal("move").executes(commandContext -> CloneCommand.execute((ServerCommandSource)commandContext.getSource(), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "begin"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "end"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "destination"), cachedBlockPosition -> true, Mode.MOVE)))).then(CommandManager.literal("normal").executes(commandContext -> CloneCommand.execute((ServerCommandSource)commandContext.getSource(), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "begin"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "end"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "destination"), cachedBlockPosition -> true, Mode.NORMAL))))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("masked").executes(commandContext -> CloneCommand.execute((ServerCommandSource)commandContext.getSource(), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "begin"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "end"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "destination"), IS_AIR_PREDICATE, Mode.NORMAL))).then(CommandManager.literal("force").executes(commandContext -> CloneCommand.execute((ServerCommandSource)commandContext.getSource(), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "begin"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "end"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "destination"), IS_AIR_PREDICATE, Mode.FORCE)))).then(CommandManager.literal("move").executes(commandContext -> CloneCommand.execute((ServerCommandSource)commandContext.getSource(), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "begin"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "end"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "destination"), IS_AIR_PREDICATE, Mode.MOVE)))).then(CommandManager.literal("normal").executes(commandContext -> CloneCommand.execute((ServerCommandSource)commandContext.getSource(), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "begin"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "end"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "destination"), IS_AIR_PREDICATE, Mode.NORMAL))))).then(CommandManager.literal("filtered").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument("filter", BlockPredicateArgumentType.blockPredicate()).executes(commandContext -> CloneCommand.execute((ServerCommandSource)commandContext.getSource(), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "begin"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "end"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "destination"), BlockPredicateArgumentType.getBlockPredicate((CommandContext<ServerCommandSource>)commandContext, "filter"), Mode.NORMAL))).then(CommandManager.literal("force").executes(commandContext -> CloneCommand.execute((ServerCommandSource)commandContext.getSource(), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "begin"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "end"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "destination"), BlockPredicateArgumentType.getBlockPredicate((CommandContext<ServerCommandSource>)commandContext, "filter"), Mode.FORCE)))).then(CommandManager.literal("move").executes(commandContext -> CloneCommand.execute((ServerCommandSource)commandContext.getSource(), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "begin"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "end"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "destination"), BlockPredicateArgumentType.getBlockPredicate((CommandContext<ServerCommandSource>)commandContext, "filter"), Mode.MOVE)))).then(CommandManager.literal("normal").executes(commandContext -> CloneCommand.execute((ServerCommandSource)commandContext.getSource(), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "begin"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "end"), BlockPosArgumentType.getLoadedBlockPos((CommandContext<ServerCommandSource>)commandContext, "destination"), BlockPredicateArgumentType.getBlockPredicate((CommandContext<ServerCommandSource>)commandContext, "filter"), Mode.NORMAL)))))))));
    }

    private static int execute(ServerCommandSource source, BlockPos begin, BlockPos end, BlockPos destination, Predicate<CachedBlockPosition> filter, Mode mode) throws CommandSyntaxException {
        BlockBox blockBox = new BlockBox(begin, end);
        BlockPos _snowman2 = destination.add(blockBox.getDimensions());
        _snowman = new BlockBox(destination, _snowman2);
        if (!mode.allowsOverlap() && _snowman.intersects(blockBox)) {
            throw OVERLAP_EXCEPTION.create();
        }
        int _snowman3 = blockBox.getBlockCountX() * blockBox.getBlockCountY() * blockBox.getBlockCountZ();
        if (_snowman3 > 32768) {
            throw TOO_BIG_EXCEPTION.create((Object)32768, (Object)_snowman3);
        }
        ServerWorld _snowman4 = source.getWorld();
        if (!_snowman4.isRegionLoaded(begin, end) || !_snowman4.isRegionLoaded(destination, _snowman2)) {
            throw BlockPosArgumentType.UNLOADED_EXCEPTION.create();
        }
        ArrayList _snowman5 = Lists.newArrayList();
        ArrayList _snowman6 = Lists.newArrayList();
        ArrayList _snowman7 = Lists.newArrayList();
        LinkedList _snowman8 = Lists.newLinkedList();
        BlockPos _snowman9 = new BlockPos(_snowman.minX - blockBox.minX, _snowman.minY - blockBox.minY, _snowman.minZ - blockBox.minZ);
        for (int i = blockBox.minZ; i <= blockBox.maxZ; ++i) {
            for (_snowman = blockBox.minY; _snowman <= blockBox.maxY; ++_snowman) {
                for (_snowman = blockBox.minX; _snowman <= blockBox.maxX; ++_snowman) {
                    Object object = new BlockPos(_snowman, _snowman, i);
                    object = ((BlockPos)object).add(_snowman9);
                    _snowman = new CachedBlockPosition(_snowman4, (BlockPos)object, false);
                    BlockState _snowman10 = ((CachedBlockPosition)_snowman).getBlockState();
                    if (!filter.test((CachedBlockPosition)_snowman)) continue;
                    BlockEntity _snowman11 = _snowman4.getBlockEntity((BlockPos)object);
                    if (_snowman11 != null) {
                        CompoundTag compoundTag = _snowman11.toTag(new CompoundTag());
                        _snowman6.add(new BlockInfo((BlockPos)object, _snowman10, compoundTag));
                        _snowman8.addLast(object);
                        continue;
                    }
                    if (_snowman10.isOpaqueFullCube(_snowman4, (BlockPos)object) || _snowman10.isFullCube(_snowman4, (BlockPos)object)) {
                        _snowman5.add(new BlockInfo((BlockPos)object, _snowman10, null));
                        _snowman8.addLast(object);
                        continue;
                    }
                    _snowman7.add(new BlockInfo((BlockPos)object, _snowman10, null));
                    _snowman8.addFirst(object);
                }
            }
        }
        if (mode == Mode.MOVE) {
            for (BlockPos blockPos : _snowman8) {
                BlockEntity blockEntity = _snowman4.getBlockEntity(blockPos);
                Clearable.clear(blockEntity);
                _snowman4.setBlockState(blockPos, Blocks.BARRIER.getDefaultState(), 2);
            }
            for (BlockPos blockPos : _snowman8) {
                _snowman4.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
            }
        }
        ArrayList arrayList = Lists.newArrayList();
        arrayList.addAll(_snowman5);
        arrayList.addAll(_snowman6);
        arrayList.addAll(_snowman7);
        List _snowman12 = Lists.reverse((List)arrayList);
        for (Object object : _snowman12) {
            object = _snowman4.getBlockEntity(((BlockInfo)object).pos);
            Clearable.clear(object);
            _snowman4.setBlockState(((BlockInfo)object).pos, Blocks.BARRIER.getDefaultState(), 2);
        }
        int n = 0;
        for (Object object : arrayList) {
            if (!_snowman4.setBlockState(((BlockInfo)object).pos, ((BlockInfo)object).state, 2)) continue;
            ++n;
        }
        for (Object object : _snowman6) {
            _snowman = _snowman4.getBlockEntity(((BlockInfo)object).pos);
            if (((BlockInfo)object).blockEntityTag != null && _snowman != null) {
                ((BlockInfo)object).blockEntityTag.putInt("x", ((BlockInfo)object).pos.getX());
                ((BlockInfo)object).blockEntityTag.putInt("y", ((BlockInfo)object).pos.getY());
                ((BlockInfo)object).blockEntityTag.putInt("z", ((BlockInfo)object).pos.getZ());
                ((BlockEntity)_snowman).fromTag(((BlockInfo)object).state, ((BlockInfo)object).blockEntityTag);
                ((BlockEntity)_snowman).markDirty();
            }
            _snowman4.setBlockState(((BlockInfo)object).pos, ((BlockInfo)object).state, 2);
        }
        for (Object object : _snowman12) {
            _snowman4.updateNeighbors(((BlockInfo)object).pos, ((BlockInfo)object).state.getBlock());
        }
        ((ServerTickScheduler)_snowman4.getBlockTickScheduler()).copyScheduledTicks(blockBox, _snowman9);
        if (n == 0) {
            throw FAILED_EXCEPTION.create();
        }
        source.sendFeedback(new TranslatableText("commands.clone.success", n), true);
        return n;
    }

    static class BlockInfo {
        public final BlockPos pos;
        public final BlockState state;
        @Nullable
        public final CompoundTag blockEntityTag;

        public BlockInfo(BlockPos pos, BlockState state, @Nullable CompoundTag blockEntityTag) {
            this.pos = pos;
            this.state = state;
            this.blockEntityTag = blockEntityTag;
        }
    }

    static enum Mode {
        FORCE(true),
        MOVE(true),
        NORMAL(false);

        private final boolean allowsOverlap;

        private Mode(boolean allowsOverlap) {
            this.allowsOverlap = allowsOverlap;
        }

        public boolean allowsOverlap() {
            return this.allowsOverlap;
        }
    }
}

