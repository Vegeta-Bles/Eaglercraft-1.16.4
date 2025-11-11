import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class vr {
   private static final Logger a = LogManager.getLogger();
   private final Path b;
   private final boolean c;

   public vr(Path var1) {
      this.b = _snowman;
      this.c = w.d || this.b();
   }

   private boolean b() {
      try (InputStream _snowman = Files.newInputStream(this.b)) {
         Properties _snowmanx = new Properties();
         _snowmanx.load(_snowman);
         return Boolean.parseBoolean(_snowmanx.getProperty("eula", "false"));
      } catch (Exception var16) {
         a.warn("Failed to load {}", this.b);
         this.c();
         return false;
      }
   }

   public boolean a() {
      return this.c;
   }

   private void c() {
      if (!w.d) {
         try (OutputStream _snowman = Files.newOutputStream(this.b)) {
            Properties _snowmanx = new Properties();
            _snowmanx.setProperty("eula", "false");
            _snowmanx.store(
               _snowman, "By changing the setting below to TRUE you are indicating your agreement to our EULA (https://account.mojang.com/documents/minecraft_eula)."
            );
         } catch (Exception var14) {
            a.warn("Failed to save {}", this.b, var14);
         }
      }
   }
}
