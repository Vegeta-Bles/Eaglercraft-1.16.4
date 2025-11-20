/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 */
package net.minecraft.structure.processor;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.argument.BlockArgumentParser;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class JigsawReplacementStructureProcessor
extends StructureProcessor {
    public static final Codec<JigsawReplacementStructureProcessor> CODEC = Codec.unit(() -> INSTANCE);
    public static final JigsawReplacementStructureProcessor INSTANCE = new JigsawReplacementStructureProcessor();

    private JigsawReplacementStructureProcessor() {
    }

    @Override
    @Nullable
    public Structure.StructureBlockInfo process(WorldView worldView, BlockPos pos, BlockPos blockPos, Structure.StructureBlockInfo structureBlockInfo, Structure.StructureBlockInfo structureBlockInfo2, StructurePlacementData structurePlacementData) {
        BlockState blockState = structureBlockInfo2.state;
        if (!blockState.isOf(Blocks.JIGSAW)) {
            return structureBlockInfo2;
        }
        String _snowman2 = structureBlockInfo2.tag.getString("final_state");
        BlockArgumentParser _snowman3 = new BlockArgumentParser(new StringReader(_snowman2), false);
        try {
            _snowman3.parse(true);
        }
        catch (CommandSyntaxException _snowman4) {
            throw new RuntimeException(_snowman4);
        }
        if (_snowman3.getBlockState().isOf(Blocks.STRUCTURE_VOID)) {
            return null;
        }
        return new Structure.StructureBlockInfo(structureBlockInfo2.pos, _snowman3.getBlockState(), null);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return StructureProcessorType.JIGSAW_REPLACEMENT;
    }
}

