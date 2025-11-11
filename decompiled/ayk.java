public class ayk extends ayi {
   private fx p;

   public ayk(aqn var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   public cxd a(fx var1, int var2) {
      this.p = _snowman;
      return super.a(_snowman, _snowman);
   }

   @Override
   public cxd a(aqa var1, int var2) {
      this.p = _snowman.cB();
      return super.a(_snowman, _snowman);
   }

   @Override
   public boolean a(aqa var1, double var2) {
      cxd _snowman = this.a(_snowman, 0);
      if (_snowman != null) {
         return this.a(_snowman, _snowman);
      } else {
         this.p = _snowman.cB();
         this.d = _snowman;
         return true;
      }
   }

   @Override
   public void c() {
      if (!this.m()) {
         super.c();
      } else {
         if (this.p != null) {
            if (!this.p.a(this.a.cA(), (double)this.a.cy())
               && (!(this.a.cE() > (double)this.p.v()) || !new fx((double)this.p.u(), this.a.cE(), (double)this.p.w()).a(this.a.cA(), (double)this.a.cy()))) {
               this.a.u().a((double)this.p.u(), (double)this.p.v(), (double)this.p.w(), this.d);
            } else {
               this.p = null;
            }
         }
      }
   }
}
