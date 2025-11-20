package net.minecraft.entity.ai.pathing;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.chunk.ChunkCache;

public class BirdPathNodeMaker extends LandPathNodeMaker {
   public BirdPathNodeMaker() {
   }

   @Override
   public void init(ChunkCache cachedWorld, MobEntity entity) {
      super.init(cachedWorld, entity);
      this.waterPathNodeTypeWeight = entity.getPathfindingPenalty(PathNodeType.WATER);
   }

   @Override
   public void clear() {
      this.entity.setPathfindingPenalty(PathNodeType.WATER, this.waterPathNodeTypeWeight);
      super.clear();
   }

   @Override
   public PathNode getStart() {
      int _snowman;
      if (this.canSwim() && this.entity.isTouchingWater()) {
         _snowman = MathHelper.floor(this.entity.getY());
         BlockPos.Mutable _snowmanx = new BlockPos.Mutable(this.entity.getX(), (double)_snowman, this.entity.getZ());

         for (Block _snowmanxx = this.cachedWorld.getBlockState(_snowmanx).getBlock(); _snowmanxx == Blocks.WATER; _snowmanxx = this.cachedWorld.getBlockState(_snowmanx).getBlock()) {
            _snowmanx.set(this.entity.getX(), (double)(++_snowman), this.entity.getZ());
         }
      } else {
         _snowman = MathHelper.floor(this.entity.getY() + 0.5);
      }

      BlockPos _snowmanx = this.entity.getBlockPos();
      PathNodeType _snowmanxx = this.getNodeType(this.entity, _snowmanx.getX(), _snowman, _snowmanx.getZ());
      if (this.entity.getPathfindingPenalty(_snowmanxx) < 0.0F) {
         Set<BlockPos> _snowmanxxx = Sets.newHashSet();
         _snowmanxxx.add(new BlockPos(this.entity.getBoundingBox().minX, (double)_snowman, this.entity.getBoundingBox().minZ));
         _snowmanxxx.add(new BlockPos(this.entity.getBoundingBox().minX, (double)_snowman, this.entity.getBoundingBox().maxZ));
         _snowmanxxx.add(new BlockPos(this.entity.getBoundingBox().maxX, (double)_snowman, this.entity.getBoundingBox().minZ));
         _snowmanxxx.add(new BlockPos(this.entity.getBoundingBox().maxX, (double)_snowman, this.entity.getBoundingBox().maxZ));

         for (BlockPos _snowmanxxxx : _snowmanxxx) {
            PathNodeType _snowmanxxxxx = this.getNodeType(this.entity, _snowmanxxxx);
            if (this.entity.getPathfindingPenalty(_snowmanxxxxx) >= 0.0F) {
               return super.getNode(_snowmanxxxx.getX(), _snowmanxxxx.getY(), _snowmanxxxx.getZ());
            }
         }
      }

      return super.getNode(_snowmanx.getX(), _snowman, _snowmanx.getZ());
   }

   @Override
   public TargetPathNode getNode(double x, double y, double z) {
      return new TargetPathNode(super.getNode(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z)));
   }

   @Override
   public int getSuccessors(PathNode[] successors, PathNode node) {
      int _snowman = 0;
      PathNode _snowmanx = this.getNode(node.x, node.y, node.z + 1);
      if (this.unvisited(_snowmanx)) {
         successors[_snowman++] = _snowmanx;
      }

      PathNode _snowmanxx = this.getNode(node.x - 1, node.y, node.z);
      if (this.unvisited(_snowmanxx)) {
         successors[_snowman++] = _snowmanxx;
      }

      PathNode _snowmanxxx = this.getNode(node.x + 1, node.y, node.z);
      if (this.unvisited(_snowmanxxx)) {
         successors[_snowman++] = _snowmanxxx;
      }

      PathNode _snowmanxxxx = this.getNode(node.x, node.y, node.z - 1);
      if (this.unvisited(_snowmanxxxx)) {
         successors[_snowman++] = _snowmanxxxx;
      }

      PathNode _snowmanxxxxx = this.getNode(node.x, node.y + 1, node.z);
      if (this.unvisited(_snowmanxxxxx)) {
         successors[_snowman++] = _snowmanxxxxx;
      }

      PathNode _snowmanxxxxxx = this.getNode(node.x, node.y - 1, node.z);
      if (this.unvisited(_snowmanxxxxxx)) {
         successors[_snowman++] = _snowmanxxxxxx;
      }

      PathNode _snowmanxxxxxxx = this.getNode(node.x, node.y + 1, node.z + 1);
      if (this.unvisited(_snowmanxxxxxxx) && this.isPassable(_snowmanx) && this.isPassable(_snowmanxxxxx)) {
         successors[_snowman++] = _snowmanxxxxxxx;
      }

      PathNode _snowmanxxxxxxxx = this.getNode(node.x - 1, node.y + 1, node.z);
      if (this.unvisited(_snowmanxxxxxxxx) && this.isPassable(_snowmanxx) && this.isPassable(_snowmanxxxxx)) {
         successors[_snowman++] = _snowmanxxxxxxxx;
      }

      PathNode _snowmanxxxxxxxxx = this.getNode(node.x + 1, node.y + 1, node.z);
      if (this.unvisited(_snowmanxxxxxxxxx) && this.isPassable(_snowmanxxx) && this.isPassable(_snowmanxxxxx)) {
         successors[_snowman++] = _snowmanxxxxxxxxx;
      }

      PathNode _snowmanxxxxxxxxxx = this.getNode(node.x, node.y + 1, node.z - 1);
      if (this.unvisited(_snowmanxxxxxxxxxx) && this.isPassable(_snowmanxxxx) && this.isPassable(_snowmanxxxxx)) {
         successors[_snowman++] = _snowmanxxxxxxxxxx;
      }

      PathNode _snowmanxxxxxxxxxxx = this.getNode(node.x, node.y - 1, node.z + 1);
      if (this.unvisited(_snowmanxxxxxxxxxxx) && this.isPassable(_snowmanx) && this.isPassable(_snowmanxxxxxx)) {
         successors[_snowman++] = _snowmanxxxxxxxxxxx;
      }

      PathNode _snowmanxxxxxxxxxxxx = this.getNode(node.x - 1, node.y - 1, node.z);
      if (this.unvisited(_snowmanxxxxxxxxxxxx) && this.isPassable(_snowmanxx) && this.isPassable(_snowmanxxxxxx)) {
         successors[_snowman++] = _snowmanxxxxxxxxxxxx;
      }

      PathNode _snowmanxxxxxxxxxxxxx = this.getNode(node.x + 1, node.y - 1, node.z);
      if (this.unvisited(_snowmanxxxxxxxxxxxxx) && this.isPassable(_snowmanxxx) && this.isPassable(_snowmanxxxxxx)) {
         successors[_snowman++] = _snowmanxxxxxxxxxxxxx;
      }

      PathNode _snowmanxxxxxxxxxxxxxx = this.getNode(node.x, node.y - 1, node.z - 1);
      if (this.unvisited(_snowmanxxxxxxxxxxxxxx) && this.isPassable(_snowmanxxxx) && this.isPassable(_snowmanxxxxxx)) {
         successors[_snowman++] = _snowmanxxxxxxxxxxxxxx;
      }

      PathNode _snowmanxxxxxxxxxxxxxxx = this.getNode(node.x + 1, node.y, node.z - 1);
      if (this.unvisited(_snowmanxxxxxxxxxxxxxxx) && this.isPassable(_snowmanxxxx) && this.isPassable(_snowmanxxx)) {
         successors[_snowman++] = _snowmanxxxxxxxxxxxxxxx;
      }

      PathNode _snowmanxxxxxxxxxxxxxxxx = this.getNode(node.x + 1, node.y, node.z + 1);
      if (this.unvisited(_snowmanxxxxxxxxxxxxxxxx) && this.isPassable(_snowmanx) && this.isPassable(_snowmanxxx)) {
         successors[_snowman++] = _snowmanxxxxxxxxxxxxxxxx;
      }

      PathNode _snowmanxxxxxxxxxxxxxxxxx = this.getNode(node.x - 1, node.y, node.z - 1);
      if (this.unvisited(_snowmanxxxxxxxxxxxxxxxxx) && this.isPassable(_snowmanxxxx) && this.isPassable(_snowmanxx)) {
         successors[_snowman++] = _snowmanxxxxxxxxxxxxxxxxx;
      }

      PathNode _snowmanxxxxxxxxxxxxxxxxxx = this.getNode(node.x - 1, node.y, node.z + 1);
      if (this.unvisited(_snowmanxxxxxxxxxxxxxxxxxx) && this.isPassable(_snowmanx) && this.isPassable(_snowmanxx)) {
         successors[_snowman++] = _snowmanxxxxxxxxxxxxxxxxxx;
      }

      PathNode _snowmanxxxxxxxxxxxxxxxxxxx = this.getNode(node.x + 1, node.y + 1, node.z - 1);
      if (this.unvisited(_snowmanxxxxxxxxxxxxxxxxxxx)
         && this.isPassable(_snowmanxxxxxxxxxxxxxxx)
         && this.isPassable(_snowmanxxxx)
         && this.isPassable(_snowmanxxx)
         && this.isPassable(_snowmanxxxxx)
         && this.isPassable(_snowmanxxxxxxxxxx)
         && this.isPassable(_snowmanxxxxxxxxx)) {
         successors[_snowman++] = _snowmanxxxxxxxxxxxxxxxxxxx;
      }

      PathNode _snowmanxxxxxxxxxxxxxxxxxxxx = this.getNode(node.x + 1, node.y + 1, node.z + 1);
      if (this.unvisited(_snowmanxxxxxxxxxxxxxxxxxxxx)
         && this.isPassable(_snowmanxxxxxxxxxxxxxxxx)
         && this.isPassable(_snowmanx)
         && this.isPassable(_snowmanxxx)
         && this.isPassable(_snowmanxxxxx)
         && this.isPassable(_snowmanxxxxxxx)
         && this.isPassable(_snowmanxxxxxxxxx)) {
         successors[_snowman++] = _snowmanxxxxxxxxxxxxxxxxxxxx;
      }

      PathNode _snowmanxxxxxxxxxxxxxxxxxxxxx = this.getNode(node.x - 1, node.y + 1, node.z - 1);
      if (this.unvisited(_snowmanxxxxxxxxxxxxxxxxxxxxx)
         && this.isPassable(_snowmanxxxxxxxxxxxxxxxxx)
         && this.isPassable(_snowmanxxxx)
         && this.isPassable(_snowmanxx) & this.isPassable(_snowmanxxxxx)
         && this.isPassable(_snowmanxxxxxxxxxx)
         && this.isPassable(_snowmanxxxxxxxx)) {
         successors[_snowman++] = _snowmanxxxxxxxxxxxxxxxxxxxxx;
      }

      PathNode _snowmanxxxxxxxxxxxxxxxxxxxxxx = this.getNode(node.x - 1, node.y + 1, node.z + 1);
      if (this.unvisited(_snowmanxxxxxxxxxxxxxxxxxxxxxx)
         && this.isPassable(_snowmanxxxxxxxxxxxxxxxxxx)
         && this.isPassable(_snowmanx)
         && this.isPassable(_snowmanxx) & this.isPassable(_snowmanxxxxx)
         && this.isPassable(_snowmanxxxxxxx)
         && this.isPassable(_snowmanxxxxxxxx)) {
         successors[_snowman++] = _snowmanxxxxxxxxxxxxxxxxxxxxxx;
      }

      PathNode _snowmanxxxxxxxxxxxxxxxxxxxxxxx = this.getNode(node.x + 1, node.y - 1, node.z - 1);
      if (this.unvisited(_snowmanxxxxxxxxxxxxxxxxxxxxxxx)
         && this.isPassable(_snowmanxxxxxxxxxxxxxxx)
         && this.isPassable(_snowmanxxxx)
         && this.isPassable(_snowmanxxx)
         && this.isPassable(_snowmanxxxxxx)
         && this.isPassable(_snowmanxxxxxxxxxxxxxx)
         && this.isPassable(_snowmanxxxxxxxxxxxxx)) {
         successors[_snowman++] = _snowmanxxxxxxxxxxxxxxxxxxxxxxx;
      }

      PathNode _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = this.getNode(node.x + 1, node.y - 1, node.z + 1);
      if (this.unvisited(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx)
         && this.isPassable(_snowmanxxxxxxxxxxxxxxxx)
         && this.isPassable(_snowmanx)
         && this.isPassable(_snowmanxxx)
         && this.isPassable(_snowmanxxxxxx)
         && this.isPassable(_snowmanxxxxxxxxxxx)
         && this.isPassable(_snowmanxxxxxxxxxxxxx)) {
         successors[_snowman++] = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx;
      }

      PathNode _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = this.getNode(node.x - 1, node.y - 1, node.z - 1);
      if (this.unvisited(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx)
         && this.isPassable(_snowmanxxxxxxxxxxxxxxxxx)
         && this.isPassable(_snowmanxxxx)
         && this.isPassable(_snowmanxx)
         && this.isPassable(_snowmanxxxxxx)
         && this.isPassable(_snowmanxxxxxxxxxxxxxx)
         && this.isPassable(_snowmanxxxxxxxxxxxx)) {
         successors[_snowman++] = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx;
      }

      PathNode _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = this.getNode(node.x - 1, node.y - 1, node.z + 1);
      if (this.unvisited(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx)
         && this.isPassable(_snowmanxxxxxxxxxxxxxxxxxx)
         && this.isPassable(_snowmanx)
         && this.isPassable(_snowmanxx)
         && this.isPassable(_snowmanxxxxxx)
         && this.isPassable(_snowmanxxxxxxxxxxx)
         && this.isPassable(_snowmanxxxxxxxxxxxx)) {
         successors[_snowman++] = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx;
      }

      return _snowman;
   }

   private boolean isPassable(@Nullable PathNode node) {
      return node != null && node.penalty >= 0.0F;
   }

   private boolean unvisited(@Nullable PathNode node) {
      return node != null && !node.visited;
   }

   @Nullable
   @Override
   protected PathNode getNode(int x, int y, int z) {
      PathNode _snowman = null;
      PathNodeType _snowmanx = this.getNodeType(this.entity, x, y, z);
      float _snowmanxx = this.entity.getPathfindingPenalty(_snowmanx);
      if (_snowmanxx >= 0.0F) {
         _snowman = super.getNode(x, y, z);
         _snowman.type = _snowmanx;
         _snowman.penalty = Math.max(_snowman.penalty, _snowmanxx);
         if (_snowmanx == PathNodeType.WALKABLE) {
            _snowman.penalty++;
         }
      }

      return _snowmanx != PathNodeType.OPEN && _snowmanx != PathNodeType.WALKABLE ? _snowman : _snowman;
   }

   @Override
   public PathNodeType getNodeType(
      BlockView world, int x, int y, int z, MobEntity mob, int sizeX, int sizeY, int sizeZ, boolean canOpenDoors, boolean canEnterOpenDoors
   ) {
      EnumSet<PathNodeType> _snowman = EnumSet.noneOf(PathNodeType.class);
      PathNodeType _snowmanx = PathNodeType.BLOCKED;
      BlockPos _snowmanxx = mob.getBlockPos();
      _snowmanx = this.findNearbyNodeTypes(world, x, y, z, sizeX, sizeY, sizeZ, canOpenDoors, canEnterOpenDoors, _snowman, _snowmanx, _snowmanxx);
      if (_snowman.contains(PathNodeType.FENCE)) {
         return PathNodeType.FENCE;
      } else {
         PathNodeType _snowmanxxx = PathNodeType.BLOCKED;

         for (PathNodeType _snowmanxxxx : _snowman) {
            if (mob.getPathfindingPenalty(_snowmanxxxx) < 0.0F) {
               return _snowmanxxxx;
            }

            if (mob.getPathfindingPenalty(_snowmanxxxx) >= mob.getPathfindingPenalty(_snowmanxxx)) {
               _snowmanxxx = _snowmanxxxx;
            }
         }

         return _snowmanx == PathNodeType.OPEN && mob.getPathfindingPenalty(_snowmanxxx) == 0.0F ? PathNodeType.OPEN : _snowmanxxx;
      }
   }

   @Override
   public PathNodeType getDefaultNodeType(BlockView world, int x, int y, int z) {
      BlockPos.Mutable _snowman = new BlockPos.Mutable();
      PathNodeType _snowmanx = getCommonNodeType(world, _snowman.set(x, y, z));
      if (_snowmanx == PathNodeType.OPEN && y >= 1) {
         BlockState _snowmanxx = world.getBlockState(_snowman.set(x, y - 1, z));
         PathNodeType _snowmanxxx = getCommonNodeType(world, _snowman.set(x, y - 1, z));
         if (_snowmanxxx == PathNodeType.DAMAGE_FIRE || _snowmanxx.isOf(Blocks.MAGMA_BLOCK) || _snowmanxxx == PathNodeType.LAVA || _snowmanxx.isIn(BlockTags.CAMPFIRES)) {
            _snowmanx = PathNodeType.DAMAGE_FIRE;
         } else if (_snowmanxxx == PathNodeType.DAMAGE_CACTUS) {
            _snowmanx = PathNodeType.DAMAGE_CACTUS;
         } else if (_snowmanxxx == PathNodeType.DAMAGE_OTHER) {
            _snowmanx = PathNodeType.DAMAGE_OTHER;
         } else if (_snowmanxxx == PathNodeType.COCOA) {
            _snowmanx = PathNodeType.COCOA;
         } else if (_snowmanxxx == PathNodeType.FENCE) {
            _snowmanx = PathNodeType.FENCE;
         } else {
            _snowmanx = _snowmanxxx != PathNodeType.WALKABLE && _snowmanxxx != PathNodeType.OPEN && _snowmanxxx != PathNodeType.WATER ? PathNodeType.WALKABLE : PathNodeType.OPEN;
         }
      }

      if (_snowmanx == PathNodeType.WALKABLE || _snowmanx == PathNodeType.OPEN) {
         _snowmanx = getNodeTypeFromNeighbors(world, _snowman.set(x, y, z), _snowmanx);
      }

      return _snowmanx;
   }

   private PathNodeType getNodeType(MobEntity entity, BlockPos pos) {
      return this.getNodeType(entity, pos.getX(), pos.getY(), pos.getZ());
   }

   private PathNodeType getNodeType(MobEntity entity, int x, int y, int z) {
      return this.getNodeType(
         this.cachedWorld, x, y, z, entity, this.entityBlockXSize, this.entityBlockYSize, this.entityBlockZSize, this.canOpenDoors(), this.canEnterOpenDoors()
      );
   }
}
