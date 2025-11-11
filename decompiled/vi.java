import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.Optional;

public class vi<T> extends vd<T> {
   private final gn b;

   public static <T> vi<T> a(DynamicOps<T> var0, gn var1) {
      return new vi<>(_snowman, _snowman);
   }

   private vi(DynamicOps<T> var1, gn var2) {
      super(_snowman);
      this.b = _snowman;
   }

   protected <E> DataResult<T> a(E var1, T var2, vj<? extends gm<E>> var3, Codec<E> var4) {
      Optional<gs<E>> _snowman = this.b.a(_snowman);
      if (_snowman.isPresent()) {
         gs<E> _snowmanx = _snowman.get();
         Optional<vj<E>> _snowmanxx = _snowmanx.c(_snowman);
         if (_snowmanxx.isPresent()) {
            vj<E> _snowmanxxx = _snowmanxx.get();
            return vk.a.encode(_snowmanxxx.a(), this.a, _snowman);
         }
      }

      return _snowman.encode(_snowman, this, _snowman);
   }
}
