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
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorContext;

public class WaterLakeDecorator
extends Decorator<ChanceDecoratorConfig> {
    public WaterLakeDecorator(Codec<ChanceDecoratorConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(DecoratorContext decoratorContext, Random random, ChanceDecoratorConfig chanceDecoratorConfig, BlockPos blockPos) {
        if (random.nextInt(chanceDecoratorConfig.chance) == 0) {
            int n = random.nextInt(16) + blockPos.getX();
            _snowman = random.nextInt(16) + blockPos.getZ();
            _snowman = random.nextInt(decoratorContext.getMaxY());
            return Stream.of(new BlockPos(n, _snowman, _snowman));
        }
        return Stream.empty();
    }
}

