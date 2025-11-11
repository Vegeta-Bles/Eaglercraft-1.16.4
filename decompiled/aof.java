public abstract class aof<R extends Runnable> extends aob<R> {
   private int b;

   public aof(String var1) {
      super(_snowman);
   }

   @Override
   protected boolean av() {
      return this.bn() || super.av();
   }

   protected boolean bn() {
      return this.b != 0;
   }

   @Override
   protected void c(R var1) {
      this.b++;

      try {
         super.c(_snowman);
      } finally {
         this.b--;
      }
   }
}
