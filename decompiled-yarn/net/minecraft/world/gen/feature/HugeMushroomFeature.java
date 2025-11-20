package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public abstract class HugeMushroomFeature extends Feature<HugeMushroomFeatureConfig> {
   public HugeMushroomFeature(Codec<HugeMushroomFeatureConfig> _snowman) {
      super(_snowman);
   }

   protected void generateStem(WorldAccess world, Random _snowman, BlockPos pos, HugeMushroomFeatureConfig config, int height, BlockPos.Mutable _snowman) {
      for (int _snowmanxx = 0; _snowmanxx < height; _snowmanxx++) {
         _snowman.set(pos).move(Direction.UP, _snowmanxx);
         if (!world.getBlockState(_snowman).isOpaqueFullCube(world, _snowman)) {
            this.setBlockState(world, _snowman, config.stemProvider.getBlockState(_snowman, pos));
         }
      }
   }

   protected int getHeight(Random _snowman) {
      int _snowmanx = _snowman.nextInt(3) + 4;
      if (_snowman.nextInt(12) == 0) {
         _snowmanx *= 2;
      }

      return _snowmanx;
   }

   protected boolean canGenerate(WorldAccess world, BlockPos pos, int height, BlockPos.Mutable _snowman, HugeMushroomFeatureConfig config) {
      int _snowmanx = pos.getY();
      if (_snowmanx >= 1 && _snowmanx + height + 1 < 256) {
         Block _snowmanxx = world.getBlockState(pos.down()).getBlock();
         if (!isSoil(_snowmanxx) && !_snowmanxx.isIn(BlockTags.MUSHROOM_GROW_BLOCK)) {
            return false;
         } else {
            for (int _snowmanxxx = 0; _snowmanxxx <= height; _snowmanxxx++) {
               int _snowmanxxxx = this.getCapSize(-1, -1, config.foliageRadius, _snowmanxxx);

               for (int _snowmanxxxxx = -_snowmanxxxx; _snowmanxxxxx <= _snowmanxxxx; _snowmanxxxxx++) {
                  for (int _snowmanxxxxxx = -_snowmanxxxx; _snowmanxxxxxx <= _snowmanxxxx; _snowmanxxxxxx++) {
                     BlockState _snowmanxxxxxxx = world.getBlockState(_snowman.set(pos, _snowmanxxxxx, _snowmanxxx, _snowmanxxxxxx));
                     if (!_snowmanxxxxxxx.isAir() && !_snowmanxxxxxxx.isIn(BlockTags.LEAVES)) {
                        return false;
                     }
                  }
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, HugeMushroomFeatureConfig _snowman) {
      int _snowmanxxxxx = this.getHeight(_snowman);
      BlockPos.Mutable _snowmanxxxxxx = new BlockPos.Mutable();
      if (!this.canGenerate(_snowman, _snowman, _snowmanxxxxx, _snowmanxxxxxx, _snowman)) {
         return false;
      } else {
         this.generateCap(_snowman, _snowman, _snowman, _snowmanxxxxx, _snowmanxxxxxx, _snowman);
         this.generateStem(_snowman, _snowman, _snowman, _snowman, _snowmanxxxxx, _snowmanxxxxxx);
         return true;
      }
   }

   protected abstract int getCapSize(int var1, int var2, int capSize, int y);

   protected abstract void generateCap(WorldAccess world, Random random, BlockPos start, int y, BlockPos.Mutable mutable, HugeMushroomFeatureConfig config);
}
