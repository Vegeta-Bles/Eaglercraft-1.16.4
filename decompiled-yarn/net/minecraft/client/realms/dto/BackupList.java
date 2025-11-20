package net.minecraft.client.realms.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BackupList extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public List<Backup> backups;

   public BackupList() {
   }

   public static BackupList parse(String json) {
      JsonParser _snowman = new JsonParser();
      BackupList _snowmanx = new BackupList();
      _snowmanx.backups = Lists.newArrayList();

      try {
         JsonElement _snowmanxx = _snowman.parse(json).getAsJsonObject().get("backups");
         if (_snowmanxx.isJsonArray()) {
            Iterator<JsonElement> _snowmanxxx = _snowmanxx.getAsJsonArray().iterator();

            while (_snowmanxxx.hasNext()) {
               _snowmanx.backups.add(Backup.parse(_snowmanxxx.next()));
            }
         }
      } catch (Exception var5) {
         LOGGER.error("Could not parse BackupList: " + var5.getMessage());
      }

      return _snowmanx;
   }
}
