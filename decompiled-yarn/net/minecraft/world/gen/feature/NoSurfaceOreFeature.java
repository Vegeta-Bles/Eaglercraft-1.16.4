package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class NoSurfaceOreFeature extends Feature<OreFeatureConfig> {
   NoSurfaceOreFeature(Codec<OreFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, OreFeatureConfig _snowman) {
      int _snowmanxxxxx = _snowman.nextInt(_snowman.size + 1);
      BlockPos.Mutable _snowmanxxxxxx = new BlockPos.Mutable();

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxx++) {
         this.getStartPos(_snowmanxxxxxx, _snowman, _snowman, Math.min(_snowmanxxxxxxx, 7));
         if (_snowman.target.test(_snowman.getBlockState(_snowmanxxxxxx), _snowman) && !this.checkAir(_snowman, _snowmanxxxxxx)) {
            _snowman.setBlockState(_snowmanxxxxxx, _snowman.state, 2);
         }
      }

      return true;
   }

   private void getStartPos(BlockPos.Mutable mutable, Random random, BlockPos pos, int size) {
      int _snowman = this.randomCoord(random, size);
      int _snowmanx = this.randomCoord(random, size);
      int _snowmanxx = this.randomCoord(random, size);
      mutable.set(pos, _snowman, _snowmanx, _snowmanxx);
   }

   private int randomCoord(Random random, int size) {
      return Math.round((random.nextFloat() - random.nextFloat()) * (float)size);
   }

   private boolean checkAir(WorldAccess world, BlockPos pos) {
      BlockPos.Mutable _snowman = new BlockPos.Mutable();

      for (Direction _snowmanx : Direction.values()) {
         _snowman.set(pos, _snowmanx);
         if (world.getBlockState(_snowman).isAir()) {
            return true;
         }
      }

      return false;
   }
}
