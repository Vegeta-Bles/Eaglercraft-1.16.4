package net.minecraft.screen;

import java.util.Optional;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class CraftingScreenHandler extends AbstractRecipeScreenHandler<CraftingInventory> {
   private final CraftingInventory input = new CraftingInventory(this, 3, 3);
   private final CraftingResultInventory result = new CraftingResultInventory();
   private final ScreenHandlerContext context;
   private final PlayerEntity player;

   public CraftingScreenHandler(int syncId, PlayerInventory playerInventory) {
      this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
   }

   public CraftingScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
      super(ScreenHandlerType.CRAFTING, syncId);
      this.context = context;
      this.player = playerInventory.player;
      this.addSlot(new CraftingResultSlot(playerInventory.player, this.input, this.result, 0, 124, 35));

      for (int _snowman = 0; _snowman < 3; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 3; _snowmanx++) {
            this.addSlot(new Slot(this.input, _snowmanx + _snowman * 3, 30 + _snowmanx * 18, 17 + _snowman * 18));
         }
      }

      for (int _snowman = 0; _snowman < 3; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
            this.addSlot(new Slot(playerInventory, _snowmanx + _snowman * 9 + 9, 8 + _snowmanx * 18, 84 + _snowman * 18));
         }
      }

      for (int _snowman = 0; _snowman < 9; _snowman++) {
         this.addSlot(new Slot(playerInventory, _snowman, 8 + _snowman * 18, 142));
      }
   }

   protected static void updateResult(
      int syncId, World world, PlayerEntity player, CraftingInventory craftingInventory, CraftingResultInventory resultInventory
   ) {
      if (!world.isClient) {
         ServerPlayerEntity _snowman = (ServerPlayerEntity)player;
         ItemStack _snowmanx = ItemStack.EMPTY;
         Optional<CraftingRecipe> _snowmanxx = world.getServer().getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingInventory, world);
         if (_snowmanxx.isPresent()) {
            CraftingRecipe _snowmanxxx = _snowmanxx.get();
            if (resultInventory.shouldCraftRecipe(world, _snowman, _snowmanxxx)) {
               _snowmanx = _snowmanxxx.craft(craftingInventory);
            }
         }

         resultInventory.setStack(0, _snowmanx);
         _snowman.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(syncId, 0, _snowmanx));
      }
   }

   @Override
   public void onContentChanged(Inventory inventory) {
      this.context.run((_snowman, _snowmanx) -> updateResult(this.syncId, _snowman, this.player, this.input, this.result));
   }

   @Override
   public void populateRecipeFinder(RecipeFinder finder) {
      this.input.provideRecipeInputs(finder);
   }

   @Override
   public void clearCraftingSlots() {
      this.input.clear();
      this.result.clear();
   }

   @Override
   public boolean matches(Recipe<? super CraftingInventory> recipe) {
      return recipe.matches(this.input, this.player.world);
   }

   @Override
   public void close(PlayerEntity player) {
      super.close(player);
      this.context.run((_snowmanx, _snowmanxx) -> this.dropInventory(player, _snowmanx, this.input));
   }

   @Override
   public boolean canUse(PlayerEntity player) {
      return canUse(this.context, player, Blocks.CRAFTING_TABLE);
   }

   @Override
   public ItemStack transferSlot(PlayerEntity player, int index) {
      ItemStack _snowman = ItemStack.EMPTY;
      Slot _snowmanx = this.slots.get(index);
      if (_snowmanx != null && _snowmanx.hasStack()) {
         ItemStack _snowmanxx = _snowmanx.getStack();
         _snowman = _snowmanxx.copy();
         if (index == 0) {
            this.context.run((_snowmanxxx, _snowmanxxxx) -> _snowman.getItem().onCraft(_snowman, _snowmanxxx, player));
            if (!this.insertItem(_snowmanxx, 10, 46, true)) {
               return ItemStack.EMPTY;
            }

            _snowmanx.onStackChanged(_snowmanxx, _snowman);
         } else if (index >= 10 && index < 46) {
            if (!this.insertItem(_snowmanxx, 1, 10, false)) {
               if (index < 37) {
                  if (!this.insertItem(_snowmanxx, 37, 46, false)) {
                     return ItemStack.EMPTY;
                  }
               } else if (!this.insertItem(_snowmanxx, 10, 37, false)) {
                  return ItemStack.EMPTY;
               }
            }
         } else if (!this.insertItem(_snowmanxx, 10, 46, false)) {
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

         ItemStack _snowmanxxx = _snowmanx.onTakeItem(player, _snowmanxx);
         if (index == 0) {
            player.dropItem(_snowmanxxx, false);
         }
      }

      return _snowman;
   }

   @Override
   public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
      return slot.inventory != this.result && super.canInsertIntoSlot(stack, slot);
   }

   @Override
   public int getCraftingResultSlotIndex() {
      return 0;
   }

   @Override
   public int getCraftingWidth() {
      return this.input.getWidth();
   }

   @Override
   public int getCraftingHeight() {
      return this.input.getHeight();
   }

   @Override
   public int getCraftingSlotCount() {
      return 10;
   }

   @Override
   public RecipeBookCategory getCategory() {
      return RecipeBookCategory.CRAFTING;
   }
}
