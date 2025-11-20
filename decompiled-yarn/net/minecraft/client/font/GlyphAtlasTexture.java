package net.minecraft.client.font;

import javax.annotation.Nullable;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.TextureUtil;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class GlyphAtlasTexture extends AbstractTexture {
   private final Identifier id;
   private final RenderLayer textLayer;
   private final RenderLayer seeThroughTextLayer;
   private final boolean hasColor;
   private final GlyphAtlasTexture.Slot rootSlot;

   public GlyphAtlasTexture(Identifier id, boolean hasColor) {
      this.id = id;
      this.hasColor = hasColor;
      this.rootSlot = new GlyphAtlasTexture.Slot(0, 0, 256, 256);
      TextureUtil.allocate(hasColor ? NativeImage.GLFormat.ABGR : NativeImage.GLFormat.INTENSITY, this.getGlId(), 256, 256);
      this.textLayer = RenderLayer.getText(id);
      this.seeThroughTextLayer = RenderLayer.getTextSeeThrough(id);
   }

   @Override
   public void load(ResourceManager manager) {
   }

   @Override
   public void close() {
      this.clearGlId();
   }

   @Nullable
   public GlyphRenderer getGlyphRenderer(RenderableGlyph glyph) {
      if (glyph.hasColor() != this.hasColor) {
         return null;
      } else {
         GlyphAtlasTexture.Slot _snowman = this.rootSlot.findSlotFor(glyph);
         if (_snowman != null) {
            this.bindTexture();
            glyph.upload(_snowman.x, _snowman.y);
            float _snowmanx = 256.0F;
            float _snowmanxx = 256.0F;
            float _snowmanxxx = 0.01F;
            return new GlyphRenderer(
               this.textLayer,
               this.seeThroughTextLayer,
               ((float)_snowman.x + 0.01F) / 256.0F,
               ((float)_snowman.x - 0.01F + (float)glyph.getWidth()) / 256.0F,
               ((float)_snowman.y + 0.01F) / 256.0F,
               ((float)_snowman.y - 0.01F + (float)glyph.getHeight()) / 256.0F,
               glyph.getXMin(),
               glyph.getXMax(),
               glyph.getYMin(),
               glyph.getYMax()
            );
         } else {
            return null;
         }
      }
   }

   public Identifier getId() {
      return this.id;
   }

   static class Slot {
      private final int x;
      private final int y;
      private final int width;
      private final int height;
      private GlyphAtlasTexture.Slot subSlot1;
      private GlyphAtlasTexture.Slot subSlot2;
      private boolean occupied;

      private Slot(int x, int y, int width, int height) {
         this.x = x;
         this.y = y;
         this.width = width;
         this.height = height;
      }

      @Nullable
      GlyphAtlasTexture.Slot findSlotFor(RenderableGlyph glyph) {
         if (this.subSlot1 != null && this.subSlot2 != null) {
            GlyphAtlasTexture.Slot _snowman = this.subSlot1.findSlotFor(glyph);
            if (_snowman == null) {
               _snowman = this.subSlot2.findSlotFor(glyph);
            }

            return _snowman;
         } else if (this.occupied) {
            return null;
         } else {
            int _snowman = glyph.getWidth();
            int _snowmanx = glyph.getHeight();
            if (_snowman > this.width || _snowmanx > this.height) {
               return null;
            } else if (_snowman == this.width && _snowmanx == this.height) {
               this.occupied = true;
               return this;
            } else {
               int _snowmanxx = this.width - _snowman;
               int _snowmanxxx = this.height - _snowmanx;
               if (_snowmanxx > _snowmanxxx) {
                  this.subSlot1 = new GlyphAtlasTexture.Slot(this.x, this.y, _snowman, this.height);
                  this.subSlot2 = new GlyphAtlasTexture.Slot(this.x + _snowman + 1, this.y, this.width - _snowman - 1, this.height);
               } else {
                  this.subSlot1 = new GlyphAtlasTexture.Slot(this.x, this.y, this.width, _snowmanx);
                  this.subSlot2 = new GlyphAtlasTexture.Slot(this.x, this.y + _snowmanx + 1, this.width, this.height - _snowmanx - 1);
               }

               return this.subSlot1.findSlotFor(glyph);
            }
         }
      }
   }
}
