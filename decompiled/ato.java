import com.google.common.collect.ImmutableMap;

public class ato extends arv<aqm> {
   public ato() {
      super(ImmutableMap.of());
   }

   @Override
   protected boolean a(aag var1, aqm var2) {
      return _snowman.t.nextInt(20) == 0;
   }

   @Override
   protected void a(aag var1, aqm var2, long var3) {
      arf<?> _snowman = _snowman.cJ();
      bhb _snowmanx = _snowman.b_(_snowman.cB());
      if (_snowmanx != null) {
         if (_snowmanx.c() && !_snowmanx.b()) {
            _snowman.b(bhf.h);
            _snowman.a(bhf.h);
         } else {
            _snowman.b(bhf.i);
            _snowman.a(bhf.i);
         }
      }
   }
}
