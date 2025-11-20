package net.minecraft.client.gui.screen.recipebook;

import java.util.Set;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class FurnaceRecipeBookScreen extends AbstractFurnaceRecipeBookScreen {
   private static final Text field_26596 = new TranslatableText("gui.recipebook.toggleRecipes.smeltable");

   public FurnaceRecipeBookScreen() {
   }

   @Override
   protected Text getToggleCraftableButtonText() {
      return field_26596;
   }

   @Override
   protected Set<Item> getAllowedFuels() {
      return AbstractFurnaceBlockEntity.createFuelTimeMap().keySet();
   }
}
