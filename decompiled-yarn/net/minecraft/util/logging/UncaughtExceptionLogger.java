package net.minecraft.util.logging;

import org.apache.logging.log4j.Logger;

public class UncaughtExceptionLogger implements java.lang.Thread.UncaughtExceptionHandler {
   private final Logger logger;

   public UncaughtExceptionLogger(Logger _snowman) {
      this.logger = _snowman;
   }

   @Override
   public void uncaughtException(Thread _snowman, Throwable _snowman) {
      this.logger.error("Caught previously unhandled exception :", _snowman);
   }
}
