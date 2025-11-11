public class dls extends aok {
   protected float h;
   protected long i;

   public dls(oz var1) {
      super(_snowman.b(), _snowman.d(), _snowman.f(), _snowman.g());
      this.h = _snowman.e();
      this.b = _snowman.e();
      this.i = x.b();
      this.a(_snowman.h());
      this.b(_snowman.i());
      this.c(_snowman.j());
   }

   @Override
   public void a(float var1) {
      this.b = this.k();
      this.h = _snowman;
      this.i = x.b();
   }

   @Override
   public float k() {
      long _snowman = x.b() - this.i;
      float _snowmanx = afm.a((float)_snowman / 100.0F, 0.0F, 1.0F);
      return afm.g(_snowmanx, this.b, this.h);
   }

   public void a(oz var1) {
      switch (_snowman.c()) {
         case d:
            this.a(_snowman.d());
            break;
         case c:
            this.a(_snowman.e());
            break;
         case e:
            this.a(_snowman.f());
            this.a(_snowman.g());
            break;
         case f:
            this.a(_snowman.h());
            this.b(_snowman.i());
      }
   }
}
