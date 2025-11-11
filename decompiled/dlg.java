import com.mojang.blaze3d.systems.RenderSystem;

public abstract class dlg extends dlh {
   protected double b;

   public dlg(int var1, int var2, int var3, int var4, nr var5, double var6) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman);
      this.b = _snowman;
   }

   @Override
   protected int a(boolean var1) {
      return 0;
   }

   @Override
   protected nx c() {
      return new of("gui.narrate.slider", this.i());
   }

   @Override
   protected void a(dfm var1, djz var2, int var3, int var4) {
      _snowman.M().a(i);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      int _snowman = (this.g() ? 2 : 1) * 20;
      this.b(_snowman, this.l + (int)(this.b * (double)(this.j - 8)), this.m, 0, 46 + _snowman, 4, 20);
      this.b(_snowman, this.l + (int)(this.b * (double)(this.j - 8)) + 4, this.m, 196, 46 + _snowman, 4, 20);
   }

   @Override
   public void a(double var1, double var3) {
      this.a(_snowman);
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      boolean _snowman = _snowman == 263;
      if (_snowman || _snowman == 262) {
         float _snowmanx = _snowman ? -1.0F : 1.0F;
         this.b(this.b + (double)(_snowmanx / (float)(this.j - 8)));
      }

      return false;
   }

   private void a(double var1) {
      this.b((_snowman - (double)(this.l + 4)) / (double)(this.j - 8));
   }

   private void b(double var1) {
      double _snowman = this.b;
      this.b = afm.a(_snowman, 0.0, 1.0);
      if (_snowman != this.b) {
         this.a();
      }

      this.b();
   }

   @Override
   protected void a(double var1, double var3, double var5, double var7) {
      this.a(_snowman);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void a(enu var1) {
   }

   @Override
   public void a_(double var1, double var3) {
      super.a(djz.C().W());
   }

   protected abstract void b();

   protected abstract void a();
}
