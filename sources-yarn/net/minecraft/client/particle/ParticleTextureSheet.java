package net.minecraft.client.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;

@Environment(EnvType.CLIENT)
public interface ParticleTextureSheet {
   ParticleTextureSheet TERRAIN_SHEET = new ParticleTextureSheet() {
      @Override
      public void begin(BufferBuilder arg, TextureManager arg2) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.depthMask(true);
         arg2.bindTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
         arg.begin(7, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
      }

      @Override
      public void draw(Tessellator arg) {
         arg.draw();
      }

      @Override
      public String toString() {
         return "TERRAIN_SHEET";
      }
   };
   ParticleTextureSheet PARTICLE_SHEET_OPAQUE = new ParticleTextureSheet() {
      @Override
      public void begin(BufferBuilder arg, TextureManager arg2) {
         RenderSystem.disableBlend();
         RenderSystem.depthMask(true);
         arg2.bindTexture(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
         arg.begin(7, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
      }

      @Override
      public void draw(Tessellator arg) {
         arg.draw();
      }

      @Override
      public String toString() {
         return "PARTICLE_SHEET_OPAQUE";
      }
   };
   ParticleTextureSheet PARTICLE_SHEET_TRANSLUCENT = new ParticleTextureSheet() {
      @Override
      public void begin(BufferBuilder arg, TextureManager arg2) {
         RenderSystem.depthMask(true);
         arg2.bindTexture(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
         RenderSystem.enableBlend();
         RenderSystem.blendFuncSeparate(
            GlStateManager.SrcFactor.SRC_ALPHA,
            GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SrcFactor.ONE,
            GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA
         );
         RenderSystem.alphaFunc(516, 0.003921569F);
         arg.begin(7, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
      }

      @Override
      public void draw(Tessellator arg) {
         arg.draw();
      }

      @Override
      public String toString() {
         return "PARTICLE_SHEET_TRANSLUCENT";
      }
   };
   ParticleTextureSheet PARTICLE_SHEET_LIT = new ParticleTextureSheet() {
      @Override
      public void begin(BufferBuilder arg, TextureManager arg2) {
         RenderSystem.disableBlend();
         RenderSystem.depthMask(true);
         arg2.bindTexture(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
         arg.begin(7, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
      }

      @Override
      public void draw(Tessellator arg) {
         arg.draw();
      }

      @Override
      public String toString() {
         return "PARTICLE_SHEET_LIT";
      }
   };
   ParticleTextureSheet CUSTOM = new ParticleTextureSheet() {
      @Override
      public void begin(BufferBuilder arg, TextureManager arg2) {
         RenderSystem.depthMask(true);
         RenderSystem.disableBlend();
      }

      @Override
      public void draw(Tessellator arg) {
      }

      @Override
      public String toString() {
         return "CUSTOM";
      }
   };
   ParticleTextureSheet NO_RENDER = new ParticleTextureSheet() {
      @Override
      public void begin(BufferBuilder arg, TextureManager arg2) {
      }

      @Override
      public void draw(Tessellator arg) {
      }

      @Override
      public String toString() {
         return "NO_RENDER";
      }
   };

   void begin(BufferBuilder arg, TextureManager arg2);

   void draw(Tessellator arg);
}
