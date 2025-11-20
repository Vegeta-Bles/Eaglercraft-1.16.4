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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;

public class IcebergFeature
extends Feature<SingleStateFeatureConfig> {
    public IcebergFeature(Codec<SingleStateFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess2, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos2, SingleStateFeatureConfig singleStateFeatureConfig) {
        Random random2;
        StructureWorldAccess structureWorldAccess2;
        int n;
        BlockPos blockPos2 = new BlockPos(blockPos2.getX(), chunkGenerator.getSeaLevel(), blockPos2.getZ());
        boolean _snowman2 = random2.nextDouble() > 0.7;
        BlockState _snowman3 = singleStateFeatureConfig.state;
        double _snowman4 = random2.nextDouble() * 2.0 * Math.PI;
        int _snowman5 = 11 - random2.nextInt(5);
        int _snowman6 = 3 + random2.nextInt(3);
        boolean _snowman7 = random2.nextDouble() > 0.7;
        int _snowman8 = 11;
        int n2 = _snowman = _snowman7 ? random2.nextInt(6) + 6 : random2.nextInt(15) + 3;
        if (!_snowman7 && random2.nextDouble() > 0.9) {
            _snowman += random2.nextInt(19) + 7;
        }
        int _snowman9 = Math.min(_snowman + random2.nextInt(11), 18);
        int _snowman10 = Math.min(_snowman + random2.nextInt(7) - random2.nextInt(5), 11);
        int _snowman11 = _snowman7 ? _snowman5 : 11;
        for (n = -_snowman11; n < _snowman11; ++n) {
            for (_snowman = -_snowman11; _snowman < _snowman11; ++_snowman) {
                for (_snowman = 0; _snowman < _snowman; ++_snowman) {
                    int n3 = _snowman = _snowman7 ? this.method_13417(_snowman, _snowman, _snowman10) : this.method_13419(random2, _snowman, _snowman, _snowman10);
                    if (!_snowman7 && n >= _snowman) continue;
                    this.method_13426(structureWorldAccess2, random2, blockPos2, _snowman, n, _snowman, _snowman, _snowman, _snowman11, _snowman7, _snowman6, _snowman4, _snowman2, _snowman3);
                }
            }
        }
        this.method_13418(structureWorldAccess2, blockPos2, _snowman10, _snowman, _snowman7, _snowman5);
        for (n = -_snowman11; n < _snowman11; ++n) {
            for (_snowman = -_snowman11; _snowman < _snowman11; ++_snowman) {
                for (_snowman = -1; _snowman > -_snowman9; --_snowman) {
                    _snowman = _snowman7 ? MathHelper.ceil((float)_snowman11 * (1.0f - (float)Math.pow(_snowman, 2.0) / ((float)_snowman9 * 8.0f))) : _snowman11;
                    _snowman = this.method_13427(random2, -_snowman, _snowman9, _snowman10);
                    if (n >= _snowman) continue;
                    this.method_13426(structureWorldAccess2, random2, blockPos2, _snowman9, n, _snowman, _snowman, _snowman, _snowman, _snowman7, _snowman6, _snowman4, _snowman2, _snowman3);
                }
            }
        }
        int n4 = _snowman7 ? (random2.nextDouble() > 0.1 ? 1 : 0) : (n = random2.nextDouble() > 0.7 ? 1 : 0);
        if (n != 0) {
            this.method_13428(random2, structureWorldAccess2, _snowman10, _snowman, blockPos2, _snowman7, _snowman5, _snowman4, _snowman6);
        }
        return true;
    }

    private void method_13428(Random random, WorldAccess worldAccess, int n, int n2, BlockPos blockPos, boolean bl, int n3, double d, int n4) {
        int n5;
        _snowman = random.nextBoolean() ? -1 : 1;
        _snowman = random.nextBoolean() ? -1 : 1;
        _snowman = random.nextInt(Math.max(n / 2 - 2, 1));
        if (random.nextBoolean()) {
            _snowman = n / 2 + 1 - random.nextInt(Math.max(n - n / 2 - 1, 1));
        }
        _snowman = random.nextInt(Math.max(n / 2 - 2, 1));
        if (random.nextBoolean()) {
            _snowman = n / 2 + 1 - random.nextInt(Math.max(n - n / 2 - 1, 1));
        }
        if (bl) {
            _snowman = _snowman = random.nextInt(Math.max(n3 - 5, 1));
        }
        BlockPos blockPos2 = new BlockPos(_snowman * _snowman, 0, _snowman * _snowman);
        double _snowman2 = bl ? d + 1.5707963267948966 : random.nextDouble() * 2.0 * Math.PI;
        for (n5 = 0; n5 < n2 - 3; ++n5) {
            _snowman = this.method_13419(random, n5, n2, n);
            this.method_13415(_snowman, n5, blockPos, worldAccess, false, _snowman2, blockPos2, n3, n4);
        }
        for (n5 = -1; n5 > -n2 + random.nextInt(5); --n5) {
            _snowman = this.method_13427(random, -n5, n2, n);
            this.method_13415(_snowman, n5, blockPos, worldAccess, true, _snowman2, blockPos2, n3, n4);
        }
    }

    private void method_13415(int n, int n2, BlockPos blockPos, WorldAccess worldAccess, boolean bl, double d, BlockPos blockPos2, int n3, int n4) {
        _snowman = n + 1 + n3 / 3;
        _snowman = Math.min(n - 3, 3) + n4 / 2 - 1;
        for (_snowman = -_snowman; _snowman < _snowman; ++_snowman) {
            for (_snowman = -_snowman; _snowman < _snowman; ++_snowman) {
                double d2 = this.method_13424(_snowman, _snowman, blockPos2, _snowman, _snowman, d);
                if (!(d2 < 0.0) || !this.isSnowyOrIcy(_snowman = worldAccess.getBlockState(_snowman = blockPos.add(_snowman, n2, _snowman)).getBlock()) && _snowman != Blocks.SNOW_BLOCK) continue;
                if (bl) {
                    this.setBlockState(worldAccess, _snowman, Blocks.WATER.getDefaultState());
                    continue;
                }
                this.setBlockState(worldAccess, _snowman, Blocks.AIR.getDefaultState());
                this.clearSnowAbove(worldAccess, _snowman);
            }
        }
    }

    private void clearSnowAbove(WorldAccess world, BlockPos pos) {
        if (world.getBlockState(pos.up()).isOf(Blocks.SNOW)) {
            this.setBlockState(world, pos.up(), Blocks.AIR.getDefaultState());
        }
    }

    private void method_13426(WorldAccess worldAccess, Random random, BlockPos blockPos, int n, int n2, int n3, int n4, int n5, int n6, boolean bl, int n7, double d, boolean bl2, BlockState blockState) {
        double d2 = _snowman = bl ? this.method_13424(n2, n4, BlockPos.ORIGIN, n6, this.method_13416(n3, n, n7), d) : this.method_13421(n2, n4, BlockPos.ORIGIN, n5, random);
        if (_snowman < 0.0) {
            BlockPos blockPos2 = blockPos.add(n2, n3, n4);
            double d3 = _snowman = bl ? -0.5 : (double)(-6 - random.nextInt(3));
            if (_snowman > _snowman && random.nextDouble() > 0.9) {
                return;
            }
            this.method_13425(blockPos2, worldAccess, random, n - n3, n, bl, bl2, blockState);
        }
    }

    private void method_13425(BlockPos blockPos, WorldAccess worldAccess, Random random, int n, int n2, boolean bl, boolean bl2, BlockState blockState) {
        _snowman = worldAccess.getBlockState(blockPos);
        if (_snowman.getMaterial() == Material.AIR || _snowman.isOf(Blocks.SNOW_BLOCK) || _snowman.isOf(Blocks.ICE) || _snowman.isOf(Blocks.WATER)) {
            boolean bl3 = !bl || random.nextDouble() > 0.05;
            int n3 = _snowman = bl ? 3 : 2;
            if (bl2 && !_snowman.isOf(Blocks.WATER) && (double)n <= (double)random.nextInt(Math.max(1, n2 / _snowman)) + (double)n2 * 0.6 && bl3) {
                this.setBlockState(worldAccess, blockPos, Blocks.SNOW_BLOCK.getDefaultState());
            } else {
                this.setBlockState(worldAccess, blockPos, blockState);
            }
        }
    }

    private int method_13416(int n, int n2, int n3) {
        _snowman = n3;
        if (n > 0 && n2 - n <= 3) {
            _snowman -= 4 - (n2 - n);
        }
        return _snowman;
    }

    private double method_13421(int n, int n2, BlockPos blockPos, int n3, Random random) {
        float f = 10.0f * MathHelper.clamp(random.nextFloat(), 0.2f, 0.8f) / (float)n3;
        return (double)f + Math.pow(n - blockPos.getX(), 2.0) + Math.pow(n2 - blockPos.getZ(), 2.0) - Math.pow(n3, 2.0);
    }

    private double method_13424(int n, int n2, BlockPos blockPos, int n3, int n4, double d) {
        return Math.pow(((double)(n - blockPos.getX()) * Math.cos(d) - (double)(n2 - blockPos.getZ()) * Math.sin(d)) / (double)n3, 2.0) + Math.pow(((double)(n - blockPos.getX()) * Math.sin(d) + (double)(n2 - blockPos.getZ()) * Math.cos(d)) / (double)n4, 2.0) - 1.0;
    }

    private int method_13419(Random random, int n, int n2, int n3) {
        float _snowman2;
        float f = 3.5f - random.nextFloat();
        _snowman2 = (1.0f - (float)Math.pow(n, 2.0) / ((float)n2 * f)) * (float)n3;
        if (n2 > 15 + random.nextInt(5)) {
            int n4 = n < 3 + random.nextInt(6) ? n / 2 : n;
            _snowman2 = (1.0f - (float)n4 / ((float)n2 * f * 0.4f)) * (float)n3;
        }
        return MathHelper.ceil(_snowman2 / 2.0f);
    }

    private int method_13417(int n, int n2, int n3) {
        float f = 1.0f;
        _snowman = (1.0f - (float)Math.pow(n, 2.0) / ((float)n2 * 1.0f)) * (float)n3;
        return MathHelper.ceil(_snowman / 2.0f);
    }

    private int method_13427(Random random, int n, int n2, int n3) {
        float f = 1.0f + random.nextFloat() / 2.0f;
        _snowman = (1.0f - (float)n / ((float)n2 * f)) * (float)n3;
        return MathHelper.ceil(_snowman / 2.0f);
    }

    private boolean isSnowyOrIcy(Block block) {
        return block == Blocks.PACKED_ICE || block == Blocks.SNOW_BLOCK || block == Blocks.BLUE_ICE;
    }

    private boolean isAirBelow(BlockView world, BlockPos pos) {
        return world.getBlockState(pos.down()).getMaterial() == Material.AIR;
    }

    private void method_13418(WorldAccess world, BlockPos pos, int n, int n2, boolean bl, int n3) {
        _snowman = bl ? n3 : n / 2;
        for (_snowman = -_snowman; _snowman <= _snowman; ++_snowman) {
            for (_snowman = -_snowman; _snowman <= _snowman; ++_snowman) {
                for (_snowman = 0; _snowman <= n2; ++_snowman) {
                    BlockPos blockPos = pos.add(_snowman, _snowman, _snowman);
                    Block _snowman2 = world.getBlockState(blockPos).getBlock();
                    if (!this.isSnowyOrIcy(_snowman2) && _snowman2 != Blocks.SNOW) continue;
                    if (this.isAirBelow(world, blockPos)) {
                        this.setBlockState(world, blockPos, Blocks.AIR.getDefaultState());
                        this.setBlockState(world, blockPos.up(), Blocks.AIR.getDefaultState());
                        continue;
                    }
                    if (!this.isSnowyOrIcy(_snowman2)) continue;
                    Block[] _snowman3 = new Block[]{world.getBlockState(blockPos.west()).getBlock(), world.getBlockState(blockPos.east()).getBlock(), world.getBlockState(blockPos.north()).getBlock(), world.getBlockState(blockPos.south()).getBlock()};
                    int _snowman4 = 0;
                    for (Block block : _snowman3) {
                        if (this.isSnowyOrIcy(block)) continue;
                        ++_snowman4;
                    }
                    if (_snowman4 < 3) continue;
                    this.setBlockState(world, blockPos, Blocks.AIR.getDefaultState());
                }
            }
        }
    }
}

