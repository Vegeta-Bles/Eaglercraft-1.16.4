import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ayq extends azb<aqm> {
   public ayq() {
      this(200);
   }

   public ayq(int var1) {
      super(_snowman);
   }

   @Override
   protected void a(aag var1, aqm var2) {
      a(_snowman);
   }

   @Override
   public Set<ayd<?>> a() {
      return ImmutableSet.of(ayd.g);
   }

   public static void a(aqm var0) {
      Optional<List<aqm>> _snowman = _snowman.cJ().c(ayd.g);
      if (_snowman.isPresent()) {
         boolean _snowmanx = _snowman.get().stream().anyMatch(var0x -> var0x.X().equals(aqe.K));
         if (_snowmanx) {
            b(_snowman);
         }
      }
   }

   public static void b(aqm var0) {
      _snowman.cJ().a(ayd.E, true, 600L);
   }
}
