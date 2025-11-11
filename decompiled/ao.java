import com.google.gson.JsonObject;
import javax.annotation.Nullable;

public class ao extends ck<ao.a> {
   private static final vk a = new vk("bred_animals");

   public ao() {
   }

   @Override
   public vk a() {
      return a;
   }

   public ao.a a(JsonObject var1, bg.b var2, ax var3) {
      bg.b _snowman = bg.b.a(_snowman, "parent", _snowman);
      bg.b _snowmanx = bg.b.a(_snowman, "partner", _snowman);
      bg.b _snowmanxx = bg.b.a(_snowman, "child", _snowman);
      return new ao.a(_snowman, _snowman, _snowmanx, _snowmanxx);
   }

   public void a(aah var1, azz var2, azz var3, @Nullable apy var4) {
      cyv _snowman = bg.b(_snowman, _snowman);
      cyv _snowmanx = bg.b(_snowman, _snowman);
      cyv _snowmanxx = _snowman != null ? bg.b(_snowman, _snowman) : null;
      this.a(_snowman, var3x -> var3x.a(_snowman, _snowman, _snowman));
   }

   public static class a extends al {
      private final bg.b a;
      private final bg.b b;
      private final bg.b c;

      public a(bg.b var1, bg.b var2, bg.b var3, bg.b var4) {
         super(ao.a, _snowman);
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      public static ao.a c() {
         return new ao.a(bg.b.a, bg.b.a, bg.b.a, bg.b.a);
      }

      public static ao.a a(bg.a var0) {
         return new ao.a(bg.b.a, bg.b.a, bg.b.a, bg.b.a(_snowman.b()));
      }

      public static ao.a a(bg var0, bg var1, bg var2) {
         return new ao.a(bg.b.a, bg.b.a(_snowman), bg.b.a(_snowman), bg.b.a(_snowman));
      }

      public boolean a(cyv var1, cyv var2, @Nullable cyv var3) {
         return this.c == bg.b.a || _snowman != null && this.c.a(_snowman) ? this.a.a(_snowman) && this.b.a(_snowman) || this.a.a(_snowman) && this.b.a(_snowman) : false;
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         _snowman.add("parent", this.a.a(_snowman));
         _snowman.add("partner", this.b.a(_snowman));
         _snowman.add("child", this.c.a(_snowman));
         return _snowman;
      }
   }
}
