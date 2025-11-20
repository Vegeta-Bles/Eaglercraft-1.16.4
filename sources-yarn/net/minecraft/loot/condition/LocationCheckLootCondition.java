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

   public boolean test(LootContext arg) {
      Vec3d lv = arg.get(LootContextParameters.ORIGIN);
      return lv != null
         && this.predicate
            .test(arg.getWorld(), lv.getX() + (double)this.offset.getX(), lv.getY() + (double)this.offset.getY(), lv.getZ() + (double)this.offset.getZ());
   }

   public static LootCondition.Builder builder(LocationPredicate.Builder predicateBuilder) {
      return () -> new LocationCheckLootCondition(predicateBuilder.build(), BlockPos.ORIGIN);
   }

   public static LootCondition.Builder method_30151(LocationPredicate.Builder arg, BlockPos arg2) {
      return () -> new LocationCheckLootCondition(arg.build(), arg2);
   }

   public static class Serializer implements JsonSerializer<LocationCheckLootCondition> {
      public Serializer() {
      }

      public void toJson(JsonObject jsonObject, LocationCheckLootCondition arg, JsonSerializationContext jsonSerializationContext) {
         jsonObject.add("predicate", arg.predicate.toJson());
         if (arg.offset.getX() != 0) {
            jsonObject.addProperty("offsetX", arg.offset.getX());
         }

         if (arg.offset.getY() != 0) {
            jsonObject.addProperty("offsetY", arg.offset.getY());
         }

         if (arg.offset.getZ() != 0) {
            jsonObject.addProperty("offsetZ", arg.offset.getZ());
         }
      }

      public LocationCheckLootCondition fromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
         LocationPredicate lv = LocationPredicate.fromJson(jsonObject.get("predicate"));
         int i = JsonHelper.getInt(jsonObject, "offsetX", 0);
         int j = JsonHelper.getInt(jsonObject, "offsetY", 0);
         int k = JsonHelper.getInt(jsonObject, "offsetZ", 0);
         return new LocationCheckLootCondition(lv, new BlockPos(i, j, k));
      }
   }
}
