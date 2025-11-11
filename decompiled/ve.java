import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;

public final class ve<E> implements Codec<gi<E>> {
   private final Codec<gi<E>> a;
   private final vj<? extends gm<E>> b;
   private final Codec<E> c;

   public static <E> ve<E> a(vj<? extends gm<E>> var0, Lifecycle var1, Codec<E> var2) {
      return new ve<>(_snowman, _snowman, _snowman);
   }

   private ve(vj<? extends gm<E>> var1, Lifecycle var2, Codec<E> var3) {
      this.a = gi.c(_snowman, _snowman, _snowman);
      this.b = _snowman;
      this.c = _snowman;
   }

   public <T> DataResult<T> a(gi<E> var1, DynamicOps<T> var2, T var3) {
      return this.a.encode(_snowman, _snowman, _snowman);
   }

   public <T> DataResult<Pair<gi<E>, T>> decode(DynamicOps<T> var1, T var2) {
      DataResult<Pair<gi<E>, T>> _snowman = this.a.decode(_snowman, _snowman);
      return _snowman instanceof vh ? _snowman.flatMap(var2x -> ((vh)_snowman).a((gi<E>)var2x.getFirst(), this.b, this.c).map(var1x -> Pair.of(var1x, var2x.getSecond()))) : _snowman;
   }

   @Override
   public String toString() {
      return "RegistryDataPackCodec[" + this.a + " " + this.b + " " + this.c + "]";
   }
}
