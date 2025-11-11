import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

public class vj<T> {
   private static final Map<String, vj<?>> a = Collections.synchronizedMap(Maps.newIdentityHashMap());
   private final vk b;
   private final vk c;

   public static <T> vj<T> a(vj<? extends gm<T>> var0, vk var1) {
      return a(_snowman.c, _snowman);
   }

   public static <T> vj<gm<T>> a(vk var0) {
      return a(gm.d, _snowman);
   }

   private static <T> vj<T> a(vk var0, vk var1) {
      String _snowman = (_snowman + ":" + _snowman).intern();
      return (vj<T>)a.computeIfAbsent(_snowman, var2x -> new vj(_snowman, _snowman));
   }

   private vj(vk var1, vk var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public String toString() {
      return "ResourceKey[" + this.b + " / " + this.c + ']';
   }

   public boolean a(vj<? extends gm<?>> var1) {
      return this.b.equals(_snowman.a());
   }

   public vk a() {
      return this.c;
   }

   public static <T> Function<vk, vj<T>> b(vj<? extends gm<T>> var0) {
      return var1 -> a(_snowman, var1);
   }
}
