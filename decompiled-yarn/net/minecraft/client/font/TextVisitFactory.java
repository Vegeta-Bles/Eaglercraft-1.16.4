package net.minecraft.client.font;

import java.util.Optional;
import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import net.minecraft.util.Unit;

public class TextVisitFactory {
   private static final Optional<Object> VISIT_TERMINATED = Optional.of(Unit.INSTANCE);

   private static boolean visitRegularCharacter(Style style, CharacterVisitor visitor, int index, char c) {
      return Character.isSurrogate(c) ? visitor.accept(index, style, 65533) : visitor.accept(index, style, c);
   }

   public static boolean visitForwards(String text, Style style, CharacterVisitor visitor) {
      int _snowman = text.length();

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         char _snowmanxx = text.charAt(_snowmanx);
         if (Character.isHighSurrogate(_snowmanxx)) {
            if (_snowmanx + 1 >= _snowman) {
               if (!visitor.accept(_snowmanx, style, 65533)) {
                  return false;
               }
               break;
            }

            char _snowmanxxx = text.charAt(_snowmanx + 1);
            if (Character.isLowSurrogate(_snowmanxxx)) {
               if (!visitor.accept(_snowmanx, style, Character.toCodePoint(_snowmanxx, _snowmanxxx))) {
                  return false;
               }

               _snowmanx++;
            } else if (!visitor.accept(_snowmanx, style, 65533)) {
               return false;
            }
         } else if (!visitRegularCharacter(style, visitor, _snowmanx, _snowmanxx)) {
            return false;
         }
      }

      return true;
   }

   public static boolean visitBackwards(String text, Style style, CharacterVisitor visitor) {
      int _snowman = text.length();

      for (int _snowmanx = _snowman - 1; _snowmanx >= 0; _snowmanx--) {
         char _snowmanxx = text.charAt(_snowmanx);
         if (Character.isLowSurrogate(_snowmanxx)) {
            if (_snowmanx - 1 < 0) {
               if (!visitor.accept(0, style, 65533)) {
                  return false;
               }
               break;
            }

            char _snowmanxxx = text.charAt(_snowmanx - 1);
            if (Character.isHighSurrogate(_snowmanxxx)) {
               if (!visitor.accept(--_snowmanx, style, Character.toCodePoint(_snowmanxxx, _snowmanxx))) {
                  return false;
               }
            } else if (!visitor.accept(_snowmanx, style, 65533)) {
               return false;
            }
         } else if (!visitRegularCharacter(style, visitor, _snowmanx, _snowmanxx)) {
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
      int _snowman = text.length();
      Style _snowmanx = startingStyle;

      for (int _snowmanxx = startIndex; _snowmanxx < _snowman; _snowmanxx++) {
         char _snowmanxxx = text.charAt(_snowmanxx);
         if (_snowmanxxx == 167) {
            if (_snowmanxx + 1 >= _snowman) {
               break;
            }

            char _snowmanxxxx = text.charAt(_snowmanxx + 1);
            Formatting _snowmanxxxxx = Formatting.byCode(_snowmanxxxx);
            if (_snowmanxxxxx != null) {
               _snowmanx = _snowmanxxxxx == Formatting.RESET ? resetStyle : _snowmanx.withExclusiveFormatting(_snowmanxxxxx);
            }

            _snowmanxx++;
         } else if (Character.isHighSurrogate(_snowmanxxx)) {
            if (_snowmanxx + 1 >= _snowman) {
               if (!visitor.accept(_snowmanxx, _snowmanx, 65533)) {
                  return false;
               }
               break;
            }

            char _snowmanxxxx = text.charAt(_snowmanxx + 1);
            if (Character.isLowSurrogate(_snowmanxxxx)) {
               if (!visitor.accept(_snowmanxx, _snowmanx, Character.toCodePoint(_snowmanxxx, _snowmanxxxx))) {
                  return false;
               }

               _snowmanxx++;
            } else if (!visitor.accept(_snowmanxx, _snowmanx, 65533)) {
               return false;
            }
         } else if (!visitRegularCharacter(_snowmanx, visitor, _snowmanxx, _snowmanxxx)) {
            return false;
         }
      }

      return true;
   }

   public static boolean visitFormatted(StringVisitable text, Style style, CharacterVisitor visitor) {
      return !text.visit((_snowmanx, _snowmanxx) -> visitFormatted(_snowmanxx, 0, _snowmanx, visitor) ? Optional.empty() : VISIT_TERMINATED, style).isPresent();
   }

   public static String validateSurrogates(String text) {
      StringBuilder _snowman = new StringBuilder();
      visitForwards(text, Style.EMPTY, (_snowmanx, _snowmanxx, _snowmanxxx) -> {
         _snowman.appendCodePoint(_snowmanxxx);
         return true;
      });
      return _snowman.toString();
   }

   public static String method_31402(StringVisitable _snowman) {
      StringBuilder _snowmanx = new StringBuilder();
      visitFormatted(_snowman, Style.EMPTY, (_snowmanxxxx, _snowmanxxx, _snowmanxx) -> {
         _snowman.appendCodePoint(_snowmanxx);
         return true;
      });
      return _snowmanx.toString();
   }
}
