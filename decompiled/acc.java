import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public interface acc {
   CompletableFuture<Void> a(acc.a var1, ach var2, anw var3, anw var4, Executor var5, Executor var6);

   default String c() {
      return this.getClass().getSimpleName();
   }

   public interface a {
      <T> CompletableFuture<T> a(T var1);
   }
}
