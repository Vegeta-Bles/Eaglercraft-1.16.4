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
   public GrassBlock(AbstractBlock.Settings arg) {
      super(arg);
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
      BlockPos lv = pos.up();
      BlockState lv2 = Blocks.GRASS.getDefaultState();

      label48:
      for (int i = 0; i < 128; i++) {
         BlockPos lv3 = lv;

         for (int j = 0; j < i / 16; j++) {
            lv3 = lv3.add(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
            if (!world.getBlockState(lv3.down()).isOf(this) || world.getBlockState(lv3).isFullCube(world, lv3)) {
               continue label48;
            }
         }

         BlockState lv4 = world.getBlockState(lv3);
         if (lv4.isOf(lv2.getBlock()) && random.nextInt(10) == 0) {
            ((Fertilizable)lv2.getBlock()).grow(world, random, lv3, lv4);
         }

         if (lv4.isAir()) {
            BlockState lv7;
            if (random.nextInt(8) == 0) {
               List<ConfiguredFeature<?, ?>> list = world.getBiome(lv3).getGenerationSettings().getFlowerFeatures();
               if (list.isEmpty()) {
                  continue;
               }

               ConfiguredFeature<?, ?> lv5 = list.get(0);
               FlowerFeature lv6 = (FlowerFeature)lv5.feature;
               lv7 = lv6.getFlowerState(random, lv3, lv5.getConfig());
            } else {
               lv7 = lv2;
            }

            if (lv7.canPlaceAt(world, lv3)) {
               world.setBlockState(lv3, lv7, 3);
            }
         }
      }
   }
}
