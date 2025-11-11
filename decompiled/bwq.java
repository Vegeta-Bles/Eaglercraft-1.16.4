public class bwq extends bvt {
   private final ddh[] i;

   public bwq(ceg.c var1) {
      super(2.0F, 2.0F, 16.0F, 16.0F, 24.0F, _snowman);
      this.j(
         this.n
            .b()
            .a(a, Boolean.valueOf(false))
            .a(b, Boolean.valueOf(false))
            .a(c, Boolean.valueOf(false))
            .a(d, Boolean.valueOf(false))
            .a(e, Boolean.valueOf(false))
      );
      this.i = this.a(2.0F, 1.0F, 16.0F, 6.0F, 15.0F);
   }

   @Override
   public ddh d(ceh var1, brc var2, fx var3) {
      return this.i[this.g(_snowman)];
   }

   @Override
   public ddh a(ceh var1, brc var2, fx var3, dcs var4) {
      return this.b(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }

   public boolean a(ceh var1, boolean var2, gc var3) {
      buo _snowman = _snowman.b();
      boolean _snowmanx = this.c(_snowman);
      boolean _snowmanxx = _snowman instanceof bwr && bwr.a(_snowman, _snowman);
      return !b(_snowman) && _snowman || _snowmanx || _snowmanxx;
   }

   private boolean c(buo var1) {
      return _snowman.a(aed.M) && _snowman.a(aed.k) == this.n().a(aed.k);
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      if (_snowman.v) {
         bmb _snowman = _snowman.b(_snowman);
         return _snowman.b() == bmd.pH ? aou.a : aou.c;
      } else {
         return bmf.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public ceh a(bny var1) {
      brc _snowman = _snowman.p();
      fx _snowmanx = _snowman.a();
      cux _snowmanxx = _snowman.p().b(_snowman.a());
      fx _snowmanxxx = _snowmanx.d();
      fx _snowmanxxxx = _snowmanx.g();
      fx _snowmanxxxxx = _snowmanx.e();
      fx _snowmanxxxxxx = _snowmanx.f();
      ceh _snowmanxxxxxxx = _snowman.d_(_snowmanxxx);
      ceh _snowmanxxxxxxxx = _snowman.d_(_snowmanxxxx);
      ceh _snowmanxxxxxxxxx = _snowman.d_(_snowmanxxxxx);
      ceh _snowmanxxxxxxxxxx = _snowman.d_(_snowmanxxxxxx);
      return super.a(_snowman)
         .a(a, Boolean.valueOf(this.a(_snowmanxxxxxxx, _snowmanxxxxxxx.d(_snowman, _snowmanxxx, gc.d), gc.d)))
         .a(b, Boolean.valueOf(this.a(_snowmanxxxxxxxx, _snowmanxxxxxxxx.d(_snowman, _snowmanxxxx, gc.e), gc.e)))
         .a(c, Boolean.valueOf(this.a(_snowmanxxxxxxxxx, _snowmanxxxxxxxxx.d(_snowman, _snowmanxxxxx, gc.c), gc.c)))
         .a(d, Boolean.valueOf(this.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxx.d(_snowman, _snowmanxxxxxx, gc.f), gc.f)))
         .a(e, Boolean.valueOf(_snowmanxx.a() == cuy.c));
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman.c(e)) {
         _snowman.I().a(_snowman, cuy.c, cuy.c.a(_snowman));
      }

      return _snowman.n().e() == gc.c.a ? _snowman.a(f.get(_snowman), Boolean.valueOf(this.a(_snowman, _snowman.d(_snowman, _snowman, _snowman.f()), _snowman.f()))) : super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a, b, d, c, e);
   }
}
