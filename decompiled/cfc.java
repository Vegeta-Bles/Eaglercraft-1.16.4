public enum cfc implements afs {
   a,
   b;

   private cfc() {
   }

   @Override
   public String toString() {
      return this.a();
   }

   @Override
   public String a() {
      return this == a ? "left" : "right";
   }
}
