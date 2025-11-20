/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.item;

import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DeadCoralWallFanBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

public class BoneMealItem
extends Item {
    public BoneMealItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos _snowman2 = context.getBlockPos();
        BlockPos _snowman3 = _snowman2.offset(context.getSide());
        if (BoneMealItem.useOnFertilizable(context.getStack(), world, _snowman2)) {
            if (!world.isClient) {
                world.syncWorldEvent(2005, _snowman2, 0);
            }
            return ActionResult.success(world.isClient);
        }
        BlockState _snowman4 = world.getBlockState(_snowman2);
        boolean _snowman5 = _snowman4.isSideSolidFullSquare(world, _snowman2, context.getSide());
        if (_snowman5 && BoneMealItem.useOnGround(context.getStack(), world, _snowman3, context.getSide())) {
            if (!world.isClient) {
                world.syncWorldEvent(2005, _snowman3, 0);
            }
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    public static boolean useOnFertilizable(ItemStack stack, World world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.getBlock() instanceof Fertilizable && (_snowman = (Fertilizable)((Object)blockState.getBlock())).isFertilizable(world, pos, blockState, world.isClient)) {
            if (world instanceof ServerWorld) {
                if (_snowman.canGrow(world, world.random, pos, blockState)) {
                    _snowman.grow((ServerWorld)world, world.random, pos, blockState);
                }
                stack.decrement(1);
            }
            return true;
        }
        return false;
    }

    public static boolean useOnGround(ItemStack stack, World world, BlockPos blockPos, @Nullable Direction facing) {
        if (!world.getBlockState(blockPos).isOf(Blocks.WATER) || world.getFluidState(blockPos).getLevel() != 8) {
            return false;
        }
        if (!(world instanceof ServerWorld)) {
            return true;
        }
        block0: for (int i = 0; i < 128; ++i) {
            BlockPos blockPos2 = blockPos;
            BlockState _snowman2 = Blocks.SEAGRASS.getDefaultState();
            for (int j = 0; j < i / 16; ++j) {
                if (world.getBlockState(blockPos2 = blockPos2.add(RANDOM.nextInt(3) - 1, (RANDOM.nextInt(3) - 1) * RANDOM.nextInt(3) / 2, RANDOM.nextInt(3) - 1)).isFullCube(world, blockPos2)) continue block0;
            }
            Optional<RegistryKey<Biome>> _snowman3 = world.method_31081(blockPos2);
            if (Objects.equals(_snowman3, Optional.of(BiomeKeys.WARM_OCEAN)) || Objects.equals(_snowman3, Optional.of(BiomeKeys.DEEP_WARM_OCEAN))) {
                if (i == 0 && facing != null && facing.getAxis().isHorizontal()) {
                    _snowman2 = (BlockState)((Block)BlockTags.WALL_CORALS.getRandom(world.random)).getDefaultState().with(DeadCoralWallFanBlock.FACING, facing);
                } else if (RANDOM.nextInt(4) == 0) {
                    _snowman2 = ((Block)BlockTags.UNDERWATER_BONEMEALS.getRandom(RANDOM)).getDefaultState();
                }
            }
            if (_snowman2.getBlock().isIn(BlockTags.WALL_CORALS)) {
                for (int j = 0; !_snowman2.canPlaceAt(world, blockPos2) && j < 4; ++j) {
                    _snowman2 = (BlockState)_snowman2.with(DeadCoralWallFanBlock.FACING, Direction.Type.HORIZONTAL.random(RANDOM));
                }
            }
            if (!_snowman2.canPlaceAt(world, blockPos2)) continue;
            BlockState _snowman4 = world.getBlockState(blockPos2);
            if (_snowman4.isOf(Blocks.WATER) && world.getFluidState(blockPos2).getLevel() == 8) {
                world.setBlockState(blockPos2, _snowman2, 3);
                continue;
            }
            if (!_snowman4.isOf(Blocks.SEAGRASS) || RANDOM.nextInt(10) != 0) continue;
            ((Fertilizable)((Object)Blocks.SEAGRASS)).grow((ServerWorld)world, RANDOM, blockPos2, _snowman4);
        }
        stack.decrement(1);
        return true;
    }

    public static void createParticles(WorldAccess world, BlockPos pos, int count) {
        double _snowman3;
        BlockState blockState;
        if (count == 0) {
            count = 15;
        }
        if ((blockState = world.getBlockState(pos)).isAir()) {
            return;
        }
        double _snowman2 = 0.5;
        if (blockState.isOf(Blocks.WATER)) {
            count *= 3;
            _snowman3 = 1.0;
            _snowman2 = 3.0;
        } else if (blockState.isOpaqueFullCube(world, pos)) {
            pos = pos.up();
            count *= 3;
            _snowman2 = 3.0;
            _snowman3 = 1.0;
        } else {
            _snowman3 = blockState.getOutlineShape(world, pos).getMax(Direction.Axis.Y);
        }
        world.addParticle(ParticleTypes.HAPPY_VILLAGER, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, 0.0, 0.0, 0.0);
        for (int _snowman4 = 0; _snowman4 < count; ++_snowman4) {
            double d = RANDOM.nextGaussian() * 0.02;
            _snowman = RANDOM.nextGaussian() * 0.02;
            _snowman = RANDOM.nextGaussian() * 0.02;
            _snowman = 0.5 - _snowman2;
            _snowman = (double)pos.getX() + _snowman + RANDOM.nextDouble() * _snowman2 * 2.0;
            if (world.getBlockState(new BlockPos(_snowman, _snowman = (double)pos.getY() + RANDOM.nextDouble() * _snowman3, _snowman = (double)pos.getZ() + _snowman + RANDOM.nextDouble() * _snowman2 * 2.0).down()).isAir()) continue;
            world.addParticle(ParticleTypes.HAPPY_VILLAGER, _snowman, _snowman, _snowman, d, _snowman, _snowman);
        }
    }
}

