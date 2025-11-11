import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bam extends bat implements bag {
   private static final us<Integer> bu = uv.a(bam.class, uu.b);
   private static final Predicate<aqn> bv = new Predicate<aqn>() {
      public boolean a(@Nullable aqn var1) {
         return _snowman != null && bam.by.containsKey(_snowman.X());
      }
   };
   private static final blx bw = bmd.ne;
   private static final Set<blx> bx = Sets.newHashSet(new blx[]{bmd.kV, bmd.nk, bmd.nj, bmd.qg});
   private static final Map<aqe<?>, adp> by = x.a(Maps.newHashMap(), var0 -> {
      var0.put(aqe.f, adq.ka);
      var0.put(aqe.i, adq.kw);
      var0.put(aqe.m, adq.kb);
      var0.put(aqe.q, adq.kc);
      var0.put(aqe.r, adq.kd);
      var0.put(aqe.t, adq.ke);
      var0.put(aqe.v, adq.kf);
      var0.put(aqe.w, adq.kg);
      var0.put(aqe.D, adq.kh);
      var0.put(aqe.F, adq.ki);
      var0.put(aqe.G, adq.kj);
      var0.put(aqe.I, adq.kk);
      var0.put(aqe.J, adq.kl);
      var0.put(aqe.S, adq.km);
      var0.put(aqe.ag, adq.kn);
      var0.put(aqe.ai, adq.ko);
      var0.put(aqe.aj, adq.kp);
      var0.put(aqe.ak, adq.kq);
      var0.put(aqe.ap, adq.kr);
      var0.put(aqe.as, adq.ks);
      var0.put(aqe.au, adq.kt);
      var0.put(aqe.av, adq.ku);
      var0.put(aqe.ax, adq.kv);
      var0.put(aqe.aC, adq.kw);
      var0.put(aqe.aE, adq.kx);
      var0.put(aqe.aO, adq.ky);
      var0.put(aqe.aQ, adq.kz);
      var0.put(aqe.aS, adq.kA);
      var0.put(aqe.aT, adq.kB);
      var0.put(aqe.aU, adq.kC);
      var0.put(aqe.aX, adq.kD);
      var0.put(aqe.aY, adq.kE);
      var0.put(aqe.ba, adq.kF);
   });
   public float bq;
   public float br;
   public float bs;
   public float bt;
   private float bz = 1.0F;
   private boolean bA;
   private fx bB;

   public bam(aqe<? extends bam> var1, brx var2) {
      super(_snowman, _snowman);
      this.bh = new auy(this, 10, false);
      this.a(cwz.l, -1.0F);
      this.a(cwz.m, -1.0F);
      this.a(cwz.x, -1.0F);
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      this.t(this.J.nextInt(5));
      if (_snowman == null) {
         _snowman = new apy.a(false);
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean w_() {
      return false;
   }

   @Override
   protected void o() {
      this.bk.a(0, new awp(this, 1.25));
      this.bk.a(0, new avp(this));
      this.bk.a(1, new awd(this, bfw.class, 8.0F));
      this.bk.a(2, new axb(this));
      this.bk.a(2, new avt(this, 1.0, 5.0F, 1.0F, true));
      this.bk.a(2, new axj(this, 1.0));
      this.bk.a(3, new awa(this));
      this.bk.a(3, new avs(this, 1.0, 3.0F, 7.0F));
   }

   public static ark.a eU() {
      return aqn.p().a(arl.a, 6.0).a(arl.e, 0.4F).a(arl.d, 0.2F);
   }

   @Override
   protected ayj b(brx var1) {
      ayh _snowman = new ayh(this, _snowman);
      _snowman.a(false);
      _snowman.d(true);
      _snowman.b(true);
      return _snowman;
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return _snowman.b * 0.6F;
   }

   @Override
   public void k() {
      if (this.bB == null || !this.bB.a(this.cA(), 3.46) || !this.l.d_(this.bB).a(bup.cI)) {
         this.bA = false;
         this.bB = null;
      }

      if (this.l.t.nextInt(400) == 0) {
         a(this.l, this);
      }

      super.k();
      this.eZ();
   }

   @Override
   public void a(fx var1, boolean var2) {
      this.bB = _snowman;
      this.bA = _snowman;
   }

   public boolean eV() {
      return this.bA;
   }

   private void eZ() {
      this.bt = this.bq;
      this.bs = this.br;
      this.br = (float)((double)this.br + (double)(!this.t && !this.br() ? 4 : -1) * 0.3);
      this.br = afm.a(this.br, 0.0F, 1.0F);
      if (!this.t && this.bz < 1.0F) {
         this.bz = 1.0F;
      }

      this.bz = (float)((double)this.bz * 0.9);
      dcn _snowman = this.cC();
      if (!this.t && _snowman.c < 0.0) {
         this.f(_snowman.d(1.0, 0.6, 1.0));
      }

      this.bq = this.bq + this.bz * 2.0F;
   }

   public static boolean a(brx var0, aqa var1) {
      if (_snowman.aX() && !_snowman.aA() && _snowman.t.nextInt(2) == 0) {
         List<aqn> _snowman = _snowman.a(aqn.class, _snowman.cc().g(20.0), bv);
         if (!_snowman.isEmpty()) {
            aqn _snowmanx = _snowman.get(_snowman.t.nextInt(_snowman.size()));
            if (!_snowmanx.aA()) {
               adp _snowmanxx = b(_snowmanx.X());
               _snowman.a(null, _snowman.cD(), _snowman.cE(), _snowman.cH(), _snowmanxx, _snowman.cu(), 0.7F, a(_snowman.t));
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   @Override
   public aou b(bfw var1, aot var2) {
      bmb _snowman = _snowman.b(_snowman);
      if (!this.eK() && bx.contains(_snowman.b())) {
         if (!_snowman.bC.d) {
            _snowman.g(1);
         }

         if (!this.aA()) {
            this.l.a(null, this.cD(), this.cE(), this.cH(), adq.jX, this.cu(), 1.0F, 1.0F + (this.J.nextFloat() - this.J.nextFloat()) * 0.2F);
         }

         if (!this.l.v) {
            if (this.J.nextInt(10) == 0) {
               this.f(_snowman);
               this.l.a(this, (byte)7);
            } else {
               this.l.a(this, (byte)6);
            }
         }

         return aou.a(this.l.v);
      } else if (_snowman.b() == bw) {
         if (!_snowman.bC.d) {
            _snowman.g(1);
         }

         this.c(new apu(apw.s, 900));
         if (_snowman.b_() || !this.bM()) {
            this.a(apk.a(_snowman), Float.MAX_VALUE);
         }

         return aou.a(this.l.v);
      } else if (!this.fa() && this.eK() && this.i(_snowman)) {
         if (!this.l.v) {
            this.w(!this.eO());
         }

         return aou.a(this.l.v);
      } else {
         return super.b(_snowman, _snowman);
      }
   }

   @Override
   public boolean k(bmb var1) {
      return false;
   }

   public static boolean c(aqe<bam> var0, bry var1, aqp var2, fx var3, Random var4) {
      ceh _snowman = _snowman.d_(_snowman.c());
      return (_snowman.a(aed.I) || _snowman.a(bup.i) || _snowman.a(aed.s) || _snowman.a(bup.a)) && _snowman.b(_snowman, 0) > 8;
   }

   @Override
   public boolean b(float var1, float var2) {
      return false;
   }

   @Override
   protected void a(double var1, boolean var3, ceh var4, fx var5) {
   }

   @Override
   public boolean a(azz var1) {
      return false;
   }

   @Nullable
   @Override
   public apy a(aag var1, apy var2) {
      return null;
   }

   @Override
   public boolean B(aqa var1) {
      return _snowman.a(apk.c(this), 3.0F);
   }

   @Nullable
   @Override
   public adp I() {
      return a(this.l, this.l.t);
   }

   public static adp a(brx var0, Random var1) {
      if (_snowman.ad() != aor.a && _snowman.nextInt(1000) == 0) {
         List<aqe<?>> _snowman = Lists.newArrayList(by.keySet());
         return b(_snowman.get(_snowman.nextInt(_snowman.size())));
      } else {
         return adq.jV;
      }
   }

   private static adp b(aqe<?> var0) {
      return by.getOrDefault(_snowman, adq.jV);
   }

   @Override
   protected adp e(apk var1) {
      return adq.jZ;
   }

   @Override
   protected adp dq() {
      return adq.jW;
   }

   @Override
   protected void b(fx var1, ceh var2) {
      this.a(adq.kG, 0.15F, 1.0F);
   }

   @Override
   protected float e(float var1) {
      this.a(adq.jY, 0.15F, 1.0F);
      return _snowman + this.br / 2.0F;
   }

   @Override
   protected boolean az() {
      return true;
   }

   @Override
   protected float dH() {
      return a(this.J);
   }

   public static float a(Random var0) {
      return (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F;
   }

   @Override
   public adr cu() {
      return adr.g;
   }

   @Override
   public boolean aU() {
      return true;
   }

   @Override
   protected void C(aqa var1) {
      if (!(_snowman instanceof bfw)) {
         super.C(_snowman);
      }
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (this.b(_snowman)) {
         return false;
      } else {
         this.w(false);
         return super.a(_snowman, _snowman);
      }
   }

   public int eW() {
      return afm.a(this.R.a(bu), 0, 4);
   }

   public void t(int var1) {
      this.R.b(bu, _snowman);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(bu, 0);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("Variant", this.eW());
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.t(_snowman.h("Variant"));
   }

   public boolean fa() {
      return !this.t;
   }

   @Override
   public dcn cf() {
      return new dcn(0.0, (double)(0.5F * this.ce()), (double)(this.cy() * 0.4F));
   }
}
