/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  it.unimi.dsi.fastutil.ints.IntSets
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.font;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
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

public class BitmapFont
implements Font {
    private static final Logger LOGGER = LogManager.getLogger();
    private final NativeImage image;
    private final Int2ObjectMap<BitmapFontGlyph> glyphs;

    private BitmapFont(NativeImage image, Int2ObjectMap<BitmapFontGlyph> glyphs) {
        this.image = image;
        this.glyphs = glyphs;
    }

    @Override
    public void close() {
        this.image.close();
    }

    @Override
    @Nullable
    public RenderableGlyph getGlyph(int codePoint) {
        return (RenderableGlyph)this.glyphs.get(codePoint);
    }

    @Override
    public IntSet getProvidedGlyphs() {
        return IntSets.unmodifiable((IntSet)this.glyphs.keySet());
    }

    static final class BitmapFontGlyph
    implements RenderableGlyph {
        private final float scaleFactor;
        private final NativeImage image;
        private final int x;
        private final int y;
        private final int width;
        private final int height;
        private final int advance;
        private final int ascent;

        private BitmapFontGlyph(float scaleFactor, NativeImage image, int x, int y, int width, int height, int advance, int ascent) {
            this.scaleFactor = scaleFactor;
            this.image = image;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.advance = advance;
            this.ascent = ascent;
        }

        @Override
        public float getOversample() {
            return 1.0f / this.scaleFactor;
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
            return this.advance;
        }

        @Override
        public float getAscent() {
            return RenderableGlyph.super.getAscent() + 7.0f - (float)this.ascent;
        }

        @Override
        public void upload(int x, int y) {
            this.image.upload(0, x, y, this.x, this.y, this.width, this.height, false, false);
        }

        @Override
        public boolean hasColor() {
            return this.image.getFormat().getChannelCount() > 1;
        }
    }

    public static class Loader
    implements FontLoader {
        private final Identifier filename;
        private final List<int[]> chars;
        private final int height;
        private final int ascent;

        public Loader(Identifier id, int height, int ascent, List<int[]> chars) {
            this.filename = new Identifier(id.getNamespace(), "textures/" + id.getPath());
            this.chars = chars;
            this.height = height;
            this.ascent = ascent;
        }

        public static Loader fromJson(JsonObject json) {
            int n = JsonHelper.getInt(json, "height", 8);
            _snowman = JsonHelper.getInt(json, "ascent");
            if (_snowman > n) {
                throw new JsonParseException("Ascent " + _snowman + " higher than height " + n);
            }
            ArrayList _snowman2 = Lists.newArrayList();
            JsonArray _snowman3 = JsonHelper.getArray(json, "chars");
            for (_snowman = 0; _snowman < _snowman3.size(); ++_snowman) {
                String string = JsonHelper.asString(_snowman3.get(_snowman), "chars[" + _snowman + "]");
                int[] _snowman4 = string.codePoints().toArray();
                if (_snowman > 0 && _snowman4.length != (_snowman = ((int[])_snowman2.get(0)).length)) {
                    throw new JsonParseException("Elements of chars have to be the same length (found: " + _snowman4.length + ", expected: " + _snowman + "), pad with space or \\u0000");
                }
                _snowman2.add(_snowman4);
            }
            if (_snowman2.isEmpty() || ((int[])_snowman2.get(0)).length == 0) {
                throw new JsonParseException("Expected to find data in chars, found none.");
            }
            return new Loader(new Identifier(JsonHelper.getString(json, "file")), n, _snowman, _snowman2);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        @Nullable
        public Font load(ResourceManager manager) {
            try (Resource resource = manager.getResource(this.filename);){
                NativeImage nativeImage = NativeImage.read(NativeImage.Format.ABGR, resource.getInputStream());
                int _snowman2 = nativeImage.getWidth();
                int _snowman3 = nativeImage.getHeight();
                int _snowman4 = _snowman2 / this.chars.get(0).length;
                int _snowman5 = _snowman3 / this.chars.size();
                float _snowman6 = (float)this.height / (float)_snowman5;
                Int2ObjectOpenHashMap _snowman7 = new Int2ObjectOpenHashMap();
                int i = 0;
                while (true) {
                    int n;
                    int[] nArray;
                    int n2;
                    if (i < this.chars.size()) {
                        n2 = 0;
                        nArray = this.chars.get(i);
                        n = nArray.length;
                    } else {
                        BitmapFont bitmapFont = new BitmapFont(nativeImage, (Int2ObjectMap)_snowman7);
                        return bitmapFont;
                    }
                    for (int j = 0; j < n; ++j) {
                        BitmapFontGlyph bitmapFontGlyph;
                        int n22 = nArray[j];
                        int n3 = n2++;
                        if (n22 == 0 || n22 == 32 || (bitmapFontGlyph = (BitmapFontGlyph)_snowman7.put(n22, (Object)new BitmapFontGlyph(_snowman6, nativeImage, n3 * _snowman4, i * _snowman5, _snowman4, _snowman5, (int)(0.5 + (double)((float)(_snowman = this.findCharacterStartX(nativeImage, _snowman4, _snowman5, n3, i)) * _snowman6)) + 1, this.ascent))) == null) continue;
                        LOGGER.warn("Codepoint '{}' declared multiple times in {}", (Object)Integer.toHexString(n22), (Object)this.filename);
                    }
                    ++i;
                }
            }
            catch (IOException iOException) {
                throw new RuntimeException(iOException.getMessage());
            }
        }

        private int findCharacterStartX(NativeImage image, int characterWidth, int characterHeight, int charPosX, int charPosY) {
            int n;
            for (n = characterWidth - 1; n >= 0; --n) {
                _snowman = charPosX * characterWidth + n;
                for (_snowman = 0; _snowman < characterHeight; ++_snowman) {
                    _snowman = charPosY * characterHeight + _snowman;
                    if (image.getPixelOpacity(_snowman, _snowman) == 0) continue;
                    return n + 1;
                }
            }
            return n + 1;
        }
    }
}

