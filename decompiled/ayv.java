import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ayv extends azb<aqm> {
   public ayv() {
   }

   @Override
   protected void a(aag var1, aqm var2) {
      dci _snowman = _snowman.cc().c(16.0, 16.0, 16.0);
      List<aqm> _snowmanx = _snowman.a(aqm.class, _snowman, var1x -> var1x != _snowman && var1x.aX());
      _snowmanx.sort(Comparator.comparingDouble(_snowman::h));
      arf<?> _snowmanxx = _snowman.cJ();
      _snowmanxx.a(ayd.g, _snowmanx);
      _snowmanxx.a(ayd.h, _snowmanx.stream().filter(var1x -> a(_snowman, var1x)).collect(Collectors.toList()));
   }

   @Override
   public Set<ayd<?>> a() {
      return ImmutableSet.of(ayd.g, ayd.h);
   }
}
