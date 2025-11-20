package net.minecraft.client.options;

import com.google.common.collect.ForwardingList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.collection.DefaultedList;

@Environment(EnvType.CLIENT)
public class HotbarStorageEntry extends ForwardingList<ItemStack> {
   private final DefaultedList<ItemStack> delegate = DefaultedList.ofSize(PlayerInventory.getHotbarSize(), ItemStack.EMPTY);

   public HotbarStorageEntry() {
   }

   protected List<ItemStack> delegate() {
      return this.delegate;
   }

   public ListTag toListTag() {
      ListTag lv = new ListTag();

      for (ItemStack lv2 : this.delegate()) {
         lv.add(lv2.toTag(new CompoundTag()));
      }

      return lv;
   }

   public void fromListTag(ListTag arg) {
      List<ItemStack> list = this.delegate();

      for (int i = 0; i < list.size(); i++) {
         list.set(i, ItemStack.fromTag(arg.getCompound(i)));
      }
   }

   public boolean isEmpty() {
      for (ItemStack lv : this.delegate()) {
         if (!lv.isEmpty()) {
            return false;
         }
      }

      return true;
   }
}
