/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.texture;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.util.Util;

public class MipmapHelper {
    private static final float[] COLOR_FRACTIONS = Util.make(new float[256], fArray -> {
        for (int i = 0; i < ((float[])fArray).length; ++i) {
            fArray[i] = (float)Math.pow((float)i / 255.0f, 2.2);
        }
    });

    public static NativeImage[] getMipmapLevelsImages(NativeImage image, int mipmap) {
        NativeImage[] nativeImageArray = new NativeImage[mipmap + 1];
        nativeImageArray[0] = image;
        if (mipmap > 0) {
            int n;
            boolean bl = false;
            block0: for (n = 0; n < image.getWidth(); ++n) {
                for (_snowman = 0; _snowman < image.getHeight(); ++_snowman) {
                    if (image.getPixelColor(n, _snowman) >> 24 != 0) continue;
                    bl = true;
                    break block0;
                }
            }
            for (n = 1; n <= mipmap; ++n) {
                NativeImage nativeImage;
                NativeImage nativeImage2 = nativeImageArray[n - 1];
                nativeImage = new NativeImage(nativeImage2.getWidth() >> 1, nativeImage2.getHeight() >> 1, false);
                int _snowman2 = nativeImage.getWidth();
                int _snowman3 = nativeImage.getHeight();
                for (int i = 0; i < _snowman2; ++i) {
                    for (_snowman = 0; _snowman < _snowman3; ++_snowman) {
                        nativeImage.setPixelColor(i, _snowman, MipmapHelper.blend(nativeImage2.getPixelColor(i * 2 + 0, _snowman * 2 + 0), nativeImage2.getPixelColor(i * 2 + 1, _snowman * 2 + 0), nativeImage2.getPixelColor(i * 2 + 0, _snowman * 2 + 1), nativeImage2.getPixelColor(i * 2 + 1, _snowman * 2 + 1), bl));
                    }
                }
                nativeImageArray[n] = nativeImage;
            }
        }
        return nativeImageArray;
    }

    private static int blend(int one, int two, int three, int four, boolean checkAlpha) {
        if (checkAlpha) {
            float f = 0.0f;
            _snowman = 0.0f;
            _snowman = 0.0f;
            _snowman = 0.0f;
            if (one >> 24 != 0) {
                f += MipmapHelper.getColorFraction(one >> 24);
                _snowman += MipmapHelper.getColorFraction(one >> 16);
                _snowman += MipmapHelper.getColorFraction(one >> 8);
                _snowman += MipmapHelper.getColorFraction(one >> 0);
            }
            if (two >> 24 != 0) {
                f += MipmapHelper.getColorFraction(two >> 24);
                _snowman += MipmapHelper.getColorFraction(two >> 16);
                _snowman += MipmapHelper.getColorFraction(two >> 8);
                _snowman += MipmapHelper.getColorFraction(two >> 0);
            }
            if (three >> 24 != 0) {
                f += MipmapHelper.getColorFraction(three >> 24);
                _snowman += MipmapHelper.getColorFraction(three >> 16);
                _snowman += MipmapHelper.getColorFraction(three >> 8);
                _snowman += MipmapHelper.getColorFraction(three >> 0);
            }
            if (four >> 24 != 0) {
                f += MipmapHelper.getColorFraction(four >> 24);
                _snowman += MipmapHelper.getColorFraction(four >> 16);
                _snowman += MipmapHelper.getColorFraction(four >> 8);
                _snowman += MipmapHelper.getColorFraction(four >> 0);
            }
            int _snowman2 = (int)(Math.pow(f /= 4.0f, 0.45454545454545453) * 255.0);
            int _snowman3 = (int)(Math.pow(_snowman /= 4.0f, 0.45454545454545453) * 255.0);
            int _snowman4 = (int)(Math.pow(_snowman /= 4.0f, 0.45454545454545453) * 255.0);
            int _snowman5 = (int)(Math.pow(_snowman /= 4.0f, 0.45454545454545453) * 255.0);
            if (_snowman2 < 96) {
                _snowman2 = 0;
            }
            return _snowman2 << 24 | _snowman3 << 16 | _snowman4 << 8 | _snowman5;
        }
        int n = MipmapHelper.getColorComponent(one, two, three, four, 24);
        _snowman = MipmapHelper.getColorComponent(one, two, three, four, 16);
        _snowman = MipmapHelper.getColorComponent(one, two, three, four, 8);
        _snowman = MipmapHelper.getColorComponent(one, two, three, four, 0);
        return n << 24 | _snowman << 16 | _snowman << 8 | _snowman;
    }

    private static int getColorComponent(int one, int two, int three, int four, int bits) {
        float f = MipmapHelper.getColorFraction(one >> bits);
        _snowman = MipmapHelper.getColorFraction(two >> bits);
        _snowman = MipmapHelper.getColorFraction(three >> bits);
        _snowman = MipmapHelper.getColorFraction(four >> bits);
        _snowman = (float)((double)((float)Math.pow((double)(f + _snowman + _snowman + _snowman) * 0.25, 0.45454545454545453)));
        return (int)((double)_snowman * 255.0);
    }

    private static float getColorFraction(int value) {
        return COLOR_FRACTIONS[value & 0xFF];
    }
}

