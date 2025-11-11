import com.google.gson.JsonObject;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class jk {
   private final blx a;
   private final bon b;
   private final int c;
   private final y.a d = y.a.a();
   private String e;
   private final bos<?> f;

   public jk(bos<?> var1, bon var2, brw var3, int var4) {
      this.f = _snowman;
      this.a = _snowman.h();
      this.b = _snowman;
      this.c = _snowman;
   }

   public static jk a(bon var0, brw var1) {
      return new jk(bos.t, _snowman, _snowman, 1);
   }

   public static jk a(bon var0, brw var1, int var2) {
      return new jk(bos.t, _snowman, _snowman, _snowman);
   }

   public jk a(String var1, ag var2) {
      this.d.a(_snowman, _snowman);
      return this;
   }

   public void a(Consumer<jf> var1, String var2) {
      vk _snowman = gm.T.b(this.a);
      if (new vk(_snowman).equals(_snowman)) {
         throw new IllegalStateException("Single Item Recipe " + _snowman + " should remove its 'save' argument");
      } else {
         this.a(_snowman, new vk(_snowman));
      }
   }

   public void a(Consumer<jf> var1, vk var2) {
      this.a(_snowman);
      this.d.a(new vk("recipes/root")).a("has_the_recipe", ch.a(_snowman)).a(ab.a.c(_snowman)).a(aj.b);
      _snowman.accept(new jk.a(_snowman, this.f, this.e == null ? "" : this.e, this.b, this.a, this.c, this.d, new vk(_snowman.b(), "recipes/" + this.a.q().b() + "/" + _snowman.a())));
   }

   private void a(vk var1) {
      if (this.d.c().isEmpty()) {
         throw new IllegalStateException("No way of obtaining recipe " + _snowman);
      }
   }

   public static class a implements jf {
      private final vk a;
      private final String b;
      private final bon c;
      private final blx d;
      private final int e;
      private final y.a f;
      private final vk g;
      private final bos<?> h;

      public a(vk var1, bos<?> var2, String var3, bon var4, blx var5, int var6, y.a var7, vk var8) {
         this.a = _snowman;
         this.h = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
         this.g = _snowman;
      }

      @Override
      public void a(JsonObject var1) {
         if (!this.b.isEmpty()) {
            _snowman.addProperty("group", this.b);
         }

         _snowman.add("ingredient", this.c.c());
         _snowman.addProperty("result", gm.T.b(this.d).toString());
         _snowman.addProperty("count", this.e);
      }

      @Override
      public vk b() {
         return this.a;
      }

      @Override
      public bos<?> c() {
         return this.h;
      }

      @Nullable
      @Override
      public JsonObject d() {
         return this.f.b();
      }

      @Nullable
      @Override
      public vk e() {
         return this.g;
      }
   }
}
