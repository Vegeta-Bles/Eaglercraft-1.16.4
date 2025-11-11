import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class lh {
   private static final Collection<lu> a = Lists.newArrayList();
   private static final Set<String> b = Sets.newHashSet();
   private static final Map<String, Consumer<aag>> c = Maps.newHashMap();
   private static final Collection<lu> d = Sets.newHashSet();

   public static Collection<lu> a(String var0) {
      return a.stream().filter(var1 -> a(var1, _snowman)).collect(Collectors.toList());
   }

   public static Collection<lu> a() {
      return a;
   }

   public static Collection<String> b() {
      return b;
   }

   public static boolean b(String var0) {
      return b.contains(_snowman);
   }

   @Nullable
   public static Consumer<aag> c(String var0) {
      return c.get(_snowman);
   }

   public static Optional<lu> d(String var0) {
      return a().stream().filter(var1 -> var1.a().equalsIgnoreCase(_snowman)).findFirst();
   }

   public static lu e(String var0) {
      Optional<lu> _snowman = d(_snowman);
      if (!_snowman.isPresent()) {
         throw new IllegalArgumentException("Can't find the test function for " + _snowman);
      } else {
         return _snowman.get();
      }
   }

   private static boolean a(lu var0, String var1) {
      return _snowman.a().toLowerCase().startsWith(_snowman.toLowerCase() + ".");
   }

   public static Collection<lu> c() {
      return d;
   }

   public static void a(lu var0) {
      d.add(_snowman);
   }

   public static void d() {
      d.clear();
   }
}
