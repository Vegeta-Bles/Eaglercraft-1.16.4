import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class jo implements hm {
   private static final Logger b = LogManager.getLogger();
   private final hl c;

   public jo(hl var1) {
      this.c = _snowman;
   }

   @Override
   public void a(hn var1) throws IOException {
      Path _snowman = this.c.b();

      for (Path _snowmanx : this.c.a()) {
         Files.walk(_snowmanx).filter(var0 -> var0.toString().endsWith(".nbt")).forEach(var3 -> a(var3, this.a(_snowman, var3), _snowman));
      }
   }

   @Override
   public String a() {
      return "NBT to SNBT";
   }

   private String a(Path var1, Path var2) {
      String _snowman = _snowman.relativize(_snowman).toString().replaceAll("\\\\", "/");
      return _snowman.substring(0, _snowman.length() - ".nbt".length());
   }

   @Nullable
   public static Path a(Path var0, String var1, Path var2) {
      try {
         md _snowman = mn.a(Files.newInputStream(_snowman));
         nr _snowmanx = _snowman.a("    ", 0);
         String _snowmanxx = _snowmanx.getString() + "\n";
         Path _snowmanxxx = _snowman.resolve(_snowman + ".snbt");
         Files.createDirectories(_snowmanxxx.getParent());

         try (BufferedWriter _snowmanxxxx = Files.newBufferedWriter(_snowmanxxx)) {
            _snowmanxxxx.write(_snowmanxx);
         }

         b.info("Converted {} from NBT to SNBT", _snowman);
         return _snowmanxxx;
      } catch (IOException var20) {
         b.error("Couldn't convert {} from NBT to SNBT at {}", _snowman, _snowman, var20);
         return null;
      }
   }
}
