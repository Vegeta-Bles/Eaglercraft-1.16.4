import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ayr extends azb<bem> {
   public ayr() {
   }

   @Override
   public Set<ayd<?>> a() {
      return ImmutableSet.of(ayd.h, ayd.ag, ayd.aa, ayd.Z, ayd.ac, ayd.ad, new ayd[0]);
   }

   protected void a(aag var1, bem var2) {
      arf<?> _snowman = _snowman.cJ();
      _snowman.a(ayd.ag, this.b(_snowman, _snowman));
      Optional<bes> _snowmanx = Optional.empty();
      int _snowmanxx = 0;
      List<bem> _snowmanxxx = Lists.newArrayList();

      for (aqm _snowmanxxxx : _snowman.c(ayd.h).orElse(Lists.newArrayList())) {
         if (_snowmanxxxx instanceof bes && !_snowmanxxxx.w_()) {
            _snowmanxx++;
            if (!_snowmanx.isPresent()) {
               _snowmanx = Optional.of((bes)_snowmanxxxx);
            }
         }

         if (_snowmanxxxx instanceof bem && !_snowmanxxxx.w_()) {
            _snowmanxxx.add((bem)_snowmanxxxx);
         }
      }

      _snowman.a(ayd.aa, _snowmanx);
      _snowman.a(ayd.Z, _snowmanxxx);
      _snowman.a(ayd.ac, _snowmanxx);
      _snowman.a(ayd.ad, _snowmanxxx.size());
   }

   private Optional<fx> b(aag var1, bem var2) {
      return fx.a(_snowman.cB(), 8, 4, var1x -> _snowman.d_(var1x).a(aed.av));
   }
}
