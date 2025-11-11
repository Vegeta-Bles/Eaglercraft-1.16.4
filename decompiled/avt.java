import java.util.EnumSet;

public class avt extends avv {
   private final are a;
   private aqm b;
   private final brz c;
   private final double d;
   private final ayj e;
   private int f;
   private final float g;
   private final float h;
   private float i;
   private final boolean j;

   public avt(are var1, double var2, float var4, float var5, boolean var6) {
      this.a = _snowman;
      this.c = _snowman.l;
      this.d = _snowman;
      this.e = _snowman.x();
      this.h = _snowman;
      this.g = _snowman;
      this.j = _snowman;
      this.a(EnumSet.of(avv.a.a, avv.a.b));
      if (!(_snowman.x() instanceof ayi) && !(_snowman.x() instanceof ayh)) {
         throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
      }
   }

   @Override
   public boolean a() {
      aqm _snowman = this.a.eN();
      if (_snowman == null) {
         return false;
      } else if (_snowman.a_()) {
         return false;
      } else if (this.a.eO()) {
         return false;
      } else if (this.a.h((aqa)_snowman) < (double)(this.h * this.h)) {
         return false;
      } else {
         this.b = _snowman;
         return true;
      }
   }

   @Override
   public boolean b() {
      if (this.e.m()) {
         return false;
      } else {
         return this.a.eO() ? false : !(this.a.h((aqa)this.b) <= (double)(this.g * this.g));
      }
   }

   @Override
   public void c() {
      this.f = 0;
      this.i = this.a.a(cwz.h);
      this.a.a(cwz.h, 0.0F);
   }

   @Override
   public void d() {
      this.b = null;
      this.e.o();
      this.a.a(cwz.h, this.i);
   }

   @Override
   public void e() {
      this.a.t().a(this.b, 10.0F, (float)this.a.O());
      if (--this.f <= 0) {
         this.f = 10;
         if (!this.a.eB() && !this.a.br()) {
            if (this.a.h((aqa)this.b) >= 144.0) {
               this.g();
            } else {
               this.e.a(this.b, this.d);
            }
         }
      }
   }

   private void g() {
      fx _snowman = this.b.cB();

      for (int _snowmanx = 0; _snowmanx < 10; _snowmanx++) {
         int _snowmanxx = this.a(-3, 3);
         int _snowmanxxx = this.a(-1, 1);
         int _snowmanxxxx = this.a(-3, 3);
         boolean _snowmanxxxxx = this.a(_snowman.u() + _snowmanxx, _snowman.v() + _snowmanxxx, _snowman.w() + _snowmanxxxx);
         if (_snowmanxxxxx) {
            return;
         }
      }
   }

   private boolean a(int var1, int var2, int var3) {
      if (Math.abs((double)_snowman - this.b.cD()) < 2.0 && Math.abs((double)_snowman - this.b.cH()) < 2.0) {
         return false;
      } else if (!this.a(new fx(_snowman, _snowman, _snowman))) {
         return false;
      } else {
         this.a.b((double)_snowman + 0.5, (double)_snowman, (double)_snowman + 0.5, this.a.p, this.a.q);
         this.e.o();
         return true;
      }
   }

   private boolean a(fx var1) {
      cwz _snowman = cxj.a(this.c, _snowman.i());
      if (_snowman != cwz.c) {
         return false;
      } else {
         ceh _snowmanx = this.c.d_(_snowman.c());
         if (!this.j && _snowmanx.b() instanceof bxx) {
            return false;
         } else {
            fx _snowmanxx = _snowman.b(this.a.cB());
            return this.c.a_(this.a, this.a.cc().a(_snowmanxx));
         }
      }
   }

   private int a(int var1, int var2) {
      return this.a.cY().nextInt(_snowman - _snowman + 1) + _snowman;
   }
}
