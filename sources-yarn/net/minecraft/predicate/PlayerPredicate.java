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
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.advancement.criterion.CriterionProgress;
import net.minecraft.entity.Entity;
import net.minecraft.recipe.book.RecipeBook;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatHandler;
import net.minecraft.stat.StatType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameMode;

public class PlayerPredicate {
   public static final PlayerPredicate ANY = new PlayerPredicate.Builder().build();
   private final NumberRange.IntRange experienceLevel;
   private final GameMode gamemode;
   private final Map<Stat<?>, NumberRange.IntRange> stats;
   private final Object2BooleanMap<Identifier> recipes;
   private final Map<Identifier, PlayerPredicate.AdvancementPredicate> advancements;

   private static PlayerPredicate.AdvancementPredicate criterionFromJson(JsonElement json) {
      if (json.isJsonPrimitive()) {
         boolean bl = json.getAsBoolean();
         return new PlayerPredicate.CompletedAdvancementPredicate(bl);
      } else {
         Object2BooleanMap<String> object2BooleanMap = new Object2BooleanOpenHashMap();
         JsonObject jsonObject = JsonHelper.asObject(json, "criterion data");
         jsonObject.entrySet().forEach(entry -> {
            boolean bl = JsonHelper.asBoolean((JsonElement)entry.getValue(), "criterion test");
            object2BooleanMap.put(entry.getKey(), bl);
         });
         return new PlayerPredicate.AdvancementCriteriaPredicate(object2BooleanMap);
      }
   }

   private PlayerPredicate(
      NumberRange.IntRange experienceLevel,
      GameMode gamemode,
      Map<Stat<?>, NumberRange.IntRange> stats,
      Object2BooleanMap<Identifier> recipes,
      Map<Identifier, PlayerPredicate.AdvancementPredicate> advancements
   ) {
      this.experienceLevel = experienceLevel;
      this.gamemode = gamemode;
      this.stats = stats;
      this.recipes = recipes;
      this.advancements = advancements;
   }

   public boolean test(Entity entity) {
      if (this == ANY) {
         return true;
      } else if (!(entity instanceof ServerPlayerEntity)) {
         return false;
      } else {
         ServerPlayerEntity lv = (ServerPlayerEntity)entity;
         if (!this.experienceLevel.test(lv.experienceLevel)) {
            return false;
         } else if (this.gamemode != GameMode.NOT_SET && this.gamemode != lv.interactionManager.getGameMode()) {
            return false;
         } else {
            StatHandler lv2 = lv.getStatHandler();

            for (Entry<Stat<?>, NumberRange.IntRange> entry : this.stats.entrySet()) {
               int i = lv2.getStat(entry.getKey());
               if (!entry.getValue().test(i)) {
                  return false;
               }
            }

            RecipeBook lv3 = lv.getRecipeBook();
            ObjectIterator var11 = this.recipes.object2BooleanEntrySet().iterator();

            while (var11.hasNext()) {
               it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry<Identifier> entry2 = (it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry<Identifier>)var11.next();
               if (lv3.contains((Identifier)entry2.getKey()) != entry2.getBooleanValue()) {
                  return false;
               }
            }

            if (!this.advancements.isEmpty()) {
               PlayerAdvancementTracker lv4 = lv.getAdvancementTracker();
               ServerAdvancementLoader lv5 = lv.getServer().getAdvancementLoader();

               for (Entry<Identifier, PlayerPredicate.AdvancementPredicate> entry3 : this.advancements.entrySet()) {
                  Advancement lv6 = lv5.get(entry3.getKey());
                  if (lv6 == null || !entry3.getValue().test(lv4.getProgress(lv6))) {
                     return false;
                  }
               }
            }

            return true;
         }
      }
   }

   public static PlayerPredicate fromJson(@Nullable JsonElement json) {
      if (json != null && !json.isJsonNull()) {
         JsonObject jsonObject = JsonHelper.asObject(json, "player");
         NumberRange.IntRange lv = NumberRange.IntRange.fromJson(jsonObject.get("level"));
         String string = JsonHelper.getString(jsonObject, "gamemode", "");
         GameMode lv2 = GameMode.byName(string, GameMode.NOT_SET);
         Map<Stat<?>, NumberRange.IntRange> map = Maps.newHashMap();
         JsonArray jsonArray = JsonHelper.getArray(jsonObject, "stats", null);
         if (jsonArray != null) {
            for (JsonElement jsonElement2 : jsonArray) {
               JsonObject jsonObject2 = JsonHelper.asObject(jsonElement2, "stats entry");
               Identifier lv3 = new Identifier(JsonHelper.getString(jsonObject2, "type"));
               StatType<?> lv4 = Registry.STAT_TYPE.get(lv3);
               if (lv4 == null) {
                  throw new JsonParseException("Invalid stat type: " + lv3);
               }

               Identifier lv5 = new Identifier(JsonHelper.getString(jsonObject2, "stat"));
               Stat<?> lv6 = getStat(lv4, lv5);
               NumberRange.IntRange lv7 = NumberRange.IntRange.fromJson(jsonObject2.get("value"));
               map.put(lv6, lv7);
            }
         }

         Object2BooleanMap<Identifier> object2BooleanMap = new Object2BooleanOpenHashMap();
         JsonObject jsonObject3 = JsonHelper.getObject(jsonObject, "recipes", new JsonObject());

         for (Entry<String, JsonElement> entry : jsonObject3.entrySet()) {
            Identifier lv8 = new Identifier(entry.getKey());
            boolean bl = JsonHelper.asBoolean(entry.getValue(), "recipe present");
            object2BooleanMap.put(lv8, bl);
         }

         Map<Identifier, PlayerPredicate.AdvancementPredicate> map2 = Maps.newHashMap();
         JsonObject jsonObject4 = JsonHelper.getObject(jsonObject, "advancements", new JsonObject());

         for (Entry<String, JsonElement> entry2 : jsonObject4.entrySet()) {
            Identifier lv9 = new Identifier(entry2.getKey());
            PlayerPredicate.AdvancementPredicate lv10 = criterionFromJson(entry2.getValue());
            map2.put(lv9, lv10);
         }

         return new PlayerPredicate(lv, lv2, map, object2BooleanMap, map2);
      } else {
         return ANY;
      }
   }

   private static <T> Stat<T> getStat(StatType<T> type, Identifier id) {
      Registry<T> lv = type.getRegistry();
      T object = lv.get(id);
      if (object == null) {
         throw new JsonParseException("Unknown object " + id + " for stat type " + Registry.STAT_TYPE.getId(type));
      } else {
         return type.getOrCreateStat(object);
      }
   }

   private static <T> Identifier getStatId(Stat<T> stat) {
      return stat.getType().getRegistry().getId(stat.getValue());
   }

   public JsonElement toJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonObject = new JsonObject();
         jsonObject.add("level", this.experienceLevel.toJson());
         if (this.gamemode != GameMode.NOT_SET) {
            jsonObject.addProperty("gamemode", this.gamemode.getName());
         }

         if (!this.stats.isEmpty()) {
            JsonArray jsonArray = new JsonArray();
            this.stats.forEach((stat, arg2) -> {
               JsonObject jsonObjectx = new JsonObject();
               jsonObjectx.addProperty("type", Registry.STAT_TYPE.getId(stat.getType()).toString());
               jsonObjectx.addProperty("stat", getStatId((Stat<?>)stat).toString());
               jsonObjectx.add("value", arg2.toJson());
               jsonArray.add(jsonObjectx);
            });
            jsonObject.add("stats", jsonArray);
         }

         if (!this.recipes.isEmpty()) {
            JsonObject jsonObject2 = new JsonObject();
            this.recipes.forEach((id, present) -> jsonObject2.addProperty(id.toString(), present));
            jsonObject.add("recipes", jsonObject2);
         }

         if (!this.advancements.isEmpty()) {
            JsonObject jsonObject3 = new JsonObject();
            this.advancements.forEach((id, arg2) -> jsonObject3.add(id.toString(), arg2.toJson()));
            jsonObject.add("advancements", jsonObject3);
         }

         return jsonObject;
      }
   }

   static class AdvancementCriteriaPredicate implements PlayerPredicate.AdvancementPredicate {
      private final Object2BooleanMap<String> criteria;

      public AdvancementCriteriaPredicate(Object2BooleanMap<String> criteria) {
         this.criteria = criteria;
      }

      @Override
      public JsonElement toJson() {
         JsonObject jsonObject = new JsonObject();
         this.criteria.forEach(jsonObject::addProperty);
         return jsonObject;
      }

      public boolean test(AdvancementProgress arg) {
         ObjectIterator var2 = this.criteria.object2BooleanEntrySet().iterator();

         while (var2.hasNext()) {
            it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry<String> entry = (it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry<String>)var2.next();
            CriterionProgress lv = arg.getCriterionProgress((String)entry.getKey());
            if (lv == null || lv.isObtained() != entry.getBooleanValue()) {
               return false;
            }
         }

         return true;
      }
   }

   interface AdvancementPredicate extends Predicate<AdvancementProgress> {
      JsonElement toJson();
   }

   public static class Builder {
      private NumberRange.IntRange experienceLevel = NumberRange.IntRange.ANY;
      private GameMode gamemode = GameMode.NOT_SET;
      private final Map<Stat<?>, NumberRange.IntRange> stats = Maps.newHashMap();
      private final Object2BooleanMap<Identifier> recipes = new Object2BooleanOpenHashMap();
      private final Map<Identifier, PlayerPredicate.AdvancementPredicate> advancements = Maps.newHashMap();

      public Builder() {
      }

      public PlayerPredicate build() {
         return new PlayerPredicate(this.experienceLevel, this.gamemode, this.stats, this.recipes, this.advancements);
      }
   }

   static class CompletedAdvancementPredicate implements PlayerPredicate.AdvancementPredicate {
      private final boolean done;

      public CompletedAdvancementPredicate(boolean done) {
         this.done = done;
      }

      @Override
      public JsonElement toJson() {
         return new JsonPrimitive(this.done);
      }

      public boolean test(AdvancementProgress arg) {
         return arg.isDone() == this.done;
      }
   }
}
