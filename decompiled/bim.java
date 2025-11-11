import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public interface bim {
   bim a = new bim() {
      @Override
      public <T> Optional<T> a(BiFunction<brx, fx, T> var1) {
         return Optional.empty();
      }
   };

   static bim a(final brx var0, final fx var1) {
      return new bim() {
         @Override
         public <T> Optional<T> a(BiFunction<brx, fx, T> var1x) {
            return Optional.of(_snowman.apply(_snowman, _snowman));
         }
      };
   }

   <T> Optional<T> a(BiFunction<brx, fx, T> var1);

   default <T> T a(BiFunction<brx, fx, T> var1, T var2) {
      return this.a(_snowman).orElse(_snowman);
   }

   default void a(BiConsumer<brx, fx> var1) {
      this.a((var1x, var2) -> {
         _snowman.accept(var1x, var2);
         return Optional.empty();
      });
   }
}
