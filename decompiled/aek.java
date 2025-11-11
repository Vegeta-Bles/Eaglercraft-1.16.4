import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class aek {
   private static final Map<vk, aej<?>> a = Maps.newHashMap();

   public static <T> aej<T> a(vk var0, Function<aen, aem<T>> var1) {
      aej<T> _snowman = new aej<>(_snowman);
      aej<?> _snowmanx = a.putIfAbsent(_snowman, _snowman);
      if (_snowmanx != null) {
         throw new IllegalStateException("Duplicate entry for static tag collection: " + _snowman);
      } else {
         return _snowman;
      }
   }

   public static void a(aen var0) {
      a.values().forEach(var1 -> var1.a(_snowman));
   }

   public static void a() {
      a.values().forEach(aej::a);
   }

   public static Multimap<vk, vk> b(aen var0) {
      Multimap<vk, vk> _snowman = HashMultimap.create();
      a.forEach((var2, var3) -> _snowman.putAll(var2, var3.b(_snowman)));
      return _snowman;
   }

   public static void b() {
      aej[] _snowman = new aej[]{aed.a, aeg.a, aef.a, aee.a};
      boolean _snowmanx = Stream.of(_snowman).anyMatch(var0x -> !a.containsValue(var0x));
      if (_snowmanx) {
         throw new IllegalStateException("Missing helper registrations");
      }
   }
}
