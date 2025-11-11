import javax.annotation.Nullable;

public class bxr extends buo implements bwm {
   public static final cfe<ge> a = cex.P;

   protected bxr(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, ge.k));
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      return _snowman.a(a, _snowman.a().a(_snowman.c(a)));
   }

   @Override
   public ceh a(ceh var1, byg var2) {
      return _snowman.a(a, _snowman.a().a(_snowman.c(a)));
   }

   @Override
   public ceh a(bny var1) {
      gc _snowman = _snowman.j();
      gc _snowmanx;
      if (_snowman.n() == gc.a.b) {
         _snowmanx = _snowman.f().f();
      } else {
         _snowmanx = gc.b;
      }

      return this.n().a(a, ge.a(_snowman, _snowmanx));
   }

   @Nullable
   @Override
   public ccj a(brc var1) {
      return new ccz();
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      ccj _snowman = _snowman.c(_snowman);
      if (_snowman instanceof ccz && _snowman.eV()) {
         _snowman.a((ccz)_snowman);
         return aou.a(_snowman.v);
      } else {
         return aou.c;
      }
   }

   public static boolean a(ctb.c var0, ctb.c var1) {
      gc _snowman = h(_snowman.b);
      gc _snowmanx = h(_snowman.b);
      gc _snowmanxx = l(_snowman.b);
      gc _snowmanxxx = l(_snowman.b);
      ccz.a _snowmanxxxx = ccz.a.a(_snowman.c.l("joint")).orElseGet(() -> _snowman.n().d() ? ccz.a.b : ccz.a.a);
      boolean _snowmanxxxxx = _snowmanxxxx == ccz.a.a;
      return _snowman == _snowmanx.f() && (_snowmanxxxxx || _snowmanxx == _snowmanxxx) && _snowman.c.l("target").equals(_snowman.c.l("name"));
   }

   public static gc h(ceh var0) {
      return _snowman.c(a).b();
   }

   public static gc l(ceh var0) {
      return _snowman.c(a).c();
   }
}
