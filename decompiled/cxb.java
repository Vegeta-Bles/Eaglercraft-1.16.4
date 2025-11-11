public class cxb {
   public final int a;
   public final int b;
   public final int c;
   private final int m;
   public int d = -1;
   public float e;
   public float f;
   public float g;
   public cxb h;
   public boolean i;
   public float j;
   public float k;
   public cwz l = cwz.a;

   public cxb(int var1, int var2, int var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.m = b(_snowman, _snowman, _snowman);
   }

   public cxb a(int var1, int var2, int var3) {
      cxb _snowman = new cxb(_snowman, _snowman, _snowman);
      _snowman.d = this.d;
      _snowman.e = this.e;
      _snowman.f = this.f;
      _snowman.g = this.g;
      _snowman.h = this.h;
      _snowman.i = this.i;
      _snowman.j = this.j;
      _snowman.k = this.k;
      _snowman.l = this.l;
      return _snowman;
   }

   public static int b(int var0, int var1, int var2) {
      return _snowman & 0xFF | (_snowman & 32767) << 8 | (_snowman & 32767) << 24 | (_snowman < 0 ? Integer.MIN_VALUE : 0) | (_snowman < 0 ? 32768 : 0);
   }

   public float a(cxb var1) {
      float _snowman = (float)(_snowman.a - this.a);
      float _snowmanx = (float)(_snowman.b - this.b);
      float _snowmanxx = (float)(_snowman.c - this.c);
      return afm.c(_snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx);
   }

   public float b(cxb var1) {
      float _snowman = (float)(_snowman.a - this.a);
      float _snowmanx = (float)(_snowman.b - this.b);
      float _snowmanxx = (float)(_snowman.c - this.c);
      return _snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx;
   }

   public float c(cxb var1) {
      float _snowman = (float)Math.abs(_snowman.a - this.a);
      float _snowmanx = (float)Math.abs(_snowman.b - this.b);
      float _snowmanxx = (float)Math.abs(_snowman.c - this.c);
      return _snowman + _snowmanx + _snowmanxx;
   }

   public float c(fx var1) {
      float _snowman = (float)Math.abs(_snowman.u() - this.a);
      float _snowmanx = (float)Math.abs(_snowman.v() - this.b);
      float _snowmanxx = (float)Math.abs(_snowman.w() - this.c);
      return _snowman + _snowmanx + _snowmanxx;
   }

   public fx a() {
      return new fx(this.a, this.b, this.c);
   }

   @Override
   public boolean equals(Object var1) {
      if (!(_snowman instanceof cxb)) {
         return false;
      } else {
         cxb _snowman = (cxb)_snowman;
         return this.m == _snowman.m && this.a == _snowman.a && this.b == _snowman.b && this.c == _snowman.c;
      }
   }

   @Override
   public int hashCode() {
      return this.m;
   }

   public boolean c() {
      return this.d >= 0;
   }

   @Override
   public String toString() {
      return "Node{x=" + this.a + ", y=" + this.b + ", z=" + this.c + '}';
   }

   public static cxb b(nf var0) {
      cxb _snowman = new cxb(_snowman.readInt(), _snowman.readInt(), _snowman.readInt());
      _snowman.j = _snowman.readFloat();
      _snowman.k = _snowman.readFloat();
      _snowman.i = _snowman.readBoolean();
      _snowman.l = cwz.values()[_snowman.readInt()];
      _snowman.g = _snowman.readFloat();
      return _snowman;
   }
}
