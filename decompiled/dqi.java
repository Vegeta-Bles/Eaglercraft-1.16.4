import com.mojang.blaze3d.systems.RenderSystem;

public class dqi extends dpp<biw> {
   private static final vk A = new vk("textures/gui/container/grindstone.png");

   public dqi(biw var1, bfv var2, nr var3) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      this.a(_snowman, _snowman, _snowman, _snowman);
      super.a(_snowman, _snowman, _snowman, _snowman);
      this.a(_snowman, _snowman, _snowman);
   }

   @Override
   protected void a(dfm var1, float var2, int var3, int var4) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.i.M().a(A);
      int _snowman = (this.k - this.b) / 2;
      int _snowmanx = (this.l - this.c) / 2;
      this.b(_snowman, _snowman, _snowmanx, 0, 0, this.b, this.c);
      if ((this.t.a(0).f() || this.t.a(1).f()) && !this.t.a(2).f()) {
         this.b(_snowman, _snowman + 92, _snowmanx + 31, this.b, 0, 28, 21);
      }
   }
}
