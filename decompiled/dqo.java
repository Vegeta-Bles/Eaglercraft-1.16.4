public class dqo extends dpv implements dqq<bjb> {
   private final bjb c;
   private final bin p = new bin() {
      @Override
      public void a(bic var1, gj<bmb> var2) {
         dqo.this.o();
      }

      @Override
      public void a(bic var1, int var2, bmb var3) {
         dqo.this.o();
      }

      @Override
      public void a(bic var1, int var2, int var3) {
         if (_snowman == 0) {
            dqo.this.p();
         }
      }
   };

   public dqo(bjb var1, bfv var2, nr var3) {
      this.c = _snowman;
   }

   public bjb n() {
      return this.c;
   }

   @Override
   protected void b() {
      super.b();
      this.c.a(this.p);
   }

   @Override
   public void at_() {
      this.i.s.m();
      super.at_();
   }

   @Override
   public void e() {
      super.e();
      this.c.b(this.p);
   }

   @Override
   protected void i() {
      if (this.i.s.eK()) {
         this.a((dlj)(new dlj(this.k / 2 - 100, 196, 98, 20, nq.c, var1 -> this.i.a(null))));
         this.a((dlj)(new dlj(this.k / 2 + 2, 196, 98, 20, new of("lectern.take_book"), var1 -> this.c(3))));
      } else {
         super.i();
      }
   }

   @Override
   protected void l() {
      this.c(1);
   }

   @Override
   protected void m() {
      this.c(2);
   }

   @Override
   protected boolean b(int var1) {
      if (_snowman != this.c.f()) {
         this.c(100 + _snowman);
         return true;
      } else {
         return false;
      }
   }

   private void c(int var1) {
      this.i.q.a(this.c.b, _snowman);
   }

   @Override
   public boolean ay_() {
      return false;
   }

   private void o() {
      bmb _snowman = this.c.e();
      this.a(dpv.a.a(_snowman));
   }

   private void p() {
      this.a(this.c.f());
   }
}
