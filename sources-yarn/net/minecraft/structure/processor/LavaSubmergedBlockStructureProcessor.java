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
      WorldView arg, BlockPos pos, BlockPos arg3, Structure.StructureBlockInfo arg4, Structure.StructureBlockInfo arg5, StructurePlacementData arg6
   ) {
      BlockPos lv = arg5.pos;
      boolean bl = arg.getBlockState(lv).isOf(Blocks.LAVA);
      return bl && !Block.isShapeFullCube(arg5.state.getOutlineShape(arg, lv))
         ? new Structure.StructureBlockInfo(lv, Blocks.LAVA.getDefaultState(), arg5.tag)
         : arg5;
   }

   @Override
   protected StructureProcessorType<?> getType() {
      return StructureProcessorType.LAVA_SUBMERGED_BLOCK;
   }
}
