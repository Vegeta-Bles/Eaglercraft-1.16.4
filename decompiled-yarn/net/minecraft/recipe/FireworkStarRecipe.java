package net.minecraft.recipe;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.FireworkItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.World;

public class FireworkStarRecipe extends SpecialCraftingRecipe {
   private static final Ingredient TYPE_MODIFIER = Ingredient.ofItems(
      Items.FIRE_CHARGE,
      Items.FEATHER,
      Items.GOLD_NUGGET,
      Items.SKELETON_SKULL,
      Items.WITHER_SKELETON_SKULL,
      Items.CREEPER_HEAD,
      Items.PLAYER_HEAD,
      Items.DRAGON_HEAD,
      Items.ZOMBIE_HEAD
   );
   private static final Ingredient TRAIL_MODIFIER = Ingredient.ofItems(Items.DIAMOND);
   private static final Ingredient FLICKER_MODIFIER = Ingredient.ofItems(Items.GLOWSTONE_DUST);
   private static final Map<Item, FireworkItem.Type> TYPE_MODIFIER_MAP = Util.make(Maps.newHashMap(), _snowman -> {
      _snowman.put(Items.FIRE_CHARGE, FireworkItem.Type.LARGE_BALL);
      _snowman.put(Items.FEATHER, FireworkItem.Type.BURST);
      _snowman.put(Items.GOLD_NUGGET, FireworkItem.Type.STAR);
      _snowman.put(Items.SKELETON_SKULL, FireworkItem.Type.CREEPER);
      _snowman.put(Items.WITHER_SKELETON_SKULL, FireworkItem.Type.CREEPER);
      _snowman.put(Items.CREEPER_HEAD, FireworkItem.Type.CREEPER);
      _snowman.put(Items.PLAYER_HEAD, FireworkItem.Type.CREEPER);
      _snowman.put(Items.DRAGON_HEAD, FireworkItem.Type.CREEPER);
      _snowman.put(Items.ZOMBIE_HEAD, FireworkItem.Type.CREEPER);
   });
   private static final Ingredient GUNPOWDER = Ingredient.ofItems(Items.GUNPOWDER);

   public FireworkStarRecipe(Identifier _snowman) {
      super(_snowman);
   }

   public boolean matches(CraftingInventory _snowman, World _snowman) {
      boolean _snowmanxx = false;
      boolean _snowmanxxx = false;
      boolean _snowmanxxxx = false;
      boolean _snowmanxxxxx = false;
      boolean _snowmanxxxxxx = false;

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowman.size(); _snowmanxxxxxxx++) {
         ItemStack _snowmanxxxxxxxx = _snowman.getStack(_snowmanxxxxxxx);
         if (!_snowmanxxxxxxxx.isEmpty()) {
            if (TYPE_MODIFIER.test(_snowmanxxxxxxxx)) {
               if (_snowmanxxxx) {
                  return false;
               }

               _snowmanxxxx = true;
            } else if (FLICKER_MODIFIER.test(_snowmanxxxxxxxx)) {
               if (_snowmanxxxxxx) {
                  return false;
               }

               _snowmanxxxxxx = true;
            } else if (TRAIL_MODIFIER.test(_snowmanxxxxxxxx)) {
               if (_snowmanxxxxx) {
                  return false;
               }

               _snowmanxxxxx = true;
            } else if (GUNPOWDER.test(_snowmanxxxxxxxx)) {
               if (_snowmanxx) {
                  return false;
               }

               _snowmanxx = true;
            } else {
               if (!(_snowmanxxxxxxxx.getItem() instanceof DyeItem)) {
                  return false;
               }

               _snowmanxxx = true;
            }
         }
      }

      return _snowmanxx && _snowmanxxx;
   }

   public ItemStack craft(CraftingInventory _snowman) {
      ItemStack _snowmanx = new ItemStack(Items.FIREWORK_STAR);
      CompoundTag _snowmanxx = _snowmanx.getOrCreateSubTag("Explosion");
      FireworkItem.Type _snowmanxxx = FireworkItem.Type.SMALL_BALL;
      List<Integer> _snowmanxxxx = Lists.newArrayList();

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowman.size(); _snowmanxxxxx++) {
         ItemStack _snowmanxxxxxx = _snowman.getStack(_snowmanxxxxx);
         if (!_snowmanxxxxxx.isEmpty()) {
            if (TYPE_MODIFIER.test(_snowmanxxxxxx)) {
               _snowmanxxx = TYPE_MODIFIER_MAP.get(_snowmanxxxxxx.getItem());
            } else if (FLICKER_MODIFIER.test(_snowmanxxxxxx)) {
               _snowmanxx.putBoolean("Flicker", true);
            } else if (TRAIL_MODIFIER.test(_snowmanxxxxxx)) {
               _snowmanxx.putBoolean("Trail", true);
            } else if (_snowmanxxxxxx.getItem() instanceof DyeItem) {
               _snowmanxxxx.add(((DyeItem)_snowmanxxxxxx.getItem()).getColor().getFireworkColor());
            }
         }
      }

      _snowmanxx.putIntArray("Colors", _snowmanxxxx);
      _snowmanxx.putByte("Type", (byte)_snowmanxxx.getId());
      return _snowmanx;
   }

   @Override
   public boolean fits(int width, int height) {
      return width * height >= 2;
   }

   @Override
   public ItemStack getOutput() {
      return new ItemStack(Items.FIREWORK_STAR);
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.FIREWORK_STAR;
   }
}
