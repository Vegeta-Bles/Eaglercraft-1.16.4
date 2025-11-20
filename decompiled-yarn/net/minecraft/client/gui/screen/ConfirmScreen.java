package net.minecraft.client.gui.screen;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.class_5489;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ConfirmScreen extends Screen {
   private final Text message;
   private class_5489 messageSplit = class_5489.field_26528;
   protected Text yesTranslated;
   protected Text noTranslated;
   private int buttonEnableTimer;
   protected final BooleanConsumer callback;

   public ConfirmScreen(BooleanConsumer callback, Text title, Text message) {
      this(callback, title, message, ScreenTexts.YES, ScreenTexts.NO);
   }

   public ConfirmScreen(BooleanConsumer callback, Text title, Text message, Text _snowman, Text _snowman) {
      super(title);
      this.callback = callback;
      this.message = message;
      this.yesTranslated = _snowman;
      this.noTranslated = _snowman;
   }

   @Override
   public String getNarrationMessage() {
      return super.getNarrationMessage() + ". " + this.message.getString();
   }

   @Override
   protected void init() {
      super.init();
      this.addButton(new ButtonWidget(this.width / 2 - 155, this.height / 6 + 96, 150, 20, this.yesTranslated, _snowman -> this.callback.accept(true)));
      this.addButton(new ButtonWidget(this.width / 2 - 155 + 160, this.height / 6 + 96, 150, 20, this.noTranslated, _snowman -> this.callback.accept(false)));
      this.messageSplit = class_5489.method_30890(this.textRenderer, this.message, this.width - 50);
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 70, 16777215);
      this.messageSplit.method_30888(matrices, this.width / 2, 90);
      super.render(matrices, mouseX, mouseY, delta);
   }

   public void disableButtons(int _snowman) {
      this.buttonEnableTimer = _snowman;

      for (AbstractButtonWidget _snowmanx : this.buttons) {
         _snowmanx.active = false;
      }
   }

   @Override
   public void tick() {
      super.tick();
      if (--this.buttonEnableTimer == 0) {
         for (AbstractButtonWidget _snowman : this.buttons) {
            _snowman.active = true;
         }
      }
   }

   @Override
   public boolean shouldCloseOnEsc() {
      return false;
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         this.callback.accept(false);
         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }
}
