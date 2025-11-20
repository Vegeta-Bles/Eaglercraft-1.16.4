/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.font;

import net.minecraft.client.font.Glyph;

public interface RenderableGlyph
extends Glyph {
    public int getWidth();

    public int getHeight();

    public void upload(int var1, int var2);

    public boolean hasColor();

    public float getOversample();

    default public float getXMin() {
        return this.getBearingX();
    }

    default public float getXMax() {
        return this.getXMin() + (float)this.getWidth() / this.getOversample();
    }

    default public float getYMin() {
        return this.getAscent();
    }

    default public float getYMax() {
        return this.getYMin() + (float)this.getHeight() / this.getOversample();
    }

    default public float getAscent() {
        return 3.0f;
    }
}

