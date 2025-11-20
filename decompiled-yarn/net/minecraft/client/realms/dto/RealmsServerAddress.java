package net.minecraft.client.realms.dto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.realms.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsServerAddress extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public String address;
   public String resourcePackUrl;
   public String resourcePackHash;

   public RealmsServerAddress() {
   }

   public static RealmsServerAddress parse(String json) {
      JsonParser _snowman = new JsonParser();
      RealmsServerAddress _snowmanx = new RealmsServerAddress();

      try {
         JsonObject _snowmanxx = _snowman.parse(json).getAsJsonObject();
         _snowmanx.address = JsonUtils.getStringOr("address", _snowmanxx, null);
         _snowmanx.resourcePackUrl = JsonUtils.getStringOr("resourcePackUrl", _snowmanxx, null);
         _snowmanx.resourcePackHash = JsonUtils.getStringOr("resourcePackHash", _snowmanxx, null);
      } catch (Exception var4) {
         LOGGER.error("Could not parse RealmsServerAddress: " + var4.getMessage());
      }

      return _snowmanx;
   }
}
