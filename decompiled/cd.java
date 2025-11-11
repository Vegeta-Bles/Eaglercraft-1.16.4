import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;

public class cd extends ck<cd.a> {
   private static final vk a = new vk("placed_block");

   public cd() {
   }

   @Override
   public vk a() {
      return a;
   }

   public cd.a a(JsonObject var1, bg.b var2, ax var3) {
      buo _snowman = a(_snowman);
      cm _snowmanx = cm.a(_snowman.get("state"));
      if (_snowman != null) {
         _snowmanx.a(_snowman.m(), var1x -> {
            throw new JsonSyntaxException("Block " + _snowman + " has no property " + var1x + ":");
         });
      }

      bw _snowmanxx = bw.a(_snowman.get("location"));
      bq _snowmanxxx = bq.a(_snowman.get("item"));
      return new cd.a(_snowman, _snowman, _snowmanx, _snowmanxx, _snowmanxxx);
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

   public void a(aah var1, fx var2, bmb var3) {
      ceh _snowman = _snowman.u().d_(_snowman);
      this.a(_snowman, var4x -> var4x.a(_snowman, _snowman, _snowman.u(), _snowman));
   }

   public static class a extends al {
      private final buo a;
      private final cm b;
      private final bw c;
      private final bq d;

      public a(bg.b var1, @Nullable buo var2, cm var3, bw var4, bq var5) {
         super(cd.a, _snowman);
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }

      public static cd.a a(buo var0) {
         return new cd.a(bg.b.a, _snowman, cm.a, bw.a, bq.a);
      }

      public boolean a(ceh var1, fx var2, aag var3, bmb var4) {
         if (this.a != null && !_snowman.a(this.a)) {
            return false;
         } else if (!this.b.a(_snowman)) {
            return false;
         } else {
            return !this.c.a(_snowman, (float)_snowman.u(), (float)_snowman.v(), (float)_snowman.w()) ? false : this.d.a(_snowman);
         }
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         if (this.a != null) {
            _snowman.addProperty("block", gm.Q.b(this.a).toString());
         }

         _snowman.add("state", this.b.a());
         _snowman.add("location", this.c.a());
         _snowman.add("item", this.d.a());
         return _snowman;
      }
   }
}
