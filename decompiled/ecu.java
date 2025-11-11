import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.primitives.Doubles;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ecu {
   private static final Logger a = LogManager.getLogger();
   private final PriorityQueue<ecu.c.a> b = Queues.newPriorityQueue();
   private final Queue<dzt> c;
   private final Queue<Runnable> d = Queues.newConcurrentLinkedQueue();
   private volatile int e;
   private volatile int f;
   private final dzt g;
   private final aoe<Runnable> h;
   private final Executor i;
   private brx j;
   private final eae k;
   private dcn l = dcn.a;

   public ecu(brx var1, eae var2, Executor var3, boolean var4, dzt var5) {
      this.j = _snowman;
      this.k = _snowman;
      int _snowman = Math.max(1, (int)((double)Runtime.getRuntime().maxMemory() * 0.3) / (eao.u().stream().mapToInt(eao::v).sum() * 4) - 1);
      int _snowmanx = Runtime.getRuntime().availableProcessors();
      int _snowmanxx = _snowman ? _snowmanx : Math.min(_snowmanx, 4);
      int _snowmanxxx = Math.max(1, Math.min(_snowmanxx, _snowman));
      this.g = _snowman;
      List<dzt> _snowmanxxxx = Lists.newArrayListWithExpectedSize(_snowmanxxx);

      try {
         for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxx; _snowmanxxxxx++) {
            _snowmanxxxx.add(new dzt());
         }
      } catch (OutOfMemoryError var14) {
         a.warn("Allocated only {}/{} buffers", _snowmanxxxx.size(), _snowmanxxx);
         int _snowmanxxxxx = Math.min(_snowmanxxxx.size() * 2 / 3, _snowmanxxxx.size() - 1);

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxxx; _snowmanxxxxxx++) {
            _snowmanxxxx.remove(_snowmanxxxx.size() - 1);
         }

         System.gc();
      }

      this.c = Queues.newArrayDeque(_snowmanxxxx);
      this.f = this.c.size();
      this.i = _snowman;
      this.h = aoe.a(_snowman, "Chunk Renderer");
      this.h.a(this::h);
   }

   public void a(brx var1) {
      this.j = _snowman;
   }

   private void h() {
      if (!this.c.isEmpty()) {
         ecu.c.a _snowman = this.b.poll();
         if (_snowman != null) {
            dzt _snowmanx = this.c.poll();
            this.e = this.b.size();
            this.f = this.c.size();
            CompletableFuture.runAsync(() -> {
            }, this.i).thenCompose(var2x -> _snowman.a(_snowman)).whenComplete((var2x, var3) -> {
               if (var3 != null) {
                  l _snowmanxx = l.a(var3, "Batching chunks");
                  djz.C().a(djz.C().c(_snowmanxx));
               } else {
                  this.h.a(() -> {
                     if (var2x == ecu.a.a) {
                        _snowman.a();
                     } else {
                        _snowman.b();
                     }

                     this.c.add(_snowman);
                     this.f = this.c.size();
                     this.h();
                  });
               }
            });
         }
      }
   }

   public String b() {
      return String.format("pC: %03d, pU: %02d, aB: %02d", this.e, this.d.size(), this.f);
   }

   public void a(dcn var1) {
      this.l = _snowman;
   }

   public dcn c() {
      return this.l;
   }

   public boolean d() {
      boolean _snowman;
      Runnable _snowmanx;
      for (_snowman = false; (_snowmanx = this.d.poll()) != null; _snowman = true) {
         _snowmanx.run();
      }

      return _snowman;
   }

   public void a(ecu.c var1) {
      _snowman.k();
   }

   public void e() {
      this.i();
   }

   public void a(ecu.c.a var1) {
      this.h.a(() -> {
         this.b.offer(_snowman);
         this.e = this.b.size();
         this.h();
      });
   }

   public CompletableFuture<Void> a(dfh var1, dfp var2) {
      return CompletableFuture.runAsync(() -> {
      }, this.d::add).thenCompose(var3 -> this.b(_snowman, _snowman));
   }

   private CompletableFuture<Void> b(dfh var1, dfp var2) {
      return _snowman.b(_snowman);
   }

   private void i() {
      while (!this.b.isEmpty()) {
         ecu.c.a _snowman = this.b.poll();
         if (_snowman != null) {
            _snowman.a();
         }
      }

      this.e = 0;
   }

   public boolean f() {
      return this.e == 0 && this.d.isEmpty();
   }

   public void g() {
      this.i();
      this.h.close();
      this.c.clear();
   }

   static enum a {
      a,
      b;

      private a() {
      }
   }

   public static class b {
      public static final ecu.b a = new ecu.b() {
         @Override
         public boolean a(gc var1, gc var2) {
            return false;
         }
      };
      private final Set<eao> b = new ObjectArraySet();
      private final Set<eao> c = new ObjectArraySet();
      private boolean d = true;
      private final List<ccj> e = Lists.newArrayList();
      private ecx f = new ecx();
      @Nullable
      private dfh.b g;

      public b() {
      }

      public boolean a() {
         return this.d;
      }

      public boolean a(eao var1) {
         return !this.b.contains(_snowman);
      }

      public List<ccj> b() {
         return this.e;
      }

      public boolean a(gc var1, gc var2) {
         return this.f.a(_snowman, _snowman);
      }
   }

   public class c {
      public final AtomicReference<ecu.b> a = new AtomicReference<>(ecu.b.a);
      @Nullable
      private ecu.c.b d;
      @Nullable
      private ecu.c.c e;
      private final Set<ccj> f = Sets.newHashSet();
      private final Map<eao, dfp> g = eao.u().stream().collect(Collectors.toMap(var0 -> (eao)var0, var0 -> new dfp(dfk.h)));
      public dci b;
      private int h = -1;
      private boolean i = true;
      private final fx.a j = new fx.a(-1, -1, -1);
      private final fx.a[] k = x.a(new fx.a[6], var0 -> {
         for (int _snowman = 0; _snowman < var0.length; _snowman++) {
            var0[_snowman] = new fx.a();
         }
      });
      private boolean l;

      public c() {
      }

      private boolean a(fx var1) {
         return ecu.this.j.a(_snowman.u() >> 4, _snowman.w() >> 4, cga.m, false) != null;
      }

      public boolean a() {
         int _snowman = 24;
         return !(this.b() > 576.0)
            ? true
            : this.a(this.k[gc.e.ordinal()]) && this.a(this.k[gc.c.ordinal()]) && this.a(this.k[gc.f.ordinal()]) && this.a(this.k[gc.d.ordinal()]);
      }

      public boolean a(int var1) {
         if (this.h == _snowman) {
            return false;
         } else {
            this.h = _snowman;
            return true;
         }
      }

      public dfp a(eao var1) {
         return this.g.get(_snowman);
      }

      public void a(int var1, int var2, int var3) {
         if (_snowman != this.j.u() || _snowman != this.j.v() || _snowman != this.j.w()) {
            this.l();
            this.j.d(_snowman, _snowman, _snowman);
            this.b = new dci((double)_snowman, (double)_snowman, (double)_snowman, (double)(_snowman + 16), (double)(_snowman + 16), (double)(_snowman + 16));

            for (gc _snowman : gc.values()) {
               this.k[_snowman.ordinal()].g(this.j).c(_snowman, 16);
            }
         }
      }

      protected double b() {
         djk _snowman = djz.C().h.k();
         double _snowmanx = this.b.a + 8.0 - _snowman.b().b;
         double _snowmanxx = this.b.b + 8.0 - _snowman.b().c;
         double _snowmanxxx = this.b.c + 8.0 - _snowman.b().d;
         return _snowmanx * _snowmanx + _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx;
      }

      private void a(dfh var1) {
         _snowman.a(7, dfk.h);
      }

      public ecu.b c() {
         return this.a.get();
      }

      private void l() {
         this.i();
         this.a.set(ecu.b.a);
         this.i = true;
      }

      public void d() {
         this.l();
         this.g.values().forEach(dfp::close);
      }

      public fx e() {
         return this.j;
      }

      public void a(boolean var1) {
         boolean _snowman = this.i;
         this.i = true;
         this.l = _snowman | (_snowman && this.l);
      }

      public void f() {
         this.i = false;
         this.l = false;
      }

      public boolean g() {
         return this.i;
      }

      public boolean h() {
         return this.i && this.l;
      }

      public fx a(gc var1) {
         return this.k[_snowman.ordinal()];
      }

      public boolean a(eao var1, ecu var2) {
         ecu.b _snowman = this.c();
         if (this.e != null) {
            this.e.a();
         }

         if (!_snowman.c.contains(_snowman)) {
            return false;
         } else {
            this.e = new ecu.c.c(this.b(), _snowman);
            _snowman.a(this.e);
            return true;
         }
      }

      protected void i() {
         if (this.d != null) {
            this.d.a();
            this.d = null;
         }

         if (this.e != null) {
            this.e.a();
            this.e = null;
         }
      }

      public ecu.c.a j() {
         this.i();
         fx _snowman = this.j.h();
         int _snowmanx = 1;
         ecv _snowmanxx = ecv.a(ecu.this.j, _snowman.b(-1, -1, -1), _snowman.b(16, 16, 16), 1);
         this.d = new ecu.c.b(this.b(), _snowmanxx);
         return this.d;
      }

      public void a(ecu var1) {
         ecu.c.a _snowman = this.j();
         _snowman.a(_snowman);
      }

      private void a(Set<ccj> var1) {
         Set<ccj> _snowman = Sets.newHashSet(_snowman);
         Set<ccj> _snowmanx = Sets.newHashSet(this.f);
         _snowman.removeAll(this.f);
         _snowmanx.removeAll(_snowman);
         this.f.clear();
         this.f.addAll(_snowman);
         ecu.this.k.a(_snowmanx, _snowman);
      }

      public void k() {
         ecu.c.a _snowman = this.j();
         _snowman.a(ecu.this.g);
      }

      abstract class a implements Comparable<ecu.c.a> {
         protected final double a;
         protected final AtomicBoolean b = new AtomicBoolean(false);

         public a(double var2) {
            this.a = _snowman;
         }

         public abstract CompletableFuture<ecu.a> a(dzt var1);

         public abstract void a();

         public int a(ecu.c.a var1) {
            return Doubles.compare(this.a, _snowman.a);
         }
      }

      class b extends ecu.c.a {
         @Nullable
         protected ecv d;

         public b(double var2, ecv var4) {
            super(_snowman);
            this.d = _snowman;
         }

         @Override
         public CompletableFuture<ecu.a> a(dzt var1) {
            if (this.b.get()) {
               return CompletableFuture.completedFuture(ecu.a.b);
            } else if (!c.this.a()) {
               this.d = null;
               c.this.a(false);
               this.b.set(true);
               return CompletableFuture.completedFuture(ecu.a.b);
            } else if (this.b.get()) {
               return CompletableFuture.completedFuture(ecu.a.b);
            } else {
               dcn _snowman = ecu.this.c();
               float _snowmanx = (float)_snowman.b;
               float _snowmanxx = (float)_snowman.c;
               float _snowmanxxx = (float)_snowman.d;
               ecu.b _snowmanxxxx = new ecu.b();
               Set<ccj> _snowmanxxxxx = this.a(_snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowman);
               c.this.a(_snowmanxxxxx);
               if (this.b.get()) {
                  return CompletableFuture.completedFuture(ecu.a.b);
               } else {
                  List<CompletableFuture<Void>> _snowmanxxxxxx = Lists.newArrayList();
                  _snowmanxxxx.c.forEach(var3x -> _snowman.add(ecu.this.a(_snowman.a(var3x), c.this.a(var3x))));
                  return x.b(_snowmanxxxxxx).handle((var2x, var3x) -> {
                     if (var3x != null && !(var3x instanceof CancellationException) && !(var3x instanceof InterruptedException)) {
                        djz.C().a(l.a(var3x, "Rendering chunk"));
                     }

                     if (this.b.get()) {
                        return ecu.a.b;
                     } else {
                        c.this.a.set(_snowman);
                        return ecu.a.a;
                     }
                  });
               }
            }
         }

         private Set<ccj> a(float var1, float var2, float var3, ecu.b var4, dzt var5) {
            int _snowman = 1;
            fx _snowmanx = c.this.j.h();
            fx _snowmanxx = _snowmanx.b(15, 15, 15);
            ecw _snowmanxxx = new ecw();
            Set<ccj> _snowmanxxxx = Sets.newHashSet();
            ecv _snowmanxxxxx = this.d;
            this.d = null;
            dfm _snowmanxxxxxx = new dfm();
            if (_snowmanxxxxx != null) {
               eaz.a();
               Random _snowmanxxxxxxx = new Random();
               eax _snowmanxxxxxxxx = djz.C().ab();

               for (fx _snowmanxxxxxxxxx : fx.a(_snowmanx, _snowmanxx)) {
                  ceh _snowmanxxxxxxxxxx = _snowmanxxxxx.d_(_snowmanxxxxxxxxx);
                  buo _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.b();
                  if (_snowmanxxxxxxxxxx.i(_snowmanxxxxx, _snowmanxxxxxxxxx)) {
                     _snowmanxxx.a(_snowmanxxxxxxxxx);
                  }

                  if (_snowmanxxxxxxxxxxx.q()) {
                     ccj _snowmanxxxxxxxxxxxx = _snowmanxxxxx.a(_snowmanxxxxxxxxx, cgh.a.c);
                     if (_snowmanxxxxxxxxxxxx != null) {
                        this.a(_snowman, _snowmanxxxx, _snowmanxxxxxxxxxxxx);
                     }
                  }

                  cux _snowmanxxxxxxxxxxxx = _snowmanxxxxx.b(_snowmanxxxxxxxxx);
                  if (!_snowmanxxxxxxxxxxxx.c()) {
                     eao _snowmanxxxxxxxxxxxxx = eab.a(_snowmanxxxxxxxxxxxx);
                     dfh _snowmanxxxxxxxxxxxxxx = _snowman.a(_snowmanxxxxxxxxxxxxx);
                     if (_snowman.c.add(_snowmanxxxxxxxxxxxxx)) {
                        c.this.a(_snowmanxxxxxxxxxxxxxx);
                     }

                     if (_snowmanxxxxxxxx.a(_snowmanxxxxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx)) {
                        _snowman.d = false;
                        _snowman.b.add(_snowmanxxxxxxxxxxxxx);
                     }
                  }

                  if (_snowmanxxxxxxxxxx.h() != bzh.a) {
                     eao _snowmanxxxxxxxxxxxxxxx = eab.a(_snowmanxxxxxxxxxx);
                     dfh _snowmanxxxxxxxxxxxxxxxx = _snowman.a(_snowmanxxxxxxxxxxxxxxx);
                     if (_snowman.c.add(_snowmanxxxxxxxxxxxxxxx)) {
                        c.this.a(_snowmanxxxxxxxxxxxxxxxx);
                     }

                     _snowmanxxxxxx.a();
                     _snowmanxxxxxx.a((double)(_snowmanxxxxxxxxx.u() & 15), (double)(_snowmanxxxxxxxxx.v() & 15), (double)(_snowmanxxxxxxxxx.w() & 15));
                     if (_snowmanxxxxxxxx.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxxxxxxxxx, true, _snowmanxxxxxxx)) {
                        _snowman.d = false;
                        _snowman.b.add(_snowmanxxxxxxxxxxxxxxx);
                     }

                     _snowmanxxxxxx.b();
                  }
               }

               if (_snowman.b.contains(eao.f())) {
                  dfh _snowmanxxxxxxxxx = _snowman.a(eao.f());
                  _snowmanxxxxxxxxx.a(_snowman - (float)_snowmanx.u(), _snowman - (float)_snowmanx.v(), _snowman - (float)_snowmanx.w());
                  _snowman.g = _snowmanxxxxxxxxx.b();
               }

               _snowman.c.stream().map(_snowman::a).forEach(dfh::c);
               eaz.b();
            }

            _snowman.f = _snowmanxxx.a();
            return _snowmanxxxx;
         }

         private <E extends ccj> void a(ecu.b var1, Set<ccj> var2, E var3) {
            ece<E> _snowman = ecd.a.a(_snowman);
            if (_snowman != null) {
               _snowman.e.add(_snowman);
               if (_snowman.a(_snowman)) {
                  _snowman.add(_snowman);
               }
            }
         }

         @Override
         public void a() {
            this.d = null;
            if (this.b.compareAndSet(false, true)) {
               c.this.a(false);
            }
         }
      }

      class c extends ecu.c.a {
         private final ecu.b e;

         public c(double var2, ecu.b var4) {
            super(_snowman);
            this.e = _snowman;
         }

         @Override
         public CompletableFuture<ecu.a> a(dzt var1) {
            if (this.b.get()) {
               return CompletableFuture.completedFuture(ecu.a.b);
            } else if (!c.this.a()) {
               this.b.set(true);
               return CompletableFuture.completedFuture(ecu.a.b);
            } else if (this.b.get()) {
               return CompletableFuture.completedFuture(ecu.a.b);
            } else {
               dcn _snowman = ecu.this.c();
               float _snowmanx = (float)_snowman.b;
               float _snowmanxx = (float)_snowman.c;
               float _snowmanxxx = (float)_snowman.d;
               dfh.b _snowmanxxxx = this.e.g;
               if (_snowmanxxxx != null && this.e.b.contains(eao.f())) {
                  dfh _snowmanxxxxx = _snowman.a(eao.f());
                  c.this.a(_snowmanxxxxx);
                  _snowmanxxxxx.a(_snowmanxxxx);
                  _snowmanxxxxx.a(_snowmanx - (float)c.this.j.u(), _snowmanxx - (float)c.this.j.v(), _snowmanxxx - (float)c.this.j.w());
                  this.e.g = _snowmanxxxxx.b();
                  _snowmanxxxxx.c();
                  if (this.b.get()) {
                     return CompletableFuture.completedFuture(ecu.a.b);
                  } else {
                     CompletableFuture<ecu.a> _snowmanxxxxxx = ecu.this.a(_snowman.a(eao.f()), c.this.a(eao.f())).thenApply(var0 -> ecu.a.b);
                     return _snowmanxxxxxx.handle((var1x, var2x) -> {
                        if (var2x != null && !(var2x instanceof CancellationException) && !(var2x instanceof InterruptedException)) {
                           djz.C().a(l.a(var2x, "Rendering chunk"));
                        }

                        return this.b.get() ? ecu.a.b : ecu.a.a;
                     });
                  }
               } else {
                  return CompletableFuture.completedFuture(ecu.a.b);
               }
            }
         }

         @Override
         public void a() {
            this.b.set(true);
         }
      }
   }
}
