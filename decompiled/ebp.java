import com.google.common.collect.Streams;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ebp implements ebq {
   private final Iterable<? extends ebq> c;

   public ebp(Iterable<? extends ebq> var1) {
      this.c = _snowman;
   }

   @Override
   public Predicate<ceh> getPredicate(cei<buo, ceh> var1) {
      List<Predicate<ceh>> _snowman = Streams.stream(this.c).map(var1x -> var1x.getPredicate(_snowman)).collect(Collectors.toList());
      return var1x -> _snowman.stream().allMatch(var1xx -> var1xx.test(var1x));
   }
}
