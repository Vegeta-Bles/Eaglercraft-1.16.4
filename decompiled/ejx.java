import com.mojang.blaze3d.systems.RenderSystem;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class ejx extends ejy {
   @Nullable
   private CompletableFuture<ejy.a> e;

   public ejx(ach var1, vk var2, Executor var3) {
      super(_snowman);
      this.e = CompletableFuture.supplyAsync(() -> ejy.a.a(_snowman, _snowman), _snowman);
   }

   @Override
   protected ejy.a b(ach var1) {
      if (this.e != null) {
         ejy.a _snowman = this.e.join();
         this.e = null;
         return _snowman;
      } else {
         return ejy.a.a(_snowman, this.d);
      }
   }

   public CompletableFuture<Void> a() {
      return this.e == null ? CompletableFuture.completedFuture(null) : this.e.thenApply(var0 -> null);
   }

   @Override
   public void a(ekd var1, ach var2, vk var3, Executor var4) {
      this.e = CompletableFuture.supplyAsync(() -> ejy.a.a(_snowman, this.d), x.f());
      this.e.thenRunAsync(() -> _snowman.a(this.d, this), a(_snowman));
   }

   private static Executor a(Executor var0) {
      return var1 -> _snowman.execute(() -> RenderSystem.recordRenderCall(var1::run));
   }
}
