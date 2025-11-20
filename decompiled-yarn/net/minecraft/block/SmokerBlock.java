package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SmokerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SmokerBlock extends AbstractFurnaceBlock {
   protected SmokerBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public BlockEntity createBlockEntity(BlockView world) {
      return new SmokerBlockEntity();
   }

   @Override
   protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
      BlockEntity _snowman = world.getBlockEntity(pos);
      if (_snowman instanceof SmokerBlockEntity) {
         player.openHandledScreen((NamedScreenHandlerFactory)_snowman);
         player.incrementStat(Stats.INTERACT_WITH_SMOKER);
      }
   }

   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      if (state.get(LIT)) {
         double _snowman = (double)pos.getX() + 0.5;
         double _snowmanx = (double)pos.getY();
         double _snowmanxx = (double)pos.getZ() + 0.5;
         if (random.nextDouble() < 0.1) {
            world.playSound(_snowman, _snowmanx, _snowmanxx, SoundEvents.BLOCK_SMOKER_SMOKE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
         }

         world.addParticle(ParticleTypes.SMOKE, _snowman, _snowmanx + 1.1, _snowmanxx, 0.0, 0.0, 0.0);
      }
   }
}
