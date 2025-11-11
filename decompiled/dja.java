import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class dja implements dhk, Runnable {
   public static final Logger a = LogManager.getLogger();
   protected dhz b;

   public dja() {
   }

   protected static void a(int var0) {
      try {
         Thread.sleep((long)(_snowman * 1000));
      } catch (InterruptedException var2) {
         a.error("", var2);
      }
   }

   public static void a(dot var0) {
      djz _snowman = djz.C();
      _snowman.execute(() -> _snowman.a(_snowman));
   }

   public void a(dhz var1) {
      this.b = _snowman;
   }

   @Override
   public void a(nr var1) {
      this.b.a(_snowman);
   }

   public void b(nr var1) {
      this.b.b(_snowman);
   }

   public boolean c() {
      return this.b.a();
   }

   public void b() {
   }

   public void d() {
   }

   public void a() {
   }
}
