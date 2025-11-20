package net.minecraft.client.gui.screen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import java.util.List;
import net.minecraft.class_5489;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;

public class DialogScreen extends Screen {
   private final StringVisitable message;
   private final ImmutableList<DialogScreen.ChoiceButton> choiceButtons;
   private class_5489 lines = class_5489.field_26528;
   private int linesY;
   private int buttonWidth;

   protected DialogScreen(Text title, List<StringVisitable> _snowman, ImmutableList<DialogScreen.ChoiceButton> choiceButtons) {
      super(title);
      this.message = StringVisitable.concat(_snowman);
      this.choiceButtons = choiceButtons;
   }

   @Override
   public String getNarrationMessage() {
      return super.getNarrationMessage() + ". " + this.message.getString();
   }

   @Override
   public void init(MinecraftClient client, int width, int height) {
      super.init(client, width, height);
      UnmodifiableIterator var4 = this.choiceButtons.iterator();

      while (var4.hasNext()) {
         DialogScreen.ChoiceButton _snowman = (DialogScreen.ChoiceButton)var4.next();
         this.buttonWidth = Math.max(this.buttonWidth, 20 + this.textRenderer.getWidth(_snowman.message) + 20);
      }

      int _snowman = 5 + this.buttonWidth + 5;
      int _snowmanx = _snowman * this.choiceButtons.size();
      this.lines = class_5489.method_30890(this.textRenderer, this.message, _snowmanx);
      int _snowmanxx = this.lines.method_30887() * 9;
      this.linesY = (int)((double)height / 2.0 - (double)_snowmanxx / 2.0);
      int _snowmanxxx = this.linesY + _snowmanxx + 9 * 2;
      int _snowmanxxxx = (int)((double)width / 2.0 - (double)_snowmanx / 2.0);

      for (UnmodifiableIterator var9 = this.choiceButtons.iterator(); var9.hasNext(); _snowmanxxxx += _snowman) {
         DialogScreen.ChoiceButton _snowmanxxxxx = (DialogScreen.ChoiceButton)var9.next();
         this.addButton(new ButtonWidget(_snowmanxxxx, _snowmanxxx, this.buttonWidth, 20, _snowmanxxxxx.message, _snowmanxxxxx.pressAction));
      }
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackgroundTexture(0);
      drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, this.linesY - 9 * 2, -1);
      this.lines.method_30888(matrices, this.width / 2, this.linesY);
      super.render(matrices, mouseX, mouseY, delta);
   }

   @Override
   public boolean shouldCloseOnEsc() {
      return false;
   }

   public static final class ChoiceButton {
      private final Text message;
      private final ButtonWidget.PressAction pressAction;

      public ChoiceButton(Text message, ButtonWidget.PressAction pressAction) {
         this.message = message;
         this.pressAction = pressAction;
      }
   }
}
