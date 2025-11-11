import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.util.stream.Stream;

public final class vg<E> extends MapCodec<gm<E>> {
   private final vj<? extends gm<E>> a;

   public static <E> vg<E> a(vj<? extends gm<E>> var0) {
      return new vg<>(_snowman);
   }

   private vg(vj<? extends gm<E>> var1) {
      this.a = _snowman;
   }

   public <T> RecordBuilder<T> a(gm<E> var1, DynamicOps<T> var2, RecordBuilder<T> var3) {
      return _snowman;
   }

   public <T> DataResult<gm<E>> decode(DynamicOps<T> var1, MapLike<T> var2) {
      return _snowman instanceof vh ? ((vh)_snowman).a(this.a) : DataResult.error("Not a registry ops");
   }

   public String toString() {
      return "RegistryLookupCodec[" + this.a + "]";
   }

   public <T> Stream<T> keys(DynamicOps<T> var1) {
      return Stream.empty();
   }
}
