import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class bsi implements brc, brg {
   protected final int a;
   protected final int b;
   protected final cfw[][] c;
   protected boolean d;
   protected final brx e;

   public bsi(brx var1, fx var2, fx var3) {
      this.e = _snowman;
      this.a = _snowman.u() >> 4;
      this.b = _snowman.w() >> 4;
      int _snowman = _snowman.u() >> 4;
      int _snowmanx = _snowman.w() >> 4;
      this.c = new cfw[_snowman - this.a + 1][_snowmanx - this.b + 1];
      cfz _snowmanxx = _snowman.H();
      this.d = true;

      for (int _snowmanxxx = this.a; _snowmanxxx <= _snowman; _snowmanxxx++) {
         for (int _snowmanxxxx = this.b; _snowmanxxxx <= _snowmanx; _snowmanxxxx++) {
            this.c[_snowmanxxx - this.a][_snowmanxxxx - this.b] = _snowmanxx.a(_snowmanxxx, _snowmanxxxx);
         }
      }

      for (int _snowmanxxx = _snowman.u() >> 4; _snowmanxxx <= _snowman.u() >> 4; _snowmanxxx++) {
         for (int _snowmanxxxx = _snowman.w() >> 4; _snowmanxxxx <= _snowman.w() >> 4; _snowmanxxxx++) {
            cfw _snowmanxxxxx = this.c[_snowmanxxx - this.a][_snowmanxxxx - this.b];
            if (_snowmanxxxxx != null && !_snowmanxxxxx.a(_snowman.v(), _snowman.v())) {
               this.d = false;
               return;
            }
         }
      }
   }

   private cfw d(fx var1) {
      return this.a(_snowman.u() >> 4, _snowman.w() >> 4);
   }

   private cfw a(int var1, int var2) {
      int _snowman = _snowman - this.a;
      int _snowmanx = _snowman - this.b;
      if (_snowman >= 0 && _snowman < this.c.length && _snowmanx >= 0 && _snowmanx < this.c[_snowman].length) {
         cfw _snowmanxx = this.c[_snowman][_snowmanx];
         return (cfw)(_snowmanxx != null ? _snowmanxx : new cgc(this.e, new brd(_snowman, _snowman)));
      } else {
         return new cgc(this.e, new brd(_snowman, _snowman));
      }
   }

   @Override
   public cfu f() {
      return this.e.f();
   }

   @Override
   public brc c(int var1, int var2) {
      return this.a(_snowman, _snowman);
   }

   @Nullable
   @Override
   public ccj c(fx var1) {
      cfw _snowman = this.d(_snowman);
      return _snowman.c(_snowman);
   }

   @Override
   public ceh d_(fx var1) {
      if (brx.m(_snowman)) {
         return bup.a.n();
      } else {
         cfw _snowman = this.d(_snowman);
         return _snowman.d_(_snowman);
      }
   }

   @Override
   public Stream<ddh> c(@Nullable aqa var1, dci var2, Predicate<aqa> var3) {
      return Stream.empty();
   }

   @Override
   public Stream<ddh> d(@Nullable aqa var1, dci var2, Predicate<aqa> var3) {
      return this.b(_snowman, _snowman);
   }

   @Override
   public cux b(fx var1) {
      if (brx.m(_snowman)) {
         return cuy.a.h();
      } else {
         cfw _snowman = this.d(_snowman);
         return _snowman.b(_snowman);
      }
   }
}
