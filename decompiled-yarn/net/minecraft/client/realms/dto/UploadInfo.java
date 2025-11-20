package net.minecraft.client.realms.dto;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.client.realms.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UploadInfo extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Pattern field_26467 = Pattern.compile("^[a-zA-Z][-a-zA-Z0-9+.]+:");
   private final boolean worldClosed;
   @Nullable
   private final String token;
   private final URI uploadEndpoint;

   private UploadInfo(boolean worldClosed, @Nullable String token, URI uploadEndpoint) {
      this.worldClosed = worldClosed;
      this.token = token;
      this.uploadEndpoint = uploadEndpoint;
   }

   @Nullable
   public static UploadInfo parse(String json) {
      try {
         JsonParser _snowman = new JsonParser();
         JsonObject _snowmanx = _snowman.parse(json).getAsJsonObject();
         String _snowmanxx = JsonUtils.getStringOr("uploadEndpoint", _snowmanx, null);
         if (_snowmanxx != null) {
            int _snowmanxxx = JsonUtils.getIntOr("port", _snowmanx, -1);
            URI _snowmanxxxx = method_30862(_snowmanxx, _snowmanxxx);
            if (_snowmanxxxx != null) {
               boolean _snowmanxxxxx = JsonUtils.getBooleanOr("worldClosed", _snowmanx, false);
               String _snowmanxxxxxx = JsonUtils.getStringOr("token", _snowmanx, null);
               return new UploadInfo(_snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxx);
            }
         }
      } catch (Exception var8) {
         LOGGER.error("Could not parse UploadInfo: " + var8.getMessage());
      }

      return null;
   }

   @Nullable
   @VisibleForTesting
   public static URI method_30862(String _snowman, int _snowman) {
      Matcher _snowmanxx = field_26467.matcher(_snowman);
      String _snowmanxxx = method_30863(_snowman, _snowmanxx);

      try {
         URI _snowmanxxxx = new URI(_snowmanxxx);
         int _snowmanxxxxx = method_30861(_snowman, _snowmanxxxx.getPort());
         return _snowmanxxxxx != _snowmanxxxx.getPort()
            ? new URI(_snowmanxxxx.getScheme(), _snowmanxxxx.getUserInfo(), _snowmanxxxx.getHost(), _snowmanxxxxx, _snowmanxxxx.getPath(), _snowmanxxxx.getQuery(), _snowmanxxxx.getFragment())
            : _snowmanxxxx;
      } catch (URISyntaxException var6) {
         LOGGER.warn("Failed to parse URI {}", _snowmanxxx, var6);
         return null;
      }
   }

   private static int method_30861(int _snowman, int _snowman) {
      if (_snowman != -1) {
         return _snowman;
      } else {
         return _snowman != -1 ? _snowman : 8080;
      }
   }

   private static String method_30863(String _snowman, Matcher _snowman) {
      return _snowman.find() ? _snowman : "http://" + _snowman;
   }

   public static String createRequestContent(@Nullable String token) {
      JsonObject _snowman = new JsonObject();
      if (token != null) {
         _snowman.addProperty("token", token);
      }

      return _snowman.toString();
   }

   @Nullable
   public String getToken() {
      return this.token;
   }

   public URI getUploadEndpoint() {
      return this.uploadEndpoint;
   }

   public boolean isWorldClosed() {
      return this.worldClosed;
   }
}
