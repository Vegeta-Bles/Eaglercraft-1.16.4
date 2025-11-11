import com.mojang.datafixers.util.Either;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public interface aod<Msg> extends AutoCloseable {
   String bj();

   void a(Msg var1);

   @Override
   default void close() {
   }

   default <Source> CompletableFuture<Source> b(Function<? super aod<Source>, ? extends Msg> var1) {
      CompletableFuture<Source> _snowman = new CompletableFuture<>();
      Msg _snowmanx = (Msg)_snowman.apply(a("ask future procesor handle", _snowman::complete));
      this.a(_snowmanx);
      return _snowman;
   }

   default <Source> CompletableFuture<Source> c(Function<? super aod<Either<Source, Exception>>, ? extends Msg> var1) {
      CompletableFuture<Source> _snowman = new CompletableFuture<>();
      Msg _snowmanx = (Msg)_snowman.apply(a("ask future procesor handle", var1x -> {
         var1x.ifLeft(_snowman::complete);
         var1x.ifRight(_snowman::completeExceptionally);
      }));
      this.a(_snowmanx);
      return _snowman;
   }

   static <Msg> aod<Msg> a(final String var0, final Consumer<Msg> var1) {
      return new aod<Msg>() {
         @Override
         public String bj() {
            return _snowman;
         }

         @Override
         public void a(Msg var1x) {
            _snowman.accept(_snowman);
         }

         @Override
         public String toString() {
            return _snowman;
         }
      };
   }
}
