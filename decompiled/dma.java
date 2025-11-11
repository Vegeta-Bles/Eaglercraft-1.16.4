import com.mojang.blaze3d.systems.RenderSystem;

public class dma extends dlh {
   protected vk a;
   protected boolean b;
   protected int c;
   protected int d;
   protected int e;
   protected int s;

   public dma(int var1, int var2, int var3, int var4, boolean var5) {
      super(_snowman, _snowman, _snowman, _snowman, oe.d);
      this.b = _snowman;
   }

   public void a(int var1, int var2, int var3, int var4, vk var5) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.s = _snowman;
      this.a = _snowman;
   }

   public void e(boolean var1) {
      this.b = _snowman;
   }

   public boolean a() {
      return this.b;
   }

   public void a(int var1, int var2) {
      this.l = _snowman;
      this.m = _snowman;
   }

   @Override
   public void b(dfm var1, int var2, int var3, float var4) {
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

      this.b(_snowman, this.l, this.m, _snowmanx, _snowmanxx, this.j, this.k);
      RenderSystem.enableDepthTest();
   }
}
