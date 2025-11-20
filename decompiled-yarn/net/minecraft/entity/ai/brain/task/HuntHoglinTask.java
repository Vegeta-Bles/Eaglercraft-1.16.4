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

   protected boolean shouldRun(ServerWorld _snowman, PiglinEntity _snowman) {
      return !_snowman.isBaby() && !PiglinBrain.haveHuntedHoglinsRecently(_snowman);
   }

   protected void run(ServerWorld _snowman, E _snowman, long _snowman) {
      HoglinEntity _snowmanxxx = _snowman.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN).get();
      PiglinBrain.becomeAngryWith(_snowman, _snowmanxxx);
      PiglinBrain.rememberHunting(_snowman);
      PiglinBrain.angerAtCloserTargets(_snowman, _snowmanxxx);
      PiglinBrain.rememberGroupHunting(_snowman);
   }
}
