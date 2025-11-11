import com.google.common.collect.ImmutableSet;
import java.util.Set;

public class ays extends azb<aqm> {
   public ays() {
   }

   @Override
   public Set<ayd<?>> a() {
      return ImmutableSet.of(ayd.x, ayd.y);
   }

   @Override
   protected void a(aag var1, aqm var2) {
      arf<?> _snowman = _snowman.cJ();
      apk _snowmanx = _snowman.dm();
      if (_snowmanx != null) {
         _snowman.a(ayd.x, _snowman.dm());
         aqa _snowmanxx = _snowmanx.k();
         if (_snowmanxx instanceof aqm) {
            _snowman.a(ayd.y, (aqm)_snowmanxx);
         }
      } else {
         _snowman.b(ayd.x);
      }

      _snowman.c(ayd.y).ifPresent(var2x -> {
         if (!var2x.aX() || var2x.l != _snowman) {
            _snowman.b(ayd.y);
         }
      });
   }
}
