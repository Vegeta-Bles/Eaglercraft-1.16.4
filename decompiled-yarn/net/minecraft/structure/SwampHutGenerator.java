package net.minecraft.structure;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.StairShape;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class SwampHutGenerator extends StructurePieceWithDimensions {
   private boolean hasWitch;
   private boolean hasCat;

   public SwampHutGenerator(Random _snowman, int _snowman, int _snowman) {
      super(StructurePieceType.SWAMP_HUT, _snowman, _snowman, 64, _snowman, 7, 7, 9);
   }

   public SwampHutGenerator(StructureManager _snowman, CompoundTag _snowman) {
      super(StructurePieceType.SWAMP_HUT, _snowman);
      this.hasWitch = _snowman.getBoolean("Witch");
      this.hasCat = _snowman.getBoolean("Cat");
   }

   @Override
   protected void toNbt(CompoundTag tag) {
      super.toNbt(tag);
      tag.putBoolean("Witch", this.hasWitch);
      tag.putBoolean("Cat", this.hasCat);
   }

   @Override
   public boolean generate(
      StructureWorldAccess _snowman, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos _snowman, BlockPos _snowman
   ) {
      if (!this.method_14839(_snowman, boundingBox, 0)) {
         return false;
      } else {
         this.fillWithOutline(_snowman, boundingBox, 1, 1, 1, 5, 1, 7, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 1, 4, 2, 5, 4, 7, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 2, 1, 0, 4, 1, 0, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 2, 2, 2, 3, 3, 2, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 1, 2, 3, 1, 3, 6, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 5, 2, 3, 5, 3, 6, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 2, 2, 7, 4, 3, 7, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 1, 0, 2, 1, 3, 2, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LOG.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 5, 0, 2, 5, 3, 2, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LOG.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 1, 0, 7, 1, 3, 7, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LOG.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 5, 0, 7, 5, 3, 7, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LOG.getDefaultState(), false);
         this.addBlock(_snowman, Blocks.OAK_FENCE.getDefaultState(), 2, 3, 2, boundingBox);
         this.addBlock(_snowman, Blocks.OAK_FENCE.getDefaultState(), 3, 3, 7, boundingBox);
         this.addBlock(_snowman, Blocks.AIR.getDefaultState(), 1, 3, 4, boundingBox);
         this.addBlock(_snowman, Blocks.AIR.getDefaultState(), 5, 3, 4, boundingBox);
         this.addBlock(_snowman, Blocks.AIR.getDefaultState(), 5, 3, 5, boundingBox);
         this.addBlock(_snowman, Blocks.POTTED_RED_MUSHROOM.getDefaultState(), 1, 3, 5, boundingBox);
         this.addBlock(_snowman, Blocks.CRAFTING_TABLE.getDefaultState(), 3, 2, 6, boundingBox);
         this.addBlock(_snowman, Blocks.CAULDRON.getDefaultState(), 4, 2, 6, boundingBox);
         this.addBlock(_snowman, Blocks.OAK_FENCE.getDefaultState(), 1, 2, 1, boundingBox);
         this.addBlock(_snowman, Blocks.OAK_FENCE.getDefaultState(), 5, 2, 1, boundingBox);
         BlockState _snowmanxxx = Blocks.SPRUCE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH);
         BlockState _snowmanxxxx = Blocks.SPRUCE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.EAST);
         BlockState _snowmanxxxxx = Blocks.SPRUCE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.WEST);
         BlockState _snowmanxxxxxx = Blocks.SPRUCE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH);
         this.fillWithOutline(_snowman, boundingBox, 0, 4, 1, 6, 4, 1, _snowmanxxx, _snowmanxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 0, 4, 2, 0, 4, 7, _snowmanxxxx, _snowmanxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 6, 4, 2, 6, 4, 7, _snowmanxxxxx, _snowmanxxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 0, 4, 8, 6, 4, 8, _snowmanxxxxxx, _snowmanxxxxxx, false);
         this.addBlock(_snowman, _snowmanxxx.with(StairsBlock.SHAPE, StairShape.OUTER_RIGHT), 0, 4, 1, boundingBox);
         this.addBlock(_snowman, _snowmanxxx.with(StairsBlock.SHAPE, StairShape.OUTER_LEFT), 6, 4, 1, boundingBox);
         this.addBlock(_snowman, _snowmanxxxxxx.with(StairsBlock.SHAPE, StairShape.OUTER_LEFT), 0, 4, 8, boundingBox);
         this.addBlock(_snowman, _snowmanxxxxxx.with(StairsBlock.SHAPE, StairShape.OUTER_RIGHT), 6, 4, 8, boundingBox);

         for (int _snowmanxxxxxxx = 2; _snowmanxxxxxxx <= 7; _snowmanxxxxxxx += 5) {
            for (int _snowmanxxxxxxxx = 1; _snowmanxxxxxxxx <= 5; _snowmanxxxxxxxx += 4) {
               this.fillDownwards(_snowman, Blocks.OAK_LOG.getDefaultState(), _snowmanxxxxxxxx, -1, _snowmanxxxxxxx, boundingBox);
            }
         }

         if (!this.hasWitch) {
            int _snowmanxxxxxxx = this.applyXTransform(2, 5);
            int _snowmanxxxxxxxx = this.applyYTransform(2);
            int _snowmanxxxxxxxxx = this.applyZTransform(2, 5);
            if (boundingBox.contains(new BlockPos(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx))) {
               this.hasWitch = true;
               WitchEntity _snowmanxxxxxxxxxx = EntityType.WITCH.create(_snowman.toServerWorld());
               _snowmanxxxxxxxxxx.setPersistent();
               _snowmanxxxxxxxxxx.refreshPositionAndAngles((double)_snowmanxxxxxxx + 0.5, (double)_snowmanxxxxxxxx, (double)_snowmanxxxxxxxxx + 0.5, 0.0F, 0.0F);
               _snowmanxxxxxxxxxx.initialize(_snowman, _snowman.getLocalDifficulty(new BlockPos(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx)), SpawnReason.STRUCTURE, null, null);
               _snowman.spawnEntityAndPassengers(_snowmanxxxxxxxxxx);
            }
         }

         this.method_16181(_snowman, boundingBox);
         return true;
      }
   }

   private void method_16181(ServerWorldAccess _snowman, BlockBox _snowman) {
      if (!this.hasCat) {
         int _snowmanxx = this.applyXTransform(2, 5);
         int _snowmanxxx = this.applyYTransform(2);
         int _snowmanxxxx = this.applyZTransform(2, 5);
         if (_snowman.contains(new BlockPos(_snowmanxx, _snowmanxxx, _snowmanxxxx))) {
            this.hasCat = true;
            CatEntity _snowmanxxxxx = EntityType.CAT.create(_snowman.toServerWorld());
            _snowmanxxxxx.setPersistent();
            _snowmanxxxxx.refreshPositionAndAngles((double)_snowmanxx + 0.5, (double)_snowmanxxx, (double)_snowmanxxxx + 0.5, 0.0F, 0.0F);
            _snowmanxxxxx.initialize(_snowman, _snowman.getLocalDifficulty(new BlockPos(_snowmanxx, _snowmanxxx, _snowmanxxxx)), SpawnReason.STRUCTURE, null, null);
            _snowman.spawnEntityAndPassengers(_snowmanxxxxx);
         }
      }
   }
}
