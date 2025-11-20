package net.minecraft.loot;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.util.Map;
import java.util.Set;
import net.minecraft.loot.condition.LootConditionManager;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootManager extends JsonDataLoader {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = LootGsons.getTableGsonBuilder().create();
   private Map<Identifier, LootTable> tables = ImmutableMap.of();
   private final LootConditionManager conditionManager;

   public LootManager(LootConditionManager conditionManager) {
      super(GSON, "loot_tables");
      this.conditionManager = conditionManager;
   }

   public LootTable getTable(Identifier id) {
      return this.tables.getOrDefault(id, LootTable.EMPTY);
   }

   protected void apply(Map<Identifier, JsonElement> _snowman, ResourceManager _snowman, Profiler _snowman) {
      Builder<Identifier, LootTable> _snowmanxxx = ImmutableMap.builder();
      JsonElement _snowmanxxxx = _snowman.remove(LootTables.EMPTY);
      if (_snowmanxxxx != null) {
         LOGGER.warn("Datapack tried to redefine {} loot table, ignoring", LootTables.EMPTY);
      }

      _snowman.forEach((_snowmanxxxxxx, _snowmanxxxxx) -> {
         try {
            LootTable _snowmanxxxxx = (LootTable)GSON.fromJson(_snowmanxxxxx, LootTable.class);
            _snowman.put(_snowmanxxxxxx, _snowmanxxxxx);
         } catch (Exception var4x) {
            LOGGER.error("Couldn't parse loot table {}", _snowmanxxxxxx, var4x);
         }
      });
      _snowmanxxx.put(LootTables.EMPTY, LootTable.EMPTY);
      ImmutableMap<Identifier, LootTable> _snowmanxxxxx = _snowmanxxx.build();
      LootTableReporter _snowmanxxxxxx = new LootTableReporter(LootContextTypes.GENERIC, this.conditionManager::get, _snowmanxxxxx::get);
      _snowmanxxxxx.forEach((_snowmanxxxxxxx, _snowmanxxxxxxxx) -> validate(_snowman, _snowmanxxxxxxx, _snowmanxxxxxxxx));
      _snowmanxxxxxx.getMessages().forEach((key, value) -> LOGGER.warn("Found validation problem in " + key + ": " + value));
      this.tables = _snowmanxxxxx;
   }

   public static void validate(LootTableReporter reporter, Identifier id, LootTable table) {
      table.validate(reporter.withContextType(table.getType()).withTable("{" + id + "}", id));
   }

   public static JsonElement toJson(LootTable table) {
      return GSON.toJsonTree(table);
   }

   public Set<Identifier> getTableIds() {
      return this.tables.keySet();
   }
}
