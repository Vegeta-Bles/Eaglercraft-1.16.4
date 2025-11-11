import com.google.common.collect.ImmutableMap;

public class ars<E extends apy> extends arv<E> {
   private final afh b;
   private final float c;

   public ars(afh var1, float var2) {
      super(ImmutableMap.of(ayd.I, aye.a, ayd.m, aye.b));
      this.b = _snowman;
      this.c = _snowman;
   }

   protected boolean a(aag var1, E var2) {
      if (!_snowman.w_()) {
         return false;
      } else {
         apy _snowman = this.a(_snowman);
         return _snowman.a(_snowman, (double)(this.b.b() + 1)) && !_snowman.a(_snowman, (double)this.b.a());
      }
   }

   protected void a(aag var1, E var2, long var3) {
      arw.a(_snowman, this.a(_snowman), this.c, this.b.a() - 1);
   }

   private apy a(E var1) {
      return _snowman.cJ().c(ayd.I).get();
   }
}
