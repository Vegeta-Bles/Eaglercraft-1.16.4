import com.google.gson.JsonObject;

public class bo extends ck<bo.a> {
   private static final vk a = new vk("item_durability_changed");

   public bo() {
   }

   @Override
   public vk a() {
      return a;
   }

   public bo.a a(JsonObject var1, bg.b var2, ax var3) {
      bq _snowman = bq.a(_snowman.get("item"));
      bz.d _snowmanx = bz.d.a(_snowman.get("durability"));
      bz.d _snowmanxx = bz.d.a(_snowman.get("delta"));
      return new bo.a(_snowman, _snowman, _snowmanx, _snowmanxx);
   }

   public void a(aah var1, bmb var2, int var3) {
      this.a(_snowman, var2x -> var2x.a(_snowman, _snowman));
   }

   public static class a extends al {
      private final bq a;
      private final bz.d b;
      private final bz.d c;

      public a(bg.b var1, bq var2, bz.d var3, bz.d var4) {
         super(bo.a, _snowman);
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      public static bo.a a(bg.b var0, bq var1, bz.d var2) {
         return new bo.a(_snowman, _snowman, _snowman, bz.d.e);
      }

      public boolean a(bmb var1, int var2) {
         if (!this.a.a(_snowman)) {
            return false;
         } else {
            return !this.b.d(_snowman.h() - _snowman) ? false : this.c.d(_snowman.g() - _snowman);
         }
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         _snowman.add("item", this.a.a());
         _snowman.add("durability", this.b.d());
         _snowman.add("delta", this.c.d());
         return _snowman;
      }
   }
}
