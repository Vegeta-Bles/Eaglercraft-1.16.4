import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class aus extends arv<bfj> {
   private final float b;

   public aus(float var1) {
      super(ImmutableMap.of(ayd.d, aye.a, ayd.c, aye.b, ayd.g, aye.a));
      this.b = _snowman;
   }

   protected boolean a(aag var1, bfj var2) {
      return _snowman.w_() ? false : _snowman.eX().b() == bfm.a;
   }

   protected void a(aag var1, bfj var2, long var3) {
      fx _snowman = _snowman.cJ().c(ayd.d).get().b();
      Optional<azr> _snowmanx = _snowman.y().c(_snowman);
      if (_snowmanx.isPresent()) {
         arw.a(_snowman, var3x -> this.a(_snowman.get(), var3x, _snowman)).findFirst().ifPresent(var4 -> this.a(_snowman, _snowman, var4, _snowman, var4.cJ().c(ayd.c).isPresent()));
      }
   }

   private boolean a(azr var1, bfj var2, fx var3) {
      boolean _snowman = _snowman.cJ().c(ayd.d).isPresent();
      if (_snowman) {
         return false;
      } else {
         Optional<gf> _snowmanx = _snowman.cJ().c(ayd.c);
         bfm _snowmanxx = _snowman.eX().b();
         if (_snowman.eX().b() == bfm.a || !_snowmanxx.b().c().test(_snowman)) {
            return false;
         } else {
            return !_snowmanx.isPresent() ? this.a(_snowman, _snowman, _snowman) : _snowmanx.get().b().equals(_snowman);
         }
      }
   }

   private void a(aag var1, bfj var2, bfj var3, fx var4, boolean var5) {
      this.a(_snowman);
      if (!_snowman) {
         arw.a(_snowman, _snowman, this.b, 1);
         _snowman.cJ().a(ayd.d, gf.a(_snowman.Y(), _snowman));
         rz.c(_snowman, _snowman);
      }
   }

   private boolean a(bfj var1, fx var2, azr var3) {
      cxd _snowman = _snowman.x().a(_snowman, _snowman.d());
      return _snowman != null && _snowman.j();
   }

   private void a(bfj var1) {
      _snowman.cJ().b(ayd.m);
      _snowman.cJ().b(ayd.n);
      _snowman.cJ().b(ayd.d);
   }
}
