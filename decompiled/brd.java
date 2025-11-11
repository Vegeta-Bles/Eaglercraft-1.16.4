import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;

public class brd {
   public static final long a = a(1875016, 1875016);
   public final int b;
   public final int c;

   public brd(int var1, int var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   public brd(fx var1) {
      this.b = _snowman.u() >> 4;
      this.c = _snowman.w() >> 4;
   }

   public brd(long var1) {
      this.b = (int)_snowman;
      this.c = (int)(_snowman >> 32);
   }

   public long a() {
      return a(this.b, this.c);
   }

   public static long a(int var0, int var1) {
      return (long)_snowman & 4294967295L | ((long)_snowman & 4294967295L) << 32;
   }

   public static int a(long var0) {
      return (int)(_snowman & 4294967295L);
   }

   public static int b(long var0) {
      return (int)(_snowman >>> 32 & 4294967295L);
   }

   @Override
   public int hashCode() {
      int _snowman = 1664525 * this.b + 1013904223;
      int _snowmanx = 1664525 * (this.c ^ -559038737) + 1013904223;
      return _snowman ^ _snowmanx;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof brd)) {
         return false;
      } else {
         brd _snowman = (brd)_snowman;
         return this.b == _snowman.b && this.c == _snowman.c;
      }
   }

   public int d() {
      return this.b << 4;
   }

   public int e() {
      return this.c << 4;
   }

   public int f() {
      return (this.b << 4) + 15;
   }

   public int g() {
      return (this.c << 4) + 15;
   }

   public int h() {
      return this.b >> 5;
   }

   public int i() {
      return this.c >> 5;
   }

   public int j() {
      return this.b & 31;
   }

   public int k() {
      return this.c & 31;
   }

   @Override
   public String toString() {
      return "[" + this.b + ", " + this.c + "]";
   }

   public fx l() {
      return new fx(this.d(), 0, this.e());
   }

   public int a(brd var1) {
      return Math.max(Math.abs(this.b - _snowman.b), Math.abs(this.c - _snowman.c));
   }

   public static Stream<brd> a(brd var0, int var1) {
      return a(new brd(_snowman.b - _snowman, _snowman.c - _snowman), new brd(_snowman.b + _snowman, _snowman.c + _snowman));
   }

   public static Stream<brd> a(final brd var0, final brd var1) {
      int _snowman = Math.abs(_snowman.b - _snowman.b) + 1;
      int _snowmanx = Math.abs(_snowman.c - _snowman.c) + 1;
      final int _snowmanxx = _snowman.b < _snowman.b ? 1 : -1;
      final int _snowmanxxx = _snowman.c < _snowman.c ? 1 : -1;
      return StreamSupport.stream(new AbstractSpliterator<brd>((long)(_snowman * _snowmanx), 64) {
         @Nullable
         private brd e;

         @Override
         public boolean tryAdvance(Consumer<? super brd> var1x) {
            if (this.e == null) {
               this.e = _snowman;
            } else {
               int _snowman = this.e.b;
               int _snowmanx = this.e.c;
               if (_snowman == _snowman.b) {
                  if (_snowmanx == _snowman.c) {
                     return false;
                  }

                  this.e = new brd(_snowman.b, _snowmanx + _snowman);
               } else {
                  this.e = new brd(_snowman + _snowman, _snowmanx);
               }
            }

            _snowman.accept(this.e);
            return true;
         }
      }, false);
   }
}
