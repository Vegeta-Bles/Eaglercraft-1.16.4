import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.Random;

public class czy extends dai {
   private czy(dbo[] var1) {
      super(_snowman);
   }

   @Override
   public dak b() {
      return dal.r;
   }

   @Override
   public bmb a(bmb var1, cyv var2) {
      Float _snowman = _snowman.c(dbc.j);
      if (_snowman != null) {
         Random _snowmanx = _snowman.a();
         float _snowmanxx = 1.0F / _snowman;
         int _snowmanxxx = _snowman.E();
         int _snowmanxxxx = 0;

         for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxx; _snowmanxxxxx++) {
            if (_snowmanx.nextFloat() <= _snowmanxx) {
               _snowmanxxxx++;
            }
         }

         _snowman.e(_snowmanxxxx);
      }

      return _snowman;
   }

   public static dai.a<?> c() {
      return a(czy::new);
   }

   public static class a extends dai.c<czy> {
      public a() {
      }

      public czy a(JsonObject var1, JsonDeserializationContext var2, dbo[] var3) {
         return new czy(_snowman);
      }
   }
}
