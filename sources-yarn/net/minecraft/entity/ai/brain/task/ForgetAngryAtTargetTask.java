package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;

public class ForgetAngryAtTargetTask<E extends MobEntity> extends Task<E> {
   public ForgetAngryAtTargetTask() {
      super(ImmutableMap.of(MemoryModuleType.ANGRY_AT, MemoryModuleState.VALUE_PRESENT));
   }

   protected void run(ServerWorld arg, E arg2, long l) {
      LookTargetUtil.getEntity(arg2, MemoryModuleType.ANGRY_AT).ifPresent(arg3 -> {
         if (arg3.isDead() && (arg3.getType() != EntityType.PLAYER || arg.getGameRules().getBoolean(GameRules.FORGIVE_DEAD_PLAYERS))) {
            arg2.getBrain().forget(MemoryModuleType.ANGRY_AT);
         }
      });
   }
}
