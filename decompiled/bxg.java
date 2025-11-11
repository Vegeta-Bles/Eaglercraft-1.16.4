import java.util.Optional;
import java.util.Random;

public abstract class bxg extends bxf implements buq {
   protected bxg(ceg.c var1, gc var2, ddh var3, boolean var4) {
      super(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman == this.a.f() && !_snowman.a(_snowman, _snowman)) {
         _snowman.J().a(_snowman, this, 1);
      }

      bxh _snowman = this.c();
      if (_snowman == this.a) {
         buo _snowmanx = _snowman.b();
         if (_snowmanx != this && _snowmanx != _snowman) {
            return _snowman.a(_snowman);
         }
      }

      if (this.b) {
         _snowman.I().a(_snowman, cuy.c, cuy.c.a(_snowman));
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public bmb a(brc var1, fx var2, ceh var3) {
      return new bmb(this.c());
   }

   @Override
   public boolean a(brc var1, fx var2, ceh var3, boolean var4) {
      Optional<fx> _snowman = this.b(_snowman, _snowman, _snowman);
      return _snowman.isPresent() && this.c().h(_snowman.d_(_snowman.get().a(this.a)));
   }

   @Override
   public boolean a(brx var1, Random var2, fx var3, ceh var4) {
      return true;
   }

   @Override
   public void a(aag var1, Random var2, fx var3, ceh var4) {
      Optional<fx> _snowman = this.b(_snowman, _snowman, _snowman);
      if (_snowman.isPresent()) {
         ceh _snowmanx = _snowman.d_(_snowman.get());
         ((bxh)_snowmanx.b()).a(_snowman, _snowman, _snowman.get(), _snowmanx);
      }
   }

   private Optional<fx> b(brc var1, fx var2, ceh var3) {
      fx _snowman = _snowman;

      buo _snowmanx;
      do {
         _snowman = _snowman.a(this.a);
         _snowmanx = _snowman.d_(_snowman).b();
      } while (_snowmanx == _snowman.b());

      return _snowmanx == this.c() ? Optional.of(_snowman) : Optional.empty();
   }

   @Override
   public boolean a(ceh var1, bny var2) {
      boolean _snowman = super.a(_snowman, _snowman);
      return _snowman && _snowman.m().b() == this.c().h() ? false : _snowman;
   }

   @Override
   protected buo d() {
      return this;
   }
}
