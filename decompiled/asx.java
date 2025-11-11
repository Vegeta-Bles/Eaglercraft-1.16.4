import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;

public class asx extends arv<aqm> {
   private final float b;

   public asx(float var1) {
      super(ImmutableMap.of(ayd.m, aye.b));
      this.b = _snowman;
   }

   @Override
   protected void a(aag var1, aqm var2, long var3) {
      Optional<dcn> _snowman = Optional.ofNullable(this.b(_snowman, _snowman));
      if (_snowman.isPresent()) {
         _snowman.cJ().a(ayd.m, _snowman.map(var1x -> new ayf(var1x, this.b, 0)));
      }
   }

   @Override
   protected boolean a(aag var1, aqm var2) {
      return !_snowman.e(_snowman.cB());
   }

   @Nullable
   private dcn b(aag var1, aqm var2) {
      Random _snowman = _snowman.cY();
      fx _snowmanx = _snowman.cB();

      for (int _snowmanxx = 0; _snowmanxx < 10; _snowmanxx++) {
         fx _snowmanxxx = _snowmanx.b(_snowman.nextInt(20) - 10, _snowman.nextInt(6) - 3, _snowman.nextInt(20) - 10);
         if (a(_snowman, _snowman, _snowmanxxx)) {
            return dcn.c(_snowmanxxx);
         }
      }

      return null;
   }

   public static boolean a(aag var0, aqm var1, fx var2) {
      return _snowman.e(_snowman) && (double)_snowman.a(chn.a.e, _snowman).v() <= _snowman.cE();
   }
}
