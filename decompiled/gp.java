import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class gp extends gr {
   private gp(int var1, int var2, int var3) {
      super(_snowman, _snowman, _snowman);
   }

   public static gp a(int var0, int var1, int var2) {
      return new gp(_snowman, _snowman, _snowman);
   }

   public static gp a(fx var0) {
      return new gp(a(_snowman.u()), a(_snowman.v()), a(_snowman.w()));
   }

   public static gp a(brd var0, int var1) {
      return new gp(_snowman.b, _snowman, _snowman.c);
   }

   public static gp a(aqa var0) {
      return new gp(a(afm.c(_snowman.cD())), a(afm.c(_snowman.cE())), a(afm.c(_snowman.cH())));
   }

   public static gp a(long var0) {
      return new gp(b(_snowman), c(_snowman), d(_snowman));
   }

   public static long a(long var0, gc var2) {
      return a(_snowman, _snowman.i(), _snowman.j(), _snowman.k());
   }

   public static long a(long var0, int var2, int var3, int var4) {
      return b(b(_snowman) + _snowman, c(_snowman) + _snowman, d(_snowman) + _snowman);
   }

   public static int a(int var0) {
      return _snowman >> 4;
   }

   public static int b(int var0) {
      return _snowman & 15;
   }

   public static short b(fx var0) {
      int _snowman = b(_snowman.u());
      int _snowmanx = b(_snowman.v());
      int _snowmanxx = b(_snowman.w());
      return (short)(_snowman << 8 | _snowmanxx << 4 | _snowmanx << 0);
   }

   public static int a(short var0) {
      return _snowman >>> 8 & 15;
   }

   public static int b(short var0) {
      return _snowman >>> 0 & 15;
   }

   public static int c(short var0) {
      return _snowman >>> 4 & 15;
   }

   public int d(short var1) {
      return this.d() + a(_snowman);
   }

   public int e(short var1) {
      return this.e() + b(_snowman);
   }

   public int f(short var1) {
      return this.f() + c(_snowman);
   }

   public fx g(short var1) {
      return new fx(this.d(_snowman), this.e(_snowman), this.f(_snowman));
   }

   public static int c(int var0) {
      return _snowman << 4;
   }

   public static int b(long var0) {
      return (int)(_snowman << 0 >> 42);
   }

   public static int c(long var0) {
      return (int)(_snowman << 44 >> 44);
   }

   public static int d(long var0) {
      return (int)(_snowman << 22 >> 42);
   }

   public int a() {
      return this.u();
   }

   public int b() {
      return this.v();
   }

   public int c() {
      return this.w();
   }

   public int d() {
      return this.a() << 4;
   }

   public int e() {
      return this.b() << 4;
   }

   public int f() {
      return this.c() << 4;
   }

   public int g() {
      return (this.a() << 4) + 15;
   }

   public int h() {
      return (this.b() << 4) + 15;
   }

   public int i() {
      return (this.c() << 4) + 15;
   }

   public static long e(long var0) {
      return b(a(fx.b(_snowman)), a(fx.c(_snowman)), a(fx.d(_snowman)));
   }

   public static long f(long var0) {
      return _snowman & -1048576L;
   }

   public fx p() {
      return new fx(c(this.a()), c(this.b()), c(this.c()));
   }

   public fx q() {
      int _snowman = 8;
      return this.p().b(8, 8, 8);
   }

   public brd r() {
      return new brd(this.a(), this.c());
   }

   public static long b(int var0, int var1, int var2) {
      long _snowman = 0L;
      _snowman |= ((long)_snowman & 4194303L) << 42;
      _snowman |= ((long)_snowman & 1048575L) << 0;
      return _snowman | ((long)_snowman & 4194303L) << 20;
   }

   public long s() {
      return b(this.a(), this.b(), this.c());
   }

   public Stream<fx> t() {
      return fx.a(this.d(), this.e(), this.f(), this.g(), this.h(), this.i());
   }

   public static Stream<gp> a(gp var0, int var1) {
      int _snowman = _snowman.a();
      int _snowmanx = _snowman.b();
      int _snowmanxx = _snowman.c();
      return a(_snowman - _snowman, _snowmanx - _snowman, _snowmanxx - _snowman, _snowman + _snowman, _snowmanx + _snowman, _snowmanxx + _snowman);
   }

   public static Stream<gp> b(brd var0, int var1) {
      int _snowman = _snowman.b;
      int _snowmanx = _snowman.c;
      return a(_snowman - _snowman, 0, _snowmanx - _snowman, _snowman + _snowman, 15, _snowmanx + _snowman);
   }

   public static Stream<gp> a(final int var0, final int var1, final int var2, final int var3, final int var4, final int var5) {
      return StreamSupport.stream(new AbstractSpliterator<gp>((long)((_snowman - _snowman + 1) * (_snowman - _snowman + 1) * (_snowman - _snowman + 1)), 64) {
         final ga a = new ga(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);

         @Override
         public boolean tryAdvance(Consumer<? super gp> var1x) {
            if (this.a.a()) {
               _snowman.accept(new gp(this.a.b(), this.a.c(), this.a.d()));
               return true;
            } else {
               return false;
            }
         }
      }, false);
   }
}
