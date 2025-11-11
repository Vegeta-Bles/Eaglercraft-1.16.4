import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;

public class czk extends czs {
   private final vk g;

   private czk(vk var1, int var2, int var3, dbo[] var4, daj[] var5) {
      super(_snowman, _snowman, _snowman, _snowman);
      this.g = _snowman;
   }

   @Override
   public czr a() {
      return czo.d;
   }

   @Override
   public void a(Consumer<bmb> var1, cyv var2) {
      _snowman.a(this.g, _snowman);
   }

   public static czs.a<?> a(vk var0) {
      return a((var1, var2, var3, var4) -> new czk(_snowman, var1, var2, var3, var4));
   }

   public static class a extends czs.e<czk> {
      public a() {
      }

      public void a(JsonObject var1, czk var2, JsonSerializationContext var3) {
         super.a(_snowman, _snowman, _snowman);
         _snowman.addProperty("name", _snowman.g.toString());
      }

      protected czk a(JsonObject var1, JsonDeserializationContext var2, int var3, int var4, dbo[] var5, daj[] var6) {
         vk _snowman = new vk(afd.h(_snowman, "name"));
         return new czk(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }
}
