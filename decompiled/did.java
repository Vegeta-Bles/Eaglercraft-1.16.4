import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class did extends eoo {
   private static final Logger a = LogManager.getLogger();
   private static final vk b = new vk("realms", "textures/gui/realms/op_icon.png");
   private static final vk c = new vk("realms", "textures/gui/realms/user_icon.png");
   private static final vk p = new vk("realms", "textures/gui/realms/cross_player_icon.png");
   private static final vk q = new vk("minecraft", "textures/gui/options_background.png");
   private static final nr r = new of("mco.configure.world.invites.normal.tooltip");
   private static final nr s = new of("mco.configure.world.invites.ops.tooltip");
   private static final nr t = new of("mco.configure.world.invites.remove.tooltip");
   private static final nr u = new of("mco.configure.world.invited");
   private nr v;
   private final dhs w;
   private final dgq x;
   private did.b y;
   private int z;
   private int A;
   private int B;
   private dlj C;
   private dlj D;
   private int E = -1;
   private String F;
   private int G = -1;
   private boolean H;
   private eom I;
   private did.c J = did.c.c;

   public did(dhs var1, dgq var2) {
      this.w = _snowman;
      this.x = _snowman;
   }

   @Override
   public void b() {
      this.z = this.k / 2 - 160;
      this.A = 150;
      this.B = this.k / 2 + 12;
      this.i.m.a(true);
      this.y = new did.b();
      this.y.g(this.z);
      this.d(this.y);

      for (dgn _snowman : this.x.h) {
         this.y.a(_snowman);
      }

      this.a((dlj)(new dlj(this.B, j(1), this.A + 10, 20, new of("mco.configure.world.buttons.invite"), var1 -> this.i.a(new dhx(this.w, this, this.x)))));
      this.C = this.a((dlj)(new dlj(this.B, j(7), this.A + 10, 20, new of("mco.configure.world.invites.remove.tooltip"), var1 -> this.n(this.G))));
      this.D = this.a((dlj)(new dlj(this.B, j(9), this.A + 10, 20, new of("mco.configure.world.invites.ops.tooltip"), var1 -> {
         if (this.x.h.get(this.G).c()) {
            this.m(this.G);
         } else {
            this.l(this.G);
         }
      })));
      this.a((dlj)(new dlj(this.B + this.A / 2 + 2, j(12), this.A / 2 + 10 - 2, 20, nq.h, var1 -> this.i())));
      this.I = this.d(new eom(new of("mco.configure.world.players.title"), this.k / 2, 17, 16777215));
      this.A();
      this.h();
   }

   private void h() {
      this.C.p = this.k(this.G);
      this.D.p = this.k(this.G);
   }

   private boolean k(int var1) {
      return _snowman != -1;
   }

   @Override
   public void e() {
      this.i.m.a(false);
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 256) {
         this.i();
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   private void i() {
      if (this.H) {
         this.i.a(this.w.c());
      } else {
         this.i.a(this.w);
      }
   }

   private void l(int var1) {
      this.h();
      dgb _snowman = dgb.a();
      String _snowmanx = this.x.h.get(_snowman).b();

      try {
         this.a(_snowman.e(this.x.a, _snowmanx));
      } catch (dhi var5) {
         a.error("Couldn't op the user");
      }
   }

   private void m(int var1) {
      this.h();
      dgb _snowman = dgb.a();
      String _snowmanx = this.x.h.get(_snowman).b();

      try {
         this.a(_snowman.f(this.x.a, _snowmanx));
      } catch (dhi var5) {
         a.error("Couldn't deop the user");
      }
   }

   private void a(dgj var1) {
      for (dgn _snowman : this.x.h) {
         _snowman.a(_snowman.a.contains(_snowman.a()));
      }
   }

   private void n(int var1) {
      this.h();
      if (_snowman >= 0 && _snowman < this.x.h.size()) {
         dgn _snowman = this.x.h.get(_snowman);
         this.F = _snowman.b();
         this.E = _snowman;
         dht _snowmanx = new dht(var1x -> {
            if (var1x) {
               dgb _snowmanxx = dgb.a();

               try {
                  _snowmanxx.a(this.x.a, this.F);
               } catch (dhi var4) {
                  a.error("Couldn't uninvite user");
               }

               this.o(this.E);
               this.G = -1;
               this.h();
            }

            this.H = true;
            this.i.a(this);
         }, new oe("Question"), new of("mco.configure.world.uninvite.question").c(" '").c(_snowman.a()).c("' ?"));
         this.i.a(_snowmanx);
      }
   }

   private void o(int var1) {
      this.x.h.remove(_snowman);
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.v = null;
      this.J = did.c.c;
      this.a(_snowman);
      if (this.y != null) {
         this.y.a(_snowman, _snowman, _snowman, _snowman);
      }

      int _snowman = j(12) + 20;
      dfo _snowmanx = dfo.a();
      dfh _snowmanxx = _snowmanx.c();
      this.i.M().a(q);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float _snowmanxxx = 32.0F;
      _snowmanxx.a(7, dfk.p);
      _snowmanxx.a(0.0, (double)this.l, 0.0).a(0.0F, (float)(this.l - _snowman) / 32.0F + 0.0F).a(64, 64, 64, 255).d();
      _snowmanxx.a((double)this.k, (double)this.l, 0.0).a((float)this.k / 32.0F, (float)(this.l - _snowman) / 32.0F + 0.0F).a(64, 64, 64, 255).d();
      _snowmanxx.a((double)this.k, (double)_snowman, 0.0).a((float)this.k / 32.0F, 0.0F).a(64, 64, 64, 255).d();
      _snowmanxx.a(0.0, (double)_snowman, 0.0).a(0.0F, 0.0F).a(64, 64, 64, 255).d();
      _snowmanx.b();
      this.I.a(this, _snowman);
      if (this.x != null && this.x.h != null) {
         this.o.b(_snowman, new oe("").a(u).c(" (").c(Integer.toString(this.x.h.size())).c(")"), (float)this.z, (float)j(0), 10526880);
      } else {
         this.o.b(_snowman, u, (float)this.z, (float)j(0), 10526880);
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
      if (this.x != null) {
         this.a(_snowman, this.v, _snowman, _snowman);
      }
   }

   protected void a(dfm var1, @Nullable nr var2, int var3, int var4) {
      if (_snowman != null) {
         int _snowman = _snowman + 12;
         int _snowmanx = _snowman - 12;
         int _snowmanxx = this.o.a(_snowman);
         this.a(_snowman, _snowman - 3, _snowmanx - 3, _snowman + _snowmanxx + 3, _snowmanx + 8 + 3, -1073741824, -1073741824);
         this.o.a(_snowman, _snowman, (float)_snowman, (float)_snowmanx, 16777215);
      }
   }

   private void c(dfm var1, int var2, int var3, int var4, int var5) {
      boolean _snowman = _snowman >= _snowman && _snowman <= _snowman + 9 && _snowman >= _snowman && _snowman <= _snowman + 9 && _snowman < j(12) + 20 && _snowman > j(1);
      this.i.M().a(p);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float _snowmanx = _snowman ? 7.0F : 0.0F;
      dkw.a(_snowman, _snowman, _snowman, 0.0F, _snowmanx, 8, 7, 8, 14);
      if (_snowman) {
         this.v = t;
         this.J = did.c.b;
      }
   }

   private void d(dfm var1, int var2, int var3, int var4, int var5) {
      boolean _snowman = _snowman >= _snowman && _snowman <= _snowman + 9 && _snowman >= _snowman && _snowman <= _snowman + 9 && _snowman < j(12) + 20 && _snowman > j(1);
      this.i.M().a(b);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float _snowmanx = _snowman ? 8.0F : 0.0F;
      dkw.a(_snowman, _snowman, _snowman, 0.0F, _snowmanx, 8, 8, 8, 16);
      if (_snowman) {
         this.v = s;
         this.J = did.c.a;
      }
   }

   private void e(dfm var1, int var2, int var3, int var4, int var5) {
      boolean _snowman = _snowman >= _snowman && _snowman <= _snowman + 9 && _snowman >= _snowman && _snowman <= _snowman + 9 && _snowman < j(12) + 20 && _snowman > j(1);
      this.i.M().a(c);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float _snowmanx = _snowman ? 8.0F : 0.0F;
      dkw.a(_snowman, _snowman, _snowman, 0.0F, _snowmanx, 8, 8, 8, 16);
      if (_snowman) {
         this.v = r;
         this.J = did.c.a;
      }
   }

   class a extends dlv.a<did.a> {
      private final dgn b;

      public a(dgn var2) {
         this.b = _snowman;
      }

      @Override
      public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         this.a(_snowman, this.b, _snowman, _snowman, _snowman, _snowman);
      }

      private void a(dfm var1, dgn var2, int var3, int var4, int var5, int var6) {
         int _snowman;
         if (!_snowman.d()) {
            _snowman = 10526880;
         } else if (_snowman.e()) {
            _snowman = 8388479;
         } else {
            _snowman = 16777215;
         }

         did.this.o.b(_snowman, _snowman.a(), (float)(did.this.z + 3 + 12), (float)(_snowman + 1), _snowman);
         if (_snowman.c()) {
            did.this.d(_snowman, did.this.z + did.this.A - 10, _snowman + 1, _snowman, _snowman);
         } else {
            did.this.e(_snowman, did.this.z + did.this.A - 10, _snowman + 1, _snowman, _snowman);
         }

         did.this.c(_snowman, did.this.z + did.this.A - 22, _snowman + 2, _snowman, _snowman);
         dir.a(_snowman.b(), () -> {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            dkw.a(_snowman, did.this.z + 2 + 2, _snowman + 1, 8, 8, 8.0F, 8.0F, 8, 8, 64, 64);
            dkw.a(_snowman, did.this.z + 2 + 2, _snowman + 1, 8, 8, 40.0F, 8.0F, 8, 8, 64, 64);
         });
      }
   }

   class b extends eon<did.a> {
      public b() {
         super(did.this.A + 10, did.j(12) + 20, did.j(1), did.j(12) + 20, 13);
      }

      public void a(dgn var1) {
         this.a((did.a)(did.this.new a(_snowman)));
      }

      @Override
      public int d() {
         return (int)((double)this.d * 1.0);
      }

      @Override
      public boolean b() {
         return did.this.aw_() == this;
      }

      @Override
      public boolean a(double var1, double var3, int var5) {
         if (_snowman == 0 && _snowman < (double)this.e() && _snowman >= (double)this.i && _snowman <= (double)this.j) {
            int _snowman = did.this.z;
            int _snowmanx = did.this.z + did.this.A;
            int _snowmanxx = (int)Math.floor(_snowman - (double)this.i) - this.n + (int)this.m() - 4;
            int _snowmanxxx = _snowmanxx / this.c;
            if (_snowman >= (double)_snowman && _snowman <= (double)_snowmanx && _snowmanxxx >= 0 && _snowmanxx >= 0 && _snowmanxxx < this.l()) {
               this.a(_snowmanxxx);
               this.a(_snowmanxx, _snowmanxxx, _snowman, _snowman, this.d);
            }

            return true;
         } else {
            return super.a(_snowman, _snowman, _snowman);
         }
      }

      @Override
      public void a(int var1, int var2, double var3, double var5, int var7) {
         if (_snowman >= 0 && _snowman <= did.this.x.h.size() && did.this.J != did.c.c) {
            if (did.this.J == did.c.a) {
               if (did.this.x.h.get(_snowman).c()) {
                  did.this.m(_snowman);
               } else {
                  did.this.l(_snowman);
               }
            } else if (did.this.J == did.c.b) {
               did.this.n(_snowman);
            }
         }
      }

      @Override
      public void a(int var1) {
         this.j(_snowman);
         if (_snowman != -1) {
            eoj.a(ekx.a("narrator.select", did.this.x.h.get(_snowman).a()));
         }

         this.b(_snowman);
      }

      public void b(int var1) {
         did.this.G = _snowman;
         did.this.h();
      }

      public void a(@Nullable did.a var1) {
         super.a(_snowman);
         did.this.G = this.au_().indexOf(_snowman);
         did.this.h();
      }

      @Override
      public void a(dfm var1) {
         did.this.a(_snowman);
      }

      @Override
      public int e() {
         return did.this.z + this.d - 5;
      }

      @Override
      public int c() {
         return this.l() * 13;
      }
   }

   static enum c {
      a,
      b,
      c;

      private c() {
      }
   }
}
