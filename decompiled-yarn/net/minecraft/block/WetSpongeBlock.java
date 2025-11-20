package net.minecraft.block;

import java.util.Random;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class WetSpongeBlock extends Block {
   protected WetSpongeBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
      if (world.getDimension().isUltrawarm()) {
         world.setBlockState(pos, Blocks.SPONGE.getDefaultState(), 3);
         world.syncWorldEvent(2009, pos, 0);
         world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, (1.0F + world.getRandom().nextFloat() * 0.2F) * 0.7F);
      }
   }

   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      Direction _snowman = Direction.random(random);
      if (_snowman != Direction.UP) {
         BlockPos _snowmanx = pos.offset(_snowman);
         BlockState _snowmanxx = world.getBlockState(_snowmanx);
         if (!state.isOpaque() || !_snowmanxx.isSideSolidFullSquare(world, _snowmanx, _snowman.getOpposite())) {
            double _snowmanxxx = (double)pos.getX();
            double _snowmanxxxx = (double)pos.getY();
            double _snowmanxxxxx = (double)pos.getZ();
            if (_snowman == Direction.DOWN) {
               _snowmanxxxx -= 0.05;
               _snowmanxxx += random.nextDouble();
               _snowmanxxxxx += random.nextDouble();
            } else {
               _snowmanxxxx += random.nextDouble() * 0.8;
               if (_snowman.getAxis() == Direction.Axis.X) {
                  _snowmanxxxxx += random.nextDouble();
                  if (_snowman == Direction.EAST) {
                     _snowmanxxx++;
                  } else {
                     _snowmanxxx += 0.05;
                  }
               } else {
                  _snowmanxxx += random.nextDouble();
                  if (_snowman == Direction.SOUTH) {
                     _snowmanxxxxx++;
                  } else {
                     _snowmanxxxxx += 0.05;
                  }
               }
            }

            world.addParticle(ParticleTypes.DRIPPING_WATER, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 0.0, 0.0, 0.0);
         }
      }
   }
}
