package net.minecraft.client.realms.dto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.realms.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsNews extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public String newsLink;

   public RealmsNews() {
   }

   public static RealmsNews parse(String json) {
      RealmsNews _snowman = new RealmsNews();

      try {
         JsonParser _snowmanx = new JsonParser();
         JsonObject _snowmanxx = _snowmanx.parse(json).getAsJsonObject();
         _snowman.newsLink = JsonUtils.getStringOr("newsLink", _snowmanxx, null);
      } catch (Exception var4) {
         LOGGER.error("Could not parse RealmsNews: " + var4.getMessage());
      }

      return _snowman;
   }
}
