public class ccc extends cdd {
   private gj<bmb> a = gj.a(27, bmb.b);
   private int b;

   private ccc(cck<?> var1) {
      super(_snowman);
   }

   public ccc() {
      this(cck.z);
   }

   @Override
   public md a(md var1) {
      super.a(_snowman);
      if (!this.c(_snowman)) {
         aoo.a(_snowman, this.a);
      }

      return _snowman;
   }

   @Override
   public void a(ceh var1, md var2) {
      super.a(_snowman, _snowman);
      this.a = gj.a(this.Z_(), bmb.b);
      if (!this.b(_snowman)) {
         aoo.b(_snowman, this.a);
      }
   }

   @Override
   public int Z_() {
      return 27;
   }

   @Override
   protected gj<bmb> f() {
      return this.a;
   }

   @Override
   protected void a(gj<bmb> var1) {
      this.a = _snowman;
   }

   @Override
   protected nr g() {
      return new of("container.barrel");
   }

   @Override
   protected bic a(int var1, bfv var2) {
      return bij.a(_snowman, _snowman, this);
   }

   @Override
   public void c_(bfw var1) {
      if (!_snowman.a_()) {
         if (this.b < 0) {
            this.b = 0;
         }

         this.b++;
         ceh _snowman = this.p();
         boolean _snowmanx = _snowman.c(btx.b);
         if (!_snowmanx) {
            this.a(_snowman, adq.aj);
            this.a(_snowman, true);
         }

         this.j();
      }
   }

   private void j() {
      this.d.J().a(this.o(), this.p().b(), 5);
   }

   public void h() {
      int _snowman = this.e.u();
      int _snowmanx = this.e.v();
      int _snowmanxx = this.e.w();
      this.b = ccn.a(this.d, this, _snowman, _snowmanx, _snowmanxx);
      if (this.b > 0) {
         this.j();
      } else {
         ceh _snowmanxxx = this.p();
         if (!_snowmanxxx.a(bup.lS)) {
            this.al_();
            return;
         }

         boolean _snowmanxxxx = _snowmanxxx.c(btx.b);
         if (_snowmanxxxx) {
            this.a(_snowmanxxx, adq.ai);
            this.a(_snowmanxxx, false);
         }
      }
   }

   @Override
   public void b_(bfw var1) {
      if (!_snowman.a_()) {
         this.b--;
      }
   }

   private void a(ceh var1, boolean var2) {
      this.d.a(this.o(), _snowman.a(btx.b, Boolean.valueOf(_snowman)), 3);
   }

   private void a(ceh var1, adp var2) {
      gr _snowman = _snowman.c(btx.a).p();
      double _snowmanx = (double)this.e.u() + 0.5 + (double)_snowman.u() / 2.0;
      double _snowmanxx = (double)this.e.v() + 0.5 + (double)_snowman.v() / 2.0;
      double _snowmanxxx = (double)this.e.w() + 0.5 + (double)_snowman.w() / 2.0;
      this.d.a(null, _snowmanx, _snowmanxx, _snowmanxxx, _snowman, adr.e, 0.5F, this.d.t.nextFloat() * 0.1F + 0.9F);
   }
}
