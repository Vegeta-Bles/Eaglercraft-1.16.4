import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dkh {
   private static final Logger a = LogManager.getLogger();
   private static final DateFormat b = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

   public static void a(File var0, int var1, int var2, deg var3, Consumer<nr> var4) {
      a(_snowman, null, _snowman, _snowman, _snowman, _snowman);
   }

   public static void a(File var0, @Nullable String var1, int var2, int var3, deg var4, Consumer<nr> var5) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> b(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman));
      } else {
         b(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   private static void b(File var0, @Nullable String var1, int var2, int var3, deg var4, Consumer<nr> var5) {
      det _snowman = a(_snowman, _snowman, _snowman);
      File _snowmanx = new File(_snowman, "screenshots");
      _snowmanx.mkdir();
      File _snowmanxx;
      if (_snowman == null) {
         _snowmanxx = a(_snowmanx);
      } else {
         _snowmanxx = new File(_snowmanx, _snowman);
      }

      x.g().execute(() -> {
         try {
            _snowman.a(_snowman);
            nr _snowmanxxx = new oe(_snowman.getName()).a(k.t).a(var1x -> var1x.a(new np(np.a.b, _snowman.getAbsolutePath())));
            _snowman.accept(new of("screenshot.success", _snowmanxxx));
         } catch (Exception var7x) {
            a.warn("Couldn't save screenshot", var7x);
            _snowman.accept(new of("screenshot.failure", var7x.getMessage()));
         } finally {
            _snowman.close();
         }
      });
   }

   public static det a(int var0, int var1, deg var2) {
      _snowman = _snowman.a;
      _snowman = _snowman.b;
      det _snowman = new det(_snowman, _snowman, false);
      RenderSystem.bindTexture(_snowman.f());
      _snowman.a(0, true);
      _snowman.f();
      return _snowman;
   }

   private static File a(File var0) {
      String _snowman = b.format(new Date());
      int _snowmanx = 1;

      while (true) {
         File _snowmanxx = new File(_snowman, _snowman + (_snowmanx == 1 ? "" : "_" + _snowmanx) + ".png");
         if (!_snowmanxx.exists()) {
            return _snowmanxx;
         }

         _snowmanx++;
      }
   }
}
