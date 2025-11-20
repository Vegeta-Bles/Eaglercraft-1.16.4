package net.minecraft.entity.ai.pathing;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.util.Util;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class SwimNavigation extends EntityNavigation {
   private boolean canJumpOutOfWater;

   public SwimNavigation(MobEntity _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   @Override
   protected PathNodeNavigator createPathNodeNavigator(int range) {
      this.canJumpOutOfWater = this.entity instanceof DolphinEntity;
      this.nodeMaker = new WaterPathNodeMaker(this.canJumpOutOfWater);
      return new PathNodeNavigator(this.nodeMaker, range);
   }

   @Override
   protected boolean isAtValidPosition() {
      return this.canJumpOutOfWater || this.isInLiquid();
   }

   @Override
   protected Vec3d getPos() {
      return new Vec3d(this.entity.getX(), this.entity.getBodyY(0.5), this.entity.getZ());
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
   protected void continueFollowingPath() {
      if (this.currentPath != null) {
         Vec3d _snowman = this.getPos();
         float _snowmanx = this.entity.getWidth();
         float _snowmanxx = _snowmanx > 0.75F ? _snowmanx / 2.0F : 0.75F - _snowmanx / 2.0F;
         Vec3d _snowmanxxx = this.entity.getVelocity();
         if (Math.abs(_snowmanxxx.x) > 0.2 || Math.abs(_snowmanxxx.z) > 0.2) {
            _snowmanxx = (float)((double)_snowmanxx * _snowmanxxx.length() * 6.0);
         }

         int _snowmanxxxx = 6;
         Vec3d _snowmanxxxxx = Vec3d.ofBottomCenter(this.currentPath.method_31032());
         if (Math.abs(this.entity.getX() - _snowmanxxxxx.x) < (double)_snowmanxx
            && Math.abs(this.entity.getZ() - _snowmanxxxxx.z) < (double)_snowmanxx
            && Math.abs(this.entity.getY() - _snowmanxxxxx.y) < (double)(_snowmanxx * 2.0F)) {
            this.currentPath.next();
         }

         for (int _snowmanxxxxxx = Math.min(this.currentPath.getCurrentNodeIndex() + 6, this.currentPath.getLength() - 1);
            _snowmanxxxxxx > this.currentPath.getCurrentNodeIndex();
            _snowmanxxxxxx--
         ) {
            _snowmanxxxxx = this.currentPath.getNodePosition(this.entity, _snowmanxxxxxx);
            if (!(_snowmanxxxxx.squaredDistanceTo(_snowman) > 36.0) && this.canPathDirectlyThrough(_snowman, _snowmanxxxxx, 0, 0, 0)) {
               this.currentPath.setCurrentNodeIndex(_snowmanxxxxxx);
               break;
            }
         }

         this.checkTimeouts(_snowman);
      }
   }

   @Override
   protected void checkTimeouts(Vec3d currentPos) {
      if (this.tickCount - this.pathStartTime > 100) {
         if (currentPos.squaredDistanceTo(this.pathStartPos) < 2.25) {
            this.stop();
         }

         this.pathStartTime = this.tickCount;
         this.pathStartPos = currentPos;
      }

      if (this.currentPath != null && !this.currentPath.isFinished()) {
         Vec3i _snowman = this.currentPath.method_31032();
         if (_snowman.equals(this.lastNodePosition)) {
            this.currentNodeMs = this.currentNodeMs + (Util.getMeasuringTimeMs() - this.lastActiveTickMs);
         } else {
            this.lastNodePosition = _snowman;
            double _snowmanx = currentPos.distanceTo(Vec3d.ofCenter(this.lastNodePosition));
            this.currentNodeTimeout = this.entity.getMovementSpeed() > 0.0F ? _snowmanx / (double)this.entity.getMovementSpeed() * 100.0 : 0.0;
         }

         if (this.currentNodeTimeout > 0.0 && (double)this.currentNodeMs > this.currentNodeTimeout * 2.0) {
            this.lastNodePosition = Vec3i.ZERO;
            this.currentNodeMs = 0L;
            this.currentNodeTimeout = 0.0;
            this.stop();
         }

         this.lastActiveTickMs = Util.getMeasuringTimeMs();
      }
   }

   @Override
   protected boolean canPathDirectlyThrough(Vec3d origin, Vec3d target, int sizeX, int sizeY, int sizeZ) {
      Vec3d _snowman = new Vec3d(target.x, target.y + (double)this.entity.getHeight() * 0.5, target.z);
      return this.world.raycast(new RaycastContext(origin, _snowman, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this.entity)).getType()
         == HitResult.Type.MISS;
   }

   @Override
   public boolean isValidPosition(BlockPos pos) {
      return !this.world.getBlockState(pos).isOpaqueFullCube(this.world, pos);
   }

   @Override
   public void setCanSwim(boolean canSwim) {
   }
}
