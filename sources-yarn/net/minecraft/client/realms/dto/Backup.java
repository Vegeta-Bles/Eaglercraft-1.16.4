package net.minecraft.client.realms.dto;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.realms.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class Backup extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public String backupId;
   public Date lastModifiedDate;
   public long size;
   private boolean uploadedVersion;
   public Map<String, String> metadata = Maps.newHashMap();
   public Map<String, String> changeList = Maps.newHashMap();

   public Backup() {
   }

   public static Backup parse(JsonElement node) {
      JsonObject jsonObject = node.getAsJsonObject();
      Backup lv = new Backup();

      try {
         lv.backupId = JsonUtils.getStringOr("backupId", jsonObject, "");
         lv.lastModifiedDate = JsonUtils.getDateOr("lastModifiedDate", jsonObject);
         lv.size = JsonUtils.getLongOr("size", jsonObject, 0L);
         if (jsonObject.has("metadata")) {
            JsonObject jsonObject2 = jsonObject.getAsJsonObject("metadata");

            for (Entry<String, JsonElement> entry : jsonObject2.entrySet()) {
               if (!entry.getValue().isJsonNull()) {
                  lv.metadata.put(format(entry.getKey()), entry.getValue().getAsString());
               }
            }
         }
      } catch (Exception var7) {
         LOGGER.error("Could not parse Backup: " + var7.getMessage());
      }

      return lv;
   }

   private static String format(String key) {
      String[] strings = key.split("_");
      StringBuilder stringBuilder = new StringBuilder();

      for (String string2 : strings) {
         if (string2 != null && string2.length() >= 1) {
            if ("of".equals(string2)) {
               stringBuilder.append(string2).append(" ");
            } else {
               char c = Character.toUpperCase(string2.charAt(0));
               stringBuilder.append(c).append(string2.substring(1)).append(" ");
            }
         }
      }

      return stringBuilder.toString();
   }

   public boolean isUploadedVersion() {
      return this.uploadedVersion;
   }

   public void setUploadedVersion(boolean uploadedVersion) {
      this.uploadedVersion = uploadedVersion;
   }
}
