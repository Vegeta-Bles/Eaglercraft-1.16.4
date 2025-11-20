/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.client.texture;

import com.google.common.collect.Lists;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.metadata.AnimationFrameResourceMetadata;
import net.minecraft.client.resource.metadata.AnimationResourceMetadata;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;
import net.minecraft.util.Lazy;

public final class MissingSprite
extends Sprite {
    private static final Identifier MISSINGNO = new Identifier("missingno");
    @Nullable
    private static NativeImageBackedTexture texture;
    private static final Lazy<NativeImage> IMAGE;
    private static final Sprite.Info INFO;

    private MissingSprite(SpriteAtlasTexture spriteAtlasTexture, int maxLevel, int atlasWidth, int atlasHeight, int x, int y) {
        super(spriteAtlasTexture, INFO, maxLevel, atlasWidth, atlasHeight, x, y, IMAGE.get());
    }

    public static MissingSprite getMissingSprite(SpriteAtlasTexture spriteAtlasTexture, int maxLevel, int atlasWidth, int atlasHeight, int x, int y) {
        return new MissingSprite(spriteAtlasTexture, maxLevel, atlasWidth, atlasHeight, x, y);
    }

    public static Identifier getMissingSpriteId() {
        return MISSINGNO;
    }

    public static Sprite.Info getMissingInfo() {
        return INFO;
    }

    @Override
    public void close() {
        for (int i = 1; i < this.images.length; ++i) {
            this.images[i].close();
        }
    }

    public static NativeImageBackedTexture getMissingSpriteTexture() {
        if (texture == null) {
            texture = new NativeImageBackedTexture(IMAGE.get());
            MinecraftClient.getInstance().getTextureManager().registerTexture(MISSINGNO, texture);
        }
        return texture;
    }

    static {
        IMAGE = new Lazy<NativeImage>(() -> {
            NativeImage nativeImage = new NativeImage(16, 16, false);
            int _snowman2 = -16777216;
            int _snowman3 = -524040;
            for (int i = 0; i < 16; ++i) {
                for (_snowman = 0; _snowman < 16; ++_snowman) {
                    if (i < 8 ^ _snowman < 8) {
                        nativeImage.setPixelColor(_snowman, i, -524040);
                        continue;
                    }
                    nativeImage.setPixelColor(_snowman, i, -16777216);
                }
            }
            nativeImage.untrack();
            return nativeImage;
        });
        INFO = new Sprite.Info(MISSINGNO, 16, 16, new AnimationResourceMetadata(Lists.newArrayList((Object[])new AnimationFrameResourceMetadata[]{new AnimationFrameResourceMetadata(0, -1)}), 16, 16, 1, false));
    }
}

