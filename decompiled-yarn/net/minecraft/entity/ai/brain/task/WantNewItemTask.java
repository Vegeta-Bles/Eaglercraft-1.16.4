package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.server.world.ServerWorld;

public class WantNewItemTask<E extends PiglinEntity> extends Task<E> {
   private final int range;

   public WantNewItemTask(int range) {
      super(
         ImmutableMap.of(
            MemoryModuleType.ADMIRING_ITEM, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleState.REGISTERED
         )
      );
      this.range = range;
   }

   protected boolean shouldRun(ServerWorld _snowman, E _snowman) {
      if (!_snowman.getOffHandStack().isEmpty()) {
         return false;
      } else {
         Optional<ItemEntity> _snowmanxx = _snowman.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
         return !_snowmanxx.isPresent() ? true : !_snowmanxx.get().isInRange(_snowman, (double)this.range);
      }
   }

   protected void run(ServerWorld _snowman, E _snowman, long _snowman) {
      _snowman.getBrain().forget(MemoryModuleType.ADMIRING_ITEM);
   }
}
