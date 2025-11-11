import java.util.Random;

public class bwp extends buo {
   public static final cfg a = cex.aw;
   protected static final ddh b = buo.a(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);

   protected bwp(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, Integer.valueOf(0)));
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman == gc.b && !_snowman.a(_snowman, _snowman)) {
         _snowman.J().a(_snowman, this, 1);
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      ceh _snowman = _snowman.d_(_snowman.b());
      return !_snowman.c().b() || _snowman.b() instanceof bwr || _snowman.b() instanceof cdz;
   }

   @Override
   public ceh a(bny var1) {
      return !this.n().a((brz)_snowman.p(), _snowman.a()) ? bup.j.n() : super.a(_snowman);
   }

   @Override
   public boolean c_(ceh var1) {
      return true;
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return b;
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      if (!_snowman.a(_snowman, _snowman)) {
         d(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      int _snowman = _snowman.c(a);
      if (!a((brz)_snowman, _snowman) && !_snowman.t(_snowman.b())) {
         if (_snowman > 0) {
            _snowman.a(_snowman, _snowman.a(a, Integer.valueOf(_snowman - 1)), 2);
         } else if (!a((brc)_snowman, _snowman)) {
            d(_snowman, _snowman, _snowman);
         }
      } else if (_snowman < 7) {
         _snowman.a(_snowman, _snowman.a(a, Integer.valueOf(7)), 2);
      }
   }

   @Override
   public void a(brx var1, fx var2, aqa var3, float var4) {
      if (!_snowman.v && _snowman.t.nextFloat() < _snowman - 0.5F && _snowman instanceof aqm && (_snowman instanceof bfw || _snowman.V().b(brt.b)) && _snowman.cy() * _snowman.cy() * _snowman.cz() > 0.512F) {
         d(_snowman.d_(_snowman), _snowman, _snowman);
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   public static void d(ceh var0, brx var1, fx var2) {
      _snowman.a(_snowman, a(_snowman, bup.j.n(), _snowman, _snowman));
   }

   private static boolean a(brc var0, fx var1) {
      buo _snowman = _snowman.d_(_snowman.b()).b();
      return _snowman instanceof bvs || _snowman instanceof cam || _snowman instanceof btt;
   }

   private static boolean a(brz var0, fx var1) {
      for (fx _snowman : fx.a(_snowman.b(-4, 0, -4), _snowman.b(4, 1, 4))) {
         if (_snowman.b(_snowman).a(aef.b)) {
            return true;
         }
      }

      return false;
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }
}
