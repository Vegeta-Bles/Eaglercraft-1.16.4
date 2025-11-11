import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;

public class dbr implements dbo {
   private final bg a;
   private final cyv.c b;

   private dbr(bg var1, cyv.c var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public dbp b() {
      return dbq.e;
   }

   @Override
   public Set<daz<?>> a() {
      return ImmutableSet.of(dbc.f, this.b.a());
   }

   public boolean a(cyv var1) {
      aqa _snowman = _snowman.c(this.b.a());
      dcn _snowmanx = _snowman.c(dbc.f);
      return this.a.a(_snowman.c(), _snowmanx, _snowman);
   }

   public static dbo.a a(cyv.c var0) {
      return a(_snowman, bg.a.a());
   }

   public static dbo.a a(cyv.c var0, bg.a var1) {
      return () -> new dbr(_snowman.b(), _snowman);
   }

   public static dbo.a a(cyv.c var0, bg var1) {
      return () -> new dbr(_snowman, _snowman);
   }

   public static class a implements cze<dbr> {
      public a() {
      }

      public void a(JsonObject var1, dbr var2, JsonSerializationContext var3) {
         _snowman.add("predicate", _snowman.a.a());
         _snowman.add("entity", _snowman.serialize(_snowman.b));
      }

      public dbr b(JsonObject var1, JsonDeserializationContext var2) {
         bg _snowman = bg.a(_snowman.get("predicate"));
         return new dbr(_snowman, afd.a(_snowman, "entity", _snowman, cyv.c.class));
      }
   }
}
