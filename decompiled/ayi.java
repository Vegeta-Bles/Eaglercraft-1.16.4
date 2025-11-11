public class ayi extends ayj {
   private boolean p;

   public ayi(aqn var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   protected cxf a(int var1) {
      this.o = new cxj();
      this.o.a(true);
      return new cxf(this.o, _snowman);
   }

   @Override
   protected boolean a() {
      return this.a.ao() || this.p() || this.a.br();
   }

   @Override
   protected dcn b() {
      return new dcn(this.a.cD(), (double)this.u(), this.a.cH());
   }

   @Override
   public cxd a(fx var1, int var2) {
      if (this.b.d_(_snowman).g()) {
         fx _snowman = _snowman.c();

         while (_snowman.v() > 0 && this.b.d_(_snowman).g()) {
            _snowman = _snowman.c();
         }

         if (_snowman.v() > 0) {
            return super.a(_snowman.b(), _snowman);
         }

         while (_snowman.v() < this.b.L() && this.b.d_(_snowman).g()) {
            _snowman = _snowman.b();
         }

         _snowman = _snowman;
      }

      if (!this.b.d_(_snowman).c().b()) {
         return super.a(_snowman, _snowman);
      } else {
         fx _snowman = _snowman.b();

         while (_snowman.v() < this.b.L() && this.b.d_(_snowman).c().b()) {
            _snowman = _snowman.b();
         }

         return super.a(_snowman, _snowman);
      }
   }

   @Override
   public cxd a(aqa var1, int var2) {
      return this.a(_snowman.cB(), _snowman);
   }

   private int u() {
      if (this.a.aE() && this.r()) {
         int _snowman = afm.c(this.a.cE());
         buo _snowmanx = this.b.d_(new fx(this.a.cD(), (double)_snowman, this.a.cH())).b();
         int _snowmanxx = 0;

         while (_snowmanx == bup.A) {
            _snowmanx = this.b.d_(new fx(this.a.cD(), (double)(++_snowman), this.a.cH())).b();
            if (++_snowmanxx > 16) {
               return afm.c(this.a.cE());
            }
         }

         return _snowman;
      } else {
         return afm.c(this.a.cE() + 0.5);
      }
   }

   @Override
   protected void D_() {
      super.D_();
      if (this.p) {
         if (this.b.e(new fx(this.a.cD(), this.a.cE() + 0.5, this.a.cH()))) {
            return;
         }

         for (int _snowman = 0; _snowman < this.c.e(); _snowman++) {
            cxb _snowmanx = this.c.a(_snowman);
            if (this.b.e(new fx(_snowmanx.a, _snowmanx.b, _snowmanx.c))) {
               this.c.b(_snowman);
               return;
            }
         }
      }
   }

   @Override
   protected boolean a(dcn var1, dcn var2, int var3, int var4, int var5) {
      int _snowman = afm.c(_snowman.b);
      int _snowmanx = afm.c(_snowman.d);
      double _snowmanxx = _snowman.b - _snowman.b;
      double _snowmanxxx = _snowman.d - _snowman.d;
      double _snowmanxxxx = _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx;
      if (_snowmanxxxx < 1.0E-8) {
         return false;
      } else {
         double _snowmanxxxxx = 1.0 / Math.sqrt(_snowmanxxxx);
         _snowmanxx *= _snowmanxxxxx;
         _snowmanxxx *= _snowmanxxxxx;
         _snowman += 2;
         _snowman += 2;
         if (!this.a(_snowman, afm.c(_snowman.c), _snowmanx, _snowman, _snowman, _snowman, _snowman, _snowmanxx, _snowmanxxx)) {
            return false;
         } else {
            _snowman -= 2;
            _snowman -= 2;
            double _snowmanxxxxxx = 1.0 / Math.abs(_snowmanxx);
            double _snowmanxxxxxxx = 1.0 / Math.abs(_snowmanxxx);
            double _snowmanxxxxxxxx = (double)_snowman - _snowman.b;
            double _snowmanxxxxxxxxx = (double)_snowmanx - _snowman.d;
            if (_snowmanxx >= 0.0) {
               _snowmanxxxxxxxx++;
            }

            if (_snowmanxxx >= 0.0) {
               _snowmanxxxxxxxxx++;
            }

            _snowmanxxxxxxxx /= _snowmanxx;
            _snowmanxxxxxxxxx /= _snowmanxxx;
            int _snowmanxxxxxxxxxx = _snowmanxx < 0.0 ? -1 : 1;
            int _snowmanxxxxxxxxxxx = _snowmanxxx < 0.0 ? -1 : 1;
            int _snowmanxxxxxxxxxxxx = afm.c(_snowman.b);
            int _snowmanxxxxxxxxxxxxx = afm.c(_snowman.d);
            int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx - _snowman;
            int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx - _snowmanx;

            while (_snowmanxxxxxxxxxxxxxx * _snowmanxxxxxxxxxx > 0 || _snowmanxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxx > 0) {
               if (_snowmanxxxxxxxx < _snowmanxxxxxxxxx) {
                  _snowmanxxxxxxxx += _snowmanxxxxxx;
                  _snowman += _snowmanxxxxxxxxxx;
                  _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx - _snowman;
               } else {
                  _snowmanxxxxxxxxx += _snowmanxxxxxxx;
                  _snowmanx += _snowmanxxxxxxxxxxx;
                  _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx - _snowmanx;
               }

               if (!this.a(_snowman, afm.c(_snowman.c), _snowmanx, _snowman, _snowman, _snowman, _snowman, _snowmanxx, _snowmanxxx)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private boolean a(int var1, int var2, int var3, int var4, int var5, int var6, dcn var7, double var8, double var10) {
      int _snowman = _snowman - _snowman / 2;
      int _snowmanx = _snowman - _snowman / 2;
      if (!this.b(_snowman, _snowman, _snowmanx, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman)) {
         return false;
      } else {
         for (int _snowmanxx = _snowman; _snowmanxx < _snowman + _snowman; _snowmanxx++) {
            for (int _snowmanxxx = _snowmanx; _snowmanxxx < _snowmanx + _snowman; _snowmanxxx++) {
               double _snowmanxxxx = (double)_snowmanxx + 0.5 - _snowman.b;
               double _snowmanxxxxx = (double)_snowmanxxx + 0.5 - _snowman.d;
               if (!(_snowmanxxxx * _snowman + _snowmanxxxxx * _snowman < 0.0)) {
                  cwz _snowmanxxxxxx = this.o.a(this.b, _snowmanxx, _snowman - 1, _snowmanxxx, this.a, _snowman, _snowman, _snowman, true, true);
                  if (!this.a(_snowmanxxxxxx)) {
                     return false;
                  }

                  _snowmanxxxxxx = this.o.a(this.b, _snowmanxx, _snowman, _snowmanxxx, this.a, _snowman, _snowman, _snowman, true, true);
                  float _snowmanxxxxxxx = this.a.a(_snowmanxxxxxx);
                  if (_snowmanxxxxxxx < 0.0F || _snowmanxxxxxxx >= 8.0F) {
                     return false;
                  }

                  if (_snowmanxxxxxx == cwz.m || _snowmanxxxxxx == cwz.l || _snowmanxxxxxx == cwz.q) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   }

   protected boolean a(cwz var1) {
      if (_snowman == cwz.h) {
         return false;
      } else {
         return _snowman == cwz.g ? false : _snowman != cwz.b;
      }
   }

   private boolean b(int var1, int var2, int var3, int var4, int var5, int var6, dcn var7, double var8, double var10) {
      for (fx _snowman : fx.a(new fx(_snowman, _snowman, _snowman), new fx(_snowman + _snowman - 1, _snowman + _snowman - 1, _snowman + _snowman - 1))) {
         double _snowmanx = (double)_snowman.u() + 0.5 - _snowman.b;
         double _snowmanxx = (double)_snowman.w() + 0.5 - _snowman.d;
         if (!(_snowmanx * _snowman + _snowmanxx * _snowman < 0.0) && !this.b.d_(_snowman).a(this.b, _snowman, cxe.a)) {
            return false;
         }
      }

      return true;
   }

   public void a(boolean var1) {
      this.o.b(_snowman);
   }

   public boolean f() {
      return this.o.c();
   }

   public void c(boolean var1) {
      this.p = _snowman;
   }
}
