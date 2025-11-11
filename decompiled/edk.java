import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map.Entry;

public class edk implements edh.a {
   private final djz a;

   public edk(djz var1) {
      this.a = _snowman;
   }

   @Override
   public void a(dfm var1, eag var2, double var3, double var5, double var7) {
      bry _snowman = this.a.r;
      RenderSystem.pushMatrix();
      RenderSystem.disableBlend();
      RenderSystem.disableTexture();
      RenderSystem.enableDepthTest();
      fx _snowmanx = new fx(_snowman, 0.0, _snowman);
      dfo _snowmanxx = dfo.a();
      dfh _snowmanxxx = _snowmanxx.c();
      _snowmanxxx.a(5, dfk.l);

      for (int _snowmanxxxx = -32; _snowmanxxxx <= 32; _snowmanxxxx += 16) {
         for (int _snowmanxxxxx = -32; _snowmanxxxxx <= 32; _snowmanxxxxx += 16) {
            cfw _snowmanxxxxxx = _snowman.z(_snowmanx.b(_snowmanxxxx, 0, _snowmanxxxxx));

            for (Entry<chn.a, chn> _snowmanxxxxxxx : _snowmanxxxxxx.f()) {
               chn.a _snowmanxxxxxxxx = _snowmanxxxxxxx.getKey();
               brd _snowmanxxxxxxxxx = _snowmanxxxxxx.g();
               g _snowmanxxxxxxxxxx = this.a(_snowmanxxxxxxxx);

               for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx < 16; _snowmanxxxxxxxxxxx++) {
                  for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < 16; _snowmanxxxxxxxxxxxx++) {
                     int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxx.b * 16 + _snowmanxxxxxxxxxxx;
                     int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxx.c * 16 + _snowmanxxxxxxxxxxxx;
                     float _snowmanxxxxxxxxxxxxxxx = (float)(
                        (double)((float)_snowman.a(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx) + (float)_snowmanxxxxxxxx.ordinal() * 0.09375F) - _snowman
                     );
                     eae.a(
                        _snowmanxxx,
                        (double)((float)_snowmanxxxxxxxxxxxxx + 0.25F) - _snowman,
                        (double)_snowmanxxxxxxxxxxxxxxx,
                        (double)((float)_snowmanxxxxxxxxxxxxxx + 0.25F) - _snowman,
                        (double)((float)_snowmanxxxxxxxxxxxxx + 0.75F) - _snowman,
                        (double)(_snowmanxxxxxxxxxxxxxxx + 0.09375F),
                        (double)((float)_snowmanxxxxxxxxxxxxxx + 0.75F) - _snowman,
                        _snowmanxxxxxxxxxx.a(),
                        _snowmanxxxxxxxxxx.b(),
                        _snowmanxxxxxxxxxx.c(),
                        1.0F
                     );
                  }
               }
            }
         }
      }

      _snowmanxx.b();
      RenderSystem.enableTexture();
      RenderSystem.popMatrix();
   }

   private g a(chn.a var1) {
      switch (_snowman) {
         case a:
            return new g(1.0F, 1.0F, 0.0F);
         case c:
            return new g(1.0F, 0.0F, 1.0F);
         case b:
            return new g(0.0F, 0.7F, 0.0F);
         case d:
            return new g(0.0F, 0.0F, 0.5F);
         case e:
            return new g(0.0F, 0.3F, 0.3F);
         case f:
            return new g(0.0F, 0.5F, 0.5F);
         default:
            return new g(0.0F, 0.0F, 0.0F);
      }
   }
}
