package net.minecraft.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EmptyMapItem extends NetworkSyncedItem {
   public EmptyMapItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack _snowman = FilledMapItem.createMap(world, MathHelper.floor(user.getX()), MathHelper.floor(user.getZ()), (byte)0, true, false);
      ItemStack _snowmanx = user.getStackInHand(hand);
      if (!user.abilities.creativeMode) {
         _snowmanx.decrement(1);
      }

      user.incrementStat(Stats.USED.getOrCreateStat(this));
      user.playSound(SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 1.0F, 1.0F);
      if (_snowmanx.isEmpty()) {
         return TypedActionResult.success(_snowman, world.isClient());
      } else {
         if (!user.inventory.insertStack(_snowman.copy())) {
            user.dropItem(_snowman, false);
         }

         return TypedActionResult.success(_snowmanx, world.isClient());
      }
   }
}
