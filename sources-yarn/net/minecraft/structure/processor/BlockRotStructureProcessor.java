package net.minecraft.structure.processor;

import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class BlockRotStructureProcessor extends StructureProcessor {
   public static final Codec<BlockRotStructureProcessor> CODEC = Codec.FLOAT
      .fieldOf("integrity")
      .orElse(1.0F)
      .xmap(BlockRotStructureProcessor::new, arg -> arg.integrity)
      .codec();
   private final float integrity;

   public BlockRotStructureProcessor(float integrity) {
      this.integrity = integrity;
   }

   @Nullable
   @Override
   public Structure.StructureBlockInfo process(
      WorldView arg, BlockPos pos, BlockPos arg3, Structure.StructureBlockInfo arg4, Structure.StructureBlockInfo arg5, StructurePlacementData arg6
   ) {
      Random random = arg6.getRandom(arg5.pos);
      return !(this.integrity >= 1.0F) && !(random.nextFloat() <= this.integrity) ? null : arg5;
   }

   @Override
   protected StructureProcessorType<?> getType() {
      return StructureProcessorType.BLOCK_ROT;
   }
}
