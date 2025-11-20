/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.CoralFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class CoralMushroomFeature
extends CoralFeature {
    public CoralMushroomFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected boolean spawnCoral(WorldAccess world, Random random, BlockPos pos, BlockState state) {
        int n = random.nextInt(3) + 3;
        _snowman = random.nextInt(3) + 3;
        _snowman = random.nextInt(3) + 3;
        _snowman = random.nextInt(3) + 1;
        BlockPos.Mutable _snowman2 = pos.mutableCopy();
        for (_snowman = 0; _snowman <= _snowman; ++_snowman) {
            for (_snowman = 0; _snowman <= n; ++_snowman) {
                for (_snowman = 0; _snowman <= _snowman; ++_snowman) {
                    _snowman2.set(_snowman + pos.getX(), _snowman + pos.getY(), _snowman + pos.getZ());
                    _snowman2.move(Direction.DOWN, _snowman);
                    if ((_snowman != 0 && _snowman != _snowman || _snowman != 0 && _snowman != n) && (_snowman != 0 && _snowman != _snowman || _snowman != 0 && _snowman != n) && (_snowman != 0 && _snowman != _snowman || _snowman != 0 && _snowman != _snowman) && (_snowman == 0 || _snowman == _snowman || _snowman == 0 || _snowman == n || _snowman == 0 || _snowman == _snowman) && !(random.nextFloat() < 0.1f) && this.spawnCoralPiece(world, random, _snowman2, state)) continue;
                }
            }
        }
        return true;
    }
}

