import com.mojang.blaze3d.systems.RenderSystem;

public class dpz extends dpp<bij> implements dqq<bij> {
   private static final vk A = new vk("textures/gui/container/generic_54.png");
   private final int B;

   public dpz(bij var1, bfv var2, nr var3) {
      super(_snowman, _snowman, _snowman);
      this.n = false;
      int _snowman = 222;
      int _snowmanx = 114;
      this.B = _snowman.f();
      this.c = 114 + this.B * 18;
      this.s = this.c - 94;
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
      this.b(_snowman, _snowman, _snowmanx, 0, 0, this.b, this.B * 18 + 17);
      this.b(_snowman, _snowman, _snowmanx + this.B * 18 + 17, 0, 126, this.b, 96);
   }
}
