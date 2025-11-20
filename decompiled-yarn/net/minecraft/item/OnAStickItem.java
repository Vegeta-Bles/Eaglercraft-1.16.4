package net.minecraft.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemSteerable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class OnAStickItem<T extends Entity & ItemSteerable> extends Item {
   private final EntityType<T> target;
   private final int damagePerUse;

   public OnAStickItem(Item.Settings settings, EntityType<T> target, int damagePerUse) {
      super(settings);
      this.target = target;
      this.damagePerUse = damagePerUse;
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack _snowman = user.getStackInHand(hand);
      if (world.isClient) {
         return TypedActionResult.pass(_snowman);
      } else {
         Entity _snowmanx = user.getVehicle();
         if (user.hasVehicle() && _snowmanx instanceof ItemSteerable && _snowmanx.getType() == this.target) {
            ItemSteerable _snowmanxx = (ItemSteerable)_snowmanx;
            if (_snowmanxx.consumeOnAStickItem()) {
               _snowman.damage(this.damagePerUse, user, p -> p.sendToolBreakStatus(hand));
               if (_snowman.isEmpty()) {
                  ItemStack _snowmanxxx = new ItemStack(Items.FISHING_ROD);
                  _snowmanxxx.setTag(_snowman.getTag());
                  return TypedActionResult.success(_snowmanxxx);
               }

               return TypedActionResult.success(_snowman);
            }
         }

         user.incrementStat(Stats.USED.getOrCreateStat(this));
         return TypedActionResult.pass(_snowman);
      }
   }
}
