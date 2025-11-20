package net.minecraft.item;

import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ElytraItem extends Item implements Wearable {
   public ElytraItem(Item.Settings _snowman) {
      super(_snowman);
      DispenserBlock.registerBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
   }

   public static boolean isUsable(ItemStack stack) {
      return stack.getDamage() < stack.getMaxDamage() - 1;
   }

   @Override
   public boolean canRepair(ItemStack stack, ItemStack ingredient) {
      return ingredient.getItem() == Items.PHANTOM_MEMBRANE;
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack _snowman = user.getStackInHand(hand);
      EquipmentSlot _snowmanx = MobEntity.getPreferredEquipmentSlot(_snowman);
      ItemStack _snowmanxx = user.getEquippedStack(_snowmanx);
      if (_snowmanxx.isEmpty()) {
         user.equipStack(_snowmanx, _snowman.copy());
         _snowman.setCount(0);
         return TypedActionResult.success(_snowman, world.isClient());
      } else {
         return TypedActionResult.fail(_snowman);
      }
   }
}
