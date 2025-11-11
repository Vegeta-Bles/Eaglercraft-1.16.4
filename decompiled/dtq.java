public class dtq<T extends bba> extends duk<T> {
   private final dwn f = new dwn(this, 26, 21);
   private final dwn g;

   public dtq(float var1) {
      super(_snowman);
      this.f.a(-4.0F, 0.0F, -2.0F, 8.0F, 8.0F, 3.0F);
      this.g = new dwn(this, 26, 21);
      this.g.a(-4.0F, 0.0F, -2.0F, 8.0F, 8.0F, 3.0F);
      this.f.e = (float) (-Math.PI / 2);
      this.g.e = (float) (Math.PI / 2);
      this.f.a(6.0F, -8.0F, 0.0F);
      this.g.a(-6.0F, -8.0F, 0.0F);
      this.a.b(this.f);
      this.a.b(this.g);
   }

   @Override
   protected void a(dwn var1) {
      dwn _snowman = new dwn(this, 0, 12);
      _snowman.a(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 1.0F);
      _snowman.a(1.25F, -10.0F, 4.0F);
      dwn _snowmanx = new dwn(this, 0, 12);
      _snowmanx.a(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 1.0F);
      _snowmanx.a(-1.25F, -10.0F, 4.0F);
      _snowman.d = (float) (Math.PI / 12);
      _snowman.f = (float) (Math.PI / 12);
      _snowmanx.d = (float) (Math.PI / 12);
      _snowmanx.f = (float) (-Math.PI / 12);
      _snowman.b(_snowman);
      _snowman.b(_snowmanx);
   }

   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      if (_snowman.eM()) {
         this.f.h = true;
         this.g.h = true;
      } else {
         this.f.h = false;
         this.g.h = false;
      }
   }
}
