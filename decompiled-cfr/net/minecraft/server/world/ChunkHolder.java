/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Either
 *  it.unimi.dsi.fastutil.shorts.ShortArraySet
 *  it.unimi.dsi.fastutil.shorts.ShortSet
 *  javax.annotation.Nullable
 */
package net.minecraft.server.world;

import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.shorts.ShortArraySet;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDeltaUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.LightUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.chunk.ReadOnlyChunk;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.chunk.light.LightingProvider;

public class ChunkHolder {
    public static final Either<Chunk, Unloaded> UNLOADED_CHUNK = Either.right((Object)Unloaded.INSTANCE);
    public static final CompletableFuture<Either<Chunk, Unloaded>> UNLOADED_CHUNK_FUTURE = CompletableFuture.completedFuture(UNLOADED_CHUNK);
    public static final Either<WorldChunk, Unloaded> UNLOADED_WORLD_CHUNK = Either.right((Object)Unloaded.INSTANCE);
    private static final CompletableFuture<Either<WorldChunk, Unloaded>> UNLOADED_WORLD_CHUNK_FUTURE = CompletableFuture.completedFuture(UNLOADED_WORLD_CHUNK);
    private static final List<ChunkStatus> CHUNK_STATUSES = ChunkStatus.createOrderedList();
    private static final LevelType[] LEVEL_TYPES = LevelType.values();
    private final AtomicReferenceArray<CompletableFuture<Either<Chunk, Unloaded>>> futuresByStatus = new AtomicReferenceArray(CHUNK_STATUSES.size());
    private volatile CompletableFuture<Either<WorldChunk, Unloaded>> accessibleFuture = UNLOADED_WORLD_CHUNK_FUTURE;
    private volatile CompletableFuture<Either<WorldChunk, Unloaded>> tickingFuture = UNLOADED_WORLD_CHUNK_FUTURE;
    private volatile CompletableFuture<Either<WorldChunk, Unloaded>> entityTickingFuture = UNLOADED_WORLD_CHUNK_FUTURE;
    private CompletableFuture<Chunk> savingFuture = CompletableFuture.completedFuture(null);
    private int lastTickLevel;
    private int level;
    private int completedLevel;
    private final ChunkPos pos;
    private boolean pendingBlockUpdates;
    private final ShortSet[] blockUpdatesBySection = new ShortSet[16];
    private int blockLightUpdateBits;
    private int skyLightUpdateBits;
    private final LightingProvider lightingProvider;
    private final LevelUpdateListener levelUpdateListener;
    private final PlayersWatchingChunkProvider playersWatchingChunkProvider;
    private boolean accessible;
    private boolean field_26744;

    public ChunkHolder(ChunkPos pos, int level, LightingProvider lightingProvider, LevelUpdateListener levelUpdateListener, PlayersWatchingChunkProvider playersWatchingChunkProvider) {
        this.pos = pos;
        this.lightingProvider = lightingProvider;
        this.levelUpdateListener = levelUpdateListener;
        this.playersWatchingChunkProvider = playersWatchingChunkProvider;
        this.level = this.lastTickLevel = ThreadedAnvilChunkStorage.MAX_LEVEL + 1;
        this.completedLevel = this.lastTickLevel;
        this.setLevel(level);
    }

    public CompletableFuture<Either<Chunk, Unloaded>> getFutureFor(ChunkStatus leastStatus) {
        CompletableFuture<Either<Chunk, Unloaded>> completableFuture = this.futuresByStatus.get(leastStatus.getIndex());
        return completableFuture == null ? UNLOADED_CHUNK_FUTURE : completableFuture;
    }

    public CompletableFuture<Either<Chunk, Unloaded>> getValidFutureFor(ChunkStatus leastStatus) {
        if (ChunkHolder.getTargetStatusForLevel(this.level).isAtLeast(leastStatus)) {
            return this.getFutureFor(leastStatus);
        }
        return UNLOADED_CHUNK_FUTURE;
    }

    public CompletableFuture<Either<WorldChunk, Unloaded>> getTickingFuture() {
        return this.tickingFuture;
    }

    public CompletableFuture<Either<WorldChunk, Unloaded>> getEntityTickingFuture() {
        return this.entityTickingFuture;
    }

    public CompletableFuture<Either<WorldChunk, Unloaded>> getAccessibleFuture() {
        return this.accessibleFuture;
    }

    @Nullable
    public WorldChunk getWorldChunk() {
        CompletableFuture<Either<WorldChunk, Unloaded>> completableFuture = this.getTickingFuture();
        Either _snowman2 = completableFuture.getNow(null);
        if (_snowman2 == null) {
            return null;
        }
        return _snowman2.left().orElse(null);
    }

    @Nullable
    public ChunkStatus getCurrentStatus() {
        for (int i = CHUNK_STATUSES.size() - 1; i >= 0; --i) {
            ChunkStatus chunkStatus = CHUNK_STATUSES.get(i);
            CompletableFuture<Either<Chunk, Unloaded>> _snowman2 = this.getFutureFor(chunkStatus);
            if (!_snowman2.getNow(UNLOADED_CHUNK).left().isPresent()) continue;
            return chunkStatus;
        }
        return null;
    }

    @Nullable
    public Chunk getCurrentChunk() {
        for (int i = CHUNK_STATUSES.size() - 1; i >= 0; --i) {
            ChunkStatus chunkStatus = CHUNK_STATUSES.get(i);
            CompletableFuture<Either<Chunk, Unloaded>> _snowman2 = this.getFutureFor(chunkStatus);
            if (_snowman2.isCompletedExceptionally() || !(_snowman = _snowman2.getNow(UNLOADED_CHUNK).left()).isPresent()) continue;
            return (Chunk)_snowman.get();
        }
        return null;
    }

    public CompletableFuture<Chunk> getSavingFuture() {
        return this.savingFuture;
    }

    public void markForBlockUpdate(BlockPos blockPos) {
        WorldChunk worldChunk = this.getWorldChunk();
        if (worldChunk == null) {
            return;
        }
        byte _snowman2 = (byte)ChunkSectionPos.getSectionCoord(blockPos.getY());
        if (this.blockUpdatesBySection[_snowman2] == null) {
            this.pendingBlockUpdates = true;
            this.blockUpdatesBySection[_snowman2] = new ShortArraySet();
        }
        this.blockUpdatesBySection[_snowman2].add(ChunkSectionPos.packLocal(blockPos));
    }

    public void markForLightUpdate(LightType type, int y) {
        WorldChunk worldChunk = this.getWorldChunk();
        if (worldChunk == null) {
            return;
        }
        worldChunk.setShouldSave(true);
        if (type == LightType.SKY) {
            this.skyLightUpdateBits |= 1 << y - -1;
        } else {
            this.blockLightUpdateBits |= 1 << y - -1;
        }
    }

    public void flushUpdates(WorldChunk chunk) {
        int n;
        if (!this.pendingBlockUpdates && this.skyLightUpdateBits == 0 && this.blockLightUpdateBits == 0) {
            return;
        }
        World world = chunk.getWorld();
        int _snowman2 = 0;
        for (n = 0; n < this.blockUpdatesBySection.length; ++n) {
            _snowman2 += this.blockUpdatesBySection[n] != null ? this.blockUpdatesBySection[n].size() : 0;
        }
        this.field_26744 |= _snowman2 >= 64;
        if (this.skyLightUpdateBits != 0 || this.blockLightUpdateBits != 0) {
            this.sendPacketToPlayersWatching(new LightUpdateS2CPacket(chunk.getPos(), this.lightingProvider, this.skyLightUpdateBits, this.blockLightUpdateBits, true), !this.field_26744);
            this.skyLightUpdateBits = 0;
            this.blockLightUpdateBits = 0;
        }
        for (n = 0; n < this.blockUpdatesBySection.length; ++n) {
            Object object;
            ShortSet shortSet = this.blockUpdatesBySection[n];
            if (shortSet == null) continue;
            ChunkSectionPos _snowman3 = ChunkSectionPos.from(chunk.getPos(), n);
            if (shortSet.size() == 1) {
                object = _snowman3.unpackBlockPos(shortSet.iterator().nextShort());
                _snowman = world.getBlockState((BlockPos)object);
                this.sendPacketToPlayersWatching(new BlockUpdateS2CPacket((BlockPos)object, (BlockState)_snowman), false);
                this.tryUpdateBlockEntityAt(world, (BlockPos)object, (BlockState)_snowman);
            } else {
                object = chunk.getSectionArray()[_snowman3.getY()];
                _snowman = new ChunkDeltaUpdateS2CPacket(_snowman3, shortSet, (ChunkSection)object, this.field_26744);
                this.sendPacketToPlayersWatching((Packet<?>)_snowman, false);
                ((ChunkDeltaUpdateS2CPacket)_snowman).visitUpdates((blockPos, blockState) -> this.tryUpdateBlockEntityAt(world, (BlockPos)blockPos, (BlockState)blockState));
            }
            this.blockUpdatesBySection[n] = null;
        }
        this.pendingBlockUpdates = false;
    }

    private void tryUpdateBlockEntityAt(World world, BlockPos blockPos, BlockState blockState) {
        if (blockState.getBlock().hasBlockEntity()) {
            this.sendBlockEntityUpdatePacket(world, blockPos);
        }
    }

    private void sendBlockEntityUpdatePacket(World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null && (_snowman = blockEntity.toUpdatePacket()) != null) {
            this.sendPacketToPlayersWatching(_snowman, false);
        }
    }

    private void sendPacketToPlayersWatching(Packet<?> packet, boolean onlyOnWatchDistanceEdge) {
        this.playersWatchingChunkProvider.getPlayersWatchingChunk(this.pos, onlyOnWatchDistanceEdge).forEach(serverPlayerEntity -> serverPlayerEntity.networkHandler.sendPacket(packet));
    }

    public CompletableFuture<Either<Chunk, Unloaded>> getChunkAt(ChunkStatus targetStatus, ThreadedAnvilChunkStorage chunkStorage) {
        int n = targetStatus.getIndex();
        CompletableFuture<Either<Chunk, Unloaded>> _snowman2 = this.futuresByStatus.get(n);
        if (_snowman2 != null && ((object = (Either)_snowman2.getNow(null)) == null || object.left().isPresent())) {
            return _snowman2;
        }
        if (ChunkHolder.getTargetStatusForLevel(this.level).isAtLeast(targetStatus)) {
            Object object = chunkStorage.getChunk(this, targetStatus);
            this.combineSavingFuture((CompletableFuture<? extends Either<? extends Chunk, Unloaded>>)object);
            this.futuresByStatus.set(n, (CompletableFuture<Either<Chunk, Unloaded>>)object);
            return object;
        }
        return _snowman2 == null ? UNLOADED_CHUNK_FUTURE : _snowman2;
    }

    private void combineSavingFuture(CompletableFuture<? extends Either<? extends Chunk, Unloaded>> then) {
        this.savingFuture = this.savingFuture.thenCombine(then, (chunk2, either) -> (Chunk)either.map(chunk -> chunk, unloaded -> chunk2));
    }

    public LevelType getLevelType() {
        return ChunkHolder.getLevelType(this.level);
    }

    public ChunkPos getPos() {
        return this.pos;
    }

    public int getLevel() {
        return this.level;
    }

    public int getCompletedLevel() {
        return this.completedLevel;
    }

    private void setCompletedLevel(int level) {
        this.completedLevel = level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    protected void tick(ThreadedAnvilChunkStorage chunkStorage) {
        CompletableFuture<Either<Chunk, Unloaded>> completableFuture;
        ChunkStatus chunkStatus = ChunkHolder.getTargetStatusForLevel(this.lastTickLevel);
        _snowman = ChunkHolder.getTargetStatusForLevel(this.level);
        boolean _snowman2 = this.lastTickLevel <= ThreadedAnvilChunkStorage.MAX_LEVEL;
        boolean _snowman3 = this.level <= ThreadedAnvilChunkStorage.MAX_LEVEL;
        LevelType _snowman4 = ChunkHolder.getLevelType(this.lastTickLevel);
        LevelType _snowman5 = ChunkHolder.getLevelType(this.level);
        if (_snowman2) {
            Either either2 = Either.right((Object)new Unloaded(this){
                final /* synthetic */ ChunkHolder field_17211;
                {
                    this.field_17211 = chunkHolder;
                }

                public String toString() {
                    return "Unloaded ticket level " + ChunkHolder.method_17206(this.field_17211).toString();
                }
            });
            int n = _snowman6 = _snowman3 ? _snowman.getIndex() + 1 : 0;
            while (_snowman6 <= chunkStatus.getIndex()) {
                completableFuture = this.futuresByStatus.get(_snowman6);
                if (completableFuture != null) {
                    completableFuture.complete((Either<Chunk, Unloaded>)either2);
                } else {
                    this.futuresByStatus.set(_snowman6, CompletableFuture.completedFuture(either2));
                }
                ++_snowman6;
            }
        }
        boolean bl = _snowman4.isAfter(LevelType.BORDER);
        int _snowman6 = _snowman5.isAfter(LevelType.BORDER);
        this.accessible |= _snowman6;
        if (!bl && _snowman6 != 0) {
            this.accessibleFuture = chunkStorage.makeChunkAccessible(this);
            this.combineSavingFuture(this.accessibleFuture);
        }
        if (bl && _snowman6 == 0) {
            completableFuture = this.accessibleFuture;
            this.accessibleFuture = UNLOADED_WORLD_CHUNK_FUTURE;
            this.combineSavingFuture((CompletableFuture<? extends Either<? extends Chunk, Unloaded>>)completableFuture.thenApply(either -> either.ifLeft(chunkStorage::enableTickSchedulers)));
        }
        boolean bl2 = _snowman4.isAfter(LevelType.TICKING);
        _snowman = _snowman5.isAfter(LevelType.TICKING);
        if (!bl2 && _snowman) {
            this.tickingFuture = chunkStorage.makeChunkTickable(this);
            this.combineSavingFuture(this.tickingFuture);
        }
        if (bl2 && !_snowman) {
            this.tickingFuture.complete(UNLOADED_WORLD_CHUNK);
            this.tickingFuture = UNLOADED_WORLD_CHUNK_FUTURE;
        }
        _snowman = _snowman4.isAfter(LevelType.ENTITY_TICKING);
        _snowman = _snowman5.isAfter(LevelType.ENTITY_TICKING);
        if (!_snowman && _snowman) {
            if (this.entityTickingFuture != UNLOADED_WORLD_CHUNK_FUTURE) {
                throw Util.throwOrPause(new IllegalStateException());
            }
            this.entityTickingFuture = chunkStorage.makeChunkEntitiesTickable(this.pos);
            this.combineSavingFuture(this.entityTickingFuture);
        }
        if (_snowman && !_snowman) {
            this.entityTickingFuture.complete(UNLOADED_WORLD_CHUNK);
            this.entityTickingFuture = UNLOADED_WORLD_CHUNK_FUTURE;
        }
        this.levelUpdateListener.updateLevel(this.pos, this::getCompletedLevel, this.level, this::setCompletedLevel);
        this.lastTickLevel = this.level;
    }

    public static ChunkStatus getTargetStatusForLevel(int level) {
        if (level < 33) {
            return ChunkStatus.FULL;
        }
        return ChunkStatus.byDistanceFromFull(level - 33);
    }

    public static LevelType getLevelType(int distance) {
        return LEVEL_TYPES[MathHelper.clamp(33 - distance + 1, 0, LEVEL_TYPES.length - 1)];
    }

    public boolean isAccessible() {
        return this.accessible;
    }

    public void updateAccessibleStatus() {
        this.accessible = ChunkHolder.getLevelType(this.level).isAfter(LevelType.BORDER);
    }

    public void setCompletedChunk(ReadOnlyChunk readOnlyChunk2) {
        ReadOnlyChunk readOnlyChunk2;
        for (int i = 0; i < this.futuresByStatus.length(); ++i) {
            CompletableFuture<Either<Chunk, Unloaded>> completableFuture = this.futuresByStatus.get(i);
            if (completableFuture == null || !(_snowman = completableFuture.getNow(UNLOADED_CHUNK).left()).isPresent() || !(_snowman.get() instanceof ProtoChunk)) continue;
            this.futuresByStatus.set(i, CompletableFuture.completedFuture(Either.left((Object)readOnlyChunk2)));
        }
        this.combineSavingFuture(CompletableFuture.completedFuture(Either.left((Object)readOnlyChunk2.getWrappedChunk())));
    }

    static /* synthetic */ ChunkPos method_17206(ChunkHolder chunkHolder) {
        return chunkHolder.pos;
    }

    public static interface PlayersWatchingChunkProvider {
        public Stream<ServerPlayerEntity> getPlayersWatchingChunk(ChunkPos var1, boolean var2);
    }

    public static interface LevelUpdateListener {
        public void updateLevel(ChunkPos var1, IntSupplier var2, int var3, IntConsumer var4);
    }

    public static interface Unloaded {
        public static final Unloaded INSTANCE = new Unloaded(){

            public String toString() {
                return "UNLOADED";
            }
        };
    }

    public static enum LevelType {
        INACCESSIBLE,
        BORDER,
        TICKING,
        ENTITY_TICKING;


        public boolean isAfter(LevelType levelType) {
            return this.ordinal() >= levelType.ordinal();
        }
    }
}

