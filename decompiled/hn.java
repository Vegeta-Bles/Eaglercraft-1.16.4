import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class hn {
   private static final Logger a = LogManager.getLogger();
   private final Path b;
   private final Path c;
   private int d;
   private final Map<Path, String> e = Maps.newHashMap();
   private final Map<Path, String> f = Maps.newHashMap();
   private final Set<Path> g = Sets.newHashSet();

   public hn(Path var1, String var2) throws IOException {
      this.b = _snowman;
      Path _snowman = _snowman.resolve(".cache");
      Files.createDirectories(_snowman);
      this.c = _snowman.resolve(_snowman);
      this.c().forEach(var1x -> {
         String var10000 = this.e.put(var1x, "");
      });
      if (Files.isReadable(this.c)) {
         IOUtils.readLines(Files.newInputStream(this.c), Charsets.UTF_8).forEach(var2x -> {
            int _snowmanx = var2x.indexOf(32);
            this.e.put(_snowman.resolve(var2x.substring(_snowmanx + 1)), var2x.substring(0, _snowmanx));
         });
      }
   }

   public void a() throws IOException {
      this.b();

      Writer _snowman;
      try {
         _snowman = Files.newBufferedWriter(this.c);
      } catch (IOException var3) {
         a.warn("Unable write cachefile {}: {}", this.c, var3.toString());
         return;
      }

      IOUtils.writeLines(
         this.f.entrySet().stream().map(var1x -> var1x.getValue() + ' ' + this.b.relativize(var1x.getKey())).collect(Collectors.toList()),
         System.lineSeparator(),
         _snowman
      );
      _snowman.close();
      a.debug("Caching: cache hits: {}, created: {} removed: {}", this.d, this.f.size() - this.d, this.e.size());
   }

   @Nullable
   public String a(Path var1) {
      return this.e.get(_snowman);
   }

   public void a(Path var1, String var2) {
      this.f.put(_snowman, _snowman);
      if (Objects.equals(this.e.remove(_snowman), _snowman)) {
         this.d++;
      }
   }

   public boolean b(Path var1) {
      return this.e.containsKey(_snowman);
   }

   public void c(Path var1) {
      this.g.add(_snowman);
   }

   private void b() throws IOException {
      this.c().forEach(var1 -> {
         if (this.b(var1) && !this.g.contains(var1)) {
            try {
               Files.delete(var1);
            } catch (IOException var3) {
               a.debug("Unable to delete: {} ({})", var1, var3.toString());
            }
         }
      });
   }

   private Stream<Path> c() throws IOException {
      return Files.walk(this.b).filter(var1 -> !Objects.equals(this.c, var1) && !Files.isDirectory(var1));
   }
}
