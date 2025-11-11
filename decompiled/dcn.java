import java.util.EnumSet;

public class dcn implements gk {
   public static final dcn a = new dcn(0.0, 0.0, 0.0);
   public final double b;
   public final double c;
   public final double d;

   public static dcn a(int var0) {
      double _snowman = (double)(_snowman >> 16 & 0xFF) / 255.0;
      double _snowmanx = (double)(_snowman >> 8 & 0xFF) / 255.0;
      double _snowmanxx = (double)(_snowman & 0xFF) / 255.0;
      return new dcn(_snowman, _snowmanx, _snowmanxx);
   }

   public static dcn a(gr var0) {
      return new dcn((double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5);
   }

   public static dcn b(gr var0) {
      return new dcn((double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w());
   }

   public static dcn c(gr var0) {
      return new dcn((double)_snowman.u() + 0.5, (double)_snowman.v(), (double)_snowman.w() + 0.5);
   }

   public static dcn a(gr var0, double var1) {
      return new dcn((double)_snowman.u() + 0.5, (double)_snowman.v() + _snowman, (double)_snowman.w() + 0.5);
   }

   public dcn(double var1, double var3, double var5) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   public dcn(g var1) {
      this((double)_snowman.a(), (double)_snowman.b(), (double)_snowman.c());
   }

   public dcn a(dcn var1) {
      return new dcn(_snowman.b - this.b, _snowman.c - this.c, _snowman.d - this.d);
   }

   public dcn d() {
      double _snowman = (double)afm.a(this.b * this.b + this.c * this.c + this.d * this.d);
      return _snowman < 1.0E-4 ? a : new dcn(this.b / _snowman, this.c / _snowman, this.d / _snowman);
   }

   public double b(dcn var1) {
      return this.b * _snowman.b + this.c * _snowman.c + this.d * _snowman.d;
   }

   public dcn c(dcn var1) {
      return new dcn(this.c * _snowman.d - this.d * _snowman.c, this.d * _snowman.b - this.b * _snowman.d, this.b * _snowman.c - this.c * _snowman.b);
   }

   public dcn d(dcn var1) {
      return this.a(_snowman.b, _snowman.c, _snowman.d);
   }

   public dcn a(double var1, double var3, double var5) {
      return this.b(-_snowman, -_snowman, -_snowman);
   }

   public dcn e(dcn var1) {
      return this.b(_snowman.b, _snowman.c, _snowman.d);
   }

   public dcn b(double var1, double var3, double var5) {
      return new dcn(this.b + _snowman, this.c + _snowman, this.d + _snowman);
   }

   public boolean a(gk var1, double var2) {
      return this.c(_snowman.a(), _snowman.b(), _snowman.c()) < _snowman * _snowman;
   }

   public double f(dcn var1) {
      double _snowman = _snowman.b - this.b;
      double _snowmanx = _snowman.c - this.c;
      double _snowmanxx = _snowman.d - this.d;
      return (double)afm.a(_snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx);
   }

   public double g(dcn var1) {
      double _snowman = _snowman.b - this.b;
      double _snowmanx = _snowman.c - this.c;
      double _snowmanxx = _snowman.d - this.d;
      return _snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx;
   }

   public double c(double var1, double var3, double var5) {
      double _snowman = _snowman - this.b;
      double _snowmanx = _snowman - this.c;
      double _snowmanxx = _snowman - this.d;
      return _snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx;
   }

   public dcn a(double var1) {
      return this.d(_snowman, _snowman, _snowman);
   }

   public dcn e() {
      return this.a(-1.0);
   }

   public dcn h(dcn var1) {
      return this.d(_snowman.b, _snowman.c, _snowman.d);
   }

   public dcn d(double var1, double var3, double var5) {
      return new dcn(this.b * _snowman, this.c * _snowman, this.d * _snowman);
   }

   public double f() {
      return (double)afm.a(this.b * this.b + this.c * this.c + this.d * this.d);
   }

   public double g() {
      return this.b * this.b + this.c * this.c + this.d * this.d;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof dcn)) {
         return false;
      } else {
         dcn _snowman = (dcn)_snowman;
         if (Double.compare(_snowman.b, this.b) != 0) {
            return false;
         } else {
            return Double.compare(_snowman.c, this.c) != 0 ? false : Double.compare(_snowman.d, this.d) == 0;
         }
      }
   }

   @Override
   public int hashCode() {
      long _snowman = Double.doubleToLongBits(this.b);
      int _snowmanx = (int)(_snowman ^ _snowman >>> 32);
      _snowman = Double.doubleToLongBits(this.c);
      _snowmanx = 31 * _snowmanx + (int)(_snowman ^ _snowman >>> 32);
      _snowman = Double.doubleToLongBits(this.d);
      return 31 * _snowmanx + (int)(_snowman ^ _snowman >>> 32);
   }

   @Override
   public String toString() {
      return "(" + this.b + ", " + this.c + ", " + this.d + ")";
   }

   public dcn a(float var1) {
      float _snowman = afm.b(_snowman);
      float _snowmanx = afm.a(_snowman);
      double _snowmanxx = this.b;
      double _snowmanxxx = this.c * (double)_snowman + this.d * (double)_snowmanx;
      double _snowmanxxxx = this.d * (double)_snowman - this.c * (double)_snowmanx;
      return new dcn(_snowmanxx, _snowmanxxx, _snowmanxxxx);
   }

   public dcn b(float var1) {
      float _snowman = afm.b(_snowman);
      float _snowmanx = afm.a(_snowman);
      double _snowmanxx = this.b * (double)_snowman + this.d * (double)_snowmanx;
      double _snowmanxxx = this.c;
      double _snowmanxxxx = this.d * (double)_snowman - this.b * (double)_snowmanx;
      return new dcn(_snowmanxx, _snowmanxxx, _snowmanxxxx);
   }

   public dcn c(float var1) {
      float _snowman = afm.b(_snowman);
      float _snowmanx = afm.a(_snowman);
      double _snowmanxx = this.b * (double)_snowman + this.c * (double)_snowmanx;
      double _snowmanxxx = this.c * (double)_snowman - this.b * (double)_snowmanx;
      double _snowmanxxxx = this.d;
      return new dcn(_snowmanxx, _snowmanxxx, _snowmanxxxx);
   }

   public static dcn a(dcm var0) {
      return a(_snowman.i, _snowman.j);
   }

   public static dcn a(float var0, float var1) {
      float _snowman = afm.b(-_snowman * (float) (Math.PI / 180.0) - (float) Math.PI);
      float _snowmanx = afm.a(-_snowman * (float) (Math.PI / 180.0) - (float) Math.PI);
      float _snowmanxx = -afm.b(-_snowman * (float) (Math.PI / 180.0));
      float _snowmanxxx = afm.a(-_snowman * (float) (Math.PI / 180.0));
      return new dcn((double)(_snowmanx * _snowmanxx), (double)_snowmanxxx, (double)(_snowman * _snowmanxx));
   }

   public dcn a(EnumSet<gc.a> var1) {
      double _snowman = _snowman.contains(gc.a.a) ? (double)afm.c(this.b) : this.b;
      double _snowmanx = _snowman.contains(gc.a.b) ? (double)afm.c(this.c) : this.c;
      double _snowmanxx = _snowman.contains(gc.a.c) ? (double)afm.c(this.d) : this.d;
      return new dcn(_snowman, _snowmanx, _snowmanxx);
   }

   public double a(gc.a var1) {
      return _snowman.a(this.b, this.c, this.d);
   }

   @Override
   public final double a() {
      return this.b;
   }

   @Override
   public final double b() {
      return this.c;
   }

   @Override
   public final double c() {
      return this.d;
   }
}
