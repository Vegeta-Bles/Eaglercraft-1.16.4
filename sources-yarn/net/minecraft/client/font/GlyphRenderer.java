package net.minecraft.client.font;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.Matrix4f;

@Environment(EnvType.CLIENT)
public class GlyphRenderer {
   private final RenderLayer textLayer;
   private final RenderLayer seeThroughTextLayer;
   private final float uMin;
   private final float uMax;
   private final float vMin;
   private final float vMax;
   private final float xMin;
   private final float xMax;
   private final float yMin;
   private final float yMax;

   public GlyphRenderer(
      RenderLayer textLayer, RenderLayer seeThroughTextLayer, float uMin, float uMax, float vMin, float vMax, float xMin, float xMax, float yMin, float yMax
   ) {
      this.textLayer = textLayer;
      this.seeThroughTextLayer = seeThroughTextLayer;
      this.uMin = uMin;
      this.uMax = uMax;
      this.vMin = vMin;
      this.vMax = vMax;
      this.xMin = xMin;
      this.xMax = xMax;
      this.yMin = yMin;
      this.yMax = yMax;
   }

   public void draw(
      boolean italic, float x, float y, Matrix4f matrix, VertexConsumer vertexConsumer, float red, float green, float blue, float alpha, int light
   ) {
      int m = 3;
      float n = x + this.xMin;
      float o = x + this.xMax;
      float p = this.yMin - 3.0F;
      float q = this.yMax - 3.0F;
      float r = y + p;
      float s = y + q;
      float t = italic ? 1.0F - 0.25F * p : 0.0F;
      float u = italic ? 1.0F - 0.25F * q : 0.0F;
      vertexConsumer.vertex(matrix, n + t, r, 0.0F).color(red, green, blue, alpha).texture(this.uMin, this.vMin).light(light).next();
      vertexConsumer.vertex(matrix, n + u, s, 0.0F).color(red, green, blue, alpha).texture(this.uMin, this.vMax).light(light).next();
      vertexConsumer.vertex(matrix, o + u, s, 0.0F).color(red, green, blue, alpha).texture(this.uMax, this.vMax).light(light).next();
      vertexConsumer.vertex(matrix, o + t, r, 0.0F).color(red, green, blue, alpha).texture(this.uMax, this.vMin).light(light).next();
   }

   public void drawRectangle(GlyphRenderer.Rectangle rectangle, Matrix4f matrix, VertexConsumer vertexConsumer, int light) {
      vertexConsumer.vertex(matrix, rectangle.xMin, rectangle.yMin, rectangle.zIndex)
         .color(rectangle.red, rectangle.green, rectangle.blue, rectangle.alpha)
         .texture(this.uMin, this.vMin)
         .light(light)
         .next();
      vertexConsumer.vertex(matrix, rectangle.xMax, rectangle.yMin, rectangle.zIndex)
         .color(rectangle.red, rectangle.green, rectangle.blue, rectangle.alpha)
         .texture(this.uMin, this.vMax)
         .light(light)
         .next();
      vertexConsumer.vertex(matrix, rectangle.xMax, rectangle.yMax, rectangle.zIndex)
         .color(rectangle.red, rectangle.green, rectangle.blue, rectangle.alpha)
         .texture(this.uMax, this.vMax)
         .light(light)
         .next();
      vertexConsumer.vertex(matrix, rectangle.xMin, rectangle.yMax, rectangle.zIndex)
         .color(rectangle.red, rectangle.green, rectangle.blue, rectangle.alpha)
         .texture(this.uMax, this.vMin)
         .light(light)
         .next();
   }

   public RenderLayer getLayer(boolean seeThrough) {
      return seeThrough ? this.seeThroughTextLayer : this.textLayer;
   }

   @Environment(EnvType.CLIENT)
   public static class Rectangle {
      protected final float xMin;
      protected final float yMin;
      protected final float xMax;
      protected final float yMax;
      protected final float zIndex;
      protected final float red;
      protected final float green;
      protected final float blue;
      protected final float alpha;

      public Rectangle(float xMin, float yMin, float xMax, float yMax, float zIndex, float red, float green, float blue, float alpha) {
         this.xMin = xMin;
         this.yMin = yMin;
         this.xMax = xMax;
         this.yMax = yMax;
         this.zIndex = zIndex;
         this.red = red;
         this.green = green;
         this.blue = blue;
         this.alpha = alpha;
      }
   }
}
