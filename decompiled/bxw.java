import javax.annotation.Nullable;

public class bxw extends buo implements bzu {
   public static final cey a = cex.j;
   public static final cey b = cex.C;
   protected static final ddh c = dde.a(buo.a(5.0, 0.0, 5.0, 11.0, 7.0, 11.0), buo.a(6.0, 7.0, 6.0, 10.0, 9.0, 10.0));
   protected static final ddh d = dde.a(buo.a(5.0, 1.0, 5.0, 11.0, 8.0, 11.0), buo.a(6.0, 8.0, 6.0, 10.0, 10.0, 10.0));

   public bxw(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, Boolean.valueOf(false)).a(b, Boolean.valueOf(false)));
   }

   @Nullable
   @Override
   public ceh a(bny var1) {
      cux _snowman = _snowman.p().b(_snowman.a());

      for (gc _snowmanx : _snowman.e()) {
         if (_snowmanx.n() == gc.a.b) {
            ceh _snowmanxx = this.n().a(a, Boolean.valueOf(_snowmanx == gc.b));
            if (_snowmanxx.a((brz)_snowman.p(), _snowman.a())) {
               return _snowmanxx.a(b, Boolean.valueOf(_snowman.a() == cuy.c));
            }
         }
      }

      return null;
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return _snowman.c(a) ? d : c;
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a, b);
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      gc _snowman = h(_snowman).f();
      return buo.a(_snowman, _snowman.a(_snowman), _snowman.f());
   }

   protected static gc h(ceh var0) {
      return _snowman.c(a) ? gc.a : gc.b;
   }

   @Override
   public cvc f(ceh var1) {
      return cvc.b;
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman.c(b)) {
         _snowman.I().a(_snowman, cuy.c, cuy.c.a(_snowman));
      }

      return h(_snowman).f() == _snowman && !_snowman.a(_snowman, _snowman) ? bup.a.n() : super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public cux d(ceh var1) {
      return _snowman.c(b) ? cuy.c.a(false) : super.d(_snowman);
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }
}
