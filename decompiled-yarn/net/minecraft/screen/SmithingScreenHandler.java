package net.minecraft.screen;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

public class SmithingScreenHandler extends ForgingScreenHandler {
   private final World field_25385;
   @Nullable
   private SmithingRecipe field_25386;
   private final List<SmithingRecipe> field_25668;

   public SmithingScreenHandler(int syncId, PlayerInventory playerInventory) {
      this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
   }

   public SmithingScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
      super(ScreenHandlerType.SMITHING, syncId, playerInventory, context);
      this.field_25385 = playerInventory.player.world;
      this.field_25668 = this.field_25385.getRecipeManager().listAllOfType(RecipeType.SMITHING);
   }

   @Override
   protected boolean canUse(BlockState state) {
      return state.isOf(Blocks.SMITHING_TABLE);
   }

   @Override
   protected boolean canTakeOutput(PlayerEntity player, boolean present) {
      return this.field_25386 != null && this.field_25386.matches(this.input, this.field_25385);
   }

   @Override
   protected ItemStack onTakeOutput(PlayerEntity player, ItemStack stack) {
      stack.onCraft(player.world, player, stack.getCount());
      this.output.unlockLastRecipe(player);
      this.method_29539(0);
      this.method_29539(1);
      this.context.run((_snowman, _snowmanx) -> _snowman.syncWorldEvent(1044, _snowmanx, 0));
      return stack;
   }

   private void method_29539(int _snowman) {
      ItemStack _snowmanx = this.input.getStack(_snowman);
      _snowmanx.decrement(1);
      this.input.setStack(_snowman, _snowmanx);
   }

   @Override
   public void updateResult() {
      List<SmithingRecipe> _snowman = this.field_25385.getRecipeManager().getAllMatches(RecipeType.SMITHING, this.input, this.field_25385);
      if (_snowman.isEmpty()) {
         this.output.setStack(0, ItemStack.EMPTY);
      } else {
         this.field_25386 = _snowman.get(0);
         ItemStack _snowmanx = this.field_25386.craft(this.input);
         this.output.setLastRecipe(this.field_25386);
         this.output.setStack(0, _snowmanx);
      }
   }

   @Override
   protected boolean method_30025(ItemStack _snowman) {
      return this.field_25668.stream().anyMatch(_snowmanxx -> _snowmanxx.method_30029(_snowman));
   }

   @Override
   public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
      return slot.inventory != this.output && super.canInsertIntoSlot(stack, slot);
   }
}
