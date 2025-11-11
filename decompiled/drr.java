import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;

public class drr extends dma {
   private final dkg t;
   private float u;

   public drr(dkg var1) {
      super(0, 0, 35, 27, false);
      this.t = _snowman;
      this.a(153, 2, 35, 0, drp.a);
   }

   public void a(djz var1) {
      djm _snowman = _snowman.s.F();
      List<drt> _snowmanx = _snowman.a(this.t);
      if (_snowman.s.bp instanceof bjj) {
         for (drt _snowmanxx : _snowmanx) {
            for (boq<?> _snowmanxxx : _snowmanxx.a(_snowman.a((bjj<?>)_snowman.s.bp))) {
               if (_snowman.d(_snowmanxxx)) {
                  this.u = 15.0F;
                  return;
               }
            }
         }
      }
   }

   @Override
   public void b(dfm var1, int var2, int var3, float var4) {
      if (this.u > 0.0F) {
         float _snowman = 1.0F + 0.1F * (float)Math.sin((double)(this.u / 15.0F * (float) Math.PI));
         RenderSystem.pushMatrix();
         RenderSystem.translatef((float)(this.l + 8), (float)(this.m + 12), 0.0F);
         RenderSystem.scalef(1.0F, _snowman, 1.0F);
         RenderSystem.translatef((float)(-(this.l + 8)), (float)(-(this.m + 12)), 0.0F);
      }

      djz _snowman = djz.C();
      _snowman.M().a(this.a);
      RenderSystem.disableDepthTest();
      int _snowmanx = this.c;
      int _snowmanxx = this.d;
      if (this.b) {
         _snowmanx += this.e;
      }

      if (this.g()) {
         _snowmanxx += this.s;
      }

      int _snowmanxxx = this.l;
      if (this.b) {
         _snowmanxxx -= 2;
      }

      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.b(_snowman, _snowmanxxx, this.m, _snowmanx, _snowmanxx, this.j, this.k);
      RenderSystem.enableDepthTest();
      this.a(_snowman.ad());
      if (this.u > 0.0F) {
         RenderSystem.popMatrix();
         this.u -= _snowman;
      }
   }

   private void a(efo var1) {
      List<bmb> _snowman = this.t.a();
      int _snowmanx = this.b ? -2 : 0;
      if (_snowman.size() == 1) {
         _snowman.c(_snowman.get(0), this.l + 9 + _snowmanx, this.m + 5);
      } else if (_snowman.size() == 2) {
         _snowman.c(_snowman.get(0), this.l + 3 + _snowmanx, this.m + 5);
         _snowman.c(_snowman.get(1), this.l + 14 + _snowmanx, this.m + 5);
      }
   }

   public dkg b() {
      return this.t;
   }

   public boolean a(djm var1) {
      List<drt> _snowman = _snowman.a(this.t);
      this.p = false;
      if (_snowman != null) {
         for (drt _snowmanx : _snowman) {
            if (_snowmanx.a() && _snowmanx.c()) {
               this.p = true;
               break;
            }
         }
      }

      return this.p;
   }
}
