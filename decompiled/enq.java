import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class enq {
   private final ach a;
   private final Map<vk, CompletableFuture<ddz>> b = Maps.newHashMap();

   public enq(ach var1) {
      this.a = _snowman;
   }

   public CompletableFuture<ddz> a(vk var1) {
      return this.b.computeIfAbsent(_snowman, var1x -> CompletableFuture.supplyAsync(() -> {
            try (
               acg _snowman = this.a.a(var1x);
               InputStream _snowmanx = _snowman.b();
               ddx _snowmanxx = new ddx(_snowmanx);
            ) {
               ByteBuffer _snowmanxxx = _snowmanxx.b();
               return new ddz(_snowmanxxx, _snowmanxx.a());
            } catch (IOException var62) {
               throw new CompletionException(var62);
            }
         }, x.f()));
   }

   public CompletableFuture<enm> a(vk var1, boolean var2) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            acg _snowman = this.a.a(_snowman);
            InputStream _snowmanx = _snowman.b();
            return (enm)(_snowman ? new eno(ddx::new, _snowmanx) : new ddx(_snowmanx));
         } catch (IOException var5) {
            throw new CompletionException(var5);
         }
      }, x.f());
   }

   public void a() {
      this.b.values().forEach(var0 -> var0.thenAccept(ddz::b));
      this.b.clear();
   }

   public CompletableFuture<?> a(Collection<emq> var1) {
      return CompletableFuture.allOf(_snowman.stream().map(var1x -> this.a(var1x.b())).toArray(CompletableFuture[]::new));
   }
}
