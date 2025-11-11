import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.List;
import java.util.function.Supplier;

public final class vf<E> implements Codec<Supplier<E>> {
   private final vj<? extends gm<E>> a;
   private final Codec<E> b;
   private final boolean c;

   public static <E> vf<E> a(vj<? extends gm<E>> var0, Codec<E> var1) {
      return a(_snowman, _snowman, true);
   }

   public static <E> Codec<List<Supplier<E>>> b(vj<? extends gm<E>> var0, Codec<E> var1) {
      return Codec.either(a(_snowman, _snowman, false).listOf(), _snowman.xmap(var0x -> () -> var0x, Supplier::get).listOf())
         .xmap(var0x -> (List)var0x.map(var0xx -> var0xx, var0xx -> var0xx), Either::left);
   }

   private static <E> vf<E> a(vj<? extends gm<E>> var0, Codec<E> var1, boolean var2) {
      return new vf<>(_snowman, _snowman, _snowman);
   }

   private vf(vj<? extends gm<E>> var1, Codec<E> var2, boolean var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   public <T> DataResult<T> a(Supplier<E> var1, DynamicOps<T> var2, T var3) {
      return _snowman instanceof vi ? ((vi)_snowman).a(_snowman.get(), _snowman, this.a, this.b) : this.b.encode(_snowman.get(), _snowman, _snowman);
   }

   public <T> DataResult<Pair<Supplier<E>, T>> decode(DynamicOps<T> var1, T var2) {
      return _snowman instanceof vh ? ((vh)_snowman).a(_snowman, this.a, this.b, this.c) : this.b.decode(_snowman, _snowman).map(var0 -> var0.mapFirst(var0x -> () -> var0x));
   }

   @Override
   public String toString() {
      return "RegistryFileCodec[" + this.a + " " + this.b + "]";
   }
}
