import com.mojang.blaze3d.systems.RenderSystem;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class dzu {
   private final vk[] a = new vk[6];

   public dzu(vk var1) {
      for (int _snowman = 0; _snowman < 6; _snowman++) {
         this.a[_snowman] = new vk(_snowman.b(), _snowman.a() + '_' + _snowman + ".png");
      }
   }

   public void a(djz var1, float var2, float var3, float var4) {
      dfo _snowman = dfo.a();
      dfh _snowmanx = _snowman.c();
      RenderSystem.matrixMode(5889);
      RenderSystem.pushMatrix();
      RenderSystem.loadIdentity();
      RenderSystem.multMatrix(b.a(85.0, (float)_snowman.aD().k() / (float)_snowman.aD().l(), 0.05F, 10.0F));
      RenderSystem.matrixMode(5888);
      RenderSystem.pushMatrix();
      RenderSystem.loadIdentity();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.rotatef(180.0F, 1.0F, 0.0F, 0.0F);
      RenderSystem.enableBlend();
      RenderSystem.disableAlphaTest();
      RenderSystem.disableCull();
      RenderSystem.depthMask(false);
      RenderSystem.defaultBlendFunc();
      int _snowmanxx = 2;

      for (int _snowmanxxx = 0; _snowmanxxx < 4; _snowmanxxx++) {
         RenderSystem.pushMatrix();
         float _snowmanxxxx = ((float)(_snowmanxxx % 2) / 2.0F - 0.5F) / 256.0F;
         float _snowmanxxxxx = ((float)(_snowmanxxx / 2) / 2.0F - 0.5F) / 256.0F;
         float _snowmanxxxxxx = 0.0F;
         RenderSystem.translatef(_snowmanxxxx, _snowmanxxxxx, 0.0F);
         RenderSystem.rotatef(_snowman, 1.0F, 0.0F, 0.0F);
         RenderSystem.rotatef(_snowman, 0.0F, 1.0F, 0.0F);

         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 6; _snowmanxxxxxxx++) {
            _snowman.M().a(this.a[_snowmanxxxxxxx]);
            _snowmanx.a(7, dfk.p);
            int _snowmanxxxxxxxx = Math.round(255.0F * _snowman) / (_snowmanxxx + 1);
            if (_snowmanxxxxxxx == 0) {
               _snowmanx.a(-1.0, -1.0, 1.0).a(0.0F, 0.0F).a(255, 255, 255, _snowmanxxxxxxxx).d();
               _snowmanx.a(-1.0, 1.0, 1.0).a(0.0F, 1.0F).a(255, 255, 255, _snowmanxxxxxxxx).d();
               _snowmanx.a(1.0, 1.0, 1.0).a(1.0F, 1.0F).a(255, 255, 255, _snowmanxxxxxxxx).d();
               _snowmanx.a(1.0, -1.0, 1.0).a(1.0F, 0.0F).a(255, 255, 255, _snowmanxxxxxxxx).d();
            }

            if (_snowmanxxxxxxx == 1) {
               _snowmanx.a(1.0, -1.0, 1.0).a(0.0F, 0.0F).a(255, 255, 255, _snowmanxxxxxxxx).d();
               _snowmanx.a(1.0, 1.0, 1.0).a(0.0F, 1.0F).a(255, 255, 255, _snowmanxxxxxxxx).d();
               _snowmanx.a(1.0, 1.0, -1.0).a(1.0F, 1.0F).a(255, 255, 255, _snowmanxxxxxxxx).d();
               _snowmanx.a(1.0, -1.0, -1.0).a(1.0F, 0.0F).a(255, 255, 255, _snowmanxxxxxxxx).d();
            }

            if (_snowmanxxxxxxx == 2) {
               _snowmanx.a(1.0, -1.0, -1.0).a(0.0F, 0.0F).a(255, 255, 255, _snowmanxxxxxxxx).d();
               _snowmanx.a(1.0, 1.0, -1.0).a(0.0F, 1.0F).a(255, 255, 255, _snowmanxxxxxxxx).d();
               _snowmanx.a(-1.0, 1.0, -1.0).a(1.0F, 1.0F).a(255, 255, 255, _snowmanxxxxxxxx).d();
               _snowmanx.a(-1.0, -1.0, -1.0).a(1.0F, 0.0F).a(255, 255, 255, _snowmanxxxxxxxx).d();
            }

            if (_snowmanxxxxxxx == 3) {
               _snowmanx.a(-1.0, -1.0, -1.0).a(0.0F, 0.0F).a(255, 255, 255, _snowmanxxxxxxxx).d();
               _snowmanx.a(-1.0, 1.0, -1.0).a(0.0F, 1.0F).a(255, 255, 255, _snowmanxxxxxxxx).d();
               _snowmanx.a(-1.0, 1.0, 1.0).a(1.0F, 1.0F).a(255, 255, 255, _snowmanxxxxxxxx).d();
               _snowmanx.a(-1.0, -1.0, 1.0).a(1.0F, 0.0F).a(255, 255, 255, _snowmanxxxxxxxx).d();
            }

            if (_snowmanxxxxxxx == 4) {
               _snowmanx.a(-1.0, -1.0, -1.0).a(0.0F, 0.0F).a(255, 255, 255, _snowmanxxxxxxxx).d();
               _snowmanx.a(-1.0, -1.0, 1.0).a(0.0F, 1.0F).a(255, 255, 255, _snowmanxxxxxxxx).d();
               _snowmanx.a(1.0, -1.0, 1.0).a(1.0F, 1.0F).a(255, 255, 255, _snowmanxxxxxxxx).d();
               _snowmanx.a(1.0, -1.0, -1.0).a(1.0F, 0.0F).a(255, 255, 255, _snowmanxxxxxxxx).d();
            }

            if (_snowmanxxxxxxx == 5) {
               _snowmanx.a(-1.0, 1.0, 1.0).a(0.0F, 0.0F).a(255, 255, 255, _snowmanxxxxxxxx).d();
               _snowmanx.a(-1.0, 1.0, -1.0).a(0.0F, 1.0F).a(255, 255, 255, _snowmanxxxxxxxx).d();
               _snowmanx.a(1.0, 1.0, -1.0).a(1.0F, 1.0F).a(255, 255, 255, _snowmanxxxxxxxx).d();
               _snowmanx.a(1.0, 1.0, 1.0).a(1.0F, 0.0F).a(255, 255, 255, _snowmanxxxxxxxx).d();
            }

            _snowman.b();
         }

         RenderSystem.popMatrix();
         RenderSystem.colorMask(true, true, true, false);
      }

      RenderSystem.colorMask(true, true, true, true);
      RenderSystem.matrixMode(5889);
      RenderSystem.popMatrix();
      RenderSystem.matrixMode(5888);
      RenderSystem.popMatrix();
      RenderSystem.depthMask(true);
      RenderSystem.enableCull();
      RenderSystem.enableDepthTest();
   }

   public CompletableFuture<Void> a(ekd var1, Executor var2) {
      CompletableFuture<?>[] _snowman = new CompletableFuture[6];

      for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
         _snowman[_snowmanx] = _snowman.a(this.a[_snowmanx], _snowman);
      }

      return CompletableFuture.allOf(_snowman);
   }
}
