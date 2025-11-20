package net.minecraft.util.logging;

import org.apache.logging.log4j.Logger;

public class UncaughtExceptionHandler implements java.lang.Thread.UncaughtExceptionHandler {
   private final Logger logger;

   public UncaughtExceptionHandler(Logger logger) {
      this.logger = logger;
   }

   @Override
   public void uncaughtException(Thread _snowman, Throwable _snowman) {
      this.logger.error("Caught previously unhandled exception :");
      this.logger.error(_snowman.getName(), _snowman);
   }
}
