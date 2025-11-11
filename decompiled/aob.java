import com.google.common.collect.Queues;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.LockSupport;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class aob<R extends Runnable> implements aod<R>, Executor {
   private final String b;
   private static final Logger c = LogManager.getLogger();
   private final Queue<R> d = Queues.newConcurrentLinkedQueue();
   private int e;

   protected aob(String var1) {
      this.b = _snowman;
   }

   protected abstract R e(Runnable var1);

   protected abstract boolean d(R var1);

   public boolean bh() {
      return Thread.currentThread() == this.aw();
   }

   protected abstract Thread aw();

   protected boolean av() {
      return !this.bh();
   }

   public int bi() {
      return this.d.size();
   }

   @Override
   public String bj() {
      return this.b;
   }

   public <V> CompletableFuture<V> a(Supplier<V> var1) {
      return this.av() ? CompletableFuture.supplyAsync(_snowman, this) : CompletableFuture.completedFuture(_snowman.get());
   }

   private CompletableFuture<Void> a(Runnable var1) {
      return CompletableFuture.supplyAsync(() -> {
         _snowman.run();
         return null;
      }, this);
   }

   public CompletableFuture<Void> f(Runnable var1) {
      if (this.av()) {
         return this.a(_snowman);
      } else {
         _snowman.run();
         return CompletableFuture.completedFuture(null);
      }
   }

   public void g(Runnable var1) {
      if (!this.bh()) {
         this.a(_snowman).join();
      } else {
         _snowman.run();
      }
   }

   public void h(R var1) {
      this.d.add(_snowman);
      LockSupport.unpark(this.aw());
   }

   @Override
   public void execute(Runnable var1) {
      if (this.av()) {
         this.h(this.e(_snowman));
      } else {
         _snowman.run();
      }
   }

   protected void bk() {
      this.d.clear();
   }

   protected void bl() {
      while (this.y()) {
      }
   }

   protected boolean y() {
      R _snowman = this.d.peek();
      if (_snowman == null) {
         return false;
      } else if (this.e == 0 && !this.d(_snowman)) {
         return false;
      } else {
         this.c(this.d.remove());
         return true;
      }
   }

   public void c(BooleanSupplier var1) {
      this.e++;

      try {
         while (!_snowman.getAsBoolean()) {
            if (!this.y()) {
               this.bm();
            }
         }
      } finally {
         this.e--;
      }
   }

   protected void bm() {
      Thread.yield();
      LockSupport.parkNanos("waiting for tasks", 100000L);
   }

   protected void c(R var1) {
      try {
         _snowman.run();
      } catch (Exception var3) {
         c.fatal("Error executing task on {}", this.bj(), var3);
      }
   }
}
