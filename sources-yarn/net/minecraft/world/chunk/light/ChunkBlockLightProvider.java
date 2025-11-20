package net.minecraft.world.chunk.light;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.ChunkNibbleArray;
import net.minecraft.world.chunk.ChunkProvider;
import org.apache.commons.lang3.mutable.MutableInt;

public final class ChunkBlockLightProvider extends ChunkLightProvider<BlockLightStorage.Data, BlockLightStorage> {
   private static final Direction[] DIRECTIONS = Direction.values();
   private final BlockPos.Mutable mutablePos = new BlockPos.Mutable();

   public ChunkBlockLightProvider(ChunkProvider chunkProvider) {
      super(chunkProvider, LightType.BLOCK, new BlockLightStorage(chunkProvider));
   }

   private int getLightSourceLuminance(long blockPos) {
      int i = BlockPos.unpackLongX(blockPos);
      int j = BlockPos.unpackLongY(blockPos);
      int k = BlockPos.unpackLongZ(blockPos);
      BlockView lv = this.chunkProvider.getChunk(i >> 4, k >> 4);
      return lv != null ? lv.getLuminance(this.mutablePos.set(i, j, k)) : 0;
   }

   @Override
   protected int getPropagatedLevel(long sourceId, long targetId, int level) {
      if (targetId == Long.MAX_VALUE) {
         return 15;
      } else if (sourceId == Long.MAX_VALUE) {
         return level + 15 - this.getLightSourceLuminance(targetId);
      } else if (level >= 15) {
         return level;
      } else {
         int j = Integer.signum(BlockPos.unpackLongX(targetId) - BlockPos.unpackLongX(sourceId));
         int k = Integer.signum(BlockPos.unpackLongY(targetId) - BlockPos.unpackLongY(sourceId));
         int n = Integer.signum(BlockPos.unpackLongZ(targetId) - BlockPos.unpackLongZ(sourceId));
         Direction lv = Direction.fromVector(j, k, n);
         if (lv == null) {
            return 15;
         } else {
            MutableInt mutableInt = new MutableInt();
            BlockState lv2 = this.getStateForLighting(targetId, mutableInt);
            if (mutableInt.getValue() >= 15) {
               return 15;
            } else {
               BlockState lv3 = this.getStateForLighting(sourceId, null);
               VoxelShape lv4 = this.getOpaqueShape(lv3, sourceId, lv);
               VoxelShape lv5 = this.getOpaqueShape(lv2, targetId, lv.getOpposite());
               return VoxelShapes.unionCoversFullCube(lv4, lv5) ? 15 : level + Math.max(1, mutableInt.getValue());
            }
         }
      }
   }

   @Override
   protected void propagateLevel(long id, int level, boolean decrease) {
      long m = ChunkSectionPos.fromBlockPos(id);

      for (Direction lv : DIRECTIONS) {
         long n = BlockPos.offset(id, lv);
         long o = ChunkSectionPos.fromBlockPos(n);
         if (m == o || this.lightStorage.hasSection(o)) {
            this.propagateLevel(id, n, level, decrease);
         }
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
         if (o != excludedId) {
            long p = ChunkSectionPos.fromBlockPos(o);
            ChunkNibbleArray lv3;
            if (n == p) {
               lv3 = lv;
            } else {
               lv3 = this.lightStorage.getLightSection(p, true);
            }

            if (lv3 != null) {
               int q = this.getPropagatedLevel(o, id, this.getCurrentLevelFromSection(lv3, o));
               if (j > q) {
                  j = q;
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
   public void addLightSource(BlockPos pos, int level) {
      this.lightStorage.updateAll();
      this.updateLevel(Long.MAX_VALUE, pos.asLong(), 15 - level, true);
   }
}
