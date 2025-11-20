/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class CryingObsidianBlock
extends Block {
    public CryingObsidianBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(5) != 0) {
            return;
        }
        Direction direction = Direction.random(random);
        if (direction == Direction.UP) {
            return;
        }
        BlockPos _snowman2 = pos.offset(direction);
        BlockState _snowman3 = world.getBlockState(_snowman2);
        if (state.isOpaque() && _snowman3.isSideSolidFullSquare(world, _snowman2, direction.getOpposite())) {
            return;
        }
        double _snowman4 = direction.getOffsetX() == 0 ? random.nextDouble() : 0.5 + (double)direction.getOffsetX() * 0.6;
        double _snowman5 = direction.getOffsetY() == 0 ? random.nextDouble() : 0.5 + (double)direction.getOffsetY() * 0.6;
        double _snowman6 = direction.getOffsetZ() == 0 ? random.nextDouble() : 0.5 + (double)direction.getOffsetZ() * 0.6;
        world.addParticle(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, (double)pos.getX() + _snowman4, (double)pos.getY() + _snowman5, (double)pos.getZ() + _snowman6, 0.0, 0.0, 0.0);
    }
}

