import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
interface czi {
   czi a = (var0, var1) -> false;
   czi b = (var0, var1) -> true;

   boolean expand(cyv var1, Consumer<czp> var2);

   default czi a(czi var1) {
      Objects.requireNonNull(_snowman);
      return (var2, var3) -> this.expand(var2, var3) && _snowman.expand(var2, var3);
   }

   default czi b(czi var1) {
      Objects.requireNonNull(_snowman);
      return (var2, var3) -> this.expand(var2, var3) || _snowman.expand(var2, var3);
   }
}
