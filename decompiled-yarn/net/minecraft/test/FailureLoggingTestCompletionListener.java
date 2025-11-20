package net.minecraft.test;

import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FailureLoggingTestCompletionListener implements TestCompletionListener {
   private static final Logger LOGGER = LogManager.getLogger();

   public FailureLoggingTestCompletionListener() {
   }

   @Override
   public void onTestFailed(GameTest _snowman) {
      if (_snowman.isRequired()) {
         LOGGER.error(_snowman.getStructurePath() + " failed! " + Util.getInnermostMessage(_snowman.getThrowable()));
      } else {
         LOGGER.warn("(optional) " + _snowman.getStructurePath() + " failed. " + Util.getInnermostMessage(_snowman.getThrowable()));
      }
   }
}
