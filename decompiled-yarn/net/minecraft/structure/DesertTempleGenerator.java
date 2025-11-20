package net.minecraft.structure;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class DesertTempleGenerator extends StructurePieceWithDimensions {
   private final boolean[] hasPlacedChest = new boolean[4];

   public DesertTempleGenerator(Random random, int x, int z) {
      super(StructurePieceType.DESERT_TEMPLE, random, x, 64, z, 21, 15, 21);
   }

   public DesertTempleGenerator(StructureManager manager, CompoundTag tag) {
      super(StructurePieceType.DESERT_TEMPLE, tag);
      this.hasPlacedChest[0] = tag.getBoolean("hasPlacedChest0");
      this.hasPlacedChest[1] = tag.getBoolean("hasPlacedChest1");
      this.hasPlacedChest[2] = tag.getBoolean("hasPlacedChest2");
      this.hasPlacedChest[3] = tag.getBoolean("hasPlacedChest3");
   }

   @Override
   protected void toNbt(CompoundTag tag) {
      super.toNbt(tag);
      tag.putBoolean("hasPlacedChest0", this.hasPlacedChest[0]);
      tag.putBoolean("hasPlacedChest1", this.hasPlacedChest[1]);
      tag.putBoolean("hasPlacedChest2", this.hasPlacedChest[2]);
      tag.putBoolean("hasPlacedChest3", this.hasPlacedChest[3]);
   }

   @Override
   public boolean generate(
      StructureWorldAccess _snowman, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos _snowman, BlockPos _snowman
   ) {
      this.fillWithOutline(
         _snowman, boundingBox, 0, -4, 0, this.width - 1, 0, this.depth - 1, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false
      );

      for (int _snowmanxxx = 1; _snowmanxxx <= 9; _snowmanxxx++) {
         this.fillWithOutline(
            _snowman,
            boundingBox,
            _snowmanxxx,
            _snowmanxxx,
            _snowmanxxx,
            this.width - 1 - _snowmanxxx,
            _snowmanxxx,
            this.depth - 1 - _snowmanxxx,
            Blocks.SANDSTONE.getDefaultState(),
            Blocks.SANDSTONE.getDefaultState(),
            false
         );
         this.fillWithOutline(
            _snowman,
            boundingBox,
            _snowmanxxx + 1,
            _snowmanxxx,
            _snowmanxxx + 1,
            this.width - 2 - _snowmanxxx,
            _snowmanxxx,
            this.depth - 2 - _snowmanxxx,
            Blocks.AIR.getDefaultState(),
            Blocks.AIR.getDefaultState(),
            false
         );
      }

      for (int _snowmanxxx = 0; _snowmanxxx < this.width; _snowmanxxx++) {
         for (int _snowmanxxxx = 0; _snowmanxxxx < this.depth; _snowmanxxxx++) {
            int _snowmanxxxxx = -5;
            this.fillDownwards(_snowman, Blocks.SANDSTONE.getDefaultState(), _snowmanxxx, -5, _snowmanxxxx, boundingBox);
         }
      }

      BlockState _snowmanxxx = Blocks.SANDSTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH);
      BlockState _snowmanxxxx = Blocks.SANDSTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH);
      BlockState _snowmanxxxxx = Blocks.SANDSTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.EAST);
      BlockState _snowmanxxxxxx = Blocks.SANDSTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.WEST);
      this.fillWithOutline(_snowman, boundingBox, 0, 0, 0, 4, 9, 4, Blocks.SANDSTONE.getDefaultState(), Blocks.AIR.getDefaultState(), false);
      this.fillWithOutline(_snowman, boundingBox, 1, 10, 1, 3, 10, 3, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false);
      this.addBlock(_snowman, _snowmanxxx, 2, 10, 0, boundingBox);
      this.addBlock(_snowman, _snowmanxxxx, 2, 10, 4, boundingBox);
      this.addBlock(_snowman, _snowmanxxxxx, 0, 10, 2, boundingBox);
      this.addBlock(_snowman, _snowmanxxxxxx, 4, 10, 2, boundingBox);
      this.fillWithOutline(_snowman, boundingBox, this.width - 5, 0, 0, this.width - 1, 9, 4, Blocks.SANDSTONE.getDefaultState(), Blocks.AIR.getDefaultState(), false);
      this.fillWithOutline(
         _snowman, boundingBox, this.width - 4, 10, 1, this.width - 2, 10, 3, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false
      );
      this.addBlock(_snowman, _snowmanxxx, this.width - 3, 10, 0, boundingBox);
      this.addBlock(_snowman, _snowmanxxxx, this.width - 3, 10, 4, boundingBox);
      this.addBlock(_snowman, _snowmanxxxxx, this.width - 5, 10, 2, boundingBox);
      this.addBlock(_snowman, _snowmanxxxxxx, this.width - 1, 10, 2, boundingBox);
      this.fillWithOutline(_snowman, boundingBox, 8, 0, 0, 12, 4, 4, Blocks.SANDSTONE.getDefaultState(), Blocks.AIR.getDefaultState(), false);
      this.fillWithOutline(_snowman, boundingBox, 9, 1, 0, 11, 3, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
      this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), 9, 1, 1, boundingBox);
      this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), 9, 2, 1, boundingBox);
      this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), 9, 3, 1, boundingBox);
      this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), 10, 3, 1, boundingBox);
      this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), 11, 3, 1, boundingBox);
      this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), 11, 2, 1, boundingBox);
      this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), 11, 1, 1, boundingBox);
      this.fillWithOutline(_snowman, boundingBox, 4, 1, 1, 8, 3, 3, Blocks.SANDSTONE.getDefaultState(), Blocks.AIR.getDefaultState(), false);
      this.fillWithOutline(_snowman, boundingBox, 4, 1, 2, 8, 2, 2, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
      this.fillWithOutline(_snowman, boundingBox, 12, 1, 1, 16, 3, 3, Blocks.SANDSTONE.getDefaultState(), Blocks.AIR.getDefaultState(), false);
      this.fillWithOutline(_snowman, boundingBox, 12, 1, 2, 16, 2, 2, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
      this.fillWithOutline(
         _snowman, boundingBox, 5, 4, 5, this.width - 6, 4, this.depth - 6, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false
      );
      this.fillWithOutline(_snowman, boundingBox, 9, 4, 9, 11, 4, 11, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
      this.fillWithOutline(_snowman, boundingBox, 8, 1, 8, 8, 3, 8, Blocks.CUT_SANDSTONE.getDefaultState(), Blocks.CUT_SANDSTONE.getDefaultState(), false);
      this.fillWithOutline(_snowman, boundingBox, 12, 1, 8, 12, 3, 8, Blocks.CUT_SANDSTONE.getDefaultState(), Blocks.CUT_SANDSTONE.getDefaultState(), false);
      this.fillWithOutline(_snowman, boundingBox, 8, 1, 12, 8, 3, 12, Blocks.CUT_SANDSTONE.getDefaultState(), Blocks.CUT_SANDSTONE.getDefaultState(), false);
      this.fillWithOutline(_snowman, boundingBox, 12, 1, 12, 12, 3, 12, Blocks.CUT_SANDSTONE.getDefaultState(), Blocks.CUT_SANDSTONE.getDefaultState(), false);
      this.fillWithOutline(_snowman, boundingBox, 1, 1, 5, 4, 4, 11, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false);
      this.fillWithOutline(
         _snowman, boundingBox, this.width - 5, 1, 5, this.width - 2, 4, 11, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false
      );
      this.fillWithOutline(_snowman, boundingBox, 6, 7, 9, 6, 7, 11, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false);
      this.fillWithOutline(
         _snowman, boundingBox, this.width - 7, 7, 9, this.width - 7, 7, 11, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false
      );
      this.fillWithOutline(_snowman, boundingBox, 5, 5, 9, 5, 7, 11, Blocks.CUT_SANDSTONE.getDefaultState(), Blocks.CUT_SANDSTONE.getDefaultState(), false);
      this.fillWithOutline(
         _snowman, boundingBox, this.width - 6, 5, 9, this.width - 6, 7, 11, Blocks.CUT_SANDSTONE.getDefaultState(), Blocks.CUT_SANDSTONE.getDefaultState(), false
      );
      this.addBlock(_snowman, Blocks.AIR.getDefaultState(), 5, 5, 10, boundingBox);
      this.addBlock(_snowman, Blocks.AIR.getDefaultState(), 5, 6, 10, boundingBox);
      this.addBlock(_snowman, Blocks.AIR.getDefaultState(), 6, 6, 10, boundingBox);
      this.addBlock(_snowman, Blocks.AIR.getDefaultState(), this.width - 6, 5, 10, boundingBox);
      this.addBlock(_snowman, Blocks.AIR.getDefaultState(), this.width - 6, 6, 10, boundingBox);
      this.addBlock(_snowman, Blocks.AIR.getDefaultState(), this.width - 7, 6, 10, boundingBox);
      this.fillWithOutline(_snowman, boundingBox, 2, 4, 4, 2, 6, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
      this.fillWithOutline(_snowman, boundingBox, this.width - 3, 4, 4, this.width - 3, 6, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
      this.addBlock(_snowman, _snowmanxxx, 2, 4, 5, boundingBox);
      this.addBlock(_snowman, _snowmanxxx, 2, 3, 4, boundingBox);
      this.addBlock(_snowman, _snowmanxxx, this.width - 3, 4, 5, boundingBox);
      this.addBlock(_snowman, _snowmanxxx, this.width - 3, 3, 4, boundingBox);
      this.fillWithOutline(_snowman, boundingBox, 1, 1, 3, 2, 2, 3, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false);
      this.fillWithOutline(
         _snowman, boundingBox, this.width - 3, 1, 3, this.width - 2, 2, 3, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false
      );
      this.addBlock(_snowman, Blocks.SANDSTONE.getDefaultState(), 1, 1, 2, boundingBox);
      this.addBlock(_snowman, Blocks.SANDSTONE.getDefaultState(), this.width - 2, 1, 2, boundingBox);
      this.addBlock(_snowman, Blocks.SANDSTONE_SLAB.getDefaultState(), 1, 2, 2, boundingBox);
      this.addBlock(_snowman, Blocks.SANDSTONE_SLAB.getDefaultState(), this.width - 2, 2, 2, boundingBox);
      this.addBlock(_snowman, _snowmanxxxxxx, 2, 1, 2, boundingBox);
      this.addBlock(_snowman, _snowmanxxxxx, this.width - 3, 1, 2, boundingBox);
      this.fillWithOutline(_snowman, boundingBox, 4, 3, 5, 4, 3, 17, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false);
      this.fillWithOutline(
         _snowman, boundingBox, this.width - 5, 3, 5, this.width - 5, 3, 17, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false
      );
      this.fillWithOutline(_snowman, boundingBox, 3, 1, 5, 4, 2, 16, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
      this.fillWithOutline(_snowman, boundingBox, this.width - 6, 1, 5, this.width - 5, 2, 16, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);

      for (int _snowmanxxxxxxx = 5; _snowmanxxxxxxx <= 17; _snowmanxxxxxxx += 2) {
         this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), 4, 1, _snowmanxxxxxxx, boundingBox);
         this.addBlock(_snowman, Blocks.CHISELED_SANDSTONE.getDefaultState(), 4, 2, _snowmanxxxxxxx, boundingBox);
         this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), this.width - 5, 1, _snowmanxxxxxxx, boundingBox);
         this.addBlock(_snowman, Blocks.CHISELED_SANDSTONE.getDefaultState(), this.width - 5, 2, _snowmanxxxxxxx, boundingBox);
      }

      this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 10, 0, 7, boundingBox);
      this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 10, 0, 8, boundingBox);
      this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 9, 0, 9, boundingBox);
      this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 11, 0, 9, boundingBox);
      this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 8, 0, 10, boundingBox);
      this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 12, 0, 10, boundingBox);
      this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 7, 0, 10, boundingBox);
      this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 13, 0, 10, boundingBox);
      this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 9, 0, 11, boundingBox);
      this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 11, 0, 11, boundingBox);
      this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 10, 0, 12, boundingBox);
      this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 10, 0, 13, boundingBox);
      this.addBlock(_snowman, Blocks.BLUE_TERRACOTTA.getDefaultState(), 10, 0, 10, boundingBox);

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx <= this.width - 1; _snowmanxxxxxxx += this.width - 1) {
         this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), _snowmanxxxxxxx, 2, 1, boundingBox);
         this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), _snowmanxxxxxxx, 2, 2, boundingBox);
         this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), _snowmanxxxxxxx, 2, 3, boundingBox);
         this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), _snowmanxxxxxxx, 3, 1, boundingBox);
         this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), _snowmanxxxxxxx, 3, 2, boundingBox);
         this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), _snowmanxxxxxxx, 3, 3, boundingBox);
         this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), _snowmanxxxxxxx, 4, 1, boundingBox);
         this.addBlock(_snowman, Blocks.CHISELED_SANDSTONE.getDefaultState(), _snowmanxxxxxxx, 4, 2, boundingBox);
         this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), _snowmanxxxxxxx, 4, 3, boundingBox);
         this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), _snowmanxxxxxxx, 5, 1, boundingBox);
         this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), _snowmanxxxxxxx, 5, 2, boundingBox);
         this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), _snowmanxxxxxxx, 5, 3, boundingBox);
         this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), _snowmanxxxxxxx, 6, 1, boundingBox);
         this.addBlock(_snowman, Blocks.CHISELED_SANDSTONE.getDefaultState(), _snowmanxxxxxxx, 6, 2, boundingBox);
         this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), _snowmanxxxxxxx, 6, 3, boundingBox);
         this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), _snowmanxxxxxxx, 7, 1, boundingBox);
         this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), _snowmanxxxxxxx, 7, 2, boundingBox);
         this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), _snowmanxxxxxxx, 7, 3, boundingBox);
         this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), _snowmanxxxxxxx, 8, 1, boundingBox);
         this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), _snowmanxxxxxxx, 8, 2, boundingBox);
         this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), _snowmanxxxxxxx, 8, 3, boundingBox);
      }

      for (int _snowmanxxxxxxx = 2; _snowmanxxxxxxx <= this.width - 3; _snowmanxxxxxxx += this.width - 3 - 2) {
         this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), _snowmanxxxxxxx - 1, 2, 0, boundingBox);
         this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), _snowmanxxxxxxx, 2, 0, boundingBox);
         this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), _snowmanxxxxxxx + 1, 2, 0, boundingBox);
         this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), _snowmanxxxxxxx - 1, 3, 0, boundingBox);
         this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), _snowmanxxxxxxx, 3, 0, boundingBox);
         this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), _snowmanxxxxxxx + 1, 3, 0, boundingBox);
         this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), _snowmanxxxxxxx - 1, 4, 0, boundingBox);
         this.addBlock(_snowman, Blocks.CHISELED_SANDSTONE.getDefaultState(), _snowmanxxxxxxx, 4, 0, boundingBox);
         this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), _snowmanxxxxxxx + 1, 4, 0, boundingBox);
         this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), _snowmanxxxxxxx - 1, 5, 0, boundingBox);
         this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), _snowmanxxxxxxx, 5, 0, boundingBox);
         this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), _snowmanxxxxxxx + 1, 5, 0, boundingBox);
         this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), _snowmanxxxxxxx - 1, 6, 0, boundingBox);
         this.addBlock(_snowman, Blocks.CHISELED_SANDSTONE.getDefaultState(), _snowmanxxxxxxx, 6, 0, boundingBox);
         this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), _snowmanxxxxxxx + 1, 6, 0, boundingBox);
         this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), _snowmanxxxxxxx - 1, 7, 0, boundingBox);
         this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), _snowmanxxxxxxx, 7, 0, boundingBox);
         this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), _snowmanxxxxxxx + 1, 7, 0, boundingBox);
         this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), _snowmanxxxxxxx - 1, 8, 0, boundingBox);
         this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), _snowmanxxxxxxx, 8, 0, boundingBox);
         this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), _snowmanxxxxxxx + 1, 8, 0, boundingBox);
      }

      this.fillWithOutline(_snowman, boundingBox, 8, 4, 0, 12, 6, 0, Blocks.CUT_SANDSTONE.getDefaultState(), Blocks.CUT_SANDSTONE.getDefaultState(), false);
      this.addBlock(_snowman, Blocks.AIR.getDefaultState(), 8, 6, 0, boundingBox);
      this.addBlock(_snowman, Blocks.AIR.getDefaultState(), 12, 6, 0, boundingBox);
      this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 9, 5, 0, boundingBox);
      this.addBlock(_snowman, Blocks.CHISELED_SANDSTONE.getDefaultState(), 10, 5, 0, boundingBox);
      this.addBlock(_snowman, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 11, 5, 0, boundingBox);
      this.fillWithOutline(_snowman, boundingBox, 8, -14, 8, 12, -11, 12, Blocks.CUT_SANDSTONE.getDefaultState(), Blocks.CUT_SANDSTONE.getDefaultState(), false);
      this.fillWithOutline(
         _snowman, boundingBox, 8, -10, 8, 12, -10, 12, Blocks.CHISELED_SANDSTONE.getDefaultState(), Blocks.CHISELED_SANDSTONE.getDefaultState(), false
      );
      this.fillWithOutline(_snowman, boundingBox, 8, -9, 8, 12, -9, 12, Blocks.CUT_SANDSTONE.getDefaultState(), Blocks.CUT_SANDSTONE.getDefaultState(), false);
      this.fillWithOutline(_snowman, boundingBox, 8, -8, 8, 12, -1, 12, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false);
      this.fillWithOutline(_snowman, boundingBox, 9, -11, 9, 11, -1, 11, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
      this.addBlock(_snowman, Blocks.STONE_PRESSURE_PLATE.getDefaultState(), 10, -11, 10, boundingBox);
      this.fillWithOutline(_snowman, boundingBox, 9, -13, 9, 11, -13, 11, Blocks.TNT.getDefaultState(), Blocks.AIR.getDefaultState(), false);
      this.addBlock(_snowman, Blocks.AIR.getDefaultState(), 8, -11, 10, boundingBox);
      this.addBlock(_snowman, Blocks.AIR.getDefaultState(), 8, -10, 10, boundingBox);
      this.addBlock(_snowman, Blocks.CHISELED_SANDSTONE.getDefaultState(), 7, -10, 10, boundingBox);
      this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), 7, -11, 10, boundingBox);
      this.addBlock(_snowman, Blocks.AIR.getDefaultState(), 12, -11, 10, boundingBox);
      this.addBlock(_snowman, Blocks.AIR.getDefaultState(), 12, -10, 10, boundingBox);
      this.addBlock(_snowman, Blocks.CHISELED_SANDSTONE.getDefaultState(), 13, -10, 10, boundingBox);
      this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), 13, -11, 10, boundingBox);
      this.addBlock(_snowman, Blocks.AIR.getDefaultState(), 10, -11, 8, boundingBox);
      this.addBlock(_snowman, Blocks.AIR.getDefaultState(), 10, -10, 8, boundingBox);
      this.addBlock(_snowman, Blocks.CHISELED_SANDSTONE.getDefaultState(), 10, -10, 7, boundingBox);
      this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), 10, -11, 7, boundingBox);
      this.addBlock(_snowman, Blocks.AIR.getDefaultState(), 10, -11, 12, boundingBox);
      this.addBlock(_snowman, Blocks.AIR.getDefaultState(), 10, -10, 12, boundingBox);
      this.addBlock(_snowman, Blocks.CHISELED_SANDSTONE.getDefaultState(), 10, -10, 13, boundingBox);
      this.addBlock(_snowman, Blocks.CUT_SANDSTONE.getDefaultState(), 10, -11, 13, boundingBox);

      for (Direction _snowmanxxxxxxx : Direction.Type.HORIZONTAL) {
         if (!this.hasPlacedChest[_snowmanxxxxxxx.getHorizontal()]) {
            int _snowmanxxxxxxxx = _snowmanxxxxxxx.getOffsetX() * 2;
            int _snowmanxxxxxxxxx = _snowmanxxxxxxx.getOffsetZ() * 2;
            this.hasPlacedChest[_snowmanxxxxxxx.getHorizontal()] = this.addChest(
               _snowman, boundingBox, random, 10 + _snowmanxxxxxxxx, -11, 10 + _snowmanxxxxxxxxx, LootTables.DESERT_PYRAMID_CHEST
            );
         }
      }

      return true;
   }
}
