package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class EndGatewayBlock extends BlockWithEntity {
   protected EndGatewayBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public BlockEntity createBlockEntity(BlockView world) {
      return new EndGatewayBlockEntity();
   }

   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      BlockEntity _snowman = world.getBlockEntity(pos);
      if (_snowman instanceof EndGatewayBlockEntity) {
         int _snowmanx = ((EndGatewayBlockEntity)_snowman).getDrawnSidesCount();

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
            double _snowmanxxx = (double)pos.getX() + random.nextDouble();
            double _snowmanxxxx = (double)pos.getY() + random.nextDouble();
            double _snowmanxxxxx = (double)pos.getZ() + random.nextDouble();
            double _snowmanxxxxxx = (random.nextDouble() - 0.5) * 0.5;
            double _snowmanxxxxxxx = (random.nextDouble() - 0.5) * 0.5;
            double _snowmanxxxxxxxx = (random.nextDouble() - 0.5) * 0.5;
            int _snowmanxxxxxxxxx = random.nextInt(2) * 2 - 1;
            if (random.nextBoolean()) {
               _snowmanxxxxx = (double)pos.getZ() + 0.5 + 0.25 * (double)_snowmanxxxxxxxxx;
               _snowmanxxxxxxxx = (double)(random.nextFloat() * 2.0F * (float)_snowmanxxxxxxxxx);
            } else {
               _snowmanxxx = (double)pos.getX() + 0.5 + 0.25 * (double)_snowmanxxxxxxxxx;
               _snowmanxxxxxx = (double)(random.nextFloat() * 2.0F * (float)_snowmanxxxxxxxxx);
            }

            world.addParticle(ParticleTypes.PORTAL, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx);
         }
      }
   }

   @Override
   public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
      return ItemStack.EMPTY;
   }

   @Override
   public boolean canBucketPlace(BlockState state, Fluid fluid) {
      return false;
   }
}
