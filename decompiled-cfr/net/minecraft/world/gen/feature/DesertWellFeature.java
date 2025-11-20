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
import net.minecraft.block.Blocks;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class DesertWellFeature
extends Feature<DefaultFeatureConfig> {
    private static final BlockStatePredicate CAN_GENERATE = BlockStatePredicate.forBlock(Blocks.SAND);
    private final BlockState slab = Blocks.SANDSTONE_SLAB.getDefaultState();
    private final BlockState wall = Blocks.SANDSTONE.getDefaultState();
    private final BlockState fluidInside = Blocks.WATER.getDefaultState();

    public DesertWellFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess2, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos2, DefaultFeatureConfig defaultFeatureConfig) {
        int n;
        StructureWorldAccess structureWorldAccess2;
        int n2;
        BlockPos blockPos2 = blockPos2.up();
        while (structureWorldAccess2.isAir(blockPos2) && blockPos2.getY() > 2) {
            blockPos2 = blockPos2.down();
        }
        if (!CAN_GENERATE.test(structureWorldAccess2.getBlockState(blockPos2))) {
            return false;
        }
        for (n2 = -2; n2 <= 2; ++n2) {
            for (_snowman = -2; _snowman <= 2; ++_snowman) {
                if (!structureWorldAccess2.isAir(blockPos2.add(n2, -1, _snowman)) || !structureWorldAccess2.isAir(blockPos2.add(n2, -2, _snowman))) continue;
                return false;
            }
        }
        for (n2 = -1; n2 <= 0; ++n2) {
            for (_snowman = -2; _snowman <= 2; ++_snowman) {
                for (_snowman = -2; _snowman <= 2; ++_snowman) {
                    structureWorldAccess2.setBlockState(blockPos2.add(_snowman, n2, _snowman), this.wall, 2);
                }
            }
        }
        structureWorldAccess2.setBlockState(blockPos2, this.fluidInside, 2);
        for (Direction direction : Direction.Type.HORIZONTAL) {
            structureWorldAccess2.setBlockState(blockPos2.offset(direction), this.fluidInside, 2);
        }
        for (n = -2; n <= 2; ++n) {
            for (_snowman = -2; _snowman <= 2; ++_snowman) {
                if (n != -2 && n != 2 && _snowman != -2 && _snowman != 2) continue;
                structureWorldAccess2.setBlockState(blockPos2.add(n, 1, _snowman), this.wall, 2);
            }
        }
        structureWorldAccess2.setBlockState(blockPos2.add(2, 1, 0), this.slab, 2);
        structureWorldAccess2.setBlockState(blockPos2.add(-2, 1, 0), this.slab, 2);
        structureWorldAccess2.setBlockState(blockPos2.add(0, 1, 2), this.slab, 2);
        structureWorldAccess2.setBlockState(blockPos2.add(0, 1, -2), this.slab, 2);
        for (n = -1; n <= 1; ++n) {
            for (_snowman = -1; _snowman <= 1; ++_snowman) {
                if (n == 0 && _snowman == 0) {
                    structureWorldAccess2.setBlockState(blockPos2.add(n, 4, _snowman), this.wall, 2);
                    continue;
                }
                structureWorldAccess2.setBlockState(blockPos2.add(n, 4, _snowman), this.slab, 2);
            }
        }
        for (n = 1; n <= 3; ++n) {
            structureWorldAccess2.setBlockState(blockPos2.add(-1, n, -1), this.wall, 2);
            structureWorldAccess2.setBlockState(blockPos2.add(-1, n, 1), this.wall, 2);
            structureWorldAccess2.setBlockState(blockPos2.add(1, n, -1), this.wall, 2);
            structureWorldAccess2.setBlockState(blockPos2.add(1, n, 1), this.wall, 2);
        }
        return true;
    }
}

