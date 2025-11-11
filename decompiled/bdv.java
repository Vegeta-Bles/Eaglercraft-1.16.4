import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bdv extends bhc {
   private static final Predicate<aqa> b = var0 -> var0.aX() && !(var0 instanceof bdv);
   private int bo;
   private int bp;
   private int bq;

   public bdv(aqe<? extends bdv> var1, brx var2) {
      super(_snowman, _snowman);
      this.G = 1.0F;
      this.f = 20;
   }

   @Override
   protected void o() {
      super.o();
      this.bk.a(0, new avp(this));
      this.bk.a(4, new bdv.a());
      this.bk.a(5, new axk(this, 0.4));
      this.bk.a(6, new awd(this, bfw.class, 6.0F));
      this.bk.a(10, new awd(this, aqn.class, 8.0F));
      this.bl.a(2, new axp(this, bhc.class).a());
      this.bl.a(3, new axq<>(this, bfw.class, true));
      this.bl.a(4, new axq<>(this, bfe.class, true));
      this.bl.a(4, new axq<>(this, bai.class, true));
   }

   @Override
   protected void H() {
      boolean _snowman = !(this.cm() instanceof aqn) || this.cm().X().a(aee.c);
      boolean _snowmanx = !(this.ct() instanceof bhn);
      this.bk.a(avv.a.a, _snowman);
      this.bk.a(avv.a.c, _snowman && _snowmanx);
      this.bk.a(avv.a.b, _snowman);
      this.bk.a(avv.a.d, _snowman);
   }

   public static ark.a m() {
      return bdq.eR().a(arl.a, 100.0).a(arl.d, 0.3).a(arl.c, 0.75).a(arl.f, 12.0).a(arl.g, 1.5).a(arl.b, 32.0);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("AttackTick", this.bo);
      _snowman.b("StunTick", this.bp);
      _snowman.b("RoarTick", this.bq);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.bo = _snowman.h("AttackTick");
      this.bp = _snowman.h("StunTick");
      this.bq = _snowman.h("RoarTick");
   }

   @Override
   public adp eL() {
      return adq.mg;
   }

   @Override
   protected ayj b(brx var1) {
      return new bdv.b(this, _snowman);
   }

   @Override
   public int Q() {
      return 45;
   }

   @Override
   public double bc() {
      return 2.1;
   }

   @Override
   public boolean er() {
      return !this.eD() && this.cm() instanceof aqm;
   }

   @Nullable
   @Override
   public aqa cm() {
      return this.cn().isEmpty() ? null : this.cn().get(0);
   }

   @Override
   public void k() {
      super.k();
      if (this.aX()) {
         if (this.dI()) {
            this.a(arl.d).a(0.0);
         } else {
            double _snowman = this.A() != null ? 0.35 : 0.3;
            double _snowmanx = this.a(arl.d).b();
            this.a(arl.d).a(afm.d(0.1, _snowmanx, _snowman));
         }

         if (this.u && this.l.V().b(brt.b)) {
            boolean _snowman = false;
            dci _snowmanx = this.cc().g(0.2);

            for (fx _snowmanxx : fx.b(afm.c(_snowmanx.a), afm.c(_snowmanx.b), afm.c(_snowmanx.c), afm.c(_snowmanx.d), afm.c(_snowmanx.e), afm.c(_snowmanx.f))) {
               ceh _snowmanxxx = this.l.d_(_snowmanxx);
               buo _snowmanxxxx = _snowmanxxx.b();
               if (_snowmanxxxx instanceof bxx) {
                  _snowman = this.l.a(_snowmanxx, true, this) || _snowman;
               }
            }

            if (!_snowman && this.t) {
               this.dK();
            }
         }

         if (this.bq > 0) {
            this.bq--;
            if (this.bq == 10) {
               this.eY();
            }
         }

         if (this.bo > 0) {
            this.bo--;
         }

         if (this.bp > 0) {
            this.bp--;
            this.eX();
            if (this.bp == 0) {
               this.a(adq.ml, 1.0F, 1.0F);
               this.bq = 20;
            }
         }
      }
   }

   private void eX() {
      if (this.J.nextInt(6) == 0) {
         double _snowman = this.cD() - (double)this.cy() * Math.sin((double)(this.aA * (float) (Math.PI / 180.0))) + (this.J.nextDouble() * 0.6 - 0.3);
         double _snowmanx = this.cE() + (double)this.cz() - 0.3;
         double _snowmanxx = this.cH() + (double)this.cy() * Math.cos((double)(this.aA * (float) (Math.PI / 180.0))) + (this.J.nextDouble() * 0.6 - 0.3);
         this.l.a(hh.u, _snowman, _snowmanx, _snowmanxx, 0.4980392156862745, 0.5137254901960784, 0.5725490196078431);
      }
   }

   @Override
   protected boolean dI() {
      return super.dI() || this.bo > 0 || this.bp > 0 || this.bq > 0;
   }

   @Override
   public boolean D(aqa var1) {
      return this.bp <= 0 && this.bq <= 0 ? super.D(_snowman) : false;
   }

   @Override
   protected void e(aqm var1) {
      if (this.bq == 0) {
         if (this.J.nextDouble() < 0.5) {
            this.bp = 40;
            this.a(adq.mk, 1.0F, 1.0F);
            this.l.a(this, (byte)39);
            _snowman.i(this);
         } else {
            this.a(_snowman);
         }

         _snowman.w = true;
      }
   }

   private void eY() {
      if (this.aX()) {
         for (aqa _snowman : this.l.a(aqm.class, this.cc().g(4.0), b)) {
            if (!(_snowman instanceof bcy)) {
               _snowman.a(apk.c(this), 6.0F);
            }

            this.a(_snowman);
         }

         dcn _snowman = this.cc().f();

         for (int _snowmanx = 0; _snowmanx < 40; _snowmanx++) {
            double _snowmanxx = this.J.nextGaussian() * 0.2;
            double _snowmanxxx = this.J.nextGaussian() * 0.2;
            double _snowmanxxxx = this.J.nextGaussian() * 0.2;
            this.l.a(hh.P, _snowman.b, _snowman.c, _snowman.d, _snowmanxx, _snowmanxxx, _snowmanxxxx);
         }
      }
   }

   private void a(aqa var1) {
      double _snowman = _snowman.cD() - this.cD();
      double _snowmanx = _snowman.cH() - this.cH();
      double _snowmanxx = Math.max(_snowman * _snowman + _snowmanx * _snowmanx, 0.001);
      _snowman.i(_snowman / _snowmanxx * 4.0, 0.2, _snowmanx / _snowmanxx * 4.0);
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 4) {
         this.bo = 10;
         this.a(adq.mf, 1.0F, 1.0F);
      } else if (_snowman == 39) {
         this.bp = 40;
      }

      super.a(_snowman);
   }

   public int eK() {
      return this.bo;
   }

   public int eM() {
      return this.bp;
   }

   public int eW() {
      return this.bq;
   }

   @Override
   public boolean B(aqa var1) {
      this.bo = 10;
      this.l.a(this, (byte)4);
      this.a(adq.mf, 1.0F, 1.0F);
      return super.B(_snowman);
   }

   @Nullable
   @Override
   protected adp I() {
      return adq.me;
   }

   @Override
   protected adp e(apk var1) {
      return adq.mi;
   }

   @Override
   protected adp dq() {
      return adq.mh;
   }

   @Override
   protected void b(fx var1, ceh var2) {
      this.a(adq.mj, 0.15F, 1.0F);
   }

   @Override
   public boolean a(brz var1) {
      return !_snowman.d(this.cc());
   }

   @Override
   public void a(int var1, boolean var2) {
   }

   @Override
   public boolean eN() {
      return false;
   }

   class a extends awf {
      public a() {
         super(bdv.this, 1.0, true);
      }

      @Override
      protected double a(aqm var1) {
         float _snowman = bdv.this.cy() - 0.1F;
         return (double)(_snowman * 2.0F * _snowman * 2.0F + _snowman.cy());
      }
   }

   static class b extends ayi {
      public b(aqn var1, brx var2) {
         super(_snowman, _snowman);
      }

      @Override
      protected cxf a(int var1) {
         this.o = new bdv.c();
         return new cxf(this.o, _snowman);
      }
   }

   static class c extends cxj {
      private c() {
      }

      @Override
      protected cwz a(brc var1, boolean var2, boolean var3, fx var4, cwz var5) {
         return _snowman == cwz.v ? cwz.b : super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }
}
