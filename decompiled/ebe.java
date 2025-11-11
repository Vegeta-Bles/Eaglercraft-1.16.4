import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

public class ebe {
   public float[] a;
   public final int b;

   public ebe(@Nullable float[] var1, int var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   public float a(int var1) {
      if (this.a == null) {
         throw new NullPointerException("uvs");
      } else {
         int _snowman = this.d(_snowman);
         return this.a[_snowman != 0 && _snowman != 1 ? 2 : 0];
      }
   }

   public float b(int var1) {
      if (this.a == null) {
         throw new NullPointerException("uvs");
      } else {
         int _snowman = this.d(_snowman);
         return this.a[_snowman != 0 && _snowman != 3 ? 3 : 1];
      }
   }

   private int d(int var1) {
      return (_snowman + this.b / 90) % 4;
   }

   public int c(int var1) {
      return (_snowman + 4 - this.b / 90) % 4;
   }

   public void a(float[] var1) {
      if (this.a == null) {
         this.a = _snowman;
      }
   }

   public static class a implements JsonDeserializer<ebe> {
      protected a() {
      }

      public ebe a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject _snowman = _snowman.getAsJsonObject();
         float[] _snowmanx = this.b(_snowman);
         int _snowmanxx = this.a(_snowman);
         return new ebe(_snowmanx, _snowmanxx);
      }

      protected int a(JsonObject var1) {
         int _snowman = afd.a(_snowman, "rotation", 0);
         if (_snowman >= 0 && _snowman % 90 == 0 && _snowman / 90 <= 3) {
            return _snowman;
         } else {
            throw new JsonParseException("Invalid rotation " + _snowman + " found, only 0/90/180/270 allowed");
         }
      }

      @Nullable
      private float[] b(JsonObject var1) {
         if (!_snowman.has("uv")) {
            return null;
         } else {
            JsonArray _snowman = afd.u(_snowman, "uv");
            if (_snowman.size() != 4) {
               throw new JsonParseException("Expected 4 uv values, found: " + _snowman.size());
            } else {
               float[] _snowmanx = new float[4];

               for (int _snowmanxx = 0; _snowmanxx < _snowmanx.length; _snowmanxx++) {
                  _snowmanx[_snowmanxx] = afd.e(_snowman.get(_snowmanxx), "uv[" + _snowmanxx + "]");
               }

               return _snowmanx;
            }
         }
      }
   }
}
