import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ayw extends azb<aqm> {
   public ayw() {
   }

   @Override
   public Set<ayd<?>> a() {
      return ImmutableSet.of(ayd.h, ayd.K, ayd.X);
   }

   @Override
   protected void a(aag var1, aqm var2) {
      arf<?> _snowman = _snowman.cJ();
      Optional<aqn> _snowmanx = Optional.empty();
      List<ber> _snowmanxx = Lists.newArrayList();

      for (aqm _snowmanxxx : _snowman.c(ayd.h).orElse(ImmutableList.of())) {
         if (_snowmanxxx instanceof beh || _snowmanxxx instanceof bcl) {
            _snowmanx = Optional.of((aqn)_snowmanxxx);
            break;
         }
      }

      for (aqm _snowmanxxxx : _snowman.c(ayd.g).orElse(ImmutableList.of())) {
         if (_snowmanxxxx instanceof ber && ((ber)_snowmanxxxx).eM()) {
            _snowmanxx.add((ber)_snowmanxxxx);
         }
      }

      _snowman.a(ayd.K, _snowmanx);
      _snowman.a(ayd.X, _snowmanxx);
   }
}
