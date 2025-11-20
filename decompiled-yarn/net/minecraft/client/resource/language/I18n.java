package net.minecraft.client.resource.language;

import java.util.IllegalFormatException;
import net.minecraft.util.Language;

public class I18n {
   private static volatile Language field_25290 = Language.getInstance();

   static void method_29391(Language _snowman) {
      field_25290 = _snowman;
   }

   public static String translate(String key, Object... args) {
      String _snowman = field_25290.get(key);

      try {
         return String.format(_snowman, args);
      } catch (IllegalFormatException var4) {
         return "Format error: " + _snowman;
      }
   }

   public static boolean hasTranslation(String key) {
      return field_25290.hasTranslation(key);
   }
}
