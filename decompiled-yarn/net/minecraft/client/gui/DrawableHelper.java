package net.minecraft.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.function.BiConsumer;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

public abstract class DrawableHelper {
   public static final Identifier OPTIONS_BACKGROUND_TEXTURE = new Identifier("textures/gui/options_background.png");
   public static final Identifier STATS_ICON_TEXTURE = new Identifier("textures/gui/container/stats_icons.png");
   public static final Identifier GUI_ICONS_TEXTURE = new Identifier("textures/gui/icons.png");
   private int zOffset;

   public DrawableHelper() {
   }

   protected void drawHorizontalLine(MatrixStack matrices, int x1, int x2, int y, int color) {
      if (x2 < x1) {
         int _snowman = x1;
         x1 = x2;
         x2 = _snowman;
      }

      fill(matrices, x1, y, x2 + 1, y + 1, color);
   }

   protected void drawVerticalLine(MatrixStack matrices, int x, int y1, int y2, int color) {
      if (y2 < y1) {
         int _snowman = y1;
         y1 = y2;
         y2 = _snowman;
      }

      fill(matrices, x, y1 + 1, x + 1, y2, color);
   }

   public static void fill(MatrixStack matrices, int x1, int y1, int x2, int y2, int color) {
      fill(matrices.peek().getModel(), x1, y1, x2, y2, color);
   }

   private static void fill(Matrix4f matrix, int x1, int y1, int x2, int y2, int color) {
      if (x1 < x2) {
         int _snowman = x1;
         x1 = x2;
         x2 = _snowman;
      }

      if (y1 < y2) {
         int _snowman = y1;
         y1 = y2;
         y2 = _snowman;
      }

      float _snowman = (float)(color >> 24 & 0xFF) / 255.0F;
      float _snowmanx = (float)(color >> 16 & 0xFF) / 255.0F;
      float _snowmanxx = (float)(color >> 8 & 0xFF) / 255.0F;
      float _snowmanxxx = (float)(color & 0xFF) / 255.0F;
      BufferBuilder _snowmanxxxx = Tessellator.getInstance().getBuffer();
      RenderSystem.enableBlend();
      RenderSystem.disableTexture();
      RenderSystem.defaultBlendFunc();
      _snowmanxxxx.begin(7, VertexFormats.POSITION_COLOR);
      _snowmanxxxx.vertex(matrix, (float)x1, (float)y2, 0.0F).color(_snowmanx, _snowmanxx, _snowmanxxx, _snowman).next();
      _snowmanxxxx.vertex(matrix, (float)x2, (float)y2, 0.0F).color(_snowmanx, _snowmanxx, _snowmanxxx, _snowman).next();
      _snowmanxxxx.vertex(matrix, (float)x2, (float)y1, 0.0F).color(_snowmanx, _snowmanxx, _snowmanxxx, _snowman).next();
      _snowmanxxxx.vertex(matrix, (float)x1, (float)y1, 0.0F).color(_snowmanx, _snowmanxx, _snowmanxxx, _snowman).next();
      _snowmanxxxx.end();
      BufferRenderer.draw(_snowmanxxxx);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
   }

   protected void fillGradient(MatrixStack matrices, int xStart, int yStart, int xEnd, int yEnd, int colorStart, int colorEnd) {
      RenderSystem.disableTexture();
      RenderSystem.enableBlend();
      RenderSystem.disableAlphaTest();
      RenderSystem.defaultBlendFunc();
      RenderSystem.shadeModel(7425);
      Tessellator _snowman = Tessellator.getInstance();
      BufferBuilder _snowmanx = _snowman.getBuffer();
      _snowmanx.begin(7, VertexFormats.POSITION_COLOR);
      fillGradient(matrices.peek().getModel(), _snowmanx, xStart, yStart, xEnd, yEnd, this.zOffset, colorStart, colorEnd);
      _snowman.draw();
      RenderSystem.shadeModel(7424);
      RenderSystem.disableBlend();
      RenderSystem.enableAlphaTest();
      RenderSystem.enableTexture();
   }

   protected static void fillGradient(Matrix4f matrix, BufferBuilder _snowman, int xStart, int yStart, int xEnd, int yEnd, int z, int colorStart, int colorEnd) {
      float _snowmanx = (float)(colorStart >> 24 & 0xFF) / 255.0F;
      float _snowmanxx = (float)(colorStart >> 16 & 0xFF) / 255.0F;
      float _snowmanxxx = (float)(colorStart >> 8 & 0xFF) / 255.0F;
      float _snowmanxxxx = (float)(colorStart & 0xFF) / 255.0F;
      float _snowmanxxxxx = (float)(colorEnd >> 24 & 0xFF) / 255.0F;
      float _snowmanxxxxxx = (float)(colorEnd >> 16 & 0xFF) / 255.0F;
      float _snowmanxxxxxxx = (float)(colorEnd >> 8 & 0xFF) / 255.0F;
      float _snowmanxxxxxxxx = (float)(colorEnd & 0xFF) / 255.0F;
      _snowman.vertex(matrix, (float)xEnd, (float)yStart, (float)z).color(_snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanx).next();
      _snowman.vertex(matrix, (float)xStart, (float)yStart, (float)z).color(_snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanx).next();
      _snowman.vertex(matrix, (float)xStart, (float)yEnd, (float)z).color(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxx).next();
      _snowman.vertex(matrix, (float)xEnd, (float)yEnd, (float)z).color(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxx).next();
   }

   public static void drawCenteredString(MatrixStack matrices, TextRenderer textRenderer, String text, int centerX, int y, int color) {
      textRenderer.drawWithShadow(matrices, text, (float)(centerX - textRenderer.getWidth(text) / 2), (float)y, color);
   }

   public static void drawCenteredText(MatrixStack matrices, TextRenderer textRenderer, Text text, int centerX, int y, int color) {
      OrderedText _snowman = text.asOrderedText();
      textRenderer.drawWithShadow(matrices, _snowman, (float)(centerX - textRenderer.getWidth(_snowman) / 2), (float)y, color);
   }

   public static void drawStringWithShadow(MatrixStack matrices, TextRenderer textRenderer, String text, int x, int y, int color) {
      textRenderer.drawWithShadow(matrices, text, (float)x, (float)y, color);
   }

   public static void drawTextWithShadow(MatrixStack matrices, TextRenderer textRenderer, Text text, int x, int y, int color) {
      textRenderer.drawWithShadow(matrices, text, (float)x, (float)y, color);
   }

   public void method_29343(int _snowman, int _snowman, BiConsumer<Integer, Integer> _snowman) {
      RenderSystem.blendFuncSeparate(
         GlStateManager.SrcFactor.ZERO,
         GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA,
         GlStateManager.SrcFactor.SRC_ALPHA,
         GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA
      );
      _snowman.accept(_snowman + 1, _snowman);
      _snowman.accept(_snowman - 1, _snowman);
      _snowman.accept(_snowman, _snowman + 1);
      _snowman.accept(_snowman, _snowman - 1);
      RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
      _snowman.accept(_snowman, _snowman);
   }

   public static void drawSprite(MatrixStack matrices, int x, int y, int z, int width, int height, Sprite sprite) {
      drawTexturedQuad(matrices.peek().getModel(), x, x + width, y, y + height, z, sprite.getMinU(), sprite.getMaxU(), sprite.getMinV(), sprite.getMaxV());
   }

   public void drawTexture(MatrixStack matrices, int x, int y, int u, int v, int width, int height) {
      drawTexture(matrices, x, y, this.zOffset, (float)u, (float)v, width, height, 256, 256);
   }

   public static void drawTexture(MatrixStack matrices, int x, int y, int z, float u, float v, int width, int height, int textureHeight, int textureWidth) {
      drawTexture(matrices, x, x + width, y, y + height, z, width, height, u, v, textureWidth, textureHeight);
   }

   public static void drawTexture(
      MatrixStack matrices, int x, int y, int width, int height, float u, float v, int regionWidth, int regionHeight, int textureWidth, int textureHeight
   ) {
      drawTexture(matrices, x, x + width, y, y + height, 0, regionWidth, regionHeight, u, v, textureWidth, textureHeight);
   }

   public static void drawTexture(MatrixStack matrices, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
      drawTexture(matrices, x, y, width, height, u, v, width, height, textureWidth, textureHeight);
   }

   private static void drawTexture(
      MatrixStack matrices, int x0, int y0, int x1, int y1, int z, int regionWidth, int regionHeight, float u, float v, int textureWidth, int textureHeight
   ) {
      drawTexturedQuad(
         matrices.peek().getModel(),
         x0,
         y0,
         x1,
         y1,
         z,
         (u + 0.0F) / (float)textureWidth,
         (u + (float)regionWidth) / (float)textureWidth,
         (v + 0.0F) / (float)textureHeight,
         (v + (float)regionHeight) / (float)textureHeight
      );
   }

   private static void drawTexturedQuad(Matrix4f matrices, int x0, int x1, int y0, int y1, int z, float u0, float u1, float v0, float v1) {
      BufferBuilder _snowman = Tessellator.getInstance().getBuffer();
      _snowman.begin(7, VertexFormats.POSITION_TEXTURE);
      _snowman.vertex(matrices, (float)x0, (float)y1, (float)z).texture(u0, v1).next();
      _snowman.vertex(matrices, (float)x1, (float)y1, (float)z).texture(u1, v1).next();
      _snowman.vertex(matrices, (float)x1, (float)y0, (float)z).texture(u1, v0).next();
      _snowman.vertex(matrices, (float)x0, (float)y0, (float)z).texture(u0, v0).next();
      _snowman.end();
      RenderSystem.enableAlphaTest();
      BufferRenderer.draw(_snowman);
   }

   public int getZOffset() {
      return this.zOffset;
   }

   public void setZOffset(int zOffset) {
      this.zOffset = zOffset;
   }
}
