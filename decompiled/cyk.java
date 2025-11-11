import com.mojang.datafixers.DataFixer;
import java.io.File;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cyk {
   private static final Logger b = LogManager.getLogger();
   private final File c;
   protected final DataFixer a;

   public cyk(cyg.a var1, DataFixer var2) {
      this.a = _snowman;
      this.c = _snowman.a(cye.c).toFile();
      this.c.mkdirs();
   }

   public void a(bfw var1) {
      try {
         md _snowman = _snowman.e(new md());
         File _snowmanx = File.createTempFile(_snowman.bT() + "-", ".dat", this.c);
         mn.a(_snowman, _snowmanx);
         File _snowmanxx = new File(this.c, _snowman.bT() + ".dat");
         File _snowmanxxx = new File(this.c, _snowman.bT() + ".dat_old");
         x.a(_snowmanxx, _snowmanx, _snowmanxxx);
      } catch (Exception var6) {
         b.warn("Failed to save player data for {}", _snowman.R().getString());
      }
   }

   @Nullable
   public md b(bfw var1) {
      md _snowman = null;

      try {
         File _snowmanx = new File(this.c, _snowman.bT() + ".dat");
         if (_snowmanx.exists() && _snowmanx.isFile()) {
            _snowman = mn.a(_snowmanx);
         }
      } catch (Exception var4) {
         b.warn("Failed to load player data for {}", _snowman.R().getString());
      }

      if (_snowman != null) {
         int _snowmanx = _snowman.c("DataVersion", 3) ? _snowman.h("DataVersion") : -1;
         _snowman.f(mp.a(this.a, aga.b, _snowman, _snowmanx));
      }

      return _snowman;
   }

   public String[] a() {
      String[] _snowman = this.c.list();
      if (_snowman == null) {
         _snowman = new String[0];
      }

      for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
         if (_snowman[_snowmanx].endsWith(".dat")) {
            _snowman[_snowmanx] = _snowman[_snowmanx].substring(0, _snowman[_snowmanx].length() - 4);
         }
      }

      return _snowman;
   }
}
