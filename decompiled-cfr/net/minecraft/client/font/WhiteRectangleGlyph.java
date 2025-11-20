/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.font;

import net.minecraft.client.font.RenderableGlyph;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.util.Util;

public enum WhiteRectangleGlyph implements RenderableGlyph
{
    INSTANCE;

    private static final NativeImage IMAGE;

    @Override
    public int getWidth() {
        return 5;
    }

    @Override
    public int getHeight() {
        return 8;
    }

    @Override
    public float getAdvance() {
        return 6.0f;
    }

    @Override
    public float getOversample() {
        return 1.0f;
    }

    @Override
    public void upload(int x, int y) {
        IMAGE.upload(0, x, y, false);
    }

    @Override
    public boolean hasColor() {
        return true;
    }

    static {
        IMAGE = Util.make(new NativeImage(NativeImage.Format.ABGR, 5, 8, false), nativeImage2 -> {
            NativeImage nativeImage2;
            for (int i = 0; i < 8; ++i) {
                for (_snowman = 0; _snowman < 5; ++_snowman) {
                    boolean bl = _snowman == 0 || _snowman + 1 == 5 || i == 0 || i + 1 == 8;
                    nativeImage2.setPixelColor(_snowman, i, -1);
                }
            }
            nativeImage2.untrack();
        });
    }
}

