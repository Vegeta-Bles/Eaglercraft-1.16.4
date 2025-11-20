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

   protected void run(ServerWorld _snowman, E _snowman, long _snowman) {
      LookTargetUtil.getEntity(_snowman, MemoryModuleType.ANGRY_AT).ifPresent(_snowmanxxxx -> {
         if (_snowmanxxxx.isDead() && (_snowmanxxxx.getType() != EntityType.PLAYER || _snowman.getGameRules().getBoolean(GameRules.FORGIVE_DEAD_PLAYERS))) {
            _snowman.getBrain().forget(MemoryModuleType.ANGRY_AT);
         }
      });
   }
}
