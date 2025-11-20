package net.minecraft.client.realms.dto;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.client.realms.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
      WorldTemplate _snowman = new WorldTemplate();

      try {
         _snowman.id = JsonUtils.getStringOr("id", node, "");
         _snowman.name = JsonUtils.getStringOr("name", node, "");
         _snowman.version = JsonUtils.getStringOr("version", node, "");
         _snowman.author = JsonUtils.getStringOr("author", node, "");
         _snowman.link = JsonUtils.getStringOr("link", node, "");
         _snowman.image = JsonUtils.getStringOr("image", node, null);
         _snowman.trailer = JsonUtils.getStringOr("trailer", node, "");
         _snowman.recommendedPlayers = JsonUtils.getStringOr("recommendedPlayers", node, "");
         _snowman.type = WorldTemplate.WorldTemplateType.valueOf(JsonUtils.getStringOr("type", node, WorldTemplate.WorldTemplateType.WORLD_TEMPLATE.name()));
      } catch (Exception var3) {
         LOGGER.error("Could not parse WorldTemplate: " + var3.getMessage());
      }

      return _snowman;
   }

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
