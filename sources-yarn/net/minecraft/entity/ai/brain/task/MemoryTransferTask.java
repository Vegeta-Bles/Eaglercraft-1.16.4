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

   public MemoryTransferTask(Predicate<E> runPredicate, MemoryModuleType<? extends T> arg, MemoryModuleType<T> arg2, IntRange duration) {
      super(ImmutableMap.of(arg, MemoryModuleState.VALUE_PRESENT, arg2, MemoryModuleState.VALUE_ABSENT));
      this.runPredicate = runPredicate;
      this.sourceType = arg;
      this.targetType = arg2;
      this.duration = duration;
   }

   protected boolean shouldRun(ServerWorld arg, E arg2) {
      return this.runPredicate.test(arg2);
   }

   protected void run(ServerWorld arg, E arg2, long l) {
      Brain<?> lv = arg2.getBrain();
      lv.remember(this.targetType, (T)lv.getOptionalMemory(this.sourceType).get(), (long)this.duration.choose(arg.random));
   }
}
