import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;

public class bed extends azz implements aqk, ara {
   private static final bon bo = bon.a(bmd.bx);
   private static final bon bp = bon.a(bmd.bx, bmd.pl);
   private static final us<Integer> bq = uv.a(bed.class, uu.b);
   private static final us<Boolean> br = uv.a(bed.class, uu.i);
   private static final us<Boolean> bs = uv.a(bed.class, uu.i);
   private final aqj bt = new aqj(this.R, bq, bs);
   private axf bu;
   private awp bv;

   public bed(aqe<? extends bed> var1, brx var2) {
      super(_snowman, _snowman);
      this.i = true;
      this.a(cwz.h, -1.0F);
      this.a(cwz.g, 0.0F);
      this.a(cwz.l, 0.0F);
      this.a(cwz.m, 0.0F);
   }

   public static boolean c(aqe<bed> var0, bry var1, aqp var2, fx var3, Random var4) {
      fx.a _snowman = _snowman.i();

      do {
         _snowman.c(gc.b);
      } while (_snowman.b(_snowman).a(aef.c));

      return _snowman.d_(_snowman).g();
   }

   @Override
   public void a(us<?> var1) {
      if (bq.equals(_snowman) && this.l.v) {
         this.bt.a();
      }

      super.a(_snowman);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(bq, 0);
      this.R.a(br, false);
      this.R.a(bs, false);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      this.bt.a(_snowman);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.bt.b(_snowman);
   }

   @Override
   public boolean M_() {
      return this.bt.b();
   }

   @Override
   public boolean L_() {
      return this.aX() && !this.w_();
   }

   @Override
   public void a(@Nullable adr var1) {
      this.bt.a(true);
      if (_snowman != null) {
         this.l.a(null, this, adq.oi, _snowman, 0.5F, 1.0F);
      }
   }

   @Override
   protected void o() {
      this.bv = new awp(this, 1.65);
      this.bk.a(1, this.bv);
      this.bk.a(2, new avi(this, 1.0));
      this.bu = new axf(this, 1.4, false, bp);
      this.bk.a(3, this.bu);
      this.bk.a(4, new bed.a(this, 1.5));
      this.bk.a(5, new avu(this, 1.1));
      this.bk.a(7, new awt(this, 1.0, 60));
      this.bk.a(8, new awd(this, bfw.class, 8.0F));
      this.bk.a(8, new aws(this));
      this.bk.a(9, new awd(this, bed.class, 8.0F));
   }

   public void t(boolean var1) {
      this.R.b(br, _snowman);
   }

   public boolean eK() {
      return this.ct() instanceof bed ? ((bed)this.ct()).eK() : this.R.a(br);
   }

   @Override
   public boolean a(cuw var1) {
      return _snowman.a(aef.c);
   }

   @Override
   public double bc() {
      float _snowman = Math.min(0.25F, this.av);
      float _snowmanx = this.aw;
      return (double)this.cz() - 0.19 + (double)(0.12F * afm.b(_snowmanx * 1.5F) * 2.0F * _snowman);
   }

   @Override
   public boolean er() {
      aqa _snowman = this.cm();
      if (!(_snowman instanceof bfw)) {
         return false;
      } else {
         bfw _snowmanx = (bfw)_snowman;
         return _snowmanx.dD().b() == bmd.pl || _snowmanx.dE().b() == bmd.pl;
      }
   }

   @Override
   public boolean a(brz var1) {
      return _snowman.j(this);
   }

   @Nullable
   @Override
   public aqa cm() {
      return this.cn().isEmpty() ? null : this.cn().get(0);
   }

   @Override
   public dcn b(aqm var1) {
      dcn[] _snowman = new dcn[]{
         a((double)this.cy(), (double)_snowman.cy(), _snowman.p),
         a((double)this.cy(), (double)_snowman.cy(), _snowman.p - 22.5F),
         a((double)this.cy(), (double)_snowman.cy(), _snowman.p + 22.5F),
         a((double)this.cy(), (double)_snowman.cy(), _snowman.p - 45.0F),
         a((double)this.cy(), (double)_snowman.cy(), _snowman.p + 45.0F)
      };
      Set<fx> _snowmanx = Sets.newLinkedHashSet();
      double _snowmanxx = this.cc().e;
      double _snowmanxxx = this.cc().b - 0.5;
      fx.a _snowmanxxxx = new fx.a();

      for (dcn _snowmanxxxxx : _snowman) {
         _snowmanxxxx.c(this.cD() + _snowmanxxxxx.b, _snowmanxx, this.cH() + _snowmanxxxxx.d);

         for (double _snowmanxxxxxx = _snowmanxx; _snowmanxxxxxx > _snowmanxxx; _snowmanxxxxxx--) {
            _snowmanx.add(_snowmanxxxx.h());
            _snowmanxxxx.c(gc.a);
         }
      }

      for (fx _snowmanxxxxx : _snowmanx) {
         if (!this.l.b(_snowmanxxxxx).a(aef.c)) {
            double _snowmanxxxxxx = this.l.h(_snowmanxxxxx);
            if (bho.a(_snowmanxxxxxx)) {
               dcn _snowmanxxxxxxx = dcn.a(_snowmanxxxxx, _snowmanxxxxxx);
               UnmodifiableIterator var14 = _snowman.ej().iterator();

               while (var14.hasNext()) {
                  aqx _snowmanxxxxxxxx = (aqx)var14.next();
                  dci _snowmanxxxxxxxxx = _snowman.f(_snowmanxxxxxxxx);
                  if (bho.a(this.l, _snowman, _snowmanxxxxxxxxx.c(_snowmanxxxxxxx))) {
                     _snowman.b(_snowmanxxxxxxxx);
                     return _snowmanxxxxxxx;
                  }
               }
            }
         }
      }

      return new dcn(this.cD(), this.cc().e, this.cH());
   }

   @Override
   public void g(dcn var1) {
      this.q(this.eL());
      this.a(this, this.bt, _snowman);
   }

   public float eL() {
      return (float)this.b(arl.d) * (this.eK() ? 0.66F : 1.0F);
   }

   @Override
   public float N_() {
      return (float)this.b(arl.d) * (this.eK() ? 0.23F : 0.55F);
   }

   @Override
   public void a_(dcn var1) {
      super.g(_snowman);
   }

   @Override
   protected float at() {
      return this.B + 0.6F;
   }

   @Override
   protected void b(fx var1, ceh var2) {
      this.a(this.aQ() ? adq.og : adq.of, 1.0F, 1.0F);
   }

   @Override
   public boolean O_() {
      return this.bt.a(this.cY());
   }

   @Override
   protected void a(double var1, boolean var3, ceh var4, fx var5) {
      this.ay();
      if (this.aQ()) {
         this.C = 0.0F;
      } else {
         super.a(_snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public void j() {
      if (this.eO() && this.J.nextInt(140) == 0) {
         this.a(adq.ob, 1.0F, this.dH());
      } else if (this.eN() && this.J.nextInt(60) == 0) {
         this.a(adq.oc, 1.0F, this.dH());
      }

      ceh _snowman = this.l.d_(this.cB());
      ceh _snowmanx = this.aN();
      boolean _snowmanxx = _snowman.a(aed.ax) || _snowmanx.a(aed.ax) || this.b(aef.c) > 0.0;
      this.t(!_snowmanxx);
      super.j();
      this.eU();
      this.ay();
   }

   private boolean eN() {
      return this.bv != null && this.bv.h();
   }

   private boolean eO() {
      return this.bu != null && this.bu.h();
   }

   @Override
   protected boolean q() {
      return true;
   }

   private void eU() {
      if (this.aQ()) {
         dcs _snowman = dcs.a(this);
         if (_snowman.a(byb.c, this.cB(), true) && !this.l.b(this.cB().b()).a(aef.c)) {
            this.t = true;
         } else {
            this.f(this.cC().a(0.5).b(0.0, 0.05, 0.0));
         }
      }
   }

   public static ark.a eM() {
      return aqn.p().a(arl.d, 0.175F).a(arl.b, 16.0);
   }

   @Override
   protected adp I() {
      return !this.eN() && !this.eO() ? adq.oa : null;
   }

   @Override
   protected adp e(apk var1) {
      return adq.oe;
   }

   @Override
   protected adp dq() {
      return adq.od;
   }

   @Override
   protected boolean q(aqa var1) {
      return this.cn().isEmpty() && !this.a(aef.c);
   }

   @Override
   public boolean dO() {
      return true;
   }

   @Override
   public boolean bq() {
      return false;
   }

   @Override
   protected ayj b(brx var1) {
      return new bed.b(this, _snowman);
   }

   @Override
   public float a(fx var1, brz var2) {
      if (_snowman.d_(_snowman).m().a(aef.c)) {
         return 10.0F;
      } else {
         return this.aQ() ? Float.NEGATIVE_INFINITY : 0.0F;
      }
   }

   public bed b(aag var1, apy var2) {
      return aqe.aF.a(_snowman);
   }

   @Override
   public boolean k(bmb var1) {
      return bo.a(_snowman);
   }

   @Override
   protected void dn() {
      super.dn();
      if (this.M_()) {
         this.a(bmd.lO);
      }
   }

   @Override
   public aou b(bfw var1, aot var2) {
      boolean _snowman = this.k(_snowman.b(_snowman));
      if (!_snowman && this.M_() && !this.bs() && !_snowman.eq()) {
         if (!this.l.v) {
            _snowman.m(this);
         }

         return aou.a(this.l.v);
      } else {
         aou _snowmanx = super.b(_snowman, _snowman);
         if (!_snowmanx.a()) {
            bmb _snowmanxx = _snowman.b(_snowman);
            return _snowmanxx.b() == bmd.lO ? _snowmanxx.a(_snowman, this, _snowman) : aou.c;
         } else {
            if (_snowman && !this.aA()) {
               this.l.a(null, this.cD(), this.cE(), this.cH(), adq.oh, this.cu(), 1.0F, 1.0F + (this.J.nextFloat() - this.J.nextFloat()) * 0.2F);
            }

            return _snowmanx;
         }
      }
   }

   @Override
   public dcn cf() {
      return new dcn(0.0, (double)(0.6F * this.ce()), (double)(this.cy() * 0.4F));
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      if (this.w_()) {
         return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      } else {
         Object var7;
         if (this.J.nextInt(30) == 0) {
            aqn _snowman = aqe.bb.a(_snowman.E());
            var7 = this.a(_snowman, _snowman, _snowman, new bej.b(bej.a(this.J), false));
            _snowman.a(aqf.a, new bmb(bmd.pl));
            this.a(null);
         } else if (this.J.nextInt(10) == 0) {
            apy _snowman = aqe.aF.a(_snowman.E());
            _snowman.c_(-24000);
            var7 = this.a(_snowman, _snowman, _snowman, null);
         } else {
            var7 = new apy.a(0.5F);
         }

         return super.a(_snowman, _snowman, _snowman, (arc)var7, _snowman);
      }
   }

   private arc a(bsk var1, aos var2, aqn var3, @Nullable arc var4) {
      _snowman.b(this.cD(), this.cE(), this.cH(), this.p, 0.0F);
      _snowman.a(_snowman, _snowman, aqp.g, _snowman, null);
      _snowman.a(this, true);
      return new apy.a(0.0F);
   }

   static class a extends awj {
      private final bed g;

      private a(bed var1, double var2) {
         super(_snowman, _snowman, 8, 2);
         this.g = _snowman;
      }

      @Override
      public fx j() {
         return this.e;
      }

      @Override
      public boolean b() {
         return !this.g.aQ() && this.a(this.g.l, this.e);
      }

      @Override
      public boolean a() {
         return !this.g.aQ() && super.a();
      }

      @Override
      public boolean k() {
         return this.d % 20 == 0;
      }

      @Override
      protected boolean a(brz var1, fx var2) {
         return _snowman.d_(_snowman).a(bup.B) && _snowman.d_(_snowman.b()).a(_snowman, _snowman, cxe.a);
      }
   }

   static class b extends ayi {
      b(bed var1, brx var2) {
         super(_snowman, _snowman);
      }

      @Override
      protected cxf a(int var1) {
         this.o = new cxj();
         return new cxf(this.o, _snowman);
      }

      @Override
      protected boolean a(cwz var1) {
         return _snowman != cwz.g && _snowman != cwz.m && _snowman != cwz.l ? super.a(_snowman) : true;
      }

      @Override
      public boolean a(fx var1) {
         return this.b.d_(_snowman).a(bup.B) || super.a(_snowman);
      }
   }
}
