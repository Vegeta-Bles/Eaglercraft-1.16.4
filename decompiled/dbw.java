import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import javax.annotation.Nullable;

public class dbw implements dbo {
   @Nullable
   private final Long a;
   private final czd b;

   private dbw(@Nullable Long var1, czd var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public dbp b() {
      return dbq.p;
   }

   public boolean a(cyv var1) {
      aag _snowman = _snowman.c();
      long _snowmanx = _snowman.U();
      if (this.a != null) {
         _snowmanx %= this.a;
      }

      return this.b.a((int)_snowmanx);
   }

   public static class b implements cze<dbw> {
      public b() {
      }

      public void a(JsonObject var1, dbw var2, JsonSerializationContext var3) {
         _snowman.addProperty("period", _snowman.a);
         _snowman.add("value", _snowman.serialize(_snowman.b));
      }

      public dbw b(JsonObject var1, JsonDeserializationContext var2) {
         Long _snowman = _snowman.has("period") ? afd.m(_snowman, "period") : null;
         czd _snowmanx = afd.a(_snowman, "value", _snowman, czd.class);
         return new dbw(_snowman, _snowmanx);
      }
   }
}
