import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;

public class dam extends dai {
   private final czd a;
   private final int b;

   private dam(dbo[] var1, czd var2, int var3) {
      super(_snowman);
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public dak b() {
      return dal.g;
   }

   @Override
   public Set<daz<?>> a() {
      return ImmutableSet.of(dbc.d);
   }

   private boolean c() {
      return this.b > 0;
   }

   @Override
   public bmb a(bmb var1, cyv var2) {
      aqa _snowman = _snowman.c(dbc.d);
      if (_snowman instanceof aqm) {
         int _snowmanx = bpu.g((aqm)_snowman);
         if (_snowmanx == 0) {
            return _snowman;
         }

         float _snowmanxx = (float)_snowmanx * this.a.b(_snowman.a());
         _snowman.f(Math.round(_snowmanxx));
         if (this.c() && _snowman.E() > this.b) {
            _snowman.e(this.b);
         }
      }

      return _snowman;
   }

   public static dam.a a(czd var0) {
      return new dam.a(_snowman);
   }

   public static class a extends dai.a<dam.a> {
      private final czd a;
      private int b = 0;

      public a(czd var1) {
         this.a = _snowman;
      }

      protected dam.a a() {
         return this;
      }

      public dam.a a(int var1) {
         this.b = _snowman;
         return this;
      }

      @Override
      public daj b() {
         return new dam(this.g(), this.a, this.b);
      }
   }

   public static class b extends dai.c<dam> {
      public b() {
      }

      public void a(JsonObject var1, dam var2, JsonSerializationContext var3) {
         super.a(_snowman, _snowman, _snowman);
         _snowman.add("count", _snowman.serialize(_snowman.a));
         if (_snowman.c()) {
            _snowman.add("limit", _snowman.serialize(_snowman.b));
         }
      }

      public dam a(JsonObject var1, JsonDeserializationContext var2, dbo[] var3) {
         int _snowman = afd.a(_snowman, "limit", 0);
         return new dam(_snowman, afd.a(_snowman, "count", _snowman, czd.class), _snowman);
      }
   }
}
