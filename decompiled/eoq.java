import com.google.common.util.concurrent.RateLimiter;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

public class eoq {
   private final float a;
   private final AtomicReference<eoq.a> b = new AtomicReference<>();

   public eoq(Duration var1) {
      this.a = 1000.0F / (float)_snowman.toMillis();
   }

   public void a(String var1) {
      eoq.a _snowman = this.b.updateAndGet(var2x -> var2x != null && _snowman.equals(var2x.a) ? var2x : new eoq.a(_snowman, RateLimiter.create((double)this.a)));
      if (_snowman.b.tryAcquire(1)) {
         dkz.b.a(no.b, new oe(_snowman), x.b);
      }
   }

   static class a {
      private final String a;
      private final RateLimiter b;

      a(String var1, RateLimiter var2) {
         this.a = _snowman;
         this.b = _snowman;
      }
   }
}
