package net.minecraft.client.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;

public interface ParticleTextureSheet {
   ParticleTextureSheet TERRAIN_SHEET = new ParticleTextureSheet() {
      @Override
      public void begin(BufferBuilder _snowman, TextureManager _snowman) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.depthMask(true);
         _snowman.bindTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
         _snowman.begin(7, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
      }

      @Override
      public void draw(Tessellator _snowman) {
         _snowman.draw();
      }

      @Override
      public String toString() {
         return "TERRAIN_SHEET";
      }
   };
   ParticleTextureSheet PARTICLE_SHEET_OPAQUE = new ParticleTextureSheet() {
      @Override
      public void begin(BufferBuilder _snowman, TextureManager _snowman) {
         RenderSystem.disableBlend();
         RenderSystem.depthMask(true);
         _snowman.bindTexture(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
         _snowman.begin(7, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
      }

      @Override
      public void draw(Tessellator _snowman) {
         _snowman.draw();
      }

      @Override
      public String toString() {
         return "PARTICLE_SHEET_OPAQUE";
      }
   };
   ParticleTextureSheet PARTICLE_SHEET_TRANSLUCENT = new ParticleTextureSheet() {
      @Override
      public void begin(BufferBuilder _snowman, TextureManager _snowman) {
         RenderSystem.depthMask(true);
         _snowman.bindTexture(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
         RenderSystem.enableBlend();
         RenderSystem.blendFuncSeparate(
            GlStateManager.SrcFactor.SRC_ALPHA,
            GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SrcFactor.ONE,
            GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA
         );
         RenderSystem.alphaFunc(516, 0.003921569F);
         _snowman.begin(7, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
      }

      @Override
      public void draw(Tessellator _snowman) {
         _snowman.draw();
      }

      @Override
      public String toString() {
         return "PARTICLE_SHEET_TRANSLUCENT";
      }
   };
   ParticleTextureSheet PARTICLE_SHEET_LIT = new ParticleTextureSheet() {
      @Override
      public void begin(BufferBuilder _snowman, TextureManager _snowman) {
         RenderSystem.disableBlend();
         RenderSystem.depthMask(true);
         _snowman.bindTexture(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
         _snowman.begin(7, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
      }

      @Override
      public void draw(Tessellator _snowman) {
         _snowman.draw();
      }

      @Override
      public String toString() {
         return "PARTICLE_SHEET_LIT";
      }
   };
   ParticleTextureSheet CUSTOM = new ParticleTextureSheet() {
      @Override
      public void begin(BufferBuilder _snowman, TextureManager _snowman) {
         RenderSystem.depthMask(true);
         RenderSystem.disableBlend();
      }

      @Override
      public void draw(Tessellator _snowman) {
      }

      @Override
      public String toString() {
         return "CUSTOM";
      }
   };
   ParticleTextureSheet NO_RENDER = new ParticleTextureSheet() {
      @Override
      public void begin(BufferBuilder _snowman, TextureManager _snowman) {
      }

      @Override
      public void draw(Tessellator _snowman) {
      }

      @Override
      public String toString() {
         return "NO_RENDER";
      }
   };

   void begin(BufferBuilder var1, TextureManager var2);

   void draw(Tessellator var1);
}
