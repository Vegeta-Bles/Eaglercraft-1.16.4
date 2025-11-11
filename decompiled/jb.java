import com.google.gson.JsonElement;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class jb {
   public static final jb.a a = a(iz::a, iy.c);
   public static final jb.a b = a(iz::a, iy.d);
   public static final jb.a c = a(iz::j, iy.e);
   public static final jb.a d = a(iz::j, iy.f);
   public static final jb.a e = a(iz::m, iy.h);
   public static final jb.a f = a(iz::k, iy.g);
   public static final jb.a g = a(iz::x, iy.i);
   public static final jb.a h = a(iz::w, iy.j);
   public static final jb.a i = a(iz::f, iy.aa);
   public static final jb.a j = a(iz::h, iy.ad);
   public static final jb.a k = a(iz::i, iy.ab);
   public static final jb.a l = a(iz::q, iy.F);
   public static final jb.a m = a(iz::z, iy.am);
   public static final jb.a n = a(iz::a, iy.I);
   public static final jb.a o = a(iz::t, iy.ax);
   public static final jb.a p = a(iz::t, iy.ay);
   public static final jb.a q = a(iz::b, iy.aE);
   public static final jb.a r = a(iz::l, iy.e);
   public static final jb.a s = a(iz::l, iy.f);
   public static final jb.a t = a(iz::n, iy.h);
   public static final jb.a u = a(iz::o, iy.e);
   private final iz v;
   private final ix w;

   private jb(iz var1, ix var2) {
      this.v = _snowman;
      this.w = _snowman;
   }

   public ix a() {
      return this.w;
   }

   public iz b() {
      return this.v;
   }

   public jb a(Consumer<iz> var1) {
      _snowman.accept(this.v);
      return this;
   }

   public vk a(buo var1, BiConsumer<vk, Supplier<JsonElement>> var2) {
      return this.w.a(_snowman, this.v, _snowman);
   }

   public vk a(buo var1, String var2, BiConsumer<vk, Supplier<JsonElement>> var3) {
      return this.w.a(_snowman, _snowman, this.v, _snowman);
   }

   private static jb.a a(Function<buo, iz> var0, ix var1) {
      return var2 -> new jb(_snowman.apply(var2), _snowman);
   }

   public static jb a(vk var0) {
      return new jb(iz.b(_snowman), iy.c);
   }

   @FunctionalInterface
   public interface a {
      jb get(buo var1);

      default vk a(buo var1, BiConsumer<vk, Supplier<JsonElement>> var2) {
         return this.get(_snowman).a(_snowman, _snowman);
      }

      default vk a(buo var1, String var2, BiConsumer<vk, Supplier<JsonElement>> var3) {
         return this.get(_snowman).a(_snowman, _snowman, _snowman);
      }

      default jb.a a(Consumer<iz> var1) {
         return var2 -> this.get(var2).a(_snowman);
      }
   }
}
