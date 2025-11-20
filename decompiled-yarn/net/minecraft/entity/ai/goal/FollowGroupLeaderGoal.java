package net.minecraft.entity.ai.goal;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.passive.SchoolingFishEntity;

public class FollowGroupLeaderGoal extends Goal {
   private final SchoolingFishEntity fish;
   private int moveDelay;
   private int checkSurroundingDelay;

   public FollowGroupLeaderGoal(SchoolingFishEntity fish) {
      this.fish = fish;
      this.checkSurroundingDelay = this.getSurroundingSearchDelay(fish);
   }

   protected int getSurroundingSearchDelay(SchoolingFishEntity fish) {
      return 200 + fish.getRandom().nextInt(200) % 20;
   }

   @Override
   public boolean canStart() {
      if (this.fish.hasOtherFishInGroup()) {
         return false;
      } else if (this.fish.hasLeader()) {
         return true;
      } else if (this.checkSurroundingDelay > 0) {
         this.checkSurroundingDelay--;
         return false;
      } else {
         this.checkSurroundingDelay = this.getSurroundingSearchDelay(this.fish);
         Predicate<SchoolingFishEntity> _snowman = _snowmanx -> _snowmanx.canHaveMoreFishInGroup() || !_snowmanx.hasLeader();
         List<SchoolingFishEntity> _snowmanx = this.fish
            .world
            .getEntitiesByClass((Class<? extends SchoolingFishEntity>)this.fish.getClass(), this.fish.getBoundingBox().expand(8.0, 8.0, 8.0), _snowman);
         SchoolingFishEntity _snowmanxx = _snowmanx.stream().filter(SchoolingFishEntity::canHaveMoreFishInGroup).findAny().orElse(this.fish);
         _snowmanxx.pullInOtherFish(_snowmanx.stream().filter(_snowmanxxx -> !_snowmanxxx.hasLeader()));
         return this.fish.hasLeader();
      }
   }

   @Override
   public boolean shouldContinue() {
      return this.fish.hasLeader() && this.fish.isCloseEnoughToLeader();
   }

   @Override
   public void start() {
      this.moveDelay = 0;
   }

   @Override
   public void stop() {
      this.fish.leaveGroup();
   }

   @Override
   public void tick() {
      if (--this.moveDelay <= 0) {
         this.moveDelay = 10;
         this.fish.moveTowardLeader();
      }
   }
}
