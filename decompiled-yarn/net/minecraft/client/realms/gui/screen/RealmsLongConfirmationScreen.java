package net.minecraft.client.realms.gui.screen;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.Realms;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class RealmsLongConfirmationScreen extends RealmsScreen {
   private final RealmsLongConfirmationScreen.Type type;
   private final Text line2;
   private final Text line3;
   protected final BooleanConsumer field_22697;
   private final boolean yesNoQuestion;

   public RealmsLongConfirmationScreen(BooleanConsumer _snowman, RealmsLongConfirmationScreen.Type type, Text _snowman, Text _snowman, boolean yesNoQuestion) {
      this.field_22697 = _snowman;
      this.type = type;
      this.line2 = _snowman;
      this.line3 = _snowman;
      this.yesNoQuestion = yesNoQuestion;
   }

   @Override
   public void init() {
      Realms.narrateNow(this.type.text, this.line2.getString(), this.line3.getString());
      if (this.yesNoQuestion) {
         this.addButton(new ButtonWidget(this.width / 2 - 105, row(8), 100, 20, ScreenTexts.YES, _snowman -> this.field_22697.accept(true)));
         this.addButton(new ButtonWidget(this.width / 2 + 5, row(8), 100, 20, ScreenTexts.NO, _snowman -> this.field_22697.accept(false)));
      } else {
         this.addButton(new ButtonWidget(this.width / 2 - 50, row(8), 100, 20, new TranslatableText("mco.gui.ok"), _snowman -> this.field_22697.accept(true)));
      }
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         this.field_22697.accept(false);
         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      drawCenteredString(matrices, this.textRenderer, this.type.text, this.width / 2, row(2), this.type.colorCode);
      drawCenteredText(matrices, this.textRenderer, this.line2, this.width / 2, row(4), 16777215);
      drawCenteredText(matrices, this.textRenderer, this.line3, this.width / 2, row(6), 16777215);
      super.render(matrices, mouseX, mouseY, delta);
   }

   public static enum Type {
      Warning("Warning!", 16711680),
      Info("Info!", 8226750);

      public final int colorCode;
      public final String text;

      private Type(String text, int colorCode) {
         this.text = text;
         this.colorCode = colorCode;
      }
   }
}
