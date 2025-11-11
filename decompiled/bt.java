import com.google.gson.JsonObject;

public class bt extends ck<bt.a> {
   private final vk a;

   public bt(vk var1) {
      this.a = _snowman;
   }

   @Override
   public vk a() {
      return this.a;
   }

   public bt.a a(JsonObject var1, bg.b var2, ax var3) {
      return new bt.a(this.a, _snowman, bg.b.a(_snowman, "entity", _snowman), aw.a(_snowman.get("killing_blow")));
   }

   public void a(aah var1, aqa var2, apk var3) {
      cyv _snowman = bg.b(_snowman, _snowman);
      this.a(_snowman, var3x -> var3x.a(_snowman, _snowman, _snowman));
   }

   public static class a extends al {
      private final bg.b a;
      private final aw b;

      public a(vk var1, bg.b var2, bg.b var3, aw var4) {
         super(_snowman, _snowman);
         this.a = _snowman;
         this.b = _snowman;
      }

      public static bt.a a(bg.a var0) {
         return new bt.a(ac.b.a, bg.b.a, bg.b.a(_snowman.b()), aw.a);
      }

      public static bt.a c() {
         return new bt.a(ac.b.a, bg.b.a, bg.b.a, aw.a);
      }

      public static bt.a a(bg.a var0, aw.a var1) {
         return new bt.a(ac.b.a, bg.b.a, bg.b.a(_snowman.b()), _snowman.b());
      }

      public static bt.a d() {
         return new bt.a(ac.c.a, bg.b.a, bg.b.a, aw.a);
      }

      public boolean a(aah var1, cyv var2, apk var3) {
         return !this.b.a(_snowman, _snowman) ? false : this.a.a(_snowman);
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         _snowman.add("entity", this.a.a(_snowman));
         _snowman.add("killing_blow", this.b.a());
         return _snowman;
      }
   }
}
