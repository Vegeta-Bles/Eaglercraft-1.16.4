import com.google.common.collect.AbstractIterator;
import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.concurrent.Immutable;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Immutable
public class fx extends gr {
   public static final Codec<fx> a = Codec.INT_STREAM
      .comapFlatMap(var0 -> x.a(var0, 3).map(var0x -> new fx(var0x[0], var0x[1], var0x[2])), var0 -> IntStream.of(var0.u(), var0.v(), var0.w()))
      .stable();
   private static final Logger e = LogManager.getLogger();
   public static final fx b = new fx(0, 0, 0);
   private static final int f = 1 + afm.f(afm.c(30000000));
   private static final int g = f;
   private static final int h = 64 - f - g;
   private static final long i = (1L << f) - 1L;
   private static final long j = (1L << h) - 1L;
   private static final long k = (1L << g) - 1L;
   private static final int l = h;
   private static final int m = h + g;

   public fx(int var1, int var2, int var3) {
      super(_snowman, _snowman, _snowman);
   }

   public fx(double var1, double var3, double var5) {
      super(_snowman, _snowman, _snowman);
   }

   public fx(dcn var1) {
      this(_snowman.b, _snowman.c, _snowman.d);
   }

   public fx(gk var1) {
      this(_snowman.a(), _snowman.b(), _snowman.c());
   }

   public fx(gr var1) {
      this(_snowman.u(), _snowman.v(), _snowman.w());
   }

   public static long a(long var0, gc var2) {
      return a(_snowman, _snowman.i(), _snowman.j(), _snowman.k());
   }

   public static long a(long var0, int var2, int var3, int var4) {
      return a(b(_snowman) + _snowman, c(_snowman) + _snowman, d(_snowman) + _snowman);
   }

   public static int b(long var0) {
      return (int)(_snowman << 64 - m - f >> 64 - f);
   }

   public static int c(long var0) {
      return (int)(_snowman << 64 - h >> 64 - h);
   }

   public static int d(long var0) {
      return (int)(_snowman << 64 - l - g >> 64 - g);
   }

   public static fx e(long var0) {
      return new fx(b(_snowman), c(_snowman), d(_snowman));
   }

   public long a() {
      return a(this.u(), this.v(), this.w());
   }

   public static long a(int var0, int var1, int var2) {
      long _snowman = 0L;
      _snowman |= ((long)_snowman & i) << m;
      _snowman |= ((long)_snowman & j) << 0;
      return _snowman | ((long)_snowman & k) << l;
   }

   public static long f(long var0) {
      return _snowman & -16L;
   }

   public fx a(double var1, double var3, double var5) {
      return _snowman == 0.0 && _snowman == 0.0 && _snowman == 0.0 ? this : new fx((double)this.u() + _snowman, (double)this.v() + _snowman, (double)this.w() + _snowman);
   }

   public fx b(int var1, int var2, int var3) {
      return _snowman == 0 && _snowman == 0 && _snowman == 0 ? this : new fx(this.u() + _snowman, this.v() + _snowman, this.w() + _snowman);
   }

   public fx a(gr var1) {
      return this.b(_snowman.u(), _snowman.v(), _snowman.w());
   }

   public fx b(gr var1) {
      return this.b(-_snowman.u(), -_snowman.v(), -_snowman.w());
   }

   public fx b() {
      return this.a(gc.b);
   }

   public fx b(int var1) {
      return this.a(gc.b, _snowman);
   }

   public fx c() {
      return this.a(gc.a);
   }

   public fx c(int var1) {
      return this.a(gc.a, _snowman);
   }

   public fx d() {
      return this.a(gc.c);
   }

   public fx d(int var1) {
      return this.a(gc.c, _snowman);
   }

   public fx e() {
      return this.a(gc.d);
   }

   public fx e(int var1) {
      return this.a(gc.d, _snowman);
   }

   public fx f() {
      return this.a(gc.e);
   }

   public fx f(int var1) {
      return this.a(gc.e, _snowman);
   }

   public fx g() {
      return this.a(gc.f);
   }

   public fx g(int var1) {
      return this.a(gc.f, _snowman);
   }

   public fx a(gc var1) {
      return new fx(this.u() + _snowman.i(), this.v() + _snowman.j(), this.w() + _snowman.k());
   }

   public fx a(gc var1, int var2) {
      return _snowman == 0 ? this : new fx(this.u() + _snowman.i() * _snowman, this.v() + _snowman.j() * _snowman, this.w() + _snowman.k() * _snowman);
   }

   public fx a(gc.a var1, int var2) {
      if (_snowman == 0) {
         return this;
      } else {
         int _snowman = _snowman == gc.a.a ? _snowman : 0;
         int _snowmanx = _snowman == gc.a.b ? _snowman : 0;
         int _snowmanxx = _snowman == gc.a.c ? _snowman : 0;
         return new fx(this.u() + _snowman, this.v() + _snowmanx, this.w() + _snowmanxx);
      }
   }

   public fx a(bzm var1) {
      switch (_snowman) {
         case a:
         default:
            return this;
         case b:
            return new fx(-this.w(), this.v(), this.u());
         case c:
            return new fx(-this.u(), this.v(), -this.w());
         case d:
            return new fx(this.w(), this.v(), -this.u());
      }
   }

   public fx c(gr var1) {
      return new fx(this.v() * _snowman.w() - this.w() * _snowman.v(), this.w() * _snowman.u() - this.u() * _snowman.w(), this.u() * _snowman.v() - this.v() * _snowman.u());
   }

   public fx h() {
      return this;
   }

   public fx.a i() {
      return new fx.a(this.u(), this.v(), this.w());
   }

   public static Iterable<fx> a(Random var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      int _snowman = _snowman - _snowman + 1;
      int _snowmanx = _snowman - _snowman + 1;
      int _snowmanxx = _snowman - _snowman + 1;
      return () -> new AbstractIterator<fx>() {
            final fx.a a = new fx.a();
            int b = _snowman;

            protected fx a() {
               if (this.b <= 0) {
                  return (fx)this.endOfData();
               } else {
                  fx _snowman = this.a.d(_snowman + _snowman.nextInt(_snowman), _snowman + _snowman.nextInt(_snowman), _snowman + _snowman.nextInt(_snowman));
                  this.b--;
                  return _snowman;
               }
            }
         };
   }

   public static Iterable<fx> a(fx var0, int var1, int var2, int var3) {
      int _snowman = _snowman + _snowman + _snowman;
      int _snowmanx = _snowman.u();
      int _snowmanxx = _snowman.v();
      int _snowmanxxx = _snowman.w();
      return () -> new AbstractIterator<fx>() {
            private final fx.a h = new fx.a();
            private int i;
            private int j;
            private int k;
            private int l;
            private int m;
            private boolean n;

            protected fx a() {
               if (this.n) {
                  this.n = false;
                  this.h.q(_snowman - (this.h.w() - _snowman));
                  return this.h;
               } else {
                  fx _snowman;
                  for (_snowman = null; _snowman == null; this.m++) {
                     if (this.m > this.k) {
                        this.l++;
                        if (this.l > this.j) {
                           this.i++;
                           if (this.i > _snowman) {
                              return (fx)this.endOfData();
                           }

                           this.j = Math.min(_snowman, this.i);
                           this.l = -this.j;
                        }

                        this.k = Math.min(_snowman, this.i - Math.abs(this.l));
                        this.m = -this.k;
                     }

                     int _snowmanx = this.l;
                     int _snowmanxx = this.m;
                     int _snowmanxxx = this.i - Math.abs(_snowmanx) - Math.abs(_snowmanxx);
                     if (_snowmanxxx <= _snowman) {
                        this.n = _snowmanxxx != 0;
                        _snowman = this.h.d(_snowman + _snowmanx, _snowman + _snowmanxx, _snowman + _snowmanxxx);
                     }
                  }

                  return _snowman;
               }
            }
         };
   }

   public static Optional<fx> a(fx var0, int var1, int var2, Predicate<fx> var3) {
      return b(_snowman, _snowman, _snowman, _snowman).filter(_snowman).findFirst();
   }

   public static Stream<fx> b(fx var0, int var1, int var2, int var3) {
      return StreamSupport.stream(a(_snowman, _snowman, _snowman, _snowman).spliterator(), false);
   }

   public static Iterable<fx> a(fx var0, fx var1) {
      return b(Math.min(_snowman.u(), _snowman.u()), Math.min(_snowman.v(), _snowman.v()), Math.min(_snowman.w(), _snowman.w()), Math.max(_snowman.u(), _snowman.u()), Math.max(_snowman.v(), _snowman.v()), Math.max(_snowman.w(), _snowman.w()));
   }

   public static Stream<fx> b(fx var0, fx var1) {
      return StreamSupport.stream(a(_snowman, _snowman).spliterator(), false);
   }

   public static Stream<fx> a(cra var0) {
      return a(Math.min(_snowman.a, _snowman.d), Math.min(_snowman.b, _snowman.e), Math.min(_snowman.c, _snowman.f), Math.max(_snowman.a, _snowman.d), Math.max(_snowman.b, _snowman.e), Math.max(_snowman.c, _snowman.f));
   }

   public static Stream<fx> a(dci var0) {
      return a(afm.c(_snowman.a), afm.c(_snowman.b), afm.c(_snowman.c), afm.c(_snowman.d), afm.c(_snowman.e), afm.c(_snowman.f));
   }

   public static Stream<fx> a(int var0, int var1, int var2, int var3, int var4, int var5) {
      return StreamSupport.stream(b(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman).spliterator(), false);
   }

   public static Iterable<fx> b(int var0, int var1, int var2, int var3, int var4, int var5) {
      int _snowman = _snowman - _snowman + 1;
      int _snowmanx = _snowman - _snowman + 1;
      int _snowmanxx = _snowman - _snowman + 1;
      int _snowmanxxx = _snowman * _snowmanx * _snowmanxx;
      return () -> new AbstractIterator<fx>() {
            private final fx.a g = new fx.a();
            private int h;

            protected fx a() {
               if (this.h == _snowman) {
                  return (fx)this.endOfData();
               } else {
                  int _snowman = this.h % _snowman;
                  int _snowmanx = this.h / _snowman;
                  int _snowmanxx = _snowmanx % _snowman;
                  int _snowmanxxx = _snowmanx / _snowman;
                  this.h++;
                  return this.g.d(_snowman + _snowman, _snowman + _snowmanxx, _snowman + _snowmanxxx);
               }
            }
         };
   }

   public static Iterable<fx.a> a(fx var0, int var1, gc var2, gc var3) {
      Validate.validState(_snowman.n() != _snowman.n(), "The two directions cannot be on the same axis", new Object[0]);
      return () -> new AbstractIterator<fx.a>() {
            private final gc[] e = new gc[]{_snowman, _snowman, _snowman.f(), _snowman.f()};
            private final fx.a f = _snowman.i().c(_snowman);
            private final int g = 4 * _snowman;
            private int h = -1;
            private int i;
            private int j;
            private int k = this.f.u();
            private int l = this.f.v();
            private int m = this.f.w();

            protected fx.a a() {
               this.f.d(this.k, this.l, this.m).c(this.e[(this.h + 4) % 4]);
               this.k = this.f.u();
               this.l = this.f.v();
               this.m = this.f.w();
               if (this.j >= this.i) {
                  if (this.h >= this.g) {
                     return (fx.a)this.endOfData();
                  }

                  this.h++;
                  this.j = 0;
                  this.i = this.h / 2 + 1;
               }

               this.j++;
               return this.f;
            }
         };
   }

   public static class a extends fx {
      public a() {
         this(0, 0, 0);
      }

      public a(int var1, int var2, int var3) {
         super(_snowman, _snowman, _snowman);
      }

      public a(double var1, double var3, double var5) {
         this(afm.c(_snowman), afm.c(_snowman), afm.c(_snowman));
      }

      @Override
      public fx a(double var1, double var3, double var5) {
         return super.a(_snowman, _snowman, _snowman).h();
      }

      @Override
      public fx b(int var1, int var2, int var3) {
         return super.b(_snowman, _snowman, _snowman).h();
      }

      @Override
      public fx a(gc var1, int var2) {
         return super.a(_snowman, _snowman).h();
      }

      @Override
      public fx a(gc.a var1, int var2) {
         return super.a(_snowman, _snowman).h();
      }

      @Override
      public fx a(bzm var1) {
         return super.a(_snowman).h();
      }

      public fx.a d(int var1, int var2, int var3) {
         this.o(_snowman);
         this.p(_snowman);
         this.q(_snowman);
         return this;
      }

      public fx.a c(double var1, double var3, double var5) {
         return this.d(afm.c(_snowman), afm.c(_snowman), afm.c(_snowman));
      }

      public fx.a g(gr var1) {
         return this.d(_snowman.u(), _snowman.v(), _snowman.w());
      }

      public fx.a g(long var1) {
         return this.d(b(_snowman), c(_snowman), d(_snowman));
      }

      public fx.a a(fv var1, int var2, int var3, int var4) {
         return this.d(_snowman.a(_snowman, _snowman, _snowman, gc.a.a), _snowman.a(_snowman, _snowman, _snowman, gc.a.b), _snowman.a(_snowman, _snowman, _snowman, gc.a.c));
      }

      public fx.a a(gr var1, gc var2) {
         return this.d(_snowman.u() + _snowman.i(), _snowman.v() + _snowman.j(), _snowman.w() + _snowman.k());
      }

      public fx.a a(gr var1, int var2, int var3, int var4) {
         return this.d(_snowman.u() + _snowman, _snowman.v() + _snowman, _snowman.w() + _snowman);
      }

      public fx.a c(gc var1) {
         return this.c(_snowman, 1);
      }

      public fx.a c(gc var1, int var2) {
         return this.d(this.u() + _snowman.i() * _snowman, this.v() + _snowman.j() * _snowman, this.w() + _snowman.k() * _snowman);
      }

      public fx.a e(int var1, int var2, int var3) {
         return this.d(this.u() + _snowman, this.v() + _snowman, this.w() + _snowman);
      }

      public fx.a h(gr var1) {
         return this.d(this.u() + _snowman.u(), this.v() + _snowman.v(), this.w() + _snowman.w());
      }

      public fx.a a(gc.a var1, int var2, int var3) {
         switch (_snowman) {
            case a:
               return this.d(afm.a(this.u(), _snowman, _snowman), this.v(), this.w());
            case b:
               return this.d(this.u(), afm.a(this.v(), _snowman, _snowman), this.w());
            case c:
               return this.d(this.u(), this.v(), afm.a(this.w(), _snowman, _snowman));
            default:
               throw new IllegalStateException("Unable to clamp axis " + _snowman);
         }
      }

      @Override
      public void o(int var1) {
         super.o(_snowman);
      }

      @Override
      public void p(int var1) {
         super.p(_snowman);
      }

      @Override
      public void q(int var1) {
         super.q(_snowman);
      }

      @Override
      public fx h() {
         return new fx(this);
      }
   }
}
