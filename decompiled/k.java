import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public enum k {
   a("BLACK", '0', 0, 0),
   b("DARK_BLUE", '1', 1, 170),
   c("DARK_GREEN", '2', 2, 43520),
   d("DARK_AQUA", '3', 3, 43690),
   e("DARK_RED", '4', 4, 11141120),
   f("DARK_PURPLE", '5', 5, 11141290),
   g("GOLD", '6', 6, 16755200),
   h("GRAY", '7', 7, 11184810),
   i("DARK_GRAY", '8', 8, 5592405),
   j("BLUE", '9', 9, 5592575),
   k("GREEN", 'a', 10, 5635925),
   l("AQUA", 'b', 11, 5636095),
   m("RED", 'c', 12, 16733525),
   n("LIGHT_PURPLE", 'd', 13, 16733695),
   o("YELLOW", 'e', 14, 16777045),
   p("WHITE", 'f', 15, 16777215),
   q("OBFUSCATED", 'k', true),
   r("BOLD", 'l', true),
   s("STRIKETHROUGH", 'm', true),
   t("UNDERLINE", 'n', true),
   u("ITALIC", 'o', true),
   v("RESET", 'r', -1, null);

   private static final Map<String, k> w = Arrays.stream(values()).collect(Collectors.toMap(var0 -> c(var0.y), var0 -> (k)var0));
   private static final Pattern x = Pattern.compile("(?i)§[0-9A-FK-OR]");
   private final String y;
   private final char z;
   private final boolean A;
   private final String B;
   private final int C;
   @Nullable
   private final Integer D;

   private static String c(String var0) {
      return _snowman.toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");
   }

   private k(String var3, char var4, int var5, Integer var6) {
      this(_snowman, _snowman, false, _snowman, _snowman);
   }

   private k(String var3, char var4, boolean var5) {
      this(_snowman, _snowman, _snowman, -1, null);
   }

   private k(String var3, char var4, boolean var5, int var6, Integer var7) {
      this.y = _snowman;
      this.z = _snowman;
      this.A = _snowman;
      this.C = _snowman;
      this.D = _snowman;
      this.B = "§" + _snowman;
   }

   public int b() {
      return this.C;
   }

   public boolean c() {
      return this.A;
   }

   public boolean d() {
      return !this.A && this != v;
   }

   @Nullable
   public Integer e() {
      return this.D;
   }

   public String f() {
      return this.name().toLowerCase(Locale.ROOT);
   }

   @Override
   public String toString() {
      return this.B;
   }

   @Nullable
   public static String a(@Nullable String var0) {
      return _snowman == null ? null : x.matcher(_snowman).replaceAll("");
   }

   @Nullable
   public static k b(@Nullable String var0) {
      return _snowman == null ? null : w.get(c(_snowman));
   }

   @Nullable
   public static k a(int var0) {
      if (_snowman < 0) {
         return v;
      } else {
         for (k _snowman : values()) {
            if (_snowman.b() == _snowman) {
               return _snowman;
            }
         }

         return null;
      }
   }

   @Nullable
   public static k a(char var0) {
      char _snowman = Character.toString(_snowman).toLowerCase(Locale.ROOT).charAt(0);

      for (k _snowmanx : values()) {
         if (_snowmanx.z == _snowman) {
            return _snowmanx;
         }
      }

      return null;
   }

   public static Collection<String> a(boolean var0, boolean var1) {
      List<String> _snowman = Lists.newArrayList();

      for (k _snowmanx : values()) {
         if ((!_snowmanx.d() || _snowman) && (!_snowmanx.c() || _snowman)) {
            _snowman.add(_snowmanx.f());
         }
      }

      return _snowman;
   }
}
