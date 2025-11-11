import java.lang.Thread.UncaughtExceptionHandler;
import org.apache.logging.log4j.Logger;

public class p implements UncaughtExceptionHandler {
   private final Logger a;

   public p(Logger var1) {
      this.a = _snowman;
   }

   @Override
   public void uncaughtException(Thread var1, Throwable var2) {
      this.a.error("Caught previously unhandled exception :");
      this.a.error(_snowman.getName(), _snowman);
   }
}
