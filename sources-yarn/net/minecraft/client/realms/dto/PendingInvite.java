package net.minecraft.client.realms.dto;

import com.google.gson.JsonObject;
import java.util.Date;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.realms.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
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
      PendingInvite lv = new PendingInvite();

      try {
         lv.invitationId = JsonUtils.getStringOr("invitationId", json, "");
         lv.worldName = JsonUtils.getStringOr("worldName", json, "");
         lv.worldOwnerName = JsonUtils.getStringOr("worldOwnerName", json, "");
         lv.worldOwnerUuid = JsonUtils.getStringOr("worldOwnerUuid", json, "");
         lv.date = JsonUtils.getDateOr("date", json);
      } catch (Exception var3) {
         LOGGER.error("Could not parse PendingInvite: " + var3.getMessage());
      }

      return lv;
   }
}
