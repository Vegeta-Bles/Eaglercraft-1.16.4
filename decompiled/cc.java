import com.google.gson.JsonObject;

public class cc extends ck<cc.a> {
   private static final vk a = new vk("nether_travel");

   public cc() {
   }

   @Override
   public vk a() {
      return a;
   }

   public cc.a a(JsonObject var1, bg.b var2, ax var3) {
      bw _snowman = bw.a(_snowman.get("entered"));
      bw _snowmanx = bw.a(_snowman.get("exited"));
      ay _snowmanxx = ay.a(_snowman.get("distance"));
      return new cc.a(_snowman, _snowman, _snowmanx, _snowmanxx);
   }

   public void a(aah var1, dcn var2) {
      this.a(_snowman, var2x -> var2x.a(_snowman.u(), _snowman, _snowman.cD(), _snowman.cE(), _snowman.cH()));
   }

   public static class a extends al {
      private final bw a;
      private final bw b;
      private final ay c;

      public a(bg.b var1, bw var2, bw var3, ay var4) {
         super(cc.a, _snowman);
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      public static cc.a a(ay var0) {
         return new cc.a(bg.b.a, bw.a, bw.a, _snowman);
      }

      public boolean a(aag var1, dcn var2, double var3, double var5, double var7) {
         if (!this.a.a(_snowman, _snowman.b, _snowman.c, _snowman.d)) {
            return false;
         } else {
            return !this.b.a(_snowman, _snowman, _snowman, _snowman) ? false : this.c.a(_snowman.b, _snowman.c, _snowman.d, _snowman, _snowman, _snowman);
         }
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         _snowman.add("entered", this.a.a());
         _snowman.add("exited", this.b.a());
         _snowman.add("distance", this.c.a());
         return _snowman;
      }
   }
}
