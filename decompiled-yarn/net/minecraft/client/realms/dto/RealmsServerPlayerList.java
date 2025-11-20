package net.minecraft.client.realms.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.List;
import net.minecraft.client.realms.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsServerPlayerList extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final JsonParser jsonParser = new JsonParser();
   public long serverId;
   public List<String> players;

   public RealmsServerPlayerList() {
   }

   public static RealmsServerPlayerList parse(JsonObject node) {
      RealmsServerPlayerList _snowman = new RealmsServerPlayerList();

      try {
         _snowman.serverId = JsonUtils.getLongOr("serverId", node, -1L);
         String _snowmanx = JsonUtils.getStringOr("playerList", node, null);
         if (_snowmanx != null) {
            JsonElement _snowmanxx = jsonParser.parse(_snowmanx);
            if (_snowmanxx.isJsonArray()) {
               _snowman.players = parsePlayers(_snowmanxx.getAsJsonArray());
            } else {
               _snowman.players = Lists.newArrayList();
            }
         } else {
            _snowman.players = Lists.newArrayList();
         }
      } catch (Exception var4) {
         LOGGER.error("Could not parse RealmsServerPlayerList: " + var4.getMessage());
      }

      return _snowman;
   }

   private static List<String> parsePlayers(JsonArray jsonArray) {
      List<String> _snowman = Lists.newArrayList();

      for (JsonElement _snowmanx : jsonArray) {
         try {
            _snowman.add(_snowmanx.getAsString());
         } catch (Exception var5) {
         }
      }

      return _snowman;
   }
}
