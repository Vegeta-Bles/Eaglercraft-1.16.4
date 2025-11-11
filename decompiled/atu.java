import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class atu extends arv<aqm> {
   private long b;

   public atu() {
      super(ImmutableMap.of(ayd.b, aye.a, ayd.G, aye.c));
   }

   @Override
   protected boolean a(aag var1, aqm var2) {
      if (_snowman.br()) {
         return false;
      } else {
         arf<?> _snowman = _snowman.cJ();
         gf _snowmanx = _snowman.c(ayd.b).get();
         if (_snowman.Y() != _snowmanx.a()) {
            return false;
         } else {
            Optional<Long> _snowmanxx = _snowman.c(ayd.G);
            if (_snowmanxx.isPresent()) {
               long _snowmanxxx = _snowman.T() - _snowmanxx.get();
               if (_snowmanxxx > 0L && _snowmanxxx < 100L) {
                  return false;
               }
            }

            ceh _snowmanxxx = _snowman.d_(_snowmanx.b());
            return _snowmanx.b().a(_snowman.cA(), 2.0) && _snowmanxxx.b().a(aed.L) && !_snowmanxxx.c(buj.b);
         }
      }
   }

   @Override
   protected boolean b(aag var1, aqm var2, long var3) {
      Optional<gf> _snowman = _snowman.cJ().c(ayd.b);
      if (!_snowman.isPresent()) {
         return false;
      } else {
         fx _snowmanx = _snowman.get().b();
         return _snowman.cJ().c(bhf.e) && _snowman.cE() > (double)_snowmanx.v() + 0.4 && _snowmanx.a(_snowman.cA(), 1.14);
      }
   }

   @Override
   protected void a(aag var1, aqm var2, long var3) {
      if (_snowman > this.b) {
         asp.a(_snowman, _snowman, null, null);
         _snowman.b(_snowman.cJ().c(ayd.b).get().b());
      }
   }

   @Override
   protected boolean a(long var1) {
      return false;
   }

   @Override
   protected void c(aag var1, aqm var2, long var3) {
      if (_snowman.em()) {
         _snowman.en();
         this.b = _snowman + 40L;
      }
   }
}
