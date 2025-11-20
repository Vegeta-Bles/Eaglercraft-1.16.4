package net.minecraft.client.realms.dto;

import com.google.gson.JsonObject;
import java.util.Objects;
import net.minecraft.client.realms.util.JsonUtils;
import net.minecraft.client.resource.language.I18n;

public class RealmsWorldOptions extends ValueObject {
   public Boolean pvp;
   public Boolean spawnAnimals;
   public Boolean spawnMonsters;
   public Boolean spawnNPCs;
   public Integer spawnProtection;
   public Boolean commandBlocks;
   public Boolean forceGameMode;
   public Integer difficulty;
   public Integer gameMode;
   public String slotName;
   public long templateId;
   public String templateImage;
   public boolean adventureMap;
   public boolean empty;
   private static final String DEFAULT_WORLD_TEMPLATE_IMAGE = null;

   public RealmsWorldOptions(
      Boolean pvp,
      Boolean spawnAnimals,
      Boolean spawnMonsters,
      Boolean spawnNPCs,
      Integer spawnProtection,
      Boolean commandBlocks,
      Integer difficulty,
      Integer gameMode,
      Boolean forceGameMode,
      String slotName
   ) {
      this.pvp = pvp;
      this.spawnAnimals = spawnAnimals;
      this.spawnMonsters = spawnMonsters;
      this.spawnNPCs = spawnNPCs;
      this.spawnProtection = spawnProtection;
      this.commandBlocks = commandBlocks;
      this.difficulty = difficulty;
      this.gameMode = gameMode;
      this.forceGameMode = forceGameMode;
      this.slotName = slotName;
   }

   public static RealmsWorldOptions getDefaults() {
      return new RealmsWorldOptions(true, true, true, true, 0, false, 2, 0, false, "");
   }

   public static RealmsWorldOptions getEmptyDefaults() {
      RealmsWorldOptions _snowman = getDefaults();
      _snowman.setEmpty(true);
      return _snowman;
   }

   public void setEmpty(boolean empty) {
      this.empty = empty;
   }

   public static RealmsWorldOptions parse(JsonObject json) {
      RealmsWorldOptions _snowman = new RealmsWorldOptions(
         JsonUtils.getBooleanOr("pvp", json, true),
         JsonUtils.getBooleanOr("spawnAnimals", json, true),
         JsonUtils.getBooleanOr("spawnMonsters", json, true),
         JsonUtils.getBooleanOr("spawnNPCs", json, true),
         JsonUtils.getIntOr("spawnProtection", json, 0),
         JsonUtils.getBooleanOr("commandBlocks", json, false),
         JsonUtils.getIntOr("difficulty", json, 2),
         JsonUtils.getIntOr("gameMode", json, 0),
         JsonUtils.getBooleanOr("forceGameMode", json, false),
         JsonUtils.getStringOr("slotName", json, "")
      );
      _snowman.templateId = JsonUtils.getLongOr("worldTemplateId", json, -1L);
      _snowman.templateImage = JsonUtils.getStringOr("worldTemplateImage", json, DEFAULT_WORLD_TEMPLATE_IMAGE);
      _snowman.adventureMap = JsonUtils.getBooleanOr("adventureMap", json, false);
      return _snowman;
   }

   public String getSlotName(int index) {
      if (this.slotName != null && !this.slotName.isEmpty()) {
         return this.slotName;
      } else {
         return this.empty ? I18n.translate("mco.configure.world.slot.empty") : this.getDefaultSlotName(index);
      }
   }

   public String getDefaultSlotName(int index) {
      return I18n.translate("mco.configure.world.slot", index);
   }

   public String toJson() {
      JsonObject _snowman = new JsonObject();
      if (!this.pvp) {
         _snowman.addProperty("pvp", this.pvp);
      }

      if (!this.spawnAnimals) {
         _snowman.addProperty("spawnAnimals", this.spawnAnimals);
      }

      if (!this.spawnMonsters) {
         _snowman.addProperty("spawnMonsters", this.spawnMonsters);
      }

      if (!this.spawnNPCs) {
         _snowman.addProperty("spawnNPCs", this.spawnNPCs);
      }

      if (this.spawnProtection != 0) {
         _snowman.addProperty("spawnProtection", this.spawnProtection);
      }

      if (this.commandBlocks) {
         _snowman.addProperty("commandBlocks", this.commandBlocks);
      }

      if (this.difficulty != 2) {
         _snowman.addProperty("difficulty", this.difficulty);
      }

      if (this.gameMode != 0) {
         _snowman.addProperty("gameMode", this.gameMode);
      }

      if (this.forceGameMode) {
         _snowman.addProperty("forceGameMode", this.forceGameMode);
      }

      if (!Objects.equals(this.slotName, "")) {
         _snowman.addProperty("slotName", this.slotName);
      }

      return _snowman.toString();
   }

   public RealmsWorldOptions clone() {
      return new RealmsWorldOptions(
         this.pvp,
         this.spawnAnimals,
         this.spawnMonsters,
         this.spawnNPCs,
         this.spawnProtection,
         this.commandBlocks,
         this.difficulty,
         this.gameMode,
         this.forceGameMode,
         this.slotName
      );
   }
}
