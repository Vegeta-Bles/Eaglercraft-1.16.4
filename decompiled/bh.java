import com.google.common.base.Joiner;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;

public abstract class bh {
   public static final bh a = new bh() {
      @Override
      public boolean a(aqe<?> var1) {
         return true;
      }

      @Override
      public JsonElement a() {
         return JsonNull.INSTANCE;
      }
   };
   private static final Joiner b = Joiner.on(", ");

   public bh() {
   }

   public abstract boolean a(aqe<?> var1);

   public abstract JsonElement a();

   public static bh a(@Nullable JsonElement var0) {
      if (_snowman != null && !_snowman.isJsonNull()) {
         String _snowman = afd.a(_snowman, "type");
         if (_snowman.startsWith("#")) {
            vk _snowmanx = new vk(_snowman.substring(1));
            return new bh.a(aeh.a().d().b(_snowmanx));
         } else {
            vk _snowmanx = new vk(_snowman);
            aqe<?> _snowmanxx = gm.S.b(_snowmanx).orElseThrow(() -> new JsonSyntaxException("Unknown entity type '" + _snowman + "', valid types are: " + b.join(gm.S.c())));
            return new bh.b(_snowmanxx);
         }
      } else {
         return a;
      }
   }

   public static bh b(aqe<?> var0) {
      return new bh.b(_snowman);
   }

   public static bh a(ael<aqe<?>> var0) {
      return new bh.a(_snowman);
   }

   static class a extends bh {
      private final ael<aqe<?>> b;

      public a(ael<aqe<?>> var1) {
         this.b = _snowman;
      }

      @Override
      public boolean a(aqe<?> var1) {
         return this.b.a(_snowman);
      }

      @Override
      public JsonElement a() {
         return new JsonPrimitive("#" + aeh.a().d().b(this.b));
      }
   }

   static class b extends bh {
      private final aqe<?> b;

      public b(aqe<?> var1) {
         this.b = _snowman;
      }

      @Override
      public boolean a(aqe<?> var1) {
         return this.b == _snowman;
      }

      @Override
      public JsonElement a() {
         return new JsonPrimitive(gm.S.b(this.b).toString());
      }
   }
}
