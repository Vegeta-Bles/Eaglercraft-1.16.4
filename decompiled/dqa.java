import com.mojang.blaze3d.systems.RenderSystem;

public class dqa extends dpp<bip> implements drv {
   private static final vk A = new vk("textures/gui/container/crafting_table.png");
   private static final vk B = new vk("textures/gui/recipe_button.png");
   private final drp C = new drp();
   private boolean D;

   public dqa(bip var1, bfv var2, nr var3) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected void b() {
      super.b();
      this.D = this.k < 379;
      this.C.a(this.k, this.l, this.i, this.D, this.t);
      this.w = this.C.a(this.D, this.k, this.b);
      this.e.add(this.C);
      this.b(this.C);
      this.a(new dlr(this.w + 5, this.l / 2 - 49, 20, 18, 0, 0, 19, B, var1 -> {
         this.C.a(this.D);
         this.C.e();
         this.w = this.C.a(this.D, this.k, this.b);
         ((dlr)var1).a(this.w + 5, this.l / 2 - 49);
      }));
      this.p = 29;
   }

   @Override
   public void d() {
      super.d();
      this.C.g();
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      if (this.C.f() && this.D) {
         this.a(_snowman, _snowman, _snowman, _snowman);
         this.C.a(_snowman, _snowman, _snowman, _snowman);
      } else {
         this.C.a(_snowman, _snowman, _snowman, _snowman);
         super.a(_snowman, _snowman, _snowman, _snowman);
         this.C.a(_snowman, this.w, this.x, true, _snowman);
      }

      this.a(_snowman, _snowman, _snowman);
      this.C.c(_snowman, this.w, this.x, _snowman, _snowman);
   }

   @Override
   protected void a(dfm var1, float var2, int var3, int var4) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.i.M().a(A);
      int _snowman = this.w;
      int _snowmanx = (this.l - this.c) / 2;
      this.b(_snowman, _snowman, _snowmanx, 0, 0, this.b, this.c);
   }

   @Override
   protected boolean a(int var1, int var2, int var3, int var4, double var5, double var7) {
      return (!this.D || !this.C.f()) && super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      if (this.C.a(_snowman, _snowman, _snowman)) {
         this.a(this.C);
         return true;
      } else {
         return this.D && this.C.f() ? true : super.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   protected boolean a(double var1, double var3, int var5, int var6, int var7) {
      boolean _snowman = _snowman < (double)_snowman || _snowman < (double)_snowman || _snowman >= (double)(_snowman + this.b) || _snowman >= (double)(_snowman + this.c);
      return this.C.a(_snowman, _snowman, this.w, this.x, this.b, this.c, _snowman) && _snowman;
   }

   @Override
   protected void a(bjr var1, int var2, int var3, bik var4) {
      super.a(_snowman, _snowman, _snowman, _snowman);
      this.C.a(_snowman);
   }

   @Override
   public void az_() {
      this.C.h();
   }

   @Override
   public void e() {
      this.C.d();
      super.e();
   }

   @Override
   public drp k() {
      return this.C;
   }
}
