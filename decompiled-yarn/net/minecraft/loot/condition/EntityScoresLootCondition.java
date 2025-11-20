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

   public boolean test(LootContext _snowman) {
      Entity _snowmanx = _snowman.get(this.target.getParameter());
      if (_snowmanx == null) {
         return false;
      } else {
         Scoreboard _snowmanxx = _snowmanx.world.getScoreboard();

         for (Entry<String, UniformLootTableRange> _snowmanxxx : this.scores.entrySet()) {
            if (!this.entityScoreIsInRange(_snowmanx, _snowmanxx, _snowmanxxx.getKey(), _snowmanxxx.getValue())) {
               return false;
            }
         }

         return true;
      }
   }

   protected boolean entityScoreIsInRange(Entity entity, Scoreboard scoreboard, String objective, UniformLootTableRange scoreRange) {
      ScoreboardObjective _snowman = scoreboard.getNullableObjective(objective);
      if (_snowman == null) {
         return false;
      } else {
         String _snowmanx = entity.getEntityName();
         return !scoreboard.playerHasObjective(_snowmanx, _snowman) ? false : scoreRange.contains(scoreboard.getPlayerScore(_snowmanx, _snowman).getScore());
      }
   }

   public static class Serializer implements JsonSerializer<EntityScoresLootCondition> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, EntityScoresLootCondition _snowman, JsonSerializationContext _snowman) {
         JsonObject _snowmanxxx = new JsonObject();

         for (Entry<String, UniformLootTableRange> _snowmanxxxx : _snowman.scores.entrySet()) {
            _snowmanxxx.add(_snowmanxxxx.getKey(), _snowman.serialize(_snowmanxxxx.getValue()));
         }

         _snowman.add("scores", _snowmanxxx);
         _snowman.add("entity", _snowman.serialize(_snowman.target));
      }

      public EntityScoresLootCondition fromJson(JsonObject _snowman, JsonDeserializationContext _snowman) {
         Set<Entry<String, JsonElement>> _snowmanxx = JsonHelper.getObject(_snowman, "scores").entrySet();
         Map<String, UniformLootTableRange> _snowmanxxx = Maps.newLinkedHashMap();

         for (Entry<String, JsonElement> _snowmanxxxx : _snowmanxx) {
            _snowmanxxx.put(_snowmanxxxx.getKey(), JsonHelper.deserialize(_snowmanxxxx.getValue(), "score", _snowman, UniformLootTableRange.class));
         }

         return new EntityScoresLootCondition(_snowmanxxx, JsonHelper.deserialize(_snowman, "entity", _snowman, LootContext.EntityTarget.class));
      }
   }
}
