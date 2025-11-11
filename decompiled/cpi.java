import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public abstract class cpi<DC extends clw> extends cpy<DC> {
   public cpi(Codec<DC> var1) {
      super(_snowman);
   }

   @Override
   public Stream<fx> a(cpv var1, Random var2, DC var3, fx var4) {
      int _snowman = _snowman.u();
      int _snowmanx = _snowman.w();
      int _snowmanxx = _snowman.a(this.a(_snowman), _snowman, _snowmanx);
      return _snowmanxx > 0 ? Stream.of(new fx(_snowman, _snowmanxx, _snowmanx)) : Stream.of();
   }
}
