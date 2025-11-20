package net.minecraft.client.realms;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class RealmsLabel implements Element {
   private final Text text;
   private final int x;
   private final int y;
   private final int color;

   public RealmsLabel(Text _snowman, int x, int y, int color) {
      this.text = _snowman;
      this.x = x;
      this.y = y;
      this.color = color;
   }

   public void render(Screen screen, MatrixStack _snowman) {
      Screen.drawCenteredText(_snowman, MinecraftClient.getInstance().textRenderer, this.text, this.x, this.y, this.color);
   }

   public String getText() {
      return this.text.getString();
   }
}
