import java.util.Random;

public class bwl extends btn<ccv> implements bzu {
   public static final cfb b = bxm.aq;
   public static final cey c = cex.C;
   protected static final ddh d = buo.a(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);
   private static final nr e = new of("container.enderchest");

   protected bwl(ceg.c var1) {
      super(_snowman, () -> cck.d);
      this.j(this.n.b().a(b, gc.c).a(c, Boolean.valueOf(false)));
   }

   @Override
   public bwc.c<? extends ccn> a(ceh var1, brx var2, fx var3, boolean var4) {
      return bwc.b::b;
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return d;
   }

   @Override
   public bzh b(ceh var1) {
      return bzh.b;
   }

   @Override
   public ceh a(bny var1) {
      cux _snowman = _snowman.p().b(_snowman.a());
      return this.n().a(b, _snowman.f().f()).a(c, Boolean.valueOf(_snowman.a() == cuy.c));
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      bji _snowman = _snowman.eL();
      ccj _snowmanx = _snowman.c(_snowman);
      if (_snowman != null && _snowmanx instanceof ccv) {
         fx _snowmanxx = _snowman.b();
         if (_snowman.d_(_snowmanxx).g(_snowman, _snowmanxx)) {
            return aou.a(_snowman.v);
         } else if (_snowman.v) {
            return aou.a;
         } else {
            ccv _snowmanxxx = (ccv)_snowmanx;
            _snowman.a(_snowmanxxx);
            _snowman.a(new apb((var1x, var2x, var3x) -> bij.a(var1x, var2x, _snowman), e));
            _snowman.a(aea.ai);
            bet.a(_snowman, true);
            return aou.b;
         }
      } else {
         return aou.a(_snowman.v);
      }
   }

   @Override
   public ccj a(brc var1) {
      return new ccv();
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      for (int _snowman = 0; _snowman < 3; _snowman++) {
         int _snowmanx = _snowman.nextInt(2) * 2 - 1;
         int _snowmanxx = _snowman.nextInt(2) * 2 - 1;
         double _snowmanxxx = (double)_snowman.u() + 0.5 + 0.25 * (double)_snowmanx;
         double _snowmanxxxx = (double)((float)_snowman.v() + _snowman.nextFloat());
         double _snowmanxxxxx = (double)_snowman.w() + 0.5 + 0.25 * (double)_snowmanxx;
         double _snowmanxxxxxx = (double)(_snowman.nextFloat() * (float)_snowmanx);
         double _snowmanxxxxxxx = ((double)_snowman.nextFloat() - 0.5) * 0.125;
         double _snowmanxxxxxxxx = (double)(_snowman.nextFloat() * (float)_snowmanxx);
         _snowman.a(hh.Q, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx);
      }
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      return _snowman.a(b, _snowman.a(_snowman.c(b)));
   }

   @Override
   public ceh a(ceh var1, byg var2) {
      return _snowman.a(_snowman.a(_snowman.c(b)));
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(b, c);
   }

   @Override
   public cux d(ceh var1) {
      return _snowman.c(c) ? cuy.c.a(false) : super.d(_snowman);
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman.c(c)) {
         _snowman.I().a(_snowman, cuy.c, cuy.c.a(_snowman));
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }
}
