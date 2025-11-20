package net.minecraft.util;

import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

public class ChatUtil {
   private static final Pattern PATTERN = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");

   public static String ticksToString(int ticks) {
      int _snowman = ticks / 20;
      int _snowmanx = _snowman / 60;
      _snowman %= 60;
      return _snowman < 10 ? _snowmanx + ":0" + _snowman : _snowmanx + ":" + _snowman;
   }

   public static String stripTextFormat(String text) {
      return PATTERN.matcher(text).replaceAll("");
   }

   public static boolean isEmpty(@Nullable String _snowman) {
      return StringUtils.isEmpty(_snowman);
   }
}
