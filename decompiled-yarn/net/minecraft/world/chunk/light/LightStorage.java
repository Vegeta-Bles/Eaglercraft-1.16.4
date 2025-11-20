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
      ChunkNibbleArray _snowman = (ChunkNibbleArray)this.queuedSections.get(sectionPos);
      return _snowman != null ? _snowman : this.getLightSection(sectionPos, false);
   }

   protected abstract int getLight(long blockPos);

   protected int get(long blockPos) {
      long _snowman = ChunkSectionPos.fromBlockPos(blockPos);
      ChunkNibbleArray _snowmanx = this.getLightSection(_snowman, true);
      return _snowmanx.get(
         ChunkSectionPos.getLocalCoord(BlockPos.unpackLongX(blockPos)),
         ChunkSectionPos.getLocalCoord(BlockPos.unpackLongY(blockPos)),
         ChunkSectionPos.getLocalCoord(BlockPos.unpackLongZ(blockPos))
      );
   }

   protected void set(long blockPos, int value) {
      long _snowman = ChunkSectionPos.fromBlockPos(blockPos);
      if (this.dirtySections.add(_snowman)) {
         this.storage.replaceWithCopy(_snowman);
      }

      ChunkNibbleArray _snowmanx = this.getLightSection(_snowman, true);
      _snowmanx.set(
         ChunkSectionPos.getLocalCoord(BlockPos.unpackLongX(blockPos)),
         ChunkSectionPos.getLocalCoord(BlockPos.unpackLongY(blockPos)),
         ChunkSectionPos.getLocalCoord(BlockPos.unpackLongZ(blockPos)),
         value
      );

      for (int _snowmanxx = -1; _snowmanxx <= 1; _snowmanxx++) {
         for (int _snowmanxxx = -1; _snowmanxxx <= 1; _snowmanxxx++) {
            for (int _snowmanxxxx = -1; _snowmanxxxx <= 1; _snowmanxxxx++) {
               this.notifySections.add(ChunkSectionPos.fromBlockPos(BlockPos.add(blockPos, _snowmanxxx, _snowmanxxxx, _snowmanxx)));
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
      int _snowman = this.getLevel(id);
      if (_snowman != 0 && level == 0) {
         this.readySections.add(id);
         this.markedReadySections.remove(id);
      }

      if (_snowman == 0 && level != 0) {
         this.readySections.remove(id);
         this.markedNotReadySections.remove(id);
      }

      if (_snowman >= 2 && level != 2) {
         if (this.sectionsToRemove.contains(id)) {
            this.sectionsToRemove.remove(id);
         } else {
            this.storage.put(id, this.createSection(id));
            this.dirtySections.add(id);
            this.onLoadSection(id);

            for (int _snowmanx = -1; _snowmanx <= 1; _snowmanx++) {
               for (int _snowmanxx = -1; _snowmanxx <= 1; _snowmanxx++) {
                  for (int _snowmanxxx = -1; _snowmanxxx <= 1; _snowmanxxx++) {
                     this.notifySections.add(ChunkSectionPos.fromBlockPos(BlockPos.add(id, _snowmanxx, _snowmanxxx, _snowmanx)));
                  }
               }
            }
         }
      }

      if (_snowman != 2 && level >= 2) {
         this.sectionsToRemove.add(id);
      }

      this.hasLightUpdates = !this.sectionsToRemove.isEmpty();
   }

   protected ChunkNibbleArray createSection(long sectionPos) {
      ChunkNibbleArray _snowman = (ChunkNibbleArray)this.queuedSections.get(sectionPos);
      return _snowman != null ? _snowman : new ChunkNibbleArray();
   }

   protected void removeSection(ChunkLightProvider<?, ?> storage, long sectionPos) {
      if (storage.getPendingUpdateCount() < 8192) {
         storage.removePendingUpdateIf(_snowmanx -> ChunkSectionPos.fromBlockPos(_snowmanx) == sectionPos);
      } else {
         int _snowman = ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackX(sectionPos));
         int _snowmanx = ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackY(sectionPos));
         int _snowmanxx = ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackZ(sectionPos));

         for (int _snowmanxxx = 0; _snowmanxxx < 16; _snowmanxxx++) {
            for (int _snowmanxxxx = 0; _snowmanxxxx < 16; _snowmanxxxx++) {
               for (int _snowmanxxxxx = 0; _snowmanxxxxx < 16; _snowmanxxxxx++) {
                  long _snowmanxxxxxx = BlockPos.asLong(_snowman + _snowmanxxx, _snowmanx + _snowmanxxxx, _snowmanxx + _snowmanxxxxx);
                  storage.removePendingUpdate(_snowmanxxxxxx);
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
         LongIterator var4 = this.sectionsToRemove.iterator();

         while (var4.hasNext()) {
            long _snowman = (Long)var4.next();
            this.removeSection(lightProvider, _snowman);
            ChunkNibbleArray _snowmanx = (ChunkNibbleArray)this.queuedSections.remove(_snowman);
            ChunkNibbleArray _snowmanxx = this.storage.removeChunk(_snowman);
            if (this.columnsToRetain.contains(ChunkSectionPos.withZeroY(_snowman))) {
               if (_snowmanx != null) {
                  this.queuedSections.put(_snowman, _snowmanx);
               } else if (_snowmanxx != null) {
                  this.queuedSections.put(_snowman, _snowmanxx);
               }
            }
         }

         this.storage.clearCache();
         var4 = this.sectionsToRemove.iterator();

         while (var4.hasNext()) {
            long _snowman = (Long)var4.next();
            this.onUnloadSection(_snowman);
         }

         this.sectionsToRemove.clear();
         this.hasLightUpdates = false;
         ObjectIterator var10 = this.queuedSections.long2ObjectEntrySet().iterator();

         while (var10.hasNext()) {
            Entry<ChunkNibbleArray> _snowman = (Entry<ChunkNibbleArray>)var10.next();
            long _snowmanx = _snowman.getLongKey();
            if (this.hasSection(_snowmanx)) {
               ChunkNibbleArray _snowmanxx = (ChunkNibbleArray)_snowman.getValue();
               if (this.storage.get(_snowmanx) != _snowmanxx) {
                  this.removeSection(lightProvider, _snowmanx);
                  this.storage.put(_snowmanx, _snowmanxx);
                  this.dirtySections.add(_snowmanx);
               }
            }
         }

         this.storage.clearCache();
         if (!skipEdgeLightPropagation) {
            var4 = this.queuedSections.keySet().iterator();

            while (var4.hasNext()) {
               long _snowman = (Long)var4.next();
               this.updateSection(lightProvider, _snowman);
            }
         } else {
            var4 = this.queuedEdgeSections.iterator();

            while (var4.hasNext()) {
               long _snowman = (Long)var4.next();
               this.updateSection(lightProvider, _snowman);
            }
         }

         this.queuedEdgeSections.clear();
         ObjectIterator<Entry<ChunkNibbleArray>> _snowman = this.queuedSections.long2ObjectEntrySet().iterator();

         while (_snowman.hasNext()) {
            Entry<ChunkNibbleArray> _snowmanx = (Entry<ChunkNibbleArray>)_snowman.next();
            long _snowmanxx = _snowmanx.getLongKey();
            if (this.hasSection(_snowmanxx)) {
               _snowman.remove();
            }
         }
      }
   }

   private void updateSection(ChunkLightProvider<M, ?> lightProvider, long sectionPos) {
      if (this.hasSection(sectionPos)) {
         int _snowman = ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackX(sectionPos));
         int _snowmanx = ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackY(sectionPos));
         int _snowmanxx = ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackZ(sectionPos));

         for (Direction _snowmanxxx : DIRECTIONS) {
            long _snowmanxxxx = ChunkSectionPos.offset(sectionPos, _snowmanxxx);
            if (!this.queuedSections.containsKey(_snowmanxxxx) && this.hasSection(_snowmanxxxx)) {
               for (int _snowmanxxxxx = 0; _snowmanxxxxx < 16; _snowmanxxxxx++) {
                  for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 16; _snowmanxxxxxx++) {
                     long _snowmanxxxxxxxx;
                     long _snowmanxxxxxxx;
                     switch (_snowmanxxx) {
                        case DOWN:
                           _snowmanxxxxxxxx = BlockPos.asLong(_snowman + _snowmanxxxxxx, _snowmanx, _snowmanxx + _snowmanxxxxx);
                           _snowmanxxxxxxx = BlockPos.asLong(_snowman + _snowmanxxxxxx, _snowmanx - 1, _snowmanxx + _snowmanxxxxx);
                           break;
                        case UP:
                           _snowmanxxxxxxxx = BlockPos.asLong(_snowman + _snowmanxxxxxx, _snowmanx + 16 - 1, _snowmanxx + _snowmanxxxxx);
                           _snowmanxxxxxxx = BlockPos.asLong(_snowman + _snowmanxxxxxx, _snowmanx + 16, _snowmanxx + _snowmanxxxxx);
                           break;
                        case NORTH:
                           _snowmanxxxxxxxx = BlockPos.asLong(_snowman + _snowmanxxxxx, _snowmanx + _snowmanxxxxxx, _snowmanxx);
                           _snowmanxxxxxxx = BlockPos.asLong(_snowman + _snowmanxxxxx, _snowmanx + _snowmanxxxxxx, _snowmanxx - 1);
                           break;
                        case SOUTH:
                           _snowmanxxxxxxxx = BlockPos.asLong(_snowman + _snowmanxxxxx, _snowmanx + _snowmanxxxxxx, _snowmanxx + 16 - 1);
                           _snowmanxxxxxxx = BlockPos.asLong(_snowman + _snowmanxxxxx, _snowmanx + _snowmanxxxxxx, _snowmanxx + 16);
                           break;
                        case WEST:
                           _snowmanxxxxxxxx = BlockPos.asLong(_snowman, _snowmanx + _snowmanxxxxx, _snowmanxx + _snowmanxxxxxx);
                           _snowmanxxxxxxx = BlockPos.asLong(_snowman - 1, _snowmanx + _snowmanxxxxx, _snowmanxx + _snowmanxxxxxx);
                           break;
                        default:
                           _snowmanxxxxxxxx = BlockPos.asLong(_snowman + 16 - 1, _snowmanx + _snowmanxxxxx, _snowmanxx + _snowmanxxxxxx);
                           _snowmanxxxxxxx = BlockPos.asLong(_snowman + 16, _snowmanx + _snowmanxxxxx, _snowmanxx + _snowmanxxxxxx);
                     }

                     lightProvider.updateLevel(
                        _snowmanxxxxxxxx, _snowmanxxxxxxx, lightProvider.getPropagatedLevel(_snowmanxxxxxxxx, _snowmanxxxxxxx, lightProvider.getLevel(_snowmanxxxxxxxx)), false
                     );
                     lightProvider.updateLevel(
                        _snowmanxxxxxxx, _snowmanxxxxxxxx, lightProvider.getPropagatedLevel(_snowmanxxxxxxx, _snowmanxxxxxxxx, lightProvider.getLevel(_snowmanxxxxxxx)), false
                     );
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

   protected void enqueueSectionData(long sectionPos, @Nullable ChunkNibbleArray array, boolean _snowman) {
      if (array != null) {
         this.queuedSections.put(sectionPos, array);
         if (!_snowman) {
            this.queuedEdgeSections.add(sectionPos);
         }
      } else {
         this.queuedSections.remove(sectionPos);
      }
   }

   protected void setSectionStatus(long sectionPos, boolean notReady) {
      boolean _snowman = this.readySections.contains(sectionPos);
      if (!_snowman && !notReady) {
         this.markedReadySections.add(sectionPos);
         this.updateLevel(Long.MAX_VALUE, sectionPos, 0, true);
      }

      if (_snowman && notReady) {
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
         M _snowman = this.storage.copy();
         _snowman.disableCache();
         this.uncachedStorage = _snowman;
         this.dirtySections.clear();
      }

      if (!this.notifySections.isEmpty()) {
         LongIterator _snowman = this.notifySections.iterator();

         while (_snowman.hasNext()) {
            long _snowmanx = _snowman.nextLong();
            this.chunkProvider.onLightUpdate(this.lightType, ChunkSectionPos.from(_snowmanx));
         }

         this.notifySections.clear();
      }
   }
}
