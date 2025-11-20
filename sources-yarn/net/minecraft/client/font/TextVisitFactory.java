package net.minecraft.client.font;

import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import net.minecraft.util.Unit;

@Environment(EnvType.CLIENT)
public class TextVisitFactory {
   private static final Optional<Object> VISIT_TERMINATED = Optional.of(Unit.INSTANCE);

   private static boolean visitRegularCharacter(Style style, CharacterVisitor visitor, int index, char c) {
      return Character.isSurrogate(c) ? visitor.accept(index, style, 65533) : visitor.accept(index, style, c);
   }

   public static boolean visitForwards(String text, Style style, CharacterVisitor visitor) {
      int i = text.length();

      for (int j = 0; j < i; j++) {
         char c = text.charAt(j);
         if (Character.isHighSurrogate(c)) {
            if (j + 1 >= i) {
               if (!visitor.accept(j, style, 65533)) {
                  return false;
               }
               break;
            }

            char d = text.charAt(j + 1);
            if (Character.isLowSurrogate(d)) {
               if (!visitor.accept(j, style, Character.toCodePoint(c, d))) {
                  return false;
               }

               j++;
            } else if (!visitor.accept(j, style, 65533)) {
               return false;
            }
         } else if (!visitRegularCharacter(style, visitor, j, c)) {
            return false;
         }
      }

      return true;
   }

   public static boolean visitBackwards(String text, Style style, CharacterVisitor visitor) {
      int i = text.length();

      for (int j = i - 1; j >= 0; j--) {
         char c = text.charAt(j);
         if (Character.isLowSurrogate(c)) {
            if (j - 1 < 0) {
               if (!visitor.accept(0, style, 65533)) {
                  return false;
               }
               break;
            }

            char d = text.charAt(j - 1);
            if (Character.isHighSurrogate(d)) {
               if (!visitor.accept(--j, style, Character.toCodePoint(d, c))) {
                  return false;
               }
            } else if (!visitor.accept(j, style, 65533)) {
               return false;
            }
         } else if (!visitRegularCharacter(style, visitor, j, c)) {
            return false;
         }
      }

      return true;
   }

   public static boolean visitFormatted(String text, Style style, CharacterVisitor visitor) {
      return visitFormatted(text, 0, style, visitor);
   }

   public static boolean visitFormatted(String text, int startIndex, Style style, CharacterVisitor visitor) {
      return visitFormatted(text, startIndex, style, style, visitor);
   }

   public static boolean visitFormatted(String text, int startIndex, Style startingStyle, Style resetStyle, CharacterVisitor visitor) {
      int j = text.length();
      Style lv = startingStyle;

      for (int k = startIndex; k < j; k++) {
         char c = text.charAt(k);
         if (c == 167) {
            if (k + 1 >= j) {
               break;
            }

            char d = text.charAt(k + 1);
            Formatting lv2 = Formatting.byCode(d);
            if (lv2 != null) {
               lv = lv2 == Formatting.RESET ? resetStyle : lv.withExclusiveFormatting(lv2);
            }

            k++;
         } else if (Character.isHighSurrogate(c)) {
            if (k + 1 >= j) {
               if (!visitor.accept(k, lv, 65533)) {
                  return false;
               }
               break;
            }

            char e = text.charAt(k + 1);
            if (Character.isLowSurrogate(e)) {
               if (!visitor.accept(k, lv, Character.toCodePoint(c, e))) {
                  return false;
               }

               k++;
            } else if (!visitor.accept(k, lv, 65533)) {
               return false;
            }
         } else if (!visitRegularCharacter(lv, visitor, k, c)) {
            return false;
         }
      }

      return true;
   }

   public static boolean visitFormatted(StringVisitable text, Style style, CharacterVisitor visitor) {
      return !text.visit((arg2, string) -> visitFormatted(string, 0, arg2, visitor) ? Optional.empty() : VISIT_TERMINATED, style).isPresent();
   }

   public static String validateSurrogates(String text) {
      StringBuilder stringBuilder = new StringBuilder();
      visitForwards(text, Style.EMPTY, (i, arg, j) -> {
         stringBuilder.appendCodePoint(j);
         return true;
      });
      return stringBuilder.toString();
   }

   public static String method_31402(StringVisitable arg) {
      StringBuilder stringBuilder = new StringBuilder();
      visitFormatted(arg, Style.EMPTY, (i, argx, j) -> {
         stringBuilder.appendCodePoint(j);
         return true;
      });
      return stringBuilder.toString();
   }
}
