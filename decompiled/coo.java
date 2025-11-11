import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class coo extends cor {
   public static final Codec<coo> a = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(coo::new, var0 -> var0.b).codec();
   private final float b;

   public coo(float var1) {
      this.b = _snowman;
   }

   @Override
   protected cos<?> a() {
      return cos.d;
   }

   @Override
   public void a(bsr var1, Random var2, List<fx> var3, List<fx> var4, Set<fx> var5, cra var6) {
      if (!(_snowman.nextFloat() >= this.b)) {
         gc _snowman = buk.a(_snowman);
         int _snowmanx = !_snowman.isEmpty() ? Math.max(_snowman.get(0).v() - 1, _snowman.get(0).v()) : Math.min(_snowman.get(0).v() + 1 + _snowman.nextInt(3), _snowman.get(_snowman.size() - 1).v());
         List<fx> _snowmanxx = _snowman.stream().filter(var1x -> var1x.v() == _snowman).collect(Collectors.toList());
         if (!_snowmanxx.isEmpty()) {
            fx _snowmanxxx = _snowmanxx.get(_snowman.nextInt(_snowmanxx.size()));
            fx _snowmanxxxx = _snowmanxxx.a(_snowman);
            if (cjl.b(_snowman, _snowmanxxxx) && cjl.b(_snowman, _snowmanxxxx.a(gc.d))) {
               ceh _snowmanxxxxx = bup.nc.n().a(buk.a, gc.d);
               this.a(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowman, _snowman);
               ccj _snowmanxxxxxx = _snowman.c(_snowmanxxxx);
               if (_snowmanxxxxxx instanceof ccg) {
                  ccg _snowmanxxxxxxx = (ccg)_snowmanxxxxxx;
                  int _snowmanxxxxxxxx = 2 + _snowman.nextInt(2);

                  for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowmanxxxxxxxx; _snowmanxxxxxxxxx++) {
                     baa _snowmanxxxxxxxxxx = new baa(aqe.e, _snowman.E());
                     _snowmanxxxxxxx.a(_snowmanxxxxxxxxxx, false, _snowman.nextInt(599));
                  }
               }
            }
         }
      }
   }
}
