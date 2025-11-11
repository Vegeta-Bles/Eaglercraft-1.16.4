import com.mojang.blaze3d.systems.RenderSystem;

public abstract class dpq<T extends bid> extends dpp<T> implements drv {
   private static final vk B = new vk("textures/gui/recipe_button.png");
   public final drl A;
   private boolean C;
   private final vk D;

   public dpq(T var1, drl var2, bfv var3, nr var4, vk var5) {
      super(_snowman, _snowman, _snowman);
      this.A = _snowman;
      this.D = _snowman;
   }

   @Override
   public void b() {
      super.b();
      this.C = this.k < 379;
      this.A.a(this.k, this.l, this.i, this.C, this.t);
      this.w = this.A.a(this.C, this.k, this.b);
      this.a(new dlr(this.w + 20, this.l / 2 - 49, 20, 18, 0, 0, 19, B, var1 -> {
         this.A.a(this.C);
         this.A.e();
         this.w = this.A.a(this.C, this.k, this.b);
         ((dlr)var1).a(this.w + 20, this.l / 2 - 49);
      }));
      this.p = (this.b - this.o.a(this.d)) / 2;
   }

   @Override
   public void d() {
      super.d();
      this.A.g();
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      if (this.A.f() && this.C) {
         this.a(_snowman, _snowman, _snowman, _snowman);
         this.A.a(_snowman, _snowman, _snowman, _snowman);
      } else {
         this.A.a(_snowman, _snowman, _snowman, _snowman);
         super.a(_snowman, _snowman, _snowman, _snowman);
         this.A.a(_snowman, this.w, this.x, true, _snowman);
      }

      this.a(_snowman, _snowman, _snowman);
      this.A.c(_snowman, this.w, this.x, _snowman, _snowman);
   }

   @Override
   protected void a(dfm var1, float var2, int var3, int var4) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.i.M().a(this.D);
      int _snowman = this.w;
      int _snowmanx = this.x;
      this.b(_snowman, _snowman, _snowmanx, 0, 0, this.b, this.c);
      if (this.t.l()) {
         int _snowmanxx = this.t.k();
         this.b(_snowman, _snowman + 56, _snowmanx + 36 + 12 - _snowmanxx, 176, 12 - _snowmanxx, 14, _snowmanxx + 1);
      }

      int _snowmanxx = this.t.j();
      this.b(_snowman, _snowman + 79, _snowmanx + 34, 176, 14, _snowmanxx + 1, 16);
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      if (this.A.a(_snowman, _snowman, _snowman)) {
         return true;
      } else {
         return this.C && this.A.f() ? true : super.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   protected void a(bjr var1, int var2, int var3, bik var4) {
      super.a(_snowman, _snowman, _snowman, _snowman);
      this.A.a(_snowman);
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      return this.A.a(_snowman, _snowman, _snowman) ? false : super.a(_snowman, _snowman, _snowman);
   }

   @Override
   protected boolean a(double var1, double var3, int var5, int var6, int var7) {
      boolean _snowman = _snowman < (double)_snowman || _snowman < (double)_snowman || _snowman >= (double)(_snowman + this.b) || _snowman >= (double)(_snowman + this.c);
      return this.A.a(_snowman, _snowman, this.w, this.x, this.b, this.c, _snowman) && _snowman;
   }

   @Override
   public boolean a(char var1, int var2) {
      return this.A.a(_snowman, _snowman) ? true : super.a(_snowman, _snowman);
   }

   @Override
   public void az_() {
      this.A.h();
   }

   @Override
   public drp k() {
      return this.A;
   }

   @Override
   public void e() {
      this.A.d();
      super.e();
   }
}
