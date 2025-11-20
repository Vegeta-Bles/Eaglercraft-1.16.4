package net.minecraft.test;

import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FailureLoggingTestCompletionListener implements TestCompletionListener {
   private static final Logger LOGGER = LogManager.getLogger();

   public FailureLoggingTestCompletionListener() {
   }

   @Override
   public void onTestFailed(GameTest arg) {
      if (arg.isRequired()) {
         LOGGER.error(arg.getStructurePath() + " failed! " + Util.getInnermostMessage(arg.getThrowable()));
      } else {
         LOGGER.warn("(optional) " + arg.getStructurePath() + " failed. " + Util.getInnermostMessage(arg.getThrowable()));
      }
   }
}
