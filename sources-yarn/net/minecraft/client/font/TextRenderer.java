package net.minecraft.client.font;

import com.google.common.collect.Lists;
import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.AffineTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;

@Environment(EnvType.CLIENT)
public class TextRenderer {
   private static final Vector3f FORWARD_SHIFT = new Vector3f(0.0F, 0.0F, 0.03F);
   public final int fontHeight = 9;
   public final Random random = new Random();
   private final Function<Identifier, FontStorage> fontStorageAccessor;
   private final TextHandler handler;

   public TextRenderer(Function<Identifier, FontStorage> fontStorageAccessor) {
      this.fontStorageAccessor = fontStorageAccessor;
      this.handler = new TextHandler((i, style) -> this.getFontStorage(style.getFont()).getGlyph(i).getAdvance(style.isBold()));
   }

   private FontStorage getFontStorage(Identifier id) {
      return this.fontStorageAccessor.apply(id);
   }

   public int drawWithShadow(MatrixStack matrices, String text, float x, float y, int color) {
      return this.draw(text, x, y, color, matrices.peek().getModel(), true, this.isRightToLeft());
   }

   public int drawWithShadow(MatrixStack matrices, String text, float x, float y, int color, boolean rightToLeft) {
      RenderSystem.enableAlphaTest();
      return this.draw(text, x, y, color, matrices.peek().getModel(), true, rightToLeft);
   }

   public int draw(MatrixStack matrices, String text, float x, float y, int color) {
      RenderSystem.enableAlphaTest();
      return this.draw(text, x, y, color, matrices.peek().getModel(), false, this.isRightToLeft());
   }

   public int drawWithShadow(MatrixStack matrices, OrderedText text, float x, float y, int color) {
      RenderSystem.enableAlphaTest();
      return this.draw(text, x, y, color, matrices.peek().getModel(), true);
   }

   public int drawWithShadow(MatrixStack matrices, Text text, float x, float y, int color) {
      RenderSystem.enableAlphaTest();
      return this.draw(text.asOrderedText(), x, y, color, matrices.peek().getModel(), true);
   }

   public int draw(MatrixStack matrices, OrderedText text, float x, float y, int color) {
      RenderSystem.enableAlphaTest();
      return this.draw(text, x, y, color, matrices.peek().getModel(), false);
   }

   public int draw(MatrixStack matrices, Text text, float x, float y, int color) {
      RenderSystem.enableAlphaTest();
      return this.draw(text.asOrderedText(), x, y, color, matrices.peek().getModel(), false);
   }

   public String mirror(String text) {
      try {
         Bidi bidi = new Bidi(new ArabicShaping(8).shape(text), 127);
         bidi.setReorderingMode(0);
         return bidi.writeReordered(2);
      } catch (ArabicShapingException var3) {
         return text;
      }
   }

   private int draw(String text, float x, float y, int color, Matrix4f matrix, boolean shadow, boolean mirror) {
      if (text == null) {
         return 0;
      } else {
         VertexConsumerProvider.Immediate lv = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
         int j = this.draw(text, x, y, color, shadow, matrix, lv, false, 0, 15728880, mirror);
         lv.draw();
         return j;
      }
   }

   private int draw(OrderedText text, float x, float y, int color, Matrix4f matrix, boolean shadow) {
      VertexConsumerProvider.Immediate lv = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
      int j = this.draw(text, x, y, color, shadow, matrix, lv, false, 0, 15728880);
      lv.draw();
      return j;
   }

   public int draw(
      String text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrix,
      VertexConsumerProvider vertexConsumers,
      boolean seeThrough,
      int backgroundColor,
      int light
   ) {
      return this.draw(text, x, y, color, shadow, matrix, vertexConsumers, seeThrough, backgroundColor, light, this.isRightToLeft());
   }

   public int draw(
      String text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrix,
      VertexConsumerProvider vertexConsumers,
      boolean seeThrough,
      int backgroundColor,
      int light,
      boolean rightToLeft
   ) {
      return this.drawInternal(text, x, y, color, shadow, matrix, vertexConsumers, seeThrough, backgroundColor, light, rightToLeft);
   }

   public int draw(
      Text text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrix,
      VertexConsumerProvider vertexConsumers,
      boolean seeThrough,
      int backgroundColor,
      int light
   ) {
      return this.draw(text.asOrderedText(), x, y, color, shadow, matrix, vertexConsumers, seeThrough, backgroundColor, light);
   }

   public int draw(
      OrderedText text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrix,
      VertexConsumerProvider vertexConsumers,
      boolean seeThrough,
      int backgroundColor,
      int light
   ) {
      return this.drawInternal(text, x, y, color, shadow, matrix, vertexConsumers, seeThrough, backgroundColor, light);
   }

   private static int tweakTransparency(int argb) {
      return (argb & -67108864) == 0 ? argb | 0xFF000000 : argb;
   }

   private int drawInternal(
      String text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrix,
      VertexConsumerProvider vertexConsumers,
      boolean seeThrough,
      int backgroundColor,
      int light,
      boolean mirror
   ) {
      if (mirror) {
         text = this.mirror(text);
      }

      color = tweakTransparency(color);
      Matrix4f lv = matrix.copy();
      if (shadow) {
         this.drawLayer(text, x, y, color, true, matrix, vertexConsumers, seeThrough, backgroundColor, light);
         lv.addToLastColumn(FORWARD_SHIFT);
      }

      x = this.drawLayer(text, x, y, color, false, lv, vertexConsumers, seeThrough, backgroundColor, light);
      return (int)x + (shadow ? 1 : 0);
   }

   private int drawInternal(
      OrderedText text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrix,
      VertexConsumerProvider vertexConsumerProvider,
      boolean seeThrough,
      int backgroundColor,
      int light
   ) {
      color = tweakTransparency(color);
      Matrix4f lv = matrix.copy();
      if (shadow) {
         this.drawLayer(text, x, y, color, true, matrix, vertexConsumerProvider, seeThrough, backgroundColor, light);
         lv.addToLastColumn(FORWARD_SHIFT);
      }

      x = this.drawLayer(text, x, y, color, false, lv, vertexConsumerProvider, seeThrough, backgroundColor, light);
      return (int)x + (shadow ? 1 : 0);
   }

   private float drawLayer(
      String text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrix,
      VertexConsumerProvider vertexConsumerProvider,
      boolean seeThrough,
      int underlineColor,
      int light
   ) {
      TextRenderer.Drawer lv = new TextRenderer.Drawer(vertexConsumerProvider, x, y, color, shadow, matrix, seeThrough, light);
      TextVisitFactory.visitFormatted(text, Style.EMPTY, lv);
      return lv.drawLayer(underlineColor, x);
   }

   private float drawLayer(
      OrderedText text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrix,
      VertexConsumerProvider vertexConsumerProvider,
      boolean seeThrough,
      int underlineColor,
      int light
   ) {
      TextRenderer.Drawer lv = new TextRenderer.Drawer(vertexConsumerProvider, x, y, color, shadow, matrix, seeThrough, light);
      text.accept(lv);
      return lv.drawLayer(underlineColor, x);
   }

   private void drawGlyph(
      GlyphRenderer glyphRenderer,
      boolean bold,
      boolean italic,
      float weight,
      float x,
      float y,
      Matrix4f matrix,
      VertexConsumer vertexConsumer,
      float red,
      float green,
      float blue,
      float alpha,
      int light
   ) {
      glyphRenderer.draw(italic, x, y, matrix, vertexConsumer, red, green, blue, alpha, light);
      if (bold) {
         glyphRenderer.draw(italic, x + weight, y, matrix, vertexConsumer, red, green, blue, alpha, light);
      }
   }

   public int getWidth(String text) {
      return MathHelper.ceil(this.handler.getWidth(text));
   }

   public int getWidth(StringVisitable text) {
      return MathHelper.ceil(this.handler.getWidth(text));
   }

   public int getWidth(OrderedText text) {
      return MathHelper.ceil(this.handler.getWidth(text));
   }

   public String trimToWidth(String text, int maxWidth, boolean backwards) {
      return backwards ? this.handler.trimToWidthBackwards(text, maxWidth, Style.EMPTY) : this.handler.trimToWidth(text, maxWidth, Style.EMPTY);
   }

   public String trimToWidth(String text, int maxWidth) {
      return this.handler.trimToWidth(text, maxWidth, Style.EMPTY);
   }

   public StringVisitable trimToWidth(StringVisitable text, int width) {
      return this.handler.trimToWidth(text, width, Style.EMPTY);
   }

   public void drawTrimmed(StringVisitable text, int x, int y, int maxWidth, int color) {
      Matrix4f lv = AffineTransformation.identity().getMatrix();

      for (OrderedText lv2 : this.wrapLines(text, maxWidth)) {
         this.draw(lv2, (float)x, (float)y, color, lv, false);
         y += 9;
      }
   }

   public int getStringBoundedHeight(String text, int maxWidth) {
      return 9 * this.handler.wrapLines(text, maxWidth, Style.EMPTY).size();
   }

   public List<OrderedText> wrapLines(StringVisitable text, int width) {
      return Language.getInstance().reorder(this.handler.wrapLines(text, width, Style.EMPTY));
   }

   public boolean isRightToLeft() {
      return Language.getInstance().isRightToLeft();
   }

   public TextHandler getTextHandler() {
      return this.handler;
   }

   @Environment(EnvType.CLIENT)
   class Drawer implements CharacterVisitor {
      final VertexConsumerProvider vertexConsumers;
      private final boolean shadow;
      private final float brightnessMultiplier;
      private final float red;
      private final float green;
      private final float blue;
      private final float alpha;
      private final Matrix4f matrix;
      private final boolean seeThrough;
      private final int light;
      private float x;
      private float y;
      @Nullable
      private List<GlyphRenderer.Rectangle> rectangles;

      private void addRectangle(GlyphRenderer.Rectangle rectangle) {
         if (this.rectangles == null) {
            this.rectangles = Lists.newArrayList();
         }

         this.rectangles.add(rectangle);
      }

      public Drawer(VertexConsumerProvider vertexConsumers, float x, float y, int color, boolean shadow, Matrix4f matrix, boolean seeThrough, int light) {
         this.vertexConsumers = vertexConsumers;
         this.x = x;
         this.y = y;
         this.shadow = shadow;
         this.brightnessMultiplier = shadow ? 0.25F : 1.0F;
         this.red = (float)(color >> 16 & 0xFF) / 255.0F * this.brightnessMultiplier;
         this.green = (float)(color >> 8 & 0xFF) / 255.0F * this.brightnessMultiplier;
         this.blue = (float)(color & 0xFF) / 255.0F * this.brightnessMultiplier;
         this.alpha = (float)(color >> 24 & 0xFF) / 255.0F;
         this.matrix = matrix;
         this.seeThrough = seeThrough;
         this.light = light;
      }

      @Override
      public boolean accept(int i, Style arg, int j) {
         FontStorage lv = TextRenderer.this.getFontStorage(arg.getFont());
         Glyph lv2 = lv.getGlyph(j);
         GlyphRenderer lv3 = arg.isObfuscated() && j != 32 ? lv.getObfuscatedGlyphRenderer(lv2) : lv.getGlyphRenderer(j);
         boolean bl = arg.isBold();
         float f = this.alpha;
         TextColor lv4 = arg.getColor();
         float g;
         float h;
         float l;
         if (lv4 != null) {
            int k = lv4.getRgb();
            g = (float)(k >> 16 & 0xFF) / 255.0F * this.brightnessMultiplier;
            h = (float)(k >> 8 & 0xFF) / 255.0F * this.brightnessMultiplier;
            l = (float)(k & 0xFF) / 255.0F * this.brightnessMultiplier;
         } else {
            g = this.red;
            h = this.green;
            l = this.blue;
         }

         if (!(lv3 instanceof EmptyGlyphRenderer)) {
            float p = bl ? lv2.getBoldOffset() : 0.0F;
            float q = this.shadow ? lv2.getShadowOffset() : 0.0F;
            VertexConsumer lv5 = this.vertexConsumers.getBuffer(lv3.getLayer(this.seeThrough));
            TextRenderer.this.drawGlyph(lv3, bl, arg.isItalic(), p, this.x + q, this.y + q, this.matrix, lv5, g, h, l, f, this.light);
         }

         float r = lv2.getAdvance(bl);
         float s = this.shadow ? 1.0F : 0.0F;
         if (arg.isStrikethrough()) {
            this.addRectangle(new GlyphRenderer.Rectangle(this.x + s - 1.0F, this.y + s + 4.5F, this.x + s + r, this.y + s + 4.5F - 1.0F, 0.01F, g, h, l, f));
         }

         if (arg.isUnderlined()) {
            this.addRectangle(new GlyphRenderer.Rectangle(this.x + s - 1.0F, this.y + s + 9.0F, this.x + s + r, this.y + s + 9.0F - 1.0F, 0.01F, g, h, l, f));
         }

         this.x += r;
         return true;
      }

      public float drawLayer(int underlineColor, float x) {
         if (underlineColor != 0) {
            float g = (float)(underlineColor >> 24 & 0xFF) / 255.0F;
            float h = (float)(underlineColor >> 16 & 0xFF) / 255.0F;
            float j = (float)(underlineColor >> 8 & 0xFF) / 255.0F;
            float k = (float)(underlineColor & 0xFF) / 255.0F;
            this.addRectangle(new GlyphRenderer.Rectangle(x - 1.0F, this.y + 9.0F, this.x + 1.0F, this.y - 1.0F, 0.01F, h, j, k, g));
         }

         if (this.rectangles != null) {
            GlyphRenderer lv = TextRenderer.this.getFontStorage(Style.DEFAULT_FONT_ID).getRectangleRenderer();
            VertexConsumer lv2 = this.vertexConsumers.getBuffer(lv.getLayer(this.seeThrough));

            for (GlyphRenderer.Rectangle lv3 : this.rectangles) {
               lv.drawRectangle(lv3, this.matrix, lv2, this.light);
            }
         }

         return this.x;
      }
   }
}
