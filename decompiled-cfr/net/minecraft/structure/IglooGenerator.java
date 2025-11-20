/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.structure;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.SimpleStructurePiece;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class IglooGenerator {
    private static final Identifier TOP_TEMPLATE = new Identifier("igloo/top");
    private static final Identifier MIDDLE_TEMPLATE = new Identifier("igloo/middle");
    private static final Identifier BOTTOM_TEMPLATE = new Identifier("igloo/bottom");
    private static final Map<Identifier, BlockPos> field_14408 = ImmutableMap.of((Object)TOP_TEMPLATE, (Object)new BlockPos(3, 5, 5), (Object)MIDDLE_TEMPLATE, (Object)new BlockPos(1, 3, 1), (Object)BOTTOM_TEMPLATE, (Object)new BlockPos(3, 6, 7));
    private static final Map<Identifier, BlockPos> field_14406 = ImmutableMap.of((Object)TOP_TEMPLATE, (Object)BlockPos.ORIGIN, (Object)MIDDLE_TEMPLATE, (Object)new BlockPos(2, -3, 4), (Object)BOTTOM_TEMPLATE, (Object)new BlockPos(0, -3, -2));

    public static void addPieces(StructureManager manager, BlockPos pos, BlockRotation rotation, List<StructurePiece> pieces, Random random) {
        if (random.nextDouble() < 0.5) {
            int n = random.nextInt(8) + 4;
            pieces.add(new Piece(manager, BOTTOM_TEMPLATE, pos, rotation, n * 3));
            for (_snowman = 0; _snowman < n - 1; ++_snowman) {
                pieces.add(new Piece(manager, MIDDLE_TEMPLATE, pos, rotation, _snowman * 3));
            }
        }
        pieces.add(new Piece(manager, TOP_TEMPLATE, pos, rotation, 0));
    }

    public static class Piece
    extends SimpleStructurePiece {
        private final Identifier template;
        private final BlockRotation rotation;

        public Piece(StructureManager manager, Identifier identifier, BlockPos pos, BlockRotation rotation, int yOffset) {
            super(StructurePieceType.IGLOO, 0);
            this.template = identifier;
            BlockPos blockPos = (BlockPos)field_14406.get(identifier);
            this.pos = pos.add(blockPos.getX(), blockPos.getY() - yOffset, blockPos.getZ());
            this.rotation = rotation;
            this.initializeStructureData(manager);
        }

        public Piece(StructureManager manager, CompoundTag tag) {
            super(StructurePieceType.IGLOO, tag);
            this.template = new Identifier(tag.getString("Template"));
            this.rotation = BlockRotation.valueOf(tag.getString("Rot"));
            this.initializeStructureData(manager);
        }

        private void initializeStructureData(StructureManager manager) {
            Structure structure = manager.getStructureOrBlank(this.template);
            StructurePlacementData _snowman2 = new StructurePlacementData().setRotation(this.rotation).setMirror(BlockMirror.NONE).setPosition((BlockPos)field_14408.get(this.template)).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
            this.setStructureData(structure, this.pos, _snowman2);
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putString("Template", this.template.toString());
            tag.putString("Rot", this.rotation.name());
        }

        @Override
        protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess serverWorldAccess, Random random, BlockBox boundingBox) {
            if (!"chest".equals(metadata)) {
                return;
            }
            serverWorldAccess.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
            BlockEntity blockEntity = serverWorldAccess.getBlockEntity(pos.down());
            if (blockEntity instanceof ChestBlockEntity) {
                ((ChestBlockEntity)blockEntity).setLootTable(LootTables.IGLOO_CHEST_CHEST, random.nextLong());
            }
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            StructurePlacementData structurePlacementData = new StructurePlacementData().setRotation(this.rotation).setMirror(BlockMirror.NONE).setPosition((BlockPos)field_14408.get(this.template)).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
            BlockPos _snowman2 = (BlockPos)field_14406.get(this.template);
            BlockPos _snowman3 = this.pos.add(Structure.transform(structurePlacementData, new BlockPos(3 - _snowman2.getX(), 0, 0 - _snowman2.getZ())));
            int _snowman4 = structureWorldAccess.getTopY(Heightmap.Type.WORLD_SURFACE_WG, _snowman3.getX(), _snowman3.getZ());
            BlockPos _snowman5 = this.pos;
            this.pos = this.pos.add(0, _snowman4 - 90 - 1, 0);
            boolean _snowman6 = super.generate(structureWorldAccess, structureAccessor, chunkGenerator, random, boundingBox, chunkPos, blockPos);
            if (this.template.equals(TOP_TEMPLATE) && !(_snowman = structureWorldAccess.getBlockState((_snowman = this.pos.add(Structure.transform(structurePlacementData, new BlockPos(3, 0, 5)))).down())).isAir() && !_snowman.isOf(Blocks.LADDER)) {
                structureWorldAccess.setBlockState(_snowman, Blocks.SNOW_BLOCK.getDefaultState(), 3);
            }
            this.pos = _snowman5;
            return _snowman6;
        }
    }
}

