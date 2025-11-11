import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC10;

public class ddy {
   private static final Logger a = LogManager.getLogger();

   private static String a(int var0) {
      switch (_snowman) {
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

   static boolean a(String var0) {
      int _snowman = AL10.alGetError();
      if (_snowman != 0) {
         a.error("{}: {}", _snowman, a(_snowman));
         return true;
      } else {
         return false;
      }
   }

   private static String b(int var0) {
      switch (_snowman) {
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

   static boolean a(long var0, String var2) {
      int _snowman = ALC10.alcGetError(_snowman);
      if (_snowman != 0) {
         a.error("{}{}: {}", _snowman, _snowman, b(_snowman));
         return true;
      } else {
         return false;
      }
   }

   static int a(AudioFormat var0) {
      Encoding _snowman = _snowman.getEncoding();
      int _snowmanx = _snowman.getChannels();
      int _snowmanxx = _snowman.getSampleSizeInBits();
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

      throw new IllegalArgumentException("Invalid audio format: " + _snowman);
   }
}
