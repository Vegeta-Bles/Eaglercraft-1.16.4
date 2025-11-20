/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.client.texture;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Arrays;
import javax.annotation.Nullable;
import net.minecraft.client.render.SpriteTexturedVertexConsumer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.resource.metadata.AnimationFrameResourceMetadata;
import net.minecraft.client.resource.metadata.AnimationResourceMetadata;
import net.minecraft.client.texture.MipmapHelper;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;

public class Sprite
implements AutoCloseable {
    private final SpriteAtlasTexture atlas;
    private final Info info;
    private final AnimationResourceMetadata animationMetadata;
    protected final NativeImage[] images;
    private final int[] frameXs;
    private final int[] frameYs;
    @Nullable
    private final Interpolation interpolation;
    private final int x;
    private final int y;
    private final float uMin;
    private final float uMax;
    private final float vMin;
    private final float vMax;
    private int frameIndex;
    private int frameTicks;

    protected Sprite(SpriteAtlasTexture spriteAtlasTexture, Info info, int maxLevel, int atlasWidth, int atlasHeight, int x, int y, NativeImage nativeImage) {
        CrashReport crashReport;
        this.atlas = spriteAtlasTexture;
        AnimationResourceMetadata _snowman7 = info.animationData;
        int _snowman2 = info.width;
        int _snowman3 = info.height;
        this.x = x;
        this.y = y;
        this.uMin = (float)x / (float)atlasWidth;
        this.uMax = (float)(x + _snowman2) / (float)atlasWidth;
        this.vMin = (float)y / (float)atlasHeight;
        this.vMax = (float)(y + _snowman3) / (float)atlasHeight;
        int _snowman4 = nativeImage.getWidth() / _snowman7.getWidth(_snowman2);
        int _snowman5 = nativeImage.getHeight() / _snowman7.getHeight(_snowman3);
        if (_snowman7.getFrameCount() > 0) {
            int n = (Integer)_snowman7.getFrameIndexSet().stream().max(Integer::compareTo).get() + 1;
            this.frameXs = new int[n];
            this.frameYs = new int[n];
            Arrays.fill(this.frameXs, -1);
            Arrays.fill(this.frameYs, -1);
            for (int n2 : _snowman7.getFrameIndexSet()) {
                if (n2 >= _snowman4 * _snowman5) {
                    throw new RuntimeException("invalid frameindex " + n2);
                }
                _snowman = n2 / _snowman4;
                this.frameXs[n2] = _snowman = n2 % _snowman4;
                this.frameYs[n2] = _snowman;
            }
        } else {
            ArrayList arrayList = Lists.newArrayList();
            int _snowman6 = _snowman4 * _snowman5;
            this.frameXs = new int[_snowman6];
            this.frameYs = new int[_snowman6];
            for (int n2 = 0; n2 < _snowman5; ++n2) {
                _snowman = 0;
                while (_snowman < _snowman4) {
                    _snowman = n2 * _snowman4 + _snowman;
                    this.frameXs[_snowman] = _snowman++;
                    this.frameYs[_snowman] = n2;
                    arrayList.add(new AnimationFrameResourceMetadata(_snowman, -1));
                }
            }
            _snowman7 = new AnimationResourceMetadata(arrayList, _snowman2, _snowman3, _snowman7.getDefaultFrameTime(), _snowman7.shouldInterpolate());
        }
        this.info = new Info(info.id, _snowman2, _snowman3, _snowman7);
        this.animationMetadata = _snowman7;
        try {
            try {
                this.images = MipmapHelper.getMipmapLevelsImages(nativeImage, maxLevel);
            }
            catch (Throwable _snowman8) {
                crashReport = CrashReport.create(_snowman8, "Generating mipmaps for frame");
                CrashReportSection _snowman9 = crashReport.addElement("Frame being iterated");
                _snowman9.add("First frame", () -> {
                    StringBuilder stringBuilder = new StringBuilder();
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(nativeImage.getWidth()).append("x").append(nativeImage.getHeight());
                    return stringBuilder.toString();
                });
                throw new CrashException(crashReport);
            }
        }
        catch (Throwable throwable) {
            crashReport = CrashReport.create(throwable, "Applying mipmap");
            CrashReportSection _snowman10 = crashReport.addElement("Sprite being mipmapped");
            _snowman10.add("Sprite name", () -> this.getId().toString());
            _snowman10.add("Sprite size", () -> this.getWidth() + " x " + this.getHeight());
            _snowman10.add("Sprite frames", () -> this.getFrameCount() + " frames");
            _snowman10.add("Mipmap levels", maxLevel);
            throw new CrashException(crashReport);
        }
        this.interpolation = _snowman7.shouldInterpolate() ? new Interpolation(info, maxLevel) : null;
    }

    private void upload(int frame) {
        int n = this.frameXs[frame] * this.info.width;
        _snowman = this.frameYs[frame] * this.info.height;
        this.upload(n, _snowman, this.images);
    }

    private void upload(int frameX, int frameY, NativeImage[] output) {
        for (int i = 0; i < this.images.length; ++i) {
            output[i].upload(i, this.x >> i, this.y >> i, frameX >> i, frameY >> i, this.info.width >> i, this.info.height >> i, this.images.length > 1, false);
        }
    }

    public int getWidth() {
        return this.info.width;
    }

    public int getHeight() {
        return this.info.height;
    }

    public float getMinU() {
        return this.uMin;
    }

    public float getMaxU() {
        return this.uMax;
    }

    public float getFrameU(double frame) {
        float f = this.uMax - this.uMin;
        return this.uMin + f * (float)frame / 16.0f;
    }

    public float getMinV() {
        return this.vMin;
    }

    public float getMaxV() {
        return this.vMax;
    }

    public float getFrameV(double frame) {
        float f = this.vMax - this.vMin;
        return this.vMin + f * (float)frame / 16.0f;
    }

    public Identifier getId() {
        return this.info.id;
    }

    public SpriteAtlasTexture getAtlas() {
        return this.atlas;
    }

    public int getFrameCount() {
        return this.frameXs.length;
    }

    @Override
    public void close() {
        for (NativeImage nativeImage : this.images) {
            if (nativeImage == null) continue;
            nativeImage.close();
        }
        if (this.interpolation != null) {
            this.interpolation.close();
        }
    }

    public String toString() {
        int n = this.frameXs.length;
        return "TextureAtlasSprite{name='" + this.info.id + '\'' + ", frameCount=" + n + ", x=" + this.x + ", y=" + this.y + ", height=" + this.info.height + ", width=" + this.info.width + ", u0=" + this.uMin + ", u1=" + this.uMax + ", v0=" + this.vMin + ", v1=" + this.vMax + '}';
    }

    public boolean isPixelTransparent(int frame, int x, int y) {
        return (this.images[0].getPixelColor(x + this.frameXs[frame] * this.info.width, y + this.frameYs[frame] * this.info.height) >> 24 & 0xFF) == 0;
    }

    public void upload() {
        this.upload(0);
    }

    private float getFrameDeltaFactor() {
        float f = (float)this.info.width / (this.uMax - this.uMin);
        _snowman = (float)this.info.height / (this.vMax - this.vMin);
        return Math.max(_snowman, f);
    }

    public float getAnimationFrameDelta() {
        return 4.0f / this.getFrameDeltaFactor();
    }

    public void tickAnimation() {
        ++this.frameTicks;
        if (this.frameTicks >= this.animationMetadata.getFrameTime(this.frameIndex)) {
            int n = this.animationMetadata.getFrameIndex(this.frameIndex);
            _snowman = this.animationMetadata.getFrameCount() == 0 ? this.getFrameCount() : this.animationMetadata.getFrameCount();
            this.frameIndex = (this.frameIndex + 1) % _snowman;
            this.frameTicks = 0;
            _snowman = this.animationMetadata.getFrameIndex(this.frameIndex);
            if (n != _snowman && _snowman >= 0 && _snowman < this.getFrameCount()) {
                this.upload(_snowman);
            }
        } else if (this.interpolation != null) {
            if (!RenderSystem.isOnRenderThread()) {
                RenderSystem.recordRenderCall(() -> this.interpolation.apply());
            } else {
                this.interpolation.apply();
            }
        }
    }

    public boolean isAnimated() {
        return this.animationMetadata.getFrameCount() > 1;
    }

    public VertexConsumer getTextureSpecificVertexConsumer(VertexConsumer vertexConsumer) {
        return new SpriteTexturedVertexConsumer(vertexConsumer, this);
    }

    final class Interpolation
    implements AutoCloseable {
        private final NativeImage[] images;

        private Interpolation(Info info, int mipmap) {
            this.images = new NativeImage[mipmap + 1];
            for (int i = 0; i < this.images.length; ++i) {
                _snowman = info.width >> i;
                _snowman = info.height >> i;
                if (this.images[i] != null) continue;
                this.images[i] = new NativeImage(_snowman, _snowman, false);
            }
        }

        private void apply() {
            double d = 1.0 - (double)Sprite.this.frameTicks / (double)Sprite.this.animationMetadata.getFrameTime(Sprite.this.frameIndex);
            int _snowman2 = Sprite.this.animationMetadata.getFrameIndex(Sprite.this.frameIndex);
            int _snowman3 = Sprite.this.animationMetadata.getFrameCount() == 0 ? Sprite.this.getFrameCount() : Sprite.this.animationMetadata.getFrameCount();
            int _snowman4 = Sprite.this.animationMetadata.getFrameIndex((Sprite.this.frameIndex + 1) % _snowman3);
            if (_snowman2 != _snowman4 && _snowman4 >= 0 && _snowman4 < Sprite.this.getFrameCount()) {
                for (int i = 0; i < this.images.length; ++i) {
                    _snowman = Sprite.this.info.width >> i;
                    _snowman = Sprite.this.info.height >> i;
                    for (_snowman = 0; _snowman < _snowman; ++_snowman) {
                        for (_snowman = 0; _snowman < _snowman; ++_snowman) {
                            _snowman = this.getPixelColor(_snowman2, i, _snowman, _snowman);
                            _snowman = this.getPixelColor(_snowman4, i, _snowman, _snowman);
                            _snowman = this.lerp(d, _snowman >> 16 & 0xFF, _snowman >> 16 & 0xFF);
                            _snowman = this.lerp(d, _snowman >> 8 & 0xFF, _snowman >> 8 & 0xFF);
                            _snowman = this.lerp(d, _snowman & 0xFF, _snowman & 0xFF);
                            this.images[i].setPixelColor(_snowman, _snowman, _snowman & 0xFF000000 | _snowman << 16 | _snowman << 8 | _snowman);
                        }
                    }
                }
                Sprite.this.upload(0, 0, this.images);
            }
        }

        private int getPixelColor(int frameIndex, int layer, int x, int y) {
            return Sprite.this.images[layer].getPixelColor(x + (Sprite.this.frameXs[frameIndex] * Sprite.this.info.width >> layer), y + (Sprite.this.frameYs[frameIndex] * Sprite.this.info.height >> layer));
        }

        private int lerp(double delta, int to, int from) {
            return (int)(delta * (double)to + (1.0 - delta) * (double)from);
        }

        @Override
        public void close() {
            for (NativeImage nativeImage : this.images) {
                if (nativeImage == null) continue;
                nativeImage.close();
            }
        }
    }

    public static final class Info {
        private final Identifier id;
        private final int width;
        private final int height;
        private final AnimationResourceMetadata animationData;

        public Info(Identifier id, int width, int height, AnimationResourceMetadata animationData) {
            this.id = id;
            this.width = width;
            this.height = height;
            this.animationData = animationData;
        }

        public Identifier getId() {
            return this.id;
        }

        public int getWidth() {
            return this.width;
        }

        public int getHeight() {
            return this.height;
        }
    }
}

