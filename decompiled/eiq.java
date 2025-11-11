public class eiq extends eit<bal, duy<bal>> {
   public eiq(egk<bal, duy<bal>> var1) {
      super(_snowman);
   }

   public void a(dfm var1, eag var2, int var3, bal var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      bmb _snowman = _snowman.b(aqf.a);
      if (_snowman.eM() && !_snowman.ff()) {
         float _snowmanx = -0.6F;
         float _snowmanxx = 1.4F;
         if (_snowman.eO()) {
            _snowmanx -= 0.2F * afm.a(_snowman * 0.6F) + 0.2F;
            _snowmanxx -= 0.09F * afm.a(_snowman * 0.6F);
         }

         _snowman.a();
         _snowman.a(0.1F, (double)_snowmanxx, (double)_snowmanx);
         djz.C().ae().a(_snowman, _snowman, ebm.b.h, false, _snowman, _snowman, _snowman);
         _snowman.b();
      }
   }
}
