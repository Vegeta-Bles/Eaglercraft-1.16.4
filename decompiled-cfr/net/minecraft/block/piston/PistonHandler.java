/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.block.piston;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class PistonHandler {
    private final World world;
    private final BlockPos posFrom;
    private final boolean retracted;
    private final BlockPos posTo;
    private final Direction motionDirection;
    private final List<BlockPos> movedBlocks = Lists.newArrayList();
    private final List<BlockPos> brokenBlocks = Lists.newArrayList();
    private final Direction pistonDirection;

    public PistonHandler(World world, BlockPos pos, Direction dir, boolean retracted) {
        this.world = world;
        this.posFrom = pos;
        this.pistonDirection = dir;
        this.retracted = retracted;
        if (retracted) {
            this.motionDirection = dir;
            this.posTo = pos.offset(dir);
        } else {
            this.motionDirection = dir.getOpposite();
            this.posTo = pos.offset(dir, 2);
        }
    }

    public boolean calculatePush() {
        this.movedBlocks.clear();
        this.brokenBlocks.clear();
        BlockState blockState = this.world.getBlockState(this.posTo);
        if (!PistonBlock.isMovable(blockState, this.world, this.posTo, this.motionDirection, false, this.pistonDirection)) {
            if (this.retracted && blockState.getPistonBehavior() == PistonBehavior.DESTROY) {
                this.brokenBlocks.add(this.posTo);
                return true;
            }
            return false;
        }
        if (!this.tryMove(this.posTo, this.motionDirection)) {
            return false;
        }
        for (int i = 0; i < this.movedBlocks.size(); ++i) {
            BlockPos blockPos = this.movedBlocks.get(i);
            if (!PistonHandler.isBlockSticky(this.world.getBlockState(blockPos).getBlock()) || this.canMoveAdjacentBlock(blockPos)) continue;
            return false;
        }
        return true;
    }

    private static boolean isBlockSticky(Block block) {
        return block == Blocks.SLIME_BLOCK || block == Blocks.HONEY_BLOCK;
    }

    private static boolean isAdjacentBlockStuck(Block block, Block block2) {
        if (block == Blocks.HONEY_BLOCK && block2 == Blocks.SLIME_BLOCK) {
            return false;
        }
        if (block == Blocks.SLIME_BLOCK && block2 == Blocks.HONEY_BLOCK) {
            return false;
        }
        return PistonHandler.isBlockSticky(block) || PistonHandler.isBlockSticky(block2);
    }

    private boolean tryMove(BlockPos pos, Direction dir) {
        BlockState _snowman5 = this.world.getBlockState(pos);
        Block _snowman2 = _snowman5.getBlock();
        if (_snowman5.isAir()) {
            return true;
        }
        if (!PistonBlock.isMovable(_snowman5, this.world, pos, this.motionDirection, false, dir)) {
            return true;
        }
        if (pos.equals(this.posFrom)) {
            return true;
        }
        if (this.movedBlocks.contains(pos)) {
            return true;
        }
        int _snowman3 = 1;
        if (_snowman3 + this.movedBlocks.size() > 12) {
            return false;
        }
        while (PistonHandler.isBlockSticky(_snowman2)) {
            BlockPos blockPos = pos.offset(this.motionDirection.getOpposite(), _snowman3);
            Block _snowman4 = _snowman2;
            _snowman5 = this.world.getBlockState(blockPos);
            _snowman2 = _snowman5.getBlock();
            if (_snowman5.isAir() || !PistonHandler.isAdjacentBlockStuck(_snowman4, _snowman2) || !PistonBlock.isMovable(_snowman5, this.world, blockPos, this.motionDirection, false, this.motionDirection.getOpposite()) || blockPos.equals(this.posFrom)) break;
            if (++_snowman3 + this.movedBlocks.size() <= 12) continue;
            return false;
        }
        int n = 0;
        for (_snowman = _snowman3 - 1; _snowman >= 0; --_snowman) {
            this.movedBlocks.add(pos.offset(this.motionDirection.getOpposite(), _snowman));
            ++n;
        }
        _snowman = 1;
        while (true) {
            BlockPos blockPos;
            if ((_snowman = this.movedBlocks.indexOf(blockPos = pos.offset(this.motionDirection, _snowman))) > -1) {
                this.setMovedBlocks(n, _snowman);
                for (_snowman = 0; _snowman <= _snowman + n; ++_snowman) {
                    BlockPos blockPos2 = this.movedBlocks.get(_snowman);
                    if (!PistonHandler.isBlockSticky(this.world.getBlockState(blockPos2).getBlock()) || this.canMoveAdjacentBlock(blockPos2)) continue;
                    return false;
                }
                return true;
            }
            _snowman5 = this.world.getBlockState(blockPos);
            if (_snowman5.isAir()) {
                return true;
            }
            if (!PistonBlock.isMovable(_snowman5, this.world, blockPos, this.motionDirection, true, this.motionDirection) || blockPos.equals(this.posFrom)) {
                return false;
            }
            if (_snowman5.getPistonBehavior() == PistonBehavior.DESTROY) {
                this.brokenBlocks.add(blockPos);
                return true;
            }
            if (this.movedBlocks.size() >= 12) {
                return false;
            }
            this.movedBlocks.add(blockPos);
            ++n;
            ++_snowman;
        }
    }

    private void setMovedBlocks(int from, int to) {
        ArrayList arrayList = Lists.newArrayList();
        _snowman = Lists.newArrayList();
        _snowman = Lists.newArrayList();
        arrayList.addAll(this.movedBlocks.subList(0, to));
        _snowman.addAll(this.movedBlocks.subList(this.movedBlocks.size() - from, this.movedBlocks.size()));
        _snowman.addAll(this.movedBlocks.subList(to, this.movedBlocks.size() - from));
        this.movedBlocks.clear();
        this.movedBlocks.addAll(arrayList);
        this.movedBlocks.addAll(_snowman);
        this.movedBlocks.addAll(_snowman);
    }

    private boolean canMoveAdjacentBlock(BlockPos pos) {
        BlockState blockState = this.world.getBlockState(pos);
        for (Direction direction : Direction.values()) {
            if (direction.getAxis() == this.motionDirection.getAxis() || !PistonHandler.isAdjacentBlockStuck((_snowman = this.world.getBlockState(_snowman = pos.offset(direction))).getBlock(), blockState.getBlock()) || this.tryMove(_snowman, direction)) continue;
            return false;
        }
        return true;
    }

    public List<BlockPos> getMovedBlocks() {
        return this.movedBlocks;
    }

    public List<BlockPos> getBrokenBlocks() {
        return this.brokenBlocks;
    }
}

