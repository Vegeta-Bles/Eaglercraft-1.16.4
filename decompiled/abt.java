import java.io.File;
import java.io.FileFilter;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class abt implements aby {
   private static final FileFilter a = var0 -> {
      boolean _snowman = var0.isFile() && var0.getName().endsWith(".zip");
      boolean _snowmanx = var0.isDirectory() && new File(var0, "pack.mcmeta").isFile();
      return _snowman || _snowmanx;
   };
   private final File b;
   private final abx c;

   public abt(File var1, abx var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public void a(Consumer<abu> var1, abu.a var2) {
      if (!this.b.isDirectory()) {
         this.b.mkdirs();
      }

      File[] _snowman = this.b.listFiles(a);
      if (_snowman != null) {
         for (File _snowmanx : _snowman) {
            String _snowmanxx = "file/" + _snowmanx.getName();
            abu _snowmanxxx = abu.a(_snowmanxx, false, this.a(_snowmanx), _snowman, abu.b.a, this.c);
            if (_snowmanxxx != null) {
               _snowman.accept(_snowmanxxx);
            }
         }
      }
   }

   private Supplier<abj> a(File var1) {
      return _snowman.isDirectory() ? () -> new abi(_snowman) : () -> new abh(_snowman);
   }
}
