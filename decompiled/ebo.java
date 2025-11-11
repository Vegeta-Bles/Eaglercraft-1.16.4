import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.Objects;

public class ebo implements elv {
   private final vk a;
   private final f b;
   private final boolean c;
   private final int d;

   public ebo(vk var1, f var2, boolean var3, int var4) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   public vk a() {
      return this.a;
   }

   @Override
   public f b() {
      return this.b;
   }

   @Override
   public boolean c() {
      return this.c;
   }

   public int d() {
      return this.d;
   }

   @Override
   public String toString() {
      return "Variant{modelLocation=" + this.a + ", rotation=" + this.b + ", uvLock=" + this.c + ", weight=" + this.d + '}';
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof ebo)) {
         return false;
      } else {
         ebo _snowman = (ebo)_snowman;
         return this.a.equals(_snowman.a) && Objects.equals(this.b, _snowman.b) && this.c == _snowman.c && this.d == _snowman.d;
      }
   }

   @Override
   public int hashCode() {
      int _snowman = this.a.hashCode();
      _snowman = 31 * _snowman + this.b.hashCode();
      _snowman = 31 * _snowman + Boolean.valueOf(this.c).hashCode();
      return 31 * _snowman + this.d;
   }

   public static class a implements JsonDeserializer<ebo> {
      public a() {
      }

      public ebo a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject _snowman = _snowman.getAsJsonObject();
         vk _snowmanx = this.b(_snowman);
         elp _snowmanxx = this.a(_snowman);
         boolean _snowmanxxx = this.d(_snowman);
         int _snowmanxxxx = this.c(_snowman);
         return new ebo(_snowmanx, _snowmanxx.b(), _snowmanxxx, _snowmanxxxx);
      }

      private boolean d(JsonObject var1) {
         return afd.a(_snowman, "uvlock", false);
      }

      protected elp a(JsonObject var1) {
         int _snowman = afd.a(_snowman, "x", 0);
         int _snowmanx = afd.a(_snowman, "y", 0);
         elp _snowmanxx = elp.a(_snowman, _snowmanx);
         if (_snowmanxx == null) {
            throw new JsonParseException("Invalid BlockModelRotation x: " + _snowman + ", y: " + _snowmanx);
         } else {
            return _snowmanxx;
         }
      }

      protected vk b(JsonObject var1) {
         return new vk(afd.h(_snowman, "model"));
      }

      protected int c(JsonObject var1) {
         int _snowman = afd.a(_snowman, "weight", 1);
         if (_snowman < 1) {
            throw new JsonParseException("Invalid weight " + _snowman + " found, expected integer >= 1");
         } else {
            return _snowman;
         }
      }
   }
}
