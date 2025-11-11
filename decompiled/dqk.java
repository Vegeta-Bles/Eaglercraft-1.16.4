import com.mojang.blaze3d.systems.RenderSystem;

public class dqk extends dpp<biy> {
   private static final vk A = new vk("textures/gui/container/horse.png");
   private final bbb B;
   private float C;
   private float D;

   public dqk(biy var1, bfv var2, bbb var3) {
      super(_snowman, _snowman, _snowman.d());
      this.B = _snowman;
      this.n = false;
   }

   @Override
   protected void a(dfm var1, float var2, int var3, int var4) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.i.M().a(A);
      int _snowman = (this.k - this.b) / 2;
      int _snowmanx = (this.l - this.c) / 2;
      this.b(_snowman, _snowman, _snowmanx, 0, 0, this.b, this.c);
      if (this.B instanceof bba) {
         bba _snowmanxx = (bba)this.B;
         if (_snowmanxx.eM()) {
            this.b(_snowman, _snowman + 79, _snowmanx + 17, 0, this.c, _snowmanxx.eU() * 18, 54);
         }
      }

      if (this.B.L_()) {
         this.b(_snowman, _snowman + 7, _snowmanx + 35 - 18, 18, this.c + 54, 18, 18);
      }

      if (this.B.fs()) {
         if (this.B instanceof bbe) {
            this.b(_snowman, _snowman + 7, _snowmanx + 35, 36, this.c + 54, 18, 18);
         } else {
            this.b(_snowman, _snowman + 7, _snowmanx + 35, 0, this.c + 54, 18, 18);
         }
      }

      dql.a(_snowman + 51, _snowmanx + 60, 17, (float)(_snowman + 51) - this.C, (float)(_snowmanx + 75 - 50) - this.D, this.B);
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      this.C = (float)_snowman;
      this.D = (float)_snowman;
      super.a(_snowman, _snowman, _snowman, _snowman);
      this.a(_snowman, _snowman, _snowman);
   }
}
