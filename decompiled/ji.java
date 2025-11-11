import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ji {
   private static final Logger a = LogManager.getLogger();
   private final blx b;
   private final int c;
   private final List<bon> d = Lists.newArrayList();
   private final y.a e = y.a.a();
   private String f;

   public ji(brw var1, int var2) {
      this.b = _snowman.h();
      this.c = _snowman;
   }

   public static ji a(brw var0) {
      return new ji(_snowman, 1);
   }

   public static ji a(brw var0, int var1) {
      return new ji(_snowman, _snowman);
   }

   public ji a(ael<blx> var1) {
      return this.a(bon.a(_snowman));
   }

   public ji b(brw var1) {
      return this.b(_snowman, 1);
   }

   public ji b(brw var1, int var2) {
      for (int _snowman = 0; _snowman < _snowman; _snowman++) {
         this.a(bon.a(_snowman));
      }

      return this;
   }

   public ji a(bon var1) {
      return this.a(_snowman, 1);
   }

   public ji a(bon var1, int var2) {
      for (int _snowman = 0; _snowman < _snowman; _snowman++) {
         this.d.add(_snowman);
      }

      return this;
   }

   public ji a(String var1, ag var2) {
      this.e.a(_snowman, _snowman);
      return this;
   }

   public ji a(String var1) {
      this.f = _snowman;
      return this;
   }

   public void a(Consumer<jf> var1) {
      this.a(_snowman, gm.T.b(this.b));
   }

   public void a(Consumer<jf> var1, String var2) {
      vk _snowman = gm.T.b(this.b);
      if (new vk(_snowman).equals(_snowman)) {
         throw new IllegalStateException("Shapeless Recipe " + _snowman + " should remove its 'save' argument");
      } else {
         this.a(_snowman, new vk(_snowman));
      }
   }

   public void a(Consumer<jf> var1, vk var2) {
      this.a(_snowman);
      this.e.a(new vk("recipes/root")).a("has_the_recipe", ch.a(_snowman)).a(ab.a.c(_snowman)).a(aj.b);
      _snowman.accept(new ji.a(_snowman, this.b, this.c, this.f == null ? "" : this.f, this.d, this.e, new vk(_snowman.b(), "recipes/" + this.b.q().b() + "/" + _snowman.a())));
   }

   private void a(vk var1) {
      if (this.e.c().isEmpty()) {
         throw new IllegalStateException("No way of obtaining recipe " + _snowman);
      }
   }

   public static class a implements jf {
      private final vk a;
      private final blx b;
      private final int c;
      private final String d;
      private final List<bon> e;
      private final y.a f;
      private final vk g;

      public a(vk var1, blx var2, int var3, String var4, List<bon> var5, y.a var6, vk var7) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
         this.g = _snowman;
      }

      @Override
      public void a(JsonObject var1) {
         if (!this.d.isEmpty()) {
            _snowman.addProperty("group", this.d);
         }

         JsonArray _snowman = new JsonArray();

         for (bon _snowmanx : this.e) {
            _snowman.add(_snowmanx.c());
         }

         _snowman.add("ingredients", _snowman);
         JsonObject _snowmanx = new JsonObject();
         _snowmanx.addProperty("item", gm.T.b(this.b).toString());
         if (this.c > 1) {
            _snowmanx.addProperty("count", this.c);
         }

         _snowman.add("result", _snowmanx);
      }

      @Override
      public bos<?> c() {
         return bos.b;
      }

      @Override
      public vk b() {
         return this.a;
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
