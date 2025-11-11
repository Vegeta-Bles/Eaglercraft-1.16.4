import com.google.common.collect.UnmodifiableIterator;
import javax.annotation.Nullable;

public class ban extends azz implements aqk, ara {
   private static final us<Boolean> bo = uv.a(ban.class, uu.i);
   private static final us<Integer> bp = uv.a(ban.class, uu.b);
   private static final bon bq = bon.a(bmd.oY, bmd.oZ, bmd.qf);
   private final aqj br = new aqj(this.R, bp, bo);

   public ban(aqe<? extends ban> var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   protected void o() {
      this.bk.a(0, new avp(this));
      this.bk.a(1, new awp(this, 1.25));
      this.bk.a(3, new avi(this, 1.0));
      this.bk.a(4, new axf(this, 1.2, bon.a(bmd.pk), false));
      this.bk.a(4, new axf(this, 1.2, false, bq));
      this.bk.a(5, new avu(this, 1.1));
      this.bk.a(6, new axk(this, 1.0));
      this.bk.a(7, new awd(this, bfw.class, 6.0F));
      this.bk.a(8, new aws(this));
   }

   public static ark.a eK() {
      return aqn.p().a(arl.a, 10.0).a(arl.d, 0.25);
   }

   @Nullable
   @Override
   public aqa cm() {
      return this.cn().isEmpty() ? null : this.cn().get(0);
   }

   @Override
   public boolean er() {
      aqa _snowman = this.cm();
      if (!(_snowman instanceof bfw)) {
         return false;
      } else {
         bfw _snowmanx = (bfw)_snowman;
         return _snowmanx.dD().b() == bmd.pk || _snowmanx.dE().b() == bmd.pk;
      }
   }

   @Override
   public void a(us<?> var1) {
      if (bp.equals(_snowman) && this.l.v) {
         this.br.a();
      }

      super.a(_snowman);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(bo, false);
      this.R.a(bp, 0);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      this.br.a(_snowman);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.br.b(_snowman);
   }

   @Override
   protected adp I() {
      return adq.kN;
   }

   @Override
   protected adp e(apk var1) {
      return adq.kP;
   }

   @Override
   protected adp dq() {
      return adq.kO;
   }

   @Override
   protected void b(fx var1, ceh var2) {
      this.a(adq.kR, 0.15F, 1.0F);
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
            return _snowmanx;
         }
      }
   }

   @Override
   public boolean L_() {
      return this.aX() && !this.w_();
   }

   @Override
   protected void dn() {
      super.dn();
      if (this.M_()) {
         this.a(bmd.lO);
      }
   }

   @Override
   public boolean M_() {
      return this.br.b();
   }

   @Override
   public void a(@Nullable adr var1) {
      this.br.a(true);
      if (_snowman != null) {
         this.l.a(null, this, adq.kQ, _snowman, 0.5F, 1.0F);
      }
   }

   @Override
   public dcn b(aqm var1) {
      gc _snowman = this.ca();
      if (_snowman.n() == gc.a.b) {
         return super.b(_snowman);
      } else {
         int[][] _snowmanx = bho.a(_snowman);
         fx _snowmanxx = this.cB();
         fx.a _snowmanxxx = new fx.a();
         UnmodifiableIterator var6 = _snowman.ej().iterator();

         while (var6.hasNext()) {
            aqx _snowmanxxxx = (aqx)var6.next();
            dci _snowmanxxxxx = _snowman.f(_snowmanxxxx);

            for (int[] _snowmanxxxxxx : _snowmanx) {
               _snowmanxxx.d(_snowmanxx.u() + _snowmanxxxxxx[0], _snowmanxx.v(), _snowmanxx.w() + _snowmanxxxxxx[1]);
               double _snowmanxxxxxxx = this.l.h(_snowmanxxx);
               if (bho.a(_snowmanxxxxxxx)) {
                  dcn _snowmanxxxxxxxx = dcn.a(_snowmanxxx, _snowmanxxxxxxx);
                  if (bho.a(this.l, _snowman, _snowmanxxxxx.c(_snowmanxxxxxxxx))) {
                     _snowman.b(_snowmanxxxx);
                     return _snowmanxxxxxxxx;
                  }
               }
            }
         }

         return super.b(_snowman);
      }
   }

   @Override
   public void a(aag var1, aql var2) {
      if (_snowman.ad() != aor.a) {
         bel _snowman = aqe.bb.a(_snowman);
         _snowman.a(aqf.a, new bmb(bmd.kv));
         _snowman.b(this.cD(), this.cE(), this.cH(), this.p, this.q);
         _snowman.q(this.eD());
         _snowman.a(this.w_());
         if (this.S()) {
            _snowman.a(this.T());
            _snowman.n(this.bX());
         }

         _snowman.es();
         _snowman.c(_snowman);
         this.ad();
      } else {
         super.a(_snowman, _snowman);
      }
   }

   @Override
   public void g(dcn var1) {
      this.a(this, this.br, _snowman);
   }

   @Override
   public float N_() {
      return (float)this.b(arl.d) * 0.225F;
   }

   @Override
   public void a_(dcn var1) {
      super.g(_snowman);
   }

   @Override
   public boolean O_() {
      return this.br.a(this.cY());
   }

   public ban b(aag var1, apy var2) {
      return aqe.ah.a(_snowman);
   }

   @Override
   public boolean k(bmb var1) {
      return bq.a(_snowman);
   }

   @Override
   public dcn cf() {
      return new dcn(0.0, (double)(0.6F * this.ce()), (double)(this.cy() * 0.4F));
   }
}
