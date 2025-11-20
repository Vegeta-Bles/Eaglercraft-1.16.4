package net.minecraft.client.realms.gui.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class RealmsClientOutdatedScreen extends RealmsScreen {
   private static final Text field_26475 = new TranslatableText("mco.client.outdated.title");
   private static final Text[] field_26476 = new Text[]{
      new TranslatableText("mco.client.outdated.msg.line1"), new TranslatableText("mco.client.outdated.msg.line2")
   };
   private static final Text field_26477 = new TranslatableText("mco.client.incompatible.title");
   private static final Text[] field_26478 = new Text[]{
      new TranslatableText("mco.client.incompatible.msg.line1"),
      new TranslatableText("mco.client.incompatible.msg.line2"),
      new TranslatableText("mco.client.incompatible.msg.line3")
   };
   private final Screen parent;
   private final boolean outdated;

   public RealmsClientOutdatedScreen(Screen parent, boolean outdated) {
      this.parent = parent;
      this.outdated = outdated;
   }

   @Override
   public void init() {
      this.addButton(new ButtonWidget(this.width / 2 - 100, row(12), 200, 20, ScreenTexts.BACK, _snowman -> this.client.openScreen(this.parent)));
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      Text _snowman;
      Text[] _snowmanx;
      if (this.outdated) {
         _snowman = field_26477;
         _snowmanx = field_26478;
      } else {
         _snowman = field_26475;
         _snowmanx = field_26476;
      }

      drawCenteredText(matrices, this.textRenderer, _snowman, this.width / 2, row(3), 16711680);

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx.length; _snowmanxx++) {
         drawCenteredText(matrices, this.textRenderer, _snowmanx[_snowmanxx], this.width / 2, row(5) + _snowmanxx * 12, 16777215);
      }

      super.render(matrices, mouseX, mouseY, delta);
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode != 257 && keyCode != 335 && keyCode != 256) {
         return super.keyPressed(keyCode, scanCode, modifiers);
      } else {
         this.client.openScreen(this.parent);
         return true;
      }
   }
}
