import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

public class aft {
   private static final Pattern a = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");

   public static String a(int var0) {
      int _snowman = _snowman / 20;
      int _snowmanx = _snowman / 60;
      _snowman %= 60;
      return _snowman < 10 ? _snowmanx + ":0" + _snowman : _snowmanx + ":" + _snowman;
   }

   public static String a(String var0) {
      return a.matcher(_snowman).replaceAll("");
   }

   public static boolean b(@Nullable String var0) {
      return StringUtils.isEmpty(_snowman);
   }
}
