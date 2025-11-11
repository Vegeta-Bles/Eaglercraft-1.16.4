import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ear {
   public static final vk a = new vk("textures/atlas/shulker_boxes.png");
   public static final vk b = new vk("textures/atlas/beds.png");
   public static final vk c = new vk("textures/atlas/banner_patterns.png");
   public static final vk d = new vk("textures/atlas/shield_patterns.png");
   public static final vk e = new vk("textures/atlas/signs.png");
   public static final vk f = new vk("textures/atlas/chest.png");
   private static final eao u = eao.d(a);
   private static final eao v = eao.b(b);
   private static final eao w = eao.k(c);
   private static final eao x = eao.k(d);
   private static final eao y = eao.d(e);
   private static final eao z = eao.c(f);
   private static final eao A = eao.b(ekb.d);
   private static final eao B = eao.c(ekb.d);
   private static final eao C = eao.f(ekb.d);
   private static final eao D = eao.g(ekb.d);
   public static final elr g = new elr(a, new vk("entity/shulker/shulker"));
   public static final List<elr> h = Stream.of(
         "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"
      )
      .map(var0 -> new elr(a, new vk("entity/shulker/shulker_" + var0)))
      .collect(ImmutableList.toImmutableList());
   public static final Map<cfq, elr> i = cfq.a().collect(Collectors.toMap(Function.identity(), ear::a));
   public static final elr[] j = Arrays.stream(bkx.values())
      .sorted(Comparator.comparingInt(bkx::b))
      .map(var0 -> new elr(b, new vk("entity/bed/" + var0.c())))
      .toArray(elr[]::new);
   public static final elr k = a("trapped");
   public static final elr l = a("trapped_left");
   public static final elr m = a("trapped_right");
   public static final elr n = a("christmas");
   public static final elr o = a("christmas_left");
   public static final elr p = a("christmas_right");
   public static final elr q = a("normal");
   public static final elr r = a("normal_left");
   public static final elr s = a("normal_right");
   public static final elr t = a("ender");

   public static eao a() {
      return w;
   }

   public static eao b() {
      return x;
   }

   public static eao c() {
      return v;
   }

   public static eao d() {
      return u;
   }

   public static eao e() {
      return y;
   }

   public static eao f() {
      return z;
   }

   public static eao g() {
      return A;
   }

   public static eao h() {
      return B;
   }

   public static eao i() {
      return C;
   }

   public static eao j() {
      return D;
   }

   public static void a(Consumer<elr> var0) {
      _snowman.accept(g);
      h.forEach(_snowman);

      for (ccb _snowman : ccb.values()) {
         _snowman.accept(new elr(c, _snowman.a(true)));
         _snowman.accept(new elr(d, _snowman.a(false)));
      }

      i.values().forEach(_snowman);

      for (elr _snowman : j) {
         _snowman.accept(_snowman);
      }

      _snowman.accept(k);
      _snowman.accept(l);
      _snowman.accept(m);
      _snowman.accept(n);
      _snowman.accept(o);
      _snowman.accept(p);
      _snowman.accept(q);
      _snowman.accept(r);
      _snowman.accept(s);
      _snowman.accept(t);
   }

   public static elr a(cfq var0) {
      return new elr(e, new vk("entity/signs/" + _snowman.b()));
   }

   private static elr a(String var0) {
      return new elr(f, new vk("entity/chest/" + _snowman));
   }

   public static elr a(ccj var0, cez var1, boolean var2) {
      if (_snowman) {
         return a(_snowman, n, o, p);
      } else if (_snowman instanceof cdn) {
         return a(_snowman, k, l, m);
      } else {
         return _snowman instanceof ccv ? t : a(_snowman, q, r, s);
      }
   }

   private static elr a(cez var0, elr var1, elr var2, elr var3) {
      switch (_snowman) {
         case b:
            return _snowman;
         case c:
            return _snowman;
         case a:
         default:
            return _snowman;
      }
   }
}
