import com.mojang.blaze3d.systems.RenderSystem;

public class dqt extends dlj {
   private final boolean a;
   private final boolean b;

   public dqt(int var1, int var2, boolean var3, dlj.a var4, boolean var5) {
      super(_snowman, _snowman, 23, 13, oe.d, _snowman);
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public void b(dfm var1, int var2, int var3, float var4) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      djz.C().M().a(dpv.b);
      int _snowman = 0;
      int _snowmanx = 192;
      if (this.g()) {
         _snowman += 23;
      }

      if (!this.a) {
         _snowmanx += 13;
      }

      this.b(_snowman, this.l, this.m, _snowman, _snowmanx, 23, 13);
   }

   @Override
   public void a(enu var1) {
      if (this.b) {
         _snowman.a(emp.a(adq.aX, 1.0F));
      }
   }
}
