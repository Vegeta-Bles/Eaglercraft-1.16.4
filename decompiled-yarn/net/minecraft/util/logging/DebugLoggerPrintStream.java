package net.minecraft.util.logging;

import java.io.OutputStream;

public class DebugLoggerPrintStream extends LoggerPrintStream {
   public DebugLoggerPrintStream(String _snowman, OutputStream _snowman) {
      super(_snowman, _snowman);
   }

   @Override
   protected void log(String message) {
      StackTraceElement[] _snowman = Thread.currentThread().getStackTrace();
      StackTraceElement _snowmanx = _snowman[Math.min(3, _snowman.length)];
      LOGGER.info("[{}]@.({}:{}): {}", this.name, _snowmanx.getFileName(), _snowmanx.getLineNumber(), message);
   }
}
