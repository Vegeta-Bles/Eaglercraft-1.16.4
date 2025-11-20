/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.ibm.icu.text.ArabicShaping
 *  com.ibm.icu.text.ArabicShapingException
 *  com.ibm.icu.text.Bidi
 *  javax.annotation.Nullable
 */
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
import net.minecraft.client.font.EmptyGlyphRenderer;
import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.Glyph;
import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.client.font.TextHandler;
import net.minecraft.client.font.TextVisitFactory;
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
    private static final Vector3f FORWARD_SHIFT = new Vector3f(0.0f, 0.0f, 0.03f);
    public final int fontHeight = 9;
    public final Random random = new Random();
    private final Function<Identifier, FontStorage> fontStorageAccessor;
    private final TextHandler handler;

    public TextRenderer(Function<Identifier, FontStorage> fontStorageAccessor) {
        this.fontStorageAccessor = fontStorageAccessor;
        this.handler = new TextHandler((n, style) -> this.getFontStorage(style.getFont()).getGlyph(n).getAdvance(style.isBold()));
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
        }
        catch (ArabicShapingException arabicShapingException) {
            return text;
        }
    }

    private int draw(String text, float x, float y, int color, Matrix4f matrix, boolean shadow, boolean mirror) {
        if (text == null) {
            return 0;
        }
        VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
        int _snowman2 = this.draw(text, x, y, color, shadow, matrix, immediate, false, 0, 0xF000F0, mirror);
        immediate.draw();
        return _snowman2;
    }

    private int draw(OrderedText text, float x, float y, int color, Matrix4f matrix, boolean shadow) {
        VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
        int _snowman2 = this.draw(text, x, y, color, shadow, matrix, (VertexConsumerProvider)immediate, false, 0, 0xF000F0);
        immediate.draw();
        return _snowman2;
    }

    public int draw(String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumers, boolean seeThrough, int backgroundColor, int light) {
        return this.draw(text, x, y, color, shadow, matrix, vertexConsumers, seeThrough, backgroundColor, light, this.isRightToLeft());
    }

    public int draw(String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumers, boolean seeThrough, int backgroundColor, int light, boolean rightToLeft) {
        return this.drawInternal(text, x, y, color, shadow, matrix, vertexConsumers, seeThrough, backgroundColor, light, rightToLeft);
    }

    public int draw(Text text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumers, boolean seeThrough, int backgroundColor, int light) {
        return this.draw(text.asOrderedText(), x, y, color, shadow, matrix, vertexConsumers, seeThrough, backgroundColor, light);
    }

    public int draw(OrderedText text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumers, boolean seeThrough, int backgroundColor, int light) {
        return this.drawInternal(text, x, y, color, shadow, matrix, vertexConsumers, seeThrough, backgroundColor, light);
    }

    private static int tweakTransparency(int argb) {
        if ((argb & 0xFC000000) == 0) {
            return argb | 0xFF000000;
        }
        return argb;
    }

    private int drawInternal(String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumers, boolean seeThrough, int backgroundColor, int light, boolean mirror) {
        if (mirror) {
            text = this.mirror(text);
        }
        color = TextRenderer.tweakTransparency(color);
        Matrix4f matrix4f = matrix.copy();
        if (shadow) {
            this.drawLayer(text, x, y, color, true, matrix, vertexConsumers, seeThrough, backgroundColor, light);
            matrix4f.addToLastColumn(FORWARD_SHIFT);
        }
        x = this.drawLayer(text, x, y, color, false, matrix4f, vertexConsumers, seeThrough, backgroundColor, light);
        return (int)x + (shadow ? 1 : 0);
    }

    private int drawInternal(OrderedText text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int backgroundColor, int light) {
        color = TextRenderer.tweakTransparency(color);
        Matrix4f matrix4f = matrix.copy();
        if (shadow) {
            this.drawLayer(text, x, y, color, true, matrix, vertexConsumerProvider, seeThrough, backgroundColor, light);
            matrix4f.addToLastColumn(FORWARD_SHIFT);
        }
        x = this.drawLayer(text, x, y, color, false, matrix4f, vertexConsumerProvider, seeThrough, backgroundColor, light);
        return (int)x + (shadow ? 1 : 0);
    }

    private float drawLayer(String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int underlineColor, int light) {
        Drawer drawer = new Drawer(vertexConsumerProvider, x, y, color, shadow, matrix, seeThrough, light);
        TextVisitFactory.visitFormatted(text, Style.EMPTY, (CharacterVisitor)drawer);
        return drawer.drawLayer(underlineColor, x);
    }

    private float drawLayer(OrderedText text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int underlineColor, int light) {
        Drawer drawer = new Drawer(vertexConsumerProvider, x, y, color, shadow, matrix, seeThrough, light);
        text.accept(drawer);
        return drawer.drawLayer(underlineColor, x);
    }

    private void drawGlyph(GlyphRenderer glyphRenderer, boolean bold, boolean italic, float weight, float x, float y, Matrix4f matrix, VertexConsumer vertexConsumer, float red, float green, float blue, float alpha, int light) {
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
        Matrix4f matrix4f = AffineTransformation.identity().getMatrix();
        for (OrderedText orderedText : this.wrapLines(text, maxWidth)) {
            this.draw(orderedText, x, y, color, matrix4f, false);
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

    class Drawer
    implements CharacterVisitor {
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
            this.brightnessMultiplier = shadow ? 0.25f : 1.0f;
            this.red = (float)(color >> 16 & 0xFF) / 255.0f * this.brightnessMultiplier;
            this.green = (float)(color >> 8 & 0xFF) / 255.0f * this.brightnessMultiplier;
            this.blue = (float)(color & 0xFF) / 255.0f * this.brightnessMultiplier;
            this.alpha = (float)(color >> 24 & 0xFF) / 255.0f;
            this.matrix = matrix;
            this.seeThrough = seeThrough;
            this.light = light;
        }

        @Override
        public boolean accept(int n, Style style, int n2) {
            float _snowman11;
            float _snowman9;
            float _snowman8;
            float _snowman7;
            FontStorage fontStorage = TextRenderer.this.getFontStorage(style.getFont());
            Glyph _snowman2 = fontStorage.getGlyph(n2);
            GlyphRenderer _snowman3 = style.isObfuscated() && n2 != 32 ? fontStorage.getObfuscatedGlyphRenderer(_snowman2) : fontStorage.getGlyphRenderer(n2);
            boolean _snowman4 = style.isBold();
            float _snowman5 = this.alpha;
            TextColor _snowman6 = style.getColor();
            if (_snowman6 != null) {
                int n3 = _snowman6.getRgb();
                _snowman7 = (float)(n3 >> 16 & 0xFF) / 255.0f * this.brightnessMultiplier;
                _snowman8 = (float)(n3 >> 8 & 0xFF) / 255.0f * this.brightnessMultiplier;
                _snowman9 = (float)(n3 & 0xFF) / 255.0f * this.brightnessMultiplier;
            } else {
                _snowman7 = this.red;
                _snowman8 = this.green;
                _snowman9 = this.blue;
            }
            if (!(_snowman3 instanceof EmptyGlyphRenderer)) {
                float _snowman10 = _snowman4 ? _snowman2.getBoldOffset() : 0.0f;
                _snowman11 = this.shadow ? _snowman2.getShadowOffset() : 0.0f;
                VertexConsumer _snowman12 = this.vertexConsumers.getBuffer(_snowman3.getLayer(this.seeThrough));
                TextRenderer.this.drawGlyph(_snowman3, _snowman4, style.isItalic(), _snowman10, this.x + _snowman11, this.y + _snowman11, this.matrix, _snowman12, _snowman7, _snowman8, _snowman9, _snowman5, this.light);
            }
            float f = _snowman2.getAdvance(_snowman4);
            float f2 = _snowman11 = this.shadow ? 1.0f : 0.0f;
            if (style.isStrikethrough()) {
                this.addRectangle(new GlyphRenderer.Rectangle(this.x + _snowman11 - 1.0f, this.y + _snowman11 + 4.5f, this.x + _snowman11 + f, this.y + _snowman11 + 4.5f - 1.0f, 0.01f, _snowman7, _snowman8, _snowman9, _snowman5));
            }
            if (style.isUnderlined()) {
                this.addRectangle(new GlyphRenderer.Rectangle(this.x + _snowman11 - 1.0f, this.y + _snowman11 + 9.0f, this.x + _snowman11 + f, this.y + _snowman11 + 9.0f - 1.0f, 0.01f, _snowman7, _snowman8, _snowman9, _snowman5));
            }
            this.x += f;
            return true;
        }

        public float drawLayer(int underlineColor, float x) {
            if (underlineColor != 0) {
                float f = (float)(underlineColor >> 24 & 0xFF) / 255.0f;
                _snowman = (float)(underlineColor >> 16 & 0xFF) / 255.0f;
                _snowman = (float)(underlineColor >> 8 & 0xFF) / 255.0f;
                _snowman = (float)(underlineColor & 0xFF) / 255.0f;
                this.addRectangle(new GlyphRenderer.Rectangle(x - 1.0f, this.y + 9.0f, this.x + 1.0f, this.y - 1.0f, 0.01f, _snowman, _snowman, _snowman, f));
            }
            if (this.rectangles != null) {
                GlyphRenderer glyphRenderer = TextRenderer.this.getFontStorage(Style.DEFAULT_FONT_ID).getRectangleRenderer();
                VertexConsumer _snowman2 = this.vertexConsumers.getBuffer(glyphRenderer.getLayer(this.seeThrough));
                for (GlyphRenderer.Rectangle rectangle : this.rectangles) {
                    glyphRenderer.drawRectangle(rectangle, this.matrix, _snowman2, this.light);
                }
            }
            return this.x;
        }
    }
}

