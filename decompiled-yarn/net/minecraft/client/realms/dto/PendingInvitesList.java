package net.minecraft.client.realms.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PendingInvitesList extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public List<PendingInvite> pendingInvites = Lists.newArrayList();

   public PendingInvitesList() {
   }

   public static PendingInvitesList parse(String json) {
      PendingInvitesList _snowman = new PendingInvitesList();

      try {
         JsonParser _snowmanx = new JsonParser();
         JsonObject _snowmanxx = _snowmanx.parse(json).getAsJsonObject();
         if (_snowmanxx.get("invites").isJsonArray()) {
            Iterator<JsonElement> _snowmanxxx = _snowmanxx.get("invites").getAsJsonArray().iterator();

            while (_snowmanxxx.hasNext()) {
               _snowman.pendingInvites.add(PendingInvite.parse(_snowmanxxx.next().getAsJsonObject()));
            }
         }
      } catch (Exception var5) {
         LOGGER.error("Could not parse PendingInvitesList: " + var5.getMessage());
      }

      return _snowman;
   }
}
