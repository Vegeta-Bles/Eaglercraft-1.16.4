package net.minecraft.predicate.entity;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.predicate.NumberRange;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.MathHelper;

public class DistancePredicate {
   public static final DistancePredicate ANY = new DistancePredicate(
      NumberRange.FloatRange.ANY, NumberRange.FloatRange.ANY, NumberRange.FloatRange.ANY, NumberRange.FloatRange.ANY, NumberRange.FloatRange.ANY
   );
   private final NumberRange.FloatRange x;
   private final NumberRange.FloatRange y;
   private final NumberRange.FloatRange z;
   private final NumberRange.FloatRange horizontal;
   private final NumberRange.FloatRange absolute;

   public DistancePredicate(
      NumberRange.FloatRange x, NumberRange.FloatRange y, NumberRange.FloatRange z, NumberRange.FloatRange horizontal, NumberRange.FloatRange absolute
   ) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.horizontal = horizontal;
      this.absolute = absolute;
   }

   public static DistancePredicate horizontal(NumberRange.FloatRange horizontal) {
      return new DistancePredicate(NumberRange.FloatRange.ANY, NumberRange.FloatRange.ANY, NumberRange.FloatRange.ANY, horizontal, NumberRange.FloatRange.ANY);
   }

   public static DistancePredicate y(NumberRange.FloatRange y) {
      return new DistancePredicate(NumberRange.FloatRange.ANY, y, NumberRange.FloatRange.ANY, NumberRange.FloatRange.ANY, NumberRange.FloatRange.ANY);
   }

   public boolean test(double x0, double y0, double z0, double x1, double y1, double z1) {
      float _snowman = (float)(x0 - x1);
      float _snowmanx = (float)(y0 - y1);
      float _snowmanxx = (float)(z0 - z1);
      if (!this.x.test(MathHelper.abs(_snowman)) || !this.y.test(MathHelper.abs(_snowmanx)) || !this.z.test(MathHelper.abs(_snowmanxx))) {
         return false;
      } else {
         return !this.horizontal.testSqrt((double)(_snowman * _snowman + _snowmanxx * _snowmanxx)) ? false : this.absolute.testSqrt((double)(_snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx));
      }
   }

   public static DistancePredicate fromJson(@Nullable JsonElement json) {
      if (json != null && !json.isJsonNull()) {
         JsonObject _snowman = JsonHelper.asObject(json, "distance");
         NumberRange.FloatRange _snowmanx = NumberRange.FloatRange.fromJson(_snowman.get("x"));
         NumberRange.FloatRange _snowmanxx = NumberRange.FloatRange.fromJson(_snowman.get("y"));
         NumberRange.FloatRange _snowmanxxx = NumberRange.FloatRange.fromJson(_snowman.get("z"));
         NumberRange.FloatRange _snowmanxxxx = NumberRange.FloatRange.fromJson(_snowman.get("horizontal"));
         NumberRange.FloatRange _snowmanxxxxx = NumberRange.FloatRange.fromJson(_snowman.get("absolute"));
         return new DistancePredicate(_snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
      } else {
         return ANY;
      }
   }

   public JsonElement toJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         _snowman.add("x", this.x.toJson());
         _snowman.add("y", this.y.toJson());
         _snowman.add("z", this.z.toJson());
         _snowman.add("horizontal", this.horizontal.toJson());
         _snowman.add("absolute", this.absolute.toJson());
         return _snowman;
      }
   }
}
