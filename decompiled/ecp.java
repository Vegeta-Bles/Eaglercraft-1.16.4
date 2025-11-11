public class ecp extends ece<cdi> {
   public ecp(ecd var1) {
      super(_snowman);
   }

   public void a(cdi var1, float var2, dfm var3, eag var4, int var5, int var6) {
      _snowman.a();
      _snowman.a(0.5, 0.0, 0.5);
      bqz _snowman = _snowman.d();
      aqa _snowmanx = _snowman.d();
      if (_snowmanx != null) {
         float _snowmanxx = 0.53125F;
         float _snowmanxxx = Math.max(_snowmanx.cy(), _snowmanx.cz());
         if ((double)_snowmanxxx > 1.0) {
            _snowmanxx /= _snowmanxxx;
         }

         _snowman.a(0.0, 0.4F, 0.0);
         _snowman.a(g.d.a((float)afm.d((double)_snowman, _snowman.f(), _snowman.e()) * 10.0F));
         _snowman.a(0.0, -0.2F, 0.0);
         _snowman.a(g.b.a(-30.0F));
         _snowman.a(_snowmanxx, _snowmanxx, _snowmanxx);
         djz.C().ac().a(_snowmanx, 0.0, 0.0, 0.0, 0.0F, _snowman, _snowman, _snowman, _snowman);
      }

      _snowman.b();
   }
}
