package net.minecraft.structure.processor;

import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;

public class BlockAgeStructureProcessor extends StructureProcessor {
   public static final Codec<BlockAgeStructureProcessor> CODEC = Codec.FLOAT
      .fieldOf("mossiness")
      .xmap(BlockAgeStructureProcessor::new, _snowman -> _snowman.mossiness)
      .codec();
   private final float mossiness;

   public BlockAgeStructureProcessor(float mossiness) {
      this.mossiness = mossiness;
   }

   @Nullable
   @Override
   public Structure.StructureBlockInfo process(
      WorldView _snowman, BlockPos pos, BlockPos _snowman, Structure.StructureBlockInfo _snowman, Structure.StructureBlockInfo _snowman, StructurePlacementData _snowman
   ) {
      Random _snowmanxxxxx = _snowman.getRandom(_snowman.pos);
      BlockState _snowmanxxxxxx = _snowman.state;
      BlockPos _snowmanxxxxxxx = _snowman.pos;
      BlockState _snowmanxxxxxxxx = null;
      if (_snowmanxxxxxx.isOf(Blocks.STONE_BRICKS) || _snowmanxxxxxx.isOf(Blocks.STONE) || _snowmanxxxxxx.isOf(Blocks.CHISELED_STONE_BRICKS)) {
         _snowmanxxxxxxxx = this.processBlocks(_snowmanxxxxx);
      } else if (_snowmanxxxxxx.isIn(BlockTags.STAIRS)) {
         _snowmanxxxxxxxx = this.processStairs(_snowmanxxxxx, _snowman.state);
      } else if (_snowmanxxxxxx.isIn(BlockTags.SLABS)) {
         _snowmanxxxxxxxx = this.processSlabs(_snowmanxxxxx);
      } else if (_snowmanxxxxxx.isIn(BlockTags.WALLS)) {
         _snowmanxxxxxxxx = this.processWalls(_snowmanxxxxx);
      } else if (_snowmanxxxxxx.isOf(Blocks.OBSIDIAN)) {
         _snowmanxxxxxxxx = this.processObsidian(_snowmanxxxxx);
      }

      return _snowmanxxxxxxxx != null ? new Structure.StructureBlockInfo(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowman.tag) : _snowman;
   }

   @Nullable
   private BlockState processBlocks(Random _snowman) {
      if (_snowman.nextFloat() >= 0.5F) {
         return null;
      } else {
         BlockState[] _snowmanx = new BlockState[]{Blocks.CRACKED_STONE_BRICKS.getDefaultState(), randomStairProperties(_snowman, Blocks.STONE_BRICK_STAIRS)};
         BlockState[] _snowmanxx = new BlockState[]{Blocks.MOSSY_STONE_BRICKS.getDefaultState(), randomStairProperties(_snowman, Blocks.MOSSY_STONE_BRICK_STAIRS)};
         return this.process(_snowman, _snowmanx, _snowmanxx);
      }
   }

   @Nullable
   private BlockState processStairs(Random _snowman, BlockState state) {
      Direction _snowmanx = state.get(StairsBlock.FACING);
      BlockHalf _snowmanxx = state.get(StairsBlock.HALF);
      if (_snowman.nextFloat() >= 0.5F) {
         return null;
      } else {
         BlockState[] _snowmanxxx = new BlockState[]{Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_BRICK_SLAB.getDefaultState()};
         BlockState[] _snowmanxxxx = new BlockState[]{
            Blocks.MOSSY_STONE_BRICK_STAIRS.getDefaultState().with(StairsBlock.FACING, _snowmanx).with(StairsBlock.HALF, _snowmanxx),
            Blocks.MOSSY_STONE_BRICK_SLAB.getDefaultState()
         };
         return this.process(_snowman, _snowmanxxx, _snowmanxxxx);
      }
   }

   @Nullable
   private BlockState processSlabs(Random _snowman) {
      return _snowman.nextFloat() < this.mossiness ? Blocks.MOSSY_STONE_BRICK_SLAB.getDefaultState() : null;
   }

   @Nullable
   private BlockState processWalls(Random _snowman) {
      return _snowman.nextFloat() < this.mossiness ? Blocks.MOSSY_STONE_BRICK_WALL.getDefaultState() : null;
   }

   @Nullable
   private BlockState processObsidian(Random _snowman) {
      return _snowman.nextFloat() < 0.15F ? Blocks.CRYING_OBSIDIAN.getDefaultState() : null;
   }

   private static BlockState randomStairProperties(Random _snowman, Block stairs) {
      return stairs.getDefaultState()
         .with(StairsBlock.FACING, Direction.Type.HORIZONTAL.random(_snowman))
         .with(StairsBlock.HALF, BlockHalf.values()[_snowman.nextInt(BlockHalf.values().length)]);
   }

   private BlockState process(Random _snowman, BlockState[] regularStates, BlockState[] mossyStates) {
      return _snowman.nextFloat() < this.mossiness ? randomState(_snowman, mossyStates) : randomState(_snowman, regularStates);
   }

   private static BlockState randomState(Random _snowman, BlockState[] states) {
      return states[_snowman.nextInt(states.length)];
   }

   @Override
   protected StructureProcessorType<?> getType() {
      return StructureProcessorType.BLOCK_AGE;
   }
}
