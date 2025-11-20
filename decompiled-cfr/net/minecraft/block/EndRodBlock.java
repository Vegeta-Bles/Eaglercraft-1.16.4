/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class EndRodBlock
extends FacingBlock {
    protected static final VoxelShape Y_SHAPE = Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
    protected static final VoxelShape Z_SHAPE = Block.createCuboidShape(6.0, 6.0, 0.0, 10.0, 10.0, 16.0);
    protected static final VoxelShape X_SHAPE = Block.createCuboidShape(0.0, 6.0, 6.0, 16.0, 10.0, 10.0);

    protected EndRodBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.UP));
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return (BlockState)state.with(FACING, mirror.apply(state.get(FACING)));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING).getAxis()) {
            default: {
                return X_SHAPE;
            }
            case Z: {
                return Z_SHAPE;
            }
            case Y: 
        }
        return Y_SHAPE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getSide();
        BlockState _snowman2 = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(direction.getOpposite()));
        if (_snowman2.isOf(this) && _snowman2.get(FACING) == direction) {
            return (BlockState)this.getDefaultState().with(FACING, direction.getOpposite());
        }
        return (BlockState)this.getDefaultState().with(FACING, direction);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        Direction direction = state.get(FACING);
        double _snowman2 = (double)pos.getX() + 0.55 - (double)(random.nextFloat() * 0.1f);
        double _snowman3 = (double)pos.getY() + 0.55 - (double)(random.nextFloat() * 0.1f);
        double _snowman4 = (double)pos.getZ() + 0.55 - (double)(random.nextFloat() * 0.1f);
        double _snowman5 = 0.4f - (random.nextFloat() + random.nextFloat()) * 0.4f;
        if (random.nextInt(5) == 0) {
            world.addParticle(ParticleTypes.END_ROD, _snowman2 + (double)direction.getOffsetX() * _snowman5, _snowman3 + (double)direction.getOffsetY() * _snowman5, _snowman4 + (double)direction.getOffsetZ() * _snowman5, random.nextGaussian() * 0.005, random.nextGaussian() * 0.005, random.nextGaussian() * 0.005);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.NORMAL;
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
}

