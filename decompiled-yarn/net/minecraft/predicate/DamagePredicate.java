package net.minecraft.predicate;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.JsonHelper;

public class DamagePredicate {
   public static final DamagePredicate ANY = DamagePredicate.Builder.create().build();
   private final NumberRange.FloatRange dealt;
   private final NumberRange.FloatRange taken;
   private final EntityPredicate sourceEntity;
   private final Boolean blocked;
   private final DamageSourcePredicate type;

   public DamagePredicate() {
      this.dealt = NumberRange.FloatRange.ANY;
      this.taken = NumberRange.FloatRange.ANY;
      this.sourceEntity = EntityPredicate.ANY;
      this.blocked = null;
      this.type = DamageSourcePredicate.EMPTY;
   }

   public DamagePredicate(
      NumberRange.FloatRange dealt, NumberRange.FloatRange taken, EntityPredicate sourceEntity, @Nullable Boolean blocked, DamageSourcePredicate type
   ) {
      this.dealt = dealt;
      this.taken = taken;
      this.sourceEntity = sourceEntity;
      this.blocked = blocked;
      this.type = type;
   }

   public boolean test(ServerPlayerEntity player, DamageSource source, float dealt, float taken, boolean blocked) {
      if (this == ANY) {
         return true;
      } else if (!this.dealt.test(dealt)) {
         return false;
      } else if (!this.taken.test(taken)) {
         return false;
      } else if (!this.sourceEntity.test(player, source.getAttacker())) {
         return false;
      } else {
         return this.blocked != null && this.blocked != blocked ? false : this.type.test(player, source);
      }
   }

   public static DamagePredicate fromJson(@Nullable JsonElement json) {
      if (json != null && !json.isJsonNull()) {
         JsonObject _snowman = JsonHelper.asObject(json, "damage");
         NumberRange.FloatRange _snowmanx = NumberRange.FloatRange.fromJson(_snowman.get("dealt"));
         NumberRange.FloatRange _snowmanxx = NumberRange.FloatRange.fromJson(_snowman.get("taken"));
         Boolean _snowmanxxx = _snowman.has("blocked") ? JsonHelper.getBoolean(_snowman, "blocked") : null;
         EntityPredicate _snowmanxxxx = EntityPredicate.fromJson(_snowman.get("source_entity"));
         DamageSourcePredicate _snowmanxxxxx = DamageSourcePredicate.fromJson(_snowman.get("type"));
         return new DamagePredicate(_snowmanx, _snowmanxx, _snowmanxxxx, _snowmanxxx, _snowmanxxxxx);
      } else {
         return ANY;
      }
   }

   public JsonElement toJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         _snowman.add("dealt", this.dealt.toJson());
         _snowman.add("taken", this.taken.toJson());
         _snowman.add("source_entity", this.sourceEntity.toJson());
         _snowman.add("type", this.type.toJson());
         if (this.blocked != null) {
            _snowman.addProperty("blocked", this.blocked);
         }

         return _snowman;
      }
   }

   public static class Builder {
      private NumberRange.FloatRange dealt = NumberRange.FloatRange.ANY;
      private NumberRange.FloatRange taken = NumberRange.FloatRange.ANY;
      private EntityPredicate sourceEntity = EntityPredicate.ANY;
      private Boolean blocked;
      private DamageSourcePredicate type = DamageSourcePredicate.EMPTY;

      public Builder() {
      }

      public static DamagePredicate.Builder create() {
         return new DamagePredicate.Builder();
      }

      public DamagePredicate.Builder blocked(Boolean blocked) {
         this.blocked = blocked;
         return this;
      }

      public DamagePredicate.Builder type(DamageSourcePredicate.Builder builder) {
         this.type = builder.build();
         return this;
      }

      public DamagePredicate build() {
         return new DamagePredicate(this.dealt, this.taken, this.sourceEntity, this.blocked, this.type);
      }
   }
}
