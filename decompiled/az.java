import com.google.gson.JsonObject;

public class az extends ck<az.a> {
   private static final vk a = new vk("effects_changed");

   public az() {
   }

   @Override
   public vk a() {
      return a;
   }

   public az.a a(JsonObject var1, bg.b var2, ax var3) {
      ca _snowman = ca.a(_snowman.get("effects"));
      return new az.a(_snowman, _snowman);
   }

   public void a(aah var1) {
      this.a(_snowman, var1x -> var1x.a(_snowman));
   }

   public static class a extends al {
      private final ca a;

      public a(bg.b var1, ca var2) {
         super(az.a, _snowman);
         this.a = _snowman;
      }

      public static az.a a(ca var0) {
         return new az.a(bg.b.a, _snowman);
      }

      public boolean a(aah var1) {
         return this.a.a((aqm)_snowman);
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         _snowman.add("effects", this.a.b());
         return _snowman;
      }
   }
}
