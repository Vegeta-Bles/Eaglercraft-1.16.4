/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.util.Either
 *  it.unimi.dsi.fastutil.longs.Long2ByteMap
 *  it.unimi.dsi.fastutil.longs.Long2ByteMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2IntMap
 *  it.unimi.dsi.fastutil.longs.Long2IntMaps
 *  it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.longs.LongIterator
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  it.unimi.dsi.fastutil.objects.ObjectIterator
 *  it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server.world;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntMaps;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.server.world.ChunkTaskPrioritySystem;
import net.minecraft.server.world.ChunkTicket;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.util.collection.SortedArraySet;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.thread.MessageListener;
import net.minecraft.world.ChunkPosDistanceLevelPropagator;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.WorldChunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ChunkTicketManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int NEARBY_PLAYER_TICKET_LEVEL = 33 + ChunkStatus.getDistanceFromFull(ChunkStatus.FULL) - 2;
    private final Long2ObjectMap<ObjectSet<ServerPlayerEntity>> playersByChunkPos = new Long2ObjectOpenHashMap();
    private final Long2ObjectOpenHashMap<SortedArraySet<ChunkTicket<?>>> ticketsByPosition = new Long2ObjectOpenHashMap();
    private final TicketDistanceLevelPropagator distanceFromTicketTracker = new TicketDistanceLevelPropagator();
    private final DistanceFromNearestPlayerTracker distanceFromNearestPlayerTracker = new DistanceFromNearestPlayerTracker(8);
    private final NearbyChunkTicketUpdater nearbyChunkTicketUpdater = new NearbyChunkTicketUpdater(33);
    private final Set<ChunkHolder> chunkHolders = Sets.newHashSet();
    private final ChunkTaskPrioritySystem levelUpdateListener;
    private final MessageListener<ChunkTaskPrioritySystem.Task<Runnable>> playerTicketThrottler;
    private final MessageListener<ChunkTaskPrioritySystem.UnblockingMessage> playerTicketThrottlerUnblocker;
    private final LongSet chunkPositions = new LongOpenHashSet();
    private final Executor mainThreadExecutor;
    private long age;

    protected ChunkTicketManager(Executor workerExecutor, Executor mainThreadExecutor) {
        MessageListener<Runnable> messageListener = MessageListener.create("player ticket throttler", mainThreadExecutor::execute);
        this.levelUpdateListener = _snowman = new ChunkTaskPrioritySystem((List<MessageListener<?>>)ImmutableList.of(messageListener), workerExecutor, 4);
        this.playerTicketThrottler = _snowman.createExecutor(messageListener, true);
        this.playerTicketThrottlerUnblocker = _snowman.createUnblockingExecutor(messageListener);
        this.mainThreadExecutor = mainThreadExecutor;
    }

    protected void purge() {
        ++this.age;
        ObjectIterator objectIterator = this.ticketsByPosition.long2ObjectEntrySet().fastIterator();
        while (objectIterator.hasNext()) {
            Long2ObjectMap.Entry entry = (Long2ObjectMap.Entry)objectIterator.next();
            if (((SortedArraySet)entry.getValue()).removeIf(chunkTicket -> chunkTicket.isExpired(this.age))) {
                this.distanceFromTicketTracker.updateLevel(entry.getLongKey(), ChunkTicketManager.getLevel((SortedArraySet)entry.getValue()), false);
            }
            if (!((SortedArraySet)entry.getValue()).isEmpty()) continue;
            objectIterator.remove();
        }
    }

    private static int getLevel(SortedArraySet<ChunkTicket<?>> sortedArraySet) {
        return !sortedArraySet.isEmpty() ? sortedArraySet.first().getLevel() : ThreadedAnvilChunkStorage.MAX_LEVEL + 1;
    }

    protected abstract boolean isUnloaded(long var1);

    @Nullable
    protected abstract ChunkHolder getChunkHolder(long var1);

    @Nullable
    protected abstract ChunkHolder setLevel(long var1, int var3, @Nullable ChunkHolder var4, int var5);

    public boolean tick(ThreadedAnvilChunkStorage chunkStorage) {
        this.distanceFromNearestPlayerTracker.updateLevels();
        this.nearbyChunkTicketUpdater.updateLevels();
        int n = Integer.MAX_VALUE - this.distanceFromTicketTracker.update(Integer.MAX_VALUE);
        boolean bl = _snowman = n != 0;
        if (_snowman) {
            // empty if block
        }
        if (!this.chunkHolders.isEmpty()) {
            this.chunkHolders.forEach(chunkHolder -> chunkHolder.tick(chunkStorage));
            this.chunkHolders.clear();
            return true;
        }
        if (!this.chunkPositions.isEmpty()) {
            LongIterator longIterator = this.chunkPositions.iterator();
            while (longIterator.hasNext()) {
                long l = longIterator.nextLong();
                if (!this.getTicketSet(l).stream().anyMatch(chunkTicket -> chunkTicket.getType() == ChunkTicketType.PLAYER)) continue;
                ChunkHolder _snowman2 = chunkStorage.getCurrentChunkHolder(l);
                if (_snowman2 == null) {
                    throw new IllegalStateException();
                }
                CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> _snowman3 = _snowman2.getEntityTickingFuture();
                _snowman3.thenAccept(either -> this.mainThreadExecutor.execute(() -> this.playerTicketThrottlerUnblocker.send(ChunkTaskPrioritySystem.createUnblockingMessage(() -> {}, l, false))));
            }
            this.chunkPositions.clear();
        }
        return _snowman;
    }

    private void addTicket(long position, ChunkTicket<?> ticket) {
        SortedArraySet<ChunkTicket<?>> sortedArraySet = this.getTicketSet(position);
        int _snowman2 = ChunkTicketManager.getLevel(sortedArraySet);
        ChunkTicket<?> _snowman3 = sortedArraySet.addAndGet(ticket);
        _snowman3.setTickCreated(this.age);
        if (ticket.getLevel() < _snowman2) {
            this.distanceFromTicketTracker.updateLevel(position, ticket.getLevel(), true);
        }
    }

    private void removeTicket(long pos, ChunkTicket<?> ticket) {
        SortedArraySet<ChunkTicket<?>> sortedArraySet = this.getTicketSet(pos);
        if (sortedArraySet.remove(ticket)) {
            // empty if block
        }
        if (sortedArraySet.isEmpty()) {
            this.ticketsByPosition.remove(pos);
        }
        this.distanceFromTicketTracker.updateLevel(pos, ChunkTicketManager.getLevel(sortedArraySet), false);
    }

    public <T> void addTicketWithLevel(ChunkTicketType<T> type, ChunkPos pos, int level, T argument) {
        this.addTicket(pos.toLong(), new ChunkTicket<T>(type, level, argument));
    }

    public <T> void removeTicketWithLevel(ChunkTicketType<T> type, ChunkPos pos, int level, T argument) {
        ChunkTicket<T> chunkTicket = new ChunkTicket<T>(type, level, argument);
        this.removeTicket(pos.toLong(), chunkTicket);
    }

    public <T> void addTicket(ChunkTicketType<T> type, ChunkPos pos, int radius, T argument) {
        this.addTicket(pos.toLong(), new ChunkTicket<T>(type, 33 - radius, argument));
    }

    public <T> void removeTicket(ChunkTicketType<T> type, ChunkPos pos, int radius, T argument) {
        ChunkTicket<T> chunkTicket = new ChunkTicket<T>(type, 33 - radius, argument);
        this.removeTicket(pos.toLong(), chunkTicket);
    }

    private SortedArraySet<ChunkTicket<?>> getTicketSet(long position) {
        return (SortedArraySet)this.ticketsByPosition.computeIfAbsent(position, l -> SortedArraySet.create(4));
    }

    protected void setChunkForced(ChunkPos pos, boolean forced) {
        ChunkTicket<ChunkPos> chunkTicket = new ChunkTicket<ChunkPos>(ChunkTicketType.FORCED, 31, pos);
        if (forced) {
            this.addTicket(pos.toLong(), chunkTicket);
        } else {
            this.removeTicket(pos.toLong(), chunkTicket);
        }
    }

    public void handleChunkEnter(ChunkSectionPos pos, ServerPlayerEntity player) {
        long l2 = pos.toChunkPos().toLong();
        ((ObjectSet)this.playersByChunkPos.computeIfAbsent(l2, l -> new ObjectOpenHashSet())).add((Object)player);
        this.distanceFromNearestPlayerTracker.updateLevel(l2, 0, true);
        this.nearbyChunkTicketUpdater.updateLevel(l2, 0, true);
    }

    public void handleChunkLeave(ChunkSectionPos pos, ServerPlayerEntity player) {
        long l = pos.toChunkPos().toLong();
        ObjectSet _snowman2 = (ObjectSet)this.playersByChunkPos.get(l);
        _snowman2.remove((Object)player);
        if (_snowman2.isEmpty()) {
            this.playersByChunkPos.remove(l);
            this.distanceFromNearestPlayerTracker.updateLevel(l, Integer.MAX_VALUE, false);
            this.nearbyChunkTicketUpdater.updateLevel(l, Integer.MAX_VALUE, false);
        }
    }

    protected String getTicket(long pos) {
        SortedArraySet sortedArraySet = (SortedArraySet)this.ticketsByPosition.get(pos);
        String _snowman2 = sortedArraySet == null || sortedArraySet.isEmpty() ? "no_ticket" : ((ChunkTicket)sortedArraySet.first()).toString();
        return _snowman2;
    }

    protected void setWatchDistance(int viewDistance) {
        this.nearbyChunkTicketUpdater.setWatchDistance(viewDistance);
    }

    public int getSpawningChunkCount() {
        this.distanceFromNearestPlayerTracker.updateLevels();
        return this.distanceFromNearestPlayerTracker.distanceFromNearestPlayer.size();
    }

    public boolean method_20800(long l) {
        this.distanceFromNearestPlayerTracker.updateLevels();
        return this.distanceFromNearestPlayerTracker.distanceFromNearestPlayer.containsKey(l);
    }

    public String toDumpString() {
        return this.levelUpdateListener.getDebugString();
    }

    class TicketDistanceLevelPropagator
    extends ChunkPosDistanceLevelPropagator {
        public TicketDistanceLevelPropagator() {
            super(ThreadedAnvilChunkStorage.MAX_LEVEL + 2, 16, 256);
        }

        @Override
        protected int getInitialLevel(long id) {
            SortedArraySet sortedArraySet = (SortedArraySet)ChunkTicketManager.this.ticketsByPosition.get(id);
            if (sortedArraySet == null) {
                return Integer.MAX_VALUE;
            }
            if (sortedArraySet.isEmpty()) {
                return Integer.MAX_VALUE;
            }
            return ((ChunkTicket)sortedArraySet.first()).getLevel();
        }

        @Override
        protected int getLevel(long id) {
            ChunkHolder chunkHolder;
            if (!ChunkTicketManager.this.isUnloaded(id) && (chunkHolder = ChunkTicketManager.this.getChunkHolder(id)) != null) {
                return chunkHolder.getLevel();
            }
            return ThreadedAnvilChunkStorage.MAX_LEVEL + 1;
        }

        @Override
        protected void setLevel(long id, int level) {
            ChunkHolder chunkHolder = ChunkTicketManager.this.getChunkHolder(id);
            int n = _snowman = chunkHolder == null ? ThreadedAnvilChunkStorage.MAX_LEVEL + 1 : chunkHolder.getLevel();
            if (_snowman == level) {
                return;
            }
            if ((chunkHolder = ChunkTicketManager.this.setLevel(id, level, chunkHolder, _snowman)) != null) {
                ChunkTicketManager.this.chunkHolders.add(chunkHolder);
            }
        }

        public int update(int distance) {
            return this.applyPendingUpdates(distance);
        }
    }

    class NearbyChunkTicketUpdater
    extends DistanceFromNearestPlayerTracker {
        private int watchDistance;
        private final Long2IntMap distances;
        private final LongSet positionsAffected;

        protected NearbyChunkTicketUpdater(int n) {
            super(n);
            this.distances = Long2IntMaps.synchronize((Long2IntMap)new Long2IntOpenHashMap());
            this.positionsAffected = new LongOpenHashSet();
            this.watchDistance = 0;
            this.distances.defaultReturnValue(n + 2);
        }

        @Override
        protected void onDistanceChange(long pos, int oldDistance, int distance) {
            this.positionsAffected.add(pos);
        }

        public void setWatchDistance(int watchDistance) {
            for (Long2ByteMap.Entry entry : this.distanceFromNearestPlayer.long2ByteEntrySet()) {
                byte by = entry.getByteValue();
                long _snowman2 = entry.getLongKey();
                this.updateTicket(_snowman2, by, this.isWithinViewDistance(by), by <= watchDistance - 2);
            }
            this.watchDistance = watchDistance;
        }

        private void updateTicket(long pos, int distance, boolean oldWithinViewDistance, boolean withinViewDistance) {
            if (oldWithinViewDistance != withinViewDistance) {
                ChunkTicket<ChunkPos> chunkTicket = new ChunkTicket<ChunkPos>(ChunkTicketType.PLAYER, NEARBY_PLAYER_TICKET_LEVEL, new ChunkPos(pos));
                if (withinViewDistance) {
                    ChunkTicketManager.this.playerTicketThrottler.send(ChunkTaskPrioritySystem.createMessage(() -> ChunkTicketManager.this.mainThreadExecutor.execute(() -> {
                        if (this.isWithinViewDistance(this.getLevel(pos))) {
                            ChunkTicketManager.this.addTicket(pos, chunkTicket);
                            ChunkTicketManager.this.chunkPositions.add(pos);
                        } else {
                            ChunkTicketManager.this.playerTicketThrottlerUnblocker.send(ChunkTaskPrioritySystem.createUnblockingMessage(() -> {}, pos, false));
                        }
                    }), pos, () -> distance));
                } else {
                    ChunkTicketManager.this.playerTicketThrottlerUnblocker.send(ChunkTaskPrioritySystem.createUnblockingMessage(() -> ChunkTicketManager.this.mainThreadExecutor.execute(() -> ChunkTicketManager.this.removeTicket(pos, chunkTicket)), pos, true));
                }
            }
        }

        @Override
        public void updateLevels() {
            super.updateLevels();
            if (!this.positionsAffected.isEmpty()) {
                LongIterator longIterator = this.positionsAffected.iterator();
                while (longIterator.hasNext()) {
                    long l = longIterator.nextLong();
                    int _snowman2 = this.distances.get(l);
                    if (_snowman2 == (_snowman = this.getLevel(l))) continue;
                    ChunkTicketManager.this.levelUpdateListener.updateLevel(new ChunkPos(l), () -> this.distances.get(l), _snowman, n -> {
                        if (n >= this.distances.defaultReturnValue()) {
                            this.distances.remove(l);
                        } else {
                            this.distances.put(l, n);
                        }
                    });
                    this.updateTicket(l, _snowman, this.isWithinViewDistance(_snowman2), this.isWithinViewDistance(_snowman));
                }
                this.positionsAffected.clear();
            }
        }

        private boolean isWithinViewDistance(int distance) {
            return distance <= this.watchDistance - 2;
        }
    }

    class DistanceFromNearestPlayerTracker
    extends ChunkPosDistanceLevelPropagator {
        protected final Long2ByteMap distanceFromNearestPlayer;
        protected final int maxDistance;

        protected DistanceFromNearestPlayerTracker(int n) {
            super(n + 2, 16, 256);
            this.distanceFromNearestPlayer = new Long2ByteOpenHashMap();
            this.maxDistance = n;
            this.distanceFromNearestPlayer.defaultReturnValue((byte)(n + 2));
        }

        @Override
        protected int getLevel(long id) {
            return this.distanceFromNearestPlayer.get(id);
        }

        @Override
        protected void setLevel(long id, int level) {
            byte by = level > this.maxDistance ? this.distanceFromNearestPlayer.remove(id) : this.distanceFromNearestPlayer.put(id, (byte)level);
            this.onDistanceChange(id, by, level);
        }

        protected void onDistanceChange(long pos, int oldDistance, int distance) {
        }

        @Override
        protected int getInitialLevel(long id) {
            return this.isPlayerInChunk(id) ? 0 : Integer.MAX_VALUE;
        }

        private boolean isPlayerInChunk(long chunkPos) {
            ObjectSet objectSet = (ObjectSet)ChunkTicketManager.this.playersByChunkPos.get(chunkPos);
            return objectSet != null && !objectSet.isEmpty();
        }

        public void updateLevels() {
            this.applyPendingUpdates(Integer.MAX_VALUE);
        }
    }
}

