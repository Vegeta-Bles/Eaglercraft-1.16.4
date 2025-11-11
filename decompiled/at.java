import com.google.gson.JsonObject;

public class at extends ck<at.a> {
   private static final vk a = new vk("consume_item");

   public at() {
   }

   @Override
   public vk a() {
      return a;
   }

   public at.a a(JsonObject var1, bg.b var2, ax var3) {
      return new at.a(_snowman, bq.a(_snowman.get("item")));
   }

   public void a(aah var1, bmb var2) {
      this.a(_snowman, var1x -> var1x.a(_snowman));
   }

   public static class a extends al {
      private final bq a;

      public a(bg.b var1, bq var2) {
         super(at.a, _snowman);
         this.a = _snowman;
      }

      public static at.a c() {
         return new at.a(bg.b.a, bq.a);
      }

      public static at.a a(brw var0) {
         return new at.a(bg.b.a, new bq(null, _snowman.h(), bz.d.e, bz.d.e, bb.b, bb.b, null, cb.a));
      }

      public boolean a(bmb var1) {
         return this.a.a(_snowman);
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         _snowman.add("item", this.a.a());
         return _snowman;
      }
   }
}
