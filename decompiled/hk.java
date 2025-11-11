import com.google.common.collect.Maps;
import com.mojang.serialization.Lifecycle;
import java.util.Map;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class hk {
   protected static final Logger a = LogManager.getLogger();
   private static final Map<vk, Supplier<?>> k = Maps.newLinkedHashMap();
   private static final gs<gs<?>> l = new gi<>(vj.a(new vk("root")), Lifecycle.experimental());
   public static final gm<? extends gm<?>> b = l;
   public static final gm<ctg<?>> c = a(gm.as, () -> kp.p);
   public static final gm<cib<?>> d = a(gm.at, () -> kf.a);
   public static final gm<civ<?, ?>> e = a(gm.au, () -> kh.bH);
   public static final gm<ciw<?, ?>> f = a(gm.av, () -> ko.b);
   public static final gm<csz> g = a(gm.aw, () -> kl.b);
   public static final gm<cok> h = a(gm.ax, kk::a);
   public static final gm<bsv> i = a(gm.ay, () -> kt.a);
   public static final gm<chp> j = a(gm.ar, chp::i);

   private static <T> gm<T> a(vj<? extends gm<T>> var0, Supplier<T> var1) {
      return a(_snowman, Lifecycle.stable(), _snowman);
   }

   private static <T> gm<T> a(vj<? extends gm<T>> var0, Lifecycle var1, Supplier<T> var2) {
      return a(_snowman, new gi<>(_snowman, _snowman), _snowman, _snowman);
   }

   private static <T, R extends gs<T>> R a(vj<? extends gm<T>> var0, R var1, Supplier<T> var2, Lifecycle var3) {
      vk _snowman = _snowman.a();
      k.put(_snowman, _snowman);
      gs<R> _snowmanx = l;
      return _snowmanx.a((vj<R>)_snowman, _snowman, _snowman);
   }

   public static <T> T a(gm<? super T> var0, String var1, T var2) {
      return a(_snowman, new vk(_snowman), _snowman);
   }

   public static <V, T extends V> T a(gm<V> var0, vk var1, T var2) {
      return ((gs)_snowman).a(vj.a(_snowman.f(), _snowman), _snowman, Lifecycle.stable());
   }

   public static <V, T extends V> T a(gm<V> var0, int var1, vj<V> var2, T var3) {
      return ((gs)_snowman).a(_snowman, _snowman, _snowman, Lifecycle.stable());
   }

   public static void a() {
   }

   static {
      k.forEach((var0, var1) -> {
         if (var1.get() == null) {
            a.error("Unable to bootstrap registry '{}'", var0);
         }
      });
      gm.a(l);
   }
}
