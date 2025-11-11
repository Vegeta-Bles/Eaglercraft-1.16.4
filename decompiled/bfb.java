import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class bfb<E extends bes> extends arv<E> {
   private final int b;
   private final int c;

   public bfb(int var1, int var2) {
      super(ImmutableMap.of(ayd.N, aye.a, ayd.J, aye.a, ayd.O, aye.c, ayd.P, aye.c));
      this.b = _snowman;
      this.c = _snowman;
   }

   protected boolean a(aag var1, E var2) {
      return _snowman.dE().a();
   }

   protected void a(aag var1, E var2, long var3) {
      arf<bes> _snowman = _snowman.cJ();
      Optional<Integer> _snowmanx = _snowman.c(ayd.O);
      if (!_snowmanx.isPresent()) {
         _snowman.a(ayd.O, 0);
      } else {
         int _snowmanxx = _snowmanx.get();
         if (_snowmanxx > this.b) {
            _snowman.b(ayd.N);
            _snowman.b(ayd.O);
            _snowman.a(ayd.P, true, (long)this.c);
         } else {
            _snowman.a(ayd.O, _snowmanxx + 1);
         }
      }
   }
}
