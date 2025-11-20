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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
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
      IntSet intSet = new IntOpenHashSet();

      for (Font lv : fonts) {
         intSet.addAll(lv.getProvidedGlyphs());
      }

      Set<Font> set = Sets.newHashSet();
      intSet.forEach((int i) -> {
         for (Font lvx : fonts) {
            Glyph lv2 = (Glyph)(i == 32 ? SPACE : lvx.getGlyph(i));
            if (lv2 != null) {
               set.add(lvx);
               if (lv2 != BlankGlyph.INSTANCE) {
                  ((IntList)this.charactersByWidth.computeIfAbsent(MathHelper.ceil(lv2.getAdvance(false)), ix -> new IntArrayList())).add(i);
               }
               break;
            }
         }
      });
      fonts.stream().filter(set::contains).forEach(this.fonts::add);
   }

   @Override
   public void close() {
      this.closeFonts();
      this.closeGlyphAtlases();
   }

   private void closeFonts() {
      for (Font lv : this.fonts) {
         lv.close();
      }

      this.fonts.clear();
   }

   private void closeGlyphAtlases() {
      for (GlyphAtlasTexture lv : this.glyphAtlases) {
         lv.close();
      }

      this.glyphAtlases.clear();
   }

   public Glyph getGlyph(int i) {
      return (Glyph)this.glyphCache.computeIfAbsent(i, ix -> (Glyph)(ix == 32 ? SPACE : this.getRenderableGlyph(ix)));
   }

   private RenderableGlyph getRenderableGlyph(int i) {
      for (Font lv : this.fonts) {
         RenderableGlyph lv2 = lv.getGlyph(i);
         if (lv2 != null) {
            return lv2;
         }
      }

      return BlankGlyph.INSTANCE;
   }

   public GlyphRenderer getGlyphRenderer(int i) {
      return (GlyphRenderer)this.glyphRendererCache
         .computeIfAbsent(i, ix -> (GlyphRenderer)(ix == 32 ? EMPTY_GLYPH_RENDERER : this.getGlyphRenderer(this.getRenderableGlyph(ix))));
   }

   private GlyphRenderer getGlyphRenderer(RenderableGlyph c) {
      for (GlyphAtlasTexture lv : this.glyphAtlases) {
         GlyphRenderer lv2 = lv.getGlyphRenderer(c);
         if (lv2 != null) {
            return lv2;
         }
      }

      GlyphAtlasTexture lv3 = new GlyphAtlasTexture(new Identifier(this.id.getNamespace(), this.id.getPath() + "/" + this.glyphAtlases.size()), c.hasColor());
      this.glyphAtlases.add(lv3);
      this.textureManager.registerTexture(lv3.getId(), lv3);
      GlyphRenderer lv4 = lv3.getGlyphRenderer(c);
      return lv4 == null ? this.blankGlyphRenderer : lv4;
   }

   public GlyphRenderer getObfuscatedGlyphRenderer(Glyph glyph) {
      IntList intList = (IntList)this.charactersByWidth.get(MathHelper.ceil(glyph.getAdvance(false)));
      return intList != null && !intList.isEmpty() ? this.getGlyphRenderer(intList.getInt(RANDOM.nextInt(intList.size()))) : this.blankGlyphRenderer;
   }

   public GlyphRenderer getRectangleRenderer() {
      return this.whiteRectangleGlyphRenderer;
   }
}
