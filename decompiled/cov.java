import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class cov extends cpb {
   public static final Codec<cov> a = RecordCodecBuilder.create(var0 -> a(var0).apply(var0, cov::new));

   public cov(int var1, int var2, int var3) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected cpc<?> a() {
      return cpc.e;
   }

   @Override
   public List<cnl.b> a(bsb var1, Random var2, int var3, fx var4, Set<fx> var5, cra var6, cmz var7) {
      List<cnl.b> _snowman = Lists.newArrayList();
      fx _snowmanx = _snowman.c();
      a(_snowman, _snowmanx);
      a(_snowman, _snowmanx.g());
      a(_snowman, _snowmanx.e());
      a(_snowman, _snowmanx.e().g());
      gc _snowmanxx = gc.c.a.a(_snowman);
      int _snowmanxxx = _snowman - _snowman.nextInt(4);
      int _snowmanxxxx = 2 - _snowman.nextInt(3);
      int _snowmanxxxxx = _snowman.u();
      int _snowmanxxxxxx = _snowman.v();
      int _snowmanxxxxxxx = _snowman.w();
      int _snowmanxxxxxxxx = _snowmanxxxxx;
      int _snowmanxxxxxxxxx = _snowmanxxxxxxx;
      int _snowmanxxxxxxxxxx = _snowmanxxxxxx + _snowman - 1;

      for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx < _snowman; _snowmanxxxxxxxxxxx++) {
         if (_snowmanxxxxxxxxxxx >= _snowmanxxx && _snowmanxxxx > 0) {
            _snowmanxxxxxxxx += _snowmanxx.i();
            _snowmanxxxxxxxxx += _snowmanxx.k();
            _snowmanxxxx--;
         }

         int _snowmanxxxxxxxxxxxx = _snowmanxxxxxx + _snowmanxxxxxxxxxxx;
         fx _snowmanxxxxxxxxxxxxx = new fx(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxx);
         if (cld.d(_snowman, _snowmanxxxxxxxxxxxxx)) {
            a(_snowman, _snowman, _snowmanxxxxxxxxxxxxx, _snowman, _snowman, _snowman);
            a(_snowman, _snowman, _snowmanxxxxxxxxxxxxx.g(), _snowman, _snowman, _snowman);
            a(_snowman, _snowman, _snowmanxxxxxxxxxxxxx.e(), _snowman, _snowman, _snowman);
            a(_snowman, _snowman, _snowmanxxxxxxxxxxxxx.g().e(), _snowman, _snowman, _snowman);
         }
      }

      _snowman.add(new cnl.b(new fx(_snowmanxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx), 0, true));

      for (int _snowmanxxxxxxxxxxx = -1; _snowmanxxxxxxxxxxx <= 2; _snowmanxxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxxx = -1; _snowmanxxxxxxxxxxxx <= 2; _snowmanxxxxxxxxxxxx++) {
            if ((_snowmanxxxxxxxxxxx < 0 || _snowmanxxxxxxxxxxx > 1 || _snowmanxxxxxxxxxxxx < 0 || _snowmanxxxxxxxxxxxx > 1) && _snowman.nextInt(3) <= 0) {
               int _snowmanxxxxxxxxxxxxx = _snowman.nextInt(3) + 2;

               for (int _snowmanxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxx++) {
                  a(_snowman, _snowman, new fx(_snowmanxxxxx + _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx - _snowmanxxxxxxxxxxxxxx - 1, _snowmanxxxxxxx + _snowmanxxxxxxxxxxxx), _snowman, _snowman, _snowman);
               }

               _snowman.add(new cnl.b(new fx(_snowmanxxxxxxxx + _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx + _snowmanxxxxxxxxxxxx), 0, false));
            }
         }
      }

      return _snowman;
   }
}
