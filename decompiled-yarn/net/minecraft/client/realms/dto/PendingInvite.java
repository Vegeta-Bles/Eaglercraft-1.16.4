package net.minecraft.client.realms.dto;

import com.google.gson.JsonObject;
import java.util.Date;
import net.minecraft.client.realms.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PendingInvite extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public String invitationId;
   public String worldName;
   public String worldOwnerName;
   public String worldOwnerUuid;
   public Date date;

   public PendingInvite() {
   }

   public static PendingInvite parse(JsonObject json) {
      PendingInvite _snowman = new PendingInvite();

      try {
         _snowman.invitationId = JsonUtils.getStringOr("invitationId", json, "");
         _snowman.worldName = JsonUtils.getStringOr("worldName", json, "");
         _snowman.worldOwnerName = JsonUtils.getStringOr("worldOwnerName", json, "");
         _snowman.worldOwnerUuid = JsonUtils.getStringOr("worldOwnerUuid", json, "");
         _snowman.date = JsonUtils.getDateOr("date", json);
      } catch (Exception var3) {
         LOGGER.error("Could not parse PendingInvite: " + var3.getMessage());
      }

      return _snowman;
   }
}
