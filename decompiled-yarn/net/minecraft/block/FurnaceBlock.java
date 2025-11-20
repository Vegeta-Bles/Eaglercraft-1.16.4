package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class FurnaceBlock extends AbstractFurnaceBlock {
   protected FurnaceBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public BlockEntity createBlockEntity(BlockView world) {
      return new FurnaceBlockEntity();
   }

   @Override
   protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
      BlockEntity _snowman = world.getBlockEntity(pos);
      if (_snowman instanceof FurnaceBlockEntity) {
         player.openHandledScreen((NamedScreenHandlerFactory)_snowman);
         player.incrementStat(Stats.INTERACT_WITH_FURNACE);
      }
   }

   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      if (state.get(LIT)) {
         double _snowman = (double)pos.getX() + 0.5;
         double _snowmanx = (double)pos.getY();
         double _snowmanxx = (double)pos.getZ() + 0.5;
         if (random.nextDouble() < 0.1) {
            world.playSound(_snowman, _snowmanx, _snowmanxx, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
         }

         Direction _snowmanxxx = state.get(FACING);
         Direction.Axis _snowmanxxxx = _snowmanxxx.getAxis();
         double _snowmanxxxxx = 0.52;
         double _snowmanxxxxxx = random.nextDouble() * 0.6 - 0.3;
         double _snowmanxxxxxxx = _snowmanxxxx == Direction.Axis.X ? (double)_snowmanxxx.getOffsetX() * 0.52 : _snowmanxxxxxx;
         double _snowmanxxxxxxxx = random.nextDouble() * 6.0 / 16.0;
         double _snowmanxxxxxxxxx = _snowmanxxxx == Direction.Axis.Z ? (double)_snowmanxxx.getOffsetZ() * 0.52 : _snowmanxxxxxx;
         world.addParticle(ParticleTypes.SMOKE, _snowman + _snowmanxxxxxxx, _snowmanx + _snowmanxxxxxxxx, _snowmanxx + _snowmanxxxxxxxxx, 0.0, 0.0, 0.0);
         world.addParticle(ParticleTypes.FLAME, _snowman + _snowmanxxxxxxx, _snowmanx + _snowmanxxxxxxxx, _snowmanxx + _snowmanxxxxxxxxx, 0.0, 0.0, 0.0);
      }
   }
}
