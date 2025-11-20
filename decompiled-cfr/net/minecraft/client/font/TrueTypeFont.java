/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntArraySet
 *  it.unimi.dsi.fastutil.ints.IntCollection
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  javax.annotation.Nullable
 *  org.lwjgl.stb.STBTTFontinfo
 *  org.lwjgl.stb.STBTruetype
 *  org.lwjgl.system.MemoryStack
 *  org.lwjgl.system.MemoryUtil
 */
package net.minecraft.client.font;

import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.client.font.Font;
import net.minecraft.client.font.RenderableGlyph;
import net.minecraft.client.texture.NativeImage;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class TrueTypeFont
implements Font {
    private final ByteBuffer field_21839;
    private final STBTTFontinfo info;
    private final float oversample;
    private final IntSet excludedCharacters = new IntArraySet();
    private final float shiftX;
    private final float shiftY;
    private final float scaleFactor;
    private final float ascent;

    public TrueTypeFont(ByteBuffer byteBuffer, STBTTFontinfo sTBTTFontinfo, float f, float f2, float f3, float f4, String string) {
        this.field_21839 = byteBuffer;
        this.info = sTBTTFontinfo;
        this.oversample = f2;
        string.codePoints().forEach(arg_0 -> ((IntSet)this.excludedCharacters).add(arg_0));
        this.shiftX = f3 * f2;
        this.shiftY = f4 * f2;
        this.scaleFactor = STBTruetype.stbtt_ScaleForPixelHeight((STBTTFontinfo)sTBTTFontinfo, (float)(f * f2));
        try (MemoryStack memoryStack = MemoryStack.stackPush();){
            IntBuffer intBuffer = memoryStack.mallocInt(1);
            _snowman = memoryStack.mallocInt(1);
            _snowman = memoryStack.mallocInt(1);
            STBTruetype.stbtt_GetFontVMetrics((STBTTFontinfo)sTBTTFontinfo, (IntBuffer)intBuffer, (IntBuffer)_snowman, (IntBuffer)_snowman);
            this.ascent = (float)intBuffer.get(0) * this.scaleFactor;
        }
    }

    @Override
    @Nullable
    public TtfGlyph getGlyph(int n) {
        if (this.excludedCharacters.contains(n)) {
            return null;
        }
        try (MemoryStack memoryStack = MemoryStack.stackPush();){
            IntBuffer intBuffer = memoryStack.mallocInt(1);
            _snowman = memoryStack.mallocInt(1);
            _snowman = memoryStack.mallocInt(1);
            _snowman = memoryStack.mallocInt(1);
            int _snowman2 = STBTruetype.stbtt_FindGlyphIndex((STBTTFontinfo)this.info, (int)n);
            if (_snowman2 == 0) {
                TtfGlyph ttfGlyph = null;
                return ttfGlyph;
            }
            STBTruetype.stbtt_GetGlyphBitmapBoxSubpixel((STBTTFontinfo)this.info, (int)_snowman2, (float)this.scaleFactor, (float)this.scaleFactor, (float)this.shiftX, (float)this.shiftY, (IntBuffer)intBuffer, (IntBuffer)_snowman, (IntBuffer)_snowman, (IntBuffer)_snowman);
            int _snowman3 = _snowman.get(0) - intBuffer.get(0);
            int _snowman4 = _snowman.get(0) - _snowman.get(0);
            if (_snowman3 == 0 || _snowman4 == 0) {
                TtfGlyph ttfGlyph = null;
                return ttfGlyph;
            }
            _snowman = memoryStack.mallocInt(1);
            _snowman = memoryStack.mallocInt(1);
            STBTruetype.stbtt_GetGlyphHMetrics((STBTTFontinfo)this.info, (int)_snowman2, (IntBuffer)_snowman, (IntBuffer)_snowman);
            TtfGlyph ttfGlyph = new TtfGlyph(intBuffer.get(0), _snowman.get(0), -_snowman.get(0), -_snowman.get(0), (float)_snowman.get(0) * this.scaleFactor, (float)_snowman.get(0) * this.scaleFactor, _snowman2);
            return ttfGlyph;
        }
    }

    @Override
    public void close() {
        this.info.free();
        MemoryUtil.memFree((Buffer)this.field_21839);
    }

    @Override
    public IntSet getProvidedGlyphs() {
        return (IntSet)IntStream.range(0, 65535).filter(n -> !this.excludedCharacters.contains(n)).collect(IntOpenHashSet::new, IntCollection::add, IntCollection::addAll);
    }

    @Override
    @Nullable
    public /* synthetic */ RenderableGlyph getGlyph(int codePoint) {
        return this.getGlyph(codePoint);
    }

    class TtfGlyph
    implements RenderableGlyph {
        private final int width;
        private final int height;
        private final float bearingX;
        private final float ascent;
        private final float advance;
        private final int glyphIndex;

        private TtfGlyph(int xMin, int xMax, int yMax, int yMin, float advance, float bearing, int index) {
            this.width = xMax - xMin;
            this.height = yMax - yMin;
            this.advance = advance / TrueTypeFont.this.oversample;
            this.bearingX = (bearing + (float)xMin + TrueTypeFont.this.shiftX) / TrueTypeFont.this.oversample;
            this.ascent = (TrueTypeFont.this.ascent - (float)yMax + TrueTypeFont.this.shiftY) / TrueTypeFont.this.oversample;
            this.glyphIndex = index;
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
        public float getOversample() {
            return TrueTypeFont.this.oversample;
        }

        @Override
        public float getAdvance() {
            return this.advance;
        }

        @Override
        public float getBearingX() {
            return this.bearingX;
        }

        @Override
        public float getAscent() {
            return this.ascent;
        }

        @Override
        public void upload(int x, int y) {
            NativeImage nativeImage = new NativeImage(NativeImage.Format.LUMINANCE, this.width, this.height, false);
            nativeImage.makeGlyphBitmapSubpixel(TrueTypeFont.this.info, this.glyphIndex, this.width, this.height, TrueTypeFont.this.scaleFactor, TrueTypeFont.this.scaleFactor, TrueTypeFont.this.shiftX, TrueTypeFont.this.shiftY, 0, 0);
            nativeImage.upload(0, x, y, 0, 0, this.width, this.height, false, true);
        }

        @Override
        public boolean hasColor() {
            return false;
        }
    }
}

