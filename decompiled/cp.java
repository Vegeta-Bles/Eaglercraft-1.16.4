import com.google.gson.JsonObject;

public class cp extends ck<cp.a> {
   private static final vk a = new vk("target_hit");

   public cp() {
   }

   @Override
   public vk a() {
      return a;
   }

   public cp.a a(JsonObject var1, bg.b var2, ax var3) {
      bz.d _snowman = bz.d.a(_snowman.get("signal_strength"));
      bg.b _snowmanx = bg.b.a(_snowman, "projectile", _snowman);
      return new cp.a(_snowman, _snowman, _snowmanx);
   }

   public void a(aah var1, aqa var2, dcn var3, int var4) {
      cyv _snowman = bg.b(_snowman, _snowman);
      this.a(_snowman, var3x -> var3x.a(_snowman, _snowman, _snowman));
   }

   public static class a extends al {
      private final bz.d a;
      private final bg.b b;

      public a(bg.b var1, bz.d var2, bg.b var3) {
         super(cp.a, _snowman);
         this.a = _snowman;
         this.b = _snowman;
      }

      public static cp.a a(bz.d var0, bg.b var1) {
         return new cp.a(bg.b.a, _snowman, _snowman);
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         _snowman.add("signal_strength", this.a.d());
         _snowman.add("projectile", this.b.a(_snowman));
         return _snowman;
      }

      public boolean a(cyv var1, dcn var2, int var3) {
         return !this.a.d(_snowman) ? false : this.b.a(_snowman);
      }
   }
}
