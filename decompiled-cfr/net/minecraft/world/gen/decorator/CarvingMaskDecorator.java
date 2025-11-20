/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.decorator.CarvingMaskDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorContext;

public class CarvingMaskDecorator
extends Decorator<CarvingMaskDecoratorConfig> {
    public CarvingMaskDecorator(Codec<CarvingMaskDecoratorConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(DecoratorContext decoratorContext, Random random, CarvingMaskDecoratorConfig carvingMaskDecoratorConfig, BlockPos blockPos) {
        ChunkPos chunkPos = new ChunkPos(blockPos);
        BitSet _snowman2 = decoratorContext.getOrCreateCarvingMask(chunkPos, carvingMaskDecoratorConfig.step);
        return IntStream.range(0, _snowman2.length()).filter(n -> _snowman2.get(n) && random.nextFloat() < carvingMaskDecoratorConfig.probability).mapToObj(n -> {
            _snowman = n & 0xF;
            _snowman = n >> 4 & 0xF;
            _snowman = n >> 8;
            return new BlockPos(chunkPos.getStartX() + _snowman, _snowman, chunkPos.getStartZ() + _snowman);
        });
    }
}

