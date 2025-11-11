public class cwy {
   private cxb[] a = new cxb[128];
   private int b;

   public cwy() {
   }

   public cxb a(cxb var1) {
      if (_snowman.d >= 0) {
         throw new IllegalStateException("OW KNOWS!");
      } else {
         if (this.b == this.a.length) {
            cxb[] _snowman = new cxb[this.b << 1];
            System.arraycopy(this.a, 0, _snowman, 0, this.b);
            this.a = _snowman;
         }

         this.a[this.b] = _snowman;
         _snowman.d = this.b;
         this.a(this.b++);
         return _snowman;
      }
   }

   public void a() {
      this.b = 0;
   }

   public cxb c() {
      cxb _snowman = this.a[0];
      this.a[0] = this.a[--this.b];
      this.a[this.b] = null;
      if (this.b > 0) {
         this.b(0);
      }

      _snowman.d = -1;
      return _snowman;
   }

   public void a(cxb var1, float var2) {
      float _snowman = _snowman.g;
      _snowman.g = _snowman;
      if (_snowman < _snowman) {
         this.a(_snowman.d);
      } else {
         this.b(_snowman.d);
      }
   }

   private void a(int var1) {
      cxb _snowman = this.a[_snowman];
      float _snowmanx = _snowman.g;

      while (_snowman > 0) {
         int _snowmanxx = _snowman - 1 >> 1;
         cxb _snowmanxxx = this.a[_snowmanxx];
         if (!(_snowmanx < _snowmanxxx.g)) {
            break;
         }

         this.a[_snowman] = _snowmanxxx;
         _snowmanxxx.d = _snowman;
         _snowman = _snowmanxx;
      }

      this.a[_snowman] = _snowman;
      _snowman.d = _snowman;
   }

   private void b(int var1) {
      cxb _snowman = this.a[_snowman];
      float _snowmanx = _snowman.g;

      while (true) {
         int _snowmanxx = 1 + (_snowman << 1);
         int _snowmanxxx = _snowmanxx + 1;
         if (_snowmanxx >= this.b) {
            break;
         }

         cxb _snowmanxxxx = this.a[_snowmanxx];
         float _snowmanxxxxx = _snowmanxxxx.g;
         cxb _snowmanxxxxxx;
         float _snowmanxxxxxxx;
         if (_snowmanxxx >= this.b) {
            _snowmanxxxxxx = null;
            _snowmanxxxxxxx = Float.POSITIVE_INFINITY;
         } else {
            _snowmanxxxxxx = this.a[_snowmanxxx];
            _snowmanxxxxxxx = _snowmanxxxxxx.g;
         }

         if (_snowmanxxxxx < _snowmanxxxxxxx) {
            if (!(_snowmanxxxxx < _snowmanx)) {
               break;
            }

            this.a[_snowman] = _snowmanxxxx;
            _snowmanxxxx.d = _snowman;
            _snowman = _snowmanxx;
         } else {
            if (!(_snowmanxxxxxxx < _snowmanx)) {
               break;
            }

            this.a[_snowman] = _snowmanxxxxxx;
            _snowmanxxxxxx.d = _snowman;
            _snowman = _snowmanxxx;
         }
      }

      this.a[_snowman] = _snowman;
      _snowman.d = _snowman;
   }

   public boolean e() {
      return this.b == 0;
   }
}
