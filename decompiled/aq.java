import com.google.gson.JsonObject;
import javax.annotation.Nullable;

public class aq extends ck<aq.a> {
   private static final vk a = new vk("changed_dimension");

   public aq() {
   }

   @Override
   public vk a() {
      return a;
   }

   public aq.a a(JsonObject var1, bg.b var2, ax var3) {
      vj<brx> _snowman = _snowman.has("from") ? vj.a(gm.L, new vk(afd.h(_snowman, "from"))) : null;
      vj<brx> _snowmanx = _snowman.has("to") ? vj.a(gm.L, new vk(afd.h(_snowman, "to"))) : null;
      return new aq.a(_snowman, _snowman, _snowmanx);
   }

   public void a(aah var1, vj<brx> var2, vj<brx> var3) {
      this.a(_snowman, var2x -> var2x.b(_snowman, _snowman));
   }

   public static class a extends al {
      @Nullable
      private final vj<brx> a;
      @Nullable
      private final vj<brx> b;

      public a(bg.b var1, @Nullable vj<brx> var2, @Nullable vj<brx> var3) {
         super(aq.a, _snowman);
         this.a = _snowman;
         this.b = _snowman;
      }

      public static aq.a a(vj<brx> var0) {
         return new aq.a(bg.b.a, null, _snowman);
      }

      public boolean b(vj<brx> var1, vj<brx> var2) {
         return this.a != null && this.a != _snowman ? false : this.b == null || this.b == _snowman;
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         if (this.a != null) {
            _snowman.addProperty("from", this.a.a().toString());
         }

         if (this.b != null) {
            _snowman.addProperty("to", this.b.a().toString());
         }

         return _snowman;
      }
   }
}
