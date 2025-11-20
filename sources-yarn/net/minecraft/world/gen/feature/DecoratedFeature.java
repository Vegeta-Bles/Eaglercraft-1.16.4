package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.decorator.DecoratorContext;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class DecoratedFeature extends Feature<DecoratedFeatureConfig> {
   public DecoratedFeature(Codec<DecoratedFeatureConfig> codec) {
      super(codec);
   }

   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, DecoratedFeatureConfig arg4) {
      MutableBoolean mutableBoolean = new MutableBoolean();
      arg4.decorator.getPositions(new DecoratorContext(arg, arg2), random, arg3).forEach(arg4x -> {
         if (arg4.feature.get().generate(arg, arg2, random, arg4x)) {
            mutableBoolean.setTrue();
         }
      });
      return mutableBoolean.isTrue();
   }

   @Override
   public String toString() {
      return String.format("< %s [%s] >", this.getClass().getSimpleName(), Registry.FEATURE.getId(this));
   }
}
