package net.minecraft.test;

import javax.annotation.Nullable;

class TimedTask {
   @Nullable
   public final Long duration;
   public final Runnable task;

   TimedTask(@Nullable Long duration, Runnable task) {
      this.duration = duration;
      this.task = task;
   }
}
