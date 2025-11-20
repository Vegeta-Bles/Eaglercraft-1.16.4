package net.minecraft.predicate.entity;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.predicate.NumberRange;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public class EntityEffectPredicate {
   public static final EntityEffectPredicate EMPTY = new EntityEffectPredicate(Collections.emptyMap());
   private final Map<StatusEffect, EntityEffectPredicate.EffectData> effects;

   public EntityEffectPredicate(Map<StatusEffect, EntityEffectPredicate.EffectData> effects) {
      this.effects = effects;
   }

   public static EntityEffectPredicate create() {
      return new EntityEffectPredicate(Maps.newLinkedHashMap());
   }

   public EntityEffectPredicate withEffect(StatusEffect statusEffect) {
      this.effects.put(statusEffect, new EntityEffectPredicate.EffectData());
      return this;
   }

   public boolean test(Entity entity) {
      if (this == EMPTY) {
         return true;
      } else {
         return entity instanceof LivingEntity ? this.test(((LivingEntity)entity).getActiveStatusEffects()) : false;
      }
   }

   public boolean test(LivingEntity livingEntity) {
      return this == EMPTY ? true : this.test(livingEntity.getActiveStatusEffects());
   }

   public boolean test(Map<StatusEffect, StatusEffectInstance> effects) {
      if (this == EMPTY) {
         return true;
      } else {
         for (Entry<StatusEffect, EntityEffectPredicate.EffectData> _snowman : this.effects.entrySet()) {
            StatusEffectInstance _snowmanx = effects.get(_snowman.getKey());
            if (!_snowman.getValue().test(_snowmanx)) {
               return false;
            }
         }

         return true;
      }
   }

   public static EntityEffectPredicate fromJson(@Nullable JsonElement json) {
      if (json != null && !json.isJsonNull()) {
         JsonObject _snowman = JsonHelper.asObject(json, "effects");
         Map<StatusEffect, EntityEffectPredicate.EffectData> _snowmanx = Maps.newLinkedHashMap();

         for (Entry<String, JsonElement> _snowmanxx : _snowman.entrySet()) {
            Identifier _snowmanxxx = new Identifier(_snowmanxx.getKey());
            StatusEffect _snowmanxxxx = Registry.STATUS_EFFECT.getOrEmpty(_snowmanxxx).orElseThrow(() -> new JsonSyntaxException("Unknown effect '" + _snowman + "'"));
            EntityEffectPredicate.EffectData _snowmanxxxxx = EntityEffectPredicate.EffectData.fromJson(JsonHelper.asObject(_snowmanxx.getValue(), _snowmanxx.getKey()));
            _snowmanx.put(_snowmanxxxx, _snowmanxxxxx);
         }

         return new EntityEffectPredicate(_snowmanx);
      } else {
         return EMPTY;
      }
   }

   public JsonElement toJson() {
      if (this == EMPTY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();

         for (Entry<StatusEffect, EntityEffectPredicate.EffectData> _snowmanx : this.effects.entrySet()) {
            _snowman.add(Registry.STATUS_EFFECT.getId(_snowmanx.getKey()).toString(), _snowmanx.getValue().toJson());
         }

         return _snowman;
      }
   }

   public static class EffectData {
      private final NumberRange.IntRange amplifier;
      private final NumberRange.IntRange duration;
      @Nullable
      private final Boolean ambient;
      @Nullable
      private final Boolean visible;

      public EffectData(NumberRange.IntRange amplifier, NumberRange.IntRange duration, @Nullable Boolean ambient, @Nullable Boolean visible) {
         this.amplifier = amplifier;
         this.duration = duration;
         this.ambient = ambient;
         this.visible = visible;
      }

      public EffectData() {
         this(NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, null, null);
      }

      public boolean test(@Nullable StatusEffectInstance statusEffectInstance) {
         if (statusEffectInstance == null) {
            return false;
         } else if (!this.amplifier.test(statusEffectInstance.getAmplifier())) {
            return false;
         } else if (!this.duration.test(statusEffectInstance.getDuration())) {
            return false;
         } else {
            return this.ambient != null && this.ambient != statusEffectInstance.isAmbient()
               ? false
               : this.visible == null || this.visible == statusEffectInstance.shouldShowParticles();
         }
      }

      public JsonElement toJson() {
         JsonObject _snowman = new JsonObject();
         _snowman.add("amplifier", this.amplifier.toJson());
         _snowman.add("duration", this.duration.toJson());
         _snowman.addProperty("ambient", this.ambient);
         _snowman.addProperty("visible", this.visible);
         return _snowman;
      }

      public static EntityEffectPredicate.EffectData fromJson(JsonObject json) {
         NumberRange.IntRange _snowman = NumberRange.IntRange.fromJson(json.get("amplifier"));
         NumberRange.IntRange _snowmanx = NumberRange.IntRange.fromJson(json.get("duration"));
         Boolean _snowmanxx = json.has("ambient") ? JsonHelper.getBoolean(json, "ambient") : null;
         Boolean _snowmanxxx = json.has("visible") ? JsonHelper.getBoolean(json, "visible") : null;
         return new EntityEffectPredicate.EffectData(_snowman, _snowmanx, _snowmanxx, _snowmanxxx);
      }
   }
}
