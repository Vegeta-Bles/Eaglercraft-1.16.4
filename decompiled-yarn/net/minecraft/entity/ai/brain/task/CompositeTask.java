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
      tasks.forEach(_snowman -> this.tasks.add((Task<? super E>)_snowman.getFirst(), (Integer)_snowman.getSecond()));
   }

   @Override
   protected boolean shouldKeepRunning(ServerWorld world, E entity, long time) {
      return this.tasks.stream().filter(_snowman -> _snowman.getStatus() == Task.Status.RUNNING).anyMatch(_snowmanxxx -> _snowmanxxx.shouldKeepRunning(world, entity, time));
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
      this.tasks.stream().filter(_snowman -> _snowman.getStatus() == Task.Status.RUNNING).forEach(_snowmanxxx -> _snowmanxxx.tick(world, entity, time));
   }

   @Override
   protected void finishRunning(ServerWorld world, E entity, long time) {
      this.tasks.stream().filter(_snowman -> _snowman.getStatus() == Task.Status.RUNNING).forEach(_snowmanxxx -> _snowmanxxx.stop(world, entity, time));
      this.memoriesToForgetWhenStopped.forEach(entity.getBrain()::forget);
   }

   @Override
   public String toString() {
      Set<? extends Task<? super E>> _snowman = this.tasks.stream().filter(_snowmanx -> _snowmanx.getStatus() == Task.Status.RUNNING).collect(Collectors.toSet());
      return "(" + this.getClass().getSimpleName() + "): " + _snowman;
   }

   static enum Order {
      ORDERED(_snowman -> {
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
            tasks.stream().filter(_snowman -> _snowman.getStatus() == Task.Status.STOPPED).filter(_snowmanxxx -> _snowmanxxx.tryStarting(world, entity, time)).findFirst();
         }
      },
      TRY_ALL {
         @Override
         public <E extends LivingEntity> void run(WeightedList<Task<? super E>> tasks, ServerWorld world, E entity, long time) {
            tasks.stream().filter(_snowman -> _snowman.getStatus() == Task.Status.STOPPED).forEach(_snowmanxxx -> _snowmanxxx.tryStarting(world, entity, time));
         }
      };

      private RunMode() {
      }

      public abstract <E extends LivingEntity> void run(WeightedList<Task<? super E>> tasks, ServerWorld world, E entity, long time);
   }
}
