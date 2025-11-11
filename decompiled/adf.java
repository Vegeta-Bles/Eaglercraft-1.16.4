import java.nio.charset.StandardCharsets;

public class adf {
   public static final char[] a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

   public static String a(byte[] var0, int var1, int var2) {
      int _snowman = _snowman - 1;
      int _snowmanx = _snowman > _snowman ? _snowman : _snowman;

      while (0 != _snowman[_snowmanx] && _snowmanx < _snowman) {
         _snowmanx++;
      }

      return new String(_snowman, _snowman, _snowmanx - _snowman, StandardCharsets.UTF_8);
   }

   public static int a(byte[] var0, int var1) {
      return b(_snowman, _snowman, _snowman.length);
   }

   public static int b(byte[] var0, int var1, int var2) {
      return 0 > _snowman - _snowman - 4 ? 0 : _snowman[_snowman + 3] << 24 | (_snowman[_snowman + 2] & 0xFF) << 16 | (_snowman[_snowman + 1] & 0xFF) << 8 | _snowman[_snowman] & 0xFF;
   }

   public static int c(byte[] var0, int var1, int var2) {
      return 0 > _snowman - _snowman - 4 ? 0 : _snowman[_snowman] << 24 | (_snowman[_snowman + 1] & 0xFF) << 16 | (_snowman[_snowman + 2] & 0xFF) << 8 | _snowman[_snowman + 3] & 0xFF;
   }

   public static String a(byte var0) {
      return "" + a[(_snowman & 240) >>> 4] + a[_snowman & 15];
   }
}
