package net.minecraft.predicate.entity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import net.minecraft.loot.LootGsons;
import net.minecraft.loot.LootTableReporter;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionManager;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancementEntityPredicateDeserializer {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Identifier advancementId;
   private final LootConditionManager conditionManager;
   private final Gson gson = LootGsons.getConditionGsonBuilder().create();

   public AdvancementEntityPredicateDeserializer(Identifier advancementId, LootConditionManager conditionManager) {
      this.advancementId = advancementId;
      this.conditionManager = conditionManager;
   }

   public final LootCondition[] loadConditions(JsonArray array, String key, LootContextType contextType) {
      LootCondition[] _snowman = (LootCondition[])this.gson.fromJson(array, LootCondition[].class);
      LootTableReporter _snowmanx = new LootTableReporter(contextType, this.conditionManager::get, _snowmanxx -> null);

      for (LootCondition _snowmanxx : _snowman) {
         _snowmanxx.validate(_snowmanx);
         _snowmanx.getMessages().forEach((_snowmanxxx, _snowmanxxxx) -> LOGGER.warn("Found validation problem in advancement trigger {}/{}: {}", key, _snowmanxxx, _snowmanxxxx));
      }

      return _snowman;
   }

   public Identifier getAdvancementId() {
      return this.advancementId;
   }
}
