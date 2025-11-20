package net.minecraft.client.render.debug;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
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

public class ChunkLoadingDebugRenderer implements DebugRenderer.Renderer {
   private final MinecraftClient client;
   private double lastUpdateTime = Double.MIN_VALUE;
   private final int field_4511 = 12;
   @Nullable
   private ChunkLoadingDebugRenderer.ChunkLoadingStatus loadingData;

   public ChunkLoadingDebugRenderer(MinecraftClient _snowman) {
      this.client = _snowman;
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
      double _snowman = (double)Util.getMeasuringTimeNano();
      if (_snowman - this.lastUpdateTime > 3.0E9) {
         this.lastUpdateTime = _snowman;
         IntegratedServer _snowmanx = this.client.getServer();
         if (_snowmanx != null) {
            this.loadingData = new ChunkLoadingDebugRenderer.ChunkLoadingStatus(_snowmanx, cameraX, cameraZ);
         } else {
            this.loadingData = null;
         }
      }

      if (this.loadingData != null) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.lineWidth(2.0F);
         RenderSystem.disableTexture();
         RenderSystem.depthMask(false);
         Map<ChunkPos, String> _snowmanx = this.loadingData.serverStates.getNow(null);
         double _snowmanxx = this.client.gameRenderer.getCamera().getPos().y * 0.85;

         for (Entry<ChunkPos, String> _snowmanxxx : this.loadingData.clientStates.entrySet()) {
            ChunkPos _snowmanxxxx = _snowmanxxx.getKey();
            String _snowmanxxxxx = _snowmanxxx.getValue();
            if (_snowmanx != null) {
               _snowmanxxxxx = _snowmanxxxxx + _snowmanx.get(_snowmanxxxx);
            }

            String[] _snowmanxxxxxx = _snowmanxxxxx.split("\n");
            int _snowmanxxxxxxx = 0;

            for (String _snowmanxxxxxxxx : _snowmanxxxxxx) {
               DebugRenderer.drawString(_snowmanxxxxxxxx, (double)((_snowmanxxxx.x << 4) + 8), _snowmanxx + (double)_snowmanxxxxxxx, (double)((_snowmanxxxx.z << 4) + 8), -1, 0.15F);
               _snowmanxxxxxxx -= 2;
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

      private ChunkLoadingStatus(IntegratedServer var2, double var3, double var5) {
         ClientWorld _snowman = ChunkLoadingDebugRenderer.this.client.world;
         RegistryKey<World> _snowmanx = _snowman.getRegistryKey();
         int _snowmanxx = (int)_snowman >> 4;
         int _snowmanxxx = (int)_snowman >> 4;
         Builder<ChunkPos, String> _snowmanxxxx = ImmutableMap.builder();
         ClientChunkManager _snowmanxxxxx = _snowman.getChunkManager();

         for (int _snowmanxxxxxx = _snowmanxx - 12; _snowmanxxxxxx <= _snowmanxx + 12; _snowmanxxxxxx++) {
            for (int _snowmanxxxxxxx = _snowmanxxx - 12; _snowmanxxxxxxx <= _snowmanxxx + 12; _snowmanxxxxxxx++) {
               ChunkPos _snowmanxxxxxxxx = new ChunkPos(_snowmanxxxxxx, _snowmanxxxxxxx);
               String _snowmanxxxxxxxxx = "";
               WorldChunk _snowmanxxxxxxxxxx = _snowmanxxxxx.getWorldChunk(_snowmanxxxxxx, _snowmanxxxxxxx, false);
               _snowmanxxxxxxxxx = _snowmanxxxxxxxxx + "Client: ";
               if (_snowmanxxxxxxxxxx == null) {
                  _snowmanxxxxxxxxx = _snowmanxxxxxxxxx + "0n/a\n";
               } else {
                  _snowmanxxxxxxxxx = _snowmanxxxxxxxxx + (_snowmanxxxxxxxxxx.isEmpty() ? " E" : "");
                  _snowmanxxxxxxxxx = _snowmanxxxxxxxxx + "\n";
               }

               _snowmanxxxx.put(_snowmanxxxxxxxx, _snowmanxxxxxxxxx);
            }
         }

         this.clientStates = _snowmanxxxx.build();
         this.serverStates = _snowman.submit(() -> {
            ServerWorld _snowmanxxxxxx = _snowman.getWorld(_snowman);
            if (_snowmanxxxxxx == null) {
               return ImmutableMap.of();
            } else {
               Builder<ChunkPos, String> _snowmanx = ImmutableMap.builder();
               ServerChunkManager _snowmanxx = _snowmanxxxxxx.getChunkManager();

               for (int _snowmanxxx = _snowman - 12; _snowmanxxx <= _snowman + 12; _snowmanxxx++) {
                  for (int _snowmanxxxx = _snowman - 12; _snowmanxxxx <= _snowman + 12; _snowmanxxxx++) {
                     ChunkPos _snowmanxxxxx = new ChunkPos(_snowmanxxx, _snowmanxxxx);
                     _snowmanx.put(_snowmanxxxxx, "Server: " + _snowmanxx.getChunkLoadingDebugInfo(_snowmanxxxxx));
                  }
               }

               return _snowmanx.build();
            }
         });
      }
   }
}
