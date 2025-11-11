import com.google.gson.JsonObject;

public class ce extends ck<ce.a> {
   private static final vk a = new vk("player_hurt_entity");

   public ce() {
   }

   @Override
   public vk a() {
      return a;
   }

   public ce.a a(JsonObject var1, bg.b var2, ax var3) {
      av _snowman = av.a(_snowman.get("damage"));
      bg.b _snowmanx = bg.b.a(_snowman, "entity", _snowman);
      return new ce.a(_snowman, _snowman, _snowmanx);
   }

   public void a(aah var1, aqa var2, apk var3, float var4, float var5, boolean var6) {
      cyv _snowman = bg.b(_snowman, _snowman);
      this.a(_snowman, var6x -> var6x.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman));
   }

   public static class a extends al {
      private final av a;
      private final bg.b b;

      public a(bg.b var1, av var2, bg.b var3) {
         super(ce.a, _snowman);
         this.a = _snowman;
         this.b = _snowman;
      }

      public static ce.a a(av.a var0) {
         return new ce.a(bg.b.a, _snowman.b(), bg.b.a);
      }

      public boolean a(aah var1, cyv var2, apk var3, float var4, float var5, boolean var6) {
         return !this.a.a(_snowman, _snowman, _snowman, _snowman, _snowman) ? false : this.b.a(_snowman);
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         _snowman.add("damage", this.a.a());
         _snowman.add("entity", this.b.a(_snowman));
         return _snowman;
      }
   }
}
