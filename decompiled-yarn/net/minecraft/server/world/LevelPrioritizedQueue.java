package net.minecraft.server.world;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.util.math.ChunkPos;

public class LevelPrioritizedQueue<T> {
   public static final int LEVEL_COUNT = ThreadedAnvilChunkStorage.MAX_LEVEL + 2;
   private final List<Long2ObjectLinkedOpenHashMap<List<Optional<T>>>> levelToPosToElements = IntStream.range(0, LEVEL_COUNT)
      .mapToObj(_snowman -> new Long2ObjectLinkedOpenHashMap())
      .collect(Collectors.toList());
   private volatile int firstNonEmptyLevel = LEVEL_COUNT;
   private final String name;
   private final LongSet blockingChunks = new LongOpenHashSet();
   private final int maxBlocking;

   public LevelPrioritizedQueue(String name, int maxSize) {
      this.name = name;
      this.maxBlocking = maxSize;
   }

   protected void updateLevel(int fromLevel, ChunkPos pos, int toLevel) {
      if (fromLevel < LEVEL_COUNT) {
         Long2ObjectLinkedOpenHashMap<List<Optional<T>>> _snowman = this.levelToPosToElements.get(fromLevel);
         List<Optional<T>> _snowmanx = (List<Optional<T>>)_snowman.remove(pos.toLong());
         if (fromLevel == this.firstNonEmptyLevel) {
            while (this.firstNonEmptyLevel < LEVEL_COUNT && this.levelToPosToElements.get(this.firstNonEmptyLevel).isEmpty()) {
               this.firstNonEmptyLevel++;
            }
         }

         if (_snowmanx != null && !_snowmanx.isEmpty()) {
            ((List)this.levelToPosToElements.get(toLevel).computeIfAbsent(pos.toLong(), _snowmanxx -> Lists.newArrayList())).addAll(_snowmanx);
            this.firstNonEmptyLevel = Math.min(this.firstNonEmptyLevel, toLevel);
         }
      }
   }

   protected void add(Optional<T> element, long pos, int level) {
      ((List)this.levelToPosToElements.get(level).computeIfAbsent(pos, _snowman -> Lists.newArrayList())).add(element);
      this.firstNonEmptyLevel = Math.min(this.firstNonEmptyLevel, level);
   }

   protected void remove(long pos, boolean removeElement) {
      for (Long2ObjectLinkedOpenHashMap<List<Optional<T>>> _snowman : this.levelToPosToElements) {
         List<Optional<T>> _snowmanx = (List<Optional<T>>)_snowman.get(pos);
         if (_snowmanx != null) {
            if (removeElement) {
               _snowmanx.clear();
            } else {
               _snowmanx.removeIf(_snowmanxx -> !_snowmanxx.isPresent());
            }

            if (_snowmanx.isEmpty()) {
               _snowman.remove(pos);
            }
         }
      }

      while (this.firstNonEmptyLevel < LEVEL_COUNT && this.levelToPosToElements.get(this.firstNonEmptyLevel).isEmpty()) {
         this.firstNonEmptyLevel++;
      }

      this.blockingChunks.remove(pos);
   }

   private Runnable createBlockingAdder(long pos) {
      return () -> this.blockingChunks.add(pos);
   }

   @Nullable
   public Stream<Either<T, Runnable>> poll() {
      if (this.blockingChunks.size() >= this.maxBlocking) {
         return null;
      } else if (this.firstNonEmptyLevel >= LEVEL_COUNT) {
         return null;
      } else {
         int _snowman = this.firstNonEmptyLevel;
         Long2ObjectLinkedOpenHashMap<List<Optional<T>>> _snowmanx = this.levelToPosToElements.get(_snowman);
         long _snowmanxx = _snowmanx.firstLongKey();
         List<Optional<T>> _snowmanxxx = (List<Optional<T>>)_snowmanx.removeFirst();

         while (this.firstNonEmptyLevel < LEVEL_COUNT && this.levelToPosToElements.get(this.firstNonEmptyLevel).isEmpty()) {
            this.firstNonEmptyLevel++;
         }

         return _snowmanxxx.stream().map(_snowmanxxxx -> _snowmanxxxx.map(Either::left).orElseGet(() -> Either.right(this.createBlockingAdder(_snowman))));
      }
   }

   @Override
   public String toString() {
      return this.name + " " + this.firstNonEmptyLevel + "...";
   }

   @VisibleForTesting
   LongSet getBlockingChunks() {
      return new LongOpenHashSet(this.blockingChunks);
   }
}
