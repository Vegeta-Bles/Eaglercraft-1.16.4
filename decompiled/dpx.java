import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;

public class dpx extends dpp<bii> {
   private static final vk A = new vk("textures/gui/container/cartography_table.png");

   public dpx(bii var1, bfv var2, nr var3) {
      super(_snowman, _snowman, _snowman);
      this.q -= 2;
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      super.a(_snowman, _snowman, _snowman, _snowman);
      this.a(_snowman, _snowman, _snowman);
   }

   @Override
   protected void a(dfm var1, float var2, int var3, int var4) {
      this.a(_snowman);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.i.M().a(A);
      int _snowman = this.w;
      int _snowmanx = this.x;
      this.b(_snowman, _snowman, _snowmanx, 0, 0, this.b, this.c);
      blx _snowmanxx = this.t.a(1).e().b();
      boolean _snowmanxxx = _snowmanxx == bmd.pc;
      boolean _snowmanxxxx = _snowmanxx == bmd.mb;
      boolean _snowmanxxxxx = _snowmanxx == bmd.dP;
      bmb _snowmanxxxxxx = this.t.a(0).e();
      boolean _snowmanxxxxxxx = false;
      cxx _snowmanxxxxxxxx;
      if (_snowmanxxxxxx.b() == bmd.nf) {
         _snowmanxxxxxxxx = bmh.a(_snowmanxxxxxx, this.i.r);
         if (_snowmanxxxxxxxx != null) {
            if (_snowmanxxxxxxxx.h) {
               _snowmanxxxxxxx = true;
               if (_snowmanxxxx || _snowmanxxxxx) {
                  this.b(_snowman, _snowman + 35, _snowmanx + 31, this.b + 50, 132, 28, 21);
               }
            }

            if (_snowmanxxxx && _snowmanxxxxxxxx.f >= 4) {
               _snowmanxxxxxxx = true;
               this.b(_snowman, _snowman + 35, _snowmanx + 31, this.b + 50, 132, 28, 21);
            }
         }
      } else {
         _snowmanxxxxxxxx = null;
      }

      this.a(_snowman, _snowmanxxxxxxxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxx);
   }

   private void a(dfm var1, @Nullable cxx var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      int _snowman = this.w;
      int _snowmanx = this.x;
      if (_snowman && !_snowman) {
         this.b(_snowman, _snowman + 67, _snowmanx + 13, this.b, 66, 66, 66);
         this.a(_snowman, _snowman + 85, _snowmanx + 31, 0.226F);
      } else if (_snowman) {
         this.b(_snowman, _snowman + 67 + 16, _snowmanx + 13, this.b, 132, 50, 66);
         this.a(_snowman, _snowman + 86, _snowmanx + 16, 0.34F);
         this.i.M().a(A);
         RenderSystem.pushMatrix();
         RenderSystem.translatef(0.0F, 0.0F, 1.0F);
         this.b(_snowman, _snowman + 67, _snowmanx + 13 + 16, this.b, 132, 50, 66);
         this.a(_snowman, _snowman + 70, _snowmanx + 32, 0.34F);
         RenderSystem.popMatrix();
      } else if (_snowman) {
         this.b(_snowman, _snowman + 67, _snowmanx + 13, this.b, 0, 66, 66);
         this.a(_snowman, _snowman + 71, _snowmanx + 17, 0.45F);
         this.i.M().a(A);
         RenderSystem.pushMatrix();
         RenderSystem.translatef(0.0F, 0.0F, 1.0F);
         this.b(_snowman, _snowman + 66, _snowmanx + 12, 0, this.c, 66, 66);
         RenderSystem.popMatrix();
      } else {
         this.b(_snowman, _snowman + 67, _snowmanx + 13, this.b, 0, 66, 66);
         this.a(_snowman, _snowman + 71, _snowmanx + 17, 0.45F);
      }
   }

   private void a(@Nullable cxx var1, int var2, int var3, float var4) {
      if (_snowman != null) {
         RenderSystem.pushMatrix();
         RenderSystem.translatef((float)_snowman, (float)_snowman, 1.0F);
         RenderSystem.scalef(_snowman, _snowman, 1.0F);
         eag.a _snowman = eag.a(dfo.a().c());
         this.i.h.h().a(new dfm(), _snowman, _snowman, true, 15728880);
         _snowman.a();
         RenderSystem.popMatrix();
      }
   }
}
