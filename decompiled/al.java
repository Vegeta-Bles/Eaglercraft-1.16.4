import com.google.gson.JsonObject;

public abstract class al implements ag {
   private final vk a;
   private final bg.b b;

   public al(vk var1, bg.b var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public vk a() {
      return this.a;
   }

   protected bg.b b() {
      return this.b;
   }

   @Override
   public JsonObject a(ci var1) {
      JsonObject _snowman = new JsonObject();
      _snowman.add("player", this.b.a(_snowman));
      return _snowman;
   }

   @Override
   public String toString() {
      return "AbstractCriterionInstance{criterion=" + this.a + '}';
   }
}
