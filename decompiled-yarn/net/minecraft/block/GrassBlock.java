package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FlowerFeature;

public class GrassBlock extends SpreadableBlock implements Fertilizable {
   public GrassBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
      return world.getBlockState(pos.up()).isAir();
   }

   @Override
   public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
      return true;
   }

   @Override
   public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
      BlockPos _snowman = pos.up();
      BlockState _snowmanx = Blocks.GRASS.getDefaultState();

      label48:
      for (int _snowmanxx = 0; _snowmanxx < 128; _snowmanxx++) {
         BlockPos _snowmanxxx = _snowman;

         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxx / 16; _snowmanxxxx++) {
            _snowmanxxx = _snowmanxxx.add(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
            if (!world.getBlockState(_snowmanxxx.down()).isOf(this) || world.getBlockState(_snowmanxxx).isFullCube(world, _snowmanxxx)) {
               continue label48;
            }
         }

         BlockState _snowmanxxxxx = world.getBlockState(_snowmanxxx);
         if (_snowmanxxxxx.isOf(_snowmanx.getBlock()) && random.nextInt(10) == 0) {
            ((Fertilizable)_snowmanx.getBlock()).grow(world, random, _snowmanxxx, _snowmanxxxxx);
         }

         if (_snowmanxxxxx.isAir()) {
            BlockState _snowmanxxxxxx;
            if (random.nextInt(8) == 0) {
               List<ConfiguredFeature<?, ?>> _snowmanxxxxxxx = world.getBiome(_snowmanxxx).getGenerationSettings().getFlowerFeatures();
               if (_snowmanxxxxxxx.isEmpty()) {
                  continue;
               }

               ConfiguredFeature<?, ?> _snowmanxxxxxxxx = _snowmanxxxxxxx.get(0);
               FlowerFeature _snowmanxxxxxxxxx = (FlowerFeature)_snowmanxxxxxxxx.feature;
               _snowmanxxxxxx = _snowmanxxxxxxxxx.getFlowerState(random, _snowmanxxx, _snowmanxxxxxxxx.getConfig());
            } else {
               _snowmanxxxxxx = _snowmanx;
            }

            if (_snowmanxxxxxx.canPlaceAt(world, _snowmanxxx)) {
               world.setBlockState(_snowmanxxx, _snowmanxxxxxx, 3);
            }
         }
      }
   }
}
