import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import javax.annotation.Nullable;

public class dbx implements dbo {
   @Nullable
   private final Boolean a;
   @Nullable
   private final Boolean b;

   private dbx(@Nullable Boolean var1, @Nullable Boolean var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public dbp b() {
      return dbq.n;
   }

   public boolean a(cyv var1) {
      aag _snowman = _snowman.c();
      return this.a != null && this.a != _snowman.X() ? false : this.b == null || this.b == _snowman.W();
   }

   public static class b implements cze<dbx> {
      public b() {
      }

      public void a(JsonObject var1, dbx var2, JsonSerializationContext var3) {
         _snowman.addProperty("raining", _snowman.a);
         _snowman.addProperty("thundering", _snowman.b);
      }

      public dbx b(JsonObject var1, JsonDeserializationContext var2) {
         Boolean _snowman = _snowman.has("raining") ? afd.j(_snowman, "raining") : null;
         Boolean _snowmanx = _snowman.has("thundering") ? afd.j(_snowman, "thundering") : null;
         return new dbx(_snowman, _snowmanx);
      }
   }
}
