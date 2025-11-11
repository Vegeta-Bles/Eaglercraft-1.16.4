import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class asn extends arv<aqu> {
   private final float b;

   public asn(float var1) {
      super(ImmutableMap.of(ayd.m, aye.b));
      this.b = _snowman;
   }

   protected boolean a(aag var1, aqu var2) {
      return !_snowman.e(_snowman.cB());
   }

   protected void a(aag var1, aqu var2, long var3) {
      fx _snowman = _snowman.cB();
      List<fx> _snowmanx = fx.b(_snowman.b(-1, -1, -1), _snowman.b(1, 1, 1)).map(fx::h).collect(Collectors.toList());
      Collections.shuffle(_snowmanx);
      Optional<fx> _snowmanxx = _snowmanx.stream().filter(var1x -> !_snowman.e(var1x)).filter(var2x -> _snowman.a(var2x, _snowman)).filter(var2x -> _snowman.k(_snowman)).findFirst();
      _snowmanxx.ifPresent(var2x -> _snowman.cJ().a(ayd.m, new ayf(var2x, this.b, 0)));
   }
}
