import com.google.gson.JsonObject;

public class cr extends ck<cr.a> {
   private static final vk a = new vk("villager_trade");

   public cr() {
   }

   @Override
   public vk a() {
      return a;
   }

   public cr.a a(JsonObject var1, bg.b var2, ax var3) {
      bg.b _snowman = bg.b.a(_snowman, "villager", _snowman);
      bq _snowmanx = bq.a(_snowman.get("item"));
      return new cr.a(_snowman, _snowman, _snowmanx);
   }

   public void a(aah var1, bfe var2, bmb var3) {
      cyv _snowman = bg.b(_snowman, _snowman);
      this.a(_snowman, var2x -> var2x.a(_snowman, _snowman));
   }

   public static class a extends al {
      private final bg.b a;
      private final bq b;

      public a(bg.b var1, bg.b var2, bq var3) {
         super(cr.a, _snowman);
         this.a = _snowman;
         this.b = _snowman;
      }

      public static cr.a c() {
         return new cr.a(bg.b.a, bg.b.a, bq.a);
      }

      public boolean a(cyv var1, bmb var2) {
         return !this.a.a(_snowman) ? false : this.b.a(_snowman);
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         _snowman.add("item", this.b.a());
         _snowman.add("villager", this.a.a(_snowman));
         return _snowman;
      }
   }
}
