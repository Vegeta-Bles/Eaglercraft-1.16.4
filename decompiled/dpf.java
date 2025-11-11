enum dpf {
   a(0, 0, 28, 32, 8),
   b(84, 0, 28, 32, 8),
   c(0, 64, 32, 28, 5),
   d(96, 64, 32, 28, 5);

   private final int e;
   private final int f;
   private final int g;
   private final int h;
   private final int i;

   private dpf(int var3, int var4, int var5, int var6, int var7) {
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
   }

   public int a() {
      return this.i;
   }

   public void a(dfm var1, dkw var2, int var3, int var4, boolean var5, int var6) {
      int _snowman = this.e;
      if (_snowman > 0) {
         _snowman += this.g;
      }

      if (_snowman == this.i - 1) {
         _snowman += this.g;
      }

      int _snowmanx = _snowman ? this.f + this.h : this.f;
      _snowman.b(_snowman, _snowman + this.a(_snowman), _snowman + this.b(_snowman), _snowman, _snowmanx, this.g, this.h);
   }

   public void a(int var1, int var2, int var3, efo var4, bmb var5) {
      int _snowman = _snowman + this.a(_snowman);
      int _snowmanx = _snowman + this.b(_snowman);
      switch (this) {
         case a:
            _snowman += 6;
            _snowmanx += 9;
            break;
         case b:
            _snowman += 6;
            _snowmanx += 6;
            break;
         case c:
            _snowman += 10;
            _snowmanx += 5;
            break;
         case d:
            _snowman += 6;
            _snowmanx += 5;
      }

      _snowman.c(_snowman, _snowman, _snowmanx);
   }

   public int a(int var1) {
      switch (this) {
         case a:
            return (this.g + 4) * _snowman;
         case b:
            return (this.g + 4) * _snowman;
         case c:
            return -this.g + 4;
         case d:
            return 248;
         default:
            throw new UnsupportedOperationException("Don't know what this tab type is!" + this);
      }
   }

   public int b(int var1) {
      switch (this) {
         case a:
            return -this.h + 4;
         case b:
            return 136;
         case c:
            return this.h * _snowman;
         case d:
            return this.h * _snowman;
         default:
            throw new UnsupportedOperationException("Don't know what this tab type is!" + this);
      }
   }

   public boolean a(int var1, int var2, int var3, double var4, double var6) {
      int _snowman = _snowman + this.a(_snowman);
      int _snowmanx = _snowman + this.b(_snowman);
      return _snowman > (double)_snowman && _snowman < (double)(_snowman + this.g) && _snowman > (double)_snowmanx && _snowman < (double)(_snowmanx + this.h);
   }
}
