/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.CoralFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class CoralTreeFeature
extends CoralFeature {
    public CoralTreeFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected boolean spawnCoral(WorldAccess world, Random random, BlockPos pos, BlockState state) {
        BlockPos.Mutable mutable = pos.mutableCopy();
        int _snowman2 = random.nextInt(3) + 1;
        for (int i = 0; i < _snowman2; ++i) {
            if (!this.spawnCoralPiece(world, random, mutable, state)) {
                return true;
            }
            mutable.move(Direction.UP);
        }
        BlockPos _snowman3 = mutable.toImmutable();
        int _snowman4 = random.nextInt(3) + 2;
        ArrayList _snowman5 = Lists.newArrayList((Iterable)Direction.Type.HORIZONTAL);
        Collections.shuffle(_snowman5, random);
        List _snowman6 = _snowman5.subList(0, _snowman4);
        for (Direction direction : _snowman6) {
            mutable.set(_snowman3);
            mutable.move(direction);
            int n = random.nextInt(5) + 2;
            _snowman = 0;
            for (_snowman = 0; _snowman < n && this.spawnCoralPiece(world, random, mutable, state); ++_snowman) {
                mutable.move(Direction.UP);
                if (_snowman != 0 && (++_snowman < 2 || !(random.nextFloat() < 0.25f))) continue;
                mutable.move(direction);
                _snowman = 0;
            }
        }
        return true;
    }
}

