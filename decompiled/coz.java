import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class coz extends coy {
   public static final Codec<coz> b = RecordCodecBuilder.create(var0 -> a(var0).apply(var0, coz::new));

   public coz(int var1, int var2, int var3) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected cpc<?> a() {
      return cpc.d;
   }

   @Override
   public List<cnl.b> a(bsb var1, Random var2, int var3, fx var4, Set<fx> var5, cra var6, cmz var7) {
      List<cnl.b> _snowman = Lists.newArrayList();
      _snowman.addAll(super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman));

      for (int _snowmanx = _snowman - 2 - _snowman.nextInt(4); _snowmanx > _snowman / 2; _snowmanx -= 2 + _snowman.nextInt(4)) {
         float _snowmanxx = _snowman.nextFloat() * (float) (Math.PI * 2);
         int _snowmanxxx = 0;
         int _snowmanxxxx = 0;

         for (int _snowmanxxxxx = 0; _snowmanxxxxx < 5; _snowmanxxxxx++) {
            _snowmanxxx = (int)(1.5F + afm.b(_snowmanxx) * (float)_snowmanxxxxx);
            _snowmanxxxx = (int)(1.5F + afm.a(_snowmanxx) * (float)_snowmanxxxxx);
            fx _snowmanxxxxxx = _snowman.b(_snowmanxxx, _snowmanx - 3 + _snowmanxxxxx / 2, _snowmanxxxx);
            a(_snowman, _snowman, _snowmanxxxxxx, _snowman, _snowman, _snowman);
         }

         _snowman.add(new cnl.b(_snowman.b(_snowmanxxx, _snowmanx, _snowmanxxxx), -2, false));
      }

      return _snowman;
   }
}
