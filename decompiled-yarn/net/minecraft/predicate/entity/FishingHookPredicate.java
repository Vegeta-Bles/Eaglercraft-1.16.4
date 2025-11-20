package net.minecraft.predicate.entity;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.util.JsonHelper;

public class FishingHookPredicate {
   public static final FishingHookPredicate ANY = new FishingHookPredicate(false);
   private boolean inOpenWater;

   private FishingHookPredicate(boolean inOpenWater) {
      this.inOpenWater = inOpenWater;
   }

   public static FishingHookPredicate of(boolean inOpenWater) {
      return new FishingHookPredicate(inOpenWater);
   }

   public static FishingHookPredicate fromJson(@Nullable JsonElement json) {
      if (json != null && !json.isJsonNull()) {
         JsonObject _snowman = JsonHelper.asObject(json, "fishing_hook");
         JsonElement _snowmanx = _snowman.get("in_open_water");
         return _snowmanx != null ? new FishingHookPredicate(JsonHelper.asBoolean(_snowmanx, "in_open_water")) : ANY;
      } else {
         return ANY;
      }
   }

   public JsonElement toJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         _snowman.add("in_open_water", new JsonPrimitive(this.inOpenWater));
         return _snowman;
      }
   }

   public boolean test(Entity entity) {
      if (this == ANY) {
         return true;
      } else if (!(entity instanceof FishingBobberEntity)) {
         return false;
      } else {
         FishingBobberEntity _snowman = (FishingBobberEntity)entity;
         return this.inOpenWater == _snowman.isInOpenWater();
      }
   }
}
