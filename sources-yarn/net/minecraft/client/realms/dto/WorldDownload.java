package net.minecraft.client.realms.dto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.realms.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class WorldDownload extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public String downloadLink;
   public String resourcePackUrl;
   public String resourcePackHash;

   public WorldDownload() {
   }

   public static WorldDownload parse(String json) {
      JsonParser jsonParser = new JsonParser();
      JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
      WorldDownload lv = new WorldDownload();

      try {
         lv.downloadLink = JsonUtils.getStringOr("downloadLink", jsonObject, "");
         lv.resourcePackUrl = JsonUtils.getStringOr("resourcePackUrl", jsonObject, "");
         lv.resourcePackHash = JsonUtils.getStringOr("resourcePackHash", jsonObject, "");
      } catch (Exception var5) {
         LOGGER.error("Could not parse WorldDownload: " + var5.getMessage());
      }

      return lv;
   }
}
