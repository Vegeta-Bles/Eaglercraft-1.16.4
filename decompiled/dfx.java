import java.util.Locale;

public enum dfx {
   a,
   b,
   c,
   d;

   private dfx() {
   }

   public static dfx a(long var0) {
      if (_snowman < 1024L) {
         return a;
      } else {
         try {
            int _snowman = (int)(Math.log((double)_snowman) / Math.log(1024.0));
            String _snowmanx = String.valueOf("KMGTPE".charAt(_snowman - 1));
            return valueOf(_snowmanx + "B");
         } catch (Exception var4) {
            return d;
         }
      }
   }

   public static double a(long var0, dfx var2) {
      return _snowman == a ? (double)_snowman : (double)_snowman / Math.pow(1024.0, (double)_snowman.ordinal());
   }

   public static String b(long var0) {
      int _snowman = 1024;
      if (_snowman < 1024L) {
         return _snowman + " B";
      } else {
         int _snowmanx = (int)(Math.log((double)_snowman) / Math.log(1024.0));
         String _snowmanxx = "KMGTPE".charAt(_snowmanx - 1) + "";
         return String.format(Locale.ROOT, "%.1f %sB", (double)_snowman / Math.pow(1024.0, (double)_snowmanx), _snowmanxx);
      }
   }

   public static String b(long var0, dfx var2) {
      return String.format("%." + (_snowman == d ? "1" : "0") + "f %s", a(_snowman, _snowman), _snowman.name());
   }
}
