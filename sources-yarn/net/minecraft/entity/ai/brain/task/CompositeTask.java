package net.minecraft.entity.ai.brain.task;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.WeightedList;

public class CompositeTask<E extends LivingEntity> extends Task<E> {
   private final Set<MemoryModuleType<?>> memoriesToForgetWhenStopped;
   private final CompositeTask.Order order;
   private final CompositeTask.RunMode runMode;
   private final WeightedList<Task<? super E>> tasks = new WeightedList<>();

   public CompositeTask(
      Map<MemoryModuleType<?>, MemoryModuleState> requiredMemoryState,
      Set<MemoryModuleType<?>> memoriesToForgetWhenStopped,
      CompositeTask.Order order,
      CompositeTask.RunMode runMode,
      List<Pair<Task<? super E>, Integer>> tasks
   ) {
      super(requiredMemoryState);
      this.memoriesToForgetWhenStopped = memoriesToForgetWhenStopped;
      this.order = order;
      this.runMode = runMode;
      tasks.forEach(pair -> this.tasks.add((Task<? super E>)pair.getFirst(), (Integer)pair.getSecond()));
   }

   @Override
   protected boolean shouldKeepRunning(ServerWorld world, E entity, long time) {
      return this.tasks.stream().filter(arg -> arg.getStatus() == Task.Status.RUNNING).anyMatch(arg3 -> arg3.shouldKeepRunning(world, entity, time));
   }

   @Override
   protected boolean isTimeLimitExceeded(long time) {
      return false;
   }

   @Override
   protected void run(ServerWorld world, E entity, long time) {
      this.order.apply(this.tasks);
      this.runMode.run(this.tasks, world, entity, time);
   }

   @Override
   protected void keepRunning(ServerWorld world, E entity, long time) {
      this.tasks.stream().filter(arg -> arg.getStatus() == Task.Status.RUNNING).forEach(arg3 -> arg3.tick(world, entity, time));
   }

   @Override
   protected void finishRunning(ServerWorld world, E entity, long time) {
      this.tasks.stream().filter(arg -> arg.getStatus() == Task.Status.RUNNING).forEach(arg3 -> arg3.stop(world, entity, time));
      this.memoriesToForgetWhenStopped.forEach(entity.getBrain()::forget);
   }

   @Override
   public String toString() {
      Set<? extends Task<? super E>> set = this.tasks.stream().filter(arg -> arg.getStatus() == Task.Status.RUNNING).collect(Collectors.toSet());
      return "(" + this.getClass().getSimpleName() + "): " + set;
   }

   static enum Order {
      ORDERED(arg -> {
      }),
      SHUFFLED(WeightedList::shuffle);

      private final Consumer<WeightedList<?>> listModifier;

      private Order(Consumer<WeightedList<?>> listModifier) {
         this.listModifier = listModifier;
      }

      public void apply(WeightedList<?> list) {
         this.listModifier.accept(list);
      }
   }

   static enum RunMode {
      RUN_ONE {
         @Override
         public <E extends LivingEntity> void run(WeightedList<Task<? super E>> tasks, ServerWorld world, E entity, long time) {
            tasks.stream().filter(arg -> arg.getStatus() == Task.Status.STOPPED).filter(arg3 -> arg3.tryStarting(world, entity, time)).findFirst();
         }
      },
      TRY_ALL {
         @Override
         public <E extends LivingEntity> void run(WeightedList<Task<? super E>> tasks, ServerWorld world, E entity, long time) {
            tasks.stream().filter(arg -> arg.getStatus() == Task.Status.STOPPED).forEach(arg3 -> arg3.tryStarting(world, entity, time));
         }
      };

      private RunMode() {
      }

      public abstract <E extends LivingEntity> void run(WeightedList<Task<? super E>> tasks, ServerWorld world, E entity, long time);
   }
}
