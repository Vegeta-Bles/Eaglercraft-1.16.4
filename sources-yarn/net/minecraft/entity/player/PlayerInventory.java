package net.minecraft.entity.player;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Predicate;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.tag.Tag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.world.World;

public class PlayerInventory implements Inventory, Nameable {
   public final DefaultedList<ItemStack> main = DefaultedList.ofSize(36, ItemStack.EMPTY);
   public final DefaultedList<ItemStack> armor = DefaultedList.ofSize(4, ItemStack.EMPTY);
   public final DefaultedList<ItemStack> offHand = DefaultedList.ofSize(1, ItemStack.EMPTY);
   private final List<DefaultedList<ItemStack>> combinedInventory = ImmutableList.of(this.main, this.armor, this.offHand);
   public int selectedSlot;
   public final PlayerEntity player;
   private ItemStack cursorStack = ItemStack.EMPTY;
   private int changeCount;

   public PlayerInventory(PlayerEntity player) {
      this.player = player;
   }

   public ItemStack getMainHandStack() {
      return isValidHotbarIndex(this.selectedSlot) ? this.main.get(this.selectedSlot) : ItemStack.EMPTY;
   }

   public static int getHotbarSize() {
      return 9;
   }

   private boolean canStackAddMore(ItemStack existingStack, ItemStack stack) {
      return !existingStack.isEmpty()
         && this.areItemsEqual(existingStack, stack)
         && existingStack.isStackable()
         && existingStack.getCount() < existingStack.getMaxCount()
         && existingStack.getCount() < this.getMaxCountPerStack();
   }

   private boolean areItemsEqual(ItemStack stack1, ItemStack stack2) {
      return stack1.getItem() == stack2.getItem() && ItemStack.areTagsEqual(stack1, stack2);
   }

   public int getEmptySlot() {
      for (int i = 0; i < this.main.size(); i++) {
         if (this.main.get(i).isEmpty()) {
            return i;
         }
      }

      return -1;
   }

   @Environment(EnvType.CLIENT)
   public void addPickBlock(ItemStack stack) {
      int i = this.getSlotWithStack(stack);
      if (isValidHotbarIndex(i)) {
         this.selectedSlot = i;
      } else {
         if (i == -1) {
            this.selectedSlot = this.getSwappableHotbarSlot();
            if (!this.main.get(this.selectedSlot).isEmpty()) {
               int j = this.getEmptySlot();
               if (j != -1) {
                  this.main.set(j, this.main.get(this.selectedSlot));
               }
            }

            this.main.set(this.selectedSlot, stack);
         } else {
            this.swapSlotWithHotbar(i);
         }
      }
   }

   public void swapSlotWithHotbar(int hotbarSlot) {
      this.selectedSlot = this.getSwappableHotbarSlot();
      ItemStack lv = this.main.get(this.selectedSlot);
      this.main.set(this.selectedSlot, this.main.get(hotbarSlot));
      this.main.set(hotbarSlot, lv);
   }

   public static boolean isValidHotbarIndex(int slot) {
      return slot >= 0 && slot < 9;
   }

   @Environment(EnvType.CLIENT)
   public int getSlotWithStack(ItemStack stack) {
      for (int i = 0; i < this.main.size(); i++) {
         if (!this.main.get(i).isEmpty() && this.areItemsEqual(stack, this.main.get(i))) {
            return i;
         }
      }

      return -1;
   }

   public int method_7371(ItemStack arg) {
      for (int i = 0; i < this.main.size(); i++) {
         ItemStack lv = this.main.get(i);
         if (!this.main.get(i).isEmpty()
            && this.areItemsEqual(arg, this.main.get(i))
            && !this.main.get(i).isDamaged()
            && !lv.hasEnchantments()
            && !lv.hasCustomName()) {
            return i;
         }
      }

      return -1;
   }

   public int getSwappableHotbarSlot() {
      for (int i = 0; i < 9; i++) {
         int j = (this.selectedSlot + i) % 9;
         if (this.main.get(j).isEmpty()) {
            return j;
         }
      }

      for (int k = 0; k < 9; k++) {
         int l = (this.selectedSlot + k) % 9;
         if (!this.main.get(l).hasEnchantments()) {
            return l;
         }
      }

      return this.selectedSlot;
   }

   @Environment(EnvType.CLIENT)
   public void scrollInHotbar(double scrollAmount) {
      if (scrollAmount > 0.0) {
         scrollAmount = 1.0;
      }

      if (scrollAmount < 0.0) {
         scrollAmount = -1.0;
      }

      this.selectedSlot = (int)((double)this.selectedSlot - scrollAmount);

      while (this.selectedSlot < 0) {
         this.selectedSlot += 9;
      }

      while (this.selectedSlot >= 9) {
         this.selectedSlot -= 9;
      }
   }

   public int remove(Predicate<ItemStack> shouldRemove, int maxCount, Inventory craftingInventory) {
      int j = 0;
      boolean bl = maxCount == 0;
      j += Inventories.remove(this, shouldRemove, maxCount - j, bl);
      j += Inventories.remove(craftingInventory, shouldRemove, maxCount - j, bl);
      j += Inventories.remove(this.cursorStack, shouldRemove, maxCount - j, bl);
      if (this.cursorStack.isEmpty()) {
         this.cursorStack = ItemStack.EMPTY;
      }

      return j;
   }

   private int addStack(ItemStack stack) {
      int i = this.getOccupiedSlotWithRoomForStack(stack);
      if (i == -1) {
         i = this.getEmptySlot();
      }

      return i == -1 ? stack.getCount() : this.addStack(i, stack);
   }

   private int addStack(int slot, ItemStack stack) {
      Item lv = stack.getItem();
      int j = stack.getCount();
      ItemStack lv2 = this.getStack(slot);
      if (lv2.isEmpty()) {
         lv2 = new ItemStack(lv, 0);
         if (stack.hasTag()) {
            lv2.setTag(stack.getTag().copy());
         }

         this.setStack(slot, lv2);
      }

      int k = j;
      if (j > lv2.getMaxCount() - lv2.getCount()) {
         k = lv2.getMaxCount() - lv2.getCount();
      }

      if (k > this.getMaxCountPerStack() - lv2.getCount()) {
         k = this.getMaxCountPerStack() - lv2.getCount();
      }

      if (k == 0) {
         return j;
      } else {
         j -= k;
         lv2.increment(k);
         lv2.setCooldown(5);
         return j;
      }
   }

   public int getOccupiedSlotWithRoomForStack(ItemStack stack) {
      if (this.canStackAddMore(this.getStack(this.selectedSlot), stack)) {
         return this.selectedSlot;
      } else if (this.canStackAddMore(this.getStack(40), stack)) {
         return 40;
      } else {
         for (int i = 0; i < this.main.size(); i++) {
            if (this.canStackAddMore(this.main.get(i), stack)) {
               return i;
            }
         }

         return -1;
      }
   }

   public void updateItems() {
      for (DefaultedList<ItemStack> lv : this.combinedInventory) {
         for (int i = 0; i < lv.size(); i++) {
            if (!lv.get(i).isEmpty()) {
               lv.get(i).inventoryTick(this.player.world, this.player, i, this.selectedSlot == i);
            }
         }
      }
   }

   public boolean insertStack(ItemStack stack) {
      return this.insertStack(-1, stack);
   }

   public boolean insertStack(int slot, ItemStack stack) {
      if (stack.isEmpty()) {
         return false;
      } else {
         try {
            if (stack.isDamaged()) {
               if (slot == -1) {
                  slot = this.getEmptySlot();
               }

               if (slot >= 0) {
                  this.main.set(slot, stack.copy());
                  this.main.get(slot).setCooldown(5);
                  stack.setCount(0);
                  return true;
               } else if (this.player.abilities.creativeMode) {
                  stack.setCount(0);
                  return true;
               } else {
                  return false;
               }
            } else {
               int j;
               do {
                  j = stack.getCount();
                  if (slot == -1) {
                     stack.setCount(this.addStack(stack));
                  } else {
                     stack.setCount(this.addStack(slot, stack));
                  }
               } while (!stack.isEmpty() && stack.getCount() < j);

               if (stack.getCount() == j && this.player.abilities.creativeMode) {
                  stack.setCount(0);
                  return true;
               } else {
                  return stack.getCount() < j;
               }
            }
         } catch (Throwable var6) {
            CrashReport lv = CrashReport.create(var6, "Adding item to inventory");
            CrashReportSection lv2 = lv.addElement("Item being added");
            lv2.add("Item ID", Item.getRawId(stack.getItem()));
            lv2.add("Item data", stack.getDamage());
            lv2.add("Item name", () -> stack.getName().getString());
            throw new CrashException(lv);
         }
      }
   }

   public void offerOrDrop(World world, ItemStack stack) {
      if (!world.isClient) {
         while (!stack.isEmpty()) {
            int i = this.getOccupiedSlotWithRoomForStack(stack);
            if (i == -1) {
               i = this.getEmptySlot();
            }

            if (i == -1) {
               this.player.dropItem(stack, false);
               break;
            }

            int j = stack.getMaxCount() - this.getStack(i).getCount();
            if (this.insertStack(i, stack.split(j))) {
               ((ServerPlayerEntity)this.player).networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(-2, i, this.getStack(i)));
            }
         }
      }
   }

   @Override
   public ItemStack removeStack(int slot, int amount) {
      List<ItemStack> list = null;

      for (DefaultedList<ItemStack> lv : this.combinedInventory) {
         if (slot < lv.size()) {
            list = lv;
            break;
         }

         slot -= lv.size();
      }

      return list != null && !list.get(slot).isEmpty() ? Inventories.splitStack(list, slot, amount) : ItemStack.EMPTY;
   }

   public void removeOne(ItemStack stack) {
      for (DefaultedList<ItemStack> lv : this.combinedInventory) {
         for (int i = 0; i < lv.size(); i++) {
            if (lv.get(i) == stack) {
               lv.set(i, ItemStack.EMPTY);
               break;
            }
         }
      }
   }

   @Override
   public ItemStack removeStack(int slot) {
      DefaultedList<ItemStack> lv = null;

      for (DefaultedList<ItemStack> lv2 : this.combinedInventory) {
         if (slot < lv2.size()) {
            lv = lv2;
            break;
         }

         slot -= lv2.size();
      }

      if (lv != null && !lv.get(slot).isEmpty()) {
         ItemStack lv3 = lv.get(slot);
         lv.set(slot, ItemStack.EMPTY);
         return lv3;
      } else {
         return ItemStack.EMPTY;
      }
   }

   @Override
   public void setStack(int slot, ItemStack stack) {
      DefaultedList<ItemStack> lv = null;

      for (DefaultedList<ItemStack> lv2 : this.combinedInventory) {
         if (slot < lv2.size()) {
            lv = lv2;
            break;
         }

         slot -= lv2.size();
      }

      if (lv != null) {
         lv.set(slot, stack);
      }
   }

   public float getBlockBreakingSpeed(BlockState block) {
      return this.main.get(this.selectedSlot).getMiningSpeedMultiplier(block);
   }

   public ListTag serialize(ListTag tag) {
      for (int i = 0; i < this.main.size(); i++) {
         if (!this.main.get(i).isEmpty()) {
            CompoundTag lv = new CompoundTag();
            lv.putByte("Slot", (byte)i);
            this.main.get(i).toTag(lv);
            tag.add(lv);
         }
      }

      for (int j = 0; j < this.armor.size(); j++) {
         if (!this.armor.get(j).isEmpty()) {
            CompoundTag lv2 = new CompoundTag();
            lv2.putByte("Slot", (byte)(j + 100));
            this.armor.get(j).toTag(lv2);
            tag.add(lv2);
         }
      }

      for (int k = 0; k < this.offHand.size(); k++) {
         if (!this.offHand.get(k).isEmpty()) {
            CompoundTag lv3 = new CompoundTag();
            lv3.putByte("Slot", (byte)(k + 150));
            this.offHand.get(k).toTag(lv3);
            tag.add(lv3);
         }
      }

      return tag;
   }

   public void deserialize(ListTag tag) {
      this.main.clear();
      this.armor.clear();
      this.offHand.clear();

      for (int i = 0; i < tag.size(); i++) {
         CompoundTag lv = tag.getCompound(i);
         int j = lv.getByte("Slot") & 255;
         ItemStack lv2 = ItemStack.fromTag(lv);
         if (!lv2.isEmpty()) {
            if (j >= 0 && j < this.main.size()) {
               this.main.set(j, lv2);
            } else if (j >= 100 && j < this.armor.size() + 100) {
               this.armor.set(j - 100, lv2);
            } else if (j >= 150 && j < this.offHand.size() + 150) {
               this.offHand.set(j - 150, lv2);
            }
         }
      }
   }

   @Override
   public int size() {
      return this.main.size() + this.armor.size() + this.offHand.size();
   }

   @Override
   public boolean isEmpty() {
      for (ItemStack lv : this.main) {
         if (!lv.isEmpty()) {
            return false;
         }
      }

      for (ItemStack lv2 : this.armor) {
         if (!lv2.isEmpty()) {
            return false;
         }
      }

      for (ItemStack lv3 : this.offHand) {
         if (!lv3.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public ItemStack getStack(int slot) {
      List<ItemStack> list = null;

      for (DefaultedList<ItemStack> lv : this.combinedInventory) {
         if (slot < lv.size()) {
            list = lv;
            break;
         }

         slot -= lv.size();
      }

      return list == null ? ItemStack.EMPTY : list.get(slot);
   }

   @Override
   public Text getName() {
      return new TranslatableText("container.inventory");
   }

   @Environment(EnvType.CLIENT)
   public ItemStack getArmorStack(int slot) {
      return this.armor.get(slot);
   }

   public void damageArmor(DamageSource damageSource, float f) {
      if (!(f <= 0.0F)) {
         f /= 4.0F;
         if (f < 1.0F) {
            f = 1.0F;
         }

         for (int i = 0; i < this.armor.size(); i++) {
            ItemStack lv = this.armor.get(i);
            if ((!damageSource.isFire() || !lv.getItem().isFireproof()) && lv.getItem() instanceof ArmorItem) {
               int j = i;
               lv.damage((int)f, this.player, arg -> arg.sendEquipmentBreakStatus(EquipmentSlot.fromTypeIndex(EquipmentSlot.Type.ARMOR, j)));
            }
         }
      }
   }

   public void dropAll() {
      for (List<ItemStack> list : this.combinedInventory) {
         for (int i = 0; i < list.size(); i++) {
            ItemStack lv = list.get(i);
            if (!lv.isEmpty()) {
               this.player.dropItem(lv, true, false);
               list.set(i, ItemStack.EMPTY);
            }
         }
      }
   }

   @Override
   public void markDirty() {
      this.changeCount++;
   }

   @Environment(EnvType.CLIENT)
   public int getChangeCount() {
      return this.changeCount;
   }

   public void setCursorStack(ItemStack stack) {
      this.cursorStack = stack;
   }

   public ItemStack getCursorStack() {
      return this.cursorStack;
   }

   @Override
   public boolean canPlayerUse(PlayerEntity player) {
      return this.player.removed ? false : !(player.squaredDistanceTo(this.player) > 64.0);
   }

   public boolean contains(ItemStack stack) {
      for (List<ItemStack> list : this.combinedInventory) {
         for (ItemStack lv : list) {
            if (!lv.isEmpty() && lv.isItemEqualIgnoreDamage(stack)) {
               return true;
            }
         }
      }

      return false;
   }

   @Environment(EnvType.CLIENT)
   public boolean contains(Tag<Item> tag) {
      for (List<ItemStack> list : this.combinedInventory) {
         for (ItemStack lv : list) {
            if (!lv.isEmpty() && tag.contains(lv.getItem())) {
               return true;
            }
         }
      }

      return false;
   }

   public void clone(PlayerInventory other) {
      for (int i = 0; i < this.size(); i++) {
         this.setStack(i, other.getStack(i));
      }

      this.selectedSlot = other.selectedSlot;
   }

   @Override
   public void clear() {
      for (List<ItemStack> list : this.combinedInventory) {
         list.clear();
      }
   }

   public void populateRecipeFinder(RecipeFinder finder) {
      for (ItemStack lv : this.main) {
         finder.addNormalItem(lv);
      }
   }
}
