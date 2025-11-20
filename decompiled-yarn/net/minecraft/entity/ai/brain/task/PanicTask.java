package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;

public class PanicTask extends Task<VillagerEntity> {
   public PanicTask() {
      super(ImmutableMap.of());
   }

   protected boolean shouldKeepRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      return wasHurt(_snowman) || isHostileNearby(_snowman);
   }

   protected void run(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      if (wasHurt(_snowman) || isHostileNearby(_snowman)) {
         Brain<?> _snowmanxxx = _snowman.getBrain();
         if (!_snowmanxxx.hasActivity(Activity.PANIC)) {
            _snowmanxxx.forget(MemoryModuleType.PATH);
            _snowmanxxx.forget(MemoryModuleType.WALK_TARGET);
            _snowmanxxx.forget(MemoryModuleType.LOOK_TARGET);
            _snowmanxxx.forget(MemoryModuleType.BREED_TARGET);
            _snowmanxxx.forget(MemoryModuleType.INTERACTION_TARGET);
         }

         _snowmanxxx.doExclusively(Activity.PANIC);
      }
   }

   protected void keepRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      if (_snowman % 100L == 0L) {
         _snowman.summonGolem(_snowman, _snowman, 3);
      }
   }

   public static boolean isHostileNearby(LivingEntity entity) {
      return entity.getBrain().hasMemoryModule(MemoryModuleType.NEAREST_HOSTILE);
   }

   public static boolean wasHurt(LivingEntity entity) {
      return entity.getBrain().hasMemoryModule(MemoryModuleType.HURT_BY);
   }
}
