import com.mojang.blaze3d.systems.RenderSystem;

public class dqm<T extends bja> extends dpp<T> implements bin {
   private vk A;

   public dqm(T var1, bfv var2, nr var3, vk var4) {
      super(_snowman, _snowman, _snowman);
      this.A = _snowman;
   }

   protected void i() {
   }

   @Override
   protected void b() {
      super.b();
      this.i();
      this.t.a(this);
   }

   @Override
   public void e() {
      super.e();
      this.t.b(this);
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      super.a(_snowman, _snowman, _snowman, _snowman);
      RenderSystem.disableBlend();
      this.b(_snowman, _snowman, _snowman, _snowman);
      this.a(_snowman, _snowman, _snowman);
   }

   protected void b(dfm var1, int var2, int var3, float var4) {
   }

   @Override
   protected void a(dfm var1, float var2, int var3, int var4) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.i.M().a(this.A);
      int _snowman = (this.k - this.b) / 2;
      int _snowmanx = (this.l - this.c) / 2;
      this.b(_snowman, _snowman, _snowmanx, 0, 0, this.b, this.c);
      this.b(_snowman, _snowman + 59, _snowmanx + 20, 0, this.c + (this.t.a(0).f() ? 0 : 16), 110, 16);
      if ((this.t.a(0).f() || this.t.a(1).f()) && !this.t.a(2).f()) {
         this.b(_snowman, _snowman + 99, _snowmanx + 45, this.b, 0, 28, 21);
      }
   }

   @Override
   public void a(bic var1, gj<bmb> var2) {
      this.a(_snowman, 0, _snowman.a(0).e());
   }

   @Override
   public void a(bic var1, int var2, int var3) {
   }

   @Override
   public void a(bic var1, int var2, bmb var3) {
   }
}
