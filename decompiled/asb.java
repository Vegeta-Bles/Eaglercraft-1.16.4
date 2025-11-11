import com.google.common.collect.ImmutableMap;
import java.util.function.BiPredicate;

public class asb<E extends aqm, T extends aqa> extends arv<E> {
   private final int b;
   private final BiPredicate<E, aqa> c;

   public asb(int var1, BiPredicate<E, aqa> var2) {
      super(ImmutableMap.of(ayd.s, aye.c));
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   protected boolean a(aag var1, E var2) {
      aqa _snowman = _snowman.ct();
      aqa _snowmanx = _snowman.cJ().c(ayd.s).orElse(null);
      if (_snowman == null && _snowmanx == null) {
         return false;
      } else {
         aqa _snowmanxx = _snowman == null ? _snowmanx : _snowman;
         return !this.a(_snowman, _snowmanxx) || this.c.test(_snowman, _snowmanxx);
      }
   }

   private boolean a(E var1, aqa var2) {
      return _snowman.aX() && _snowman.a(_snowman, (double)this.b) && _snowman.l == _snowman.l;
   }

   @Override
   protected void a(aag var1, E var2, long var3) {
      _snowman.l();
      _snowman.cJ().b(ayd.s);
   }
}
