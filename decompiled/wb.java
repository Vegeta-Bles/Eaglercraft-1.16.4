public class wb implements Runnable {
   private final int a;
   private final Runnable b;

   public wb(int var1, Runnable var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   public int a() {
      return this.a;
   }

   @Override
   public void run() {
      this.b.run();
   }
}
