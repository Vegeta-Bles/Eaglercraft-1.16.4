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
      int _snowman = BlockPos.unpackLongX(blockPos);
      int _snowmanx = BlockPos.unpackLongY(blockPos);
      int _snowmanxx = BlockPos.unpackLongZ(blockPos);
      BlockView _snowmanxxx = this.chunkProvider.getChunk(_snowman >> 4, _snowmanxx >> 4);
      return _snowmanxxx != null ? _snowmanxxx.getLuminance(this.mutablePos.set(_snowman, _snowmanx, _snowmanxx)) : 0;
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
         int _snowman = Integer.signum(BlockPos.unpackLongX(targetId) - BlockPos.unpackLongX(sourceId));
         int _snowmanx = Integer.signum(BlockPos.unpackLongY(targetId) - BlockPos.unpackLongY(sourceId));
         int _snowmanxx = Integer.signum(BlockPos.unpackLongZ(targetId) - BlockPos.unpackLongZ(sourceId));
         Direction _snowmanxxx = Direction.fromVector(_snowman, _snowmanx, _snowmanxx);
         if (_snowmanxxx == null) {
            return 15;
         } else {
            MutableInt _snowmanxxxx = new MutableInt();
            BlockState _snowmanxxxxx = this.getStateForLighting(targetId, _snowmanxxxx);
            if (_snowmanxxxx.getValue() >= 15) {
               return 15;
            } else {
               BlockState _snowmanxxxxxx = this.getStateForLighting(sourceId, null);
               VoxelShape _snowmanxxxxxxx = this.getOpaqueShape(_snowmanxxxxxx, sourceId, _snowmanxxx);
               VoxelShape _snowmanxxxxxxxx = this.getOpaqueShape(_snowmanxxxxx, targetId, _snowmanxxx.getOpposite());
               return VoxelShapes.unionCoversFullCube(_snowmanxxxxxxx, _snowmanxxxxxxxx) ? 15 : level + Math.max(1, _snowmanxxxx.getValue());
            }
         }
      }
   }

   @Override
   protected void propagateLevel(long id, int level, boolean decrease) {
      long _snowman = ChunkSectionPos.fromBlockPos(id);

      for (Direction _snowmanx : DIRECTIONS) {
         long _snowmanxx = BlockPos.offset(id, _snowmanx);
         long _snowmanxxx = ChunkSectionPos.fromBlockPos(_snowmanxx);
         if (_snowman == _snowmanxxx || this.lightStorage.hasSection(_snowmanxxx)) {
            this.propagateLevel(id, _snowmanxx, level, decrease);
         }
      }
   }

   @Override
   protected int recalculateLevel(long id, long excludedId, int maxLevel) {
      int _snowman = maxLevel;
      if (Long.MAX_VALUE != excludedId) {
         int _snowmanx = this.getPropagatedLevel(Long.MAX_VALUE, id, 0);
         if (maxLevel > _snowmanx) {
            _snowman = _snowmanx;
         }

         if (_snowman == 0) {
            return _snowman;
         }
      }

      long _snowmanxx = ChunkSectionPos.fromBlockPos(id);
      ChunkNibbleArray _snowmanxxx = this.lightStorage.getLightSection(_snowmanxx, true);

      for (Direction _snowmanxxxx : DIRECTIONS) {
         long _snowmanxxxxx = BlockPos.offset(id, _snowmanxxxx);
         if (_snowmanxxxxx != excludedId) {
            long _snowmanxxxxxx = ChunkSectionPos.fromBlockPos(_snowmanxxxxx);
            ChunkNibbleArray _snowmanxxxxxxx;
            if (_snowmanxx == _snowmanxxxxxx) {
               _snowmanxxxxxxx = _snowmanxxx;
            } else {
               _snowmanxxxxxxx = this.lightStorage.getLightSection(_snowmanxxxxxx, true);
            }

            if (_snowmanxxxxxxx != null) {
               int _snowmanxxxxxxxx = this.getPropagatedLevel(_snowmanxxxxx, id, this.getCurrentLevelFromSection(_snowmanxxxxxxx, _snowmanxxxxx));
               if (_snowman > _snowmanxxxxxxxx) {
                  _snowman = _snowmanxxxxxxxx;
               }

               if (_snowman == 0) {
                  return _snowman;
               }
            }
         }
      }

      return _snowman;
   }

   @Override
   public void addLightSource(BlockPos pos, int level) {
      this.lightStorage.updateAll();
      this.updateLevel(Long.MAX_VALUE, pos.asLong(), 15 - level, true);
   }
}
