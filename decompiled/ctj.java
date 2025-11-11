import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;

public class ctj extends ctt<ctu> {
   protected static final ceh a = bup.gT.n();
   protected static final ceh b = bup.cE.n();
   private static final ceh c = bup.a.n();
   private static final ceh d = bup.E.n();
   private static final ceh e = bup.cD.n();
   private cuc K;
   private cuc L;
   private long M;

   public ctj(Codec<ctu> var1) {
      super(_snowman);
   }

   public void a(Random var1, cfw var2, bsv var3, int var4, int var5, int var6, double var7, ceh var9, ceh var10, int var11, long var12, ctu var14) {
      double _snowman = 0.0;
      double _snowmanx = 0.0;
      fx.a _snowmanxx = new fx.a();
      float _snowmanxxx = _snowman.a(_snowmanxx.d(_snowman, 63, _snowman));
      double _snowmanxxxx = Math.min(Math.abs(_snowman), this.K.a((double)_snowman * 0.1, (double)_snowman * 0.1, false) * 15.0);
      if (_snowmanxxxx > 1.8) {
         double _snowmanxxxxx = 0.09765625;
         double _snowmanxxxxxx = Math.abs(this.L.a((double)_snowman * 0.09765625, (double)_snowman * 0.09765625, false));
         _snowman = _snowmanxxxx * _snowmanxxxx * 1.2;
         double _snowmanxxxxxxx = Math.ceil(_snowmanxxxxxx * 40.0) + 14.0;
         if (_snowman > _snowmanxxxxxxx) {
            _snowman = _snowmanxxxxxxx;
         }

         if (_snowmanxxx > 0.1F) {
            _snowman -= 2.0;
         }

         if (_snowman > 2.0) {
            _snowmanx = (double)_snowman - _snowman - 7.0;
            _snowman += (double)_snowman;
         } else {
            _snowman = 0.0;
         }
      }

      int _snowmanxxxxxxxx = _snowman & 15;
      int _snowmanxxxxxxxxx = _snowman & 15;
      ctv _snowmanxxxxxxxxxx = _snowman.e().e();
      ceh _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.b();
      ceh _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx.a();
      ceh _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx;
      ceh _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx;
      int _snowmanxxxxxxxxxxxxxxx = (int)(_snowman / 3.0 + 3.0 + _snowman.nextDouble() * 0.25);
      int _snowmanxxxxxxxxxxxxxxxx = -1;
      int _snowmanxxxxxxxxxxxxxxxxx = 0;
      int _snowmanxxxxxxxxxxxxxxxxxx = 2 + _snowman.nextInt(4);
      int _snowmanxxxxxxxxxxxxxxxxxxx = _snowman + 18 + _snowman.nextInt(10);

      for (int _snowmanxxxxxxxxxxxxxxxxxxxx = Math.max(_snowman, (int)_snowman + 1); _snowmanxxxxxxxxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxxxxxxxxx--) {
         _snowmanxx.d(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxx);
         if (_snowman.d_(_snowmanxx).g() && _snowmanxxxxxxxxxxxxxxxxxxxx < (int)_snowman && _snowman.nextDouble() > 0.01) {
            _snowman.a(_snowmanxx, a, false);
         } else if (_snowman.d_(_snowmanxx).c() == cva.j && _snowmanxxxxxxxxxxxxxxxxxxxx > (int)_snowmanx && _snowmanxxxxxxxxxxxxxxxxxxxx < _snowman && _snowmanx != 0.0 && _snowman.nextDouble() > 0.15) {
            _snowman.a(_snowmanxx, a, false);
         }

         ceh _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowman.d_(_snowmanxx);
         if (_snowmanxxxxxxxxxxxxxxxxxxxxx.g()) {
            _snowmanxxxxxxxxxxxxxxxx = -1;
         } else if (_snowmanxxxxxxxxxxxxxxxxxxxxx.a(_snowman.b())) {
            if (_snowmanxxxxxxxxxxxxxxxx == -1) {
               if (_snowmanxxxxxxxxxxxxxxx <= 0) {
                  _snowmanxxxxxxxxxxxxxx = c;
                  _snowmanxxxxxxxxxxxxx = _snowman;
               } else if (_snowmanxxxxxxxxxxxxxxxxxxxx >= _snowman - 4 && _snowmanxxxxxxxxxxxxxxxxxxxx <= _snowman + 1) {
                  _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx;
                  _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx;
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxxx < _snowman && (_snowmanxxxxxxxxxxxxxx == null || _snowmanxxxxxxxxxxxxxx.g())) {
                  if (_snowman.a(_snowmanxx.d(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowman)) < 0.15F) {
                     _snowmanxxxxxxxxxxxxxx = e;
                  } else {
                     _snowmanxxxxxxxxxxxxxx = _snowman;
                  }
               }

               _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx;
               if (_snowmanxxxxxxxxxxxxxxxxxxxx >= _snowman - 1) {
                  _snowman.a(_snowmanxx, _snowmanxxxxxxxxxxxxxx, false);
               } else if (_snowmanxxxxxxxxxxxxxxxxxxxx < _snowman - 7 - _snowmanxxxxxxxxxxxxxxx) {
                  _snowmanxxxxxxxxxxxxxx = c;
                  _snowmanxxxxxxxxxxxxx = _snowman;
                  _snowman.a(_snowmanxx, d, false);
               } else {
                  _snowman.a(_snowmanxx, _snowmanxxxxxxxxxxxxx, false);
               }
            } else if (_snowmanxxxxxxxxxxxxxxxx > 0) {
               _snowmanxxxxxxxxxxxxxxxx--;
               _snowman.a(_snowmanxx, _snowmanxxxxxxxxxxxxx, false);
               if (_snowmanxxxxxxxxxxxxxxxx == 0 && _snowmanxxxxxxxxxxxxx.a(bup.C) && _snowmanxxxxxxxxxxxxxxx > 1) {
                  _snowmanxxxxxxxxxxxxxxxx = _snowman.nextInt(4) + Math.max(0, _snowmanxxxxxxxxxxxxxxxxxxxx - 63);
                  _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.a(bup.D) ? bup.hG.n() : bup.at.n();
               }
            }
         } else if (_snowmanxxxxxxxxxxxxxxxxxxxxx.a(bup.gT) && _snowmanxxxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxxxxxxxx && _snowmanxxxxxxxxxxxxxxxxxxxx > _snowmanxxxxxxxxxxxxxxxxxxx) {
            _snowman.a(_snowmanxx, b, false);
            _snowmanxxxxxxxxxxxxxxxxx++;
         }
      }
   }

   @Override
   public void a(long var1) {
      if (this.M != _snowman || this.K == null || this.L == null) {
         chx _snowman = new chx(_snowman);
         this.K = new cuc(_snowman, IntStream.rangeClosed(-3, 0));
         this.L = new cuc(_snowman, ImmutableList.of(0));
      }

      this.M = _snowman;
   }
}
