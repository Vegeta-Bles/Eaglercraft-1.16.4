import com.google.common.collect.ImmutableMap;

public class asu extends arv<aqn> {
   public asu(int var1, int var2) {
      super(ImmutableMap.of(ayd.n, aye.a), _snowman, _snowman);
   }

   protected boolean a(aag var1, aqn var2, long var3) {
      return _snowman.cJ().c(ayd.n).filter(var1x -> var1x.a(_snowman)).isPresent();
   }

   protected void b(aag var1, aqn var2, long var3) {
      _snowman.cJ().b(ayd.n);
   }

   protected void c(aag var1, aqn var2, long var3) {
      _snowman.cJ().c(ayd.n).ifPresent(var1x -> _snowman.t().a(var1x.a()));
   }
}
