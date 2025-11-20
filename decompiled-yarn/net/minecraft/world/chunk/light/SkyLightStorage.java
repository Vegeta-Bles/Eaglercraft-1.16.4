package net.minecraft.world.chunk.light;

import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Arrays;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.ChunkNibbleArray;
import net.minecraft.world.chunk.ChunkProvider;
import net.minecraft.world.chunk.ChunkToNibbleArrayMap;
import net.minecraft.world.chunk.ColumnChunkNibbleArray;

public class SkyLightStorage extends LightStorage<SkyLightStorage.Data> {
   private static final Direction[] LIGHT_REDUCTION_DIRECTIONS = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
   private final LongSet field_15820 = new LongOpenHashSet();
   private final LongSet sectionsToUpdate = new LongOpenHashSet();
   private final LongSet sectionsToRemove = new LongOpenHashSet();
   private final LongSet enabledColumns = new LongOpenHashSet();
   private volatile boolean hasUpdates;

   protected SkyLightStorage(ChunkProvider chunkProvider) {
      super(LightType.SKY, chunkProvider, new SkyLightStorage.Data(new Long2ObjectOpenHashMap(), new Long2IntOpenHashMap(), Integer.MAX_VALUE));
   }

   @Override
   protected int getLight(long blockPos) {
      long _snowman = ChunkSectionPos.fromBlockPos(blockPos);
      int _snowmanx = ChunkSectionPos.unpackY(_snowman);
      SkyLightStorage.Data _snowmanxx = this.uncachedStorage;
      int _snowmanxxx = _snowmanxx.columnToTopSection.get(ChunkSectionPos.withZeroY(_snowman));
      if (_snowmanxxx != _snowmanxx.minSectionY && _snowmanx < _snowmanxxx) {
         ChunkNibbleArray _snowmanxxxx = this.getLightSection(_snowmanxx, _snowman);
         if (_snowmanxxxx == null) {
            for (blockPos = BlockPos.removeChunkSectionLocalY(blockPos); _snowmanxxxx == null; _snowmanxxxx = this.getLightSection(_snowmanxx, _snowman)) {
               _snowman = ChunkSectionPos.offset(_snowman, Direction.UP);
               if (++_snowmanx >= _snowmanxxx) {
                  return 15;
               }

               blockPos = BlockPos.add(blockPos, 0, 16, 0);
            }
         }

         return _snowmanxxxx.get(
            ChunkSectionPos.getLocalCoord(BlockPos.unpackLongX(blockPos)),
            ChunkSectionPos.getLocalCoord(BlockPos.unpackLongY(blockPos)),
            ChunkSectionPos.getLocalCoord(BlockPos.unpackLongZ(blockPos))
         );
      } else {
         return 15;
      }
   }

   @Override
   protected void onLoadSection(long sectionPos) {
      int _snowman = ChunkSectionPos.unpackY(sectionPos);
      if (this.storage.minSectionY > _snowman) {
         this.storage.minSectionY = _snowman;
         this.storage.columnToTopSection.defaultReturnValue(this.storage.minSectionY);
      }

      long _snowmanx = ChunkSectionPos.withZeroY(sectionPos);
      int _snowmanxx = this.storage.columnToTopSection.get(_snowmanx);
      if (_snowmanxx < _snowman + 1) {
         this.storage.columnToTopSection.put(_snowmanx, _snowman + 1);
         if (this.enabledColumns.contains(_snowmanx)) {
            this.enqueueAddSection(sectionPos);
            if (_snowmanxx > this.storage.minSectionY) {
               long _snowmanxxx = ChunkSectionPos.asLong(ChunkSectionPos.unpackX(sectionPos), _snowmanxx - 1, ChunkSectionPos.unpackZ(sectionPos));
               this.enqueueRemoveSection(_snowmanxxx);
            }

            this.checkForUpdates();
         }
      }
   }

   private void enqueueRemoveSection(long sectionPos) {
      this.sectionsToRemove.add(sectionPos);
      this.sectionsToUpdate.remove(sectionPos);
   }

   private void enqueueAddSection(long sectionPos) {
      this.sectionsToUpdate.add(sectionPos);
      this.sectionsToRemove.remove(sectionPos);
   }

   private void checkForUpdates() {
      this.hasUpdates = !this.sectionsToUpdate.isEmpty() || !this.sectionsToRemove.isEmpty();
   }

   @Override
   protected void onUnloadSection(long sectionPos) {
      long _snowman = ChunkSectionPos.withZeroY(sectionPos);
      boolean _snowmanx = this.enabledColumns.contains(_snowman);
      if (_snowmanx) {
         this.enqueueRemoveSection(sectionPos);
      }

      int _snowmanxx = ChunkSectionPos.unpackY(sectionPos);
      if (this.storage.columnToTopSection.get(_snowman) == _snowmanxx + 1) {
         long _snowmanxxx;
         for (_snowmanxxx = sectionPos; !this.hasSection(_snowmanxxx) && this.isAboveMinHeight(_snowmanxx); _snowmanxxx = ChunkSectionPos.offset(_snowmanxxx, Direction.DOWN)) {
            _snowmanxx--;
         }

         if (this.hasSection(_snowmanxxx)) {
            this.storage.columnToTopSection.put(_snowman, _snowmanxx + 1);
            if (_snowmanx) {
               this.enqueueAddSection(_snowmanxxx);
            }
         } else {
            this.storage.columnToTopSection.remove(_snowman);
         }
      }

      if (_snowmanx) {
         this.checkForUpdates();
      }
   }

   @Override
   protected void setColumnEnabled(long columnPos, boolean enabled) {
      this.updateAll();
      if (enabled && this.enabledColumns.add(columnPos)) {
         int _snowman = this.storage.columnToTopSection.get(columnPos);
         if (_snowman != this.storage.minSectionY) {
            long _snowmanx = ChunkSectionPos.asLong(ChunkSectionPos.unpackX(columnPos), _snowman - 1, ChunkSectionPos.unpackZ(columnPos));
            this.enqueueAddSection(_snowmanx);
            this.checkForUpdates();
         }
      } else if (!enabled) {
         this.enabledColumns.remove(columnPos);
      }
   }

   @Override
   protected boolean hasLightUpdates() {
      return super.hasLightUpdates() || this.hasUpdates;
   }

   @Override
   protected ChunkNibbleArray createSection(long sectionPos) {
      ChunkNibbleArray _snowman = (ChunkNibbleArray)this.queuedSections.get(sectionPos);
      if (_snowman != null) {
         return _snowman;
      } else {
         long _snowmanx = ChunkSectionPos.offset(sectionPos, Direction.UP);
         int _snowmanxx = this.storage.columnToTopSection.get(ChunkSectionPos.withZeroY(sectionPos));
         if (_snowmanxx != this.storage.minSectionY && ChunkSectionPos.unpackY(_snowmanx) < _snowmanxx) {
            ChunkNibbleArray _snowmanxxx;
            while ((_snowmanxxx = this.getLightSection(_snowmanx, true)) == null) {
               _snowmanx = ChunkSectionPos.offset(_snowmanx, Direction.UP);
            }

            return new ChunkNibbleArray(new ColumnChunkNibbleArray(_snowmanxxx, 0).asByteArray());
         } else {
            return new ChunkNibbleArray();
         }
      }
   }

   @Override
   protected void updateLight(ChunkLightProvider<SkyLightStorage.Data, ?> lightProvider, boolean doSkylight, boolean skipEdgeLightPropagation) {
      super.updateLight(lightProvider, doSkylight, skipEdgeLightPropagation);
      if (doSkylight) {
         if (!this.sectionsToUpdate.isEmpty()) {
            LongIterator var4 = this.sectionsToUpdate.iterator();

            while (var4.hasNext()) {
               long _snowman = (Long)var4.next();
               int _snowmanx = this.getLevel(_snowman);
               if (_snowmanx != 2 && !this.sectionsToRemove.contains(_snowman) && this.field_15820.add(_snowman)) {
                  if (_snowmanx == 1) {
                     this.removeSection(lightProvider, _snowman);
                     if (this.dirtySections.add(_snowman)) {
                        this.storage.replaceWithCopy(_snowman);
                     }

                     Arrays.fill(this.getLightSection(_snowman, true).asByteArray(), (byte)-1);
                     int _snowmanxx = ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackX(_snowman));
                     int _snowmanxxx = ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackY(_snowman));
                     int _snowmanxxxx = ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackZ(_snowman));

                     for (Direction _snowmanxxxxx : LIGHT_REDUCTION_DIRECTIONS) {
                        long _snowmanxxxxxx = ChunkSectionPos.offset(_snowman, _snowmanxxxxx);
                        if ((this.sectionsToRemove.contains(_snowmanxxxxxx) || !this.field_15820.contains(_snowmanxxxxxx) && !this.sectionsToUpdate.contains(_snowmanxxxxxx))
                           && this.hasSection(_snowmanxxxxxx)) {
                           for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 16; _snowmanxxxxxxx++) {
                              for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 16; _snowmanxxxxxxxx++) {
                                 long _snowmanxxxxxxxxx;
                                 long _snowmanxxxxxxxxxx;
                                 switch (_snowmanxxxxx) {
                                    case NORTH:
                                       _snowmanxxxxxxxxx = BlockPos.asLong(_snowmanxx + _snowmanxxxxxxx, _snowmanxxx + _snowmanxxxxxxxx, _snowmanxxxx);
                                       _snowmanxxxxxxxxxx = BlockPos.asLong(_snowmanxx + _snowmanxxxxxxx, _snowmanxxx + _snowmanxxxxxxxx, _snowmanxxxx - 1);
                                       break;
                                    case SOUTH:
                                       _snowmanxxxxxxxxx = BlockPos.asLong(_snowmanxx + _snowmanxxxxxxx, _snowmanxxx + _snowmanxxxxxxxx, _snowmanxxxx + 16 - 1);
                                       _snowmanxxxxxxxxxx = BlockPos.asLong(_snowmanxx + _snowmanxxxxxxx, _snowmanxxx + _snowmanxxxxxxxx, _snowmanxxxx + 16);
                                       break;
                                    case WEST:
                                       _snowmanxxxxxxxxx = BlockPos.asLong(_snowmanxx, _snowmanxxx + _snowmanxxxxxxx, _snowmanxxxx + _snowmanxxxxxxxx);
                                       _snowmanxxxxxxxxxx = BlockPos.asLong(_snowmanxx - 1, _snowmanxxx + _snowmanxxxxxxx, _snowmanxxxx + _snowmanxxxxxxxx);
                                       break;
                                    default:
                                       _snowmanxxxxxxxxx = BlockPos.asLong(_snowmanxx + 16 - 1, _snowmanxxx + _snowmanxxxxxxx, _snowmanxxxx + _snowmanxxxxxxxx);
                                       _snowmanxxxxxxxxxx = BlockPos.asLong(_snowmanxx + 16, _snowmanxxx + _snowmanxxxxxxx, _snowmanxxxx + _snowmanxxxxxxxx);
                                 }

                                 lightProvider.updateLevel(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, lightProvider.getPropagatedLevel(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, 0), true);
                              }
                           }
                        }
                     }

                     for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 16; _snowmanxxxxxx++) {
                        for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 16; _snowmanxxxxxxx++) {
                           long _snowmanxxxxxxxx = BlockPos.asLong(
                              ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackX(_snowman)) + _snowmanxxxxxx,
                              ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackY(_snowman)),
                              ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackZ(_snowman)) + _snowmanxxxxxxx
                           );
                           long _snowmanxxxxxxxxx = BlockPos.asLong(
                              ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackX(_snowman)) + _snowmanxxxxxx,
                              ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackY(_snowman)) - 1,
                              ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackZ(_snowman)) + _snowmanxxxxxxx
                           );
                           lightProvider.updateLevel(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, lightProvider.getPropagatedLevel(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, 0), true);
                        }
                     }
                  } else {
                     for (int _snowmanxx = 0; _snowmanxx < 16; _snowmanxx++) {
                        for (int _snowmanxxx = 0; _snowmanxxx < 16; _snowmanxxx++) {
                           long _snowmanxxxx = BlockPos.asLong(
                              ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackX(_snowman)) + _snowmanxx,
                              ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackY(_snowman)) + 16 - 1,
                              ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackZ(_snowman)) + _snowmanxxx
                           );
                           lightProvider.updateLevel(Long.MAX_VALUE, _snowmanxxxx, 0, true);
                        }
                     }
                  }
               }
            }
         }

         this.sectionsToUpdate.clear();
         if (!this.sectionsToRemove.isEmpty()) {
            LongIterator var23 = this.sectionsToRemove.iterator();

            while (var23.hasNext()) {
               long _snowman = (Long)var23.next();
               if (this.field_15820.remove(_snowman) && this.hasSection(_snowman)) {
                  for (int _snowmanx = 0; _snowmanx < 16; _snowmanx++) {
                     for (int _snowmanxx = 0; _snowmanxx < 16; _snowmanxx++) {
                        long _snowmanxxx = BlockPos.asLong(
                           ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackX(_snowman)) + _snowmanx,
                           ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackY(_snowman)) + 16 - 1,
                           ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackZ(_snowman)) + _snowmanxx
                        );
                        lightProvider.updateLevel(Long.MAX_VALUE, _snowmanxxx, 15, false);
                     }
                  }
               }
            }
         }

         this.sectionsToRemove.clear();
         this.hasUpdates = false;
      }
   }

   protected boolean isAboveMinHeight(int sectionY) {
      return sectionY >= this.storage.minSectionY;
   }

   protected boolean isTopmostBlock(long blockPos) {
      int _snowman = BlockPos.unpackLongY(blockPos);
      if ((_snowman & 15) != 15) {
         return false;
      } else {
         long _snowmanx = ChunkSectionPos.fromBlockPos(blockPos);
         long _snowmanxx = ChunkSectionPos.withZeroY(_snowmanx);
         if (!this.enabledColumns.contains(_snowmanxx)) {
            return false;
         } else {
            int _snowmanxxx = this.storage.columnToTopSection.get(_snowmanxx);
            return ChunkSectionPos.getBlockCoord(_snowmanxxx) == _snowman + 16;
         }
      }
   }

   protected boolean isAtOrAboveTopmostSection(long sectionPos) {
      long _snowman = ChunkSectionPos.withZeroY(sectionPos);
      int _snowmanx = this.storage.columnToTopSection.get(_snowman);
      return _snowmanx == this.storage.minSectionY || ChunkSectionPos.unpackY(sectionPos) >= _snowmanx;
   }

   protected boolean isSectionEnabled(long sectionPos) {
      long _snowman = ChunkSectionPos.withZeroY(sectionPos);
      return this.enabledColumns.contains(_snowman);
   }

   public static final class Data extends ChunkToNibbleArrayMap<SkyLightStorage.Data> {
      private int minSectionY;
      private final Long2IntOpenHashMap columnToTopSection;

      public Data(Long2ObjectOpenHashMap<ChunkNibbleArray> arrays, Long2IntOpenHashMap columnToTopSection, int minSectionY) {
         super(arrays);
         this.columnToTopSection = columnToTopSection;
         columnToTopSection.defaultReturnValue(minSectionY);
         this.minSectionY = minSectionY;
      }

      public SkyLightStorage.Data copy() {
         return new SkyLightStorage.Data(this.arrays.clone(), this.columnToTopSection.clone(), this.minSectionY);
      }
   }
}
