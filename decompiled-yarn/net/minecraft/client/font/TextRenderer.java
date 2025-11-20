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

public class TextRenderer {
   private static final Vector3f FORWARD_SHIFT = new Vector3f(0.0F, 0.0F, 0.03F);
   public final int fontHeight = 9;
   public final Random random = new Random();
   private final Function<Identifier, FontStorage> fontStorageAccessor;
   private final TextHandler handler;

   public TextRenderer(Function<Identifier, FontStorage> fontStorageAccessor) {
      this.fontStorageAccessor = fontStorageAccessor;
      this.handler = new TextHandler((_snowman, style) -> this.getFontStorage(style.getFont()).getGlyph(_snowman).getAdvance(style.isBold()));
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
         Bidi _snowman = new Bidi(new ArabicShaping(8).shape(text), 127);
         _snowman.setReorderingMode(0);
         return _snowman.writeReordered(2);
      } catch (ArabicShapingException var3) {
         return text;
      }
   }

   private int draw(String text, float x, float y, int color, Matrix4f matrix, boolean shadow, boolean mirror) {
      if (text == null) {
         return 0;
      } else {
         VertexConsumerProvider.Immediate _snowman = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
         int _snowmanx = this.draw(text, x, y, color, shadow, matrix, _snowman, false, 0, 15728880, mirror);
         _snowman.draw();
         return _snowmanx;
      }
   }

   private int draw(OrderedText text, float x, float y, int color, Matrix4f matrix, boolean shadow) {
      VertexConsumerProvider.Immediate _snowman = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
      int _snowmanx = this.draw(text, x, y, color, shadow, matrix, _snowman, false, 0, 15728880);
      _snowman.draw();
      return _snowmanx;
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
      Matrix4f _snowman = matrix.copy();
      if (shadow) {
         this.drawLayer(text, x, y, color, true, matrix, vertexConsumers, seeThrough, backgroundColor, light);
         _snowman.addToLastColumn(FORWARD_SHIFT);
      }

      x = this.drawLayer(text, x, y, color, false, _snowman, vertexConsumers, seeThrough, backgroundColor, light);
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
      Matrix4f _snowman = matrix.copy();
      if (shadow) {
         this.drawLayer(text, x, y, color, true, matrix, vertexConsumerProvider, seeThrough, backgroundColor, light);
         _snowman.addToLastColumn(FORWARD_SHIFT);
      }

      x = this.drawLayer(text, x, y, color, false, _snowman, vertexConsumerProvider, seeThrough, backgroundColor, light);
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
      TextRenderer.Drawer _snowman = new TextRenderer.Drawer(vertexConsumerProvider, x, y, color, shadow, matrix, seeThrough, light);
      TextVisitFactory.visitFormatted(text, Style.EMPTY, _snowman);
      return _snowman.drawLayer(underlineColor, x);
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
      TextRenderer.Drawer _snowman = new TextRenderer.Drawer(vertexConsumerProvider, x, y, color, shadow, matrix, seeThrough, light);
      text.accept(_snowman);
      return _snowman.drawLayer(underlineColor, x);
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
      Matrix4f _snowman = AffineTransformation.identity().getMatrix();

      for (OrderedText _snowmanx : this.wrapLines(text, maxWidth)) {
         this.draw(_snowmanx, (float)x, (float)y, color, _snowman, false);
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
      public boolean accept(int _snowman, Style _snowman, int _snowman) {
         FontStorage _snowmanxxx = TextRenderer.this.getFontStorage(_snowman.getFont());
         Glyph _snowmanxxxx = _snowmanxxx.getGlyph(_snowman);
         GlyphRenderer _snowmanxxxxx = _snowman.isObfuscated() && _snowman != 32 ? _snowmanxxx.getObfuscatedGlyphRenderer(_snowmanxxxx) : _snowmanxxx.getGlyphRenderer(_snowman);
         boolean _snowmanxxxxxx = _snowman.isBold();
         float _snowmanxxxxxxx = this.alpha;
         TextColor _snowmanxxxxxxxx = _snowman.getColor();
         float _snowmanxxxxxxxxx;
         float _snowmanxxxxxxxxxx;
         float _snowmanxxxxxxxxxxx;
         if (_snowmanxxxxxxxx != null) {
            int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxx.getRgb();
            _snowmanxxxxxxxxx = (float)(_snowmanxxxxxxxxxxxx >> 16 & 0xFF) / 255.0F * this.brightnessMultiplier;
            _snowmanxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxxx >> 8 & 0xFF) / 255.0F * this.brightnessMultiplier;
            _snowmanxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxxx & 0xFF) / 255.0F * this.brightnessMultiplier;
         } else {
            _snowmanxxxxxxxxx = this.red;
            _snowmanxxxxxxxxxx = this.green;
            _snowmanxxxxxxxxxxx = this.blue;
         }

         if (!(_snowmanxxxxx instanceof EmptyGlyphRenderer)) {
            float _snowmanxxxxxxxxxxxx = _snowmanxxxxxx ? _snowmanxxxx.getBoldOffset() : 0.0F;
            float _snowmanxxxxxxxxxxxxx = this.shadow ? _snowmanxxxx.getShadowOffset() : 0.0F;
            VertexConsumer _snowmanxxxxxxxxxxxxxx = this.vertexConsumers.getBuffer(_snowmanxxxxx.getLayer(this.seeThrough));
            TextRenderer.this.drawGlyph(
               _snowmanxxxxx,
               _snowmanxxxxxx,
               _snowman.isItalic(),
               _snowmanxxxxxxxxxxxx,
               this.x + _snowmanxxxxxxxxxxxxx,
               this.y + _snowmanxxxxxxxxxxxxx,
               this.matrix,
               _snowmanxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowmanxxxxxxxxxx,
               _snowmanxxxxxxxxxxx,
               _snowmanxxxxxxx,
               this.light
            );
         }

         float _snowmanxxxxxxxxxxxx = _snowmanxxxx.getAdvance(_snowmanxxxxxx);
         float _snowmanxxxxxxxxxxxxx = this.shadow ? 1.0F : 0.0F;
         if (_snowman.isStrikethrough()) {
            this.addRectangle(
               new GlyphRenderer.Rectangle(
                  this.x + _snowmanxxxxxxxxxxxxx - 1.0F,
                  this.y + _snowmanxxxxxxxxxxxxx + 4.5F,
                  this.x + _snowmanxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxx,
                  this.y + _snowmanxxxxxxxxxxxxx + 4.5F - 1.0F,
                  0.01F,
                  _snowmanxxxxxxxxx,
                  _snowmanxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx,
                  _snowmanxxxxxxx
               )
            );
         }

         if (_snowman.isUnderlined()) {
            this.addRectangle(
               new GlyphRenderer.Rectangle(
                  this.x + _snowmanxxxxxxxxxxxxx - 1.0F,
                  this.y + _snowmanxxxxxxxxxxxxx + 9.0F,
                  this.x + _snowmanxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxx,
                  this.y + _snowmanxxxxxxxxxxxxx + 9.0F - 1.0F,
                  0.01F,
                  _snowmanxxxxxxxxx,
                  _snowmanxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx,
                  _snowmanxxxxxxx
               )
            );
         }

         this.x += _snowmanxxxxxxxxxxxx;
         return true;
      }

      public float drawLayer(int underlineColor, float x) {
         if (underlineColor != 0) {
            float _snowman = (float)(underlineColor >> 24 & 0xFF) / 255.0F;
            float _snowmanx = (float)(underlineColor >> 16 & 0xFF) / 255.0F;
            float _snowmanxx = (float)(underlineColor >> 8 & 0xFF) / 255.0F;
            float _snowmanxxx = (float)(underlineColor & 0xFF) / 255.0F;
            this.addRectangle(new GlyphRenderer.Rectangle(x - 1.0F, this.y + 9.0F, this.x + 1.0F, this.y - 1.0F, 0.01F, _snowmanx, _snowmanxx, _snowmanxxx, _snowman));
         }

         if (this.rectangles != null) {
            GlyphRenderer _snowman = TextRenderer.this.getFontStorage(Style.DEFAULT_FONT_ID).getRectangleRenderer();
            VertexConsumer _snowmanx = this.vertexConsumers.getBuffer(_snowman.getLayer(this.seeThrough));

            for (GlyphRenderer.Rectangle _snowmanxx : this.rectangles) {
               _snowman.drawRectangle(_snowmanxx, this.matrix, _snowmanx, this.light);
            }
         }

         return this.x;
      }
   }
}
