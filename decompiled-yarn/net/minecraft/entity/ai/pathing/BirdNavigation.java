package net.minecraft.entity.ai.pathing;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BirdNavigation extends EntityNavigation {
   public BirdNavigation(MobEntity _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   @Override
   protected PathNodeNavigator createPathNodeNavigator(int range) {
      this.nodeMaker = new BirdPathNodeMaker();
      this.nodeMaker.setCanEnterOpenDoors(true);
      return new PathNodeNavigator(this.nodeMaker, range);
   }

   @Override
   protected boolean isAtValidPosition() {
      return this.canSwim() && this.isInLiquid() || !this.entity.hasVehicle();
   }

   @Override
   protected Vec3d getPos() {
      return this.entity.getPos();
   }

   @Override
   public Path findPathTo(Entity entity, int distance) {
      return this.findPathTo(entity.getBlockPos(), distance);
   }

   @Override
   public void tick() {
      this.tickCount++;
      if (this.shouldRecalculate) {
         this.recalculatePath();
      }

      if (!this.isIdle()) {
         if (this.isAtValidPosition()) {
            this.continueFollowingPath();
         } else if (this.currentPath != null && !this.currentPath.isFinished()) {
            Vec3d _snowman = this.currentPath.getNodePosition(this.entity);
            if (MathHelper.floor(this.entity.getX()) == MathHelper.floor(_snowman.x)
               && MathHelper.floor(this.entity.getY()) == MathHelper.floor(_snowman.y)
               && MathHelper.floor(this.entity.getZ()) == MathHelper.floor(_snowman.z)) {
               this.currentPath.next();
            }
         }

         DebugInfoSender.sendPathfindingData(this.world, this.entity, this.currentPath, this.nodeReachProximity);
         if (!this.isIdle()) {
            Vec3d _snowman = this.currentPath.getNodePosition(this.entity);
            this.entity.getMoveControl().moveTo(_snowman.x, _snowman.y, _snowman.z, this.speed);
         }
      }
   }

   @Override
   protected boolean canPathDirectlyThrough(Vec3d origin, Vec3d target, int sizeX, int sizeY, int sizeZ) {
      int _snowman = MathHelper.floor(origin.x);
      int _snowmanx = MathHelper.floor(origin.y);
      int _snowmanxx = MathHelper.floor(origin.z);
      double _snowmanxxx = target.x - origin.x;
      double _snowmanxxxx = target.y - origin.y;
      double _snowmanxxxxx = target.z - origin.z;
      double _snowmanxxxxxx = _snowmanxxx * _snowmanxxx + _snowmanxxxx * _snowmanxxxx + _snowmanxxxxx * _snowmanxxxxx;
      if (_snowmanxxxxxx < 1.0E-8) {
         return false;
      } else {
         double _snowmanxxxxxxx = 1.0 / Math.sqrt(_snowmanxxxxxx);
         _snowmanxxx *= _snowmanxxxxxxx;
         _snowmanxxxx *= _snowmanxxxxxxx;
         _snowmanxxxxx *= _snowmanxxxxxxx;
         double _snowmanxxxxxxxx = 1.0 / Math.abs(_snowmanxxx);
         double _snowmanxxxxxxxxx = 1.0 / Math.abs(_snowmanxxxx);
         double _snowmanxxxxxxxxxx = 1.0 / Math.abs(_snowmanxxxxx);
         double _snowmanxxxxxxxxxxx = (double)_snowman - origin.x;
         double _snowmanxxxxxxxxxxxx = (double)_snowmanx - origin.y;
         double _snowmanxxxxxxxxxxxxx = (double)_snowmanxx - origin.z;
         if (_snowmanxxx >= 0.0) {
            _snowmanxxxxxxxxxxx++;
         }

         if (_snowmanxxxx >= 0.0) {
            _snowmanxxxxxxxxxxxx++;
         }

         if (_snowmanxxxxx >= 0.0) {
            _snowmanxxxxxxxxxxxxx++;
         }

         _snowmanxxxxxxxxxxx /= _snowmanxxx;
         _snowmanxxxxxxxxxxxx /= _snowmanxxxx;
         _snowmanxxxxxxxxxxxxx /= _snowmanxxxxx;
         int _snowmanxxxxxxxxxxxxxx = _snowmanxxx < 0.0 ? -1 : 1;
         int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxx < 0.0 ? -1 : 1;
         int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxx < 0.0 ? -1 : 1;
         int _snowmanxxxxxxxxxxxxxxxxx = MathHelper.floor(target.x);
         int _snowmanxxxxxxxxxxxxxxxxxx = MathHelper.floor(target.y);
         int _snowmanxxxxxxxxxxxxxxxxxxx = MathHelper.floor(target.z);
         int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx - _snowman;
         int _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx - _snowmanx;
         int _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx - _snowmanxx;

         while (_snowmanxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxx > 0 || _snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxx > 0 || _snowmanxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxx > 0) {
            if (_snowmanxxxxxxxxxxx < _snowmanxxxxxxxxxxxxx && _snowmanxxxxxxxxxxx <= _snowmanxxxxxxxxxxxx) {
               _snowmanxxxxxxxxxxx += _snowmanxxxxxxxx;
               _snowman += _snowmanxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx - _snowman;
            } else if (_snowmanxxxxxxxxxxxx < _snowmanxxxxxxxxxxx && _snowmanxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxxx) {
               _snowmanxxxxxxxxxxxx += _snowmanxxxxxxxxx;
               _snowmanx += _snowmanxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx - _snowmanx;
            } else {
               _snowmanxxxxxxxxxxxxx += _snowmanxxxxxxxxxx;
               _snowmanxx += _snowmanxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx - _snowmanxx;
            }
         }

         return true;
      }
   }

   public void setCanPathThroughDoors(boolean canPathThroughDoors) {
      this.nodeMaker.setCanOpenDoors(canPathThroughDoors);
   }

   public void setCanEnterOpenDoors(boolean canEnterOpenDoors) {
      this.nodeMaker.setCanEnterOpenDoors(canEnterOpenDoors);
   }

   @Override
   public boolean isValidPosition(BlockPos pos) {
      return this.world.getBlockState(pos).hasSolidTopSurface(this.world, pos, this.entity);
   }
}
