public class dvc<T extends aqn> extends dvd<T> {
   public final dwn a;
   public final dwn b;
   private final dwn y;
   private final dwn z;
   private final dwn A;
   private final dwn B;

   public dvc(float var1, int var2, int var3) {
      super(_snowman, false);
      this.r = _snowman;
      this.s = _snowman;
      this.h = new dwn(this, 16, 16);
      this.h.a(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, _snowman);
      this.f = new dwn(this);
      this.f.a(0, 0).a(-5.0F, -8.0F, -4.0F, 10.0F, 8.0F, 8.0F, _snowman);
      this.f.a(31, 1).a(-2.0F, -4.0F, -5.0F, 4.0F, 4.0F, 1.0F, _snowman);
      this.f.a(2, 4).a(2.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, _snowman);
      this.f.a(2, 0).a(-3.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, _snowman);
      this.a = new dwn(this);
      this.a.a(4.5F, -6.0F, 0.0F);
      this.a.a(51, 6).a(0.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, _snowman);
      this.f.b(this.a);
      this.b = new dwn(this);
      this.b.a(-4.5F, -6.0F, 0.0F);
      this.b.a(39, 6).a(-1.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, _snowman);
      this.f.b(this.b);
      this.g = new dwn(this);
      this.y = this.h.a();
      this.z = this.f.a();
      this.A = this.j.a();
      this.B = this.j.a();
   }

   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.h.a(this.y);
      this.f.a(this.z);
      this.j.a(this.A);
      this.i.a(this.B);
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      float _snowman = (float) (Math.PI / 6);
      float _snowmanx = _snowman * 0.1F + _snowman * 0.5F;
      float _snowmanxx = 0.08F + _snowman * 0.4F;
      this.a.f = (float) (-Math.PI / 6) - afm.b(_snowmanx * 1.2F) * _snowmanxx;
      this.b.f = (float) (Math.PI / 6) + afm.b(_snowmanx) * _snowmanxx;
      if (_snowman instanceof ber) {
         ber _snowmanxxx = (ber)_snowman;
         beu _snowmanxxxx = _snowmanxxx.eN();
         if (_snowmanxxxx == beu.e) {
            float _snowmanxxxxx = _snowman / 60.0F;
            this.b.f = (float) (Math.PI / 6) + (float) (Math.PI / 180.0) * afm.a(_snowmanxxxxx * 30.0F) * 10.0F;
            this.a.f = (float) (-Math.PI / 6) - (float) (Math.PI / 180.0) * afm.b(_snowmanxxxxx * 30.0F) * 10.0F;
            this.f.a = afm.a(_snowmanxxxxx * 10.0F);
            this.f.b = afm.a(_snowmanxxxxx * 40.0F) + 0.4F;
            this.i.f = (float) (Math.PI / 180.0) * (70.0F + afm.b(_snowmanxxxxx * 40.0F) * 10.0F);
            this.j.f = this.i.f * -1.0F;
            this.i.b = afm.a(_snowmanxxxxx * 40.0F) * 0.5F + 1.5F;
            this.j.b = afm.a(_snowmanxxxxx * 40.0F) * 0.5F + 1.5F;
            this.h.b = afm.a(_snowmanxxxxx * 40.0F) * 0.35F;
         } else if (_snowmanxxxx == beu.a && this.c == 0.0F) {
            this.a(_snowman);
         } else if (_snowmanxxxx == beu.b) {
            dtg.a(this.i, this.j, this.f, !_snowman.eE());
         } else if (_snowmanxxxx == beu.c) {
            dtg.a(this.i, this.j, _snowman, !_snowman.eE());
         } else if (_snowmanxxxx == beu.d) {
            this.f.d = 0.5F;
            this.f.e = 0.0F;
            if (_snowman.eE()) {
               this.i.e = -0.5F;
               this.i.d = -0.9F;
            } else {
               this.j.e = 0.5F;
               this.j.d = -0.9F;
            }
         }
      } else if (_snowman.X() == aqe.bb) {
         dtg.a(this.j, this.i, _snowman.eF(), this.c, _snowman);
      }

      this.v.a(this.l);
      this.w.a(this.k);
      this.t.a(this.j);
      this.u.a(this.i);
      this.x.a(this.h);
      this.g.a(this.f);
   }

   protected void a(T var1, float var2) {
      if (this.c > 0.0F && _snowman instanceof bes && ((bes)_snowman).eN() == beu.a) {
         dtg.a(this.i, this.j, _snowman, this.c, _snowman);
      } else {
         super.a(_snowman, _snowman);
      }
   }

   private void a(T var1) {
      if (_snowman.eE()) {
         this.j.d = -1.8F;
      } else {
         this.i.d = -1.8F;
      }
   }
}
