import java.util.concurrent.locks.LockSupport;

public class ens extends aob<Runnable> {
   private Thread a = this.b();
   private volatile boolean b;

   public ens() {
      super("Sound executor");
   }

   private Thread b() {
      Thread _snowman = new Thread(this::c);
      _snowman.setDaemon(true);
      _snowman.setName("Sound engine");
      _snowman.start();
      return _snowman;
   }

   @Override
   protected Runnable e(Runnable var1) {
      return _snowman;
   }

   @Override
   protected boolean d(Runnable var1) {
      return !this.b;
   }

   @Override
   protected Thread aw() {
      return this.a;
   }

   private void c() {
      while (!this.b) {
         this.c(() -> this.b);
      }
   }

   @Override
   protected void bm() {
      LockSupport.park("waiting for tasks");
   }

   public void a() {
      this.b = true;
      this.a.interrupt();

      try {
         this.a.join();
      } catch (InterruptedException var2) {
         Thread.currentThread().interrupt();
      }

      this.bk();
      this.b = false;
      this.a = this.b();
   }
}
