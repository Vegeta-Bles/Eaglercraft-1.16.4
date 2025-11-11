import com.mojang.datafixers.DataFixer;
import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class djv {
   private static final Logger a = LogManager.getLogger();
   private final File b;
   private final DataFixer c;
   private final dzo[] d = new dzo[9];
   private boolean e;

   public djv(File var1, DataFixer var2) {
      this.b = new File(_snowman, "hotbar.nbt");
      this.c = _snowman;

      for (int _snowman = 0; _snowman < 9; _snowman++) {
         this.d[_snowman] = new dzo();
      }
   }

   private void b() {
      try {
         md _snowman = mn.b(this.b);
         if (_snowman == null) {
            return;
         }

         if (!_snowman.c("DataVersion", 99)) {
            _snowman.b("DataVersion", 1343);
         }

         _snowman = mp.a(this.c, aga.d, _snowman, _snowman.h("DataVersion"));

         for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
            this.d[_snowmanx].a(_snowman.d(String.valueOf(_snowmanx), 10));
         }
      } catch (Exception var3) {
         a.error("Failed to load creative mode options", var3);
      }
   }

   public void a() {
      try {
         md _snowman = new md();
         _snowman.b("DataVersion", w.a().getWorldVersion());

         for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
            _snowman.a(String.valueOf(_snowmanx), this.a(_snowmanx).a());
         }

         mn.b(_snowman, this.b);
      } catch (Exception var3) {
         a.error("Failed to save creative mode options", var3);
      }
   }

   public dzo a(int var1) {
      if (!this.e) {
         this.b();
         this.e = true;
      }

      return this.d[_snowman];
   }
}
