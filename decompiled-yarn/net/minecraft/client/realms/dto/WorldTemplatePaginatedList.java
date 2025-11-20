package net.minecraft.client.realms.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.realms.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldTemplatePaginatedList extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public List<WorldTemplate> templates;
   public int page;
   public int size;
   public int total;

   public WorldTemplatePaginatedList() {
   }

   public WorldTemplatePaginatedList(int size) {
      this.templates = Collections.emptyList();
      this.page = 0;
      this.size = size;
      this.total = -1;
   }

   public static WorldTemplatePaginatedList parse(String json) {
      WorldTemplatePaginatedList _snowman = new WorldTemplatePaginatedList();
      _snowman.templates = Lists.newArrayList();

      try {
         JsonParser _snowmanx = new JsonParser();
         JsonObject _snowmanxx = _snowmanx.parse(json).getAsJsonObject();
         if (_snowmanxx.get("templates").isJsonArray()) {
            Iterator<JsonElement> _snowmanxxx = _snowmanxx.get("templates").getAsJsonArray().iterator();

            while (_snowmanxxx.hasNext()) {
               _snowman.templates.add(WorldTemplate.parse(_snowmanxxx.next().getAsJsonObject()));
            }
         }

         _snowman.page = JsonUtils.getIntOr("page", _snowmanxx, 0);
         _snowman.size = JsonUtils.getIntOr("size", _snowmanxx, 0);
         _snowman.total = JsonUtils.getIntOr("total", _snowmanxx, 0);
      } catch (Exception var5) {
         LOGGER.error("Could not parse WorldTemplatePaginatedList: " + var5.getMessage());
      }

      return _snowman;
   }
}
