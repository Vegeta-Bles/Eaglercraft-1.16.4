/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.AbstractPlantStemBlock;
import net.minecraft.block.BlockState;
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

public class TwistingVinesFeature
extends Feature<DefaultFeatureConfig> {
    public TwistingVinesFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, DefaultFeatureConfig defaultFeatureConfig) {
        return TwistingVinesFeature.method_26265(structureWorldAccess, random, blockPos, 8, 4, 8);
    }

    public static boolean method_26265(WorldAccess worldAccess, Random random, BlockPos blockPos, int n, int n2, int n3) {
        if (TwistingVinesFeature.isNotSuitable(worldAccess, blockPos)) {
            return false;
        }
        TwistingVinesFeature.generateVinesInArea(worldAccess, random, blockPos, n, n2, n3);
        return true;
    }

    private static void generateVinesInArea(WorldAccess worldAccess, Random random, BlockPos blockPos, int n, int n2, int n3) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = 0; i < n * n; ++i) {
            mutable.set(blockPos).move(MathHelper.nextInt(random, -n, n), MathHelper.nextInt(random, -n2, n2), MathHelper.nextInt(random, -n, n));
            if (!TwistingVinesFeature.method_27220(worldAccess, mutable) || TwistingVinesFeature.isNotSuitable(worldAccess, mutable)) continue;
            _snowman = MathHelper.nextInt(random, 1, n3);
            if (random.nextInt(6) == 0) {
                _snowman *= 2;
            }
            if (random.nextInt(5) == 0) {
                _snowman = 1;
            }
            _snowman = 17;
            _snowman = 25;
            TwistingVinesFeature.generateVineColumn(worldAccess, random, mutable, _snowman, 17, 25);
        }
    }

    private static boolean method_27220(WorldAccess worldAccess, BlockPos.Mutable mutable) {
        do {
            mutable.move(0, -1, 0);
            if (!World.isOutOfBuildLimitVertically(mutable)) continue;
            return false;
        } while (worldAccess.getBlockState(mutable).isAir());
        mutable.move(0, 1, 0);
        return true;
    }

    public static void generateVineColumn(WorldAccess world, Random random, BlockPos.Mutable pos, int maxLength, int minAge, int maxAge) {
        for (int i = 1; i <= maxLength; ++i) {
            if (world.isAir(pos)) {
                if (i == maxLength || !world.isAir((BlockPos)pos.up())) {
                    world.setBlockState(pos, (BlockState)Blocks.TWISTING_VINES.getDefaultState().with(AbstractPlantStemBlock.AGE, MathHelper.nextInt(random, minAge, maxAge)), 2);
                    break;
                }
                world.setBlockState(pos, Blocks.TWISTING_VINES_PLANT.getDefaultState(), 2);
            }
            pos.move(Direction.UP);
        }
    }

    private static boolean isNotSuitable(WorldAccess worldAccess, BlockPos blockPos) {
        if (!worldAccess.isAir(blockPos)) {
            return true;
        }
        BlockState blockState = worldAccess.getBlockState(blockPos.down());
        return !blockState.isOf(Blocks.NETHERRACK) && !blockState.isOf(Blocks.WARPED_NYLIUM) && !blockState.isOf(Blocks.WARPED_WART_BLOCK);
    }
}

