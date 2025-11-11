import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cyc {
   private static final Logger a = LogManager.getLogger();
   private final Map<String, cxs> b = Maps.newHashMap();
   private final DataFixer c;
   private final File d;

   public cyc(File var1, DataFixer var2) {
      this.c = _snowman;
      this.d = _snowman;
   }

   private File a(String var1) {
      return new File(this.d, _snowman + ".dat");
   }

   public <T extends cxs> T a(Supplier<T> var1, String var2) {
      T _snowman = this.b(_snowman, _snowman);
      if (_snowman != null) {
         return _snowman;
      } else {
         T _snowmanx = (T)_snowman.get();
         this.a(_snowmanx);
         return _snowmanx;
      }
   }

   @Nullable
   public <T extends cxs> T b(Supplier<T> var1, String var2) {
      cxs _snowman = this.b.get(_snowman);
      if (_snowman == null && !this.b.containsKey(_snowman)) {
         _snowman = this.c(_snowman, _snowman);
         this.b.put(_snowman, _snowman);
      }

      return (T)_snowman;
   }

   @Nullable
   private <T extends cxs> T c(Supplier<T> var1, String var2) {
      try {
         File _snowman = this.a(_snowman);
         if (_snowman.exists()) {
            T _snowmanx = (T)_snowman.get();
            md _snowmanxx = this.a(_snowman, w.a().getWorldVersion());
            _snowmanx.a(_snowmanxx.p("data"));
            return _snowmanx;
         }
      } catch (Exception var6) {
         a.error("Error loading saved data: {}", _snowman, var6);
      }

      return null;
   }

   public void a(cxs var1) {
      this.b.put(_snowman.d(), _snowman);
   }

   public md a(String var1, int var2) throws IOException {
      File _snowman = this.a(_snowman);

      md var61;
      try (
         FileInputStream _snowmanx = new FileInputStream(_snowman);
         PushbackInputStream _snowmanxx = new PushbackInputStream(_snowmanx, 2);
      ) {
         md _snowmanxxx;
         if (this.a(_snowmanxx)) {
            _snowmanxxx = mn.a(_snowmanxx);
         } else {
            try (DataInputStream _snowmanxxxx = new DataInputStream(_snowmanxx)) {
               _snowmanxxx = mn.a((DataInput)_snowmanxxxx);
            }
         }

         int _snowmanxxxx = _snowmanxxx.c("DataVersion", 99) ? _snowmanxxx.h("DataVersion") : 1343;
         var61 = mp.a(this.c, aga.h, _snowmanxxx, _snowmanxxxx, _snowman);
      }

      return var61;
   }

   private boolean a(PushbackInputStream var1) throws IOException {
      byte[] _snowman = new byte[2];
      boolean _snowmanx = false;
      int _snowmanxx = _snowman.read(_snowman, 0, 2);
      if (_snowmanxx == 2) {
         int _snowmanxxx = (_snowman[1] & 255) << 8 | _snowman[0] & 255;
         if (_snowmanxxx == 35615) {
            _snowmanx = true;
         }
      }

      if (_snowmanxx != 0) {
         _snowman.unread(_snowman, 0, _snowmanxx);
      }

      return _snowmanx;
   }

   public void a() {
      for (cxs _snowman : this.b.values()) {
         if (_snowman != null) {
            _snowman.a(this.a(_snowman.d()));
         }
      }
   }
}
