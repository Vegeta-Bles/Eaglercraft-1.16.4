import java.util.Random;

public class bzi extends bvy {
   public static final cey a = cex.s;
   public static final cfg d = cex.am;

   protected bzi(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(aq, gc.c).a(d, Integer.valueOf(1)).a(a, Boolean.valueOf(false)).a(c, Boolean.valueOf(false)));
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      if (!_snowman.bC.e) {
         return aou.c;
      } else {
         _snowman.a(_snowman, _snowman.a(d), 3);
         return aou.a(_snowman.v);
      }
   }

   @Override
   protected int g(ceh var1) {
      return _snowman.c(d) * 2;
   }

   @Override
   public ceh a(bny var1) {
      ceh _snowman = super.a(_snowman);
      return _snowman.a(a, Boolean.valueOf(this.a(_snowman.p(), _snowman.a(), _snowman)));
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      return !_snowman.s_() && _snowman.n() != _snowman.c(aq).n() ? _snowman.a(a, Boolean.valueOf(this.a(_snowman, _snowman, _snowman))) : super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(brz var1, fx var2, ceh var3) {
      return this.b(_snowman, _snowman, _snowman) > 0;
   }

   @Override
   protected boolean h(ceh var1) {
      return l(_snowman);
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      if (_snowman.c(c)) {
         gc _snowman = _snowman.c(aq);
         double _snowmanx = (double)_snowman.u() + 0.5 + (_snowman.nextDouble() - 0.5) * 0.2;
         double _snowmanxx = (double)_snowman.v() + 0.4 + (_snowman.nextDouble() - 0.5) * 0.2;
         double _snowmanxxx = (double)_snowman.w() + 0.5 + (_snowman.nextDouble() - 0.5) * 0.2;
         float _snowmanxxxx = -5.0F;
         if (_snowman.nextBoolean()) {
            _snowmanxxxx = (float)(_snowman.c(d) * 2 - 1);
         }

         _snowmanxxxx /= 16.0F;
         double _snowmanxxxxx = (double)(_snowmanxxxx * (float)_snowman.i());
         double _snowmanxxxxxx = (double)(_snowmanxxxx * (float)_snowman.k());
         _snowman.a(hd.a, _snowmanx + _snowmanxxxxx, _snowmanxx, _snowmanxxx + _snowmanxxxxxx, 0.0, 0.0, 0.0);
      }
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(aq, d, a, c);
   }
}
