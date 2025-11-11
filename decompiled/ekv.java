import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ekv extends ly {
   private static final Logger a = LogManager.getLogger();
   private final Map<String, String> b;
   private final boolean c;

   private ekv(Map<String, String> var1, boolean var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   public static ekv a(ach var0, List<eky> var1) {
      Map<String, String> _snowman = Maps.newHashMap();
      boolean _snowmanx = false;

      for (eky _snowmanxx : _snowman) {
         _snowmanx |= _snowmanxx.a();
         String _snowmanxxx = String.format("lang/%s.json", _snowmanxx.getCode());

         for (String _snowmanxxxx : _snowman.a()) {
            try {
               vk _snowmanxxxxx = new vk(_snowmanxxxx, _snowmanxxx);
               a(_snowman.c(_snowmanxxxxx), _snowman);
            } catch (FileNotFoundException var10) {
            } catch (Exception var11) {
               a.warn("Skipped language file: {}:{} ({})", _snowmanxxxx, _snowmanxxx, var11.toString());
            }
         }
      }

      return new ekv(ImmutableMap.copyOf(_snowman), _snowmanx);
   }

   private static void a(List<acg> var0, Map<String, String> var1) {
      for (acg _snowman : _snowman) {
         try (InputStream _snowmanx = _snowman.b()) {
            ly.a(_snowmanx, _snowman::put);
         } catch (IOException var17) {
            a.warn("Failed to load translations from {}", _snowman, var17);
         }
      }
   }

   @Override
   public String a(String var1) {
      return this.b.getOrDefault(_snowman, _snowman);
   }

   @Override
   public boolean b(String var1) {
      return this.b.containsKey(_snowman);
   }

   @Override
   public boolean b() {
      return this.c;
   }

   @Override
   public afa a(nu var1) {
      return ekw.a(_snowman, this.c);
   }
}
