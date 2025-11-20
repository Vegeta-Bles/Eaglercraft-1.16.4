package net.minecraft.server.world;

import java.util.Objects;

public final class ChunkTicket<T> implements Comparable<ChunkTicket<?>> {
   private final ChunkTicketType<T> type;
   private final int level;
   private final T argument;
   private long tickCreated;

   protected ChunkTicket(ChunkTicketType<T> type, int level, T argument) {
      this.type = type;
      this.level = level;
      this.argument = argument;
   }

   public int compareTo(ChunkTicket<?> arg) {
      int i = Integer.compare(this.level, arg.level);
      if (i != 0) {
         return i;
      } else {
         int j = Integer.compare(System.identityHashCode(this.type), System.identityHashCode(arg.type));
         return j != 0 ? j : this.type.getArgumentComparator().compare(this.argument, (T)arg.argument);
      }
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof ChunkTicket)) {
         return false;
      } else {
         ChunkTicket<?> lv = (ChunkTicket<?>)obj;
         return this.level == lv.level && Objects.equals(this.type, lv.type) && Objects.equals(this.argument, lv.argument);
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.type, this.level, this.argument);
   }

   @Override
   public String toString() {
      return "Ticket[" + this.type + " " + this.level + " (" + this.argument + ")] at " + this.tickCreated;
   }

   public ChunkTicketType<T> getType() {
      return this.type;
   }

   public int getLevel() {
      return this.level;
   }

   protected void setTickCreated(long tickCreated) {
      this.tickCreated = tickCreated;
   }

   protected boolean isExpired(long currentTick) {
      long m = this.type.getExpiryTicks();
      return m != 0L && currentTick - this.tickCreated > m;
   }
}
