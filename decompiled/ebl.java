import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

public class ebl {
   public static final ebl a = new ebl(new g(), new g(), new g(1.0F, 1.0F, 1.0F));
   public final g b;
   public final g c;
   public final g d;

   public ebl(g var1, g var2, g var3) {
      this.b = _snowman.e();
      this.c = _snowman.e();
      this.d = _snowman.e();
   }

   public void a(boolean var1, dfm var2) {
      if (this != a) {
         float _snowman = this.b.a();
         float _snowmanx = this.b.b();
         float _snowmanxx = this.b.c();
         if (_snowman) {
            _snowmanx = -_snowmanx;
            _snowmanxx = -_snowmanxx;
         }

         int _snowmanxxx = _snowman ? -1 : 1;
         _snowman.a((double)((float)_snowmanxxx * this.c.a()), (double)this.c.b(), (double)this.c.c());
         _snowman.a(new d(_snowman, _snowmanx, _snowmanxx, true));
         _snowman.a(this.d.a(), this.d.b(), this.d.c());
      }
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (this.getClass() != _snowman.getClass()) {
         return false;
      } else {
         ebl _snowman = (ebl)_snowman;
         return this.b.equals(_snowman.b) && this.d.equals(_snowman.d) && this.c.equals(_snowman.c);
      }
   }

   @Override
   public int hashCode() {
      int _snowman = this.b.hashCode();
      _snowman = 31 * _snowman + this.c.hashCode();
      return 31 * _snowman + this.d.hashCode();
   }

   public static class a implements JsonDeserializer<ebl> {
      private static final g a = new g(0.0F, 0.0F, 0.0F);
      private static final g b = new g(0.0F, 0.0F, 0.0F);
      private static final g c = new g(1.0F, 1.0F, 1.0F);

      protected a() {
      }

      public ebl a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject _snowman = _snowman.getAsJsonObject();
         g _snowmanx = this.a(_snowman, "rotation", a);
         g _snowmanxx = this.a(_snowman, "translation", b);
         _snowmanxx.b(0.0625F);
         _snowmanxx.a(-5.0F, 5.0F);
         g _snowmanxxx = this.a(_snowman, "scale", c);
         _snowmanxxx.a(-4.0F, 4.0F);
         return new ebl(_snowmanx, _snowmanxx, _snowmanxxx);
      }

      private g a(JsonObject var1, String var2, g var3) {
         if (!_snowman.has(_snowman)) {
            return _snowman;
         } else {
            JsonArray _snowman = afd.u(_snowman, _snowman);
            if (_snowman.size() != 3) {
               throw new JsonParseException("Expected 3 " + _snowman + " values, found: " + _snowman.size());
            } else {
               float[] _snowmanx = new float[3];

               for (int _snowmanxx = 0; _snowmanxx < _snowmanx.length; _snowmanxx++) {
                  _snowmanx[_snowmanxx] = afd.e(_snowman.get(_snowmanxx), _snowman + "[" + _snowmanxx + "]");
               }

               return new g(_snowmanx[0], _snowmanx[1], _snowmanx[2]);
            }
         }
      }
   }
}
