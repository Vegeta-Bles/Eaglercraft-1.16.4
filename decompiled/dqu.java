import com.mojang.blaze3d.systems.RenderSystem;

public class dqu extends dpp<bjo> {
   private static final vk A = new vk("textures/gui/container/shulker_box.png");

   public dqu(bjo var1, bfv var2, nr var3) {
      super(_snowman, _snowman, _snowman);
      this.c++;
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
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
   }
}
