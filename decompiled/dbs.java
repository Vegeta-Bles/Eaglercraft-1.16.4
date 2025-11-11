import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;

public class dbs implements dbo {
   private static final dbs a = new dbs();

   private dbs() {
   }

   @Override
   public dbp b() {
      return dbq.f;
   }

   @Override
   public Set<daz<?>> a() {
      return ImmutableSet.of(dbc.b);
   }

   public boolean a(cyv var1) {
      return _snowman.a(dbc.b);
   }

   public static dbo.a c() {
      return () -> a;
   }

   public static class a implements cze<dbs> {
      public a() {
      }

      public void a(JsonObject var1, dbs var2, JsonSerializationContext var3) {
      }

      public dbs b(JsonObject var1, JsonDeserializationContext var2) {
         return dbs.a;
      }
   }
}
