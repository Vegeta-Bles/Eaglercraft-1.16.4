package net.minecraft.world.chunk.light;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.ChunkNibbleArray;
import net.minecraft.world.chunk.ChunkProvider;
import org.apache.commons.lang3.mutable.MutableInt;

public final class ChunkSkyLightProvider extends ChunkLightProvider<SkyLightStorage.Data, SkyLightStorage> {
   private static final Direction[] DIRECTIONS = Direction.values();
   private static final Direction[] HORIZONTAL_DIRECTIONS = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

   public ChunkSkyLightProvider(ChunkProvider chunkProvider) {
      super(chunkProvider, LightType.SKY, new SkyLightStorage(chunkProvider));
   }

   @Override
   protected int getPropagatedLevel(long sourceId, long targetId, int level) {
      if (targetId == Long.MAX_VALUE) {
         return 15;
      } else {
         if (sourceId == Long.MAX_VALUE) {
            if (!this.lightStorage.isTopmostBlock(targetId)) {
               return 15;
            }

            level = 0;
         }

         if (level >= 15) {
            return level;
         } else {
            MutableInt mutableInt = new MutableInt();
            BlockState lv = this.getStateForLighting(targetId, mutableInt);
            if (mutableInt.getValue() >= 15) {
               return 15;
            } else {
               int j = BlockPos.unpackLongX(sourceId);
               int k = BlockPos.unpackLongY(sourceId);
               int n = BlockPos.unpackLongZ(sourceId);
               int o = BlockPos.unpackLongX(targetId);
               int p = BlockPos.unpackLongY(targetId);
               int q = BlockPos.unpackLongZ(targetId);
               boolean bl = j == o && n == q;
               int r = Integer.signum(o - j);
               int s = Integer.signum(p - k);
               int t = Integer.signum(q - n);
               Direction lv2;
               if (sourceId == Long.MAX_VALUE) {
                  lv2 = Direction.DOWN;
               } else {
                  lv2 = Direction.fromVector(r, s, t);
               }

               BlockState lv4 = this.getStateForLighting(sourceId, null);
               if (lv2 != null) {
                  VoxelShape lv5 = this.getOpaqueShape(lv4, sourceId, lv2);
                  VoxelShape lv6 = this.getOpaqueShape(lv, targetId, lv2.getOpposite());
                  if (VoxelShapes.unionCoversFullCube(lv5, lv6)) {
                     return 15;
                  }
               } else {
                  VoxelShape lv7 = this.getOpaqueShape(lv4, sourceId, Direction.DOWN);
                  if (VoxelShapes.unionCoversFullCube(lv7, VoxelShapes.empty())) {
                     return 15;
                  }

                  int u = bl ? -1 : 0;
                  Direction lv8 = Direction.fromVector(r, u, t);
                  if (lv8 == null) {
                     return 15;
                  }

                  VoxelShape lv9 = this.getOpaqueShape(lv, targetId, lv8.getOpposite());
                  if (VoxelShapes.unionCoversFullCube(VoxelShapes.empty(), lv9)) {
                     return 15;
                  }
               }

               boolean bl2 = sourceId == Long.MAX_VALUE || bl && k > p;
               return bl2 && level == 0 && mutableInt.getValue() == 0 ? 0 : level + Math.max(1, mutableInt.getValue());
            }
         }
      }
   }

   @Override
   protected void propagateLevel(long id, int level, boolean decrease) {
      long m = ChunkSectionPos.fromBlockPos(id);
      int j = BlockPos.unpackLongY(id);
      int k = ChunkSectionPos.getLocalCoord(j);
      int n = ChunkSectionPos.getSectionCoord(j);
      int o;
      if (k != 0) {
         o = 0;
      } else {
         int p = 0;

         while (!this.lightStorage.hasSection(ChunkSectionPos.offset(m, 0, -p - 1, 0)) && this.lightStorage.isAboveMinHeight(n - p - 1)) {
            p++;
         }

         o = p;
      }

      long r = BlockPos.add(id, 0, -1 - o * 16, 0);
      long s = ChunkSectionPos.fromBlockPos(r);
      if (m == s || this.lightStorage.hasSection(s)) {
         this.propagateLevel(id, r, level, decrease);
      }

      long t = BlockPos.offset(id, Direction.UP);
      long u = ChunkSectionPos.fromBlockPos(t);
      if (m == u || this.lightStorage.hasSection(u)) {
         this.propagateLevel(id, t, level, decrease);
      }

      for (Direction lv : HORIZONTAL_DIRECTIONS) {
         int v = 0;

         do {
            long w = BlockPos.add(id, lv.getOffsetX(), -v, lv.getOffsetZ());
            long x = ChunkSectionPos.fromBlockPos(w);
            if (m == x) {
               this.propagateLevel(id, w, level, decrease);
               break;
            }

            if (this.lightStorage.hasSection(x)) {
               this.propagateLevel(id, w, level, decrease);
            }
         } while (++v > o * 16);
      }
   }

   @Override
   protected int recalculateLevel(long id, long excludedId, int maxLevel) {
      int j = maxLevel;
      if (Long.MAX_VALUE != excludedId) {
         int k = this.getPropagatedLevel(Long.MAX_VALUE, id, 0);
         if (maxLevel > k) {
            j = k;
         }

         if (j == 0) {
            return j;
         }
      }

      long n = ChunkSectionPos.fromBlockPos(id);
      ChunkNibbleArray lv = this.lightStorage.getLightSection(n, true);

      for (Direction lv2 : DIRECTIONS) {
         long o = BlockPos.offset(id, lv2);
         long p = ChunkSectionPos.fromBlockPos(o);
         ChunkNibbleArray lv3;
         if (n == p) {
            lv3 = lv;
         } else {
            lv3 = this.lightStorage.getLightSection(p, true);
         }

         if (lv3 != null) {
            if (o != excludedId) {
               int q = this.getPropagatedLevel(o, id, this.getCurrentLevelFromSection(lv3, o));
               if (j > q) {
                  j = q;
               }

               if (j == 0) {
                  return j;
               }
            }
         } else if (lv2 != Direction.DOWN) {
            for (o = BlockPos.removeChunkSectionLocalY(o);
               !this.lightStorage.hasSection(p) && !this.lightStorage.isAtOrAboveTopmostSection(p);
               o = BlockPos.add(o, 0, 16, 0)
            ) {
               p = ChunkSectionPos.offset(p, Direction.UP);
            }

            ChunkNibbleArray lv5 = this.lightStorage.getLightSection(p, true);
            if (o != excludedId) {
               int r;
               if (lv5 != null) {
                  r = this.getPropagatedLevel(o, id, this.getCurrentLevelFromSection(lv5, o));
               } else {
                  r = this.lightStorage.isSectionEnabled(p) ? 0 : 15;
               }

               if (j > r) {
                  j = r;
               }

               if (j == 0) {
                  return j;
               }
            }
         }
      }

      return j;
   }

   @Override
   protected void resetLevel(long id) {
      this.lightStorage.updateAll();
      long m = ChunkSectionPos.fromBlockPos(id);
      if (this.lightStorage.hasSection(m)) {
         super.resetLevel(id);
      } else {
         for (id = BlockPos.removeChunkSectionLocalY(id);
            !this.lightStorage.hasSection(m) && !this.lightStorage.isAtOrAboveTopmostSection(m);
            id = BlockPos.add(id, 0, 16, 0)
         ) {
            m = ChunkSectionPos.offset(m, Direction.UP);
         }

         if (this.lightStorage.hasSection(m)) {
            super.resetLevel(id);
         }
      }
   }

   @Environment(EnvType.CLIENT)
   @Override
   public String displaySectionLevel(long sectionPos) {
      return super.displaySectionLevel(sectionPos) + (this.lightStorage.isAtOrAboveTopmostSection(sectionPos) ? "*" : "");
   }
}
