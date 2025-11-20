package net.minecraft.client.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC10;

public class AlUtil {
   private static final Logger LOGGER = LogManager.getLogger();

   private static String getErrorMessage(int errorCode) {
      switch (errorCode) {
         case 40961:
            return "Invalid name parameter.";
         case 40962:
            return "Invalid enumerated parameter value.";
         case 40963:
            return "Invalid parameter parameter value.";
         case 40964:
            return "Invalid operation.";
         case 40965:
            return "Unable to allocate memory.";
         default:
            return "An unrecognized error occurred.";
      }
   }

   static boolean checkErrors(String sectionName) {
      int _snowman = AL10.alGetError();
      if (_snowman != 0) {
         LOGGER.error("{}: {}", sectionName, getErrorMessage(_snowman));
         return true;
      } else {
         return false;
      }
   }

   private static String getAlcErrorMessage(int errorCode) {
      switch (errorCode) {
         case 40961:
            return "Invalid device.";
         case 40962:
            return "Invalid context.";
         case 40963:
            return "Illegal enum.";
         case 40964:
            return "Invalid value.";
         case 40965:
            return "Unable to allocate memory.";
         default:
            return "An unrecognized error occurred.";
      }
   }

   static boolean checkAlcErrors(long deviceHandle, String sectionName) {
      int _snowman = ALC10.alcGetError(deviceHandle);
      if (_snowman != 0) {
         LOGGER.error("{}{}: {}", sectionName, deviceHandle, getAlcErrorMessage(_snowman));
         return true;
      } else {
         return false;
      }
   }

   static int getFormatId(AudioFormat format) {
      Encoding _snowman = format.getEncoding();
      int _snowmanx = format.getChannels();
      int _snowmanxx = format.getSampleSizeInBits();
      if (_snowman.equals(Encoding.PCM_UNSIGNED) || _snowman.equals(Encoding.PCM_SIGNED)) {
         if (_snowmanx == 1) {
            if (_snowmanxx == 8) {
               return 4352;
            }

            if (_snowmanxx == 16) {
               return 4353;
            }
         } else if (_snowmanx == 2) {
            if (_snowmanxx == 8) {
               return 4354;
            }

            if (_snowmanxx == 16) {
               return 4355;
            }
         }
      }

      throw new IllegalArgumentException("Invalid audio format: " + format);
   }
}
