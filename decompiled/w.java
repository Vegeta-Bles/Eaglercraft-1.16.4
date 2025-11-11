import com.mojang.bridge.game.GameVersion;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;
import java.time.Duration;

public class w {
   public static final Level a = Level.DISABLED;
   public static final long b = Duration.ofMillis(300L).toNanos();
   public static boolean c = true;
   public static boolean d;
   public static final char[] e = new char[]{'/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':'};
   private static GameVersion f;

   public static boolean a(char var0) {
      return _snowman != 167 && _snowman >= ' ' && _snowman != 127;
   }

   public static String a(String var0) {
      StringBuilder _snowman = new StringBuilder();

      for (char _snowmanx : _snowman.toCharArray()) {
         if (a(_snowmanx)) {
            _snowman.append(_snowmanx);
         }
      }

      return _snowman.toString();
   }

   public static GameVersion a() {
      if (f == null) {
         f = q.a();
      }

      return f;
   }

   public static int b() {
      return 754;
   }

   static {
      ResourceLeakDetector.setLevel(a);
      CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES = false;
      CommandSyntaxException.BUILT_IN_EXCEPTIONS = new cx();
   }
}
