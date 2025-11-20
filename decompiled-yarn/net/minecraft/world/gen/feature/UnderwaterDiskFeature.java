package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class UnderwaterDiskFeature extends DiskFeature {
   public UnderwaterDiskFeature(Codec<DiskFeatureConfig> _snowman) {
      super(_snowman);
   }

   @Override
   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, DiskFeatureConfig _snowman) {
      return !_snowman.getFluidState(_snowman).isIn(FluidTags.WATER) ? false : super.generate(_snowman, _snowman, _snowman, _snowman, _snowman);
   }
}
