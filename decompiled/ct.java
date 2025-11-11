import com.google.gson.JsonObject;

public class ct extends ck<ct.a> {
   private static final vk a = new vk("used_totem");

   public ct() {
   }

   @Override
   public vk a() {
      return a;
   }

   public ct.a a(JsonObject var1, bg.b var2, ax var3) {
      bq _snowman = bq.a(_snowman.get("item"));
      return new ct.a(_snowman, _snowman);
   }

   public void a(aah var1, bmb var2) {
      this.a(_snowman, var1x -> var1x.a(_snowman));
   }

   public static class a extends al {
      private final bq a;

      public a(bg.b var1, bq var2) {
         super(ct.a, _snowman);
         this.a = _snowman;
      }

      public static ct.a a(brw var0) {
         return new ct.a(bg.b.a, bq.a.a().a(_snowman).b());
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
