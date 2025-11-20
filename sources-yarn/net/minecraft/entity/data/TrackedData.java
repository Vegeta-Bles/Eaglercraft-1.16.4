package net.minecraft.entity.data;

public class TrackedData<T> {
   private final int id;
   private final TrackedDataHandler<T> dataType;

   public TrackedData(int id, TrackedDataHandler<T> arg) {
      this.id = id;
      this.dataType = arg;
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
         TrackedData<?> lv = (TrackedData<?>)o;
         return this.id == lv.id;
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
