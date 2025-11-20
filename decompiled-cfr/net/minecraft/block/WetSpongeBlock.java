/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class WetSpongeBlock
extends Block {
    protected WetSpongeBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (world.getDimension().isUltrawarm()) {
            world.setBlockState(pos, Blocks.SPONGE.getDefaultState(), 3);
            world.syncWorldEvent(2009, pos, 0);
            world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, (1.0f + world.getRandom().nextFloat() * 0.2f) * 0.7f);
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        Direction direction = Direction.random(random);
        if (direction == Direction.UP) {
            return;
        }
        BlockPos _snowman2 = pos.offset(direction);
        BlockState _snowman3 = world.getBlockState(_snowman2);
        if (state.isOpaque() && _snowman3.isSideSolidFullSquare(world, _snowman2, direction.getOpposite())) {
            return;
        }
        double _snowman4 = pos.getX();
        double _snowman5 = pos.getY();
        double _snowman6 = pos.getZ();
        if (direction == Direction.DOWN) {
            _snowman5 -= 0.05;
            _snowman4 += random.nextDouble();
            _snowman6 += random.nextDouble();
        } else {
            _snowman5 += random.nextDouble() * 0.8;
            if (direction.getAxis() == Direction.Axis.X) {
                _snowman6 += random.nextDouble();
                _snowman4 = direction == Direction.EAST ? (_snowman4 += 1.1) : (_snowman4 += 0.05);
            } else {
                _snowman4 += random.nextDouble();
                _snowman6 = direction == Direction.SOUTH ? (_snowman6 += 1.1) : (_snowman6 += 0.05);
            }
        }
        world.addParticle(ParticleTypes.DRIPPING_WATER, _snowman4, _snowman5, _snowman6, 0.0, 0.0, 0.0);
    }
}

