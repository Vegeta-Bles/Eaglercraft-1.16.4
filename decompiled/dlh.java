import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Objects;

public abstract class dlh extends dkw implements dmf, dmi {
   public static final vk i = new vk("textures/gui/widgets.png");
   protected int j;
   protected int k;
   public int l;
   public int m;
   private nr a;
   private boolean b;
   protected boolean n;
   public boolean o = true;
   public boolean p = true;
   protected float q = 1.0F;
   protected long r = Long.MAX_VALUE;
   private boolean c;

   public dlh(int var1, int var2, int var3, int var4, nr var5) {
      this.l = _snowman;
      this.m = _snowman;
      this.j = _snowman;
      this.k = _snowman;
      this.a = _snowman;
   }

   public int e() {
      return this.k;
   }

   protected int a(boolean var1) {
      int _snowman = 1;
      if (!this.o) {
         _snowman = 0;
      } else if (_snowman) {
         _snowman = 2;
      }

      return _snowman;
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      if (this.p) {
         this.n = _snowman >= this.l && _snowman >= this.m && _snowman < this.l + this.j && _snowman < this.m + this.k;
         if (this.b != this.g()) {
            if (this.g()) {
               if (this.c) {
                  this.c(200);
               } else {
                  this.c(750);
               }
            } else {
               this.r = Long.MAX_VALUE;
            }
         }

         if (this.p) {
            this.b(_snowman, _snowman, _snowman, _snowman);
         }

         this.f();
         this.b = this.g();
      }
   }

   protected void f() {
      if (this.o && this.g() && x.b() > this.r) {
         String _snowman = this.c().getString();
         if (!_snowman.isEmpty()) {
            dkz.b.a(_snowman);
            this.r = Long.MAX_VALUE;
         }
      }
   }

   protected nx c() {
      return new of("gui.narrate.button", this.i());
   }

   public void b(dfm var1, int var2, int var3, float var4) {
      djz _snowman = djz.C();
      dku _snowmanx = _snowman.g;
      _snowman.M().a(i);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.q);
      int _snowmanxx = this.a(this.g());
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableDepthTest();
      this.b(_snowman, this.l, this.m, 0, 46 + _snowmanxx * 20, this.j / 2, this.k);
      this.b(_snowman, this.l + this.j / 2, this.m, 200 - this.j / 2, 46 + _snowmanxx * 20, this.j / 2, this.k);
      this.a(_snowman, _snowman, _snowman, _snowman);
      int _snowmanxxx = this.o ? 16777215 : 10526880;
      a(_snowman, _snowmanx, this.i(), this.l + this.j / 2, this.m + (this.k - 8) / 2, _snowmanxxx | afm.f(this.q * 255.0F) << 24);
   }

   protected void a(dfm var1, djz var2, int var3, int var4) {
   }

   public void a(double var1, double var3) {
   }

   public void a_(double var1, double var3) {
   }

   protected void a(double var1, double var3, double var5, double var7) {
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      if (this.o && this.p) {
         if (this.a(_snowman)) {
            boolean _snowman = this.c(_snowman, _snowman);
            if (_snowman) {
               this.a(djz.C().W());
               this.a(_snowman, _snowman);
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   @Override
   public boolean c(double var1, double var3, int var5) {
      if (this.a(_snowman)) {
         this.a_(_snowman, _snowman);
         return true;
      } else {
         return false;
      }
   }

   protected boolean a(int var1) {
      return _snowman == 0;
   }

   @Override
   public boolean a(double var1, double var3, int var5, double var6, double var8) {
      if (this.a(_snowman)) {
         this.a(_snowman, _snowman, _snowman, _snowman);
         return true;
      } else {
         return false;
      }
   }

   protected boolean c(double var1, double var3) {
      return this.o && this.p && _snowman >= (double)this.l && _snowman >= (double)this.m && _snowman < (double)(this.l + this.j) && _snowman < (double)(this.m + this.k);
   }

   public boolean g() {
      return this.n || this.c;
   }

   @Override
   public boolean c_(boolean var1) {
      if (this.o && this.p) {
         this.c = !this.c;
         this.c(this.c);
         return this.c;
      } else {
         return false;
      }
   }

   protected void c(boolean var1) {
   }

   @Override
   public boolean b(double var1, double var3) {
      return this.o && this.p && _snowman >= (double)this.l && _snowman >= (double)this.m && _snowman < (double)(this.l + this.j) && _snowman < (double)(this.m + this.k);
   }

   public void a(dfm var1, int var2, int var3) {
   }

   public void a(enu var1) {
      _snowman.a(emp.a(adq.pF, 1.0F));
   }

   public int h() {
      return this.j;
   }

   public void b(int var1) {
      this.j = _snowman;
   }

   public void a(float var1) {
      this.q = _snowman;
   }

   public void a(nr var1) {
      if (!Objects.equals(_snowman.getString(), this.a.getString())) {
         this.c(250);
      }

      this.a = _snowman;
   }

   public void c(int var1) {
      this.r = x.b() + (long)_snowman;
   }

   public nr i() {
      return this.a;
   }

   public boolean j() {
      return this.c;
   }

   protected void d(boolean var1) {
      this.c = _snowman;
   }
}
