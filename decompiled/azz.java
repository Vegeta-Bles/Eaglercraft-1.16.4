import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;

public abstract class azz extends apy {
   private int bo;
   private UUID bp;

   protected azz(aqe<? extends azz> var1, brx var2) {
      super(_snowman, _snowman);
      this.a(cwz.l, 16.0F);
      this.a(cwz.m, -1.0F);
   }

   @Override
   protected void N() {
      if (this.i() != 0) {
         this.bo = 0;
      }

      super.N();
   }

   @Override
   public void k() {
      super.k();
      if (this.i() != 0) {
         this.bo = 0;
      }

      if (this.bo > 0) {
         this.bo--;
         if (this.bo % 10 == 0) {
            double _snowman = this.J.nextGaussian() * 0.02;
            double _snowmanx = this.J.nextGaussian() * 0.02;
            double _snowmanxx = this.J.nextGaussian() * 0.02;
            this.l.a(hh.G, this.d(1.0), this.cF() + 0.5, this.g(1.0), _snowman, _snowmanx, _snowmanxx);
         }
      }
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (this.b(_snowman)) {
         return false;
      } else {
         this.bo = 0;
         return super.a(_snowman, _snowman);
      }
   }

   @Override
   public float a(fx var1, brz var2) {
      return _snowman.d_(_snowman.c()).a(bup.i) ? 10.0F : _snowman.y(_snowman) - 0.5F;
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("InLove", this.bo);
      if (this.bp != null) {
         _snowman.a("LoveCause", this.bp);
      }
   }

   @Override
   public double bb() {
      return 0.14;
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.bo = _snowman.h("InLove");
      this.bp = _snowman.b("LoveCause") ? _snowman.a("LoveCause") : null;
   }

   public static boolean b(aqe<? extends azz> var0, bry var1, aqp var2, fx var3, Random var4) {
      return _snowman.d_(_snowman.c()).a(bup.i) && _snowman.b(_snowman, 0) > 8;
   }

   @Override
   public int D() {
      return 120;
   }

   @Override
   public boolean h(double var1) {
      return false;
   }

   @Override
   protected int d(bfw var1) {
      return 1 + this.l.t.nextInt(3);
   }

   public boolean k(bmb var1) {
      return _snowman.b() == bmd.kW;
   }

   @Override
   public aou b(bfw var1, aot var2) {
      bmb _snowman = _snowman.b(_snowman);
      if (this.k(_snowman)) {
         int _snowmanx = this.i();
         if (!this.l.v && _snowmanx == 0 && this.eP()) {
            this.a(_snowman, _snowman);
            this.g(_snowman);
            return aou.a;
         }

         if (this.w_()) {
            this.a(_snowman, _snowman);
            this.a((int)((float)(-_snowmanx / 20) * 0.1F), true);
            return aou.a(this.l.v);
         }

         if (this.l.v) {
            return aou.b;
         }
      }

      return super.b(_snowman, _snowman);
   }

   protected void a(bfw var1, bmb var2) {
      if (!_snowman.bC.d) {
         _snowman.g(1);
      }
   }

   public boolean eP() {
      return this.bo <= 0;
   }

   public void g(@Nullable bfw var1) {
      this.bo = 600;
      if (_snowman != null) {
         this.bp = _snowman.bS();
      }

      this.l.a(this, (byte)18);
   }

   public void s(int var1) {
      this.bo = _snowman;
   }

   public int eQ() {
      return this.bo;
   }

   @Nullable
   public aah eR() {
      if (this.bp == null) {
         return null;
      } else {
         bfw _snowman = this.l.b(this.bp);
         return _snowman instanceof aah ? (aah)_snowman : null;
      }
   }

   public boolean eS() {
      return this.bo > 0;
   }

   public void eT() {
      this.bo = 0;
   }

   public boolean a(azz var1) {
      if (_snowman == this) {
         return false;
      } else {
         return _snowman.getClass() != this.getClass() ? false : this.eS() && _snowman.eS();
      }
   }

   public void a(aag var1, azz var2) {
      apy _snowman = this.a(_snowman, (apy)_snowman);
      if (_snowman != null) {
         aah _snowmanx = this.eR();
         if (_snowmanx == null && _snowman.eR() != null) {
            _snowmanx = _snowman.eR();
         }

         if (_snowmanx != null) {
            _snowmanx.a(aea.O);
            ac.o.a(_snowmanx, this, _snowman, _snowman);
         }

         this.c_(6000);
         _snowman.c_(6000);
         this.eT();
         _snowman.eT();
         _snowman.a(true);
         _snowman.b(this.cD(), this.cE(), this.cH(), 0.0F, 0.0F);
         _snowman.l(_snowman);
         _snowman.a(this, (byte)18);
         if (_snowman.V().b(brt.e)) {
            _snowman.c(new aqg(_snowman, this.cD(), this.cE(), this.cH(), this.cY().nextInt(7) + 1));
         }
      }
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 18) {
         for (int _snowman = 0; _snowman < 7; _snowman++) {
            double _snowmanx = this.J.nextGaussian() * 0.02;
            double _snowmanxx = this.J.nextGaussian() * 0.02;
            double _snowmanxxx = this.J.nextGaussian() * 0.02;
            this.l.a(hh.G, this.d(1.0), this.cF() + 0.5, this.g(1.0), _snowmanx, _snowmanxx, _snowmanxxx);
         }
      } else {
         super.a(_snowman);
      }
   }
}
