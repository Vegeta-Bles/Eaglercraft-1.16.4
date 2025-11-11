import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

public class dbm implements dbo {
   private final bw a;
   private final fx b;

   private dbm(bw var1, fx var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public dbp b() {
      return dbq.m;
   }

   public boolean a(cyv var1) {
      dcn _snowman = _snowman.c(dbc.f);
      return _snowman != null && this.a.a(_snowman.c(), _snowman.a() + (double)this.b.u(), _snowman.b() + (double)this.b.v(), _snowman.c() + (double)this.b.w());
   }

   public static dbo.a a(bw.a var0) {
      return () -> new dbm(_snowman.b(), fx.b);
   }

   public static dbo.a a(bw.a var0, fx var1) {
      return () -> new dbm(_snowman.b(), _snowman);
   }

   public static class a implements cze<dbm> {
      public a() {
      }

      public void a(JsonObject var1, dbm var2, JsonSerializationContext var3) {
         _snowman.add("predicate", _snowman.a.a());
         if (_snowman.b.u() != 0) {
            _snowman.addProperty("offsetX", _snowman.b.u());
         }

         if (_snowman.b.v() != 0) {
            _snowman.addProperty("offsetY", _snowman.b.v());
         }

         if (_snowman.b.w() != 0) {
            _snowman.addProperty("offsetZ", _snowman.b.w());
         }
      }

      public dbm b(JsonObject var1, JsonDeserializationContext var2) {
         bw _snowman = bw.a(_snowman.get("predicate"));
         int _snowmanx = afd.a(_snowman, "offsetX", 0);
         int _snowmanxx = afd.a(_snowman, "offsetY", 0);
         int _snowmanxxx = afd.a(_snowman, "offsetZ", 0);
         return new dbm(_snowman, new fx(_snowmanx, _snowmanxx, _snowmanxxx));
      }
   }
}
