import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class aur extends arv<bfj> {
   private long b;

   public aur() {
      super(ImmutableMap.of(ayd.c, aye.a, ayd.n, aye.c));
   }

   protected boolean b(aag var1, bfj var2) {
      if (_snowman.T() - this.b < 300L) {
         return false;
      } else if (_snowman.t.nextInt(2) != 0) {
         return false;
      } else {
         this.b = _snowman.T();
         gf _snowman = _snowman.cJ().c(ayd.c).get();
         return _snowman.a() == _snowman.Y() && _snowman.b().a(_snowman.cA(), 1.73);
      }
   }

   protected void a(aag var1, bfj var2, long var3) {
      arf<bfj> _snowman = _snowman.cJ();
      _snowman.a(ayd.H, _snowman);
      _snowman.c(ayd.c).ifPresent(var1x -> _snowman.a(ayd.n, new arx(var1x.b())));
      _snowman.fd();
      this.a(_snowman, _snowman);
      if (_snowman.fc()) {
         _snowman.fb();
      }
   }

   protected void a(aag var1, bfj var2) {
   }

   protected boolean b(aag var1, bfj var2, long var3) {
      Optional<gf> _snowman = _snowman.cJ().c(ayd.c);
      if (!_snowman.isPresent()) {
         return false;
      } else {
         gf _snowmanx = _snowman.get();
         return _snowmanx.a() == _snowman.Y() && _snowmanx.b().a(_snowman.cA(), 1.73);
      }
   }
}
