public class eib extends eit<baf, dtx<baf>> {
   public eib(egk<baf, dtx<baf>> var1) {
      super(_snowman);
   }

   public void a(dfm var1, eag var2, int var3, baf var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      boolean _snowman = _snowman.dV() == aqi.b;
      _snowman.a();
      float _snowmanx = 1.0F;
      float _snowmanxx = -1.0F;
      float _snowmanxxx = afm.e(_snowman.q) / 60.0F;
      if (_snowman.q < 0.0F) {
         _snowman.a(0.0, (double)(1.0F - _snowmanxxx * 0.5F), (double)(-1.0F + _snowmanxxx * 0.5F));
      } else {
         _snowman.a(0.0, (double)(1.0F + _snowmanxxx * 0.8F), (double)(-1.0F + _snowmanxxx * 0.2F));
      }

      bmb _snowmanxxxx = _snowman ? _snowman.dD() : _snowman.dE();
      djz.C().ae().a(_snowman, _snowmanxxxx, ebm.b.h, false, _snowman, _snowman, _snowman);
      _snowman.b();
   }
}
