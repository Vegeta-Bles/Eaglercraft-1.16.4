package net.minecraft.client.realms.dto;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.realms.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
      JsonObject _snowman = node.getAsJsonObject();
      Backup _snowmanx = new Backup();

      try {
         _snowmanx.backupId = JsonUtils.getStringOr("backupId", _snowman, "");
         _snowmanx.lastModifiedDate = JsonUtils.getDateOr("lastModifiedDate", _snowman);
         _snowmanx.size = JsonUtils.getLongOr("size", _snowman, 0L);
         if (_snowman.has("metadata")) {
            JsonObject _snowmanxx = _snowman.getAsJsonObject("metadata");

            for (Entry<String, JsonElement> _snowmanxxx : _snowmanxx.entrySet()) {
               if (!_snowmanxxx.getValue().isJsonNull()) {
                  _snowmanx.metadata.put(format(_snowmanxxx.getKey()), _snowmanxxx.getValue().getAsString());
               }
            }
         }
      } catch (Exception var7) {
         LOGGER.error("Could not parse Backup: " + var7.getMessage());
      }

      return _snowmanx;
   }

   private static String format(String key) {
      String[] _snowman = key.split("_");
      StringBuilder _snowmanx = new StringBuilder();

      for (String _snowmanxx : _snowman) {
         if (_snowmanxx != null && _snowmanxx.length() >= 1) {
            if ("of".equals(_snowmanxx)) {
               _snowmanx.append(_snowmanxx).append(" ");
            } else {
               char _snowmanxxx = Character.toUpperCase(_snowmanxx.charAt(0));
               _snowmanx.append(_snowmanxxx).append(_snowmanxx.substring(1)).append(" ");
            }
         }
      }

      return _snowmanx.toString();
   }

   public boolean isUploadedVersion() {
      return this.uploadedVersion;
   }

   public void setUploadedVersion(boolean uploadedVersion) {
      this.uploadedVersion = uploadedVersion;
   }
}
