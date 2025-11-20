package net.minecraft.client.font;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class FontStorage implements AutoCloseable {
   private static final EmptyGlyphRenderer EMPTY_GLYPH_RENDERER = new EmptyGlyphRenderer();
   private static final Glyph SPACE = () -> 4.0F;
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
      IntSet _snowman = new IntOpenHashSet();

      for (Font _snowmanx : fonts) {
         _snowman.addAll(_snowmanx.getProvidedGlyphs());
      }

      Set<Font> _snowmanx = Sets.newHashSet();
      _snowman.forEach(_snowmanxx -> {
         for (Font _snowmanxx : fonts) {
            Glyph _snowmanx = (Glyph)(_snowmanxx == 32 ? SPACE : _snowmanxx.getGlyph(_snowmanxx));
            if (_snowmanx != null) {
               _snowman.add(_snowmanxx);
               if (_snowmanx != BlankGlyph.INSTANCE) {
                  ((IntList)this.charactersByWidth.computeIfAbsent(MathHelper.ceil(_snowmanx.getAdvance(false)), _snowmanxxxx -> new IntArrayList())).add(_snowmanxx);
               }
               break;
            }
         }
      });
      fonts.stream().filter(_snowmanx::contains).forEach(this.fonts::add);
   }

   @Override
   public void close() {
      this.closeFonts();
      this.closeGlyphAtlases();
   }

   private void closeFonts() {
      for (Font _snowman : this.fonts) {
         _snowman.close();
      }

      this.fonts.clear();
   }

   private void closeGlyphAtlases() {
      for (GlyphAtlasTexture _snowman : this.glyphAtlases) {
         _snowman.close();
      }

      this.glyphAtlases.clear();
   }

   public Glyph getGlyph(int _snowman) {
      return (Glyph)this.glyphCache.computeIfAbsent(_snowman, _snowmanx -> (Glyph)(_snowmanx == 32 ? SPACE : this.getRenderableGlyph(_snowmanx)));
   }

   private RenderableGlyph getRenderableGlyph(int _snowman) {
      for (Font _snowmanx : this.fonts) {
         RenderableGlyph _snowmanxx = _snowmanx.getGlyph(_snowman);
         if (_snowmanxx != null) {
            return _snowmanxx;
         }
      }

      return BlankGlyph.INSTANCE;
   }

   public GlyphRenderer getGlyphRenderer(int _snowman) {
      return (GlyphRenderer)this.glyphRendererCache
         .computeIfAbsent(_snowman, _snowmanx -> (GlyphRenderer)(_snowmanx == 32 ? EMPTY_GLYPH_RENDERER : this.getGlyphRenderer(this.getRenderableGlyph(_snowmanx))));
   }

   private GlyphRenderer getGlyphRenderer(RenderableGlyph c) {
      for (GlyphAtlasTexture _snowman : this.glyphAtlases) {
         GlyphRenderer _snowmanx = _snowman.getGlyphRenderer(c);
         if (_snowmanx != null) {
            return _snowmanx;
         }
      }

      GlyphAtlasTexture _snowmanx = new GlyphAtlasTexture(new Identifier(this.id.getNamespace(), this.id.getPath() + "/" + this.glyphAtlases.size()), c.hasColor());
      this.glyphAtlases.add(_snowmanx);
      this.textureManager.registerTexture(_snowmanx.getId(), _snowmanx);
      GlyphRenderer _snowmanxx = _snowmanx.getGlyphRenderer(c);
      return _snowmanxx == null ? this.blankGlyphRenderer : _snowmanxx;
   }

   public GlyphRenderer getObfuscatedGlyphRenderer(Glyph glyph) {
      IntList _snowman = (IntList)this.charactersByWidth.get(MathHelper.ceil(glyph.getAdvance(false)));
      return _snowman != null && !_snowman.isEmpty() ? this.getGlyphRenderer(_snowman.getInt(RANDOM.nextInt(_snowman.size()))) : this.blankGlyphRenderer;
   }

   public GlyphRenderer getRectangleRenderer() {
      return this.whiteRectangleGlyphRenderer;
   }
}
