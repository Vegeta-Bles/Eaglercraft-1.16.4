package net.minecraft.predicate.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionTypes;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.PlayerPredicate;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.Vec3d;

public class EntityPredicate {
   public static final EntityPredicate ANY = new EntityPredicate(
      EntityTypePredicate.ANY,
      DistancePredicate.ANY,
      LocationPredicate.ANY,
      EntityEffectPredicate.EMPTY,
      NbtPredicate.ANY,
      EntityFlagsPredicate.ANY,
      EntityEquipmentPredicate.ANY,
      PlayerPredicate.ANY,
      FishingHookPredicate.ANY,
      null,
      null
   );
   private final EntityTypePredicate type;
   private final DistancePredicate distance;
   private final LocationPredicate location;
   private final EntityEffectPredicate effects;
   private final NbtPredicate nbt;
   private final EntityFlagsPredicate flags;
   private final EntityEquipmentPredicate equipment;
   private final PlayerPredicate player;
   private final FishingHookPredicate fishingHook;
   private final EntityPredicate vehicle;
   private final EntityPredicate targetedEntity;
   @Nullable
   private final String team;
   @Nullable
   private final Identifier catType;

   private EntityPredicate(
      EntityTypePredicate _snowman,
      DistancePredicate _snowman,
      LocationPredicate _snowman,
      EntityEffectPredicate _snowman,
      NbtPredicate _snowman,
      EntityFlagsPredicate _snowman,
      EntityEquipmentPredicate _snowman,
      PlayerPredicate _snowman,
      FishingHookPredicate _snowman,
      @Nullable String _snowman,
      @Nullable Identifier _snowman
   ) {
      this.type = _snowman;
      this.distance = _snowman;
      this.location = _snowman;
      this.effects = _snowman;
      this.nbt = _snowman;
      this.flags = _snowman;
      this.equipment = _snowman;
      this.player = _snowman;
      this.fishingHook = _snowman;
      this.vehicle = this;
      this.targetedEntity = this;
      this.team = _snowman;
      this.catType = _snowman;
   }

   private EntityPredicate(
      EntityTypePredicate type,
      DistancePredicate distance,
      LocationPredicate location,
      EntityEffectPredicate effects,
      NbtPredicate nbt,
      EntityFlagsPredicate flags,
      EntityEquipmentPredicate equipment,
      PlayerPredicate player,
      FishingHookPredicate fishingHook,
      EntityPredicate vehicle,
      EntityPredicate targetedEntity,
      @Nullable String team,
      @Nullable Identifier catType
   ) {
      this.type = type;
      this.distance = distance;
      this.location = location;
      this.effects = effects;
      this.nbt = nbt;
      this.flags = flags;
      this.equipment = equipment;
      this.player = player;
      this.fishingHook = fishingHook;
      this.vehicle = vehicle;
      this.targetedEntity = targetedEntity;
      this.team = team;
      this.catType = catType;
   }

   public boolean test(ServerPlayerEntity player, @Nullable Entity entity) {
      return this.test(player.getServerWorld(), player.getPos(), entity);
   }

   public boolean test(ServerWorld world, @Nullable Vec3d pos, @Nullable Entity entity) {
      if (this == ANY) {
         return true;
      } else if (entity == null) {
         return false;
      } else if (!this.type.matches(entity.getType())) {
         return false;
      } else {
         if (pos == null) {
            if (this.distance != DistancePredicate.ANY) {
               return false;
            }
         } else if (!this.distance.test(pos.x, pos.y, pos.z, entity.getX(), entity.getY(), entity.getZ())) {
            return false;
         }

         if (!this.location.test(world, entity.getX(), entity.getY(), entity.getZ())) {
            return false;
         } else if (!this.effects.test(entity)) {
            return false;
         } else if (!this.nbt.test(entity)) {
            return false;
         } else if (!this.flags.test(entity)) {
            return false;
         } else if (!this.equipment.test(entity)) {
            return false;
         } else if (!this.player.test(entity)) {
            return false;
         } else if (!this.fishingHook.test(entity)) {
            return false;
         } else if (!this.vehicle.test(world, pos, entity.getVehicle())) {
            return false;
         } else if (!this.targetedEntity.test(world, pos, entity instanceof MobEntity ? ((MobEntity)entity).getTarget() : null)) {
            return false;
         } else {
            if (this.team != null) {
               AbstractTeam _snowman = entity.getScoreboardTeam();
               if (_snowman == null || !this.team.equals(_snowman.getName())) {
                  return false;
               }
            }

            return this.catType == null || entity instanceof CatEntity && ((CatEntity)entity).getTexture().equals(this.catType);
         }
      }
   }

   public static EntityPredicate fromJson(@Nullable JsonElement json) {
      if (json != null && !json.isJsonNull()) {
         JsonObject _snowman = JsonHelper.asObject(json, "entity");
         EntityTypePredicate _snowmanx = EntityTypePredicate.fromJson(_snowman.get("type"));
         DistancePredicate _snowmanxx = DistancePredicate.fromJson(_snowman.get("distance"));
         LocationPredicate _snowmanxxx = LocationPredicate.fromJson(_snowman.get("location"));
         EntityEffectPredicate _snowmanxxxx = EntityEffectPredicate.fromJson(_snowman.get("effects"));
         NbtPredicate _snowmanxxxxx = NbtPredicate.fromJson(_snowman.get("nbt"));
         EntityFlagsPredicate _snowmanxxxxxx = EntityFlagsPredicate.fromJson(_snowman.get("flags"));
         EntityEquipmentPredicate _snowmanxxxxxxx = EntityEquipmentPredicate.fromJson(_snowman.get("equipment"));
         PlayerPredicate _snowmanxxxxxxxx = PlayerPredicate.fromJson(_snowman.get("player"));
         FishingHookPredicate _snowmanxxxxxxxxx = FishingHookPredicate.fromJson(_snowman.get("fishing_hook"));
         EntityPredicate _snowmanxxxxxxxxxx = fromJson(_snowman.get("vehicle"));
         EntityPredicate _snowmanxxxxxxxxxxx = fromJson(_snowman.get("targeted_entity"));
         String _snowmanxxxxxxxxxxxx = JsonHelper.getString(_snowman, "team", null);
         Identifier _snowmanxxxxxxxxxxxxx = _snowman.has("catType") ? new Identifier(JsonHelper.getString(_snowman, "catType")) : null;
         return new EntityPredicate.Builder()
            .type(_snowmanx)
            .distance(_snowmanxx)
            .location(_snowmanxxx)
            .effects(_snowmanxxxx)
            .nbt(_snowmanxxxxx)
            .flags(_snowmanxxxxxx)
            .equipment(_snowmanxxxxxxx)
            .player(_snowmanxxxxxxxx)
            .fishHook(_snowmanxxxxxxxxx)
            .team(_snowmanxxxxxxxxxxxx)
            .vehicle(_snowmanxxxxxxxxxx)
            .targetedEntity(_snowmanxxxxxxxxxxx)
            .catType(_snowmanxxxxxxxxxxxxx)
            .build();
      } else {
         return ANY;
      }
   }

   public JsonElement toJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         _snowman.add("type", this.type.toJson());
         _snowman.add("distance", this.distance.toJson());
         _snowman.add("location", this.location.toJson());
         _snowman.add("effects", this.effects.toJson());
         _snowman.add("nbt", this.nbt.toJson());
         _snowman.add("flags", this.flags.toJson());
         _snowman.add("equipment", this.equipment.toJson());
         _snowman.add("player", this.player.toJson());
         _snowman.add("fishing_hook", this.fishingHook.toJson());
         _snowman.add("vehicle", this.vehicle.toJson());
         _snowman.add("targeted_entity", this.targetedEntity.toJson());
         _snowman.addProperty("team", this.team);
         if (this.catType != null) {
            _snowman.addProperty("catType", this.catType.toString());
         }

         return _snowman;
      }
   }

   public static LootContext createAdvancementEntityLootContext(ServerPlayerEntity player, Entity target) {
      return new LootContext.Builder(player.getServerWorld())
         .parameter(LootContextParameters.THIS_ENTITY, target)
         .parameter(LootContextParameters.ORIGIN, player.getPos())
         .random(player.getRandom())
         .build(LootContextTypes.ADVANCEMENT_ENTITY);
   }

   public static class Builder {
      private EntityTypePredicate type = EntityTypePredicate.ANY;
      private DistancePredicate distance = DistancePredicate.ANY;
      private LocationPredicate location = LocationPredicate.ANY;
      private EntityEffectPredicate effects = EntityEffectPredicate.EMPTY;
      private NbtPredicate nbt = NbtPredicate.ANY;
      private EntityFlagsPredicate flags = EntityFlagsPredicate.ANY;
      private EntityEquipmentPredicate equipment = EntityEquipmentPredicate.ANY;
      private PlayerPredicate player = PlayerPredicate.ANY;
      private FishingHookPredicate fishHook = FishingHookPredicate.ANY;
      private EntityPredicate vehicle = EntityPredicate.ANY;
      private EntityPredicate targetedEntity = EntityPredicate.ANY;
      private String team;
      private Identifier catType;

      public Builder() {
      }

      public static EntityPredicate.Builder create() {
         return new EntityPredicate.Builder();
      }

      public EntityPredicate.Builder type(EntityType<?> type) {
         this.type = EntityTypePredicate.create(type);
         return this;
      }

      public EntityPredicate.Builder type(Tag<EntityType<?>> tag) {
         this.type = EntityTypePredicate.create(tag);
         return this;
      }

      public EntityPredicate.Builder type(Identifier catType) {
         this.catType = catType;
         return this;
      }

      public EntityPredicate.Builder type(EntityTypePredicate type) {
         this.type = type;
         return this;
      }

      public EntityPredicate.Builder distance(DistancePredicate distance) {
         this.distance = distance;
         return this;
      }

      public EntityPredicate.Builder location(LocationPredicate location) {
         this.location = location;
         return this;
      }

      public EntityPredicate.Builder effects(EntityEffectPredicate effects) {
         this.effects = effects;
         return this;
      }

      public EntityPredicate.Builder nbt(NbtPredicate nbt) {
         this.nbt = nbt;
         return this;
      }

      public EntityPredicate.Builder flags(EntityFlagsPredicate flags) {
         this.flags = flags;
         return this;
      }

      public EntityPredicate.Builder equipment(EntityEquipmentPredicate equipment) {
         this.equipment = equipment;
         return this;
      }

      public EntityPredicate.Builder player(PlayerPredicate player) {
         this.player = player;
         return this;
      }

      public EntityPredicate.Builder fishHook(FishingHookPredicate fishHook) {
         this.fishHook = fishHook;
         return this;
      }

      public EntityPredicate.Builder vehicle(EntityPredicate vehicle) {
         this.vehicle = vehicle;
         return this;
      }

      public EntityPredicate.Builder targetedEntity(EntityPredicate targetedEntity) {
         this.targetedEntity = targetedEntity;
         return this;
      }

      public EntityPredicate.Builder team(@Nullable String team) {
         this.team = team;
         return this;
      }

      public EntityPredicate.Builder catType(@Nullable Identifier catType) {
         this.catType = catType;
         return this;
      }

      public EntityPredicate build() {
         return new EntityPredicate(
            this.type,
            this.distance,
            this.location,
            this.effects,
            this.nbt,
            this.flags,
            this.equipment,
            this.player,
            this.fishHook,
            this.vehicle,
            this.targetedEntity,
            this.team,
            this.catType
         );
      }
   }

   public static class Extended {
      public static final EntityPredicate.Extended EMPTY = new EntityPredicate.Extended(new LootCondition[0]);
      private final LootCondition[] conditions;
      private final Predicate<LootContext> combinedCondition;

      private Extended(LootCondition[] conditions) {
         this.conditions = conditions;
         this.combinedCondition = LootConditionTypes.joinAnd(conditions);
      }

      public static EntityPredicate.Extended create(LootCondition... conditions) {
         return new EntityPredicate.Extended(conditions);
      }

      public static EntityPredicate.Extended getInJson(JsonObject root, String key, AdvancementEntityPredicateDeserializer predicateDeserializer) {
         JsonElement _snowman = root.get(key);
         return fromJson(key, predicateDeserializer, _snowman);
      }

      public static EntityPredicate.Extended[] requireInJson(JsonObject root, String key, AdvancementEntityPredicateDeserializer predicateDeserializer) {
         JsonElement _snowman = root.get(key);
         if (_snowman != null && !_snowman.isJsonNull()) {
            JsonArray _snowmanx = JsonHelper.asArray(_snowman, key);
            EntityPredicate.Extended[] _snowmanxx = new EntityPredicate.Extended[_snowmanx.size()];

            for (int _snowmanxxx = 0; _snowmanxxx < _snowmanx.size(); _snowmanxxx++) {
               _snowmanxx[_snowmanxxx] = fromJson(key + "[" + _snowmanxxx + "]", predicateDeserializer, _snowmanx.get(_snowmanxxx));
            }

            return _snowmanxx;
         } else {
            return new EntityPredicate.Extended[0];
         }
      }

      private static EntityPredicate.Extended fromJson(String key, AdvancementEntityPredicateDeserializer predicateDeserializer, @Nullable JsonElement json) {
         if (json != null && json.isJsonArray()) {
            LootCondition[] _snowman = predicateDeserializer.loadConditions(
               json.getAsJsonArray(), predicateDeserializer.getAdvancementId().toString() + "/" + key, LootContextTypes.ADVANCEMENT_ENTITY
            );
            return new EntityPredicate.Extended(_snowman);
         } else {
            EntityPredicate _snowman = EntityPredicate.fromJson(json);
            return ofLegacy(_snowman);
         }
      }

      public static EntityPredicate.Extended ofLegacy(EntityPredicate predicate) {
         if (predicate == EntityPredicate.ANY) {
            return EMPTY;
         } else {
            LootCondition _snowman = EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, predicate).build();
            return new EntityPredicate.Extended(new LootCondition[]{_snowman});
         }
      }

      public boolean test(LootContext context) {
         return this.combinedCondition.test(context);
      }

      public JsonElement toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         return (JsonElement)(this.conditions.length == 0 ? JsonNull.INSTANCE : predicateSerializer.conditionsToJson(this.conditions));
      }

      public static JsonElement toPredicatesJsonArray(EntityPredicate.Extended[] predicates, AdvancementEntityPredicateSerializer predicateSerializer) {
         if (predicates.length == 0) {
            return JsonNull.INSTANCE;
         } else {
            JsonArray _snowman = new JsonArray();

            for (EntityPredicate.Extended _snowmanx : predicates) {
               _snowman.add(_snowmanx.toJson(predicateSerializer));
            }

            return _snowman;
         }
      }
   }
}
