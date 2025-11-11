import it.unimi.dsi.fastutil.ints.Int2BooleanFunction;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aoe<T> implements aod<T>, AutoCloseable, Runnable {
   private static final Logger b = LogManager.getLogger();
   private final AtomicInteger c = new AtomicInteger(0);
   public final aog<? super T, ? extends Runnable> a;
   private final Executor d;
   private final String e;

   public static aoe<Runnable> a(Executor var0, String var1) {
      return new aoe<>(new aog.c<>(new ConcurrentLinkedQueue<>()), _snowman, _snowman);
   }

   public aoe(aog<? super T, ? extends Runnable> var1, Executor var2, String var3) {
      this.d = _snowman;
      this.a = _snowman;
      this.e = _snowman;
   }

   private boolean a() {
      int _snowman;
      do {
         _snowman = this.c.get();
         if ((_snowman & 3) != 0) {
            return false;
         }
      } while (!this.c.compareAndSet(_snowman, _snowman | 2));

      return true;
   }

   private void b() {
      int _snowman;
      do {
         _snowman = this.c.get();
      } while (!this.c.compareAndSet(_snowman, _snowman & -3));
   }

   private boolean c() {
      return (this.c.get() & 1) != 0 ? false : !this.a.b();
   }

   @Override
   public void close() {
      int _snowman;
      do {
         _snowman = this.c.get();
      } while (!this.c.compareAndSet(_snowman, _snowman | 1));
   }

   private boolean d() {
      return (this.c.get() & 2) != 0;
   }

   private boolean e() {
      if (!this.d()) {
         return false;
      } else {
         Runnable _snowman = this.a.a();
         if (_snowman == null) {
            return false;
         } else {
            String _snowmanx;
            Thread _snowmanxx;
            if (w.d) {
               _snowmanxx = Thread.currentThread();
               _snowmanx = _snowmanxx.getName();
               _snowmanxx.setName(this.e);
            } else {
               _snowmanxx = null;
               _snowmanx = null;
            }

            _snowman.run();
            if (_snowmanxx != null) {
               _snowmanxx.setName(_snowmanx);
            }

            return true;
         }
      }
   }

   @Override
   public void run() {
      try {
         this.a(var0 -> var0 == 0);
      } finally {
         this.b();
         this.f();
      }
   }

   @Override
   public void a(T var1) {
      this.a.a(_snowman);
      this.f();
   }

   private void f() {
      if (this.c() && this.a()) {
         try {
            this.d.execute(this);
         } catch (RejectedExecutionException var4) {
            try {
               this.d.execute(this);
            } catch (RejectedExecutionException var3) {
               b.error("Cound not schedule mailbox", var3);
            }
         }
      }
   }

   private int a(Int2BooleanFunction var1) {
      int _snowman = 0;

      while (_snowman.get(_snowman) && this.e()) {
         _snowman++;
      }

      return _snowman;
   }

   @Override
   public String toString() {
      return this.e + " " + this.c.get() + " " + this.a.b();
   }

   @Override
   public String bj() {
      return this.e;
   }
}
