package net.minecraft.world.chunk.light;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.LightType;
import net.minecraft.world.SectionDistanceLevelPropagator;
import net.minecraft.world.chunk.ChunkNibbleArray;
import net.minecraft.world.chunk.ChunkProvider;
import net.minecraft.world.chunk.ChunkToNibbleArrayMap;

public abstract class LightStorage<M extends ChunkToNibbleArrayMap<M>> extends SectionDistanceLevelPropagator {
   protected static final ChunkNibbleArray EMPTY = new ChunkNibbleArray();
   private static final Direction[] DIRECTIONS = Direction.values();
   private final LightType lightType;
   private final ChunkProvider chunkProvider;
   protected final LongSet readySections = new LongOpenHashSet();
   protected final LongSet markedNotReadySections = new LongOpenHashSet();
   protected final LongSet markedReadySections = new LongOpenHashSet();
   protected volatile M uncachedStorage;
   protected final M storage;
   protected final LongSet dirtySections = new LongOpenHashSet();
   protected final LongSet notifySections = new LongOpenHashSet();
   protected final Long2ObjectMap<ChunkNibbleArray> queuedSections = Long2ObjectMaps.synchronize(new Long2ObjectOpenHashMap());
   private final LongSet queuedEdgeSections = new LongOpenHashSet();
   private final LongSet columnsToRetain = new LongOpenHashSet();
   private final LongSet sectionsToRemove = new LongOpenHashSet();
   protected volatile boolean hasLightUpdates;

   protected LightStorage(LightType lightType, ChunkProvider chunkProvider, M lightData) {
      super(3, 16, 256);
      this.lightType = lightType;
      this.chunkProvider = chunkProvider;
      this.storage = lightData;
      this.uncachedStorage = lightData.copy();
      this.uncachedStorage.disableCache();
   }

   protected boolean hasSection(long sectionPos) {
      return this.getLightSection(sectionPos, true) != null;
   }

   @Nullable
   protected ChunkNibbleArray getLightSection(long sectionPos, boolean cached) {
      return this.getLightSection(cached ? this.storage : this.uncachedStorage, sectionPos);
   }

   @Nullable
   protected ChunkNibbleArray getLightSection(M storage, long sectionPos) {
      return storage.get(sectionPos);
   }

   @Nullable
   public ChunkNibbleArray getLightSection(long sectionPos) {
      ChunkNibbleArray lv = (ChunkNibbleArray)this.queuedSections.get(sectionPos);
      return lv != null ? lv : this.getLightSection(sectionPos, false);
   }

   protected abstract int getLight(long blockPos);

   protected int get(long blockPos) {
      long m = ChunkSectionPos.fromBlockPos(blockPos);
      ChunkNibbleArray lv = this.getLightSection(m, true);
      return lv.get(
         ChunkSectionPos.getLocalCoord(BlockPos.unpackLongX(blockPos)),
         ChunkSectionPos.getLocalCoord(BlockPos.unpackLongY(blockPos)),
         ChunkSectionPos.getLocalCoord(BlockPos.unpackLongZ(blockPos))
      );
   }

   protected void set(long blockPos, int value) {
      long m = ChunkSectionPos.fromBlockPos(blockPos);
      if (this.dirtySections.add(m)) {
         this.storage.replaceWithCopy(m);
      }

      ChunkNibbleArray lv = this.getLightSection(m, true);
      lv.set(
         ChunkSectionPos.getLocalCoord(BlockPos.unpackLongX(blockPos)),
         ChunkSectionPos.getLocalCoord(BlockPos.unpackLongY(blockPos)),
         ChunkSectionPos.getLocalCoord(BlockPos.unpackLongZ(blockPos)),
         value
      );

      for (int j = -1; j <= 1; j++) {
         for (int k = -1; k <= 1; k++) {
            for (int n = -1; n <= 1; n++) {
               this.notifySections.add(ChunkSectionPos.fromBlockPos(BlockPos.add(blockPos, k, n, j)));
            }
         }
      }
   }

   @Override
   protected int getLevel(long id) {
      if (id == Long.MAX_VALUE) {
         return 2;
      } else if (this.readySections.contains(id)) {
         return 0;
      } else {
         return !this.sectionsToRemove.contains(id) && this.storage.containsKey(id) ? 1 : 2;
      }
   }

   @Override
   protected int getInitialLevel(long id) {
      if (this.markedNotReadySections.contains(id)) {
         return 2;
      } else {
         return !this.readySections.contains(id) && !this.markedReadySections.contains(id) ? 2 : 0;
      }
   }

   @Override
   protected void setLevel(long id, int level) {
      int j = this.getLevel(id);
      if (j != 0 && level == 0) {
         this.readySections.add(id);
         this.markedReadySections.remove(id);
      }

      if (j == 0 && level != 0) {
         this.readySections.remove(id);
         this.markedNotReadySections.remove(id);
      }

      if (j >= 2 && level != 2) {
         if (this.sectionsToRemove.contains(id)) {
            this.sectionsToRemove.remove(id);
         } else {
            this.storage.put(id, this.createSection(id));
            this.dirtySections.add(id);
            this.onLoadSection(id);

            for (int k = -1; k <= 1; k++) {
               for (int m = -1; m <= 1; m++) {
                  for (int n = -1; n <= 1; n++) {
                     this.notifySections.add(ChunkSectionPos.fromBlockPos(BlockPos.add(id, m, n, k)));
                  }
               }
            }
         }
      }

      if (j != 2 && level >= 2) {
         this.sectionsToRemove.add(id);
      }

      this.hasLightUpdates = !this.sectionsToRemove.isEmpty();
   }

   protected ChunkNibbleArray createSection(long sectionPos) {
      ChunkNibbleArray lv = (ChunkNibbleArray)this.queuedSections.get(sectionPos);
      return lv != null ? lv : new ChunkNibbleArray();
   }

   protected void removeSection(ChunkLightProvider<?, ?> storage, long sectionPos) {
      if (storage.getPendingUpdateCount() < 8192) {
         storage.removePendingUpdateIf(mx -> ChunkSectionPos.fromBlockPos(mx) == sectionPos);
      } else {
         int i = ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackX(sectionPos));
         int j = ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackY(sectionPos));
         int k = ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackZ(sectionPos));

         for (int m = 0; m < 16; m++) {
            for (int n = 0; n < 16; n++) {
               for (int o = 0; o < 16; o++) {
                  long p = BlockPos.asLong(i + m, j + n, k + o);
                  storage.removePendingUpdate(p);
               }
            }
         }
      }
   }

   protected boolean hasLightUpdates() {
      return this.hasLightUpdates;
   }

   protected void updateLight(ChunkLightProvider<M, ?> lightProvider, boolean doSkylight, boolean skipEdgeLightPropagation) {
      if (this.hasLightUpdates() || !this.queuedSections.isEmpty()) {
         LongIterator objectIterator = this.sectionsToRemove.iterator();

         while (objectIterator.hasNext()) {
            long l = (Long)objectIterator.next();
            this.removeSection(lightProvider, l);
            ChunkNibbleArray lv = (ChunkNibbleArray)this.queuedSections.remove(l);
            ChunkNibbleArray lv2 = this.storage.removeChunk(l);
            if (this.columnsToRetain.contains(ChunkSectionPos.withZeroY(l))) {
               if (lv != null) {
                  this.queuedSections.put(l, lv);
               } else if (lv2 != null) {
                  this.queuedSections.put(l, lv2);
               }
            }
         }

         this.storage.clearCache();
         objectIterator = this.sectionsToRemove.iterator();

         while (objectIterator.hasNext()) {
            long m = (Long)objectIterator.next();
            this.onUnloadSection(m);
         }

         this.sectionsToRemove.clear();
         this.hasLightUpdates = false;
         ObjectIterator var10 = this.queuedSections.long2ObjectEntrySet().iterator();

         while (var10.hasNext()) {
            Entry<ChunkNibbleArray> entry = (Entry<ChunkNibbleArray>)var10.next();
            long n = entry.getLongKey();
            if (this.hasSection(n)) {
               ChunkNibbleArray lv3 = (ChunkNibbleArray)entry.getValue();
               if (this.storage.get(n) != lv3) {
                  this.removeSection(lightProvider, n);
                  this.storage.put(n, lv3);
                  this.dirtySections.add(n);
               }
            }
         }

         this.storage.clearCache();
         if (!skipEdgeLightPropagation) {
            objectIterator = this.queuedSections.keySet().iterator();

            while (objectIterator.hasNext()) {
               long o = (Long)objectIterator.next();
               this.updateSection(lightProvider, o);
            }
         } else {
            objectIterator = this.queuedEdgeSections.iterator();

            while (objectIterator.hasNext()) {
               long p = (Long)objectIterator.next();
               this.updateSection(lightProvider, p);
            }
         }

         this.queuedEdgeSections.clear();
         ObjectIterator<Entry<ChunkNibbleArray>> objectIteratorx = this.queuedSections.long2ObjectEntrySet().iterator();

         while (objectIteratorx.hasNext()) {
            Entry<ChunkNibbleArray> entry2 = (Entry<ChunkNibbleArray>)objectIteratorx.next();
            long q = entry2.getLongKey();
            if (this.hasSection(q)) {
               objectIteratorx.remove();
            }
         }
      }
   }

   private void updateSection(ChunkLightProvider<M, ?> lightProvider, long sectionPos) {
      if (this.hasSection(sectionPos)) {
         int i = ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackX(sectionPos));
         int j = ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackY(sectionPos));
         int k = ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackZ(sectionPos));

         for (Direction lv : DIRECTIONS) {
            long m = ChunkSectionPos.offset(sectionPos, lv);
            if (!this.queuedSections.containsKey(m) && this.hasSection(m)) {
               for (int n = 0; n < 16; n++) {
                  for (int o = 0; o < 16; o++) {
                     long p;
                     long q;
                     switch (lv) {
                        case DOWN:
                           p = BlockPos.asLong(i + o, j, k + n);
                           q = BlockPos.asLong(i + o, j - 1, k + n);
                           break;
                        case UP:
                           p = BlockPos.asLong(i + o, j + 16 - 1, k + n);
                           q = BlockPos.asLong(i + o, j + 16, k + n);
                           break;
                        case NORTH:
                           p = BlockPos.asLong(i + n, j + o, k);
                           q = BlockPos.asLong(i + n, j + o, k - 1);
                           break;
                        case SOUTH:
                           p = BlockPos.asLong(i + n, j + o, k + 16 - 1);
                           q = BlockPos.asLong(i + n, j + o, k + 16);
                           break;
                        case WEST:
                           p = BlockPos.asLong(i, j + n, k + o);
                           q = BlockPos.asLong(i - 1, j + n, k + o);
                           break;
                        default:
                           p = BlockPos.asLong(i + 16 - 1, j + n, k + o);
                           q = BlockPos.asLong(i + 16, j + n, k + o);
                     }

                     lightProvider.updateLevel(p, q, lightProvider.getPropagatedLevel(p, q, lightProvider.getLevel(p)), false);
                     lightProvider.updateLevel(q, p, lightProvider.getPropagatedLevel(q, p, lightProvider.getLevel(q)), false);
                  }
               }
            }
         }
      }
   }

   protected void onLoadSection(long sectionPos) {
   }

   protected void onUnloadSection(long sectionPos) {
   }

   protected void setColumnEnabled(long columnPos, boolean enabled) {
   }

   public void setRetainColumn(long sectionPos, boolean retain) {
      if (retain) {
         this.columnsToRetain.add(sectionPos);
      } else {
         this.columnsToRetain.remove(sectionPos);
      }
   }

   protected void enqueueSectionData(long sectionPos, @Nullable ChunkNibbleArray array, boolean bl) {
      if (array != null) {
         this.queuedSections.put(sectionPos, array);
         if (!bl) {
            this.queuedEdgeSections.add(sectionPos);
         }
      } else {
         this.queuedSections.remove(sectionPos);
      }
   }

   protected void setSectionStatus(long sectionPos, boolean notReady) {
      boolean bl2 = this.readySections.contains(sectionPos);
      if (!bl2 && !notReady) {
         this.markedReadySections.add(sectionPos);
         this.updateLevel(Long.MAX_VALUE, sectionPos, 0, true);
      }

      if (bl2 && notReady) {
         this.markedNotReadySections.add(sectionPos);
         this.updateLevel(Long.MAX_VALUE, sectionPos, 2, false);
      }
   }

   protected void updateAll() {
      if (this.hasPendingUpdates()) {
         this.applyPendingUpdates(Integer.MAX_VALUE);
      }
   }

   protected void notifyChanges() {
      if (!this.dirtySections.isEmpty()) {
         M lv = this.storage.copy();
         lv.disableCache();
         this.uncachedStorage = lv;
         this.dirtySections.clear();
      }

      if (!this.notifySections.isEmpty()) {
         LongIterator longIterator = this.notifySections.iterator();

         while (longIterator.hasNext()) {
            long l = longIterator.nextLong();
            this.chunkProvider.onLightUpdate(this.lightType, ChunkSectionPos.from(l));
         }

         this.notifySections.clear();
      }
   }
}
