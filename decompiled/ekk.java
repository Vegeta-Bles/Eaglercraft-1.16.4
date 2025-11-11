import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ekk extends ekg {
   private final File b;

   public ekk(File var1) {
      this.b = _snowman;
   }

   @Override
   public File a(vk var1) {
      return new File(this.b, _snowman.toString().replace(':', '/'));
   }

   @Override
   public File a(String var1) {
      return new File(this.b, _snowman);
   }

   @Override
   public Collection<vk> a(String var1, String var2, int var3, Predicate<String> var4) {
      Path _snowman = this.b.toPath().resolve(_snowman);

      try (Stream<Path> _snowmanx = Files.walk(_snowman.resolve(_snowman), _snowman)) {
         return _snowmanx.filter(var0 -> Files.isRegularFile(var0))
            .filter(var0 -> !var0.endsWith(".mcmeta"))
            .filter(var1x -> _snowman.test(var1x.getFileName().toString()))
            .map(var2x -> new vk(_snowman, _snowman.relativize(var2x).toString().replaceAll("\\\\", "/")))
            .collect(Collectors.toList());
      } catch (NoSuchFileException var21) {
      } catch (IOException var22) {
         a.warn("Unable to getFiles on {}", _snowman, var22);
      }

      return Collections.emptyList();
   }
}
