import javax.annotation.Nullable;

public class bxv extends buo implements bzu {
   public static final cfb a = bxm.aq;
   public static final cey b = cex.C;
   protected static final ddh c = buo.a(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);
   protected static final ddh d = buo.a(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final ddh e = buo.a(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
   protected static final ddh f = buo.a(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);

   protected bxv(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, gc.c).a(b, Boolean.valueOf(false)));
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      switch ((gc)_snowman.c(a)) {
         case c:
            return f;
         case d:
            return e;
         case e:
            return d;
         case f:
         default:
            return c;
      }
   }

   private boolean a(brc var1, fx var2, gc var3) {
      ceh _snowman = _snowman.d_(_snowman);
      return _snowman.d(_snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      gc _snowman = _snowman.c(a);
      return this.a(_snowman, _snowman.a(_snowman.f()), _snowman);
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman.f() == _snowman.c(a) && !_snowman.a(_snowman, _snowman)) {
         return bup.a.n();
      } else {
         if (_snowman.c(b)) {
            _snowman.I().a(_snowman, cuy.c, cuy.c.a(_snowman));
         }

         return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   @Nullable
   @Override
   public ceh a(bny var1) {
      if (!_snowman.c()) {
         ceh _snowman = _snowman.p().d_(_snowman.a().a(_snowman.j().f()));
         if (_snowman.a(this) && _snowman.c(a) == _snowman.j()) {
            return null;
         }
      }

      ceh _snowman = this.n();
      brz _snowmanx = _snowman.p();
      fx _snowmanxx = _snowman.a();
      cux _snowmanxxx = _snowman.p().b(_snowman.a());

      for (gc _snowmanxxxx : _snowman.e()) {
         if (_snowmanxxxx.n().d()) {
            _snowman = _snowman.a(a, _snowmanxxxx.f());
            if (_snowman.a(_snowmanx, _snowmanxx)) {
               return _snowman.a(b, Boolean.valueOf(_snowmanxxx.a() == cuy.c));
            }
         }
      }

      return null;
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      return _snowman.a(a, _snowman.a(_snowman.c(a)));
   }

   @Override
   public ceh a(ceh var1, byg var2) {
      return _snowman.a(_snowman.a(_snowman.c(a)));
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a, b);
   }

   @Override
   public cux d(ceh var1) {
      return _snowman.c(b) ? cuy.c.a(false) : super.d(_snowman);
   }
}
