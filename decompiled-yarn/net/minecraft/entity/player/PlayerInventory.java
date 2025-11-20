package net.minecraft.entity.player;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Predicate;
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
      for (int _snowman = 0; _snowman < this.main.size(); _snowman++) {
         if (this.main.get(_snowman).isEmpty()) {
            return _snowman;
         }
      }

      return -1;
   }

   public void addPickBlock(ItemStack stack) {
      int _snowman = this.getSlotWithStack(stack);
      if (isValidHotbarIndex(_snowman)) {
         this.selectedSlot = _snowman;
      } else {
         if (_snowman == -1) {
            this.selectedSlot = this.getSwappableHotbarSlot();
            if (!this.main.get(this.selectedSlot).isEmpty()) {
               int _snowmanx = this.getEmptySlot();
               if (_snowmanx != -1) {
                  this.main.set(_snowmanx, this.main.get(this.selectedSlot));
               }
            }

            this.main.set(this.selectedSlot, stack);
         } else {
            this.swapSlotWithHotbar(_snowman);
         }
      }
   }

   public void swapSlotWithHotbar(int hotbarSlot) {
      this.selectedSlot = this.getSwappableHotbarSlot();
      ItemStack _snowman = this.main.get(this.selectedSlot);
      this.main.set(this.selectedSlot, this.main.get(hotbarSlot));
      this.main.set(hotbarSlot, _snowman);
   }

   public static boolean isValidHotbarIndex(int slot) {
      return slot >= 0 && slot < 9;
   }

   public int getSlotWithStack(ItemStack stack) {
      for (int _snowman = 0; _snowman < this.main.size(); _snowman++) {
         if (!this.main.get(_snowman).isEmpty() && this.areItemsEqual(stack, this.main.get(_snowman))) {
            return _snowman;
         }
      }

      return -1;
   }

   public int method_7371(ItemStack _snowman) {
      for (int _snowmanx = 0; _snowmanx < this.main.size(); _snowmanx++) {
         ItemStack _snowmanxx = this.main.get(_snowmanx);
         if (!this.main.get(_snowmanx).isEmpty()
            && this.areItemsEqual(_snowman, this.main.get(_snowmanx))
            && !this.main.get(_snowmanx).isDamaged()
            && !_snowmanxx.hasEnchantments()
            && !_snowmanxx.hasCustomName()) {
            return _snowmanx;
         }
      }

      return -1;
   }

   public int getSwappableHotbarSlot() {
      for (int _snowman = 0; _snowman < 9; _snowman++) {
         int _snowmanx = (this.selectedSlot + _snowman) % 9;
         if (this.main.get(_snowmanx).isEmpty()) {
            return _snowmanx;
         }
      }

      for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
         int _snowmanxx = (this.selectedSlot + _snowmanx) % 9;
         if (!this.main.get(_snowmanxx).hasEnchantments()) {
            return _snowmanxx;
         }
      }

      return this.selectedSlot;
   }

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
      int _snowman = 0;
      boolean _snowmanx = maxCount == 0;
      _snowman += Inventories.remove(this, shouldRemove, maxCount - _snowman, _snowmanx);
      _snowman += Inventories.remove(craftingInventory, shouldRemove, maxCount - _snowman, _snowmanx);
      _snowman += Inventories.remove(this.cursorStack, shouldRemove, maxCount - _snowman, _snowmanx);
      if (this.cursorStack.isEmpty()) {
         this.cursorStack = ItemStack.EMPTY;
      }

      return _snowman;
   }

   private int addStack(ItemStack stack) {
      int _snowman = this.getOccupiedSlotWithRoomForStack(stack);
      if (_snowman == -1) {
         _snowman = this.getEmptySlot();
      }

      return _snowman == -1 ? stack.getCount() : this.addStack(_snowman, stack);
   }

   private int addStack(int slot, ItemStack stack) {
      Item _snowman = stack.getItem();
      int _snowmanx = stack.getCount();
      ItemStack _snowmanxx = this.getStack(slot);
      if (_snowmanxx.isEmpty()) {
         _snowmanxx = new ItemStack(_snowman, 0);
         if (stack.hasTag()) {
            _snowmanxx.setTag(stack.getTag().copy());
         }

         this.setStack(slot, _snowmanxx);
      }

      int _snowmanxxx = _snowmanx;
      if (_snowmanx > _snowmanxx.getMaxCount() - _snowmanxx.getCount()) {
         _snowmanxxx = _snowmanxx.getMaxCount() - _snowmanxx.getCount();
      }

      if (_snowmanxxx > this.getMaxCountPerStack() - _snowmanxx.getCount()) {
         _snowmanxxx = this.getMaxCountPerStack() - _snowmanxx.getCount();
      }

      if (_snowmanxxx == 0) {
         return _snowmanx;
      } else {
         _snowmanx -= _snowmanxxx;
         _snowmanxx.increment(_snowmanxxx);
         _snowmanxx.setCooldown(5);
         return _snowmanx;
      }
   }

   public int getOccupiedSlotWithRoomForStack(ItemStack stack) {
      if (this.canStackAddMore(this.getStack(this.selectedSlot), stack)) {
         return this.selectedSlot;
      } else if (this.canStackAddMore(this.getStack(40), stack)) {
         return 40;
      } else {
         for (int _snowman = 0; _snowman < this.main.size(); _snowman++) {
            if (this.canStackAddMore(this.main.get(_snowman), stack)) {
               return _snowman;
            }
         }

         return -1;
      }
   }

   public void updateItems() {
      for (DefaultedList<ItemStack> _snowman : this.combinedInventory) {
         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            if (!_snowman.get(_snowmanx).isEmpty()) {
               _snowman.get(_snowmanx).inventoryTick(this.player.world, this.player, _snowmanx, this.selectedSlot == _snowmanx);
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
               int _snowman;
               do {
                  _snowman = stack.getCount();
                  if (slot == -1) {
                     stack.setCount(this.addStack(stack));
                  } else {
                     stack.setCount(this.addStack(slot, stack));
                  }
               } while (!stack.isEmpty() && stack.getCount() < _snowman);

               if (stack.getCount() == _snowman && this.player.abilities.creativeMode) {
                  stack.setCount(0);
                  return true;
               } else {
                  return stack.getCount() < _snowman;
               }
            }
         } catch (Throwable var6) {
            CrashReport _snowmanx = CrashReport.create(var6, "Adding item to inventory");
            CrashReportSection _snowmanxx = _snowmanx.addElement("Item being added");
            _snowmanxx.add("Item ID", Item.getRawId(stack.getItem()));
            _snowmanxx.add("Item data", stack.getDamage());
            _snowmanxx.add("Item name", () -> stack.getName().getString());
            throw new CrashException(_snowmanx);
         }
      }
   }

   public void offerOrDrop(World world, ItemStack stack) {
      if (!world.isClient) {
         while (!stack.isEmpty()) {
            int _snowman = this.getOccupiedSlotWithRoomForStack(stack);
            if (_snowman == -1) {
               _snowman = this.getEmptySlot();
            }

            if (_snowman == -1) {
               this.player.dropItem(stack, false);
               break;
            }

            int _snowmanx = stack.getMaxCount() - this.getStack(_snowman).getCount();
            if (this.insertStack(_snowman, stack.split(_snowmanx))) {
               ((ServerPlayerEntity)this.player).networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(-2, _snowman, this.getStack(_snowman)));
            }
         }
      }
   }

   @Override
   public ItemStack removeStack(int slot, int amount) {
      List<ItemStack> _snowman = null;

      for (DefaultedList<ItemStack> _snowmanx : this.combinedInventory) {
         if (slot < _snowmanx.size()) {
            _snowman = _snowmanx;
            break;
         }

         slot -= _snowmanx.size();
      }

      return _snowman != null && !_snowman.get(slot).isEmpty() ? Inventories.splitStack(_snowman, slot, amount) : ItemStack.EMPTY;
   }

   public void removeOne(ItemStack stack) {
      for (DefaultedList<ItemStack> _snowman : this.combinedInventory) {
         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            if (_snowman.get(_snowmanx) == stack) {
               _snowman.set(_snowmanx, ItemStack.EMPTY);
               break;
            }
         }
      }
   }

   @Override
   public ItemStack removeStack(int slot) {
      DefaultedList<ItemStack> _snowman = null;

      for (DefaultedList<ItemStack> _snowmanx : this.combinedInventory) {
         if (slot < _snowmanx.size()) {
            _snowman = _snowmanx;
            break;
         }

         slot -= _snowmanx.size();
      }

      if (_snowman != null && !_snowman.get(slot).isEmpty()) {
         ItemStack _snowmanx = _snowman.get(slot);
         _snowman.set(slot, ItemStack.EMPTY);
         return _snowmanx;
      } else {
         return ItemStack.EMPTY;
      }
   }

   @Override
   public void setStack(int slot, ItemStack stack) {
      DefaultedList<ItemStack> _snowman = null;

      for (DefaultedList<ItemStack> _snowmanx : this.combinedInventory) {
         if (slot < _snowmanx.size()) {
            _snowman = _snowmanx;
            break;
         }

         slot -= _snowmanx.size();
      }

      if (_snowman != null) {
         _snowman.set(slot, stack);
      }
   }

   public float getBlockBreakingSpeed(BlockState block) {
      return this.main.get(this.selectedSlot).getMiningSpeedMultiplier(block);
   }

   public ListTag serialize(ListTag tag) {
      for (int _snowman = 0; _snowman < this.main.size(); _snowman++) {
         if (!this.main.get(_snowman).isEmpty()) {
            CompoundTag _snowmanx = new CompoundTag();
            _snowmanx.putByte("Slot", (byte)_snowman);
            this.main.get(_snowman).toTag(_snowmanx);
            tag.add(_snowmanx);
         }
      }

      for (int _snowmanx = 0; _snowmanx < this.armor.size(); _snowmanx++) {
         if (!this.armor.get(_snowmanx).isEmpty()) {
            CompoundTag _snowmanxx = new CompoundTag();
            _snowmanxx.putByte("Slot", (byte)(_snowmanx + 100));
            this.armor.get(_snowmanx).toTag(_snowmanxx);
            tag.add(_snowmanxx);
         }
      }

      for (int _snowmanxx = 0; _snowmanxx < this.offHand.size(); _snowmanxx++) {
         if (!this.offHand.get(_snowmanxx).isEmpty()) {
            CompoundTag _snowmanxxx = new CompoundTag();
            _snowmanxxx.putByte("Slot", (byte)(_snowmanxx + 150));
            this.offHand.get(_snowmanxx).toTag(_snowmanxxx);
            tag.add(_snowmanxxx);
         }
      }

      return tag;
   }

   public void deserialize(ListTag tag) {
      this.main.clear();
      this.armor.clear();
      this.offHand.clear();

      for (int _snowman = 0; _snowman < tag.size(); _snowman++) {
         CompoundTag _snowmanx = tag.getCompound(_snowman);
         int _snowmanxx = _snowmanx.getByte("Slot") & 255;
         ItemStack _snowmanxxx = ItemStack.fromTag(_snowmanx);
         if (!_snowmanxxx.isEmpty()) {
            if (_snowmanxx >= 0 && _snowmanxx < this.main.size()) {
               this.main.set(_snowmanxx, _snowmanxxx);
            } else if (_snowmanxx >= 100 && _snowmanxx < this.armor.size() + 100) {
               this.armor.set(_snowmanxx - 100, _snowmanxxx);
            } else if (_snowmanxx >= 150 && _snowmanxx < this.offHand.size() + 150) {
               this.offHand.set(_snowmanxx - 150, _snowmanxxx);
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
      for (ItemStack _snowman : this.main) {
         if (!_snowman.isEmpty()) {
            return false;
         }
      }

      for (ItemStack _snowmanx : this.armor) {
         if (!_snowmanx.isEmpty()) {
            return false;
         }
      }

      for (ItemStack _snowmanxx : this.offHand) {
         if (!_snowmanxx.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public ItemStack getStack(int slot) {
      List<ItemStack> _snowman = null;

      for (DefaultedList<ItemStack> _snowmanx : this.combinedInventory) {
         if (slot < _snowmanx.size()) {
            _snowman = _snowmanx;
            break;
         }

         slot -= _snowmanx.size();
      }

      return _snowman == null ? ItemStack.EMPTY : _snowman.get(slot);
   }

   @Override
   public Text getName() {
      return new TranslatableText("container.inventory");
   }

   public ItemStack getArmorStack(int slot) {
      return this.armor.get(slot);
   }

   public void damageArmor(DamageSource damageSource, float _snowman) {
      if (!(_snowman <= 0.0F)) {
         _snowman /= 4.0F;
         if (_snowman < 1.0F) {
            _snowman = 1.0F;
         }

         for (int _snowmanx = 0; _snowmanx < this.armor.size(); _snowmanx++) {
            ItemStack _snowmanxx = this.armor.get(_snowmanx);
            if ((!damageSource.isFire() || !_snowmanxx.getItem().isFireproof()) && _snowmanxx.getItem() instanceof ArmorItem) {
               int _snowmanxxx = _snowmanx;
               _snowmanxx.damage((int)_snowman, this.player, _snowmanxxxx -> _snowmanxxxx.sendEquipmentBreakStatus(EquipmentSlot.fromTypeIndex(EquipmentSlot.Type.ARMOR, _snowman)));
            }
         }
      }
   }

   public void dropAll() {
      for (List<ItemStack> _snowman : this.combinedInventory) {
         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            ItemStack _snowmanxx = _snowman.get(_snowmanx);
            if (!_snowmanxx.isEmpty()) {
               this.player.dropItem(_snowmanxx, true, false);
               _snowman.set(_snowmanx, ItemStack.EMPTY);
            }
         }
      }
   }

   @Override
   public void markDirty() {
      this.changeCount++;
   }

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
      for (List<ItemStack> _snowman : this.combinedInventory) {
         for (ItemStack _snowmanx : _snowman) {
            if (!_snowmanx.isEmpty() && _snowmanx.isItemEqualIgnoreDamage(stack)) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean contains(Tag<Item> tag) {
      for (List<ItemStack> _snowman : this.combinedInventory) {
         for (ItemStack _snowmanx : _snowman) {
            if (!_snowmanx.isEmpty() && tag.contains(_snowmanx.getItem())) {
               return true;
            }
         }
      }

      return false;
   }

   public void clone(PlayerInventory other) {
      for (int _snowman = 0; _snowman < this.size(); _snowman++) {
         this.setStack(_snowman, other.getStack(_snowman));
      }

      this.selectedSlot = other.selectedSlot;
   }

   @Override
   public void clear() {
      for (List<ItemStack> _snowman : this.combinedInventory) {
         _snowman.clear();
      }
   }

   public void populateRecipeFinder(RecipeFinder finder) {
      for (ItemStack _snowman : this.main) {
         finder.addNormalItem(_snowman);
      }
   }
}
