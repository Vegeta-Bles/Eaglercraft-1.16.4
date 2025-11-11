import com.google.common.collect.ImmutableMap;
import java.util.function.Predicate;

public class auh extends arv<aqm> {
   private final ayd<gf> b;
   private final Predicate<azr> c;

   public auh(azr var1, ayd<gf> var2) {
      super(ImmutableMap.of(_snowman, aye.a));
      this.c = _snowman.c();
      this.b = _snowman;
   }

   @Override
   protected boolean a(aag var1, aqm var2) {
      gf _snowman = _snowman.cJ().c(this.b).get();
      return _snowman.Y() == _snowman.a() && _snowman.b().a(_snowman.cA(), 16.0);
   }

   @Override
   protected void a(aag var1, aqm var2, long var3) {
      arf<?> _snowman = _snowman.cJ();
      gf _snowmanx = _snowman.c(this.b).get();
      fx _snowmanxx = _snowmanx.b();
      aag _snowmanxxx = _snowman.l().a(_snowmanx.a());
      if (_snowmanxxx == null || this.a(_snowmanxxx, _snowmanxx)) {
         _snowman.b(this.b);
      } else if (this.a(_snowmanxxx, _snowmanxx, _snowman)) {
         _snowman.b(this.b);
         _snowman.y().b(_snowmanxx);
         rz.c(_snowman, _snowmanxx);
      }
   }

   private boolean a(aag var1, fx var2, aqm var3) {
      ceh _snowman = _snowman.d_(_snowman);
      return _snowman.b().a(aed.L) && _snowman.c(buj.b) && !_snowman.em();
   }

   private boolean a(aag var1, fx var2) {
      return !_snowman.y().a(_snowman, this.c);
   }
}
