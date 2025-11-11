public class bxk extends bxi {
   protected static final ddh a = buo.a(1.0, 0.0, 1.0, 15.0, 15.0, 15.0);

   public bxk(ceg.c var1) {
      super(_snowman);
   }

   private static boolean c(aqa var0) {
      return _snowman instanceof aqm || _snowman instanceof bhl || _snowman instanceof bcw || _snowman instanceof bhn;
   }

   @Override
   public ddh c(ceh var1, brc var2, fx var3, dcs var4) {
      return a;
   }

   @Override
   public void a(brx var1, fx var2, aqa var3, float var4) {
      _snowman.a(adq.fN, 1.0F, 1.0F);
      if (!_snowman.v) {
         _snowman.a(_snowman, (byte)54);
      }

      if (_snowman.b(_snowman, 0.2F)) {
         _snowman.a(this.aw.g(), this.aw.a() * 0.5F, this.aw.b() * 0.75F);
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, aqa var4) {
      if (this.a(_snowman, _snowman)) {
         this.a(_snowman, _snowman);
         this.d(_snowman);
         this.a(_snowman, _snowman);
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   private boolean a(fx var1, aqa var2) {
      if (_snowman.ao()) {
         return false;
      } else if (_snowman.cE() > (double)_snowman.v() + 0.9375 - 1.0E-7) {
         return false;
      } else if (_snowman.cC().c >= -0.08) {
         return false;
      } else {
         double _snowman = Math.abs((double)_snowman.u() + 0.5 - _snowman.cD());
         double _snowmanx = Math.abs((double)_snowman.w() + 0.5 - _snowman.cH());
         double _snowmanxx = 0.4375 + (double)(_snowman.cy() / 2.0F);
         return _snowman + 1.0E-7 > _snowmanxx || _snowmanx + 1.0E-7 > _snowmanxx;
      }
   }

   private void a(aqa var1, fx var2) {
      if (_snowman instanceof aah && _snowman.l.T() % 20L == 0L) {
         ac.J.a((aah)_snowman, _snowman.l.d_(_snowman));
      }
   }

   private void d(aqa var1) {
      dcn _snowman = _snowman.cC();
      if (_snowman.c < -0.13) {
         double _snowmanx = -0.05 / _snowman.c;
         _snowman.f(new dcn(_snowman.b * _snowmanx, -0.05, _snowman.d * _snowmanx));
      } else {
         _snowman.f(new dcn(_snowman.b, -0.05, _snowman.d));
      }

      _snowman.C = 0.0F;
   }

   private void a(brx var1, aqa var2) {
      if (c(_snowman)) {
         if (_snowman.t.nextInt(5) == 0) {
            _snowman.a(adq.fN, 1.0F, 1.0F);
         }

         if (!_snowman.v && _snowman.t.nextInt(5) == 0) {
            _snowman.a(_snowman, (byte)53);
         }
      }
   }

   public static void a(aqa var0) {
      a(_snowman, 5);
   }

   public static void b(aqa var0) {
      a(_snowman, 10);
   }

   private static void a(aqa var0, int var1) {
      if (_snowman.l.v) {
         ceh _snowman = bup.ne.n();

         for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
            _snowman.l.a(new hc(hh.d, _snowman), _snowman.cD(), _snowman.cE(), _snowman.cH(), 0.0, 0.0, 0.0);
         }
      }
   }
}
