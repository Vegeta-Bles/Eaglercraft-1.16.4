import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class jp implements hm {
   @Nullable
   private static final Path b = null;
   private static final Logger c = LogManager.getLogger();
   private final hl d;
   private final List<jp.a> e = Lists.newArrayList();

   public jp(hl var1) {
      this.d = _snowman;
   }

   public jp a(jp.a var1) {
      this.e.add(_snowman);
      return this;
   }

   private md a(String var1, md var2) {
      md _snowman = _snowman;

      for (jp.a _snowmanx : this.e) {
         _snowman = _snowmanx.a(_snowman, _snowman);
      }

      return _snowman;
   }

   @Override
   public void a(hn var1) throws IOException {
      Path _snowman = this.d.b();
      List<CompletableFuture<jp.b>> _snowmanx = Lists.newArrayList();

      for (Path _snowmanxx : this.d.a()) {
         Files.walk(_snowmanxx)
            .filter(var0 -> var0.toString().endsWith(".snbt"))
            .forEach(var3x -> _snowman.add(CompletableFuture.supplyAsync(() -> this.a(var3x, this.a(_snowman, var3x)), x.f())));
      }

      x.b(_snowmanx).join().stream().filter(Objects::nonNull).forEach(var3x -> this.a(_snowman, var3x, _snowman));
   }

   @Override
   public String a() {
      return "SNBT -> NBT";
   }

   private String a(Path var1, Path var2) {
      String _snowman = _snowman.relativize(_snowman).toString().replaceAll("\\\\", "/");
      return _snowman.substring(0, _snowman.length() - ".snbt".length());
   }

   @Nullable
   private jp.b a(Path var1, String var2) {
      try (BufferedReader _snowman = Files.newBufferedReader(_snowman)) {
         String _snowmanx = IOUtils.toString(_snowman);
         md _snowmanxx = this.a(_snowman, mu.a(_snowmanx));
         ByteArrayOutputStream _snowmanxxx = new ByteArrayOutputStream();
         mn.a(_snowmanxx, _snowmanxxx);
         byte[] _snowmanxxxx = _snowmanxxx.toByteArray();
         String _snowmanxxxxx = a.hashBytes(_snowmanxxxx).toString();
         String _snowmanxxxxxx;
         if (b != null) {
            _snowmanxxxxxx = _snowmanxx.a("    ", 0).getString() + "\n";
         } else {
            _snowmanxxxxxx = null;
         }

         return new jp.b(_snowman, _snowmanxxxx, _snowmanxxxxxx, _snowmanxxxxx);
      } catch (CommandSyntaxException var24) {
         c.error("Couldn't convert {} from SNBT to NBT at {} as it's invalid SNBT", _snowman, _snowman, var24);
      } catch (IOException var25) {
         c.error("Couldn't convert {} from SNBT to NBT at {}", _snowman, _snowman, var25);
      }

      return null;
   }

   private void a(hn var1, jp.b var2, Path var3) {
      if (_snowman.c != null) {
         Path _snowman = b.resolve(_snowman.a + ".snbt");

         try {
            FileUtils.write(_snowman.toFile(), _snowman.c, StandardCharsets.UTF_8);
         } catch (IOException var18) {
            c.error("Couldn't write structure SNBT {} at {}", _snowman.a, _snowman, var18);
         }
      }

      Path _snowman = _snowman.resolve(_snowman.a + ".nbt");

      try {
         if (!Objects.equals(_snowman.a(_snowman), _snowman.d) || !Files.exists(_snowman)) {
            Files.createDirectories(_snowman.getParent());

            try (OutputStream _snowmanx = Files.newOutputStream(_snowman)) {
               _snowmanx.write(_snowman.b);
            }
         }

         _snowman.a(_snowman, _snowman.d);
      } catch (IOException var20) {
         c.error("Couldn't write structure {} at {}", _snowman.a, _snowman, var20);
      }
   }

   @FunctionalInterface
   public interface a {
      md a(String var1, md var2);
   }

   static class b {
      private final String a;
      private final byte[] b;
      @Nullable
      private final String c;
      private final String d;

      public b(String var1, byte[] var2, @Nullable String var3, String var4) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }
   }
}
