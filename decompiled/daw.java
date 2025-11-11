import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class daw extends dai {
   private static final Logger a = LogManager.getLogger();

   private daw(dbo[] var1) {
      super(_snowman);
   }

   @Override
   public dak b() {
      return dal.f;
   }

   @Override
   public bmb a(bmb var1, cyv var2) {
      if (_snowman.a()) {
         return _snowman;
      } else {
         Optional<bpc> _snowman = _snowman.c().o().a(bot.b, new apa(_snowman), _snowman.c());
         if (_snowman.isPresent()) {
            bmb _snowmanx = _snowman.get().c();
            if (!_snowmanx.a()) {
               bmb _snowmanxx = _snowmanx.i();
               _snowmanxx.e(_snowman.E());
               return _snowmanxx;
            }
         }

         a.warn("Couldn't smelt {} because there is no smelting recipe", _snowman);
         return _snowman;
      }
   }

   public static dai.a<?> c() {
      return a(daw::new);
   }

   public static class a extends dai.c<daw> {
      public a() {
      }

      public daw a(JsonObject var1, JsonDeserializationContext var2, dbo[] var3) {
         return new daw(_snowman);
      }
   }
}
