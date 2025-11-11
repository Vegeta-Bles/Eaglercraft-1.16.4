import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class azd extends azb<aqm> {
   public azd() {
   }

   @Override
   public Set<ayd<?>> a() {
      return ImmutableSet.of(ayd.i);
   }

   @Override
   protected void a(aag var1, aqm var2) {
      _snowman.cJ().a(ayd.i, this.a(_snowman));
   }

   private List<aqm> a(aqm var1) {
      return this.c(_snowman).stream().filter(this::b).collect(Collectors.toList());
   }

   private boolean b(aqm var1) {
      return _snowman.X() == aqe.aP && _snowman.w_();
   }

   private List<aqm> c(aqm var1) {
      return _snowman.cJ().c(ayd.h).orElse(Lists.newArrayList());
   }
}
