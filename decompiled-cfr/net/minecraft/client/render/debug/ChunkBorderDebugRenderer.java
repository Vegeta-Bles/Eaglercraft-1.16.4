/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class ChunkBorderDebugRenderer
implements DebugRenderer.Renderer {
    private final MinecraftClient client;

    public ChunkBorderDebugRenderer(MinecraftClient client) {
        this.client = client;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
        int n;
        RenderSystem.enableDepthTest();
        RenderSystem.shadeModel(7425);
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultAlphaFunc();
        Entity entity = this.client.gameRenderer.getCamera().getFocusedEntity();
        Tessellator _snowman2 = Tessellator.getInstance();
        BufferBuilder _snowman3 = _snowman2.getBuffer();
        double _snowman4 = 0.0 - cameraY;
        double _snowman5 = 256.0 - cameraY;
        RenderSystem.disableTexture();
        RenderSystem.disableBlend();
        double _snowman6 = (double)(entity.chunkX << 4) - cameraX;
        double _snowman7 = (double)(entity.chunkZ << 4) - cameraZ;
        RenderSystem.lineWidth(1.0f);
        _snowman3.begin(3, VertexFormats.POSITION_COLOR);
        for (n = -16; n <= 32; n += 16) {
            for (_snowman = -16; _snowman <= 32; _snowman += 16) {
                _snowman3.vertex(_snowman6 + (double)n, _snowman4, _snowman7 + (double)_snowman).color(1.0f, 0.0f, 0.0f, 0.0f).next();
                _snowman3.vertex(_snowman6 + (double)n, _snowman4, _snowman7 + (double)_snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                _snowman3.vertex(_snowman6 + (double)n, _snowman5, _snowman7 + (double)_snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                _snowman3.vertex(_snowman6 + (double)n, _snowman5, _snowman7 + (double)_snowman).color(1.0f, 0.0f, 0.0f, 0.0f).next();
            }
        }
        for (n = 2; n < 16; n += 2) {
            _snowman3.vertex(_snowman6 + (double)n, _snowman4, _snowman7).color(1.0f, 1.0f, 0.0f, 0.0f).next();
            _snowman3.vertex(_snowman6 + (double)n, _snowman4, _snowman7).color(1.0f, 1.0f, 0.0f, 1.0f).next();
            _snowman3.vertex(_snowman6 + (double)n, _snowman5, _snowman7).color(1.0f, 1.0f, 0.0f, 1.0f).next();
            _snowman3.vertex(_snowman6 + (double)n, _snowman5, _snowman7).color(1.0f, 1.0f, 0.0f, 0.0f).next();
            _snowman3.vertex(_snowman6 + (double)n, _snowman4, _snowman7 + 16.0).color(1.0f, 1.0f, 0.0f, 0.0f).next();
            _snowman3.vertex(_snowman6 + (double)n, _snowman4, _snowman7 + 16.0).color(1.0f, 1.0f, 0.0f, 1.0f).next();
            _snowman3.vertex(_snowman6 + (double)n, _snowman5, _snowman7 + 16.0).color(1.0f, 1.0f, 0.0f, 1.0f).next();
            _snowman3.vertex(_snowman6 + (double)n, _snowman5, _snowman7 + 16.0).color(1.0f, 1.0f, 0.0f, 0.0f).next();
        }
        for (n = 2; n < 16; n += 2) {
            _snowman3.vertex(_snowman6, _snowman4, _snowman7 + (double)n).color(1.0f, 1.0f, 0.0f, 0.0f).next();
            _snowman3.vertex(_snowman6, _snowman4, _snowman7 + (double)n).color(1.0f, 1.0f, 0.0f, 1.0f).next();
            _snowman3.vertex(_snowman6, _snowman5, _snowman7 + (double)n).color(1.0f, 1.0f, 0.0f, 1.0f).next();
            _snowman3.vertex(_snowman6, _snowman5, _snowman7 + (double)n).color(1.0f, 1.0f, 0.0f, 0.0f).next();
            _snowman3.vertex(_snowman6 + 16.0, _snowman4, _snowman7 + (double)n).color(1.0f, 1.0f, 0.0f, 0.0f).next();
            _snowman3.vertex(_snowman6 + 16.0, _snowman4, _snowman7 + (double)n).color(1.0f, 1.0f, 0.0f, 1.0f).next();
            _snowman3.vertex(_snowman6 + 16.0, _snowman5, _snowman7 + (double)n).color(1.0f, 1.0f, 0.0f, 1.0f).next();
            _snowman3.vertex(_snowman6 + 16.0, _snowman5, _snowman7 + (double)n).color(1.0f, 1.0f, 0.0f, 0.0f).next();
        }
        for (n = 0; n <= 256; n += 2) {
            double d = (double)n - cameraY;
            _snowman3.vertex(_snowman6, d, _snowman7).color(1.0f, 1.0f, 0.0f, 0.0f).next();
            _snowman3.vertex(_snowman6, d, _snowman7).color(1.0f, 1.0f, 0.0f, 1.0f).next();
            _snowman3.vertex(_snowman6, d, _snowman7 + 16.0).color(1.0f, 1.0f, 0.0f, 1.0f).next();
            _snowman3.vertex(_snowman6 + 16.0, d, _snowman7 + 16.0).color(1.0f, 1.0f, 0.0f, 1.0f).next();
            _snowman3.vertex(_snowman6 + 16.0, d, _snowman7).color(1.0f, 1.0f, 0.0f, 1.0f).next();
            _snowman3.vertex(_snowman6, d, _snowman7).color(1.0f, 1.0f, 0.0f, 1.0f).next();
            _snowman3.vertex(_snowman6, d, _snowman7).color(1.0f, 1.0f, 0.0f, 0.0f).next();
        }
        _snowman2.draw();
        RenderSystem.lineWidth(2.0f);
        _snowman3.begin(3, VertexFormats.POSITION_COLOR);
        for (n = 0; n <= 16; n += 16) {
            for (_snowman = 0; _snowman <= 16; _snowman += 16) {
                _snowman3.vertex(_snowman6 + (double)n, _snowman4, _snowman7 + (double)_snowman).color(0.25f, 0.25f, 1.0f, 0.0f).next();
                _snowman3.vertex(_snowman6 + (double)n, _snowman4, _snowman7 + (double)_snowman).color(0.25f, 0.25f, 1.0f, 1.0f).next();
                _snowman3.vertex(_snowman6 + (double)n, _snowman5, _snowman7 + (double)_snowman).color(0.25f, 0.25f, 1.0f, 1.0f).next();
                _snowman3.vertex(_snowman6 + (double)n, _snowman5, _snowman7 + (double)_snowman).color(0.25f, 0.25f, 1.0f, 0.0f).next();
            }
        }
        for (n = 0; n <= 256; n += 16) {
            double d = (double)n - cameraY;
            _snowman3.vertex(_snowman6, d, _snowman7).color(0.25f, 0.25f, 1.0f, 0.0f).next();
            _snowman3.vertex(_snowman6, d, _snowman7).color(0.25f, 0.25f, 1.0f, 1.0f).next();
            _snowman3.vertex(_snowman6, d, _snowman7 + 16.0).color(0.25f, 0.25f, 1.0f, 1.0f).next();
            _snowman3.vertex(_snowman6 + 16.0, d, _snowman7 + 16.0).color(0.25f, 0.25f, 1.0f, 1.0f).next();
            _snowman3.vertex(_snowman6 + 16.0, d, _snowman7).color(0.25f, 0.25f, 1.0f, 1.0f).next();
            _snowman3.vertex(_snowman6, d, _snowman7).color(0.25f, 0.25f, 1.0f, 1.0f).next();
            _snowman3.vertex(_snowman6, d, _snowman7).color(0.25f, 0.25f, 1.0f, 0.0f).next();
        }
        _snowman2.draw();
        RenderSystem.lineWidth(1.0f);
        RenderSystem.enableBlend();
        RenderSystem.enableTexture();
        RenderSystem.shadeModel(7424);
    }
}

