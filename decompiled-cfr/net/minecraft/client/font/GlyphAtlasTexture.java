/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.font;

import javax.annotation.Nullable;
import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.client.font.RenderableGlyph;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.TextureUtil;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class GlyphAtlasTexture
extends AbstractTexture {
    private final Identifier id;
    private final RenderLayer textLayer;
    private final RenderLayer seeThroughTextLayer;
    private final boolean hasColor;
    private final Slot rootSlot;

    public GlyphAtlasTexture(Identifier id, boolean hasColor) {
        this.id = id;
        this.hasColor = hasColor;
        this.rootSlot = new Slot(0, 0, 256, 256);
        TextureUtil.allocate(hasColor ? NativeImage.GLFormat.ABGR : NativeImage.GLFormat.INTENSITY, this.getGlId(), 256, 256);
        this.textLayer = RenderLayer.getText(id);
        this.seeThroughTextLayer = RenderLayer.getTextSeeThrough(id);
    }

    @Override
    public void load(ResourceManager manager) {
    }

    @Override
    public void close() {
        this.clearGlId();
    }

    @Nullable
    public GlyphRenderer getGlyphRenderer(RenderableGlyph glyph) {
        if (glyph.hasColor() != this.hasColor) {
            return null;
        }
        Slot slot = this.rootSlot.findSlotFor(glyph);
        if (slot != null) {
            this.bindTexture();
            glyph.upload(slot.x, slot.y);
            float f = 256.0f;
            _snowman = 256.0f;
            _snowman = 0.01f;
            return new GlyphRenderer(this.textLayer, this.seeThroughTextLayer, ((float)slot.x + 0.01f) / 256.0f, ((float)slot.x - 0.01f + (float)glyph.getWidth()) / 256.0f, ((float)slot.y + 0.01f) / 256.0f, ((float)slot.y - 0.01f + (float)glyph.getHeight()) / 256.0f, glyph.getXMin(), glyph.getXMax(), glyph.getYMin(), glyph.getYMax());
        }
        return null;
    }

    public Identifier getId() {
        return this.id;
    }

    static class Slot {
        private final int x;
        private final int y;
        private final int width;
        private final int height;
        private Slot subSlot1;
        private Slot subSlot2;
        private boolean occupied;

        private Slot(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        @Nullable
        Slot findSlotFor(RenderableGlyph glyph) {
            if (this.subSlot1 != null && this.subSlot2 != null) {
                Slot slot = this.subSlot1.findSlotFor(glyph);
                if (slot == null) {
                    slot = this.subSlot2.findSlotFor(glyph);
                }
                return slot;
            }
            if (this.occupied) {
                return null;
            }
            int n = glyph.getWidth();
            _snowman = glyph.getHeight();
            if (n > this.width || _snowman > this.height) {
                return null;
            }
            if (n == this.width && _snowman == this.height) {
                this.occupied = true;
                return this;
            }
            _snowman = this.width - n;
            _snowman = this.height - _snowman;
            if (_snowman > _snowman) {
                this.subSlot1 = new Slot(this.x, this.y, n, this.height);
                this.subSlot2 = new Slot(this.x + n + 1, this.y, this.width - n - 1, this.height);
            } else {
                this.subSlot1 = new Slot(this.x, this.y, this.width, _snowman);
                this.subSlot2 = new Slot(this.x, this.y + _snowman + 1, this.width, this.height - _snowman - 1);
            }
            return this.subSlot1.findSlotFor(glyph);
        }
    }
}

