package net.minecraft.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ItemUsage {
   public static TypedActionResult<ItemStack> consumeHeldItem(World _snowman, PlayerEntity _snowman, Hand _snowman) {
      _snowman.setCurrentHand(_snowman);
      return TypedActionResult.consume(_snowman.getStackInHand(_snowman));
   }

   public static ItemStack method_30270(ItemStack _snowman, PlayerEntity _snowman, ItemStack _snowman, boolean _snowman) {
      boolean _snowmanxxxx = _snowman.abilities.creativeMode;
      if (_snowman && _snowmanxxxx) {
         if (!_snowman.inventory.contains(_snowman)) {
            _snowman.inventory.insertStack(_snowman);
         }

         return _snowman;
      } else {
         if (!_snowmanxxxx) {
            _snowman.decrement(1);
         }

         if (_snowman.isEmpty()) {
            return _snowman;
         } else {
            if (!_snowman.inventory.insertStack(_snowman)) {
               _snowman.dropItem(_snowman, false);
            }

            return _snowman;
         }
      }
   }

   public static ItemStack method_30012(ItemStack _snowman, PlayerEntity _snowman, ItemStack _snowman) {
      return method_30270(_snowman, _snowman, _snowman, true);
   }
}
