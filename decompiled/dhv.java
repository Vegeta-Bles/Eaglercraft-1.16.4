import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dhv extends eoo {
   private static final Logger a = LogManager.getLogger();
   private static final ReentrantLock b = new ReentrantLock();
   private final dot c;
   private final dhd p;
   private final nr q;
   private final RateLimiter r;
   private dlj s;
   private final String t;
   private final dhv.a u;
   private volatile nr v;
   private volatile nr w = new of("mco.download.preparing");
   private volatile String x;
   private volatile boolean y;
   private volatile boolean z = true;
   private volatile boolean A;
   private volatile boolean B;
   private Long C;
   private Long D;
   private long E;
   private int F;
   private static final String[] G = new String[]{"", ".", ". .", ". . ."};
   private int H;
   private boolean I;
   private final BooleanConsumer J;

   public dhv(dot var1, dhd var2, String var3, BooleanConsumer var4) {
      this.J = _snowman;
      this.c = _snowman;
      this.t = _snowman;
      this.p = _snowman;
      this.u = new dhv.a();
      this.q = new of("mco.download.title");
      this.r = RateLimiter.create(0.1F);
   }

   @Override
   public void b() {
      this.i.m.a(true);
      this.s = this.a(new dlj(this.k / 2 - 100, this.l - 42, 200, 20, nq.d, var1 -> {
         this.y = true;
         this.i();
      }));
      this.h();
   }

   private void h() {
      if (!this.A) {
         if (!this.I && this.b(this.p.a) >= 5368709120L) {
            nr _snowman = new of("mco.download.confirmation.line1", dfx.b(5368709120L));
            nr _snowmanx = new of("mco.download.confirmation.line2");
            this.i.a(new dhy(var1x -> {
               this.I = true;
               this.i.a(this);
               this.k();
            }, dhy.a.a, _snowman, _snowmanx, false));
         } else {
            this.k();
         }
      }
   }

   private long b(String var1) {
      dfy _snowman = new dfy();
      return _snowman.a(_snowman);
   }

   @Override
   public void d() {
      super.d();
      this.F++;
      if (this.w != null && this.r.tryAcquire(1)) {
         List<nr> _snowman = Lists.newArrayList();
         _snowman.add(this.q);
         _snowman.add(this.w);
         if (this.x != null) {
            _snowman.add(new oe(this.x + "%"));
            _snowman.add(new oe(dfx.b(this.E) + "/s"));
         }

         if (this.v != null) {
            _snowman.add(this.v);
         }

         String _snowmanx = _snowman.stream().map(nr::getString).collect(Collectors.joining("\n"));
         eoj.a(_snowmanx);
      }
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 256) {
         this.y = true;
         this.i();
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   private void i() {
      if (this.A && this.J != null && this.v == null) {
         this.J.accept(true);
      }

      this.i.a(this.c);
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      a(_snowman, this.o, this.q, this.k / 2, 20, 16777215);
      a(_snowman, this.o, this.w, this.k / 2, 50, 16777215);
      if (this.z) {
         this.b(_snowman);
      }

      if (this.u.a != 0L && !this.y) {
         this.c(_snowman);
         this.d(_snowman);
      }

      if (this.v != null) {
         a(_snowman, this.o, this.v, this.k / 2, 110, 16711680);
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   private void b(dfm var1) {
      int _snowman = this.o.a(this.w);
      if (this.F % 10 == 0) {
         this.H++;
      }

      this.o.b(_snowman, G[this.H % G.length], (float)(this.k / 2 + _snowman / 2 + 5), 50.0F, 16777215);
   }

   private void c(dfm var1) {
      double _snowman = Math.min((double)this.u.a / (double)this.u.b, 1.0);
      this.x = String.format(Locale.ROOT, "%.1f", _snowman * 100.0);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.disableTexture();
      dfo _snowmanx = dfo.a();
      dfh _snowmanxx = _snowmanx.c();
      _snowmanxx.a(7, dfk.l);
      double _snowmanxxx = (double)(this.k / 2 - 100);
      double _snowmanxxxx = 0.5;
      _snowmanxx.a(_snowmanxxx - 0.5, 95.5, 0.0).a(217, 210, 210, 255).d();
      _snowmanxx.a(_snowmanxxx + 200.0 * _snowman + 0.5, 95.5, 0.0).a(217, 210, 210, 255).d();
      _snowmanxx.a(_snowmanxxx + 200.0 * _snowman + 0.5, 79.5, 0.0).a(217, 210, 210, 255).d();
      _snowmanxx.a(_snowmanxxx - 0.5, 79.5, 0.0).a(217, 210, 210, 255).d();
      _snowmanxx.a(_snowmanxxx, 95.0, 0.0).a(128, 128, 128, 255).d();
      _snowmanxx.a(_snowmanxxx + 200.0 * _snowman, 95.0, 0.0).a(128, 128, 128, 255).d();
      _snowmanxx.a(_snowmanxxx + 200.0 * _snowman, 80.0, 0.0).a(128, 128, 128, 255).d();
      _snowmanxx.a(_snowmanxxx, 80.0, 0.0).a(128, 128, 128, 255).d();
      _snowmanx.b();
      RenderSystem.enableTexture();
      a(_snowman, this.o, this.x + " %", this.k / 2, 84, 16777215);
   }

   private void d(dfm var1) {
      if (this.F % 20 == 0) {
         if (this.C != null) {
            long _snowman = x.b() - this.D;
            if (_snowman == 0L) {
               _snowman = 1L;
            }

            this.E = 1000L * (this.u.a - this.C) / _snowman;
            this.a(_snowman, this.E);
         }

         this.C = this.u.a;
         this.D = x.b();
      } else {
         this.a(_snowman, this.E);
      }
   }

   private void a(dfm var1, long var2) {
      if (_snowman > 0L) {
         int _snowman = this.o.b(this.x);
         String _snowmanx = "(" + dfx.b(_snowman) + "/s)";
         this.o.b(_snowman, _snowmanx, (float)(this.k / 2 + _snowman / 2 + 15), 84.0F, 16777215);
      }
   }

   private void k() {
      new Thread(() -> {
         try {
            try {
               if (!b.tryLock(1L, TimeUnit.SECONDS)) {
                  this.w = new of("mco.download.failed");
                  return;
               }

               if (this.y) {
                  this.l();
                  return;
               }

               this.w = new of("mco.download.downloading", this.t);
               dfy _snowman = new dfy();
               _snowman.a(this.p.a);
               _snowman.a(this.p, this.t, this.u, this.i.k());

               while (!_snowman.b()) {
                  if (_snowman.c()) {
                     _snowman.a();
                     this.v = new of("mco.download.failed");
                     this.s.a(nq.c);
                     return;
                  }

                  if (_snowman.d()) {
                     if (!this.B) {
                        this.w = new of("mco.download.extracting");
                     }

                     this.B = true;
                  }

                  if (this.y) {
                     _snowman.a();
                     this.l();
                     return;
                  }

                  try {
                     Thread.sleep(500L);
                  } catch (InterruptedException var8) {
                     a.error("Failed to check Realms backup download status");
                  }
               }

               this.A = true;
               this.w = new of("mco.download.done");
               this.s.a(nq.c);
               return;
            } catch (InterruptedException var9) {
               a.error("Could not acquire upload lock");
            } catch (Exception var10) {
               this.v = new of("mco.download.failed");
               var10.printStackTrace();
            }
         } finally {
            if (!b.isHeldByCurrentThread()) {
               return;
            } else {
               b.unlock();
               this.z = false;
               this.A = true;
            }
         }
      }).start();
   }

   private void l() {
      this.w = new of("mco.download.cancelled");
   }

   public class a {
      public volatile long a;
      public volatile long b;

      public a() {
      }
   }
}
