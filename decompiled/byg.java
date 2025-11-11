public enum byg {
   a(c.a),
   b(c.B),
   c(c.z);

   private final c d;

   private byg(c var3) {
      this.d = _snowman;
   }

   public int a(int var1, int var2) {
      int _snowman = _snowman / 2;
      int _snowmanx = _snowman > _snowman ? _snowman - _snowman : _snowman;
      switch (this) {
         case c:
            return (_snowman - _snowmanx) % _snowman;
         case b:
            return (_snowman - _snowmanx + _snowman) % _snowman;
         default:
            return _snowman;
      }
   }

   public bzm a(gc var1) {
      gc.a _snowman = _snowman.n();
      return (this != b || _snowman != gc.a.c) && (this != c || _snowman != gc.a.a) ? bzm.a : bzm.c;
   }

   public gc b(gc var1) {
      if (this == c && _snowman.n() == gc.a.a) {
         return _snowman.f();
      } else {
         return this == b && _snowman.n() == gc.a.c ? _snowman.f() : _snowman;
      }
   }

   public c a() {
      return this.d;
   }
}
