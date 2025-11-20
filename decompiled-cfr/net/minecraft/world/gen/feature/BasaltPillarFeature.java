/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class BasaltPillarFeature
extends Feature<DefaultFeatureConfig> {
    public BasaltPillarFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, DefaultFeatureConfig defaultFeatureConfig) {
        if (!structureWorldAccess.isAir(blockPos) || structureWorldAccess.isAir(blockPos.up())) {
            return false;
        }
        BlockPos.Mutable mutable = blockPos.mutableCopy();
        _snowman = blockPos.mutableCopy();
        boolean _snowman2 = true;
        boolean _snowman3 = true;
        boolean _snowman4 = true;
        boolean _snowman5 = true;
        while (structureWorldAccess.isAir(mutable)) {
            if (World.isOutOfBuildLimitVertically(mutable)) {
                return true;
            }
            structureWorldAccess.setBlockState(mutable, Blocks.BASALT.getDefaultState(), 2);
            _snowman2 = _snowman2 && this.stopOrPlaceBasalt(structureWorldAccess, random, _snowman.set(mutable, Direction.NORTH));
            _snowman3 = _snowman3 && this.stopOrPlaceBasalt(structureWorldAccess, random, _snowman.set(mutable, Direction.SOUTH));
            _snowman4 = _snowman4 && this.stopOrPlaceBasalt(structureWorldAccess, random, _snowman.set(mutable, Direction.WEST));
            _snowman5 = _snowman5 && this.stopOrPlaceBasalt(structureWorldAccess, random, _snowman.set(mutable, Direction.EAST));
            mutable.move(Direction.DOWN);
        }
        mutable.move(Direction.UP);
        this.tryPlaceBasalt(structureWorldAccess, random, _snowman.set(mutable, Direction.NORTH));
        this.tryPlaceBasalt(structureWorldAccess, random, _snowman.set(mutable, Direction.SOUTH));
        this.tryPlaceBasalt(structureWorldAccess, random, _snowman.set(mutable, Direction.WEST));
        this.tryPlaceBasalt(structureWorldAccess, random, _snowman.set(mutable, Direction.EAST));
        mutable.move(Direction.DOWN);
        _snowman = new BlockPos.Mutable();
        for (int i = -3; i < 4; ++i) {
            for (_snowman = -3; _snowman < 4; ++_snowman) {
                _snowman = MathHelper.abs(i) * MathHelper.abs(_snowman);
                if (random.nextInt(10) >= 10 - _snowman) continue;
                _snowman.set(mutable.add(i, 0, _snowman));
                _snowman = 3;
                while (structureWorldAccess.isAir(_snowman.set(_snowman, Direction.DOWN))) {
                    _snowman.move(Direction.DOWN);
                    if (--_snowman > 0) continue;
                }
                if (structureWorldAccess.isAir(_snowman.set(_snowman, Direction.DOWN))) continue;
                structureWorldAccess.setBlockState(_snowman, Blocks.BASALT.getDefaultState(), 2);
            }
        }
        return true;
    }

    private void tryPlaceBasalt(WorldAccess world, Random random, BlockPos pos) {
        if (random.nextBoolean()) {
            world.setBlockState(pos, Blocks.BASALT.getDefaultState(), 2);
        }
    }

    private boolean stopOrPlaceBasalt(WorldAccess world, Random random, BlockPos pos) {
        if (random.nextInt(10) != 0) {
            world.setBlockState(pos, Blocks.BASALT.getDefaultState(), 2);
            return true;
        }
        return false;
    }
}

