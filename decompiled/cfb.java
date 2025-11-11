import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class cfb extends cfe<gc> {
   protected cfb(String var1, Collection<gc> var2) {
      super(_snowman, gc.class, _snowman);
   }

   public static cfb a(String var0, Predicate<gc> var1) {
      return a(_snowman, Arrays.stream(gc.values()).filter(_snowman).collect(Collectors.toList()));
   }

   public static cfb a(String var0, gc... var1) {
      return a(_snowman, Lists.newArrayList(_snowman));
   }

   public static cfb a(String var0, Collection<gc> var1) {
      return new cfb(_snowman, _snowman);
   }
}
