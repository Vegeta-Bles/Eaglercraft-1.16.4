import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public interface aci extends acc {
   @Override
   default CompletableFuture<Void> a(acc.a var1, ach var2, anw var3, anw var4, Executor var5, Executor var6) {
      return _snowman.a(afx.a).thenRunAsync(() -> {
         _snowman.a();
         _snowman.a("listener");
         this.a(_snowman);
         _snowman.c();
         _snowman.b();
      }, _snowman);
   }

   void a(ach var1);
}
