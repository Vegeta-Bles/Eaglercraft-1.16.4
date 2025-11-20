package net.minecraft.predicate.entity;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.JsonHelper;

public class EntityFlagsPredicate {
   public static final EntityFlagsPredicate ANY = new EntityFlagsPredicate.Builder().build();
   @Nullable
   private final Boolean isOnFire;
   @Nullable
   private final Boolean isSneaking;
   @Nullable
   private final Boolean isSprinting;
   @Nullable
   private final Boolean isSwimming;
   @Nullable
   private final Boolean isBaby;

   public EntityFlagsPredicate(
      @Nullable Boolean isOnFire, @Nullable Boolean isSneaking, @Nullable Boolean isSprinting, @Nullable Boolean isSwimming, @Nullable Boolean isBaby
   ) {
      this.isOnFire = isOnFire;
      this.isSneaking = isSneaking;
      this.isSprinting = isSprinting;
      this.isSwimming = isSwimming;
      this.isBaby = isBaby;
   }

   public boolean test(Entity entity) {
      if (this.isOnFire != null && entity.isOnFire() != this.isOnFire) {
         return false;
      } else if (this.isSneaking != null && entity.isInSneakingPose() != this.isSneaking) {
         return false;
      } else if (this.isSprinting != null && entity.isSprinting() != this.isSprinting) {
         return false;
      } else {
         return this.isSwimming != null && entity.isSwimming() != this.isSwimming
            ? false
            : this.isBaby == null || !(entity instanceof LivingEntity) || ((LivingEntity)entity).isBaby() == this.isBaby;
      }
   }

   @Nullable
   private static Boolean nullableBooleanFromJson(JsonObject json, String key) {
      return json.has(key) ? JsonHelper.getBoolean(json, key) : null;
   }

   public static EntityFlagsPredicate fromJson(@Nullable JsonElement json) {
      if (json != null && !json.isJsonNull()) {
         JsonObject _snowman = JsonHelper.asObject(json, "entity flags");
         Boolean _snowmanx = nullableBooleanFromJson(_snowman, "is_on_fire");
         Boolean _snowmanxx = nullableBooleanFromJson(_snowman, "is_sneaking");
         Boolean _snowmanxxx = nullableBooleanFromJson(_snowman, "is_sprinting");
         Boolean _snowmanxxxx = nullableBooleanFromJson(_snowman, "is_swimming");
         Boolean _snowmanxxxxx = nullableBooleanFromJson(_snowman, "is_baby");
         return new EntityFlagsPredicate(_snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
      } else {
         return ANY;
      }
   }

   private void nullableBooleanToJson(JsonObject json, String key, @Nullable Boolean value) {
      if (value != null) {
         json.addProperty(key, value);
      }
   }

   public JsonElement toJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         this.nullableBooleanToJson(_snowman, "is_on_fire", this.isOnFire);
         this.nullableBooleanToJson(_snowman, "is_sneaking", this.isSneaking);
         this.nullableBooleanToJson(_snowman, "is_sprinting", this.isSprinting);
         this.nullableBooleanToJson(_snowman, "is_swimming", this.isSwimming);
         this.nullableBooleanToJson(_snowman, "is_baby", this.isBaby);
         return _snowman;
      }
   }

   public static class Builder {
      @Nullable
      private Boolean isOnFire;
      @Nullable
      private Boolean isSneaking;
      @Nullable
      private Boolean isSprinting;
      @Nullable
      private Boolean isSwimming;
      @Nullable
      private Boolean isBaby;

      public Builder() {
      }

      public static EntityFlagsPredicate.Builder create() {
         return new EntityFlagsPredicate.Builder();
      }

      public EntityFlagsPredicate.Builder onFire(@Nullable Boolean onFire) {
         this.isOnFire = onFire;
         return this;
      }

      public EntityFlagsPredicate.Builder method_29935(@Nullable Boolean _snowman) {
         this.isBaby = _snowman;
         return this;
      }

      public EntityFlagsPredicate build() {
         return new EntityFlagsPredicate(this.isOnFire, this.isSneaking, this.isSprinting, this.isSwimming, this.isBaby);
      }
   }
}
