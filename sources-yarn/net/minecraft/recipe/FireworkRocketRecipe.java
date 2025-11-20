package net.minecraft.recipe;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class FireworkRocketRecipe extends SpecialCraftingRecipe {
   private static final Ingredient PAPER = Ingredient.ofItems(Items.PAPER);
   private static final Ingredient DURATION_MODIFIER = Ingredient.ofItems(Items.GUNPOWDER);
   private static final Ingredient FIREWORK_STAR = Ingredient.ofItems(Items.FIREWORK_STAR);

   public FireworkRocketRecipe(Identifier arg) {
      super(arg);
   }

   public boolean matches(CraftingInventory arg, World arg2) {
      boolean bl = false;
      int i = 0;

      for (int j = 0; j < arg.size(); j++) {
         ItemStack lv = arg.getStack(j);
         if (!lv.isEmpty()) {
            if (PAPER.test(lv)) {
               if (bl) {
                  return false;
               }

               bl = true;
            } else if (DURATION_MODIFIER.test(lv)) {
               if (++i > 3) {
                  return false;
               }
            } else if (!FIREWORK_STAR.test(lv)) {
               return false;
            }
         }
      }

      return bl && i >= 1;
   }

   public ItemStack craft(CraftingInventory arg) {
      ItemStack lv = new ItemStack(Items.FIREWORK_ROCKET, 3);
      CompoundTag lv2 = lv.getOrCreateSubTag("Fireworks");
      ListTag lv3 = new ListTag();
      int i = 0;

      for (int j = 0; j < arg.size(); j++) {
         ItemStack lv4 = arg.getStack(j);
         if (!lv4.isEmpty()) {
            if (DURATION_MODIFIER.test(lv4)) {
               i++;
            } else if (FIREWORK_STAR.test(lv4)) {
               CompoundTag lv5 = lv4.getSubTag("Explosion");
               if (lv5 != null) {
                  lv3.add(lv5);
               }
            }
         }
      }

      lv2.putByte("Flight", (byte)i);
      if (!lv3.isEmpty()) {
         lv2.put("Explosions", lv3);
      }

      return lv;
   }

   @Environment(EnvType.CLIENT)
   @Override
   public boolean fits(int width, int height) {
      return width * height >= 2;
   }

   @Override
   public ItemStack getOutput() {
      return new ItemStack(Items.FIREWORK_ROCKET);
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.FIREWORK_ROCKET;
   }
}
