import com.google.common.base.MoreObjects;

public class cra {
   public int a;
   public int b;
   public int c;
   public int d;
   public int e;
   public int f;

   public cra() {
   }

   public cra(int[] var1) {
      if (_snowman.length == 6) {
         this.a = _snowman[0];
         this.b = _snowman[1];
         this.c = _snowman[2];
         this.d = _snowman[3];
         this.e = _snowman[4];
         this.f = _snowman[5];
      }
   }

   public static cra a() {
      return new cra(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
   }

   public static cra b() {
      return new cra(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
   }

   public static cra a(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, gc var9) {
      switch (_snowman) {
         case c:
            return new cra(_snowman + _snowman, _snowman + _snowman, _snowman - _snowman + 1 + _snowman, _snowman + _snowman - 1 + _snowman, _snowman + _snowman - 1 + _snowman, _snowman + _snowman);
         case d:
            return new cra(_snowman + _snowman, _snowman + _snowman, _snowman + _snowman, _snowman + _snowman - 1 + _snowman, _snowman + _snowman - 1 + _snowman, _snowman + _snowman - 1 + _snowman);
         case e:
            return new cra(_snowman - _snowman + 1 + _snowman, _snowman + _snowman, _snowman + _snowman, _snowman + _snowman, _snowman + _snowman - 1 + _snowman, _snowman + _snowman - 1 + _snowman);
         case f:
            return new cra(_snowman + _snowman, _snowman + _snowman, _snowman + _snowman, _snowman + _snowman - 1 + _snowman, _snowman + _snowman - 1 + _snowman, _snowman + _snowman - 1 + _snowman);
         default:
            return new cra(_snowman + _snowman, _snowman + _snowman, _snowman + _snowman, _snowman + _snowman - 1 + _snowman, _snowman + _snowman - 1 + _snowman, _snowman + _snowman - 1 + _snowman);
      }
   }

   public static cra a(int var0, int var1, int var2, int var3, int var4, int var5) {
      return new cra(Math.min(_snowman, _snowman), Math.min(_snowman, _snowman), Math.min(_snowman, _snowman), Math.max(_snowman, _snowman), Math.max(_snowman, _snowman), Math.max(_snowman, _snowman));
   }

   public cra(cra var1) {
      this.a = _snowman.a;
      this.b = _snowman.b;
      this.c = _snowman.c;
      this.d = _snowman.d;
      this.e = _snowman.e;
      this.f = _snowman.f;
   }

   public cra(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
   }

   public cra(gr var1, gr var2) {
      this.a = Math.min(_snowman.u(), _snowman.u());
      this.b = Math.min(_snowman.v(), _snowman.v());
      this.c = Math.min(_snowman.w(), _snowman.w());
      this.d = Math.max(_snowman.u(), _snowman.u());
      this.e = Math.max(_snowman.v(), _snowman.v());
      this.f = Math.max(_snowman.w(), _snowman.w());
   }

   public cra(int var1, int var2, int var3, int var4) {
      this.a = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.f = _snowman;
      this.b = 1;
      this.e = 512;
   }

   public boolean b(cra var1) {
      return this.d >= _snowman.a && this.a <= _snowman.d && this.f >= _snowman.c && this.c <= _snowman.f && this.e >= _snowman.b && this.b <= _snowman.e;
   }

   public boolean a(int var1, int var2, int var3, int var4) {
      return this.d >= _snowman && this.a <= _snowman && this.f >= _snowman && this.c <= _snowman;
   }

   public void c(cra var1) {
      this.a = Math.min(this.a, _snowman.a);
      this.b = Math.min(this.b, _snowman.b);
      this.c = Math.min(this.c, _snowman.c);
      this.d = Math.max(this.d, _snowman.d);
      this.e = Math.max(this.e, _snowman.e);
      this.f = Math.max(this.f, _snowman.f);
   }

   public void a(int var1, int var2, int var3) {
      this.a += _snowman;
      this.b += _snowman;
      this.c += _snowman;
      this.d += _snowman;
      this.e += _snowman;
      this.f += _snowman;
   }

   public cra b(int var1, int var2, int var3) {
      return new cra(this.a + _snowman, this.b + _snowman, this.c + _snowman, this.d + _snowman, this.e + _snowman, this.f + _snowman);
   }

   public void a(gr var1) {
      this.a(_snowman.u(), _snowman.v(), _snowman.w());
   }

   public boolean b(gr var1) {
      return _snowman.u() >= this.a && _snowman.u() <= this.d && _snowman.w() >= this.c && _snowman.w() <= this.f && _snowman.v() >= this.b && _snowman.v() <= this.e;
   }

   public gr c() {
      return new gr(this.d - this.a, this.e - this.b, this.f - this.c);
   }

   public int d() {
      return this.d - this.a + 1;
   }

   public int e() {
      return this.e - this.b + 1;
   }

   public int f() {
      return this.f - this.c + 1;
   }

   public gr g() {
      return new fx(this.a + (this.d - this.a + 1) / 2, this.b + (this.e - this.b + 1) / 2, this.c + (this.f - this.c + 1) / 2);
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this)
         .add("x0", this.a)
         .add("y0", this.b)
         .add("z0", this.c)
         .add("x1", this.d)
         .add("y1", this.e)
         .add("z1", this.f)
         .toString();
   }

   public mh h() {
      return new mh(new int[]{this.a, this.b, this.c, this.d, this.e, this.f});
   }
}
