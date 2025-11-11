public class efk extends efj<bdo> {
   private static final vk a = new vk("textures/entity/illager/illusioner.png");

   public efk(eet var1) {
      super(_snowman, new dun<>(0.0F, 0.0F, 64, 64), 0.5F);
      this.a(new ein<bdo, dun<bdo>>(this) {
         public void a(dfm var1, eag var2, int var3, bdo var4, float var5, float var6, float var7, float var8, float var9, float var10) {
            if (_snowman.eW() || _snowman.eF()) {
               super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
            }
         }
      });
      this.e.b().h = true;
   }

   public vk a(bdo var1) {
      return a;
   }

   public void a(bdo var1, float var2, float var3, dfm var4, eag var5, int var6) {
      if (_snowman.bF()) {
         dcn[] _snowman = _snowman.y(_snowman);
         float _snowmanx = this.a(_snowman, _snowman);

         for (int _snowmanxx = 0; _snowmanxx < _snowman.length; _snowmanxx++) {
            _snowman.a();
            _snowman.a(
               _snowman[_snowmanxx].b + (double)afm.b((float)_snowmanxx + _snowmanx * 0.5F) * 0.025,
               _snowman[_snowmanxx].c + (double)afm.b((float)_snowmanxx + _snowmanx * 0.75F) * 0.0125,
               _snowman[_snowmanxx].d + (double)afm.b((float)_snowmanxx + _snowmanx * 0.7F) * 0.025
            );
            super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
            _snowman.b();
         }
      } else {
         super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   protected boolean b(bdo var1) {
      return true;
   }
}
