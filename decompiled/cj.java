import com.google.gson.JsonObject;

public class cj extends ck<cj.a> {
   private static final vk a = new vk("shot_crossbow");

   public cj() {
   }

   @Override
   public vk a() {
      return a;
   }

   public cj.a a(JsonObject var1, bg.b var2, ax var3) {
      bq _snowman = bq.a(_snowman.get("item"));
      return new cj.a(_snowman, _snowman);
   }

   public void a(aah var1, bmb var2) {
      this.a(_snowman, var1x -> var1x.a(_snowman));
   }

   public static class a extends al {
      private final bq a;

      public a(bg.b var1, bq var2) {
         super(cj.a, _snowman);
         this.a = _snowman;
      }

      public static cj.a a(brw var0) {
         return new cj.a(bg.b.a, bq.a.a().a(_snowman).b());
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
