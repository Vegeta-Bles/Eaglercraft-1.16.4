package net.minecraft.block;

import java.util.Random;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class CryingObsidianBlock extends Block {
   public CryingObsidianBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      if (random.nextInt(5) == 0) {
         Direction _snowman = Direction.random(random);
         if (_snowman != Direction.UP) {
            BlockPos _snowmanx = pos.offset(_snowman);
            BlockState _snowmanxx = world.getBlockState(_snowmanx);
            if (!state.isOpaque() || !_snowmanxx.isSideSolidFullSquare(world, _snowmanx, _snowman.getOpposite())) {
               double _snowmanxxx = _snowman.getOffsetX() == 0 ? random.nextDouble() : 0.5 + (double)_snowman.getOffsetX() * 0.6;
               double _snowmanxxxx = _snowman.getOffsetY() == 0 ? random.nextDouble() : 0.5 + (double)_snowman.getOffsetY() * 0.6;
               double _snowmanxxxxx = _snowman.getOffsetZ() == 0 ? random.nextDouble() : 0.5 + (double)_snowman.getOffsetZ() * 0.6;
               world.addParticle(
                  ParticleTypes.DRIPPING_OBSIDIAN_TEAR, (double)pos.getX() + _snowmanxxx, (double)pos.getY() + _snowmanxxxx, (double)pos.getZ() + _snowmanxxxxx, 0.0, 0.0, 0.0
               );
            }
         }
      }
   }
}
