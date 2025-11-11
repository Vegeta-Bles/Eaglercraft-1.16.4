import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ayy extends azb<aqm> {
   public ayy() {
   }

   @Override
   public Set<ayd<?>> a() {
      return ImmutableSet.of(ayd.j, ayd.k, ayd.l);
   }

   @Override
   protected void a(aag var1, aqm var2) {
      List<bfw> _snowman = _snowman.x().stream().filter(aqd.g).filter(var1x -> _snowman.a(var1x, 16.0)).sorted(Comparator.comparingDouble(_snowman::h)).collect(Collectors.toList());
      arf<?> _snowmanx = _snowman.cJ();
      _snowmanx.a(ayd.j, _snowman);
      List<bfw> _snowmanxx = _snowman.stream().filter(var1x -> a(_snowman, (aqm)var1x)).collect(Collectors.toList());
      _snowmanx.a(ayd.k, _snowmanxx.isEmpty() ? null : _snowmanxx.get(0));
      Optional<bfw> _snowmanxxx = _snowmanxx.stream().filter(aqd.f).findFirst();
      _snowmanx.a(ayd.l, _snowmanxxx);
   }
}
