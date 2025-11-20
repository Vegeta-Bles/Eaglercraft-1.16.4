package net.minecraft.structure.processor;

import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class LavaSubmergedBlockStructureProcessor extends StructureProcessor {
   public static final Codec<LavaSubmergedBlockStructureProcessor> CODEC = Codec.unit(() -> LavaSubmergedBlockStructureProcessor.INSTANCE);
   public static final LavaSubmergedBlockStructureProcessor INSTANCE = new LavaSubmergedBlockStructureProcessor();

   public LavaSubmergedBlockStructureProcessor() {
   }

   @Nullable
   @Override
   public Structure.StructureBlockInfo process(
      WorldView _snowman, BlockPos pos, BlockPos _snowman, Structure.StructureBlockInfo _snowman, Structure.StructureBlockInfo _snowman, StructurePlacementData _snowman
   ) {
      BlockPos _snowmanxxxxx = _snowman.pos;
      boolean _snowmanxxxxxx = _snowman.getBlockState(_snowmanxxxxx).isOf(Blocks.LAVA);
      return _snowmanxxxxxx && !Block.isShapeFullCube(_snowman.state.getOutlineShape(_snowman, _snowmanxxxxx))
         ? new Structure.StructureBlockInfo(_snowmanxxxxx, Blocks.LAVA.getDefaultState(), _snowman.tag)
         : _snowman;
   }

   @Override
   protected StructureProcessorType<?> getType() {
      return StructureProcessorType.LAVA_SUBMERGED_BLOCK;
   }
}
