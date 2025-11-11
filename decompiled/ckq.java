import com.mojang.serialization.Codec;
import java.util.Random;

public class ckq extends cjl<clu> {
   public ckq(Codec<clu> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, clu var5) {
      int _snowman = 0;
      int _snowmanx = _snowman.a().a(_snowman);

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
         int _snowmanxxx = _snowman.nextInt(8) - _snowman.nextInt(8);
         int _snowmanxxxx = _snowman.nextInt(8) - _snowman.nextInt(8);
         int _snowmanxxxxx = _snowman.a(chn.a.d, _snowman.u() + _snowmanxxx, _snowman.w() + _snowmanxxxx);
         fx _snowmanxxxxxx = new fx(_snowman.u() + _snowmanxxx, _snowmanxxxxx, _snowman.w() + _snowmanxxxx);
         ceh _snowmanxxxxxxx = bup.kU.n().a(bzq.a, Integer.valueOf(_snowman.nextInt(4) + 1));
         if (_snowman.d_(_snowmanxxxxxx).a(bup.A) && _snowmanxxxxxxx.a(_snowman, _snowmanxxxxxx)) {
            _snowman.a(_snowmanxxxxxx, _snowmanxxxxxxx, 2);
            _snowman++;
         }
      }

      return _snowman > 0;
   }
}
