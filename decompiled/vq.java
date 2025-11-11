import java.io.OutputStream;

public class vq extends vs {
   public vq(String var1, OutputStream var2) {
      super(_snowman, _snowman);
   }

   @Override
   protected void a(String var1) {
      StackTraceElement[] _snowman = Thread.currentThread().getStackTrace();
      StackTraceElement _snowmanx = _snowman[Math.min(3, _snowman.length)];
      a.info("[{}]@.({}:{}): {}", this.b, _snowmanx.getFileName(), _snowmanx.getLineNumber(), _snowman);
   }
}
