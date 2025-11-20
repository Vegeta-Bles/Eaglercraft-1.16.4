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
      .xmap(BlockRotStructureProcessor::new, _snowman -> _snowman.integrity)
      .codec();
   private final float integrity;

   public BlockRotStructureProcessor(float integrity) {
      this.integrity = integrity;
   }

   @Nullable
   @Override
   public Structure.StructureBlockInfo process(
      WorldView _snowman, BlockPos pos, BlockPos _snowman, Structure.StructureBlockInfo _snowman, Structure.StructureBlockInfo _snowman, StructurePlacementData _snowman
   ) {
      Random _snowmanxxxxx = _snowman.getRandom(_snowman.pos);
      return !(this.integrity >= 1.0F) && !(_snowmanxxxxx.nextFloat() <= this.integrity) ? null : _snowman;
   }

   @Override
   protected StructureProcessorType<?> getType() {
      return StructureProcessorType.BLOCK_ROT;
   }
}
