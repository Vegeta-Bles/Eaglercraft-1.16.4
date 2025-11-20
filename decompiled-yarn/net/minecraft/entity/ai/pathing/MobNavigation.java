package net.minecraft.entity.ai.pathing;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MobNavigation extends EntityNavigation {
   private boolean avoidSunlight;

   public MobNavigation(MobEntity _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   @Override
   protected PathNodeNavigator createPathNodeNavigator(int range) {
      this.nodeMaker = new LandPathNodeMaker();
      this.nodeMaker.setCanEnterOpenDoors(true);
      return new PathNodeNavigator(this.nodeMaker, range);
   }

   @Override
   protected boolean isAtValidPosition() {
      return this.entity.isOnGround() || this.isInLiquid() || this.entity.hasVehicle();
   }

   @Override
   protected Vec3d getPos() {
      return new Vec3d(this.entity.getX(), (double)this.getPathfindingY(), this.entity.getZ());
   }

   @Override
   public Path findPathTo(BlockPos target, int distance) {
      if (this.world.getBlockState(target).isAir()) {
         BlockPos _snowman = target.down();

         while (_snowman.getY() > 0 && this.world.getBlockState(_snowman).isAir()) {
            _snowman = _snowman.down();
         }

         if (_snowman.getY() > 0) {
            return super.findPathTo(_snowman.up(), distance);
         }

         while (_snowman.getY() < this.world.getHeight() && this.world.getBlockState(_snowman).isAir()) {
            _snowman = _snowman.up();
         }

         target = _snowman;
      }

      if (!this.world.getBlockState(target).getMaterial().isSolid()) {
         return super.findPathTo(target, distance);
      } else {
         BlockPos _snowman = target.up();

         while (_snowman.getY() < this.world.getHeight() && this.world.getBlockState(_snowman).getMaterial().isSolid()) {
            _snowman = _snowman.up();
         }

         return super.findPathTo(_snowman, distance);
      }
   }

   @Override
   public Path findPathTo(Entity entity, int distance) {
      return this.findPathTo(entity.getBlockPos(), distance);
   }

   private int getPathfindingY() {
      if (this.entity.isTouchingWater() && this.canSwim()) {
         int _snowman = MathHelper.floor(this.entity.getY());
         Block _snowmanx = this.world.getBlockState(new BlockPos(this.entity.getX(), (double)_snowman, this.entity.getZ())).getBlock();
         int _snowmanxx = 0;

         while (_snowmanx == Blocks.WATER) {
            _snowmanx = this.world.getBlockState(new BlockPos(this.entity.getX(), (double)(++_snowman), this.entity.getZ())).getBlock();
            if (++_snowmanxx > 16) {
               return MathHelper.floor(this.entity.getY());
            }
         }

         return _snowman;
      } else {
         return MathHelper.floor(this.entity.getY() + 0.5);
      }
   }

   @Override
   protected void adjustPath() {
      super.adjustPath();
      if (this.avoidSunlight) {
         if (this.world.isSkyVisible(new BlockPos(this.entity.getX(), this.entity.getY() + 0.5, this.entity.getZ()))) {
            return;
         }

         for (int _snowman = 0; _snowman < this.currentPath.getLength(); _snowman++) {
            PathNode _snowmanx = this.currentPath.getNode(_snowman);
            if (this.world.isSkyVisible(new BlockPos(_snowmanx.x, _snowmanx.y, _snowmanx.z))) {
               this.currentPath.setLength(_snowman);
               return;
            }
         }
      }
   }

   @Override
   protected boolean canPathDirectlyThrough(Vec3d origin, Vec3d target, int sizeX, int sizeY, int sizeZ) {
      int _snowman = MathHelper.floor(origin.x);
      int _snowmanx = MathHelper.floor(origin.z);
      double _snowmanxx = target.x - origin.x;
      double _snowmanxxx = target.z - origin.z;
      double _snowmanxxxx = _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx;
      if (_snowmanxxxx < 1.0E-8) {
         return false;
      } else {
         double _snowmanxxxxx = 1.0 / Math.sqrt(_snowmanxxxx);
         _snowmanxx *= _snowmanxxxxx;
         _snowmanxxx *= _snowmanxxxxx;
         sizeX += 2;
         sizeZ += 2;
         if (!this.allVisibleAreSafe(_snowman, MathHelper.floor(origin.y), _snowmanx, sizeX, sizeY, sizeZ, origin, _snowmanxx, _snowmanxxx)) {
            return false;
         } else {
            sizeX -= 2;
            sizeZ -= 2;
            double _snowmanxxxxxx = 1.0 / Math.abs(_snowmanxx);
            double _snowmanxxxxxxx = 1.0 / Math.abs(_snowmanxxx);
            double _snowmanxxxxxxxx = (double)_snowman - origin.x;
            double _snowmanxxxxxxxxx = (double)_snowmanx - origin.z;
            if (_snowmanxx >= 0.0) {
               _snowmanxxxxxxxx++;
            }

            if (_snowmanxxx >= 0.0) {
               _snowmanxxxxxxxxx++;
            }

            _snowmanxxxxxxxx /= _snowmanxx;
            _snowmanxxxxxxxxx /= _snowmanxxx;
            int _snowmanxxxxxxxxxx = _snowmanxx < 0.0 ? -1 : 1;
            int _snowmanxxxxxxxxxxx = _snowmanxxx < 0.0 ? -1 : 1;
            int _snowmanxxxxxxxxxxxx = MathHelper.floor(target.x);
            int _snowmanxxxxxxxxxxxxx = MathHelper.floor(target.z);
            int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx - _snowman;
            int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx - _snowmanx;

            while (_snowmanxxxxxxxxxxxxxx * _snowmanxxxxxxxxxx > 0 || _snowmanxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxx > 0) {
               if (_snowmanxxxxxxxx < _snowmanxxxxxxxxx) {
                  _snowmanxxxxxxxx += _snowmanxxxxxx;
                  _snowman += _snowmanxxxxxxxxxx;
                  _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx - _snowman;
               } else {
                  _snowmanxxxxxxxxx += _snowmanxxxxxxx;
                  _snowmanx += _snowmanxxxxxxxxxxx;
                  _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx - _snowmanx;
               }

               if (!this.allVisibleAreSafe(_snowman, MathHelper.floor(origin.y), _snowmanx, sizeX, sizeY, sizeZ, origin, _snowmanxx, _snowmanxxx)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private boolean allVisibleAreSafe(int centerX, int centerY, int centerZ, int xSize, int ySize, int zSize, Vec3d entityPos, double lookVecX, double lookVecZ) {
      int _snowman = centerX - xSize / 2;
      int _snowmanx = centerZ - zSize / 2;
      if (!this.allVisibleArePassable(_snowman, centerY, _snowmanx, xSize, ySize, zSize, entityPos, lookVecX, lookVecZ)) {
         return false;
      } else {
         for (int _snowmanxx = _snowman; _snowmanxx < _snowman + xSize; _snowmanxx++) {
            for (int _snowmanxxx = _snowmanx; _snowmanxxx < _snowmanx + zSize; _snowmanxxx++) {
               double _snowmanxxxx = (double)_snowmanxx + 0.5 - entityPos.x;
               double _snowmanxxxxx = (double)_snowmanxxx + 0.5 - entityPos.z;
               if (!(_snowmanxxxx * lookVecX + _snowmanxxxxx * lookVecZ < 0.0)) {
                  PathNodeType _snowmanxxxxxx = this.nodeMaker.getNodeType(this.world, _snowmanxx, centerY - 1, _snowmanxxx, this.entity, xSize, ySize, zSize, true, true);
                  if (!this.canWalkOnPath(_snowmanxxxxxx)) {
                     return false;
                  }

                  _snowmanxxxxxx = this.nodeMaker.getNodeType(this.world, _snowmanxx, centerY, _snowmanxxx, this.entity, xSize, ySize, zSize, true, true);
                  float _snowmanxxxxxxx = this.entity.getPathfindingPenalty(_snowmanxxxxxx);
                  if (_snowmanxxxxxxx < 0.0F || _snowmanxxxxxxx >= 8.0F) {
                     return false;
                  }

                  if (_snowmanxxxxxx == PathNodeType.DAMAGE_FIRE || _snowmanxxxxxx == PathNodeType.DANGER_FIRE || _snowmanxxxxxx == PathNodeType.DAMAGE_OTHER) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   }

   protected boolean canWalkOnPath(PathNodeType pathType) {
      if (pathType == PathNodeType.WATER) {
         return false;
      } else {
         return pathType == PathNodeType.LAVA ? false : pathType != PathNodeType.OPEN;
      }
   }

   private boolean allVisibleArePassable(int x, int y, int z, int xSize, int ySize, int zSize, Vec3d entityPos, double lookVecX, double lookVecZ) {
      for (BlockPos _snowman : BlockPos.iterate(new BlockPos(x, y, z), new BlockPos(x + xSize - 1, y + ySize - 1, z + zSize - 1))) {
         double _snowmanx = (double)_snowman.getX() + 0.5 - entityPos.x;
         double _snowmanxx = (double)_snowman.getZ() + 0.5 - entityPos.z;
         if (!(_snowmanx * lookVecX + _snowmanxx * lookVecZ < 0.0) && !this.world.getBlockState(_snowman).canPathfindThrough(this.world, _snowman, NavigationType.LAND)) {
            return false;
         }
      }

      return true;
   }

   public void setCanPathThroughDoors(boolean canPathThroughDoors) {
      this.nodeMaker.setCanOpenDoors(canPathThroughDoors);
   }

   public boolean canEnterOpenDoors() {
      return this.nodeMaker.canEnterOpenDoors();
   }

   public void setAvoidSunlight(boolean avoidSunlight) {
      this.avoidSunlight = avoidSunlight;
   }
}
