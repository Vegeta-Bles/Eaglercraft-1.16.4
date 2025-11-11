import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

public class dah extends dai {
   private final cyu a;

   private dah(dbo[] var1, cyu var2) {
      super(_snowman);
      this.a = _snowman;
   }

   @Override
   public dak b() {
      return dal.o;
   }

   @Override
   public bmb a(bmb var1, cyv var2) {
      int _snowman = this.a.applyAsInt(_snowman.E());
      _snowman.e(_snowman);
      return _snowman;
   }

   public static dai.a<?> a(cyu var0) {
      return a(var1 -> new dah(var1, _snowman));
   }

   public static class a extends dai.c<dah> {
      public a() {
      }

      public void a(JsonObject var1, dah var2, JsonSerializationContext var3) {
         super.a(_snowman, _snowman, _snowman);
         _snowman.add("limit", _snowman.serialize(_snowman.a));
      }

      public dah a(JsonObject var1, JsonDeserializationContext var2, dbo[] var3) {
         cyu _snowman = afd.a(_snowman, "limit", _snowman, cyu.class);
         return new dah(_snowman, _snowman);
      }
   }
}
