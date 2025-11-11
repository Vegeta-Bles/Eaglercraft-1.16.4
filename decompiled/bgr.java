public class bgr extends bga {
   private int f = 200;

   public bgr(aqe<? extends bgr> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bgr(brx var1, aqm var2) {
      super(aqe.aB, _snowman, _snowman);
   }

   public bgr(brx var1, double var2, double var4, double var6) {
      super(aqe.aB, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void j() {
      super.j();
      if (this.l.v && !this.b) {
         this.l.a(hh.H, this.cD(), this.cE(), this.cH(), 0.0, 0.0, 0.0);
      }
   }

   @Override
   protected bmb m() {
      return new bmb(bmd.qk);
   }

   @Override
   protected void a(aqm var1) {
      super.a(_snowman);
      apu _snowman = new apu(apw.x, this.f, 0);
      _snowman.c(_snowman);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      if (_snowman.e("Duration")) {
         this.f = _snowman.h("Duration");
      }
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("Duration", this.f);
   }
}
