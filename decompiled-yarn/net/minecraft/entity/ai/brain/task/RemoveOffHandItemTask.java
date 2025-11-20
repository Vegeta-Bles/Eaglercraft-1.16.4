package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;

public class RemoveOffHandItemTask<E extends PiglinEntity> extends Task<E> {
   public RemoveOffHandItemTask() {
      super(ImmutableMap.of(MemoryModuleType.ADMIRING_ITEM, MemoryModuleState.VALUE_ABSENT));
   }

   protected boolean shouldRun(ServerWorld _snowman, E _snowman) {
      return !_snowman.getOffHandStack().isEmpty() && _snowman.getOffHandStack().getItem() != Items.SHIELD;
   }

   protected void run(ServerWorld _snowman, E _snowman, long _snowman) {
      PiglinBrain.consumeOffHandItem(_snowman, true);
   }
}
