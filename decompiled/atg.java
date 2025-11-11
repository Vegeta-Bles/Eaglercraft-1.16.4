import com.google.common.collect.ImmutableMap;

public class atg extends arv<aqm> {
   public atg() {
      super(ImmutableMap.of(ayd.e, aye.a));
   }

   @Override
   protected boolean a(aag var1, aqm var2) {
      return _snowman.t.nextFloat() > 0.95F;
   }

   @Override
   protected void a(aag var1, aqm var2, long var3) {
      arf<?> _snowman = _snowman.cJ();
      fx _snowmanx = _snowman.c(ayd.e).get().b();
      if (_snowmanx.a(_snowman.cB(), 3.0)) {
         ceh _snowmanxx = _snowman.d_(_snowmanx);
         if (_snowmanxx.a(bup.mb)) {
            bum _snowmanxxx = (bum)_snowmanxx.b();
            _snowmanxxx.a(_snowman, _snowmanx, null);
         }
      }
   }
}
