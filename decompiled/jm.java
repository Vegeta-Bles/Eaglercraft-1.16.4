import com.google.gson.JsonObject;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class jm {
   private final bon a;
   private final bon b;
   private final blx c;
   private final y.a d = y.a.a();
   private final bos<?> e;

   public jm(bos<?> var1, bon var2, bon var3, blx var4) {
      this.e = _snowman;
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   public static jm a(bon var0, bon var1, blx var2) {
      return new jm(bos.u, _snowman, _snowman, _snowman);
   }

   public jm a(String var1, ag var2) {
      this.d.a(_snowman, _snowman);
      return this;
   }

   public void a(Consumer<jf> var1, String var2) {
      this.a(_snowman, new vk(_snowman));
   }

   public void a(Consumer<jf> var1, vk var2) {
      this.a(_snowman);
      this.d.a(new vk("recipes/root")).a("has_the_recipe", ch.a(_snowman)).a(ab.a.c(_snowman)).a(aj.b);
      _snowman.accept(new jm.a(_snowman, this.e, this.a, this.b, this.c, this.d, new vk(_snowman.b(), "recipes/" + this.c.q().b() + "/" + _snowman.a())));
   }

   private void a(vk var1) {
      if (this.d.c().isEmpty()) {
         throw new IllegalStateException("No way of obtaining recipe " + _snowman);
      }
   }

   public static class a implements jf {
      private final vk a;
      private final bon b;
      private final bon c;
      private final blx d;
      private final y.a e;
      private final vk f;
      private final bos<?> g;

      public a(vk var1, bos<?> var2, bon var3, bon var4, blx var5, y.a var6, vk var7) {
         this.a = _snowman;
         this.g = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
      }

      @Override
      public void a(JsonObject var1) {
         _snowman.add("base", this.b.c());
         _snowman.add("addition", this.c.c());
         JsonObject _snowman = new JsonObject();
         _snowman.addProperty("item", gm.T.b(this.d).toString());
         _snowman.add("result", _snowman);
      }

      @Override
      public vk b() {
         return this.a;
      }

      @Override
      public bos<?> c() {
         return this.g;
      }

      @Nullable
      @Override
      public JsonObject d() {
         return this.e.b();
      }

      @Nullable
      @Override
      public vk e() {
         return this.f;
      }
   }
}
