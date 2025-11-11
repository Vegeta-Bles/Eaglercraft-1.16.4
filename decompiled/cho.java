import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import javax.annotation.Nullable;

public final class cho extends cfy {
   public static final Codec<cho> d = RecordCodecBuilder.create(
      var0 -> var0.group(
               bsy.a.fieldOf("biome_source").forGetter(var0x -> var0x.b),
               Codec.LONG.fieldOf("seed").stable().forGetter(var0x -> var0x.w),
               chp.b.fieldOf("settings").forGetter(var0x -> var0x.h)
            )
            .apply(var0, var0.stable(cho::new))
   );
   private static final float[] i = x.a(new float[13824], var0 -> {
      for (int _snowman = 0; _snowman < 24; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 24; _snowmanx++) {
            for (int _snowmanxx = 0; _snowmanxx < 24; _snowmanxx++) {
               var0[_snowman * 24 * 24 + _snowmanx * 24 + _snowmanxx] = (float)b(_snowmanx - 12, _snowmanxx - 12, _snowman - 12);
            }
         }
      }
   });
   private static final float[] j = x.a(new float[25], var0 -> {
      for (int _snowman = -2; _snowman <= 2; _snowman++) {
         for (int _snowmanx = -2; _snowmanx <= 2; _snowmanx++) {
            float _snowmanxx = 10.0F / afm.c((float)(_snowman * _snowman + _snowmanx * _snowmanx) + 0.2F);
            var0[_snowman + 2 + (_snowmanx + 2) * 5] = _snowmanxx;
         }
      }
   });
   private static final ceh k = bup.a.n();
   private final int l;
   private final int m;
   private final int n;
   private final int o;
   private final int p;
   protected final chx e;
   private final cub q;
   private final cub r;
   private final cub s;
   private final cue t;
   private final cub u;
   @Nullable
   private final cud v;
   protected final ceh f;
   protected final ceh g;
   private final long w;
   protected final Supplier<chp> h;
   private final int x;

   public cho(bsy var1, long var2, Supplier<chp> var4) {
      this(_snowman, _snowman, _snowman, _snowman);
   }

   private cho(bsy var1, bsy var2, long var3, Supplier<chp> var5) {
      super(_snowman, _snowman, _snowman.get().a(), _snowman);
      this.w = _snowman;
      chp _snowman = _snowman.get();
      this.h = _snowman;
      chr _snowmanx = _snowman.b();
      this.x = _snowmanx.a();
      this.l = _snowmanx.f() * 4;
      this.m = _snowmanx.e() * 4;
      this.f = _snowman.c();
      this.g = _snowman.d();
      this.n = 16 / this.m;
      this.o = _snowmanx.a() / this.l;
      this.p = 16 / this.m;
      this.e = new chx(_snowman);
      this.q = new cub(this.e, IntStream.rangeClosed(-15, 0));
      this.r = new cub(this.e, IntStream.rangeClosed(-15, 0));
      this.s = new cub(this.e, IntStream.rangeClosed(-7, 0));
      this.t = (cue)(_snowmanx.i() ? new cuc(this.e, IntStream.rangeClosed(-3, 0)) : new cub(this.e, IntStream.rangeClosed(-3, 0)));
      this.e.a(2620);
      this.u = new cub(this.e, IntStream.rangeClosed(-15, 0));
      if (_snowmanx.k()) {
         chx _snowmanxx = new chx(_snowman);
         _snowmanxx.a(17292);
         this.v = new cud(_snowmanxx);
      } else {
         this.v = null;
      }
   }

   @Override
   protected Codec<? extends cfy> a() {
      return d;
   }

   @Override
   public cfy a(long var1) {
      return new cho(this.b.a(_snowman), _snowman, this.h);
   }

   public boolean a(long var1, vj<chp> var3) {
      return this.w == _snowman && this.h.get().a(_snowman);
   }

   private double a(int var1, int var2, int var3, double var4, double var6, double var8, double var10) {
      double _snowman = 0.0;
      double _snowmanx = 0.0;
      double _snowmanxx = 0.0;
      boolean _snowmanxxx = true;
      double _snowmanxxxx = 1.0;

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < 16; _snowmanxxxxx++) {
         double _snowmanxxxxxx = cub.a((double)_snowman * _snowman * _snowmanxxxx);
         double _snowmanxxxxxxx = cub.a((double)_snowman * _snowman * _snowmanxxxx);
         double _snowmanxxxxxxxx = cub.a((double)_snowman * _snowman * _snowmanxxxx);
         double _snowmanxxxxxxxxx = _snowman * _snowmanxxxx;
         ctz _snowmanxxxxxxxxxx = this.q.a(_snowmanxxxxx);
         if (_snowmanxxxxxxxxxx != null) {
            _snowman += _snowmanxxxxxxxxxx.a(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, (double)_snowman * _snowmanxxxxxxxxx) / _snowmanxxxx;
         }

         ctz _snowmanxxxxxxxxxxx = this.r.a(_snowmanxxxxx);
         if (_snowmanxxxxxxxxxxx != null) {
            _snowmanx += _snowmanxxxxxxxxxxx.a(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, (double)_snowman * _snowmanxxxxxxxxx) / _snowmanxxxx;
         }

         if (_snowmanxxxxx < 8) {
            ctz _snowmanxxxxxxxxxxxx = this.s.a(_snowmanxxxxx);
            if (_snowmanxxxxxxxxxxxx != null) {
               _snowmanxx += _snowmanxxxxxxxxxxxx.a(
                     cub.a((double)_snowman * _snowman * _snowmanxxxx), cub.a((double)_snowman * _snowman * _snowmanxxxx), cub.a((double)_snowman * _snowman * _snowmanxxxx), _snowman * _snowmanxxxx, (double)_snowman * _snowman * _snowmanxxxx
                  )
                  / _snowmanxxxx;
            }
         }

         _snowmanxxxx /= 2.0;
      }

      return afm.b(_snowman / 512.0, _snowmanx / 512.0, (_snowmanxx / 10.0 + 1.0) / 2.0);
   }

   private double[] b(int var1, int var2) {
      double[] _snowman = new double[this.o + 1];
      this.a(_snowman, _snowman, _snowman);
      return _snowman;
   }

   private void a(double[] var1, int var2, int var3) {
      chr _snowman = this.h.get().b();
      double _snowmanx;
      double _snowmanxx;
      if (this.v != null) {
         _snowmanx = (double)(btk.a(this.v, _snowman, _snowman) - 8.0F);
         if (_snowmanx > 0.0) {
            _snowmanxx = 0.25;
         } else {
            _snowmanxx = 1.0;
         }
      } else {
         float _snowmanxxx = 0.0F;
         float _snowmanxxxx = 0.0F;
         float _snowmanxxxxx = 0.0F;
         int _snowmanxxxxxx = 2;
         int _snowmanxxxxxxx = this.f();
         float _snowmanxxxxxxxx = this.b.b(_snowman, _snowmanxxxxxxx, _snowman).h();

         for (int _snowmanxxxxxxxxx = -2; _snowmanxxxxxxxxx <= 2; _snowmanxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxx = -2; _snowmanxxxxxxxxxx <= 2; _snowmanxxxxxxxxxx++) {
               bsv _snowmanxxxxxxxxxxx = this.b.b(_snowman + _snowmanxxxxxxxxx, _snowmanxxxxxxx, _snowman + _snowmanxxxxxxxxxx);
               float _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.h();
               float _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.j();
               float _snowmanxxxxxxxxxxxxxx;
               float _snowmanxxxxxxxxxxxxxxx;
               if (_snowman.l() && _snowmanxxxxxxxxxxxx > 0.0F) {
                  _snowmanxxxxxxxxxxxxxx = 1.0F + _snowmanxxxxxxxxxxxx * 2.0F;
                  _snowmanxxxxxxxxxxxxxxx = 1.0F + _snowmanxxxxxxxxxxxxx * 4.0F;
               } else {
                  _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx;
                  _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx;
               }

               float _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx > _snowmanxxxxxxxx ? 0.5F : 1.0F;
               float _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx * j[_snowmanxxxxxxxxx + 2 + (_snowmanxxxxxxxxxx + 2) * 5] / (_snowmanxxxxxxxxxxxxxx + 2.0F);
               _snowmanxxx += _snowmanxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxx;
               _snowmanxxxx += _snowmanxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxx;
               _snowmanxxxxx += _snowmanxxxxxxxxxxxxxxxxx;
            }
         }

         float _snowmanxxxxxxxxx = _snowmanxxxx / _snowmanxxxxx;
         float _snowmanxxxxxxxxxx = _snowmanxxx / _snowmanxxxxx;
         double _snowmanxxxxxxxxxxx = (double)(_snowmanxxxxxxxxx * 0.5F - 0.125F);
         double _snowmanxxxxxxxxxxxx = (double)(_snowmanxxxxxxxxxx * 0.9F + 0.1F);
         _snowmanx = _snowmanxxxxxxxxxxx * 0.265625;
         _snowmanxx = 96.0 / _snowmanxxxxxxxxxxxx;
      }

      double _snowmanxxx = 684.412 * _snowman.b().a();
      double _snowmanxxxx = 684.412 * _snowman.b().b();
      double _snowmanxxxxx = _snowmanxxx / _snowman.b().c();
      double _snowmanxxxxxx = _snowmanxxxx / _snowman.b().d();
      double _snowmanxxxxxxx = (double)_snowman.c().a();
      double _snowmanxxxxxxxx = (double)_snowman.c().b();
      double _snowmanxxxxxxxxx = (double)_snowman.c().c();
      double _snowmanxxxxxxxxxx = (double)_snowman.d().a();
      double _snowmanxxxxxxxxxxx = (double)_snowman.d().b();
      double _snowmanxxxxxxxxxxxx = (double)_snowman.d().c();
      double _snowmanxxxxxxxxxxxxx = _snowman.j() ? this.c(_snowman, _snowman) : 0.0;
      double _snowmanxxxxxxxxxxxxxx = _snowman.g();
      double _snowmanxxxxxxxxxxxxxxx = _snowman.h();

      for (int _snowmanxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxx <= this.o; _snowmanxxxxxxxxxxxxxxxx++) {
         double _snowmanxxxxxxxxxxxxxxxxx = this.a(_snowman, _snowmanxxxxxxxxxxxxxxxx, _snowman, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
         double _snowmanxxxxxxxxxxxxxxxxxx = 1.0 - (double)_snowmanxxxxxxxxxxxxxxxx * 2.0 / (double)this.o + _snowmanxxxxxxxxxxxxx;
         double _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxx;
         double _snowmanxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxxxxxx + _snowmanx) * _snowmanxx;
         if (_snowmanxxxxxxxxxxxxxxxxxxxx > 0.0) {
            _snowmanxxxxxxxxxxxxxxxxx += _snowmanxxxxxxxxxxxxxxxxxxxx * 4.0;
         } else {
            _snowmanxxxxxxxxxxxxxxxxx += _snowmanxxxxxxxxxxxxxxxxxxxx;
         }

         if (_snowmanxxxxxxxx > 0.0) {
            double _snowmanxxxxxxxxxxxxxxxxxxxxx = ((double)(this.o - _snowmanxxxxxxxxxxxxxxxx) - _snowmanxxxxxxxxx) / _snowmanxxxxxxxx;
            _snowmanxxxxxxxxxxxxxxxxx = afm.b(_snowmanxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx);
         }

         if (_snowmanxxxxxxxxxxx > 0.0) {
            double _snowmanxxxxxxxxxxxxxxxxxxxxx = ((double)_snowmanxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxx) / _snowmanxxxxxxxxxxx;
            _snowmanxxxxxxxxxxxxxxxxx = afm.b(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx);
         }

         _snowman[_snowmanxxxxxxxxxxxxxxxx] = _snowmanxxxxxxxxxxxxxxxxx;
      }
   }

   private double c(int var1, int var2) {
      double _snowman = this.u.a((double)(_snowman * 200), 10.0, (double)(_snowman * 200), 1.0, 0.0, true);
      double _snowmanx;
      if (_snowman < 0.0) {
         _snowmanx = -_snowman * 0.3;
      } else {
         _snowmanx = _snowman;
      }

      double _snowmanxx = _snowmanx * 24.575625 - 2.0;
      return _snowmanxx < 0.0 ? _snowmanxx * 0.009486607142857142 : Math.min(_snowmanxx, 1.0) * 0.006640625;
   }

   @Override
   public int a(int var1, int var2, chn.a var3) {
      return this.a(_snowman, _snowman, null, _snowman.e());
   }

   @Override
   public brc a(int var1, int var2) {
      ceh[] _snowman = new ceh[this.o * this.l];
      this.a(_snowman, _snowman, _snowman, null);
      return new bsh(_snowman);
   }

   private int a(int var1, int var2, @Nullable ceh[] var3, @Nullable Predicate<ceh> var4) {
      int _snowman = Math.floorDiv(_snowman, this.m);
      int _snowmanx = Math.floorDiv(_snowman, this.m);
      int _snowmanxx = Math.floorMod(_snowman, this.m);
      int _snowmanxxx = Math.floorMod(_snowman, this.m);
      double _snowmanxxxx = (double)_snowmanxx / (double)this.m;
      double _snowmanxxxxx = (double)_snowmanxxx / (double)this.m;
      double[][] _snowmanxxxxxx = new double[][]{this.b(_snowman, _snowmanx), this.b(_snowman, _snowmanx + 1), this.b(_snowman + 1, _snowmanx), this.b(_snowman + 1, _snowmanx + 1)};

      for (int _snowmanxxxxxxx = this.o - 1; _snowmanxxxxxxx >= 0; _snowmanxxxxxxx--) {
         double _snowmanxxxxxxxx = _snowmanxxxxxx[0][_snowmanxxxxxxx];
         double _snowmanxxxxxxxxx = _snowmanxxxxxx[1][_snowmanxxxxxxx];
         double _snowmanxxxxxxxxxx = _snowmanxxxxxx[2][_snowmanxxxxxxx];
         double _snowmanxxxxxxxxxxx = _snowmanxxxxxx[3][_snowmanxxxxxxx];
         double _snowmanxxxxxxxxxxxx = _snowmanxxxxxx[0][_snowmanxxxxxxx + 1];
         double _snowmanxxxxxxxxxxxxx = _snowmanxxxxxx[1][_snowmanxxxxxxx + 1];
         double _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxx[2][_snowmanxxxxxxx + 1];
         double _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxx[3][_snowmanxxxxxxx + 1];

         for (int _snowmanxxxxxxxxxxxxxxxx = this.l - 1; _snowmanxxxxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxxxxx--) {
            double _snowmanxxxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxxxxxxx / (double)this.l;
            double _snowmanxxxxxxxxxxxxxxxxxx = afm.a(
               _snowmanxxxxxxxxxxxxxxxxx,
               _snowmanxxxx,
               _snowmanxxxxx,
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxxxxx,
               _snowmanxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowmanxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxx
            );
            int _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx * this.l + _snowmanxxxxxxxxxxxxxxxx;
            ceh _snowmanxxxxxxxxxxxxxxxxxxxx = this.a(_snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx);
            if (_snowman != null) {
               _snowman[_snowmanxxxxxxxxxxxxxxxxxxx] = _snowmanxxxxxxxxxxxxxxxxxxxx;
            }

            if (_snowman != null && _snowman.test(_snowmanxxxxxxxxxxxxxxxxxxxx)) {
               return _snowmanxxxxxxxxxxxxxxxxxxx + 1;
            }
         }
      }

      return 0;
   }

   protected ceh a(double var1, int var3) {
      ceh _snowman;
      if (_snowman > 0.0) {
         _snowman = this.f;
      } else if (_snowman < this.f()) {
         _snowman = this.g;
      } else {
         _snowman = k;
      }

      return _snowman;
   }

   @Override
   public void a(aam var1, cfw var2) {
      brd _snowman = _snowman.g();
      int _snowmanx = _snowman.b;
      int _snowmanxx = _snowman.c;
      chx _snowmanxxx = new chx();
      _snowmanxxx.a(_snowmanx, _snowmanxx);
      brd _snowmanxxxx = _snowman.g();
      int _snowmanxxxxx = _snowmanxxxx.d();
      int _snowmanxxxxxx = _snowmanxxxx.e();
      double _snowmanxxxxxxx = 0.0625;
      fx.a _snowmanxxxxxxxx = new fx.a();

      for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < 16; _snowmanxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < 16; _snowmanxxxxxxxxxx++) {
            int _snowmanxxxxxxxxxxx = _snowmanxxxxx + _snowmanxxxxxxxxx;
            int _snowmanxxxxxxxxxxxx = _snowmanxxxxxx + _snowmanxxxxxxxxxx;
            int _snowmanxxxxxxxxxxxxx = _snowman.a(chn.a.a, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx) + 1;
            double _snowmanxxxxxxxxxxxxxx = this.t.a((double)_snowmanxxxxxxxxxxx * 0.0625, (double)_snowmanxxxxxxxxxxxx * 0.0625, 0.0625, (double)_snowmanxxxxxxxxx * 0.0625) * 15.0;
            _snowman.v(_snowmanxxxxxxxx.d(_snowmanxxxxx + _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxx + _snowmanxxxxxxxxxx))
               .a(_snowmanxxx, _snowman, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, this.f, this.g, this.f(), _snowman.C());
         }
      }

      this.a(_snowman, _snowmanxxx);
   }

   private void a(cfw var1, Random var2) {
      fx.a _snowman = new fx.a();
      int _snowmanx = _snowman.g().d();
      int _snowmanxx = _snowman.g().e();
      chp _snowmanxxx = this.h.get();
      int _snowmanxxxx = _snowmanxxx.f();
      int _snowmanxxxxx = this.x - 1 - _snowmanxxx.e();
      int _snowmanxxxxxx = 5;
      boolean _snowmanxxxxxxx = _snowmanxxxxx + 4 >= 0 && _snowmanxxxxx < this.x;
      boolean _snowmanxxxxxxxx = _snowmanxxxx + 4 >= 0 && _snowmanxxxx < this.x;
      if (_snowmanxxxxxxx || _snowmanxxxxxxxx) {
         for (fx _snowmanxxxxxxxxx : fx.b(_snowmanx, 0, _snowmanxx, _snowmanx + 15, 0, _snowmanxx + 15)) {
            if (_snowmanxxxxxxx) {
               for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < 5; _snowmanxxxxxxxxxx++) {
                  if (_snowmanxxxxxxxxxx <= _snowman.nextInt(5)) {
                     _snowman.a(_snowman.d(_snowmanxxxxxxxxx.u(), _snowmanxxxxx - _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx.w()), bup.z.n(), false);
                  }
               }
            }

            if (_snowmanxxxxxxxx) {
               for (int _snowmanxxxxxxxxxxx = 4; _snowmanxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxx--) {
                  if (_snowmanxxxxxxxxxxx <= _snowman.nextInt(5)) {
                     _snowman.a(_snowman.d(_snowmanxxxxxxxxx.u(), _snowmanxxxx + _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx.w()), bup.z.n(), false);
                  }
               }
            }
         }
      }
   }

   @Override
   public void a(bry var1, bsn var2, cfw var3) {
      ObjectList<cru> _snowman = new ObjectArrayList(10);
      ObjectList<cod> _snowmanx = new ObjectArrayList(32);
      brd _snowmanxx = _snowman.g();
      int _snowmanxxx = _snowmanxx.b;
      int _snowmanxxxx = _snowmanxx.c;
      int _snowmanxxxxx = _snowmanxxx << 4;
      int _snowmanxxxxxx = _snowmanxxxx << 4;

      for (cla<?> _snowmanxxxxxxx : cla.t) {
         _snowman.a(gp.a(_snowmanxx, 0), _snowmanxxxxxxx).forEach(var5x -> {
            for (cru _snowmanxxxxxxxx : var5x.d()) {
               if (_snowmanxxxxxxxx.a(_snowman, 12)) {
                  if (_snowmanxxxxxxxx instanceof cro) {
                     cro _snowmanx = (cro)_snowmanxxxxxxxx;
                     cok.a _snowmanxx = _snowmanx.b().e();
                     if (_snowmanxx == cok.a.b) {
                        _snowman.add(_snowmanx);
                     }

                     for (cod _snowmanxxx : _snowmanx.e()) {
                        int _snowmanxxxx = _snowmanxxx.a();
                        int _snowmanxxxxx = _snowmanxxx.c();
                        if (_snowmanxxxx > _snowman - 12 && _snowmanxxxxx > _snowman - 12 && _snowmanxxxx < _snowman + 15 + 12 && _snowmanxxxxx < _snowman + 15 + 12) {
                           _snowman.add(_snowmanxxx);
                        }
                     }
                  } else {
                     _snowman.add(_snowmanxxxxxxxx);
                  }
               }
            }
         });
      }

      double[][][] _snowmanxxxxxxx = new double[2][this.p + 1][this.o + 1];

      for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < this.p + 1; _snowmanxxxxxxxx++) {
         _snowmanxxxxxxx[0][_snowmanxxxxxxxx] = new double[this.o + 1];
         this.a(_snowmanxxxxxxx[0][_snowmanxxxxxxxx], _snowmanxxx * this.n, _snowmanxxxx * this.p + _snowmanxxxxxxxx);
         _snowmanxxxxxxx[1][_snowmanxxxxxxxx] = new double[this.o + 1];
      }

      cgp _snowmanxxxxxxxx = (cgp)_snowman;
      chn _snowmanxxxxxxxxx = _snowmanxxxxxxxx.a(chn.a.c);
      chn _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.a(chn.a.a);
      fx.a _snowmanxxxxxxxxxxx = new fx.a();
      ObjectListIterator<cru> _snowmanxxxxxxxxxxxx = _snowman.iterator();
      ObjectListIterator<cod> _snowmanxxxxxxxxxxxxx = _snowmanx.iterator();

      for (int _snowmanxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxx < this.n; _snowmanxxxxxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxx < this.p + 1; _snowmanxxxxxxxxxxxxxxx++) {
            this.a(_snowmanxxxxxxx[1][_snowmanxxxxxxxxxxxxxxx], _snowmanxxx * this.n + _snowmanxxxxxxxxxxxxxx + 1, _snowmanxxxx * this.p + _snowmanxxxxxxxxxxxxxxx);
         }

         for (int _snowmanxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxx < this.p; _snowmanxxxxxxxxxxxxxxx++) {
            cgi _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx.a(15);
            _snowmanxxxxxxxxxxxxxxxx.a();

            for (int _snowmanxxxxxxxxxxxxxxxxx = this.o - 1; _snowmanxxxxxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxxxxxx--) {
               double _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx[0][_snowmanxxxxxxxxxxxxxxx][_snowmanxxxxxxxxxxxxxxxxx];
               double _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx[0][_snowmanxxxxxxxxxxxxxxx + 1][_snowmanxxxxxxxxxxxxxxxxx];
               double _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx[1][_snowmanxxxxxxxxxxxxxxx][_snowmanxxxxxxxxxxxxxxxxx];
               double _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx[1][_snowmanxxxxxxxxxxxxxxx + 1][_snowmanxxxxxxxxxxxxxxxxx];
               double _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx[0][_snowmanxxxxxxxxxxxxxxx][_snowmanxxxxxxxxxxxxxxxxx + 1];
               double _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx[0][_snowmanxxxxxxxxxxxxxxx + 1][_snowmanxxxxxxxxxxxxxxxxx + 1];
               double _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx[1][_snowmanxxxxxxxxxxxxxxx][_snowmanxxxxxxxxxxxxxxxxx + 1];
               double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx[1][_snowmanxxxxxxxxxxxxxxx + 1][_snowmanxxxxxxxxxxxxxxxxx + 1];

               for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = this.l - 1; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx--) {
                  int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx * this.l + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx;
                  int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx & 15;
                  int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx >> 4;
                  if (_snowmanxxxxxxxxxxxxxxxx.g() >> 4 != _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                     _snowmanxxxxxxxxxxxxxxxx.b();
                     _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                     _snowmanxxxxxxxxxxxxxxxx.a();
                  }

                  double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx / (double)this.l;
                  double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = afm.d(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx);
                  double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = afm.d(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxx);
                  double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = afm.d(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx);
                  double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = afm.d(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx);

                  for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < this.m; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++) {
                     int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxx + _snowmanxxxxxxxxxxxxxx * this.m + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                     int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx & 15;
                     double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx / (double)this.m;
                     double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = afm.d(
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                     );
                     double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = afm.d(
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                     );

                     for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < this.m;
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
                     ) {
                        int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx + _snowmanxxxxxxxxxxxxxxx * this.m + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                        int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx & 15;
                        double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx / (double)this.m;
                        double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = afm.d(
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                        );
                        double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = afm.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx / 200.0, -1.0, 1.0);
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx / 2.0
                           - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                              * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                              * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                              / 24.0;

                        while (_snowmanxxxxxxxxxxxx.hasNext()) {
                           cru _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (cru)_snowmanxxxxxxxxxxxx.next();
                           cra _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.g();
                           int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Math.max(
                              0,
                              Math.max(
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.a - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.d
                              )
                           );
                           int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx
                              - (
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.b
                                    + (
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx instanceof cro
                                          ? ((cro)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx).d()
                                          : 0
                                    )
                              );
                           int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Math.max(
                              0,
                              Math.max(
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.c - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.f
                              )
                           );
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx += a(
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                              )
                              * 0.8;
                        }

                        _snowmanxxxxxxxxxxxx.back(_snowman.size());

                        while (_snowmanxxxxxxxxxxxxx.hasNext()) {
                           cod _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (cod)_snowmanxxxxxxxxxxxxx.next();
                           int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                              - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.a();
                           int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx
                              - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.b();
                           int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                              - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.c();
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx += a(
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                              )
                              * 0.4;
                        }

                        _snowmanxxxxxxxxxxxxx.back(_snowmanx.size());
                        ceh _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.a(
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx
                        );
                        if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != k) {
                           if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.f() != 0) {
                              _snowmanxxxxxxxxxxx.d(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                              _snowmanxxxxxxxx.k(_snowmanxxxxxxxxxxx);
                           }

                           _snowmanxxxxxxxxxxxxxxxx.a(
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              false
                           );
                           _snowmanxxxxxxxxx.a(
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                           );
                           _snowmanxxxxxxxxxx.a(
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                           );
                        }
                     }
                  }
               }
            }

            _snowmanxxxxxxxxxxxxxxxx.b();
         }

         double[][] _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxx[0];
         _snowmanxxxxxxx[0] = _snowmanxxxxxxx[1];
         _snowmanxxxxxxx[1] = _snowmanxxxxxxxxxxxxxxx;
      }
   }

   private static double a(int var0, int var1, int var2) {
      int _snowman = _snowman + 12;
      int _snowmanx = _snowman + 12;
      int _snowmanxx = _snowman + 12;
      if (_snowman < 0 || _snowman >= 24) {
         return 0.0;
      } else if (_snowmanx < 0 || _snowmanx >= 24) {
         return 0.0;
      } else {
         return _snowmanxx >= 0 && _snowmanxx < 24 ? (double)i[_snowmanxx * 24 * 24 + _snowman * 24 + _snowmanx] : 0.0;
      }
   }

   private static double b(int var0, int var1, int var2) {
      double _snowman = (double)(_snowman * _snowman + _snowman * _snowman);
      double _snowmanx = (double)_snowman + 0.5;
      double _snowmanxx = _snowmanx * _snowmanx;
      double _snowmanxxx = Math.pow(Math.E, -(_snowmanxx / 16.0 + _snowman / 16.0));
      double _snowmanxxxx = -_snowmanx * afm.i(_snowmanxx / 2.0 + _snowman / 2.0) / 2.0;
      return _snowmanxxxx * _snowmanxxx;
   }

   @Override
   public int e() {
      return this.x;
   }

   @Override
   public int f() {
      return this.h.get().g();
   }

   @Override
   public List<btg.c> a(bsv var1, bsn var2, aqo var3, fx var4) {
      if (_snowman.a(_snowman, true, cla.j).e()) {
         if (_snowman == aqo.a) {
            return cla.j.c();
         }

         if (_snowman == aqo.b) {
            return cla.j.j();
         }
      }

      if (_snowman == aqo.a) {
         if (_snowman.a(_snowman, false, cla.b).e()) {
            return cla.b.c();
         }

         if (_snowman.a(_snowman, false, cla.l).e()) {
            return cla.l.c();
         }

         if (_snowman.a(_snowman, true, cla.n).e()) {
            return cla.n.c();
         }
      }

      return super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void a(aam var1) {
      if (!this.h.get().h()) {
         int _snowman = _snowman.a();
         int _snowmanx = _snowman.b();
         bsv _snowmanxx = _snowman.v(new brd(_snowman, _snowmanx).l());
         chx _snowmanxxx = new chx();
         _snowmanxxx.a(_snowman.C(), _snowman << 4, _snowmanx << 4);
         bsg.a(_snowman, _snowmanxx, _snowman, _snowmanx, _snowmanxxx);
      }
   }
}
