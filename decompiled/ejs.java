import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ejs extends ejq {
   private static final Logger d = LogManager.getLogger();
   @Nullable
   private det e;

   public ejs(det var1) {
      this.e = _snowman;
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> {
            dex.a(this.b(), this.e.a(), this.e.b());
            this.a();
         });
      } else {
         dex.a(this.b(), this.e.a(), this.e.b());
         this.a();
      }
   }

   public ejs(int var1, int var2, boolean var3) {
      RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
      this.e = new det(_snowman, _snowman, _snowman);
      dex.a(this.b(), this.e.a(), this.e.b());
   }

   @Override
   public void a(ach var1) {
   }

   @Override
   public void a() {
      if (this.e != null) {
         this.d();
         this.e.a(0, 0, 0, false);
      } else {
         d.warn("Trying to upload disposed texture {}", this.b());
      }
   }

   @Nullable
   public det e() {
      return this.e;
   }

   public void a(det var1) {
      if (this.e != null) {
         this.e.close();
      }

      this.e = _snowman;
   }

   @Override
   public void close() {
      if (this.e != null) {
         this.e.close();
         this.c();
         this.e = null;
      }
   }
}
