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
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.EndGatewayFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class EndGatewayFeature
extends Feature<EndGatewayFeatureConfig> {
    public EndGatewayFeature(Codec<EndGatewayFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, EndGatewayFeatureConfig endGatewayFeatureConfig) {
        for (BlockPos blockPos3 : BlockPos.iterate(blockPos.add(-1, -2, -1), blockPos.add(1, 2, 1))) {
            boolean bl;
            boolean bl2 = blockPos3.getX() == blockPos.getX();
            bl = blockPos3.getY() == blockPos.getY();
            _snowman = blockPos3.getZ() == blockPos.getZ();
            boolean bl3 = _snowman = Math.abs(blockPos3.getY() - blockPos.getY()) == 2;
            if (bl2 && bl && _snowman) {
                BlockPos blockPos4 = blockPos3.toImmutable();
                this.setBlockState(structureWorldAccess, blockPos4, Blocks.END_GATEWAY.getDefaultState());
                endGatewayFeatureConfig.getExitPos().ifPresent(blockPos2 -> {
                    BlockEntity blockEntity = structureWorldAccess.getBlockEntity(blockPos4);
                    if (blockEntity instanceof EndGatewayBlockEntity) {
                        EndGatewayBlockEntity endGatewayBlockEntity = (EndGatewayBlockEntity)blockEntity;
                        endGatewayBlockEntity.setExitPortalPos((BlockPos)blockPos2, endGatewayFeatureConfig.isExact());
                        blockEntity.markDirty();
                    }
                });
                continue;
            }
            if (bl) {
                this.setBlockState(structureWorldAccess, blockPos3, Blocks.AIR.getDefaultState());
                continue;
            }
            if (_snowman && bl2 && _snowman) {
                this.setBlockState(structureWorldAccess, blockPos3, Blocks.BEDROCK.getDefaultState());
                continue;
            }
            if (!bl2 && !_snowman || _snowman) {
                this.setBlockState(structureWorldAccess, blockPos3, Blocks.AIR.getDefaultState());
                continue;
            }
            this.setBlockState(structureWorldAccess, blockPos3, Blocks.BEDROCK.getDefaultState());
        }
        return true;
    }
}

