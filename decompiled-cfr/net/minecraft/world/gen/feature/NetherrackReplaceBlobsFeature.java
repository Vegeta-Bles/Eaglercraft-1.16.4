/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NetherrackReplaceBlobsFeatureConfig;

public class NetherrackReplaceBlobsFeature
extends Feature<NetherrackReplaceBlobsFeatureConfig> {
    public NetherrackReplaceBlobsFeature(Codec<NetherrackReplaceBlobsFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, NetherrackReplaceBlobsFeatureConfig netherrackReplaceBlobsFeatureConfig) {
        Block block = netherrackReplaceBlobsFeatureConfig.target.getBlock();
        BlockPos _snowman2 = NetherrackReplaceBlobsFeature.method_27107(structureWorldAccess, blockPos.mutableCopy().clamp(Direction.Axis.Y, 1, structureWorldAccess.getHeight() - 1), block);
        if (_snowman2 == null) {
            return false;
        }
        int _snowman3 = netherrackReplaceBlobsFeatureConfig.getRadius().getValue(random);
        boolean _snowman4 = false;
        for (BlockPos blockPos2 : BlockPos.iterateOutwards(_snowman2, _snowman3, _snowman3, _snowman3)) {
            if (blockPos2.getManhattanDistance(_snowman2) > _snowman3) break;
            BlockState blockState = structureWorldAccess.getBlockState(blockPos2);
            if (!blockState.isOf(block)) continue;
            this.setBlockState(structureWorldAccess, blockPos2, netherrackReplaceBlobsFeatureConfig.state);
            _snowman4 = true;
        }
        return _snowman4;
    }

    @Nullable
    private static BlockPos method_27107(WorldAccess worldAccess, BlockPos.Mutable mutable, Block block) {
        while (mutable.getY() > 1) {
            BlockState blockState = worldAccess.getBlockState(mutable);
            if (blockState.isOf(block)) {
                return mutable;
            }
            mutable.move(Direction.DOWN);
        }
        return null;
    }
}

