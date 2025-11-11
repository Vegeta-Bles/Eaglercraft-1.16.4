import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2LongMap.Entry;
import java.util.Collection;
import javax.annotation.Nullable;

public class lf {
   private final lu a;
   @Nullable
   private fx b;
   private final aag c;
   private final Collection<lg> d = Lists.newArrayList();
   private final int e;
   private final Collection<lj> f = Lists.newCopyOnWriteArrayList();
   private Object2LongMap<Runnable> g = new Object2LongOpenHashMap();
   private long h;
   private long i;
   private boolean j = false;
   private final Stopwatch k = Stopwatch.createUnstarted();
   private boolean l = false;
   private final bzm m;
   @Nullable
   private Throwable n;

   public lf(lu var1, bzm var2, aag var3) {
      this.a = _snowman;
      this.c = _snowman;
      this.e = _snowman.c();
      this.m = _snowman.g().a(_snowman);
   }

   void a(fx var1) {
      this.b = _snowman;
   }

   void a() {
      this.h = this.c.T() + 1L + this.a.f();
      this.k.start();
   }

   public void b() {
      if (!this.k()) {
         this.i = this.c.T() - this.h;
         if (this.i >= 0L) {
            if (this.i == 0L) {
               this.v();
            }

            ObjectIterator<Entry<Runnable>> _snowman = this.g.object2LongEntrySet().iterator();

            while (_snowman.hasNext()) {
               Entry<Runnable> _snowmanx = (Entry<Runnable>)_snowman.next();
               if (_snowmanx.getLongValue() <= this.i) {
                  try {
                     ((Runnable)_snowmanx.getKey()).run();
                  } catch (Exception var4) {
                     this.a(var4);
                  }

                  _snowman.remove();
               }
            }

            if (this.i > (long)this.e) {
               if (this.f.isEmpty()) {
                  this.a(new lm("Didn't succeed or fail within " + this.a.c() + " ticks"));
               } else {
                  this.f.forEach(var1x -> var1x.b(this.i));
                  if (this.n == null) {
                     this.a(new lm("No sequences finished"));
                  }
               }
            } else {
               this.f.forEach(var1x -> var1x.a(this.i));
            }
         }
      }
   }

   private void v() {
      if (this.j) {
         throw new IllegalStateException("Test already started");
      } else {
         this.j = true;

         try {
            this.a.a(new le(this));
         } catch (Exception var2) {
            this.a(var2);
         }
      }
   }

   public String c() {
      return this.a.a();
   }

   public fx d() {
      return this.b;
   }

   public aag g() {
      return this.c;
   }

   public boolean h() {
      return this.l && this.n == null;
   }

   public boolean i() {
      return this.n != null;
   }

   public boolean j() {
      return this.j;
   }

   public boolean k() {
      return this.l;
   }

   private void x() {
      if (!this.l) {
         this.l = true;
         this.k.stop();
      }
   }

   public void a(Throwable var1) {
      this.x();
      this.n = _snowman;
      this.d.forEach(var1x -> var1x.c(this));
   }

   @Nullable
   public Throwable n() {
      return this.n;
   }

   @Override
   public String toString() {
      return this.c();
   }

   public void a(lg var1) {
      this.d.add(_snowman);
   }

   public void a(fx var1, int var2) {
      cdj _snowman = lq.a(this.s(), _snowman, this.t(), _snowman, this.c, false);
      this.a(_snowman.o());
      _snowman.a(this.c());
      lq.a(this.b, new fx(1, 0, -1), this.t(), this.c);
      this.d.forEach(var1x -> var1x.a(this));
   }

   public boolean q() {
      return this.a.d();
   }

   public boolean r() {
      return !this.a.d();
   }

   public String s() {
      return this.a.b();
   }

   public bzm t() {
      return this.m;
   }

   public lu u() {
      return this.a;
   }
}
