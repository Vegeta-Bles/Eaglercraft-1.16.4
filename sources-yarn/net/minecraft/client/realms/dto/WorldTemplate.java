package net.minecraft.client.realms.dto;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.realms.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class WorldTemplate extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public String id = "";
   public String name = "";
   public String version = "";
   public String author = "";
   public String link = "";
   @Nullable
   public String image;
   public String trailer = "";
   public String recommendedPlayers = "";
   public WorldTemplate.WorldTemplateType type = WorldTemplate.WorldTemplateType.WORLD_TEMPLATE;

   public WorldTemplate() {
   }

   public static WorldTemplate parse(JsonObject node) {
      WorldTemplate lv = new WorldTemplate();

      try {
         lv.id = JsonUtils.getStringOr("id", node, "");
         lv.name = JsonUtils.getStringOr("name", node, "");
         lv.version = JsonUtils.getStringOr("version", node, "");
         lv.author = JsonUtils.getStringOr("author", node, "");
         lv.link = JsonUtils.getStringOr("link", node, "");
         lv.image = JsonUtils.getStringOr("image", node, null);
         lv.trailer = JsonUtils.getStringOr("trailer", node, "");
         lv.recommendedPlayers = JsonUtils.getStringOr("recommendedPlayers", node, "");
         lv.type = WorldTemplate.WorldTemplateType.valueOf(JsonUtils.getStringOr("type", node, WorldTemplate.WorldTemplateType.WORLD_TEMPLATE.name()));
      } catch (Exception var3) {
         LOGGER.error("Could not parse WorldTemplate: " + var3.getMessage());
      }

      return lv;
   }

   @Environment(EnvType.CLIENT)
   public static enum WorldTemplateType {
      WORLD_TEMPLATE,
      MINIGAME,
      ADVENTUREMAP,
      EXPERIENCE,
      INSPIRATION;

      private WorldTemplateType() {
      }
   }
}
