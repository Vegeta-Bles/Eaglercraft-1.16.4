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
      WorldView _snowman, BlockPos pos, BlockPos _snowman, Structure.StructureBlockInfo _snowman, Structure.StructureBlockInfo _snowman, StructurePlacementData _snowman
   ) {
      return _snowman;
   }

   @Override
   protected StructureProcessorType<?> getType() {
      return StructureProcessorType.NOP;
   }
}
