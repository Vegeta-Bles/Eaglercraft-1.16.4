import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;

public class ap extends ck<ap.a> {
   private static final vk a = new vk("brewed_potion");

   public ap() {
   }

   @Override
   public vk a() {
      return a;
   }

   public ap.a a(JsonObject var1, bg.b var2, ax var3) {
      bnt _snowman = null;
      if (_snowman.has("potion")) {
         vk _snowmanx = new vk(afd.h(_snowman, "potion"));
         _snowman = gm.U.b(_snowmanx).orElseThrow(() -> new JsonSyntaxException("Unknown potion '" + _snowman + "'"));
      }

      return new ap.a(_snowman, _snowman);
   }

   public void a(aah var1, bnt var2) {
      this.a(_snowman, var1x -> var1x.a(_snowman));
   }

   public static class a extends al {
      private final bnt a;

      public a(bg.b var1, @Nullable bnt var2) {
         super(ap.a, _snowman);
         this.a = _snowman;
      }

      public static ap.a c() {
         return new ap.a(bg.b.a, null);
      }

      public boolean a(bnt var1) {
         return this.a == null || this.a == _snowman;
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         if (this.a != null) {
            _snowman.addProperty("potion", gm.U.b(this.a).toString());
         }

         return _snowman;
      }
   }
}
