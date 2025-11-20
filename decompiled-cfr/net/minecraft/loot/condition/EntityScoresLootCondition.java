/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 */
package net.minecraft.loot.condition;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.loot.UniformLootTableRange;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.condition.LootConditionTypes;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.JsonSerializer;

public class EntityScoresLootCondition
implements LootCondition {
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

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.get(this.target.getParameter());
        if (entity == null) {
            return false;
        }
        Scoreboard _snowman2 = entity.world.getScoreboard();
        for (Map.Entry<String, UniformLootTableRange> entry : this.scores.entrySet()) {
            if (this.entityScoreIsInRange(entity, _snowman2, entry.getKey(), entry.getValue())) continue;
            return false;
        }
        return true;
    }

    protected boolean entityScoreIsInRange(Entity entity, Scoreboard scoreboard, String objective, UniformLootTableRange scoreRange) {
        ScoreboardObjective scoreboardObjective = scoreboard.getNullableObjective(objective);
        if (scoreboardObjective == null) {
            return false;
        }
        String _snowman2 = entity.getEntityName();
        if (!scoreboard.playerHasObjective(_snowman2, scoreboardObjective)) {
            return false;
        }
        return scoreRange.contains(scoreboard.getPlayerScore(_snowman2, scoreboardObjective).getScore());
    }

    @Override
    public /* synthetic */ boolean test(Object context) {
        return this.test((LootContext)context);
    }

    public static class Serializer
    implements JsonSerializer<EntityScoresLootCondition> {
        @Override
        public void toJson(JsonObject jsonObject2, EntityScoresLootCondition entityScoresLootCondition, JsonSerializationContext jsonSerializationContext) {
            JsonObject jsonObject2;
            JsonObject jsonObject3 = new JsonObject();
            for (Map.Entry entry : entityScoresLootCondition.scores.entrySet()) {
                jsonObject3.add((String)entry.getKey(), jsonSerializationContext.serialize(entry.getValue()));
            }
            jsonObject2.add("scores", (JsonElement)jsonObject3);
            jsonObject2.add("entity", jsonSerializationContext.serialize((Object)entityScoresLootCondition.target));
        }

        @Override
        public EntityScoresLootCondition fromJson(JsonObject jsonObject2, JsonDeserializationContext jsonDeserializationContext) {
            JsonObject jsonObject2;
            Set set = JsonHelper.getObject(jsonObject2, "scores").entrySet();
            LinkedHashMap _snowman2 = Maps.newLinkedHashMap();
            for (Map.Entry entry : set) {
                _snowman2.put(entry.getKey(), JsonHelper.deserialize((JsonElement)entry.getValue(), "score", jsonDeserializationContext, UniformLootTableRange.class));
            }
            return new EntityScoresLootCondition(_snowman2, JsonHelper.deserialize(jsonObject2, "entity", jsonDeserializationContext, LootContext.EntityTarget.class));
        }

        @Override
        public /* synthetic */ Object fromJson(JsonObject json, JsonDeserializationContext context) {
            return this.fromJson(json, context);
        }
    }
}

