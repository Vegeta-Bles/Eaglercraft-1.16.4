import java.util.Random;

public class bvg extends bys {
   protected bvg(ceg.c var1) {
      super(0.3125F, _snowman);
      this.j(
         this.n
            .b()
            .a(a, Boolean.valueOf(false))
            .a(b, Boolean.valueOf(false))
            .a(c, Boolean.valueOf(false))
            .a(d, Boolean.valueOf(false))
            .a(e, Boolean.valueOf(false))
            .a(f, Boolean.valueOf(false))
      );
   }

   @Override
   public ceh a(bny var1) {
      return this.a(_snowman.p(), _snowman.a());
   }

   public ceh a(brc var1, fx var2) {
      buo _snowman = _snowman.d_(_snowman.c()).b();
      buo _snowmanx = _snowman.d_(_snowman.b()).b();
      buo _snowmanxx = _snowman.d_(_snowman.d()).b();
      buo _snowmanxxx = _snowman.d_(_snowman.g()).b();
      buo _snowmanxxxx = _snowman.d_(_snowman.e()).b();
      buo _snowmanxxxxx = _snowman.d_(_snowman.f()).b();
      return this.n()
         .a(f, Boolean.valueOf(_snowman == this || _snowman == bup.iy || _snowman == bup.ee))
         .a(e, Boolean.valueOf(_snowmanx == this || _snowmanx == bup.iy))
         .a(a, Boolean.valueOf(_snowmanxx == this || _snowmanxx == bup.iy))
         .a(b, Boolean.valueOf(_snowmanxxx == this || _snowmanxxx == bup.iy))
         .a(c, Boolean.valueOf(_snowmanxxxx == this || _snowmanxxxx == bup.iy))
         .a(d, Boolean.valueOf(_snowmanxxxxx == this || _snowmanxxxxx == bup.iy));
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (!_snowman.a(_snowman, _snowman)) {
         _snowman.J().a(_snowman, this, 1);
         return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      } else {
         boolean _snowman = _snowman.b() == this || _snowman.a(bup.iy) || _snowman == gc.a && _snowman.a(bup.ee);
         return _snowman.a(g.get(_snowman), Boolean.valueOf(_snowman));
      }
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      if (!_snowman.a(_snowman, _snowman)) {
         _snowman.b(_snowman, true);
      }
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      ceh _snowman = _snowman.d_(_snowman.c());
      boolean _snowmanx = !_snowman.d_(_snowman.b()).g() && !_snowman.g();

      for (gc _snowmanxx : gc.c.a) {
         fx _snowmanxxx = _snowman.a(_snowmanxx);
         buo _snowmanxxxx = _snowman.d_(_snowmanxxx).b();
         if (_snowmanxxxx == this) {
            if (_snowmanx) {
               return false;
            }

            buo _snowmanxxxxx = _snowman.d_(_snowmanxxx.c()).b();
            if (_snowmanxxxxx == this || _snowmanxxxxx == bup.ee) {
               return true;
            }
         }
      }

      buo _snowmanxxx = _snowman.b();
      return _snowmanxxx == this || _snowmanxxx == bup.ee;
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a, b, c, d, e, f);
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }
}
