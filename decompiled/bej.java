import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bej extends bdq {
   private static final UUID b = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
   private static final arj c = new arj(b, "Baby speed boost", 0.5, arj.a.b);
   private static final us<Boolean> d = uv.a(bej.class, uu.i);
   private static final us<Integer> bo = uv.a(bej.class, uu.b);
   private static final us<Boolean> bp = uv.a(bej.class, uu.i);
   private static final Predicate<aor> bq = var0 -> var0 == aor.d;
   private final avg br = new avg(this, bq);
   private boolean bs;
   private int bt;
   private int bu;

   public bej(aqe<? extends bej> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bej(brx var1) {
      this(aqe.aY, _snowman);
   }

   @Override
   protected void o() {
      this.bk.a(4, new bej.a(this, 1.0, 3));
      this.bk.a(8, new awd(this, bfw.class, 8.0F));
      this.bk.a(8, new aws(this));
      this.m();
   }

   @Override
   protected void m() {
      this.bk.a(2, new axm(this, 1.0, false));
      this.bk.a(6, new awi(this, 1.0, true, 4, this::eU));
      this.bk.a(7, new axk(this, 1.0));
      this.bl.a(1, new axp(this).a(bel.class));
      this.bl.a(2, new axq<>(this, bfw.class, true));
      this.bl.a(3, new axq<>(this, bfe.class, false));
      this.bl.a(3, new axq<>(this, bai.class, true));
      this.bl.a(5, new axq<>(this, bax.class, 10, true, false, bax.bo));
   }

   public static ark.a eS() {
      return bdq.eR().a(arl.b, 35.0).a(arl.d, 0.23F).a(arl.f, 3.0).a(arl.i, 2.0).a(arl.l);
   }

   @Override
   protected void e() {
      super.e();
      this.ab().a(d, false);
      this.ab().a(bo, 0);
      this.ab().a(bp, false);
   }

   public boolean eT() {
      return this.ab().a(bp);
   }

   public boolean eU() {
      return this.bs;
   }

   public void u(boolean var1) {
      if (this.eK() && azi.a(this)) {
         if (this.bs != _snowman) {
            this.bs = _snowman;
            ((ayi)this.x()).a(_snowman);
            if (_snowman) {
               this.bk.a(1, this.br);
            } else {
               this.bk.a(this.br);
            }
         }
      } else if (this.bs) {
         this.bk.a(this.br);
         this.bs = false;
      }
   }

   protected boolean eK() {
      return true;
   }

   @Override
   public boolean w_() {
      return this.ab().a(d);
   }

   @Override
   protected int d(bfw var1) {
      if (this.w_()) {
         this.f = (int)((float)this.f * 2.5F);
      }

      return super.d(_snowman);
   }

   @Override
   public void a(boolean var1) {
      this.ab().b(d, _snowman);
      if (this.l != null && !this.l.v) {
         arh _snowman = this.a(arl.d);
         _snowman.d(c);
         if (_snowman) {
            _snowman.b(c);
         }
      }
   }

   @Override
   public void a(us<?> var1) {
      if (d.equals(_snowman)) {
         this.x_();
      }

      super.a(_snowman);
   }

   protected boolean eN() {
      return true;
   }

   @Override
   public void j() {
      if (!this.l.v && this.aX() && !this.eD()) {
         if (this.eT()) {
            this.bu--;
            if (this.bu < 0) {
               this.eP();
            }
         } else if (this.eN()) {
            if (this.a(aef.b)) {
               this.bt++;
               if (this.bt >= 600) {
                  this.a(300);
               }
            } else {
               this.bt = -1;
            }
         }
      }

      super.j();
   }

   @Override
   public void k() {
      if (this.aX()) {
         boolean _snowman = this.T_() && this.eG();
         if (_snowman) {
            bmb _snowmanx = this.b(aqf.f);
            if (!_snowmanx.a()) {
               if (_snowmanx.e()) {
                  _snowmanx.b(_snowmanx.g() + this.J.nextInt(2));
                  if (_snowmanx.g() >= _snowmanx.h()) {
                     this.c(aqf.f);
                     this.a(aqf.f, bmb.b);
                  }
               }

               _snowman = false;
            }

            if (_snowman) {
               this.f(8);
            }
         }
      }

      super.k();
   }

   private void a(int var1) {
      this.bu = _snowman;
      this.ab().b(bp, true);
   }

   protected void eP() {
      this.b(aqe.q);
      if (!this.aA()) {
         this.l.a(null, 1040, this.cB(), 0);
      }
   }

   protected void b(aqe<? extends bej> var1) {
      bej _snowman = this.a(_snowman, true);
      if (_snowman != null) {
         _snowman.y(_snowman.l.d(_snowman.cB()).d());
         _snowman.u(_snowman.eK() && this.eU());
      }
   }

   protected boolean T_() {
      return true;
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (!super.a(_snowman, _snowman)) {
         return false;
      } else if (!(this.l instanceof aag)) {
         return false;
      } else {
         aag _snowman = (aag)this.l;
         aqm _snowmanx = this.A();
         if (_snowmanx == null && _snowman.k() instanceof aqm) {
            _snowmanx = (aqm)_snowman.k();
         }

         if (_snowmanx != null && this.l.ad() == aor.d && (double)this.J.nextFloat() < this.b(arl.l) && this.l.V().b(brt.d)) {
            int _snowmanxx = afm.c(this.cD());
            int _snowmanxxx = afm.c(this.cE());
            int _snowmanxxxx = afm.c(this.cH());
            bej _snowmanxxxxx = new bej(this.l);

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 50; _snowmanxxxxxx++) {
               int _snowmanxxxxxxx = _snowmanxx + afm.a(this.J, 7, 40) * afm.a(this.J, -1, 1);
               int _snowmanxxxxxxxx = _snowmanxxx + afm.a(this.J, 7, 40) * afm.a(this.J, -1, 1);
               int _snowmanxxxxxxxxx = _snowmanxxxx + afm.a(this.J, 7, 40) * afm.a(this.J, -1, 1);
               fx _snowmanxxxxxxxxxx = new fx(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
               aqe<?> _snowmanxxxxxxxxxxx = _snowmanxxxxx.X();
               ard.c _snowmanxxxxxxxxxxxx = ard.a(_snowmanxxxxxxxxxxx);
               if (bsg.a(_snowmanxxxxxxxxxxxx, this.l, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx) && ard.a(_snowmanxxxxxxxxxxx, _snowman, aqp.j, _snowmanxxxxxxxxxx, this.l.t)) {
                  _snowmanxxxxx.d((double)_snowmanxxxxxxx, (double)_snowmanxxxxxxxx, (double)_snowmanxxxxxxxxx);
                  if (!this.l.a((double)_snowmanxxxxxxx, (double)_snowmanxxxxxxxx, (double)_snowmanxxxxxxxxx, 7.0) && this.l.j(_snowmanxxxxx) && this.l.k(_snowmanxxxxx) && !this.l.d(_snowmanxxxxx.cc())
                     )
                   {
                     _snowmanxxxxx.h(_snowmanx);
                     _snowmanxxxxx.a(_snowman, this.l.d(_snowmanxxxxx.cB()), aqp.j, null, null);
                     _snowman.l(_snowmanxxxxx);
                     this.a(arl.l).c(new arj("Zombie reinforcement caller charge", -0.05F, arj.a.a));
                     _snowmanxxxxx.a(arl.l).c(new arj("Zombie reinforcement callee charge", -0.05F, arj.a.a));
                     break;
                  }
               }
            }
         }

         return true;
      }
   }

   @Override
   public boolean B(aqa var1) {
      boolean _snowman = super.B(_snowman);
      if (_snowman) {
         float _snowmanx = this.l.d(this.cB()).b();
         if (this.dD().a() && this.bq() && this.J.nextFloat() < _snowmanx * 0.3F) {
            _snowman.f(2 * (int)_snowmanx);
         }
      }

      return _snowman;
   }

   @Override
   protected adp I() {
      return adq.rH;
   }

   @Override
   protected adp e(apk var1) {
      return adq.rR;
   }

   @Override
   protected adp dq() {
      return adq.rM;
   }

   protected adp eL() {
      return adq.rX;
   }

   @Override
   protected void b(fx var1, ceh var2) {
      this.a(this.eL(), 0.15F, 1.0F);
   }

   @Override
   public aqq dC() {
      return aqq.b;
   }

   @Override
   protected void a(aos var1) {
      super.a(_snowman);
      if (this.J.nextFloat() < (this.l.ad() == aor.d ? 0.05F : 0.01F)) {
         int _snowman = this.J.nextInt(3);
         if (_snowman == 0) {
            this.a(aqf.a, new bmb(bmd.kA));
         } else {
            this.a(aqf.a, new bmb(bmd.kB));
         }
      }
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.a("IsBaby", this.w_());
      _snowman.a("CanBreakDoors", this.eU());
      _snowman.b("InWaterTime", this.aE() ? this.bt : -1);
      _snowman.b("DrownedConversionTime", this.eT() ? this.bu : -1);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.a(_snowman.q("IsBaby"));
      this.u(_snowman.q("CanBreakDoors"));
      this.bt = _snowman.h("InWaterTime");
      if (_snowman.c("DrownedConversionTime", 99) && _snowman.h("DrownedConversionTime") > -1) {
         this.a(_snowman.h("DrownedConversionTime"));
      }
   }

   @Override
   public void a(aag var1, aqm var2) {
      super.a(_snowman, _snowman);
      if ((_snowman.ad() == aor.c || _snowman.ad() == aor.d) && _snowman instanceof bfj) {
         if (_snowman.ad() != aor.d && this.J.nextBoolean()) {
            return;
         }

         bfj _snowman = (bfj)_snowman;
         bek _snowmanx = _snowman.a(aqe.ba, false);
         _snowmanx.a(_snowman, _snowman.d(_snowmanx.cB()), aqp.i, new bej.b(false, true), null);
         _snowmanx.a(_snowman.eX());
         _snowmanx.a((mt)_snowman.fj().a(mo.a).getValue());
         _snowmanx.g(_snowman.eO().a());
         _snowmanx.a(_snowman.eL());
         if (!this.aA()) {
            _snowman.a(null, 1026, this.cB(), 0);
         }
      }
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return this.w_() ? 0.93F : 1.74F;
   }

   @Override
   public boolean h(bmb var1) {
      return _snowman.b() == bmd.mg && this.w_() && this.br() ? false : super.h(_snowman);
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      _snowman = super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      float _snowman = _snowman.d();
      this.p(this.J.nextFloat() < 0.55F * _snowman);
      if (_snowman == null) {
         _snowman = new bej.b(a(_snowman.u_()), true);
      }

      if (_snowman instanceof bej.b) {
         bej.b _snowmanx = (bej.b)_snowman;
         if (_snowmanx.a) {
            this.a(true);
            if (_snowmanx.b) {
               if ((double)_snowman.u_().nextFloat() < 0.05) {
                  List<bac> _snowmanxx = _snowman.a(bac.class, this.cc().c(5.0, 3.0, 5.0), aqd.c);
                  if (!_snowmanxx.isEmpty()) {
                     bac _snowmanxxx = _snowmanxx.get(0);
                     _snowmanxxx.t(true);
                     this.m(_snowmanxxx);
                  }
               } else if ((double)_snowman.u_().nextFloat() < 0.05) {
                  bac _snowmanxx = aqe.j.a(this.l);
                  _snowmanxx.b(this.cD(), this.cE(), this.cH(), this.p, 0.0F);
                  _snowmanxx.a(_snowman, _snowman, aqp.g, null, null);
                  _snowmanxx.t(true);
                  this.m(_snowmanxx);
                  _snowman.c(_snowmanxx);
               }
            }
         }

         this.u(this.eK() && this.J.nextFloat() < _snowman * 0.1F);
         this.a(_snowman);
         this.b(_snowman);
      }

      if (this.b(aqf.f).a()) {
         LocalDate _snowmanx = LocalDate.now();
         int _snowmanxx = _snowmanx.get(ChronoField.DAY_OF_MONTH);
         int _snowmanxxx = _snowmanx.get(ChronoField.MONTH_OF_YEAR);
         if (_snowmanxxx == 10 && _snowmanxx == 31 && this.J.nextFloat() < 0.25F) {
            this.a(aqf.f, new bmb(this.J.nextFloat() < 0.1F ? bup.cV : bup.cU));
            this.bn[aqf.f.b()] = 0.0F;
         }
      }

      this.y(_snowman);
      return _snowman;
   }

   public static boolean a(Random var0) {
      return _snowman.nextFloat() < 0.05F;
   }

   protected void y(float var1) {
      this.eV();
      this.a(arl.c).c(new arj("Random spawn bonus", this.J.nextDouble() * 0.05F, arj.a.a));
      double _snowman = this.J.nextDouble() * 1.5 * (double)_snowman;
      if (_snowman > 1.0) {
         this.a(arl.b).c(new arj("Random zombie-spawn bonus", _snowman, arj.a.c));
      }

      if (this.J.nextFloat() < _snowman * 0.05F) {
         this.a(arl.l).c(new arj("Leader zombie bonus", this.J.nextDouble() * 0.25 + 0.5, arj.a.a));
         this.a(arl.a).c(new arj("Leader zombie bonus", this.J.nextDouble() * 3.0 + 1.0, arj.a.c));
         this.u(this.eK());
      }
   }

   protected void eV() {
      this.a(arl.l).a(this.J.nextDouble() * 0.1F);
   }

   @Override
   public double bb() {
      return this.w_() ? 0.0 : -0.45;
   }

   @Override
   protected void a(apk var1, int var2, boolean var3) {
      super.a(_snowman, _snowman, _snowman);
      aqa _snowman = _snowman.k();
      if (_snowman instanceof bdc) {
         bdc _snowmanx = (bdc)_snowman;
         if (_snowmanx.eN()) {
            bmb _snowmanxx = this.eM();
            if (!_snowmanxx.a()) {
               _snowmanx.eO();
               this.a(_snowmanxx);
            }
         }
      }
   }

   protected bmb eM() {
      return new bmb(bmd.ph);
   }

   class a extends awy {
      a(aqu var2, double var3, int var5) {
         super(bup.kf, _snowman, _snowman, _snowman);
      }

      @Override
      public void a(bry var1, fx var2) {
         _snowman.a(null, _snowman, adq.rN, adr.f, 0.5F, 0.9F + bej.this.J.nextFloat() * 0.2F);
      }

      @Override
      public void a(brx var1, fx var2) {
         _snowman.a(null, _snowman, adq.pw, adr.e, 0.7F, 0.9F + _snowman.t.nextFloat() * 0.2F);
      }

      @Override
      public double h() {
         return 1.14;
      }
   }

   public static class b implements arc {
      public final boolean a;
      public final boolean b;

      public b(boolean var1, boolean var2) {
         this.a = _snowman;
         this.b = _snowman;
      }
   }
}
