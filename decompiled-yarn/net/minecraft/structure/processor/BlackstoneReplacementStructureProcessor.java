package net.minecraft.structure.processor;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class BlackstoneReplacementStructureProcessor extends StructureProcessor {
   public static final Codec<BlackstoneReplacementStructureProcessor> CODEC = Codec.unit(() -> BlackstoneReplacementStructureProcessor.INSTANCE);
   public static final BlackstoneReplacementStructureProcessor INSTANCE = new BlackstoneReplacementStructureProcessor();
   private final Map<Block, Block> replacementMap = Util.make(Maps.newHashMap(), _snowman -> {
      _snowman.put(Blocks.COBBLESTONE, Blocks.BLACKSTONE);
      _snowman.put(Blocks.MOSSY_COBBLESTONE, Blocks.BLACKSTONE);
      _snowman.put(Blocks.STONE, Blocks.POLISHED_BLACKSTONE);
      _snowman.put(Blocks.STONE_BRICKS, Blocks.POLISHED_BLACKSTONE_BRICKS);
      _snowman.put(Blocks.MOSSY_STONE_BRICKS, Blocks.POLISHED_BLACKSTONE_BRICKS);
      _snowman.put(Blocks.COBBLESTONE_STAIRS, Blocks.BLACKSTONE_STAIRS);
      _snowman.put(Blocks.MOSSY_COBBLESTONE_STAIRS, Blocks.BLACKSTONE_STAIRS);
      _snowman.put(Blocks.STONE_STAIRS, Blocks.POLISHED_BLACKSTONE_STAIRS);
      _snowman.put(Blocks.STONE_BRICK_STAIRS, Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS);
      _snowman.put(Blocks.MOSSY_STONE_BRICK_STAIRS, Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS);
      _snowman.put(Blocks.COBBLESTONE_SLAB, Blocks.BLACKSTONE_SLAB);
      _snowman.put(Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.BLACKSTONE_SLAB);
      _snowman.put(Blocks.SMOOTH_STONE_SLAB, Blocks.POLISHED_BLACKSTONE_SLAB);
      _snowman.put(Blocks.STONE_SLAB, Blocks.POLISHED_BLACKSTONE_SLAB);
      _snowman.put(Blocks.STONE_BRICK_SLAB, Blocks.POLISHED_BLACKSTONE_BRICK_SLAB);
      _snowman.put(Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.POLISHED_BLACKSTONE_BRICK_SLAB);
      _snowman.put(Blocks.STONE_BRICK_WALL, Blocks.POLISHED_BLACKSTONE_BRICK_WALL);
      _snowman.put(Blocks.MOSSY_STONE_BRICK_WALL, Blocks.POLISHED_BLACKSTONE_BRICK_WALL);
      _snowman.put(Blocks.COBBLESTONE_WALL, Blocks.BLACKSTONE_WALL);
      _snowman.put(Blocks.MOSSY_COBBLESTONE_WALL, Blocks.BLACKSTONE_WALL);
      _snowman.put(Blocks.CHISELED_STONE_BRICKS, Blocks.CHISELED_POLISHED_BLACKSTONE);
      _snowman.put(Blocks.CRACKED_STONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS);
      _snowman.put(Blocks.IRON_BARS, Blocks.CHAIN);
   });

   private BlackstoneReplacementStructureProcessor() {
   }

   @Override
   public Structure.StructureBlockInfo process(
      WorldView _snowman, BlockPos pos, BlockPos _snowman, Structure.StructureBlockInfo _snowman, Structure.StructureBlockInfo _snowman, StructurePlacementData _snowman
   ) {
      Block _snowmanxxxxx = this.replacementMap.get(_snowman.state.getBlock());
      if (_snowmanxxxxx == null) {
         return _snowman;
      } else {
         BlockState _snowmanxxxxxx = _snowman.state;
         BlockState _snowmanxxxxxxx = _snowmanxxxxx.getDefaultState();
         if (_snowmanxxxxxx.contains(StairsBlock.FACING)) {
            _snowmanxxxxxxx = _snowmanxxxxxxx.with(StairsBlock.FACING, _snowmanxxxxxx.get(StairsBlock.FACING));
         }

         if (_snowmanxxxxxx.contains(StairsBlock.HALF)) {
            _snowmanxxxxxxx = _snowmanxxxxxxx.with(StairsBlock.HALF, _snowmanxxxxxx.get(StairsBlock.HALF));
         }

         if (_snowmanxxxxxx.contains(SlabBlock.TYPE)) {
            _snowmanxxxxxxx = _snowmanxxxxxxx.with(SlabBlock.TYPE, _snowmanxxxxxx.get(SlabBlock.TYPE));
         }

         return new Structure.StructureBlockInfo(_snowman.pos, _snowmanxxxxxxx, _snowman.tag);
      }
   }

   @Override
   protected StructureProcessorType<?> getType() {
      return StructureProcessorType.BLACKSTONE_REPLACE;
   }
}
