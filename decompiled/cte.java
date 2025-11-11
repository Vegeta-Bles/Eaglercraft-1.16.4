import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class cte extends ctt<ctu> {
   private static final ceh K = bup.fF.n();
   private static final ceh L = bup.fG.n();
   private static final ceh M = bup.gR.n();
   private static final ceh N = bup.fJ.n();
   private static final ceh O = bup.fR.n();
   private static final ceh P = bup.fT.n();
   private static final ceh Q = bup.fN.n();
   protected ceh[] a;
   protected long b;
   protected cuc c;
   protected cuc d;
   protected cuc e;

   public cte(Codec<ctu> var1) {
      super(_snowman);
   }

   public void a(Random var1, cfw var2, bsv var3, int var4, int var5, int var6, double var7, ceh var9, ceh var10, int var11, long var12, ctu var14) {
      int _snowman = _snowman & 15;
      int _snowmanx = _snowman & 15;
      ceh _snowmanxx = K;
      ctv _snowmanxxx = _snowman.e().e();
      ceh _snowmanxxxx = _snowmanxxx.b();
      ceh _snowmanxxxxx = _snowmanxxx.a();
      ceh _snowmanxxxxxx = _snowmanxxxx;
      int _snowmanxxxxxxx = (int)(_snowman / 3.0 + 3.0 + _snowman.nextDouble() * 0.25);
      boolean _snowmanxxxxxxxx = Math.cos(_snowman / 3.0 * Math.PI) > 0.0;
      int _snowmanxxxxxxxxx = -1;
      boolean _snowmanxxxxxxxxxx = false;
      int _snowmanxxxxxxxxxxx = 0;
      fx.a _snowmanxxxxxxxxxxxx = new fx.a();

      for (int _snowmanxxxxxxxxxxxxx = _snowman; _snowmanxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxx--) {
         if (_snowmanxxxxxxxxxxx < 15) {
            _snowmanxxxxxxxxxxxx.d(_snowman, _snowmanxxxxxxxxxxxxx, _snowmanx);
            ceh _snowmanxxxxxxxxxxxxxx = _snowman.d_(_snowmanxxxxxxxxxxxx);
            if (_snowmanxxxxxxxxxxxxxx.g()) {
               _snowmanxxxxxxxxx = -1;
            } else if (_snowmanxxxxxxxxxxxxxx.a(_snowman.b())) {
               if (_snowmanxxxxxxxxx == -1) {
                  _snowmanxxxxxxxxxx = false;
                  if (_snowmanxxxxxxx <= 0) {
                     _snowmanxx = bup.a.n();
                     _snowmanxxxxxx = _snowman;
                  } else if (_snowmanxxxxxxxxxxxxx >= _snowman - 4 && _snowmanxxxxxxxxxxxxx <= _snowman + 1) {
                     _snowmanxx = K;
                     _snowmanxxxxxx = _snowmanxxxx;
                  }

                  if (_snowmanxxxxxxxxxxxxx < _snowman && (_snowmanxx == null || _snowmanxx.g())) {
                     _snowmanxx = _snowman;
                  }

                  _snowmanxxxxxxxxx = _snowmanxxxxxxx + Math.max(0, _snowmanxxxxxxxxxxxxx - _snowman);
                  if (_snowmanxxxxxxxxxxxxx >= _snowman - 1) {
                     if (_snowmanxxxxxxxxxxxxx <= _snowman + 3 + _snowmanxxxxxxx) {
                        _snowman.a(_snowmanxxxxxxxxxxxx, _snowmanxxxxx, false);
                        _snowmanxxxxxxxxxx = true;
                     } else {
                        ceh _snowmanxxxxxxxxxxxxxxx;
                        if (_snowmanxxxxxxxxxxxxx < 64 || _snowmanxxxxxxxxxxxxx > 127) {
                           _snowmanxxxxxxxxxxxxxxx = L;
                        } else if (_snowmanxxxxxxxx) {
                           _snowmanxxxxxxxxxxxxxxx = M;
                        } else {
                           _snowmanxxxxxxxxxxxxxxx = this.a(_snowman, _snowmanxxxxxxxxxxxxx, _snowman);
                        }

                        _snowman.a(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, false);
                     }
                  } else {
                     _snowman.a(_snowmanxxxxxxxxxxxx, _snowmanxxxxxx, false);
                     buo _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxx.b();
                     if (_snowmanxxxxxxxxxxxxxxx == bup.fF
                        || _snowmanxxxxxxxxxxxxxxx == bup.fG
                        || _snowmanxxxxxxxxxxxxxxx == bup.fH
                        || _snowmanxxxxxxxxxxxxxxx == bup.fI
                        || _snowmanxxxxxxxxxxxxxxx == bup.fJ
                        || _snowmanxxxxxxxxxxxxxxx == bup.fK
                        || _snowmanxxxxxxxxxxxxxxx == bup.fL
                        || _snowmanxxxxxxxxxxxxxxx == bup.fM
                        || _snowmanxxxxxxxxxxxxxxx == bup.fN
                        || _snowmanxxxxxxxxxxxxxxx == bup.fO
                        || _snowmanxxxxxxxxxxxxxxx == bup.fP
                        || _snowmanxxxxxxxxxxxxxxx == bup.fQ
                        || _snowmanxxxxxxxxxxxxxxx == bup.fR
                        || _snowmanxxxxxxxxxxxxxxx == bup.fS
                        || _snowmanxxxxxxxxxxxxxxx == bup.fT
                        || _snowmanxxxxxxxxxxxxxxx == bup.fU) {
                        _snowman.a(_snowmanxxxxxxxxxxxx, L, false);
                     }
                  }
               } else if (_snowmanxxxxxxxxx > 0) {
                  _snowmanxxxxxxxxx--;
                  if (_snowmanxxxxxxxxxx) {
                     _snowman.a(_snowmanxxxxxxxxxxxx, L, false);
                  } else {
                     _snowman.a(_snowmanxxxxxxxxxxxx, this.a(_snowman, _snowmanxxxxxxxxxxxxx, _snowman), false);
                  }
               }

               _snowmanxxxxxxxxxxx++;
            }
         }
      }
   }

   @Override
   public void a(long var1) {
      if (this.b != _snowman || this.a == null) {
         this.b(_snowman);
      }

      if (this.b != _snowman || this.c == null || this.d == null) {
         chx _snowman = new chx(_snowman);
         this.c = new cuc(_snowman, IntStream.rangeClosed(-3, 0));
         this.d = new cuc(_snowman, ImmutableList.of(0));
      }

      this.b = _snowman;
   }

   protected void b(long var1) {
      this.a = new ceh[64];
      Arrays.fill(this.a, M);
      chx _snowman = new chx(_snowman);
      this.e = new cuc(_snowman, ImmutableList.of(0));

      for (int _snowmanx = 0; _snowmanx < 64; _snowmanx++) {
         _snowmanx += _snowman.nextInt(5) + 1;
         if (_snowmanx < 64) {
            this.a[_snowmanx] = L;
         }
      }

      int _snowmanxx = _snowman.nextInt(4) + 2;

      for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx; _snowmanxxx++) {
         int _snowmanxxxx = _snowman.nextInt(3) + 1;
         int _snowmanxxxxx = _snowman.nextInt(64);

         for (int _snowmanxxxxxx = 0; _snowmanxxxxx + _snowmanxxxxxx < 64 && _snowmanxxxxxx < _snowmanxxxx; _snowmanxxxxxx++) {
            this.a[_snowmanxxxxx + _snowmanxxxxxx] = N;
         }
      }

      int _snowmanxxx = _snowman.nextInt(4) + 2;

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx; _snowmanxxxx++) {
         int _snowmanxxxxx = _snowman.nextInt(3) + 2;
         int _snowmanxxxxxx = _snowman.nextInt(64);

         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxx + _snowmanxxxxxxx < 64 && _snowmanxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxx++) {
            this.a[_snowmanxxxxxx + _snowmanxxxxxxx] = O;
         }
      }

      int _snowmanxxxx = _snowman.nextInt(4) + 2;

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxxx; _snowmanxxxxx++) {
         int _snowmanxxxxxx = _snowman.nextInt(3) + 1;
         int _snowmanxxxxxxx = _snowman.nextInt(64);

         for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxx + _snowmanxxxxxxxx < 64 && _snowmanxxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxxx++) {
            this.a[_snowmanxxxxxxx + _snowmanxxxxxxxx] = P;
         }
      }

      int _snowmanxxxxx = _snowman.nextInt(3) + 3;
      int _snowmanxxxxxx = 0;

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxx++) {
         int _snowmanxxxxxxxx = 1;
         _snowmanxxxxxx += _snowman.nextInt(16) + 4;

         for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxx + _snowmanxxxxxxxxx < 64 && _snowmanxxxxxxxxx < 1; _snowmanxxxxxxxxx++) {
            this.a[_snowmanxxxxxx + _snowmanxxxxxxxxx] = K;
            if (_snowmanxxxxxx + _snowmanxxxxxxxxx > 1 && _snowman.nextBoolean()) {
               this.a[_snowmanxxxxxx + _snowmanxxxxxxxxx - 1] = Q;
            }

            if (_snowmanxxxxxx + _snowmanxxxxxxxxx < 63 && _snowman.nextBoolean()) {
               this.a[_snowmanxxxxxx + _snowmanxxxxxxxxx + 1] = Q;
            }
         }
      }
   }

   protected ceh a(int var1, int var2, int var3) {
      int _snowman = (int)Math.round(this.e.a((double)_snowman / 512.0, (double)_snowman / 512.0, false) * 2.0);
      return this.a[(_snowman + _snowman + 64) % 64];
   }
}
