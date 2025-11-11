public class ego extends efw<bdw, dvq<bdw>> {
   public static final vk a = new vk("textures/" + ear.g.b().a() + ".png");
   public static final vk[] g = ear.h.stream().map(var0 -> new vk("textures/" + var0.b().a() + ".png")).toArray(vk[]::new);

   public ego(eet var1) {
      super(_snowman, new dvq<>(), 0.0F);
      this.a(new eiw(this));
   }

   public dcn a(bdw var1, float var2) {
      int _snowman = _snowman.eO();
      if (_snowman > 0 && _snowman.eQ()) {
         fx _snowmanx = _snowman.eM();
         fx _snowmanxx = _snowman.eP();
         double _snowmanxxx = (double)((float)_snowman - _snowman) / 6.0;
         _snowmanxxx *= _snowmanxxx;
         double _snowmanxxxx = (double)(_snowmanx.u() - _snowmanxx.u()) * _snowmanxxx;
         double _snowmanxxxxx = (double)(_snowmanx.v() - _snowmanxx.v()) * _snowmanxxx;
         double _snowmanxxxxxx = (double)(_snowmanx.w() - _snowmanxx.w()) * _snowmanxxx;
         return new dcn(-_snowmanxxxx, -_snowmanxxxxx, -_snowmanxxxxxx);
      } else {
         return super.a(_snowman, _snowman);
      }
   }

   public boolean a(bdw var1, ecz var2, double var3, double var5, double var7) {
      if (super.a(_snowman, _snowman, _snowman, _snowman, _snowman)) {
         return true;
      } else {
         if (_snowman.eO() > 0 && _snowman.eQ()) {
            dcn _snowman = dcn.b(_snowman.eM());
            dcn _snowmanx = dcn.b(_snowman.eP());
            if (_snowman.a(new dci(_snowmanx.b, _snowmanx.c, _snowmanx.d, _snowman.b, _snowman.c, _snowman.d))) {
               return true;
            }
         }

         return false;
      }
   }

   public vk a(bdw var1) {
      return _snowman.eS() == null ? a : g[_snowman.eS().b()];
   }

   protected void a(bdw var1, dfm var2, float var3, float var4, float var5) {
      super.a(_snowman, _snowman, _snowman, _snowman + 180.0F, _snowman);
      _snowman.a(0.0, 0.5, 0.0);
      _snowman.a(_snowman.eL().f().b());
      _snowman.a(0.0, -0.5, 0.0);
   }
}
