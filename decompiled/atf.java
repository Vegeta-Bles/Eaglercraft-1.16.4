import com.google.common.collect.ImmutableMap;

public class atf extends arv<aqm> {
   public atf() {
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
      if (_snowmanx == null || _snowmanx.d() || _snowmanx.f()) {
         _snowman.b(bhf.b);
         _snowman.a(_snowman.U(), _snowman.T());
      }
   }
}
