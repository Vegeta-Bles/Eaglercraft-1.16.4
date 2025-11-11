import com.google.gson.JsonObject;

public class bu extends ck<bu.a> {
   private static final vk a = new vk("levitation");

   public bu() {
   }

   @Override
   public vk a() {
      return a;
   }

   public bu.a a(JsonObject var1, bg.b var2, ax var3) {
      ay _snowman = ay.a(_snowman.get("distance"));
      bz.d _snowmanx = bz.d.a(_snowman.get("duration"));
      return new bu.a(_snowman, _snowman, _snowmanx);
   }

   public void a(aah var1, dcn var2, int var3) {
      this.a(_snowman, var3x -> var3x.a(_snowman, _snowman, _snowman));
   }

   public static class a extends al {
      private final ay a;
      private final bz.d b;

      public a(bg.b var1, ay var2, bz.d var3) {
         super(bu.a, _snowman);
         this.a = _snowman;
         this.b = _snowman;
      }

      public static bu.a a(ay var0) {
         return new bu.a(bg.b.a, _snowman, bz.d.e);
      }

      public boolean a(aah var1, dcn var2, int var3) {
         return !this.a.a(_snowman.b, _snowman.c, _snowman.d, _snowman.cD(), _snowman.cE(), _snowman.cH()) ? false : this.b.d(_snowman);
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         _snowman.add("distance", this.a.a());
         _snowman.add("duration", this.b.d());
         return _snowman;
      }
   }
}
