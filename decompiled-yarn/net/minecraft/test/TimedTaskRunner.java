package net.minecraft.test;

import java.util.Iterator;
import java.util.List;

public class TimedTaskRunner {
   private final GameTest test;
   private final List<TimedTask> tasks;
   private long tick;

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
      Iterator<TimedTask> _snowman = this.tasks.iterator();

      while (_snowman.hasNext()) {
         TimedTask _snowmanx = _snowman.next();
         _snowmanx.task.run();
         _snowman.remove();
         long _snowmanxx = tick - this.tick;
         long _snowmanxxx = this.tick;
         this.tick = tick;
         if (_snowmanx.duration != null && _snowmanx.duration != _snowmanxx) {
            this.test.fail(new TimeMismatchException("Succeeded in invalid tick: expected " + (_snowmanxxx + _snowmanx.duration) + ", but current tick is " + tick));
            break;
         }
      }
   }
}
