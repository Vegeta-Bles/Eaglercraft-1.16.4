import com.google.gson.JsonObject;

public class bx extends ck<bx.a> {
   private final vk a;

   public bx(vk var1) {
      this.a = _snowman;
   }

   @Override
   public vk a() {
      return this.a;
   }

   public bx.a a(JsonObject var1, bg.b var2, ax var3) {
      JsonObject _snowman = afd.a(_snowman, "location", _snowman);
      bw _snowmanx = bw.a(_snowman);
      return new bx.a(this.a, _snowman, _snowmanx);
   }

   public void a(aah var1) {
      this.a(_snowman, var1x -> var1x.a(_snowman.u(), _snowman.cD(), _snowman.cE(), _snowman.cH()));
   }

   public static class a extends al {
      private final bw a;

      public a(vk var1, bg.b var2, bw var3) {
         super(_snowman, _snowman);
         this.a = _snowman;
      }

      public static bx.a a(bw var0) {
         return new bx.a(ac.p.a, bg.b.a, _snowman);
      }

      public static bx.a c() {
         return new bx.a(ac.q.a, bg.b.a, bw.a);
      }

      public static bx.a d() {
         return new bx.a(ac.H.a, bg.b.a, bw.a);
      }

      public boolean a(aag var1, double var2, double var4, double var6) {
         return this.a.a(_snowman, _snowman, _snowman, _snowman);
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         _snowman.add("location", this.a.a());
         return _snowman;
      }
   }
}
