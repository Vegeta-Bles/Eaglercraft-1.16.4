import java.util.Random;

public class cbu extends bwu {
   public cbu(aps var1, ceg.c var2) {
      super(_snowman, 8, _snowman);
   }

   @Override
   protected boolean c(ceh var1, brc var2, fx var3) {
      return super.c(_snowman, _snowman, _snowman) || _snowman.a(bup.cL) || _snowman.a(bup.cM) || _snowman.a(bup.cN);
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      ddh _snowman = this.b(_snowman, _snowman, _snowman, dcs.a());
      dcn _snowmanx = _snowman.a().f();
      double _snowmanxx = (double)_snowman.u() + _snowmanx.b;
      double _snowmanxxx = (double)_snowman.w() + _snowmanx.d;

      for (int _snowmanxxxx = 0; _snowmanxxxx < 3; _snowmanxxxx++) {
         if (_snowman.nextBoolean()) {
            _snowman.a(hh.S, _snowmanxx + _snowman.nextDouble() / 5.0, (double)_snowman.v() + (0.5 - _snowman.nextDouble()), _snowmanxxx + _snowman.nextDouble() / 5.0, 0.0, 0.0, 0.0);
         }
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, aqa var4) {
      if (!_snowman.v && _snowman.ad() != aor.a) {
         if (_snowman instanceof aqm) {
            aqm _snowman = (aqm)_snowman;
            if (!_snowman.b(apk.p)) {
               _snowman.c(new apu(apw.t, 40));
            }
         }
      }
   }
}
