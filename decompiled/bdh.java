import java.util.Random;

public class bdh extends bdq {
   private int b;
   private boolean c;

   public bdh(aqe<? extends bdh> var1, brx var2) {
      super(_snowman, _snowman);
      this.f = 3;
   }

   @Override
   protected void o() {
      this.bk.a(1, new avp(this));
      this.bk.a(2, new awf(this, 1.0, false));
      this.bk.a(3, new axk(this, 1.0));
      this.bk.a(7, new awd(this, bfw.class, 8.0F));
      this.bk.a(8, new aws(this));
      this.bl.a(1, new axp(this).a());
      this.bl.a(2, new axq<>(this, bfw.class, true));
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return 0.13F;
   }

   public static ark.a m() {
      return bdq.eR().a(arl.a, 8.0).a(arl.d, 0.25).a(arl.f, 2.0);
   }

   @Override
   protected boolean aC() {
      return false;
   }

   @Override
   protected adp I() {
      return adq.dE;
   }

   @Override
   protected adp e(apk var1) {
      return adq.dG;
   }

   @Override
   protected adp dq() {
      return adq.dF;
   }

   @Override
   protected void b(fx var1, ceh var2) {
      this.a(adq.dH, 0.15F, 1.0F);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.b = _snowman.h("Lifetime");
      this.c = _snowman.q("PlayerSpawned");
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("Lifetime", this.b);
      _snowman.a("PlayerSpawned", this.c);
   }

   @Override
   public void j() {
      this.aA = this.p;
      super.j();
   }

   @Override
   public void n(float var1) {
      this.p = _snowman;
      super.n(_snowman);
   }

   @Override
   public double bb() {
      return 0.1;
   }

   public boolean eK() {
      return this.c;
   }

   public void t(boolean var1) {
      this.c = _snowman;
   }

   @Override
   public void k() {
      super.k();
      if (this.l.v) {
         for (int _snowman = 0; _snowman < 2; _snowman++) {
            this.l.a(hh.Q, this.d(0.5), this.cF(), this.g(0.5), (this.J.nextDouble() - 0.5) * 2.0, -this.J.nextDouble(), (this.J.nextDouble() - 0.5) * 2.0);
         }
      } else {
         if (!this.eu()) {
            this.b++;
         }

         if (this.b >= 2400) {
            this.ad();
         }
      }
   }

   public static boolean b(aqe<bdh> var0, bry var1, aqp var2, fx var3, Random var4) {
      if (c(_snowman, _snowman, _snowman, _snowman, _snowman)) {
         bfw _snowman = _snowman.a((double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5, 5.0, true);
         return _snowman == null;
      } else {
         return false;
      }
   }

   @Override
   public aqq dC() {
      return aqq.c;
   }
}
