import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

public class dbt implements dbo {
   private final float a;

   private dbt(float var1) {
      this.a = _snowman;
   }

   @Override
   public dbp b() {
      return dbq.c;
   }

   public boolean a(cyv var1) {
      return _snowman.a().nextFloat() < this.a;
   }

   public static dbo.a a(float var0) {
      return () -> new dbt(_snowman);
   }

   public static class a implements cze<dbt> {
      public a() {
      }

      public void a(JsonObject var1, dbt var2, JsonSerializationContext var3) {
         _snowman.addProperty("chance", _snowman.a);
      }

      public dbt b(JsonObject var1, JsonDeserializationContext var2) {
         return new dbt(afd.l(_snowman, "chance"));
      }
   }
}
