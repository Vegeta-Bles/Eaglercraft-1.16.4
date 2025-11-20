package net.minecraft.loot.condition;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.JsonSerializer;
import net.minecraft.util.math.Vec3d;

public class EntityPropertiesLootCondition implements LootCondition {
   private final EntityPredicate predicate;
   private final LootContext.EntityTarget entity;

   private EntityPropertiesLootCondition(EntityPredicate predicate, LootContext.EntityTarget entity) {
      this.predicate = predicate;
      this.entity = entity;
   }

   @Override
   public LootConditionType getType() {
      return LootConditionTypes.ENTITY_PROPERTIES;
   }

   @Override
   public Set<LootContextParameter<?>> getRequiredParameters() {
      return ImmutableSet.of(LootContextParameters.ORIGIN, this.entity.getParameter());
   }

   public boolean test(LootContext _snowman) {
      Entity _snowmanx = _snowman.get(this.entity.getParameter());
      Vec3d _snowmanxx = _snowman.get(LootContextParameters.ORIGIN);
      return this.predicate.test(_snowman.getWorld(), _snowmanxx, _snowmanx);
   }

   public static LootCondition.Builder create(LootContext.EntityTarget entity) {
      return builder(entity, EntityPredicate.Builder.create());
   }

   public static LootCondition.Builder builder(LootContext.EntityTarget entity, EntityPredicate.Builder predicateBuilder) {
      return () -> new EntityPropertiesLootCondition(predicateBuilder.build(), entity);
   }

   public static LootCondition.Builder builder(LootContext.EntityTarget entity, EntityPredicate predicate) {
      return () -> new EntityPropertiesLootCondition(predicate, entity);
   }

   public static class Serializer implements JsonSerializer<EntityPropertiesLootCondition> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, EntityPropertiesLootCondition _snowman, JsonSerializationContext _snowman) {
         _snowman.add("predicate", _snowman.predicate.toJson());
         _snowman.add("entity", _snowman.serialize(_snowman.entity));
      }

      public EntityPropertiesLootCondition fromJson(JsonObject _snowman, JsonDeserializationContext _snowman) {
         EntityPredicate _snowmanxx = EntityPredicate.fromJson(_snowman.get("predicate"));
         return new EntityPropertiesLootCondition(_snowmanxx, JsonHelper.deserialize(_snowman, "entity", _snowman, LootContext.EntityTarget.class));
      }
   }
}
