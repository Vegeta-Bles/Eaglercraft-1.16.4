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
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorContext;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;

public class EndGatewayDecorator
extends Decorator<NopeDecoratorConfig> {
    public EndGatewayDecorator(Codec<NopeDecoratorConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(DecoratorContext decoratorContext, Random random, NopeDecoratorConfig nopeDecoratorConfig, BlockPos blockPos) {
        if (random.nextInt(700) == 0 && (_snowman = decoratorContext.getTopY(Heightmap.Type.MOTION_BLOCKING, _snowman = random.nextInt(16) + blockPos.getX(), _snowman = random.nextInt(16) + blockPos.getZ())) > 0) {
            int n = _snowman + 3 + random.nextInt(7);
            return Stream.of(new BlockPos(_snowman, n, _snowman));
        }
        return Stream.empty();
    }
}

