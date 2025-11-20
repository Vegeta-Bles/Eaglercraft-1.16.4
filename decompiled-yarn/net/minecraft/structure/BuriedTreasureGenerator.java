package net.minecraft.structure;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class BuriedTreasureGenerator {
   public static class Piece extends StructurePiece {
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
      public boolean generate(
         StructureWorldAccess _snowman,
         StructureAccessor structureAccessor,
         ChunkGenerator chunkGenerator,
         Random random,
         BlockBox boundingBox,
         ChunkPos _snowman,
         BlockPos _snowman
      ) {
         int _snowmanxxx = _snowman.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, this.boundingBox.minX, this.boundingBox.minZ);
         BlockPos.Mutable _snowmanxxxx = new BlockPos.Mutable(this.boundingBox.minX, _snowmanxxx, this.boundingBox.minZ);

         while (_snowmanxxxx.getY() > 0) {
            BlockState _snowmanxxxxx = _snowman.getBlockState(_snowmanxxxx);
            BlockState _snowmanxxxxxx = _snowman.getBlockState(_snowmanxxxx.down());
            if (_snowmanxxxxxx == Blocks.SANDSTONE.getDefaultState()
               || _snowmanxxxxxx == Blocks.STONE.getDefaultState()
               || _snowmanxxxxxx == Blocks.ANDESITE.getDefaultState()
               || _snowmanxxxxxx == Blocks.GRANITE.getDefaultState()
               || _snowmanxxxxxx == Blocks.DIORITE.getDefaultState()) {
               BlockState _snowmanxxxxxxx = !_snowmanxxxxx.isAir() && !this.isLiquid(_snowmanxxxxx) ? _snowmanxxxxx : Blocks.SAND.getDefaultState();

               for (Direction _snowmanxxxxxxxx : Direction.values()) {
                  BlockPos _snowmanxxxxxxxxx = _snowmanxxxx.offset(_snowmanxxxxxxxx);
                  BlockState _snowmanxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxx);
                  if (_snowmanxxxxxxxxxx.isAir() || this.isLiquid(_snowmanxxxxxxxxxx)) {
                     BlockPos _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx.down();
                     BlockState _snowmanxxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxxxx);
                     if ((_snowmanxxxxxxxxxxxx.isAir() || this.isLiquid(_snowmanxxxxxxxxxxxx)) && _snowmanxxxxxxxx != Direction.UP) {
                        _snowman.setBlockState(_snowmanxxxxxxxxx, _snowmanxxxxxx, 3);
                     } else {
                        _snowman.setBlockState(_snowmanxxxxxxxxx, _snowmanxxxxxxx, 3);
                     }
                  }
               }

               this.boundingBox = new BlockBox(_snowmanxxxx.getX(), _snowmanxxxx.getY(), _snowmanxxxx.getZ(), _snowmanxxxx.getX(), _snowmanxxxx.getY(), _snowmanxxxx.getZ());
               return this.addChest(_snowman, boundingBox, random, _snowmanxxxx, LootTables.BURIED_TREASURE_CHEST, null);
            }

            _snowmanxxxx.move(0, -1, 0);
         }

         return false;
      }

      private boolean isLiquid(BlockState state) {
         return state == Blocks.WATER.getDefaultState() || state == Blocks.LAVA.getDefaultState();
      }
   }
}
