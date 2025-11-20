package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.function.Predicate;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.IntRange;

public class MemoryTransferTask<E extends MobEntity, T> extends Task<E> {
   private final Predicate<E> runPredicate;
   private final MemoryModuleType<? extends T> sourceType;
   private final MemoryModuleType<T> targetType;
   private final IntRange duration;

   public MemoryTransferTask(Predicate<E> runPredicate, MemoryModuleType<? extends T> _snowman, MemoryModuleType<T> _snowman, IntRange duration) {
      super(ImmutableMap.of(_snowman, MemoryModuleState.VALUE_PRESENT, _snowman, MemoryModuleState.VALUE_ABSENT));
      this.runPredicate = runPredicate;
      this.sourceType = _snowman;
      this.targetType = _snowman;
      this.duration = duration;
   }

   protected boolean shouldRun(ServerWorld _snowman, E _snowman) {
      return this.runPredicate.test(_snowman);
   }

   protected void run(ServerWorld _snowman, E _snowman, long _snowman) {
      Brain<?> _snowmanxxx = _snowman.getBrain();
      _snowmanxxx.remember(this.targetType, (T)_snowmanxxx.getOptionalMemory(this.sourceType).get(), (long)this.duration.choose(_snowman.random));
   }
}
