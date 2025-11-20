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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.decorator.SimpleDecorator;

public class FireDecorator
extends SimpleDecorator<CountConfig> {
    public FireDecorator(Codec<CountConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(Random random, CountConfig countConfig, BlockPos blockPos) {
        ArrayList arrayList = Lists.newArrayList();
        for (int i = 0; i < random.nextInt(random.nextInt(countConfig.getCount().getValue(random)) + 1) + 1; ++i) {
            _snowman = random.nextInt(16) + blockPos.getX();
            _snowman = random.nextInt(16) + blockPos.getZ();
            _snowman = random.nextInt(120) + 4;
            arrayList.add(new BlockPos(_snowman, _snowman, _snowman));
        }
        return arrayList.stream();
    }
}

