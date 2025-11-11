public enum aou {
   a,
   b,
   c,
   d;

   private aou() {
   }

   public boolean a() {
      return this == a || this == b;
   }

   public boolean b() {
      return this == a;
   }

   public static aou a(boolean var0) {
      return _snowman ? a : b;
   }
}
