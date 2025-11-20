package net.minecraft.screen;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.StonecuttingRecipe;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class StonecutterScreenHandler extends ScreenHandler {
   private final ScreenHandlerContext context;
   private final Property selectedRecipe = Property.create();
   private final World world;
   private List<StonecuttingRecipe> availableRecipes = Lists.newArrayList();
   private ItemStack inputStack = ItemStack.EMPTY;
   private long lastTakeTime;
   final Slot inputSlot;
   final Slot outputSlot;
   private Runnable contentsChangedListener = () -> {
   };
   public final Inventory input = new SimpleInventory(1) {
      @Override
      public void markDirty() {
         super.markDirty();
         StonecutterScreenHandler.this.onContentChanged(this);
         StonecutterScreenHandler.this.contentsChangedListener.run();
      }
   };
   private final CraftingResultInventory output = new CraftingResultInventory();

   public StonecutterScreenHandler(int syncId, PlayerInventory playerInventory) {
      this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
   }

   public StonecutterScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
      super(ScreenHandlerType.STONECUTTER, syncId);
      this.context = context;
      this.world = playerInventory.player.world;
      this.inputSlot = this.addSlot(new Slot(this.input, 0, 20, 33));
      this.outputSlot = this.addSlot(new Slot(this.output, 1, 143, 33) {
         @Override
         public boolean canInsert(ItemStack stack) {
            return false;
         }

         @Override
         public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
            stack.onCraft(player.world, player, stack.getCount());
            StonecutterScreenHandler.this.output.unlockLastRecipe(player);
            ItemStack _snowman = StonecutterScreenHandler.this.inputSlot.takeStack(1);
            if (!_snowman.isEmpty()) {
               StonecutterScreenHandler.this.populateResult();
            }

            context.run((_snowmanx, _snowmanxx) -> {
               long _snowmanxx = _snowmanx.getTime();
               if (StonecutterScreenHandler.this.lastTakeTime != _snowmanxx) {
                  _snowmanx.playSound(null, _snowmanxx, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                  StonecutterScreenHandler.this.lastTakeTime = _snowmanxx;
               }
            });
            return super.onTakeItem(player, stack);
         }
      });

      for (int _snowman = 0; _snowman < 3; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
            this.addSlot(new Slot(playerInventory, _snowmanx + _snowman * 9 + 9, 8 + _snowmanx * 18, 84 + _snowman * 18));
         }
      }

      for (int _snowman = 0; _snowman < 9; _snowman++) {
         this.addSlot(new Slot(playerInventory, _snowman, 8 + _snowman * 18, 142));
      }

      this.addProperty(this.selectedRecipe);
   }

   public int getSelectedRecipe() {
      return this.selectedRecipe.get();
   }

   public List<StonecuttingRecipe> getAvailableRecipes() {
      return this.availableRecipes;
   }

   public int getAvailableRecipeCount() {
      return this.availableRecipes.size();
   }

   public boolean canCraft() {
      return this.inputSlot.hasStack() && !this.availableRecipes.isEmpty();
   }

   @Override
   public boolean canUse(PlayerEntity player) {
      return canUse(this.context, player, Blocks.STONECUTTER);
   }

   @Override
   public boolean onButtonClick(PlayerEntity player, int id) {
      if (this.method_30160(id)) {
         this.selectedRecipe.set(id);
         this.populateResult();
      }

      return true;
   }

   private boolean method_30160(int _snowman) {
      return _snowman >= 0 && _snowman < this.availableRecipes.size();
   }

   @Override
   public void onContentChanged(Inventory inventory) {
      ItemStack _snowman = this.inputSlot.getStack();
      if (_snowman.getItem() != this.inputStack.getItem()) {
         this.inputStack = _snowman.copy();
         this.updateInput(inventory, _snowman);
      }
   }

   private void updateInput(Inventory input, ItemStack stack) {
      this.availableRecipes.clear();
      this.selectedRecipe.set(-1);
      this.outputSlot.setStack(ItemStack.EMPTY);
      if (!stack.isEmpty()) {
         this.availableRecipes = this.world.getRecipeManager().getAllMatches(RecipeType.STONECUTTING, input, this.world);
      }
   }

   private void populateResult() {
      if (!this.availableRecipes.isEmpty() && this.method_30160(this.selectedRecipe.get())) {
         StonecuttingRecipe _snowman = this.availableRecipes.get(this.selectedRecipe.get());
         this.output.setLastRecipe(_snowman);
         this.outputSlot.setStack(_snowman.craft(this.input));
      } else {
         this.outputSlot.setStack(ItemStack.EMPTY);
      }

      this.sendContentUpdates();
   }

   @Override
   public ScreenHandlerType<?> getType() {
      return ScreenHandlerType.STONECUTTER;
   }

   public void setContentsChangedListener(Runnable _snowman) {
      this.contentsChangedListener = _snowman;
   }

   @Override
   public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
      return slot.inventory != this.output && super.canInsertIntoSlot(stack, slot);
   }

   @Override
   public ItemStack transferSlot(PlayerEntity player, int index) {
      ItemStack _snowman = ItemStack.EMPTY;
      Slot _snowmanx = this.slots.get(index);
      if (_snowmanx != null && _snowmanx.hasStack()) {
         ItemStack _snowmanxx = _snowmanx.getStack();
         Item _snowmanxxx = _snowmanxx.getItem();
         _snowman = _snowmanxx.copy();
         if (index == 1) {
            _snowmanxxx.onCraft(_snowmanxx, player.world, player);
            if (!this.insertItem(_snowmanxx, 2, 38, true)) {
               return ItemStack.EMPTY;
            }

            _snowmanx.onStackChanged(_snowmanxx, _snowman);
         } else if (index == 0) {
            if (!this.insertItem(_snowmanxx, 2, 38, false)) {
               return ItemStack.EMPTY;
            }
         } else if (this.world.getRecipeManager().getFirstMatch(RecipeType.STONECUTTING, new SimpleInventory(_snowmanxx), this.world).isPresent()) {
            if (!this.insertItem(_snowmanxx, 0, 1, false)) {
               return ItemStack.EMPTY;
            }
         } else if (index >= 2 && index < 29) {
            if (!this.insertItem(_snowmanxx, 29, 38, false)) {
               return ItemStack.EMPTY;
            }
         } else if (index >= 29 && index < 38 && !this.insertItem(_snowmanxx, 2, 29, false)) {
            return ItemStack.EMPTY;
         }

         if (_snowmanxx.isEmpty()) {
            _snowmanx.setStack(ItemStack.EMPTY);
         }

         _snowmanx.markDirty();
         if (_snowmanxx.getCount() == _snowman.getCount()) {
            return ItemStack.EMPTY;
         }

         _snowmanx.onTakeItem(player, _snowmanxx);
         this.sendContentUpdates();
      }

      return _snowman;
   }

   @Override
   public void close(PlayerEntity player) {
      super.close(player);
      this.output.removeStack(1);
      this.context.run((_snowmanx, _snowmanxx) -> this.dropInventory(player, player.world, this.input));
   }
}
