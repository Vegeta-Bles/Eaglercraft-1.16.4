import com.google.gson.JsonObject;

public class ba extends ck<ba.a> {
   private static final vk a = new vk("enchanted_item");

   public ba() {
   }

   @Override
   public vk a() {
      return a;
   }

   public ba.a a(JsonObject var1, bg.b var2, ax var3) {
      bq _snowman = bq.a(_snowman.get("item"));
      bz.d _snowmanx = bz.d.a(_snowman.get("levels"));
      return new ba.a(_snowman, _snowman, _snowmanx);
   }

   public void a(aah var1, bmb var2, int var3) {
      this.a(_snowman, var2x -> var2x.a(_snowman, _snowman));
   }

   public static class a extends al {
      private final bq a;
      private final bz.d b;

      public a(bg.b var1, bq var2, bz.d var3) {
         super(ba.a, _snowman);
         this.a = _snowman;
         this.b = _snowman;
      }

      public static ba.a c() {
         return new ba.a(bg.b.a, bq.a, bz.d.e);
      }

      public boolean a(bmb var1, int var2) {
         return !this.a.a(_snowman) ? false : this.b.d(_snowman);
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         _snowman.add("item", this.a.a());
         _snowman.add("levels", this.b.d());
         return _snowman;
      }
   }
}
