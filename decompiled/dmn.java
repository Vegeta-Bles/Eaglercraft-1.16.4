import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;

public class dmn implements dmq {
   private final y c;
   private boolean d;

   public dmn(y var1) {
      this.c = _snowman;
   }

   @Override
   public dmq.a a(dfm var1, dmr var2, long var3) {
      _snowman.b().M().a(a);
      RenderSystem.color3f(1.0F, 1.0F, 1.0F);
      ah _snowman = this.c.c();
      _snowman.b(_snowman, 0, 0, 0, 0, this.a(), this.d());
      if (_snowman != null) {
         List<afa> _snowmanx = _snowman.b().g.b(_snowman.a(), 125);
         int _snowmanxx = _snowman.e() == ai.b ? 16746751 : 16776960;
         if (_snowmanx.size() == 1) {
            _snowman.b().g.b(_snowman, _snowman.e().d(), 30.0F, 7.0F, _snowmanxx | 0xFF000000);
            _snowman.b().g.b(_snowman, _snowmanx.get(0), 30.0F, 18.0F, -1);
         } else {
            int _snowmanxxx = 1500;
            float _snowmanxxxx = 300.0F;
            if (_snowman < 1500L) {
               int _snowmanxxxxx = afm.d(afm.a((float)(1500L - _snowman) / 300.0F, 0.0F, 1.0F) * 255.0F) << 24 | 67108864;
               _snowman.b().g.b(_snowman, _snowman.e().d(), 30.0F, 11.0F, _snowmanxx | _snowmanxxxxx);
            } else {
               int _snowmanxxxxx = afm.d(afm.a((float)(_snowman - 1500L) / 300.0F, 0.0F, 1.0F) * 252.0F) << 24 | 67108864;
               int _snowmanxxxxxx = this.d() / 2 - _snowmanx.size() * 9 / 2;

               for (afa _snowmanxxxxxxx : _snowmanx) {
                  _snowman.b().g.b(_snowman, _snowmanxxxxxxx, 30.0F, (float)_snowmanxxxxxx, 16777215 | _snowmanxxxxx);
                  _snowmanxxxxxx += 9;
               }
            }
         }

         if (!this.d && _snowman > 0L) {
            this.d = true;
            if (_snowman.e() == ai.b) {
               _snowman.b().W().a(emp.a(adq.pL, 1.0F, 1.0F));
            }
         }

         _snowman.b().ad().c(_snowman.c(), 8, 8);
         return _snowman >= 5000L ? dmq.a.b : dmq.a.a;
      } else {
         return dmq.a.b;
      }
   }
}
