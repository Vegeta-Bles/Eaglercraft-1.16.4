package net.minecraft.block.entity;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;

public class DispenserBlockEntity extends LootableContainerBlockEntity {
   private static final Random RANDOM = new Random();
   private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);

   protected DispenserBlockEntity(BlockEntityType<?> _snowman) {
      super(_snowman);
   }

   public DispenserBlockEntity() {
      this(BlockEntityType.DISPENSER);
   }

   @Override
   public int size() {
      return 9;
   }

   public int chooseNonEmptySlot() {
      this.checkLootInteraction(null);
      int _snowman = -1;
      int _snowmanx = 1;

      for (int _snowmanxx = 0; _snowmanxx < this.inventory.size(); _snowmanxx++) {
         if (!this.inventory.get(_snowmanxx).isEmpty() && RANDOM.nextInt(_snowmanx++) == 0) {
            _snowman = _snowmanxx;
         }
      }

      return _snowman;
   }

   public int addToFirstFreeSlot(ItemStack stack) {
      for (int _snowman = 0; _snowman < this.inventory.size(); _snowman++) {
         if (this.inventory.get(_snowman).isEmpty()) {
            this.setStack(_snowman, stack);
            return _snowman;
         }
      }

      return -1;
   }

   @Override
   protected Text getContainerName() {
      return new TranslatableText("container.dispenser");
   }

   @Override
   public void fromTag(BlockState state, CompoundTag tag) {
      super.fromTag(state, tag);
      this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
      if (!this.deserializeLootTable(tag)) {
         Inventories.fromTag(tag, this.inventory);
      }
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      super.toTag(tag);
      if (!this.serializeLootTable(tag)) {
         Inventories.toTag(tag, this.inventory);
      }

      return tag;
   }

   @Override
   protected DefaultedList<ItemStack> getInvStackList() {
      return this.inventory;
   }

   @Override
   protected void setInvStackList(DefaultedList<ItemStack> list) {
      this.inventory = list;
   }

   @Override
   protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
      return new Generic3x3ContainerScreenHandler(syncId, playerInventory, this);
   }
}
