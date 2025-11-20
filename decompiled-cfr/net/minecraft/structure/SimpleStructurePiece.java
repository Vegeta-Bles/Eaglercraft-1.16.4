/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.structure;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.StructureBlockMode;
import net.minecraft.command.argument.BlockArgumentParser;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class SimpleStructurePiece
extends StructurePiece {
    private static final Logger LOGGER = LogManager.getLogger();
    protected Structure structure;
    protected StructurePlacementData placementData;
    protected BlockPos pos;

    public SimpleStructurePiece(StructurePieceType structurePieceType, int n) {
        super(structurePieceType, n);
    }

    public SimpleStructurePiece(StructurePieceType structurePieceType, CompoundTag compoundTag) {
        super(structurePieceType, compoundTag);
        this.pos = new BlockPos(compoundTag.getInt("TPX"), compoundTag.getInt("TPY"), compoundTag.getInt("TPZ"));
    }

    protected void setStructureData(Structure structure, BlockPos pos, StructurePlacementData placementData) {
        this.structure = structure;
        this.setOrientation(Direction.NORTH);
        this.pos = pos;
        this.placementData = placementData;
        this.boundingBox = structure.calculateBoundingBox(placementData, pos);
    }

    @Override
    protected void toNbt(CompoundTag tag) {
        tag.putInt("TPX", this.pos.getX());
        tag.putInt("TPY", this.pos.getY());
        tag.putInt("TPZ", this.pos.getZ());
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
        this.placementData.setBoundingBox(boundingBox);
        this.boundingBox = this.structure.calculateBoundingBox(this.placementData, this.pos);
        if (this.structure.place(structureWorldAccess, this.pos, blockPos, this.placementData, random, 2)) {
            List<Structure.StructureBlockInfo> list = this.structure.getInfosForBlock(this.pos, this.placementData, Blocks.STRUCTURE_BLOCK);
            for (Structure.StructureBlockInfo structureBlockInfo : list) {
                StructureBlockMode structureBlockMode;
                if (structureBlockInfo.tag == null || (structureBlockMode = StructureBlockMode.valueOf(structureBlockInfo.tag.getString("mode"))) != StructureBlockMode.DATA) continue;
                this.handleMetadata(structureBlockInfo.tag.getString("metadata"), structureBlockInfo.pos, structureWorldAccess, random, boundingBox);
            }
            List<Structure.StructureBlockInfo> list2 = this.structure.getInfosForBlock(this.pos, this.placementData, Blocks.JIGSAW);
            for (Structure.StructureBlockInfo structureBlockInfo : list2) {
                if (structureBlockInfo.tag == null) continue;
                String string = structureBlockInfo.tag.getString("final_state");
                BlockArgumentParser _snowman2 = new BlockArgumentParser(new StringReader(string), false);
                BlockState _snowman3 = Blocks.AIR.getDefaultState();
                try {
                    _snowman2.parse(true);
                    BlockState blockState = _snowman2.getBlockState();
                    if (blockState != null) {
                        _snowman3 = blockState;
                    } else {
                        LOGGER.error("Error while parsing blockstate {} in jigsaw block @ {}", (Object)string, (Object)structureBlockInfo.pos);
                    }
                }
                catch (CommandSyntaxException commandSyntaxException) {
                    LOGGER.error("Error while parsing blockstate {} in jigsaw block @ {}", (Object)string, (Object)structureBlockInfo.pos);
                }
                structureWorldAccess.setBlockState(structureBlockInfo.pos, _snowman3, 3);
            }
        }
        return true;
    }

    protected abstract void handleMetadata(String var1, BlockPos var2, ServerWorldAccess var3, Random var4, BlockBox var5);

    @Override
    public void translate(int x, int y, int z) {
        super.translate(x, y, z);
        this.pos = this.pos.add(x, y, z);
    }

    @Override
    public BlockRotation getRotation() {
        return this.placementData.getRotation();
    }
}

