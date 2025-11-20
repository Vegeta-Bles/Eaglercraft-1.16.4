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

   public int compareTo(ChunkTicket<?> _snowman) {
      int _snowmanx = Integer.compare(this.level, _snowman.level);
      if (_snowmanx != 0) {
         return _snowmanx;
      } else {
         int _snowmanxx = Integer.compare(System.identityHashCode(this.type), System.identityHashCode(_snowman.type));
         return _snowmanxx != 0 ? _snowmanxx : this.type.getArgumentComparator().compare(this.argument, (T)_snowman.argument);
      }
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof ChunkTicket)) {
         return false;
      } else {
         ChunkTicket<?> _snowman = (ChunkTicket<?>)obj;
         return this.level == _snowman.level && Objects.equals(this.type, _snowman.type) && Objects.equals(this.argument, _snowman.argument);
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
      long _snowman = this.type.getExpiryTicks();
      return _snowman != 0L && currentTick - this.tickCreated > _snowman;
   }
}
