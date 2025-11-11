public class dti extends dum<bcn> {
   public dti(float var1) {
      this(_snowman, 64, 32);
   }

   protected dti(float var1, int var2, int var3) {
      super(_snowman, 0.0F, _snowman, _snowman);
   }

   public void a(bcn var1, float var2, float var3, float var4, float var5, float var6) {
      this.f.d = (float) (Math.PI / 180.0) * _snowman.r().b();
      this.f.e = (float) (Math.PI / 180.0) * _snowman.r().c();
      this.f.f = (float) (Math.PI / 180.0) * _snowman.r().d();
      this.f.a(0.0F, 1.0F, 0.0F);
      this.h.d = (float) (Math.PI / 180.0) * _snowman.t().b();
      this.h.e = (float) (Math.PI / 180.0) * _snowman.t().c();
      this.h.f = (float) (Math.PI / 180.0) * _snowman.t().d();
      this.j.d = (float) (Math.PI / 180.0) * _snowman.u().b();
      this.j.e = (float) (Math.PI / 180.0) * _snowman.u().c();
      this.j.f = (float) (Math.PI / 180.0) * _snowman.u().d();
      this.i.d = (float) (Math.PI / 180.0) * _snowman.v().b();
      this.i.e = (float) (Math.PI / 180.0) * _snowman.v().c();
      this.i.f = (float) (Math.PI / 180.0) * _snowman.v().d();
      this.l.d = (float) (Math.PI / 180.0) * _snowman.x().b();
      this.l.e = (float) (Math.PI / 180.0) * _snowman.x().c();
      this.l.f = (float) (Math.PI / 180.0) * _snowman.x().d();
      this.l.a(1.9F, 11.0F, 0.0F);
      this.k.d = (float) (Math.PI / 180.0) * _snowman.z().b();
      this.k.e = (float) (Math.PI / 180.0) * _snowman.z().c();
      this.k.f = (float) (Math.PI / 180.0) * _snowman.z().d();
      this.k.a(-1.9F, 11.0F, 0.0F);
      this.g.a(this.f);
   }
}
