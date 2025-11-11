import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public class cqg<DC extends clw> extends cpy<DC> {
   public cqg(Codec<DC> var1) {
      super(_snowman);
   }

   @Override
   protected chn.a a(DC var1) {
      return chn.a.e;
   }

   @Override
   public Stream<fx> a(cpv var1, Random var2, DC var3, fx var4) {
      int _snowman = _snowman.u();
      int _snowmanx = _snowman.w();
      int _snowmanxx = _snowman.a(this.a(_snowman), _snowman, _snowmanx);
      return _snowmanxx == 0 ? Stream.of() : Stream.of(new fx(_snowman, _snowman.nextInt(_snowmanxx * 2), _snowmanx));
   }
}
