package net.minecraft.entity.data;

public class TrackedData<T> {
   private final int id;
   private final TrackedDataHandler<T> dataType;

   public TrackedData(int id, TrackedDataHandler<T> _snowman) {
      this.id = id;
      this.dataType = _snowman;
   }

   public int getId() {
      return this.id;
   }

   public TrackedDataHandler<T> getType() {
      return this.dataType;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         TrackedData<?> _snowman = (TrackedData<?>)o;
         return this.id == _snowman.id;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.id;
   }

   @Override
   public String toString() {
      return "<entity data: " + this.id + ">";
   }
}
