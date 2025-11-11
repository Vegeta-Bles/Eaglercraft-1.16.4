import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ejr implements AutoCloseable {
   private final Map<vk, ekb> a;

   public ejr(Collection<ekb> var1) {
      this.a = _snowman.stream().collect(Collectors.toMap(ekb::g, Function.identity()));
   }

   public ekb a(vk var1) {
      return this.a.get(_snowman);
   }

   public ekc a(elr var1) {
      return this.a.get(_snowman.a()).a(_snowman.b());
   }

   @Override
   public void close() {
      this.a.values().forEach(ekb::f);
      this.a.clear();
   }
}
