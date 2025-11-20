package net.minecraft.screen;

import com.google.common.collect.Lists;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import net.minecraft.util.Unit;
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

   public StonecutterScreenHandler(int syncId, PlayerInventory playerInventory, final ScreenHandlerContext context) {
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
            ItemStack lv = StonecutterScreenHandler.this.inputSlot.takeStack(1);
            if (!lv.isEmpty()) {
               StonecutterScreenHandler.this.populateResult();
            }

            context.run((arg, arg2) -> {
               long l = arg.getTime();
               if (StonecutterScreenHandler.this.lastTakeTime != l) {
                  arg.playSound(null, arg2, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                  StonecutterScreenHandler.this.lastTakeTime = l;
               }
               return Unit.INSTANCE;
            });
            return super.onTakeItem(player, stack);
         }
      });

      for (int j = 0; j < 3; j++) {
         for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
         }
      }

      for (int l = 0; l < 9; l++) {
         this.addSlot(new Slot(playerInventory, l, 8 + l * 18, 142));
      }

      this.addProperty(this.selectedRecipe);
   }

   @Environment(EnvType.CLIENT)
   public int getSelectedRecipe() {
      return this.selectedRecipe.get();
   }

   @Environment(EnvType.CLIENT)
   public List<StonecuttingRecipe> getAvailableRecipes() {
      return this.availableRecipes;
   }

   @Environment(EnvType.CLIENT)
   public int getAvailableRecipeCount() {
      return this.availableRecipes.size();
   }

   @Environment(EnvType.CLIENT)
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

   private boolean method_30160(int i) {
      return i >= 0 && i < this.availableRecipes.size();
   }

   @Override
   public void onContentChanged(Inventory inventory) {
      ItemStack lv = this.inputSlot.getStack();
      if (lv.getItem() != this.inputStack.getItem()) {
         this.inputStack = lv.copy();
         this.updateInput(inventory, lv);
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
         StonecuttingRecipe lv = this.availableRecipes.get(this.selectedRecipe.get());
         this.output.setLastRecipe(lv);
         this.outputSlot.setStack(lv.craft(this.input));
      } else {
         this.outputSlot.setStack(ItemStack.EMPTY);
      }

      this.sendContentUpdates();
   }

   @Override
   public ScreenHandlerType<?> getType() {
      return ScreenHandlerType.STONECUTTER;
   }

   @Environment(EnvType.CLIENT)
   public void setContentsChangedListener(Runnable runnable) {
      this.contentsChangedListener = runnable;
   }

   @Override
   public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
      return slot.inventory != this.output && super.canInsertIntoSlot(stack, slot);
   }

   @Override
   public ItemStack transferSlot(PlayerEntity player, int index) {
      ItemStack lv = ItemStack.EMPTY;
      Slot lv2 = this.slots.get(index);
      if (lv2 != null && lv2.hasStack()) {
         ItemStack lv3 = lv2.getStack();
         Item lv4 = lv3.getItem();
         lv = lv3.copy();
         if (index == 1) {
            lv4.onCraft(lv3, player.world, player);
            if (!this.insertItem(lv3, 2, 38, true)) {
               return ItemStack.EMPTY;
            }

            lv2.onStackChanged(lv3, lv);
         } else if (index == 0) {
            if (!this.insertItem(lv3, 2, 38, false)) {
               return ItemStack.EMPTY;
            }
         } else if (this.world.getRecipeManager().getFirstMatch(RecipeType.STONECUTTING, new SimpleInventory(lv3), this.world).isPresent()) {
            if (!this.insertItem(lv3, 0, 1, false)) {
               return ItemStack.EMPTY;
            }
         } else if (index >= 2 && index < 29) {
            if (!this.insertItem(lv3, 29, 38, false)) {
               return ItemStack.EMPTY;
            }
         } else if (index >= 29 && index < 38 && !this.insertItem(lv3, 2, 29, false)) {
            return ItemStack.EMPTY;
         }

         if (lv3.isEmpty()) {
            lv2.setStack(ItemStack.EMPTY);
         }

         lv2.markDirty();
         if (lv3.getCount() == lv.getCount()) {
            return ItemStack.EMPTY;
         }

         lv2.onTakeItem(player, lv3);
         this.sendContentUpdates();
      }

      return lv;
   }

   @Override
   public void close(PlayerEntity player) {
      super.close(player);
      this.output.removeStack(1);
      this.context.run((arg2, arg3) -> {
         this.dropInventory(player, player.world, this.input);
         return Unit.INSTANCE;
      });
   }
}
