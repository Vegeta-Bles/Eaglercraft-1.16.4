import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

public class ebc {
   public final gc a;
   public final int b;
   public final String c;
   public final ebe d;

   public ebc(@Nullable gc var1, int var2, String var3, ebe var4) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   public static class a implements JsonDeserializer<ebc> {
      protected a() {
      }

      public ebc a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject _snowman = _snowman.getAsJsonObject();
         gc _snowmanx = this.c(_snowman);
         int _snowmanxx = this.a(_snowman);
         String _snowmanxxx = this.b(_snowman);
         ebe _snowmanxxxx = (ebe)_snowman.deserialize(_snowman, ebe.class);
         return new ebc(_snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx);
      }

      protected int a(JsonObject var1) {
         return afd.a(_snowman, "tintindex", -1);
      }

      private String b(JsonObject var1) {
         return afd.h(_snowman, "texture");
      }

      @Nullable
      private gc c(JsonObject var1) {
         String _snowman = afd.a(_snowman, "cullface", "");
         return gc.a(_snowman);
      }
   }
}
