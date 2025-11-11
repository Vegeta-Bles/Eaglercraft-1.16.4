import com.mojang.blaze3d.systems.RenderSystem;
import java.io.Closeable;
import java.io.IOException;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ejy extends ejq {
   private static final Logger e = LogManager.getLogger();
   protected final vk d;

   public ejy(vk var1) {
      this.d = _snowman;
   }

   @Override
   public void a(ach var1) throws IOException {
      ejy.a _snowman = this.b(_snowman);
      _snowman.c();
      ell _snowmanx = _snowman.a();
      boolean _snowmanxx;
      boolean _snowmanxxx;
      if (_snowmanx != null) {
         _snowmanxx = _snowmanx.a();
         _snowmanxxx = _snowmanx.b();
      } else {
         _snowmanxx = false;
         _snowmanxxx = false;
      }

      det _snowmanxxxx = _snowman.b();
      if (!RenderSystem.isOnRenderThreadOrInit()) {
         RenderSystem.recordRenderCall(() -> this.a(_snowman, _snowman, _snowman));
      } else {
         this.a(_snowmanxxxx, _snowmanxx, _snowmanxxx);
      }
   }

   private void a(det var1, boolean var2, boolean var3) {
      dex.a(this.b(), 0, _snowman.a(), _snowman.b());
      _snowman.a(0, 0, 0, 0, 0, _snowman.a(), _snowman.b(), _snowman, _snowman, false, true);
   }

   protected ejy.a b(ach var1) {
      return ejy.a.a(_snowman, this.d);
   }

   public static class a implements Closeable {
      @Nullable
      private final ell a;
      @Nullable
      private final det b;
      @Nullable
      private final IOException c;

      public a(IOException var1) {
         this.c = _snowman;
         this.a = null;
         this.b = null;
      }

      public a(@Nullable ell var1, det var2) {
         this.c = null;
         this.a = _snowman;
         this.b = _snowman;
      }

      public static ejy.a a(ach var0, vk var1) {
         try (acg _snowman = _snowman.a(_snowman)) {
            det _snowmanx = det.a(_snowman.b());
            ell _snowmanxx = null;

            try {
               _snowmanxx = _snowman.a(ell.a);
            } catch (RuntimeException var17) {
               ejy.e.warn("Failed reading metadata of: {}", _snowman, var17);
            }

            return new ejy.a(_snowmanxx, _snowmanx);
         } catch (IOException var20) {
            return new ejy.a(var20);
         }
      }

      @Nullable
      public ell a() {
         return this.a;
      }

      public det b() throws IOException {
         if (this.c != null) {
            throw this.c;
         } else {
            return this.b;
         }
      }

      @Override
      public void close() {
         if (this.b != null) {
            this.b.close();
         }
      }

      public void c() throws IOException {
         if (this.c != null) {
            throw this.c;
         }
      }
   }
}
