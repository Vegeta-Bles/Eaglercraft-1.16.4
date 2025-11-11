import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;

public class dbv implements dbo {
   private final bq a;

   public dbv(bq var1) {
      this.a = _snowman;
   }

   @Override
   public dbp b() {
      return dbq.i;
   }

   @Override
   public Set<daz<?>> a() {
      return ImmutableSet.of(dbc.i);
   }

   public boolean a(cyv var1) {
      bmb _snowman = _snowman.c(dbc.i);
      return _snowman != null && this.a.a(_snowman);
   }

   public static dbo.a a(bq.a var0) {
      return () -> new dbv(_snowman.b());
   }

   public static class a implements cze<dbv> {
      public a() {
      }

      public void a(JsonObject var1, dbv var2, JsonSerializationContext var3) {
         _snowman.add("predicate", _snowman.a.a());
      }

      public dbv b(JsonObject var1, JsonDeserializationContext var2) {
         bq _snowman = bq.a(_snowman.get("predicate"));
         return new dbv(_snowman);
      }
   }
}
