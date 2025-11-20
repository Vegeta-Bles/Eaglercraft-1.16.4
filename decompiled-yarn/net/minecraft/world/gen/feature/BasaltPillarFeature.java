package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class BasaltPillarFeature extends Feature<DefaultFeatureConfig> {
   public BasaltPillarFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, DefaultFeatureConfig _snowman) {
      if (_snowman.isAir(_snowman) && !_snowman.isAir(_snowman.up())) {
         BlockPos.Mutable _snowmanxxxxx = _snowman.mutableCopy();
         BlockPos.Mutable _snowmanxxxxxx = _snowman.mutableCopy();
         boolean _snowmanxxxxxxx = true;
         boolean _snowmanxxxxxxxx = true;
         boolean _snowmanxxxxxxxxx = true;
         boolean _snowmanxxxxxxxxxx = true;

         while (_snowman.isAir(_snowmanxxxxx)) {
            if (World.isOutOfBuildLimitVertically(_snowmanxxxxx)) {
               return true;
            }

            _snowman.setBlockState(_snowmanxxxxx, Blocks.BASALT.getDefaultState(), 2);
            _snowmanxxxxxxx = _snowmanxxxxxxx && this.stopOrPlaceBasalt(_snowman, _snowman, _snowmanxxxxxx.set(_snowmanxxxxx, Direction.NORTH));
            _snowmanxxxxxxxx = _snowmanxxxxxxxx && this.stopOrPlaceBasalt(_snowman, _snowman, _snowmanxxxxxx.set(_snowmanxxxxx, Direction.SOUTH));
            _snowmanxxxxxxxxx = _snowmanxxxxxxxxx && this.stopOrPlaceBasalt(_snowman, _snowman, _snowmanxxxxxx.set(_snowmanxxxxx, Direction.WEST));
            _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxx && this.stopOrPlaceBasalt(_snowman, _snowman, _snowmanxxxxxx.set(_snowmanxxxxx, Direction.EAST));
            _snowmanxxxxx.move(Direction.DOWN);
         }

         _snowmanxxxxx.move(Direction.UP);
         this.tryPlaceBasalt(_snowman, _snowman, _snowmanxxxxxx.set(_snowmanxxxxx, Direction.NORTH));
         this.tryPlaceBasalt(_snowman, _snowman, _snowmanxxxxxx.set(_snowmanxxxxx, Direction.SOUTH));
         this.tryPlaceBasalt(_snowman, _snowman, _snowmanxxxxxx.set(_snowmanxxxxx, Direction.WEST));
         this.tryPlaceBasalt(_snowman, _snowman, _snowmanxxxxxx.set(_snowmanxxxxx, Direction.EAST));
         _snowmanxxxxx.move(Direction.DOWN);
         BlockPos.Mutable _snowmanxxxxxxxxxxx = new BlockPos.Mutable();

         for (int _snowmanxxxxxxxxxxxx = -3; _snowmanxxxxxxxxxxxx < 4; _snowmanxxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxxx = -3; _snowmanxxxxxxxxxxxxx < 4; _snowmanxxxxxxxxxxxxx++) {
               int _snowmanxxxxxxxxxxxxxx = MathHelper.abs(_snowmanxxxxxxxxxxxx) * MathHelper.abs(_snowmanxxxxxxxxxxxxx);
               if (_snowman.nextInt(10) < 10 - _snowmanxxxxxxxxxxxxxx) {
                  _snowmanxxxxxxxxxxx.set(_snowmanxxxxx.add(_snowmanxxxxxxxxxxxx, 0, _snowmanxxxxxxxxxxxxx));
                  int _snowmanxxxxxxxxxxxxxxx = 3;

                  while (_snowman.isAir(_snowmanxxxxxx.set(_snowmanxxxxxxxxxxx, Direction.DOWN))) {
                     _snowmanxxxxxxxxxxx.move(Direction.DOWN);
                     if (--_snowmanxxxxxxxxxxxxxxx <= 0) {
                        break;
                     }
                  }

                  if (!_snowman.isAir(_snowmanxxxxxx.set(_snowmanxxxxxxxxxxx, Direction.DOWN))) {
                     _snowman.setBlockState(_snowmanxxxxxxxxxxx, Blocks.BASALT.getDefaultState(), 2);
                  }
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private void tryPlaceBasalt(WorldAccess world, Random random, BlockPos pos) {
      if (random.nextBoolean()) {
         world.setBlockState(pos, Blocks.BASALT.getDefaultState(), 2);
      }
   }

   private boolean stopOrPlaceBasalt(WorldAccess world, Random random, BlockPos pos) {
      if (random.nextInt(10) != 0) {
         world.setBlockState(pos, Blocks.BASALT.getDefaultState(), 2);
         return true;
      } else {
         return false;
      }
   }
}
