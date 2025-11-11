import java.util.Optional;

public class afr {
   private static final Optional<Object> a = Optional.of(afx.a);

   private static boolean a(ob var0, afb var1, int var2, char var3) {
      return Character.isSurrogate(_snowman) ? _snowman.accept(_snowman, _snowman, 65533) : _snowman.accept(_snowman, _snowman, _snowman);
   }

   public static boolean a(String var0, ob var1, afb var2) {
      int _snowman = _snowman.length();

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         char _snowmanxx = _snowman.charAt(_snowmanx);
         if (Character.isHighSurrogate(_snowmanxx)) {
            if (_snowmanx + 1 >= _snowman) {
               if (!_snowman.accept(_snowmanx, _snowman, 65533)) {
                  return false;
               }
               break;
            }

            char _snowmanxxx = _snowman.charAt(_snowmanx + 1);
            if (Character.isLowSurrogate(_snowmanxxx)) {
               if (!_snowman.accept(_snowmanx, _snowman, Character.toCodePoint(_snowmanxx, _snowmanxxx))) {
                  return false;
               }

               _snowmanx++;
            } else if (!_snowman.accept(_snowmanx, _snowman, 65533)) {
               return false;
            }
         } else if (!a(_snowman, _snowman, _snowmanx, _snowmanxx)) {
            return false;
         }
      }

      return true;
   }

   public static boolean b(String var0, ob var1, afb var2) {
      int _snowman = _snowman.length();

      for (int _snowmanx = _snowman - 1; _snowmanx >= 0; _snowmanx--) {
         char _snowmanxx = _snowman.charAt(_snowmanx);
         if (Character.isLowSurrogate(_snowmanxx)) {
            if (_snowmanx - 1 < 0) {
               if (!_snowman.accept(0, _snowman, 65533)) {
                  return false;
               }
               break;
            }

            char _snowmanxxx = _snowman.charAt(_snowmanx - 1);
            if (Character.isHighSurrogate(_snowmanxxx)) {
               if (!_snowman.accept(--_snowmanx, _snowman, Character.toCodePoint(_snowmanxxx, _snowmanxx))) {
                  return false;
               }
            } else if (!_snowman.accept(_snowmanx, _snowman, 65533)) {
               return false;
            }
         } else if (!a(_snowman, _snowman, _snowmanx, _snowmanxx)) {
            return false;
         }
      }

      return true;
   }

   public static boolean c(String var0, ob var1, afb var2) {
      return a(_snowman, 0, _snowman, _snowman);
   }

   public static boolean a(String var0, int var1, ob var2, afb var3) {
      return a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public static boolean a(String var0, int var1, ob var2, ob var3, afb var4) {
      int _snowman = _snowman.length();
      ob _snowmanx = _snowman;

      for (int _snowmanxx = _snowman; _snowmanxx < _snowman; _snowmanxx++) {
         char _snowmanxxx = _snowman.charAt(_snowmanxx);
         if (_snowmanxxx == 167) {
            if (_snowmanxx + 1 >= _snowman) {
               break;
            }

            char _snowmanxxxx = _snowman.charAt(_snowmanxx + 1);
            k _snowmanxxxxx = k.a(_snowmanxxxx);
            if (_snowmanxxxxx != null) {
               _snowmanx = _snowmanxxxxx == k.v ? _snowman : _snowmanx.c(_snowmanxxxxx);
            }

            _snowmanxx++;
         } else if (Character.isHighSurrogate(_snowmanxxx)) {
            if (_snowmanxx + 1 >= _snowman) {
               if (!_snowman.accept(_snowmanxx, _snowmanx, 65533)) {
                  return false;
               }
               break;
            }

            char _snowmanxxxx = _snowman.charAt(_snowmanxx + 1);
            if (Character.isLowSurrogate(_snowmanxxxx)) {
               if (!_snowman.accept(_snowmanxx, _snowmanx, Character.toCodePoint(_snowmanxxx, _snowmanxxxx))) {
                  return false;
               }

               _snowmanxx++;
            } else if (!_snowman.accept(_snowmanxx, _snowmanx, 65533)) {
               return false;
            }
         } else if (!a(_snowmanx, _snowman, _snowmanxx, _snowmanxxx)) {
            return false;
         }
      }

      return true;
   }

   public static boolean a(nu var0, ob var1, afb var2) {
      return !_snowman.a((var1x, var2x) -> a(var2x, 0, var1x, _snowman) ? Optional.empty() : a, _snowman).isPresent();
   }

   public static String a(String var0) {
      StringBuilder _snowman = new StringBuilder();
      a(_snowman, ob.a, (var1x, var2, var3) -> {
         _snowman.appendCodePoint(var3);
         return true;
      });
      return _snowman.toString();
   }

   public static String a(nu var0) {
      StringBuilder _snowman = new StringBuilder();
      a(_snowman, ob.a, (var1x, var2, var3) -> {
         _snowman.appendCodePoint(var3);
         return true;
      });
      return _snowman.toString();
   }
}
