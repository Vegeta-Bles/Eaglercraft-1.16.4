import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class adi implements Runnable {
   private static final Logger d = LogManager.getLogger();
   private static final AtomicInteger e = new AtomicInteger(0);
   protected volatile boolean a;
   protected final String b;
   @Nullable
   protected Thread c;

   protected adi(String var1) {
      this.b = _snowman;
   }

   public synchronized boolean a() {
      if (this.a) {
         return true;
      } else {
         this.a = true;
         this.c = new Thread(this, this.b + " #" + e.incrementAndGet());
         this.c.setUncaughtExceptionHandler(new p(d));
         this.c.start();
         d.info("Thread {} started", this.b);
         return true;
      }
   }

   public synchronized void b() {
      this.a = false;
      if (null != this.c) {
         int _snowman = 0;

         while (this.c.isAlive()) {
            try {
               this.c.join(1000L);
               if (++_snowman >= 5) {
                  d.warn("Waited {} seconds attempting force stop!", _snowman);
               } else if (this.c.isAlive()) {
                  d.warn("Thread {} ({}) failed to exit after {} second(s)", this, this.c.getState(), _snowman, new Exception("Stack:"));
                  this.c.interrupt();
               }
            } catch (InterruptedException var3) {
            }
         }

         d.info("Thread {} stopped", this.b);
         this.c = null;
      }
   }

   public boolean c() {
      return this.a;
   }
}
