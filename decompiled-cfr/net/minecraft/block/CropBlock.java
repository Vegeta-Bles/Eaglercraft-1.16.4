/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.State;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class CropBlock
extends PlantBlock
implements Fertilizable {
    public static final IntProperty AGE = Properties.AGE_7;
    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 10.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)};

    protected CropBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(this.getAgeProperty(), 0));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[state.get(this.getAgeProperty())];
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.FARMLAND);
    }

    public IntProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return 7;
    }

    protected int getAge(BlockState state) {
        return state.get(this.getAgeProperty());
    }

    public BlockState withAge(int age) {
        return (BlockState)this.getDefaultState().with(this.getAgeProperty(), age);
    }

    public boolean isMature(BlockState state) {
        return state.get(this.getAgeProperty()) >= this.getMaxAge();
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return !this.isMature(state);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int n;
        if (world.getBaseLightLevel(pos, 0) >= 9 && (n = this.getAge(state)) < this.getMaxAge() && random.nextInt((int)(25.0f / (_snowman = CropBlock.getAvailableMoisture(this, world, pos))) + 1) == 0) {
            world.setBlockState(pos, this.withAge(n + 1), 2);
        }
    }

    public void applyGrowth(World world, BlockPos pos, BlockState state) {
        int n = this.getAge(state) + this.getGrowthAmount(world);
        if (n > (_snowman = this.getMaxAge())) {
            n = _snowman;
        }
        world.setBlockState(pos, this.withAge(n), 2);
    }

    protected int getGrowthAmount(World world) {
        return MathHelper.nextInt(world.random, 2, 5);
    }

    protected static float getAvailableMoisture(Block block, BlockView world, BlockPos pos) {
        Object _snowman3;
        float f = 1.0f;
        BlockPos _snowman2 = pos.down();
        for (int i = -1; i <= 1; ++i) {
            for (_snowman = -1; _snowman <= 1; ++_snowman) {
                float f2 = 0.0f;
                _snowman3 = world.getBlockState(_snowman2.add(i, 0, _snowman));
                if (((AbstractBlock.AbstractBlockState)_snowman3).isOf(Blocks.FARMLAND)) {
                    f2 = 1.0f;
                    if (((State)_snowman3).get(FarmlandBlock.MOISTURE) > 0) {
                        f2 = 3.0f;
                    }
                }
                if (i != 0 || _snowman != 0) {
                    f2 /= 4.0f;
                }
                f += f2;
            }
        }
        BlockPos blockPos = pos.north();
        _snowman = pos.south();
        _snowman = pos.west();
        _snowman3 = pos.east();
        boolean _snowman4 = block == world.getBlockState(_snowman).getBlock() || block == world.getBlockState((BlockPos)_snowman3).getBlock();
        boolean bl = _snowman = block == world.getBlockState(blockPos).getBlock() || block == world.getBlockState(_snowman).getBlock();
        if (_snowman4 && _snowman) {
            f /= 2.0f;
        } else {
            boolean bl2 = _snowman = block == world.getBlockState(_snowman.north()).getBlock() || block == world.getBlockState(((BlockPos)_snowman3).north()).getBlock() || block == world.getBlockState(((BlockPos)_snowman3).south()).getBlock() || block == world.getBlockState(_snowman.south()).getBlock();
            if (_snowman) {
                f /= 2.0f;
            }
        }
        return f;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return (world.getBaseLightLevel(pos, 0) >= 8 || world.isSkyVisible(pos)) && super.canPlaceAt(state, world, pos);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof RavagerEntity && world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            world.breakBlock(pos, true, entity);
        }
        super.onEntityCollision(state, world, pos, entity);
    }

    protected ItemConvertible getSeedsItem() {
        return Items.WHEAT_SEEDS;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(this.getSeedsItem());
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return !this.isMature(state);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        this.applyGrowth(world, pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}

