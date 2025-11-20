/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntCollection
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 */
package net.minecraft.client.font;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import net.minecraft.client.font.BlankGlyph;
import net.minecraft.client.font.EmptyGlyphRenderer;
import net.minecraft.client.font.Font;
import net.minecraft.client.font.Glyph;
import net.minecraft.client.font.GlyphAtlasTexture;
import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.client.font.RenderableGlyph;
import net.minecraft.client.font.WhiteRectangleGlyph;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class FontStorage
implements AutoCloseable {
    private static final EmptyGlyphRenderer EMPTY_GLYPH_RENDERER = new EmptyGlyphRenderer();
    private static final Glyph SPACE = () -> 4.0f;
    private static final Random RANDOM = new Random();
    private final TextureManager textureManager;
    private final Identifier id;
    private GlyphRenderer blankGlyphRenderer;
    private GlyphRenderer whiteRectangleGlyphRenderer;
    private final List<Font> fonts = Lists.newArrayList();
    private final Int2ObjectMap<GlyphRenderer> glyphRendererCache = new Int2ObjectOpenHashMap();
    private final Int2ObjectMap<Glyph> glyphCache = new Int2ObjectOpenHashMap();
    private final Int2ObjectMap<IntList> charactersByWidth = new Int2ObjectOpenHashMap();
    private final List<GlyphAtlasTexture> glyphAtlases = Lists.newArrayList();

    public FontStorage(TextureManager textureManager, Identifier id) {
        this.textureManager = textureManager;
        this.id = id;
    }

    public void setFonts(List<Font> fonts) {
        this.closeFonts();
        this.closeGlyphAtlases();
        this.glyphRendererCache.clear();
        this.glyphCache.clear();
        this.charactersByWidth.clear();
        this.blankGlyphRenderer = this.getGlyphRenderer(BlankGlyph.INSTANCE);
        this.whiteRectangleGlyphRenderer = this.getGlyphRenderer(WhiteRectangleGlyph.INSTANCE);
        IntOpenHashSet intOpenHashSet = new IntOpenHashSet();
        for (Font font : fonts) {
            intOpenHashSet.addAll((IntCollection)font.getProvidedGlyphs());
        }
        HashSet hashSet = Sets.newHashSet();
        intOpenHashSet.forEach(n2 -> {
            for (Font font : fonts) {
                Glyph glyph = n2 == 32 ? SPACE : font.getGlyph(n2);
                if (glyph == null) continue;
                hashSet.add(font);
                if (glyph == BlankGlyph.INSTANCE) break;
                ((IntList)this.charactersByWidth.computeIfAbsent(MathHelper.ceil(glyph.getAdvance(false)), n -> new IntArrayList())).add(n2);
                break;
            }
        });
        fonts.stream().filter(hashSet::contains).forEach(this.fonts::add);
    }

    @Override
    public void close() {
        this.closeFonts();
        this.closeGlyphAtlases();
    }

    private void closeFonts() {
        for (Font font : this.fonts) {
            font.close();
        }
        this.fonts.clear();
    }

    private void closeGlyphAtlases() {
        for (GlyphAtlasTexture glyphAtlasTexture : this.glyphAtlases) {
            glyphAtlasTexture.close();
        }
        this.glyphAtlases.clear();
    }

    public Glyph getGlyph(int n2) {
        return (Glyph)this.glyphCache.computeIfAbsent(n2, n -> n == 32 ? SPACE : this.getRenderableGlyph(n));
    }

    private RenderableGlyph getRenderableGlyph(int n) {
        for (Font font : this.fonts) {
            RenderableGlyph renderableGlyph = font.getGlyph(n);
            if (renderableGlyph == null) continue;
            return renderableGlyph;
        }
        return BlankGlyph.INSTANCE;
    }

    public GlyphRenderer getGlyphRenderer(int n2) {
        return (GlyphRenderer)this.glyphRendererCache.computeIfAbsent(n2, n -> n == 32 ? EMPTY_GLYPH_RENDERER : this.getGlyphRenderer(this.getRenderableGlyph(n)));
    }

    private GlyphRenderer getGlyphRenderer(RenderableGlyph c) {
        for (GlyphAtlasTexture glyphAtlasTexture : this.glyphAtlases) {
            GlyphRenderer glyphRenderer = glyphAtlasTexture.getGlyphRenderer(c);
            if (glyphRenderer == null) continue;
            return glyphRenderer;
        }
        GlyphAtlasTexture glyphAtlasTexture = new GlyphAtlasTexture(new Identifier(this.id.getNamespace(), this.id.getPath() + "/" + this.glyphAtlases.size()), c.hasColor());
        this.glyphAtlases.add(glyphAtlasTexture);
        this.textureManager.registerTexture(glyphAtlasTexture.getId(), glyphAtlasTexture);
        GlyphRenderer glyphRenderer = glyphAtlasTexture.getGlyphRenderer(c);
        return glyphRenderer == null ? this.blankGlyphRenderer : glyphRenderer;
    }

    public GlyphRenderer getObfuscatedGlyphRenderer(Glyph glyph) {
        IntList intList = (IntList)this.charactersByWidth.get(MathHelper.ceil(glyph.getAdvance(false)));
        if (intList != null && !intList.isEmpty()) {
            return this.getGlyphRenderer(intList.getInt(RANDOM.nextInt(intList.size())));
        }
        return this.blankGlyphRenderer;
    }

    public GlyphRenderer getRectangleRenderer() {
        return this.whiteRectangleGlyphRenderer;
    }
}

