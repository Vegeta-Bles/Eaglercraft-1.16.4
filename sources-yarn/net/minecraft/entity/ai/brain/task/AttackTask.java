package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;

public class AttackTask<E extends MobEntity> extends Task<E> {
   private final int distance;
   private final float forwardMovement;

   public AttackTask(int distance, float forwardMovement) {
      super(
         ImmutableMap.of(
            MemoryModuleType.WALK_TARGET,
            MemoryModuleState.VALUE_ABSENT,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleState.REGISTERED,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleState.VALUE_PRESENT,
            MemoryModuleType.VISIBLE_MOBS,
            MemoryModuleState.VALUE_PRESENT
         )
      );
      this.distance = distance;
      this.forwardMovement = forwardMovement;
   }

   protected boolean shouldRun(ServerWorld arg, E arg2) {
      return this.isAttackTargetVisible(arg2) && this.isNearAttackTarget(arg2);
   }

   protected void run(ServerWorld arg, E arg2, long l) {
      arg2.getBrain().remember(MemoryModuleType.LOOK_TARGET, new EntityLookTarget(this.getAttackTarget(arg2), true));
      arg2.getMoveControl().strafeTo(-this.forwardMovement, 0.0F);
      arg2.yaw = MathHelper.stepAngleTowards(arg2.yaw, arg2.headYaw, 0.0F);
   }

   private boolean isAttackTargetVisible(E entity) {
      return entity.getBrain().getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).get().contains(this.getAttackTarget(entity));
   }

   private boolean isNearAttackTarget(E entity) {
      return this.getAttackTarget(entity).isInRange(entity, (double)this.distance);
   }

   private LivingEntity getAttackTarget(E entity) {
      return entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).get();
   }
}
