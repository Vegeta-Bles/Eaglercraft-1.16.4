import com.google.gson.JsonObject;

public class bp extends ck<bp.a> {
   private static final vk a = new vk("thrown_item_picked_up_by_entity");

   public bp() {
   }

   @Override
   public vk a() {
      return a;
   }

   protected bp.a a(JsonObject var1, bg.b var2, ax var3) {
      bq _snowman = bq.a(_snowman.get("item"));
      bg.b _snowmanx = bg.b.a(_snowman, "entity", _snowman);
      return new bp.a(_snowman, _snowman, _snowmanx);
   }

   public void a(aah var1, bmb var2, aqa var3) {
      cyv _snowman = bg.b(_snowman, _snowman);
      this.a(_snowman, var3x -> var3x.a(_snowman, _snowman, _snowman));
   }

   public static class a extends al {
      private final bq a;
      private final bg.b b;

      public a(bg.b var1, bq var2, bg.b var3) {
         super(bp.a, _snowman);
         this.a = _snowman;
         this.b = _snowman;
      }

      public static bp.a a(bg.b var0, bq.a var1, bg.b var2) {
         return new bp.a(_snowman, _snowman.b(), _snowman);
      }

      public boolean a(aah var1, bmb var2, cyv var3) {
         return !this.a.a(_snowman) ? false : this.b.a(_snowman);
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         _snowman.add("item", this.a.a());
         _snowman.add("entity", this.b.a(_snowman));
         return _snowman;
      }
   }
}
