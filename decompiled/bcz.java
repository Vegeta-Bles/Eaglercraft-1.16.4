import java.time.LocalDate;
import java.time.temporal.ChronoField;
import javax.annotation.Nullable;

public abstract class bcz extends bdq implements bdu {
   private final aww<bcz> b = new aww<>(this, 1.0, 20, 15.0F);
   private final awf c = new awf(this, 1.2, false) {
      @Override
      public void d() {
         super.d();
         bcz.this.s(false);
      }

      @Override
      public void c() {
         super.c();
         bcz.this.s(true);
      }
   };

   protected bcz(aqe<? extends bcz> var1, brx var2) {
      super(_snowman, _snowman);
      this.eL();
   }

   @Override
   protected void o() {
      this.bk.a(2, new awz(this));
      this.bk.a(3, new avo(this, 1.0));
      this.bk.a(3, new avd<>(this, baz.class, 6.0F, 1.0, 1.2));
      this.bk.a(5, new axk(this, 1.0));
      this.bk.a(6, new awd(this, bfw.class, 8.0F));
      this.bk.a(6, new aws(this));
      this.bl.a(1, new axp(this));
      this.bl.a(2, new axq<>(this, bfw.class, true));
      this.bl.a(3, new axq<>(this, bai.class, true));
      this.bl.a(3, new axq<>(this, bax.class, 10, true, false, bax.bo));
   }

   public static ark.a m() {
      return bdq.eR().a(arl.d, 0.25);
   }

   @Override
   protected void b(fx var1, ceh var2) {
      this.a(this.eK(), 0.15F, 1.0F);
   }

   abstract adp eK();

   @Override
   public aqq dC() {
      return aqq.b;
   }

   @Override
   public void k() {
      boolean _snowman = this.eG();
      if (_snowman) {
         bmb _snowmanx = this.b(aqf.f);
         if (!_snowmanx.a()) {
            if (_snowmanx.e()) {
               _snowmanx.b(_snowmanx.g() + this.J.nextInt(2));
               if (_snowmanx.g() >= _snowmanx.h()) {
                  this.c(aqf.f);
                  this.a(aqf.f, bmb.b);
               }
            }

            _snowman = false;
         }

         if (_snowman) {
            this.f(8);
         }
      }

      super.k();
   }

   @Override
   public void ba() {
      super.ba();
      if (this.ct() instanceof aqu) {
         aqu _snowman = (aqu)this.ct();
         this.aA = _snowman.aA;
      }
   }

   @Override
   protected void a(aos var1) {
      super.a(_snowman);
      this.a(aqf.a, new bmb(bmd.kc));
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      _snowman = super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      this.a(_snowman);
      this.b(_snowman);
      this.eL();
      this.p(this.J.nextFloat() < 0.55F * _snowman.d());
      if (this.b(aqf.f).a()) {
         LocalDate _snowman = LocalDate.now();
         int _snowmanx = _snowman.get(ChronoField.DAY_OF_MONTH);
         int _snowmanxx = _snowman.get(ChronoField.MONTH_OF_YEAR);
         if (_snowmanxx == 10 && _snowmanx == 31 && this.J.nextFloat() < 0.25F) {
            this.a(aqf.f, new bmb(this.J.nextFloat() < 0.1F ? bup.cV : bup.cU));
            this.bn[aqf.f.b()] = 0.0F;
         }
      }

      return _snowman;
   }

   public void eL() {
      if (this.l != null && !this.l.v) {
         this.bk.a(this.c);
         this.bk.a(this.b);
         bmb _snowman = this.b(bgn.a(this, bmd.kc));
         if (_snowman.b() == bmd.kc) {
            int _snowmanx = 20;
            if (this.l.ad() != aor.d) {
               _snowmanx = 40;
            }

            this.b.a(_snowmanx);
            this.bk.a(4, this.b);
         } else {
            this.bk.a(4, this.c);
         }
      }
   }

   @Override
   public void a(aqm var1, float var2) {
      bmb _snowman = this.f(this.b(bgn.a(this, bmd.kc)));
      bga _snowmanx = this.b(_snowman, _snowman);
      double _snowmanxx = _snowman.cD() - this.cD();
      double _snowmanxxx = _snowman.e(0.3333333333333333) - _snowmanx.cE();
      double _snowmanxxxx = _snowman.cH() - this.cH();
      double _snowmanxxxxx = (double)afm.a(_snowmanxx * _snowmanxx + _snowmanxxxx * _snowmanxxxx);
      _snowmanx.c(_snowmanxx, _snowmanxxx + _snowmanxxxxx * 0.2F, _snowmanxxxx, 1.6F, (float)(14 - this.l.ad().a() * 4));
      this.a(adq.nD, 1.0F, 1.0F / (this.cY().nextFloat() * 0.4F + 0.8F));
      this.l.c(_snowmanx);
   }

   protected bga b(bmb var1, float var2) {
      return bgn.a(this, _snowman, _snowman);
   }

   @Override
   public boolean a(bmo var1) {
      return _snowman == bmd.kc;
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.eL();
   }

   @Override
   public void a(aqf var1, bmb var2) {
      super.a(_snowman, _snowman);
      if (!this.l.v) {
         this.eL();
      }
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return 1.74F;
   }

   @Override
   public double bb() {
      return -0.6;
   }
}
