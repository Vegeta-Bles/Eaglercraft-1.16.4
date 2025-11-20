package net.minecraft.entity.ai.pathing;

import javax.annotation.Nullable;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.chunk.ChunkCache;

public class AmphibiousPathNodeMaker extends LandPathNodeMaker {
   private float oldWalkablePenalty;
   private float oldWaterBorderPenalty;

   public AmphibiousPathNodeMaker() {
   }

   @Override
   public void init(ChunkCache cachedWorld, MobEntity entity) {
      super.init(cachedWorld, entity);
      entity.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
      this.oldWalkablePenalty = entity.getPathfindingPenalty(PathNodeType.WALKABLE);
      entity.setPathfindingPenalty(PathNodeType.WALKABLE, 6.0F);
      this.oldWaterBorderPenalty = entity.getPathfindingPenalty(PathNodeType.WATER_BORDER);
      entity.setPathfindingPenalty(PathNodeType.WATER_BORDER, 4.0F);
   }

   @Override
   public void clear() {
      this.entity.setPathfindingPenalty(PathNodeType.WALKABLE, this.oldWalkablePenalty);
      this.entity.setPathfindingPenalty(PathNodeType.WATER_BORDER, this.oldWaterBorderPenalty);
      super.clear();
   }

   @Override
   public PathNode getStart() {
      return this.getNode(
         MathHelper.floor(this.entity.getBoundingBox().minX),
         MathHelper.floor(this.entity.getBoundingBox().minY + 0.5),
         MathHelper.floor(this.entity.getBoundingBox().minZ)
      );
   }

   @Override
   public TargetPathNode getNode(double x, double y, double z) {
      return new TargetPathNode(this.getNode(MathHelper.floor(x), MathHelper.floor(y + 0.5), MathHelper.floor(z)));
   }

   @Override
   public int getSuccessors(PathNode[] successors, PathNode node) {
      int _snowman = 0;
      int _snowmanx = 1;
      BlockPos _snowmanxx = new BlockPos(node.x, node.y, node.z);
      double _snowmanxxx = this.getFeetY(_snowmanxx);
      PathNode _snowmanxxxx = this.getPathNode(node.x, node.y, node.z + 1, 1, _snowmanxxx);
      PathNode _snowmanxxxxx = this.getPathNode(node.x - 1, node.y, node.z, 1, _snowmanxxx);
      PathNode _snowmanxxxxxx = this.getPathNode(node.x + 1, node.y, node.z, 1, _snowmanxxx);
      PathNode _snowmanxxxxxxx = this.getPathNode(node.x, node.y, node.z - 1, 1, _snowmanxxx);
      PathNode _snowmanxxxxxxxx = this.getPathNode(node.x, node.y + 1, node.z, 0, _snowmanxxx);
      PathNode _snowmanxxxxxxxxx = this.getPathNode(node.x, node.y - 1, node.z, 1, _snowmanxxx);
      if (_snowmanxxxx != null && !_snowmanxxxx.visited) {
         successors[_snowman++] = _snowmanxxxx;
      }

      if (_snowmanxxxxx != null && !_snowmanxxxxx.visited) {
         successors[_snowman++] = _snowmanxxxxx;
      }

      if (_snowmanxxxxxx != null && !_snowmanxxxxxx.visited) {
         successors[_snowman++] = _snowmanxxxxxx;
      }

      if (_snowmanxxxxxxx != null && !_snowmanxxxxxxx.visited) {
         successors[_snowman++] = _snowmanxxxxxxx;
      }

      if (_snowmanxxxxxxxx != null && !_snowmanxxxxxxxx.visited) {
         successors[_snowman++] = _snowmanxxxxxxxx;
      }

      if (_snowmanxxxxxxxxx != null && !_snowmanxxxxxxxxx.visited) {
         successors[_snowman++] = _snowmanxxxxxxxxx;
      }

      boolean _snowmanxxxxxxxxxx = _snowmanxxxxxxx == null || _snowmanxxxxxxx.type == PathNodeType.OPEN || _snowmanxxxxxxx.penalty != 0.0F;
      boolean _snowmanxxxxxxxxxxx = _snowmanxxxx == null || _snowmanxxxx.type == PathNodeType.OPEN || _snowmanxxxx.penalty != 0.0F;
      boolean _snowmanxxxxxxxxxxxx = _snowmanxxxxxx == null || _snowmanxxxxxx.type == PathNodeType.OPEN || _snowmanxxxxxx.penalty != 0.0F;
      boolean _snowmanxxxxxxxxxxxxx = _snowmanxxxxx == null || _snowmanxxxxx.type == PathNodeType.OPEN || _snowmanxxxxx.penalty != 0.0F;
      if (_snowmanxxxxxxxxxx && _snowmanxxxxxxxxxxxxx) {
         PathNode _snowmanxxxxxxxxxxxxxx = this.getPathNode(node.x - 1, node.y, node.z - 1, 1, _snowmanxxx);
         if (_snowmanxxxxxxxxxxxxxx != null && !_snowmanxxxxxxxxxxxxxx.visited) {
            successors[_snowman++] = _snowmanxxxxxxxxxxxxxx;
         }
      }

      if (_snowmanxxxxxxxxxx && _snowmanxxxxxxxxxxxx) {
         PathNode _snowmanxxxxxxxxxxxxxx = this.getPathNode(node.x + 1, node.y, node.z - 1, 1, _snowmanxxx);
         if (_snowmanxxxxxxxxxxxxxx != null && !_snowmanxxxxxxxxxxxxxx.visited) {
            successors[_snowman++] = _snowmanxxxxxxxxxxxxxx;
         }
      }

      if (_snowmanxxxxxxxxxxx && _snowmanxxxxxxxxxxxxx) {
         PathNode _snowmanxxxxxxxxxxxxxx = this.getPathNode(node.x - 1, node.y, node.z + 1, 1, _snowmanxxx);
         if (_snowmanxxxxxxxxxxxxxx != null && !_snowmanxxxxxxxxxxxxxx.visited) {
            successors[_snowman++] = _snowmanxxxxxxxxxxxxxx;
         }
      }

      if (_snowmanxxxxxxxxxxx && _snowmanxxxxxxxxxxxx) {
         PathNode _snowmanxxxxxxxxxxxxxx = this.getPathNode(node.x + 1, node.y, node.z + 1, 1, _snowmanxxx);
         if (_snowmanxxxxxxxxxxxxxx != null && !_snowmanxxxxxxxxxxxxxx.visited) {
            successors[_snowman++] = _snowmanxxxxxxxxxxxxxx;
         }
      }

      return _snowman;
   }

   private double getFeetY(BlockPos pos) {
      if (!this.entity.isTouchingWater()) {
         BlockPos _snowman = pos.down();
         VoxelShape _snowmanx = this.cachedWorld.getBlockState(_snowman).getCollisionShape(this.cachedWorld, _snowman);
         return (double)_snowman.getY() + (_snowmanx.isEmpty() ? 0.0 : _snowmanx.getMax(Direction.Axis.Y));
      } else {
         return (double)pos.getY() + 0.5;
      }
   }

   @Nullable
   private PathNode getPathNode(int x, int y, int z, int maxYStep, double prevFeetY) {
      PathNode _snowman = null;
      BlockPos _snowmanx = new BlockPos(x, y, z);
      double _snowmanxx = this.getFeetY(_snowmanx);
      if (_snowmanxx - prevFeetY > 1.125) {
         return null;
      } else {
         PathNodeType _snowmanxxx = this.getNodeType(
            this.cachedWorld, x, y, z, this.entity, this.entityBlockXSize, this.entityBlockYSize, this.entityBlockZSize, false, false
         );
         float _snowmanxxxx = this.entity.getPathfindingPenalty(_snowmanxxx);
         double _snowmanxxxxx = (double)this.entity.getWidth() / 2.0;
         if (_snowmanxxxx >= 0.0F) {
            _snowman = this.getNode(x, y, z);
            _snowman.type = _snowmanxxx;
            _snowman.penalty = Math.max(_snowman.penalty, _snowmanxxxx);
         }

         if (_snowmanxxx != PathNodeType.WATER && _snowmanxxx != PathNodeType.WALKABLE) {
            if (_snowman == null && maxYStep > 0 && _snowmanxxx != PathNodeType.FENCE && _snowmanxxx != PathNodeType.UNPASSABLE_RAIL && _snowmanxxx != PathNodeType.TRAPDOOR) {
               _snowman = this.getPathNode(x, y + 1, z, maxYStep - 1, prevFeetY);
            }

            if (_snowmanxxx == PathNodeType.OPEN) {
               Box _snowmanxxxxxx = new Box(
                  (double)x - _snowmanxxxxx + 0.5,
                  (double)y + 0.001,
                  (double)z - _snowmanxxxxx + 0.5,
                  (double)x + _snowmanxxxxx + 0.5,
                  (double)((float)y + this.entity.getHeight()),
                  (double)z + _snowmanxxxxx + 0.5
               );
               if (!this.entity.world.isSpaceEmpty(this.entity, _snowmanxxxxxx)) {
                  return null;
               }

               PathNodeType _snowmanxxxxxxx = this.getNodeType(
                  this.cachedWorld, x, y - 1, z, this.entity, this.entityBlockXSize, this.entityBlockYSize, this.entityBlockZSize, false, false
               );
               if (_snowmanxxxxxxx == PathNodeType.BLOCKED) {
                  _snowman = this.getNode(x, y, z);
                  _snowman.type = PathNodeType.WALKABLE;
                  _snowman.penalty = Math.max(_snowman.penalty, _snowmanxxxx);
                  return _snowman;
               }

               if (_snowmanxxxxxxx == PathNodeType.WATER) {
                  _snowman = this.getNode(x, y, z);
                  _snowman.type = PathNodeType.WATER;
                  _snowman.penalty = Math.max(_snowman.penalty, _snowmanxxxx);
                  return _snowman;
               }

               int _snowmanxxxxxxxx = 0;

               while (y > 0 && _snowmanxxx == PathNodeType.OPEN) {
                  y--;
                  if (_snowmanxxxxxxxx++ >= this.entity.getSafeFallDistance()) {
                     return null;
                  }

                  _snowmanxxx = this.getNodeType(
                     this.cachedWorld, x, y, z, this.entity, this.entityBlockXSize, this.entityBlockYSize, this.entityBlockZSize, false, false
                  );
                  _snowmanxxxx = this.entity.getPathfindingPenalty(_snowmanxxx);
                  if (_snowmanxxx != PathNodeType.OPEN && _snowmanxxxx >= 0.0F) {
                     _snowman = this.getNode(x, y, z);
                     _snowman.type = _snowmanxxx;
                     _snowman.penalty = Math.max(_snowman.penalty, _snowmanxxxx);
                     break;
                  }

                  if (_snowmanxxxx < 0.0F) {
                     return null;
                  }
               }
            }

            return _snowman;
         } else {
            if (y < this.entity.world.getSeaLevel() - 10 && _snowman != null) {
               _snowman.penalty++;
            }

            return _snowman;
         }
      }
   }

   @Override
   protected PathNodeType adjustNodeType(BlockView world, boolean canOpenDoors, boolean canEnterOpenDoors, BlockPos pos, PathNodeType type) {
      if (type == PathNodeType.RAIL
         && !(world.getBlockState(pos).getBlock() instanceof AbstractRailBlock)
         && !(world.getBlockState(pos.down()).getBlock() instanceof AbstractRailBlock)) {
         type = PathNodeType.UNPASSABLE_RAIL;
      }

      if (type == PathNodeType.DOOR_OPEN || type == PathNodeType.DOOR_WOOD_CLOSED || type == PathNodeType.DOOR_IRON_CLOSED) {
         type = PathNodeType.BLOCKED;
      }

      if (type == PathNodeType.LEAVES) {
         type = PathNodeType.BLOCKED;
      }

      return type;
   }

   @Override
   public PathNodeType getDefaultNodeType(BlockView world, int x, int y, int z) {
      BlockPos.Mutable _snowman = new BlockPos.Mutable();
      PathNodeType _snowmanx = getCommonNodeType(world, _snowman.set(x, y, z));
      if (_snowmanx == PathNodeType.WATER) {
         for (Direction _snowmanxx : Direction.values()) {
            PathNodeType _snowmanxxx = getCommonNodeType(world, _snowman.set(x, y, z).move(_snowmanxx));
            if (_snowmanxxx == PathNodeType.BLOCKED) {
               return PathNodeType.WATER_BORDER;
            }
         }

         return PathNodeType.WATER;
      } else {
         if (_snowmanx == PathNodeType.OPEN && y >= 1) {
            BlockState _snowmanxxx = world.getBlockState(new BlockPos(x, y - 1, z));
            PathNodeType _snowmanxxxx = getCommonNodeType(world, _snowman.set(x, y - 1, z));
            if (_snowmanxxxx != PathNodeType.WALKABLE && _snowmanxxxx != PathNodeType.OPEN && _snowmanxxxx != PathNodeType.LAVA) {
               _snowmanx = PathNodeType.WALKABLE;
            } else {
               _snowmanx = PathNodeType.OPEN;
            }

            if (_snowmanxxxx == PathNodeType.DAMAGE_FIRE || _snowmanxxx.isOf(Blocks.MAGMA_BLOCK) || _snowmanxxx.isIn(BlockTags.CAMPFIRES)) {
               _snowmanx = PathNodeType.DAMAGE_FIRE;
            }

            if (_snowmanxxxx == PathNodeType.DAMAGE_CACTUS) {
               _snowmanx = PathNodeType.DAMAGE_CACTUS;
            }

            if (_snowmanxxxx == PathNodeType.DAMAGE_OTHER) {
               _snowmanx = PathNodeType.DAMAGE_OTHER;
            }
         }

         if (_snowmanx == PathNodeType.WALKABLE) {
            _snowmanx = getNodeTypeFromNeighbors(world, _snowman.set(x, y, z), _snowmanx);
         }

         return _snowmanx;
      }
   }
}
