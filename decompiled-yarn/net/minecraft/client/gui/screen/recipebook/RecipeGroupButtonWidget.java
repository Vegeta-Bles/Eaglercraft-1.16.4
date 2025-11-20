package net.minecraft.client.gui.screen.recipebook;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.AbstractRecipeScreenHandler;

public class RecipeGroupButtonWidget extends ToggleButtonWidget {
   private final RecipeBookGroup category;
   private float bounce;

   public RecipeGroupButtonWidget(RecipeBookGroup category) {
      super(0, 0, 35, 27, false);
      this.category = category;
      this.setTextureUV(153, 2, 35, 0, RecipeBookWidget.TEXTURE);
   }

   public void checkForNewRecipes(MinecraftClient client) {
      ClientRecipeBook _snowman = client.player.getRecipeBook();
      List<RecipeResultCollection> _snowmanx = _snowman.getResultsForGroup(this.category);
      if (client.player.currentScreenHandler instanceof AbstractRecipeScreenHandler) {
         for (RecipeResultCollection _snowmanxx : _snowmanx) {
            for (Recipe<?> _snowmanxxx : _snowmanxx.getResults(_snowman.isFilteringCraftable((AbstractRecipeScreenHandler<?>)client.player.currentScreenHandler))) {
               if (_snowman.shouldDisplay(_snowmanxxx)) {
                  this.bounce = 15.0F;
                  return;
               }
            }
         }
      }
   }

   @Override
   public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      if (this.bounce > 0.0F) {
         float _snowman = 1.0F + 0.1F * (float)Math.sin((double)(this.bounce / 15.0F * (float) Math.PI));
         RenderSystem.pushMatrix();
         RenderSystem.translatef((float)(this.x + 8), (float)(this.y + 12), 0.0F);
         RenderSystem.scalef(1.0F, _snowman, 1.0F);
         RenderSystem.translatef((float)(-(this.x + 8)), (float)(-(this.y + 12)), 0.0F);
      }

      MinecraftClient _snowman = MinecraftClient.getInstance();
      _snowman.getTextureManager().bindTexture(this.texture);
      RenderSystem.disableDepthTest();
      int _snowmanx = this.u;
      int _snowmanxx = this.v;
      if (this.toggled) {
         _snowmanx += this.pressedUOffset;
      }

      if (this.isHovered()) {
         _snowmanxx += this.hoverVOffset;
      }

      int _snowmanxxx = this.x;
      if (this.toggled) {
         _snowmanxxx -= 2;
      }

      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.drawTexture(matrices, _snowmanxxx, this.y, _snowmanx, _snowmanxx, this.width, this.height);
      RenderSystem.enableDepthTest();
      this.renderIcons(_snowman.getItemRenderer());
      if (this.bounce > 0.0F) {
         RenderSystem.popMatrix();
         this.bounce -= delta;
      }
   }

   private void renderIcons(ItemRenderer itemRenderer) {
      List<ItemStack> _snowman = this.category.getIcons();
      int _snowmanx = this.toggled ? -2 : 0;
      if (_snowman.size() == 1) {
         itemRenderer.renderInGui(_snowman.get(0), this.x + 9 + _snowmanx, this.y + 5);
      } else if (_snowman.size() == 2) {
         itemRenderer.renderInGui(_snowman.get(0), this.x + 3 + _snowmanx, this.y + 5);
         itemRenderer.renderInGui(_snowman.get(1), this.x + 14 + _snowmanx, this.y + 5);
      }
   }

   public RecipeBookGroup getCategory() {
      return this.category;
   }

   public boolean hasKnownRecipes(ClientRecipeBook recipeBook) {
      List<RecipeResultCollection> _snowman = recipeBook.getResultsForGroup(this.category);
      this.visible = false;
      if (_snowman != null) {
         for (RecipeResultCollection _snowmanx : _snowman) {
            if (_snowmanx.isInitialized() && _snowmanx.hasFittingRecipes()) {
               this.visible = true;
               break;
            }
         }
      }

      return this.visible;
   }
}
