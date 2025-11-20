package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.LeashKnotEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.util.math.Vec3d;

public class FormCaravanGoal extends Goal {
   public final LlamaEntity llama;
   private double speed;
   private int counter;

   public FormCaravanGoal(LlamaEntity llama, double speed) {
      this.llama = llama;
      this.speed = speed;
      this.setControls(EnumSet.of(Goal.Control.MOVE));
   }

   @Override
   public boolean canStart() {
      if (!this.llama.isLeashed() && !this.llama.isFollowing()) {
         List<Entity> list = this.llama.world.getOtherEntities(this.llama, this.llama.getBoundingBox().expand(9.0, 4.0, 9.0), arg -> {
            EntityType<?> lvx = arg.getType();
            return lvx == EntityType.LLAMA || lvx == EntityType.TRADER_LLAMA;
         });
         LlamaEntity lv = null;
         double d = Double.MAX_VALUE;

         for (Entity lv2 : list) {
            LlamaEntity lv3 = (LlamaEntity)lv2;
            if (lv3.isFollowing() && !lv3.hasFollower()) {
               double e = this.llama.squaredDistanceTo(lv3);
               if (!(e > d)) {
                  d = e;
                  lv = lv3;
               }
            }
         }

         if (lv == null) {
            for (Entity lv4 : list) {
               LlamaEntity lv5 = (LlamaEntity)lv4;
               if (lv5.isLeashed() && !lv5.hasFollower()) {
                  double f = this.llama.squaredDistanceTo(lv5);
                  if (!(f > d)) {
                     d = f;
                     lv = lv5;
                  }
               }
            }
         }

         if (lv == null) {
            return false;
         } else if (d < 4.0) {
            return false;
         } else if (!lv.isLeashed() && !this.canFollow(lv, 1)) {
            return false;
         } else {
            this.llama.follow(lv);
            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   public boolean shouldContinue() {
      if (this.llama.isFollowing() && this.llama.getFollowing().isAlive() && this.canFollow(this.llama, 0)) {
         double d = this.llama.squaredDistanceTo(this.llama.getFollowing());
         if (d > 676.0) {
            if (this.speed <= 3.0) {
               this.speed *= 1.2;
               this.counter = 40;
               return true;
            }

            if (this.counter == 0) {
               return false;
            }
         }

         if (this.counter > 0) {
            this.counter--;
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public void stop() {
      this.llama.stopFollowing();
      this.speed = 2.1;
   }

   @Override
   public void tick() {
      if (this.llama.isFollowing()) {
         if (!(this.llama.getHoldingEntity() instanceof LeashKnotEntity)) {
            LlamaEntity lv = this.llama.getFollowing();
            double d = (double)this.llama.distanceTo(lv);
            float f = 2.0F;
            Vec3d lv2 = new Vec3d(lv.getX() - this.llama.getX(), lv.getY() - this.llama.getY(), lv.getZ() - this.llama.getZ())
               .normalize()
               .multiply(Math.max(d - 2.0, 0.0));
            this.llama.getNavigation().startMovingTo(this.llama.getX() + lv2.x, this.llama.getY() + lv2.y, this.llama.getZ() + lv2.z, this.speed);
         }
      }
   }

   private boolean canFollow(LlamaEntity llama, int length) {
      if (length > 8) {
         return false;
      } else if (llama.isFollowing()) {
         return llama.getFollowing().isLeashed() ? true : this.canFollow(llama.getFollowing(), ++length);
      } else {
         return false;
      }
   }
}
