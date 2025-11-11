import java.util.Random;
import javax.annotation.Nullable;

public class bak extends azz {
   private static final bon bo = bon.a(bmd.ml, bmd.mm);
   private static final us<Boolean> bp = uv.a(bak.class, uu.i);
   private bak.a<bfw> bq;
   private bak.b br;

   public bak(aqe<? extends bak> var1, brx var2) {
      super(_snowman, _snowman);
      this.eL();
   }

   private boolean eM() {
      return this.R.a(bp);
   }

   private void t(boolean var1) {
      this.R.b(bp, _snowman);
      this.eL();
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.a("Trusting", this.eM());
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.t(_snowman.q("Trusting"));
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(bp, false);
   }

   @Override
   protected void o() {
      this.br = new bak.b(this, 0.6, bo, true);
      this.bk.a(1, new avp(this));
      this.bk.a(3, this.br);
      this.bk.a(7, new awb(this, 0.3F));
      this.bk.a(8, new awm(this));
      this.bk.a(9, new avi(this, 0.8));
      this.bk.a(10, new axk(this, 0.8, 1.0000001E-5F));
      this.bk.a(11, new awd(this, bfw.class, 10.0F));
      this.bl.a(1, new axq<>(this, bac.class, false));
      this.bl.a(1, new axq<>(this, bax.class, 10, false, false, bax.bo));
   }

   @Override
   public void N() {
      if (this.u().b()) {
         double _snowman = this.u().c();
         if (_snowman == 0.6) {
            this.b(aqx.f);
            this.g(false);
         } else if (_snowman == 1.33) {
            this.b(aqx.a);
            this.g(true);
         } else {
            this.b(aqx.a);
            this.g(false);
         }
      } else {
         this.b(aqx.a);
         this.g(false);
      }
   }

   @Override
   public boolean h(double var1) {
      return !this.eM() && this.K > 2400;
   }

   public static ark.a eK() {
      return aqn.p().a(arl.a, 10.0).a(arl.d, 0.3F).a(arl.f, 3.0);
   }

   @Override
   public boolean b(float var1, float var2) {
      return false;
   }

   @Nullable
   @Override
   protected adp I() {
      return adq.jG;
   }

   @Override
   public int D() {
      return 900;
   }

   @Override
   protected adp e(apk var1) {
      return adq.jF;
   }

   @Override
   protected adp dq() {
      return adq.jH;
   }

   private float eN() {
      return (float)this.b(arl.f);
   }

   @Override
   public boolean B(aqa var1) {
      return _snowman.a(apk.c(this), this.eN());
   }

   @Override
   public boolean a(apk var1, float var2) {
      return this.b(_snowman) ? false : super.a(_snowman, _snowman);
   }

   @Override
   public aou b(bfw var1, aot var2) {
      bmb _snowman = _snowman.b(_snowman);
      if ((this.br == null || this.br.h()) && !this.eM() && this.k(_snowman) && _snowman.h(this) < 9.0) {
         this.a(_snowman, _snowman);
         if (!this.l.v) {
            if (this.J.nextInt(3) == 0) {
               this.t(true);
               this.u(true);
               this.l.a(this, (byte)41);
            } else {
               this.u(false);
               this.l.a(this, (byte)40);
            }
         }

         return aou.a(this.l.v);
      } else {
         return super.b(_snowman, _snowman);
      }
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 41) {
         this.u(true);
      } else if (_snowman == 40) {
         this.u(false);
      } else {
         super.a(_snowman);
      }
   }

   private void u(boolean var1) {
      hf _snowman = hh.G;
      if (!_snowman) {
         _snowman = hh.S;
      }

      for (int _snowmanx = 0; _snowmanx < 7; _snowmanx++) {
         double _snowmanxx = this.J.nextGaussian() * 0.02;
         double _snowmanxxx = this.J.nextGaussian() * 0.02;
         double _snowmanxxxx = this.J.nextGaussian() * 0.02;
         this.l.a(_snowman, this.d(1.0), this.cF() + 0.5, this.g(1.0), _snowmanxx, _snowmanxxx, _snowmanxxxx);
      }
   }

   protected void eL() {
      if (this.bq == null) {
         this.bq = new bak.a<>(this, bfw.class, 16.0F, 0.8, 1.33);
      }

      this.bk.a(this.bq);
      if (!this.eM()) {
         this.bk.a(4, this.bq);
      }
   }

   public bak b(aag var1, apy var2) {
      return aqe.ac.a(_snowman);
   }

   @Override
   public boolean k(bmb var1) {
      return bo.a(_snowman);
   }

   public static boolean c(aqe<bak> var0, bry var1, aqp var2, fx var3, Random var4) {
      return _snowman.nextInt(3) != 0;
   }

   @Override
   public boolean a(brz var1) {
      if (_snowman.j(this) && !_snowman.d(this.cc())) {
         fx _snowman = this.cB();
         if (_snowman.v() < _snowman.t_()) {
            return false;
         }

         ceh _snowmanx = _snowman.d_(_snowman.c());
         if (_snowmanx.a(bup.i) || _snowmanx.a(aed.I)) {
            return true;
         }
      }

      return false;
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      if (_snowman == null) {
         _snowman = new apy.a(1.0F);
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public dcn cf() {
      return new dcn(0.0, (double)(0.5F * this.ce()), (double)(this.cy() * 0.4F));
   }

   static class a<T extends aqm> extends avd<T> {
      private final bak i;

      public a(bak var1, Class<T> var2, float var3, double var4, double var6) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, aqd.e::test);
         this.i = _snowman;
      }

      @Override
      public boolean a() {
         return !this.i.eM() && super.a();
      }

      @Override
      public boolean b() {
         return !this.i.eM() && super.b();
      }
   }

   static class b extends axf {
      private final bak c;

      public b(bak var1, double var2, bon var4, boolean var5) {
         super(_snowman, _snowman, _snowman, _snowman);
         this.c = _snowman;
      }

      @Override
      protected boolean g() {
         return super.g() && !this.c.eM();
      }
   }
}
