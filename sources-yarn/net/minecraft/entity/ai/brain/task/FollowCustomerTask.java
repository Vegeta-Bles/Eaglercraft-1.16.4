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

   protected boolean shouldRun(ServerWorld arg, VillagerEntity arg2) {
      PlayerEntity lv = arg2.getCurrentCustomer();
      return arg2.isAlive()
         && lv != null
         && !arg2.isTouchingWater()
         && !arg2.velocityModified
         && arg2.squaredDistanceTo(lv) <= 16.0
         && lv.currentScreenHandler != null;
   }

   protected boolean shouldKeepRunning(ServerWorld arg, VillagerEntity arg2, long l) {
      return this.shouldRun(arg, arg2);
   }

   protected void run(ServerWorld arg, VillagerEntity arg2, long l) {
      this.update(arg2);
   }

   protected void finishRunning(ServerWorld arg, VillagerEntity arg2, long l) {
      Brain<?> lv = arg2.getBrain();
      lv.forget(MemoryModuleType.WALK_TARGET);
      lv.forget(MemoryModuleType.LOOK_TARGET);
   }

   protected void keepRunning(ServerWorld arg, VillagerEntity arg2, long l) {
      this.update(arg2);
   }

   @Override
   protected boolean isTimeLimitExceeded(long time) {
      return false;
   }

   private void update(VillagerEntity villager) {
      Brain<?> lv = villager.getBrain();
      lv.remember(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityLookTarget(villager.getCurrentCustomer(), false), this.speed, 2));
      lv.remember(MemoryModuleType.LOOK_TARGET, new EntityLookTarget(villager.getCurrentCustomer(), true));
   }
}
