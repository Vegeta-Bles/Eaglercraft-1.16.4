/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SmokerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SmokerBlock
extends AbstractFurnaceBlock {
    protected SmokerBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new SmokerBlockEntity();
    }

    @Override
    protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof SmokerBlockEntity) {
            player.openHandledScreen((NamedScreenHandlerFactory)((Object)blockEntity));
            player.incrementStat(Stats.INTERACT_WITH_SMOKER);
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!state.get(LIT).booleanValue()) {
            return;
        }
        double d = (double)pos.getX() + 0.5;
        _snowman = pos.getY();
        _snowman = (double)pos.getZ() + 0.5;
        if (random.nextDouble() < 0.1) {
            world.playSound(d, _snowman, _snowman, SoundEvents.BLOCK_SMOKER_SMOKE, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
        }
        world.addParticle(ParticleTypes.SMOKE, d, _snowman + 1.1, _snowman, 0.0, 0.0, 0.0);
    }
}

