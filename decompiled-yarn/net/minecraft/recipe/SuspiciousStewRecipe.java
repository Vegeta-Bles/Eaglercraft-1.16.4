package net.minecraft.recipe;

import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class SuspiciousStewRecipe extends SpecialCraftingRecipe {
   public SuspiciousStewRecipe(Identifier _snowman) {
      super(_snowman);
   }

   public boolean matches(CraftingInventory _snowman, World _snowman) {
      boolean _snowmanxx = false;
      boolean _snowmanxxx = false;
      boolean _snowmanxxxx = false;
      boolean _snowmanxxxxx = false;

      for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowman.size(); _snowmanxxxxxx++) {
         ItemStack _snowmanxxxxxxx = _snowman.getStack(_snowmanxxxxxx);
         if (!_snowmanxxxxxxx.isEmpty()) {
            if (_snowmanxxxxxxx.getItem() == Blocks.BROWN_MUSHROOM.asItem() && !_snowmanxxxx) {
               _snowmanxxxx = true;
            } else if (_snowmanxxxxxxx.getItem() == Blocks.RED_MUSHROOM.asItem() && !_snowmanxxx) {
               _snowmanxxx = true;
            } else if (_snowmanxxxxxxx.getItem().isIn(ItemTags.SMALL_FLOWERS) && !_snowmanxx) {
               _snowmanxx = true;
            } else {
               if (_snowmanxxxxxxx.getItem() != Items.BOWL || _snowmanxxxxx) {
                  return false;
               }

               _snowmanxxxxx = true;
            }
         }
      }

      return _snowmanxx && _snowmanxxxx && _snowmanxxx && _snowmanxxxxx;
   }

   public ItemStack craft(CraftingInventory _snowman) {
      ItemStack _snowmanx = ItemStack.EMPTY;

      for (int _snowmanxx = 0; _snowmanxx < _snowman.size(); _snowmanxx++) {
         ItemStack _snowmanxxx = _snowman.getStack(_snowmanxx);
         if (!_snowmanxxx.isEmpty() && _snowmanxxx.getItem().isIn(ItemTags.SMALL_FLOWERS)) {
            _snowmanx = _snowmanxxx;
            break;
         }
      }

      ItemStack _snowmanxxx = new ItemStack(Items.SUSPICIOUS_STEW, 1);
      if (_snowmanx.getItem() instanceof BlockItem && ((BlockItem)_snowmanx.getItem()).getBlock() instanceof FlowerBlock) {
         FlowerBlock _snowmanxxxx = (FlowerBlock)((BlockItem)_snowmanx.getItem()).getBlock();
         StatusEffect _snowmanxxxxx = _snowmanxxxx.getEffectInStew();
         SuspiciousStewItem.addEffectToStew(_snowmanxxx, _snowmanxxxxx, _snowmanxxxx.getEffectInStewDuration());
      }

      return _snowmanxxx;
   }

   @Override
   public boolean fits(int width, int height) {
      return width >= 2 && height >= 2;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.SUSPICIOUS_STEW;
   }
}
