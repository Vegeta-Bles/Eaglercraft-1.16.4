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
      int i;
      if (this.canSwim() && this.entity.isTouchingWater()) {
         i = MathHelper.floor(this.entity.getY());
         BlockPos.Mutable lv = new BlockPos.Mutable(this.entity.getX(), (double)i, this.entity.getZ());

         for (Block lv2 = this.cachedWorld.getBlockState(lv).getBlock(); lv2 == Blocks.WATER; lv2 = this.cachedWorld.getBlockState(lv).getBlock()) {
            lv.set(this.entity.getX(), (double)(++i), this.entity.getZ());
         }
      } else {
         i = MathHelper.floor(this.entity.getY() + 0.5);
      }

      BlockPos lv3 = this.entity.getBlockPos();
      PathNodeType lv4 = this.getNodeType(this.entity, lv3.getX(), i, lv3.getZ());
      if (this.entity.getPathfindingPenalty(lv4) < 0.0F) {
         Set<BlockPos> set = Sets.newHashSet();
         set.add(new BlockPos(this.entity.getBoundingBox().minX, (double)i, this.entity.getBoundingBox().minZ));
         set.add(new BlockPos(this.entity.getBoundingBox().minX, (double)i, this.entity.getBoundingBox().maxZ));
         set.add(new BlockPos(this.entity.getBoundingBox().maxX, (double)i, this.entity.getBoundingBox().minZ));
         set.add(new BlockPos(this.entity.getBoundingBox().maxX, (double)i, this.entity.getBoundingBox().maxZ));

         for (BlockPos lv5 : set) {
            PathNodeType lv6 = this.getNodeType(this.entity, lv5);
            if (this.entity.getPathfindingPenalty(lv6) >= 0.0F) {
               return super.getNode(lv5.getX(), lv5.getY(), lv5.getZ());
            }
         }
      }

      return super.getNode(lv3.getX(), i, lv3.getZ());
   }

   @Override
   public TargetPathNode getNode(double x, double y, double z) {
      return new TargetPathNode(super.getNode(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z)));
   }

   @Override
   public int getSuccessors(PathNode[] successors, PathNode node) {
      int i = 0;
      PathNode lv = this.getNode(node.x, node.y, node.z + 1);
      if (this.unvisited(lv)) {
         successors[i++] = lv;
      }

      PathNode lv2 = this.getNode(node.x - 1, node.y, node.z);
      if (this.unvisited(lv2)) {
         successors[i++] = lv2;
      }

      PathNode lv3 = this.getNode(node.x + 1, node.y, node.z);
      if (this.unvisited(lv3)) {
         successors[i++] = lv3;
      }

      PathNode lv4 = this.getNode(node.x, node.y, node.z - 1);
      if (this.unvisited(lv4)) {
         successors[i++] = lv4;
      }

      PathNode lv5 = this.getNode(node.x, node.y + 1, node.z);
      if (this.unvisited(lv5)) {
         successors[i++] = lv5;
      }

      PathNode lv6 = this.getNode(node.x, node.y - 1, node.z);
      if (this.unvisited(lv6)) {
         successors[i++] = lv6;
      }

      PathNode lv7 = this.getNode(node.x, node.y + 1, node.z + 1);
      if (this.unvisited(lv7) && this.isPassable(lv) && this.isPassable(lv5)) {
         successors[i++] = lv7;
      }

      PathNode lv8 = this.getNode(node.x - 1, node.y + 1, node.z);
      if (this.unvisited(lv8) && this.isPassable(lv2) && this.isPassable(lv5)) {
         successors[i++] = lv8;
      }

      PathNode lv9 = this.getNode(node.x + 1, node.y + 1, node.z);
      if (this.unvisited(lv9) && this.isPassable(lv3) && this.isPassable(lv5)) {
         successors[i++] = lv9;
      }

      PathNode lv10 = this.getNode(node.x, node.y + 1, node.z - 1);
      if (this.unvisited(lv10) && this.isPassable(lv4) && this.isPassable(lv5)) {
         successors[i++] = lv10;
      }

      PathNode lv11 = this.getNode(node.x, node.y - 1, node.z + 1);
      if (this.unvisited(lv11) && this.isPassable(lv) && this.isPassable(lv6)) {
         successors[i++] = lv11;
      }

      PathNode lv12 = this.getNode(node.x - 1, node.y - 1, node.z);
      if (this.unvisited(lv12) && this.isPassable(lv2) && this.isPassable(lv6)) {
         successors[i++] = lv12;
      }

      PathNode lv13 = this.getNode(node.x + 1, node.y - 1, node.z);
      if (this.unvisited(lv13) && this.isPassable(lv3) && this.isPassable(lv6)) {
         successors[i++] = lv13;
      }

      PathNode lv14 = this.getNode(node.x, node.y - 1, node.z - 1);
      if (this.unvisited(lv14) && this.isPassable(lv4) && this.isPassable(lv6)) {
         successors[i++] = lv14;
      }

      PathNode lv15 = this.getNode(node.x + 1, node.y, node.z - 1);
      if (this.unvisited(lv15) && this.isPassable(lv4) && this.isPassable(lv3)) {
         successors[i++] = lv15;
      }

      PathNode lv16 = this.getNode(node.x + 1, node.y, node.z + 1);
      if (this.unvisited(lv16) && this.isPassable(lv) && this.isPassable(lv3)) {
         successors[i++] = lv16;
      }

      PathNode lv17 = this.getNode(node.x - 1, node.y, node.z - 1);
      if (this.unvisited(lv17) && this.isPassable(lv4) && this.isPassable(lv2)) {
         successors[i++] = lv17;
      }

      PathNode lv18 = this.getNode(node.x - 1, node.y, node.z + 1);
      if (this.unvisited(lv18) && this.isPassable(lv) && this.isPassable(lv2)) {
         successors[i++] = lv18;
      }

      PathNode lv19 = this.getNode(node.x + 1, node.y + 1, node.z - 1);
      if (this.unvisited(lv19)
         && this.isPassable(lv15)
         && this.isPassable(lv4)
         && this.isPassable(lv3)
         && this.isPassable(lv5)
         && this.isPassable(lv10)
         && this.isPassable(lv9)) {
         successors[i++] = lv19;
      }

      PathNode lv20 = this.getNode(node.x + 1, node.y + 1, node.z + 1);
      if (this.unvisited(lv20)
         && this.isPassable(lv16)
         && this.isPassable(lv)
         && this.isPassable(lv3)
         && this.isPassable(lv5)
         && this.isPassable(lv7)
         && this.isPassable(lv9)) {
         successors[i++] = lv20;
      }

      PathNode lv21 = this.getNode(node.x - 1, node.y + 1, node.z - 1);
      if (this.unvisited(lv21)
         && this.isPassable(lv17)
         && this.isPassable(lv4)
         && this.isPassable(lv2) & this.isPassable(lv5)
         && this.isPassable(lv10)
         && this.isPassable(lv8)) {
         successors[i++] = lv21;
      }

      PathNode lv22 = this.getNode(node.x - 1, node.y + 1, node.z + 1);
      if (this.unvisited(lv22)
         && this.isPassable(lv18)
         && this.isPassable(lv)
         && this.isPassable(lv2) & this.isPassable(lv5)
         && this.isPassable(lv7)
         && this.isPassable(lv8)) {
         successors[i++] = lv22;
      }

      PathNode lv23 = this.getNode(node.x + 1, node.y - 1, node.z - 1);
      if (this.unvisited(lv23)
         && this.isPassable(lv15)
         && this.isPassable(lv4)
         && this.isPassable(lv3)
         && this.isPassable(lv6)
         && this.isPassable(lv14)
         && this.isPassable(lv13)) {
         successors[i++] = lv23;
      }

      PathNode lv24 = this.getNode(node.x + 1, node.y - 1, node.z + 1);
      if (this.unvisited(lv24)
         && this.isPassable(lv16)
         && this.isPassable(lv)
         && this.isPassable(lv3)
         && this.isPassable(lv6)
         && this.isPassable(lv11)
         && this.isPassable(lv13)) {
         successors[i++] = lv24;
      }

      PathNode lv25 = this.getNode(node.x - 1, node.y - 1, node.z - 1);
      if (this.unvisited(lv25)
         && this.isPassable(lv17)
         && this.isPassable(lv4)
         && this.isPassable(lv2)
         && this.isPassable(lv6)
         && this.isPassable(lv14)
         && this.isPassable(lv12)) {
         successors[i++] = lv25;
      }

      PathNode lv26 = this.getNode(node.x - 1, node.y - 1, node.z + 1);
      if (this.unvisited(lv26)
         && this.isPassable(lv18)
         && this.isPassable(lv)
         && this.isPassable(lv2)
         && this.isPassable(lv6)
         && this.isPassable(lv11)
         && this.isPassable(lv12)) {
         successors[i++] = lv26;
      }

      return i;
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
      PathNode lv = null;
      PathNodeType lv2 = this.getNodeType(this.entity, x, y, z);
      float f = this.entity.getPathfindingPenalty(lv2);
      if (f >= 0.0F) {
         lv = super.getNode(x, y, z);
         lv.type = lv2;
         lv.penalty = Math.max(lv.penalty, f);
         if (lv2 == PathNodeType.WALKABLE) {
            lv.penalty++;
         }
      }

      return lv2 != PathNodeType.OPEN && lv2 != PathNodeType.WALKABLE ? lv : lv;
   }

   @Override
   public PathNodeType getNodeType(
      BlockView world, int x, int y, int z, MobEntity mob, int sizeX, int sizeY, int sizeZ, boolean canOpenDoors, boolean canEnterOpenDoors
   ) {
      EnumSet<PathNodeType> enumSet = EnumSet.noneOf(PathNodeType.class);
      PathNodeType lv = PathNodeType.BLOCKED;
      BlockPos lv2 = mob.getBlockPos();
      lv = this.findNearbyNodeTypes(world, x, y, z, sizeX, sizeY, sizeZ, canOpenDoors, canEnterOpenDoors, enumSet, lv, lv2);
      if (enumSet.contains(PathNodeType.FENCE)) {
         return PathNodeType.FENCE;
      } else {
         PathNodeType lv3 = PathNodeType.BLOCKED;

         for (PathNodeType lv4 : enumSet) {
            if (mob.getPathfindingPenalty(lv4) < 0.0F) {
               return lv4;
            }

            if (mob.getPathfindingPenalty(lv4) >= mob.getPathfindingPenalty(lv3)) {
               lv3 = lv4;
            }
         }

         return lv == PathNodeType.OPEN && mob.getPathfindingPenalty(lv3) == 0.0F ? PathNodeType.OPEN : lv3;
      }
   }

   @Override
   public PathNodeType getDefaultNodeType(BlockView world, int x, int y, int z) {
      BlockPos.Mutable lv = new BlockPos.Mutable();
      PathNodeType lv2 = getCommonNodeType(world, lv.set(x, y, z));
      if (lv2 == PathNodeType.OPEN && y >= 1) {
         BlockState lv3 = world.getBlockState(lv.set(x, y - 1, z));
         PathNodeType lv4 = getCommonNodeType(world, lv.set(x, y - 1, z));
         if (lv4 == PathNodeType.DAMAGE_FIRE || lv3.isOf(Blocks.MAGMA_BLOCK) || lv4 == PathNodeType.LAVA || lv3.isIn(BlockTags.CAMPFIRES)) {
            lv2 = PathNodeType.DAMAGE_FIRE;
         } else if (lv4 == PathNodeType.DAMAGE_CACTUS) {
            lv2 = PathNodeType.DAMAGE_CACTUS;
         } else if (lv4 == PathNodeType.DAMAGE_OTHER) {
            lv2 = PathNodeType.DAMAGE_OTHER;
         } else if (lv4 == PathNodeType.COCOA) {
            lv2 = PathNodeType.COCOA;
         } else if (lv4 == PathNodeType.FENCE) {
            lv2 = PathNodeType.FENCE;
         } else {
            lv2 = lv4 != PathNodeType.WALKABLE && lv4 != PathNodeType.OPEN && lv4 != PathNodeType.WATER ? PathNodeType.WALKABLE : PathNodeType.OPEN;
         }
      }

      if (lv2 == PathNodeType.WALKABLE || lv2 == PathNodeType.OPEN) {
         lv2 = getNodeTypeFromNeighbors(world, lv.set(x, y, z), lv2);
      }

      return lv2;
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
