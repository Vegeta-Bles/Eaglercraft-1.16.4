import javax.annotation.Nullable;

public abstract class ber extends bdq {
   protected static final us<Boolean> b = uv.a(ber.class, uu.i);
   protected int c = 0;

   public ber(aqe<? extends ber> var1, brx var2) {
      super(_snowman, _snowman);
      this.p(true);
      this.eS();
      this.a(cwz.l, 16.0F);
      this.a(cwz.m, -1.0F);
   }

   private void eS() {
      if (azi.a(this)) {
         ((ayi)this.x()).a(true);
      }
   }

   protected abstract boolean m();

   public void t(boolean var1) {
      this.ab().b(b, _snowman);
   }

   protected boolean eK() {
      return this.ab().a(b);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(b, false);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      if (this.eK()) {
         _snowman.a("IsImmuneToZombification", true);
      }

      _snowman.b("TimeInOverworld", this.c);
   }

   @Override
   public double bb() {
      return this.w_() ? -0.05 : -0.45;
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.t(_snowman.q("IsImmuneToZombification"));
      this.c = _snowman.h("TimeInOverworld");
   }

   @Override
   protected void N() {
      super.N();
      if (this.eL()) {
         this.c++;
      } else {
         this.c = 0;
      }

      if (this.c > 300) {
         this.eP();
         this.c((aag)this.l);
      }
   }

   public boolean eL() {
      return !this.l.k().g() && !this.eK() && !this.eD();
   }

   protected void c(aag var1) {
      bel _snowman = this.a(aqe.bb, true);
      if (_snowman != null) {
         _snowman.c(new apu(apw.i, 200, 0));
      }
   }

   public boolean eM() {
      return !this.w_();
   }

   public abstract beu eN();

   @Nullable
   @Override
   public aqm A() {
      return this.bg.c(ayd.o).orElse(null);
   }

   protected boolean eO() {
      return this.dD().b() instanceof bni;
   }

   @Override
   public void F() {
      if (bet.d(this)) {
         super.F();
      }
   }

   @Override
   protected void M() {
      super.M();
      rz.a(this);
   }

   protected abstract void eP();
}
