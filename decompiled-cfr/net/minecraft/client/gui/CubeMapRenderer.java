/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

public class CubeMapRenderer {
    private final Identifier[] faces = new Identifier[6];

    public CubeMapRenderer(Identifier faces) {
        for (int i = 0; i < 6; ++i) {
            this.faces[i] = new Identifier(faces.getNamespace(), faces.getPath() + '_' + i + ".png");
        }
    }

    public void draw(MinecraftClient client, float x, float y, float alpha) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder _snowman2 = tessellator.getBuffer();
        RenderSystem.matrixMode(5889);
        RenderSystem.pushMatrix();
        RenderSystem.loadIdentity();
        RenderSystem.multMatrix(Matrix4f.viewboxMatrix(85.0, (float)client.getWindow().getFramebufferWidth() / (float)client.getWindow().getFramebufferHeight(), 0.05f, 10.0f));
        RenderSystem.matrixMode(5888);
        RenderSystem.pushMatrix();
        RenderSystem.loadIdentity();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.rotatef(180.0f, 1.0f, 0.0f, 0.0f);
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.disableCull();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        int _snowman3 = 2;
        for (int i = 0; i < 4; ++i) {
            RenderSystem.pushMatrix();
            float f = ((float)(i % 2) / 2.0f - 0.5f) / 256.0f;
            _snowman = ((float)(i / 2) / 2.0f - 0.5f) / 256.0f;
            _snowman = 0.0f;
            RenderSystem.translatef(f, _snowman, 0.0f);
            RenderSystem.rotatef(x, 1.0f, 0.0f, 0.0f);
            RenderSystem.rotatef(y, 0.0f, 1.0f, 0.0f);
            for (int j = 0; j < 6; ++j) {
                client.getTextureManager().bindTexture(this.faces[j]);
                _snowman2.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
                _snowman = Math.round(255.0f * alpha) / (i + 1);
                if (j == 0) {
                    _snowman2.vertex(-1.0, -1.0, 1.0).texture(0.0f, 0.0f).color(255, 255, 255, _snowman).next();
                    _snowman2.vertex(-1.0, 1.0, 1.0).texture(0.0f, 1.0f).color(255, 255, 255, _snowman).next();
                    _snowman2.vertex(1.0, 1.0, 1.0).texture(1.0f, 1.0f).color(255, 255, 255, _snowman).next();
                    _snowman2.vertex(1.0, -1.0, 1.0).texture(1.0f, 0.0f).color(255, 255, 255, _snowman).next();
                }
                if (j == 1) {
                    _snowman2.vertex(1.0, -1.0, 1.0).texture(0.0f, 0.0f).color(255, 255, 255, _snowman).next();
                    _snowman2.vertex(1.0, 1.0, 1.0).texture(0.0f, 1.0f).color(255, 255, 255, _snowman).next();
                    _snowman2.vertex(1.0, 1.0, -1.0).texture(1.0f, 1.0f).color(255, 255, 255, _snowman).next();
                    _snowman2.vertex(1.0, -1.0, -1.0).texture(1.0f, 0.0f).color(255, 255, 255, _snowman).next();
                }
                if (j == 2) {
                    _snowman2.vertex(1.0, -1.0, -1.0).texture(0.0f, 0.0f).color(255, 255, 255, _snowman).next();
                    _snowman2.vertex(1.0, 1.0, -1.0).texture(0.0f, 1.0f).color(255, 255, 255, _snowman).next();
                    _snowman2.vertex(-1.0, 1.0, -1.0).texture(1.0f, 1.0f).color(255, 255, 255, _snowman).next();
                    _snowman2.vertex(-1.0, -1.0, -1.0).texture(1.0f, 0.0f).color(255, 255, 255, _snowman).next();
                }
                if (j == 3) {
                    _snowman2.vertex(-1.0, -1.0, -1.0).texture(0.0f, 0.0f).color(255, 255, 255, _snowman).next();
                    _snowman2.vertex(-1.0, 1.0, -1.0).texture(0.0f, 1.0f).color(255, 255, 255, _snowman).next();
                    _snowman2.vertex(-1.0, 1.0, 1.0).texture(1.0f, 1.0f).color(255, 255, 255, _snowman).next();
                    _snowman2.vertex(-1.0, -1.0, 1.0).texture(1.0f, 0.0f).color(255, 255, 255, _snowman).next();
                }
                if (j == 4) {
                    _snowman2.vertex(-1.0, -1.0, -1.0).texture(0.0f, 0.0f).color(255, 255, 255, _snowman).next();
                    _snowman2.vertex(-1.0, -1.0, 1.0).texture(0.0f, 1.0f).color(255, 255, 255, _snowman).next();
                    _snowman2.vertex(1.0, -1.0, 1.0).texture(1.0f, 1.0f).color(255, 255, 255, _snowman).next();
                    _snowman2.vertex(1.0, -1.0, -1.0).texture(1.0f, 0.0f).color(255, 255, 255, _snowman).next();
                }
                if (j == 5) {
                    _snowman2.vertex(-1.0, 1.0, 1.0).texture(0.0f, 0.0f).color(255, 255, 255, _snowman).next();
                    _snowman2.vertex(-1.0, 1.0, -1.0).texture(0.0f, 1.0f).color(255, 255, 255, _snowman).next();
                    _snowman2.vertex(1.0, 1.0, -1.0).texture(1.0f, 1.0f).color(255, 255, 255, _snowman).next();
                    _snowman2.vertex(1.0, 1.0, 1.0).texture(1.0f, 0.0f).color(255, 255, 255, _snowman).next();
                }
                tessellator.draw();
            }
            RenderSystem.popMatrix();
            RenderSystem.colorMask(true, true, true, false);
        }
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.matrixMode(5889);
        RenderSystem.popMatrix();
        RenderSystem.matrixMode(5888);
        RenderSystem.popMatrix();
        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
        RenderSystem.enableDepthTest();
    }

    public CompletableFuture<Void> loadTexturesAsync(TextureManager textureManager, Executor executor) {
        CompletableFuture[] completableFutureArray = new CompletableFuture[6];
        for (int i = 0; i < completableFutureArray.length; ++i) {
            completableFutureArray[i] = textureManager.loadTextureAsync(this.faces[i], executor);
        }
        return CompletableFuture.allOf(completableFutureArray);
    }
}

