import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface abc {
   void a();

   void b();

   CompletableFuture<Optional<String>> a(String var1);

   CompletableFuture<Optional<List<String>>> a(List<String> var1);
}
