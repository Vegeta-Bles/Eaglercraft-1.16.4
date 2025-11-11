import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class aze extends azb<aqm> {
   private static final ImmutableMap<aqe<?>, Float> a = ImmutableMap.builder()
      .put(aqe.q, 8.0F)
      .put(aqe.w, 12.0F)
      .put(aqe.I, 8.0F)
      .put(aqe.J, 12.0F)
      .put(aqe.ak, 15.0F)
      .put(aqe.ap, 12.0F)
      .put(aqe.aO, 8.0F)
      .put(aqe.aQ, 10.0F)
      .put(aqe.aX, 10.0F)
      .put(aqe.aY, 8.0F)
      .put(aqe.ba, 8.0F)
      .build();

   public aze() {
   }

   @Override
   public Set<ayd<?>> a() {
      return ImmutableSet.of(ayd.A);
   }

   @Override
   protected void a(aag var1, aqm var2) {
      _snowman.cJ().a(ayd.A, this.a(_snowman));
   }

   private Optional<aqm> a(aqm var1) {
      return this.b(_snowman).flatMap(var2 -> var2.stream().filter(this::c).filter(var2x -> this.b(_snowman, var2x)).min((var2x, var3) -> this.a(_snowman, var2x, var3)));
   }

   private Optional<List<aqm>> b(aqm var1) {
      return _snowman.cJ().c(ayd.h);
   }

   private int a(aqm var1, aqm var2, aqm var3) {
      return afm.c(_snowman.h(_snowman) - _snowman.h(_snowman));
   }

   private boolean b(aqm var1, aqm var2) {
      float _snowman = (Float)a.get(_snowman.X());
      return _snowman.h(_snowman) <= (double)(_snowman * _snowman);
   }

   private boolean c(aqm var1) {
      return a.containsKey(_snowman.X());
   }
}
