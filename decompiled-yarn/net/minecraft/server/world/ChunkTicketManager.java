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
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.server.network.ServerPlayerEntity;
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
   private final ChunkTicketManager.TicketDistanceLevelPropagator distanceFromTicketTracker = new ChunkTicketManager.TicketDistanceLevelPropagator();
   private final ChunkTicketManager.DistanceFromNearestPlayerTracker distanceFromNearestPlayerTracker = new ChunkTicketManager.DistanceFromNearestPlayerTracker(
      8
   );
   private final ChunkTicketManager.NearbyChunkTicketUpdater nearbyChunkTicketUpdater = new ChunkTicketManager.NearbyChunkTicketUpdater(33);
   private final Set<ChunkHolder> chunkHolders = Sets.newHashSet();
   private final ChunkTaskPrioritySystem levelUpdateListener;
   private final MessageListener<ChunkTaskPrioritySystem.Task<Runnable>> playerTicketThrottler;
   private final MessageListener<ChunkTaskPrioritySystem.UnblockingMessage> playerTicketThrottlerUnblocker;
   private final LongSet chunkPositions = new LongOpenHashSet();
   private final Executor mainThreadExecutor;
   private long age;

   protected ChunkTicketManager(Executor workerExecutor, Executor mainThreadExecutor) {
      MessageListener<Runnable> _snowman = MessageListener.create("player ticket throttler", mainThreadExecutor::execute);
      ChunkTaskPrioritySystem _snowmanx = new ChunkTaskPrioritySystem(ImmutableList.of(_snowman), workerExecutor, 4);
      this.levelUpdateListener = _snowmanx;
      this.playerTicketThrottler = _snowmanx.createExecutor(_snowman, true);
      this.playerTicketThrottlerUnblocker = _snowmanx.createUnblockingExecutor(_snowman);
      this.mainThreadExecutor = mainThreadExecutor;
   }

   protected void purge() {
      this.age++;
      ObjectIterator<Entry<SortedArraySet<ChunkTicket<?>>>> _snowman = this.ticketsByPosition.long2ObjectEntrySet().fastIterator();

      while (_snowman.hasNext()) {
         Entry<SortedArraySet<ChunkTicket<?>>> _snowmanx = (Entry<SortedArraySet<ChunkTicket<?>>>)_snowman.next();
         if (((SortedArraySet)_snowmanx.getValue()).removeIf(_snowmanxx -> _snowmanxx.isExpired(this.age))) {
            this.distanceFromTicketTracker.updateLevel(_snowmanx.getLongKey(), getLevel((SortedArraySet<ChunkTicket<?>>)_snowmanx.getValue()), false);
         }

         if (((SortedArraySet)_snowmanx.getValue()).isEmpty()) {
            _snowman.remove();
         }
      }
   }

   private static int getLevel(SortedArraySet<ChunkTicket<?>> _snowman) {
      return !_snowman.isEmpty() ? _snowman.first().getLevel() : ThreadedAnvilChunkStorage.MAX_LEVEL + 1;
   }

   protected abstract boolean isUnloaded(long pos);

   @Nullable
   protected abstract ChunkHolder getChunkHolder(long pos);

   @Nullable
   protected abstract ChunkHolder setLevel(long pos, int level, @Nullable ChunkHolder holder, int var5);

   public boolean tick(ThreadedAnvilChunkStorage chunkStorage) {
      this.distanceFromNearestPlayerTracker.updateLevels();
      this.nearbyChunkTicketUpdater.updateLevels();
      int _snowman = Integer.MAX_VALUE - this.distanceFromTicketTracker.update(Integer.MAX_VALUE);
      boolean _snowmanx = _snowman != 0;
      if (_snowmanx) {
      }

      if (!this.chunkHolders.isEmpty()) {
         this.chunkHolders.forEach(_snowmanxx -> _snowmanxx.tick(chunkStorage));
         this.chunkHolders.clear();
         return true;
      } else {
         if (!this.chunkPositions.isEmpty()) {
            LongIterator _snowmanxx = this.chunkPositions.iterator();

            while (_snowmanxx.hasNext()) {
               long _snowmanxxx = _snowmanxx.nextLong();
               if (this.getTicketSet(_snowmanxxx).stream().anyMatch(_snowmanxxxx -> _snowmanxxxx.getType() == ChunkTicketType.PLAYER)) {
                  ChunkHolder _snowmanxxxx = chunkStorage.getCurrentChunkHolder(_snowmanxxx);
                  if (_snowmanxxxx == null) {
                     throw new IllegalStateException();
                  }

                  CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> _snowmanxxxxx = _snowmanxxxx.getEntityTickingFuture();
                  _snowmanxxxxx.thenAccept(
                     _snowmanxxxxxx -> this.mainThreadExecutor
                           .execute(() -> this.playerTicketThrottlerUnblocker.send(ChunkTaskPrioritySystem.createUnblockingMessage(() -> {
                              }, _snowman, false)))
                  );
               }
            }

            this.chunkPositions.clear();
         }

         return _snowmanx;
      }
   }

   private void addTicket(long position, ChunkTicket<?> ticket) {
      SortedArraySet<ChunkTicket<?>> _snowman = this.getTicketSet(position);
      int _snowmanx = getLevel(_snowman);
      ChunkTicket<?> _snowmanxx = _snowman.addAndGet(ticket);
      _snowmanxx.setTickCreated(this.age);
      if (ticket.getLevel() < _snowmanx) {
         this.distanceFromTicketTracker.updateLevel(position, ticket.getLevel(), true);
      }
   }

   private void removeTicket(long pos, ChunkTicket<?> ticket) {
      SortedArraySet<ChunkTicket<?>> _snowman = this.getTicketSet(pos);
      if (_snowman.remove(ticket)) {
      }

      if (_snowman.isEmpty()) {
         this.ticketsByPosition.remove(pos);
      }

      this.distanceFromTicketTracker.updateLevel(pos, getLevel(_snowman), false);
   }

   public <T> void addTicketWithLevel(ChunkTicketType<T> type, ChunkPos pos, int level, T argument) {
      this.addTicket(pos.toLong(), new ChunkTicket<>(type, level, argument));
   }

   public <T> void removeTicketWithLevel(ChunkTicketType<T> type, ChunkPos pos, int level, T argument) {
      ChunkTicket<T> _snowman = new ChunkTicket<>(type, level, argument);
      this.removeTicket(pos.toLong(), _snowman);
   }

   public <T> void addTicket(ChunkTicketType<T> type, ChunkPos pos, int radius, T argument) {
      this.addTicket(pos.toLong(), new ChunkTicket<>(type, 33 - radius, argument));
   }

   public <T> void removeTicket(ChunkTicketType<T> type, ChunkPos pos, int radius, T argument) {
      ChunkTicket<T> _snowman = new ChunkTicket<>(type, 33 - radius, argument);
      this.removeTicket(pos.toLong(), _snowman);
   }

   private SortedArraySet<ChunkTicket<?>> getTicketSet(long position) {
      return (SortedArraySet<ChunkTicket<?>>)this.ticketsByPosition.computeIfAbsent(position, _snowman -> SortedArraySet.create(4));
   }

   protected void setChunkForced(ChunkPos pos, boolean forced) {
      ChunkTicket<ChunkPos> _snowman = new ChunkTicket<>(ChunkTicketType.FORCED, 31, pos);
      if (forced) {
         this.addTicket(pos.toLong(), _snowman);
      } else {
         this.removeTicket(pos.toLong(), _snowman);
      }
   }

   public void handleChunkEnter(ChunkSectionPos pos, ServerPlayerEntity player) {
      long _snowman = pos.toChunkPos().toLong();
      ((ObjectSet)this.playersByChunkPos.computeIfAbsent(_snowman, _snowmanx -> new ObjectOpenHashSet())).add(player);
      this.distanceFromNearestPlayerTracker.updateLevel(_snowman, 0, true);
      this.nearbyChunkTicketUpdater.updateLevel(_snowman, 0, true);
   }

   public void handleChunkLeave(ChunkSectionPos pos, ServerPlayerEntity player) {
      long _snowman = pos.toChunkPos().toLong();
      ObjectSet<ServerPlayerEntity> _snowmanx = (ObjectSet<ServerPlayerEntity>)this.playersByChunkPos.get(_snowman);
      _snowmanx.remove(player);
      if (_snowmanx.isEmpty()) {
         this.playersByChunkPos.remove(_snowman);
         this.distanceFromNearestPlayerTracker.updateLevel(_snowman, Integer.MAX_VALUE, false);
         this.nearbyChunkTicketUpdater.updateLevel(_snowman, Integer.MAX_VALUE, false);
      }
   }

   protected String getTicket(long pos) {
      SortedArraySet<ChunkTicket<?>> _snowman = (SortedArraySet<ChunkTicket<?>>)this.ticketsByPosition.get(pos);
      String _snowmanx;
      if (_snowman != null && !_snowman.isEmpty()) {
         _snowmanx = _snowman.first().toString();
      } else {
         _snowmanx = "no_ticket";
      }

      return _snowmanx;
   }

   protected void setWatchDistance(int viewDistance) {
      this.nearbyChunkTicketUpdater.setWatchDistance(viewDistance);
   }

   public int getSpawningChunkCount() {
      this.distanceFromNearestPlayerTracker.updateLevels();
      return this.distanceFromNearestPlayerTracker.distanceFromNearestPlayer.size();
   }

   public boolean method_20800(long _snowman) {
      this.distanceFromNearestPlayerTracker.updateLevels();
      return this.distanceFromNearestPlayerTracker.distanceFromNearestPlayer.containsKey(_snowman);
   }

   public String toDumpString() {
      return this.levelUpdateListener.getDebugString();
   }

   class DistanceFromNearestPlayerTracker extends ChunkPosDistanceLevelPropagator {
      protected final Long2ByteMap distanceFromNearestPlayer = new Long2ByteOpenHashMap();
      protected final int maxDistance;

      protected DistanceFromNearestPlayerTracker(int _snowman) {
         super(_snowman + 2, 16, 256);
         this.maxDistance = _snowman;
         this.distanceFromNearestPlayer.defaultReturnValue((byte)(_snowman + 2));
      }

      @Override
      protected int getLevel(long id) {
         return this.distanceFromNearestPlayer.get(id);
      }

      @Override
      protected void setLevel(long id, int level) {
         byte _snowman;
         if (level > this.maxDistance) {
            _snowman = this.distanceFromNearestPlayer.remove(id);
         } else {
            _snowman = this.distanceFromNearestPlayer.put(id, (byte)level);
         }

         this.onDistanceChange(id, _snowman, level);
      }

      protected void onDistanceChange(long pos, int oldDistance, int distance) {
      }

      @Override
      protected int getInitialLevel(long id) {
         return this.isPlayerInChunk(id) ? 0 : Integer.MAX_VALUE;
      }

      private boolean isPlayerInChunk(long chunkPos) {
         ObjectSet<ServerPlayerEntity> _snowman = (ObjectSet<ServerPlayerEntity>)ChunkTicketManager.this.playersByChunkPos.get(chunkPos);
         return _snowman != null && !_snowman.isEmpty();
      }

      public void updateLevels() {
         this.applyPendingUpdates(Integer.MAX_VALUE);
      }
   }

   class NearbyChunkTicketUpdater extends ChunkTicketManager.DistanceFromNearestPlayerTracker {
      private int watchDistance;
      private final Long2IntMap distances = Long2IntMaps.synchronize(new Long2IntOpenHashMap());
      private final LongSet positionsAffected = new LongOpenHashSet();

      protected NearbyChunkTicketUpdater(int var2) {
         super(_snowman);
         this.watchDistance = 0;
         this.distances.defaultReturnValue(_snowman + 2);
      }

      @Override
      protected void onDistanceChange(long pos, int oldDistance, int distance) {
         this.positionsAffected.add(pos);
      }

      public void setWatchDistance(int watchDistance) {
         ObjectIterator var2 = this.distanceFromNearestPlayer.long2ByteEntrySet().iterator();

         while (var2.hasNext()) {
            it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry _snowman = (it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry)var2.next();
            byte _snowmanx = _snowman.getByteValue();
            long _snowmanxx = _snowman.getLongKey();
            this.updateTicket(_snowmanxx, _snowmanx, this.isWithinViewDistance(_snowmanx), _snowmanx <= watchDistance - 2);
         }

         this.watchDistance = watchDistance;
      }

      private void updateTicket(long pos, int distance, boolean oldWithinViewDistance, boolean withinViewDistance) {
         if (oldWithinViewDistance != withinViewDistance) {
            ChunkTicket<?> _snowman = new ChunkTicket<>(ChunkTicketType.PLAYER, ChunkTicketManager.NEARBY_PLAYER_TICKET_LEVEL, new ChunkPos(pos));
            if (withinViewDistance) {
               ChunkTicketManager.this.playerTicketThrottler
                  .send(ChunkTaskPrioritySystem.createMessage(() -> ChunkTicketManager.this.mainThreadExecutor.execute(() -> {
                        if (this.isWithinViewDistance(this.getLevel(pos))) {
                           ChunkTicketManager.this.addTicket(pos, _snowman);
                           ChunkTicketManager.this.chunkPositions.add(pos);
                        } else {
                           ChunkTicketManager.this.playerTicketThrottlerUnblocker.send(ChunkTaskPrioritySystem.createUnblockingMessage(() -> {
                           }, pos, false));
                        }
                     }), pos, () -> distance));
            } else {
               ChunkTicketManager.this.playerTicketThrottlerUnblocker
                  .send(
                     ChunkTaskPrioritySystem.createUnblockingMessage(
                        () -> ChunkTicketManager.this.mainThreadExecutor.execute(() -> ChunkTicketManager.this.removeTicket(pos, _snowman)), pos, true
                     )
                  );
            }
         }
      }

      @Override
      public void updateLevels() {
         super.updateLevels();
         if (!this.positionsAffected.isEmpty()) {
            LongIterator _snowman = this.positionsAffected.iterator();

            while (_snowman.hasNext()) {
               long _snowmanx = _snowman.nextLong();
               int _snowmanxx = this.distances.get(_snowmanx);
               int _snowmanxxx = this.getLevel(_snowmanx);
               if (_snowmanxx != _snowmanxxx) {
                  ChunkTicketManager.this.levelUpdateListener.updateLevel(new ChunkPos(_snowmanx), () -> this.distances.get(_snowman), _snowmanxxx, _snowmanxxxx -> {
                     if (_snowmanxxxx >= this.distances.defaultReturnValue()) {
                        this.distances.remove(_snowman);
                     } else {
                        this.distances.put(_snowman, _snowmanxxxx);
                     }
                  });
                  this.updateTicket(_snowmanx, _snowmanxxx, this.isWithinViewDistance(_snowmanxx), this.isWithinViewDistance(_snowmanxxx));
               }
            }

            this.positionsAffected.clear();
         }
      }

      private boolean isWithinViewDistance(int distance) {
         return distance <= this.watchDistance - 2;
      }
   }

   class TicketDistanceLevelPropagator extends ChunkPosDistanceLevelPropagator {
      public TicketDistanceLevelPropagator() {
         super(ThreadedAnvilChunkStorage.MAX_LEVEL + 2, 16, 256);
      }

      @Override
      protected int getInitialLevel(long id) {
         SortedArraySet<ChunkTicket<?>> _snowman = (SortedArraySet<ChunkTicket<?>>)ChunkTicketManager.this.ticketsByPosition.get(id);
         if (_snowman == null) {
            return Integer.MAX_VALUE;
         } else {
            return _snowman.isEmpty() ? Integer.MAX_VALUE : _snowman.first().getLevel();
         }
      }

      @Override
      protected int getLevel(long id) {
         if (!ChunkTicketManager.this.isUnloaded(id)) {
            ChunkHolder _snowman = ChunkTicketManager.this.getChunkHolder(id);
            if (_snowman != null) {
               return _snowman.getLevel();
            }
         }

         return ThreadedAnvilChunkStorage.MAX_LEVEL + 1;
      }

      @Override
      protected void setLevel(long id, int level) {
         ChunkHolder _snowman = ChunkTicketManager.this.getChunkHolder(id);
         int _snowmanx = _snowman == null ? ThreadedAnvilChunkStorage.MAX_LEVEL + 1 : _snowman.getLevel();
         if (_snowmanx != level) {
            _snowman = ChunkTicketManager.this.setLevel(id, level, _snowman, _snowmanx);
            if (_snowman != null) {
               ChunkTicketManager.this.chunkHolders.add(_snowman);
            }
         }
      }

      public int update(int distance) {
         return this.applyPendingUpdates(distance);
      }
   }
}
