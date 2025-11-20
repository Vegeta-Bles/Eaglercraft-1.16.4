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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class EndIslandFeature
extends Feature<DefaultFeatureConfig> {
    public EndIslandFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, DefaultFeatureConfig defaultFeatureConfig) {
        float f = random.nextInt(3) + 4;
        int _snowman2 = 0;
        while (f > 0.5f) {
            for (int i = MathHelper.floor(-f); i <= MathHelper.ceil(f); ++i) {
                for (_snowman = MathHelper.floor(-f); _snowman <= MathHelper.ceil(f); ++_snowman) {
                    if (!((float)(i * i + _snowman * _snowman) <= (f + 1.0f) * (f + 1.0f))) continue;
                    this.setBlockState(structureWorldAccess, blockPos.add(i, _snowman2, _snowman), Blocks.END_STONE.getDefaultState());
                }
            }
            f = (float)((double)f - ((double)random.nextInt(2) + 0.5));
            --_snowman2;
        }
        return true;
    }
}

