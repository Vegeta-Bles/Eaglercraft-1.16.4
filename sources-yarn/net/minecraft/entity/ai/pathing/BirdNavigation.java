package net.minecraft.entity.ai.pathing;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BirdNavigation extends EntityNavigation {
   public BirdNavigation(MobEntity arg, World arg2) {
      super(arg, arg2);
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
            Vec3d lv = this.currentPath.getNodePosition(this.entity);
            if (MathHelper.floor(this.entity.getX()) == MathHelper.floor(lv.x)
               && MathHelper.floor(this.entity.getY()) == MathHelper.floor(lv.y)
               && MathHelper.floor(this.entity.getZ()) == MathHelper.floor(lv.z)) {
               this.currentPath.next();
            }
         }

         DebugInfoSender.sendPathfindingData(this.world, this.entity, this.currentPath, this.nodeReachProximity);
         if (!this.isIdle()) {
            Vec3d lv2 = this.currentPath.getNodePosition(this.entity);
            this.entity.getMoveControl().moveTo(lv2.x, lv2.y, lv2.z, this.speed);
         }
      }
   }

   @Override
   protected boolean canPathDirectlyThrough(Vec3d origin, Vec3d target, int sizeX, int sizeY, int sizeZ) {
      int l = MathHelper.floor(origin.x);
      int m = MathHelper.floor(origin.y);
      int n = MathHelper.floor(origin.z);
      double d = target.x - origin.x;
      double e = target.y - origin.y;
      double f = target.z - origin.z;
      double g = d * d + e * e + f * f;
      if (g < 1.0E-8) {
         return false;
      } else {
         double h = 1.0 / Math.sqrt(g);
         d *= h;
         e *= h;
         f *= h;
         double o = 1.0 / Math.abs(d);
         double p = 1.0 / Math.abs(e);
         double q = 1.0 / Math.abs(f);
         double r = (double)l - origin.x;
         double s = (double)m - origin.y;
         double t = (double)n - origin.z;
         if (d >= 0.0) {
            r++;
         }

         if (e >= 0.0) {
            s++;
         }

         if (f >= 0.0) {
            t++;
         }

         r /= d;
         s /= e;
         t /= f;
         int u = d < 0.0 ? -1 : 1;
         int v = e < 0.0 ? -1 : 1;
         int w = f < 0.0 ? -1 : 1;
         int x = MathHelper.floor(target.x);
         int y = MathHelper.floor(target.y);
         int z = MathHelper.floor(target.z);
         int aa = x - l;
         int ab = y - m;
         int ac = z - n;

         while (aa * u > 0 || ab * v > 0 || ac * w > 0) {
            if (r < t && r <= s) {
               r += o;
               l += u;
               aa = x - l;
            } else if (s < r && s <= t) {
               s += p;
               m += v;
               ab = y - m;
            } else {
               t += q;
               n += w;
               ac = z - n;
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
