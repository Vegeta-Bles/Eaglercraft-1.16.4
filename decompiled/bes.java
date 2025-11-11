import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;

public class bes extends ber implements bdd {
   private static final us<Boolean> bp = uv.a(bes.class, uu.i);
   private static final us<Boolean> bq = uv.a(bes.class, uu.i);
   private static final us<Boolean> br = uv.a(bes.class, uu.i);
   private static final UUID bs = UUID.fromString("766bfa64-11f3-11ea-8d71-362b9e155667");
   private static final arj bt = new arj(bs, "Baby speed boost", 0.2F, arj.a.b);
   private final apa bu = new apa(8);
   private boolean bv = false;
   protected static final ImmutableList<azc<? extends azb<? super bes>>> d = ImmutableList.of(azc.c, azc.d, azc.b, azc.f, azc.k);
   protected static final ImmutableList<ayd<?>> bo = ImmutableList.of(
      ayd.n,
      ayd.v,
      ayd.g,
      ayd.h,
      ayd.k,
      ayd.l,
      ayd.Y,
      ayd.X,
      ayd.J,
      ayd.x,
      ayd.y,
      ayd.m,
      new ayd[]{
         ayd.D,
         ayd.o,
         ayd.p,
         ayd.q,
         ayd.t,
         ayd.L,
         ayd.M,
         ayd.z,
         ayd.N,
         ayd.O,
         ayd.Q,
         ayd.P,
         ayd.S,
         ayd.T,
         ayd.R,
         ayd.V,
         ayd.K,
         ayd.ab,
         ayd.s,
         ayd.ac,
         ayd.ad,
         ayd.U,
         ayd.W,
         ayd.ae,
         ayd.af,
         ayd.ag
      }
   );

   public bes(aqe<? extends ber> var1, brx var2) {
      super(_snowman, _snowman);
      this.f = 5;
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      if (this.w_()) {
         _snowman.a("IsBaby", true);
      }

      if (this.bv) {
         _snowman.a("CannotHunt", true);
      }

      _snowman.a("Inventory", this.bu.g());
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.a(_snowman.q("IsBaby"));
      this.v(_snowman.q("CannotHunt"));
      this.bu.a(_snowman.d("Inventory", 10));
   }

   @Override
   protected void a(apk var1, int var2, boolean var3) {
      super.a(_snowman, _snowman, _snowman);
      this.bu.f().forEach(this::a);
   }

   protected bmb k(bmb var1) {
      return this.bu.a(_snowman);
   }

   protected boolean l(bmb var1) {
      return this.bu.b(_snowman);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(bp, false);
      this.R.a(bq, false);
      this.R.a(br, false);
   }

   @Override
   public void a(us<?> var1) {
      super.a(_snowman);
      if (bp.equals(_snowman)) {
         this.x_();
      }
   }

   public static ark.a eT() {
      return bdq.eR().a(arl.a, 16.0).a(arl.d, 0.35F).a(arl.f, 5.0);
   }

   public static boolean b(aqe<bes> var0, bry var1, aqp var2, fx var3, Random var4) {
      return !_snowman.d_(_snowman.c()).a(bup.iK);
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      if (_snowman != aqp.d) {
         if (_snowman.u_().nextFloat() < 0.2F) {
            this.a(true);
         } else if (this.eM()) {
            this.a(aqf.a, this.eV());
         }
      }

      bet.a(this);
      this.a(_snowman);
      this.b(_snowman);
      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected boolean L() {
      return false;
   }

   @Override
   public boolean h(double var1) {
      return !this.eu();
   }

   @Override
   protected void a(aos var1) {
      if (this.eM()) {
         this.d(aqf.f, new bmb(bmd.lo));
         this.d(aqf.e, new bmb(bmd.lp));
         this.d(aqf.d, new bmb(bmd.lq));
         this.d(aqf.c, new bmb(bmd.lr));
      }
   }

   private void d(aqf var1, bmb var2) {
      if (this.l.t.nextFloat() < 0.1F) {
         this.a(_snowman, _snowman);
      }
   }

   @Override
   protected arf.b<bes> cK() {
      return arf.a(bo, d);
   }

   @Override
   protected arf<?> a(Dynamic<?> var1) {
      return bet.a(this, this.cK().a(_snowman));
   }

   @Override
   public arf<bes> cJ() {
      return (arf<bes>)super.cJ();
   }

   @Override
   public aou b(bfw var1, aot var2) {
      aou _snowman = super.b(_snowman, _snowman);
      if (_snowman.a()) {
         return _snowman;
      } else if (!this.l.v) {
         return bet.a(this, _snowman, _snowman);
      } else {
         boolean _snowmanx = bet.b(this, _snowman.b(_snowman)) && this.eN() != beu.d;
         return _snowmanx ? aou.a : aou.c;
      }
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return this.w_() ? 0.93F : 1.74F;
   }

   @Override
   public double bc() {
      return (double)this.cz() * 0.92;
   }

   @Override
   public void a(boolean var1) {
      this.ab().b(bp, _snowman);
      if (!this.l.v) {
         arh _snowman = this.a(arl.d);
         _snowman.d(bt);
         if (_snowman) {
            _snowman.b(bt);
         }
      }
   }

   @Override
   public boolean w_() {
      return this.ab().a(bp);
   }

   private void v(boolean var1) {
      this.bv = _snowman;
   }

   @Override
   protected boolean m() {
      return !this.bv;
   }

   @Override
   protected void N() {
      this.l.Z().a("piglinBrain");
      this.cJ().a((aag)this.l, this);
      this.l.Z().c();
      bet.b(this);
      super.N();
   }

   @Override
   protected int d(bfw var1) {
      return this.f;
   }

   @Override
   protected void c(aag var1) {
      bet.c(this);
      this.bu.f().forEach(this::a);
      super.c(_snowman);
   }

   private bmb eV() {
      return (double)this.J.nextFloat() < 0.5 ? new bmb(bmd.qQ) : new bmb(bmd.kv);
   }

   private boolean eW() {
      return this.R.a(bq);
   }

   @Override
   public void b(boolean var1) {
      this.R.b(bq, _snowman);
   }

   @Override
   public void U_() {
      this.aI = 0;
   }

   @Override
   public beu eN() {
      if (this.eU()) {
         return beu.e;
      } else if (bet.a(this.dE().b())) {
         return beu.d;
      } else if (this.eF() && this.eO()) {
         return beu.a;
      } else if (this.eW()) {
         return beu.c;
      } else {
         return this.eF() && this.a(bmd.qQ) ? beu.b : beu.f;
      }
   }

   public boolean eU() {
      return this.R.a(br);
   }

   public void u(boolean var1) {
      this.R.b(br, _snowman);
   }

   @Override
   public boolean a(apk var1, float var2) {
      boolean _snowman = super.a(_snowman, _snowman);
      if (this.l.v) {
         return false;
      } else {
         if (_snowman && _snowman.k() instanceof aqm) {
            bet.a(this, (aqm)_snowman.k());
         }

         return _snowman;
      }
   }

   @Override
   public void a(aqm var1, float var2) {
      this.b(this, 1.6F);
   }

   @Override
   public void a(aqm var1, bmb var2, bgm var3, float var4) {
      this.a(this, _snowman, _snowman, _snowman, 1.6F);
   }

   @Override
   public boolean a(bmo var1) {
      return _snowman == bmd.qQ;
   }

   protected void m(bmb var1) {
      this.b(aqf.a, _snowman);
   }

   protected void n(bmb var1) {
      if (_snowman.b() == bet.a) {
         this.a(aqf.b, _snowman);
         this.d(aqf.b);
      } else {
         this.b(aqf.b, _snowman);
      }
   }

   @Override
   public boolean i(bmb var1) {
      return this.l.V().b(brt.b) && this.et() && bet.a(this, _snowman);
   }

   protected boolean o(bmb var1) {
      aqf _snowman = aqn.j(_snowman);
      bmb _snowmanx = this.b(_snowman);
      return this.a(_snowman, _snowmanx);
   }

   @Override
   protected boolean a(bmb var1, bmb var2) {
      if (bpu.d(_snowman)) {
         return false;
      } else {
         boolean _snowman = bet.a(_snowman.b()) || _snowman.b() == bmd.qQ;
         boolean _snowmanx = bet.a(_snowman.b()) || _snowman.b() == bmd.qQ;
         if (_snowman && !_snowmanx) {
            return true;
         } else if (!_snowman && _snowmanx) {
            return false;
         } else {
            return this.eM() && _snowman.b() != bmd.qQ && _snowman.b() == bmd.qQ ? false : super.a(_snowman, _snowman);
         }
      }
   }

   @Override
   protected void b(bcv var1) {
      this.a(_snowman);
      bet.a(this, _snowman);
   }

   @Override
   public boolean a(aqa var1, boolean var2) {
      if (this.w_() && _snowman.X() == aqe.G) {
         _snowman = this.b(_snowman, 3);
      }

      return super.a(_snowman, _snowman);
   }

   private aqa b(aqa var1, int var2) {
      List<aqa> _snowman = _snowman.cn();
      return _snowman != 1 && !_snowman.isEmpty() ? this.b(_snowman.get(0), _snowman - 1) : _snowman;
   }

   @Override
   protected adp I() {
      return this.l.v ? null : bet.d(this).orElse(null);
   }

   @Override
   protected adp e(apk var1) {
      return adq.kY;
   }

   @Override
   protected adp dq() {
      return adq.kW;
   }

   @Override
   protected void b(fx var1, ceh var2) {
      this.a(adq.la, 0.15F, 1.0F);
   }

   protected void a(adp var1) {
      this.a(_snowman, this.dG(), this.dH());
   }

   @Override
   protected void eP() {
      this.a(adq.lb);
   }
}
