import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import java.util.Set;

public class dbk implements dbo {
   private static final dbk a = new dbk();

   private dbk() {
   }

   @Override
   public dbp b() {
      return dbq.k;
   }

   @Override
   public Set<daz<?>> a() {
      return ImmutableSet.of(dbc.j);
   }

   public boolean a(cyv var1) {
      Float _snowman = _snowman.c(dbc.j);
      if (_snowman != null) {
         Random _snowmanx = _snowman.a();
         float _snowmanxx = 1.0F / _snowman;
         return _snowmanx.nextFloat() <= _snowmanxx;
      } else {
         return true;
      }
   }

   public static dbo.a c() {
      return () -> a;
   }

   public static class a implements cze<dbk> {
      public a() {
      }

      public void a(JsonObject var1, dbk var2, JsonSerializationContext var3) {
      }

      public dbk b(JsonObject var1, JsonDeserializationContext var2) {
         return dbk.a;
      }
   }
}
