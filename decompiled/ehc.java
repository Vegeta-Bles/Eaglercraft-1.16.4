public class ehc extends eeu<bcw> {
   public ehc(eet var1) {
      super(_snowman);
      this.c = 0.5F;
   }

   public void a(bcw var1, float var2, float var3, dfm var4, eag var5, int var6) {
      _snowman.a();
      _snowman.a(0.0, 0.5, 0.0);
      if ((float)_snowman.i() - _snowman + 1.0F < 10.0F) {
         float _snowman = 1.0F - ((float)_snowman.i() - _snowman + 1.0F) / 10.0F;
         _snowman = afm.a(_snowman, 0.0F, 1.0F);
         _snowman *= _snowman;
         _snowman *= _snowman;
         float _snowmanx = 1.0F + _snowman * 0.3F;
         _snowman.a(_snowmanx, _snowmanx, _snowmanx);
      }

      _snowman.a(g.d.a(-90.0F));
      _snowman.a(-0.5, -0.5, 0.5);
      _snowman.a(g.d.a(90.0F));
      ehb.a(bup.bH.n(), _snowman, _snowman, _snowman, _snowman.i() / 5 % 2 == 0);
      _snowman.b();
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public vk a(bcw var1) {
      return ekb.d;
   }
}
