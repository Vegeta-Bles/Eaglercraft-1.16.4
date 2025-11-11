import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class atm extends arv<aqm> {
   private final int b;
   private final int c;
   private int d;

   public atm(int var1, int var2) {
      super(ImmutableMap.of(ayd.B, aye.a, ayd.C, aye.a));
      this.c = _snowman * 20;
      this.d = 0;
      this.b = _snowman;
   }

   @Override
   protected void a(aag var1, aqm var2, long var3) {
      arf<?> _snowman = _snowman.cJ();
      Optional<Long> _snowmanx = _snowman.c(ayd.C);
      boolean _snowmanxx = _snowmanx.get() + 300L <= _snowman;
      if (this.d <= this.c && !_snowmanxx) {
         fx _snowmanxxx = _snowman.c(ayd.B).get().b();
         if (_snowmanxxx.a(_snowman.cB(), (double)this.b)) {
            this.d++;
         }
      } else {
         _snowman.b(ayd.C);
         _snowman.b(ayd.B);
         _snowman.a(_snowman.U(), _snowman.T());
         this.d = 0;
      }
   }
}
