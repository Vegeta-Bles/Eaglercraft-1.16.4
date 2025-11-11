import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;

public class dbl implements dbo {
   private final dbo a;

   private dbl(dbo var1) {
      this.a = _snowman;
   }

   @Override
   public dbp b() {
      return dbq.a;
   }

   public final boolean a(cyv var1) {
      return !this.a.test(_snowman);
   }

   @Override
   public Set<daz<?>> a() {
      return this.a.a();
   }

   @Override
   public void a(czg var1) {
      dbo.super.a(_snowman);
      this.a.a(_snowman);
   }

   public static dbo.a a(dbo.a var0) {
      dbl _snowman = new dbl(_snowman.build());
      return () -> _snowman;
   }

   public static class a implements cze<dbl> {
      public a() {
      }

      public void a(JsonObject var1, dbl var2, JsonSerializationContext var3) {
         _snowman.add("term", _snowman.serialize(_snowman.a));
      }

      public dbl b(JsonObject var1, JsonDeserializationContext var2) {
         dbo _snowman = afd.a(_snowman, "term", _snowman, dbo.class);
         return new dbl(_snowman);
      }
   }
}
