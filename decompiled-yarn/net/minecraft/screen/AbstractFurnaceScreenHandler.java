package net.minecraft.screen;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.FurnaceInputSlotFiller;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.slot.FurnaceFuelSlot;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public abstract class AbstractFurnaceScreenHandler extends AbstractRecipeScreenHandler<Inventory> {
   private final Inventory inventory;
   private final PropertyDelegate propertyDelegate;
   protected final World world;
   private final RecipeType<? extends AbstractCookingRecipe> recipeType;
   private final RecipeBookCategory category;

   protected AbstractFurnaceScreenHandler(
      ScreenHandlerType<?> type, RecipeType<? extends AbstractCookingRecipe> recipeType, RecipeBookCategory _snowman, int _snowman, PlayerInventory _snowman
   ) {
      this(type, recipeType, _snowman, _snowman, _snowman, new SimpleInventory(3), new ArrayPropertyDelegate(4));
   }

   protected AbstractFurnaceScreenHandler(
      ScreenHandlerType<?> type,
      RecipeType<? extends AbstractCookingRecipe> recipeType,
      RecipeBookCategory _snowman,
      int _snowman,
      PlayerInventory _snowman,
      Inventory _snowman,
      PropertyDelegate _snowman
   ) {
      super(type, _snowman);
      this.recipeType = recipeType;
      this.category = _snowman;
      checkSize(_snowman, 3);
      checkDataCount(_snowman, 4);
      this.inventory = _snowman;
      this.propertyDelegate = _snowman;
      this.world = _snowman.player.world;
      this.addSlot(new Slot(_snowman, 0, 56, 17));
      this.addSlot(new FurnaceFuelSlot(this, _snowman, 1, 56, 53));
      this.addSlot(new FurnaceOutputSlot(_snowman.player, _snowman, 2, 116, 35));

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < 3; _snowmanxxxxx++) {
         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 9; _snowmanxxxxxx++) {
            this.addSlot(new Slot(_snowman, _snowmanxxxxxx + _snowmanxxxxx * 9 + 9, 8 + _snowmanxxxxxx * 18, 84 + _snowmanxxxxx * 18));
         }
      }

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < 9; _snowmanxxxxx++) {
         this.addSlot(new Slot(_snowman, _snowmanxxxxx, 8 + _snowmanxxxxx * 18, 142));
      }

      this.addProperties(_snowman);
   }

   @Override
   public void populateRecipeFinder(RecipeFinder finder) {
      if (this.inventory instanceof RecipeInputProvider) {
         ((RecipeInputProvider)this.inventory).provideRecipeInputs(finder);
      }
   }

   @Override
   public void clearCraftingSlots() {
      this.inventory.clear();
   }

   @Override
   public void fillInputSlots(boolean craftAll, Recipe<?> recipe, ServerPlayerEntity player) {
      new FurnaceInputSlotFiller<>(this).fillInputSlots(player, (Recipe<Inventory>)recipe, craftAll);
   }

   @Override
   public boolean matches(Recipe<? super Inventory> recipe) {
      return recipe.matches(this.inventory, this.world);
   }

   @Override
   public int getCraftingResultSlotIndex() {
      return 2;
   }

   @Override
   public int getCraftingWidth() {
      return 1;
   }

   @Override
   public int getCraftingHeight() {
      return 1;
   }

   @Override
   public int getCraftingSlotCount() {
      return 3;
   }

   @Override
   public boolean canUse(PlayerEntity player) {
      return this.inventory.canPlayerUse(player);
   }

   @Override
   public ItemStack transferSlot(PlayerEntity player, int index) {
      ItemStack _snowman = ItemStack.EMPTY;
      Slot _snowmanx = this.slots.get(index);
      if (_snowmanx != null && _snowmanx.hasStack()) {
         ItemStack _snowmanxx = _snowmanx.getStack();
         _snowman = _snowmanxx.copy();
         if (index == 2) {
            if (!this.insertItem(_snowmanxx, 3, 39, true)) {
               return ItemStack.EMPTY;
            }

            _snowmanx.onStackChanged(_snowmanxx, _snowman);
         } else if (index != 1 && index != 0) {
            if (this.isSmeltable(_snowmanxx)) {
               if (!this.insertItem(_snowmanxx, 0, 1, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (this.isFuel(_snowmanxx)) {
               if (!this.insertItem(_snowmanxx, 1, 2, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (index >= 3 && index < 30) {
               if (!this.insertItem(_snowmanxx, 30, 39, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (index >= 30 && index < 39 && !this.insertItem(_snowmanxx, 3, 30, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.insertItem(_snowmanxx, 3, 39, false)) {
            return ItemStack.EMPTY;
         }

         if (_snowmanxx.isEmpty()) {
            _snowmanx.setStack(ItemStack.EMPTY);
         } else {
            _snowmanx.markDirty();
         }

         if (_snowmanxx.getCount() == _snowman.getCount()) {
            return ItemStack.EMPTY;
         }

         _snowmanx.onTakeItem(player, _snowmanxx);
      }

      return _snowman;
   }

   protected boolean isSmeltable(ItemStack itemStack) {
      return this.world.getRecipeManager().getFirstMatch(this.recipeType, new SimpleInventory(itemStack), this.world).isPresent();
   }

   protected boolean isFuel(ItemStack itemStack) {
      return AbstractFurnaceBlockEntity.canUseAsFuel(itemStack);
   }

   public int getCookProgress() {
      int _snowman = this.propertyDelegate.get(2);
      int _snowmanx = this.propertyDelegate.get(3);
      return _snowmanx != 0 && _snowman != 0 ? _snowman * 24 / _snowmanx : 0;
   }

   public int getFuelProgress() {
      int _snowman = this.propertyDelegate.get(1);
      if (_snowman == 0) {
         _snowman = 200;
      }

      return this.propertyDelegate.get(0) * 13 / _snowman;
   }

   public boolean isBurning() {
      return this.propertyDelegate.get(0) > 0;
   }

   @Override
   public RecipeBookCategory getCategory() {
      return this.category;
   }
}
