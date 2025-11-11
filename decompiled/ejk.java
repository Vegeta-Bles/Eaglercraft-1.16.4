public class ejk extends efr<dzj, dvd<dzj>> {
   public ejk(eet var1) {
      this(_snowman, false);
   }

   public ejk(eet var1, boolean var2) {
      super(_snowman, new dvd<>(0.0F, _snowman), 0.5F);
      this.a(new eik<>(this, new dum(0.5F), new dum(1.0F)));
      this.a(new ein<>(this));
      this.a(new ehs<>(this));
      this.a(new eia(this));
      this.a(new ehu(this));
      this.a(new ehz<>(this));
      this.a(new eid<>(this));
      this.a(new eir<>(this));
      this.a(new eja<>(this));
      this.a(new eht<>(this));
   }

   public void a(dzj var1, float var2, float var3, dfm var4, eag var5, int var6) {
      this.b(_snowman);
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public dcn a(dzj var1, float var2) {
      return _snowman.bz() ? new dcn(0.0, -0.125, 0.0) : super.a(_snowman, _snowman);
   }

   private void b(dzj var1) {
      dvd<dzj> _snowman = this.c();
      if (_snowman.a_()) {
         _snowman.d_(false);
         _snowman.f.h = true;
         _snowman.g.h = true;
      } else {
         _snowman.d_(true);
         _snowman.g.h = _snowman.a(bfx.g);
         _snowman.x.h = _snowman.a(bfx.b);
         _snowman.v.h = _snowman.a(bfx.e);
         _snowman.w.h = _snowman.a(bfx.f);
         _snowman.t.h = _snowman.a(bfx.c);
         _snowman.u.h = _snowman.a(bfx.d);
         _snowman.o = _snowman.bz();
         dum.a _snowmanx = a(_snowman, aot.a);
         dum.a _snowmanxx = a(_snowman, aot.b);
         if (_snowmanx.a()) {
            _snowmanxx = _snowman.dE().a() ? dum.a.a : dum.a.b;
         }

         if (_snowman.dV() == aqi.b) {
            _snowman.n = _snowmanx;
            _snowman.m = _snowmanxx;
         } else {
            _snowman.n = _snowmanxx;
            _snowman.m = _snowmanx;
         }
      }
   }

   private static dum.a a(dzj var0, aot var1) {
      bmb _snowman = _snowman.b(_snowman);
      if (_snowman.a()) {
         return dum.a.a;
      } else {
         if (_snowman.dX() == _snowman && _snowman.dZ() > 0) {
            bnn _snowmanx = _snowman.l();
            if (_snowmanx == bnn.d) {
               return dum.a.c;
            }

            if (_snowmanx == bnn.e) {
               return dum.a.d;
            }

            if (_snowmanx == bnn.f) {
               return dum.a.e;
            }

            if (_snowmanx == bnn.g && _snowman == _snowman.dX()) {
               return dum.a.f;
            }
         } else if (!_snowman.ai && _snowman.b() == bmd.qQ && bkt.d(_snowman)) {
            return dum.a.g;
         }

         return dum.a.b;
      }
   }

   public vk a(dzj var1) {
      return _snowman.o();
   }

   protected void a(dzj var1, dfm var2, float var3) {
      float _snowman = 0.9375F;
      _snowman.a(0.9375F, 0.9375F, 0.9375F);
   }

   protected void a(dzj var1, nr var2, dfm var3, eag var4, int var5) {
      double _snowman = this.b.b(_snowman);
      _snowman.a();
      if (_snowman < 100.0) {
         ddn _snowmanx = _snowman.eN();
         ddk _snowmanxx = _snowmanx.a(2);
         if (_snowmanxx != null) {
            ddm _snowmanxxx = _snowmanx.c(_snowman.bU(), _snowmanxx);
            super.a(_snowman, new oe(Integer.toString(_snowmanxxx.b())).c(" ").a(_snowmanxx.d()), _snowman, _snowman, _snowman);
            _snowman.a(0.0, (double)(9.0F * 1.15F * 0.025F), 0.0);
         }
      }

      super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      _snowman.b();
   }

   public void a(dfm var1, eag var2, int var3, dzj var4) {
      this.a(_snowman, _snowman, _snowman, _snowman, this.e.i, this.e.u);
   }

   public void b(dfm var1, eag var2, int var3, dzj var4) {
      this.a(_snowman, _snowman, _snowman, _snowman, this.e.j, this.e.t);
   }

   private void a(dfm var1, eag var2, int var3, dzj var4, dwn var5, dwn var6) {
      dvd<dzj> _snowman = this.c();
      this.b(_snowman);
      _snowman.c = 0.0F;
      _snowman.o = false;
      _snowman.p = 0.0F;
      _snowman.a(_snowman, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      _snowman.d = 0.0F;
      _snowman.a(_snowman, _snowman.getBuffer(eao.b(_snowman.o())), _snowman, ejw.a);
      _snowman.d = 0.0F;
      _snowman.a(_snowman, _snowman.getBuffer(eao.h(_snowman.o())), _snowman, ejw.a);
   }

   protected void a(dzj var1, dfm var2, float var3, float var4, float var5) {
      float _snowman = _snowman.a(_snowman);
      if (_snowman.ef()) {
         super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
         float _snowmanx = (float)_snowman.eg() + _snowman;
         float _snowmanxx = afm.a(_snowmanx * _snowmanx / 100.0F, 0.0F, 1.0F);
         if (!_snowman.dR()) {
            _snowman.a(g.b.a(_snowmanxx * (-90.0F - _snowman.q)));
         }

         dcn _snowmanxxx = _snowman.f(_snowman);
         dcn _snowmanxxxx = _snowman.cC();
         double _snowmanxxxxx = aqa.c(_snowmanxxxx);
         double _snowmanxxxxxx = aqa.c(_snowmanxxx);
         if (_snowmanxxxxx > 0.0 && _snowmanxxxxxx > 0.0) {
            double _snowmanxxxxxxx = (_snowmanxxxx.b * _snowmanxxx.b + _snowmanxxxx.d * _snowmanxxx.d) / Math.sqrt(_snowmanxxxxx * _snowmanxxxxxx);
            double _snowmanxxxxxxxx = _snowmanxxxx.b * _snowmanxxx.d - _snowmanxxxx.d * _snowmanxxx.b;
            _snowman.a(g.d.c((float)(Math.signum(_snowmanxxxxxxxx) * Math.acos(_snowmanxxxxxxx))));
         }
      } else if (_snowman > 0.0F) {
         super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
         float _snowmanxxx = _snowman.aE() ? -90.0F - _snowman.q : -90.0F;
         float _snowmanxxxx = afm.g(_snowman, 0.0F, _snowmanxxx);
         _snowman.a(g.b.a(_snowmanxxxx));
         if (_snowman.bC()) {
            _snowman.a(0.0, -1.0, 0.3F);
         }
      } else {
         super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }
}
