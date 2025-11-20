package net.minecraft.client.gui.screen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_5489;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class DialogScreen extends Screen {
   private final StringVisitable message;
   private final ImmutableList<DialogScreen.ChoiceButton> choiceButtons;
   private class_5489 lines = class_5489.field_26528;
   private int linesY;
   private int buttonWidth;

   protected DialogScreen(Text title, List<StringVisitable> list, ImmutableList<DialogScreen.ChoiceButton> choiceButtons) {
      super(title);
      this.message = StringVisitable.concat(list);
      this.choiceButtons = choiceButtons;
   }

   @Override
   public String getNarrationMessage() {
      return super.getNarrationMessage() + ". " + this.message.getString();
   }

   @Override
   public void init(MinecraftClient client, int width, int height) {
      super.init(client, width, height);
      UnmodifiableIterator k = this.choiceButtons.iterator();

      while (k.hasNext()) {
         DialogScreen.ChoiceButton lv = (DialogScreen.ChoiceButton)k.next();
         this.buttonWidth = Math.max(this.buttonWidth, 20 + this.textRenderer.getWidth(lv.message) + 20);
      }

      int kx = 5 + this.buttonWidth + 5;
      int l = kx * this.choiceButtons.size();
      this.lines = class_5489.method_30890(this.textRenderer, this.message, l);
      int m = this.lines.method_30887() * 9;
      this.linesY = (int)((double)height / 2.0 - (double)m / 2.0);
      int n = this.linesY + m + 9 * 2;
      int o = (int)((double)width / 2.0 - (double)l / 2.0);

      for (UnmodifiableIterator var9 = this.choiceButtons.iterator(); var9.hasNext(); o += kx) {
         DialogScreen.ChoiceButton lv2 = (DialogScreen.ChoiceButton)var9.next();
         this.addButton(new ButtonWidget(o, n, this.buttonWidth, 20, lv2.message, lv2.pressAction));
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

   @Environment(EnvType.CLIENT)
   public static final class ChoiceButton {
      private final Text message;
      private final ButtonWidget.PressAction pressAction;

      public ChoiceButton(Text message, ButtonWidget.PressAction pressAction) {
         this.message = message;
         this.pressAction = pressAction;
      }
   }
}
