package net.minecraft.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TexturedButtonWidget extends ButtonWidget {
   private final Identifier texture;
   private final int u;
   private final int v;
   private final int hoveredVOffset;
   private final int textureWidth;
   private final int textureHeight;

   public TexturedButtonWidget(int x, int y, int width, int height, int u, int v, int hoveredVOffset, Identifier texture, ButtonWidget.PressAction pressAction) {
      this(x, y, width, height, u, v, hoveredVOffset, texture, 256, 256, pressAction);
   }

   public TexturedButtonWidget(
      int x,
      int y,
      int width,
      int height,
      int u,
      int v,
      int hoveredVOffset,
      Identifier texture,
      int textureWidth,
      int textureHeight,
      ButtonWidget.PressAction pressAction
   ) {
      this(x, y, width, height, u, v, hoveredVOffset, texture, textureWidth, textureHeight, pressAction, LiteralText.EMPTY);
   }

   public TexturedButtonWidget(int _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman, Identifier _snowman, int _snowman, int _snowman, ButtonWidget.PressAction _snowman, Text _snowman) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, EMPTY, _snowman);
   }

   public TexturedButtonWidget(
      int x,
      int y,
      int width,
      int height,
      int u,
      int v,
      int hoveredVOffset,
      Identifier texture,
      int textureWidth,
      int textureHeight,
      ButtonWidget.PressAction pressAction,
      ButtonWidget.TooltipSupplier _snowman,
      Text _snowman
   ) {
      super(x, y, width, height, _snowman, pressAction, _snowman);
      this.textureWidth = textureWidth;
      this.textureHeight = textureHeight;
      this.u = u;
      this.v = v;
      this.hoveredVOffset = hoveredVOffset;
      this.texture = texture;
   }

   public void setPos(int x, int y) {
      this.x = x;
      this.y = y;
   }

   @Override
   public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      MinecraftClient _snowman = MinecraftClient.getInstance();
      _snowman.getTextureManager().bindTexture(this.texture);
      int _snowmanx = this.v;
      if (this.isHovered()) {
         _snowmanx += this.hoveredVOffset;
      }

      RenderSystem.enableDepthTest();
      drawTexture(matrices, this.x, this.y, (float)this.u, (float)_snowmanx, this.width, this.height, this.textureWidth, this.textureHeight);
      if (this.isHovered()) {
         this.renderToolTip(matrices, mouseX, mouseY);
      }
   }
}
