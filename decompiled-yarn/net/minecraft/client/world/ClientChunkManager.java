package net.minecraft.client.world;

import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
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
         ChunkPos _snowman = chunk.getPos();
         return _snowman.x == x && _snowman.z == y;
      }
   }

   public void unload(int chunkX, int chunkZ) {
      if (this.chunks.isInRadius(chunkX, chunkZ)) {
         int _snowman = this.chunks.getIndex(chunkX, chunkZ);
         WorldChunk _snowmanx = this.chunks.getChunk(_snowman);
         if (positionEquals(_snowmanx, chunkX, chunkZ)) {
            this.chunks.compareAndSet(_snowman, _snowmanx, null);
         }
      }
   }

   @Nullable
   public WorldChunk getChunk(int _snowman, int _snowman, ChunkStatus _snowman, boolean _snowman) {
      if (this.chunks.isInRadius(_snowman, _snowman)) {
         WorldChunk _snowmanxxxx = this.chunks.getChunk(this.chunks.getIndex(_snowman, _snowman));
         if (positionEquals(_snowmanxxxx, _snowman, _snowman)) {
            return _snowmanxxxx;
         }
      }

      return _snowman ? this.emptyChunk : null;
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
         int _snowman = this.chunks.getIndex(x, z);
         WorldChunk _snowmanx = this.chunks.chunks.get(_snowman);
         if (!complete && positionEquals(_snowmanx, x, z)) {
            _snowmanx.loadFromPacket(biomes, buf, tag, verticalStripBitmask);
         } else {
            if (biomes == null) {
               LOGGER.warn("Ignoring chunk since we don't have complete data: {}, {}", x, z);
               return null;
            }

            _snowmanx = new WorldChunk(this.world, new ChunkPos(x, z), biomes);
            _snowmanx.loadFromPacket(biomes, buf, tag, verticalStripBitmask);
            this.chunks.set(_snowman, _snowmanx);
         }

         ChunkSection[] _snowmanxx = _snowmanx.getSectionArray();
         LightingProvider _snowmanxxx = this.getLightingProvider();
         _snowmanxxx.setColumnEnabled(new ChunkPos(x, z), true);

         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxx.length; _snowmanxxxx++) {
            ChunkSection _snowmanxxxxx = _snowmanxx[_snowmanxxxx];
            _snowmanxxx.setSectionStatus(ChunkSectionPos.from(x, _snowmanxxxx, z), ChunkSection.isEmpty(_snowmanxxxxx));
         }

         this.world.resetChunkColor(x, z);
         return _snowmanx;
      }
   }

   public void tick(BooleanSupplier shouldKeepTicking) {
   }

   public void setChunkMapCenter(int x, int z) {
      this.chunks.centerChunkX = x;
      this.chunks.centerChunkZ = z;
   }

   public void updateLoadDistance(int loadDistance) {
      int _snowman = this.chunks.radius;
      int _snowmanx = getChunkMapRadius(loadDistance);
      if (_snowman != _snowmanx) {
         ClientChunkManager.ClientChunkMap _snowmanxx = new ClientChunkManager.ClientChunkMap(_snowmanx);
         _snowmanxx.centerChunkX = this.chunks.centerChunkX;
         _snowmanxx.centerChunkZ = this.chunks.centerChunkZ;

         for (int _snowmanxxx = 0; _snowmanxxx < this.chunks.chunks.length(); _snowmanxxx++) {
            WorldChunk _snowmanxxxx = this.chunks.chunks.get(_snowmanxxx);
            if (_snowmanxxxx != null) {
               ChunkPos _snowmanxxxxx = _snowmanxxxx.getPos();
               if (_snowmanxx.isInRadius(_snowmanxxxxx.x, _snowmanxxxxx.z)) {
                  _snowmanxx.set(_snowmanxx.getIndex(_snowmanxxxxx.x, _snowmanxxxxx.z), _snowmanxxxx);
               }
            }
         }

         this.chunks = _snowmanxx;
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
         WorldChunk _snowman = this.chunks.getAndSet(index, chunk);
         if (_snowman != null) {
            this.loadedChunkCount--;
            ClientChunkManager.this.world.unloadBlockEntities(_snowman);
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
