import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;

public class czt extends czs {
   private final vk g;

   private czt(vk var1, int var2, int var3, dbo[] var4, daj[] var5) {
      super(_snowman, _snowman, _snowman, _snowman);
      this.g = _snowman;
   }

   @Override
   public czr a() {
      return czo.c;
   }

   @Override
   public void a(Consumer<bmb> var1, cyv var2) {
      cyy _snowman = _snowman.a(this.g);
      _snowman.a(_snowman, _snowman);
   }

   @Override
   public void a(czg var1) {
      if (_snowman.a(this.g)) {
         _snowman.a("Table " + this.g + " is recursively called");
      } else {
         super.a(_snowman);
         cyy _snowman = _snowman.c(this.g);
         if (_snowman == null) {
            _snowman.a("Unknown loot table called " + this.g);
         } else {
            _snowman.a(_snowman.a("->{" + this.g + "}", this.g));
         }
      }
   }

   public static czs.a<?> a(vk var0) {
      return a((var1, var2, var3, var4) -> new czt(_snowman, var1, var2, var3, var4));
   }

   public static class a extends czs.e<czt> {
      public a() {
      }

      public void a(JsonObject var1, czt var2, JsonSerializationContext var3) {
         super.a(_snowman, _snowman, _snowman);
         _snowman.addProperty("name", _snowman.g.toString());
      }

      protected czt a(JsonObject var1, JsonDeserializationContext var2, int var3, int var4, dbo[] var5, daj[] var6) {
         vk _snowman = new vk(afd.h(_snowman, "name"));
         return new czt(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }
}
