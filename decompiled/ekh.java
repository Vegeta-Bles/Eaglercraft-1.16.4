import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ekh implements aby {
   private static final Logger a = LogManager.getLogger();
   private static final Pattern b = Pattern.compile("^[a-fA-F0-9]{40}$");
   private final abm c;
   private final File d;
   private final ReentrantLock e = new ReentrantLock();
   private final ekg f;
   @Nullable
   private CompletableFuture<?> g;
   @Nullable
   private abu h;

   public ekh(File var1, ekg var2) {
      this.d = _snowman;
      this.f = _snowman;
      this.c = new eki(_snowman);
   }

   @Override
   public void a(Consumer<abu> var1, abu.a var2) {
      abu _snowman = abu.a("vanilla", true, () -> this.c, _snowman, abu.b.b, abx.b);
      if (_snowman != null) {
         _snowman.accept(_snowman);
      }

      if (this.h != null) {
         _snowman.accept(this.h);
      }

      abu _snowmanx = this.a(_snowman);
      if (_snowmanx != null) {
         _snowman.accept(_snowmanx);
      }
   }

   public abm a() {
      return this.c;
   }

   private static Map<String, String> c() {
      Map<String, String> _snowman = Maps.newHashMap();
      _snowman.put("X-Minecraft-Username", djz.C().J().c());
      _snowman.put("X-Minecraft-UUID", djz.C().J().b());
      _snowman.put("X-Minecraft-Version", w.a().getName());
      _snowman.put("X-Minecraft-Version-ID", w.a().getId());
      _snowman.put("X-Minecraft-Pack-Format", String.valueOf(w.a().getPackVersion()));
      _snowman.put("User-Agent", "Minecraft Java/" + w.a().getName());
      return _snowman;
   }

   public CompletableFuture<?> a(String var1, String var2) {
      String _snowman = DigestUtils.sha1Hex(_snowman);
      String _snowmanx = b.matcher(_snowman).matches() ? _snowman : "";
      this.e.lock();

      CompletableFuture var13;
      try {
         this.b();
         this.d();
         File _snowmanxx = new File(this.d, _snowman);
         CompletableFuture<?> _snowmanxxx;
         if (_snowmanxx.exists()) {
            _snowmanxxx = CompletableFuture.completedFuture("");
         } else {
            dor _snowmanxxxx = new dor();
            Map<String, String> _snowmanxxxxx = c();
            djz _snowmanxxxxxx = djz.C();
            _snowmanxxxxxx.g(() -> _snowman.a(_snowman));
            _snowmanxxx = aff.a(_snowmanxx, _snowman, _snowmanxxxxx, 104857600, _snowmanxxxx, _snowmanxxxxxx.L());
         }

         this.g = _snowmanxxx.<Void>thenCompose(
               var3x -> !this.a(_snowman, _snowman) ? x.a(new RuntimeException("Hash check failure for file " + _snowman + ", see log")) : this.a(_snowman, abx.d)
            )
            .whenComplete((var1x, var2x) -> {
               if (var2x != null) {
                  a.warn("Pack application failed: {}, deleting file {}", var2x.getMessage(), _snowman);
                  a(_snowman);
               }
            });
         var13 = this.g;
      } finally {
         this.e.unlock();
      }

      return var13;
   }

   private static void a(File var0) {
      try {
         Files.delete(_snowman.toPath());
      } catch (IOException var2) {
         a.warn("Failed to delete file {}: {}", _snowman, var2.getMessage());
      }
   }

   public void b() {
      this.e.lock();

      try {
         if (this.g != null) {
            this.g.cancel(true);
         }

         this.g = null;
         if (this.h != null) {
            this.h = null;
            djz.C().D();
         }
      } finally {
         this.e.unlock();
      }
   }

   private boolean a(String var1, File var2) {
      try (FileInputStream _snowman = new FileInputStream(_snowman)) {
         String _snowmanx = DigestUtils.sha1Hex(_snowman);
         if (_snowman.isEmpty()) {
            a.info("Found file {} without verification hash", _snowman);
            return true;
         }

         if (_snowmanx.toLowerCase(Locale.ROOT).equals(_snowman.toLowerCase(Locale.ROOT))) {
            a.info("Found file {} matching requested hash {}", _snowman, _snowman);
            return true;
         }

         a.warn("File {} had wrong hash (expected {}, found {}).", _snowman, _snowman, _snowmanx);
      } catch (IOException var17) {
         a.warn("File {} couldn't be hashed.", _snowman, var17);
      }

      return false;
   }

   private void d() {
      try {
         List<File> _snowman = Lists.newArrayList(FileUtils.listFiles(this.d, TrueFileFilter.TRUE, null));
         _snowman.sort(LastModifiedFileComparator.LASTMODIFIED_REVERSE);
         int _snowmanx = 0;

         for (File _snowmanxx : _snowman) {
            if (_snowmanx++ >= 10) {
               a.info("Deleting old server resource pack {}", _snowmanxx.getName());
               FileUtils.deleteQuietly(_snowmanxx);
            }
         }
      } catch (IllegalArgumentException var5) {
         a.error("Error while deleting old server resource pack : {}", var5.getMessage());
      }
   }

   public CompletableFuture<Void> a(File var1, abx var2) {
      abo _snowman;
      try (abh _snowmanx = new abh(_snowman)) {
         _snowman = _snowmanx.a(abo.a);
      } catch (IOException var17) {
         return x.a(new IOException(String.format("Invalid resourcepack at %s", _snowman), var17));
      }

      a.info("Applying server pack {}", _snowman);
      this.h = new abu("server", true, () -> new abh(_snowman), new of("resourcePack.server.name"), _snowman.a(), abv.a(_snowman.b()), abu.b.a, true, _snowman);
      return djz.C().D();
   }

   @Nullable
   private abu a(abu.a var1) {
      abu _snowman = null;
      File _snowmanx = this.f.a(new vk("resourcepacks/programmer_art.zip"));
      if (_snowmanx != null && _snowmanx.isFile()) {
         _snowman = a(_snowman, () -> c(_snowman));
      }

      if (_snowman == null && w.d) {
         File _snowmanxx = this.f.a("../resourcepacks/programmer_art");
         if (_snowmanxx != null && _snowmanxx.isDirectory()) {
            _snowman = a(_snowman, () -> b(_snowman));
         }
      }

      return _snowman;
   }

   @Nullable
   private static abu a(abu.a var0, Supplier<abj> var1) {
      return abu.a("programer_art", false, _snowman, _snowman, abu.b.a, abx.b);
   }

   private static abi b(File var0) {
      return new abi(_snowman) {
         @Override
         public String a() {
            return "Programmer Art";
         }
      };
   }

   private static abj c(File var0) {
      return new abh(_snowman) {
         @Override
         public String a() {
            return "Programmer Art";
         }
      };
   }
}
