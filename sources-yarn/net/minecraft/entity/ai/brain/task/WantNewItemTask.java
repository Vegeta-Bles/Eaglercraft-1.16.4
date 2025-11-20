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

   protected boolean shouldRun(ServerWorld arg, E arg2) {
      if (!arg2.getOffHandStack().isEmpty()) {
         return false;
      } else {
         Optional<ItemEntity> optional = arg2.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
         return !optional.isPresent() ? true : !optional.get().isInRange(arg2, (double)this.range);
      }
   }

   protected void run(ServerWorld arg, E arg2, long l) {
      arg2.getBrain().forget(MemoryModuleType.ADMIRING_ITEM);
   }
}
