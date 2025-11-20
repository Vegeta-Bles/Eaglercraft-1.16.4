package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;

public class RangedApproachTask extends Task<MobEntity> {
   private final float speed;

   public RangedApproachTask(float speed) {
      super(
         ImmutableMap.of(
            MemoryModuleType.WALK_TARGET,
            MemoryModuleState.REGISTERED,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleState.REGISTERED,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleState.VALUE_PRESENT,
            MemoryModuleType.VISIBLE_MOBS,
            MemoryModuleState.REGISTERED
         )
      );
      this.speed = speed;
   }

   protected void run(ServerWorld _snowman, MobEntity _snowman, long _snowman) {
      LivingEntity _snowmanxxx = _snowman.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).get();
      if (LookTargetUtil.isVisibleInMemory(_snowman, _snowmanxxx) && LookTargetUtil.method_25940(_snowman, _snowmanxxx, 1)) {
         this.forgetWalkTarget(_snowman);
      } else {
         this.rememberWalkTarget(_snowman, _snowmanxxx);
      }
   }

   private void rememberWalkTarget(LivingEntity entity, LivingEntity target) {
      Brain _snowman = entity.getBrain();
      _snowman.remember(MemoryModuleType.LOOK_TARGET, new EntityLookTarget(target, true));
      WalkTarget _snowmanx = new WalkTarget(new EntityLookTarget(target, false), this.speed, 0);
      _snowman.remember(MemoryModuleType.WALK_TARGET, _snowmanx);
   }

   private void forgetWalkTarget(LivingEntity entity) {
      entity.getBrain().forget(MemoryModuleType.WALK_TARGET);
   }
}
