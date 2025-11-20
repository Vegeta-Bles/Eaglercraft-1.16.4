/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;

public class OverlayTexture
implements AutoCloseable {
    public static final int DEFAULT_UV = OverlayTexture.packUv(0, 10);
    private final NativeImageBackedTexture texture = new NativeImageBackedTexture(16, 16, false);

    public OverlayTexture() {
        NativeImage nativeImage = this.texture.getImage();
        for (int i = 0; i < 16; ++i) {
            for (_snowman = 0; _snowman < 16; ++_snowman) {
                if (i < 8) {
                    nativeImage.setPixelColor(_snowman, i, -1308622593);
                    continue;
                }
                _snowman = (int)((1.0f - (float)_snowman / 15.0f * 0.75f) * 255.0f);
                nativeImage.setPixelColor(_snowman, i, _snowman << 24 | 0xFFFFFF);
            }
        }
        RenderSystem.activeTexture(33985);
        this.texture.bindTexture();
        RenderSystem.matrixMode(5890);
        RenderSystem.loadIdentity();
        float f = 0.06666667f;
        RenderSystem.scalef(0.06666667f, 0.06666667f, 0.06666667f);
        RenderSystem.matrixMode(5888);
        this.texture.bindTexture();
        nativeImage.upload(0, 0, 0, 0, 0, nativeImage.getWidth(), nativeImage.getHeight(), false, true, false, false);
        RenderSystem.activeTexture(33984);
    }

    @Override
    public void close() {
        this.texture.close();
    }

    public void setupOverlayColor() {
        RenderSystem.setupOverlayColor(this.texture::getGlId, 16);
    }

    public static int getU(float whiteOverlayProgress) {
        return (int)(whiteOverlayProgress * 15.0f);
    }

    public static int getV(boolean hurt) {
        return hurt ? 3 : 10;
    }

    public static int packUv(int u, int v) {
        return u | v << 16;
    }

    public static int getUv(float f, boolean hurt) {
        return OverlayTexture.packUv(OverlayTexture.getU(f), OverlayTexture.getV(hurt));
    }

    public void teardownOverlayColor() {
        RenderSystem.teardownOverlayColor();
    }
}

