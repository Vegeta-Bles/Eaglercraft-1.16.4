import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class cox extends cpb {
   public static final Codec<cox> a = RecordCodecBuilder.create(var0 -> a(var0).apply(var0, cox::new));

   public cox(int var1, int var2, int var3) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected cpc<?> a() {
      return cpc.b;
   }

   @Override
   public List<cnl.b> a(bsb var1, Random var2, int var3, fx var4, Set<fx> var5, cra var6, cmz var7) {
      a(_snowman, _snowman.c());
      List<cnl.b> _snowman = Lists.newArrayList();
      gc _snowmanx = gc.c.a.a(_snowman);
      int _snowmanxx = _snowman - _snowman.nextInt(4) - 1;
      int _snowmanxxx = 3 - _snowman.nextInt(3);
      fx.a _snowmanxxxx = new fx.a();
      int _snowmanxxxxx = _snowman.u();
      int _snowmanxxxxxx = _snowman.w();
      int _snowmanxxxxxxx = 0;

      for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowman; _snowmanxxxxxxxx++) {
         int _snowmanxxxxxxxxx = _snowman.v() + _snowmanxxxxxxxx;
         if (_snowmanxxxxxxxx >= _snowmanxx && _snowmanxxx > 0) {
            _snowmanxxxxx += _snowmanx.i();
            _snowmanxxxxxx += _snowmanx.k();
            _snowmanxxx--;
         }

         if (a(_snowman, _snowman, (fx)_snowmanxxxx.d(_snowmanxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxx), _snowman, _snowman, _snowman)) {
            _snowmanxxxxxxx = _snowmanxxxxxxxxx + 1;
         }
      }

      _snowman.add(new cnl.b(new fx(_snowmanxxxxx, _snowmanxxxxxxx, _snowmanxxxxxx), 1, false));
      _snowmanxxxxx = _snowman.u();
      _snowmanxxxxxx = _snowman.w();
      gc _snowmanxxxxxxxx = gc.c.a.a(_snowman);
      if (_snowmanxxxxxxxx != _snowmanx) {
         int _snowmanxxxxxxxxxx = _snowmanxx - _snowman.nextInt(2) - 1;
         int _snowmanxxxxxxxxxxx = 1 + _snowman.nextInt(3);
         _snowmanxxxxxxx = 0;

         for (int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxxx < _snowman && _snowmanxxxxxxxxxxx > 0; _snowmanxxxxxxxxxxx--) {
            if (_snowmanxxxxxxxxxxxx >= 1) {
               int _snowmanxxxxxxxxxxxxx = _snowman.v() + _snowmanxxxxxxxxxxxx;
               _snowmanxxxxx += _snowmanxxxxxxxx.i();
               _snowmanxxxxxx += _snowmanxxxxxxxx.k();
               if (a(_snowman, _snowman, (fx)_snowmanxxxx.d(_snowmanxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxx), _snowman, _snowman, _snowman)) {
                  _snowmanxxxxxxx = _snowmanxxxxxxxxxxxxx + 1;
               }
            }

            _snowmanxxxxxxxxxxxx++;
         }

         if (_snowmanxxxxxxx > 1) {
            _snowman.add(new cnl.b(new fx(_snowmanxxxxx, _snowmanxxxxxxx, _snowmanxxxxxx), 0, false));
         }
      }

      return _snowman;
   }
}
