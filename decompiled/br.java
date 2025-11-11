import com.google.gson.JsonObject;

public class br extends ck<br.a> {
   private static final vk a = new vk("item_used_on_block");

   public br() {
   }

   @Override
   public vk a() {
      return a;
   }

   public br.a a(JsonObject var1, bg.b var2, ax var3) {
      bw _snowman = bw.a(_snowman.get("location"));
      bq _snowmanx = bq.a(_snowman.get("item"));
      return new br.a(_snowman, _snowman, _snowmanx);
   }

   public void a(aah var1, fx var2, bmb var3) {
      ceh _snowman = _snowman.u().d_(_snowman);
      this.a(_snowman, var4x -> var4x.a(_snowman, _snowman.u(), _snowman, _snowman));
   }

   public static class a extends al {
      private final bw a;
      private final bq b;

      public a(bg.b var1, bw var2, bq var3) {
         super(br.a, _snowman);
         this.a = _snowman;
         this.b = _snowman;
      }

      public static br.a a(bw.a var0, bq.a var1) {
         return new br.a(bg.b.a, _snowman.b(), _snowman.b());
      }

      public boolean a(ceh var1, aag var2, fx var3, bmb var4) {
         return !this.a.a(_snowman, (double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5) ? false : this.b.a(_snowman);
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         _snowman.add("location", this.a.a());
         _snowman.add("item", this.b.a());
         return _snowman;
      }
   }
}
