public enum bru {
   a(-1, ""),
   b(0, "survival"),
   c(1, "creative"),
   d(2, "adventure"),
   e(3, "spectator");

   private final int f;
   private final String g;

   private bru(int var3, String var4) {
      this.f = _snowman;
      this.g = _snowman;
   }

   public int a() {
      return this.f;
   }

   public String b() {
      return this.g;
   }

   public nr c() {
      return new of("gameMode." + this.g);
   }

   public void a(bft var1) {
      if (this == c) {
         _snowman.c = true;
         _snowman.d = true;
         _snowman.a = true;
      } else if (this == e) {
         _snowman.c = true;
         _snowman.d = false;
         _snowman.a = true;
         _snowman.b = true;
      } else {
         _snowman.c = false;
         _snowman.d = false;
         _snowman.a = false;
         _snowman.b = false;
      }

      _snowman.e = !this.d();
   }

   public boolean d() {
      return this == d || this == e;
   }

   public boolean e() {
      return this == c;
   }

   public boolean f() {
      return this == b || this == d;
   }

   public static bru a(int var0) {
      return a(_snowman, b);
   }

   public static bru a(int var0, bru var1) {
      for (bru _snowman : values()) {
         if (_snowman.f == _snowman) {
            return _snowman;
         }
      }

      return _snowman;
   }

   public static bru a(String var0) {
      return a(_snowman, b);
   }

   public static bru a(String var0, bru var1) {
      for (bru _snowman : values()) {
         if (_snowman.g.equals(_snowman)) {
            return _snowman;
         }
      }

      return _snowman;
   }
}
