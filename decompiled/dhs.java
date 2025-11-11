import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dhs extends dig {
   private static final Logger a = LogManager.getLogger();
   private static final vk b = new vk("realms", "textures/gui/realms/on_icon.png");
   private static final vk c = new vk("realms", "textures/gui/realms/off_icon.png");
   private static final vk p = new vk("realms", "textures/gui/realms/expired_icon.png");
   private static final vk q = new vk("realms", "textures/gui/realms/expires_soon_icon.png");
   private static final nr r = new of("mco.configure.worlds.title");
   private static final nr s = new of("mco.configure.world.title");
   private static final nr t = new of("mco.configure.current.minigame").c(": ");
   private static final nr u = new of("mco.selectServer.expired");
   private static final nr v = new of("mco.selectServer.expires.soon");
   private static final nr w = new of("mco.selectServer.expires.day");
   private static final nr x = new of("mco.selectServer.open");
   private static final nr y = new of("mco.selectServer.closed");
   @Nullable
   private nr z;
   private final dfw A;
   @Nullable
   private dgq B;
   private final long C;
   private int D;
   private int E;
   private dlj F;
   private dlj G;
   private dlj H;
   private dlj I;
   private dlj J;
   private dlj K;
   private dlj L;
   private boolean M;
   private int N;
   private int O;

   public dhs(dfw var1, long var2) {
      this.A = _snowman;
      this.C = _snowman;
   }

   @Override
   public void b() {
      if (this.B == null) {
         this.a(this.C);
      }

      this.D = this.k / 2 - 187;
      this.E = this.k / 2 + 190;
      this.i.m.a(true);
      this.F = this.a((dlj)(new dlj(this.b(0, 3), j(0), 100, 20, new of("mco.configure.world.buttons.players"), var1x -> this.i.a(new did(this, this.B)))));
      this.G = this.a((dlj)(new dlj(this.b(1, 3), j(0), 100, 20, new of("mco.configure.world.buttons.settings"), var1x -> this.i.a(new dij(this, this.B.d())))));
      this.H = this.a(
         (dlj)(new dlj(this.b(2, 3), j(0), 100, 20, new of("mco.configure.world.buttons.subscription"), var1x -> this.i.a(new dil(this, this.B.d(), this.A))))
      );

      for (int _snowman = 1; _snowman < 5; _snowman++) {
         this.a(_snowman);
      }

      this.L = this.a((dlj)(new dlj(this.b(0), j(13) - 5, 100, 20, new of("mco.configure.world.buttons.switchminigame"), var1x -> {
         dii _snowman = new dii(this, dgq.c.b);
         _snowman.a(new of("mco.template.title.minigame"));
         this.i.a(_snowman);
      })));
      this.I = this.a(
         (dlj)(new dlj(
            this.b(0),
            j(13) - 5,
            90,
            20,
            new of("mco.configure.world.buttons.options"),
            var1x -> this.i.a(new dik(this, this.B.i.get(this.B.n).d(), this.B.m, this.B.n))
         ))
      );
      this.J = this.a(
         (dlj)(new dlj(this.b(1), j(13) - 5, 90, 20, new of("mco.configure.world.backup"), var1x -> this.i.a(new dhp(this, this.B.d(), this.B.n))))
      );
      this.K = this.a(
         (dlj)(new dlj(
            this.b(2),
            j(13) - 5,
            90,
            20,
            new of("mco.configure.world.buttons.resetworld"),
            var1x -> this.i.a(new dif(this, this.B.d(), () -> this.i.a(this.c()), () -> this.i.a(this.c())))
         ))
      );
      this.a((dlj)(new dlj(this.E - 80 + 8, j(13) - 5, 70, 20, nq.h, var1x -> this.h())));
      this.J.o = true;
      if (this.B == null) {
         this.n();
         this.m();
         this.F.o = false;
         this.G.o = false;
         this.H.o = false;
      } else {
         this.i();
         if (this.l()) {
            this.m();
         } else {
            this.n();
         }
      }
   }

   private void a(int var1) {
      int _snowman = this.c(_snowman);
      int _snowmanx = j(5) + 5;
      dhm _snowmanxx = new dhm(_snowman, _snowmanx, 80, 80, () -> this.B, var1x -> this.z = var1x, _snowman, var2x -> {
         dhm.b _snowmanxxx = ((dhm)var2x).a();
         if (_snowmanxxx != null) {
            switch (_snowmanxxx.c) {
               case a:
                  break;
               case c:
                  this.a(this.B);
                  break;
               case b:
                  if (_snowmanxxx.b) {
                     this.k();
                  } else if (_snowmanxxx.a) {
                     this.b(_snowman, this.B);
                  } else {
                     this.a(_snowman, this.B);
                  }
                  break;
               default:
                  throw new IllegalStateException("Unknown action " + _snowmanxxx.c);
            }
         }
      });
      this.a(_snowmanxx);
   }

   private int b(int var1) {
      return this.D + _snowman * 95;
   }

   private int b(int var1, int var2) {
      return this.k / 2 - (_snowman * 105 - 5) / 2 + _snowman * 105;
   }

   @Override
   public void d() {
      super.d();
      this.N++;
      this.O--;
      if (this.O < 0) {
         this.O = 0;
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.z = null;
      this.a(_snowman);
      a(_snowman, this.o, r, this.k / 2, j(4), 16777215);
      super.a(_snowman, _snowman, _snowman, _snowman);
      if (this.B == null) {
         a(_snowman, this.o, s, this.k / 2, 17, 16777215);
      } else {
         String _snowman = this.B.b();
         int _snowmanx = this.o.b(_snowman);
         int _snowmanxx = this.B.e == dgq.b.a ? 10526880 : 8388479;
         int _snowmanxxx = this.o.a(s);
         a(_snowman, this.o, s, this.k / 2, 12, 16777215);
         a(_snowman, this.o, _snowman, this.k / 2, 24, _snowmanxx);
         int _snowmanxxxx = Math.min(this.b(2, 3) + 80 - 11, this.k / 2 + _snowmanx / 2 + _snowmanxxx / 2 + 10);
         this.c(_snowman, _snowmanxxxx, 7, _snowman, _snowman);
         if (this.l()) {
            this.o.b(_snowman, t.e().c(this.B.c()), (float)(this.D + 80 + 20 + 10), (float)j(13), 16777215);
         }

         if (this.z != null) {
            this.a(_snowman, this.z, _snowman, _snowman);
         }
      }
   }

   private int c(int var1) {
      return this.D + (_snowman - 1) * 98;
   }

   @Override
   public void e() {
      this.i.m.a(false);
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 256) {
         this.h();
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   private void h() {
      if (this.M) {
         this.A.ar_();
      }

      this.i.a(this.A);
   }

   private void a(long var1) {
      new Thread(() -> {
         dgb _snowman = dgb.a();

         try {
            this.B = _snowman.a(_snowman);
            this.i();
            if (this.l()) {
               this.b(this.L);
            } else {
               this.b(this.I);
               this.b(this.J);
               this.b(this.K);
            }
         } catch (dhi var5) {
            a.error("Couldn't get own world");
            this.i.execute(() -> this.i.a(new dhw(nr.a(var5.getMessage()), this.A)));
         }
      }).start();
   }

   private void i() {
      this.F.o = !this.B.j;
      this.G.o = !this.B.j;
      this.H.o = true;
      this.L.o = !this.B.j;
      this.I.o = !this.B.j;
      this.K.o = !this.B.j;
   }

   private void a(dgq var1) {
      if (this.B.e == dgq.b.b) {
         this.A.a(_snowman, new dhs(this.A.g(), this.C));
      } else {
         this.a(true, new dhs(this.A.g(), this.C));
      }
   }

   private void k() {
      dii _snowman = new dii(this, dgq.c.b);
      _snowman.a(new of("mco.template.title.minigame"));
      _snowman.a(new of("mco.minigame.world.info.line1"), new of("mco.minigame.world.info.line2"));
      this.i.a(_snowman);
   }

   private void a(int var1, dgq var2) {
      nr _snowman = new of("mco.configure.world.slot.switch.question.line1");
      nr _snowmanx = new of("mco.configure.world.slot.switch.question.line2");
      this.i.a(new dhy(var3x -> {
         if (var3x) {
            this.i.a(new dhz(this.A, new djf(_snowman.a, _snowman, () -> this.i.a(this.c()))));
         } else {
            this.i.a(this);
         }
      }, dhy.a.b, _snowman, _snowmanx, true));
   }

   private void b(int var1, dgq var2) {
      nr _snowman = new of("mco.configure.world.slot.switch.question.line1");
      nr _snowmanx = new of("mco.configure.world.slot.switch.question.line2");
      this.i
         .a(
            new dhy(
               var3x -> {
                  if (var3x) {
                     dif _snowmanxx = new dif(
                        this,
                        _snowman,
                        new of("mco.configure.world.switch.slot"),
                        new of("mco.configure.world.switch.slot.subtitle"),
                        10526880,
                        nq.d,
                        () -> this.i.a(this.c()),
                        () -> this.i.a(this.c())
                     );
                     _snowmanxx.a(_snowman);
                     _snowmanxx.a(new of("mco.create.world.reset.title"));
                     this.i.a(_snowmanxx);
                  } else {
                     this.i.a(this);
                  }
               },
               dhy.a.b,
               _snowman,
               _snowmanx,
               true
            )
         );
   }

   protected void a(dfm var1, @Nullable nr var2, int var3, int var4) {
      int _snowman = _snowman + 12;
      int _snowmanx = _snowman - 12;
      int _snowmanxx = this.o.a(_snowman);
      if (_snowman + _snowmanxx + 3 > this.E) {
         _snowman = _snowman - _snowmanxx - 20;
      }

      this.a(_snowman, _snowman - 3, _snowmanx - 3, _snowman + _snowmanxx + 3, _snowmanx + 8 + 3, -1073741824, -1073741824);
      this.o.a(_snowman, _snowman, (float)_snowman, (float)_snowmanx, 16777215);
   }

   private void c(dfm var1, int var2, int var3, int var4, int var5) {
      if (this.B.j) {
         this.d(_snowman, _snowman, _snowman, _snowman, _snowman);
      } else if (this.B.e == dgq.b.a) {
         this.f(_snowman, _snowman, _snowman, _snowman, _snowman);
      } else if (this.B.e == dgq.b.b) {
         if (this.B.l < 7) {
            this.b(_snowman, _snowman, _snowman, _snowman, _snowman, this.B.l);
         } else {
            this.e(_snowman, _snowman, _snowman, _snowman, _snowman);
         }
      }
   }

   private void d(dfm var1, int var2, int var3, int var4, int var5) {
      this.i.M().a(p);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      dkw.a(_snowman, _snowman, _snowman, 0.0F, 0.0F, 10, 28, 10, 28);
      if (_snowman >= _snowman && _snowman <= _snowman + 9 && _snowman >= _snowman && _snowman <= _snowman + 27) {
         this.z = u;
      }
   }

   private void b(dfm var1, int var2, int var3, int var4, int var5, int var6) {
      this.i.M().a(q);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.N % 20 < 10) {
         dkw.a(_snowman, _snowman, _snowman, 0.0F, 0.0F, 10, 28, 20, 28);
      } else {
         dkw.a(_snowman, _snowman, _snowman, 10.0F, 0.0F, 10, 28, 20, 28);
      }

      if (_snowman >= _snowman && _snowman <= _snowman + 9 && _snowman >= _snowman && _snowman <= _snowman + 27) {
         if (_snowman <= 0) {
            this.z = v;
         } else if (_snowman == 1) {
            this.z = w;
         } else {
            this.z = new of("mco.selectServer.expires.days", _snowman);
         }
      }
   }

   private void e(dfm var1, int var2, int var3, int var4, int var5) {
      this.i.M().a(b);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      dkw.a(_snowman, _snowman, _snowman, 0.0F, 0.0F, 10, 28, 10, 28);
      if (_snowman >= _snowman && _snowman <= _snowman + 9 && _snowman >= _snowman && _snowman <= _snowman + 27) {
         this.z = x;
      }
   }

   private void f(dfm var1, int var2, int var3, int var4, int var5) {
      this.i.M().a(c);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      dkw.a(_snowman, _snowman, _snowman, 0.0F, 0.0F, 10, 28, 10, 28);
      if (_snowman >= _snowman && _snowman <= _snowman + 9 && _snowman >= _snowman && _snowman <= _snowman + 27) {
         this.z = y;
      }
   }

   private boolean l() {
      return this.B != null && this.B.m == dgq.c.b;
   }

   private void m() {
      this.a(this.I);
      this.a(this.J);
      this.a(this.K);
   }

   private void a(dlj var1) {
      _snowman.p = false;
      this.e.remove(_snowman);
      this.m.remove(_snowman);
   }

   private void b(dlj var1) {
      _snowman.p = true;
      this.a((dlj)_snowman);
   }

   private void n() {
      this.a(this.L);
   }

   public void a(dgw var1) {
      dgw _snowman = this.B.i.get(this.B.n);
      _snowman.k = _snowman.k;
      _snowman.l = _snowman.l;
      dgb _snowmanx = dgb.a();

      try {
         _snowmanx.a(this.B.a, this.B.n, _snowman);
         this.B.i.put(this.B.n, _snowman);
      } catch (dhi var5) {
         a.error("Couldn't save slot settings");
         this.i.a(new dhw(var5, this));
         return;
      }

      this.i.a(this);
   }

   public void a(String var1, String var2) {
      String _snowman = _snowman.trim().isEmpty() ? null : _snowman;
      dgb _snowmanx = dgb.a();

      try {
         _snowmanx.b(this.B.a, _snowman, _snowman);
         this.B.a(_snowman);
         this.B.b(_snowman);
      } catch (dhi var6) {
         a.error("Couldn't save settings");
         this.i.a(new dhw(var6, this));
         return;
      }

      this.i.a(this);
   }

   public void a(boolean var1, dot var2) {
      this.i.a(new dhz(_snowman, new djb(this.B, this, this.A, _snowman)));
   }

   public void a(dot var1) {
      this.i.a(new dhz(_snowman, new diw(this.B, this)));
   }

   public void a() {
      this.M = true;
   }

   @Override
   protected void a(@Nullable dhe var1) {
      if (_snowman != null) {
         if (dhe.a.b == _snowman.i) {
            this.i.a(new dhz(this.A, new dje(this.B.a, _snowman, this.c())));
         }
      }
   }

   public dhs c() {
      return new dhs(this.A, this.C);
   }
}
