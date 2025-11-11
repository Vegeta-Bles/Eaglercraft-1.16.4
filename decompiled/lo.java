import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class lo implements lw {
   private static final Logger a = LogManager.getLogger();

   public lo() {
   }

   @Override
   public void a(lf var1) {
      if (_snowman.q()) {
         a.error(_snowman.c() + " failed! " + x.d(_snowman.n()));
      } else {
         a.warn("(optional) " + _snowman.c() + " failed. " + x.d(_snowman.n()));
      }
   }
}
