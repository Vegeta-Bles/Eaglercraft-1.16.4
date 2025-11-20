package net.minecraft.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
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

   public TexturedButtonWidget(int i, int j, int k, int l, int m, int n, int o, Identifier arg, int p, int q, ButtonWidget.PressAction arg2, Text arg3) {
      this(i, j, k, l, m, n, o, arg, p, q, arg2, EMPTY, arg3);
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
      ButtonWidget.TooltipSupplier arg3,
      Text arg4
   ) {
      super(x, y, width, height, arg4, pressAction, arg3);
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
      MinecraftClient lv = MinecraftClient.getInstance();
      lv.getTextureManager().bindTexture(this.texture);
      int k = this.v;
      if (this.isHovered()) {
         k += this.hoveredVOffset;
      }

      RenderSystem.enableDepthTest();
      drawTexture(matrices, this.x, this.y, (float)this.u, (float)k, this.width, this.height, this.textureWidth, this.textureHeight);
      if (this.isHovered()) {
         this.renderToolTip(matrices, mouseX, mouseY);
      }
   }
}
