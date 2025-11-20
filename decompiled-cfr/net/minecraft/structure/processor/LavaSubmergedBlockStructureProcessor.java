/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 */
package net.minecraft.structure.processor;

import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class LavaSubmergedBlockStructureProcessor
extends StructureProcessor {
    public static final Codec<LavaSubmergedBlockStructureProcessor> CODEC = Codec.unit(() -> INSTANCE);
    public static final LavaSubmergedBlockStructureProcessor INSTANCE = new LavaSubmergedBlockStructureProcessor();

    @Override
    @Nullable
    public Structure.StructureBlockInfo process(WorldView worldView, BlockPos pos, BlockPos blockPos, Structure.StructureBlockInfo structureBlockInfo, Structure.StructureBlockInfo structureBlockInfo2, StructurePlacementData structurePlacementData) {
        BlockPos blockPos2 = structureBlockInfo2.pos;
        boolean _snowman2 = worldView.getBlockState(blockPos2).isOf(Blocks.LAVA);
        if (_snowman2 && !Block.isShapeFullCube(structureBlockInfo2.state.getOutlineShape(worldView, blockPos2))) {
            return new Structure.StructureBlockInfo(blockPos2, Blocks.LAVA.getDefaultState(), structureBlockInfo2.tag);
        }
        return structureBlockInfo2;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return StructureProcessorType.LAVA_SUBMERGED_BLOCK;
    }
}

