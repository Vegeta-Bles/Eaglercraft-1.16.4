package net.minecraft.world;

import java.util.Comparator;
import net.minecraft.util.math.BlockPos;

public class ScheduledTick<T> {
   private static long idCounter;
   private final T object;
   public final BlockPos pos;
   public final long time;
   public final TickPriority priority;
   private final long id;

   public ScheduledTick(BlockPos pos, T t) {
      this(pos, t, 0L, TickPriority.NORMAL);
   }

   public ScheduledTick(BlockPos pos, T t, long time, TickPriority priority) {
      this.id = idCounter++;
      this.pos = pos.toImmutable();
      this.object = t;
      this.time = time;
      this.priority = priority;
   }

   @Override
   public boolean equals(Object o) {
      if (!(o instanceof ScheduledTick)) {
         return false;
      } else {
         ScheduledTick<?> _snowman = (ScheduledTick<?>)o;
         return this.pos.equals(_snowman.pos) && this.object == _snowman.object;
      }
   }

   @Override
   public int hashCode() {
      return this.pos.hashCode();
   }

   public static <T> Comparator<ScheduledTick<T>> getComparator() {
      return Comparator.<ScheduledTick<T>>comparingLong(_snowman -> _snowman.time).thenComparing(_snowman -> _snowman.priority).thenComparingLong(_snowman -> _snowman.id);
   }

   @Override
   public String toString() {
      return this.object + ": " + this.pos + ", " + this.time + ", " + this.priority + ", " + this.id;
   }

   public T getObject() {
      return this.object;
   }
}
