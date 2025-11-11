public class ehb extends efv<bhv> {
   public ehb(eet var1) {
      super(_snowman);
   }

   protected void a(bhv var1, float var2, ceh var3, dfm var4, eag var5, int var6) {
      int _snowman = _snowman.v();
      if (_snowman > -1 && (float)_snowman - _snowman + 1.0F < 10.0F) {
         float _snowmanx = 1.0F - ((float)_snowman - _snowman + 1.0F) / 10.0F;
         _snowmanx = afm.a(_snowmanx, 0.0F, 1.0F);
         _snowmanx *= _snowmanx;
         _snowmanx *= _snowmanx;
         float _snowmanxx = 1.0F + _snowmanx * 0.3F;
         _snowman.a(_snowmanxx, _snowmanxx, _snowmanxx);
      }

      a(_snowman, _snowman, _snowman, _snowman, _snowman > -1 && _snowman / 5 % 2 == 0);
   }

   public static void a(ceh var0, dfm var1, eag var2, int var3, boolean var4) {
      int _snowman;
      if (_snowman) {
         _snowman = ejw.a(ejw.a(1.0F), 10);
      } else {
         _snowman = ejw.a;
      }

      djz.C().ab().a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }
}
