package net.minecraft.structure.processor;

import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class NopStructureProcessor extends StructureProcessor {
   public static final Codec<NopStructureProcessor> CODEC = Codec.unit(() -> NopStructureProcessor.INSTANCE);
   public static final NopStructureProcessor INSTANCE = new NopStructureProcessor();

   private NopStructureProcessor() {
   }

   @Nullable
   @Override
   public Structure.StructureBlockInfo process(
      WorldView arg, BlockPos pos, BlockPos arg3, Structure.StructureBlockInfo arg4, Structure.StructureBlockInfo arg5, StructurePlacementData arg6
   ) {
      return arg5;
   }

   @Override
   protected StructureProcessorType<?> getType() {
      return StructureProcessorType.NOP;
   }
}
