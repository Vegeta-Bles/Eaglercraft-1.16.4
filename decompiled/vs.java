import java.io.OutputStream;
import java.io.PrintStream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class vs extends PrintStream {
   protected static final Logger a = LogManager.getLogger();
   protected final String b;

   public vs(String var1, OutputStream var2) {
      super(_snowman);
      this.b = _snowman;
   }

   @Override
   public void println(@Nullable String var1) {
      this.a(_snowman);
   }

   @Override
   public void println(Object var1) {
      this.a(String.valueOf(_snowman));
   }

   protected void a(@Nullable String var1) {
      a.info("[{}]: {}", this.b, _snowman);
   }
}
