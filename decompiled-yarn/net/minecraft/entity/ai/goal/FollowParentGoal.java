package net.minecraft.entity.ai.goal;

import java.util.List;
import net.minecraft.entity.passive.AnimalEntity;

public class FollowParentGoal extends Goal {
   private final AnimalEntity animal;
   private AnimalEntity parent;
   private final double speed;
   private int delay;

   public FollowParentGoal(AnimalEntity animal, double speed) {
      this.animal = animal;
      this.speed = speed;
   }

   @Override
   public boolean canStart() {
      if (this.animal.getBreedingAge() >= 0) {
         return false;
      } else {
         List<AnimalEntity> _snowman = this.animal
            .world
            .getNonSpectatingEntities((Class<? extends AnimalEntity>)this.animal.getClass(), this.animal.getBoundingBox().expand(8.0, 4.0, 8.0));
         AnimalEntity _snowmanx = null;
         double _snowmanxx = Double.MAX_VALUE;

         for (AnimalEntity _snowmanxxx : _snowman) {
            if (_snowmanxxx.getBreedingAge() >= 0) {
               double _snowmanxxxx = this.animal.squaredDistanceTo(_snowmanxxx);
               if (!(_snowmanxxxx > _snowmanxx)) {
                  _snowmanxx = _snowmanxxxx;
                  _snowmanx = _snowmanxxx;
               }
            }
         }

         if (_snowmanx == null) {
            return false;
         } else if (_snowmanxx < 9.0) {
            return false;
         } else {
            this.parent = _snowmanx;
            return true;
         }
      }
   }

   @Override
   public boolean shouldContinue() {
      if (this.animal.getBreedingAge() >= 0) {
         return false;
      } else if (!this.parent.isAlive()) {
         return false;
      } else {
         double _snowman = this.animal.squaredDistanceTo(this.parent);
         return !(_snowman < 9.0) && !(_snowman > 256.0);
      }
   }

   @Override
   public void start() {
      this.delay = 0;
   }

   @Override
   public void stop() {
      this.parent = null;
   }

   @Override
   public void tick() {
      if (--this.delay <= 0) {
         this.delay = 10;
         this.animal.getNavigation().startMovingTo(this.parent, this.speed);
      }
   }
}
