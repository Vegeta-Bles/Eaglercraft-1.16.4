/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  javax.annotation.Nullable
 */
package net.minecraft.client.render.debug;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientChunkManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

public class ChunkLoadingDebugRenderer
implements DebugRenderer.Renderer {
    private final MinecraftClient client;
    private double lastUpdateTime = Double.MIN_VALUE;
    private final int field_4511 = 12;
    @Nullable
    private ChunkLoadingStatus loadingData;

    public ChunkLoadingDebugRenderer(MinecraftClient minecraftClient) {
        this.client = minecraftClient;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
        Object object;
        double d = Util.getMeasuringTimeNano();
        if (d - this.lastUpdateTime > 3.0E9) {
            this.lastUpdateTime = d;
            object = this.client.getServer();
            this.loadingData = object != null ? new ChunkLoadingStatus((IntegratedServer)object, cameraX, cameraZ) : null;
        }
        if (this.loadingData != null) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.lineWidth(2.0f);
            RenderSystem.disableTexture();
            RenderSystem.depthMask(false);
            object = this.loadingData.serverStates.getNow(null);
            double _snowman2 = this.client.gameRenderer.getCamera().getPos().y * 0.85;
            for (Map.Entry entry : this.loadingData.clientStates.entrySet()) {
                ChunkPos chunkPos = (ChunkPos)entry.getKey();
                String _snowman3 = (String)entry.getValue();
                if (object != null) {
                    _snowman3 = _snowman3 + (String)object.get(chunkPos);
                }
                String[] _snowman4 = _snowman3.split("\n");
                int _snowman5 = 0;
                for (String string : _snowman4) {
                    DebugRenderer.drawString(string, (chunkPos.x << 4) + 8, _snowman2 + (double)_snowman5, (chunkPos.z << 4) + 8, -1, 0.15f);
                    _snowman5 -= 2;
                }
            }
            RenderSystem.depthMask(true);
            RenderSystem.enableTexture();
            RenderSystem.disableBlend();
        }
    }

    final class ChunkLoadingStatus {
        private final Map<ChunkPos, String> clientStates;
        private final CompletableFuture<Map<ChunkPos, String>> serverStates;

        private ChunkLoadingStatus(IntegratedServer integratedServer, double d, double d2) {
            ClientWorld clientWorld = ((ChunkLoadingDebugRenderer)ChunkLoadingDebugRenderer.this).client.world;
            RegistryKey<World> _snowman2 = clientWorld.getRegistryKey();
            int _snowman3 = (int)d >> 4;
            int _snowman4 = (int)d2 >> 4;
            ImmutableMap.Builder _snowman5 = ImmutableMap.builder();
            ClientChunkManager _snowman6 = clientWorld.getChunkManager();
            for (int i = _snowman3 - 12; i <= _snowman3 + 12; ++i) {
                for (_snowman = _snowman4 - 12; _snowman <= _snowman4 + 12; ++_snowman) {
                    ChunkPos chunkPos = new ChunkPos(i, _snowman);
                    String _snowman7 = "";
                    WorldChunk _snowman8 = _snowman6.getWorldChunk(i, _snowman, false);
                    _snowman7 = _snowman7 + "Client: ";
                    if (_snowman8 == null) {
                        _snowman7 = _snowman7 + "0n/a\n";
                    } else {
                        _snowman7 = _snowman7 + (_snowman8.isEmpty() ? " E" : "");
                        _snowman7 = _snowman7 + "\n";
                    }
                    _snowman5.put((Object)chunkPos, (Object)_snowman7);
                }
            }
            this.clientStates = _snowman5.build();
            this.serverStates = integratedServer.submit(() -> {
                ServerWorld serverWorld = integratedServer.getWorld(_snowman2);
                if (serverWorld == null) {
                    return ImmutableMap.of();
                }
                ImmutableMap.Builder _snowman2 = ImmutableMap.builder();
                ServerChunkManager _snowman3 = serverWorld.getChunkManager();
                for (int i = _snowman3 - 12; i <= _snowman3 + 12; ++i) {
                    for (_snowman = _snowman4 - 12; _snowman <= _snowman4 + 12; ++_snowman) {
                        ChunkPos chunkPos = new ChunkPos(i, _snowman);
                        _snowman2.put((Object)chunkPos, (Object)("Server: " + _snowman3.getChunkLoadingDebugInfo(chunkPos)));
                    }
                }
                return _snowman2.build();
            });
        }
    }
}

