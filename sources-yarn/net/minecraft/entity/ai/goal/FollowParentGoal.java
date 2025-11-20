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
         List<AnimalEntity> list = this.animal
            .world
            .getNonSpectatingEntities((Class<? extends AnimalEntity>)this.animal.getClass(), this.animal.getBoundingBox().expand(8.0, 4.0, 8.0));
         AnimalEntity lv = null;
         double d = Double.MAX_VALUE;

         for (AnimalEntity lv2 : list) {
            if (lv2.getBreedingAge() >= 0) {
               double e = this.animal.squaredDistanceTo(lv2);
               if (!(e > d)) {
                  d = e;
                  lv = lv2;
               }
            }
         }

         if (lv == null) {
            return false;
         } else if (d < 9.0) {
            return false;
         } else {
            this.parent = lv;
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
         double d = this.animal.squaredDistanceTo(this.parent);
         return !(d < 9.0) && !(d > 256.0);
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
