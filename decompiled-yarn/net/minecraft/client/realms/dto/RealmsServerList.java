package net.minecraft.client.realms.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsServerList extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public List<RealmsServer> servers;

   public RealmsServerList() {
   }

   public static RealmsServerList parse(String json) {
      RealmsServerList _snowman = new RealmsServerList();
      _snowman.servers = Lists.newArrayList();

      try {
         JsonParser _snowmanx = new JsonParser();
         JsonObject _snowmanxx = _snowmanx.parse(json).getAsJsonObject();
         if (_snowmanxx.get("servers").isJsonArray()) {
            JsonArray _snowmanxxx = _snowmanxx.get("servers").getAsJsonArray();
            Iterator<JsonElement> _snowmanxxxx = _snowmanxxx.iterator();

            while (_snowmanxxxx.hasNext()) {
               _snowman.servers.add(RealmsServer.parse(_snowmanxxxx.next().getAsJsonObject()));
            }
         }
      } catch (Exception var6) {
         LOGGER.error("Could not parse McoServerList: " + var6.getMessage());
      }

      return _snowman;
   }
}
