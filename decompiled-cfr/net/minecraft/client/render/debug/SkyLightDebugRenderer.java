/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 */
package net.minecraft.client.render.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;

public class SkyLightDebugRenderer
implements DebugRenderer.Renderer {
    private final MinecraftClient client;

    public SkyLightDebugRenderer(MinecraftClient client) {
        this.client = client;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
        ClientWorld clientWorld = this.client.world;
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        BlockPos _snowman2 = new BlockPos(cameraX, cameraY, cameraZ);
        LongOpenHashSet _snowman3 = new LongOpenHashSet();
        for (BlockPos blockPos : BlockPos.iterate(_snowman2.add(-10, -10, -10), _snowman2.add(10, 10, 10))) {
            int n = clientWorld.getLightLevel(LightType.SKY, blockPos);
            float _snowman4 = (float)(15 - n) / 15.0f * 0.5f + 0.16f;
            _snowman = MathHelper.hsvToRgb(_snowman4, 0.9f, 0.9f);
            long _snowman5 = ChunkSectionPos.fromBlockPos(blockPos.asLong());
            if (_snowman3.add(_snowman5)) {
                DebugRenderer.drawString(clientWorld.getChunkManager().getLightingProvider().displaySectionLevel(LightType.SKY, ChunkSectionPos.from(_snowman5)), ChunkSectionPos.unpackX(_snowman5) * 16 + 8, ChunkSectionPos.unpackY(_snowman5) * 16 + 8, ChunkSectionPos.unpackZ(_snowman5) * 16 + 8, 0xFF0000, 0.3f);
            }
            if (n == 15) continue;
            DebugRenderer.drawString(String.valueOf(n), (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.25, (double)blockPos.getZ() + 0.5, _snowman);
        }
        RenderSystem.enableTexture();
        RenderSystem.popMatrix();
    }
}

