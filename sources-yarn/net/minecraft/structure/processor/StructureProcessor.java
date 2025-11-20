package net.minecraft.structure.processor;

import javax.annotation.Nullable;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public abstract class StructureProcessor {
   public StructureProcessor() {
   }

   @Nullable
   public abstract Structure.StructureBlockInfo process(
      WorldView arg, BlockPos pos, BlockPos arg3, Structure.StructureBlockInfo arg4, Structure.StructureBlockInfo arg5, StructurePlacementData arg6
   );

   protected abstract StructureProcessorType<?> getType();
}
