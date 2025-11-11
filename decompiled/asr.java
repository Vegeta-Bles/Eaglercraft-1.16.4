import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class asr extends arv<aqm> {
   private final float b;
   private final int c;
   private final int d;
   private Optional<fx> e = Optional.empty();

   public asr(int var1, float var2, int var3) {
      super(ImmutableMap.of(ayd.m, aye.b, ayd.b, aye.c, ayd.B, aye.c));
      this.c = _snowman;
      this.b = _snowman;
      this.d = _snowman;
   }

   @Override
   protected boolean a(aag var1, aqm var2) {
      Optional<fx> _snowman = _snowman.y().c(var0 -> var0 == azr.r, var0 -> true, _snowman.cB(), this.d + 1, azo.b.c);
      if (_snowman.isPresent() && _snowman.get().a(_snowman.cA(), (double)this.d)) {
         this.e = _snowman;
      } else {
         this.e = Optional.empty();
      }

      return true;
   }

   @Override
   protected void a(aag var1, aqm var2, long var3) {
      arf<?> _snowman = _snowman.cJ();
      Optional<fx> _snowmanx = this.e;
      if (!_snowmanx.isPresent()) {
         _snowmanx = _snowman.y().a(var0 -> var0 == azr.r, var0 -> true, azo.b.c, _snowman.cB(), this.c, _snowman.cY());
         if (!_snowmanx.isPresent()) {
            Optional<gf> _snowmanxx = _snowman.c(ayd.b);
            if (_snowmanxx.isPresent()) {
               _snowmanx = Optional.of(_snowmanxx.get().b());
            }
         }
      }

      if (_snowmanx.isPresent()) {
         _snowman.b(ayd.t);
         _snowman.b(ayd.n);
         _snowman.b(ayd.r);
         _snowman.b(ayd.q);
         _snowman.a(ayd.B, gf.a(_snowman.Y(), _snowmanx.get()));
         if (!_snowmanx.get().a(_snowman.cA(), (double)this.d)) {
            _snowman.a(ayd.m, new ayf(_snowmanx.get(), this.b, this.d));
         }
      }
   }
}
