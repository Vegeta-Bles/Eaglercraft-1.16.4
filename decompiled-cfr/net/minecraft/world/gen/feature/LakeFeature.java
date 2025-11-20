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
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.LightType;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class LakeFeature
extends Feature<SingleStateFeatureConfig> {
    private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();

    public LakeFeature(Codec<SingleStateFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos2, SingleStateFeatureConfig singleStateFeatureConfig2) {
        SingleStateFeatureConfig singleStateFeatureConfig2;
        Object object;
        int n;
        BlockPos blockPos2;
        while (blockPos2.getY() > 5 && structureWorldAccess.isAir(blockPos2)) {
            blockPos2 = blockPos2.down();
        }
        if (blockPos2.getY() <= 4) {
            return false;
        }
        if (structureWorldAccess.getStructures(ChunkSectionPos.from(blockPos2 = blockPos2.down(4)), StructureFeature.VILLAGE).findAny().isPresent()) {
            return false;
        }
        boolean[] _snowman2 = new boolean[2048];
        int _snowman3 = random.nextInt(4) + 4;
        for (n = 0; n < _snowman3; ++n) {
            double d = random.nextDouble() * 6.0 + 3.0;
            _snowman = random.nextDouble() * 4.0 + 2.0;
            _snowman = random.nextDouble() * 6.0 + 3.0;
            _snowman = random.nextDouble() * (16.0 - d - 2.0) + 1.0 + d / 2.0;
            _snowman = random.nextDouble() * (8.0 - _snowman - 4.0) + 2.0 + _snowman / 2.0;
            _snowman = random.nextDouble() * (16.0 - _snowman - 2.0) + 1.0 + _snowman / 2.0;
            for (int i = 1; i < 15; ++i) {
                for (_snowman = 1; _snowman < 15; ++_snowman) {
                    for (_snowman = 1; _snowman < 7; ++_snowman) {
                        double d2 = ((double)i - _snowman) / (d / 2.0);
                        _snowman = ((double)_snowman - _snowman) / (_snowman / 2.0);
                        _snowman = ((double)_snowman - _snowman) / (_snowman / 2.0);
                        _snowman = d2 * d2 + _snowman * _snowman + _snowman * _snowman;
                        if (!(_snowman < 1.0)) continue;
                        _snowman2[(i * 16 + _snowman) * 8 + _snowman] = true;
                    }
                }
            }
        }
        for (n = 0; n < 16; ++n) {
            for (_snowman = 0; _snowman < 16; ++_snowman) {
                for (_snowman = 0; _snowman < 8; ++_snowman) {
                    boolean bl = _snowman = !_snowman2[(n * 16 + _snowman) * 8 + _snowman] && (n < 15 && _snowman2[((n + 1) * 16 + _snowman) * 8 + _snowman] || n > 0 && _snowman2[((n - 1) * 16 + _snowman) * 8 + _snowman] || _snowman < 15 && _snowman2[(n * 16 + _snowman + 1) * 8 + _snowman] || _snowman > 0 && _snowman2[(n * 16 + (_snowman - 1)) * 8 + _snowman] || _snowman < 7 && _snowman2[(n * 16 + _snowman) * 8 + _snowman + 1] || _snowman > 0 && _snowman2[(n * 16 + _snowman) * 8 + (_snowman - 1)]);
                    if (!_snowman) continue;
                    object = structureWorldAccess.getBlockState(blockPos2.add(n, _snowman, _snowman)).getMaterial();
                    if (_snowman >= 4 && ((Material)object).isLiquid()) {
                        return false;
                    }
                    if (_snowman >= 4 || ((Material)object).isSolid() || structureWorldAccess.getBlockState(blockPos2.add(n, _snowman, _snowman)) == singleStateFeatureConfig2.state) continue;
                    return false;
                }
            }
        }
        for (n = 0; n < 16; ++n) {
            for (_snowman = 0; _snowman < 16; ++_snowman) {
                for (_snowman = 0; _snowman < 8; ++_snowman) {
                    if (!_snowman2[(n * 16 + _snowman) * 8 + _snowman]) continue;
                    structureWorldAccess.setBlockState(blockPos2.add(n, _snowman, _snowman), _snowman >= 4 ? CAVE_AIR : singleStateFeatureConfig2.state, 2);
                }
            }
        }
        for (n = 0; n < 16; ++n) {
            for (_snowman = 0; _snowman < 16; ++_snowman) {
                for (_snowman = 4; _snowman < 8; ++_snowman) {
                    if (!_snowman2[(n * 16 + _snowman) * 8 + _snowman] || !LakeFeature.isSoil(structureWorldAccess.getBlockState(_snowman = blockPos2.add(n, _snowman - 1, _snowman)).getBlock()) || structureWorldAccess.getLightLevel(LightType.SKY, blockPos2.add(n, _snowman, _snowman)) <= 0) continue;
                    object = structureWorldAccess.getBiome(_snowman);
                    if (((Biome)object).getGenerationSettings().getSurfaceConfig().getTopMaterial().isOf(Blocks.MYCELIUM)) {
                        structureWorldAccess.setBlockState(_snowman, Blocks.MYCELIUM.getDefaultState(), 2);
                        continue;
                    }
                    structureWorldAccess.setBlockState(_snowman, Blocks.GRASS_BLOCK.getDefaultState(), 2);
                }
            }
        }
        if (singleStateFeatureConfig2.state.getMaterial() == Material.LAVA) {
            for (n = 0; n < 16; ++n) {
                for (_snowman = 0; _snowman < 16; ++_snowman) {
                    for (_snowman = 0; _snowman < 8; ++_snowman) {
                        boolean bl = _snowman = !_snowman2[(n * 16 + _snowman) * 8 + _snowman] && (n < 15 && _snowman2[((n + 1) * 16 + _snowman) * 8 + _snowman] || n > 0 && _snowman2[((n - 1) * 16 + _snowman) * 8 + _snowman] || _snowman < 15 && _snowman2[(n * 16 + _snowman + 1) * 8 + _snowman] || _snowman > 0 && _snowman2[(n * 16 + (_snowman - 1)) * 8 + _snowman] || _snowman < 7 && _snowman2[(n * 16 + _snowman) * 8 + _snowman + 1] || _snowman > 0 && _snowman2[(n * 16 + _snowman) * 8 + (_snowman - 1)]);
                        if (!_snowman || _snowman >= 4 && random.nextInt(2) == 0 || !structureWorldAccess.getBlockState(blockPos2.add(n, _snowman, _snowman)).getMaterial().isSolid()) continue;
                        structureWorldAccess.setBlockState(blockPos2.add(n, _snowman, _snowman), Blocks.STONE.getDefaultState(), 2);
                    }
                }
            }
        }
        if (singleStateFeatureConfig2.state.getMaterial() == Material.WATER) {
            for (n = 0; n < 16; ++n) {
                for (_snowman = 0; _snowman < 16; ++_snowman) {
                    _snowman = 4;
                    BlockPos blockPos3 = blockPos2.add(n, 4, _snowman);
                    if (!structureWorldAccess.getBiome(blockPos3).canSetIce(structureWorldAccess, blockPos3, false)) continue;
                    structureWorldAccess.setBlockState(blockPos3, Blocks.ICE.getDefaultState(), 2);
                }
            }
        }
        return true;
    }
}

