/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonNull
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonPrimitive
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.predicate;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.advancement.criterion.CriterionProgress;
import net.minecraft.entity.Entity;
import net.minecraft.predicate.NumberRange;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerRecipeBook;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameMode;

public class PlayerPredicate {
    public static final PlayerPredicate ANY = new Builder().build();
    private final NumberRange.IntRange experienceLevel;
    private final GameMode gamemode;
    private final Map<Stat<?>, NumberRange.IntRange> stats;
    private final Object2BooleanMap<Identifier> recipes;
    private final Map<Identifier, AdvancementPredicate> advancements;

    private static AdvancementPredicate criterionFromJson(JsonElement json) {
        if (json.isJsonPrimitive()) {
            boolean bl = json.getAsBoolean();
            return new CompletedAdvancementPredicate(bl);
        }
        Object2BooleanOpenHashMap object2BooleanOpenHashMap = new Object2BooleanOpenHashMap();
        JsonObject _snowman2 = JsonHelper.asObject(json, "criterion data");
        _snowman2.entrySet().forEach(arg_0 -> PlayerPredicate.method_22502((Object2BooleanMap)object2BooleanOpenHashMap, arg_0));
        return new AdvancementCriteriaPredicate((Object2BooleanMap<String>)object2BooleanOpenHashMap);
    }

    private PlayerPredicate(NumberRange.IntRange experienceLevel, GameMode gamemode, Map<Stat<?>, NumberRange.IntRange> stats, Object2BooleanMap<Identifier> recipes, Map<Identifier, AdvancementPredicate> advancements) {
        this.experienceLevel = experienceLevel;
        this.gamemode = gamemode;
        this.stats = stats;
        this.recipes = recipes;
        this.advancements = advancements;
    }

    public boolean test(Entity entity) {
        if (this == ANY) {
            return true;
        }
        if (!(entity instanceof ServerPlayerEntity)) {
            return false;
        }
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity;
        if (!this.experienceLevel.test(serverPlayerEntity.experienceLevel)) {
            return false;
        }
        if (this.gamemode != GameMode.NOT_SET && this.gamemode != serverPlayerEntity.interactionManager.getGameMode()) {
            return false;
        }
        ServerStatHandler _snowman2 = serverPlayerEntity.getStatHandler();
        for (Map.Entry<Stat<?>, NumberRange.IntRange> entry : this.stats.entrySet()) {
            int n = _snowman2.getStat(entry.getKey());
            if (entry.getValue().test(n)) continue;
            return false;
        }
        ServerRecipeBook _snowman3 = serverPlayerEntity.getRecipeBook();
        for (Object2BooleanMap.Entry entry : this.recipes.object2BooleanEntrySet()) {
            if (_snowman3.contains((Identifier)entry.getKey()) == entry.getBooleanValue()) continue;
            return false;
        }
        if (!this.advancements.isEmpty()) {
            PlayerAdvancementTracker playerAdvancementTracker = serverPlayerEntity.getAdvancementTracker();
            ServerAdvancementLoader _snowman5 = serverPlayerEntity.getServer().getAdvancementLoader();
            for (Map.Entry<Identifier, AdvancementPredicate> entry : this.advancements.entrySet()) {
                Advancement advancement = _snowman5.get(entry.getKey());
                if (advancement != null && entry.getValue().test(playerAdvancementTracker.getProgress(advancement))) continue;
                return false;
            }
        }
        return true;
    }

    public static PlayerPredicate fromJson(@Nullable JsonElement json) {
        StatType<?> _snowman7;
        Object object;
        JsonElement _snowman92;
        if (json == null || json.isJsonNull()) {
            return ANY;
        }
        JsonObject jsonObject = JsonHelper.asObject(json, "player");
        NumberRange.IntRange _snowman2 = NumberRange.IntRange.fromJson(jsonObject.get("level"));
        String _snowman3 = JsonHelper.getString(jsonObject, "gamemode", "");
        GameMode _snowman4 = GameMode.byName(_snowman3, GameMode.NOT_SET);
        HashMap _snowman5 = Maps.newHashMap();
        JsonArray _snowman6 = JsonHelper.getArray(jsonObject, "stats", null);
        if (_snowman6 != null) {
            for (JsonElement _snowman92 : _snowman6) {
                object = JsonHelper.asObject(_snowman92, "stats entry");
                object2 = new Identifier(JsonHelper.getString((JsonObject)object, "type"));
                _snowman7 = Registry.STAT_TYPE.get((Identifier)object2);
                if (_snowman7 == null) {
                    throw new JsonParseException("Invalid stat type: " + object2);
                }
                Identifier _snowman8 = new Identifier(JsonHelper.getString((JsonObject)object, "stat"));
                object3 = PlayerPredicate.getStat(_snowman7, _snowman8);
                _snowman = NumberRange.IntRange.fromJson(object.get("value"));
                _snowman5.put(object3, _snowman);
            }
        }
        Object2BooleanOpenHashMap object2BooleanOpenHashMap = new Object2BooleanOpenHashMap();
        _snowman92 = JsonHelper.getObject(jsonObject, "recipes", new JsonObject());
        for (Object object2 : _snowman92.entrySet()) {
            _snowman7 = new Identifier((String)object2.getKey());
            boolean bl = JsonHelper.asBoolean((JsonElement)object2.getValue(), "recipe present");
            object2BooleanOpenHashMap.put((Object)_snowman7, bl);
        }
        object = Maps.newHashMap();
        object2 = JsonHelper.getObject(jsonObject, "advancements", new JsonObject());
        for (Map.Entry entry : object2.entrySet()) {
            Object object3 = new Identifier((String)entry.getKey());
            _snowman = PlayerPredicate.criterionFromJson((JsonElement)entry.getValue());
            object.put(object3, _snowman);
        }
        return new PlayerPredicate(_snowman2, _snowman4, _snowman5, (Object2BooleanMap<Identifier>)object2BooleanOpenHashMap, (Map<Identifier, AdvancementPredicate>)object);
    }

    private static <T> Stat<T> getStat(StatType<T> type, Identifier id) {
        Registry<T> registry = type.getRegistry();
        T _snowman2 = registry.get(id);
        if (_snowman2 == null) {
            throw new JsonParseException("Unknown object " + id + " for stat type " + Registry.STAT_TYPE.getId(type));
        }
        return type.getOrCreateStat(_snowman2);
    }

    private static <T> Identifier getStatId(Stat<T> stat) {
        return stat.getType().getRegistry().getId(stat.getValue());
    }

    public JsonElement toJson() {
        JsonArray jsonArray;
        if (this == ANY) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("level", this.experienceLevel.toJson());
        if (this.gamemode != GameMode.NOT_SET) {
            jsonObject.addProperty("gamemode", this.gamemode.getName());
        }
        if (!this.stats.isEmpty()) {
            jsonArray = new JsonArray();
            this.stats.forEach((stat, intRange) -> {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("type", Registry.STAT_TYPE.getId(stat.getType()).toString());
                jsonObject.addProperty("stat", PlayerPredicate.getStatId(stat).toString());
                jsonObject.add("value", intRange.toJson());
                jsonArray.add((JsonElement)jsonObject);
            });
            jsonObject.add("stats", (JsonElement)jsonArray);
        }
        if (!this.recipes.isEmpty()) {
            jsonArray = new JsonObject();
            this.recipes.forEach((arg_0, arg_1) -> PlayerPredicate.method_22501((JsonObject)jsonArray, arg_0, arg_1));
            jsonObject.add("recipes", (JsonElement)jsonArray);
        }
        if (!this.advancements.isEmpty()) {
            jsonArray = new JsonObject();
            this.advancements.forEach((arg_0, arg_1) -> PlayerPredicate.method_22500((JsonObject)jsonArray, arg_0, arg_1));
            jsonObject.add("advancements", (JsonElement)jsonArray);
        }
        return jsonObject;
    }

    private static /* synthetic */ void method_22500(JsonObject jsonObject, Identifier id, AdvancementPredicate advancementPredicate) {
        jsonObject.add(id.toString(), advancementPredicate.toJson());
    }

    private static /* synthetic */ void method_22501(JsonObject jsonObject, Identifier id, Boolean present) {
        jsonObject.addProperty(id.toString(), present);
    }

    private static /* synthetic */ void method_22502(Object2BooleanMap object2BooleanMap, Map.Entry entry) {
        boolean bl = JsonHelper.asBoolean((JsonElement)entry.getValue(), "criterion test");
        object2BooleanMap.put(entry.getKey(), bl);
    }

    public static class Builder {
        private NumberRange.IntRange experienceLevel = NumberRange.IntRange.ANY;
        private GameMode gamemode = GameMode.NOT_SET;
        private final Map<Stat<?>, NumberRange.IntRange> stats = Maps.newHashMap();
        private final Object2BooleanMap<Identifier> recipes = new Object2BooleanOpenHashMap();
        private final Map<Identifier, AdvancementPredicate> advancements = Maps.newHashMap();

        public PlayerPredicate build() {
            return new PlayerPredicate(this.experienceLevel, this.gamemode, this.stats, this.recipes, this.advancements);
        }
    }

    static class AdvancementCriteriaPredicate
    implements AdvancementPredicate {
        private final Object2BooleanMap<String> criteria;

        public AdvancementCriteriaPredicate(Object2BooleanMap<String> criteria) {
            this.criteria = criteria;
        }

        @Override
        public JsonElement toJson() {
            JsonObject jsonObject = new JsonObject();
            this.criteria.forEach((arg_0, arg_1) -> ((JsonObject)jsonObject).addProperty(arg_0, arg_1));
            return jsonObject;
        }

        @Override
        public boolean test(AdvancementProgress advancementProgress) {
            for (Object2BooleanMap.Entry entry : this.criteria.object2BooleanEntrySet()) {
                CriterionProgress criterionProgress = advancementProgress.getCriterionProgress((String)entry.getKey());
                if (criterionProgress != null && criterionProgress.isObtained() == entry.getBooleanValue()) continue;
                return false;
            }
            return true;
        }

        @Override
        public /* synthetic */ boolean test(Object progress) {
            return this.test((AdvancementProgress)progress);
        }
    }

    static class CompletedAdvancementPredicate
    implements AdvancementPredicate {
        private final boolean done;

        public CompletedAdvancementPredicate(boolean done) {
            this.done = done;
        }

        @Override
        public JsonElement toJson() {
            return new JsonPrimitive(Boolean.valueOf(this.done));
        }

        @Override
        public boolean test(AdvancementProgress advancementProgress) {
            return advancementProgress.isDone() == this.done;
        }

        @Override
        public /* synthetic */ boolean test(Object progress) {
            return this.test((AdvancementProgress)progress);
        }
    }

    static interface AdvancementPredicate
    extends Predicate<AdvancementProgress> {
        public JsonElement toJson();
    }
}

