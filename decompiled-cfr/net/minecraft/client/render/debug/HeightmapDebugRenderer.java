/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.chunk.Chunk;

public class HeightmapDebugRenderer
implements DebugRenderer.Renderer {
    private final MinecraftClient client;

    public HeightmapDebugRenderer(MinecraftClient client) {
        this.client = client;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
        ClientWorld clientWorld = this.client.world;
        RenderSystem.pushMatrix();
        RenderSystem.disableBlend();
        RenderSystem.disableTexture();
        RenderSystem.enableDepthTest();
        BlockPos _snowman2 = new BlockPos(cameraX, 0.0, cameraZ);
        Tessellator _snowman3 = Tessellator.getInstance();
        BufferBuilder _snowman4 = _snowman3.getBuffer();
        _snowman4.begin(5, VertexFormats.POSITION_COLOR);
        for (int i = -32; i <= 32; i += 16) {
            for (_snowman = -32; _snowman <= 32; _snowman += 16) {
                Chunk chunk = clientWorld.getChunk(_snowman2.add(i, 0, _snowman));
                for (Map.Entry<Heightmap.Type, Heightmap> entry : chunk.getHeightmaps()) {
                    Heightmap.Type type = entry.getKey();
                    ChunkPos _snowman5 = chunk.getPos();
                    Vector3f _snowman6 = this.method_27037(type);
                    for (int j = 0; j < 16; ++j) {
                        for (_snowman = 0; _snowman < 16; ++_snowman) {
                            _snowman = _snowman5.x * 16 + j;
                            _snowman = _snowman5.z * 16 + _snowman;
                            float f = (float)((double)((float)clientWorld.getTopY(type, _snowman, _snowman) + (float)type.ordinal() * 0.09375f) - cameraY);
                            WorldRenderer.drawBox(_snowman4, (double)((float)_snowman + 0.25f) - cameraX, f, (double)((float)_snowman + 0.25f) - cameraZ, (double)((float)_snowman + 0.75f) - cameraX, f + 0.09375f, (double)((float)_snowman + 0.75f) - cameraZ, _snowman6.getX(), _snowman6.getY(), _snowman6.getZ(), 1.0f);
                        }
                    }
                }
            }
        }
        _snowman3.draw();
        RenderSystem.enableTexture();
        RenderSystem.popMatrix();
    }

    private Vector3f method_27037(Heightmap.Type type) {
        switch (type) {
            case WORLD_SURFACE_WG: {
                return new Vector3f(1.0f, 1.0f, 0.0f);
            }
            case OCEAN_FLOOR_WG: {
                return new Vector3f(1.0f, 0.0f, 1.0f);
            }
            case WORLD_SURFACE: {
                return new Vector3f(0.0f, 0.7f, 0.0f);
            }
            case OCEAN_FLOOR: {
                return new Vector3f(0.0f, 0.0f, 0.5f);
            }
            case MOTION_BLOCKING: {
                return new Vector3f(0.0f, 0.3f, 0.3f);
            }
            case MOTION_BLOCKING_NO_LEAVES: {
                return new Vector3f(0.0f, 0.5f, 0.5f);
            }
        }
        return new Vector3f(0.0f, 0.0f, 0.0f);
    }
}

