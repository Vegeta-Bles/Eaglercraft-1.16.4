import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;

public class dfp implements AutoCloseable {
   private int a;
   private final dfr b;
   private int c;

   public dfp(dfr var1) {
      this.b = _snowman;
      RenderSystem.glGenBuffers(var1x -> this.a = var1x);
   }

   public void a() {
      RenderSystem.glBindBuffer(34962, () -> this.a);
   }

   public void a(dfh var1) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> this.c(_snowman));
      } else {
         this.c(_snowman);
      }
   }

   public CompletableFuture<Void> b(dfh var1) {
      if (!RenderSystem.isOnRenderThread()) {
         return CompletableFuture.runAsync(() -> this.c(_snowman), var0 -> RenderSystem.recordRenderCall(var0::run));
      } else {
         this.c(_snowman);
         return CompletableFuture.completedFuture(null);
      }
   }

   private void c(dfh var1) {
      Pair<dfh.a, ByteBuffer> _snowman = _snowman.f();
      if (this.a != -1) {
         ByteBuffer _snowmanx = (ByteBuffer)_snowman.getSecond();
         this.c = _snowmanx.remaining() / this.b.b();
         this.a();
         RenderSystem.glBufferData(34962, _snowmanx, 35044);
         b();
      }
   }

   public void a(b var1, int var2) {
      RenderSystem.pushMatrix();
      RenderSystem.loadIdentity();
      RenderSystem.multMatrix(_snowman);
      RenderSystem.drawArrays(_snowman, 0, this.c);
      RenderSystem.popMatrix();
   }

   public static void b() {
      RenderSystem.glBindBuffer(34962, () -> 0);
   }

   @Override
   public void close() {
      if (this.a >= 0) {
         RenderSystem.glDeleteBuffers(this.a);
         this.a = -1;
      }
   }
}
