import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;

public class am extends ck<am.a> {
   private static final vk a = new vk("bee_nest_destroyed");

   public am() {
   }

   @Override
   public vk a() {
      return a;
   }

   public am.a a(JsonObject var1, bg.b var2, ax var3) {
      buo _snowman = a(_snowman);
      bq _snowmanx = bq.a(_snowman.get("item"));
      bz.d _snowmanxx = bz.d.a(_snowman.get("num_bees_inside"));
      return new am.a(_snowman, _snowman, _snowmanx, _snowmanxx);
   }

   @Nullable
   private static buo a(JsonObject var0) {
      if (_snowman.has("block")) {
         vk _snowman = new vk(afd.h(_snowman, "block"));
         return gm.Q.b(_snowman).orElseThrow(() -> new JsonSyntaxException("Unknown block type '" + _snowman + "'"));
      } else {
         return null;
      }
   }

   public void a(aah var1, buo var2, bmb var3, int var4) {
      this.a(_snowman, var3x -> var3x.a(_snowman, _snowman, _snowman));
   }

   public static class a extends al {
      @Nullable
      private final buo a;
      private final bq b;
      private final bz.d c;

      public a(bg.b var1, @Nullable buo var2, bq var3, bz.d var4) {
         super(am.a, _snowman);
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      public static am.a a(buo var0, bq.a var1, bz.d var2) {
         return new am.a(bg.b.a, _snowman, _snowman.b(), _snowman);
      }

      public boolean a(buo var1, bmb var2, int var3) {
         if (this.a != null && _snowman != this.a) {
            return false;
         } else {
            return !this.b.a(_snowman) ? false : this.c.d(_snowman);
         }
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         if (this.a != null) {
            _snowman.addProperty("block", gm.Q.b(this.a).toString());
         }

         _snowman.add("item", this.b.a());
         _snowman.add("num_bees_inside", this.c.d());
         return _snowman;
      }
   }
}
