import com.google.gson.JsonObject;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class jj {
   private final blx a;
   private final bon b;
   private final float c;
   private final int d;
   private final y.a e = y.a.a();
   private String f;
   private final boz<?> g;

   private jj(brw var1, bon var2, float var3, int var4, boz<?> var5) {
      this.a = _snowman.h();
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.g = _snowman;
   }

   public static jj a(bon var0, brw var1, float var2, int var3, boz<?> var4) {
      return new jj(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public static jj b(bon var0, brw var1, float var2, int var3) {
      return a(_snowman, _snowman, _snowman, _snowman, bos.q);
   }

   public static jj c(bon var0, brw var1, float var2, int var3) {
      return a(_snowman, _snowman, _snowman, _snowman, bos.p);
   }

   public jj a(String var1, ag var2) {
      this.e.a(_snowman, _snowman);
      return this;
   }

   public void a(Consumer<jf> var1) {
      this.a(_snowman, gm.T.b(this.a));
   }

   public void a(Consumer<jf> var1, String var2) {
      vk _snowman = gm.T.b(this.a);
      vk _snowmanx = new vk(_snowman);
      if (_snowmanx.equals(_snowman)) {
         throw new IllegalStateException("Recipe " + _snowmanx + " should remove its 'save' argument");
      } else {
         this.a(_snowman, _snowmanx);
      }
   }

   public void a(Consumer<jf> var1, vk var2) {
      this.a(_snowman);
      this.e.a(new vk("recipes/root")).a("has_the_recipe", ch.a(_snowman)).a(ab.a.c(_snowman)).a(aj.b);
      _snowman.accept(
         new jj.a(
            _snowman,
            this.f == null ? "" : this.f,
            this.b,
            this.a,
            this.c,
            this.d,
            this.e,
            new vk(_snowman.b(), "recipes/" + this.a.q().b() + "/" + _snowman.a()),
            (bos<? extends boc>)this.g
         )
      );
   }

   private void a(vk var1) {
      if (this.e.c().isEmpty()) {
         throw new IllegalStateException("No way of obtaining recipe " + _snowman);
      }
   }

   public static class a implements jf {
      private final vk a;
      private final String b;
      private final bon c;
      private final blx d;
      private final float e;
      private final int f;
      private final y.a g;
      private final vk h;
      private final bos<? extends boc> i;

      public a(vk var1, String var2, bon var3, blx var4, float var5, int var6, y.a var7, vk var8, bos<? extends boc> var9) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
         this.g = _snowman;
         this.h = _snowman;
         this.i = _snowman;
      }

      @Override
      public void a(JsonObject var1) {
         if (!this.b.isEmpty()) {
            _snowman.addProperty("group", this.b);
         }

         _snowman.add("ingredient", this.c.c());
         _snowman.addProperty("result", gm.T.b(this.d).toString());
         _snowman.addProperty("experience", this.e);
         _snowman.addProperty("cookingtime", this.f);
      }

      @Override
      public bos<?> c() {
         return this.i;
      }

      @Override
      public vk b() {
         return this.a;
      }

      @Nullable
      @Override
      public JsonObject d() {
         return this.g.b();
      }

      @Nullable
      @Override
      public vk e() {
         return this.h;
      }
   }
}
