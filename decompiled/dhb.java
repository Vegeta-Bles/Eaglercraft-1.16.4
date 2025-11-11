import com.google.common.annotations.VisibleForTesting;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dhb extends dhc {
   private static final Logger a = LogManager.getLogger();
   private static final Pattern b = Pattern.compile("^[a-zA-Z][-a-zA-Z0-9+.]+:");
   private final boolean c;
   @Nullable
   private final String d;
   private final URI e;

   private dhb(boolean var1, @Nullable String var2, URI var3) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }

   @Nullable
   public static dhb a(String var0) {
      try {
         JsonParser _snowman = new JsonParser();
         JsonObject _snowmanx = _snowman.parse(_snowman).getAsJsonObject();
         String _snowmanxx = dip.a("uploadEndpoint", _snowmanx, null);
         if (_snowmanxx != null) {
            int _snowmanxxx = dip.a("port", _snowmanx, -1);
            URI _snowmanxxxx = a(_snowmanxx, _snowmanxxx);
            if (_snowmanxxxx != null) {
               boolean _snowmanxxxxx = dip.a("worldClosed", _snowmanx, false);
               String _snowmanxxxxxx = dip.a("token", _snowmanx, null);
               return new dhb(_snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxx);
            }
         }
      } catch (Exception var8) {
         a.error("Could not parse UploadInfo: " + var8.getMessage());
      }

      return null;
   }

   @Nullable
   @VisibleForTesting
   public static URI a(String var0, int var1) {
      Matcher _snowman = b.matcher(_snowman);
      String _snowmanx = a(_snowman, _snowman);

      try {
         URI _snowmanxx = new URI(_snowmanx);
         int _snowmanxxx = a(_snowman, _snowmanxx.getPort());
         return _snowmanxxx != _snowmanxx.getPort()
            ? new URI(_snowmanxx.getScheme(), _snowmanxx.getUserInfo(), _snowmanxx.getHost(), _snowmanxxx, _snowmanxx.getPath(), _snowmanxx.getQuery(), _snowmanxx.getFragment())
            : _snowmanxx;
      } catch (URISyntaxException var6) {
         a.warn("Failed to parse URI {}", _snowmanx, var6);
         return null;
      }
   }

   private static int a(int var0, int var1) {
      if (_snowman != -1) {
         return _snowman;
      } else {
         return _snowman != -1 ? _snowman : 8080;
      }
   }

   private static String a(String var0, Matcher var1) {
      return _snowman.find() ? _snowman : "http://" + _snowman;
   }

   public static String b(@Nullable String var0) {
      JsonObject _snowman = new JsonObject();
      if (_snowman != null) {
         _snowman.addProperty("token", _snowman);
      }

      return _snowman.toString();
   }

   @Nullable
   public String a() {
      return this.d;
   }

   public URI b() {
      return this.e;
   }

   public boolean c() {
      return this.c;
   }
}
