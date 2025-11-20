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
         boolean _snowman = json.getAsBoolean();
         return new PlayerPredicate.CompletedAdvancementPredicate(_snowman);
      } else {
         Object2BooleanMap<String> _snowman = new Object2BooleanOpenHashMap();
         JsonObject _snowmanx = JsonHelper.asObject(json, "criterion data");
         _snowmanx.entrySet().forEach(_snowmanxx -> {
            boolean _snowmanxxx = JsonHelper.asBoolean((JsonElement)_snowmanxx.getValue(), "criterion test");
            _snowman.put(_snowmanxx.getKey(), _snowmanxxx);
         });
         return new PlayerPredicate.AdvancementCriteriaPredicate(_snowman);
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
         ServerPlayerEntity _snowman = (ServerPlayerEntity)entity;
         if (!this.experienceLevel.test(_snowman.experienceLevel)) {
            return false;
         } else if (this.gamemode != GameMode.NOT_SET && this.gamemode != _snowman.interactionManager.getGameMode()) {
            return false;
         } else {
            StatHandler _snowmanx = _snowman.getStatHandler();

            for (Entry<Stat<?>, NumberRange.IntRange> _snowmanxx : this.stats.entrySet()) {
               int _snowmanxxx = _snowmanx.getStat(_snowmanxx.getKey());
               if (!_snowmanxx.getValue().test(_snowmanxxx)) {
                  return false;
               }
            }

            RecipeBook _snowmanxxx = _snowman.getRecipeBook();
            ObjectIterator var11 = this.recipes.object2BooleanEntrySet().iterator();

            while (var11.hasNext()) {
               it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry<Identifier> _snowmanxxxx = (it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry<Identifier>)var11.next();
               if (_snowmanxxx.contains((Identifier)_snowmanxxxx.getKey()) != _snowmanxxxx.getBooleanValue()) {
                  return false;
               }
            }

            if (!this.advancements.isEmpty()) {
               PlayerAdvancementTracker _snowmanxxxx = _snowman.getAdvancementTracker();
               ServerAdvancementLoader _snowmanxxxxx = _snowman.getServer().getAdvancementLoader();

               for (Entry<Identifier, PlayerPredicate.AdvancementPredicate> _snowmanxxxxxx : this.advancements.entrySet()) {
                  Advancement _snowmanxxxxxxx = _snowmanxxxxx.get(_snowmanxxxxxx.getKey());
                  if (_snowmanxxxxxxx == null || !_snowmanxxxxxx.getValue().test(_snowmanxxxx.getProgress(_snowmanxxxxxxx))) {
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
         JsonObject _snowman = JsonHelper.asObject(json, "player");
         NumberRange.IntRange _snowmanx = NumberRange.IntRange.fromJson(_snowman.get("level"));
         String _snowmanxx = JsonHelper.getString(_snowman, "gamemode", "");
         GameMode _snowmanxxx = GameMode.byName(_snowmanxx, GameMode.NOT_SET);
         Map<Stat<?>, NumberRange.IntRange> _snowmanxxxx = Maps.newHashMap();
         JsonArray _snowmanxxxxx = JsonHelper.getArray(_snowman, "stats", null);
         if (_snowmanxxxxx != null) {
            for (JsonElement _snowmanxxxxxx : _snowmanxxxxx) {
               JsonObject _snowmanxxxxxxx = JsonHelper.asObject(_snowmanxxxxxx, "stats entry");
               Identifier _snowmanxxxxxxxx = new Identifier(JsonHelper.getString(_snowmanxxxxxxx, "type"));
               StatType<?> _snowmanxxxxxxxxx = Registry.STAT_TYPE.get(_snowmanxxxxxxxx);
               if (_snowmanxxxxxxxxx == null) {
                  throw new JsonParseException("Invalid stat type: " + _snowmanxxxxxxxx);
               }

               Identifier _snowmanxxxxxxxxxx = new Identifier(JsonHelper.getString(_snowmanxxxxxxx, "stat"));
               Stat<?> _snowmanxxxxxxxxxxx = getStat(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
               NumberRange.IntRange _snowmanxxxxxxxxxxxx = NumberRange.IntRange.fromJson(_snowmanxxxxxxx.get("value"));
               _snowmanxxxx.put(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
            }
         }

         Object2BooleanMap<Identifier> _snowmanxxxxxx = new Object2BooleanOpenHashMap();
         JsonObject _snowmanxxxxxxx = JsonHelper.getObject(_snowman, "recipes", new JsonObject());

         for (Entry<String, JsonElement> _snowmanxxxxxxxx : _snowmanxxxxxxx.entrySet()) {
            Identifier _snowmanxxxxxxxxx = new Identifier(_snowmanxxxxxxxx.getKey());
            boolean _snowmanxxxxxxxxxx = JsonHelper.asBoolean(_snowmanxxxxxxxx.getValue(), "recipe present");
            _snowmanxxxxxx.put(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
         }

         Map<Identifier, PlayerPredicate.AdvancementPredicate> _snowmanxxxxxxxx = Maps.newHashMap();
         JsonObject _snowmanxxxxxxxxx = JsonHelper.getObject(_snowman, "advancements", new JsonObject());

         for (Entry<String, JsonElement> _snowmanxxxxxxxxxx : _snowmanxxxxxxxxx.entrySet()) {
            Identifier _snowmanxxxxxxxxxxx = new Identifier(_snowmanxxxxxxxxxx.getKey());
            PlayerPredicate.AdvancementPredicate _snowmanxxxxxxxxxxxx = criterionFromJson(_snowmanxxxxxxxxxx.getValue());
            _snowmanxxxxxxxx.put(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
         }

         return new PlayerPredicate(_snowmanx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxxx, _snowmanxxxxxxxx);
      } else {
         return ANY;
      }
   }

   private static <T> Stat<T> getStat(StatType<T> type, Identifier id) {
      Registry<T> _snowman = type.getRegistry();
      T _snowmanx = _snowman.get(id);
      if (_snowmanx == null) {
         throw new JsonParseException("Unknown object " + id + " for stat type " + Registry.STAT_TYPE.getId(type));
      } else {
         return type.getOrCreateStat(_snowmanx);
      }
   }

   private static <T> Identifier getStatId(Stat<T> stat) {
      return stat.getType().getRegistry().getId(stat.getValue());
   }

   public JsonElement toJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         _snowman.add("level", this.experienceLevel.toJson());
         if (this.gamemode != GameMode.NOT_SET) {
            _snowman.addProperty("gamemode", this.gamemode.getName());
         }

         if (!this.stats.isEmpty()) {
            JsonArray _snowmanx = new JsonArray();
            this.stats.forEach((stat, _snowmanxx) -> {
               JsonObject _snowmanxxx = new JsonObject();
               _snowmanxxx.addProperty("type", Registry.STAT_TYPE.getId(stat.getType()).toString());
               _snowmanxxx.addProperty("stat", getStatId((Stat<?>)stat).toString());
               _snowmanxxx.add("value", _snowmanxx.toJson());
               _snowman.add(_snowmanxxx);
            });
            _snowman.add("stats", _snowmanx);
         }

         if (!this.recipes.isEmpty()) {
            JsonObject _snowmanx = new JsonObject();
            this.recipes.forEach((id, present) -> _snowman.addProperty(id.toString(), present));
            _snowman.add("recipes", _snowmanx);
         }

         if (!this.advancements.isEmpty()) {
            JsonObject _snowmanx = new JsonObject();
            this.advancements.forEach((id, _snowmanxx) -> _snowman.add(id.toString(), _snowmanxx.toJson()));
            _snowman.add("advancements", _snowmanx);
         }

         return _snowman;
      }
   }

   static class AdvancementCriteriaPredicate implements PlayerPredicate.AdvancementPredicate {
      private final Object2BooleanMap<String> criteria;

      public AdvancementCriteriaPredicate(Object2BooleanMap<String> criteria) {
         this.criteria = criteria;
      }

      @Override
      public JsonElement toJson() {
         JsonObject _snowman = new JsonObject();
         this.criteria.forEach(_snowman::addProperty);
         return _snowman;
      }

      public boolean test(AdvancementProgress _snowman) {
         ObjectIterator var2 = this.criteria.object2BooleanEntrySet().iterator();

         while (var2.hasNext()) {
            it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry<String> _snowmanx = (it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry<String>)var2.next();
            CriterionProgress _snowmanxx = _snowman.getCriterionProgress((String)_snowmanx.getKey());
            if (_snowmanxx == null || _snowmanxx.isObtained() != _snowmanx.getBooleanValue()) {
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

      public boolean test(AdvancementProgress _snowman) {
         return _snowman.isDone() == this.done;
      }
   }
}
