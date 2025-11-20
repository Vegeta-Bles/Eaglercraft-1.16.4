package net.minecraft.block;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class PumpkinBlock extends GourdBlock {
   protected PumpkinBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      ItemStack _snowman = player.getStackInHand(hand);
      if (_snowman.getItem() == Items.SHEARS) {
         if (!world.isClient) {
            Direction _snowmanx = hit.getSide();
            Direction _snowmanxx = _snowmanx.getAxis() == Direction.Axis.Y ? player.getHorizontalFacing().getOpposite() : _snowmanx;
            world.playSound(null, pos, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.setBlockState(pos, Blocks.CARVED_PUMPKIN.getDefaultState().with(CarvedPumpkinBlock.FACING, _snowmanxx), 11);
            ItemEntity _snowmanxxx = new ItemEntity(
               world,
               (double)pos.getX() + 0.5 + (double)_snowmanxx.getOffsetX() * 0.65,
               (double)pos.getY() + 0.1,
               (double)pos.getZ() + 0.5 + (double)_snowmanxx.getOffsetZ() * 0.65,
               new ItemStack(Items.PUMPKIN_SEEDS, 4)
            );
            _snowmanxxx.setVelocity(
               0.05 * (double)_snowmanxx.getOffsetX() + world.random.nextDouble() * 0.02, 0.05, 0.05 * (double)_snowmanxx.getOffsetZ() + world.random.nextDouble() * 0.02
            );
            world.spawnEntity(_snowmanxxx);
            _snowman.damage(1, player, _snowmanxxxx -> _snowmanxxxx.sendToolBreakStatus(hand));
         }

         return ActionResult.success(world.isClient);
      } else {
         return super.onUse(state, world, pos, player, hand, hit);
      }
   }

   @Override
   public StemBlock getStem() {
      return (StemBlock)Blocks.PUMPKIN_STEM;
   }

   @Override
   public AttachedStemBlock getAttachedStem() {
      return (AttachedStemBlock)Blocks.ATTACHED_PUMPKIN_STEM;
   }
}
