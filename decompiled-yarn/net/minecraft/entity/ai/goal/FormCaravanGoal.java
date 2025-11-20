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
         List<Entity> _snowman = this.llama.world.getOtherEntities(this.llama, this.llama.getBoundingBox().expand(9.0, 4.0, 9.0), _snowmanx -> {
            EntityType<?> _snowmanx = _snowmanx.getType();
            return _snowmanx == EntityType.LLAMA || _snowmanx == EntityType.TRADER_LLAMA;
         });
         LlamaEntity _snowmanx = null;
         double _snowmanxx = Double.MAX_VALUE;

         for (Entity _snowmanxxx : _snowman) {
            LlamaEntity _snowmanxxxx = (LlamaEntity)_snowmanxxx;
            if (_snowmanxxxx.isFollowing() && !_snowmanxxxx.hasFollower()) {
               double _snowmanxxxxx = this.llama.squaredDistanceTo(_snowmanxxxx);
               if (!(_snowmanxxxxx > _snowmanxx)) {
                  _snowmanxx = _snowmanxxxxx;
                  _snowmanx = _snowmanxxxx;
               }
            }
         }

         if (_snowmanx == null) {
            for (Entity _snowmanxxxx : _snowman) {
               LlamaEntity _snowmanxxxxx = (LlamaEntity)_snowmanxxxx;
               if (_snowmanxxxxx.isLeashed() && !_snowmanxxxxx.hasFollower()) {
                  double _snowmanxxxxxx = this.llama.squaredDistanceTo(_snowmanxxxxx);
                  if (!(_snowmanxxxxxx > _snowmanxx)) {
                     _snowmanxx = _snowmanxxxxxx;
                     _snowmanx = _snowmanxxxxx;
                  }
               }
            }
         }

         if (_snowmanx == null) {
            return false;
         } else if (_snowmanxx < 4.0) {
            return false;
         } else if (!_snowmanx.isLeashed() && !this.canFollow(_snowmanx, 1)) {
            return false;
         } else {
            this.llama.follow(_snowmanx);
            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   public boolean shouldContinue() {
      if (this.llama.isFollowing() && this.llama.getFollowing().isAlive() && this.canFollow(this.llama, 0)) {
         double _snowman = this.llama.squaredDistanceTo(this.llama.getFollowing());
         if (_snowman > 676.0) {
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
            LlamaEntity _snowman = this.llama.getFollowing();
            double _snowmanx = (double)this.llama.distanceTo(_snowman);
            float _snowmanxx = 2.0F;
            Vec3d _snowmanxxx = new Vec3d(_snowman.getX() - this.llama.getX(), _snowman.getY() - this.llama.getY(), _snowman.getZ() - this.llama.getZ())
               .normalize()
               .multiply(Math.max(_snowmanx - 2.0, 0.0));
            this.llama.getNavigation().startMovingTo(this.llama.getX() + _snowmanxxx.x, this.llama.getY() + _snowmanxxx.y, this.llama.getZ() + _snowmanxxx.z, this.speed);
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
