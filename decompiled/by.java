import com.google.gson.JsonObject;

public class by extends ck<by.a> {
   private static final vk a = new vk("player_generates_container_loot");

   public by() {
   }

   @Override
   public vk a() {
      return a;
   }

   protected by.a a(JsonObject var1, bg.b var2, ax var3) {
      vk _snowman = new vk(afd.h(_snowman, "loot_table"));
      return new by.a(_snowman, _snowman);
   }

   public void a(aah var1, vk var2) {
      this.a(_snowman, var1x -> var1x.b(_snowman));
   }

   public static class a extends al {
      private final vk a;

      public a(bg.b var1, vk var2) {
         super(by.a, _snowman);
         this.a = _snowman;
      }

      public static by.a a(vk var0) {
         return new by.a(bg.b.a, _snowman);
      }

      public boolean b(vk var1) {
         return this.a.equals(_snowman);
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         _snowman.addProperty("loot_table", this.a.toString());
         return _snowman;
      }
   }
}
