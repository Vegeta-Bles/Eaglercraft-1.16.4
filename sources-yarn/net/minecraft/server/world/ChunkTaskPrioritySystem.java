package net.minecraft.server.world;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Either;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.thread.MessageListener;
import net.minecraft.util.thread.TaskExecutor;
import net.minecraft.util.thread.TaskQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkTaskPrioritySystem implements AutoCloseable, ChunkHolder.LevelUpdateListener {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Map<MessageListener<?>, LevelPrioritizedQueue<? extends Function<MessageListener<Unit>, ?>>> queues;
   private final Set<MessageListener<?>> idleActors;
   private final TaskExecutor<TaskQueue.PrioritizedTask> controlActor;

   public ChunkTaskPrioritySystem(List<MessageListener<?>> actors, Executor executor, int maxQueues) {
      this.queues = actors.stream().collect(Collectors.toMap(Function.identity(), actor -> new LevelPrioritizedQueue<>(actor.getName() + "_queue", maxQueues)));
      this.idleActors = Sets.newHashSet(actors);
      this.controlActor = new TaskExecutor<>(new TaskQueue.Prioritized(4), executor, "sorter");
   }

   public static ChunkTaskPrioritySystem.Task<Runnable> createMessage(Runnable task, long pos, IntSupplier lastLevelUpdatedToProvider) {
      return new ChunkTaskPrioritySystem.Task<>(yield -> () -> {
            task.run();
            yield.send(Unit.INSTANCE);
         }, pos, lastLevelUpdatedToProvider);
   }

   public static ChunkTaskPrioritySystem.Task<Runnable> createMessage(ChunkHolder holder, Runnable task) {
      return createMessage(task, holder.getPos().toLong(), holder::getCompletedLevel);
   }

   public static ChunkTaskPrioritySystem.UnblockingMessage createUnblockingMessage(Runnable task, long pos, boolean removeTask) {
      return new ChunkTaskPrioritySystem.UnblockingMessage(task, pos, removeTask);
   }

   public <T> MessageListener<ChunkTaskPrioritySystem.Task<T>> createExecutor(MessageListener<T> executor, boolean addBlocker) {
      return this.controlActor
         .<MessageListener<ChunkTaskPrioritySystem.Task<T>>>ask(
            yield -> new TaskQueue.PrioritizedTask(
                  0,
                  () -> {
                     this.getQueue(executor);
                     yield.send(
                        MessageListener.create(
                           "chunk priority sorter around " + executor.getName(),
                           arg2 -> this.enqueueChunk(executor, arg2.taskFunction, arg2.pos, arg2.lastLevelUpdatedToProvider, addBlocker)
                        )
                     );
                  }
               )
         )
         .join();
   }

   public MessageListener<ChunkTaskPrioritySystem.UnblockingMessage> createUnblockingExecutor(MessageListener<Runnable> executor) {
      return this.controlActor
         .<MessageListener<ChunkTaskPrioritySystem.UnblockingMessage>>ask(
            yield -> new TaskQueue.PrioritizedTask(
                  0,
                  () -> yield.send(
                        MessageListener.create(
                           "chunk priority sorter around " + executor.getName(), arg2 -> this.removeChunk(executor, arg2.pos, arg2.callback, arg2.removeTask)
                        )
                     )
               )
         )
         .join();
   }

   @Override
   public void updateLevel(ChunkPos pos, IntSupplier levelGetter, int targetLevel, IntConsumer levelSetter) {
      this.controlActor.send(new TaskQueue.PrioritizedTask(0, () -> {
         int j = levelGetter.getAsInt();
         this.queues.values().forEach(queue -> queue.updateLevel(j, pos, targetLevel));
         levelSetter.accept(targetLevel);
      }));
   }

   private <T> void removeChunk(MessageListener<T> actor, long chunkPos, Runnable callback, boolean clearTask) {
      this.controlActor.send(new TaskQueue.PrioritizedTask(1, () -> {
         LevelPrioritizedQueue<Function<MessageListener<Unit>, T>> lv = this.getQueue(actor);
         lv.remove(chunkPos, clearTask);
         if (this.idleActors.remove(actor)) {
            this.enqueueExecution(lv, actor);
         }

         callback.run();
      }));
   }

   private <T> void enqueueChunk(
      MessageListener<T> actor, Function<MessageListener<Unit>, T> task, long chunkPos, IntSupplier lastLevelUpdatedToProvider, boolean addBlocker
   ) {
      this.controlActor.send(new TaskQueue.PrioritizedTask(2, () -> {
         LevelPrioritizedQueue<Function<MessageListener<Unit>, T>> lv = this.getQueue(actor);
         int i = lastLevelUpdatedToProvider.getAsInt();
         lv.add(Optional.of(task), chunkPos, i);
         if (addBlocker) {
            lv.add(Optional.empty(), chunkPos, i);
         }

         if (this.idleActors.remove(actor)) {
            this.enqueueExecution(lv, actor);
         }
      }));
   }

   private <T> void enqueueExecution(LevelPrioritizedQueue<Function<MessageListener<Unit>, T>> queue, MessageListener<T> actor) {
      this.controlActor.send(new TaskQueue.PrioritizedTask(3, () -> {
         Stream<Either<Function<MessageListener<Unit>, T>, Runnable>> stream = queue.poll();
         if (stream == null) {
            this.idleActors.add(actor);
         } else {
            List<CompletableFuture<Unit>> futures = stream
               .map(executeOrAddBlocking -> executeOrAddBlocking.map(
                     task -> actor.ask(task).thenApply(ignored -> Unit.INSTANCE),
                     addBlocking -> {
                        addBlocking.run();
                        return CompletableFuture.completedFuture(Unit.INSTANCE);
                     }
                  ))
               .collect(Collectors.toList());
            Util.combine(futures).thenAccept(ignored -> this.enqueueExecution(queue, actor));
         }
      }));
   }

   private <T> LevelPrioritizedQueue<Function<MessageListener<Unit>, T>> getQueue(MessageListener<T> actor) {
      LevelPrioritizedQueue<? extends Function<MessageListener<Unit>, ?>> lv = this.queues.get(actor);
      if (lv == null) {
         throw (IllegalArgumentException)Util.throwOrPause(new IllegalArgumentException("No queue for: " + actor));
      } else {
         return (LevelPrioritizedQueue<Function<MessageListener<Unit>, T>>)lv;
      }
   }

   @VisibleForTesting
   public String getDebugString() {
      return this.queues
            .entrySet()
            .stream()
            .map(
               entry -> entry.getKey().getName()
                     + "=["
                     + entry.getValue().getBlockingChunks().stream().map(long_ -> long_ + ":" + new ChunkPos(long_)).collect(Collectors.joining(","))
                     + "]"
            )
            .collect(Collectors.joining(","))
         + ", s="
         + this.idleActors.size();
   }

   @Override
   public void close() {
      this.queues.keySet().forEach(MessageListener::close);
   }

   public static final class Task<T> {
      private final Function<MessageListener<Unit>, T> taskFunction;
      private final long pos;
      private final IntSupplier lastLevelUpdatedToProvider;

      private Task(Function<MessageListener<Unit>, T> function, long pos, IntSupplier lastLevelUpdatedToProvider) {
         this.taskFunction = function;
         this.pos = pos;
         this.lastLevelUpdatedToProvider = lastLevelUpdatedToProvider;
      }
   }

   public static final class UnblockingMessage {
      private final Runnable callback;
      private final long pos;
      private final boolean removeTask;

      private UnblockingMessage(Runnable callback, long pos, boolean removeTask) {
         this.callback = callback;
         this.pos = pos;
         this.removeTask = removeTask;
      }
   }
}
