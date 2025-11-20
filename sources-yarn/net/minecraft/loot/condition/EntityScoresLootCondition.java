package net.minecraft.loot.condition;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.loot.UniformLootTableRange;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.JsonSerializer;

public class EntityScoresLootCondition implements LootCondition {
   private final Map<String, UniformLootTableRange> scores;
   private final LootContext.EntityTarget target;

   private EntityScoresLootCondition(Map<String, UniformLootTableRange> scores, LootContext.EntityTarget target) {
      this.scores = ImmutableMap.copyOf(scores);
      this.target = target;
   }

   @Override
   public LootConditionType getType() {
      return LootConditionTypes.ENTITY_SCORES;
   }

   @Override
   public Set<LootContextParameter<?>> getRequiredParameters() {
      return ImmutableSet.of(this.target.getParameter());
   }

   public boolean test(LootContext arg) {
      Entity lv = arg.get(this.target.getParameter());
      if (lv == null) {
         return false;
      } else {
         Scoreboard lv2 = lv.world.getScoreboard();

         for (Entry<String, UniformLootTableRange> entry : this.scores.entrySet()) {
            if (!this.entityScoreIsInRange(lv, lv2, entry.getKey(), entry.getValue())) {
               return false;
            }
         }

         return true;
      }
   }

   protected boolean entityScoreIsInRange(Entity entity, Scoreboard scoreboard, String objective, UniformLootTableRange scoreRange) {
      ScoreboardObjective lv = scoreboard.getNullableObjective(objective);
      if (lv == null) {
         return false;
      } else {
         String string2 = entity.getEntityName();
         return !scoreboard.playerHasObjective(string2, lv) ? false : scoreRange.contains(scoreboard.getPlayerScore(string2, lv).getScore());
      }
   }

   public static class Serializer implements JsonSerializer<EntityScoresLootCondition> {
      public Serializer() {
      }

      public void toJson(JsonObject jsonObject, EntityScoresLootCondition arg, JsonSerializationContext jsonSerializationContext) {
         JsonObject jsonObject2 = new JsonObject();

         for (Entry<String, UniformLootTableRange> entry : arg.scores.entrySet()) {
            jsonObject2.add(entry.getKey(), jsonSerializationContext.serialize(entry.getValue()));
         }

         jsonObject.add("scores", jsonObject2);
         jsonObject.add("entity", jsonSerializationContext.serialize(arg.target));
      }

      public EntityScoresLootCondition fromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
         Set<Entry<String, JsonElement>> set = JsonHelper.getObject(jsonObject, "scores").entrySet();
         Map<String, UniformLootTableRange> map = Maps.newLinkedHashMap();

         for (Entry<String, JsonElement> entry : set) {
            map.put(entry.getKey(), JsonHelper.deserialize(entry.getValue(), "score", jsonDeserializationContext, UniformLootTableRange.class));
         }

         return new EntityScoresLootCondition(map, JsonHelper.deserialize(jsonObject, "entity", jsonDeserializationContext, LootContext.EntityTarget.class));
      }
   }
}
