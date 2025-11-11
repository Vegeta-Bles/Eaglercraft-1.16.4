import com.google.gson.JsonObject;

public class as extends ck<as.a> {
   private static final vk a = new vk("construct_beacon");

   public as() {
   }

   @Override
   public vk a() {
      return a;
   }

   public as.a a(JsonObject var1, bg.b var2, ax var3) {
      bz.d _snowman = bz.d.a(_snowman.get("level"));
      return new as.a(_snowman, _snowman);
   }

   public void a(aah var1, cce var2) {
      this.a(_snowman, var1x -> var1x.a(_snowman));
   }

   public static class a extends al {
      private final bz.d a;

      public a(bg.b var1, bz.d var2) {
         super(as.a, _snowman);
         this.a = _snowman;
      }

      public static as.a a(bz.d var0) {
         return new as.a(bg.b.a, _snowman);
      }

      public boolean a(cce var1) {
         return this.a.d(_snowman.h());
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         _snowman.add("level", this.a.d());
         return _snowman;
      }
   }
}
