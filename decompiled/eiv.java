public class eiv extends eit<bas, dvn<bas>> {
   private static final vk a = new vk("textures/entity/sheep/sheep_fur.png");
   private final dvm<bas> b = new dvm<>();

   public eiv(egk<bas, dvn<bas>> var1) {
      super(_snowman);
   }

   public void a(dfm var1, eag var2, int var3, bas var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      if (!_snowman.eM() && !_snowman.bF()) {
         float _snowman;
         float _snowmanx;
         float _snowmanxx;
         if (_snowman.S() && "jeb_".equals(_snowman.R().a())) {
            int _snowmanxxx = 25;
            int _snowmanxxxx = _snowman.K / 25 + _snowman.Y();
            int _snowmanxxxxx = bkx.values().length;
            int _snowmanxxxxxx = _snowmanxxxx % _snowmanxxxxx;
            int _snowmanxxxxxxx = (_snowmanxxxx + 1) % _snowmanxxxxx;
            float _snowmanxxxxxxxx = ((float)(_snowman.K % 25) + _snowman) / 25.0F;
            float[] _snowmanxxxxxxxxx = bas.a(bkx.a(_snowmanxxxxxx));
            float[] _snowmanxxxxxxxxxx = bas.a(bkx.a(_snowmanxxxxxxx));
            _snowman = _snowmanxxxxxxxxx[0] * (1.0F - _snowmanxxxxxxxx) + _snowmanxxxxxxxxxx[0] * _snowmanxxxxxxxx;
            _snowmanx = _snowmanxxxxxxxxx[1] * (1.0F - _snowmanxxxxxxxx) + _snowmanxxxxxxxxxx[1] * _snowmanxxxxxxxx;
            _snowmanxx = _snowmanxxxxxxxxx[2] * (1.0F - _snowmanxxxxxxxx) + _snowmanxxxxxxxxxx[2] * _snowmanxxxxxxxx;
         } else {
            float[] _snowmanxxx = bas.a(_snowman.eL());
            _snowman = _snowmanxxx[0];
            _snowmanx = _snowmanxxx[1];
            _snowmanxx = _snowmanxxx[2];
         }

         a(this.aC_(), this.b, a, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowmanx, _snowmanxx);
      }
   }
}
