public class ccv extends ccj implements cdc, cdm {
   public float a;
   public float b;
   public int c;
   private int g;

   public ccv() {
      super(cck.d);
   }

   @Override
   public void aj_() {
      if (++this.g % 20 * 4 == 0) {
         this.d.a(this.e, bup.ek, 1, this.c);
      }

      this.b = this.a;
      int _snowman = this.e.u();
      int _snowmanx = this.e.v();
      int _snowmanxx = this.e.w();
      float _snowmanxxx = 0.1F;
      if (this.c > 0 && this.a == 0.0F) {
         double _snowmanxxxx = (double)_snowman + 0.5;
         double _snowmanxxxxx = (double)_snowmanxx + 0.5;
         this.d.a(null, _snowmanxxxx, (double)_snowmanx + 0.5, _snowmanxxxxx, adq.do_, adr.e, 0.5F, this.d.t.nextFloat() * 0.1F + 0.9F);
      }

      if (this.c == 0 && this.a > 0.0F || this.c > 0 && this.a < 1.0F) {
         float _snowmanxxxx = this.a;
         if (this.c > 0) {
            this.a += 0.1F;
         } else {
            this.a -= 0.1F;
         }

         if (this.a > 1.0F) {
            this.a = 1.0F;
         }

         float _snowmanxxxxx = 0.5F;
         if (this.a < 0.5F && _snowmanxxxx >= 0.5F) {
            double _snowmanxxxxxx = (double)_snowman + 0.5;
            double _snowmanxxxxxxx = (double)_snowmanxx + 0.5;
            this.d.a(null, _snowmanxxxxxx, (double)_snowmanx + 0.5, _snowmanxxxxxxx, adq.dn, adr.e, 0.5F, this.d.t.nextFloat() * 0.1F + 0.9F);
         }

         if (this.a < 0.0F) {
            this.a = 0.0F;
         }
      }
   }

   @Override
   public boolean a_(int var1, int var2) {
      if (_snowman == 1) {
         this.c = _snowman;
         return true;
      } else {
         return super.a_(_snowman, _snowman);
      }
   }

   @Override
   public void al_() {
      this.s();
      super.al_();
   }

   public void d() {
      this.c++;
      this.d.a(this.e, bup.ek, 1, this.c);
   }

   public void f() {
      this.c--;
      this.d.a(this.e, bup.ek, 1, this.c);
   }

   public boolean a(bfw var1) {
      return this.d.c(this.e) != this ? false : !(_snowman.h((double)this.e.u() + 0.5, (double)this.e.v() + 0.5, (double)this.e.w() + 0.5) > 64.0);
   }

   @Override
   public float a(float var1) {
      return afm.g(_snowman, this.b, this.a);
   }
}
