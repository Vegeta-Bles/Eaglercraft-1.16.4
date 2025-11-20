/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class EndPortalFeature
extends Feature<DefaultFeatureConfig> {
    public static final BlockPos ORIGIN = BlockPos.ORIGIN;
    private final boolean open;

    public EndPortalFeature(boolean open) {
        super(DefaultFeatureConfig.CODEC);
        this.open = open;
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos2, DefaultFeatureConfig defaultFeatureConfig) {
        BlockPos blockPos2;
        for (BlockPos blockPos3 : BlockPos.iterate(new BlockPos(blockPos2.getX() - 4, blockPos2.getY() - 1, blockPos2.getZ() - 4), new BlockPos(blockPos2.getX() + 4, blockPos2.getY() + 32, blockPos2.getZ() + 4))) {
            boolean bl = blockPos3.isWithinDistance(blockPos2, 2.5);
            if (!bl && !blockPos3.isWithinDistance(blockPos2, 3.5)) continue;
            if (blockPos3.getY() < blockPos2.getY()) {
                if (bl) {
                    this.setBlockState(structureWorldAccess, blockPos3, Blocks.BEDROCK.getDefaultState());
                    continue;
                }
                if (blockPos3.getY() >= blockPos2.getY()) continue;
                this.setBlockState(structureWorldAccess, blockPos3, Blocks.END_STONE.getDefaultState());
                continue;
            }
            if (blockPos3.getY() > blockPos2.getY()) {
                this.setBlockState(structureWorldAccess, blockPos3, Blocks.AIR.getDefaultState());
                continue;
            }
            if (!bl) {
                this.setBlockState(structureWorldAccess, blockPos3, Blocks.BEDROCK.getDefaultState());
                continue;
            }
            if (this.open) {
                this.setBlockState(structureWorldAccess, new BlockPos(blockPos3), Blocks.END_PORTAL.getDefaultState());
                continue;
            }
            this.setBlockState(structureWorldAccess, new BlockPos(blockPos3), Blocks.AIR.getDefaultState());
        }
        for (int i = 0; i < 4; ++i) {
            this.setBlockState(structureWorldAccess, blockPos2.up(i), Blocks.BEDROCK.getDefaultState());
        }
        _snowman = blockPos2.up(2);
        for (Direction direction : Direction.Type.HORIZONTAL) {
            this.setBlockState(structureWorldAccess, _snowman.offset(direction), (BlockState)Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, direction));
        }
        return true;
    }
}

