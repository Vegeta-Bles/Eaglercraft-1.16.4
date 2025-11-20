package net.minecraft.world.chunk.light;

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
            MutableInt _snowman = new MutableInt();
            BlockState _snowmanx = this.getStateForLighting(targetId, _snowman);
            if (_snowman.getValue() >= 15) {
               return 15;
            } else {
               int _snowmanxx = BlockPos.unpackLongX(sourceId);
               int _snowmanxxx = BlockPos.unpackLongY(sourceId);
               int _snowmanxxxx = BlockPos.unpackLongZ(sourceId);
               int _snowmanxxxxx = BlockPos.unpackLongX(targetId);
               int _snowmanxxxxxx = BlockPos.unpackLongY(targetId);
               int _snowmanxxxxxxx = BlockPos.unpackLongZ(targetId);
               boolean _snowmanxxxxxxxx = _snowmanxx == _snowmanxxxxx && _snowmanxxxx == _snowmanxxxxxxx;
               int _snowmanxxxxxxxxx = Integer.signum(_snowmanxxxxx - _snowmanxx);
               int _snowmanxxxxxxxxxx = Integer.signum(_snowmanxxxxxx - _snowmanxxx);
               int _snowmanxxxxxxxxxxx = Integer.signum(_snowmanxxxxxxx - _snowmanxxxx);
               Direction _snowmanxxxxxxxxxxxx;
               if (sourceId == Long.MAX_VALUE) {
                  _snowmanxxxxxxxxxxxx = Direction.DOWN;
               } else {
                  _snowmanxxxxxxxxxxxx = Direction.fromVector(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
               }

               BlockState _snowmanxxxxxxxxxxxxx = this.getStateForLighting(sourceId, null);
               if (_snowmanxxxxxxxxxxxx != null) {
                  VoxelShape _snowmanxxxxxxxxxxxxxx = this.getOpaqueShape(_snowmanxxxxxxxxxxxxx, sourceId, _snowmanxxxxxxxxxxxx);
                  VoxelShape _snowmanxxxxxxxxxxxxxxx = this.getOpaqueShape(_snowmanx, targetId, _snowmanxxxxxxxxxxxx.getOpposite());
                  if (VoxelShapes.unionCoversFullCube(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx)) {
                     return 15;
                  }
               } else {
                  VoxelShape _snowmanxxxxxxxxxxxxxx = this.getOpaqueShape(_snowmanxxxxxxxxxxxxx, sourceId, Direction.DOWN);
                  if (VoxelShapes.unionCoversFullCube(_snowmanxxxxxxxxxxxxxx, VoxelShapes.empty())) {
                     return 15;
                  }

                  int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxx ? -1 : 0;
                  Direction _snowmanxxxxxxxxxxxxxxxx = Direction.fromVector(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx);
                  if (_snowmanxxxxxxxxxxxxxxxx == null) {
                     return 15;
                  }

                  VoxelShape _snowmanxxxxxxxxxxxxxxxxx = this.getOpaqueShape(_snowmanx, targetId, _snowmanxxxxxxxxxxxxxxxx.getOpposite());
                  if (VoxelShapes.unionCoversFullCube(VoxelShapes.empty(), _snowmanxxxxxxxxxxxxxxxxx)) {
                     return 15;
                  }
               }

               boolean _snowmanxxxxxxxxxxxxxxxxx = sourceId == Long.MAX_VALUE || _snowmanxxxxxxxx && _snowmanxxx > _snowmanxxxxxx;
               return _snowmanxxxxxxxxxxxxxxxxx && level == 0 && _snowman.getValue() == 0 ? 0 : level + Math.max(1, _snowman.getValue());
            }
         }
      }
   }

   @Override
   protected void propagateLevel(long id, int level, boolean decrease) {
      long _snowman = ChunkSectionPos.fromBlockPos(id);
      int _snowmanx = BlockPos.unpackLongY(id);
      int _snowmanxx = ChunkSectionPos.getLocalCoord(_snowmanx);
      int _snowmanxxx = ChunkSectionPos.getSectionCoord(_snowmanx);
      int _snowmanxxxx;
      if (_snowmanxx != 0) {
         _snowmanxxxx = 0;
      } else {
         int _snowmanxxxxx = 0;

         while (!this.lightStorage.hasSection(ChunkSectionPos.offset(_snowman, 0, -_snowmanxxxxx - 1, 0)) && this.lightStorage.isAboveMinHeight(_snowmanxxx - _snowmanxxxxx - 1)) {
            _snowmanxxxxx++;
         }

         _snowmanxxxx = _snowmanxxxxx;
      }

      long _snowmanxxxxx = BlockPos.add(id, 0, -1 - _snowmanxxxx * 16, 0);
      long _snowmanxxxxxx = ChunkSectionPos.fromBlockPos(_snowmanxxxxx);
      if (_snowman == _snowmanxxxxxx || this.lightStorage.hasSection(_snowmanxxxxxx)) {
         this.propagateLevel(id, _snowmanxxxxx, level, decrease);
      }

      long _snowmanxxxxxxx = BlockPos.offset(id, Direction.UP);
      long _snowmanxxxxxxxx = ChunkSectionPos.fromBlockPos(_snowmanxxxxxxx);
      if (_snowman == _snowmanxxxxxxxx || this.lightStorage.hasSection(_snowmanxxxxxxxx)) {
         this.propagateLevel(id, _snowmanxxxxxxx, level, decrease);
      }

      for (Direction _snowmanxxxxxxxxx : HORIZONTAL_DIRECTIONS) {
         int _snowmanxxxxxxxxxx = 0;

         do {
            long _snowmanxxxxxxxxxxx = BlockPos.add(id, _snowmanxxxxxxxxx.getOffsetX(), -_snowmanxxxxxxxxxx, _snowmanxxxxxxxxx.getOffsetZ());
            long _snowmanxxxxxxxxxxxx = ChunkSectionPos.fromBlockPos(_snowmanxxxxxxxxxxx);
            if (_snowman == _snowmanxxxxxxxxxxxx) {
               this.propagateLevel(id, _snowmanxxxxxxxxxxx, level, decrease);
               break;
            }

            if (this.lightStorage.hasSection(_snowmanxxxxxxxxxxxx)) {
               this.propagateLevel(id, _snowmanxxxxxxxxxxx, level, decrease);
            }
         } while (++_snowmanxxxxxxxxxx > _snowmanxxxx * 16);
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
         long _snowmanxxxxxx = ChunkSectionPos.fromBlockPos(_snowmanxxxxx);
         ChunkNibbleArray _snowmanxxxxxxx;
         if (_snowmanxx == _snowmanxxxxxx) {
            _snowmanxxxxxxx = _snowmanxxx;
         } else {
            _snowmanxxxxxxx = this.lightStorage.getLightSection(_snowmanxxxxxx, true);
         }

         if (_snowmanxxxxxxx != null) {
            if (_snowmanxxxxx != excludedId) {
               int _snowmanxxxxxxxx = this.getPropagatedLevel(_snowmanxxxxx, id, this.getCurrentLevelFromSection(_snowmanxxxxxxx, _snowmanxxxxx));
               if (_snowman > _snowmanxxxxxxxx) {
                  _snowman = _snowmanxxxxxxxx;
               }

               if (_snowman == 0) {
                  return _snowman;
               }
            }
         } else if (_snowmanxxxx != Direction.DOWN) {
            for (_snowmanxxxxx = BlockPos.removeChunkSectionLocalY(_snowmanxxxxx);
               !this.lightStorage.hasSection(_snowmanxxxxxx) && !this.lightStorage.isAtOrAboveTopmostSection(_snowmanxxxxxx);
               _snowmanxxxxx = BlockPos.add(_snowmanxxxxx, 0, 16, 0)
            ) {
               _snowmanxxxxxx = ChunkSectionPos.offset(_snowmanxxxxxx, Direction.UP);
            }

            ChunkNibbleArray _snowmanxxxxxxxxx = this.lightStorage.getLightSection(_snowmanxxxxxx, true);
            if (_snowmanxxxxx != excludedId) {
               int _snowmanxxxxxxxxxx;
               if (_snowmanxxxxxxxxx != null) {
                  _snowmanxxxxxxxxxx = this.getPropagatedLevel(_snowmanxxxxx, id, this.getCurrentLevelFromSection(_snowmanxxxxxxxxx, _snowmanxxxxx));
               } else {
                  _snowmanxxxxxxxxxx = this.lightStorage.isSectionEnabled(_snowmanxxxxxx) ? 0 : 15;
               }

               if (_snowman > _snowmanxxxxxxxxxx) {
                  _snowman = _snowmanxxxxxxxxxx;
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
   protected void resetLevel(long id) {
      this.lightStorage.updateAll();
      long _snowman = ChunkSectionPos.fromBlockPos(id);
      if (this.lightStorage.hasSection(_snowman)) {
         super.resetLevel(id);
      } else {
         for (id = BlockPos.removeChunkSectionLocalY(id);
            !this.lightStorage.hasSection(_snowman) && !this.lightStorage.isAtOrAboveTopmostSection(_snowman);
            id = BlockPos.add(id, 0, 16, 0)
         ) {
            _snowman = ChunkSectionPos.offset(_snowman, Direction.UP);
         }

         if (this.lightStorage.hasSection(_snowman)) {
            super.resetLevel(id);
         }
      }
   }

   @Override
   public String displaySectionLevel(long sectionPos) {
      return super.displaySectionLevel(sectionPos) + (this.lightStorage.isAtOrAboveTopmostSection(sectionPos) ? "*" : "");
   }
}
