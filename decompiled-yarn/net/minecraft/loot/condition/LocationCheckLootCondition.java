package net.minecraft.loot.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.JsonSerializer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class LocationCheckLootCondition implements LootCondition {
   private final LocationPredicate predicate;
   private final BlockPos offset;

   private LocationCheckLootCondition(LocationPredicate predicate, BlockPos offset) {
      this.predicate = predicate;
      this.offset = offset;
   }

   @Override
   public LootConditionType getType() {
      return LootConditionTypes.LOCATION_CHECK;
   }

   public boolean test(LootContext _snowman) {
      Vec3d _snowmanx = _snowman.get(LootContextParameters.ORIGIN);
      return _snowmanx != null
         && this.predicate
            .test(_snowman.getWorld(), _snowmanx.getX() + (double)this.offset.getX(), _snowmanx.getY() + (double)this.offset.getY(), _snowmanx.getZ() + (double)this.offset.getZ());
   }

   public static LootCondition.Builder builder(LocationPredicate.Builder predicateBuilder) {
      return () -> new LocationCheckLootCondition(predicateBuilder.build(), BlockPos.ORIGIN);
   }

   public static LootCondition.Builder method_30151(LocationPredicate.Builder _snowman, BlockPos _snowman) {
      return () -> new LocationCheckLootCondition(_snowman.build(), _snowman);
   }

   public static class Serializer implements JsonSerializer<LocationCheckLootCondition> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, LocationCheckLootCondition _snowman, JsonSerializationContext _snowman) {
         _snowman.add("predicate", _snowman.predicate.toJson());
         if (_snowman.offset.getX() != 0) {
            _snowman.addProperty("offsetX", _snowman.offset.getX());
         }

         if (_snowman.offset.getY() != 0) {
            _snowman.addProperty("offsetY", _snowman.offset.getY());
         }

         if (_snowman.offset.getZ() != 0) {
            _snowman.addProperty("offsetZ", _snowman.offset.getZ());
         }
      }

      public LocationCheckLootCondition fromJson(JsonObject _snowman, JsonDeserializationContext _snowman) {
         LocationPredicate _snowmanxx = LocationPredicate.fromJson(_snowman.get("predicate"));
         int _snowmanxxx = JsonHelper.getInt(_snowman, "offsetX", 0);
         int _snowmanxxxx = JsonHelper.getInt(_snowman, "offsetY", 0);
         int _snowmanxxxxx = JsonHelper.getInt(_snowman, "offsetZ", 0);
         return new LocationCheckLootCondition(_snowmanxx, new BlockPos(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx));
      }
   }
}
