/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class EndGatewayBlock
extends BlockWithEntity {
    protected EndGatewayBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new EndGatewayBlockEntity();
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof EndGatewayBlockEntity)) {
            return;
        }
        int _snowman2 = ((EndGatewayBlockEntity)blockEntity).getDrawnSidesCount();
        for (int i = 0; i < _snowman2; ++i) {
            double d = (double)pos.getX() + random.nextDouble();
            _snowman = (double)pos.getY() + random.nextDouble();
            _snowman = (double)pos.getZ() + random.nextDouble();
            _snowman = (random.nextDouble() - 0.5) * 0.5;
            _snowman = (random.nextDouble() - 0.5) * 0.5;
            _snowman = (random.nextDouble() - 0.5) * 0.5;
            int _snowman3 = random.nextInt(2) * 2 - 1;
            if (random.nextBoolean()) {
                _snowman = (double)pos.getZ() + 0.5 + 0.25 * (double)_snowman3;
                _snowman = random.nextFloat() * 2.0f * (float)_snowman3;
            } else {
                d = (double)pos.getX() + 0.5 + 0.25 * (double)_snowman3;
                _snowman = random.nextFloat() * 2.0f * (float)_snowman3;
            }
            world.addParticle(ParticleTypes.PORTAL, d, _snowman, _snowman, _snowman, _snowman, _snowman);
        }
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canBucketPlace(BlockState state, Fluid fluid) {
        return false;
    }
}

