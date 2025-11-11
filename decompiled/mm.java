public class mm {
   public static final mm a = new mm(0L) {
      @Override
      public void a(long var1) {
      }
   };
   private final long b;
   private long c;

   public mm(long var1) {
      this.b = _snowman;
   }

   public void a(long var1) {
      this.c += _snowman / 8L;
      if (this.c > this.b) {
         throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: " + this.c + "bytes where max allowed: " + this.b);
      }
   }
}
