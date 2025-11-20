package net.minecraft.block.entity;

import java.util.Arrays;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.BrewingStandBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.screen.BrewingStandScreenHandler;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class BrewingStandBlockEntity extends LockableContainerBlockEntity implements SidedInventory, Tickable {
   private static final int[] TOP_SLOTS = new int[]{3};
   private static final int[] BOTTOM_SLOTS = new int[]{0, 1, 2, 3};
   private static final int[] SIDE_SLOTS = new int[]{0, 1, 2, 4};
   private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);
   private int brewTime;
   private boolean[] slotsEmptyLastTick;
   private Item itemBrewing;
   private int fuel;
   protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
      @Override
      public int get(int index) {
         switch (index) {
            case 0:
               return BrewingStandBlockEntity.this.brewTime;
            case 1:
               return BrewingStandBlockEntity.this.fuel;
            default:
               return 0;
         }
      }

      @Override
      public void set(int index, int value) {
         switch (index) {
            case 0:
               BrewingStandBlockEntity.this.brewTime = value;
               break;
            case 1:
               BrewingStandBlockEntity.this.fuel = value;
         }
      }

      @Override
      public int size() {
         return 2;
      }
   };

   public BrewingStandBlockEntity() {
      super(BlockEntityType.BREWING_STAND);
   }

   @Override
   protected Text getContainerName() {
      return new TranslatableText("container.brewing");
   }

   @Override
   public int size() {
      return this.inventory.size();
   }

   @Override
   public boolean isEmpty() {
      for (ItemStack _snowman : this.inventory) {
         if (!_snowman.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public void tick() {
      ItemStack _snowman = this.inventory.get(4);
      if (this.fuel <= 0 && _snowman.getItem() == Items.BLAZE_POWDER) {
         this.fuel = 20;
         _snowman.decrement(1);
         this.markDirty();
      }

      boolean _snowmanx = this.canCraft();
      boolean _snowmanxx = this.brewTime > 0;
      ItemStack _snowmanxxx = this.inventory.get(3);
      if (_snowmanxx) {
         this.brewTime--;
         boolean _snowmanxxxx = this.brewTime == 0;
         if (_snowmanxxxx && _snowmanx) {
            this.craft();
            this.markDirty();
         } else if (!_snowmanx) {
            this.brewTime = 0;
            this.markDirty();
         } else if (this.itemBrewing != _snowmanxxx.getItem()) {
            this.brewTime = 0;
            this.markDirty();
         }
      } else if (_snowmanx && this.fuel > 0) {
         this.fuel--;
         this.brewTime = 400;
         this.itemBrewing = _snowmanxxx.getItem();
         this.markDirty();
      }

      if (!this.world.isClient) {
         boolean[] _snowmanxxxx = this.getSlotsEmpty();
         if (!Arrays.equals(_snowmanxxxx, this.slotsEmptyLastTick)) {
            this.slotsEmptyLastTick = _snowmanxxxx;
            BlockState _snowmanxxxxx = this.world.getBlockState(this.getPos());
            if (!(_snowmanxxxxx.getBlock() instanceof BrewingStandBlock)) {
               return;
            }

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < BrewingStandBlock.BOTTLE_PROPERTIES.length; _snowmanxxxxxx++) {
               _snowmanxxxxx = _snowmanxxxxx.with(BrewingStandBlock.BOTTLE_PROPERTIES[_snowmanxxxxxx], Boolean.valueOf(_snowmanxxxx[_snowmanxxxxxx]));
            }

            this.world.setBlockState(this.pos, _snowmanxxxxx, 2);
         }
      }
   }

   public boolean[] getSlotsEmpty() {
      boolean[] _snowman = new boolean[3];

      for (int _snowmanx = 0; _snowmanx < 3; _snowmanx++) {
         if (!this.inventory.get(_snowmanx).isEmpty()) {
            _snowman[_snowmanx] = true;
         }
      }

      return _snowman;
   }

   private boolean canCraft() {
      ItemStack _snowman = this.inventory.get(3);
      if (_snowman.isEmpty()) {
         return false;
      } else if (!BrewingRecipeRegistry.isValidIngredient(_snowman)) {
         return false;
      } else {
         for (int _snowmanx = 0; _snowmanx < 3; _snowmanx++) {
            ItemStack _snowmanxx = this.inventory.get(_snowmanx);
            if (!_snowmanxx.isEmpty() && BrewingRecipeRegistry.hasRecipe(_snowmanxx, _snowman)) {
               return true;
            }
         }

         return false;
      }
   }

   private void craft() {
      ItemStack _snowman = this.inventory.get(3);

      for (int _snowmanx = 0; _snowmanx < 3; _snowmanx++) {
         this.inventory.set(_snowmanx, BrewingRecipeRegistry.craft(_snowman, this.inventory.get(_snowmanx)));
      }

      _snowman.decrement(1);
      BlockPos _snowmanx = this.getPos();
      if (_snowman.getItem().hasRecipeRemainder()) {
         ItemStack _snowmanxx = new ItemStack(_snowman.getItem().getRecipeRemainder());
         if (_snowman.isEmpty()) {
            _snowman = _snowmanxx;
         } else if (!this.world.isClient) {
            ItemScatterer.spawn(this.world, (double)_snowmanx.getX(), (double)_snowmanx.getY(), (double)_snowmanx.getZ(), _snowmanxx);
         }
      }

      this.inventory.set(3, _snowman);
      this.world.syncWorldEvent(1035, _snowmanx, 0);
   }

   @Override
   public void fromTag(BlockState state, CompoundTag tag) {
      super.fromTag(state, tag);
      this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
      Inventories.fromTag(tag, this.inventory);
      this.brewTime = tag.getShort("BrewTime");
      this.fuel = tag.getByte("Fuel");
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      super.toTag(tag);
      tag.putShort("BrewTime", (short)this.brewTime);
      Inventories.toTag(tag, this.inventory);
      tag.putByte("Fuel", (byte)this.fuel);
      return tag;
   }

   @Override
   public ItemStack getStack(int slot) {
      return slot >= 0 && slot < this.inventory.size() ? this.inventory.get(slot) : ItemStack.EMPTY;
   }

   @Override
   public ItemStack removeStack(int slot, int amount) {
      return Inventories.splitStack(this.inventory, slot, amount);
   }

   @Override
   public ItemStack removeStack(int slot) {
      return Inventories.removeStack(this.inventory, slot);
   }

   @Override
   public void setStack(int slot, ItemStack stack) {
      if (slot >= 0 && slot < this.inventory.size()) {
         this.inventory.set(slot, stack);
      }
   }

   @Override
   public boolean canPlayerUse(PlayerEntity player) {
      return this.world.getBlockEntity(this.pos) != this
         ? false
         : !(player.squaredDistanceTo((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 0.5, (double)this.pos.getZ() + 0.5) > 64.0);
   }

   @Override
   public boolean isValid(int slot, ItemStack stack) {
      if (slot == 3) {
         return BrewingRecipeRegistry.isValidIngredient(stack);
      } else {
         Item _snowman = stack.getItem();
         return slot == 4
            ? _snowman == Items.BLAZE_POWDER
            : (_snowman == Items.POTION || _snowman == Items.SPLASH_POTION || _snowman == Items.LINGERING_POTION || _snowman == Items.GLASS_BOTTLE) && this.getStack(slot).isEmpty();
      }
   }

   @Override
   public int[] getAvailableSlots(Direction side) {
      if (side == Direction.UP) {
         return TOP_SLOTS;
      } else {
         return side == Direction.DOWN ? BOTTOM_SLOTS : SIDE_SLOTS;
      }
   }

   @Override
   public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
      return this.isValid(slot, stack);
   }

   @Override
   public boolean canExtract(int slot, ItemStack stack, Direction dir) {
      return slot == 3 ? stack.getItem() == Items.GLASS_BOTTLE : true;
   }

   @Override
   public void clear() {
      this.inventory.clear();
   }

   @Override
   protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
      return new BrewingStandScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
   }
}
