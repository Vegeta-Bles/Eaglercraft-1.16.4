import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.Set;

public class dbf implements dbo {
   private final bps a;
   private final float[] b;

   private dbf(bps var1, float[] var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public dbp b() {
      return dbq.j;
   }

   @Override
   public Set<daz<?>> a() {
      return ImmutableSet.of(dbc.i);
   }

   public boolean a(cyv var1) {
      bmb _snowman = _snowman.c(dbc.i);
      int _snowmanx = _snowman != null ? bpu.a(this.a, _snowman) : 0;
      float _snowmanxx = this.b[Math.min(_snowmanx, this.b.length - 1)];
      return _snowman.a().nextFloat() < _snowmanxx;
   }

   public static dbo.a a(bps var0, float... var1) {
      return () -> new dbf(_snowman, _snowman);
   }

   public static class a implements cze<dbf> {
      public a() {
      }

      public void a(JsonObject var1, dbf var2, JsonSerializationContext var3) {
         _snowman.addProperty("enchantment", gm.R.b(_snowman.a).toString());
         _snowman.add("chances", _snowman.serialize(_snowman.b));
      }

      public dbf b(JsonObject var1, JsonDeserializationContext var2) {
         vk _snowman = new vk(afd.h(_snowman, "enchantment"));
         bps _snowmanx = gm.R.b(_snowman).orElseThrow(() -> new JsonParseException("Invalid enchantment id: " + _snowman));
         float[] _snowmanxx = afd.a(_snowman, "chances", _snowman, float[].class);
         return new dbf(_snowmanx, _snowmanxx);
      }
   }
}
