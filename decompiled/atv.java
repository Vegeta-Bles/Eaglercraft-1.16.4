import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class atv extends arv<aqm> {
   public atv() {
      super(ImmutableMap.of(ayd.m, aye.c, ayd.n, aye.c, ayd.e, aye.a, ayd.h, aye.a, ayd.q, aye.b));
   }

   @Override
   protected boolean a(aag var1, aqm var2) {
      arf<?> _snowman = _snowman.cJ();
      Optional<gf> _snowmanx = _snowman.c(ayd.e);
      return _snowman.u_().nextInt(100) == 0
         && _snowmanx.isPresent()
         && _snowman.Y() == _snowmanx.get().a()
         && _snowmanx.get().b().a(_snowman.cA(), 4.0)
         && _snowman.c(ayd.h).get().stream().anyMatch(var0 -> aqe.aP.equals(var0.X()));
   }

   @Override
   protected void a(aag var1, aqm var2, long var3) {
      arf<?> _snowman = _snowman.cJ();
      _snowman.c(ayd.h)
         .ifPresent(var2x -> var2x.stream().filter(var0x -> aqe.aP.equals(var0x.X())).filter(var1x -> var1x.h(_snowman) <= 32.0).findFirst().ifPresent(var1x -> {
               _snowman.a(ayd.q, var1x);
               _snowman.a(ayd.n, new asd(var1x, true));
               _snowman.a(ayd.m, new ayf(new asd(var1x, false), 0.3F, 1));
            }));
   }
}
