import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;

public class dbu implements dbo {
   private final float a;
   private final float b;

   private dbu(float var1, float var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public dbp b() {
      return dbq.d;
   }

   @Override
   public Set<daz<?>> a() {
      return ImmutableSet.of(dbc.d);
   }

   public boolean a(cyv var1) {
      aqa _snowman = _snowman.c(dbc.d);
      int _snowmanx = 0;
      if (_snowman instanceof aqm) {
         _snowmanx = bpu.g((aqm)_snowman);
      }

      return _snowman.a().nextFloat() < this.a + (float)_snowmanx * this.b;
   }

   public static dbo.a a(float var0, float var1) {
      return () -> new dbu(_snowman, _snowman);
   }

   public static class a implements cze<dbu> {
      public a() {
      }

      public void a(JsonObject var1, dbu var2, JsonSerializationContext var3) {
         _snowman.addProperty("chance", _snowman.a);
         _snowman.addProperty("looting_multiplier", _snowman.b);
      }

      public dbu b(JsonObject var1, JsonDeserializationContext var2) {
         return new dbu(afd.l(_snowman, "chance"), afd.l(_snowman, "looting_multiplier"));
      }
   }
}
