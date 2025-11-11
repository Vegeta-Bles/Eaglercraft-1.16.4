import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.function.Predicate;

public class atn extends arv<aqm> {
   private final aqe<?> b;
   private final int c;
   private final Predicate<aqm> d;
   private final Predicate<aqm> e;

   public atn(aqe<?> var1, int var2, Predicate<aqm> var3, Predicate<aqm> var4) {
      super(ImmutableMap.of(ayd.n, aye.c, ayd.q, aye.b, ayd.h, aye.a));
      this.b = _snowman;
      this.c = _snowman * _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }

   public atn(aqe<?> var1, int var2) {
      this(_snowman, _snowman, var0 -> true, var0 -> true);
   }

   @Override
   public boolean a(aag var1, aqm var2) {
      return this.e.test(_snowman) && this.b(_snowman).stream().anyMatch(this::a);
   }

   @Override
   public void a(aag var1, aqm var2, long var3) {
      super.a(_snowman, _snowman, _snowman);
      arf<?> _snowman = _snowman.cJ();
      _snowman.c(ayd.h).ifPresent(var3x -> var3x.stream().filter(var2x -> var2x.h(_snowman) <= (double)this.c).filter(this::a).findFirst().ifPresent(var1x -> {
            _snowman.a(ayd.q, var1x);
            _snowman.a(ayd.n, new asd(var1x, true));
         }));
   }

   private boolean a(aqm var1) {
      return this.b.equals(_snowman.X()) && this.d.test(_snowman);
   }

   private List<aqm> b(aqm var1) {
      return _snowman.cJ().c(ayd.h).get();
   }
}
