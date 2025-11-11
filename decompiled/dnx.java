import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;

public class dnx extends dot {
   private int a;
   private final nr b;
   private final boolean c;
   private nr p;

   public dnx(@Nullable nr var1, boolean var2) {
      super(new of(_snowman ? "deathScreen.title.hardcore" : "deathScreen.title"));
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   protected void b() {
      this.a = 0;
      this.a(new dlj(this.k / 2 - 100, this.l / 4 + 72, 200, 20, this.c ? new of("deathScreen.spectate") : new of("deathScreen.respawn"), var1x -> {
         this.i.s.ey();
         this.i.a(null);
      }));
      dlj _snowman = this.a(new dlj(this.k / 2 - 100, this.l / 4 + 96, 200, 20, new of("deathScreen.titleScreen"), var1x -> {
         if (this.c) {
            this.h();
         } else {
            dns _snowmanx = new dns(this::c, new of("deathScreen.quit.confirm"), oe.d, new of("deathScreen.titleScreen"), new of("deathScreen.respawn"));
            this.i.a(_snowmanx);
            _snowmanx.a(20);
         }
      }));
      if (!this.c && this.i.J() == null) {
         _snowman.o = false;
      }

      for (dlh _snowmanx : this.m) {
         _snowmanx.o = false;
      }

      this.p = new of("deathScreen.score").c(": ").a(new oe(Integer.toString(this.i.s.ev())).a(k.o));
   }

   @Override
   public boolean as_() {
      return false;
   }

   private void c(boolean var1) {
      if (_snowman) {
         this.h();
      } else {
         this.i.s.ey();
         this.i.a(null);
      }
   }

   private void h() {
      if (this.i.r != null) {
         this.i.r.S();
      }

      this.i.b(new dod(new of("menu.savingLevel")));
      this.i.a(new doy());
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman, 0, 0, this.k, this.l, 1615855616, -1602211792);
      RenderSystem.pushMatrix();
      RenderSystem.scalef(2.0F, 2.0F, 2.0F);
      a(_snowman, this.o, this.d, this.k / 2 / 2, 30, 16777215);
      RenderSystem.popMatrix();
      if (this.b != null) {
         a(_snowman, this.o, this.b, this.k / 2, 85, 16777215);
      }

      a(_snowman, this.o, this.p, this.k / 2, 100, 16777215);
      if (this.b != null && _snowman > 85 && _snowman < 85 + 9) {
         ob _snowman = this.a(_snowman);
         this.a(_snowman, _snowman, _snowman, _snowman);
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Nullable
   private ob a(int var1) {
      if (this.b == null) {
         return null;
      } else {
         int _snowman = this.i.g.a(this.b);
         int _snowmanx = this.k / 2 - _snowman / 2;
         int _snowmanxx = this.k / 2 + _snowman / 2;
         return _snowman >= _snowmanx && _snowman <= _snowmanxx ? this.i.g.b().a(this.b, _snowman - _snowmanx) : null;
      }
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      if (this.b != null && _snowman > 85.0 && _snowman < (double)(85 + 9)) {
         ob _snowman = this.a((int)_snowman);
         if (_snowman != null && _snowman.h() != null && _snowman.h().a() == np.a.a) {
            this.a(_snowman);
            return false;
         }
      }

      return super.a(_snowman, _snowman, _snowman);
   }

   @Override
   public boolean ay_() {
      return false;
   }

   @Override
   public void d() {
      super.d();
      this.a++;
      if (this.a == 20) {
         for (dlh _snowman : this.m) {
            _snowman.o = true;
         }
      }
   }
}
