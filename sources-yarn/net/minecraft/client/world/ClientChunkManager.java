package net.minecraft.client.world;

import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.source.BiomeArray;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.chunk.light.LightingProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class ClientChunkManager extends ChunkManager {
   private static final Logger LOGGER = LogManager.getLogger();
   private final WorldChunk emptyChunk;
   private final LightingProvider lightingProvider;
   private volatile ClientChunkManager.ClientChunkMap chunks;
   private final ClientWorld world;

   public ClientChunkManager(ClientWorld world, int loadDistance) {
      this.world = world;
      this.emptyChunk = new EmptyChunk(world, new ChunkPos(0, 0));
      this.lightingProvider = new LightingProvider(this, true, world.getDimension().hasSkyLight());
      this.chunks = new ClientChunkManager.ClientChunkMap(getChunkMapRadius(loadDistance));
   }

   @Override
   public LightingProvider getLightingProvider() {
      return this.lightingProvider;
   }

   private static boolean positionEquals(@Nullable WorldChunk chunk, int x, int y) {
      if (chunk == null) {
         return false;
      } else {
         ChunkPos lv = chunk.getPos();
         return lv.x == x && lv.z == y;
      }
   }

   public void unload(int chunkX, int chunkZ) {
      if (this.chunks.isInRadius(chunkX, chunkZ)) {
         int k = this.chunks.getIndex(chunkX, chunkZ);
         WorldChunk lv = this.chunks.getChunk(k);
         if (positionEquals(lv, chunkX, chunkZ)) {
            this.chunks.compareAndSet(k, lv, null);
         }
      }
   }

   @Nullable
   public WorldChunk getChunk(int i, int j, ChunkStatus arg, boolean bl) {
      if (this.chunks.isInRadius(i, j)) {
         WorldChunk lv = this.chunks.getChunk(this.chunks.getIndex(i, j));
         if (positionEquals(lv, i, j)) {
            return lv;
         }
      }

      return bl ? this.emptyChunk : null;
   }

   @Override
   public BlockView getWorld() {
      return this.world;
   }

   @Nullable
   public WorldChunk loadChunkFromPacket(
      int x, int z, @Nullable BiomeArray biomes, PacketByteBuf buf, CompoundTag tag, int verticalStripBitmask, boolean complete
   ) {
      if (!this.chunks.isInRadius(x, z)) {
         LOGGER.warn("Ignoring chunk since it's not in the view range: {}, {}", x, z);
         return null;
      } else {
         int l = this.chunks.getIndex(x, z);
         WorldChunk lv = this.chunks.chunks.get(l);
         if (!complete && positionEquals(lv, x, z)) {
            lv.loadFromPacket(biomes, buf, tag, verticalStripBitmask);
         } else {
            if (biomes == null) {
               LOGGER.warn("Ignoring chunk since we don't have complete data: {}, {}", x, z);
               return null;
            }

            lv = new WorldChunk(this.world, new ChunkPos(x, z), biomes);
            lv.loadFromPacket(biomes, buf, tag, verticalStripBitmask);
            this.chunks.set(l, lv);
         }

         ChunkSection[] lvs = lv.getSectionArray();
         LightingProvider lv2 = this.getLightingProvider();
         lv2.setColumnEnabled(new ChunkPos(x, z), true);

         for (int m = 0; m < lvs.length; m++) {
            ChunkSection lv3 = lvs[m];
            lv2.setSectionStatus(ChunkSectionPos.from(x, m, z), ChunkSection.isEmpty(lv3));
         }

         this.world.resetChunkColor(x, z);
         return lv;
      }
   }

   public void tick(BooleanSupplier shouldKeepTicking) {
   }

   public void setChunkMapCenter(int x, int z) {
      this.chunks.centerChunkX = x;
      this.chunks.centerChunkZ = z;
   }

   public void updateLoadDistance(int loadDistance) {
      int j = this.chunks.radius;
      int k = getChunkMapRadius(loadDistance);
      if (j != k) {
         ClientChunkManager.ClientChunkMap lv = new ClientChunkManager.ClientChunkMap(k);
         lv.centerChunkX = this.chunks.centerChunkX;
         lv.centerChunkZ = this.chunks.centerChunkZ;

         for (int l = 0; l < this.chunks.chunks.length(); l++) {
            WorldChunk lv2 = this.chunks.chunks.get(l);
            if (lv2 != null) {
               ChunkPos lv3 = lv2.getPos();
               if (lv.isInRadius(lv3.x, lv3.z)) {
                  lv.set(lv.getIndex(lv3.x, lv3.z), lv2);
               }
            }
         }

         this.chunks = lv;
      }
   }

   private static int getChunkMapRadius(int loadDistance) {
      return Math.max(2, loadDistance) + 3;
   }

   @Override
   public String getDebugString() {
      return "Client Chunk Cache: " + this.chunks.chunks.length() + ", " + this.getLoadedChunkCount();
   }

   public int getLoadedChunkCount() {
      return this.chunks.loadedChunkCount;
   }

   @Override
   public void onLightUpdate(LightType type, ChunkSectionPos pos) {
      MinecraftClient.getInstance().worldRenderer.scheduleBlockRender(pos.getSectionX(), pos.getSectionY(), pos.getSectionZ());
   }

   @Override
   public boolean shouldTickBlock(BlockPos pos) {
      return this.isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4);
   }

   @Override
   public boolean shouldTickChunk(ChunkPos pos) {
      return this.isChunkLoaded(pos.x, pos.z);
   }

   @Override
   public boolean shouldTickEntity(Entity entity) {
      return this.isChunkLoaded(MathHelper.floor(entity.getX()) >> 4, MathHelper.floor(entity.getZ()) >> 4);
   }

   @Environment(EnvType.CLIENT)
   final class ClientChunkMap {
      private final AtomicReferenceArray<WorldChunk> chunks;
      private final int radius;
      private final int diameter;
      private volatile int centerChunkX;
      private volatile int centerChunkZ;
      private int loadedChunkCount;

      private ClientChunkMap(int loadDistance) {
         this.radius = loadDistance;
         this.diameter = loadDistance * 2 + 1;
         this.chunks = new AtomicReferenceArray<>(this.diameter * this.diameter);
      }

      private int getIndex(int chunkX, int chunkZ) {
         return Math.floorMod(chunkZ, this.diameter) * this.diameter + Math.floorMod(chunkX, this.diameter);
      }

      protected void set(int index, @Nullable WorldChunk chunk) {
         WorldChunk lv = this.chunks.getAndSet(index, chunk);
         if (lv != null) {
            this.loadedChunkCount--;
            ClientChunkManager.this.world.unloadBlockEntities(lv);
         }

         if (chunk != null) {
            this.loadedChunkCount++;
         }
      }

      protected WorldChunk compareAndSet(int index, WorldChunk expect, @Nullable WorldChunk update) {
         if (this.chunks.compareAndSet(index, expect, update) && update == null) {
            this.loadedChunkCount--;
         }

         ClientChunkManager.this.world.unloadBlockEntities(expect);
         return expect;
      }

      private boolean isInRadius(int chunkX, int chunkZ) {
         return Math.abs(chunkX - this.centerChunkX) <= this.radius && Math.abs(chunkZ - this.centerChunkZ) <= this.radius;
      }

      @Nullable
      protected WorldChunk getChunk(int index) {
         return this.chunks.get(index);
      }
   }
}
