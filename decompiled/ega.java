import com.google.common.collect.Maps;
import java.util.Map;

public class ega extends efw<bal, duy<bal>> {
   private static final Map<bal.a, vk> a = x.a(Maps.newEnumMap(bal.a.class), var0 -> {
      var0.put(bal.a.a, new vk("textures/entity/panda/panda.png"));
      var0.put(bal.a.b, new vk("textures/entity/panda/lazy_panda.png"));
      var0.put(bal.a.c, new vk("textures/entity/panda/worried_panda.png"));
      var0.put(bal.a.d, new vk("textures/entity/panda/playful_panda.png"));
      var0.put(bal.a.e, new vk("textures/entity/panda/brown_panda.png"));
      var0.put(bal.a.f, new vk("textures/entity/panda/weak_panda.png"));
      var0.put(bal.a.g, new vk("textures/entity/panda/aggressive_panda.png"));
   });

   public ega(eet var1) {
      super(_snowman, new duy<>(9, 0.0F), 0.9F);
      this.a(new eiq(this));
   }

   public vk a(bal var1) {
      return a.getOrDefault(_snowman.eZ(), a.get(bal.a.a));
   }

   protected void a(bal var1, dfm var2, float var3, float var4, float var5) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      if (_snowman.bo > 0) {
         int _snowman = _snowman.bo;
         int _snowmanx = _snowman + 1;
         float _snowmanxx = 7.0F;
         float _snowmanxxx = _snowman.w_() ? 0.3F : 0.8F;
         if (_snowman < 8) {
            float _snowmanxxxx = (float)(90 * _snowman) / 7.0F;
            float _snowmanxxxxx = (float)(90 * _snowmanx) / 7.0F;
            float _snowmanxxxxxx = this.a(_snowmanxxxx, _snowmanxxxxx, _snowmanx, _snowman, 8.0F);
            _snowman.a(0.0, (double)((_snowmanxxx + 0.2F) * (_snowmanxxxxxx / 90.0F)), 0.0);
            _snowman.a(g.b.a(-_snowmanxxxxxx));
         } else if (_snowman < 16) {
            float _snowmanxxxx = ((float)_snowman - 8.0F) / 7.0F;
            float _snowmanxxxxx = 90.0F + 90.0F * _snowmanxxxx;
            float _snowmanxxxxxx = 90.0F + 90.0F * ((float)_snowmanx - 8.0F) / 7.0F;
            float _snowmanxxxxxxx = this.a(_snowmanxxxxx, _snowmanxxxxxx, _snowmanx, _snowman, 16.0F);
            _snowman.a(0.0, (double)(_snowmanxxx + 0.2F + (_snowmanxxx - 0.2F) * (_snowmanxxxxxxx - 90.0F) / 90.0F), 0.0);
            _snowman.a(g.b.a(-_snowmanxxxxxxx));
         } else if ((float)_snowman < 24.0F) {
            float _snowmanxxxx = ((float)_snowman - 16.0F) / 7.0F;
            float _snowmanxxxxx = 180.0F + 90.0F * _snowmanxxxx;
            float _snowmanxxxxxx = 180.0F + 90.0F * ((float)_snowmanx - 16.0F) / 7.0F;
            float _snowmanxxxxxxx = this.a(_snowmanxxxxx, _snowmanxxxxxx, _snowmanx, _snowman, 24.0F);
            _snowman.a(0.0, (double)(_snowmanxxx + _snowmanxxx * (270.0F - _snowmanxxxxxxx) / 90.0F), 0.0);
            _snowman.a(g.b.a(-_snowmanxxxxxxx));
         } else if (_snowman < 32) {
            float _snowmanxxxx = ((float)_snowman - 24.0F) / 7.0F;
            float _snowmanxxxxx = 270.0F + 90.0F * _snowmanxxxx;
            float _snowmanxxxxxx = 270.0F + 90.0F * ((float)_snowmanx - 24.0F) / 7.0F;
            float _snowmanxxxxxxx = this.a(_snowmanxxxxx, _snowmanxxxxxx, _snowmanx, _snowman, 32.0F);
            _snowman.a(0.0, (double)(_snowmanxxx * ((360.0F - _snowmanxxxxxxx) / 90.0F)), 0.0);
            _snowman.a(g.b.a(-_snowmanxxxxxxx));
         }
      }

      float _snowman = _snowman.y(_snowman);
      if (_snowman > 0.0F) {
         _snowman.a(0.0, (double)(0.8F * _snowman), 0.0);
         _snowman.a(g.b.a(afm.g(_snowman, _snowman.q, _snowman.q + 90.0F)));
         _snowman.a(0.0, (double)(-1.0F * _snowman), 0.0);
         if (_snowman.ff()) {
            float _snowmanx = (float)(Math.cos((double)_snowman.K * 1.25) * Math.PI * 0.05F);
            _snowman.a(g.d.a(_snowmanx));
            if (_snowman.w_()) {
               _snowman.a(0.0, 0.8F, 0.55F);
            }
         }
      }

      float _snowmanx = _snowman.z(_snowman);
      if (_snowmanx > 0.0F) {
         float _snowmanxx = _snowman.w_() ? 0.5F : 1.3F;
         _snowman.a(0.0, (double)(_snowmanxx * _snowmanx), 0.0);
         _snowman.a(g.b.a(afm.g(_snowmanx, _snowman.q, _snowman.q + 180.0F)));
      }
   }

   private float a(float var1, float var2, int var3, float var4, float var5) {
      return (float)_snowman < _snowman ? afm.g(_snowman, _snowman, _snowman) : _snowman;
   }
}
