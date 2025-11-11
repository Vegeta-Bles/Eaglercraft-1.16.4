import java.util.List;

public class bht extends bhm implements ccx {
   private boolean d = true;
   private int e = -1;
   private final fx f = fx.b;

   public bht(aqe<? extends bht> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bht(brx var1, double var2, double var4, double var6) {
      super(aqe.X, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public bhl.a o() {
      return bhl.a.f;
   }

   @Override
   public ceh q() {
      return bup.fy.n();
   }

   @Override
   public int s() {
      return 1;
   }

   @Override
   public int Z_() {
      return 5;
   }

   @Override
   public void a(int var1, int var2, int var3, boolean var4) {
      boolean _snowman = !_snowman;
      if (_snowman != this.u()) {
         this.o(_snowman);
      }
   }

   public boolean u() {
      return this.d;
   }

   public void o(boolean var1) {
      this.d = _snowman;
   }

   @Override
   public brx v() {
      return this.l;
   }

   @Override
   public double x() {
      return this.cD();
   }

   @Override
   public double z() {
      return this.cE() + 0.5;
   }

   @Override
   public double A() {
      return this.cH();
   }

   @Override
   public void j() {
      super.j();
      if (!this.l.v && this.aX() && this.u()) {
         fx _snowman = this.cB();
         if (_snowman.equals(this.f)) {
            this.e--;
         } else {
            this.m(0);
         }

         if (!this.C()) {
            this.m(0);
            if (this.B()) {
               this.m(4);
               this.X_();
            }
         }
      }
   }

   public boolean B() {
      if (ccy.a((ccx)this)) {
         return true;
      } else {
         List<bcv> _snowman = this.l.a(bcv.class, this.cc().c(0.25, 0.0, 0.25), aqd.a);
         if (!_snowman.isEmpty()) {
            ccy.a(this, _snowman.get(0));
         }

         return false;
      }
   }

   @Override
   public void a(apk var1) {
      super.a(_snowman);
      if (this.l.V().b(brt.g)) {
         this.a(bup.fy);
      }
   }

   @Override
   protected void b(md var1) {
      super.b(_snowman);
      _snowman.b("TransferCooldown", this.e);
      _snowman.a("Enabled", this.d);
   }

   @Override
   protected void a(md var1) {
      super.a(_snowman);
      this.e = _snowman.h("TransferCooldown");
      this.d = _snowman.e("Enabled") ? _snowman.q("Enabled") : true;
   }

   public void m(int var1) {
      this.e = _snowman;
   }

   public boolean C() {
      return this.e > 0;
   }

   @Override
   public bic a(int var1, bfv var2) {
      return new bix(_snowman, _snowman, this);
   }
}
