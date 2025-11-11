import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class abg implements abj {
   private static final Logger b = LogManager.getLogger();
   protected final File a;

   public abg(File var1) {
      this.a = _snowman;
   }

   private static String c(abk var0, vk var1) {
      return String.format("%s/%s/%s", _snowman.a(), _snowman.b(), _snowman.a());
   }

   protected static String a(File var0, File var1) {
      return _snowman.toURI().relativize(_snowman.toURI()).getPath();
   }

   @Override
   public InputStream a(abk var1, vk var2) throws IOException {
      return this.a(c(_snowman, _snowman));
   }

   @Override
   public boolean b(abk var1, vk var2) {
      return this.c(c(_snowman, _snowman));
   }

   protected abstract InputStream a(String var1) throws IOException;

   @Override
   public InputStream b(String var1) throws IOException {
      if (!_snowman.contains("/") && !_snowman.contains("\\")) {
         return this.a(_snowman);
      } else {
         throw new IllegalArgumentException("Root resources can only be filenames, not paths (no / allowed!)");
      }
   }

   protected abstract boolean c(String var1);

   protected void d(String var1) {
      b.warn("ResourcePack: ignored non-lowercase namespace: {} in {}", _snowman, this.a);
   }

   @Nullable
   @Override
   public <T> T a(abn<T> var1) throws IOException {
      Object var4;
      try (InputStream _snowman = this.a("pack.mcmeta")) {
         var4 = a(_snowman, _snowman);
      }

      return (T)var4;
   }

   @Nullable
   public static <T> T a(abn<T> var0, InputStream var1) {
      JsonObject _snowman;
      try (BufferedReader _snowmanx = new BufferedReader(new InputStreamReader(_snowman, StandardCharsets.UTF_8))) {
         _snowman = afd.a(_snowmanx);
      } catch (JsonParseException | IOException var18) {
         b.error("Couldn't load {} metadata", _snowman.a(), var18);
         return null;
      }

      if (!_snowman.has(_snowman.a())) {
         return null;
      } else {
         try {
            return _snowman.a(afd.t(_snowman, _snowman.a()));
         } catch (JsonParseException var15) {
            b.error("Couldn't load {} metadata", _snowman.a(), var15);
            return null;
         }
      }
   }

   @Override
   public String a() {
      return this.a.getName();
   }
}
