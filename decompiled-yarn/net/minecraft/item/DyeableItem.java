package net.minecraft.item;

import java.util.List;
import net.minecraft.nbt.CompoundTag;

public interface DyeableItem {
   default boolean hasColor(ItemStack stack) {
      CompoundTag _snowman = stack.getSubTag("display");
      return _snowman != null && _snowman.contains("color", 99);
   }

   default int getColor(ItemStack stack) {
      CompoundTag _snowman = stack.getSubTag("display");
      return _snowman != null && _snowman.contains("color", 99) ? _snowman.getInt("color") : 10511680;
   }

   default void removeColor(ItemStack stack) {
      CompoundTag _snowman = stack.getSubTag("display");
      if (_snowman != null && _snowman.contains("color")) {
         _snowman.remove("color");
      }
   }

   default void setColor(ItemStack stack, int color) {
      stack.getOrCreateSubTag("display").putInt("color", color);
   }

   static ItemStack blendAndSetColor(ItemStack stack, List<DyeItem> colors) {
      ItemStack _snowman = ItemStack.EMPTY;
      int[] _snowmanx = new int[3];
      int _snowmanxx = 0;
      int _snowmanxxx = 0;
      DyeableItem _snowmanxxxx = null;
      Item _snowmanxxxxx = stack.getItem();
      if (_snowmanxxxxx instanceof DyeableItem) {
         _snowmanxxxx = (DyeableItem)_snowmanxxxxx;
         _snowman = stack.copy();
         _snowman.setCount(1);
         if (_snowmanxxxx.hasColor(stack)) {
            int _snowmanxxxxxx = _snowmanxxxx.getColor(_snowman);
            float _snowmanxxxxxxx = (float)(_snowmanxxxxxx >> 16 & 0xFF) / 255.0F;
            float _snowmanxxxxxxxx = (float)(_snowmanxxxxxx >> 8 & 0xFF) / 255.0F;
            float _snowmanxxxxxxxxx = (float)(_snowmanxxxxxx & 0xFF) / 255.0F;
            _snowmanxx = (int)((float)_snowmanxx + Math.max(_snowmanxxxxxxx, Math.max(_snowmanxxxxxxxx, _snowmanxxxxxxxxx)) * 255.0F);
            _snowmanx[0] = (int)((float)_snowmanx[0] + _snowmanxxxxxxx * 255.0F);
            _snowmanx[1] = (int)((float)_snowmanx[1] + _snowmanxxxxxxxx * 255.0F);
            _snowmanx[2] = (int)((float)_snowmanx[2] + _snowmanxxxxxxxxx * 255.0F);
            _snowmanxxx++;
         }

         for (DyeItem _snowmanxxxxxx : colors) {
            float[] _snowmanxxxxxxx = _snowmanxxxxxx.getColor().getColorComponents();
            int _snowmanxxxxxxxx = (int)(_snowmanxxxxxxx[0] * 255.0F);
            int _snowmanxxxxxxxxx = (int)(_snowmanxxxxxxx[1] * 255.0F);
            int _snowmanxxxxxxxxxx = (int)(_snowmanxxxxxxx[2] * 255.0F);
            _snowmanxx += Math.max(_snowmanxxxxxxxx, Math.max(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx));
            _snowmanx[0] += _snowmanxxxxxxxx;
            _snowmanx[1] += _snowmanxxxxxxxxx;
            _snowmanx[2] += _snowmanxxxxxxxxxx;
            _snowmanxxx++;
         }
      }

      if (_snowmanxxxx == null) {
         return ItemStack.EMPTY;
      } else {
         int _snowmanxxxxxx = _snowmanx[0] / _snowmanxxx;
         int _snowmanxxxxxxx = _snowmanx[1] / _snowmanxxx;
         int _snowmanxxxxxxxx = _snowmanx[2] / _snowmanxxx;
         float _snowmanxxxxxxxxx = (float)_snowmanxx / (float)_snowmanxxx;
         float _snowmanxxxxxxxxxx = (float)Math.max(_snowmanxxxxxx, Math.max(_snowmanxxxxxxx, _snowmanxxxxxxxx));
         _snowmanxxxxxx = (int)((float)_snowmanxxxxxx * _snowmanxxxxxxxxx / _snowmanxxxxxxxxxx);
         _snowmanxxxxxxx = (int)((float)_snowmanxxxxxxx * _snowmanxxxxxxxxx / _snowmanxxxxxxxxxx);
         _snowmanxxxxxxxx = (int)((float)_snowmanxxxxxxxx * _snowmanxxxxxxxxx / _snowmanxxxxxxxxxx);
         int var26 = (_snowmanxxxxxx << 8) + _snowmanxxxxxxx;
         var26 = (var26 << 8) + _snowmanxxxxxxxx;
         _snowmanxxxx.setColor(_snowman, var26);
         return _snowman;
      }
   }
}
