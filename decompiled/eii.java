public class eii extends eit<bbd, duk<bbd>> {
   private final duk<bbd> a = new duk<>(0.1F);

   public eii(egk<bbd, duk<bbd>> var1) {
      super(_snowman);
   }

   public void a(dfm var1, eag var2, int var3, bbd var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      bmb _snowman = _snowman.eL();
      if (_snowman.b() instanceof blw) {
         blw _snowmanx = (blw)_snowman.b();
         this.aC_().a(this.a);
         this.a.a(_snowman, _snowman, _snowman, _snowman);
         this.a.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         float _snowmanxx;
         float _snowmanxxx;
         float _snowmanxxxx;
         if (_snowmanx instanceof bla) {
            int _snowmanxxxxx = ((bla)_snowmanx).b(_snowman);
            _snowmanxx = (float)(_snowmanxxxxx >> 16 & 0xFF) / 255.0F;
            _snowmanxxx = (float)(_snowmanxxxxx >> 8 & 0xFF) / 255.0F;
            _snowmanxxxx = (float)(_snowmanxxxxx & 0xFF) / 255.0F;
         } else {
            _snowmanxx = 1.0F;
            _snowmanxxx = 1.0F;
            _snowmanxxxx = 1.0F;
         }

         dfq _snowmanxxxxx = _snowman.getBuffer(eao.d(_snowmanx.f()));
         this.a.a(_snowman, _snowmanxxxxx, _snowman, ejw.a, _snowmanxx, _snowmanxxx, _snowmanxxxx, 1.0F);
      }
   }
}
