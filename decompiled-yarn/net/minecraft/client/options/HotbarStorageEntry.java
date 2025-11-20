package net.minecraft.client.options;

import com.google.common.collect.ForwardingList;
import java.util.List;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.collection.DefaultedList;

public class HotbarStorageEntry extends ForwardingList<ItemStack> {
   private final DefaultedList<ItemStack> delegate = DefaultedList.ofSize(PlayerInventory.getHotbarSize(), ItemStack.EMPTY);

   public HotbarStorageEntry() {
   }

   protected List<ItemStack> delegate() {
      return this.delegate;
   }

   public ListTag toListTag() {
      ListTag _snowman = new ListTag();

      for (ItemStack _snowmanx : this.delegate()) {
         _snowman.add(_snowmanx.toTag(new CompoundTag()));
      }

      return _snowman;
   }

   public void fromListTag(ListTag _snowman) {
      List<ItemStack> _snowmanx = this.delegate();

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
         _snowmanx.set(_snowmanxx, ItemStack.fromTag(_snowman.getCompound(_snowmanxx)));
      }
   }

   public boolean isEmpty() {
      for (ItemStack _snowman : this.delegate()) {
         if (!_snowman.isEmpty()) {
            return false;
         }
      }

      return true;
   }
}
