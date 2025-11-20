/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.world;

import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeArray;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.chunk.light.LightingProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientChunkManager
extends ChunkManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private final WorldChunk emptyChunk;
    private final LightingProvider lightingProvider;
    private volatile ClientChunkMap chunks;
    private final ClientWorld world;

    public ClientChunkManager(ClientWorld world, int loadDistance) {
        this.world = world;
        this.emptyChunk = new EmptyChunk((World)world, new ChunkPos(0, 0));
        this.lightingProvider = new LightingProvider(this, true, world.getDimension().hasSkyLight());
        this.chunks = new ClientChunkMap(ClientChunkManager.getChunkMapRadius(loadDistance));
    }

    @Override
    public LightingProvider getLightingProvider() {
        return this.lightingProvider;
    }

    private static boolean positionEquals(@Nullable WorldChunk chunk, int x, int y) {
        if (chunk == null) {
            return false;
        }
        ChunkPos chunkPos = chunk.getPos();
        return chunkPos.x == x && chunkPos.z == y;
    }

    public void unload(int chunkX, int chunkZ) {
        if (!this.chunks.isInRadius(chunkX, chunkZ)) {
            return;
        }
        int n = this.chunks.getIndex(chunkX, chunkZ);
        WorldChunk _snowman2 = this.chunks.getChunk(n);
        if (ClientChunkManager.positionEquals(_snowman2, chunkX, chunkZ)) {
            this.chunks.compareAndSet(n, _snowman2, null);
        }
    }

    @Override
    @Nullable
    public WorldChunk getChunk(int n, int n2, ChunkStatus chunkStatus, boolean bl) {
        if (this.chunks.isInRadius(n, n2) && ClientChunkManager.positionEquals(_snowman = this.chunks.getChunk(this.chunks.getIndex(n, n2)), n, n2)) {
            return _snowman;
        }
        if (bl) {
            return this.emptyChunk;
        }
        return null;
    }

    @Override
    public BlockView getWorld() {
        return this.world;
    }

    @Nullable
    public WorldChunk loadChunkFromPacket(int x, int z, @Nullable BiomeArray biomes, PacketByteBuf buf, CompoundTag tag, int verticalStripBitmask, boolean complete) {
        if (!this.chunks.isInRadius(x, z)) {
            LOGGER.warn("Ignoring chunk since it's not in the view range: {}, {}", (Object)x, (Object)z);
            return null;
        }
        int n = this.chunks.getIndex(x, z);
        WorldChunk _snowman2 = (WorldChunk)this.chunks.chunks.get(n);
        if (complete || !ClientChunkManager.positionEquals(_snowman2, x, z)) {
            if (biomes == null) {
                LOGGER.warn("Ignoring chunk since we don't have complete data: {}, {}", (Object)x, (Object)z);
                return null;
            }
            _snowman2 = new WorldChunk(this.world, new ChunkPos(x, z), biomes);
            _snowman2.loadFromPacket(biomes, buf, tag, verticalStripBitmask);
            this.chunks.set(n, _snowman2);
        } else {
            _snowman2.loadFromPacket(biomes, buf, tag, verticalStripBitmask);
        }
        ChunkSection[] _snowman3 = _snowman2.getSectionArray();
        LightingProvider _snowman4 = this.getLightingProvider();
        _snowman4.setColumnEnabled(new ChunkPos(x, z), true);
        for (_snowman = 0; _snowman < _snowman3.length; ++_snowman) {
            ChunkSection chunkSection = _snowman3[_snowman];
            _snowman4.setSectionStatus(ChunkSectionPos.from(x, _snowman, z), ChunkSection.isEmpty(chunkSection));
        }
        this.world.resetChunkColor(x, z);
        return _snowman2;
    }

    public void tick(BooleanSupplier shouldKeepTicking) {
    }

    public void setChunkMapCenter(int x, int z) {
        this.chunks.centerChunkX = x;
        this.chunks.centerChunkZ = z;
    }

    public void updateLoadDistance(int loadDistance) {
        int n = this.chunks.radius;
        if (n != (_snowman = ClientChunkManager.getChunkMapRadius(loadDistance))) {
            ClientChunkMap clientChunkMap = new ClientChunkMap(_snowman);
            clientChunkMap.centerChunkX = this.chunks.centerChunkX;
            clientChunkMap.centerChunkZ = this.chunks.centerChunkZ;
            for (int i = 0; i < this.chunks.chunks.length(); ++i) {
                WorldChunk worldChunk = (WorldChunk)this.chunks.chunks.get(i);
                if (worldChunk == null) continue;
                ChunkPos _snowman2 = worldChunk.getPos();
                if (!clientChunkMap.isInRadius(_snowman2.x, _snowman2.z)) continue;
                clientChunkMap.set(clientChunkMap.getIndex(_snowman2.x, _snowman2.z), worldChunk);
            }
            this.chunks = clientChunkMap;
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

    @Override
    @Nullable
    public /* synthetic */ Chunk getChunk(int x, int z, ChunkStatus leastStatus, boolean create) {
        return this.getChunk(x, z, leastStatus, create);
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
            this.chunks = new AtomicReferenceArray(this.diameter * this.diameter);
        }

        private int getIndex(int chunkX, int chunkZ) {
            return Math.floorMod(chunkZ, this.diameter) * this.diameter + Math.floorMod(chunkX, this.diameter);
        }

        protected void set(int index, @Nullable WorldChunk chunk) {
            WorldChunk worldChunk = this.chunks.getAndSet(index, chunk);
            if (worldChunk != null) {
                --this.loadedChunkCount;
                ClientChunkManager.this.world.unloadBlockEntities(worldChunk);
            }
            if (chunk != null) {
                ++this.loadedChunkCount;
            }
        }

        protected WorldChunk compareAndSet(int index, WorldChunk expect, @Nullable WorldChunk update) {
            if (this.chunks.compareAndSet(index, expect, update) && update == null) {
                --this.loadedChunkCount;
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

