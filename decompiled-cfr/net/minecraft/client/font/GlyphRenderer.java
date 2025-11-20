/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.font;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.Matrix4f;

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

    public GlyphRenderer(RenderLayer textLayer, RenderLayer seeThroughTextLayer, float uMin, float uMax, float vMin, float vMax, float xMin, float xMax, float yMin, float yMax) {
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

    public void draw(boolean italic, float x, float y, Matrix4f matrix, VertexConsumer vertexConsumer, float red, float green, float blue, float alpha, int light) {
        int n = 3;
        float _snowman2 = x + this.xMin;
        float _snowman3 = x + this.xMax;
        float _snowman4 = this.yMin - 3.0f;
        float _snowman5 = this.yMax - 3.0f;
        float _snowman6 = y + _snowman4;
        float _snowman7 = y + _snowman5;
        float _snowman8 = italic ? 1.0f - 0.25f * _snowman4 : 0.0f;
        float _snowman9 = italic ? 1.0f - 0.25f * _snowman5 : 0.0f;
        vertexConsumer.vertex(matrix, _snowman2 + _snowman8, _snowman6, 0.0f).color(red, green, blue, alpha).texture(this.uMin, this.vMin).light(light).next();
        vertexConsumer.vertex(matrix, _snowman2 + _snowman9, _snowman7, 0.0f).color(red, green, blue, alpha).texture(this.uMin, this.vMax).light(light).next();
        vertexConsumer.vertex(matrix, _snowman3 + _snowman9, _snowman7, 0.0f).color(red, green, blue, alpha).texture(this.uMax, this.vMax).light(light).next();
        vertexConsumer.vertex(matrix, _snowman3 + _snowman8, _snowman6, 0.0f).color(red, green, blue, alpha).texture(this.uMax, this.vMin).light(light).next();
    }

    public void drawRectangle(Rectangle rectangle, Matrix4f matrix, VertexConsumer vertexConsumer, int light) {
        vertexConsumer.vertex(matrix, rectangle.xMin, rectangle.yMin, rectangle.zIndex).color(rectangle.red, rectangle.green, rectangle.blue, rectangle.alpha).texture(this.uMin, this.vMin).light(light).next();
        vertexConsumer.vertex(matrix, rectangle.xMax, rectangle.yMin, rectangle.zIndex).color(rectangle.red, rectangle.green, rectangle.blue, rectangle.alpha).texture(this.uMin, this.vMax).light(light).next();
        vertexConsumer.vertex(matrix, rectangle.xMax, rectangle.yMax, rectangle.zIndex).color(rectangle.red, rectangle.green, rectangle.blue, rectangle.alpha).texture(this.uMax, this.vMax).light(light).next();
        vertexConsumer.vertex(matrix, rectangle.xMin, rectangle.yMax, rectangle.zIndex).color(rectangle.red, rectangle.green, rectangle.blue, rectangle.alpha).texture(this.uMax, this.vMin).light(light).next();
    }

    public RenderLayer getLayer(boolean seeThrough) {
        return seeThrough ? this.seeThroughTextLayer : this.textLayer;
    }

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

