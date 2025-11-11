import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class csw {
   private static final Logger a = LogManager.getLogger();
   private final Map<vk, ctb> b = Maps.newHashMap();
   private final DataFixer c;
   private ach d;
   private final Path e;

   public csw(ach var1, cyg.a var2, DataFixer var3) {
      this.d = _snowman;
      this.c = _snowman;
      this.e = _snowman.a(cye.f).normalize();
   }

   public ctb a(vk var1) {
      ctb _snowman = this.b(_snowman);
      if (_snowman == null) {
         _snowman = new ctb();
         this.b.put(_snowman, _snowman);
      }

      return _snowman;
   }

   @Nullable
   public ctb b(vk var1) {
      return this.b.computeIfAbsent(_snowman, var1x -> {
         ctb _snowman = this.f(var1x);
         return _snowman != null ? _snowman : this.e(var1x);
      });
   }

   public void a(ach var1) {
      this.d = _snowman;
      this.b.clear();
   }

   @Nullable
   private ctb e(vk var1) {
      vk _snowman = new vk(_snowman.b(), "structures/" + _snowman.a() + ".nbt");

      try (acg _snowmanx = this.d.a(_snowman)) {
         return this.a(_snowmanx.b());
      } catch (FileNotFoundException var18) {
         return null;
      } catch (Throwable var19) {
         a.error("Couldn't load structure {}: {}", _snowman, var19.toString());
         return null;
      }
   }

   @Nullable
   private ctb f(vk var1) {
      if (!this.e.toFile().isDirectory()) {
         return null;
      } else {
         Path _snowman = this.b(_snowman, ".nbt");

         try (InputStream _snowmanx = new FileInputStream(_snowman.toFile())) {
            return this.a(_snowmanx);
         } catch (FileNotFoundException var18) {
            return null;
         } catch (IOException var19) {
            a.error("Couldn't load structure from {}", _snowman, var19);
            return null;
         }
      }
   }

   private ctb a(InputStream var1) throws IOException {
      md _snowman = mn.a(_snowman);
      return this.a(_snowman);
   }

   public ctb a(md var1) {
      if (!_snowman.c("DataVersion", 99)) {
         _snowman.b("DataVersion", 500);
      }

      ctb _snowman = new ctb();
      _snowman.b(mp.a(this.c, aga.f, _snowman, _snowman.h("DataVersion")));
      return _snowman;
   }

   public boolean c(vk var1) {
      ctb _snowman = this.b.get(_snowman);
      if (_snowman == null) {
         return false;
      } else {
         Path _snowmanx = this.b(_snowman, ".nbt");
         Path _snowmanxx = _snowmanx.getParent();
         if (_snowmanxx == null) {
            return false;
         } else {
            try {
               Files.createDirectories(Files.exists(_snowmanxx) ? _snowmanxx.toRealPath() : _snowmanxx);
            } catch (IOException var19) {
               a.error("Failed to create parent directory: {}", _snowmanxx);
               return false;
            }

            md _snowmanxxx = _snowman.a(new md());

            try (OutputStream _snowmanxxxx = new FileOutputStream(_snowmanx.toFile())) {
               mn.a(_snowmanxxx, _snowmanxxxx);
               return true;
            } catch (Throwable var21) {
               return false;
            }
         }
      }
   }

   public Path a(vk var1, String var2) {
      try {
         Path _snowman = this.e.resolve(_snowman.b());
         Path _snowmanx = _snowman.resolve("structures");
         return s.b(_snowmanx, _snowman.a(), _snowman);
      } catch (InvalidPathException var5) {
         throw new v("Invalid resource path: " + _snowman, var5);
      }
   }

   private Path b(vk var1, String var2) {
      if (_snowman.a().contains("//")) {
         throw new v("Invalid resource path: " + _snowman);
      } else {
         Path _snowman = this.a(_snowman, _snowman);
         if (_snowman.startsWith(this.e) && s.a(_snowman) && s.b(_snowman)) {
            return _snowman;
         } else {
            throw new v("Invalid resource path: " + _snowman);
         }
      }
   }

   public void d(vk var1) {
      this.b.remove(_snowman);
   }
}
