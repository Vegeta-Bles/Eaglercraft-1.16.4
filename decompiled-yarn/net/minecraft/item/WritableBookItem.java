package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LecternBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WritableBookItem extends Item {
   public WritableBookItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public ActionResult useOnBlock(ItemUsageContext context) {
      World _snowman = context.getWorld();
      BlockPos _snowmanx = context.getBlockPos();
      BlockState _snowmanxx = _snowman.getBlockState(_snowmanx);
      if (_snowmanxx.isOf(Blocks.LECTERN)) {
         return LecternBlock.putBookIfAbsent(_snowman, _snowmanx, _snowmanxx, context.getStack()) ? ActionResult.success(_snowman.isClient) : ActionResult.PASS;
      } else {
         return ActionResult.PASS;
      }
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack _snowman = user.getStackInHand(hand);
      user.openEditBookScreen(_snowman, hand);
      user.incrementStat(Stats.USED.getOrCreateStat(this));
      return TypedActionResult.success(_snowman, world.isClient());
   }

   public static boolean isValid(@Nullable CompoundTag tag) {
      if (tag == null) {
         return false;
      } else if (!tag.contains("pages", 9)) {
         return false;
      } else {
         ListTag _snowman = tag.getList("pages", 8);

         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            String _snowmanxx = _snowman.getString(_snowmanx);
            if (_snowmanxx.length() > 32767) {
               return false;
            }
         }

         return true;
      }
   }
}
