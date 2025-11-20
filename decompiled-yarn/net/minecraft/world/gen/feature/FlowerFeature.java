package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public abstract class FlowerFeature<U extends FeatureConfig> extends Feature<U> {
   public FlowerFeature(Codec<U> _snowman) {
      super(_snowman);
   }

   @Override
   public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, U config) {
      BlockState _snowman = this.getFlowerState(random, pos, config);
      int _snowmanx = 0;

      for (int _snowmanxx = 0; _snowmanxx < this.getFlowerAmount(config); _snowmanxx++) {
         BlockPos _snowmanxxx = this.getPos(random, pos, config);
         if (world.isAir(_snowmanxxx) && _snowmanxxx.getY() < 255 && _snowman.canPlaceAt(world, _snowmanxxx) && this.isPosValid(world, _snowmanxxx, config)) {
            world.setBlockState(_snowmanxxx, _snowman, 2);
            _snowmanx++;
         }
      }

      return _snowmanx > 0;
   }

   public abstract boolean isPosValid(WorldAccess world, BlockPos pos, U config);

   public abstract int getFlowerAmount(U config);

   public abstract BlockPos getPos(Random var1, BlockPos pos, U config);

   public abstract BlockState getFlowerState(Random var1, BlockPos pos, U config);
}
