package net.minecraft.client.realms.dto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.realms.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class RealmsServerAddress extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public String address;
   public String resourcePackUrl;
   public String resourcePackHash;

   public RealmsServerAddress() {
   }

   public static RealmsServerAddress parse(String json) {
      JsonParser jsonParser = new JsonParser();
      RealmsServerAddress lv = new RealmsServerAddress();

      try {
         JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
         lv.address = JsonUtils.getStringOr("address", jsonObject, null);
         lv.resourcePackUrl = JsonUtils.getStringOr("resourcePackUrl", jsonObject, null);
         lv.resourcePackHash = JsonUtils.getStringOr("resourcePackHash", jsonObject, null);
      } catch (Exception var4) {
         LOGGER.error("Could not parse RealmsServerAddress: " + var4.getMessage());
      }

      return lv;
   }
}
