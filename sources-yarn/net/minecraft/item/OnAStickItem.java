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
      ItemStack lv = user.getStackInHand(hand);
      if (world.isClient) {
         return TypedActionResult.pass(lv);
      } else {
         Entity lv2 = user.getVehicle();
         if (user.hasVehicle() && lv2 instanceof ItemSteerable && lv2.getType() == this.target) {
            ItemSteerable lv3 = (ItemSteerable)lv2;
            if (lv3.consumeOnAStickItem()) {
               lv.damage(this.damagePerUse, user, p -> p.sendToolBreakStatus(hand));
               if (lv.isEmpty()) {
                  ItemStack lv4 = new ItemStack(Items.FISHING_ROD);
                  lv4.setTag(lv.getTag());
                  return TypedActionResult.success(lv4);
               }

               return TypedActionResult.success(lv);
            }
         }

         user.incrementStat(Stats.USED.getOrCreateStat(this));
         return TypedActionResult.pass(lv);
      }
   }
}
