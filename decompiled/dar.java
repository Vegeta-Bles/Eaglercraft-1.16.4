import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dar extends dai {
   private static final Logger a = LogManager.getLogger();
   private final czd b;

   private dar(dbo[] var1, czd var2) {
      super(_snowman);
      this.b = _snowman;
   }

   @Override
   public dak b() {
      return dal.h;
   }

   @Override
   public bmb a(bmb var1, cyv var2) {
      if (_snowman.e()) {
         float _snowman = 1.0F - this.b.b(_snowman.a());
         _snowman.b(afm.d(_snowman * (float)_snowman.h()));
      } else {
         a.warn("Couldn't set damage of loot item {}", _snowman);
      }

      return _snowman;
   }

   public static dai.a<?> a(czd var0) {
      return a(var1 -> new dar(var1, _snowman));
   }

   public static class a extends dai.c<dar> {
      public a() {
      }

      public void a(JsonObject var1, dar var2, JsonSerializationContext var3) {
         super.a(_snowman, _snowman, _snowman);
         _snowman.add("damage", _snowman.serialize(_snowman.b));
      }

      public dar a(JsonObject var1, JsonDeserializationContext var2, dbo[] var3) {
         return new dar(_snowman, afd.a(_snowman, "damage", _snowman, czd.class));
      }
   }
}
