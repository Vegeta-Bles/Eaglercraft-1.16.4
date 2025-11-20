package net.minecraft.client.gui.screen.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeBook;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class AnimatedResultButton extends AbstractButtonWidget {
   private static final Identifier BACKGROUND_TEXTURE = new Identifier("textures/gui/recipe_book.png");
   private static final Text field_26595 = new TranslatableText("gui.recipebook.moreRecipes");
   private AbstractRecipeScreenHandler<?> craftingScreenHandler;
   private RecipeBook recipeBook;
   private RecipeResultCollection results;
   private float time;
   private float bounce;
   private int currentResultIndex;

   public AnimatedResultButton() {
      super(0, 0, 25, 25, LiteralText.EMPTY);
   }

   public void showResultCollection(RecipeResultCollection arg, RecipeBookResults arg2) {
      this.results = arg;
      this.craftingScreenHandler = (AbstractRecipeScreenHandler<?>)arg2.getMinecraftClient().player.currentScreenHandler;
      this.recipeBook = arg2.getRecipeBook();
      List<Recipe<?>> list = arg.getResults(this.recipeBook.isFilteringCraftable(this.craftingScreenHandler));

      for (Recipe<?> lv : list) {
         if (this.recipeBook.shouldDisplay(lv)) {
            arg2.onRecipesDisplayed(list);
            this.bounce = 15.0F;
            break;
         }
      }
   }

   public RecipeResultCollection getResultCollection() {
      return this.results;
   }

   public void setPos(int x, int y) {
      this.x = x;
      this.y = y;
   }

   @Override
   public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      if (!Screen.hasControlDown()) {
         this.time += delta;
      }

      MinecraftClient lv = MinecraftClient.getInstance();
      lv.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
      int k = 29;
      if (!this.results.hasCraftableRecipes()) {
         k += 25;
      }

      int l = 206;
      if (this.results.getResults(this.recipeBook.isFilteringCraftable(this.craftingScreenHandler)).size() > 1) {
         l += 25;
      }

      boolean bl = this.bounce > 0.0F;
      if (bl) {
         float g = 1.0F + 0.1F * (float)Math.sin((double)(this.bounce / 15.0F * (float) Math.PI));
         RenderSystem.pushMatrix();
         RenderSystem.translatef((float)(this.x + 8), (float)(this.y + 12), 0.0F);
         RenderSystem.scalef(g, g, 1.0F);
         RenderSystem.translatef((float)(-(this.x + 8)), (float)(-(this.y + 12)), 0.0F);
         this.bounce -= delta;
      }

      this.drawTexture(matrices, this.x, this.y, k, l, this.width, this.height);
      List<Recipe<?>> list = this.getResults();
      this.currentResultIndex = MathHelper.floor(this.time / 30.0F) % list.size();
      ItemStack lv2 = list.get(this.currentResultIndex).getOutput();
      int m = 4;
      if (this.results.hasSingleOutput() && this.getResults().size() > 1) {
         lv.getItemRenderer().renderInGuiWithOverrides(lv2, this.x + m + 1, this.y + m + 1);
         m--;
      }

      lv.getItemRenderer().renderInGui(lv2, this.x + m, this.y + m);
      if (bl) {
         RenderSystem.popMatrix();
      }
   }

   private List<Recipe<?>> getResults() {
      List<Recipe<?>> list = this.results.getRecipes(true);
      if (!this.recipeBook.isFilteringCraftable(this.craftingScreenHandler)) {
         list.addAll(this.results.getRecipes(false));
      }

      return list;
   }

   public boolean hasResults() {
      return this.getResults().size() == 1;
   }

   public Recipe<?> currentRecipe() {
      List<Recipe<?>> list = this.getResults();
      return list.get(this.currentResultIndex);
   }

   public List<Text> getTooltip(Screen screen) {
      ItemStack lv = this.getResults().get(this.currentResultIndex).getOutput();
      List<Text> list = Lists.newArrayList(screen.getTooltipFromItem(lv));
      if (this.results.getResults(this.recipeBook.isFilteringCraftable(this.craftingScreenHandler)).size() > 1) {
         list.add(field_26595);
      }

      return list;
   }

   @Override
   public int getWidth() {
      return 25;
   }

   @Override
   protected boolean isValidClickButton(int button) {
      return button == 0 || button == 1;
   }
}
