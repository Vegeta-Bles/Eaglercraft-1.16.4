/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChorusPlantBlock;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class ChorusFlowerBlock
extends Block {
    public static final IntProperty AGE = Properties.AGE_5;
    private final ChorusPlantBlock plantBlock;

    protected ChorusFlowerBlock(ChorusPlantBlock plantBlock, AbstractBlock.Settings settings) {
        super(settings);
        this.plantBlock = plantBlock;
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(AGE, 0));
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        }
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < 5;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int n;
        BlockPos blockPos = pos.up();
        if (!world.isAir(blockPos) || blockPos.getY() >= 256) {
            return;
        }
        int _snowman2 = state.get(AGE);
        if (_snowman2 >= 5) {
            return;
        }
        boolean _snowman3 = false;
        boolean _snowman4 = false;
        BlockState _snowman5 = world.getBlockState(pos.down());
        Block _snowman6 = _snowman5.getBlock();
        if (_snowman6 == Blocks.END_STONE) {
            _snowman3 = true;
        } else if (_snowman6 == this.plantBlock) {
            n = 1;
            for (_snowman8 = 0; _snowman8 < 4; ++_snowman8) {
                Block block = world.getBlockState(pos.down(n + 1)).getBlock();
                if (block == this.plantBlock) {
                    ++n;
                    continue;
                }
                if (block != Blocks.END_STONE) break;
                _snowman4 = true;
                break;
            }
            if (n < 2 || n <= random.nextInt(_snowman4 ? 5 : 4)) {
                _snowman3 = true;
            }
        } else if (_snowman5.isAir()) {
            _snowman3 = true;
        }
        if (_snowman3 && ChorusFlowerBlock.isSurroundedByAir(world, blockPos, null) && world.isAir(pos.up(2))) {
            world.setBlockState(pos, this.plantBlock.withConnectionProperties(world, pos), 2);
            this.grow(world, blockPos, _snowman2);
        } else if (_snowman2 < 4) {
            int _snowman8;
            n = random.nextInt(4);
            if (_snowman4) {
                ++n;
            }
            _snowman8 = 0;
            for (_snowman = 0; _snowman < n; ++_snowman) {
                Direction direction = Direction.Type.HORIZONTAL.random(random);
                BlockPos _snowman7 = pos.offset(direction);
                if (!world.isAir(_snowman7) || !world.isAir(_snowman7.down()) || !ChorusFlowerBlock.isSurroundedByAir(world, _snowman7, direction.getOpposite())) continue;
                this.grow(world, _snowman7, _snowman2 + 1);
                _snowman8 = 1;
            }
            if (_snowman8 != 0) {
                world.setBlockState(pos, this.plantBlock.withConnectionProperties(world, pos), 2);
            } else {
                this.die(world, pos);
            }
        } else {
            this.die(world, pos);
        }
    }

    private void grow(World world, BlockPos pos, int age) {
        world.setBlockState(pos, (BlockState)this.getDefaultState().with(AGE, age), 2);
        world.syncWorldEvent(1033, pos, 0);
    }

    private void die(World world, BlockPos pos) {
        world.setBlockState(pos, (BlockState)this.getDefaultState().with(AGE, 5), 2);
        world.syncWorldEvent(1034, pos, 0);
    }

    private static boolean isSurroundedByAir(WorldView world, BlockPos pos, @Nullable Direction exceptDirection) {
        for (Direction direction : Direction.Type.HORIZONTAL) {
            if (direction == exceptDirection || world.isAir(pos.offset(direction))) continue;
            return false;
        }
        return true;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (direction != Direction.UP && !state.canPlaceAt(world, pos)) {
            world.getBlockTickScheduler().schedule(pos, this, 1);
        }
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        if (blockState.getBlock() == this.plantBlock || blockState.isOf(Blocks.END_STONE)) {
            return true;
        }
        if (!blockState.isAir()) {
            return false;
        }
        boolean _snowman2 = false;
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockState blockState2 = world.getBlockState(pos.offset(direction));
            if (blockState2.isOf(this.plantBlock)) {
                if (_snowman2) {
                    return false;
                }
                _snowman2 = true;
                continue;
            }
            if (blockState2.isAir()) continue;
            return false;
        }
        return _snowman2;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    public static void generate(WorldAccess world, BlockPos pos, Random random, int size) {
        world.setBlockState(pos, ((ChorusPlantBlock)Blocks.CHORUS_PLANT).withConnectionProperties(world, pos), 2);
        ChorusFlowerBlock.generate(world, pos, random, pos, size, 0);
    }

    private static void generate(WorldAccess world, BlockPos pos, Random random, BlockPos rootPos, int size, int layer) {
        int _snowman4;
        ChorusPlantBlock chorusPlantBlock = (ChorusPlantBlock)Blocks.CHORUS_PLANT;
        int _snowman2 = random.nextInt(4) + 1;
        if (layer == 0) {
            ++_snowman2;
        }
        for (_snowman4 = 0; _snowman4 < _snowman2; ++_snowman4) {
            BlockPos blockPos = pos.up(_snowman4 + 1);
            if (!ChorusFlowerBlock.isSurroundedByAir(world, blockPos, null)) {
                return;
            }
            world.setBlockState(blockPos, chorusPlantBlock.withConnectionProperties(world, blockPos), 2);
            world.setBlockState(blockPos.down(), chorusPlantBlock.withConnectionProperties(world, blockPos.down()), 2);
        }
        _snowman4 = 0;
        if (layer < 4) {
            _snowman = random.nextInt(4);
            if (layer == 0) {
                ++_snowman;
            }
            for (_snowman = 0; _snowman < _snowman; ++_snowman) {
                Direction direction = Direction.Type.HORIZONTAL.random(random);
                BlockPos _snowman3 = pos.up(_snowman2).offset(direction);
                if (Math.abs(_snowman3.getX() - rootPos.getX()) >= size || Math.abs(_snowman3.getZ() - rootPos.getZ()) >= size || !world.isAir(_snowman3) || !world.isAir(_snowman3.down()) || !ChorusFlowerBlock.isSurroundedByAir(world, _snowman3, direction.getOpposite())) continue;
                _snowman4 = 1;
                world.setBlockState(_snowman3, chorusPlantBlock.withConnectionProperties(world, _snowman3), 2);
                world.setBlockState(_snowman3.offset(direction.getOpposite()), chorusPlantBlock.withConnectionProperties(world, _snowman3.offset(direction.getOpposite())), 2);
                ChorusFlowerBlock.generate(world, _snowman3, random, rootPos, size, layer + 1);
            }
        }
        if (_snowman4 == 0) {
            world.setBlockState(pos.up(_snowman2), (BlockState)Blocks.CHORUS_FLOWER.getDefaultState().with(AGE, 5), 2);
        }
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (projectile.getType().isIn(EntityTypeTags.IMPACT_PROJECTILES)) {
            BlockPos blockPos = hit.getBlockPos();
            world.breakBlock(blockPos, true, projectile);
        }
    }
}

