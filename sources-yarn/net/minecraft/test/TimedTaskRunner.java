package net.minecraft.test;

import java.util.Iterator;
import java.util.List;

public class TimedTaskRunner {
   private final GameTest test;
   private final List<TimedTask> tasks;
   private long tick;

   public TimedTaskRunner(GameTest test, List<TimedTask> tasks) {
      this.test = test;
      this.tasks = tasks;
   }

   public void runSilently(long tick) {
      try {
         this.runTasks(tick);
      } catch (Exception var4) {
      }
   }

   public void runReported(long tick) {
      try {
         this.runTasks(tick);
      } catch (Exception var4) {
         this.test.fail(var4);
      }
   }

   private void runTasks(long tick) {
      Iterator<TimedTask> iterator = this.tasks.iterator();

      while (iterator.hasNext()) {
         TimedTask lv = iterator.next();
         lv.task.run();
         iterator.remove();
         long m = tick - this.tick;
         long n = this.tick;
         this.tick = tick;
         if (lv.duration != null && lv.duration != m) {
            this.test.fail(new TimeMismatchException("Succeeded in invalid tick: expected " + (n + lv.duration) + ", but current tick is " + tick));
            break;
         }
      }
   }
}
