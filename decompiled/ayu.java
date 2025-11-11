import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ayu extends azb<aqn> {
   public ayu() {
   }

   @Override
   public Set<ayd<?>> a() {
      return ImmutableSet.of(ayd.J);
   }

   protected void a(aag var1, aqn var2) {
      arf<?> _snowman = _snowman.cJ();
      List<bcv> _snowmanx = _snowman.a(bcv.class, _snowman.cc().c(8.0, 4.0, 8.0), var0 -> true);
      _snowmanx.sort(Comparator.comparingDouble(_snowman::h));
      Optional<bcv> _snowmanxx = _snowmanx.stream().filter(var1x -> _snowman.i(var1x.g())).filter(var1x -> var1x.a(_snowman, 9.0)).filter(_snowman::D).findFirst();
      _snowman.a(ayd.J, _snowmanxx);
   }
}
