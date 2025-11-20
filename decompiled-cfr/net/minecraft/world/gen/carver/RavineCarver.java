/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.carver;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;

public class RavineCarver
extends Carver<ProbabilityConfig> {
    private final float[] heightToHorizontalStretchFactor = new float[1024];

    public RavineCarver(Codec<ProbabilityConfig> configCodec) {
        super(configCodec, 256);
    }

    @Override
    public boolean shouldCarve(Random random, int n, int n2, ProbabilityConfig probabilityConfig) {
        return random.nextFloat() <= probabilityConfig.probability;
    }

    @Override
    public boolean carve(Chunk chunk, Function<BlockPos, Biome> function, Random random, int n, int n2, int n3, int n4, int n5, BitSet bitSet, ProbabilityConfig probabilityConfig) {
        int n6 = (this.getBranchFactor() * 2 - 1) * 16;
        double _snowman2 = n2 * 16 + random.nextInt(16);
        double _snowman3 = random.nextInt(random.nextInt(40) + 8) + 20;
        double _snowman4 = n3 * 16 + random.nextInt(16);
        float _snowman5 = random.nextFloat() * ((float)Math.PI * 2);
        float _snowman6 = (random.nextFloat() - 0.5f) * 2.0f / 8.0f;
        double _snowman7 = 3.0;
        float _snowman8 = (random.nextFloat() * 2.0f + random.nextFloat()) * 2.0f;
        _snowman = n6 - random.nextInt(n6 / 4);
        boolean _snowman9 = false;
        this.carveRavine(chunk, function, random.nextLong(), n, n4, n5, _snowman2, _snowman3, _snowman4, _snowman8, _snowman5, _snowman6, 0, _snowman, 3.0, bitSet);
        return true;
    }

    private void carveRavine(Chunk chunk, Function<BlockPos, Biome> posToBiome, long seed, int seaLevel, int mainChunkX, int mainChunkZ, double x, double y, double z, float width, float yaw, float pitch, int branchStartIndex, int branchCount, double yawPitchRatio, BitSet carvingMask) {
        Random random = new Random(seed);
        float _snowman2 = 1.0f;
        for (int i = 0; i < 256; ++i) {
            if (i == 0 || random.nextInt(3) == 0) {
                _snowman2 = 1.0f + random.nextFloat() * random.nextFloat();
            }
            this.heightToHorizontalStretchFactor[i] = _snowman2 * _snowman2;
        }
        float f = 0.0f;
        _snowman = 0.0f;
        for (int i = branchStartIndex; i < branchCount; ++i) {
            double d = 1.5 + (double)(MathHelper.sin((float)i * (float)Math.PI / (float)branchCount) * width);
            _snowman = d * yawPitchRatio;
            d *= (double)random.nextFloat() * 0.25 + 0.75;
            _snowman *= (double)random.nextFloat() * 0.25 + 0.75;
            float _snowman3 = MathHelper.cos(pitch);
            float _snowman4 = MathHelper.sin(pitch);
            x += (double)(MathHelper.cos(yaw) * _snowman3);
            y += (double)_snowman4;
            z += (double)(MathHelper.sin(yaw) * _snowman3);
            pitch *= 0.7f;
            pitch += _snowman * 0.05f;
            yaw += f * 0.05f;
            _snowman *= 0.8f;
            f *= 0.5f;
            _snowman += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0f;
            f += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0f;
            if (random.nextInt(4) == 0) continue;
            if (!this.canCarveBranch(mainChunkX, mainChunkZ, x, z, i, branchCount, width)) {
                return;
            }
            this.carveRegion(chunk, posToBiome, seed, seaLevel, mainChunkX, mainChunkZ, x, y, z, d, _snowman, carvingMask);
        }
    }

    @Override
    protected boolean isPositionExcluded(double scaledRelativeX, double scaledRelativeY, double scaledRelativeZ, int y) {
        return (scaledRelativeX * scaledRelativeX + scaledRelativeZ * scaledRelativeZ) * (double)this.heightToHorizontalStretchFactor[y - 1] + scaledRelativeY * scaledRelativeY / 6.0 >= 1.0;
    }
}

