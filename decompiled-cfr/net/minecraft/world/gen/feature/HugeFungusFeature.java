/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.HugeFungusFeatureConfig;
import net.minecraft.world.gen.feature.WeepingVinesFeature;

public class HugeFungusFeature
extends Feature<HugeFungusFeatureConfig> {
    public HugeFungusFeature(Codec<HugeFungusFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, HugeFungusFeatureConfig hugeFungusFeatureConfig2) {
        Block block = hugeFungusFeatureConfig2.validBaseBlock.getBlock();
        BlockPos _snowman2 = null;
        Block block2 = structureWorldAccess.getBlockState(blockPos.down()).getBlock();
        if (block2 == block) {
            _snowman2 = blockPos;
        }
        if (_snowman2 == null) {
            return false;
        }
        int _snowman3 = MathHelper.nextInt(random, 4, 13);
        if (random.nextInt(12) == 0) {
            _snowman3 *= 2;
        }
        if (!hugeFungusFeatureConfig2.planted) {
            int n = chunkGenerator.getWorldHeight();
            if (_snowman2.getY() + _snowman3 + 1 >= n) {
                return false;
            }
        }
        boolean bl = !hugeFungusFeatureConfig2.planted && random.nextFloat() < 0.06f;
        structureWorldAccess.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 4);
        this.generateStem(structureWorldAccess, random, hugeFungusFeatureConfig2, _snowman2, _snowman3, bl);
        this.generateHat(structureWorldAccess, random, hugeFungusFeatureConfig2, _snowman2, _snowman3, bl);
        return true;
    }

    private static boolean method_24866(WorldAccess worldAccess, BlockPos blockPos, boolean bl) {
        return worldAccess.testBlockState(blockPos, blockState -> {
            Material material = blockState.getMaterial();
            return blockState.getMaterial().isReplaceable() || bl && material == Material.PLANT;
        });
    }

    private void generateStem(WorldAccess world, Random random, HugeFungusFeatureConfig config, BlockPos blockPos, int stemHeight, boolean thickStem) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        BlockState _snowman2 = config.stemState;
        int _snowman3 = thickStem ? 1 : 0;
        for (int i = -_snowman3; i <= _snowman3; ++i) {
            for (_snowman = -_snowman3; _snowman <= _snowman3; ++_snowman) {
                boolean bl = thickStem && MathHelper.abs(i) == _snowman3 && MathHelper.abs(_snowman) == _snowman3;
                for (int j = 0; j < stemHeight; ++j) {
                    mutable.set(blockPos, i, j, _snowman);
                    if (!HugeFungusFeature.method_24866(world, mutable, true)) continue;
                    if (config.planted) {
                        if (!world.getBlockState((BlockPos)mutable.down()).isAir()) {
                            world.breakBlock(mutable, true);
                        }
                        world.setBlockState(mutable, _snowman2, 3);
                        continue;
                    }
                    if (bl) {
                        if (!(random.nextFloat() < 0.1f)) continue;
                        this.setBlockState(world, mutable, _snowman2);
                        continue;
                    }
                    this.setBlockState(world, mutable, _snowman2);
                }
            }
        }
    }

    private void generateHat(WorldAccess world, Random random, HugeFungusFeatureConfig config, BlockPos blockPos, int hatHeight, boolean thickStem) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        boolean _snowman2 = config.hatState.isOf(Blocks.NETHER_WART_BLOCK);
        int _snowman3 = Math.min(random.nextInt(1 + hatHeight / 3) + 5, hatHeight);
        for (int i = _snowman = hatHeight - _snowman3; i <= hatHeight; ++i) {
            int n = _snowman = i < hatHeight - random.nextInt(3) ? 2 : 1;
            if (_snowman3 > 8 && i < _snowman + 4) {
                _snowman = 3;
            }
            if (thickStem) {
                ++_snowman;
            }
            for (_snowman = -_snowman; _snowman <= _snowman; ++_snowman) {
                for (_snowman = -_snowman; _snowman <= _snowman; ++_snowman) {
                    boolean bl = _snowman == -_snowman || _snowman == _snowman;
                    _snowman = _snowman == -_snowman || _snowman == _snowman;
                    _snowman = !bl && !_snowman && i != hatHeight;
                    _snowman = bl && _snowman;
                    _snowman = i < _snowman + 3;
                    mutable.set(blockPos, _snowman, i, _snowman);
                    if (!HugeFungusFeature.method_24866(world, mutable, false)) continue;
                    if (config.planted && !world.getBlockState((BlockPos)mutable.down()).isAir()) {
                        world.breakBlock(mutable, true);
                    }
                    if (_snowman) {
                        if (_snowman) continue;
                        this.tryGenerateVines(world, random, mutable, config.hatState, _snowman2);
                        continue;
                    }
                    if (_snowman) {
                        this.generateHatBlock(world, random, config, mutable, 0.1f, 0.2f, _snowman2 ? 0.1f : 0.0f);
                        continue;
                    }
                    if (_snowman) {
                        this.generateHatBlock(world, random, config, mutable, 0.01f, 0.7f, _snowman2 ? 0.083f : 0.0f);
                        continue;
                    }
                    this.generateHatBlock(world, random, config, mutable, 5.0E-4f, 0.98f, _snowman2 ? 0.07f : 0.0f);
                }
            }
        }
    }

    private void generateHatBlock(WorldAccess world, Random random, HugeFungusFeatureConfig config, BlockPos.Mutable pos, float decorationChance, float generationChance, float vineChance) {
        if (random.nextFloat() < decorationChance) {
            this.setBlockState(world, pos, config.decorationState);
        } else if (random.nextFloat() < generationChance) {
            this.setBlockState(world, pos, config.hatState);
            if (random.nextFloat() < vineChance) {
                HugeFungusFeature.generateVines(pos, world, random);
            }
        }
    }

    private void tryGenerateVines(WorldAccess world, Random random, BlockPos pos, BlockState state, boolean bl) {
        if (world.getBlockState(pos.down()).isOf(state.getBlock())) {
            this.setBlockState(world, pos, state);
        } else if ((double)random.nextFloat() < 0.15) {
            this.setBlockState(world, pos, state);
            if (bl && random.nextInt(11) == 0) {
                HugeFungusFeature.generateVines(pos, world, random);
            }
        }
    }

    private static void generateVines(BlockPos pos, WorldAccess world, Random random) {
        BlockPos.Mutable mutable = pos.mutableCopy().move(Direction.DOWN);
        if (!world.isAir(mutable)) {
            return;
        }
        int _snowman2 = MathHelper.nextInt(random, 1, 5);
        if (random.nextInt(7) == 0) {
            _snowman2 *= 2;
        }
        int _snowman3 = 23;
        int _snowman4 = 25;
        WeepingVinesFeature.generateVineColumn(world, random, mutable, _snowman2, 23, 25);
    }
}

