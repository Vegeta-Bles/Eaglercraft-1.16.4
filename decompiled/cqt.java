import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class cqt extends cqc<clu> {
   public cqt(Codec<clu> var1) {
      super(_snowman);
   }

   public Stream<fx> a(cpv var1, Random var2, clu var3, fx var4) {
      List<fx> _snowman = Lists.newArrayList();
      int _snowmanx = 0;

      boolean _snowmanxx;
      do {
         _snowmanxx = false;

         for (int _snowmanxxx = 0; _snowmanxxx < _snowman.a().a(_snowman); _snowmanxxx++) {
            int _snowmanxxxx = _snowman.nextInt(16) + _snowman.u();
            int _snowmanxxxxx = _snowman.nextInt(16) + _snowman.w();
            int _snowmanxxxxxx = _snowman.a(chn.a.e, _snowmanxxxx, _snowmanxxxxx);
            int _snowmanxxxxxxx = a(_snowman, _snowmanxxxx, _snowmanxxxxxx, _snowmanxxxxx, _snowmanx);
            if (_snowmanxxxxxxx != Integer.MAX_VALUE) {
               _snowman.add(new fx(_snowmanxxxx, _snowmanxxxxxxx, _snowmanxxxxx));
               _snowmanxx = true;
            }
         }

         _snowmanx++;
      } while (_snowmanxx);

      return _snowman.stream();
   }

   private static int a(cpv var0, int var1, int var2, int var3, int var4) {
      fx.a _snowman = new fx.a(_snowman, _snowman, _snowman);
      int _snowmanx = 0;
      ceh _snowmanxx = _snowman.a(_snowman);

      for (int _snowmanxxx = _snowman; _snowmanxxx >= 1; _snowmanxxx--) {
         _snowman.p(_snowmanxxx - 1);
         ceh _snowmanxxxx = _snowman.a(_snowman);
         if (!a(_snowmanxxxx) && a(_snowmanxx) && !_snowmanxxxx.a(bup.z)) {
            if (_snowmanx == _snowman) {
               return _snowman.v() + 1;
            }

            _snowmanx++;
         }

         _snowmanxx = _snowmanxxxx;
      }

      return Integer.MAX_VALUE;
   }

   private static boolean a(ceh var0) {
      return _snowman.g() || _snowman.a(bup.A) || _snowman.a(bup.B);
   }
}
