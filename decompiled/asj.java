import com.google.common.collect.ImmutableMap;

public class asj extends arv<bfj> {
   private final float b;
   private final int c;

   public asj(float var1, int var2) {
      super(ImmutableMap.of(ayd.m, aye.b));
      this.b = _snowman;
      this.c = _snowman;
   }

   protected boolean a(aag var1, bfj var2) {
      return !_snowman.a_(_snowman.cB());
   }

   protected void a(aag var1, bfj var2, long var3) {
      azo _snowman = _snowman.y();
      int _snowmanx = _snowman.a(gp.a(_snowman.cB()));
      dcn _snowmanxx = null;

      for (int _snowmanxxx = 0; _snowmanxxx < 5; _snowmanxxx++) {
         dcn _snowmanxxxx = azj.a(_snowman, 15, 7, var1x -> (double)(-_snowman.b(gp.a(var1x))));
         if (_snowmanxxxx != null) {
            int _snowmanxxxxx = _snowman.a(gp.a(new fx(_snowmanxxxx)));
            if (_snowmanxxxxx < _snowmanx) {
               _snowmanxx = _snowmanxxxx;
               break;
            }

            if (_snowmanxxxxx == _snowmanx) {
               _snowmanxx = _snowmanxxxx;
            }
         }
      }

      if (_snowmanxx != null) {
         _snowman.cJ().a(ayd.m, new ayf(_snowmanxx, this.b, this.c));
      }
   }
}
