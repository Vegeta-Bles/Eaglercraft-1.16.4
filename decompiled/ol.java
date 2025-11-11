import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ol {
   private static final Logger a = LogManager.getLogger();

   public static <T extends ni> void a(oj<T> var0, T var1, aag var2) throws vu {
      a(_snowman, _snowman, _snowman.l());
   }

   public static <T extends ni> void a(oj<T> var0, T var1, aob<?> var2) throws vu {
      if (!_snowman.bh()) {
         _snowman.execute(() -> {
            if (_snowman.a().h()) {
               _snowman.a(_snowman);
            } else {
               a.debug("Ignoring packet due to disconnection: " + _snowman);
            }
         });
         throw vu.a;
      }
   }
}
