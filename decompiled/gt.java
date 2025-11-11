public abstract class gt extends gv {
   public gt() {
   }

   @Override
   public bmb a(fy var1, bmb var2) {
      brx _snowman = _snowman.h();
      gk _snowmanx = bwa.a(_snowman);
      gc _snowmanxx = _snowman.e().c(bwa.a);
      bgm _snowmanxxx = this.a(_snowman, _snowmanx, _snowman);
      _snowmanxxx.c((double)_snowmanxx.i(), (double)((float)_snowmanxx.j() + 0.1F), (double)_snowmanxx.k(), this.b(), this.a());
      _snowman.c(_snowmanxxx);
      _snowman.g(1);
      return _snowman;
   }

   @Override
   protected void a(fy var1) {
      _snowman.h().c(1002, _snowman.d(), 0);
   }

   protected abstract bgm a(brx var1, gk var2, bmb var3);

   protected float a() {
      return 6.0F;
   }

   protected float b() {
      return 1.1F;
   }
}
