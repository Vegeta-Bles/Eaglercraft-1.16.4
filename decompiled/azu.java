import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Random;
import javax.annotation.Nullable;

public class azu extends azt {
   private static final us<Byte> b = uv.a(azu.class, uu.a);
   private static final azg c = new azg().a(4.0).b();
   private fx d;

   public azu(aqe<? extends azu> var1, brx var2) {
      super(_snowman, _snowman);
      this.t(true);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(b, (byte)0);
   }

   @Override
   protected float dG() {
      return 0.1F;
   }

   @Override
   protected float dH() {
      return super.dH() * 0.95F;
   }

   @Nullable
   @Override
   public adp I() {
      return this.eI() && this.J.nextInt(4) != 0 ? null : adq.ap;
   }

   @Override
   protected adp e(apk var1) {
      return adq.ar;
   }

   @Override
   protected adp dq() {
      return adq.aq;
   }

   @Override
   public boolean aU() {
      return false;
   }

   @Override
   protected void C(aqa var1) {
   }

   @Override
   protected void dQ() {
   }

   public static ark.a m() {
      return aqn.p().a(arl.a, 6.0);
   }

   public boolean eI() {
      return (this.R.a(b) & 1) != 0;
   }

   public void t(boolean var1) {
      byte _snowman = this.R.a(b);
      if (_snowman) {
         this.R.b(b, (byte)(_snowman | 1));
      } else {
         this.R.b(b, (byte)(_snowman & -2));
      }
   }

   @Override
   public void j() {
      super.j();
      if (this.eI()) {
         this.f(dcn.a);
         this.o(this.cD(), (double)afm.c(this.cE()) + 1.0 - (double)this.cz(), this.cH());
      } else {
         this.f(this.cC().d(1.0, 0.6, 1.0));
      }
   }

   @Override
   protected void N() {
      super.N();
      fx _snowman = this.cB();
      fx _snowmanx = _snowman.b();
      if (this.eI()) {
         boolean _snowmanxx = this.aA();
         if (this.l.d_(_snowmanx).g(this.l, _snowman)) {
            if (this.J.nextInt(200) == 0) {
               this.aC = (float)this.J.nextInt(360);
            }

            if (this.l.a(c, this) != null) {
               this.t(false);
               if (!_snowmanxx) {
                  this.l.a(null, 1025, _snowman, 0);
               }
            }
         } else {
            this.t(false);
            if (!_snowmanxx) {
               this.l.a(null, 1025, _snowman, 0);
            }
         }
      } else {
         if (this.d != null && (!this.l.w(this.d) || this.d.v() < 1)) {
            this.d = null;
         }

         if (this.d == null || this.J.nextInt(30) == 0 || this.d.a(this.cA(), 2.0)) {
            this.d = new fx(
               this.cD() + (double)this.J.nextInt(7) - (double)this.J.nextInt(7),
               this.cE() + (double)this.J.nextInt(6) - 2.0,
               this.cH() + (double)this.J.nextInt(7) - (double)this.J.nextInt(7)
            );
         }

         double _snowmanxx = (double)this.d.u() + 0.5 - this.cD();
         double _snowmanxxx = (double)this.d.v() + 0.1 - this.cE();
         double _snowmanxxxx = (double)this.d.w() + 0.5 - this.cH();
         dcn _snowmanxxxxx = this.cC();
         dcn _snowmanxxxxxx = _snowmanxxxxx.b(
            (Math.signum(_snowmanxx) * 0.5 - _snowmanxxxxx.b) * 0.1F, (Math.signum(_snowmanxxx) * 0.7F - _snowmanxxxxx.c) * 0.1F, (Math.signum(_snowmanxxxx) * 0.5 - _snowmanxxxxx.d) * 0.1F
         );
         this.f(_snowmanxxxxxx);
         float _snowmanxxxxxxx = (float)(afm.d(_snowmanxxxxxx.d, _snowmanxxxxxx.b) * 180.0F / (float)Math.PI) - 90.0F;
         float _snowmanxxxxxxxx = afm.g(_snowmanxxxxxxx - this.p);
         this.aT = 0.5F;
         this.p += _snowmanxxxxxxxx;
         if (this.J.nextInt(100) == 0 && this.l.d_(_snowmanx).g(this.l, _snowmanx)) {
            this.t(true);
         }
      }
   }

   @Override
   protected boolean aC() {
      return false;
   }

   @Override
   public boolean b(float var1, float var2) {
      return false;
   }

   @Override
   protected void a(double var1, boolean var3, ceh var4, fx var5) {
   }

   @Override
   public boolean bQ() {
      return true;
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (this.b(_snowman)) {
         return false;
      } else {
         if (!this.l.v && this.eI()) {
            this.t(false);
         }

         return super.a(_snowman, _snowman);
      }
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.R.b(b, _snowman.f("BatFlags"));
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.a("BatFlags", this.R.a(b));
   }

   public static boolean b(aqe<azu> var0, bry var1, aqp var2, fx var3, Random var4) {
      if (_snowman.v() >= _snowman.t_()) {
         return false;
      } else {
         int _snowman = _snowman.B(_snowman);
         int _snowmanx = 4;
         if (eJ()) {
            _snowmanx = 7;
         } else if (_snowman.nextBoolean()) {
            return false;
         }

         return _snowman > _snowman.nextInt(_snowmanx) ? false : a(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   private static boolean eJ() {
      LocalDate _snowman = LocalDate.now();
      int _snowmanx = _snowman.get(ChronoField.DAY_OF_MONTH);
      int _snowmanxx = _snowman.get(ChronoField.MONTH_OF_YEAR);
      return _snowmanxx == 10 && _snowmanx >= 20 || _snowmanxx == 11 && _snowmanx <= 3;
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return _snowman.b / 2.0F;
   }
}
