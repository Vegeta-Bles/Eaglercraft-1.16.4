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
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.CoralFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class CoralClawFeature
extends CoralFeature {
    public CoralClawFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected boolean spawnCoral(WorldAccess world, Random random, BlockPos pos, BlockState state) {
        if (!this.spawnCoralPiece(world, random, pos, state)) {
            return false;
        }
        Direction direction = Direction.Type.HORIZONTAL.random(random);
        int _snowman2 = random.nextInt(2) + 2;
        ArrayList _snowman3 = Lists.newArrayList((Object[])new Direction[]{direction, direction.rotateYClockwise(), direction.rotateYCounterclockwise()});
        Collections.shuffle(_snowman3, random);
        List _snowman4 = _snowman3.subList(0, _snowman2);
        block0: for (Direction direction2 : _snowman4) {
            int _snowman8;
            int _snowman6;
            BlockPos.Mutable mutable = pos.mutableCopy();
            int _snowman5 = random.nextInt(2) + 1;
            mutable.move(direction2);
            if (direction2 == direction) {
                Direction direction3 = direction;
                _snowman6 = random.nextInt(3) + 2;
            } else {
                mutable.move(Direction.UP);
                Direction[] _snowman7 = new Direction[]{direction2, Direction.UP};
                direction3 = Util.getRandom(_snowman7, random);
                _snowman6 = random.nextInt(3) + 3;
            }
            for (_snowman8 = 0; _snowman8 < _snowman5 && this.spawnCoralPiece(world, random, mutable, state); ++_snowman8) {
                mutable.move(direction3);
            }
            mutable.move(direction3.getOpposite());
            mutable.move(Direction.UP);
            for (_snowman8 = 0; _snowman8 < _snowman6; ++_snowman8) {
                mutable.move(direction);
                if (!this.spawnCoralPiece(world, random, mutable, state)) continue block0;
                if (!(random.nextFloat() < 0.25f)) continue;
                mutable.move(Direction.UP);
            }
        }
        return true;
    }
}

