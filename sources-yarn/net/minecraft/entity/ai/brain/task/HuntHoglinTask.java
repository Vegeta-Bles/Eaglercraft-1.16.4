package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.server.world.ServerWorld;

public class HuntHoglinTask<E extends PiglinEntity> extends Task<E> {
   public HuntHoglinTask() {
      super(
         ImmutableMap.of(
            MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN,
            MemoryModuleState.VALUE_PRESENT,
            MemoryModuleType.ANGRY_AT,
            MemoryModuleState.VALUE_ABSENT,
            MemoryModuleType.HUNTED_RECENTLY,
            MemoryModuleState.VALUE_ABSENT,
            MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS,
            MemoryModuleState.REGISTERED
         )
      );
   }

   protected boolean shouldRun(ServerWorld arg, PiglinEntity arg2) {
      return !arg2.isBaby() && !PiglinBrain.haveHuntedHoglinsRecently(arg2);
   }

   protected void run(ServerWorld arg, E arg2, long l) {
      HoglinEntity lv = arg2.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN).get();
      PiglinBrain.becomeAngryWith(arg2, lv);
      PiglinBrain.rememberHunting(arg2);
      PiglinBrain.angerAtCloserTargets(arg2, lv);
      PiglinBrain.rememberGroupHunting(arg2);
   }
}
