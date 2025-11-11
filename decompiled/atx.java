import com.google.common.collect.ImmutableMap;
import java.util.function.BiPredicate;

public class atx extends arv<aqm> {
   private final int b;
   private final BiPredicate<aqm, aqm> c;

   public atx(int var1, BiPredicate<aqm, aqm> var2) {
      super(ImmutableMap.of(ayd.o, aye.a, ayd.L, aye.c, ayd.S, aye.b, ayd.T, aye.c));
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   protected boolean a(aag var1, aqm var2) {
      return this.a(_snowman).dl();
   }

   @Override
   protected void a(aag var1, aqm var2, long var3) {
      aqm _snowman = this.a(_snowman);
      if (this.c.test(_snowman, _snowman)) {
         _snowman.cJ().a(ayd.T, true, (long)this.b);
      }

      _snowman.cJ().a(ayd.S, _snowman.cB(), (long)this.b);
      if (_snowman.X() != aqe.bc || _snowman.V().b(brt.F)) {
         _snowman.cJ().b(ayd.o);
         _snowman.cJ().b(ayd.L);
      }
   }

   private aqm a(aqm var1) {
      return _snowman.cJ().c(ayd.o).get();
   }
}
