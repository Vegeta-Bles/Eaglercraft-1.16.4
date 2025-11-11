import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;

public class cki extends cjl<cmj> {
   public cki(Codec<cmj> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmj var5) {
      float _snowman = _snowman.nextFloat() * (float) Math.PI;
      float _snowmanx = (float)_snowman.c / 8.0F;
      int _snowmanxx = afm.f(((float)_snowman.c / 16.0F * 2.0F + 1.0F) / 2.0F);
      double _snowmanxxx = (double)_snowman.u() + Math.sin((double)_snowman) * (double)_snowmanx;
      double _snowmanxxxx = (double)_snowman.u() - Math.sin((double)_snowman) * (double)_snowmanx;
      double _snowmanxxxxx = (double)_snowman.w() + Math.cos((double)_snowman) * (double)_snowmanx;
      double _snowmanxxxxxx = (double)_snowman.w() - Math.cos((double)_snowman) * (double)_snowmanx;
      int _snowmanxxxxxxx = 2;
      double _snowmanxxxxxxxx = (double)(_snowman.v() + _snowman.nextInt(3) - 2);
      double _snowmanxxxxxxxxx = (double)(_snowman.v() + _snowman.nextInt(3) - 2);
      int _snowmanxxxxxxxxxx = _snowman.u() - afm.f(_snowmanx) - _snowmanxx;
      int _snowmanxxxxxxxxxxx = _snowman.v() - 2 - _snowmanxx;
      int _snowmanxxxxxxxxxxxx = _snowman.w() - afm.f(_snowmanx) - _snowmanxx;
      int _snowmanxxxxxxxxxxxxx = 2 * (afm.f(_snowmanx) + _snowmanxx);
      int _snowmanxxxxxxxxxxxxxx = 2 * (2 + _snowmanxx);

      for (int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx++) {
            if (_snowmanxxxxxxxxxxx <= _snowman.a(chn.a.c, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx)) {
               return this.a(
                  _snowman, _snowman, _snowman, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx
               );
            }
         }
      }

      return false;
   }

   protected boolean a(
      bry var1,
      Random var2,
      cmj var3,
      double var4,
      double var6,
      double var8,
      double var10,
      double var12,
      double var14,
      int var16,
      int var17,
      int var18,
      int var19,
      int var20
   ) {
      int _snowman = 0;
      BitSet _snowmanx = new BitSet(_snowman * _snowman * _snowman);
      fx.a _snowmanxx = new fx.a();
      int _snowmanxxx = _snowman.c;
      double[] _snowmanxxxx = new double[_snowmanxxx * 4];

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxx; _snowmanxxxxx++) {
         float _snowmanxxxxxx = (float)_snowmanxxxxx / (float)_snowmanxxx;
         double _snowmanxxxxxxx = afm.d((double)_snowmanxxxxxx, _snowman, _snowman);
         double _snowmanxxxxxxxx = afm.d((double)_snowmanxxxxxx, _snowman, _snowman);
         double _snowmanxxxxxxxxx = afm.d((double)_snowmanxxxxxx, _snowman, _snowman);
         double _snowmanxxxxxxxxxx = _snowman.nextDouble() * (double)_snowmanxxx / 16.0;
         double _snowmanxxxxxxxxxxx = ((double)(afm.a((float) Math.PI * _snowmanxxxxxx) + 1.0F) * _snowmanxxxxxxxxxx + 1.0) / 2.0;
         _snowmanxxxx[_snowmanxxxxx * 4 + 0] = _snowmanxxxxxxx;
         _snowmanxxxx[_snowmanxxxxx * 4 + 1] = _snowmanxxxxxxxx;
         _snowmanxxxx[_snowmanxxxxx * 4 + 2] = _snowmanxxxxxxxxx;
         _snowmanxxxx[_snowmanxxxxx * 4 + 3] = _snowmanxxxxxxxxxxx;
      }

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxx - 1; _snowmanxxxxx++) {
         if (!(_snowmanxxxx[_snowmanxxxxx * 4 + 3] <= 0.0)) {
            for (int _snowmanxxxxxx = _snowmanxxxxx + 1; _snowmanxxxxxx < _snowmanxxx; _snowmanxxxxxx++) {
               if (!(_snowmanxxxx[_snowmanxxxxxx * 4 + 3] <= 0.0)) {
                  double _snowmanxxxxxxx = _snowmanxxxx[_snowmanxxxxx * 4 + 0] - _snowmanxxxx[_snowmanxxxxxx * 4 + 0];
                  double _snowmanxxxxxxxx = _snowmanxxxx[_snowmanxxxxx * 4 + 1] - _snowmanxxxx[_snowmanxxxxxx * 4 + 1];
                  double _snowmanxxxxxxxxx = _snowmanxxxx[_snowmanxxxxx * 4 + 2] - _snowmanxxxx[_snowmanxxxxxx * 4 + 2];
                  double _snowmanxxxxxxxxxx = _snowmanxxxx[_snowmanxxxxx * 4 + 3] - _snowmanxxxx[_snowmanxxxxxx * 4 + 3];
                  if (_snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx > _snowmanxxxxxxx * _snowmanxxxxxxx + _snowmanxxxxxxxx * _snowmanxxxxxxxx + _snowmanxxxxxxxxx * _snowmanxxxxxxxxx) {
                     if (_snowmanxxxxxxxxxx > 0.0) {
                        _snowmanxxxx[_snowmanxxxxxx * 4 + 3] = -1.0;
                     } else {
                        _snowmanxxxx[_snowmanxxxxx * 4 + 3] = -1.0;
                     }
                  }
               }
            }
         }
      }

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxx; _snowmanxxxxxxx++) {
         double _snowmanxxxxxxxx = _snowmanxxxx[_snowmanxxxxxxx * 4 + 3];
         if (!(_snowmanxxxxxxxx < 0.0)) {
            double _snowmanxxxxxxxxx = _snowmanxxxx[_snowmanxxxxxxx * 4 + 0];
            double _snowmanxxxxxxxxxx = _snowmanxxxx[_snowmanxxxxxxx * 4 + 1];
            double _snowmanxxxxxxxxxxx = _snowmanxxxx[_snowmanxxxxxxx * 4 + 2];
            int _snowmanxxxxxxxxxxxx = Math.max(afm.c(_snowmanxxxxxxxxx - _snowmanxxxxxxxx), _snowman);
            int _snowmanxxxxxxxxxxxxx = Math.max(afm.c(_snowmanxxxxxxxxxx - _snowmanxxxxxxxx), _snowman);
            int _snowmanxxxxxxxxxxxxxx = Math.max(afm.c(_snowmanxxxxxxxxxxx - _snowmanxxxxxxxx), _snowman);
            int _snowmanxxxxxxxxxxxxxxx = Math.max(afm.c(_snowmanxxxxxxxxx + _snowmanxxxxxxxx), _snowmanxxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxxx = Math.max(afm.c(_snowmanxxxxxxxxxx + _snowmanxxxxxxxx), _snowmanxxxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxxxx = Math.max(afm.c(_snowmanxxxxxxxxxxx + _snowmanxxxxxxxx), _snowmanxxxxxxxxxxxxxx);

            for (int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxx++) {
               double _snowmanxxxxxxxxxxxxxxxxxxx = ((double)_snowmanxxxxxxxxxxxxxxxxxx + 0.5 - _snowmanxxxxxxxxx) / _snowmanxxxxxxxx;
               if (_snowmanxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxx < 1.0) {
                  for (int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxx++) {
                     double _snowmanxxxxxxxxxxxxxxxxxxxxx = ((double)_snowmanxxxxxxxxxxxxxxxxxxxx + 0.5 - _snowmanxxxxxxxxxx) / _snowmanxxxxxxxx;
                     if (_snowmanxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxx < 1.0) {
                        for (int _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxxxx++) {
                           double _snowmanxxxxxxxxxxxxxxxxxxxxxxx = ((double)_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.5 - _snowmanxxxxxxxxxxx) / _snowmanxxxxxxxx;
                           if (_snowmanxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxx
                                 + _snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxx
                                 + _snowmanxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxx
                              < 1.0) {
                              int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx - _snowman + (_snowmanxxxxxxxxxxxxxxxxxxxx - _snowman) * _snowman + (_snowmanxxxxxxxxxxxxxxxxxxxxxx - _snowman) * _snowman * _snowman;
                              if (!_snowmanx.get(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx)) {
                                 _snowmanx.set(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx);
                                 _snowmanxx.d(_snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx);
                                 if (_snowman.b.a(_snowman.d_(_snowmanxx), _snowman)) {
                                    _snowman.a(_snowmanxx, _snowman.d, 2);
                                    _snowman++;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return _snowman > 0;
   }
}
