import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class jq implements jp.a {
   private static final Logger a = LogManager.getLogger();

   public jq() {
   }

   @Override
   public md a(String var1, md var2) {
      return _snowman.startsWith("data/minecraft/structures/") ? b(_snowman, a(_snowman)) : _snowman;
   }

   private static md a(md var0) {
      if (!_snowman.c("DataVersion", 99)) {
         _snowman.b("DataVersion", 500);
      }

      return _snowman;
   }

   private static md b(String var0, md var1) {
      ctb _snowman = new ctb();
      int _snowmanx = _snowman.h("DataVersion");
      int _snowmanxx = 2532;
      if (_snowmanx < 2532) {
         a.warn("SNBT Too old, do_ not forget to update: " + _snowmanx + " < " + 2532 + ": " + _snowman);
      }

      md _snowmanxxx = mp.a(agb.a(), aga.f, _snowman, _snowmanx);
      _snowman.b(_snowmanxxx);
      return _snowman.a(new md());
   }
}
