import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dic extends eoo {
   private static final Logger a = LogManager.getLogger();
   private static final vk b = new vk("realms", "textures/gui/realms/accept_icon.png");
   private static final vk c = new vk("realms", "textures/gui/realms/reject_icon.png");
   private static final nr p = new of("mco.invites.nopending");
   private static final nr q = new of("mco.invites.button.accept");
   private static final nr r = new of("mco.invites.button.reject");
   private final dot s;
   @Nullable
   private nr t;
   private boolean u;
   private dic.b v;
   private eom w;
   private int x = -1;
   private dlj y;
   private dlj z;

   public dic(dot var1) {
      this.s = _snowman;
   }

   @Override
   public void b() {
      this.i.m.a(true);
      this.v = new dic.b();
      (new Thread("Realms-pending-invitations-fetcher") {
         @Override
         public void run() {
            dgb _snowman = dgb.a();

            try {
               List<dgk> _snowmanx = _snowman.k().a;
               List<dic.a> _snowmanxx = _snowmanx.stream().map(var1x -> dic.this.new a(var1x)).collect(Collectors.toList());
               dic.this.i.execute(() -> dic.this.v.a(_snowman));
            } catch (dhi var7) {
               dic.a.error("Couldn't list invites");
            } finally {
               dic.this.u = true;
            }
         }
      }).start();
      this.d(this.v);
      this.y = this.a((dlj)(new dlj(this.k / 2 - 174, this.l - 32, 100, 20, new of("mco.invites.button.accept"), var1 -> {
         this.c(this.x);
         this.x = -1;
         this.i();
      })));
      this.a((dlj)(new dlj(this.k / 2 - 50, this.l - 32, 100, 20, nq.c, var1 -> this.i.a(new dfw(this.s)))));
      this.z = this.a((dlj)(new dlj(this.k / 2 + 74, this.l - 32, 100, 20, new of("mco.invites.button.reject"), var1 -> {
         this.b(this.x);
         this.x = -1;
         this.i();
      })));
      this.w = new eom(new of("mco.invites.title"), this.k / 2, 12, 16777215);
      this.d(this.w);
      this.A();
      this.i();
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 256) {
         this.i.a(new dfw(this.s));
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   private void a(int var1) {
      this.v.b(_snowman);
   }

   private void b(final int var1) {
      if (_snowman < this.v.l()) {
         (new Thread("Realms-reject-invitation") {
            @Override
            public void run() {
               try {
                  dgb _snowman = dgb.a();
                  _snowman.b(dic.this.v.au_().get(_snowman).b.a);
                  dic.this.i.execute(() -> dic.this.a(_snowman));
               } catch (dhi var2) {
                  dic.a.error("Couldn't reject invite");
               }
            }
         }).start();
      }
   }

   private void c(final int var1) {
      if (_snowman < this.v.l()) {
         (new Thread("Realms-accept-invitation") {
            @Override
            public void run() {
               try {
                  dgb _snowman = dgb.a();
                  _snowman.a(dic.this.v.au_().get(_snowman).b.a);
                  dic.this.i.execute(() -> dic.this.a(_snowman));
               } catch (dhi var2) {
                  dic.a.error("Couldn't accept invite");
               }
            }
         }).start();
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.t = null;
      this.a(_snowman);
      this.v.a(_snowman, _snowman, _snowman, _snowman);
      this.w.a(this, _snowman);
      if (this.t != null) {
         this.a(_snowman, this.t, _snowman, _snowman);
      }

      if (this.v.l() == 0 && this.u) {
         a(_snowman, this.o, p, this.k / 2, this.l / 2 - 20, 16777215);
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
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

   private void i() {
      this.y.p = this.k(this.x);
      this.z.p = this.k(this.x);
   }

   private boolean k(int var1) {
      return _snowman != -1;
   }

   class a extends dlv.a<dic.a> {
      private final dgk b;
      private final List<dhn> c;

      a(dgk var2) {
         this.b = _snowman;
         this.c = Arrays.asList(new dic.a.a(), new dic.a.b());
      }

      @Override
      public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         this.a(_snowman, this.b, _snowman, _snowman, _snowman, _snowman);
      }

      @Override
      public boolean a(double var1, double var3, int var5) {
         dhn.a(dic.this.v, this, this.c, _snowman, _snowman, _snowman);
         return true;
      }

      private void a(dfm var1, dgk var2, int var3, int var4, int var5, int var6) {
         dic.this.o.b(_snowman, _snowman.b, (float)(_snowman + 38), (float)(_snowman + 1), 16777215);
         dic.this.o.b(_snowman, _snowman.c, (float)(_snowman + 38), (float)(_snowman + 12), 7105644);
         dic.this.o.b(_snowman, dis.a(_snowman.e), (float)(_snowman + 38), (float)(_snowman + 24), 7105644);
         dhn.a(_snowman, this.c, dic.this.v, _snowman, _snowman, _snowman, _snowman);
         dir.a(_snowman.d, () -> {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            dkw.a(_snowman, _snowman, _snowman, 32, 32, 8.0F, 8.0F, 8, 8, 64, 64);
            dkw.a(_snowman, _snowman, _snowman, 32, 32, 40.0F, 8.0F, 8, 8, 64, 64);
         });
      }

      class a extends dhn {
         a() {
            super(15, 15, 215, 5);
         }

         @Override
         protected void a(dfm var1, int var2, int var3, boolean var4) {
            dic.this.i.M().a(dic.b);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            float _snowman = _snowman ? 19.0F : 0.0F;
            dkw.a(_snowman, _snowman, _snowman, _snowman, 0.0F, 18, 18, 37, 18);
            if (_snowman) {
               dic.this.t = dic.q;
            }
         }

         @Override
         public void a(int var1) {
            dic.this.c(_snowman);
         }
      }

      class b extends dhn {
         b() {
            super(15, 15, 235, 5);
         }

         @Override
         protected void a(dfm var1, int var2, int var3, boolean var4) {
            dic.this.i.M().a(dic.c);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            float _snowman = _snowman ? 19.0F : 0.0F;
            dkw.a(_snowman, _snowman, _snowman, _snowman, 0.0F, 18, 18, 37, 18);
            if (_snowman) {
               dic.this.t = dic.r;
            }
         }

         @Override
         public void a(int var1) {
            dic.this.b(_snowman);
         }
      }
   }

   class b extends eon<dic.a> {
      public b() {
         super(dic.this.k, dic.this.l, 32, dic.this.l - 40, 36);
      }

      public void b(int var1) {
         this.i(_snowman);
      }

      @Override
      public int c() {
         return this.l() * 36;
      }

      @Override
      public int d() {
         return 260;
      }

      @Override
      public boolean b() {
         return dic.this.aw_() == this;
      }

      @Override
      public void a(dfm var1) {
         dic.this.a(_snowman);
      }

      @Override
      public void a(int var1) {
         this.j(_snowman);
         if (_snowman != -1) {
            List<dic.a> _snowman = dic.this.v.au_();
            dgk _snowmanx = _snowman.get(_snowman).b;
            String _snowmanxx = ekx.a("narrator.select.list.position", _snowman + 1, _snowman.size());
            String _snowmanxxx = eoj.b(Arrays.asList(_snowmanx.b, _snowmanx.c, dis.a(_snowmanx.e), _snowmanxx));
            eoj.a(ekx.a("narrator.select", _snowmanxxx));
         }

         this.c(_snowman);
      }

      public void c(int var1) {
         dic.this.x = _snowman;
         dic.this.i();
      }

      public void a(@Nullable dic.a var1) {
         super.a(_snowman);
         dic.this.x = this.au_().indexOf(_snowman);
         dic.this.i();
      }
   }
}
