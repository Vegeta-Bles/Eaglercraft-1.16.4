public class dty<T extends bej> extends dwj<T> {
   public dty(float var1, float var2, int var3, int var4) {
      super(_snowman, _snowman, _snowman, _snowman);
      this.i = new dwn(this, 32, 48);
      this.i.a(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman);
      this.i.a(-5.0F, 2.0F + _snowman, 0.0F);
      this.k = new dwn(this, 16, 48);
      this.k.a(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman);
      this.k.a(-1.9F, 12.0F + _snowman, 0.0F);
   }

   public dty(float var1, boolean var2) {
      super(_snowman, 0.0F, 64, _snowman ? 32 : 64);
   }

   public void a(T var1, float var2, float var3, float var4) {
      this.n = dum.a.a;
      this.m = dum.a.a;
      bmb _snowman = _snowman.b(aot.a);
      if (_snowman.b() == bmd.qM && _snowman.eF()) {
         if (_snowman.dV() == aqi.b) {
            this.n = dum.a.e;
         } else {
            this.m = dum.a.e;
         }
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      if (this.m == dum.a.e) {
         this.j.d = this.j.d * 0.5F - (float) Math.PI;
         this.j.e = 0.0F;
      }

      if (this.n == dum.a.e) {
         this.i.d = this.i.d * 0.5F - (float) Math.PI;
         this.i.e = 0.0F;
      }

      if (this.p > 0.0F) {
         this.i.d = this.a(this.p, this.i.d, (float) (-Math.PI * 4.0 / 5.0)) + this.p * 0.35F * afm.a(0.1F * _snowman);
         this.j.d = this.a(this.p, this.j.d, (float) (-Math.PI * 4.0 / 5.0)) - this.p * 0.35F * afm.a(0.1F * _snowman);
         this.i.f = this.a(this.p, this.i.f, -0.15F);
         this.j.f = this.a(this.p, this.j.f, 0.15F);
         this.l.d = this.l.d - this.p * 0.55F * afm.a(0.1F * _snowman);
         this.k.d = this.k.d + this.p * 0.55F * afm.a(0.1F * _snowman);
         this.f.d = 0.0F;
      }
   }
}
