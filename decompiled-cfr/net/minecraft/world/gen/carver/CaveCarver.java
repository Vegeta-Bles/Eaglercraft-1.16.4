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

public class CaveCarver
extends Carver<ProbabilityConfig> {
    public CaveCarver(Codec<ProbabilityConfig> codec, int n) {
        super(codec, n);
    }

    @Override
    public boolean shouldCarve(Random random, int n, int n2, ProbabilityConfig probabilityConfig) {
        return random.nextFloat() <= probabilityConfig.probability;
    }

    @Override
    public boolean carve(Chunk chunk, Function<BlockPos, Biome> function, Random random, int n, int n2, int n3, int n4, int n5, BitSet bitSet, ProbabilityConfig probabilityConfig) {
        int n6 = (this.getBranchFactor() * 2 - 1) * 16;
        _snowman = random.nextInt(random.nextInt(random.nextInt(this.getMaxCaveCount()) + 1) + 1);
        for (_snowman = 0; _snowman < _snowman; ++_snowman) {
            double d = n2 * 16 + random.nextInt(16);
            _snowman = this.getCaveY(random);
            _snowman = n3 * 16 + random.nextInt(16);
            int _snowman2 = 1;
            if (random.nextInt(4) == 0) {
                _snowman = 0.5;
                float f = 1.0f + random.nextFloat() * 6.0f;
                this.carveCave(chunk, function, random.nextLong(), n, n4, n5, d, _snowman, _snowman, f, 0.5, bitSet);
                _snowman2 += random.nextInt(4);
            }
            for (int i = 0; i < _snowman2; ++i) {
                float f = random.nextFloat() * ((float)Math.PI * 2);
                f = (random.nextFloat() - 0.5f) / 4.0f;
                _snowman = this.getTunnelSystemWidth(random);
                int _snowman3 = n6 - random.nextInt(n6 / 4);
                boolean _snowman4 = false;
                this.carveTunnels(chunk, function, random.nextLong(), n, n4, n5, d, _snowman, _snowman, _snowman, f, f, 0, _snowman3, this.getTunnelSystemHeightWidthRatio(), bitSet);
            }
        }
        return true;
    }

    protected int getMaxCaveCount() {
        return 15;
    }

    protected float getTunnelSystemWidth(Random random) {
        float f = random.nextFloat() * 2.0f + random.nextFloat();
        if (random.nextInt(10) == 0) {
            f *= random.nextFloat() * random.nextFloat() * 3.0f + 1.0f;
        }
        return f;
    }

    protected double getTunnelSystemHeightWidthRatio() {
        return 1.0;
    }

    protected int getCaveY(Random random) {
        return random.nextInt(random.nextInt(120) + 8);
    }

    protected void carveCave(Chunk chunk, Function<BlockPos, Biome> posToBiome, long seed, int seaLevel, int mainChunkX, int mainChunkZ, double x, double y, double z, float yaw, double yawPitchRatio, BitSet carvingMask) {
        double d = 1.5 + (double)(MathHelper.sin(1.5707964f) * yaw);
        _snowman = d * yawPitchRatio;
        this.carveRegion(chunk, posToBiome, seed, seaLevel, mainChunkX, mainChunkZ, x + 1.0, y, z, d, _snowman, carvingMask);
    }

    protected void carveTunnels(Chunk chunk, Function<BlockPos, Biome> postToBiome, long seed, int seaLevel, int mainChunkX, int mainChunkZ, double x, double y, double z, float width, float yaw, float pitch, int branchStartIndex, int branchCount, double yawPitchRatio, BitSet carvingMask) {
        Random random = new Random(seed);
        int _snowman2 = random.nextInt(branchCount / 2) + branchCount / 4;
        boolean _snowman3 = random.nextInt(6) == 0;
        float _snowman4 = 0.0f;
        float _snowman5 = 0.0f;
        for (int i = branchStartIndex; i < branchCount; ++i) {
            double d = 1.5 + (double)(MathHelper.sin((float)Math.PI * (float)i / (float)branchCount) * width);
            _snowman = d * yawPitchRatio;
            float _snowman6 = MathHelper.cos(pitch);
            x += (double)(MathHelper.cos(yaw) * _snowman6);
            y += (double)MathHelper.sin(pitch);
            z += (double)(MathHelper.sin(yaw) * _snowman6);
            pitch *= _snowman3 ? 0.92f : 0.7f;
            pitch += _snowman5 * 0.1f;
            yaw += _snowman4 * 0.1f;
            _snowman5 *= 0.9f;
            _snowman4 *= 0.75f;
            _snowman5 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0f;
            _snowman4 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0f;
            if (i == _snowman2 && width > 1.0f) {
                this.carveTunnels(chunk, postToBiome, random.nextLong(), seaLevel, mainChunkX, mainChunkZ, x, y, z, random.nextFloat() * 0.5f + 0.5f, yaw - 1.5707964f, pitch / 3.0f, i, branchCount, 1.0, carvingMask);
                this.carveTunnels(chunk, postToBiome, random.nextLong(), seaLevel, mainChunkX, mainChunkZ, x, y, z, random.nextFloat() * 0.5f + 0.5f, yaw + 1.5707964f, pitch / 3.0f, i, branchCount, 1.0, carvingMask);
                return;
            }
            if (random.nextInt(4) == 0) continue;
            if (!this.canCarveBranch(mainChunkX, mainChunkZ, x, z, i, branchCount, width)) {
                return;
            }
            this.carveRegion(chunk, postToBiome, seed, seaLevel, mainChunkX, mainChunkZ, x, y, z, d, _snowman, carvingMask);
        }
    }

    @Override
    protected boolean isPositionExcluded(double scaledRelativeX, double scaledRelativeY, double scaledRelativeZ, int y) {
        return scaledRelativeY <= -0.7 || scaledRelativeX * scaledRelativeX + scaledRelativeY * scaledRelativeY + scaledRelativeZ * scaledRelativeZ >= 1.0;
    }
}

