public class bxq extends bvt {
   protected bxq(ceg.c var1) {
      super(1.0F, 1.0F, 16.0F, 16.0F, 16.0F, _snowman);
      this.j(
         this.n
            .b()
            .a(a, Boolean.valueOf(false))
            .a(b, Boolean.valueOf(false))
            .a(c, Boolean.valueOf(false))
            .a(d, Boolean.valueOf(false))
            .a(e, Boolean.valueOf(false))
      );
   }

   @Override
   public ceh a(bny var1) {
      brc _snowman = _snowman.p();
      fx _snowmanx = _snowman.a();
      cux _snowmanxx = _snowman.p().b(_snowman.a());
      fx _snowmanxxx = _snowmanx.d();
      fx _snowmanxxxx = _snowmanx.e();
      fx _snowmanxxxxx = _snowmanx.f();
      fx _snowmanxxxxxx = _snowmanx.g();
      ceh _snowmanxxxxxxx = _snowman.d_(_snowmanxxx);
      ceh _snowmanxxxxxxxx = _snowman.d_(_snowmanxxxx);
      ceh _snowmanxxxxxxxxx = _snowman.d_(_snowmanxxxxx);
      ceh _snowmanxxxxxxxxxx = _snowman.d_(_snowmanxxxxxx);
      return this.n()
         .a(a, Boolean.valueOf(this.a(_snowmanxxxxxxx, _snowmanxxxxxxx.d(_snowman, _snowmanxxx, gc.d))))
         .a(c, Boolean.valueOf(this.a(_snowmanxxxxxxxx, _snowmanxxxxxxxx.d(_snowman, _snowmanxxxx, gc.c))))
         .a(d, Boolean.valueOf(this.a(_snowmanxxxxxxxxx, _snowmanxxxxxxxxx.d(_snowman, _snowmanxxxxx, gc.f))))
         .a(b, Boolean.valueOf(this.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxx.d(_snowman, _snowmanxxxxxx, gc.e))))
         .a(e, Boolean.valueOf(_snowmanxx.a() == cuy.c));
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman.c(e)) {
         _snowman.I().a(_snowman, cuy.c, cuy.c.a(_snowman));
      }

      return _snowman.n().d() ? _snowman.a(f.get(_snowman), Boolean.valueOf(this.a(_snowman, _snowman.d(_snowman, _snowman, _snowman.f())))) : super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public ddh a(ceh var1, brc var2, fx var3, dcs var4) {
      return dde.a();
   }

   @Override
   public boolean a(ceh var1, ceh var2, gc var3) {
      if (_snowman.a(this)) {
         if (!_snowman.n().d()) {
            return true;
         }

         if (_snowman.c(f.get(_snowman)) && _snowman.c(f.get(_snowman.f()))) {
            return true;
         }
      }

      return super.a(_snowman, _snowman, _snowman);
   }

   public final boolean a(ceh var1, boolean var2) {
      buo _snowman = _snowman.b();
      return !b(_snowman) && _snowman || _snowman instanceof bxq || _snowman.a(aed.F);
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a, b, d, c, e);
   }
}
