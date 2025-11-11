import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;

public class czv extends czs {
   private final ael<blx> g;
   private final boolean h;

   private czv(ael<blx> var1, boolean var2, int var3, int var4, dbo[] var5, daj[] var6) {
      super(_snowman, _snowman, _snowman, _snowman);
      this.g = _snowman;
      this.h = _snowman;
   }

   @Override
   public czr a() {
      return czo.e;
   }

   @Override
   public void a(Consumer<bmb> var1, cyv var2) {
      this.g.b().forEach(var1x -> _snowman.accept(new bmb(var1x)));
   }

   private boolean a(cyv var1, Consumer<czp> var2) {
      if (!this.a(_snowman)) {
         return false;
      } else {
         for (final blx _snowman : this.g.b()) {
            _snowman.accept(new czs.c() {
               @Override
               public void a(Consumer<bmb> var1, cyv var2) {
                  _snowman.accept(new bmb(_snowman));
               }
            });
         }

         return true;
      }
   }

   @Override
   public boolean expand(cyv var1, Consumer<czp> var2) {
      return this.h ? this.a(_snowman, _snowman) : super.expand(_snowman, _snowman);
   }

   public static czs.a<?> b(ael<blx> var0) {
      return a((var1, var2, var3, var4) -> new czv(_snowman, true, var1, var2, var3, var4));
   }

   public static class a extends czs.e<czv> {
      public a() {
      }

      public void a(JsonObject var1, czv var2, JsonSerializationContext var3) {
         super.a(_snowman, _snowman, _snowman);
         _snowman.addProperty("name", aeh.a().b().b(_snowman.g).toString());
         _snowman.addProperty("expand", _snowman.h);
      }

      protected czv a(JsonObject var1, JsonDeserializationContext var2, int var3, int var4, dbo[] var5, daj[] var6) {
         vk _snowman = new vk(afd.h(_snowman, "name"));
         ael<blx> _snowmanx = aeh.a().b().a(_snowman);
         if (_snowmanx == null) {
            throw new JsonParseException("Can't find tag: " + _snowman);
         } else {
            boolean _snowmanxx = afd.j(_snowman, "expand");
            return new czv(_snowmanx, _snowmanxx, _snowman, _snowman, _snowman, _snowman);
         }
      }
   }
}
