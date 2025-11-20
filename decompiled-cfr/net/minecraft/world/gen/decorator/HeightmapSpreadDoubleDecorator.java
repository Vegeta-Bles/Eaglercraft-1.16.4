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
import net.minecraft.world.gen.decorator.AbstractHeightmapDecorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.decorator.DecoratorContext;

public class HeightmapSpreadDoubleDecorator<DC extends DecoratorConfig>
extends AbstractHeightmapDecorator<DC> {
    public HeightmapSpreadDoubleDecorator(Codec<DC> codec) {
        super(codec);
    }

    @Override
    protected Heightmap.Type getHeightmapType(DC config) {
        return Heightmap.Type.MOTION_BLOCKING;
    }

    @Override
    public Stream<BlockPos> getPositions(DecoratorContext context, Random random, DC config, BlockPos pos) {
        int n = pos.getX();
        _snowman = pos.getZ();
        _snowman = context.getTopY(this.getHeightmapType(config), n, _snowman);
        if (_snowman == 0) {
            return Stream.of(new BlockPos[0]);
        }
        return Stream.of(new BlockPos(n, random.nextInt(_snowman * 2), _snowman));
    }
}

