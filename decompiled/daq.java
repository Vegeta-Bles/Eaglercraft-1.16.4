import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

public class daq extends dai {
   private final czb a;

   private daq(dbo[] var1, czb var2) {
      super(_snowman);
      this.a = _snowman;
   }

   @Override
   public dak b() {
      return dal.b;
   }

   @Override
   public bmb a(bmb var1, cyv var2) {
      _snowman.e(this.a.a(_snowman.a()));
      return _snowman;
   }

   public static dai.a<?> a(czb var0) {
      return a(var1 -> new daq(var1, _snowman));
   }

   public static class a extends dai.c<daq> {
      public a() {
      }

      public void a(JsonObject var1, daq var2, JsonSerializationContext var3) {
         super.a(_snowman, _snowman, _snowman);
         _snowman.add("count", czc.a(_snowman.a, _snowman));
      }

      public daq a(JsonObject var1, JsonDeserializationContext var2, dbo[] var3) {
         czb _snowman = czc.a(_snowman.get("count"), _snowman);
         return new daq(_snowman, _snowman);
      }
   }
}
