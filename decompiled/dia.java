import com.mojang.blaze3d.systems.RenderSystem;

public class dia extends eoo {
   private static final vk a = new vk("realms", "textures/gui/realms/invite_icon.png");
   private static final vk b = new vk("realms", "textures/gui/realms/trial_icon.png");
   private static final vk c = new vk("realms", "textures/gui/realms/news_notification_mainscreen.png");
   private static final dhl p = new dhl();
   private volatile int q;
   private static boolean r;
   private static boolean s;
   private static boolean t;
   private static boolean u;

   public dia() {
   }

   @Override
   public void b() {
      this.k();
      this.i.m.a(true);
   }

   @Override
   public void d() {
      if ((!this.h() || !this.i() || !t) && !p.a()) {
         p.l();
      } else if (t && this.h()) {
         p.c();
         if (p.a(dhl.d.b)) {
            this.q = p.g();
         }

         if (p.a(dhl.d.c)) {
            s = p.h();
         }

         if (p.a(dhl.d.e)) {
            u = p.j();
         }

         p.d();
      }
   }

   private boolean h() {
      return this.i.k.T;
   }

   private boolean i() {
      return this.i.y instanceof doy;
   }

   private void k() {
      if (!r) {
         r = true;
         (new Thread("Realms Notification Availability checker #1") {
            @Override
            public void run() {
               dgb _snowman = dgb.a();

               try {
                  dgb.a _snowmanx = _snowman.i();
                  if (_snowmanx != dgb.a.a) {
                     return;
                  }
               } catch (dhi var3) {
                  if (var3.a != 401) {
                     dia.r = false;
                  }

                  return;
               }

               dia.t = true;
            }
         }).start();
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      if (t) {
         this.a(_snowman, _snowman, _snowman);
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   private void a(dfm var1, int var2, int var3) {
      int _snowman = this.q;
      int _snowmanx = 24;
      int _snowmanxx = this.l / 4 + 48;
      int _snowmanxxx = this.k / 2 + 80;
      int _snowmanxxxx = _snowmanxx + 48 + 2;
      int _snowmanxxxxx = 0;
      if (u) {
         this.i.M().a(c);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.pushMatrix();
         RenderSystem.scalef(0.4F, 0.4F, 0.4F);
         dkw.a(_snowman, (int)((double)(_snowmanxxx + 2 - _snowmanxxxxx) * 2.5), (int)((double)_snowmanxxxx * 2.5), 0.0F, 0.0F, 40, 40, 40, 40);
         RenderSystem.popMatrix();
         _snowmanxxxxx += 14;
      }

      if (_snowman != 0) {
         this.i.M().a(a);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         dkw.a(_snowman, _snowmanxxx - _snowmanxxxxx, _snowmanxxxx - 6, 0.0F, 0.0F, 15, 25, 31, 25);
         _snowmanxxxxx += 16;
      }

      if (s) {
         this.i.M().a(b);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         int _snowmanxxxxxx = 0;
         if ((x.b() / 800L & 1L) == 1L) {
            _snowmanxxxxxx = 8;
         }

         dkw.a(_snowman, _snowmanxxx + 4 - _snowmanxxxxx, _snowmanxxxx + 4, 0.0F, (float)_snowmanxxxxxx, 8, 8, 8, 16);
      }
   }

   @Override
   public void e() {
      p.l();
   }
}
