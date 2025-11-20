package net.minecraft.client.realms.dto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.realms.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldDownload extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public String downloadLink;
   public String resourcePackUrl;
   public String resourcePackHash;

   public WorldDownload() {
   }

   public static WorldDownload parse(String json) {
      JsonParser _snowman = new JsonParser();
      JsonObject _snowmanx = _snowman.parse(json).getAsJsonObject();
      WorldDownload _snowmanxx = new WorldDownload();

      try {
         _snowmanxx.downloadLink = JsonUtils.getStringOr("downloadLink", _snowmanx, "");
         _snowmanxx.resourcePackUrl = JsonUtils.getStringOr("resourcePackUrl", _snowmanx, "");
         _snowmanxx.resourcePackHash = JsonUtils.getStringOr("resourcePackHash", _snowmanx, "");
      } catch (Exception var5) {
         LOGGER.error("Could not parse WorldDownload: " + var5.getMessage());
      }

      return _snowmanxx;
   }
}
