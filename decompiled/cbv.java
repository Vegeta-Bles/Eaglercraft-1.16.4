import javax.annotation.Nullable;

public class cbv extends bzv {
   @Nullable
   private static cem c;
   @Nullable
   private static cem d;

   protected cbv(ceg.c var1) {
      super(bzv.b.b, _snowman);
   }

   @Override
   public void a(brx var1, fx var2, ceh var3, @Nullable aqm var4, bmb var5) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      ccj _snowman = _snowman.c(_snowman);
      if (_snowman instanceof cdg) {
         a(_snowman, _snowman, (cdg)_snowman);
      }
   }

   public static void a(brx var0, fx var1, cdg var2) {
      if (!_snowman.v) {
         ceh _snowman = _snowman.p();
         boolean _snowmanx = _snowman.a(bup.fe) || _snowman.a(bup.ff);
         if (_snowmanx && _snowman.v() >= 0 && _snowman.ad() != aor.a) {
            cem _snowmanxx = c();
            cem.b _snowmanxxx = _snowmanxx.a(_snowman, _snowman);
            if (_snowmanxxx != null) {
               for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxx.c(); _snowmanxxxx++) {
                  for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxx.b(); _snowmanxxxxx++) {
                     cel _snowmanxxxxxx = _snowmanxxx.a(_snowmanxxxx, _snowmanxxxxx, 0);
                     _snowman.a(_snowmanxxxxxx.d(), bup.a.n(), 2);
                     _snowman.c(2001, _snowmanxxxxxx.d(), buo.i(_snowmanxxxxxx.a()));
                  }
               }

               bcl _snowmanxxxx = aqe.aT.a(_snowman);
               fx _snowmanxxxxx = _snowmanxxx.a(1, 2, 0).d();
               _snowmanxxxx.b((double)_snowmanxxxxx.u() + 0.5, (double)_snowmanxxxxx.v() + 0.55, (double)_snowmanxxxxx.w() + 0.5, _snowmanxxx.b().n() == gc.a.a ? 0.0F : 90.0F, 0.0F);
               _snowmanxxxx.aA = _snowmanxxx.b().n() == gc.a.a ? 0.0F : 90.0F;
               _snowmanxxxx.m();

               for (aah _snowmanxxxxxx : _snowman.a(aah.class, _snowmanxxxx.cc().g(50.0))) {
                  ac.n.a(_snowmanxxxxxx, _snowmanxxxx);
               }

               _snowman.c(_snowmanxxxx);

               for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxx.c(); _snowmanxxxxxx++) {
                  for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxx.b(); _snowmanxxxxxxx++) {
                     _snowman.a(_snowmanxxx.a(_snowmanxxxxxx, _snowmanxxxxxxx, 0).d(), bup.a);
                  }
               }
            }
         }
      }
   }

   public static boolean b(brx var0, fx var1, bmb var2) {
      return _snowman.b() == bmd.pf && _snowman.v() >= 2 && _snowman.ad() != aor.a && !_snowman.v ? d().a(_snowman, _snowman) != null : false;
   }

   private static cem c() {
      if (c == null) {
         c = cen.a().a("^^^", "###", "~#~").a('#', var0 -> var0.a().a(aed.ai)).a('^', cel.a(cer.a(bup.fe).or(cer.a(bup.ff)))).a('~', cel.a(cep.a(cva.a))).b();
      }

      return c;
   }

   private static cem d() {
      if (d == null) {
         d = cen.a().a("   ", "###", "~#~").a('#', var0 -> var0.a().a(aed.ai)).a('~', cel.a(cep.a(cva.a))).b();
      }

      return d;
   }
}
