/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.structure;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class BuriedTreasureGenerator {

    public static class Piece
    extends StructurePiece {
        public Piece(BlockPos pos) {
            super(StructurePieceType.BURIED_TREASURE, 0);
            this.boundingBox = new BlockBox(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
        }

        public Piece(StructureManager manager, CompoundTag tag) {
            super(StructurePieceType.BURIED_TREASURE, tag);
        }

        @Override
        protected void toNbt(CompoundTag tag) {
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            int n = structureWorldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, this.boundingBox.minX, this.boundingBox.minZ);
            BlockPos.Mutable _snowman2 = new BlockPos.Mutable(this.boundingBox.minX, n, this.boundingBox.minZ);
            while (_snowman2.getY() > 0) {
                BlockState blockState = structureWorldAccess.getBlockState(_snowman2);
                _snowman = structureWorldAccess.getBlockState((BlockPos)_snowman2.down());
                if (_snowman == Blocks.SANDSTONE.getDefaultState() || _snowman == Blocks.STONE.getDefaultState() || _snowman == Blocks.ANDESITE.getDefaultState() || _snowman == Blocks.GRANITE.getDefaultState() || _snowman == Blocks.DIORITE.getDefaultState()) {
                    _snowman = blockState.isAir() || this.isLiquid(blockState) ? Blocks.SAND.getDefaultState() : blockState;
                    for (Direction direction : Direction.values()) {
                        BlockPos blockPos2 = _snowman2.offset(direction);
                        BlockState _snowman3 = structureWorldAccess.getBlockState(blockPos2);
                        if (!_snowman3.isAir() && !this.isLiquid(_snowman3)) continue;
                        _snowman = blockPos2.down();
                        BlockState _snowman4 = structureWorldAccess.getBlockState(_snowman);
                        if ((_snowman4.isAir() || this.isLiquid(_snowman4)) && direction != Direction.UP) {
                            structureWorldAccess.setBlockState(blockPos2, _snowman, 3);
                            continue;
                        }
                        structureWorldAccess.setBlockState(blockPos2, _snowman, 3);
                    }
                    this.boundingBox = new BlockBox(_snowman2.getX(), _snowman2.getY(), _snowman2.getZ(), _snowman2.getX(), _snowman2.getY(), _snowman2.getZ());
                    return this.addChest(structureWorldAccess, boundingBox, random, _snowman2, LootTables.BURIED_TREASURE_CHEST, null);
                }
                _snowman2.move(0, -1, 0);
            }
            return false;
        }

        private boolean isLiquid(BlockState state) {
            return state == Blocks.WATER.getDefaultState() || state == Blocks.LAVA.getDefaultState();
        }
    }
}

