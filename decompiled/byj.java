import java.util.Random;

public class byj extends buo {
   public static final cfe<gc.a> a = cex.E;
   protected static final ddh b = buo.a(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
   protected static final ddh c = buo.a(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);

   public byj(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, gc.a.a));
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      switch ((gc.a)_snowman.c(a)) {
         case c:
            return c;
         case a:
         default:
            return b;
      }
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      if (_snowman.k().e() && _snowman.V().b(brt.d) && _snowman.nextInt(2000) < _snowman.ad().a()) {
         while (_snowman.d_(_snowman).a(this)) {
            _snowman = _snowman.c();
         }

         if (_snowman.d_(_snowman).a(_snowman, _snowman, aqe.bb)) {
            aqa _snowman = aqe.bb.a(_snowman, null, null, null, _snowman.b(), aqp.d, false, false);
            if (_snowman != null) {
               _snowman.ah();
            }
         }
      }
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      gc.a _snowman = _snowman.n();
      gc.a _snowmanx = _snowman.c(a);
      boolean _snowmanxx = _snowmanx != _snowman && _snowman.d();
      return !_snowmanxx && !_snowman.a(this) && !new cxn(_snowman, _snowman, _snowmanx).c() ? bup.a.n() : super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, aqa var4) {
      if (!_snowman.br() && !_snowman.bs() && _snowman.bO()) {
         _snowman.d(_snowman);
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      if (_snowman.nextInt(100) == 0) {
         _snowman.a((double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5, adq.lN, adr.e, 0.5F, _snowman.nextFloat() * 0.4F + 0.8F, false);
      }

      for (int _snowman = 0; _snowman < 4; _snowman++) {
         double _snowmanx = (double)_snowman.u() + _snowman.nextDouble();
         double _snowmanxx = (double)_snowman.v() + _snowman.nextDouble();
         double _snowmanxxx = (double)_snowman.w() + _snowman.nextDouble();
         double _snowmanxxxx = ((double)_snowman.nextFloat() - 0.5) * 0.5;
         double _snowmanxxxxx = ((double)_snowman.nextFloat() - 0.5) * 0.5;
         double _snowmanxxxxxx = ((double)_snowman.nextFloat() - 0.5) * 0.5;
         int _snowmanxxxxxxx = _snowman.nextInt(2) * 2 - 1;
         if (!_snowman.d_(_snowman.f()).a(this) && !_snowman.d_(_snowman.g()).a(this)) {
            _snowmanx = (double)_snowman.u() + 0.5 + 0.25 * (double)_snowmanxxxxxxx;
            _snowmanxxxx = (double)(_snowman.nextFloat() * 2.0F * (float)_snowmanxxxxxxx);
         } else {
            _snowmanxxx = (double)_snowman.w() + 0.5 + 0.25 * (double)_snowmanxxxxxxx;
            _snowmanxxxxxx = (double)(_snowman.nextFloat() * 2.0F * (float)_snowmanxxxxxxx);
         }

         _snowman.a(hh.Q, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
      }
   }

   @Override
   public bmb a(brc var1, fx var2, ceh var3) {
      return bmb.b;
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      switch (_snowman) {
         case d:
         case b:
            switch ((gc.a)_snowman.c(a)) {
               case c:
                  return _snowman.a(a, gc.a.a);
               case a:
                  return _snowman.a(a, gc.a.c);
               default:
                  return _snowman;
            }
         default:
            return _snowman;
      }
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }
}
