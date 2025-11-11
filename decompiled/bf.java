import com.google.gson.JsonObject;

public class bf extends ck<bf.a> {
   private static final vk a = new vk("entity_hurt_player");

   public bf() {
   }

   @Override
   public vk a() {
      return a;
   }

   public bf.a a(JsonObject var1, bg.b var2, ax var3) {
      av _snowman = av.a(_snowman.get("damage"));
      return new bf.a(_snowman, _snowman);
   }

   public void a(aah var1, apk var2, float var3, float var4, boolean var5) {
      this.a(_snowman, var5x -> var5x.a(_snowman, _snowman, _snowman, _snowman, _snowman));
   }

   public static class a extends al {
      private final av a;

      public a(bg.b var1, av var2) {
         super(bf.a, _snowman);
         this.a = _snowman;
      }

      public static bf.a a(av.a var0) {
         return new bf.a(bg.b.a, _snowman.b());
      }

      public boolean a(aah var1, apk var2, float var3, float var4, boolean var5) {
         return this.a.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         _snowman.add("damage", this.a.a());
         return _snowman;
      }
   }
}
