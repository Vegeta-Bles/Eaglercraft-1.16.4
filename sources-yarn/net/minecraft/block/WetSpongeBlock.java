package net.minecraft.block;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class WetSpongeBlock extends Block {
   protected WetSpongeBlock(AbstractBlock.Settings arg) {
      super(arg);
   }

   @Override
   public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
      if (world.getDimension().isUltrawarm()) {
         world.setBlockState(pos, Blocks.SPONGE.getDefaultState(), 3);
         world.syncWorldEvent(2009, pos, 0);
         world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, (1.0F + world.getRandom().nextFloat() * 0.2F) * 0.7F);
      }
   }

   @Environment(EnvType.CLIENT)
   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      Direction lv = Direction.random(random);
      if (lv != Direction.UP) {
         BlockPos lv2 = pos.offset(lv);
         BlockState lv3 = world.getBlockState(lv2);
         if (!state.isOpaque() || !lv3.isSideSolidFullSquare(world, lv2, lv.getOpposite())) {
            double d = (double)pos.getX();
            double e = (double)pos.getY();
            double f = (double)pos.getZ();
            if (lv == Direction.DOWN) {
               e -= 0.05;
               d += random.nextDouble();
               f += random.nextDouble();
            } else {
               e += random.nextDouble() * 0.8;
               if (lv.getAxis() == Direction.Axis.X) {
                  f += random.nextDouble();
                  if (lv == Direction.EAST) {
                     d++;
                  } else {
                     d += 0.05;
                  }
               } else {
                  d += random.nextDouble();
                  if (lv == Direction.SOUTH) {
                     f++;
                  } else {
                     f += 0.05;
                  }
               }
            }

            world.addParticle(ParticleTypes.DRIPPING_WATER, d, e, f, 0.0, 0.0, 0.0);
         }
      }
   }
}
