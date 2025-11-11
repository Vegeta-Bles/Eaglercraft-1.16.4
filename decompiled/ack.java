import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public abstract class ack<T> implements acc {
   public ack() {
   }

   @Override
   public final CompletableFuture<Void> a(acc.a var1, ach var2, anw var3, anw var4, Executor var5, Executor var6) {
      return CompletableFuture.<T>supplyAsync(() -> this.b(_snowman, _snowman), _snowman).thenCompose(_snowman::a).thenAcceptAsync(var3x -> this.a((T)var3x, _snowman, _snowman), _snowman);
   }

   protected abstract T b(ach var1, anw var2);

   protected abstract void a(T var1, ach var2, anw var3);
}
