import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

public class ebm {
   public static final ebm a = new ebm();
   public final ebl b;
   public final ebl c;
   public final ebl d;
   public final ebl e;
   public final ebl f;
   public final ebl g;
   public final ebl h;
   public final ebl i;

   private ebm() {
      this(ebl.a, ebl.a, ebl.a, ebl.a, ebl.a, ebl.a, ebl.a, ebl.a);
   }

   public ebm(ebm var1) {
      this.b = _snowman.b;
      this.c = _snowman.c;
      this.d = _snowman.d;
      this.e = _snowman.e;
      this.f = _snowman.f;
      this.g = _snowman.g;
      this.h = _snowman.h;
      this.i = _snowman.i;
   }

   public ebm(ebl var1, ebl var2, ebl var3, ebl var4, ebl var5, ebl var6, ebl var7, ebl var8) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
   }

   public ebl a(ebm.b var1) {
      switch (_snowman) {
         case b:
            return this.b;
         case c:
            return this.c;
         case d:
            return this.d;
         case e:
            return this.e;
         case f:
            return this.f;
         case g:
            return this.g;
         case h:
            return this.h;
         case i:
            return this.i;
         default:
            return ebl.a;
      }
   }

   public boolean b(ebm.b var1) {
      return this.a(_snowman) != ebl.a;
   }

   public static class a implements JsonDeserializer<ebm> {
      protected a() {
      }

      public ebm a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject _snowman = _snowman.getAsJsonObject();
         ebl _snowmanx = this.a(_snowman, _snowman, "thirdperson_righthand");
         ebl _snowmanxx = this.a(_snowman, _snowman, "thirdperson_lefthand");
         if (_snowmanxx == ebl.a) {
            _snowmanxx = _snowmanx;
         }

         ebl _snowmanxxx = this.a(_snowman, _snowman, "firstperson_righthand");
         ebl _snowmanxxxx = this.a(_snowman, _snowman, "firstperson_lefthand");
         if (_snowmanxxxx == ebl.a) {
            _snowmanxxxx = _snowmanxxx;
         }

         ebl _snowmanxxxxx = this.a(_snowman, _snowman, "head");
         ebl _snowmanxxxxxx = this.a(_snowman, _snowman, "gui");
         ebl _snowmanxxxxxxx = this.a(_snowman, _snowman, "ground");
         ebl _snowmanxxxxxxxx = this.a(_snowman, _snowman, "fixed");
         return new ebm(_snowmanxx, _snowmanx, _snowmanxxxx, _snowmanxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx);
      }

      private ebl a(JsonDeserializationContext var1, JsonObject var2, String var3) {
         return _snowman.has(_snowman) ? (ebl)_snowman.deserialize(_snowman.get(_snowman), ebl.class) : ebl.a;
      }
   }

   public static enum b {
      a,
      b,
      c,
      d,
      e,
      f,
      g,
      h,
      i;

      private b() {
      }

      public boolean a() {
         return this == d || this == e;
      }
   }
}
