import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dif extends dig {
   private static final Logger b = LogManager.getLogger();
   private final dot c;
   private final dgq p;
   private eom q;
   private eom r;
   private nr s = new of("mco.reset.world.title");
   private nr t = new of("mco.reset.world.warning");
   private nr u = nq.d;
   private int v = 16711680;
   private static final vk w = new vk("realms", "textures/gui/realms/slot_frame.png");
   private static final vk x = new vk("realms", "textures/gui/realms/upload.png");
   private static final vk y = new vk("realms", "textures/gui/realms/adventure.png");
   private static final vk z = new vk("realms", "textures/gui/realms/survival_spawn.png");
   private static final vk A = new vk("realms", "textures/gui/realms/new_world.png");
   private static final vk B = new vk("realms", "textures/gui/realms/experience.png");
   private static final vk C = new vk("realms", "textures/gui/realms/inspiration.png");
   private dhf D;
   private dhf E;
   private dhf F;
   private dhf G;
   public int a = -1;
   private dif.b H = dif.b.a;
   private dif.c I;
   private dhe J;
   @Nullable
   private nr K;
   private final Runnable L;
   private final Runnable M;

   public dif(dot var1, dgq var2, Runnable var3, Runnable var4) {
      this.c = _snowman;
      this.p = _snowman;
      this.L = _snowman;
      this.M = _snowman;
   }

   public dif(dot var1, dgq var2, nr var3, nr var4, int var5, nr var6, Runnable var7, Runnable var8) {
      this(_snowman, _snowman, _snowman, _snowman);
      this.s = _snowman;
      this.t = _snowman;
      this.v = _snowman;
      this.u = _snowman;
   }

   public void a(int var1) {
      this.a = _snowman;
   }

   public void a(nr var1) {
      this.K = _snowman;
   }

   @Override
   public void b() {
      this.a((dlj)(new dlj(this.k / 2 - 40, j(14) - 10, 80, 20, this.u, var1 -> this.i.a(this.c))));
      (new Thread("Realms-reset-world-fetcher") {
         @Override
         public void run() {
            dgb _snowman = dgb.a();

            try {
               dhf _snowmanx = _snowman.a(1, 10, dgq.c.a);
               dhf _snowmanxx = _snowman.a(1, 10, dgq.c.c);
               dhf _snowmanxxx = _snowman.a(1, 10, dgq.c.d);
               dhf _snowmanxxxx = _snowman.a(1, 10, dgq.c.e);
               dif.this.i.execute(() -> {
                  dif.this.D = _snowman;
                  dif.this.E = _snowman;
                  dif.this.F = _snowman;
                  dif.this.G = _snowman;
               });
            } catch (dhi var6) {
               dif.b.error("Couldn't fetch templates in reset world", var6);
            }
         }
      }).start();
      this.q = this.d(new eom(this.s, this.k / 2, 7, 16777215));
      this.r = this.d(new eom(this.t, this.k / 2, 22, this.v));
      this.a(new dif.a(this.b(1), j(0) + 10, new of("mco.reset.world.generate"), A, var1 -> this.i.a(new die(this, this.s))));
      this.a(new dif.a(this.b(2), j(0) + 10, new of("mco.reset.world.upload"), x, var1 -> {
         dot _snowman = new dih(this.p.a, this.a != -1 ? this.a : this.p.n, this, this.M);
         this.i.a(_snowman);
      }));
      this.a(new dif.a(this.b(3), j(0) + 10, new of("mco.reset.world.template"), z, var1 -> {
         dii _snowman = new dii(this, dgq.c.a, this.D);
         _snowman.a(new of("mco.reset.world.template"));
         this.i.a(_snowman);
      }));
      this.a(new dif.a(this.b(1), j(6) + 20, new of("mco.reset.world.adventure"), y, var1 -> {
         dii _snowman = new dii(this, dgq.c.c, this.E);
         _snowman.a(new of("mco.reset.world.adventure"));
         this.i.a(_snowman);
      }));
      this.a(new dif.a(this.b(2), j(6) + 20, new of("mco.reset.world.experience"), B, var1 -> {
         dii _snowman = new dii(this, dgq.c.d, this.F);
         _snowman.a(new of("mco.reset.world.experience"));
         this.i.a(_snowman);
      }));
      this.a(new dif.a(this.b(3), j(6) + 20, new of("mco.reset.world.inspiration"), C, var1 -> {
         dii _snowman = new dii(this, dgq.c.e, this.G);
         _snowman.a(new of("mco.reset.world.inspiration"));
         this.i.a(_snowman);
      }));
      this.A();
   }

   @Override
   public void e() {
      this.i.m.a(false);
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 256) {
         this.i.a(this.c);
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   private int b(int var1) {
      return this.k / 2 - 130 + (_snowman - 1) * 100;
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      this.q.a(this, _snowman);
      this.r.a(this, _snowman);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   private void a(dfm var1, int var2, int var3, nr var4, vk var5, boolean var6, boolean var7) {
      this.i.M().a(_snowman);
      if (_snowman) {
         RenderSystem.color4f(0.56F, 0.56F, 0.56F, 1.0F);
      } else {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

      dkw.a(_snowman, _snowman + 2, _snowman + 14, 0.0F, 0.0F, 56, 56, 56, 56);
      this.i.M().a(w);
      if (_snowman) {
         RenderSystem.color4f(0.56F, 0.56F, 0.56F, 1.0F);
      } else {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

      dkw.a(_snowman, _snowman, _snowman + 12, 0.0F, 0.0F, 60, 60, 60, 60);
      int _snowman = _snowman ? 10526880 : 16777215;
      a(_snowman, this.o, _snowman, _snowman + 30, _snowman, _snowman);
   }

   @Override
   protected void a(@Nullable dhe var1) {
      if (_snowman != null) {
         if (this.a == -1) {
            this.b(_snowman);
         } else {
            switch (_snowman.i) {
               case a:
                  this.H = dif.b.e;
                  break;
               case c:
                  this.H = dif.b.d;
                  break;
               case d:
                  this.H = dif.b.f;
                  break;
               case e:
                  this.H = dif.b.g;
            }

            this.J = _snowman;
            this.h();
         }
      }
   }

   private void h() {
      this.a(() -> {
         switch (this.H) {
            case d:
            case e:
            case f:
            case g:
               if (this.J != null) {
                  this.b(this.J);
               }
               break;
            case b:
               if (this.I != null) {
                  this.b(this.I);
               }
         }
      });
   }

   public void a(Runnable var1) {
      this.i.a(new dhz(this.c, new djf(this.p.a, this.a, _snowman)));
   }

   public void b(dhe var1) {
      this.a(null, _snowman, -1, true);
   }

   private void b(dif.c var1) {
      this.a(_snowman.a, null, _snowman.b, _snowman.c);
   }

   private void a(@Nullable String var1, @Nullable dhe var2, int var3, boolean var4) {
      this.i.a(new dhz(this.c, new djc(_snowman, _snowman, _snowman, _snowman, this.p.a, this.K, this.L)));
   }

   public void a(dif.c var1) {
      if (this.a == -1) {
         this.b(_snowman);
      } else {
         this.H = dif.b.b;
         this.I = _snowman;
         this.h();
      }
   }

   class a extends dlj {
      private final vk b;

      public a(int var2, int var3, nr var4, vk var5, dlj.a var6) {
         super(_snowman, _snowman, 60, 72, _snowman, _snowman);
         this.b = _snowman;
      }

      @Override
      public void b(dfm var1, int var2, int var3, float var4) {
         dif.this.a(_snowman, this.l, this.m, this.i(), this.b, this.g(), this.b((double)_snowman, (double)_snowman));
      }
   }

   static enum b {
      a,
      b,
      c,
      d,
      e,
      f,
      g;

      private b() {
      }
   }

   public static class c {
      private final String a;
      private final int b;
      private final boolean c;

      public c(String var1, int var2, boolean var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }
   }
}
