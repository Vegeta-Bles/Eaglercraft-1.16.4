import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ayo extends azb<apy> {
   public ayo() {
   }

   @Override
   public Set<ayd<?>> a() {
      return ImmutableSet.of(ayd.I, ayd.h);
   }

   protected void a(aag var1, apy var2) {
      _snowman.cJ().c(ayd.h).ifPresent(var2x -> this.a(_snowman, (List<aqm>)var2x));
   }

   private void a(apy var1, List<aqm> var2) {
      Optional<apy> _snowman = _snowman.stream().filter(var1x -> var1x.X() == _snowman.X()).map(var0 -> (apy)var0).filter(var0 -> !var0.w_()).findFirst();
      _snowman.cJ().a(ayd.I, _snowman);
   }
}
