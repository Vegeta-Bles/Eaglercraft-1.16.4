/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block;

import java.util.Optional;
import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SoulFireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.dimension.AreaHelper;

public abstract class AbstractFireBlock
extends Block {
    private final float damage;
    protected static final VoxelShape BASE_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);

    public AbstractFireBlock(AbstractBlock.Settings settings, float damage) {
        super(settings);
        this.damage = damage;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return AbstractFireBlock.getState(ctx.getWorld(), ctx.getBlockPos());
    }

    public static BlockState getState(BlockView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState _snowman2 = world.getBlockState(blockPos);
        if (SoulFireBlock.isSoulBase(_snowman2.getBlock())) {
            return Blocks.SOUL_FIRE.getDefaultState();
        }
        return ((FireBlock)Blocks.FIRE).getStateForPosition(world, pos);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BASE_SHAPE;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        block12: {
            double d;
            int n;
            block11: {
                BlockState blockState;
                if (random.nextInt(24) == 0) {
                    world.playSound((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                }
                if (!this.isFlammable(blockState = world.getBlockState(_snowman = pos.down())) && !blockState.isSideSolidFullSquare(world, _snowman, Direction.UP)) break block11;
                for (int i = 0; i < 3; ++i) {
                    double d2 = (double)pos.getX() + random.nextDouble();
                    _snowman = (double)pos.getY() + random.nextDouble() * 0.5 + 0.5;
                    _snowman = (double)pos.getZ() + random.nextDouble();
                    world.addParticle(ParticleTypes.LARGE_SMOKE, d2, _snowman, _snowman, 0.0, 0.0, 0.0);
                }
                break block12;
            }
            if (this.isFlammable(world.getBlockState(pos.west()))) {
                for (n = 0; n < 2; ++n) {
                    d = (double)pos.getX() + random.nextDouble() * (double)0.1f;
                    _snowman = (double)pos.getY() + random.nextDouble();
                    _snowman = (double)pos.getZ() + random.nextDouble();
                    world.addParticle(ParticleTypes.LARGE_SMOKE, d, _snowman, _snowman, 0.0, 0.0, 0.0);
                }
            }
            if (this.isFlammable(world.getBlockState(pos.east()))) {
                for (n = 0; n < 2; ++n) {
                    d = (double)(pos.getX() + 1) - random.nextDouble() * (double)0.1f;
                    _snowman = (double)pos.getY() + random.nextDouble();
                    _snowman = (double)pos.getZ() + random.nextDouble();
                    world.addParticle(ParticleTypes.LARGE_SMOKE, d, _snowman, _snowman, 0.0, 0.0, 0.0);
                }
            }
            if (this.isFlammable(world.getBlockState(pos.north()))) {
                for (n = 0; n < 2; ++n) {
                    d = (double)pos.getX() + random.nextDouble();
                    _snowman = (double)pos.getY() + random.nextDouble();
                    _snowman = (double)pos.getZ() + random.nextDouble() * (double)0.1f;
                    world.addParticle(ParticleTypes.LARGE_SMOKE, d, _snowman, _snowman, 0.0, 0.0, 0.0);
                }
            }
            if (this.isFlammable(world.getBlockState(pos.south()))) {
                for (n = 0; n < 2; ++n) {
                    d = (double)pos.getX() + random.nextDouble();
                    _snowman = (double)pos.getY() + random.nextDouble();
                    _snowman = (double)(pos.getZ() + 1) - random.nextDouble() * (double)0.1f;
                    world.addParticle(ParticleTypes.LARGE_SMOKE, d, _snowman, _snowman, 0.0, 0.0, 0.0);
                }
            }
            if (!this.isFlammable(world.getBlockState(pos.up()))) break block12;
            for (n = 0; n < 2; ++n) {
                d = (double)pos.getX() + random.nextDouble();
                _snowman = (double)(pos.getY() + 1) - random.nextDouble() * (double)0.1f;
                _snowman = (double)pos.getZ() + random.nextDouble();
                world.addParticle(ParticleTypes.LARGE_SMOKE, d, _snowman, _snowman, 0.0, 0.0, 0.0);
            }
        }
    }

    protected abstract boolean isFlammable(BlockState var1);

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!entity.isFireImmune()) {
            entity.setFireTicks(entity.getFireTicks() + 1);
            if (entity.getFireTicks() == 0) {
                entity.setOnFireFor(8);
            }
            entity.damage(DamageSource.IN_FIRE, this.damage);
        }
        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        Optional<AreaHelper> optional;
        if (oldState.isOf(state.getBlock())) {
            return;
        }
        if (AbstractFireBlock.method_30366(world) && (optional = AreaHelper.method_30485(world, pos, Direction.Axis.X)).isPresent()) {
            optional.get().createPortal();
            return;
        }
        if (!state.canPlaceAt(world, pos)) {
            world.removeBlock(pos, false);
        }
    }

    private static boolean method_30366(World world) {
        return world.getRegistryKey() == World.OVERWORLD || world.getRegistryKey() == World.NETHER;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient()) {
            world.syncWorldEvent(null, 1009, pos, 0);
        }
    }

    public static boolean method_30032(World world, BlockPos blockPos, Direction direction) {
        BlockState blockState = world.getBlockState(blockPos);
        if (!blockState.isAir()) {
            return false;
        }
        return AbstractFireBlock.getState(world, blockPos).canPlaceAt(world, blockPos) || AbstractFireBlock.method_30033(world, blockPos, direction);
    }

    private static boolean method_30033(World world2, BlockPos blockPos, Direction direction) {
        World world2;
        if (!AbstractFireBlock.method_30366(world2)) {
            return false;
        }
        BlockPos.Mutable mutable = blockPos.mutableCopy();
        boolean _snowman2 = false;
        for (Direction direction2 : Direction.values()) {
            if (!world2.getBlockState(mutable.set(blockPos).move(direction2)).isOf(Blocks.OBSIDIAN)) continue;
            _snowman2 = true;
            break;
        }
        return _snowman2 && AreaHelper.method_30485(world2, blockPos, direction.rotateYCounterclockwise().getAxis()).isPresent();
    }
}

