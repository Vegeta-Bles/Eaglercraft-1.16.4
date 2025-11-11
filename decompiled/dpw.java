import com.mojang.blaze3d.systems.RenderSystem;

public class dpw extends dpp<bih> {
   private static final vk A = new vk("textures/gui/container/brewing_stand.png");
   private static final int[] B = new int[]{29, 24, 20, 16, 11, 6, 0};

   public dpw(bih var1, bfv var2, nr var3) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected void b() {
      super.b();
      this.p = (this.b - this.o.a(this.d)) / 2;
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
      int _snowmanxx = this.t.e();
      int _snowmanxxx = afm.a((18 * _snowmanxx + 20 - 1) / 20, 0, 18);
      if (_snowmanxxx > 0) {
         this.b(_snowman, _snowman + 60, _snowmanx + 44, 176, 29, _snowmanxxx, 4);
      }

      int _snowmanxxxx = this.t.f();
      if (_snowmanxxxx > 0) {
         int _snowmanxxxxx = (int)(28.0F * (1.0F - (float)_snowmanxxxx / 400.0F));
         if (_snowmanxxxxx > 0) {
            this.b(_snowman, _snowman + 97, _snowmanx + 16, 176, 0, 9, _snowmanxxxxx);
         }

         _snowmanxxxxx = B[_snowmanxxxx / 2 % 7];
         if (_snowmanxxxxx > 0) {
            this.b(_snowman, _snowman + 63, _snowmanx + 14 + 29 - _snowmanxxxxx, 185, 29 - _snowmanxxxxx, 12, _snowmanxxxxx);
         }
      }
   }
}
