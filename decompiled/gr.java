import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import java.util.stream.IntStream;
import javax.annotation.concurrent.Immutable;

@Immutable
public class gr implements Comparable<gr> {
   public static final Codec<gr> c = Codec.INT_STREAM
      .comapFlatMap(var0 -> x.a(var0, 3).map(var0x -> new gr(var0x[0], var0x[1], var0x[2])), var0 -> IntStream.of(var0.u(), var0.v(), var0.w()));
   public static final gr d = new gr(0, 0, 0);
   private int a;
   private int b;
   private int e;

   public gr(int var1, int var2, int var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.e = _snowman;
   }

   public gr(double var1, double var3, double var5) {
      this(afm.c(_snowman), afm.c(_snowman), afm.c(_snowman));
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof gr)) {
         return false;
      } else {
         gr _snowman = (gr)_snowman;
         if (this.u() != _snowman.u()) {
            return false;
         } else {
            return this.v() != _snowman.v() ? false : this.w() == _snowman.w();
         }
      }
   }

   @Override
   public int hashCode() {
      return (this.v() + this.w() * 31) * 31 + this.u();
   }

   public int i(gr var1) {
      if (this.v() == _snowman.v()) {
         return this.w() == _snowman.w() ? this.u() - _snowman.u() : this.w() - _snowman.w();
      } else {
         return this.v() - _snowman.v();
      }
   }

   public int u() {
      return this.a;
   }

   public int v() {
      return this.b;
   }

   public int w() {
      return this.e;
   }

   protected void o(int var1) {
      this.a = _snowman;
   }

   protected void p(int var1) {
      this.b = _snowman;
   }

   protected void q(int var1) {
      this.e = _snowman;
   }

   public gr o() {
      return this.m(1);
   }

   public gr m(int var1) {
      return this.b(gc.b, _snowman);
   }

   public gr n() {
      return this.l(1);
   }

   public gr l(int var1) {
      return this.b(gc.a, _snowman);
   }

   public gr b(gc var1, int var2) {
      return _snowman == 0 ? this : new gr(this.u() + _snowman.i() * _snowman, this.v() + _snowman.j() * _snowman, this.w() + _snowman.k() * _snowman);
   }

   public gr d(gr var1) {
      return new gr(this.v() * _snowman.w() - this.w() * _snowman.v(), this.w() * _snowman.u() - this.u() * _snowman.w(), this.u() * _snowman.v() - this.v() * _snowman.u());
   }

   public boolean a(gr var1, double var2) {
      return this.a((double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w(), false) < _snowman * _snowman;
   }

   public boolean a(gk var1, double var2) {
      return this.a(_snowman.a(), _snowman.b(), _snowman.c(), true) < _snowman * _snowman;
   }

   public double j(gr var1) {
      return this.a((double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w(), true);
   }

   public double a(gk var1, boolean var2) {
      return this.a(_snowman.a(), _snowman.b(), _snowman.c(), _snowman);
   }

   public double a(double var1, double var3, double var5, boolean var7) {
      double _snowman = _snowman ? 0.5 : 0.0;
      double _snowmanx = (double)this.u() + _snowman - _snowman;
      double _snowmanxx = (double)this.v() + _snowman - _snowman;
      double _snowmanxxx = (double)this.w() + _snowman - _snowman;
      return _snowmanx * _snowmanx + _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx;
   }

   public int k(gr var1) {
      float _snowman = (float)Math.abs(_snowman.u() - this.u());
      float _snowmanx = (float)Math.abs(_snowman.v() - this.v());
      float _snowmanxx = (float)Math.abs(_snowman.w() - this.w());
      return (int)(_snowman + _snowmanx + _snowmanxx);
   }

   public int a(gc.a var1) {
      return _snowman.a(this.a, this.b, this.e);
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this).add("x", this.u()).add("y", this.v()).add("z", this.w()).toString();
   }

   public String x() {
      return "" + this.u() + ", " + this.v() + ", " + this.w();
   }
}
