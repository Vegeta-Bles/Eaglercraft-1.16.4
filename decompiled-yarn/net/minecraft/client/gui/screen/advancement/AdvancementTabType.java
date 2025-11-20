package net.minecraft.client.gui.screen.advancement;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

enum AdvancementTabType {
   ABOVE(0, 0, 28, 32, 8),
   BELOW(84, 0, 28, 32, 8),
   LEFT(0, 64, 32, 28, 5),
   RIGHT(96, 64, 32, 28, 5);

   private final int u;
   private final int v;
   private final int width;
   private final int height;
   private final int tabCount;

   private AdvancementTabType(int u, int v, int width, int height, int tabCount) {
      this.u = u;
      this.v = v;
      this.width = width;
      this.height = height;
      this.tabCount = tabCount;
   }

   public int getTabCount() {
      return this.tabCount;
   }

   public void drawBackground(MatrixStack _snowman, DrawableHelper _snowman, int _snowman, int _snowman, boolean _snowman, int _snowman) {
      int _snowmanxxxxxx = this.u;
      if (_snowman > 0) {
         _snowmanxxxxxx += this.width;
      }

      if (_snowman == this.tabCount - 1) {
         _snowmanxxxxxx += this.width;
      }

      int _snowmanxxxxxxx = _snowman ? this.v + this.height : this.v;
      _snowman.drawTexture(_snowman, _snowman + this.getTabX(_snowman), _snowman + this.getTabY(_snowman), _snowmanxxxxxx, _snowmanxxxxxxx, this.width, this.height);
   }

   public void drawIcon(int x, int y, int index, ItemRenderer itemRenderer, ItemStack icon) {
      int _snowman = x + this.getTabX(index);
      int _snowmanx = y + this.getTabY(index);
      switch (this) {
         case ABOVE:
            _snowman += 6;
            _snowmanx += 9;
            break;
         case BELOW:
            _snowman += 6;
            _snowmanx += 6;
            break;
         case LEFT:
            _snowman += 10;
            _snowmanx += 5;
            break;
         case RIGHT:
            _snowman += 6;
            _snowmanx += 5;
      }

      itemRenderer.renderInGui(icon, _snowman, _snowmanx);
   }

   public int getTabX(int index) {
      switch (this) {
         case ABOVE:
            return (this.width + 4) * index;
         case BELOW:
            return (this.width + 4) * index;
         case LEFT:
            return -this.width + 4;
         case RIGHT:
            return 248;
         default:
            throw new UnsupportedOperationException("Don't know what this tab type is!" + this);
      }
   }

   public int getTabY(int index) {
      switch (this) {
         case ABOVE:
            return -this.height + 4;
         case BELOW:
            return 136;
         case LEFT:
            return this.height * index;
         case RIGHT:
            return this.height * index;
         default:
            throw new UnsupportedOperationException("Don't know what this tab type is!" + this);
      }
   }

   public boolean isClickOnTab(int screenX, int screenY, int index, double mouseX, double mouseY) {
      int _snowman = screenX + this.getTabX(index);
      int _snowmanx = screenY + this.getTabY(index);
      return mouseX > (double)_snowman && mouseX < (double)(_snowman + this.width) && mouseY > (double)_snowmanx && mouseY < (double)(_snowmanx + this.height);
   }
}
