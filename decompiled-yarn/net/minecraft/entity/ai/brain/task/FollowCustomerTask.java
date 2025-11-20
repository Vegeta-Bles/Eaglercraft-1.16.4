package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class FollowCustomerTask extends Task<VillagerEntity> {
   private final float speed;

   public FollowCustomerTask(float speed) {
      super(
         ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.REGISTERED, MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED),
         Integer.MAX_VALUE
      );
      this.speed = speed;
   }

   protected boolean shouldRun(ServerWorld _snowman, VillagerEntity _snowman) {
      PlayerEntity _snowmanxx = _snowman.getCurrentCustomer();
      return _snowman.isAlive() && _snowmanxx != null && !_snowman.isTouchingWater() && !_snowman.velocityModified && _snowman.squaredDistanceTo(_snowmanxx) <= 16.0 && _snowmanxx.currentScreenHandler != null;
   }

   protected boolean shouldKeepRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      return this.shouldRun(_snowman, _snowman);
   }

   protected void run(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      this.update(_snowman);
   }

   protected void finishRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      Brain<?> _snowmanxxx = _snowman.getBrain();
      _snowmanxxx.forget(MemoryModuleType.WALK_TARGET);
      _snowmanxxx.forget(MemoryModuleType.LOOK_TARGET);
   }

   protected void keepRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      this.update(_snowman);
   }

   @Override
   protected boolean isTimeLimitExceeded(long time) {
      return false;
   }

   private void update(VillagerEntity villager) {
      Brain<?> _snowman = villager.getBrain();
      _snowman.remember(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityLookTarget(villager.getCurrentCustomer(), false), this.speed, 2));
      _snowman.remember(MemoryModuleType.LOOK_TARGET, new EntityLookTarget(villager.getCurrentCustomer(), true));
   }
}
