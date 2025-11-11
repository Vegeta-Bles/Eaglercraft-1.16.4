import com.google.common.collect.ImmutableMap;
import java.util.function.Predicate;

public class atl extends arv<aqm> {
   private final Predicate<aqm> b;
   private final float c;

   public atl(aqo var1, float var2) {
      this(var1x -> _snowman.equals(var1x.X().e()), _snowman);
   }

   public atl(aqe<?> var1, float var2) {
      this(var1x -> _snowman.equals(var1x.X()), _snowman);
   }

   public atl(float var1) {
      this(var0 -> true, _snowman);
   }

   public atl(Predicate<aqm> var1, float var2) {
      super(ImmutableMap.of(ayd.n, aye.b, ayd.h, aye.a));
      this.b = _snowman;
      this.c = _snowman * _snowman;
   }

   @Override
   protected boolean a(aag var1, aqm var2) {
      return _snowman.cJ().c(ayd.h).get().stream().anyMatch(this.b);
   }

   @Override
   protected void a(aag var1, aqm var2, long var3) {
      arf<?> _snowman = _snowman.cJ();
      _snowman.c(ayd.h)
         .ifPresent(
            var3x -> var3x.stream()
                  .filter(this.b)
                  .filter(var2x -> var2x.h(_snowman) <= (double)this.c)
                  .findFirst()
                  .ifPresent(var1x -> _snowman.a(ayd.n, new asd(var1x, true)))
         );
   }
}
