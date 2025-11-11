import java.util.Random;

public class bzc extends buo {
   public static final cey a = bzf.a;

   public bzc(ceg.c var1) {
      super(_snowman);
      this.j(this.n().a(a, Boolean.valueOf(false)));
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, bfw var4) {
      d(_snowman, _snowman, _snowman);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void a(brx var1, fx var2, aqa var3) {
      d(_snowman.d_(_snowman), _snowman, _snowman);
      super.a(_snowman, _snowman, _snowman);
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      if (_snowman.v) {
         a(_snowman, _snowman);
      } else {
         d(_snowman, _snowman, _snowman);
      }

      bmb _snowman = _snowman.b(_snowman);
      return _snowman.b() instanceof bkh && new bny(_snowman, _snowman, _snowman, _snowman).b() ? aou.c : aou.a;
   }

   private static void d(ceh var0, brx var1, fx var2) {
      a(_snowman, _snowman);
      if (!_snowman.c(a)) {
         _snowman.a(_snowman, _snowman.a(a, Boolean.valueOf(true)), 3);
      }
   }

   @Override
   public boolean a_(ceh var1) {
      return _snowman.c(a);
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      if (_snowman.c(a)) {
         _snowman.a(_snowman, _snowman.a(a, Boolean.valueOf(false)), 3);
      }
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, bmb var4) {
      super.a(_snowman, _snowman, _snowman, _snowman);
      if (bpu.a(bpw.u, _snowman) == 0) {
         int _snowman = 1 + _snowman.t.nextInt(5);
         this.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      if (_snowman.c(a)) {
         a(_snowman, _snowman);
      }
   }

   private static void a(brx var0, fx var1) {
      double _snowman = 0.5625;
      Random _snowmanx = _snowman.t;

      for (gc _snowmanxx : gc.values()) {
         fx _snowmanxxx = _snowman.a(_snowmanxx);
         if (!_snowman.d_(_snowmanxxx).i(_snowman, _snowmanxxx)) {
            gc.a _snowmanxxxx = _snowmanxx.n();
            double _snowmanxxxxx = _snowmanxxxx == gc.a.a ? 0.5 + 0.5625 * (double)_snowmanxx.i() : (double)_snowmanx.nextFloat();
            double _snowmanxxxxxx = _snowmanxxxx == gc.a.b ? 0.5 + 0.5625 * (double)_snowmanxx.j() : (double)_snowmanx.nextFloat();
            double _snowmanxxxxxxx = _snowmanxxxx == gc.a.c ? 0.5 + 0.5625 * (double)_snowmanxx.k() : (double)_snowmanx.nextFloat();
            _snowman.a(hd.a, (double)_snowman.u() + _snowmanxxxxx, (double)_snowman.v() + _snowmanxxxxxx, (double)_snowman.w() + _snowmanxxxxxxx, 0.0, 0.0, 0.0);
         }
      }
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }
}
