import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.function.Predicate;

public class aso<E extends aqm, T extends aqm> extends arv<E> {
   private final int b;
   private final float c;
   private final aqe<? extends T> d;
   private final int e;
   private final Predicate<T> f;
   private final Predicate<E> g;
   private final ayd<T> h;

   public aso(aqe<? extends T> var1, int var2, Predicate<E> var3, Predicate<T> var4, ayd<T> var5, float var6, int var7) {
      super(ImmutableMap.of(ayd.n, aye.c, ayd.m, aye.b, ayd.h, aye.a));
      this.d = _snowman;
      this.c = _snowman;
      this.e = _snowman * _snowman;
      this.b = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
   }

   public static <T extends aqm> aso<aqm, T> a(aqe<? extends T> var0, int var1, ayd<T> var2, float var3, int var4) {
      return new aso<>(_snowman, _snowman, var0x -> true, var0x -> true, _snowman, _snowman, _snowman);
   }

   @Override
   protected boolean a(aag var1, E var2) {
      return this.g.test(_snowman) && this.a(_snowman);
   }

   private boolean a(E var1) {
      List<aqm> _snowman = _snowman.cJ().c(ayd.h).get();
      return _snowman.stream().anyMatch(this::b);
   }

   private boolean b(aqm var1) {
      return this.d.equals(_snowman.X()) && this.f.test((T)_snowman);
   }

   @Override
   protected void a(aag var1, E var2, long var3) {
      arf<?> _snowman = _snowman.cJ();
      _snowman.c(ayd.h)
         .ifPresent(
            var3x -> var3x.stream()
                  .filter(var1x -> this.d.equals(var1x.X()))
                  .map(var0 -> (aqm)var0)
                  .filter(var2x -> var2x.h(_snowman) <= (double)this.e)
                  .filter(this.f)
                  .findFirst()
                  .ifPresent(var2x -> {
                     _snowman.a(this.h, (T)var2x);
                     _snowman.a(ayd.n, new asd(var2x, true));
                     _snowman.a(ayd.m, new ayf(new asd(var2x, false), this.c, this.b));
                  })
         );
   }
}
