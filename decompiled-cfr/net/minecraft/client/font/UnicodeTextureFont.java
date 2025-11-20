/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.font;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.Font;
import net.minecraft.client.font.FontLoader;
import net.minecraft.client.font.RenderableGlyph;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UnicodeTextureFont
implements Font {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ResourceManager resourceManager;
    private final byte[] sizes;
    private final String template;
    private final Map<Identifier, NativeImage> images = Maps.newHashMap();

    public UnicodeTextureFont(ResourceManager resourceManager, byte[] sizes, String template) {
        this.resourceManager = resourceManager;
        this.sizes = sizes;
        this.template = template;
        for (int i = 0; i < 256; ++i) {
            int n;
            n = i * 256;
            Identifier identifier = this.getImageId(n);
            try (Resource resource = this.resourceManager.getResource(identifier);
                 NativeImage nativeImage = NativeImage.read(NativeImage.Format.ABGR, resource.getInputStream());){
                if (nativeImage.getWidth() == 256 && nativeImage.getHeight() == 256) {
                    for (int j = 0; j < 256; ++j) {
                        byte by = sizes[n + j];
                        if (by == 0 || UnicodeTextureFont.getStart(by) <= UnicodeTextureFont.getEnd(by)) continue;
                        sizes[n + j] = 0;
                    }
                    continue;
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
            Arrays.fill(sizes, n, n + 256, (byte)0);
        }
    }

    @Override
    public void close() {
        this.images.values().forEach(NativeImage::close);
    }

    private Identifier getImageId(int codePoint) {
        Identifier identifier = new Identifier(String.format(this.template, String.format("%02x", codePoint / 256)));
        return new Identifier(identifier.getNamespace(), "textures/" + identifier.getPath());
    }

    @Override
    @Nullable
    public RenderableGlyph getGlyph(int codePoint) {
        if (codePoint < 0 || codePoint > 65535) {
            return null;
        }
        byte by = this.sizes[codePoint];
        if (by != 0 && (_snowman = this.images.computeIfAbsent(this.getImageId(codePoint), this::getGlyphImage)) != null) {
            int n = UnicodeTextureFont.getStart(by);
            return new UnicodeTextureGlyph(codePoint % 16 * 16 + n, (codePoint & 0xFF) / 16 * 16, UnicodeTextureFont.getEnd(by) - n, 16, _snowman);
        }
        return null;
    }

    @Override
    public IntSet getProvidedGlyphs() {
        IntOpenHashSet intOpenHashSet = new IntOpenHashSet();
        for (int i = 0; i < 65535; ++i) {
            if (this.sizes[i] == 0) continue;
            intOpenHashSet.add(i);
        }
        return intOpenHashSet;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Nullable
    private NativeImage getGlyphImage(Identifier glyphId) {
        try (Resource resource = this.resourceManager.getResource(glyphId);){
            NativeImage nativeImage = NativeImage.read(NativeImage.Format.ABGR, resource.getInputStream());
            return nativeImage;
        }
        catch (IOException iOException) {
            LOGGER.error("Couldn't load texture {}", (Object)glyphId, (Object)iOException);
            return null;
        }
    }

    private static int getStart(byte size) {
        return size >> 4 & 0xF;
    }

    private static int getEnd(byte size) {
        return (size & 0xF) + 1;
    }

    static class UnicodeTextureGlyph
    implements RenderableGlyph {
        private final int width;
        private final int height;
        private final int unpackSkipPixels;
        private final int unpackSkipRows;
        private final NativeImage image;

        private UnicodeTextureGlyph(int x, int y, int width, int height, NativeImage image) {
            this.width = width;
            this.height = height;
            this.unpackSkipPixels = x;
            this.unpackSkipRows = y;
            this.image = image;
        }

        @Override
        public float getOversample() {
            return 2.0f;
        }

        @Override
        public int getWidth() {
            return this.width;
        }

        @Override
        public int getHeight() {
            return this.height;
        }

        @Override
        public float getAdvance() {
            return this.width / 2 + 1;
        }

        @Override
        public void upload(int x, int y) {
            this.image.upload(0, x, y, this.unpackSkipPixels, this.unpackSkipRows, this.width, this.height, false, false);
        }

        @Override
        public boolean hasColor() {
            return this.image.getFormat().getChannelCount() > 1;
        }

        @Override
        public float getShadowOffset() {
            return 0.5f;
        }

        @Override
        public float getBoldOffset() {
            return 0.5f;
        }
    }

    public static class Loader
    implements FontLoader {
        private final Identifier sizes;
        private final String template;

        public Loader(Identifier sizes, String template) {
            this.sizes = sizes;
            this.template = template;
        }

        public static FontLoader fromJson(JsonObject json) {
            return new Loader(new Identifier(JsonHelper.getString(json, "sizes")), JsonHelper.getString(json, "template"));
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        @Nullable
        public Font load(ResourceManager manager) {
            try (Resource resource = MinecraftClient.getInstance().getResourceManager().getResource(this.sizes);){
                byte[] _snowman2 = new byte[65536];
                resource.getInputStream().read(_snowman2);
                UnicodeTextureFont unicodeTextureFont = new UnicodeTextureFont(manager, _snowman2, this.template);
                return unicodeTextureFont;
            }
            catch (IOException iOException) {
                LOGGER.error("Cannot load {}, unicode glyphs will not render correctly", (Object)this.sizes);
                return null;
            }
        }
    }
}

