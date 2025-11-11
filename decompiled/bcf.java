import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bcf extends bbt {
   private static final Logger b = LogManager.getLogger();
   private int c;
   private cxd d;
   private dcn e;
   private aqm f;
   private boolean g;

   public bcf(bbr var1) {
      super(_snowman);
   }

   @Override
   public void c() {
      if (this.f == null) {
         b.warn("Skipping player strafe phase because no player was found");
         this.a.eK().a(bch.a);
      } else {
         if (this.d != null && this.d.c()) {
            double _snowman = this.f.cD();
            double _snowmanx = this.f.cH();
            double _snowmanxx = _snowman - this.a.cD();
            double _snowmanxxx = _snowmanx - this.a.cH();
            double _snowmanxxxx = (double)afm.a(_snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx);
            double _snowmanxxxxx = Math.min(0.4F + _snowmanxxxx / 80.0 - 1.0, 10.0);
            this.e = new dcn(_snowman, this.f.cE() + _snowmanxxxxx, _snowmanx);
         }

         double _snowman = this.e == null ? 0.0 : this.e.c(this.a.cD(), this.a.cE(), this.a.cH());
         if (_snowman < 100.0 || _snowman > 22500.0) {
            this.j();
         }

         double _snowmanx = 64.0;
         if (this.f.h(this.a) < 4096.0) {
            if (this.a.D(this.f)) {
               this.c++;
               dcn _snowmanxx = new dcn(this.f.cD() - this.a.cD(), 0.0, this.f.cH() - this.a.cH()).d();
               dcn _snowmanxxx = new dcn((double)afm.a(this.a.p * (float) (Math.PI / 180.0)), 0.0, (double)(-afm.b(this.a.p * (float) (Math.PI / 180.0)))).d();
               float _snowmanxxxx = (float)_snowmanxxx.b(_snowmanxx);
               float _snowmanxxxxx = (float)(Math.acos((double)_snowmanxxxx) * 180.0F / (float)Math.PI);
               _snowmanxxxxx += 0.5F;
               if (this.c >= 5 && _snowmanxxxxx >= 0.0F && _snowmanxxxxx < 10.0F) {
                  double _snowmanxxxxxx = 1.0;
                  dcn _snowmanxxxxxxx = this.a.f(1.0F);
                  double _snowmanxxxxxxxx = this.a.bo.cD() - _snowmanxxxxxxx.b * 1.0;
                  double _snowmanxxxxxxxxx = this.a.bo.e(0.5) + 0.5;
                  double _snowmanxxxxxxxxxx = this.a.bo.cH() - _snowmanxxxxxxx.d * 1.0;
                  double _snowmanxxxxxxxxxxx = this.f.cD() - _snowmanxxxxxxxx;
                  double _snowmanxxxxxxxxxxxx = this.f.e(0.5) - _snowmanxxxxxxxxx;
                  double _snowmanxxxxxxxxxxxxx = this.f.cH() - _snowmanxxxxxxxxxx;
                  if (!this.a.aA()) {
                     this.a.l.a(null, 1017, this.a.cB(), 0);
                  }

                  bgd _snowmanxxxxxxxxxxxxxx = new bgd(this.a.l, this.a, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
                  _snowmanxxxxxxxxxxxxxx.b(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, 0.0F, 0.0F);
                  this.a.l.c(_snowmanxxxxxxxxxxxxxx);
                  this.c = 0;
                  if (this.d != null) {
                     while (!this.d.c()) {
                        this.d.a();
                     }
                  }

                  this.a.eK().a(bch.a);
               }
            } else if (this.c > 0) {
               this.c--;
            }
         } else if (this.c > 0) {
            this.c--;
         }
      }
   }

   private void j() {
      if (this.d == null || this.d.c()) {
         int _snowman = this.a.eI();
         int _snowmanx = _snowman;
         if (this.a.cY().nextInt(8) == 0) {
            this.g = !this.g;
            _snowmanx = _snowman + 6;
         }

         if (this.g) {
            _snowmanx++;
         } else {
            _snowmanx--;
         }

         if (this.a.eL() != null && this.a.eL().c() > 0) {
            _snowmanx %= 12;
            if (_snowmanx < 0) {
               _snowmanx += 12;
            }
         } else {
            _snowmanx -= 12;
            _snowmanx &= 7;
            _snowmanx += 12;
         }

         this.d = this.a.a(_snowman, _snowmanx, null);
         if (this.d != null) {
            this.d.a();
         }
      }

      this.k();
   }

   private void k() {
      if (this.d != null && !this.d.c()) {
         gr _snowman = this.d.g();
         this.d.a();
         double _snowmanx = (double)_snowman.u();
         double _snowmanxx = (double)_snowman.w();

         double _snowmanxxx;
         do {
            _snowmanxxx = (double)((float)_snowman.v() + this.a.cY().nextFloat() * 20.0F);
         } while (_snowmanxxx < (double)_snowman.v());

         this.e = new dcn(_snowmanx, _snowmanxxx, _snowmanxx);
      }
   }

   @Override
   public void d() {
      this.c = 0;
      this.e = null;
      this.d = null;
      this.f = null;
   }

   public void a(aqm var1) {
      this.f = _snowman;
      int _snowman = this.a.eI();
      int _snowmanx = this.a.p(this.f.cD(), this.f.cE(), this.f.cH());
      int _snowmanxx = afm.c(this.f.cD());
      int _snowmanxxx = afm.c(this.f.cH());
      double _snowmanxxxx = (double)_snowmanxx - this.a.cD();
      double _snowmanxxxxx = (double)_snowmanxxx - this.a.cH();
      double _snowmanxxxxxx = (double)afm.a(_snowmanxxxx * _snowmanxxxx + _snowmanxxxxx * _snowmanxxxxx);
      double _snowmanxxxxxxx = Math.min(0.4F + _snowmanxxxxxx / 80.0 - 1.0, 10.0);
      int _snowmanxxxxxxxx = afm.c(this.f.cE() + _snowmanxxxxxxx);
      cxb _snowmanxxxxxxxxx = new cxb(_snowmanxx, _snowmanxxxxxxxx, _snowmanxxx);
      this.d = this.a.a(_snowman, _snowmanx, _snowmanxxxxxxxxx);
      if (this.d != null) {
         this.d.a();
         this.k();
      }
   }

   @Nullable
   @Override
   public dcn g() {
      return this.e;
   }

   @Override
   public bch<bcf> i() {
      return bch.b;
   }
}
