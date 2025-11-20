/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.structure.MarginedStructureStart;
import net.minecraft.structure.NetherFossilGenerator;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.BlockView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class NetherFossilFeature
extends StructureFeature<DefaultFeatureConfig> {
    public NetherFossilFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public StructureFeature.StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return Start::new;
    }

    public static class Start
    extends MarginedStructureStart<DefaultFeatureConfig> {
        public Start(StructureFeature<DefaultFeatureConfig> structureFeature, int n, int n2, BlockBox blockBox, int n3, long l) {
            super(structureFeature, n, n2, blockBox, n3, l);
        }

        @Override
        public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, int n, int n2, Biome biome, DefaultFeatureConfig defaultFeatureConfig) {
            int n3;
            ChunkPos chunkPos = new ChunkPos(n, n2);
            int _snowman2 = chunkPos.getStartX() + this.random.nextInt(16);
            int _snowman3 = chunkPos.getStartZ() + this.random.nextInt(16);
            int _snowman4 = chunkGenerator.getSeaLevel();
            BlockView _snowman5 = chunkGenerator.getColumnSample(_snowman2, _snowman3);
            BlockPos.Mutable _snowman6 = new BlockPos.Mutable(_snowman2, n3, _snowman3);
            for (n3 = _snowman4 + this.random.nextInt(chunkGenerator.getWorldHeight() - 2 - _snowman4); n3 > _snowman4; --n3) {
                BlockState blockState = _snowman5.getBlockState(_snowman6);
                _snowman6.move(Direction.DOWN);
                _snowman = _snowman5.getBlockState(_snowman6);
                if (blockState.isAir() && (_snowman.isOf(Blocks.SOUL_SAND) || _snowman.isSideSolidFullSquare(_snowman5, _snowman6, Direction.UP))) break;
            }
            if (n3 <= _snowman4) {
                return;
            }
            NetherFossilGenerator.addPieces(structureManager, this.children, this.random, new BlockPos(_snowman2, n3, _snowman3));
            this.setBoundingBoxFromChildren();
        }
    }
}

