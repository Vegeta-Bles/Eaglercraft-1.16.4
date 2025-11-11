public enum cfd implements afs {
   a,
   b;

   private cfd() {
   }

   @Override
   public String toString() {
      return this.a();
   }

   @Override
   public String a() {
      return this == a ? "upper" : "lower";
   }
}
