public class aez {
   public static class a {
      public static int a(int var0) {
         return _snowman >>> 24;
      }

      public static int b(int var0) {
         return _snowman >> 16 & 0xFF;
      }

      public static int c(int var0) {
         return _snowman >> 8 & 0xFF;
      }

      public static int d(int var0) {
         return _snowman & 0xFF;
      }

      public static int a(int var0, int var1, int var2, int var3) {
         return _snowman << 24 | _snowman << 16 | _snowman << 8 | _snowman;
      }

      public static int a(int var0, int var1) {
         return a(a(_snowman) * a(_snowman) / 255, b(_snowman) * b(_snowman) / 255, c(_snowman) * c(_snowman) / 255, d(_snowman) * d(_snowman) / 255);
      }
   }
}
