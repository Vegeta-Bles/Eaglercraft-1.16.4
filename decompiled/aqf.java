public enum aqf {
   a(aqf.a.a, 0, 0, "mainhand"),
   b(aqf.a.a, 1, 5, "offhand"),
   c(aqf.a.b, 0, 1, "feet"),
   d(aqf.a.b, 1, 2, "legs"),
   e(aqf.a.b, 2, 3, "chest"),
   f(aqf.a.b, 3, 4, "head");

   private final aqf.a g;
   private final int h;
   private final int i;
   private final String j;

   private aqf(aqf.a var3, int var4, int var5, String var6) {
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.j = _snowman;
   }

   public aqf.a a() {
      return this.g;
   }

   public int b() {
      return this.h;
   }

   public int c() {
      return this.i;
   }

   public String d() {
      return this.j;
   }

   public static aqf a(String var0) {
      for (aqf _snowman : values()) {
         if (_snowman.d().equals(_snowman)) {
            return _snowman;
         }
      }

      throw new IllegalArgumentException("Invalid slot '" + _snowman + "'");
   }

   public static aqf a(aqf.a var0, int var1) {
      for (aqf _snowman : values()) {
         if (_snowman.a() == _snowman && _snowman.b() == _snowman) {
            return _snowman;
         }
      }

      throw new IllegalArgumentException("Invalid slot '" + _snowman + "': " + _snowman);
   }

   public static enum a {
      a,
      b;

      private a() {
      }
   }
}
