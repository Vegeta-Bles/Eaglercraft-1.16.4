package net.minecraft.loot.condition;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.util.JsonSerializer;
import net.minecraft.util.math.Vec3d;

public class DamageSourcePropertiesLootCondition implements LootCondition {
   private final DamageSourcePredicate predicate;

   private DamageSourcePropertiesLootCondition(DamageSourcePredicate predicate) {
      this.predicate = predicate;
   }

   @Override
   public LootConditionType getType() {
      return LootConditionTypes.DAMAGE_SOURCE_PROPERTIES;
   }

   @Override
   public Set<LootContextParameter<?>> getRequiredParameters() {
      return ImmutableSet.of(LootContextParameters.ORIGIN, LootContextParameters.DAMAGE_SOURCE);
   }

   public boolean test(LootContext _snowman) {
      DamageSource _snowmanx = _snowman.get(LootContextParameters.DAMAGE_SOURCE);
      Vec3d _snowmanxx = _snowman.get(LootContextParameters.ORIGIN);
      return _snowmanxx != null && _snowmanx != null && this.predicate.test(_snowman.getWorld(), _snowmanxx, _snowmanx);
   }

   public static LootCondition.Builder builder(DamageSourcePredicate.Builder builder) {
      return () -> new DamageSourcePropertiesLootCondition(builder.build());
   }

   public static class Serializer implements JsonSerializer<DamageSourcePropertiesLootCondition> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, DamageSourcePropertiesLootCondition _snowman, JsonSerializationContext _snowman) {
         _snowman.add("predicate", _snowman.predicate.toJson());
      }

      public DamageSourcePropertiesLootCondition fromJson(JsonObject _snowman, JsonDeserializationContext _snowman) {
         DamageSourcePredicate _snowmanxx = DamageSourcePredicate.fromJson(_snowman.get("predicate"));
         return new DamageSourcePropertiesLootCondition(_snowmanxx);
      }
   }
}
