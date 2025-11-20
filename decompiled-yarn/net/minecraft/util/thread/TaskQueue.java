package net.minecraft.util.thread;

import com.google.common.collect.Queues;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;

public interface TaskQueue<T, F> {
   @Nullable
   F poll();

   boolean add(T message);

   boolean isEmpty();

   public static final class Prioritized implements TaskQueue<TaskQueue.PrioritizedTask, Runnable> {
      private final List<Queue<Runnable>> queues;

      public Prioritized(int priorityCount) {
         this.queues = IntStream.range(0, priorityCount).mapToObj(_snowman -> Queues.newConcurrentLinkedQueue()).collect(Collectors.toList());
      }

      @Nullable
      public Runnable poll() {
         for (Queue<Runnable> _snowman : this.queues) {
            Runnable _snowmanx = _snowman.poll();
            if (_snowmanx != null) {
               return _snowmanx;
            }
         }

         return null;
      }

      public boolean add(TaskQueue.PrioritizedTask _snowman) {
         int _snowmanx = _snowman.getPriority();
         this.queues.get(_snowmanx).add(_snowman);
         return true;
      }

      @Override
      public boolean isEmpty() {
         return this.queues.stream().allMatch(Collection::isEmpty);
      }
   }

   public static final class PrioritizedTask implements Runnable {
      private final int priority;
      private final Runnable runnable;

      public PrioritizedTask(int priority, Runnable runnable) {
         this.priority = priority;
         this.runnable = runnable;
      }

      @Override
      public void run() {
         this.runnable.run();
      }

      public int getPriority() {
         return this.priority;
      }
   }

   public static final class Simple<T> implements TaskQueue<T, T> {
      private final Queue<T> queue;

      public Simple(Queue<T> queue) {
         this.queue = queue;
      }

      @Nullable
      @Override
      public T poll() {
         return this.queue.poll();
      }

      @Override
      public boolean add(T message) {
         return this.queue.add(message);
      }

      @Override
      public boolean isEmpty() {
         return this.queue.isEmpty();
      }
   }
}
