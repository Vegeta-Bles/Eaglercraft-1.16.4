import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;

public class cl extends ck<cl.a> {
   private static final vk a = new vk("slide_down_block");

   public cl() {
   }

   @Override
   public vk a() {
      return a;
   }

   public cl.a a(JsonObject var1, bg.b var2, ax var3) {
      buo _snowman = a(_snowman);
      cm _snowmanx = cm.a(_snowman.get("state"));
      if (_snowman != null) {
         _snowmanx.a(_snowman.m(), var1x -> {
            throw new JsonSyntaxException("Block " + _snowman + " has no property " + var1x);
         });
      }

      return new cl.a(_snowman, _snowman, _snowmanx);
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

   public void a(aah var1, ceh var2) {
      this.a(_snowman, var1x -> var1x.a(_snowman));
   }

   public static class a extends al {
      private final buo a;
      private final cm b;

      public a(bg.b var1, @Nullable buo var2, cm var3) {
         super(cl.a, _snowman);
         this.a = _snowman;
         this.b = _snowman;
      }

      public static cl.a a(buo var0) {
         return new cl.a(bg.b.a, _snowman, cm.a);
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         if (this.a != null) {
            _snowman.addProperty("block", gm.Q.b(this.a).toString());
         }

         _snowman.add("state", this.b.a());
         return _snowman;
      }

      public boolean a(ceh var1) {
         return this.a != null && !_snowman.a(this.a) ? false : this.b.a(_snowman);
      }
   }
}
