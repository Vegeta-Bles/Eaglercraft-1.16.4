import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.util.concurrent.Executor;

public abstract class ejq implements AutoCloseable {
   protected int a = -1;
   protected boolean b;
   protected boolean c;

   public ejq() {
   }

   public void a(boolean var1, boolean var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      this.b = _snowman;
      this.c = _snowman;
      int _snowman;
      int _snowmanx;
      if (_snowman) {
         _snowman = _snowman ? 9987 : 9729;
         _snowmanx = 9729;
      } else {
         _snowman = _snowman ? 9986 : 9728;
         _snowmanx = 9728;
      }

      dem.b(3553, 10241, _snowman);
      dem.b(3553, 10240, _snowmanx);
   }

   public int b() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (this.a == -1) {
         this.a = dex.a();
      }

      return this.a;
   }

   public void c() {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> {
            if (this.a != -1) {
               dex.a(this.a);
               this.a = -1;
            }
         });
      } else if (this.a != -1) {
         dex.a(this.a);
         this.a = -1;
      }
   }

   public abstract void a(ach var1) throws IOException;

   public void d() {
      if (!RenderSystem.isOnRenderThreadOrInit()) {
         RenderSystem.recordRenderCall(() -> dem.s(this.b()));
      } else {
         dem.s(this.b());
      }
   }

   public void a(ekd var1, ach var2, vk var3, Executor var4) {
      _snowman.a(_snowman, this);
   }

   @Override
   public void close() {
   }
}
