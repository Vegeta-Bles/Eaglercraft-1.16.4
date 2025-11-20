/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;

public interface ParticleTextureSheet {
    public static final ParticleTextureSheet TERRAIN_SHEET = new ParticleTextureSheet(){

        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.depthMask(true);
            textureManager.bindTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
            bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
        }

        public void draw(Tessellator tessellator) {
            tessellator.draw();
        }

        public String toString() {
            return "TERRAIN_SHEET";
        }
    };
    public static final ParticleTextureSheet PARTICLE_SHEET_OPAQUE = new ParticleTextureSheet(){

        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            textureManager.bindTexture(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
            bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
        }

        public void draw(Tessellator tessellator) {
            tessellator.draw();
        }

        public String toString() {
            return "PARTICLE_SHEET_OPAQUE";
        }
    };
    public static final ParticleTextureSheet PARTICLE_SHEET_TRANSLUCENT = new ParticleTextureSheet(){

        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
            RenderSystem.depthMask(true);
            textureManager.bindTexture(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.alphaFunc(516, 0.003921569f);
            bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
        }

        public void draw(Tessellator tessellator) {
            tessellator.draw();
        }

        public String toString() {
            return "PARTICLE_SHEET_TRANSLUCENT";
        }
    };
    public static final ParticleTextureSheet PARTICLE_SHEET_LIT = new ParticleTextureSheet(){

        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            textureManager.bindTexture(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
            bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
        }

        public void draw(Tessellator tessellator) {
            tessellator.draw();
        }

        public String toString() {
            return "PARTICLE_SHEET_LIT";
        }
    };
    public static final ParticleTextureSheet CUSTOM = new ParticleTextureSheet(){

        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
            RenderSystem.depthMask(true);
            RenderSystem.disableBlend();
        }

        public void draw(Tessellator tessellator) {
        }

        public String toString() {
            return "CUSTOM";
        }
    };
    public static final ParticleTextureSheet NO_RENDER = new ParticleTextureSheet(){

        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
        }

        public void draw(Tessellator tessellator) {
        }

        public String toString() {
            return "NO_RENDER";
        }
    };

    public void begin(BufferBuilder var1, TextureManager var2);

    public void draw(Tessellator var1);
}

