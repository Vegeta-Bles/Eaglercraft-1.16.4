import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;

public class dbi implements dbo {
   private final aw a;

   private dbi(aw var1) {
      this.a = _snowman;
   }

   @Override
   public dbp b() {
      return dbq.l;
   }

   @Override
   public Set<daz<?>> a() {
      return ImmutableSet.of(dbc.f, dbc.c);
   }

   public boolean a(cyv var1) {
      apk _snowman = _snowman.c(dbc.c);
      dcn _snowmanx = _snowman.c(dbc.f);
      return _snowmanx != null && _snowman != null && this.a.a(_snowman.c(), _snowmanx, _snowman);
   }

   public static dbo.a a(aw.a var0) {
      return () -> new dbi(_snowman.b());
   }

   public static class a implements cze<dbi> {
      public a() {
      }

      public void a(JsonObject var1, dbi var2, JsonSerializationContext var3) {
         _snowman.add("predicate", _snowman.a.a());
      }

      public dbi b(JsonObject var1, JsonDeserializationContext var2) {
         aw _snowman = aw.a(_snowman.get("predicate"));
         return new dbi(_snowman);
      }
   }
}
