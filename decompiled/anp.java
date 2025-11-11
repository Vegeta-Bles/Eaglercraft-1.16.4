import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongMaps;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class anp implements anu {
   private static final long a = Duration.ofMillis(100L).toNanos();
   private static final Logger b = LogManager.getLogger();
   private final List<String> c = Lists.newArrayList();
   private final LongList d = new LongArrayList();
   private final Map<String, anp.a> e = Maps.newHashMap();
   private final IntSupplier f;
   private final LongSupplier g;
   private final long h;
   private final int i;
   private String j = "";
   private boolean k;
   @Nullable
   private anp.a l;
   private final boolean m;

   public anp(LongSupplier var1, IntSupplier var2, boolean var3) {
      this.h = _snowman.getAsLong();
      this.g = _snowman;
      this.i = _snowman.getAsInt();
      this.f = _snowman;
      this.m = _snowman;
   }

   @Override
   public void a() {
      if (this.k) {
         b.error("Profiler tick already started - missing endTick()?");
      } else {
         this.k = true;
         this.j = "";
         this.c.clear();
         this.a("root");
      }
   }

   @Override
   public void b() {
      if (!this.k) {
         b.error("Profiler tick already ended - missing startTick()?");
      } else {
         this.c();
         this.k = false;
         if (!this.j.isEmpty()) {
            b.error(
               "Profiler tick ended before path was fully popped (remainder: '{}'). Mismatched push/pop?",
               new org.apache.logging.log4j.util.Supplier[]{() -> anv.b(this.j)}
            );
         }
      }
   }

   @Override
   public void a(String var1) {
      if (!this.k) {
         b.error("Cannot push '{}' to profiler if_ profiler tick hasn't started - missing startTick()?", _snowman);
      } else {
         if (!this.j.isEmpty()) {
            this.j = this.j + '\u001e';
         }

         this.j = this.j + _snowman;
         this.c.add(this.j);
         this.d.add(x.c());
         this.l = null;
      }
   }

   @Override
   public void a(Supplier<String> var1) {
      this.a(_snowman.get());
   }

   @Override
   public void c() {
      if (!this.k) {
         b.error("Cannot pop from profiler if_ profiler tick hasn't started - missing startTick()?");
      } else if (this.d.isEmpty()) {
         b.error("Tried to pop one too many times! Mismatched push() and pop()?");
      } else {
         long _snowman = x.c();
         long _snowmanx = this.d.removeLong(this.d.size() - 1);
         this.c.remove(this.c.size() - 1);
         long _snowmanxx = _snowman - _snowmanx;
         anp.a _snowmanxxx = this.e();
         _snowmanxxx.a = _snowmanxxx.a + _snowmanxx;
         _snowmanxxx.b = _snowmanxxx.b + 1L;
         if (this.m && _snowmanxx > a) {
            b.warn(
               "Something's taking too long! '{}' took aprox {} ms",
               new org.apache.logging.log4j.util.Supplier[]{() -> anv.b(this.j), () -> (double)_snowman / 1000000.0}
            );
         }

         this.j = this.c.isEmpty() ? "" : this.c.get(this.c.size() - 1);
         this.l = null;
      }
   }

   @Override
   public void b(String var1) {
      this.c();
      this.a(_snowman);
   }

   @Override
   public void b(Supplier<String> var1) {
      this.c();
      this.a(_snowman);
   }

   private anp.a e() {
      if (this.l == null) {
         this.l = this.e.computeIfAbsent(this.j, var0 -> new anp.a());
      }

      return this.l;
   }

   @Override
   public void c(String var1) {
      this.e().c.addTo(_snowman, 1L);
   }

   @Override
   public void c(Supplier<String> var1) {
      this.e().c.addTo(_snowman.get(), 1L);
   }

   @Override
   public anv d() {
      return new ans(this.e, this.h, this.i, this.g.getAsLong(), this.f.getAsInt());
   }

   static class a implements anx {
      private long a;
      private long b;
      private Object2LongOpenHashMap<String> c = new Object2LongOpenHashMap();

      private a() {
      }

      @Override
      public long a() {
         return this.a;
      }

      @Override
      public long b() {
         return this.b;
      }

      @Override
      public Object2LongMap<String> c() {
         return Object2LongMaps.unmodifiable(this.c);
      }
   }
}
