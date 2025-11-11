public class duy<T extends bal> extends dvi<T> {
   private float j;
   private float k;
   private float l;

   public duy(int var1, float var2) {
      super(_snowman, _snowman, true, 23.0F, 4.8F, 2.7F, 3.0F, 49);
      this.r = 64;
      this.s = 64;
      this.a = new dwn(this, 0, 6);
      this.a.a(-6.5F, -5.0F, -4.0F, 13.0F, 10.0F, 9.0F);
      this.a.a(0.0F, 11.5F, -17.0F);
      this.a.a(45, 16).a(-3.5F, 0.0F, -6.0F, 7.0F, 5.0F, 2.0F);
      this.a.a(52, 25).a(-8.5F, -8.0F, -1.0F, 5.0F, 4.0F, 1.0F);
      this.a.a(52, 25).a(3.5F, -8.0F, -1.0F, 5.0F, 4.0F, 1.0F);
      this.b = new dwn(this, 0, 25);
      this.b.a(-9.5F, -13.0F, -6.5F, 19.0F, 26.0F, 13.0F);
      this.b.a(0.0F, 10.0F, 0.0F);
      int _snowman = 9;
      int _snowmanx = 6;
      this.f = new dwn(this, 40, 0);
      this.f.a(-3.0F, 0.0F, -3.0F, 6.0F, 9.0F, 6.0F);
      this.f.a(-5.5F, 15.0F, 9.0F);
      this.g = new dwn(this, 40, 0);
      this.g.a(-3.0F, 0.0F, -3.0F, 6.0F, 9.0F, 6.0F);
      this.g.a(5.5F, 15.0F, 9.0F);
      this.h = new dwn(this, 40, 0);
      this.h.a(-3.0F, 0.0F, -3.0F, 6.0F, 9.0F, 6.0F);
      this.h.a(-5.5F, 15.0F, -9.0F);
      this.i = new dwn(this, 40, 0);
      this.i.a(-3.0F, 0.0F, -3.0F, 6.0F, 9.0F, 6.0F);
      this.i.a(5.5F, 15.0F, -9.0F);
   }

   public void a(T var1, float var2, float var3, float var4) {
      super.a(_snowman, _snowman, _snowman, _snowman);
      this.j = _snowman.y(_snowman);
      this.k = _snowman.z(_snowman);
      this.l = _snowman.w_() ? 0.0F : _snowman.A(_snowman);
   }

   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      boolean _snowman = _snowman.eK() > 0;
      boolean _snowmanx = _snowman.eL();
      int _snowmanxx = _snowman.eU();
      boolean _snowmanxxx = _snowman.eO();
      boolean _snowmanxxxx = _snowman.ff();
      if (_snowman) {
         this.a.e = 0.35F * afm.a(0.6F * _snowman);
         this.a.f = 0.35F * afm.a(0.6F * _snowman);
         this.h.d = -0.75F * afm.a(0.3F * _snowman);
         this.i.d = 0.75F * afm.a(0.3F * _snowman);
      } else {
         this.a.f = 0.0F;
      }

      if (_snowmanx) {
         if (_snowmanxx < 15) {
            this.a.d = (float) (-Math.PI / 4) * (float)_snowmanxx / 14.0F;
         } else if (_snowmanxx < 20) {
            float _snowmanxxxxx = (float)((_snowmanxx - 15) / 5);
            this.a.d = (float) (-Math.PI / 4) + (float) (Math.PI / 4) * _snowmanxxxxx;
         }
      }

      if (this.j > 0.0F) {
         this.b.d = duw.a(this.b.d, 1.7407963F, this.j);
         this.a.d = duw.a(this.a.d, (float) (Math.PI / 2), this.j);
         this.h.f = -0.27079642F;
         this.i.f = 0.27079642F;
         this.f.f = 0.5707964F;
         this.g.f = -0.5707964F;
         if (_snowmanxxx) {
            this.a.d = (float) (Math.PI / 2) + 0.2F * afm.a(_snowman * 0.6F);
            this.h.d = -0.4F - 0.2F * afm.a(_snowman * 0.6F);
            this.i.d = -0.4F - 0.2F * afm.a(_snowman * 0.6F);
         }

         if (_snowmanxxxx) {
            this.a.d = 2.1707964F;
            this.h.d = -0.9F;
            this.i.d = -0.9F;
         }
      } else {
         this.f.f = 0.0F;
         this.g.f = 0.0F;
         this.h.f = 0.0F;
         this.i.f = 0.0F;
      }

      if (this.k > 0.0F) {
         this.f.d = -0.6F * afm.a(_snowman * 0.15F);
         this.g.d = 0.6F * afm.a(_snowman * 0.15F);
         this.h.d = 0.3F * afm.a(_snowman * 0.25F);
         this.i.d = -0.3F * afm.a(_snowman * 0.25F);
         this.a.d = duw.a(this.a.d, (float) (Math.PI / 2), this.k);
      }

      if (this.l > 0.0F) {
         this.a.d = duw.a(this.a.d, 2.0561945F, this.l);
         this.f.d = -0.5F * afm.a(_snowman * 0.5F);
         this.g.d = 0.5F * afm.a(_snowman * 0.5F);
         this.h.d = 0.5F * afm.a(_snowman * 0.5F);
         this.i.d = -0.5F * afm.a(_snowman * 0.5F);
      }
   }
}
