import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import java.util.Random;
import javax.annotation.Nullable;

public class bem extends azz implements bdi, beo {
   private static final us<Boolean> bq = uv.a(bem.class, uu.i);
   private int br;
   private int bs = 0;
   private boolean bt = false;
   protected static final ImmutableList<? extends azc<? extends azb<? super bem>>> bo = ImmutableList.of(azc.c, azc.d, azc.n, azc.m);
   protected static final ImmutableList<? extends ayd<?>> bp = ImmutableList.of(
      ayd.r, ayd.g, ayd.h, ayd.k, ayd.l, ayd.n, ayd.m, ayd.D, ayd.t, ayd.o, ayd.p, ayd.aa, new ayd[]{ayd.z, ayd.ac, ayd.ad, ayd.Z, ayd.I, ayd.ag, ayd.ah}
   );

   public bem(aqe<? extends bem> var1, brx var2) {
      super(_snowman, _snowman);
      this.f = 5;
   }

   @Override
   public boolean a(bfw var1) {
      return !this.eB();
   }

   public static ark.a eK() {
      return bdq.eR().a(arl.a, 40.0).a(arl.d, 0.3F).a(arl.c, 0.6F).a(arl.g, 1.0).a(arl.f, 6.0);
   }

   @Override
   public boolean B(aqa var1) {
      if (!(_snowman instanceof aqm)) {
         return false;
      } else {
         this.br = 10;
         this.l.a(this, (byte)4);
         this.a(adq.fD, 1.0F, this.dH());
         ben.a(this, (aqm)_snowman);
         return beo.a(this, (aqm)_snowman);
      }
   }

   @Override
   protected void e(aqm var1) {
      if (this.eL()) {
         beo.b(this, _snowman);
      }
   }

   @Override
   public boolean a(apk var1, float var2) {
      boolean _snowman = super.a(_snowman, _snowman);
      if (this.l.v) {
         return false;
      } else {
         if (_snowman && _snowman.k() instanceof aqm) {
            ben.b(this, (aqm)_snowman.k());
         }

         return _snowman;
      }
   }

   @Override
   protected arf.b<bem> cK() {
      return arf.a(bp, bo);
   }

   @Override
   protected arf<?> a(Dynamic<?> var1) {
      return ben.a(this.cK().a(_snowman));
   }

   @Override
   public arf<bem> cJ() {
      return (arf<bem>)super.cJ();
   }

   @Override
   protected void N() {
      this.l.Z().a("hoglinBrain");
      this.cJ().a((aag)this.l, this);
      this.l.Z().c();
      ben.a(this);
      if (this.eN()) {
         this.bs++;
         if (this.bs > 300) {
            this.a(adq.fE);
            this.c((aag)this.l);
         }
      } else {
         this.bs = 0;
      }
   }

   @Override
   public void k() {
      if (this.br > 0) {
         this.br--;
      }

      super.k();
   }

   @Override
   protected void m() {
      if (this.w_()) {
         this.f = 3;
         this.a(arl.f).a(0.5);
      } else {
         this.f = 5;
         this.a(arl.f).a(6.0);
      }
   }

   public static boolean c(aqe<bem> var0, bry var1, aqp var2, fx var3, Random var4) {
      return !_snowman.d_(_snowman.c()).a(bup.iK);
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      if (_snowman.u_().nextFloat() < 0.2F) {
         this.a(true);
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean h(double var1) {
      return !this.eu();
   }

   @Override
   public float a(fx var1, brz var2) {
      if (ben.a(this, _snowman)) {
         return -1.0F;
      } else {
         return _snowman.d_(_snowman.c()).a(bup.mu) ? 10.0F : 0.0F;
      }
   }

   @Override
   public double bc() {
      return (double)this.cz() - (this.w_() ? 0.2 : 0.15);
   }

   @Override
   public aou b(bfw var1, aot var2) {
      aou _snowman = super.b(_snowman, _snowman);
      if (_snowman.a()) {
         this.es();
      }

      return _snowman;
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 4) {
         this.br = 10;
         this.a(adq.fD, 1.0F, this.dH());
      } else {
         super.a(_snowman);
      }
   }

   @Override
   public int eM() {
      return this.br;
   }

   @Override
   protected boolean cV() {
      return true;
   }

   @Override
   protected int d(bfw var1) {
      return this.f;
   }

   private void c(aag var1) {
      bei _snowman = this.a(aqe.aX, true);
      if (_snowman != null) {
         _snowman.c(new apu(apw.i, 200, 0));
      }
   }

   @Override
   public boolean k(bmb var1) {
      return _snowman.b() == bmd.bw;
   }

   public boolean eL() {
      return !this.w_();
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(bq, false);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      if (this.eV()) {
         _snowman.a("IsImmuneToZombification", true);
      }

      _snowman.b("TimeInOverworld", this.bs);
      if (this.bt) {
         _snowman.a("CannotBeHunted", true);
      }
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.t(_snowman.q("IsImmuneToZombification"));
      this.bs = _snowman.h("TimeInOverworld");
      this.u(_snowman.q("CannotBeHunted"));
   }

   public void t(boolean var1) {
      this.ab().b(bq, _snowman);
   }

   private boolean eV() {
      return this.ab().a(bq);
   }

   public boolean eN() {
      return !this.l.k().g() && !this.eV() && !this.eD();
   }

   private void u(boolean var1) {
      this.bt = _snowman;
   }

   public boolean eO() {
      return this.eL() && !this.bt;
   }

   @Nullable
   @Override
   public apy a(aag var1, apy var2) {
      bem _snowman = aqe.G.a(_snowman);
      if (_snowman != null) {
         _snowman.es();
      }

      return _snowman;
   }

   @Override
   public boolean eP() {
      return !ben.c(this) && super.eP();
   }

   @Override
   public adr cu() {
      return adr.f;
   }

   @Override
   protected adp I() {
      return this.l.v ? null : ben.b(this).orElse(null);
   }

   @Override
   protected adp e(apk var1) {
      return adq.fG;
   }

   @Override
   protected adp dq() {
      return adq.fF;
   }

   @Override
   protected adp av() {
      return adq.gi;
   }

   @Override
   protected adp aw() {
      return adq.gh;
   }

   @Override
   protected void b(fx var1, ceh var2) {
      this.a(adq.fI, 0.15F, 1.0F);
   }

   protected void a(adp var1) {
      this.a(_snowman, this.dG(), this.dH());
   }

   @Override
   protected void M() {
      super.M();
      rz.a(this);
   }
}
