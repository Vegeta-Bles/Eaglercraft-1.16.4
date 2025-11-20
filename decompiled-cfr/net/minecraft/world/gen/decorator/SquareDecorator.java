/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import net.minecraft.world.gen.decorator.SimpleDecorator;

public class SquareDecorator
extends SimpleDecorator<NopeDecoratorConfig> {
    public SquareDecorator(Codec<NopeDecoratorConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(Random random, NopeDecoratorConfig nopeDecoratorConfig, BlockPos blockPos) {
        int n = random.nextInt(16) + blockPos.getX();
        _snowman = random.nextInt(16) + blockPos.getZ();
        _snowman = blockPos.getY();
        return Stream.of(new BlockPos(n, _snowman, _snowman));
    }
}

