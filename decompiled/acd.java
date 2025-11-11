import com.google.common.base.Stopwatch;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class acd extends acl<acd.a> {
   private static final Logger d = LogManager.getLogger();
   private final Stopwatch e = Stopwatch.createUnstarted();

   public acd(ach var1, List<acc> var2, Executor var3, Executor var4, CompletableFuture<afx> var5) {
      super(_snowman, _snowman, _snowman, _snowman, (var1x, var2x, var3x, var4x, var5x) -> {
         AtomicLong _snowman = new AtomicLong();
         AtomicLong _snowmanx = new AtomicLong();
         anp _snowmanxx = new anp(x.a, () -> 0, false);
         anp _snowmanxxx = new anp(x.a, () -> 0, false);
         CompletableFuture<Void> _snowmanxxxx = var3x.a(var1x, var2x, _snowmanxx, _snowmanxxx, var2xx -> var4x.execute(() -> {
               long _snowmanxxxxx = x.c();
               var2xx.run();
               _snowman.addAndGet(x.c() - _snowmanxxxxx);
            }), var2xx -> var5x.execute(() -> {
               long _snowmanxxxxx = x.c();
               var2xx.run();
               _snowman.addAndGet(x.c() - _snowmanxxxxx);
            }));
         return _snowmanxxxx.thenApplyAsync(var5xx -> new acd.a(var3x.c(), _snowman.d(), _snowman.d(), _snowman, _snowman), _snowman);
      }, _snowman);
      this.e.start();
      this.c.thenAcceptAsync(this::a, _snowman);
   }

   private void a(List<acd.a> var1) {
      this.e.stop();
      int _snowman = 0;
      d.info("Resource reload finished after " + this.e.elapsed(TimeUnit.MILLISECONDS) + " ms");

      for (acd.a _snowmanx : _snowman) {
         anv _snowmanxx = _snowmanx.b;
         anv _snowmanxxx = _snowmanx.c;
         int _snowmanxxxx = (int)((double)_snowmanx.d.get() / 1000000.0);
         int _snowmanxxxxx = (int)((double)_snowmanx.e.get() / 1000000.0);
         int _snowmanxxxxxx = _snowmanxxxx + _snowmanxxxxx;
         String _snowmanxxxxxxx = _snowmanx.a;
         d.info(_snowmanxxxxxxx + " took approximately " + _snowmanxxxxxx + " ms (" + _snowmanxxxx + " ms preparing, " + _snowmanxxxxx + " ms applying)");
         _snowman += _snowmanxxxxx;
      }

      d.info("Total blocking time: " + _snowman + " ms");
   }

   public static class a {
      private final String a;
      private final anv b;
      private final anv c;
      private final AtomicLong d;
      private final AtomicLong e;

      private a(String var1, anv var2, anv var3, AtomicLong var4, AtomicLong var5) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
      }
   }
}
