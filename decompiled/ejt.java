import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ejt extends ejy {
   private static final Logger e = LogManager.getLogger();
   @Nullable
   private final File f;
   private final String g;
   private final boolean h;
   @Nullable
   private final Runnable i;
   @Nullable
   private CompletableFuture<?> j;
   private boolean k;

   public ejt(@Nullable File var1, String var2, vk var3, boolean var4, @Nullable Runnable var5) {
      super(_snowman);
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
   }

   private void a(det var1) {
      if (this.i != null) {
         this.i.run();
      }

      djz.C().execute(() -> {
         this.k = true;
         if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> this.b(_snowman));
         } else {
            this.b(_snowman);
         }
      });
   }

   private void b(det var1) {
      dex.a(this.b(), _snowman.a(), _snowman.b());
      _snowman.a(0, 0, 0, true);
   }

   @Override
   public void a(ach var1) throws IOException {
      djz.C().execute(() -> {
         if (!this.k) {
            try {
               super.a(_snowman);
            } catch (IOException var3x) {
               e.warn("Failed to load texture: {}", this.d, var3x);
            }

            this.k = true;
         }
      });
      if (this.j == null) {
         det _snowman;
         if (this.f != null && this.f.isFile()) {
            e.debug("Loading http texture from local cache ({})", this.f);
            FileInputStream _snowmanx = new FileInputStream(this.f);
            _snowman = this.a(_snowmanx);
         } else {
            _snowman = null;
         }

         if (_snowman != null) {
            this.a(_snowman);
         } else {
            this.j = CompletableFuture.runAsync(() -> {
               HttpURLConnection _snowmanx = null;
               e.debug("Downloading http texture from {} to {}", this.g, this.f);

               try {
                  _snowmanx = (HttpURLConnection)new URL(this.g).openConnection(djz.C().L());
                  _snowmanx.setDoInput(true);
                  _snowmanx.setDoOutput(false);
                  _snowmanx.connect();
                  if (_snowmanx.getResponseCode() / 100 == 2) {
                     InputStream _snowmanx;
                     if (this.f != null) {
                        FileUtils.copyInputStreamToFile(_snowmanx.getInputStream(), this.f);
                        _snowmanx = new FileInputStream(this.f);
                     } else {
                        _snowmanx = _snowmanx.getInputStream();
                     }

                     djz.C().execute(() -> {
                        det _snowmanxx = this.a(_snowman);
                        if (_snowmanxx != null) {
                           this.a(_snowmanxx);
                        }
                     });
                     return;
                  }
               } catch (Exception var6) {
                  e.error("Couldn't download http texture", var6);
                  return;
               } finally {
                  if (_snowmanx != null) {
                     _snowmanx.disconnect();
                  }
               }
            }, x.f());
         }
      }
   }

   @Nullable
   private det a(InputStream var1) {
      det _snowman = null;

      try {
         _snowman = det.a(_snowman);
         if (this.h) {
            _snowman = c(_snowman);
         }
      } catch (IOException var4) {
         e.warn("Error while loading the skin texture", var4);
      }

      return _snowman;
   }

   private static det c(det var0) {
      boolean _snowman = _snowman.b() == 32;
      if (_snowman) {
         det _snowmanx = new det(64, 64, true);
         _snowmanx.a(_snowman);
         _snowman.close();
         _snowman = _snowmanx;
         _snowmanx.a(0, 32, 64, 32, 0);
         _snowmanx.a(4, 16, 16, 32, 4, 4, true, false);
         _snowmanx.a(8, 16, 16, 32, 4, 4, true, false);
         _snowmanx.a(0, 20, 24, 32, 4, 12, true, false);
         _snowmanx.a(4, 20, 16, 32, 4, 12, true, false);
         _snowmanx.a(8, 20, 8, 32, 4, 12, true, false);
         _snowmanx.a(12, 20, 16, 32, 4, 12, true, false);
         _snowmanx.a(44, 16, -8, 32, 4, 4, true, false);
         _snowmanx.a(48, 16, -8, 32, 4, 4, true, false);
         _snowmanx.a(40, 20, 0, 32, 4, 12, true, false);
         _snowmanx.a(44, 20, -8, 32, 4, 12, true, false);
         _snowmanx.a(48, 20, -16, 32, 4, 12, true, false);
         _snowmanx.a(52, 20, -8, 32, 4, 12, true, false);
      }

      b(_snowman, 0, 0, 32, 16);
      if (_snowman) {
         a(_snowman, 32, 0, 64, 32);
      }

      b(_snowman, 0, 16, 64, 32);
      b(_snowman, 16, 48, 48, 64);
      return _snowman;
   }

   private static void a(det var0, int var1, int var2, int var3, int var4) {
      for (int _snowman = _snowman; _snowman < _snowman; _snowman++) {
         for (int _snowmanx = _snowman; _snowmanx < _snowman; _snowmanx++) {
            int _snowmanxx = _snowman.a(_snowman, _snowmanx);
            if ((_snowmanxx >> 24 & 0xFF) < 128) {
               return;
            }
         }
      }

      for (int _snowman = _snowman; _snowman < _snowman; _snowman++) {
         for (int _snowmanxx = _snowman; _snowmanxx < _snowman; _snowmanxx++) {
            _snowman.a(_snowman, _snowmanxx, _snowman.a(_snowman, _snowmanxx) & 16777215);
         }
      }
   }

   private static void b(det var0, int var1, int var2, int var3, int var4) {
      for (int _snowman = _snowman; _snowman < _snowman; _snowman++) {
         for (int _snowmanx = _snowman; _snowmanx < _snowman; _snowmanx++) {
            _snowman.a(_snowman, _snowmanx, _snowman.a(_snowman, _snowmanx) | 0xFF000000);
         }
      }
   }
}
