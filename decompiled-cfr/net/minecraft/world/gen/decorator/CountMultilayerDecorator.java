/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.decorator;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorContext;

public class CountMultilayerDecorator
extends Decorator<CountConfig> {
    public CountMultilayerDecorator(Codec<CountConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(DecoratorContext decoratorContext, Random random, CountConfig countConfig, BlockPos blockPos) {
        ArrayList arrayList = Lists.newArrayList();
        int _snowman2 = 0;
        do {
            boolean bl = false;
            for (int i = 0; i < countConfig.getCount().getValue(random); ++i) {
                _snowman = random.nextInt(16) + blockPos.getX();
                _snowman = CountMultilayerDecorator.findPos(decoratorContext, _snowman, _snowman = decoratorContext.getTopY(Heightmap.Type.MOTION_BLOCKING, _snowman, _snowman = random.nextInt(16) + blockPos.getZ()), _snowman, _snowman2);
                if (_snowman == Integer.MAX_VALUE) continue;
                arrayList.add(new BlockPos(_snowman, _snowman, _snowman));
                bl = true;
            }
            ++_snowman2;
        } while (bl);
        return arrayList.stream();
    }

    private static int findPos(DecoratorContext context, int x, int y, int z, int targetY) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);
        int _snowman2 = 0;
        BlockState _snowman3 = context.getBlockState(mutable);
        for (int i = y; i >= 1; --i) {
            mutable.setY(i - 1);
            BlockState blockState = context.getBlockState(mutable);
            if (!CountMultilayerDecorator.blocksSpawn(blockState) && CountMultilayerDecorator.blocksSpawn(_snowman3) && !blockState.isOf(Blocks.BEDROCK)) {
                if (_snowman2 == targetY) {
                    return mutable.getY() + 1;
                }
                ++_snowman2;
            }
            _snowman3 = blockState;
        }
        return Integer.MAX_VALUE;
    }

    private static boolean blocksSpawn(BlockState state) {
        return state.isAir() || state.isOf(Blocks.WATER) || state.isOf(Blocks.LAVA);
    }
}

