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
      WorldView var1, BlockPos pos, BlockPos var3, Structure.StructureBlockInfo var4, Structure.StructureBlockInfo var5, StructurePlacementData var6
   );

   protected abstract StructureProcessorType<?> getType();
}
