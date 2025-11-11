import com.google.gson.JsonObject;

public class au extends ck<au.a> {
   private static final vk a = new vk("cured_zombie_villager");

   public au() {
   }

   @Override
   public vk a() {
      return a;
   }

   public au.a a(JsonObject var1, bg.b var2, ax var3) {
      bg.b _snowman = bg.b.a(_snowman, "zombie", _snowman);
      bg.b _snowmanx = bg.b.a(_snowman, "villager", _snowman);
      return new au.a(_snowman, _snowman, _snowmanx);
   }

   public void a(aah var1, bej var2, bfj var3) {
      cyv _snowman = bg.b(_snowman, _snowman);
      cyv _snowmanx = bg.b(_snowman, _snowman);
      this.a(_snowman, var2x -> var2x.a(_snowman, _snowman));
   }

   public static class a extends al {
      private final bg.b a;
      private final bg.b b;

      public a(bg.b var1, bg.b var2, bg.b var3) {
         super(au.a, _snowman);
         this.a = _snowman;
         this.b = _snowman;
      }

      public static au.a c() {
         return new au.a(bg.b.a, bg.b.a, bg.b.a);
      }

      public boolean a(cyv var1, cyv var2) {
         return !this.a.a(_snowman) ? false : this.b.a(_snowman);
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         _snowman.add("zombie", this.a.a(_snowman));
         _snowman.add("villager", this.b.a(_snowman));
         return _snowman;
      }
   }
}
