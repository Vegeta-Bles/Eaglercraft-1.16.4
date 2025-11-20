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
   public DecoratedFeature(Codec<DecoratedFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, DecoratedFeatureConfig _snowman) {
      MutableBoolean _snowmanxxxxx = new MutableBoolean();
      _snowman.decorator.getPositions(new DecoratorContext(_snowman, _snowman), _snowman, _snowman).forEach(_snowmanxxxxxx -> {
         if (_snowman.feature.get().generate(_snowman, _snowman, _snowman, _snowmanxxxxxx)) {
            _snowman.setTrue();
         }
      });
      return _snowmanxxxxx.isTrue();
   }

   @Override
   public String toString() {
      return String.format("< %s [%s] >", this.getClass().getSimpleName(), Registry.FEATURE.getId(this));
   }
}
