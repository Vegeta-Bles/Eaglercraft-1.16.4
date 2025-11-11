import java.io.File;
import java.io.FileNotFoundException;

public class abl extends FileNotFoundException {
   public abl(File var1, String var2) {
      super(String.format("'%s' in ResourcePack '%s'", _snowman, _snowman));
   }
}
