import com.google.gson.JsonObject;

public class ch extends ck<ch.a> {
   private static final vk a = new vk("recipe_unlocked");

   public ch() {
   }

   @Override
   public vk a() {
      return a;
   }

   public ch.a a(JsonObject var1, bg.b var2, ax var3) {
      vk _snowman = new vk(afd.h(_snowman, "recipe"));
      return new ch.a(_snowman, _snowman);
   }

   public void a(aah var1, boq<?> var2) {
      this.a(_snowman, var1x -> var1x.a(_snowman));
   }

   public static ch.a a(vk var0) {
      return new ch.a(bg.b.a, _snowman);
   }

   public static class a extends al {
      private final vk a;

      public a(bg.b var1, vk var2) {
         super(ch.a, _snowman);
         this.a = _snowman;
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         _snowman.addProperty("recipe", this.a.toString());
         return _snowman;
      }

      public boolean a(boq<?> var1) {
         return this.a.equals(_snowman.f());
      }
   }
}
